import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Canvas extends JPanel  implements MouseMotionListener, MouseListener 
{
	public Whiteboard board;
	public ControlPanel controls;
	public double previousFontSize;
	public DShape selected;
	public DShapeModel selectedModel;
	private ArrayList<DShape> shapesList;
	private ArrayList<Point> knobs;
	int offsetX, offsetY;
	public Point upperLeft, bottomLeft, upperRight, bottomRight;
	public Rectangle ul, bl, ur,br;
	private Point movingPoint;
	private Point anchorPoint;
	boolean dragging, draggingUl, draggingBl, draggingUr, draggingBr;
	public Point p;
	private static final int KNOB_SIZE = 9;
	private static final int OFFSET = 3;
	private TableModel tableModel;
	
	public Canvas(Whiteboard board)
	{
		super();
		this.setPreferredSize(new Dimension(500,500));
		this.setLayout(new BorderLayout());
		this.setOpaque(true);
	    this.setBackground(Color.WHITE);
	    shapesList = new ArrayList<>();
	    knobs = new ArrayList<>();
	    addMouseMotionListener(this);
	    addMouseListener(this);
		tableModel = new TableModel();
		tableModel.setCanvas(this);
	}
	
	/**
	 * sets a unique whiteboard to canvas
	 * @param board
	 */
	public void setWhiteboard(Whiteboard board) 
	{
		this.board = board;
	}
	
	/**
	 * sets the a unique control panel to canvas
	 * @param controls
	 */
	public void setControlPanel(ControlPanel controls)
	{
		this.controls = controls; 
	}
	
	/**
	 * gets the knobs of the currently selected shape
	 * @return
	 */
	public ArrayList<Point> getKnobs()
	{
		return knobs;
	}

	/**
	 * returns a list of what has been added to the canvas
	 * @return
	 */
	public ArrayList<DShape> getShapesList() 
	{
		return shapesList;
	}
	
	/**
	 * sets the anchor point
	 * @param p
	 */
	public void setAnchorPoint(Point p)
	{
		this.anchorPoint = p;
	}
	
	/**
	 * sets the moving point
	 * @param p
	 */
	public void setMovingPoint(Point p)
	{
		this.movingPoint = p;
	}

	/**
	 * defines the knobs at the rectangle corners
	 */
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

	/**
	 * creates the rectangles that will serve as knobs 
	 */
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

	/**
	 * private method to draw the knobs at four corners of the bounds
	 * @param g2
	 */
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
	 
	/*
	 * gets the point that has been pressed
	 */
	private Point getPoint()
	{
		return this.p;
	}

	/**
	 * paints all the graphics of the shape with knobs
	 */
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g2);
		for (int i = 0 ; i < shapesList.size(); i++)
		{
			DShape d = shapesList.get(i);
			d.draw(g2);
			if (selected != null)
			{
				if (shapesList.contains(selected))
				{
					
					DShape shape = shapesList.get(shapesList.indexOf(selected));
					if ("DRect".equals(shape.getName()))
					{
						// creates point objects for the knobs
						defineKnobs();
						
						// creating the knobs as rectangle objects
						createKnobs();
						
						// drawing the knobs
						drawKnobs(g2);
					}
					else if ("DOval".equals(shape.getName()))
					{
						// creates point objects for the knobs
						defineKnobs();
						
						// creating the knobs as rectangle objects
						createKnobs();
						
						// drawing the knobs
						drawKnobs(g2);
						
						
					}
					else if ("DLine".equals(shape.getName()))
					{
						// creates point objects for the knobs
						defineKnobs();
						
						// creating the knobs as rectangle objects
						createKnobs();
						
						// drawing the knobs
						drawKnobs(g2);
					}
					
					else if (shape.getName().equals("DText"))
					{
						// creates point objects for the knobs
						defineKnobs();

						if (!(controls.getStatus().getText().equals("Client mode")))
						{
							((DTextModel) selectedModel).setFontType(((Font) controls.getFonts().getSelectedItem()).getName());
							((DTextModel) selectedModel).setText(controls.getTextDisplay().getText());		
							String xmlModel = WhiteBoardServer.ObjectToXML(selectedModel);
							WhiteBoardServer.send("change", xmlModel );
						}
						
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
	
	/**
	 * adds shape to the canvas
	 * @param shapeModel
	 */
	public void addShape(DShapeModel shapeModel)
	{
		if (shapeModel instanceof DRectModel)
		{
			DRect rect = new DRect();
			rect.info = (DRectModel) shapeModel;
			shapesList.add(rect);
			shapeModel.getListeners().add(shapeModel);
			tableModel.fireTableDataChanged();
		}
		else if (shapeModel instanceof DOvalModel)
		{
			DOval oval = new DOval();
			oval.info = (DOvalModel) shapeModel;
			shapesList.add(oval);
			shapeModel.getListeners().add(shapeModel);
			tableModel.fireTableDataChanged();
		}
		else if (shapeModel instanceof DLineModel)
		{
			DLine line = new DLine();
			line.info = (DLineModel) shapeModel;
			shapesList.add(line);
			shapeModel.getListeners().add(shapeModel);
			tableModel.fireTableDataChanged();
		}
		else if (shapeModel instanceof DTextModel)
		{
			DText textLine = new DText();
			textLine.info = (DTextModel) shapeModel;
			shapesList.add(textLine);
			shapeModel.getListeners().add(shapeModel);
			tableModel.fireTableDataChanged();
		} 
	}
	
	/**
	 * saves the canvas to a png file
	 * @param file
	 */
	public void saveImage(File file)
	{
		BufferedImage image = (BufferedImage) createImage(this.getWidth(), this.getHeight());
		Graphics g = image.getGraphics();
		paintAll(g);
		g.dispose();
		try {
			ImageIO.write(image, "png", file);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}  
	
	/**
	 * checks if the mouse has clicked a knob or the bounds
	 */
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
				offsetX = x - selected.info.getX();
				offsetY = y - selected.info.getY();

			}
			
			else if(ul.contains(movingPoint))
			{
				draggingUl = true;
				dragging = false;
				draggingBl = false;
				draggingUr = false;
				draggingBr = false;
				setAnchorPoint(br.getLocation());

			}
			
			else if(bl.contains(movingPoint))
			{
				draggingBl = true;
				dragging = false;
				draggingUl = false;
				draggingUr = false;
				draggingBr = false;
				setAnchorPoint(ur.getLocation());

			}
			
			else if(ur.contains(movingPoint))
			{
				draggingUr = true;
				dragging = false;
				draggingUl = false;
				draggingBl = false;
				draggingBr = false;
				setAnchorPoint(bl.getLocation());

			}
			
			else if(br.contains(movingPoint))
			{
				draggingBr = true;
				dragging = false;
				draggingUl = false;
				draggingBl = false;
				draggingUr = false;
				setAnchorPoint(ul.getLocation());

			}	
		}	
	}
	
	@Override
	/**
	 * Checks if the selected shape should move or resize
	 */
	public void mouseDragged(MouseEvent e) 
	{
		p = e.getPoint();
		if(selected == null || controls.getStatus().getText().equals("Client mode"))
		{
			return;
		}
		
		else if("DRect".equals(selected.getName()))
		{
			resize();
		}
		
		else if("DOval".equals(selected.getName()))
		{
			resize();
		}
		
		else if("DText".equals(selected.getName()))
		{
			resize();
		}
		
		else if("DLine".equals(selected.getName()))
		{
			resizeLine();
			
		}
		
	}
	
	/**
	 * when user clicks on shape knobs are added to list
	 */
	public void mouseClicked(MouseEvent e) 
	{

         for (DShape d : shapesList)
         {
     		if (d.getName().equals("DRect"))
     	    {
     	    	DRect rect = (DRect) d;
     	    	if (rect.contains(e.getPoint()))
     	    	{	
     	    		
     	    		selected = rect;
     	    		selectedModel = rect.info;
     	    		knobs.add(new Point(selected.getBounds().x-OFFSET, selected.getBounds().y-OFFSET));
     	    		knobs.add(new Point(selected.getBounds().x-OFFSET, (int)selected.getBounds().getMaxY()-OFFSET));
     	    		knobs.add(new Point((int)selected.getBounds().getMaxX()-OFFSET, selected.getBounds().y-OFFSET));
     	    		knobs.add(new Point((int)selected.getBounds().getMaxX()-OFFSET, (int)selected.getBounds().getMaxY()-OFFSET));

     	    		break;
     	    	}
     	    	
     	    	else {
     	    		controls.disableButtons();
     	    		selected = null;
     	    		selectedModel = null;
     	    		getKnobs().clear();
     	    	}
     	   	}
     	    else if (d.getName().equals("DOval"))
     	    {
     	    	DOval oval = (DOval) d;
     	    	if (oval.contains(e.getPoint()))
     	    	{
     	    		selected = oval;
     	    		selectedModel = oval.info;
     	    		knobs.add(new Point(selected.getBounds().x-OFFSET, selected.getBounds().y-OFFSET));
     	    		knobs.add(new Point(selected.getBounds().x-OFFSET, (int)selected.getBounds().getMaxY()-OFFSET));
     	    		knobs.add(new Point((int)selected.getBounds().getMaxX()-OFFSET, selected.getBounds().y-OFFSET));
     	    		knobs.add(new Point((int)selected.getBounds().getMaxX()-OFFSET, (int)selected.getBounds().getMaxY()-OFFSET));
     
     	    		break;
     	    	}
     	    	else 
     	    	{
     	    		controls.disableButtons();
     	    		selected = null;
     	    		selectedModel = null;
     	    		getKnobs().clear();
     	    	}	
     	  	}
     	    else if (d.getName().equals("DLine"))
     	    {	  
     	    	
     	    	DLine line = (DLine) d;
     	    	if (line.contains(e.getPoint()))
     	    	{	        
     	    		
     	    		selected = line;
     	    		selectedModel = line.info;
     	    		knobs.add(new Point(selected.getBounds().x-OFFSET, selected.getBounds().y-OFFSET));
     	    		knobs.add(new Point((int)selected.getBounds().getMaxX()-OFFSET, (int)selected.getBounds().getMaxY()-OFFSET));

     	    		break;
     	    	}
     	    	else 
     	    	{
     	    		controls.disableButtons();
      	    		selected = null;
      	    		selectedModel = null;
      	    		getKnobs().clear();
      	    	}
     	    	
     	  	}
     	    else if (d.getName().equals("DText"))
     	    {	        
     	    	DText text = (DText) d;
     	    	if (text.contains(e.getPoint()))
     	    	{	        
     	    		controls.enableButtons();
     	    		selected = text;
     	    		selectedModel = text.info;
     	    		knobs.add(new Point(selected.getBounds().x-OFFSET, selected.getBounds().y-OFFSET));
     	    		knobs.add(new Point(selected.getBounds().x-OFFSET, (int)selected.getBounds().getMaxY()-OFFSET));
     	    		knobs.add(new Point((int)selected.getBounds().getMaxX()-OFFSET, selected.getBounds().y-OFFSET));

     	    		break;
     	    	}
     	    	else 
     	    	{
     	    		controls.disableButtons();
      	    		selected = null;
      	    		selectedModel = null;
      	    		getKnobs().clear();
      	    	}
     	  	}
        } 
	}
	
	@Override
	/**
	 * when the mouse has been released the boolean values 
	 * will reset
	 */
	public void mouseReleased(MouseEvent e) {
		movingPoint = null;
		draggingUl = false;
		draggingBl =false;
		draggingUr = false;
		draggingBr = false;
		
	}
	

	/**
	 * private method that calculates the bounds
	 * of the selected to shape to resize or move
	 */
	private void resize()
	{
		String xmlModel = "";
		p = getPoint();
		int dx = p.x - selected.info.getX();
		int dy = p.y - selected.info.getY();
		
		if(dragging == true)
		{
			selected.info.setX(p.x - offsetX);
			selected.info.setY(p.y - offsetY);
			xmlModel = WhiteBoardServer.ObjectToXML(selectedModel);
			WhiteBoardServer.send("change", xmlModel);
			tableModel.fireTableDataChanged();
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
				xmlModel = WhiteBoardServer.ObjectToXML(selectedModel);
				WhiteBoardServer.send("change", xmlModel);
				tableModel.fireTableDataChanged();
			}
			else
			selected.info.setBounds(selected.info.getX() + dx, selected.info.getY() + dy, width, height);
			xmlModel = WhiteBoardServer.ObjectToXML(selectedModel);
			WhiteBoardServer.send("change", xmlModel);
			tableModel.fireTableDataChanged();
		}
		
		else if(draggingBl == true)
		{
			int width = selected.info.getWidth() - dx;
			int height = dy;
			selected.info.setBounds(selected.info.getX() + dx, selected.info.getY() , width, height);
			xmlModel = WhiteBoardServer.ObjectToXML(selectedModel);
			WhiteBoardServer.send("change", xmlModel);
			tableModel.fireTableDataChanged();
			
			if(bl.x - anchorPoint.x >= 0)
			{

				draggingBl = false;
				draggingBr = true;
			}
			
			else if(bl.y - anchorPoint.y <= 0)
			{

				draggingBl = false;
				draggingUl = true;
				
				int width1 = selected.info.getWidth() - dx;
				int height1 = selected.info.getHeight() - dy;
				selected.info.setBounds(selected.info.getX() + dx, selected.info.getY() + dy, width1, height1);
				xmlModel = WhiteBoardServer.ObjectToXML(selectedModel);
				WhiteBoardServer.send("change", xmlModel);
				tableModel.fireTableDataChanged();
			}
					
		}
		
		else if(draggingUr == true)
		{
			int width = dx;
			int height = selected.info.getHeight() - dy;
			selected.info.setBounds(selected.info.getX(), selected.info.getY() + dy, width, height);
			xmlModel = WhiteBoardServer.ObjectToXML(selectedModel);
			WhiteBoardServer.send("change", xmlModel);
			tableModel.fireTableDataChanged();
			
			if(ur.x - anchorPoint.x <= 0)
			{
				int width1 = selected.info.getWidth() - dx;
				int height1 = selected.info.getHeight() - dy;
				draggingUr = false;
				draggingUl = true;
				selected.info.setBounds(selected.info.getX() + dx, selected.info.getY() + dy, width1, height1);
				xmlModel = WhiteBoardServer.ObjectToXML(selectedModel);
				xmlModel = WhiteBoardServer.ObjectToXML(selectedModel);
				WhiteBoardServer.send("change", xmlModel);
				tableModel.fireTableDataChanged();
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
			xmlModel = WhiteBoardServer.ObjectToXML(selectedModel);
			WhiteBoardServer.send("change", xmlModel);
			tableModel.fireTableDataChanged();
			
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
				xmlModel = WhiteBoardServer.ObjectToXML(selectedModel);
				WhiteBoardServer.send("change", xmlModel);
				tableModel.fireTableDataChanged();
				
			}
		}
	}
	
	/**
	 * individual method to resize the line
	 * because line has only two knobs.
	 */
	private void resizeLine()
	{
		p = getPoint();
		int dx = p.x - selected.info.getX();
		int dy = p.y - selected.info.getY();
		
		if(dragging == true)
		{
			selected.info.setX(p.x - offsetX);
			selected.info.setY(p.y - offsetY);
			String xmlModel = WhiteBoardServer.ObjectToXML(selectedModel);
			xmlModel = WhiteBoardServer.ObjectToXML(selectedModel);
			WhiteBoardServer.send("change", xmlModel);
			tableModel.fireTableDataChanged();
		}
		
		else if(draggingUl == true)
		{
			int width = selected.info.getWidth() - dx;
			int height = selected.info.getHeight() - dy;
			selected.info.setBounds(selected.info.getX() + dx, selected.info.getY() + dy, width, height);
			String xmlModel = WhiteBoardServer.ObjectToXML(selectedModel);
			xmlModel = WhiteBoardServer.ObjectToXML(selectedModel);
			WhiteBoardServer.send("change", xmlModel);
			tableModel.fireTableDataChanged();
		
		}
		
		else if(draggingBr == true)
		{
			int width = dx;
			int height = dy;
			selected.info.setBounds(selected.info.getX(), selected.info.getY(), width, height);
			String xmlModel = WhiteBoardServer.ObjectToXML(selectedModel);
			xmlModel = WhiteBoardServer.ObjectToXML(selectedModel);
			WhiteBoardServer.send("change", xmlModel);
			tableModel.fireTableDataChanged();
		}
	}

	/**
	 * clears the server and clients
	 */
    public void clear()
    { 
    	shapesList.clear();
        selected = null;
        selectedModel = null;
        tableModel.fireTableDataChanged();
    	repaint();  
    }
	
    /**
     * the selected shape moves to the front of the canvas
     * @param model
     */
	public void moveFront(DShapeModel model)
	{
		DShape shape = null;
		if (!getShapesList().isEmpty())
  	    {
			for (DShape d : getShapesList())
  		    {
  			    if (d.info.getId() == model.getId())
  			    {
  			     	  shape = d;
  			    }
  		    }
  	    }
		if (shape != null)
		{
			getShapesList().remove(getShapesList().indexOf(shape));
		    getShapesList().add(shape);
		}
	}
	
	/**
	 * the selected shape moves to the back of the canvas
	 * @param model
	 */
	public void moveBack(DShapeModel model)
	{
		DShape shape = null;
		if (!getShapesList().isEmpty())
     	{
			for (DShape d : getShapesList())
     		{
     			if (d.info.getId() == model.getId())
     			{
     		      shape = d;
     			}
     		}
     	 }
		 if (shape != null)
		 {
		    getShapesList().remove(getShapesList().indexOf(shape));
		    getShapesList().add(0,shape);
		 }
	}

	/**
	 * the selected shape is removed from the canvas
	 * @param model
	 */
	public void removeShape(DShapeModel model)
	{
		 if (!getShapesList().isEmpty())
     	 {
     		 synchronized(getShapesList()) {
     			 Iterator<DShape> itr = getShapesList().iterator(); 
     			 while(itr.hasNext())
     			 { 
     			     if (itr.next().info.getId() == model.getId())
           			 {
           				 itr.remove();
           				 break;
           			 } 
     			 } 
     		 }
     	 }
		 tableModel.fireTableDataChanged();
	}
	
	/**
	 * Finds shapeModel in list and changes it
	 */
	public void change(DShapeModel shapeModel)
	{
		if (!getShapesList().isEmpty())
     	{
     		for (DShape d : getShapesList())
     		{
     			if (d.info.getId() == shapeModel.getId())
     			{
     				  d.modelChanged(shapeModel);
     				  tableModel.modelChanged(shapeModel);
     			}
     		}
     	}
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

	public TableModel getTableModel() {
		// TODO Auto-generated method stub
		return tableModel;
	}

}