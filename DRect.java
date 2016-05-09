import java.awt.*;
import java.awt.geom.Point2D;

public class DRect extends DShape {
	public DRectModel info;
	public DRect()
	{
		super();
	}
	
	public void draw(Graphics g)
	{	
		g.setColor(info.getC());
        g.fillRect(info.getX(),info.getY(),info.getWidth(),info.getHeight());
	}
	
	public boolean contains(Point2D p)
	{
		if (p.getX() <= info.getWidth() + info.getX() && p.getX() >= info.getX() && p.getY() <= info.getHeight() + info.getY() 
		&& p.getY() >= info.getY())
		{
			return true;
		}
		return false;
	}
	
	public String getName()
	{
		return "DRect";
	}
	
}	
