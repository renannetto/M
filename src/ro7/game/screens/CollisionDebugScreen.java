package ro7.game.screens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

import cs195n.Vec2f;
import ro7.engine.Application;
import ro7.engine.Screen;
import ro7.engine.sprites.shapes.AAB;
import ro7.engine.sprites.shapes.Circle;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.sprites.shapes.CompoundShape;
import ro7.engine.sprites.shapes.Polygon;

public class CollisionDebugScreen extends Screen {

	private List<CollidingShape> shapes = new ArrayList<CollidingShape>();
	private int shape1 = 0;
	private int shape2 = 0;

	public CollisionDebugScreen(Application app) {
		super(app);
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		for (CollidingShape shapeA : shapes) {
			shapeA.changeFillColor(Color.GREEN);
			for (CollidingShape shapeB : shapes) {
				if (!shapeA.equals(shapeB)) {
					Vec2f mtv = shapeA.collides(shapeB);
					if (mtv != null) {
						shapeA.changeFillColor(Color.RED);
						shapeB.changeFillColor(Color.RED);
					}
				}
			}
		}
	}

	@Override
	public void onDraw(Graphics2D g) {
		for (CollidingShape shape : shapes) {
			shape.draw(g);
		}
	}

	@Override
	public void onKeyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onKeyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case 49:
			shape1 = (shape1 + 1) % 4;
			break;
		case 50:
			shape2 = (shape2 + 1) % 4;
			break;
		}
	}

	@Override
	public void onKeyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMousePressed(MouseEvent e) {
		int button = e.getButton();
		Point point = e.getPoint();

		CollidingShape shape;
		if (button == 1) {
			shape = createShape(point, shape1);
		} else {
			shape = createShape(point, shape2);
		}
		shapes.add(shape);
	}

	private CollidingShape createShape(Point point, int shapeCode) {
		CollidingShape shape = null;
		Vec2f pointVector = new Vec2f(point.x, point.y);
		switch (shapeCode) {
		case 0:
			shape = new AAB(new Vec2f(point.x, point.y), Color.GREEN,
					Color.GREEN, new Vec2f(50.0f, 25.0f));
			break;
		case 1:
			shape = new Circle(new Vec2f(point.x, point.y), Color.GREEN,
					Color.GREEN, 15.0f);
			break;
		case 2:
			shape = new CompoundShape(pointVector, new Circle(pointVector,
					Color.GREEN, Color.GREEN, 15.0f), new AAB(new Vec2f(
					point.x, point.y + 15.0f), Color.GREEN, Color.GREEN,
					new Vec2f(25.0f, 50.0f)));
			break;
		case 3:
			shape = new Polygon(pointVector, Color.GREEN, pointVector,
					new Vec2f(pointVector.x - 20.0f, pointVector.y - 20.0f),
					new Vec2f(pointVector.x - 40.0f, pointVector.y));
			break;
		}
		return shape;
	}

	@Override
	public void onMouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub

	}

}
