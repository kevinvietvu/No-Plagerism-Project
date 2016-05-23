import java.awt.*;

public class DText extends DShape 
{
    private Font selectedFont;
    private int fontAscent;
    
    /**
     * calls the superclass constructor
     */
	public DText()
	{
		super();
	}

	/**
	 * computes the font size based on the bounds of
	 * the rectangle
	 * @param g
	 * @return
	 */
	public Font computeFont(Graphics g)
	{
		double fontSize = 1.0;
		double previousFontSize = 1.0;
		selectedFont = new Font(((DTextModel) info).getFontType(), Font.PLAIN, (int) fontSize);
		FontMetrics metrics = new FontMetrics(selectedFont) {};
		while (metrics.getHeight() <= info.getHeight() )
		{	
			previousFontSize = fontSize;
			fontSize = (fontSize * 1.10) + 1;
			Font biggerFont = selectedFont.deriveFont((float)fontSize);
			metrics = new FontMetrics(biggerFont) {};
			fontAscent = metrics.getAscent();
		}
		return selectedFont.deriveFont((float)previousFontSize);
	} 
	
	/**
	 * draws the text in a box
	 */
	public void draw(Graphics g)
	{	
		//Rendering makes the font edges look less fuzzy or edgy
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

	    rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);

	    ((Graphics2D) g).setRenderingHints(rh);

	    Font f = computeFont(g);
		
	    g.setFont(f);
	           
	    g.setColor(info.getColor());
	    
	    Shape clip = g.getClip();
	    
	    g.drawRect(info.getX() , info.getY(), info.getWidth(),info.getHeight());
	    
	    //Clips the width of the text within the box
	    g.setClip(clip.getBounds().createIntersection(getBounds()));
	    
	    g.drawString(((DTextModel) info).getText(), info.getX() , info.getY() + info.getHeight() - fontAscent / 5 );
	    
	    //Restore old clip
	    g.setClip(clip);

	} 
	   
	/**
	 * gets the name
	 */
	public String getName()
	{
		return "DText";
	}
	
}
