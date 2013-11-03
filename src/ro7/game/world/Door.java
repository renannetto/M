package ro7.game.world;

import java.awt.Graphics2D;
import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.Entity;

public class Door extends Entity {

	public Door(GameWorld world, CollidingShape shape, String name, Map<String, String> properties) {
		super(world, shape, name);
	}

	@Override
	public void update(long nanoseconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		shape.draw(g);
	}
	
	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
