package ro7.engine.world;

import cs195n.Vec2f;

public abstract class PhysicsEntity extends Entity {

	protected float mass;
	protected Vec2f velocity;
	protected Vec2f impulse;
	protected Vec2f force;
	protected boolean moving;

	protected PhysicsEntity(GameWorld world, Vec2f position, Vec2f velocity, boolean moving) {
		super(world, position);
		this.velocity = velocity;
		this.force = new Vec2f(0.0f, 0.0f);
		this.impulse = new Vec2f(0.0f, 0.0f);
		this.moving = moving;
	}

	protected PhysicsEntity(GameWorld world, Vec2f position, float velocity,
			boolean moving) {
		super(world, position);
		this.velocity = randomDirection().smult(velocity);
		this.force = new Vec2f(0.0f, 0.0f);
		this.impulse = new Vec2f(0.0f, 0.0f);
		this.moving = moving;
	}

	@Override
	public void update(long nanoseconds) {
		if (moving) {
			float seconds = nanoseconds / 1000000000.0f;
			velocity = force.sdiv(mass).smult(seconds).plus(impulse.sdiv(mass));
			position = position.plus(velocity.smult(seconds));
			force = new Vec2f(0.0f, 0.0f);
			impulse = new Vec2f(0.0f, 0.0f);
			insideWorld();
		}
	}
	
	public void applyForce(Vec2f force) {
		this.force = this.force.plus(force);
	}
	
	public void applyImpulse(Vec2f impulse) {
		this.impulse = this.impulse.plus(impulse);
	}

	protected Vec2f randomDirection() {
		float dirX = -1 + (float) (Math.random() * 2);
		float dirY = (float) Math.sqrt(1 - (dirX * dirX));
		int up = (int) (Math.random() * 2);
		if (up == 0) {
			dirY = -dirY;
		}
		return new Vec2f(dirX, dirY);
	}

}
