package ro7.engine.world;

import ro7.engine.sprites.shapes.CollidingShape;
import cs195n.Vec2f;

public abstract class StaticEntity extends PhysicalEntity {

	protected StaticEntity(GameWorld world, Vec2f position, float mass,
			Vec2f velocity, float restitution, CollidingShape shape) {
		super(world, position, mass, velocity, restitution, shape);
	}

	@Override
	public void applyForce(Vec2f force) {
		
	}
	
	@Override
	public void onCollision(Collision collision) {
		assert collision.validCollision();
		Vec2f mtv = collision.mtv;
		Vec2f centerDistance = collision.thisShape.center().minus(
				collision.otherShape.center());
		assert mtv.dot(centerDistance) >= 0;

		PhysicalEntity other = (PhysicalEntity) collision.other;
		other.onCollisionStatic(collision);
	}
	
	@Override
	public void onCollisionDynamic(Collision collision) {
		Vec2f mtv = collision.mtv;
		PhysicalEntity other = (PhysicalEntity) collision.other;
		other.position = other.position.plus(mtv.smult(-1.0f));
		applyImpulse(mtv.smult(IMPULSE_PROPORTION));
		other.applyImpulse(mtv.smult(-1.0f).smult(IMPULSE_PROPORTION));
	}
	
	@Override
	public void onCollisionStatic(Collision collision) {
		Vec2f mtv = collision.mtv;
		PhysicalEntity other = (PhysicalEntity) collision.other;
		applyImpulse(mtv.smult(IMPULSE_PROPORTION));
		other.applyImpulse(mtv.smult(-1.0f).smult(IMPULSE_PROPORTION));
	}

}
