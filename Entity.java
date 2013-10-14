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

public class Entity {

	private Body body;
	private boolean runningEvent = false;

	public Entity(Body body) {
		this.body = body;
	}

	public Body getBody() {
		return body;
	}

	public boolean isRunningEvent() {
		return runningEvent;
	}

	public void setRunningEvent(boolean r) {
		runningEvent = r;
	}

	public void update(GameContainer container, int delta) {

	}

	public void render(GameContainer container, Graphics g) {

	}

}