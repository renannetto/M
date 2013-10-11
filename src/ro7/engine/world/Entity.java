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
	
	public void insideWorld() {
		Vec2f min = new Vec2f(0.0f, 0.0f);
		Vec2f max = world.getDimensions();
		
		if (position.x < min.x) {
			position = new Vec2f(min.x, position.y);
		} else if (position.x > max.x) {
			position = new Vec2f(max.x, position.y);
		}
		
		if (position.y < min.y) {
			position = new Vec2f(position.x, min.y);
		} else if (position.y > max.y) {
			position = new Vec2f(position.x, max.y);
		}
	}

}
