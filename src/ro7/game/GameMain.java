package ro7.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import cs195n.CS195NLevelReader;
import cs195n.CS195NLevelReader.InvalidLevelException;
import cs195n.LevelData;
import cs195n.LevelData.EntityData;
import ro7.engine.Application;
import ro7.game.screens.GameScreen;

public class GameMain {
	
	public static void main(String[] args) {
		//Application app = new Application("Tou", false);
		//app.pushScreen(new GameScreen(app));
		//app.startup();
		try {
			LevelData level = CS195NLevelReader.readLevel(new File("levels/first_level.nlf"));
			level.getEntities();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidLevelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
