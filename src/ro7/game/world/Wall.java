package ro7.game.world;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;

public class Wall extends MStaticEntity {

	public Wall(GameWorld world, CollidingShape shape, String name, Map<String, String> properties) {
		super(world, shape, name, properties);
	}

}
