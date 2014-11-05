import java.awt.BasicStroke;
import java.awt.Color;
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
	private Ellipse2D E;
	private boolean isCircle = false;

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
		g.setStroke(new BasicStroke(Constants.DEFAULT_THICKNESS));
		if (isDashed()) {
			// g.setStroke(dashed);
			int S = 8;
			for (int i = 0; i < xBounds.length; i++) {
				g.drawRect(xBounds[i] - S / 2, yBounds[i] - S / 2, S, S);
			}
		}
		g.setColor(getColor());
		g.setStroke(new BasicStroke(getThickness()));
		g.drawOval(x, y, a, b);
		if (isFilled()) {
			g.setColor(getFill());
			g.fillOval(x, y, a, b);
		}
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
		if (index == 0) {
			this.y += dy;
			this.b -= dy;
			if (isCircle)
				this.a = this.b;
		} else if (index == 1) {
			this.x += dx;
			this.a -= dx;
			if (isCircle)
				this.b = this.a;
		} else if (index == 2) {
			this.a += dx;
			if (isCircle)
				this.b = this.a;
		} else {
			this.b += dy;
			if (isCircle)
				this.a = this.b;
		}
		GUI.isChanged = true;
		updatePoints();
	}

	@Override
	public void updatePoints() {
		E = new Ellipse2D.Double(x, y, a, b);
		xBounds[0] = (int) E.getCenterX();
		yBounds[0] = (int) E.getMinY();
		xBounds[1] = (int) E.getMinX();
		yBounds[1] = (int) E.getCenterY();
		xBounds[2] = (int) E.getMaxX();
		yBounds[2] = (int) E.getCenterY();
		xBounds[3] = (int) E.getCenterX();
		yBounds[3] = (int) E.getMaxY();
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
