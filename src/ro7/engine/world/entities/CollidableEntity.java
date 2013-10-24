package ro7.engine.world.entities;

import java.awt.Graphics2D;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.RayCollision;
import cs195n.Vec2f;

public abstract class CollidableEntity extends GroupEntity {

	protected CollidingShape shape;
	
	protected CollidableEntity(GameWorld world, Vec2f position, int groupIndex, CollidingShape shape) {
		super(world, position, groupIndex);
		this.shape = shape;
	}
	
	public Collision collides(CollidableEntity other) {
		Vec2f mtv = this.shape.collides(other.shape);
		return new Collision(other, mtv, this.shape, other.shape);
	}
	
	public RayCollision collidesRay(Ray ray) {
		Vec2f point = this.shape.collidesRay(ray);
		return new RayCollision(this, point, this.shape);
	}
	
	public abstract void onCollision(Collision collision);
	
	@Override
	public void draw(Graphics2D g) {
		shape.draw(g);
	}

}
