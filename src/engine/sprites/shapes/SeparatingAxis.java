package ro7.engine.sprites.shapes;

import java.util.List;

import cs195n.Vec2f;

public class SeparatingAxis {
	
	private Vec2f axis;

	public SeparatingAxis(Vec2f axis) {
		this.axis = axis;
	}
	
	public Range project(Circle circle) {
		Vec2f center = circle.getCenter();
		float radius = circle.getRadius();
		
		Vec2f min = center.minus(radius, radius).projectOnto(axis);
		Vec2f max = center.plus(radius, radius).projectOnto(axis);
		return new Range(min, max);
	}
	
	public Range project(AAB aab) {
		Vec2f min = new Vec2f(Float.MAX_VALUE, Float.MAX_VALUE);
		Vec2f max = new Vec2f(Float.MIN_VALUE, Float.MIN_VALUE);
		List<Vec2f> points = aab.getPoints();
		for (Vec2f point : points) {
			Vec2f projection = point.projectOnto(axis);
			if (projection.mag2() < min.mag2()) {
				min = projection;
			}
			if (projection.mag2() > max.mag2()) {
				max = projection;
			}
		}
		return new Range(min, max);
	}
	
	public Range project(Polygon polygon) {
		Vec2f min = new Vec2f(Float.MAX_VALUE, Float.MAX_VALUE);
		Vec2f max = new Vec2f(Float.MIN_VALUE, Float.MIN_VALUE);
		List<Vec2f> points = polygon.getPoints();
		for (Vec2f point : points) {
			Vec2f projection = point.projectOnto(axis);
			if (projection.mag2() < min.mag2()) {
				min = projection;
			}
			if (projection.mag2() > max.mag2()) {
				max = projection;
			}
		}
		return new Range(min, max);
	}

}
