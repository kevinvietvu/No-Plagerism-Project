import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.geom.*;

public class DLine extends DShape 
{
	private static final int HIT_BOX_SIZE = 3;
	private Line2D.Double line = new Line2D.Double();
	
	/**
	 * calls the superclass constructor
	 */
	public DLine()
	{
		super();
	}
	
	/**
	 * draws the line
	 */
	public void draw(Graphics g)
	{	
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(info.getColor());
		line.setLine(info.getX(), info.getY(), info.getWidth() + info.getX(), info.getHeight() + info.getY());
        g2.draw(line);
	}
	
	/**
	 * checks to see if a point is in the line
	 */
	public boolean contains(Point2D p)
	{
		int rectangleX = (int) p.getX() - HIT_BOX_SIZE / 2;
		int rectangleY = (int) p.getY() - HIT_BOX_SIZE / 2;

		int width = HIT_BOX_SIZE;
		int height = HIT_BOX_SIZE;
		return line.intersects(rectangleX, rectangleY, width, height);
		
	}
	
	/**
	 * returns a the name
	 */
	public String getName()
	{
		return "DLine";
	}
	
}
