package ro7.game.world;

import java.util.Map;

import cs195n.Vec2f;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.engine.world.io.Input;
import ro7.engine.world.io.Output;

public class Box extends MDynamicEntity {

	public Box(final GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
		
		outputs.put("onExplosion", new Output());
		
		inputs.put("doBreak", new Input() {
			
			@Override
			public void run(Map<String, String> args) {
				breakBox();
			}
		});
	}
	
	public void breakBox() {
		world.removeEntity(this.name);
	}
	
	@Override
	public void exploded(Vec2f impulse) {
		super.exploded(impulse);
		outputs.get("onExplosion").run();
	}

}
