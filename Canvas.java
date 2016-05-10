import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.xml.bind.Marshaller.Listener;

public class Canvas extends JPanel {
	static DShape selected;
	static DShapeModel previousSelected;
	static DShapeModel selectedModel;
	public static ArrayList<DShape> shapesList;
	
	public Canvas()
	{
		super();
		this.setPreferredSize(new Dimension(500,500));
		this.setOpaque(true);
	    this.setBackground(Color.WHITE);
	    shapesList = new ArrayList<>();
	    //Adds clicking on shapes to select it.
	    addMouseListener(new MouseAdapter() {
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
	    					selectedModel = rect.info;
	    					System.out.println(selected.getName());
	    					break;
	    				}
	    				else {
	    					selected = null;
	    				}
	    			}
	            	else if (d.getName().equals("DOval"))
	    			{
	    				DOval oval = (DOval) d;
	    				if (oval.contains(e.getPoint()))
	    				{
	    					selected = oval;
	    					selectedModel = oval.info;
	    					System.out.println(selected.getName());
	    					break;
	    				}
	    				else {
	    					selected = null;
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
			if (selected != null)
			{
				DShape shape = shapesList.get(shapesList.indexOf(selected));
				if (shape.getName().equals("DRect"))
				{
					DRect rect = (DRect) shape;
					g.setColor(Color.CYAN);
					g.drawRect(rect.info.getX(),rect.info.getY(),rect.info.getWidth(),rect.info.getHeight());
				}
				else if (shape.getName().equals("DOval"))
				{
					DOval oval = (DOval) shape;
					g.setColor(Color.CYAN);
					g.drawOval(oval.info.getX(),oval.info.getY(),oval.info.getWidth(),oval.info.getHeight());
				}			
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
			DShapeModel.listeners.add(shape);
		}
		if (shape instanceof DOvalModel)
		{
			DOval oval = new DOval();
			oval.info = (DOvalModel) shape;
			shapesList.add(oval);
			DShapeModel.listeners.add(shape);
		}
		
	}
}
