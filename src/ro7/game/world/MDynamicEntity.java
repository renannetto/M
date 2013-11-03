package ro7.game.world;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.DynamicEntity;
import cs195n.Vec2f;

public abstract class MDynamicEntity extends DynamicEntity implements MEntity {
	
	protected MDynamicEntity(GameWorld world, CollidingShape shape, String name, Map<String, String> properties) {
		super(world, shape, name, properties);
	}

	@Override
	public void shooted(Vec2f impulse) {
		applyImpulse(impulse);
	}
	
	@Override
	public void exploded(Vec2f impulse) {
		applyImpulse(impulse);
	}

}
