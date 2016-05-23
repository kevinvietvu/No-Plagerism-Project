import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class ControlPanel extends JPanel 
{
	private JTextField textDisplay;
	private JComboBox<Font> fonts;
	private GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	private Font[] allFonts = ge.getAllFonts();
	private JButton rect;
	private JButton oval;
	private JButton line;
	private JButton text;
	private JButton setColor;
	private JButton moveFront;
	private JButton moveBack;
	private JButton remove;
	private JButton server;
	private JButton client;
	private JButton open;
	private JButton save;
	private JButton saveImage;
	private JLabel status;
	private String xmlModel;
	private int count = 1;
	private Canvas canvas;

	public ControlPanel(Canvas canvas)
	{
		super();
		this.canvas = canvas;
		this.addButtons();
	}
	
	public void addButtons()
	{
		JPanel shapePanel = new JPanel();
		JPanel colorPanel = new JPanel();
		JPanel fontBox = new JPanel();
		JPanel movePanel = new JPanel();
		JPanel networkPanel = new JPanel();
		JPanel tablePanel = new JPanel();
		
	    rect = new JButton("Rect");
		rect.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				DRectModel bounds = new DRectModel(10, 10, 20, 20);	
				bounds.setId(count);
				count++;
				canvas.addShape(bounds);
				if (Whiteboard.getServer() != null)
				{
					xmlModel = WhiteBoardServer.ObjectToXML(bounds);
					WhiteBoardServer.send("add",xmlModel);
				}
				
			}
		});
		
		oval = new JButton("Oval");
		oval.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{		
				DOvalModel bounds = new DOvalModel(10, 10, 20, 20);	
				bounds.setId(count);
				count++;
				canvas.addShape(bounds);
				if (Whiteboard.getServer() != null)
				{
					xmlModel = WhiteBoardServer.ObjectToXML(bounds);
					WhiteBoardServer.send("add",xmlModel);
				}
				
			}
		});
		
	    line = new JButton("Line");
		line.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				DLineModel bounds = new DLineModel(10,10, 20, 20);		
				bounds.setId(count);
				count++;
				canvas.addShape(bounds);
				if (Whiteboard.getServer() != null)
				{
					xmlModel = WhiteBoardServer.ObjectToXML(bounds);
					WhiteBoardServer.send("add",xmlModel);

				}
				
			}
		});
		
		text = new JButton("Text");
		text.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				DTextModel bounds = new DTextModel(10,10,20,20,"Hello","Dialog.plain"); 
				bounds.setId(count);
				count++;
				canvas.addShape(bounds);
				if (Whiteboard.getServer() != null)
				{
					xmlModel = WhiteBoardServer.ObjectToXML(bounds);
					WhiteBoardServer.send("add",xmlModel);
				}
			}
		});
		
		setColor = new JButton("Set Color");
		setColor.addActionListener(new ActionListener() 
		{ 
		public void actionPerformed(ActionEvent e) 
		{
			Color initialcolor = Color.GRAY;
			Color newColor = JColorChooser.showDialog(setColor, "Select a color", initialcolor);
			if (canvas.getSelectedModel() == null)
			{
				return;
			}
			for (DShapeModel d : canvas.getSelectedModel().getListeners())
			{
				if (canvas.getSelectedModel().equals(d))
				{
					canvas.getSelectedModel().setColor(newColor);
					canvas.getSelected().modelChanged(canvas.getSelectedModel());
					xmlModel = WhiteBoardServer.ObjectToXML(canvas.getSelected().info);
					WhiteBoardServer.send("change",xmlModel);
				}	
				repaint();
			} 
		  }
		});
		
		moveFront = new JButton("Move To Front");
		moveFront.addActionListener(new ActionListener() 
		{ 
			public void actionPerformed(ActionEvent e) 
			{

				if (canvas.getSelected() != null && !canvas.getShapesList().isEmpty())
				{
					xmlModel = WhiteBoardServer.ObjectToXML(canvas.getSelectedModel());
					WhiteBoardServer.send("moveFront",xmlModel);
					canvas.getShapesList().remove(canvas.getShapesList().indexOf(canvas.getSelected()));
					canvas.getShapesList().add(canvas.getSelected());
				}
			
			}
		});
		
		moveBack = new JButton("Move To Back");
		moveBack.addActionListener(new ActionListener() 
		{ 
			public void actionPerformed(ActionEvent e) 
			{
		
				if (canvas.getSelected() != null && !canvas.getShapesList().isEmpty())
				{
					xmlModel = WhiteBoardServer.ObjectToXML(canvas.getSelectedModel());
					WhiteBoardServer.send("moveBack",xmlModel);
					canvas.getShapesList().remove(canvas.getShapesList().indexOf(canvas.getSelected()));
					canvas.getShapesList().add(0,canvas.getSelected());
					
				}
			
			}
		});
		
		remove = new JButton("Remove Shape");
		remove.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (canvas.getSelected() != null) 
				{
					if (canvas.getShapesList().contains(canvas.getSelected())) 
					{
						xmlModel = WhiteBoardServer.ObjectToXML(canvas.getSelectedModel());
						WhiteBoardServer.send("remove",xmlModel);
						canvas.getSelectedModel().getListeners().remove(canvas.getSelectedModel());
						canvas.getShapesList().remove(canvas.getSelected());
						canvas.getTableModel().fireTableDataChanged();
						repaint();			
					}
				}
			}
		});
		
		open = new JButton("Open");
		open.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String result = JOptionPane.showInputDialog("File Name", null);
                if (result != null) {
                    File f = new File(result);
            		try 
            		{
                        XMLDecoder xmlIn = new XMLDecoder(new BufferedInputStream(new FileInputStream(f))); 
                        DShape[] shapeArray = (DShape[]) xmlIn.readObject();
                        xmlIn.close();
                        canvas.clear();
                        xmlModel = WhiteBoardServer.ObjectToXML(canvas.getSelectedModel());
                        WhiteBoardServer.send("clear", xmlModel);
                        for(DShape shape : shapeArray) 
                        {
                        	canvas.getShapesList().add(shape);
                        	String xmlModel = WhiteBoardServer.ObjectToXML(shape.getModel());
                        	WhiteBoardServer.send("add", xmlModel );
                        }
                		repaint();
                    } catch (IOException x)
            		
            		{
                        x.printStackTrace();
                    }
                }
			}
		});
		
		save = new JButton("Save");
		save.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String result = JOptionPane.showInputDialog("File Name", null);
                if (result != null) 
                {
                    File f = new File(result);
                    try 
                    {
                        XMLEncoder xmlOut = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(f)));
                        DShape[] shapeArray = canvas.getShapesList().toArray(new DShape[canvas.getShapesList().size()]);
                        xmlOut.writeObject(shapeArray);
                        xmlOut.close();
                    } catch (IOException x) 
                    
                    {
                    	x.printStackTrace();
                    }
                }
			}
		});
		
		saveImage = new JButton("Save PNG");
        saveImage.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
    				if(canvas.getSelected() != null)
    				{
    					DShape selected = canvas.getSelected();
    					selected = null;
    				}
    				String result = JOptionPane.showInputDialog("File Name", null);
    				if(result != null) 
    				{
    					File f = new File(result);
    					canvas.saveImage(f);		
    				}
            }
        });
		
		server = new JButton("Server Start");
		server.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (getStatus().getText().equals("Client mode"))
				{
					JOptionPane.showMessageDialog(null, "Cannot change from client mode to server mode.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (getStatus().getText().equals("Server mode"))
				{
					JOptionPane.showMessageDialog(null, "Whiteboard already in Server Mode", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				else {
					String result = JOptionPane.showInputDialog("Run Server on port", "11584");
					try {
						int port = Integer.parseInt(result);
						if (port > 65535 || port < 0) 
						{
							throw new IllegalArgumentException();
						}
					          System.out.println("server: start");
					          getStatus().setText("Server mode");
					          WhiteBoardServer server = new WhiteBoardServer(port, canvas);
					          Whiteboard.setServer(server);
					          server.start();
					    }
					catch(IllegalArgumentException e1) 
					{
						JOptionPane.showMessageDialog(null, "Enter an integer between 0 and 65535", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		client = new JButton("Client Start");
		client.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
		        
				if (getStatus().getText().equals("Server mode"))
				{
					JOptionPane.showMessageDialog(null, "Can't change from server mode to client mode. Restart the program.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (getStatus().getText().equals("Client mode"))
				{
					JOptionPane.showMessageDialog(null, "Whiteboard already in client mode." , "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{
					String result = JOptionPane.showInputDialog("Connect to host:port", "127.0.0.1:11584");
					try 
					{
						String[] parts = result.split(":");
						int port = Integer.parseInt(parts[1].trim());
						if (port > 65535 || port < 0) 
						{
							throw new IllegalArgumentException();
						}
						getStatus().setText("Client mode");
						canvas.clear();
						disableAllButtons();
						WhiteBoardClient client = new WhiteBoardClient(canvas,parts[0].trim(), port);
						client.start();
					}
					catch (Exception e1) //ArrayIndexOutOfBounds and IllegalArgumentException
					{
						JOptionPane.showMessageDialog(null, "Please enter a valid IP address", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
		
		setStatus(new JLabel("N/A"));
		
		shapePanel.setLayout(new BoxLayout(shapePanel, BoxLayout.X_AXIS)); 
		shapePanel.add(new JLabel("Add"));
		shapePanel.add(rect);
		shapePanel.add(oval);
		shapePanel.add(line);
		shapePanel.add(text);
		shapePanel.add(Box.createRigidArea(new Dimension(0,50))); // creates white space between the panels.
		
		colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.X_AXIS));
		colorPanel.add(setColor);
		colorPanel.add(save);
		colorPanel.add(saveImage);
		colorPanel.add(open);
		colorPanel.add(Box.createRigidArea(new Dimension(0,50)));
		
		setTextDisplay(new JTextField(10));
		setFonts(new JComboBox<Font>(allFonts));
		//this gets the actual font name into the box
		getFonts().setRenderer(new DefaultListCellRenderer() 
		{
		   @Override
		   public Component getListCellRendererComponent(JList<?> list,
		         Object value, int index, boolean isSelected, boolean cellHasFocus) 
		   {
		      if (value != null) 
		      {
		         Font font = (Font) value;
		         value = font.getName();
		      }
		      return super.getListCellRendererComponent(list, value, index,
		            isSelected, cellHasFocus);
		   }
		});
		
		getTextDisplay().setMaximumSize(new Dimension(100, getTextDisplay().getPreferredSize().height));
		disableButtons();
		fontBox.setLayout(new BoxLayout(fontBox, BoxLayout.X_AXIS));
		fontBox.add(getTextDisplay());
		fontBox.add(Box.createRigidArea(new Dimension(20,0)));
		fontBox.add(getFonts());
		colorPanel.add(Box.createRigidArea(new Dimension(0,50)));
		
		movePanel.setLayout(new BoxLayout(movePanel, BoxLayout.X_AXIS));
		movePanel.add(moveFront);
		movePanel.add(moveBack);
		movePanel.add(remove);
		movePanel.add(Box.createRigidArea(new Dimension(0,40)));
		
		networkPanel.setLayout(new BoxLayout(networkPanel, BoxLayout.X_AXIS));
		networkPanel.add(server);
		networkPanel.add(client);
		networkPanel.add(Box.createRigidArea(new Dimension(20,0)));
		networkPanel.add(getStatus());
		networkPanel.add(Box.createRigidArea(new Dimension(0,50)));
		
		tablePanel.setLayout(new BorderLayout());
		JTable table = new JTable(canvas.getTableModel());
		JScrollPane tableContainer = new JScrollPane(table);
		tablePanel.add(tableContainer);
		tableContainer.setPreferredSize(new Dimension());
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(shapePanel);
		this.add(fontBox);
		this.add(colorPanel);
		this.add(movePanel);
		this.add(networkPanel);
		this.add(tablePanel);
		
		for(Component comp: this.getComponents())
		{
			((JComponent)comp).setAlignmentX(LEFT_ALIGNMENT); // aligns all the panels to the left margin.
		}
	}
	
	public void disableAllButtons()
	{
	    rect.setEnabled(false);
	    oval.setEnabled(false);
	    line.setEnabled(false);
	    text.setEnabled(false);
		setColor.setEnabled(false);
		moveFront.setEnabled(false);
		moveBack.setEnabled(false);
	    remove.setEnabled(false);
		getTextDisplay().setEnabled(false);
		getFonts().setEnabled(false);
	}
	
	public void enableButtons()
	{
		getTextDisplay().setEnabled(true);
		getFonts().setEnabled(true);
	}
	
	public void disableButtons()
	{
		getTextDisplay().setEnabled(false);
		getFonts().setEnabled(false);
	}

	public JLabel getStatus() {
		return status;
	}

	public void setStatus(JLabel status) {
		this.status = status;
	}

	public JTextField getTextDisplay() {
		return textDisplay;
	}

	public void setTextDisplay(JTextField textDisplay) {
		this.textDisplay = textDisplay;
	}

	public JComboBox<Font> getFonts() {
		return fonts;
	}

	public void setFonts(JComboBox<Font> fonts) {
		this.fonts = fonts;
	}
}