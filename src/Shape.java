import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;

public abstract class Shape {

	private Color color = Color.BLACK;
	private Color fill;
	private int thickness;
	private boolean isDashed;
	private boolean isFilled ;
	protected Double[] boundries;
	private static int index = -1 ;
	static ArrayList < ArrayList<Shape> > allShapes = new ArrayList < ArrayList<Shape> >();
	static ArrayList<Shape> shapes = new ArrayList<Shape>();
	
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

	public static void setIndex(int index) {
		Shape.index = index;
	}
	
	public static void addShape(Shape S) {
		shapes.add(S);
		GUI.isChanged = true;
	}
	
	
	public static void addShapeVector () {
		ArrayList <Shape> vec = new ArrayList<Shape>();
		for (int i=0 ; i<shapes.size() ; i++)
		{			
			vec.add(shapes.get(i).copy());
		}
		
		while (allShapes.size() > index+1) {
			allShapes.remove(allShapes.size()-1);
		}
		allShapes.add(vec);
		index ++ ;
	}
	
	public static void undo () throws Exception
	{
		index -- ;
		if(index<0) {
			index = 0;
			System.out.println("HEEERE" + index);
			throw new Exception();
		}
		else {
			shapes = new ArrayList <Shape>();
			for (int i=0 ; i<allShapes.get(index).size() ; i++) {
				shapes.add(allShapes.get(index).get(i).copy());
			}
			System.out.println("Current: " + shapes);
			System.out.println("============");
			for (int i=0 ; i<allShapes.size() ; i++) {
				System.out.println(allShapes.get(i));
			}
			System.out.println("============");
			System.out.println();

		}
	}
	
	public static void redo() throws Exception
	{
		index ++ ;
		if(index >= allShapes.size()) 
		{
			index = allShapes.size()-1 ;
			throw new Exception();
		}
		else {
			shapes = new ArrayList <Shape>();
			for (int i=0 ; i<allShapes.get(index).size() ; i++) {
				shapes.add(allShapes.get(index).get(i).copy());
			}
			System.out.println("Current: " + shapes);
			System.out.println("============");
			for (int i=0 ; i<allShapes.size() ; i++) {
				System.out.println(allShapes.get(i));
			}
			System.out.println("============");
			System.out.println();
		}
	}
	
	public static int getSelectedShape(int x, int y) {
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
	
	public int isBoundary(int x, int y) {
		for (int i=0 ; i<boundries.length ; i++) {
			if (boundries[i].contains(x,y)) return i;
		}
		
		return -1;
	}
	
	abstract public void draw(Graphics2D g);

	abstract public void move(int dx, int dy);

	abstract public boolean contains(int x, int y);
	
	abstract public void resize(int dx, int dy , int index);
	
	abstract public Shape copy ();
	
	abstract public void updatePoints();
}
