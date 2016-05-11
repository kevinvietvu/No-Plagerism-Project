import java.awt.*;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;

public class DText extends DShape {
	static GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    static Font[] fonts = ge.getAllFonts();
    String text = ControlPanel.textDisplay.getText();
    
	public DText()
	{
		super();
	}
	
	//RenderingHints makes the text smoother and less edgy I think...
	public void draw(Graphics g)
	{	
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

	    rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);

	    ((Graphics2D) g).setRenderingHints(rh);
		
	    int fontSize = (info.getHeight() + info.getWidth()) / 2;
	           
	    g.setFont(new Font("Purisa", Font.PLAIN, fontSize));
	           
	    g.setColor(info.getC());
	    
	    g.drawString(text, info.getX(), info.getY());

	}
	
	public String getName()
	{
		return "DText";
	}
	
}
