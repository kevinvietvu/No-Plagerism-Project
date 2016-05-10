import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

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
		for (ListIterator<DShape> iterator = shapesList.listIterator(shapesList.size()); iterator.hasPrevious();)
		{
			DShape d = (DShape) iterator.previous();
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
				if (shapesList.contains(selected))
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
		}
		repaint();
	}
	
	// testing purposes
	public static void printList()
	{
		int count = 1;
		for (DShape d : shapesList)
		{
			System.out.println(count + " " + d.getName());
			count++;
		}
	}
	
	// testing purposes
	public static void printReverse()
	{
		int count = 1;
		for (ListIterator<DShape> iterator = shapesList.listIterator(shapesList.size()); iterator.hasPrevious();)
		{
			System.out.println(count + " " + ((DShape) iterator.previous()).getName());
			count++;
		}
	}
	
	public static void addShape(DShapeModel shapeModel)
	{
		if (shapeModel instanceof DRectModel)
		{
			DRect rect = new DRect();
			rect.info = (DRectModel) shapeModel;
			shapesList.add(rect);
			DShapeModel.listeners.add(shapeModel);
		}
		if (shapeModel instanceof DOvalModel)
		{
			DOval oval = new DOval();
			oval.info = (DOvalModel) shapeModel;
			shapesList.add(oval);
			DShapeModel.listeners.add(shapeModel);
		}
	}
}
