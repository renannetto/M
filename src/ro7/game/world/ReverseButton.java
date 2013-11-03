package ro7.game.world;

import java.awt.Graphics2D;
import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.Sensor;
import ro7.engine.world.io.Input;
import ro7.engine.world.io.Output;

public class ReverseButton extends Sensor {

	private boolean enabled;

	public ReverseButton(final GameWorld world, CollidingShape shape,
			String name, Map<String, String> properties) {
		super(world, shape, name, properties);

		outputs.put("onPush", new Output());

		inputs.put("doReverse", new Input() {

			@Override
			public void run(Map<String, String> args) {
				((MWorld) world).reverseGravity();
			}
		});
		inputs.put("doEnable", new Input() {

			@Override
			public void run(Map<String, String> args) {
				enabled = true;
			}
		});
		inputs.put("doDisable", new Input() {

			@Override
			public void run(Map<String, String> args) {
				enabled = false;
			}
		});
		enabled = true;
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		shape.draw(g);
	}

	@Override
	public void onCollision(Collision collision) {
		if (enabled) {
			super.onCollision(collision);
			outputs.get("onPush").run();
		}
	}

}
