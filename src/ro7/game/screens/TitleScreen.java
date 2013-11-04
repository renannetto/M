package ro7.game.screens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import cs195n.Vec2f;
import cs195n.Vec2i;
import ro7.engine.Application;
import ro7.engine.Screen;
import ro7.engine.sprites.Message;
import ro7.engine.sprites.shapes.AAB;

public class TitleScreen extends Screen {

	private AAB background;
	private Message title;
	private Message subtitle;

	public TitleScreen(Application app) {
		super(app);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDraw(Graphics2D g) {
		background.draw(g);
		title.draw(g);
		subtitle.draw(g);
	}

	@Override
	public void onKeyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onKeyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == 10) {
			try {
				app.pushScreen(new GameScreen(app));
			} catch (Exception exception) {
				System.out.println("Failure to open level");
			}
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
		// TODO Auto-generated method stub

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

	@Override
	public void onResize(Vec2i newSize) {
		super.onResize(newSize);
		try {
			float titleX = windowSize.x / 2.5f;
			float titleY = windowSize.y / 3.5f;
			int fontSize = windowSize.x / 16;
			title = new Message("M!", fontSize, Color.WHITE, new Vec2f(titleX,
					titleY));

			float subTitleX = windowSize.x / 2.8f;
			float subTitleY = windowSize.y / 2.5f;
			int subFontSize = windowSize.x / 36;
			subtitle = new Message("Press Enter to start", subFontSize,
					Color.WHITE, new Vec2f(subTitleX, subTitleY));

			background = new AAB(new Vec2f(windowSize.x / 2, windowSize.y / 2),
					Color.BLACK, Color.BLACK, new Vec2f(windowSize.x,
							windowSize.y));
		} catch (Exception e) {
			System.out.println("No window size defined");
		}
	}

}
