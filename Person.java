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

public class Person extends Entity {

	public Person(Body body) {
		super(body);
	}

	public float getDesiredAngle() {
		// Body body = this.getBody();
		// Vec2 vel = body.getLinearVelocity();
		// return (float)Math.atan(vel.x/vel.y) + (float)Math.PI;
		return 0.0f;
	}

	public void update(GameContainer container, int delta) {
		super.update(container, delta);

		Body body = this.getBody();

		// adjust angle
		if (body.getAngle() % 2.0f*(float)Math.PI < getDesiredAngle())
			body.applyTorque(10.0f);
		else if (body.getAngle() % 2.0f*(float)Math.PI > getDesiredAngle())
			body.applyTorque(-10.0f);

	}

}