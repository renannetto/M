package ro7.game.world;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.DynamicEntity;
import ro7.engine.world.GameWorld;
import cs195n.Vec2f;

public abstract class MDynamicEntity extends DynamicEntity implements MEntity {
	
	protected MDynamicEntity(GameWorld world, Vec2f position, float mass,
			Vec2f velocity, float restitution, CollidingShape shape) {
		super(world, position, mass, velocity, restitution, shape);
	}

	public void shooted(Vec2f impulse) {
		applyImpulse(impulse);
	}

}
