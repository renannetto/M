package ro7.engine.world.entities;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import cs195n.Vec2f;

public abstract class DynamicEntity extends PhysicalEntity {

	protected DynamicEntity(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
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
		if (mtv.dot(centerDistance) < 0) {
			mtv = mtv.smult(-1.0f);
		}

		try {
			PhysicalEntity other = (PhysicalEntity) collision.other;
			other.onCollisionDynamic(new Collision(this, mtv.smult(-1.0f),
					other.shape, this.shape));
		} catch (Exception e) {

		}
	}

	@Override
	public void onCollisionDynamic(Collision collision) {
		Vec2f mtv = collision.mtv;
		if (mtv.mag2() == 0) {
			return;
		}

		PhysicalEntity other = (PhysicalEntity) collision.other;
		shape.move(mtv.sdiv(2.0f));
		other.shape.move(mtv.smult(-1.0f).sdiv(2.0f));

		mtv = mtv.normalized();

		float cor = this.cor(other);
		float ua = this.velocity.dot(mtv);
		float ub = other.velocity.dot(mtv);
		float ma = this.mass;
		float mb = other.mass;

		float impulse = ((ma * mb * (1 + cor)) / (ma + mb)) * (ub - ua);

		applyImpulse(mtv.smult(impulse).sdiv(2.0f));
		other.applyImpulse(mtv.smult(-impulse).sdiv(2.0f));
	}

	@Override
	public void onCollisionStatic(Collision collision) {
		Vec2f mtv = collision.mtv;
		if (mtv.mag2() == 0) {
			return;
		}

		PhysicalEntity other = (PhysicalEntity) collision.other;
		shape.move(mtv);

		mtv = mtv.normalized();

		float cor = this.cor(other);
		float ua = this.velocity.dot(mtv);
		float ub = other.velocity.dot(mtv);
		float ma = this.mass;

		float impulse = ma * (1 + cor) * (ub - ua);

		applyImpulse(mtv.smult(impulse).sdiv(2.0f));
	}

}
