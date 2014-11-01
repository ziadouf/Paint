//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Point;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.geom.Rectangle2D;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//
//public class JRisk extends JPanel {
//  private int SIZE = 8;
//  private Rectangle2D[] points = { new Rectangle2D.Double(50, 50,SIZE, SIZE), new Rectangle2D.Double(150, 100,SIZE, SIZE) };
//  Rectangle2D s = new Rectangle2D.Double();
//
//  ShapeResizeHandler ada = new ShapeResizeHandler();
//
//  public JRisk() {
//    addMouseListener(ada);
//    addMouseMotionListener(ada);
//  }
//
//  public void paintComponent(Graphics g) {
//    super.paintComponent(g);
//
//    Graphics2D g2 = (Graphics2D) g;
//
//    for (int i = 0; i < points.length; i++) {
//      g2.fill(points[i]);
//    }
//    s.setRect(points[0].getCenterX(), points[0].getCenterY(),
//        Math.abs(points[1].getCenterX()-points[0].getCenterX()),
//        Math.abs(points[1].getCenterY()- points[0].getCenterY()));
//
//    g2.draw(s);
//  }
//
//  class ShapeResizeHandler extends MouseAdapter {
//    Rectangle2D r = new Rectangle2D.Double(0,0,SIZE,SIZE);
//    private int pos = -1;
//    public void mousePressed(MouseEvent event) {
//      Point p = event.getPoint();
//
//      for (int i = 0; i < points.length; i++) {
//        if (points[i].contains(p)) {
//          pos = i;
//          return;
//        }
//      }
//    }
//
//    public void mouseReleased(MouseEvent event) {
//      pos = -1;
//    }
//
//    public void mouseDragged(MouseEvent event) {
//      if (pos == -1)
//        return;
//
//      points[pos].setRect(event.getPoint().x,event.getPoint().y,points[pos].getWidth(),
//          points[pos].getHeight());
//      repaint();
//    }
//  }
//
//  public static void main(String[] args) {
//
//    JFrame frame = new JFrame("Resize Rectangle");
//
//    frame.add(new JRisk());
//    frame.setSize(300, 300);
//    frame.setLocationRelativeTo(null);
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    frame.setVisible(true);
//  }
//}

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main_Test {

	private JFrame mainMap;
	private Poly poly;
	private Color color;

	public Main_Test() {
		initComponents();

	}

	private void initComponents() {

		mainMap = new JFrame();
		mainMap.setResizable(false);

		mainMap.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		/*
		 * int xPoly[] = {150, 250, 325, 375, 450, 275, 100}; int yPoly[] =
		 * {150, 100, 125, 225, 250, 375, 300};
		 */

		int xPoly[] = { 100, 100, 200, 200 };
		int yPoly[] = { 100, 200, 200, 100 };

		poly = new Poly(xPoly, yPoly, xPoly.length);
		Shape.addShape(poly);
		Shape.addShape(new Circle(300, 300, 100, 100));
		Shape.addShape(new Line(100, 100, 500, 400));
		Shape.shapes.get(0).setFilled(true);
		//Shape.shapes.get(1).setFilled(true);
		// Shape.shapes.get(2).setFilled(true);
		Shape.shapes.get(0).setFill(Color.BLUE);
		Shape.shapes.get(1).setFill(Color.RED);
		// Shape.shapes.get(2).setFill(Color.GREEN);

		color = Color.BLUE;

		JPanel p = new JPanel() {

			int xPoly[] = { 400, 200, 600 };
			int yPoly[] = { 400, 600, 600 };

			// int x = 300 , y = 300 , a = 100;

			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2D = (Graphics2D) g;
				super.paintComponent(g);
				for (int i = 0; i < Shape.shapes.size(); i++) {
					Shape.shapes.get(i).draw(g2D);
				}
				if (curCursor != null)
			        setCursor(curCursor);
				/*
				 * Scanner sc = new Scanner(System.in); for (int i=0 ; i<1 ;
				 * i++) { System.out.println("Enter Vertices:"); int N; N =
				 * sc.nextInt(); int xPoly[] = new int[100]; int yPoly[] = new
				 * int[100]; int cur = 0; for (int j=0 ; j<N ; j++) { int x,y; x
				 * = sc.nextInt(); y = sc.nextInt(); xPoly[cur] = x;
				 * yPoly[cur++] = y; }
				 * 
				 * Poly p = new Poly(xPoly,yPoly,cur); p.draw(g2D); pp = p; }
				 * sc.close(); //pp = new Poly(xPoly, yPoly, xPoly.length);
				 * 
				 * //pp = new Line (100,100,200,200); pp.draw(g2D);
				 */
				addMouseListener(new MyMouseListener());
				addMouseMotionListener(new MyMouseMotionListener());
			}

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(800, 600);
			}

			int xInit, yInit, xFinal, yFinal;
			Cursor curCursor;
			int selectedShape = -1;
			int boundaryIndex = -1;
			class MyMouseListener extends MouseAdapter {
				public void mousePressed(MouseEvent e) {
					selectedShape = Shape.getSelectedShape(e.getX(), e.getY());
					if (selectedShape != -1) {
						boundaryIndex = Shape.shapes.get(selectedShape).isBoundary(e.getX(), e.getY());
						xInit = e.getX();
						yInit = e.getY();
						curCursor = Cursor
								.getPredefinedCursor(Cursor.HAND_CURSOR);
					}
					repaint();
				}
				
				public void mouseReleased(MouseEvent e) {
					curCursor = Cursor.getDefaultCursor();
					repaint();
				}
			}

			class MyMouseMotionListener extends MouseMotionAdapter {
				public void mouseDragged(MouseEvent e) {
					if (selectedShape != -1) {
						xFinal = e.getX();
						yFinal = e.getY();
						int dx = xFinal - xInit;
						int dy = yFinal - yInit;
						xInit = xFinal;
						yInit = yFinal;
						if (boundaryIndex != -1) Shape.shapes.get(selectedShape).resize(dy, dy, boundaryIndex);
						else Shape.shapes.get(selectedShape).move(dx, dy);
						Shape S = Shape.shapes.get(selectedShape);
						Shape.shapes.remove(selectedShape);
						Shape.shapes.add(S);
						selectedShape = Shape.shapes.size() - 1;
					}
					repaint();
				}
				
				public void mouseMoved(MouseEvent e) {
					if (selectedShape != -1) {
						curCursor = Cursor
								.getPredefinedCursor(Cursor.HAND_CURSOR);
					} else {
						curCursor = Cursor.getDefaultCursor();
					}

					//repaint();
				}
			}

		};

		mainMap.add(p);

		mainMap.pack();
		mainMap.setVisible(true);

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Main_Test();
			}
		});
	}
}