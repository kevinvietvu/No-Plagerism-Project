import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Canvas extends JPanel implements MouseMotionListener, MouseListener {
	static double previousFontSize;
	static DShape selected;
	static DShapeModel selectedModel;
	public static ArrayList<DShape> shapesList;
	private ArrayList<Point> knobs;
	int offsetX, offsetY;
	public Point upperLeft, bottomLeft, upperRight, bottomRight;
	public Rectangle ul, bl, ur,br;
	private Point movingPoint;
	private Point anchorPoint;
	DRect rect;
	DOval oval;
	DText text;
	DLine line;
	boolean dragging, draggingUl, draggingBl, draggingUr, draggingBr;
	public JButton saveImage;
	public JPanel save;
	public Point p;
	private static final int KNOB_SIZE = 9;
	private static final int OFFSET = 3;
	public Canvas()
	{
		
		super();
		saveImage = new JButton("Save as PNG");
		save = new JPanel();
		save.add(saveImage);
		this.setPreferredSize(new Dimension(500,500));
		this.setLayout(new BorderLayout());
		this.add(save, BorderLayout.BEFORE_FIRST_LINE);
		addButton();
		this.setOpaque(true);
	    this.setBackground(Color.WHITE);
	    shapesList = new ArrayList<>();
	    knobs = new ArrayList<>();
	    //Adds clicking on shapes to select it.
	    addMouseMotionListener(this);
	    addMouseListener(this);
	   
	}
	
	public ArrayList<Point> getKnobs()
	{
		return knobs;
	}

	public void setKnobs()
	{
		getKnobs().get(0).setLocation(new Point(selected.getBounds().x-OFFSET, selected.getBounds().y-OFFSET));
		getKnobs().get(1).setLocation(new Point(selected.getBounds().x-OFFSET, (int)selected.getBounds().getMaxY()-OFFSET));
		getKnobs().get(2).setLocation(new Point((int)selected.getBounds().getMaxX()-OFFSET, selected.getBounds().y-OFFSET));
		getKnobs().get(3).setLocation(new Point((int)selected.getBounds().getMaxX()-OFFSET, (int)selected.getBounds().getMaxY()-OFFSET));
		
	}

	public void setAnchorPoint(Point p)
	{
		this.anchorPoint = p;
	}
	
	public void setMovingPoint(Point p)
	{
		this.movingPoint = p;
	}

	private void defineKnobs()
	{
		if(selected.equals("DLine"))
		{
			upperLeft = new Point(selected.getBounds().x, selected.getBounds().y);
			bottomRight = new Point((int)selected.getBounds().getMaxX(), (int)selected.getBounds().getMaxY());
		}
		
		upperLeft = new Point(selected.getBounds().x, selected.getBounds().y);
		bottomLeft= new Point(selected.getBounds().x, (int)selected.getBounds().getMaxY());
		upperRight = new Point((int)selected.getBounds().getMaxX(), selected.getBounds().y);
		bottomRight = new Point((int)selected.getBounds().getMaxX(), (int)selected.getBounds().getMaxY());
		
	}

	private void createKnobs()
	{
		if(selected.equals("DLine"))
		{
			ul = new Rectangle(upperLeft.x , upperLeft.y, KNOB_SIZE , KNOB_SIZE);
			br = new Rectangle(bottomRight.x, bottomRight.y, KNOB_SIZE ,KNOB_SIZE);
		}
		ul = new Rectangle(upperLeft.x -OFFSET , upperLeft.y - OFFSET, KNOB_SIZE , KNOB_SIZE);
		bl = new Rectangle(bottomLeft.x-OFFSET, bottomLeft.y -OFFSET, KNOB_SIZE, KNOB_SIZE);
		ur = new Rectangle(upperRight.x -OFFSET, upperRight.y-OFFSET, KNOB_SIZE, KNOB_SIZE);
		br = new Rectangle(bottomRight.x -OFFSET, bottomRight.y -OFFSET, KNOB_SIZE ,KNOB_SIZE);
	
	}

	private void drawKnobs(Graphics2D g2)
	{
		if(selected.getName().equals("DLine"))
		{
			g2.setColor(Color.BLACK);
			g2.fillRect(upperLeft.x , upperLeft.y , KNOB_SIZE, KNOB_SIZE);
			g2.fillRect(bottomRight.x, bottomRight.y, KNOB_SIZE, KNOB_SIZE);
			
		}
		else
		{
		g2.setColor(Color.BLACK);
		g2.fillRect(upperLeft.x-OFFSET, upperLeft.y-OFFSET, KNOB_SIZE, KNOB_SIZE);
		g2.fillRect(bottomLeft.x-OFFSET, bottomLeft.y-OFFSET, KNOB_SIZE, KNOB_SIZE);
		g2.fillRect(upperRight.x-OFFSET, upperRight.y-OFFSET, KNOB_SIZE, KNOB_SIZE);
		g2.fillRect(bottomRight.x-OFFSET, bottomRight.y-OFFSET, KNOB_SIZE, KNOB_SIZE);
		}
	}

	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g2);
		for (ListIterator<DShape> iterator = shapesList.listIterator(shapesList.size()); iterator.hasPrevious();)
		{
			DShape d = (DShape) iterator.previous();
			if (d.getName().equals("DRect"))
			{	
				DRect rect = (DRect) d;
				rect.draw(g2);
			}
			else if (d.getName().equals("DOval"))
			{
				DOval oval = (DOval) d;
				oval.draw(g2);
			}
			else if (d.getName().equals("DLine"))
			{
				DLine line = (DLine) d;
				line.draw(g2);
			}
			else if (d.getName().equals("DText"))
			{
				DText text = (DText) d;
				text.draw(g2);
			}
			if (selected != null)
			{
				if (shapesList.contains(selected))
				{
					
					DShape shape = shapesList.get(shapesList.indexOf(selected));
					if ("DRect".equals(shape.getName()))
					{
						// creates point objects for the knobs
						defineKnobs();
						DRect rect = (DRect) shape;
						
						// creating the knobs as rectangle objects
						createKnobs();
						
						// drawing the knobs
						drawKnobs(g2);
					}
					else if ("DOval".equals(shape.getName()))
					{
						// creates point objects for the knobs
						defineKnobs();
						DOval oval = (DOval) shape;
						
						// creating the knobs as rectangle objects
						createKnobs();
						
						// drawing the knobs
						drawKnobs(g2);
						
						
					}
					else if ("DLine".equals(shape.getName()))
					{
						// creates point objects for the knobs
						defineKnobs();
						DLine line = (DLine) shape;
						//g2.setColor(Color.CYAN);
						//g2.drawRect(line.getBounds().x, line.getBounds().y, line.getBounds().width, line.getBounds().height);
						
						// creating the knobs as rectangle objects
						createKnobs();
						
						// drawing the knobs
						drawKnobs(g2);
					}
					
					else if (shape.getName().equals("DText"))
					{
						// creates point objects for the knobs
						defineKnobs();
						DText text = (DText) shape;
						
						// creating the knobs as rectangle objects
						createKnobs();
						
						// drawing the knobs
						drawKnobs(g2);
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
		if (shapeModel instanceof DLineModel)
		{
			DLine line = new DLine();
			line.info = (DLineModel) shapeModel;
			shapesList.add(line);
			DShapeModel.listeners.add(shapeModel);
		}
		if (shapeModel instanceof DTextModel)
		{
			DText text = new DText();
			text.info = (DTextModel) shapeModel;
			shapesList.add(text);
			DShapeModel.listeners.add(shapeModel);
		} 
	}

	public void saveImage(File file)
	{
		BufferedImage image = (BufferedImage) createImage(this.getWidth(), this.getHeight());
		Graphics g = image.getGraphics();
		paintAll(g);
		g.dispose();
		try{
			ImageIO.write(image, "png", file);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public void addButton()
	{
		saveImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selected != null)
				{
					selected = null;
				}
				String result = JOptionPane.showInputDialog("File Name", null);
				if(result != null) {
					File f = new File(result);
					saveImage(f);
					
				}
			}
		});
		
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		movingPoint = e.getPoint();
		int x = e.getX();
		int y = e.getY();
		if(selected != null)
		{
			if(ul == null || bl == null || ur == null || br == null)
			{
				return;
			}
			
			if(selected.contains(e.getPoint()))
			{
				dragging = true;
				draggingUl = false;
				draggingBl = false;
				draggingUr = false;
				draggingBr = false;
				offsetX = x - selected.info.x;
				offsetY = y - selected.info.y;
				System.out.println("Dragging...");
			}
			
			else if(ul.contains(movingPoint))
			{
				draggingUl = true;
				dragging = false;
				draggingBl = false;
				draggingUr = false;
				draggingBr = false;
				setAnchorPoint(br.getLocation());
				System.out.println("upper left knob has been pressed. anchor point is " + anchorPoint);
			}
			
			else if(bl.contains(movingPoint))
			{
				draggingBl = true;
				dragging = false;
				draggingUl = false;
				draggingUr = false;
				draggingBr = false;
				setAnchorPoint(ur.getLocation());
				System.out.println("bottom left knob has been pressed. anchor point is " + anchorPoint);
			}
			
			else if(ur.contains(movingPoint))
			{
				draggingUr = true;
				dragging = false;
				draggingUl = false;
				draggingBl = false;
				draggingBr = false;
				setAnchorPoint(bl.getLocation());
				System.out.println("upper right knob has been pressed. anchor point is " + anchorPoint);
			}
			
			else if(br.contains(movingPoint))
			{
				draggingBr = true;
				dragging = false;
				draggingUl = false;
				draggingBl = false;
				draggingUr = false;
				setAnchorPoint(ul.getLocation());
				System.out.println("bottom right knob has been pressed. anchor point is " + anchorPoint);
			}
			
		}
		
		
	}

	@Override
	public void mouseDragged(MouseEvent e) 
	{
		p = e.getPoint();
		if(selected == null)
		{
			return;
		}
		
		
		else if(selected.equals(rect))
		{
			resize();
		}
		
		else if(selected.equals(oval))
		{
			resize();
		}
		
		else if(selected.equals(text))
		{
			resize();
		}
		
		else if(selected.equals(line))
		{
			resizeLine();
			
		}
		
		}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		System.out.println(e.getPoint());
         for (DShape d : shapesList)
         {
     		if (d.getName().equals("DRect"))
     	    {
     	    	rect = (DRect) d;
     	    	if (rect.contains(e.getPoint()))
     	    	{	
     	    		
     	    		selected = rect;
     	    		selectedModel = rect.info;
     	    		knobs.add(new Point(selected.getBounds().x-OFFSET, selected.getBounds().y-OFFSET));
     	    		knobs.add(new Point(selected.getBounds().x-OFFSET, (int)selected.getBounds().getMaxY()-OFFSET));
     	    		knobs.add(new Point((int)selected.getBounds().getMaxX()-OFFSET, selected.getBounds().y-OFFSET));
     	    		knobs.add(new Point((int)selected.getBounds().getMaxX()-OFFSET, (int)selected.getBounds().getMaxY()-OFFSET));
     	    		System.out.println(selected.getName());
     	    		break;
     	    	}
     	    	
     	    	else {
     	    		ControlPanel.disableButtons();
     	    		selected = null;
     	    		selectedModel = null;
     	    		getKnobs().clear();
     	    	}
     	   	}
     	    else if (d.getName().equals("DOval"))
     	    {
     	    	oval = (DOval) d;
     	    	if (oval.contains(e.getPoint()))
     	    	{
     	    		selected = oval;
     	    		selectedModel = oval.info;
     	    		knobs.add(new Point(selected.getBounds().x-OFFSET, selected.getBounds().y-OFFSET));
     	    		knobs.add(new Point(selected.getBounds().x-OFFSET, (int)selected.getBounds().getMaxY()-OFFSET));
     	    		knobs.add(new Point((int)selected.getBounds().getMaxX()-OFFSET, selected.getBounds().y-OFFSET));
     	    		knobs.add(new Point((int)selected.getBounds().getMaxX()-OFFSET, (int)selected.getBounds().getMaxY()-OFFSET));
     
     	    		System.out.println(selected.getName());
     	    		System.out.println("bounds for the selected oval " + selected.getBounds());
     	    		System.out.println(getKnobs());
     	    		break;
     	    	}
     	    	else {
     	    		ControlPanel.disableButtons();
     	    		selected = null;
     	    		selectedModel = null;
     	    		getKnobs().clear();
     	    	}	
     	  	}
     	    else if (d.getName().equals("DLine"))
     	    {	  
     	    	
     	    	line = (DLine) d;
     	    	if (line.contains(e.getPoint()))
     	    	{	        
     	    		
     	    		selected = line;
     	    		selectedModel = line.info;
     	    		knobs.add(new Point(selected.getBounds().x-OFFSET, selected.getBounds().y-OFFSET));
     	    		knobs.add(new Point((int)selected.getBounds().getMaxX()-OFFSET, (int)selected.getBounds().getMaxY()-OFFSET));
     	    		System.out.println("Knobs are at: " + getKnobs());
     	    		System.out.println(selected.getName());
     	    		break;
     	    	}
     	    	else {
     	    		ControlPanel.disableButtons();
      	    		selected = null;
      	    		selectedModel = null;
      	    		getKnobs().clear();
      	    	}
     	    	
     	  	}
     	    else if (d.getName().equals("DText"))
     	    {	        
     	    	text = (DText) d;
     	    	if (text.contains(e.getPoint()))
     	    	{	        
     	    		ControlPanel.enableButtons();
     	    		selected = text;
     	    		selectedModel = text.info;
     	    		knobs.add(new Point(selected.getBounds().x-OFFSET, selected.getBounds().y-OFFSET));
     	    		knobs.add(new Point(selected.getBounds().x-OFFSET, (int)selected.getBounds().getMaxY()-OFFSET));
     	    		knobs.add(new Point((int)selected.getBounds().getMaxX()-OFFSET, selected.getBounds().y-OFFSET));
     	    		knobs.add(new Point((int)selected.getBounds().getMaxX()-OFFSET, (int)selected.getBounds().getMaxY()-OFFSET));
     	    		System.out.println(selected.getName());
     	    		break;
     	    	}
     	    	else {
     	    		ControlPanel.disableButtons();
      	    		selected = null;
      	    		selectedModel = null;
      	    		getKnobs().clear();
      	    	}
     	  	}
     	   	
         } 
       
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		movingPoint = null;
		draggingUl = false;
		draggingBl =false;
		draggingUr = false;
		draggingBr = false;
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void resize()
	{
		p = getPoint();
		int dx = p.x - selected.info.getX();
		int dy = p.y - selected.info.getY();
		
		if(dragging == true)
		{
			selected.info.setX(p.x - offsetX);
			selected.info.setY(p.y - offsetY);
		}
		
		else if(draggingUl == true)
		{
			int width = selected.info.getWidth() - dx;
			int height = selected.info.getHeight() - dy;
			
			if(ul.x - anchorPoint.x >= 0)
			{
				draggingUl = false;
				draggingUr = true;
			}
			
			else if(ul.y - anchorPoint.y >= 0)
			{
				draggingUl = false;
				draggingBl = true;
				width = selected.info.getWidth() - dx;
				height = dy;
				selected.info.setBounds(selected.info.getX() + dx, selected.info.getY() , width, height);
			}
			else
			selected.info.setBounds(selected.info.getX() + dx, selected.info.getY() + dy, width, height);
			System.out.println(selected.getBounds());
		
		}
		
		else if(draggingBl == true)
		{
			int width = selected.info.getWidth() - dx;
			int height = dy;
			selected.info.setBounds(selected.info.getX() + dx, selected.info.getY() , width, height);
			
			if(bl.x - anchorPoint.x >= 0)
			{
				System.out.println("working");
				draggingBl = false;
				draggingBr = true;
			}
			
			else if(bl.y - anchorPoint.y <= 0)
			{
				System.out.println("working");
				draggingBl = false;
				draggingUl = true;
				
				int width1 = selected.info.getWidth() - dx;
				int height1 = selected.info.getHeight() - dy;
				selected.info.setBounds(selected.info.getX() + dx, selected.info.getY() + dy, width1, height1);
				System.out.println("is ul dragging?" + draggingUl);
				
			}
				
		
		}
		
		else if(draggingUr == true)
		{
			int width = dx;
			int height = selected.info.getHeight() - dy;
			selected.info.setBounds(selected.info.getX(), selected.info.getY() + dy, width, height);
			System.out.println(selected.getBounds());
			
			if(ur.x - anchorPoint.x <= 0)
			{
				int width1 = selected.info.getWidth() - dx;
				int height1 = selected.info.getHeight() - dy;
				draggingUr = false;
				draggingUl = true;
				selected.info.setBounds(selected.info.getX() + dx, selected.info.getY() + dy, width1, height1);
				System.out.println(" is ur dragging?" + draggingUr);
				System.out.println("is ul dragging?" + draggingUl);
			}
			
			else if(ur.y - anchorPoint.y >= 0)
			{
				draggingUr = false;
				draggingBr = true;
			}
			
		}
		
		else if(draggingBr == true)
		{
			int width = dx;
			int height = dy;
			selected.info.setBounds(selected.info.getX(), selected.info.getY(), width, height);
			
			if(br.x - anchorPoint.x <= 0)
			{
				draggingBr = false;
				draggingBl = true;
			}
			
			else if(br.y - anchorPoint.y <= 0)
			{
				draggingBr = false;
				draggingUr = true;
				width = dx;
				height = selected.info.getHeight() - dy;
				selected.info.setBounds(selected.info.getX(), selected.info.getY() + dy, width, height);
				
			}
		}
		
	}
	
	
	private Point getPoint()
	{
		return this.p;
	}
	
	private void resizeLine()
	{
		p = getPoint();
		int dx = p.x - selected.info.getX();
		int dy = p.y - selected.info.getY();
		
		if(dragging == true)
		{
			selected.info.setX(p.x - offsetX);
			selected.info.setY(p.y - offsetY);
		}
		
		else if(draggingUl == true)
		{
			int width = selected.info.getWidth() - dx;
			int height = selected.info.getHeight() - dy;
			selected.info.setBounds(selected.info.getX() + dx, selected.info.getY() + dy, width, height);
		
		}
		
		else if(draggingBr == true)
		{
			int width = dx;
			int height = dy;
			selected.info.setBounds(selected.info.getX(), selected.info.getY(), width, height);
		}
	}
}
