import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;


public class Triangle extends Poly {
	
	private int triX[] = new int [3];
	private int triY[] = new int [3];
	
	Triangle(int[] x, int[] y, int length) {
		super(x, y, length);
	}
	
	@Override
	public void draw(Graphics2D g) {
		if (isDashed()) {
			//g.setStroke(dashed);
			int S = 8;
			for (int i=0 ; i<triX.length ; i++) {
				g.drawRect(triX[i]-S/2, triY[i]-S/2, S, S);
			}
		}
		g.setColor(getColor());
		g.setStroke(new BasicStroke(getThickness()));
		g.drawPolygon(P);
		if (isFilled()) {
			g.setColor(getFill());
			g.fillPolygon(P);
		}
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
		updatePoints();
	}
	
	
	@Override
	public void updatePoints() {
		triX = new int[3];
		triY = new int[3];
		triX[0] = xPoly[0] + (xPoly[1] - xPoly[0]) / 2;	triY[0] = yPoly[0];
		triX[1] = xPoly[2];	triY[1] = yPoly[2];
		triX[2] = xPoly[3];	triY[2] = yPoly[3];
		P = new Polygon(triX, triY, triX.length);
		int S = 8;
		boundries[0] = new Rectangle2D.Double(triX[0]-S/2, triY[0]-S/2, S, S);
		for (int i=1 ; i<xPoly.length ; i++) {
			boundries[i] = new Rectangle2D.Double(triX[i-1]-S/2, triY[i-1]-S/2, S, S);
		}
	}
	
}
