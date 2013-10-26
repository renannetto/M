package ro7.game.world;

import java.util.HashMap;
import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import cs195n.Vec2f;

public class Player extends MDynamicEntity {
	
	private final String GRENADE_MASS = "1.0f";
	private final float GRENADE_VELOCITY = 500.0f;
	private final String GRENADE_RESTITUTION = "0.5f";
	private final String GRENADE_GROUP = "-2";

	private final static float VELOCITY = 300.0f;
	private final static Vec2f DIMENSIONS = new Vec2f(50.0f, 50.0f);
	private final float K = 100.0f;
	private final Vec2f JUMP_IMPULSE = new Vec2f(0.0f, -1000.0f);

	public Player(GameWorld world, Vec2f position, CollidingShape shape, Map<String, String> properties) {
		super(world, position, shape, properties);
	}

	public void move(Vec2f direction) {
		Vec2f goalVelocity = direction.smult(VELOCITY);
		Vec2f force;
		if (direction.x != 0) {
			force = new Vec2f(K * (goalVelocity.x - velocity.x), 0.0f);
		} else {
			force = new Vec2f(0.0f, K * (goalVelocity.y - velocity.y));
		}
		applyForce(force);
	}

	public void jump() {
		applyImpulse(JUMP_IMPULSE);
	}

	public Bullet shoot(Vec2f point) {
		Vec2f direction = point.minus(position).normalized();
		
		Vec2f bulletPosition = shape.center();
		if (direction.x > 0) {
			bulletPosition = bulletPosition.plus(DIMENSIONS.x/2.0f, 0.0f);
		} else {
			bulletPosition = bulletPosition.plus(-DIMENSIONS.x/2.0f, 0.0f);
		}
		return new Bullet(world, bulletPosition, direction);
	}

	public Grenade tossGrenade() {
		Vec2f direction = new Vec2f(velocity.projectOnto(new Vec2f(1.0f, -1.0f)).x, -1.0f).normalized();
		Vec2f grenadePosition;
		if (direction.x > 0) {
			grenadePosition = position.plus(DIMENSIONS.x+10.0f, -10.0f);
		} else {
			grenadePosition = position.plus(-10.0f, -10.0f);
		}
		
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("mass", GRENADE_MASS);
		properties.put("velocityX", "" + GRENADE_VELOCITY*direction.x);
		properties.put("velocityY", "" + GRENADE_VELOCITY*direction.y);
		properties.put("restitution", GRENADE_RESTITUTION);
		properties.put("groupIndex", GRENADE_GROUP);
		
		return new Grenade(world, grenadePosition, properties);
	}

}
