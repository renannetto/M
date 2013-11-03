package ro7.engine.world.entities;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import cs195n.Vec2f;

public abstract class PhysicalEntity extends CollidableEntity {

	protected float mass;
	protected Vec2f velocity;
	protected Vec2f impulse;
	protected Vec2f force;
	protected float restitution;

	protected PhysicalEntity(GameWorld world, CollidingShape shape, String name, Map<String, String> properties) {
		super(world, shape, name, properties);
		this.mass = Float.parseFloat(properties.get("mass"));
		this.velocity = new Vec2f(Float.parseFloat(properties.get("velocityX")), Float.parseFloat(properties.get("velocityY")));
		this.force = new Vec2f(0.0f, 0.0f);
		this.impulse = new Vec2f(0.0f, 0.0f);
		this.restitution = Float.parseFloat(properties.get("restitution"));
		
		world.addPhysicalEntity(this);
	}

	@Override
	public void update(long nanoseconds) {
		float seconds = nanoseconds / 1000000000.0f;
		velocity = velocity.plus(force.sdiv(mass).smult(seconds)).plus(
				impulse.sdiv(mass));
		Vec2f translation = velocity.smult(seconds);
		shape.move(translation);
		force = new Vec2f(0.0f, 0.0f);
		impulse = new Vec2f(0.0f, 0.0f);
	}

	public abstract void applyForce(Vec2f force);

	public void applyGravity(Vec2f gravity) {
		applyForce(gravity.smult(mass));
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

	@Override
	public abstract void onCollision(Collision collision);
	
	public abstract void onCollisionDynamic(Collision collision);
	
	public abstract void onCollisionStatic(Collision collision);

	public float cor(PhysicalEntity other) {
		return (float) Math.sqrt(restitution * other.restitution);
	}
	
	@Override
	public void remove() {
		super.remove();
		world.removePhysicalEntity(this);
	}

}
