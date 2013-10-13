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
	public Vec2f collides(CollidingShape shape) {
		return shape.collidesCompoundShape(this);
	}

	@Override
	public Vec2f collidesCircle(Circle circle) {
		for (CollidingShape shape : shapes) {
			Vec2f mtv = shape.collidesCircle(circle);
			if (mtv != null) {
				return mtv;
			}
		}
		return null;
	}

	@Override
	public Vec2f collidesAAB(AAB aab) {
		for (CollidingShape shape : shapes) {
			Vec2f mtv = shape.collidesAAB(aab);
			if (mtv != null) {
				return mtv;
			}
		}
		return null;
	}
	
	@Override
	public Vec2f collidesPolygon(Polygon polygon) {
		for (CollidingShape shape : shapes) {
			Vec2f mtv = shape.collidesPolygon(polygon);
			if (mtv != null) {
				return mtv;
			}
		}
		return null;
	}

	@Override
	public Vec2f collidesCompoundShape(CompoundShape compound) {
		for (CollidingShape shape : shapes) {
			Vec2f mtv = shape.collidesCompoundShape(compound);
			if (mtv != null) {
				return mtv;
			}
		}
		return null;
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

	@Override
	public Range projectTo(SeparatingAxis axis) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vec2f center() {
		// TODO Auto-generated method stub
		return null;
	}

}
