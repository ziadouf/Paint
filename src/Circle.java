import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

public class Circle extends Shape {

	private int x;
	private int y;
	private int a;
	private int b;
	private int xBounds[] = new int[4];
	private int yBounds[] = new int[4];
	private Ellipse2D E;
	private boolean isCircle = false;
	private int drawX;
	private int drawY;
	private int drawA;
	private int drawB;
	
	Circle(int x, int y, int a, int b) {
		this.x = x;
		this.y = y;
		this.a = a;
		this.b = b;
		boundries = new Rectangle2D.Double[4];
		updatePoints();
	}

	Circle(int x, int y, int a, int b, boolean isCircle) {
		this.x = x;
		this.y = y;
		this.a = a;
		this.b = b;
		this.isCircle = isCircle;
		boundries = new Rectangle2D.Double[4];
		updatePoints();
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Constants.DEFAULT_COLOR);
		g.setStroke(new BasicStroke(5));
		g.setStroke(new BasicStroke(Constants.DEFAULT_THICKNESS));
		if (isDashed()) {
			// g.setStroke(dashed);
			for (int i = 0; i < xBounds.length; i++) {
				g.draw(boundries[i]);
			}
		}
		g.setColor(getColor());
		g.setStroke(new BasicStroke(getThickness()));
		
		g.draw(E);
		if (isFilled()) {
			g.setColor(getFill());
			g.fill(E);
		}
		
//		if (isDashed()) {
//			for (int i = 0; i < xBounds.length; i++) {
//				//g.setStroke(new BasicStroke(3));
//				g.setColor(new Color(102,178,255));
//				g.fillOval((int)boundries[i].getMinX(), (int)boundries[i].getMinY(), 10, 10);
//				//g.draw(boundries[i]);
//			}
//		}
	}

	@Override
	public void move(int dx, int dy) {
		this.x += dx;
		this.y += dy;
		GUI.isChanged = true;
		updatePoints();
	}

	@Override
	public boolean contains(int x, int y) {
		return (E.contains(x, y) || isBoundary(x, y) != -1);
	}

	public void resize(int dx, int dy, int index) {
		if (isCircle) {
			System.out.println("INDEX: " + index);
			int dr = dx;
			if (index == 0 || index == 3) dr = dy;
			if (index == 0 || index == 1) dr *= -1;
			this.a += dr*2;
			this.b += dr*2;
			this.x -= dr;
			this.y -= dr;
		}
		else
		{
			if (index == 0) {
				this.y += dy;
				this.b -= dy;
				this.a += dx;
			} else if (index == 1) {
				this.x += dx;
				this.a -= dx;
			} else if (index == 2) {
				this.a += dx;
			} else {
				this.b += dy;
			}
		}
		GUI.isChanged = true;
		updatePoints();
	}

	@Override
	public void updatePoints() {
		drawX = this.x;
		drawY = this.y;
		drawA = this.a;
		drawB = this.b;
		
		if (a < 0) {
			drawA *= -1;
			drawX += a;
		}
		if (b < 0) {
			drawB *= -1;
			drawY += b;
		}
		
		E = new Ellipse2D.Double(drawX, drawY, drawA, drawB);
		
		xBounds[0] = (int) E.getCenterX();
		yBounds[0] = (b >= 0) ? (int) E.getMinY() : (int) E.getMaxY();
		xBounds[1] = (a >= 0) ? (int) E.getMinX() : (int) E.getMaxX();
		yBounds[1] = (int) E.getCenterY();
		xBounds[2] = (a >= 0) ? (int) E.getMaxX() : (int) E.getMinX();
		yBounds[2] = (int) E.getCenterY();
		xBounds[3] = (int) E.getCenterX();
		yBounds[3] = (b >= 0) ? (int) E.getMaxY() : (int) E.getMinY();
		
		int S = 8;
		for (int i = 0; i < xBounds.length; i++) {
			boundries[i] = new Rectangle2D.Double(xBounds[i] - S / 2,
					yBounds[i] - S / 2, S, S);
		}
		
	}

	@Override
	public Shape copy() {
		Circle newShape = new Circle (x,y,a,b,isCircle);
		newShape.setColor(getColor());
		newShape.setThickness(getThickness());
		newShape.setFilled(isFilled());
		newShape.setFill(getFill());
		return newShape;
	}

}
