package ro7.engine.world.entities;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import cs195n.Vec2f;

public abstract class StaticEntity extends PhysicalEntity {

	protected StaticEntity(GameWorld world, Vec2f position, CollidingShape shape, Map<String, String> properties) {
		super(world, position, shape, properties);
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
		other.onCollisionStatic(new Collision(this, mtv.smult(-1.0f), other.shape, this.shape));
	}
	
	@Override
	public void onCollisionDynamic(Collision collision) {
		Vec2f mtv = collision.mtv;
		if (mtv.mag2() == 0) {
			return;
		}
		PhysicalEntity other = (PhysicalEntity) collision.other;
		other.position = other.position.plus(mtv.smult(-1.0f));
		
		mtv = mtv.normalized();
		
		float cor = this.cor(other);
		float ua = this.velocity.dot(mtv);
		float ub = other.velocity.dot(mtv);
		float mb = other.mass;
		
		float impulse = mb*(1+cor)*(ub-ua);
		
		applyImpulse(mtv.smult(impulse/2.0f));
		other.applyImpulse(mtv.smult(-impulse/2.0f));
	}
	
	@Override
	public void onCollisionStatic(Collision collision) {
		
	}

}
