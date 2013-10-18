package ro7.engine.world;

import ro7.engine.sprites.shapes.CollidingShape;
import cs195n.Vec2f;

public abstract class DynamicEntity extends PhysicalEntity {

	protected DynamicEntity(GameWorld world, Vec2f position, float mass,
			Vec2f velocity, float restitution, CollidingShape shape) {
		super(world, position, mass, velocity, restitution, shape);
	}

	@Override
	public void applyForce(Vec2f force) {
		this.force = this.force.plus(force);
	}
	
	@Override
	public void onCollision(Collision collision) {
		assert collision.validCollision();
		Vec2f mtv = collision.mtv;
		Vec2f centerDistance = collision.thisShape.center().minus(
				collision.otherShape.center());
		assert mtv.dot(centerDistance) >= 0;

		PhysicalEntity other = (PhysicalEntity) collision.other;
		other.onCollisionDynamic(collision);
	}
	
	@Override
	public void onCollisionDynamic(Collision collision) {
		Vec2f mtv = collision.mtv;
		PhysicalEntity other = (PhysicalEntity) collision.other;
		position = position.plus(mtv.sdiv(2.0f));
		other.position = other.position.plus(mtv.smult(-1.0f)
				.sdiv(2.0f));
		applyImpulse(mtv.smult(IMPULSE_PROPORTION));
		other.applyImpulse(mtv.smult(-1.0f).smult(IMPULSE_PROPORTION));
	}
	
	@Override
	public void onCollisionStatic(Collision collision) {
		Vec2f mtv = collision.mtv;
		PhysicalEntity other = (PhysicalEntity) collision.other;
		position = position.plus(mtv);
		applyImpulse(mtv.smult(IMPULSE_PROPORTION));
		other.applyImpulse(mtv.smult(-1.0f).smult(IMPULSE_PROPORTION));
	}

}
