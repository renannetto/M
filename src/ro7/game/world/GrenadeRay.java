package ro7.game.world;

import cs195n.Vec2f;
import ro7.engine.world.GameWorld;
import ro7.engine.world.RayCollision;
import ro7.engine.world.entities.Ray;

public class GrenadeRay extends Ray {
	
	private static final int GROUP = -2;

	protected GrenadeRay(GameWorld world, Vec2f position, Vec2f direction) {
		super(world, position, GROUP, direction);
	}

	@Override
	public void onCollision(RayCollision collision) {
		// TODO Auto-generated method stub

	}

}
