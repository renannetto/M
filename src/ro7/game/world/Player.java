package ro7.game.world;

import java.awt.Color;

import ro7.engine.sprites.shapes.AAB;
import ro7.engine.world.GameWorld;
import ro7.engine.world.PhysicalEntity;
import cs195n.Vec2f;

public class Player extends PhysicalEntity {
	
	private final static float MASS = 5.0f;
	private final static float VELOCITY = 100.0f;
	private final static Color COLOR = Color.BLUE;
	private final static Vec2f DIMENIONS = new Vec2f(50.0f, 50.0f);
	private final float K = 100.0f;
	private final Vec2f JUMP_IMPULSE = new Vec2f(0.0f, -1000.0f);

	public Player(GameWorld world, Vec2f position) {
		super(world, position, MASS, new Vec2f(0.0f, 0.0f), true, new AAB(position, COLOR, COLOR, DIMENIONS));
	}

	@Override
	protected void updateShape() {
		shape = new AAB(position, COLOR, COLOR, DIMENIONS);
	}

	public void move(Vec2f direction) {
		Vec2f goalVelocity = direction.smult(VELOCITY);
		Vec2f force;
		if (direction.x!=0) {
			force = new Vec2f(K*(goalVelocity.x-velocity.x), 0.0f);
		} else {
			force = new Vec2f(0.0f, K*(goalVelocity.y-velocity.y));
		}
		applyForce(force);
	}

	public void jump() {
		applyImpulse(JUMP_IMPULSE);
	}

}
