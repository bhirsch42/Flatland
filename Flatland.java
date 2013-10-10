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

		// Vec2[] vecs1 = {new Vec2(1.0f, 1.0f), new Vec2(1.0f, -1.0f), new Vec2(-1.0f, -1.0f), new Vec2(-1.0f, 1.0f), new Vec2(1.0f, 1.0f), new Vec2(5.0f, 5.0f)};
		// Vec2[] vecs2 = {new Vec2(2.0f, 2.0f), new Vec2(2.0f, -2.0f), new Vec2(-2.0f, -2.0f), new Vec2(-2.0f, 2.0f), new Vec2(2.0f, 2.0f)};
		// Vec2[] vecs3 = {new Vec2(3.0f, -3.0f), new Vec2(3.0f, 0.0f), new Vec2(-3.0f, 0.0f)};

		// PolygonShape polygonShape1 = new PolygonShape();
		// polygonShape1.set(vecs1, vecs1.length);

		// PolygonShape polygonShape2 = new PolygonShape();
		// polygonShape2.set(vecs2, vecs2.length);

		// PolygonShape polygonShape3 = new PolygonShape();
		// polygonShape3.set(vecs3, vecs3.length);
		// polygonShape3.setAsBox(5.0f, 3.0f);

		// BodyDef bodyDef1 = new BodyDef();
		// bodyDef1.position = new Vec2(0.0f, 10.0f);
		// bodyDef1.fixedRotation = false;
		// bodyDef1.angularVelocity = 7.0f;
		// bodyDef1.active = true;
		// bodyDef1.angle = 0.0f;
		// bodyDef1.linearDamping = 1.0f;
		// bodyDef1.type = BodyType.DYNAMIC;

		// BodyDef bodyDef2 = new BodyDef();
		// bodyDef2.position = new Vec2(0, -10.0f);
		// bodyDef2.angularVelocity = 0f;
		// bodyDef2.fixedRotation = false;
		// bodyDef2.active = true;
		// bodyDef2.angle = 0.0f;
		// bodyDef2.linearDamping = 0.0f;
		// bodyDef2.linearVelocity = new Vec2(0.0f, 10.0f);
		// bodyDef2.type = BodyType.DYNAMIC;

		// BodyDef bodyDef3 = new BodyDef();
		// bodyDef3.position = new Vec2(0, 0);
		// bodyDef3.angularVelocity = 0f;
		// bodyDef3.fixedRotation = false;
		// bodyDef3.active = true;
		// bodyDef3.angle = 0.0f;
		// bodyDef3.linearDamping = 0.0f;
		// bodyDef3.linearVelocity = new Vec2(0.0f, 0.0f);
		// bodyDef3.type = BodyType.DYNAMIC;
	

		// Body body;
		// FixtureDef fixtureDef;

		// body = world.createBody(bodyDef1);
		// fixtureDef = new FixtureDef();
		// fixtureDef.restitution = 0.5f;
		// fixtureDef.shape = polygonShape1;
		// fixtureDef.density = 1.0f;
		// body.createFixture(fixtureDef);

		// body = world.createBody(bodyDef2);
		// fixtureDef = new FixtureDef();
		// fixtureDef.restitution = 0.5f;
		// fixtureDef.shape = polygonShape2;
		// fixtureDef.density = 1.0f;
		// body.createFixture(fixtureDef);

		// body = world.createBody(bodyDef3);
		// fixtureDef = new FixtureDef();
		// fixtureDef.restitution = 0.5f;
		// fixtureDef.shape = polygonShape3;
		// fixtureDef.density = 1.0f;
		// body.createFixture(fixtureDef);

		world.setRenderTranslation(500.0f, 500.0f);
		world.setRenderScalar(20.0f);

		world.createHouse(new Vec2(4.0f, 4.0f), 10.0f);
		world.createSquareMan(new Vec2(4.0f, 4.0f), 1.0f);
		world.createPlayer(new Vec2(1.0f, 1.0f), 1.0f);
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