import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;


public class Poly extends Shape {
	
	protected int xPoly[];
	protected int yPoly[];
	protected int length;
	protected Polygon P;
	protected boolean isRegular;
	
	Poly (int x[] , int y[] , int length) {
		this.xPoly = x;
		this.yPoly = y;
		this.length = length;
		boundries = new Rectangle2D.Double[length];
		updatePoints();
	}
	
	Poly (int x[] , int y[] , int length, boolean isRegular) {
		this.xPoly = x;
		this.yPoly = y;
		this.length = length;
		boundries = new Rectangle2D.Double[length];
		this.isRegular = isRegular;
		updatePoints();
	}
	
	public Polygon getPolygon () {
		return P;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Constants.DEFAULT_COLOR);
		g.setStroke(new BasicStroke(Constants.DEFAULT_THICKNESS));
		if (isDashed()) {
			//g.setStroke(dashed);
			int S = 8;
			for (int i=0 ; i<xPoly.length ; i++) {
				g.drawRect(xPoly[i]-S/2, yPoly[i]-S/2, S, S);
			}
		}
		g.setColor(getColor());
		g.setStroke(new BasicStroke(getThickness()));
		g.drawPolygon(P);
		if (isFilled()) {
			g.setColor(getFill());
			g.fillPolygon(P);
		}
	}

	@Override
	public void fill(Graphics2D g) {
		g.setColor(getFill());
		g.fillPolygon(P);
	}

	@Override
	public void move(int dx , int dy) {
		for (int i=0 ; i<length ; i++) {
			xPoly[i] += dx;
			yPoly[i] += dy;
		}
		updatePoints();
	}

	@Override
	public boolean contains(int x, int y) {
		return (P.contains(x, y) || isBoundary(x,y) != -1);
	}


	@Override
	public void resize(int dx, int dy, int index) {
	}

	@Override
	public void updatePoints() {
		P = new Polygon(xPoly, yPoly, length);
		int S = 8;
		for (int i=0 ; i<xPoly.length ; i++) {
			boundries[i] = new Rectangle2D.Double(xPoly[i]-S/2, yPoly[i]-S/2, S, S);
		}
	}
	
}
