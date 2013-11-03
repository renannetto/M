package ro7.game.world;

import java.awt.Color;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ro7.engine.sprites.ImageSprite;
import ro7.engine.sprites.shapes.Circle;
import ro7.engine.sprites.shapes.CollidingSprite;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.RayCollision;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class Grenade extends MDynamicEntity {

	private static final String SPRITE_SHEET = "objects.png";
	private static final Vec2i SHEET_POSITION = new Vec2i(1, 4);
	private static final Color COLOR = Color.GRAY;
	private static final float GRENADE_RADIUS = 10.0f;

	private final float EXPLOSION_RADIUS = 100.0f;
	private final float EXPLOSION_TIME = 3.0f;
	private final float EXPLOSION_IMPULSE = 1000.0f;

	private Set<GrenadeRay> rays;
	private float elapsedTime;

	protected Grenade(GameWorld world, Map<String, String> properties, Vec2f position) {
		super(world, new CollidingSprite(new ImageSprite(position, world.getSpriteSheet(SPRITE_SHEET), SHEET_POSITION), new Circle(position, COLOR, COLOR, GRENADE_RADIUS)), null, properties);
		name = toString();

		rays = new HashSet<GrenadeRay>();
	}

	@Override
	public void update(long nanoseconds) {
		super.update(nanoseconds);

		elapsedTime += nanoseconds / 1000000000.0f;
		if (elapsedTime > EXPLOSION_TIME) {
			explode();
		}
	}

	private void explode() {
		Vec2f position = shape.getPosition();
		rays.add(new GrenadeRay(world, position, new Vec2f(1.0f, 0.0f)));
		rays.add(new GrenadeRay(world, position, new Vec2f(1.0f, -1.0f)));
		rays.add(new GrenadeRay(world, position, new Vec2f(0.0f, -1.0f)));
		rays.add(new GrenadeRay(world, position, new Vec2f(-1.0f, -1.0f)));
		rays.add(new GrenadeRay(world, position, new Vec2f(-1.0f, 0.0f)));
		rays.add(new GrenadeRay(world, position, new Vec2f(-1.0f, 1.0f)));
		rays.add(new GrenadeRay(world, position, new Vec2f(0.0f, 1.0f)));
		rays.add(new GrenadeRay(world, position, new Vec2f(1.0f, 1.0f)));

		Set<MEntity> exploded = new HashSet<MEntity>();
		Set<RayCollision> explosions = new HashSet<RayCollision>();
		for (GrenadeRay ray : rays) {
			RayCollision collision = world.getCollided(ray);
			if (collision != null && collision.validCollision()) {
				float distance = collision.point.dist(position);
				MEntity entity = (MEntity) collision.other;
				if (distance < EXPLOSION_RADIUS && !exploded.contains(entity)) {
					exploded.add((MEntity) collision.other);
					explosions.add(collision);
				}
			}
		}

		for (RayCollision explosion : explosions) {
			Vec2f direction = explosion.point.minus(position);
			if (direction.mag2()>0) {
				direction = direction.normalized();
			}
			MEntity entity = (MEntity) explosion.other;
			entity.exploded(direction.smult(EXPLOSION_IMPULSE));
		}

		world.removeEntity(this.toString());
	}
	
	@Override
	public void onCollision(Collision collision) {
		explode();
	}
	
	@Override
	public void onCollisionDynamic(Collision collision) {
		explode();
	}
	
	@Override
	public void onCollisionStatic(Collision collision) {
		explode();
	}

}
