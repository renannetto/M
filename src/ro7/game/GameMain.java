package ro7.game;

import ro7.engine.Application;
import ro7.game.screens.CollisionDebugScreen;

public class GameMain {
	
	public static void main(String[] args) {
		Application app = new Application("Tou", false);
		app.pushScreen(new CollisionDebugScreen(app));
		app.startup();		
	}

}
