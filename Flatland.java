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

		Vec2[] vecs1 = {new Vec2(1.0f, 1.0f), new Vec2(1.0f, -1.0f), new Vec2(-1.0f, -1.0f), new Vec2(-1.0f, 1.0f), new Vec2(1.0f, 1.0f)};
		Vec2[] vecs2 = {new Vec2(2.0f, 2.0f), new Vec2(2.0f, -2.0f), new Vec2(-2.0f, -2.0f), new Vec2(-2.0f, 2.0f), new Vec2(2.0f, 2.0f)};
		Vec2[] vecs3 = {new Vec2(3.0f, -3.0f), new Vec2(3.0f, 0.0f), new Vec2(-3.0f, 0.0f)};

		PolygonShape polygonShape1 = new PolygonShape();
		polygonShape1.set(vecs1, vecs1.length);

		PolygonShape polygonShape2 = new PolygonShape();
		polygonShape2.set(vecs2, vecs2.length);

		PolygonShape polygonShape3 = new PolygonShape();
		polygonShape3.set(vecs3, vecs3.length);

		BodyDef bodyDef1 = new BodyDef();
		bodyDef1.position = new Vec2(0.0f, 10.0f);
		bodyDef1.fixedRotation = false;
		bodyDef1.angularVelocity = 0f;
		bodyDef1.active = true;
		bodyDef1.angle = 0.0f;
		bodyDef1.linearDamping = 0.0f;
		bodyDef1.type = BodyType.DYNAMIC;

		BodyDef bodyDef2 = new BodyDef();
		bodyDef2.position = new Vec2(0, -10.0f);
		bodyDef2.angularVelocity = 0f;
		bodyDef2.fixedRotation = false;
		bodyDef2.active = true;
		bodyDef2.angle = 0.0f;
		bodyDef2.linearDamping = 0.0f;
		bodyDef2.linearVelocity = new Vec2(0.0f, 10.0f);
		bodyDef2.type = BodyType.DYNAMIC;

		BodyDef bodyDef3 = new BodyDef();
		bodyDef3.position = new Vec2(0, 0);
		bodyDef3.angularVelocity = 0f;
		bodyDef3.fixedRotation = false;
		bodyDef3.active = true;
		bodyDef3.angle = 0.0f;
		bodyDef3.linearDamping = 0.0f;
		bodyDef3.linearVelocity = new Vec2(0.0f, 0.0f);
		bodyDef3.type = BodyType.DYNAMIC;
	

		Body body;
		body = world.createBody(bodyDef1);
		body.createFixture(polygonShape1, 1.0f);
		body = world.createBody(bodyDef2);
		body.createFixture(polygonShape2, 1.0f);
		body = world.createBody(bodyDef3);
		body.createFixture(polygonShape3, 1.0f);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		world.step(1.0f/60.0f, 12, 8);
	}

	public static final float RENDER_SCALAR = 10.0f;
	public static final float RENDER_TRANS_X = 500.0f;
	public static final float RENDER_TRANS_Y = 500.0f;

	public void render(GameContainer container, Graphics g) throws SlickException {
		Body thisBody = world.getBodyList();
		for (int i = 0; i < world.getBodyCount(); i++) {
			Fixture thisFixture = thisBody.getFixtureList();
			while (thisFixture != null) {
				PolygonShape shape = (PolygonShape)thisFixture.getShape();
				Vec2[] jboxPoints = shape.getVertices();
				float[] slickPoints = new float[jboxPoints.length];
				for (int j = 0; j < shape.getVertexCount(); j++) {
					Vec2 point = thisBody.getWorldPoint(jboxPoints[j]);
					slickPoints[2*j] = point.x*RENDER_SCALAR+RENDER_TRANS_X;
					slickPoints[2*j+1] = point.y*RENDER_SCALAR+RENDER_TRANS_Y;
				}
				// if (i == 0) {
					for (int j = 0; j < jboxPoints.length; j++) {
						System.out.print(jboxPoints[j] + ", ");
					}
					System.out.println();
					for (int j = 0; j < slickPoints.length; j++) {
						System.out.print(slickPoints[j] + ", ");
					}
					System.out.println();
					System.out.println();
				// }
				Polygon slickPoly = new Polygon(slickPoints);
				g.draw(slickPoly);
				thisFixture = thisFixture.getNext();
			}
			thisBody = thisBody.getNext();
		}

	}

}