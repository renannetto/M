package ro7.engine.sprites.shapes;

import java.awt.Color;

import ro7.engine.sprites.Sprite;
import cs195n.Vec2f;

public abstract class CollidingShape extends Sprite {

	protected CollidingShape(Vec2f position) {
		super(position);
	}
	
	public abstract boolean collides(CollidingShape shape);
	
	public abstract boolean collidesCircle(Circle circle);
	
	public abstract boolean collidesAAB(AAB aab);
	
	public abstract boolean collidesPolygon(Polygon polygon);
	
	public abstract boolean collidesCompoundShape(CompoundShape compound);
	
	public Vec2f getPosition() {
		return position;
	}
	
	public abstract void changeBorderColor(Color color);
	
	public abstract void changeFillColor(Color color);

}
