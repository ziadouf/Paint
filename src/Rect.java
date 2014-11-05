import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;


public class Rect extends Poly {
	
	Rect(int[] x, int[] y, int length) {
		super(x, y, length);
	}
	
	public Rect(int[] x, int[] y, int length, boolean b) {
		super(x, y, length, b);
	}

	public void resize (int dx , int dy , int index) {
		if (index == 0) {
			if (isRegular) dx = dy;
			xPoly[0] += dx;
			xPoly[3] += dx;
			yPoly[0] += dy;
			yPoly[1] += dy; 
		}
		else if (index == 1) {
			if (isRegular) dx = -dy;
			xPoly[1] += dx;
			xPoly[2] += dx;
			yPoly[0] += dy;
			yPoly[1] += dy;
		}
		else if (index == 2) {
			if (isRegular) dx = dy;
			xPoly[1] += dx;
			xPoly[2] += dx;
			yPoly[2] += dy;
			yPoly[3] += dy;
		}
		else {
			if (isRegular) dx = -dy;
			xPoly[0] += dx;
			xPoly[3] += dx;
			yPoly[2] += dy;
			yPoly[3] += dy;
		}
		GUI.isChanged = true;
		updatePoints();
	}
	
	@Override
	public void updatePoints() {
		P = new Polygon(xPoly, yPoly, length);
		int S = 8;
		for (int i=0 ; i<xPoly.length ; i++) {
			boundries[i] = new Rectangle2D.Double(xPoly[i]-S/2, yPoly[i]-S/2, S, S);
		}
	}
	
	@Override
	public Shape copy() {
		Rect newShape = new Rect(xPoly,yPoly,length,isRegular);
		newShape.setColor(getColor());
		newShape.setThickness(getThickness());
		newShape.setFilled(isFilled());
		newShape.setFill(getFill());
		return newShape;
	}
}
