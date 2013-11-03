package ro7.game.world;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import ro7.engine.sprites.SpriteSheet;
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
import cs195n.Vec2i;

public class MWorld extends GameWorld {

	private Vec2f GRAVITY = new Vec2f(0.0f, 100.0f);

	private Player player;
	
	private Set<Entity> newEntities;
	
	private Set<Bullet> removeShoots;
	
	private boolean won, lost;

	public MWorld(Vec2f dimensions) {
		super(dimensions);
		
		newEntities = new HashSet<Entity>();
		
		removeShoots = new HashSet<Bullet>();
		
		try {
			LevelData level = CS195NLevelReader.readLevel(new File("resources/levels/first_level.nlf"));
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
		classes.put("GroundEnemy", GroundEnemy.class);
		classes.put("AirEnemy", AirEnemy.class);
		classes.put("ReverseButton", ReverseButton.class);
		classes.put("Wall", Wall.class);
		classes.put("Door", Door.class);
		classes.put("Sensor", Sensor.class);
		classes.put("Relay", Relay.class);
	}
	
	@Override
	public void loadSpriteSheets() {
		spriteSheets.put("samus.png", new SpriteSheet("resources/sprites/samus.png", new Vec2i(36, 50), new Vec2i(14, 0)));
		spriteSheets.put("samus_walking_right.png", new SpriteSheet("resources/sprites/samus_walking_right.png", new Vec2i(36, 50), new Vec2i(14, 0)));
		spriteSheets.put("samus_walking_left.png", new SpriteSheet("resources/sprites/samus_walking_left.png", new Vec2i(36, 50), new Vec2i(14, 0)));
		spriteSheets.put("ground_enemy.png", new SpriteSheet("resources/sprites/ground_enemy.png", new Vec2i(80, 100), new Vec2i(15, 0)));
		spriteSheets.put("ground_enemy_right.png", new SpriteSheet("resources/sprites/ground_enemy_right.png", new Vec2i(80, 100), new Vec2i(15, 0)));
		spriteSheets.put("ground_enemy_left.png", new SpriteSheet("resources/sprites/ground_enemy_left.png", new Vec2i(80, 100), new Vec2i(15, 0)));
		spriteSheets.put("air_enemy.png", new SpriteSheet("resources/sprites/air_enemy.png", new Vec2i(50, 50), new Vec2i(0, 0)));
		spriteSheets.put("wall.png", new SpriteSheet("resources/sprites/wall.png", new Vec2i(16, 16), new Vec2i(0, 0)));
		spriteSheets.put("door.png", new SpriteSheet("resources/sprites/door.png", new Vec2i(32, 48), new Vec2i(0, 0)));
		spriteSheets.put("objects.png", new SpriteSheet("resources/sprites/objects.png", new Vec2i(32, 32), new Vec2i(0, 0)));
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
	}

	public void movePlayer(Vec2f direction) {
		player.move(direction);
	}

	public void jumpPlayer() {
		for (PhysicalEntity other : physEntities) {
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

	public void tossGrenade(Vec2f point) {
		Grenade grenade = player.tossGrenade(point);
		entities.put(grenade.toString(), grenade);
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
	
	public Vec2f getPlayerPosition() {
		return player.getPosition();
	}

	public void reverseGravity() {
		GRAVITY = GRAVITY.smult(-1.0f);
	}

}
