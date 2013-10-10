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

	public FlatlandWorld(Vec2 gravity) {
		super(gravity);
	}

	public void createHouse(Vec2 position, float radius) {
		createHouse(position, radius, 0.0f);
	}

	public void createHouse(Vec2 position, float radius, float angle) {
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


	}

}