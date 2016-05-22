import java.awt.*;

public class DText extends DShape {
    double fontSize = 1.0;
    Font selectedFont;
    FontMetrics metrics;
    int fontHeight;
    int fontDescent;
    String textLine;
    
	public DText()
	{
		super();
	}
	
	public Font computeFont(Graphics g)
	{
		
		double fontSize = 1.0;
		selectedFont = new Font(((DTextModel) info).getFontType(), Font.PLAIN, (int) fontSize);
		FontMetrics metrics = new FontMetrics(selectedFont) {};
		while (metrics.getHeight() <= info.getHeight() )
		{	
			fontSize = (fontSize * 1.10) + 1;
			Font biggerFont = selectedFont.deriveFont((float)fontSize);
			metrics = new FontMetrics(biggerFont) {};
		}
		return selectedFont.deriveFont((float)fontSize);
	}
	
	//RenderingHints makes the text smoother and less edgy I think...
	public void draw(Graphics g)
	{	
		
		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

	    rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);

	    ((Graphics2D) g).setRenderingHints(rh);

	    Font f = computeFont(g);
	    
	    //selectedFont = new Font(((DTextModel) info).getFontType(), Font.PLAIN, (int) fontSize);
		
	    g.setFont(f);
	           
	    g.setColor(info.getC());
	    
	    //place holder box drawn to see if the text fits inside the box
	    g.drawRect(info.getX(), info.getY(),info.getWidth(),info.getHeight());
	    
	    Shape clip = g.getClip();
	    
	    //Clips the width of the text within the box
	    g.setClip(clip.getBounds().createIntersection(getBounds()));
	    
	    g.drawString(((DTextModel) info).getText(), info.getX() , info.getY() + info.getHeight());
	    
	    //Restore old clip
	    g.setClip(clip);
	    
	}
	
	public String getName()
	{
		return "DText";
	}
	
	public String getFontType()
	{
		DTextModel info = new DTextModel();
		return info.getFontType();
	}
	
}
