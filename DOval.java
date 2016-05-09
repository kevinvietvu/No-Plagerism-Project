import java.awt.*;
import java.awt.geom.Point2D;

public class DOval extends DShape {
	public DOvalModel info;
	
	public DOval()
	{
		super();
	}
	
	public void draw(Graphics g)
	{	
		g.setColor(info.getC());
        g.fillOval(info.getX(),info.getY(),info.getWidth(),info.getHeight());
	}
	
	public boolean contains(Point2D p)
	{
		if (p.getX() < info.getWidth() + info.getX() - 1 && p.getX() > info.getX() + 1 && p.getY() < info.getHeight() + info.getY() - 1
		&& p.getY() > info.getY() + 1)
		{
			return true;
		}
		return false;
	}
	
	
	public String getName()
	{
		return "DOval";
	}
	
}
