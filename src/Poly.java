import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Polygon;


public class Poly extends Shape {
	
	private int xPoly[];
	private int yPoly[];
	private int length;
	private Polygon P;

	Poly (int x[] , int y[] , int length) {
		this.xPoly = x;
		this.yPoly = y;
		this.length = length;
		P = new Polygon(xPoly, yPoly, length);
	}
	
	public Polygon getPolygon () {
		return P;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(getColor());
		g.setStroke(new BasicStroke(getThickness()));
		if (isDashed()) g.setStroke(dashed);
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
		P = new Polygon(xPoly, yPoly, length);
		System.out.println(dx + " " + dy);
	}

	@Override
	public boolean contains(int x, int y) {
		return P.contains(x,y);
	}
	
}
