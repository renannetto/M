package ro7.engine.sprites.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import cs195n.Vec2f;

public class CompoundShape extends CollidingShape {

	private List<CollidingShape> shapes;

	public CompoundShape(Vec2f position, CollidingShape... shapes) {
		super(position);

		this.shapes = new ArrayList<CollidingShape>();
		for (CollidingShape shape : shapes) {
			this.shapes.add(shape);
		}
	}
	
	public List<CollidingShape> getShapes() {
		return new ArrayList<CollidingShape>(shapes);
	}

	@Override
	public boolean collides(CollidingShape shape) {
		return shape.collidesCompoundShape(this);
	}

	@Override
	public boolean collidesCircle(Circle circle) {
		for (CollidingShape shape : shapes) {
			if (shape.collidesCircle(circle)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean collidesAAB(AAB aab) {
		for (CollidingShape shape : shapes) {
			if (shape.collidesAAB(aab)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean collidesPolygon(Polygon polygon) {
		for (CollidingShape shape : shapes) {
			if (shape.collidesPolygon(polygon)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean collidesCompoundShape(CompoundShape compound) {
		for (CollidingShape shape : shapes) {
			if (shape.collidesCompoundShape(compound)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void draw(Graphics2D g) {
		for (CollidingShape shape : shapes) {
			shape.draw(g);
		}
	}

	@Override
	public void changeBorderColor(Color color) {
		for (CollidingShape shape : shapes) {
			shape.changeBorderColor(color);
		}
	}

	@Override
	public void changeFillColor(Color color) {
		for (CollidingShape shape : shapes) {
			shape.changeFillColor(color);
		}
	}

}
