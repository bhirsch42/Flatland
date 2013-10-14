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

public class DialoguePerson extends Person {

	private static final float RANGE = 5.0f;

	private String[] dialogue;
	private boolean eventCompleted = false;

	public DialoguePerson(Body body, String[] dialogue) {
		super(body);
		this.dialogue = dialogue;
	}

	private int step = 0;

	public void update(GameContainer container, int delta) {
		super.update(container, delta);
		Body body = this.getBody();
		FlatlandWorld world = (FlatlandWorld)body.getWorld();
		Player player = world.getPlayer();

		if (shouldStartEvent(player) && !eventCompleted) {
			this.setRunningEvent(true);
		}
		if (this.isRunningEvent()) {
			player.setCanMove(false);
			panToCenterOfDialogue(world, player);
			zoomIn(world);
			if (step > 5000) {
				player.setCanMove(true);
				panBackToPlayer(world, player);
				zoomOut(world);
				this.setRunningEvent(false);
				eventCompleted = true;
			}
			step += delta;
		}
	}

	private void panToCenterOfDialogue(FlatlandWorld world, Player player) {
		world.setSmoothPan(0.99f);
		Vec2 pVec = player.getBody().getPosition();
		Vec2 tVec = this.getBody().getPosition();
		Vec2 aveVec = new Vec2((pVec.x+tVec.x)/2.0f, (pVec.y+tVec.y)/2.0f);
		world.setFocus(aveVec);
	}

	private void zoomIn(FlatlandWorld world) {
		world.setRenderScalar(40.0f);
	}

	private void zoomOut(FlatlandWorld world) {
		world.setRenderScalar();
	}

	private void panBackToPlayer(FlatlandWorld world, Player player) {
		world.setSmoothPan();
		world.setFocus(player.getBody());
	}

	private boolean shouldStartEvent(Player player) {
		return isInRange(player);
	}

	private boolean isInRange(Player player) {
		Vec2 pVec = player.getBody().getPosition();
		Vec2 tVec = this.getBody().getPosition();
		return Math.abs(pVec.sub(tVec).length()) < RANGE;
	}

	public void render(GameContainer container, int delta) {

	}

}