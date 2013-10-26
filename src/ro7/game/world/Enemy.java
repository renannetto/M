package ro7.game.world;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import cs195n.Vec2f;

public class Enemy extends MDynamicEntity {

	public Enemy(GameWorld world, Vec2f position, CollidingShape shape, Map<String, String> properties) {
		super(world, position, shape, properties);
	}

}
