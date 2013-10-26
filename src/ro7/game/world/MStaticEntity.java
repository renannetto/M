package ro7.game.world;

import java.util.Map;

import cs195n.Vec2f;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.StaticEntity;

public abstract class MStaticEntity extends StaticEntity implements MEntity {

	protected MStaticEntity(GameWorld world, Vec2f position, CollidingShape shape, Map<String, String> properties) {
		super(world, position, shape, properties);
	}
	
	public void shooted(Vec2f impulse) {
		
	}

}
