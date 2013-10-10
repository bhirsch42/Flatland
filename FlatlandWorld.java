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

public class FlatlandWorld extends SlickWorld {

	private static final Vec2[] HOUSE_VECTORS;
	static {
		float c1 = (float)((1.0/4.0)*(Math.sqrt(5.0)-1.0));
		float c2 = (float)((1.0/4.0)*(Math.sqrt(5.0)+1.0));
		float s1 = (float)((1.0/4.0)*Math.sqrt(10.0+2.0*Math.sqrt(5.0)));
		float s2 = (float)((1.0/4.0)*Math.sqrt(10.0-2.0*Math.sqrt(5.0)));
		Vec2[] vecs = {new Vec2(0.0f, 1.0f), new Vec2(s1, c1), new Vec2(s2, -c2), new Vec2(-s2, -c2), new Vec2(-s1, c1)};
		HOUSE_VECTORS = vecs;
	}

	private ArrayList<Entity> entities = new ArrayList<Entity>();

	public FlatlandWorld(Vec2 gravity) {
		super(gravity);
	}

	public void setFocus(Entity entity) {
		Body body = entity.getBody();
		this.setFocus(body);
	}

	public Body createPlayer(Vec2 position, float sideLength) {
		// !!! most code copied from createSquareMan, should reconcile if possible !!!
		PolygonShape polygonShape = new PolygonShape();
		// polygonShape.setAsBox(sideLength, sideLength);

		polygonShape.set(HOUSE_VECTORS, HOUSE_VECTORS.length);

		BodyDef bodyDef = new BodyDef();
		bodyDef.position = position;
		bodyDef.active = true;
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.linearDamping = 1.0f;
		bodyDef.angularDamping = 4.0f;

		Body body = this.createBody(bodyDef);
		body.createFixture(polygonShape, 1.0f);
		Player player = new Player(body);
		body.setUserData(player);
		entities.add(player);
		return body;

	}

	public Body createSquareMan(Vec2 position, float sideLength) {
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(sideLength, sideLength);

		BodyDef bodyDef = new BodyDef();
		bodyDef.position = position;
		bodyDef.active = true;
		bodyDef.type = BodyType.DYNAMIC;

		Body body = this.createBody(bodyDef);
		body.createFixture(polygonShape, 1.0f);
		Person person = new Person(body);
		body.setUserData(person);
		entities.add(person);
		return body;
	}

	public Body createHouse(Vec2 position, float radius) {
		return createHouse(position, radius, 0.0f);
	}

	public Body createHouse(Vec2 position, float radius, float angle) {
		Vec2[] vecs = new Vec2[HOUSE_VECTORS.length];
		for (int i = 0; i < vecs.length; i++) {
			vecs[i] = HOUSE_VECTORS[i].mul(radius);
		}

		ChainShape chainShape = new ChainShape();
		chainShape.createChain(vecs, vecs.length);

		BodyDef bodyDef = new BodyDef();
		bodyDef.position = position;
		bodyDef.active = true;
		bodyDef.angle = angle;
		bodyDef.type = BodyType.STATIC;

		Body body = this.createBody(bodyDef);
		body.createFixture(chainShape, 1.0f);
		Entity entity = new Entity(body);
		body.setUserData(entity);
		entities.add(entity);
		return body;
	}

	public void update(GameContainer container, int delta) {
		super.update(container, delta);
		for (Entity entity : entities) {
			entity.update(container, delta);
		}
	}

}