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

	private float renderScalar = 10.0f;
	private Vec2 renderTranslation = new Vec2(0.0f, 0.0f);

	public SlickWorld(Vec2 gravity) {
		super(gravity);
	}

	public void setRenderScalar(float renderScalar) {
		this.renderScalar = renderScalar;
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

	public void renderPolygonShape(GameContainer container, Graphics g, PolygonShape shape, Body body) {
		Vec2[] jboxPoints = shape.getVertices();
		float[] slickPoints = new float[jboxPoints.length];
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
				thisFixture = thisFixture.getNext();
			}
			thisBody = thisBody.getNext();
		}
	}

}