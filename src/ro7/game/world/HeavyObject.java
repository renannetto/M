package ro7.game.world;

import java.awt.Color;

import ro7.engine.sprites.shapes.Polygon;
import ro7.engine.world.GameWorld;
import ro7.engine.world.PhysicalEntity;
import cs195n.Vec2f;

public class HeavyObject extends PhysicalEntity {

	private final static float MASS = 20.0f;
	private final static Color COLOR = Color.BLUE;
	private final static float POLYGON_SIZE = 50.0f;

	public HeavyObject(GameWorld world, Vec2f position) {
		super(world, position, MASS, new Vec2f(0.0f, 0.0f), true, new Polygon(
				position, COLOR, position, position.plus(-POLYGON_SIZE,
						POLYGON_SIZE),
				position.plus(POLYGON_SIZE, POLYGON_SIZE)));
	}

	@Override
	protected void updateShape() {
		shape = new Polygon(position, COLOR, position, position.plus(
				-POLYGON_SIZE, POLYGON_SIZE), position.plus(POLYGON_SIZE,
				POLYGON_SIZE));
	}

}
