package ro7.game.world;

import java.awt.Color;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ro7.engine.sprites.shapes.Circle;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.RayCollision;
import cs195n.Vec2f;

public class Grenade extends MDynamicEntity {

	private static final Color COLOR = Color.GRAY;
	private static final float GRENADE_RADIUS = 10.0f;

	private final float EXPLOSION_RADIUS = 200.0f;
	private final float EXPLOSION_TIME = 3.0f;
	private final float EXPLOSION_IMPULSE = 1000.0f;

	private Set<GrenadeRay> rays;
	private float elapsedTime;

	protected Grenade(GameWorld world, Vec2f position, Map<String, String> properties) {
		super(world, position, new Circle(position, COLOR, COLOR, GRENADE_RADIUS), properties);

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
			Vec2f direction = explosion.point.minus(position).normalized();
			MEntity entity = (MEntity) explosion.other;
			entity.shooted(direction.smult(EXPLOSION_IMPULSE));
		}

		((MWorld) world).removeGrenade(this);
	}
	
	@Override
	public void onCollision(Collision collision) {
		explode();
	}

}
