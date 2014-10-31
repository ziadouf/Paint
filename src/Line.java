import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Line extends Shape {
	private int x1;
	private int y1;
	private int x2;
	private int y2;

	Line(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(getColor());
		g.setStroke(new BasicStroke(getThickness()));
		if (isDashed()) g.setStroke(dashed);
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
	}

	@Override
	public boolean contains(int x, int y) {
		return (Line2D.ptSegDist(x1, y1, x2, y2, x, y) <= 4);
	}

}
