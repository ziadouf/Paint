import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ButtonModel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

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
	static ArrayList <Integer> selectedIndices = new ArrayList<Integer>();
	int boundaryIndex = -1;

	class MyMouseListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			selectedShape = Shape.getSelectedShape(e.getX(), e.getY());
			if (!e.isControlDown()) {
				selectedIndices.clear();
			}
			if (selectedShape != -1) {
				if (!selectedIndices.contains(selectedShape))
					selectedIndices.add(selectedShape);
			}
			Shape.selectShapes(selectedIndices);
//			System.out.println("SSS  " + selectedIndices.size());
			if (GUI.drawState == Constants.RECTANGLE) {
				int x[] = new int[4];
				int y[] = new int[4];
				for (int i = 0; i < 4; i++) {
					x[i] = e.getX();
					y[i] = e.getY();

				}
				Shape.addShape(new Rect(x, y, x.length));
			}
			else if(GUI.drawState == Constants.SQUARE)
			{
				int x[] = new int [4];
				int y[] = new int [4];
				for (int i = 0; i < 4; i++) {
					x[i] = e.getX();
					y[i] = e.getY();

				}
				Shape.addShape(new Rect(x, y, x.length,true));
			}
			else if(GUI.drawState == Constants.ELLIPSE)
			{
				int a = 0;
				int b = 0;
				Shape.addShape(new Circle(e.getX(), e.getY(), a, b));
			}
			else if(GUI.drawState == Constants.CIRCLE)
			{
				int a = 0;
				int b = 0;
				Shape.addShape(new Circle(e.getX(), e.getY(), a, b, true));
			}
			else if(GUI.drawState == Constants.LINE)
			{
				 Shape.addShape(new
				 Line(e.getX(),e.getY(),e.getX(),e.getY()));
			}
			else if(GUI.drawState == Constants.TRIANGLE)
			{
				int x[] = new int [4];
				int y[] = new int [4];
				for (int i = 0; i < 4; i++) {
					x[i] = e.getX();
					y[i] = e.getY();

				}
				Shape.addShape(new Triangle(x, y, x.length));
			} else if (GUI.drawState == Constants.MOVE) {
				if (selectedShape != -1) {
					boundaryIndex = Shape.shapes.get(selectedShape).isBoundary(
							e.getX(), e.getY());
					xInit = e.getX();
					yInit = e.getY();
					curCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
				}
			}
			else if(GUI.drawState == Constants.FILL)
			{
				if(selectedShape !=-1)
				{
					if (!Shape.shapes.get(selectedShape).isFilled() || Shape.shapes.get(selectedShape).getFill() != GUI.drawColor)
						GUI.isChanged = true;
					Shape.shapes.get(selectedShape).setFilled(true);
					Shape.shapes.get(selectedShape).setFill(GUI.drawColor);
				}
			}
			else if(GUI.drawState == Constants.DELETE)
			{
				if(selectedShape !=-1) Shape.delete(selectedShape);
			}
			
			if (GUI.drawState != Constants.MOVE && GUI.drawState != Constants.FILL) {
				Shape.shapes.get(Shape.shapes.size()-1).setThickness(GUI.drawThick);
				Shape.shapes.get(Shape.shapes.size()-1).setColor(GUI.drawColor);
			}
			
			GUI.drawState = Constants.MOVE;
			repaint();
		}

		public void mouseReleased(MouseEvent e) {
			curCursor = Cursor.getDefaultCursor();
			if (GUI.isChanged) {
				for (int i=0 ; i<GUI.toggleButtons.size() ; i++)
				if (GUI.toggleButtons.get(i).isSelected()) {
					GUI.toggleButtons.get(i).doClick();
				}
				Shape.addShapeVector();
			}
			GUI.isChanged = false;
			System.out.println(Shape.allShapes.size());
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
				if (boundaryIndex != -1)
					Shape.shapes.get(selectedShape).resize(dx, dy,
							boundaryIndex);
				else
				{
					for (int i=0 ; i<selectedIndices.size() ; i++)
					Shape.shapes.get(selectedIndices.get(i)).move(dx, dy);
				}
				Shape S = Shape.shapes.get(selectedShape);
				//Shape.shapes.remove(selectedShape);
				//Shape.shapes.add(S);
				//selectedShape = Shape.shapes.size() - 1;
			}
			repaint();
		}

		public void mouseMoved(MouseEvent e) {
			if (selectedShape != -1) {
				curCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
			} else {
				curCursor = Cursor.getDefaultCursor();
			}
			repaint();
		}
	}
}
