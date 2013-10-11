package ro7.engine.sprites.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.List;

import cs195n.Vec2f;

/**
 * @author ro7 Sprite that represents a colored circle
 */
public class Circle extends SingleShape {

	private float radius;

	public Circle(Vec2f position, Color borderColor, Color fillColor,
			float radius) {
		super(position.minus(radius, radius), borderColor, fillColor);
		this.radius = radius;
	}

	@Override
	public void draw(Graphics2D g) {
		Ellipse2D circle = new Ellipse2D.Float(position.x, position.y,
				2.0f * radius, 2.0f * radius);

		g.setColor(borderColor);
		g.draw(circle);

		if (fillColor != null) {
			g.setColor(fillColor);
			g.fill(circle);
		}
	}

	public Vec2f getCenter() {
		return new Vec2f(position.x + radius, position.y + radius);
	}

	public float getRadius() {
		return radius;
	}

	@Override
	public boolean collides(CollidingShape shape) {
		return shape.collidesCircle(this);
	}

	@Override
	public boolean collidesCircle(Circle circle) {
		float distance = this.getCenter().dist2(circle.getCenter());
		return distance <= (this.radius + circle.radius)
				* (this.radius + circle.radius);
	}

	@Override
	public boolean collidesAAB(AAB aab) {
		Vec2f center = this.getCenter();
		Vec2f minAAB = aab.getPosition();
		Vec2f maxAAB = minAAB.plus(aab.getDimensions());

		float pointx;
		float pointy;
		if (center.x < minAAB.x) {
			pointx = minAAB.x;
		} else if (center.x > maxAAB.x) {
			pointx = maxAAB.x;
		} else {
			pointx = center.x;
		}

		if (center.y < minAAB.y) {
			pointy = minAAB.y;
		} else if (center.y > maxAAB.y) {
			pointy = maxAAB.y;
		} else {
			pointy = center.y;
		}

		Vec2f point = new Vec2f(pointx, pointy);
		float distance = point.dist2(center);

		return distance <= (this.radius * this.radius);
	}
	
	@Override
	public boolean collidesPolygon(Polygon polygon) {
		if (!checkAxis(polygon) || !polygon.checkAxis(this)) {
			return false;
		}
		return true;
	}
	
	public boolean checkAxis(Polygon polygon) {
		List<Vec2f> points = polygon.getPoints();
		Vec2f center = this.getCenter();
		Vec2f closest = new Vec2f(Float.MAX_VALUE, Float.MAX_VALUE);
		for (Vec2f point : points) {
			Vec2f distance = point.minus(center);
			if (distance.mag2() < closest.mag2()) {
				closest = distance;
			}
		}
		
		SeparatingAxis axis = new SeparatingAxis(new Vec2f(closest.y, -closest.x));
		Range range1 = axis.project(this);
		Range range2 = axis.project(polygon);
		if (!range1.overlaps(range2)) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean collidesCompoundShape(CompoundShape compound) {
		List<CollidingShape> shapes = compound.getShapes();
		for (CollidingShape shape : shapes) {
			if (shape.collidesCircle(this)) {
				return true;
			}
		}
		return false;
	}

}
