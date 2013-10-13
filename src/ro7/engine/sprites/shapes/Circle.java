package ro7.engine.sprites.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	public Vec2f center() {
		return new Vec2f(position.x + radius, position.y + radius);
	}

	public float getRadius() {
		return radius;
	}

	@Override
	public Vec2f collides(CollidingShape shape) {
		return shape.collidesCircle(this);
	}

	@Override
	public Vec2f collidesCircle(Circle circle) {
		float distance = this.center().dist2(circle.center());
		if (distance > (this.radius + circle.radius) * (this.radius + circle.radius)) {
			return null;
		}
		
		Set<SeparatingAxis> axes = new HashSet<SeparatingAxis>();
		axes.add(new SeparatingAxis(this.center().minus(circle.center())));
		
		return mtv(axes, circle);
	}

	@Override
	public Vec2f collidesAAB(AAB aab) {
		Vec2f center = this.center();
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

		if (distance > (radius * radius)) {
			return null;
		}
		
		if (point.equals(center)) {
			return mtv(aab.getAxes(), aab);
		}

		Set<SeparatingAxis> axes = new HashSet<SeparatingAxis>();
		axes.add(new SeparatingAxis(this.center().minus(point)));
		
		return mtv(axes, aab);
	}

	@Override
	public Vec2f collidesPolygon(Polygon polygon) {
		Set<SeparatingAxis> thisAxes = this.getAxes(polygon);
		Set<SeparatingAxis> thatAxes = polygon.getAxes();

		thisAxes.addAll(thatAxes);
		return mtv(thisAxes, polygon);
	}

	@Override
	public Vec2f collidesCompoundShape(CompoundShape compound) {
		List<CollidingShape> shapes = compound.getShapes();
		for (CollidingShape shape : shapes) {
			Vec2f mtv = shape.collidesCircle(this);
			if (mtv != null) {
				return mtv;
			}
		}
		return null;
	}

	public Set<SeparatingAxis> getAxes(Polygon polygon) {
		Set<SeparatingAxis> axes = new HashSet<SeparatingAxis>();

		List<Vec2f> points = polygon.getPoints();
		Vec2f center = this.center();
		Vec2f closest = new Vec2f(Float.MAX_VALUE, Float.MAX_VALUE);
		for (Vec2f point : points) {
			Vec2f distance = point.minus(center);
			if (distance.mag2() < closest.mag2()) {
				closest = distance;
			}
		}

		SeparatingAxis axis = new SeparatingAxis(new Vec2f(closest.y,
				-closest.x));
		axes.add(axis);

		return axes;
	}

	@Override
	public Range projectTo(SeparatingAxis axis) {
		return axis.project(this);
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

}
