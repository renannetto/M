package ro7.game.world;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.CollidableEntity;
import ro7.engine.world.entities.Entity;
import ro7.engine.world.entities.PhysicalEntity;
import ro7.engine.world.entities.Relay;
import ro7.engine.world.entities.Sensor;
import cs195n.CS195NLevelReader;
import cs195n.CS195NLevelReader.InvalidLevelException;
import cs195n.LevelData;
import cs195n.Vec2f;

public class MWorld extends GameWorld {

	private final Vec2f GRAVITY = new Vec2f(0.0f, 100.0f);

	private Player player;
	
	private Set<Entity> newEntities;
	
	private Set<Bullet> removeShoots;
	private Set<Grenade> removeGrenades;
	
	private boolean won, lost;

	public MWorld(Vec2f dimensions) {
		super(dimensions);
		
		newEntities = new HashSet<Entity>();
		
		removeShoots = new HashSet<Bullet>();
		removeGrenades = new HashSet<Grenade>();
		
		try {
			LevelData level = CS195NLevelReader.readLevel(new File("levels/first_level.nlf"));
			initLevel(level);
			player = (Player)entities.get("player");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidLevelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		won = false;
		lost = false;
	}
	
	@Override
	public void setGameClasses() {
		classes.put("Player", Player.class);
		classes.put("Enemy", Enemy.class);
		classes.put("Wall", Wall.class);
		classes.put("Door", Door.class);
		classes.put("Camera", Camera.class);
		classes.put("Sensor", Sensor.class);
		classes.put("Relay", Relay.class);
	}

	@Override
	public void update(long nanoseconds) {
		super.update(nanoseconds);

		for (PhysicalEntity entity : physEntities) {
			entity.applyGravity(GRAVITY);
		}
		
		for (Entity entity : newEntities) {
			entities.put(entity.toString(), entity);
		}
		newEntities.clear();
		
		for (Bullet bullet : removeShoots) {
			rays.remove(bullet);
		}
		removeShoots.clear();
		
		for (Grenade grenade : removeGrenades) {
			physEntities.remove(grenade);
			collidables.remove(collidables.indexOf(grenade));
		}
		removeGrenades.clear();
	}

	public void movePlayer(Vec2f direction) {
		player.move(direction);
	}

	public void jumpPlayer() {
		for (CollidableEntity other : collidables) {
			if (!other.equals(player)) {
				Collision collision = player.collides(other);
				if (collision.validCollision() && collision.mtv.y < 0) {
					player.jump();
				}
			}
		}
	}
	
	public void shoot(Vec2f point) {
		Bullet bullet = player.shoot(point);
		addShoot(bullet);
	}

	public void removeShoot(Bullet bullet) {
		removeShoots.add(bullet);
	}
	
	public void removeGrenade(Grenade grenade) {
		removeGrenades.add(grenade);
	}

	public void tossGrenade() {
		Grenade grenade = player.tossGrenade();
		physEntities.add(grenade);
		collidables.add(grenade);
	}

	public void win() {
		won = true;
	}
	
	public boolean won() {
		return won;
	}
	
	public void lose() {
		lost = true;
	}
	
	public boolean lost() {
		return lost;
	}

	public Collision collidesPlayer(CollidableEntity collidable) {
		return player.collides(collidable);
	}

	public void addShoot(Bullet shoot) {
		rays.add(shoot);
		newEntities.add(shoot);
	}

}
