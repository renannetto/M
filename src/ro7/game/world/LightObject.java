package ro7.game.world;

import java.awt.Color;

import ro7.engine.sprites.shapes.Circle;
import ro7.engine.world.GameWorld;
import cs195n.Vec2f;

public class LightObject extends MDynamicEntity {
	
	private final static float MASS = 2.0f;
	private final static float RESTITUTION = 1.0f;
	private final static Color COLOR = Color.GREEN;
	private final static float RADIUS = 10.0f;

	public LightObject(GameWorld world, Vec2f position) {
		super(world, position, MASS, new Vec2f(0.0f, 0.0f), RESTITUTION, new Circle(position, COLOR, COLOR, RADIUS));
	}

	@Override
	protected void updateShape() {
		shape = new Circle(position, COLOR, COLOR, RADIUS);
	}

}
