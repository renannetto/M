package ro7.game.world;

import java.util.Map;

import cs195n.Vec2f;
import cs195n.Vec2i;
import ro7.engine.sprites.AnimatedSprite;
import ro7.engine.sprites.SpriteSheet;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.sprites.shapes.CollidingSprite;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.io.Output;

public class GroundEnemy extends MDynamicEntity {
	
	private final int WALKING_TIME = 3;
	
	private AnimatedSprite walkingRight;
	private AnimatedSprite walkingLeft;
	
	private float walkingTime;

	public GroundEnemy(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
		
		Vec2i walkingPos = new Vec2i(Integer.parseInt(properties
				.get("posWalkingX")), Integer.parseInt(properties
				.get("posWalkingY")));
		int framesWalking = Integer.parseInt(properties.get("framesWalking"));
		int timeToMoveWalking = Integer.parseInt(properties
				.get("timeToMoveWalking"));
		SpriteSheet walkingRightSheet = world.getSpriteSheet(properties
				.get("walkingRightSheet"));
		walkingRight = new AnimatedSprite(shape.getPosition(),
				walkingRightSheet, walkingPos, framesWalking, timeToMoveWalking);
		SpriteSheet walkingLeftSheet = world.getSpriteSheet(properties
				.get("walkingLeftSheet"));
		walkingLeft = new AnimatedSprite(shape.getPosition(), walkingLeftSheet,
				walkingPos, framesWalking, timeToMoveWalking);
		
		walkingTime = 0.0f;
		
		outputs.put("onPlayerCollision", new Output());
	}
	
	@Override
	public void update(long nanoseconds) {
		super.update(nanoseconds);
		
		if (velocity.x > 0.1) {
			((CollidingSprite) shape).updateSprite(walkingRight);
		} else {
			((CollidingSprite) shape).updateSprite(walkingLeft);
		}
		
		walkingTime += nanoseconds/1000000000.0f;
		if (walkingTime > WALKING_TIME) {
			velocity = new Vec2f(-velocity.x, velocity.y);
			walkingTime = 0;
		}
		
		this.shape.update(nanoseconds);
	}
	
	@Override
	public void exploded(Vec2f impulse) {
		super.exploded(impulse);
		world.removeEntity(name);
	}
	
	@Override
	public void onCollision(Collision collision) {
		super.onCollision(collision);
		if (((MWorld)world).collidesPlayer(this).validCollision()) {
			outputs.get("onPlayerCollision").run();
		}
	}

}
