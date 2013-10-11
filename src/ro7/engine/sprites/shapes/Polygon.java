package ro7.engine.sprites.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import cs195n.Vec2f;

public class Polygon extends SingleShape {
	
	private final List<Vec2f> points;

	public Polygon(Vec2f position, Color fillColor, Vec2f... points) {
		super(position, fillColor, fillColor);
		
		this.points = new ArrayList<Vec2f>();
		for (Vec2f point : points) {
			this.points.add(point);
		}
	}

	@Override
	public boolean collides(CollidingShape shape) {
		return shape.collidesPolygon(this);
	}

	@Override
	public boolean collidesCircle(Circle circle) {
		if (!checkAxis(circle) || !circle.checkAxis(this)) {
			return false;
		}
		return true;
	}
	
	public boolean checkAxis(Circle circle) {
		for (int i=0; i<points.size(); i++) {
			Vec2f startPoint = points.get(i);
			Vec2f endPoint;
			if (i<points.size()-1) {
				endPoint = points.get(i+1);
			} else {
				endPoint = points.get(0);
			}
			Vec2f edgeVector = endPoint.minus(startPoint);
			SeparatingAxis axis = new SeparatingAxis(new Vec2f(edgeVector.y, -edgeVector.x));
			Range range1 = axis.project(this);
			Range range2 = axis.project(circle);
			if (!range1.overlaps(range2)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean collidesAAB(AAB aab) {
		if (!checkAxis(aab) || !aab.checkAxis(this)) {
			return false;
		}
		return true;
	}
	
	public boolean checkAxis(AAB aab) {
		for (int i=0; i<points.size(); i++) {
			Vec2f startPoint = points.get(i);
			Vec2f endPoint;
			if (i<points.size()-1) {
				endPoint = points.get(i+1);
			} else {
				endPoint = points.get(0);
			}
			Vec2f edgeVector = endPoint.minus(startPoint);
			SeparatingAxis axis = new SeparatingAxis(new Vec2f(edgeVector.y, -edgeVector.x));
			Range range1 = axis.project(this);
			Range range2 = axis.project(aab);
			if (!range1.overlaps(range2)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean collidesPolygon(Polygon polygon) {
		if (!checkAxis(polygon) || !polygon.checkAxis(this)) {
			return false;
		}
		return true;
	}

	public boolean checkAxis(Polygon polygon) {
		for (int i=0; i<points.size(); i++) {
			Vec2f startPoint = points.get(i);
			Vec2f endPoint;
			if (i<points.size()-1) {
				endPoint = points.get(i+1);
			} else {
				endPoint = points.get(0);
			}
			Vec2f edgeVector = endPoint.minus(startPoint);
			SeparatingAxis axis = new SeparatingAxis(new Vec2f(edgeVector.y, -edgeVector.x));
			Range range1 = axis.project(this);
			Range range2 = axis.project(polygon);
			if (!range1.overlaps(range2)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean collidesCompoundShape(CompoundShape compound) {
		List<CollidingShape> shapes = compound.getShapes();
		for (CollidingShape shape : shapes) {
			if (shape.collidesPolygon(this)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(fillColor);
		Path2D path = new Path2D.Float();
		path.moveTo(position.x, position.y);
		for (Vec2f point : points) {
			path.lineTo(point.x, point.y);
		}
		g.fill(path);
	}

	public List<Vec2f> getPoints() {
		return points;
	}

}
