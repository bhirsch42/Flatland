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

public class Raindrop extends Entity {

	private Vec2 horizForce;

	public Raindrop(Body body) {
		super(body);
		horizForce = new Vec2(-0.1f+(float)Math.random()*0.2f, 0.0f);
	}

	public void update(GameContainer container, int delta) {

		Body body = this.getBody();
		body.applyForceToCenter(new Vec2(0.0f, 1.0f));
		body.applyForceToCenter(horizForce);
		if (body.getAngle() % 2.0f*(float)Math.PI/4.0f < 0.0f)
			body.applyTorque(0.01f);
		else if (body.getAngle() % 2.0f*(float)Math.PI/4.0f > 0.0f)
			body.applyTorque(-0.01f);

	}

	public void render(GameContainer container, Graphics g) {
		g.setColor(Color.blue);
	}

}