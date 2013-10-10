package ro7.engine.sprites.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import cs195n.Vec2f;

public class AAB extends SingleShape {

	private Vec2f dimensions;

	public AAB(Vec2f position, Color borderColor, Color fillColor,
			Vec2f dimensions) {
		super(position.minus(dimensions.sdiv(2.0f)), borderColor, fillColor);
		this.dimensions = dimensions;
	}

	@Override
	public boolean collides(CollidingShape shape) {
		return shape.collidesAAB(this);
	}

	@Override
	public boolean collidesCircle(Circle circle) {
		Vec2f center = circle.getCenter();
		Vec2f minAAB = this.getPosition();
		Vec2f maxAAB = minAAB.plus(this.getDimensions());

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

		float radius = circle.getRadius();

		return distance <= (radius * radius);
	}

	@Override
	public boolean collidesAAB(AAB aab) {
		Vec2f minThis = this.position;
		Vec2f maxThis = minThis.plus(this.dimensions);
		Vec2f minAAB = aab.position;
		Vec2f maxAAB = minAAB.plus(aab.dimensions);

		return minThis.x <= maxAAB.x && maxThis.x >= minAAB.x
				&& minThis.y <= maxAAB.y && maxThis.y >= minAAB.y;
	}
	
	@Override
	public boolean collidesPolygon(Polygon polygon) {
		if (!checkAxis(polygon) || !polygon.checkAxis(this)) {
			return false;
		}
		return true;
	}
	
	public boolean checkAxis(Polygon polygon) {
		Vec2f edgeY = new Vec2f(0.0f, -dimensions.y);
		SeparatingAxis axis = new SeparatingAxis(new Vec2f(edgeY.y, -edgeY.x));
		Range range1 = axis.project(this);
		Range range2 = axis.project(polygon);
		if (!range1.overlaps(range2)) {
			return false;
		}
		
		Vec2f edgeX = new Vec2f(-dimensions.x, 0.0f);
		axis = new SeparatingAxis(new Vec2f(edgeX.y, -edgeX.x));
		range1 = axis.project(this);
		range2 = axis.project(polygon);
		if (!range1.overlaps(range2)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean collidesCompoundShape(CompoundShape compound) {
		List<CollidingShape> shapes = compound.getShapes();
		for (CollidingShape shape : shapes) {
			if (shape.collidesAAB(this)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void draw(Graphics2D g) {
		Rectangle2D rectangle = new Rectangle2D.Float(position.x, position.y,
				dimensions.x, dimensions.y);

		g.setColor(borderColor);
		g.draw(rectangle);

		if (fillColor != null) {
			g.setColor(fillColor);
			g.fill(rectangle);
		}
	}

	public Vec2f getDimensions() {
		return dimensions;
	}

	public Shape getShape() {
		return new Rectangle2D.Float(position.x, position.y, dimensions.x,
				dimensions.y);
	}

	public List<Vec2f> getPoints() {
		List<Vec2f> points = new ArrayList<Vec2f>();
		points.add(position);
		points.add(position.plus(0.0f, dimensions.y));
		points.add(position.plus(dimensions.x, dimensions.y));
		points.add(position.plus(dimensions.x, 0.0f));
		return points;
	}

}
