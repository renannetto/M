package ro7.game.world;

import java.awt.Color;

import ro7.engine.sprites.shapes.AAB;
import ro7.engine.world.GameWorld;
import cs195n.Vec2f;

public class Player extends MDynamicEntity {

	private final static float MASS = 5.0f;
	private final static float VELOCITY = 300.0f;
	private final static float RESTITUTION = 0.0f;
	private static final int GROUP = -1;
	private final static Color COLOR = Color.BLUE;
	private final static Vec2f DIMENSIONS = new Vec2f(50.0f, 50.0f);
	private final float K = 100.0f;
	private final Vec2f JUMP_IMPULSE = new Vec2f(0.0f, -1000.0f);

	public Player(GameWorld world, Vec2f position) {
		super(world, position, MASS, new Vec2f(0.0f, 0.0f), RESTITUTION, GROUP, 
				new AAB(position, COLOR, COLOR, DIMENSIONS));
	}

	@Override
	protected void updateShape() {
		shape = new AAB(position, COLOR, COLOR, DIMENSIONS);
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
		return new Grenade(world, grenadePosition, direction);
	}

}
