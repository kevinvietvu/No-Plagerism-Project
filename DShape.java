import java.awt.*;
import java.awt.geom.Point2D;


public class DShape implements ModelListener {
	Shape s;
	public DShapeModel info;
	
	public DShape()
	{
		Shape s = new Rectangle(0,0,0,0);
	}
	
	public DShapeModel getBounds()
	{
		return info;
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
		return "DShape";
	}

	@Override
	public void modelChanged(DShapeModel model) {
		info = model;
		
	}

}
