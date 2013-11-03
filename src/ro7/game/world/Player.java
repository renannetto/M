package ro7.game.world;

import java.util.HashMap;
import java.util.Map;

import ro7.engine.sprites.AnimatedSprite;
import ro7.engine.sprites.ImageSprite;
import ro7.engine.sprites.SpriteSheet;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.sprites.shapes.CollidingSprite;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.CollidableEntity;
import ro7.engine.world.io.Input;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class Player extends MDynamicEntity {

	private final String GRENADE_MASS = "1.0f";
	private final float GRENADE_VELOCITY = 500.0f;
	private final String GRENADE_RESTITUTION = "0.5f";
	private final String GRENADE_MASK = "1";
	private final String GRENADE_COLLISION = "2";

	private final int BULLET_MASK = 16;
	private final int BULLET_COLLISION = 2;

	private final static float VELOCITY = 200.0f;
	private final static Vec2f DIMENSIONS = new Vec2f(50.0f, 50.0f);
	private final float K = 300.0f;
	private final Vec2f JUMP_IMPULSE = new Vec2f(0.0f, -8000.0f);

	private final float DEAD_TIME = 0.2f;

	private float deadTime;

	private ImageSprite standing;
	private AnimatedSprite walkingRight;
	private AnimatedSprite walkingLeft;

	public Player(final GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
		inputs.put("doEnterDoor", new Input() {

			@Override
			public void run(Map<String, String> args) {
				((MWorld) world).win();
			}
		});
		inputs.put("doDie", new Input() {
			
			@Override
			public void run(Map<String, String> args) {
				die();
			}
		});

		SpriteSheet standingSheet = world.getSpriteSheet(properties
				.get("standingSheet"));
		Vec2i standingRightPos = new Vec2i(Integer.parseInt(properties
				.get("posStandingX")), Integer.parseInt(properties
				.get("posStandingY")));
		standing = new ImageSprite(shape.getPosition(), standingSheet,
				standingRightPos);

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

		deadTime = -1;
	}

	@Override
	public void update(long nanoseconds) {
		super.update(nanoseconds);

		if (velocity.x > 0.1) {
			((CollidingSprite) shape).updateSprite(walkingRight);
		} else if (velocity.x < -0.1) {
			((CollidingSprite) shape).updateSprite(walkingLeft);
		} else {
			((CollidingSprite) shape).updateSprite(standing);
		}

		if (deadTime >= 0) {
			deadTime += nanoseconds / 1000000000.0f;
			if (deadTime > DEAD_TIME) {
				((MWorld) world).lose();
			}
		}

		this.shape.update(nanoseconds);
	}

	public void move(Vec2f direction) {
		Vec2f goalVelocity = direction.smult(VELOCITY);
		Vec2f force;
		if (direction.x != 0) {
			force = new Vec2f(K * (goalVelocity.x - velocity.x), 0.0f);
		} else {
			force = new Vec2f(0.0f, K * (goalVelocity.y - velocity.y));
		}
		applyForce(force);
	}

	public void jump() {
		applyImpulse(JUMP_IMPULSE);
	}

	public Bullet shoot(Vec2f point) {
		Vec2f position = shape.getPosition();
		Vec2f direction = point.minus(position).normalized();

		Vec2f bulletPosition = shape.center();
		if (direction.x > 0) {
			bulletPosition = bulletPosition.plus(DIMENSIONS.x / 2.0f, 0.0f);
		} else {
			bulletPosition = bulletPosition.plus(-DIMENSIONS.x / 2.0f, 0.0f);
		}
		return new Bullet(world, BULLET_MASK, BULLET_COLLISION, bulletPosition,
				direction);
	}

	public Grenade tossGrenade(Vec2f point) {
		Vec2f position = shape.getPosition();
		Vec2f direction = point.minus(position).normalized();
		Vec2f grenadePosition;
		if (direction.x > 0) {
			grenadePosition = position.plus(DIMENSIONS.x + 10.0f, -10.0f);
		} else {
			grenadePosition = position.plus(-10.0f, -10.0f);
		}

		Map<String, String> properties = new HashMap<String, String>();
		properties.put("mass", GRENADE_MASS);
		properties.put("velocityX", "" + GRENADE_VELOCITY * direction.x);
		properties.put("velocityY", "" + GRENADE_VELOCITY * direction.y);
		properties.put("restitution", GRENADE_RESTITUTION);
		properties.put("categoryMask", GRENADE_MASK);
		properties.put("collisionMask", GRENADE_COLLISION);

		return new Grenade(world, properties, grenadePosition);
	}

	@Override
	public Collision collides(CollidableEntity other) {
		// TODO Auto-generated method stub
		return super.collides(other);
	}

	@Override
	public void shooted(Vec2f impulse) {
		super.shooted(impulse);
		deadTime = 0;
	}

	public Vec2f getPosition() {
		return shape.center();
	}
	
	public void die() {
		deadTime = DEAD_TIME;
	}

}
