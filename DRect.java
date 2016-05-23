import java.awt.*;
import java.awt.geom.Point2D;

public class DRect extends DShape {

	public DRect()
	{
		super();
	}
	
	public void draw(Graphics g)
	{	
		g.setColor(info.getColor());
        g.fillRect(info.getX(),info.getY(),info.getWidth(),info.getHeight());
	}
	
	
	
	public String getName()
	{
		return "DRect";
	}
	
}	
