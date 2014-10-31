import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Circle extends Shape {

	private int a;
	private int b;
	private int x;
	private int y;
	private Ellipse2D E;

	Circle(int x, int y, int a, int b) {
		this.x = x;
		this.y = y;
		this.a = a;
		this.b = b;
		E = new Ellipse2D.Double(x, y, a, b);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(getColor());
		g.setStroke(new BasicStroke(getThickness()));
		if (isDashed()) g.setStroke(dashed);
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
		System.out.println(x + " " + y);
		E = new Ellipse2D.Double(x, y, a, b);
	}

	@Override
	public boolean contains(int x, int y) {
		return E.contains(x, y);
	}
	
	public void resize(int da , int db) {
		
	}

}
