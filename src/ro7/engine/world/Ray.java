package ro7.engine.world;

import java.awt.Graphics2D;
import java.util.Set;

import ro7.engine.sprites.shapes.AAB;
import ro7.engine.sprites.shapes.Circle;
import ro7.engine.sprites.shapes.Polygon;
import cs195n.Vec2f;

public abstract class Ray extends Entity {
	
	protected Vec2f direction;

	protected Ray(GameWorld world, Vec2f position, Vec2f direction) {
		super(world, position);
		this.direction = direction.normalized();
	}
	
	@Override
	public void update(long nanoseconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}
	
	public Vec2f collidesCircle(Circle circle) {
		Vec2f center = circle.center();
		float L = center.dot(position) / position.mag();
		Vec2f projectionPoint = position.plus(direction.smult(L));
		float x = projectionPoint.dist(center);
		float r = circle.getRadius();
		float t = 0.0f;
		
		if (circle.inside(position)) {
			if (L < 0) {
				return null;
			}
			t = (float)(L-Math.sqrt(r*r + x*x));
		} else {
			t = (float)(L+Math.sqrt(r*r + x*x));
		}
		Vec2f intersection = position.plus(direction.smult(t));
		return intersection;
	}
	
	public Vec2f collidesPolygon(Polygon polygon) {
		return collidesEdges(polygon.edges());
	}
	
	public Vec2f collidesAAB(AAB aab) {
		return collidesEdges(aab.edges());
	}
	
	public Vec2f collidesEdges(Set<Edge> edges) {
		float minT = Float.MAX_VALUE;
		Vec2f closest = null;
		for (Edge edge : edges) {
			float cross1 = (edge.pointA.minus(position)).cross(direction);
			float cross2 = (edge.pointA.minus(position)).cross(direction);
			if (cross1*cross2 < 0) {
				float t = (edge.pointB.minus(position).dot(edge.n)) / (direction.dot(edge.n));
				if (t < minT) {
					minT = t;
					closest = position.plus(direction.smult(t));
				}
			}
		}
		return closest;
	}

	public float dist2(Vec2f point) {
		return point.dist2(position);
	}
	
	public abstract void onCollision(RayCollision collision);

}
