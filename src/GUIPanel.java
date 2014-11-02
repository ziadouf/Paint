import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;


public class GUIPanel extends JPanel {
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		super.paintComponent(g);
		for (int i = 0; i < Shape.shapes.size(); i++) {
			Shape.shapes.get(i).draw(g2D);
		}
		if (curCursor != null)
	        setCursor(curCursor);
		addMouseListener(new MyMouseListener());
		addMouseMotionListener(new MyMouseMotionListener());
	}

	int xInit, yInit, xFinal, yFinal;
	Cursor curCursor;
	int selectedShape = -1;
	int boundaryIndex = -1;
	class MyMouseListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if (GUI.drawLine) {
				//Shape.addShape(new Line(e.getX(),e.getY(),e.getX(),e.getY()));
				int x[] = new int[4];
				int y[] = new int[4];
				for (int i=0 ; i<4 ; i++) {
					x[i] = e.getX();
					y[i] = e.getY();
					
				}
				Shape.addShape(new Rect(x,y,x.length));
				GUI.drawLine = false;
			}
			else {
				selectedShape = Shape.getSelectedShape(e.getX(), e.getY());
				if (selectedShape != -1) {
					boundaryIndex = Shape.shapes.get(selectedShape).isBoundary(e.getX(), e.getY());
					xInit = e.getX();
					yInit = e.getY();
					curCursor = Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR);
				}
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
				System.out.println(boundaryIndex);
				if (boundaryIndex != -1) Shape.shapes.get(selectedShape).resize(dx, dy, boundaryIndex);
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
			repaint();
		}
	}
}
