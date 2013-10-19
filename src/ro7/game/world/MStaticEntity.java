package ro7.game.world;

import cs195n.Vec2f;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.engine.world.StaticEntity;

public abstract class MStaticEntity extends StaticEntity implements MEntity {

	protected MStaticEntity(GameWorld world, Vec2f position, float mass,
			Vec2f velocity, float restitution, CollidingShape shape) {
		super(world, position, mass, velocity, restitution, shape);
	}
	
	public void shooted(Vec2f impulse) {
		
	}

}
