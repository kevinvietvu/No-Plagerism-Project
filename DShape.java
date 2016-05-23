import java.awt.*;
import java.awt.geom.Point2D;

public abstract class DShape implements ModelListener {
	public DShapeModel info;
	
	/**
	 * creates a new DShapeModel
	 */
	public DShape()
	{
		info = new DShapeModel();
	}
	
	public abstract void draw(Graphics g);
	
	/**
	 * get the bounds of DShapeModel
	 * @return
	 */
	public Rectangle getBounds()
	{
		return info.getBounds();
	}
	
	/**
	 * checks to see if the point is contained
	 * within the bounds
	 * @param p
	 * @return
	 */
	public boolean contains(Point2D p)
	{
		if (p.getX() < this.info.getWidth() +  this.info.getX() - 1 && p.getX() >  this.info.getX() + 1 && p.getY() <  this.info.getHeight() +  this.info.getY() - 1
		&& p.getY() >  this.info.getY() + 1)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * returns a DShapeModel
	 * @return
	 */
	public DShapeModel getModel()
	{
		return info;
	}
	
	/**
	 * returns the width
	 * @return
	 */
	public int getWidth()
	{
		return info.getWidth();
	}
	
	/**
	 * returns the name
	 * @return
	 */
	public String getName()
	{
		return "DShape";
	}

	@Override
	/**
	 * method to notfiy change in the model
	 */
	public void modelChanged(DShapeModel model) {
		info = model;
		
	}
	
}