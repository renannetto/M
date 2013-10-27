package ro7.game.world;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;

public class Enemy extends MDynamicEntity {

	public Enemy(GameWorld world, CollidingShape shape, Map<String, String> properties) {
		super(world, shape, properties);
	}

}
