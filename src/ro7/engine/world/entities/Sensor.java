package ro7.engine.world.entities;

import java.awt.Graphics2D;
import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.io.Output;

public class Sensor extends CollidableEntity {
	
	private Output output;

	public Sensor(GameWorld world, CollidingShape shape, Output output, Map<String, String> properties) {
		super(world, shape, properties);
		this.output = output;
	}

	@Override
	public void onCollision(Collision collision) {
		output.run();
	}

	@Override
	public void update(long nanoseconds) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void draw(Graphics2D g) {
		
	}

}
