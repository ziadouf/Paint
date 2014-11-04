import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

public class Line extends Shape {
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private int xBounds[] = new int[2];
	private int yBounds[] = new int[2];
	
	Line(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		boundries = new Rectangle2D.Double[2];
		updatePoints();
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Constants.DEFAULT_COLOR);
		g.setStroke(new BasicStroke(Constants.DEFAULT_THICKNESS));
		if (isDashed()) {
			//g.setStroke(dashed);
			int S = 8;
			for (int i=0 ; i<xBounds.length ; i++) {
				g.drawRect(xBounds[i]-S/2, yBounds[i]-S/2, S, S);			}
		}
		g.setColor(getColor());
		g.setStroke(new BasicStroke(getThickness()));
		g.drawLine(x1, y1, x2, y2);
	}

	@Override
	public void fill(Graphics2D g) {
		return;
	}

	@Override
	public void move(int dx, int dy) {
		this.x1 += dx;
		this.x2 += dx;
		this.y1 += dy;
		this.y2 += dy;
		updatePoints();
	}

	@Override
	public boolean contains(int x, int y) {
		return (Line2D.ptSegDist(x1, y1, x2, y2, x, y) <= 4);
	}

	@Override
	public void resize(int x, int y, int index) {
		if (index == 0) {
			x1 += x;
			y1 += y;
		}
		else {
			x2 += x;
			y2 += y;
		}
		updatePoints();
	}

	@Override
	public void updatePoints() {
		xBounds[0] = x1; yBounds[0] = y1;
		xBounds[1] = x2 ; yBounds[1] = y2;
		int S = 8;
		for (int i=0 ; i<xBounds.length ; i++) {
			boundries[i] = new Rectangle2D.Double(xBounds[i]-S/2, yBounds[i]-S/2, S, S);
		}
	}

}
