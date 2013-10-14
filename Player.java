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

public class Player extends Person {

	private final float speed = 100.0f;
	private final float force = 50.0f;
	private boolean canMove = true;

	public Player(Body body) {
		super(body);
	}

	public Vec2 getDesiredPosition() {
		return this.getBody().getPosition();
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	public void update(GameContainer container, int delta) {
		// get user input
		Input input = container.getInput();
		Body body = this.getBody();
		Vec2 linVel = body.getLinearVelocity();
		if (canMove) {
			if (input.isKeyDown(Input.KEY_S)) {
				if (linVel.y < speed) {
					body.applyForceToCenter(new Vec2(0.0f, force));
					if (linVel.y > speed)
						linVel.y = speed;
				}
			}
			if (input.isKeyDown(Input.KEY_A)) {
				if (linVel.x > -speed) {
					body.applyForceToCenter(new Vec2(-force, 0.0f));
					if (linVel.x < -speed)
						linVel.x = -speed;
				}
			}
			if (input.isKeyDown(Input.KEY_W)) {
				if (linVel.y > -speed) {
					body.applyForceToCenter(new Vec2(0.0f, -force));
					if (linVel.y < -speed)
						linVel.y = -speed;
				}
			}
			if (input.isKeyDown(Input.KEY_D)) {
				if (linVel.x < speed) {
					body.applyForceToCenter(new Vec2(force, 0.0f));
					if (linVel.x > speed) {
						linVel.x = speed;
					}
				}
			}
		}

		super.update(container, delta);
	}

}