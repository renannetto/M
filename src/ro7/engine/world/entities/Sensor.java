package ro7.engine.world.entities;

import java.awt.Graphics2D;

import cs195n.Vec2f;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.io.Output;

public class Sensor extends CollidableEntity {
	
	private Output output;

	public Sensor(GameWorld world, Vec2f position, int groupIndex,
			CollidingShape shape, Output output) {
		super(world, position, groupIndex, shape);
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
