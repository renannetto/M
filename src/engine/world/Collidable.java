package ro7.engine.world;

import ro7.engine.sprites.shapes.CollidingShape;

public interface Collidable {
	
	public boolean collides(Collidable other);
	
	public CollidingShape getShape();

}
