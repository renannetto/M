package ro7.engine.world;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import cs195n.Vec2f;

public abstract class GameWorld {

	protected Vec2f dimensions;
	protected List<Entity> entities;
	protected List<CollidableEntity> collidables;
	protected List<Ray> rays;

	protected GameWorld(Vec2f dimensions) {
		this.dimensions = dimensions;
		entities = new ArrayList<Entity>();
		collidables = new ArrayList<CollidableEntity>();
		rays = new ArrayList<Ray>();
	}

	/**
	 * Draw the game world inside the viewport
	 * @param g Graphics object used to draw
	 * @param min minimum game coordinate that will appear on the viewport
	 * @param max maximum game coordinate that will appear on the viewport
	 * @param viewport the current viewport
	 */
	public void draw(Graphics2D g, Vec2f min, Vec2f max, Viewport viewport) {
		for (Entity entity : entities) {
			entity.draw(g);
		}
		for (CollidableEntity collidable : collidables) {
			collidable.draw(g);
		}
	}
	
	public void update(long nanoseconds) {
		for (Entity entity : entities) {
			entity.update(nanoseconds);
		}
		for (CollidableEntity collidable : collidables) {
			collidable.update(nanoseconds);
		}
		for (CollidableEntity collidableA : collidables) {
			for (CollidableEntity collidableB : collidables) {
				if (!collidableA.equals(collidableB)) {
					Collision collision = collidableA.collides(collidableB);
					if (collision.validCollision()) {
						collidableA.onCollision(collision);
					}
				}
			}
		}
		for (Ray ray : rays) {
			RayCollision closest = null;
			float minDistance = Float.MAX_VALUE;
			for (CollidableEntity other : collidables) {
				RayCollision collision = other.collidesRay(ray);
				if (collision.validCollision()) {
					Vec2f point = collision.point;
					float distance = ray.dist2(point);
					if (distance < minDistance) {
						minDistance = distance;
						closest = collision;
					}
				}
			}
			if (closest != null) {
				ray.onCollision(closest);
			}
		}
	}

	public Vec2f getDimensions() {
		return dimensions;
	}
	
	public void resize(Vec2f newSize) {
		dimensions = new Vec2f(newSize.x, newSize.y);
	}

}
