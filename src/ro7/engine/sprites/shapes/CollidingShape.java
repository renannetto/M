package ro7.engine.sprites.shapes;

import java.awt.Color;
import java.util.Set;

import ro7.engine.sprites.Sprite;
import cs195n.Vec2f;

public abstract class CollidingShape extends Sprite {

	protected CollidingShape(Vec2f position) {
		super(position);
	}
	
	public abstract Vec2f collides(CollidingShape shape);
	
	public abstract Vec2f collidesCircle(Circle circle);
	
	public abstract Vec2f collidesAAB(AAB aab);
	
	public abstract Vec2f collidesPolygon(Polygon polygon);
	
	public abstract Vec2f collidesCompoundShape(CompoundShape compound);
	
	public Vec2f getPosition() {
		return position;
	}
	
	public abstract void changeBorderColor(Color color);
	
	public abstract void changeFillColor(Color color);
	
	public abstract Range projectTo(SeparatingAxis axis);
	
	public Vec2f mtv(Set<SeparatingAxis> axes, CollidingShape shape) {
		float minMagnitude = Float.MAX_VALUE;
		Vec2f mtv = null;
		for (SeparatingAxis axis : axes) {
			Range range1 = this.projectTo(axis);
			Range range2 = shape.projectTo(axis);
			if (!range1.overlaps(range2)) {
				return null;
			} else {
				float mtv1d = range1.mtv(range2);
				if (Math.abs(mtv1d) < minMagnitude) {
					minMagnitude = Math.abs(mtv1d);
					mtv = axis.smult(mtv1d);
				}
			}
		}
		return mtv;
	}

	public abstract Vec2f center();

}
