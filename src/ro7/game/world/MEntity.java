package ro7.game.world;

import cs195n.Vec2f;

public interface MEntity {
	
	public void shooted(Vec2f impulse);
	
	public void exploded(Vec2f impulse);

}
