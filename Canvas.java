import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Canvas extends JPanel {
	DShape selected;
	public static ArrayList<DShape> shapesList;
	
	public Canvas()
	{
		super();
		this.setPreferredSize(new Dimension(500,500));
		this.setOpaque(true);
	    this.setBackground(Color.WHITE);
	    shapesList = new ArrayList<>();
	    addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            super.mouseClicked(e);
	            System.out.println(e.getPoint());
	            for (DShape d : shapesList)
	            {
	            	if (d.getName().equals("DRect"))
	    			{
	    				DRect rect = (DRect) d;
	    				if (rect.contains(e.getPoint()))
	    				{	
	    					selected = rect;
	    					System.out.println(d.getName());
	    					break;
	    				}
	    			}
	            	else if (d.getName().equals("DOval"))
	    			{
	    				DOval oval = (DOval) d;
	    				if (oval.contains(e.getPoint()))
	    				{
	    					selected = oval;
	    					System.out.println(d.getName());
	    					break;
	    				}
	    			}
	            }
	        }
	    });
	   
	}
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for (DShape d : shapesList)
		{
			if (shapesList.contains(selected))
			{
				shapesList.get(shapesList.indexOf(selected));
			}
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
		if (shapesList.contains(selected))
		{
			DShape shape = shapesList.get(shapesList.indexOf(selected));
			if (shape.getName().equals("DRect"))
			{
				DRect rect = (DRect) shape;
				rect.draw(g);
				g.setColor(Color.CYAN);
				g.drawRect(rect.info.getX(),rect.info.getY(),rect.info.getWidth(),rect.info.getHeight());
			}
			else if (shape.getName().equals("DOval"))
			{
				DOval oval = (DOval) shape;
				g.setColor(Color.CYAN);
				g.drawRect(oval.info.getX(),oval.info.getY(),oval.info.getWidth(),oval.info.getHeight());
			}
		}
		repaint();
		
	}
	
	public static void addShape(DShapeModel shape)
	{
		if (shape instanceof DRectModel)
		{
			DRect rect = new DRect();
			rect.info = (DRectModel) shape;
			shapesList.add(rect);
		}
		if (shape instanceof DOvalModel)
		{
			DOval oval = new DOval();
			oval.info = (DOvalModel) shape;
			shapesList.add(oval);
		}
	}
}
