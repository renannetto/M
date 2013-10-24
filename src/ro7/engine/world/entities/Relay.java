package ro7.engine.world.entities;

import java.awt.Graphics2D;

import ro7.engine.world.GameWorld;
import ro7.engine.world.io.Output;
import cs195n.Vec2f;

public class Relay extends Entity {
	
	private boolean enable;
	private Output output;

	protected Relay(GameWorld world, Vec2f position, Output output) {
		super(world, position);
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
