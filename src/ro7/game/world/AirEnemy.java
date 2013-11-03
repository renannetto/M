package ro7.game.world;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import ro7.engine.sprites.shapes.AAB;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.CollidableEntity;
import ro7.engine.world.io.Input;
import ro7.engine.world.io.Output;
import cs195n.Vec2f;

public class AirEnemy extends CollidableEntity implements MEntity {

	private final float DETECTION_RANGE = 10.0f;
	private final float DETECTION_DISTANCE = 400.0f;
	private final String AREA_CATEGORY_MASK = "8";
	private final String AREA_COLLISION_MASK = "1";
	private final int BULLET_MASK = 2;
	private final int BULLET_COLLISION = 3;

	private CollisionArea collisionArea;

	public AirEnemy(final GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
		Vec2f position = shape.getPosition();
		
		Map<String, String> areaProperties = new HashMap<String, String>();
		areaProperties.put("categoryMask", AREA_CATEGORY_MASK);
		areaProperties.put("collisionMask", AREA_COLLISION_MASK);
		collisionArea = new CollisionArea(world, new AAB(position, null, null,
				new Vec2f(DETECTION_RANGE, DETECTION_DISTANCE)), name + "collisionArea", areaProperties);
		
		outputs.put("onPlayerDetect", new Output());
		
		inputs.put("doShoot", new Input() {
			
			@Override
			public void run(Map<String, String> args) {
				((MWorld)world).addShoot(shoot());
			}
		});
	}

	@Override
	public void draw(Graphics2D g) {
		shape.draw(g);
	}

	@Override
	public void update(long nanoseconds) {
		Collision collision = ((MWorld) world).collidesPlayer(collisionArea);
		if (collision.validCollision()) {
			outputs.get("onPlayerDetect").run();
		}
		
		this.shape.update(nanoseconds);
	}
	
	public Bullet shoot() {
		Vec2f position = shape.center();
		return new Bullet(world, BULLET_MASK, BULLET_COLLISION, position, new Vec2f(0.0f, 1.0f));
	}

	@Override
	public void onCollision(Collision collision) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shooted(Vec2f impulse) {
		world.removeEntity(name);
	}
	
	@Override
	public void exploded(Vec2f impulse) {
		
	}

}
