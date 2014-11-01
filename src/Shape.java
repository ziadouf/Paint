import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Shape {

	private Color color = Color.BLACK;
	private Color fill;
	private int thickness;
	private boolean isDashed;
	private boolean isFilled ;
	final static ArrayList<Shape> shapes = new ArrayList<Shape>();
	final static float dash1[] = { 10.0f };
	final static BasicStroke dashed = new BasicStroke(1.0f,
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getFill() {
		return fill;
	}

	public void setFill(Color fill) {
		this.fill = fill;
	}

	public int getThickness() {
		return thickness;
	}

	public void setThickness(int thickness) {
		this.thickness = thickness;
	}

	public boolean isDashed() {
		return isDashed;
	}

	public void setDashed(boolean isDashed) {
		this.isDashed = isDashed;
	}

	public boolean isFilled() {
		return isFilled;
	}

	public void setFilled(boolean isFilled) {
		this.isFilled = isFilled;
	}

	public static final void addShape(Shape S) {
		shapes.add(S);
	}

	public final static int getSelectedShape(int x, int y) {
		for (int i=0 ; i<shapes.size() ; i++) {
			shapes.get(i).setDashed(false);
		}
		for (int i = shapes.size() - 1; i >= 0; i--) {
			if (shapes.get(i).contains(x, y)) {
				shapes.get(i).setDashed(true);
				return i;
			}
		}
		return -1;

	}

	abstract public void draw(Graphics2D g);

	abstract public void fill(Graphics2D g);

	abstract public void move(int dx, int dy);

	abstract public boolean contains(int x, int y);
	
	abstract public int isBoundary (int x , int y);

	abstract public void resize(int dx, int dy , int index);
	
	abstract public void updatePoints();
}
