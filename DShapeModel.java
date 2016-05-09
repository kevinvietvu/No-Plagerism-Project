import java.awt.*;
import java.util.ArrayList;

public class DShapeModel implements ModelListener {
	public int x;
	public int y;
	public int width;
	public int height;
	public Color c;
	public ArrayList<ModelListener> listeners;
	
	DShapeModel()
	{
		x = 10;
		y = 10;
		width = 10;
		height = 10;
		c = Color.GRAY;
	}
	
	DShapeModel(int x, int y,int w, int h)
	{
		this.x = x;
		this.y = y;
	    width = w;
	    height = h;
	    this.c = Color.GRAY;
	}
	
	@Override
	public void modelChanged(DShapeModel model) {
		// TODO Auto-generated method stub
		
	}
	
	public void removeListener(ModelListener ml)
	{
		if (listeners.contains(ml))
		{
			listeners.remove(ml);
		}
	}
	
	public void addListener(ModelListener ml)
	{
		listeners.add(ml);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}
	
}
