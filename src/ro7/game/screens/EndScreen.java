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

public class EndScreen extends Screen {

	private AAB background;
	private Message endMessage;
	private Message subtitle;
	
	private String text;

	public EndScreen(Application app, String text) {
		super(app);
		this.text = text;
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDraw(Graphics2D g) {
		background.draw(g);
		endMessage.draw(g);
		subtitle.draw(g);
	}

	@Override
	public void onKeyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onKeyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == 27) {
			app.popScreen();
			app.pushScreen(new TitleScreen(app));
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
			float titleX = windowSize.x / 3f;
			float titleY = windowSize.y / 3.5f;
			int fontSize = windowSize.x / 12;
			endMessage = new Message(text, fontSize, Color.WHITE, new Vec2f(
					titleX, titleY));

			float subTitleX = windowSize.x / 2.8f;
			float subTitleY = windowSize.y / 2.5f;
			int subFontSize = windowSize.x / 36;
			subtitle = new Message("Press Escape to return", subFontSize,
					Color.WHITE, new Vec2f(subTitleX, subTitleY));

			background = new AAB(new Vec2f(windowSize.x/2, windowSize.y/2), Color.BLACK,
					Color.BLACK, new Vec2f(windowSize.x, windowSize.y));
		} catch (Exception e) {
			System.out.println("No window size defined");
		}
	}

}
