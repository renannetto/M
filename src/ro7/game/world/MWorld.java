package ro7.game.world;

import java.util.HashSet;
import java.util.Set;

import ro7.engine.world.CollidableEntity;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.PhysicalEntity;
import cs195n.Vec2f;

public class MWorld extends GameWorld {

	private final float WALL_SIZE = 50.0f;
	private final Vec2f GRAVITY = new Vec2f(0.0f, 100.0f);

	private Player player;

	Set<PhysicalEntity> physEntities;

	Wall floor;
	
	Set<Bullet> removeShoots;

	public MWorld(Vec2f dimensions) {
		super(dimensions);

		physEntities = new HashSet<PhysicalEntity>();

		floor = new Wall(this, new Vec2f(0.0f, dimensions.y - WALL_SIZE),
				new Vec2f(dimensions.x, WALL_SIZE));
		collidables.add(floor);
		physEntities.add(floor);
		Wall leftWall = new Wall(this, new Vec2f(0.0f, WALL_SIZE), new Vec2f(
				WALL_SIZE, dimensions.y - 2 * WALL_SIZE));
		collidables.add(leftWall);
		physEntities.add(leftWall);
		Wall rightWall = new Wall(this, new Vec2f(dimensions.x - WALL_SIZE,
				WALL_SIZE), new Vec2f(WALL_SIZE, dimensions.y - 2 * WALL_SIZE));
		collidables.add(rightWall);
		physEntities.add(rightWall);
		Wall ceiling = new Wall(this, new Vec2f(-WALL_SIZE, 0.0f), new Vec2f(
				dimensions.x + WALL_SIZE, WALL_SIZE));
		collidables.add(ceiling);
		physEntities.add(ceiling);

		player = new Player(this, new Vec2f(dimensions.x / 2.0f, dimensions.y
				- WALL_SIZE - 50.0f));
		collidables.add(player);
		physEntities.add(player);

		HeavyObject heavy = new HeavyObject(this, new Vec2f(
				3 * dimensions.x / 4.0f, dimensions.y / 2.0f));
		collidables.add(heavy);
		physEntities.add(heavy);

		LightObject light = new LightObject(this, new Vec2f(
				dimensions.x / 4.0f, dimensions.y / 2.0f));
		collidables.add(light);
		physEntities.add(light);
		
		removeShoots = new HashSet<Bullet>();
	}

	@Override
	public void update(long nanoseconds) {
		super.update(nanoseconds);

		for (PhysicalEntity entity : physEntities) {
			entity.applyGravity(GRAVITY);
		}
		for (Bullet bullet : removeShoots) {
			rays.remove(bullet);
		}
	}

	public void movePlayer(Vec2f direction) {
		player.move(direction);
	}

	public void jumpPlayer() {
		Collision collision = player.collides(floor);
		if (collision.validCollision()) {
			player.jump();
		}
	}

	public void createLightObject(Vec2f position) {
		if (collidables.size() < 15) {
			LightObject object = new LightObject(this, position);
			if (!collides(object)) {
				collidables.add(object);
				physEntities.add(object);
			}
		}
	}

	public void createHeavyObject(Vec2f position) {
		if (collidables.size() < 15) {
			HeavyObject object = new HeavyObject(this, position);
			if (!collides(object)) {
				collidables.add(object);
				physEntities.add(object);
			}
		}
	}

	private boolean collides(CollidableEntity collidable) {
		for (CollidableEntity entity : collidables) {
			Collision collision = entity
					.collides(collidable);
			if (collision.validCollision()) {
				return true;
			}
		}
		return false;
	}

	public void shoot() {
		Bullet bullet = player.shoot();
		rays.add(bullet);
	}

	public void removeShoot(Bullet bullet) {
		removeShoots.add(bullet);
	}

}
