import java.awt.Graphics;
import java.awt.Rectangle;

public class DOval extends DShape {
	public Rectangle oval;
	public DOvalModel info;
	
	public DOval()
	{
		oval = new Rectangle(0,0,0,0);
	}
	
	public void draw(Graphics g)
	{	
		g.setColor(info.getC());
        g.fillOval(info.getX(),info.getY(),info.getWidth(),info.getHeight());
	}
	
	public String getName()
	{
		return "DOval";
	}
	
}
