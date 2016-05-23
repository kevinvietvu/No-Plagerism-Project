import java.awt.*;
import java.util.ArrayList;

public class DShapeModel {
	public int x;
	public int y;
	public int width;
	public int height;
	public Color color;
	public ArrayList<DShapeModel> listeners;
	public int id;
	
	public DShapeModel() 
	{
			
	}
	
	/**
	 * creates a new DShapeModel with given parameters
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public DShapeModel(int x, int y,int width, int height)
	{
		listeners = new ArrayList<>();
		this.x = x;
		this.y = y;
	    this.width = width;
	    this.height = height;
	    this.color = Color.GRAY;
	    this.id = 1;
	}
	
	/**
	 * returns the listeners
	 * @return
	 */
	public ArrayList<DShapeModel> getListeners() 
	{
		return listeners;
	}

	/**
	 * sets the listeners
	 * @param listeners
	 */
	public void setListeners(ArrayList<DShapeModel> listeners) 
	{
		this.listeners = listeners;
	}

	/**
	 * gets the x, y, width, height of rectangle
	 * @return
	 */
	public Rectangle getBounds()
	{
		return new Rectangle(this.x,this.y,this.width,this.height);
	}
	
	/**
	 * checks to see if the model are equal
	 * @param d
	 * @return
	 */
	public boolean equals(DShapeModel d)
	{
		if (d.getX() == this.x && d.getY() == this.y && d.getWidth() == this.width && d.getHeight() == this.height && d.getColor().equals(this.color) && d.getId() == this.id)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * sets the x, y , h, w 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public void setBounds(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}
	
	/**
	 * returns the x value
	 * @return
	 */
	public int getX() 
	{
		return x;
	}

	/**
	 * sets the x value with a given int
	 * @param x
	 */
	public void setX(int x) 
	{
		this.x = x;
	}

	/**
	 * gets the y value with a given int
	 * @return
	 */
	public int getY() 
	{
		return y;
	}

	/**
	 * sets the y value with a given int
	 * @param y
	 */
	public void setY(int y) 
	{
		this.y = y;
	}

	/**
	 * gets the width value
	 * @return
	 */
	public int getWidth() 
	{
		return width;
	}

	/**
	 * sets the width value
	 * @param width
	 */
	public void setWidth(int width) 
	{
		this.width = width;
	}

	/**
	 * returns the height
	 * @return
	 */
	public int getHeight() 
	{
		return height;
	}

	/**
	 * sets the height
	 * @param height
	 */
	public void setHeight(int height) 
	{
		this.height = height;
	}

	/**
	 * gets the color
	 * @return
	 */
	public Color getColor() 
	{
		return color;
	}

	/**
	 * sets the color
	 * @param c
	 */
	public void setColor(Color c) 
	{
		this.color = c;
	}

	/**
	 * returns the id
	 * @return
	 */
	public int getId() 
	{
		return id;
	}

	/**
	 * sets the id
	 * @param id
	 */
	public void setId(int id) 
	{
		this.id = id;
	}
	
	
}
