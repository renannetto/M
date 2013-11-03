package ro7.game.world;

import cs195n.Vec2f;
import ro7.engine.world.GameWorld;
import ro7.engine.world.RayCollision;
import ro7.engine.world.entities.Ray;

public class GrenadeRay extends Ray {
	
	private static final int CATEGORY_MASK = 2;
	private static final int COLLISION_MASK = 3;

	protected GrenadeRay(GameWorld world, Vec2f position, Vec2f direction) {
		super(world, CATEGORY_MASK, COLLISION_MASK, position, direction);
	}

	@Override
	public void onCollision(RayCollision collision) {
		// TODO Auto-generated method stub

	}

}
