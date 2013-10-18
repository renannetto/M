package ro7.engine.world;

import java.awt.Graphics2D;

import ro7.engine.sprites.shapes.CollidingShape;
import cs195n.Vec2f;

public abstract class CollidableEntity extends Entity {

	protected CollidingShape shape;
	
	protected CollidableEntity(GameWorld world, Vec2f position, CollidingShape shape) {
		super(world, position);
		this.shape = shape;
	}
	
	public Collision collides(CollidableEntity other) {
		Vec2f mtv = this.shape.collides(other.shape);
		return new Collision(other, mtv, this.shape, other.shape);
	}
	
	public abstract void onCollision(Collision collision);
	
	@Override
	public void draw(Graphics2D g) {
		shape.draw(g);
	}

}
