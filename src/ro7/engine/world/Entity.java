package ro7.engine.world;

import java.awt.Graphics2D;

import cs195n.Vec2f;

public abstract class Entity {
	
	protected GameWorld world;
	protected Vec2f position;
	
	protected Entity(GameWorld world, Vec2f position) {
		this.world = world;
		this.position = position;
	}
	
	public abstract void update(long nanoseconds);
	
	public abstract void draw(Graphics2D g);	

}
