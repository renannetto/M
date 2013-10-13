package ro7.engine.sprites.shapes;

import java.util.List;

import cs195n.Vec2f;

public class SeparatingAxis {
	
	private Vec2f axis;

	public SeparatingAxis(Vec2f axis) {
		this.axis = axis;
	}
	
	public Range project(Circle circle) {
		Vec2f center = circle.center();
		float radius = circle.getRadius();
		
		float min = center.minus(radius, radius).projectOnto(axis).mag();
		float max = center.plus(radius, radius).projectOnto(axis).mag();
		return new Range(min, max);
	}
	
	public Range project(AAB aab) {
		float min = Float.MAX_VALUE;
		float max = -Float.MAX_VALUE;
		List<Vec2f> points = aab.getPoints();
		for (Vec2f point : points) {
			float projection = point.projectOnto(axis).mag();
			if (projection < min) {
				min = projection;
			}
			if (projection > max) {
				max = projection;
			}
		}
		return new Range(min, max);
	}
	
	public Range project(Polygon polygon) {
		float min = Float.MAX_VALUE;
		float max = -Float.MAX_VALUE;
		List<Vec2f> points = polygon.getPoints();
		for (Vec2f point : points) {
			float projection = point.projectOnto(axis).mag();
			if (projection < min) {
				min = projection;
			}
			if (projection > max) {
				max = projection;
			}
		}
		return new Range(min, max);
	}

	public Vec2f smult(float minMagnitude) {
		return axis.smult(minMagnitude);
	}
	
	

}
