package ro7.game.world;

import ro7.engine.world.GameWorld;
import ro7.engine.world.Ray;
import ro7.engine.world.RayCollision;
import cs195n.Vec2f;

public class Bullet extends Ray {
	
	private final float SHOOT_IMPULSE = 500;

	public Bullet(GameWorld world, Vec2f position, Vec2f direction) {
		super(world, position, direction);
	}

	@Override
	public void onCollision(RayCollision collision) {
		MEntity other = (MEntity)collision.other;
		other.shooted(direction.smult(SHOOT_IMPULSE));
		((MWorld)world).removeShoot(this);
	}

}
