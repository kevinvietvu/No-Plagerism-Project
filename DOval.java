import java.awt.*;
import java.awt.geom.Point2D;

public class DOval extends DShape {
	
	public DOval()
	{
		super();
	}
	
	public void draw(Graphics g)
	{	
		g.setColor(info.getColor());
        g.fillOval(info.getX(),info.getY(),info.getWidth(),info.getHeight());
	}
	
	public String getName()
	{
		return "DOval";
	}
	
}
