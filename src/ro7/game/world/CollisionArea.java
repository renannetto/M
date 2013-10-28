package ro7.game.world;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.CollidableEntity;

public class CollisionArea extends CollidableEntity {

	protected CollisionArea(GameWorld world, CollidingShape shape,
			Map<String, String> properties) {
		super(world, shape, properties);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCollision(Collision collision) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(long nanoseconds) {
		// TODO Auto-generated method stub

	}

}
