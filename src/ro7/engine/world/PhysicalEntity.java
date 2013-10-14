package ro7.engine.world;

import ro7.engine.sprites.shapes.CollidingShape;
import cs195n.Vec2f;

public abstract class PhysicalEntity extends CollidableEntity {

	private final float IMPULSE_PROPORTION = 10.0f;

	protected float mass;
	protected Vec2f velocity;
	protected Vec2f impulse;
	protected Vec2f force;
	protected boolean dynamic;

	protected PhysicalEntity(GameWorld world, Vec2f position, float mass,
			Vec2f velocity, boolean dynamic, CollidingShape shape) {
		super(world, position, shape);
		this.mass = mass;
		this.velocity = velocity;
		this.force = new Vec2f(0.0f, 0.0f);
		this.impulse = new Vec2f(0.0f, 0.0f);
		this.dynamic = dynamic;
	}

	protected PhysicalEntity(GameWorld world, Vec2f position, float mass,
			float velocity, boolean dynamic, CollidingShape shape) {
		super(world, position, shape);
		this.mass = mass;
		this.velocity = randomDirection().smult(velocity);
		this.force = new Vec2f(0.0f, 0.0f);
		this.impulse = new Vec2f(0.0f, 0.0f);
		this.dynamic = dynamic;
	}

	@Override
	public void update(long nanoseconds) {
		if (dynamic) {
			float seconds = nanoseconds / 1000000000.0f;
			velocity = velocity.plus(force.sdiv(mass).smult(seconds)).plus(
					impulse.sdiv(mass));
			position = position.plus(velocity.smult(seconds));
			force = new Vec2f(0.0f, 0.0f);
			impulse = new Vec2f(0.0f, 0.0f);
			updateShape();
			insideWorld();
			updateShape();
		}
	}

	public void applyForce(Vec2f force) {
		if (dynamic) {
			this.force = this.force.plus(force);
		}
	}

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
	public void onCollision(Collision collision) {
		assert collision.validCollision();
		Vec2f mtv = collision.mtv;
		Vec2f centerDistance = collision.thisShape.center().minus(
				collision.otherShape.center());
		assert mtv.dot(centerDistance) >= 0;

		if (dynamic) {
			PhysicalEntity other = (PhysicalEntity) collision.other;
			if (other.dynamic) {
				position = position.plus(mtv.sdiv(2.0f));
			} else {
				position = position.plus(mtv);
			}
		}
		applyImpulse(mtv.smult(IMPULSE_PROPORTION));
	}

	protected abstract void updateShape();

	public void insideWorld() {
		Vec2f min = new Vec2f(0.0f, 0.0f);
		Vec2f max = world.getDimensions();

		Vec2f center = shape.center();
		Vec2f translate;
		if (center.x < min.x) {
			translate = new Vec2f(min.x - center.x, 0.0f);
			position = position.plus(translate);
		} else if (center.x > max.x) {
			translate = new Vec2f(max.x - center.x, 0.0f);
			position = position.plus(translate);
		}

		if (center.y < min.y) {
			translate = new Vec2f(0.0f, min.y - center.y);
			position = position.plus(translate);
		} else if (center.y > max.y) {
			translate = new Vec2f(0.0f, max.y - center.y);
			position = position.plus(translate);
		}
	}

}
