package ro7.game.world;

import java.awt.Color;

import ro7.engine.sprites.shapes.Circle;
import ro7.engine.world.GameWorld;
import ro7.engine.world.PhysicalEntity;
import cs195n.Vec2f;

public class LightObject extends PhysicalEntity {
	
	private final static float MASS = 1.0f;
	private final static Color COLOR = Color.BLUE;
	private final static float RADIUS = 10.0f;

	public LightObject(GameWorld world, Vec2f position) {
		super(world, position, MASS, new Vec2f(0.0f, 0.0f), true, new Circle(position, COLOR, COLOR, RADIUS));
	}

	@Override
	protected void updateShape() {
		shape = new Circle(position, COLOR, COLOR, RADIUS);
	}

}
