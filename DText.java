import java.awt.*;

import java.awt.geom.Point2D;

public class DText extends DShape {
	static GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    static Font[] fonts = ge.getAllFonts();
    double fontSize = 1.0;
    String text = ControlPanel.textDisplay.getText();
    String fontType =  ((Font) ControlPanel.fonts.getSelectedItem()).getName();
    Font selectedFont;
    FontMetrics metrics;
    int fontHeight;
    int fontDescent;
    int fontAscend;
    
	public DText()
	{
		super();
	}
	
	public Font computeFont(Graphics g)
	{
		while (info.getHeight() > fontHeight)
		{	
			fontSize = (fontSize * 1.10) + 1;
			selectedFont = new Font(fontType, Font.PLAIN, (int) fontSize);
			metrics = g.getFontMetrics(selectedFont);
			fontHeight = metrics.getHeight();
			fontAscend = metrics.getAscent();
			fontDescent = metrics.getDescent();
			fontSize = (fontSize * 1.10) + 1;
		}
		return selectedFont;
	}
	
	//RenderingHints makes the text smoother and less edgy I think...
	public void draw(Graphics g)
	{	
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

	    rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);

	    ((Graphics2D) g).setRenderingHints(rh);

	    selectedFont = computeFont(g);
		
	    g.setFont(selectedFont);
	           
	    g.setColor(info.getC());
	    
	    //place holder box drawn to see if the text fits inside the box
	    g.drawRect(info.getX(), info.getY(),info.getWidth(),info.getHeight());
	    
	    Shape clip = g.getClip();
	    
	    //Clips the width of the text within the box
	    g.setClip(clip.getBounds().createIntersection(getBounds()));
	    
	    g.drawString(text, info.getX() , info.getY() + info.getHeight() - fontDescent );
	    
	    //Restore old clip
	    g.setClip(clip);

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
		return "DText";
	}
	
}
