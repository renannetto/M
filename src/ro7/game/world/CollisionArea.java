package ro7.game.world;

import java.awt.Graphics2D;
import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.CollidableEntity;

public class CollisionArea extends CollidableEntity {

	protected CollisionArea(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
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
	
	@Override
	public void draw(Graphics2D g) {

	}

}
