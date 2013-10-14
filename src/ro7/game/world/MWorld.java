package ro7.game.world;

import java.util.HashSet;
import java.util.Set;

import cs195n.Vec2f;
import ro7.engine.world.GameWorld;
import ro7.engine.world.PhysicalEntity;

public class MWorld extends GameWorld {
	
	private final float WALL_SIZE = 50.0f;
	private final Vec2f GRAVITY = new Vec2f(0.0f, 50.0f);
	
	private Player player;
	
	Set<PhysicalEntity> physEntities;

	public MWorld(Vec2f dimensions) {
		super(dimensions);
		
		physEntities = new HashSet<PhysicalEntity>();
		
		Wall floor = new Wall(this, new Vec2f(0.0f, dimensions.y-WALL_SIZE), new Vec2f(dimensions.x, WALL_SIZE));
		collidables.add(floor);
		physEntities.add(floor);
		Wall leftWall = new Wall(this, new Vec2f(0.0f, WALL_SIZE), new Vec2f(WALL_SIZE, dimensions.y-2*WALL_SIZE));
		collidables.add(leftWall);
		physEntities.add(leftWall);
		Wall rightWall = new Wall(this, new Vec2f(dimensions.x-WALL_SIZE, WALL_SIZE), new Vec2f(WALL_SIZE, dimensions.y-2*WALL_SIZE));
		collidables.add(rightWall);
		physEntities.add(rightWall);
		Wall ceiling = new Wall(this, new Vec2f(-WALL_SIZE, 0.0f), new Vec2f(dimensions.x+WALL_SIZE, WALL_SIZE));
		collidables.add(ceiling);
		physEntities.add(ceiling);
		
		player = new Player(this, new Vec2f(dimensions.x/2.0f, dimensions.y-WALL_SIZE-50.0f));
		collidables.add(player);
		physEntities.add(player);
		
		HeavyObject heavy = new HeavyObject(this, new Vec2f(3*dimensions.x/4.0f, dimensions.y/2.0f));
		collidables.add(heavy);
		physEntities.add(heavy);
		
		LightObject light = new LightObject(this, new Vec2f(dimensions.x/4.0f, dimensions.y/2.0f));
		collidables.add(light);
		physEntities.add(light);
	}
	
	@Override
	public void update(long nanoseconds) {
		super.update(nanoseconds);
		
		for (PhysicalEntity entity : physEntities) {
			entity.applyGravity(GRAVITY);
		}
	}

	public void movePlayer(Vec2f direction) {
		player.move(direction);
	}

	public void jumpPlayer() {
		player.jump();
	}

}
