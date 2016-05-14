import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;


public class DShape implements ModelListener {
	Shape s;
	public DShapeModel info;
	
	public DShape()
	{
<<<<<<< HEAD
		Shape s = new Rectangle(0,0,0,0);
		info = new DShapeModel();
		
=======
		Shape s = new Rectangle(10,10,20,20);
>>>>>>> c42b81463d0f6a4c2755dea154ca1ec65ff7510e
	}
	
	public DShape(DShapeModel model)
	{
		Shape s = new Rectangle(model.getX(),model.getY(),model.getWidth(),model.getHeight());

		
	}
	
	public Rectangle getBounds()
	{
		return info.getBounds();
	}
	
	public boolean contains(Point2D p)
	{
		if (p.getX() < this.info.getWidth() +  this.info.getX() - 1 && p.getX() >  this.info.getX() + 1 && p.getY() <  this.info.getHeight() +  this.info.getY() - 1
		&& p.getY() >  this.info.getY() + 1)
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
