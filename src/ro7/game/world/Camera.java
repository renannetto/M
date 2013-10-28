package ro7.game.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Map;

import cs195n.Vec2f;
import ro7.engine.sprites.shapes.AAB;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.Relay;
import ro7.engine.world.io.Input;

public class Camera extends Relay {

	private final float DETECTION_RANGE = 10.0f;
	private final int GROUP_INDEX = -3;

	private CollisionArea collisionArea;
	private Vec2f direction;

	public Camera(final GameWorld world, CollidingShape shape,
			Map<String, String> properties) {
		super(world, shape, properties);
		Vec2f position = shape.getPosition();
		collisionArea = new CollisionArea(world, new AAB(position, null, null,
				new Vec2f(Float.MAX_VALUE, DETECTION_RANGE)), properties);
		direction = new Vec2f(Float.parseFloat(properties.get("directionX")), Float.parseFloat(properties.get("directionY")));
		
		inputs.put("doShoot", new Input() {
			
			@Override
			public void run(Map<String, String> args) {
				((MWorld)world).addShoot(shoot());
			}
		});
		inputs.put("doDisable", new Input() {
			
			@Override
			public void run(Map<String, String> args) {
				disable();
			}
		});
		enable();
	}
	
	@Override
	public void disable() {
		super.disable();
		shape.changeFillColor(Color.RED);
	}

	@Override
	public void draw(Graphics2D g) {
		shape.draw(g);
	}

	@Override
	public void update(long nanoseconds) {
		Collision collision = ((MWorld) world).collidesPlayer(collisionArea);
		if (collision.validCollision()) {
			runOutput();
		}
	}
	
	public Bullet shoot() {
		Vec2f position = shape.center();
		return new Bullet(world, GROUP_INDEX, position, direction);
	}

}
