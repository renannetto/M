package ro7.engine.world.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Set;

import ro7.engine.sprites.Line;
import ro7.engine.sprites.shapes.AAB;
import ro7.engine.sprites.shapes.Circle;
import ro7.engine.sprites.shapes.Polygon;
import ro7.engine.world.Edge;
import ro7.engine.world.GameWorld;
import ro7.engine.world.RayCollision;
import cs195n.Vec2f;

public abstract class Ray extends GroupEntity {

	private final Color COLOR = Color.RED;
	private final float TIME_LIMIT = 0.1f;

	protected Vec2f position;
	protected Vec2f direction;
	protected Line sprite;
	protected float elapsedTime;

	protected Ray(GameWorld world, int categoryMask, int collisionMask, Vec2f position,
			Vec2f direction) {
		super(world, categoryMask, collisionMask);
		this.position = position;
		this.direction = direction.normalized();
		elapsedTime = 0;
	}

	@Override
	public void update(long nanoseconds) {
		elapsedTime += nanoseconds / 1000000000.0f;
		if (elapsedTime > TIME_LIMIT) {
			world.removeRay(this);
		}
	}

	public void updateShape(Vec2f intersection) {
		sprite = new Line(position, intersection, COLOR);
	}

	@Override
	public void draw(Graphics2D g) {
		sprite.draw(g);
	}

	public Vec2f collidesCircle(Circle circle) {
		Vec2f center = circle.center();

		Vec2f projection = center.projectOnto(direction);
		float L = projection.minus(position).dot(direction);
		Vec2f projectionPoint = position.plus(direction.smult(L));
		float x = projectionPoint.dist(center);
		float r = circle.getRadius();
		float t = 0.0f;

		if (!circle.inside(position)) {
			if (L < 0 || !circle.inside(projectionPoint)) {
				return null;
			}
			t = (float) (L - Math.sqrt(r * r + x * x));
		} else {
			t = (float) (L + Math.sqrt(r * r + x * x));
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
			float cross2 = (edge.pointB.minus(position)).cross(direction);
			if (cross1 * cross2 < 0) {
				float t = (edge.pointB.minus(position).dot(edge.n))
						/ (direction.dot(edge.n));
				if (t >= 0 && t < minT) {
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
	
	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
