import org.newdawn.slick.*;

import org.jbox2d.callbacks.*;
import org.jbox2d.collision.*;
import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;
import org.jbox2d.pooling.*;
import org.jbox2d.profile.*;

import java.awt.Toolkit;
import java.awt.Dimension;

public class Flatland extends BasicGame {

	public Flatland() {
		super("Flatland");
	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new Flatland());
			app.setShowFPS(true);
			// set fullscreen of native resolution
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int width = (int)screenSize.getWidth();
			int height = (int)screenSize.getHeight();
			app.setDisplayMode(width, height, true);
			// app.setDisplayMode(1366, 768, false);
			app.setTargetFrameRate(120);
			app.start();
		}
		catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public World world;

	@Override
	public void init(GameContainer container) throws SlickException {
		world = new World(new Vec2(0.0f, 0.0f));
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {

	}

	public void render(GameContainer container, Graphics g) throws SlickException {

	}

}