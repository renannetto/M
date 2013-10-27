package ro7.engine.world.entities;

import java.awt.Graphics2D;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.engine.world.io.Output;

public class Relay extends Entity {
	
	private boolean enable;
	private Output output;

	protected Relay(GameWorld world, CollidingShape shape, Output output) {
		super(world, shape);
		enable = false;
		this.output = output;
	}
	
	public void enable() {
		enable = true;
	}
	
	public void disable() {
		enable = false;
	}
	
	public void runOutput() {
		if (enable) {
			output.run();
		}
	}

	@Override
	public void update(long nanoseconds) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub

	}

}
