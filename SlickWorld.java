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

public class SlickWorld extends World {

	private final float DEFAULT_RENDER_SCALAR = 20.0f;
	private final float DEFAULT_SMOOTH_PAN = 0.9f;
	private final float DEFAULT_SMOOTH_ZOOM = 0.99f;

	private float renderScalar = DEFAULT_RENDER_SCALAR;
	private float newRenderScalar = DEFAULT_RENDER_SCALAR;
	private Vec2 renderTranslation = new Vec2(0.0f, 0.0f);
	private Body focus;
	private Vec2 fixedFocus;
	private boolean focusFixed = false;
	private float smoothPan = DEFAULT_SMOOTH_PAN;
	private float smoothZoom = DEFAULT_SMOOTH_ZOOM;

	public SlickWorld(Vec2 gravity) {
		super(gravity);
	}

	public void setRenderScalar(float renderScalar) {
		this.newRenderScalar = renderScalar;
	}

	public void setRenderScalar() {
		this.newRenderScalar = DEFAULT_RENDER_SCALAR;
	}

	public float getRenderScalar() {
		return renderScalar;
	}

	public void setRenderTranslation(float x, float y) {
		renderTranslation = new Vec2(x, y);
	}

	public void setRenderTranslation(Vec2 translation) {
		renderTranslation = translation;
	}

	public Vec2 getRenderTranslation() {
		return new Vec2(renderTranslation);
	}

	public void setFocus(Body body) {
		focusFixed = false;
		this.focus = body;
	}

	public void setFocus(Vec2 vec) {
		focusFixed = true;
		this.fixedFocus = new Vec2(vec);
	}

	public void renderPolygonShape(GameContainer container, Graphics g, PolygonShape shape, Body body) {
		Vec2[] jboxPoints = shape.getVertices();
		float[] slickPoints = new float[shape.getVertexCount()*2];
		for (int i = 0; i < shape.getVertexCount(); i++) {
			Vec2 point = body.getWorldPoint(jboxPoints[i]);
			slickPoints[2*i] = point.x*renderScalar+renderTranslation.x;
			slickPoints[2*i+1] = point.y*renderScalar+renderTranslation.y;
		}
		Polygon slickPoly = new Polygon(slickPoints);
		g.draw(slickPoly);
	}

	public void renderChainShape(GameContainer container, Graphics g, ChainShape shape, Body body) {
		Vec2[] jboxPoints = shape.m_vertices;
		float[] slickPoints = new float[jboxPoints.length];
		for (int i = 0; i < shape.getChildCount(); i++) {
			Vec2 point1 = body.getWorldPoint(jboxPoints[i]);
			Vec2 point2 = body.getWorldPoint(jboxPoints[i+1]);
			float x1 = point1.x*renderScalar+renderTranslation.x;
			float y1 = point1.y*renderScalar+renderTranslation.y;
			float x2 = point2.x*renderScalar+renderTranslation.x;
			float y2 = point2.y*renderScalar+renderTranslation.y;
			g.drawLine(x1, y1, x2, y2);
		}
	}

	public void renderCircleShape(GameContainer container, Graphics g, CircleShape shape, Body body) {
		Vec2 center = body.getWorldCenter();
		g.drawOval((center.x-shape.m_radius/1.0f)*renderScalar+renderTranslation.x, (center.y-shape.m_radius/1.0f)*renderScalar+renderTranslation.y, shape.m_radius*2.0f*renderScalar, shape.m_radius*2.0f*renderScalar);
	}

	public void setSmoothPan(float smoothPan) {
		this.smoothPan = smoothPan;
	}

	public void setSmoothPan() {
		this.smoothPan = DEFAULT_SMOOTH_PAN;
	}

	private Vec2 lastTranslation = null;
	public void update(GameContainer container, int delta) {
		// this.step(1.0f/60.0f, 12, 8);
		this.step((float)delta/500.0f, 12, 8);
		
		// translation, with smooth pan
		if (focus != null) {
			if (lastTranslation != null) {
				Vec2 newTranslation;
				if (focusFixed)
					newTranslation = fixedFocus.mul(-1.0f).mul(renderScalar).add(new Vec2((float)container.getWidth()/2.0f, (float)container.getHeight()/2.0f));
				else
					newTranslation = focus.getPosition().mul(-1.0f).mul(renderScalar).add(new Vec2((float)container.getWidth()/2.0f, (float)container.getHeight()/2.0f));
				renderTranslation.x = (lastTranslation.x*(1.0f+smoothPan)+newTranslation.x*(1.0f-smoothPan))/2.0f;
				renderTranslation.y = (lastTranslation.y*(1.0f+smoothPan)+newTranslation.y*(1.0f-smoothPan))/2.0f;
				lastTranslation = new Vec2(renderTranslation);

			}
			else {
				renderTranslation = focus.getPosition().mul(-1.0f).mul(renderScalar).add(new Vec2((float)container.getWidth()/2.0f, (float)container.getHeight()/2.0f));
				lastTranslation = new Vec2(renderTranslation);
			}		
		}

		// scaling, with smooth zoom
		renderScalar = (renderScalar*(1.0f+smoothZoom)+newRenderScalar*(1.0f-smoothZoom))/2.0f;
	}

	public void render(GameContainer container, Graphics g) {
		Body thisBody = this.getBodyList();
		for (int i = 0; i < this.getBodyCount(); i++) {
			Fixture thisFixture = thisBody.getFixtureList();
			while (thisFixture != null) {
				Shape shape = thisFixture.getShape();
				if (shape instanceof PolygonShape) {
					renderPolygonShape(container, g, (PolygonShape)shape, thisBody);
				}
				else if (shape instanceof ChainShape) {
					renderChainShape(container, g, (ChainShape)shape, thisBody);
				}
				else if (shape instanceof CircleShape) {
					renderCircleShape(container, g, (CircleShape)shape, thisBody);
				}
				thisFixture = thisFixture.getNext();
			}
			thisBody = thisBody.getNext();
		}
	}

}