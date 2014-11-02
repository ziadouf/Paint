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

		int xPoly[] = { 100, 200, 200, 100 };
		int yPoly[] = { 100, 100, 200, 200 };

		int x2Poly[] = { 300, 400, 400, 300 };
		int y2Poly[] = { 300, 300, 400, 400 };
		
		poly = new Rect(xPoly, yPoly, xPoly.length);
		Shape.addShape(poly);
		Shape.addShape(new Circle(200, 200, 100, 100,true));
		Shape.addShape(new Circle(400, 400, 200, 200));
		Shape.addShape(new Line(100, 100, 500, 400));
		Shape.addShape(new Rect(x2Poly, y2Poly, x2Poly.length,true));
		//Shape.shapes.get(0).setFilled(true);
		//Shape.shapes.get(1).setFilled(true);
		// Shape.shapes.get(2).setFilled(true);
		Shape.shapes.get(0).setFill(Color.BLUE);
		Shape.shapes.get(1).setFill(Color.RED);
		// Shape.shapes.get(2).setFill(Color.GREEN);

		color = Color.BLUE;

		JPanel p = new GUIPanel();

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