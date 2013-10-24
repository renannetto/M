package ro7.game.screens;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.HashSet;
import java.util.Set;

import ro7.engine.Application;
import ro7.engine.Screen;
import ro7.engine.world.Viewport;
import ro7.game.world.MWorld;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class GameScreen extends Screen {
	
	private final float ZOOM_FACTOR = 1.1f;

	private Viewport viewport;
	private MWorld world;

	private int object;
	private Set<Integer> pressedKeys;

	public GameScreen(Application app) {
		super(app);
		pressedKeys = new HashSet<Integer>();
		object = 1;
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		try {
			world.update(nanosSincePreviousTick);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDraw(Graphics2D g) {
		viewport.draw(g);
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
			object = 1;
			break;
		case 50:
			object = 2;
			break;
		case 65:
			world.movePlayer(new Vec2f(-1.0f, 0.0f));
			break;
		case 71:
			world.tossGrenade();
		case 87:
			if (!pressedKeys.contains(keyCode)) {
				world.jumpPlayer();
			}
			break;
		case 68:
			world.movePlayer(new Vec2f(1.0f, 0.0f));
			break;
		case 82:
			world = new MWorld(new Vec2f(windowSize.x, windowSize.y));
			viewport = new Viewport(new Vec2f(0.0f, 0.0f), new Vec2f(
					windowSize.x, windowSize.y), world, new Vec2f(1.0f, 1.0f),
					new Vec2f(0.0f, 0.0f));
		}
		pressedKeys.add(keyCode);
	}

	@Override
	public void onKeyReleased(KeyEvent e) {
		pressedKeys.remove(e.getKeyCode());
	}

	@Override
	public void onMouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMousePressed(MouseEvent e) {
		int button = e.getButton();
		Point point = e.getPoint();
		Vec2f gamePosition = viewport.screenToGame(new Vec2f(point.x, point.y));
		if (button == 1) {
			world.shoot(gamePosition);
		} else if (button == 3) {
			if (object == 1) {
				world.createLightObject(gamePosition);
			} else {
				world.createHeavyObject(gamePosition);
			}
		}
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
		int rotation = e.getWheelRotation();
		if (rotation < 0) {
			viewport.zoomIn(-rotation * ZOOM_FACTOR);
		} else {
			viewport.zoomOut(rotation * ZOOM_FACTOR);
		}
	}

	@Override
	public void onResize(Vec2i newSize) {
		super.onResize(newSize);

		try {
			if (world == null) {
				world = new MWorld(new Vec2f(windowSize.x, windowSize.y));
			}

			if (viewport != null) {
				Vec2f gamePosition = viewport.getGamePosition();
				Vec2f scale = viewport.getScale();
				viewport = new Viewport(new Vec2f(0.0f, 0.0f), new Vec2f(
						windowSize.x, windowSize.y), world, scale, gamePosition);
			} else {
				viewport = new Viewport(new Vec2f(0.0f, 0.0f), new Vec2f(
						windowSize.x, windowSize.y), world, new Vec2f(1.0f,
						1.0f), new Vec2f(0.0f, 0.0f));
			}
		} catch (NullPointerException e) {
			System.out.println("No window size defined");
		}
	}

}
