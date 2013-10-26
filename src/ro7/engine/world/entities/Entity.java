package ro7.engine.world.entities;

import java.awt.Graphics2D;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import cs195n.Vec2f;

public abstract class Entity {
	
	protected GameWorld world;
	protected Vec2f position;
	protected CollidingShape shape;
	
	protected Entity(GameWorld world, Vec2f position, CollidingShape shape) {
		this.world = world;
		this.position = position;
		this.shape = shape;
	}
	
	public abstract void update(long nanoseconds);
	
	public abstract void draw(Graphics2D g);	

}
