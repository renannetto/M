package ro7.game.world;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.DynamicEntity;
import cs195n.Vec2f;

public abstract class MDynamicEntity extends DynamicEntity implements MEntity {
	
	protected MDynamicEntity(GameWorld world, Vec2f position, CollidingShape shape, Map<String, String> properties) {
		super(world, position, shape, properties);
	}

	public void shooted(Vec2f impulse) {
		applyImpulse(impulse);
	}

}
