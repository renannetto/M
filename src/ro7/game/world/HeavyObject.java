package ro7.game.world;

import java.awt.Color;

import ro7.engine.sprites.shapes.Polygon;
import ro7.engine.world.DynamicEntity;
import ro7.engine.world.GameWorld;
import cs195n.Vec2f;

public class HeavyObject extends DynamicEntity {

	private final static float MASS = 10.0f;
	private final static float RESTITUTION = 0.5f;
	private final static Color COLOR = Color.RED;
	private final static float POLYGON_SIZE = 50.0f;

	public HeavyObject(GameWorld world, Vec2f position) {
		super(world, position, MASS, new Vec2f(0.0f, 0.0f), RESTITUTION, new Polygon(position, COLOR, position,
				new Vec2f(position.x - POLYGON_SIZE/2.0f, position.y - POLYGON_SIZE),
				new Vec2f(position.x - POLYGON_SIZE, position.y)));
	}

	@Override
	protected void updateShape() {
		shape = new Polygon(position, COLOR, position,
				new Vec2f(position.x - POLYGON_SIZE/2.0f, position.y - POLYGON_SIZE),
				new Vec2f(position.x - POLYGON_SIZE, position.y));
	}

}
