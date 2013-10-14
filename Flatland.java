import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;

import org.jbox2d.callbacks.*;
import org.jbox2d.collision.*;
import org.jbox2d.collision.shapes.*;
import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;
import org.jbox2d.pooling.*;
import org.jbox2d.profile.*;

import java.awt.Toolkit;
import java.awt.Dimension;
import java.util.*;

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

	public FlatlandWorld world;

	@Override
	public void init(GameContainer container) throws SlickException {
		world = new FlatlandWorld(new Vec2(0.0f, 0.0f));
		world.setRenderTranslation(500.0f, 500.0f);
		
		// for (int i = 0; i < 3000; i++) {
		// 	world.createRaindrop(new Vec2((float)Math.random()*100.0f-50.0f, (float)Math.random()*-5000.0f));
		// }

		world.createHouse(new Vec2(4.0f, 4.0f), 10.0f, 2.0f*(float)Math.PI*(-1.5f/5.0f));
		String[] dialogue = {};
		world.createSquareDialogueMan(new Vec2(-40.0f, 4.0f), 1.0f, dialogue);
		Body focus = world.createPlayer(new Vec2(1.0f, 1.0f), 1.0f);
		world.setFocus(focus);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		world.update(container, delta);
	}

	public void render(GameContainer container, Graphics g) throws SlickException {
		g.setBackground(Color.white);
		g.setColor(Color.black);
		g.setLineWidth(2.0f);
		world.render(container, g);
	}

}