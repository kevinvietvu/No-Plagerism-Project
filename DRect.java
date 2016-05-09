import java.awt.*;

public class DRect extends DShape {
	public Rectangle rect;
	public DRectModel info;
	
	public DRect()
	{
		rect = new Rectangle(0,0,0,0);
	}
	
	public void draw(Graphics g)
	{	
		g.setColor(info.getC());
        g.fillRect(info.getX(),info.getY(),info.getWidth(),info.getHeight());
	}
	
	public String getName()
	{
		return "DRect";
	}
	
}	
