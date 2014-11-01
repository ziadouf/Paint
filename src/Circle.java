import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

public class Circle extends Shape {

	private int a;
	private int b;
	private int x;
	private int y;
	private int xBounds[] = new int[4];
	private int yBounds[] = new int[4];
	private Double[] boundries = new Rectangle2D.Double[4];
	private Ellipse2D E;

	Circle(int x, int y, int a, int b) {
		this.x = x;
		this.y = y;
		this.a = a;
		this.b = b;
		updatePoints();
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(getColor());
		g.setStroke(new BasicStroke(getThickness()));
		if (isDashed()) {
			//g.setStroke(dashed);
			int S = 8;
			for (int i=0 ; i<xBounds.length ; i++) {
				g.drawRect(xBounds[i]-S/2, yBounds[i]-S/2, S, S);
			}
		}
		g.drawOval(x, y, a, b);
		if (isFilled()) {
			g.setColor(getFill());
			g.fillOval(x, y, a, b);
		}
	}

	@Override
	public void fill(Graphics2D g) {
		g.setColor(getFill());
		g.fillOval(x, y, a, b);
	}

	@Override
	public void move(int dx, int dy) {
		this.x += dx;
		this.y += dy;
		updatePoints();
	}

	@Override
	public boolean contains(int x, int y) {
		return E.contains(x, y);
	}
	
	public void resize(int da , int db , int index) {
		if (index == 0) {
			this.y += db;
			this.b -= db;
		}
		else if (index == 1) {
			this.x += da;
			this.a -= da;
		}
		else if (index == 2) {
			this.a += da;
		}
		else {
			this.b += db;
		}
		updatePoints();
	}

	@Override
	public int isBoundary(int x, int y) {
		for (int i=0 ; i<boundries.length ; i++)
			if (boundries[i].contains(x,y)) return i;
		
		return -1;
	}

	@Override
	public void updatePoints() {
		E = new Ellipse2D.Double(x, y, a, b);
		xBounds[0] = (int) E.getCenterX();	yBounds[0] = (int) E.getMinY();
		xBounds[1] = (int) E.getMinX();	yBounds[1] = (int) E.getCenterY();
		xBounds[2] = (int) E.getMaxX();	yBounds[2] = (int) E.getCenterY();
		xBounds[3] = (int) E.getCenterX();	yBounds[3] = (int) E.getMaxY();
		int S = 8;
		for (int i=0 ; i<xBounds.length ; i++) {
			boundries[i] = new Rectangle2D.Double(xBounds[i]-S/2, yBounds[i]-S/2, S, S);
		}
	}


}
