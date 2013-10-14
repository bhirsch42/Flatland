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

	private Vec2 desiredPosition;

	public Person(Body body) {
		super(body);
		this.desiredPosition = new Vec2(body.getPosition());
	}

	public float getDesiredAngle() {
		// Body body = this.getBody();
		// Vec2 vel = body.getLinearVelocity();
		// return (float)Math.atan(vel.x/vel.y) + (float)Math.PI;
		return 0.0f;
	}

	public Vec2 getDesiredPosition() {
		return desiredPosition;
	}

	public void update(GameContainer container, int delta) {
		super.update(container, delta);

		Body body = this.getBody();

		// adjust angle
		if (body.getAngle() % 2.0f*(float)Math.PI < getDesiredAngle())
			body.applyTorque(10.0f);
		else if (body.getAngle() % 2.0f*(float)Math.PI > getDesiredAngle())
			body.applyTorque(-10.0f);

		// adjust position
		if (body.getPosition().x < getDesiredPosition().x)
			body.applyForceToCenter(new Vec2(10.0f, 0.0f));
		else if (body.getPosition().x > getDesiredPosition().x)
			body.applyForceToCenter(new Vec2(-10.0f, 0.0f));
		if (body.getPosition().y < getDesiredPosition().y)
			body.applyForceToCenter(new Vec2(0.0f, 10.0f));
		else if (body.getPosition().y > getDesiredPosition().y)
			body.applyForceToCenter(new Vec2(0.0f, -10.0f));


	}

}