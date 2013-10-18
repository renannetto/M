package ro7.game.world;

import java.awt.Color;

import ro7.engine.sprites.shapes.AAB;
import ro7.engine.world.GameWorld;
import ro7.engine.world.StaticEntity;
import cs195n.Vec2f;

public class Wall extends StaticEntity {
	
	private static final float MASS = Float.MAX_VALUE;
	private static final float RESTITUTION = 0.0f;
	private static final Color COLOR = Color.BLACK;
	
	private Vec2f dimensions;

	public Wall(GameWorld world, Vec2f position, Vec2f dimensions) {
		super(world, position, MASS, new Vec2f(0.0f, 0.0f), RESTITUTION, new AAB(position, COLOR, COLOR, dimensions));
		this.dimensions = dimensions;
	}

	@Override
	protected void updateShape() {
		shape = new AAB(position, COLOR, COLOR, dimensions);
	}

}
