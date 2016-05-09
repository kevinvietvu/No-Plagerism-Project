import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Canvas extends JPanel {
	public static ArrayList<DShape> shapesList;
	
	public Canvas()
	{
		super();
		this.setPreferredSize(new Dimension(500,500));
		this.setOpaque(true);
	    this.setBackground(Color.WHITE);
	    shapesList = new ArrayList<>();
	   
	}
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for (DShape d : shapesList)
		{
			if (d.getName().equals("DRect"))
			{
				DRect rect = (DRect) d;
				rect.draw(g);
			}
			if (d.getName().equals("DOval"))
			{
				DOval oval = (DOval) d;
				oval.draw(g);
			}
		}
		repaint();
		
	}
	
	public static void addShape(DShapeModel shape)
	{
		//Not sure what she wants for this 
	}
}
