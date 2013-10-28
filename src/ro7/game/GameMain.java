package ro7.game;

import cs195n.Vec2i;
import ro7.engine.Application;
import ro7.game.screens.TitleScreen;

public class GameMain {
	
	public static void main(String[] args) {
		Application app = new Application("Tou", false, new Vec2i(640, 480));
		app.pushScreen(new TitleScreen(app));
		app.startup();
	}

}
