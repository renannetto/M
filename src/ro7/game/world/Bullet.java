package ro7.game.world;

import ro7.engine.world.GameWorld;
import ro7.engine.world.RayCollision;
import ro7.engine.world.entities.Ray;
import cs195n.Vec2f;

public class Bullet extends Ray {
	
	private final float SHOOT_IMPULSE = 500;
	private static final int GROUP = -1;

	public Bullet(GameWorld world, Vec2f position, Vec2f direction) {
		super(world, GROUP, position, direction);
	}

	@Override
	public void onCollision(RayCollision collision) {
		MEntity other = (MEntity)collision.other;
		other.shooted(direction.smult(SHOOT_IMPULSE));
		((MWorld)world).removeShoot(this);
	}

}
