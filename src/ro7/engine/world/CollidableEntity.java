package ro7.engine.world;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import cs195n.Vec2f;
import ro7.engine.sprites.shapes.CollidingShape;

public abstract class CollidableEntity extends Entity {

	protected CollidingShape shape;
	
	protected CollidableEntity(GameWorld world, Vec2f position, CollidingShape shape) {
		super(world, position);
		this.shape = shape;
	}
	
	public Map<CollidableEntity, Collision> collides(CollidableEntity other) {
		Map<CollidableEntity, Collision> collisions = new HashMap<CollidableEntity, Collision>();
		Vec2f mtv = this.shape.collides(other.shape);
		if (mtv == null) {
			collisions.put(this, new Collision(other, mtv, this.shape, other.shape));
			collisions.put(other, new Collision(this, mtv, other.shape, this.shape));
		} else {
			collisions.put(this, new Collision(other, mtv, this.shape, other.shape));
			collisions.put(other, new Collision(this, mtv.smult(-1.0f), other.shape, this.shape));
		}
		return collisions;
	}
	
	public abstract void onCollision(Collision collision);
	
	@Override
	public void draw(Graphics2D g) {
		shape.draw(g);
	}

}
