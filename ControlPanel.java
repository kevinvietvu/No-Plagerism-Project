import java.awt.*;

import java.util.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class ControlPanel extends JPanel {
	static JTextField textDisplay;
	static JComboBox<Font> fonts;
	static GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    static Font[] allFonts = ge.getAllFonts();
	static JButton setColor;
	static JButton moveFront;
	static JButton moveBack;
	static JButton remove;

	public ControlPanel()
	{
		super();
		this.addButtons();
	}
	
	public void addButtons()
	{
		JPanel shapePanel = new JPanel();
		JPanel colorPanel = new JPanel();
		JPanel fontBox = new JPanel();
		JPanel movePanel = new JPanel();
		JTextArea textBox = new JTextArea();
		
		textBox.setBorder(new LineBorder(Color.gray, 2));
		textBox.setEditable(false);
		
		JButton rect = new JButton("Rect");
		rect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int random = (int )(Math.random() * 400 + 1);
				int random2 = (int )(Math.random() * 400 + 1);
				int random3 = (int )(Math.random() * 150 + 10);
				int random4 = (int )(Math.random() * 150 + 10);
				DRectModel bounds = new DRectModel();
				bounds.setX(random);
				bounds.setY(random2);
				bounds.setWidth(random3);
				bounds.setHeight(random4);
				
				Canvas.addShape(bounds);
			}
		});
		
		JButton oval = new JButton("Oval");
		oval.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int random = (int )(Math.random() * 400 + 1);
				int random2 = (int )(Math.random() * 400 + 1);
				int random3 = (int )(Math.random() * 150 + 10);
				int random4 = (int )(Math.random() * 150 + 10);				
				DOvalModel bounds = new DOvalModel(random, random2, random3, random4);			
				Canvas.addShape(bounds);
			}
		});
		
		JButton line = new JButton("Line");
		line.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int random = (int )(Math.random() * 350 + 2);
				int random2 = (int )(Math.random() * 350 + 2);
				int random3 = (int )(Math.random() * 150 + 10);
				int random4 = (int )(Math.random() * 150 + 10);		
				DLineModel bounds = new DLineModel(random, random2, random3, random4);	
				//DLineModel bounds = new DLineModel(0,0,20,20);
				Canvas.addShape(bounds);
			}
		});
		
		JButton text = new JButton("Text");
		text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int random = (int )(Math.random() * 400 + 2);
				int random2 = (int )(Math.random() * 400 + 2);
			/*	int random3 = (int )(Math.random() * 100 + 5);
				int random4 = (int )(Math.random() * 100 + 5);		
				DTextModel bounds = new DTextModel(random, random2, random3, random4);	 */
				DTextModel bounds = new DTextModel(random,random2,200,100,"Hello","Dialog.plain"); 
				Canvas.addShape(bounds);
			}
		});
		
		setColor = new JButton("Set Color");
		setColor.addActionListener(new ActionListener() { 
		public void actionPerformed(ActionEvent e) {
			Color initialcolor = Color.GRAY;
			Color newColor = JColorChooser.showDialog(setColor, "Select a color", initialcolor);
			if (Canvas.selectedModel == null)
			{
				return;
			}
			for (DShapeModel d : DShapeModel.listeners)
			{
				if (Canvas.selectedModel.equals(d))
				Canvas.selectedModel.setC(newColor);
				Canvas.selected.modelChanged(Canvas.selectedModel);
				repaint();
			} 
		  }
		});
		moveFront = new JButton("Move To Front");
		moveFront.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				Canvas.printReverse();
				System.out.println(" ");
				if (Canvas.selected != null && !Canvas.shapesList.isEmpty())
				{
					Collections.swap(Canvas.shapesList, 0, Canvas.shapesList.indexOf(Canvas.selected));
				}
				Canvas.printList();
			}
		});
		moveBack = new JButton("Move To Back");
		moveBack.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				Canvas.printReverse();
				System.out.println(" ");
				if (Canvas.selected != null && !Canvas.shapesList.isEmpty())
				{
					Collections.swap(Canvas.shapesList, Canvas.shapesList.size() - 1, Canvas.shapesList.indexOf(Canvas.selected));
				}
				Canvas.printReverse();
			}
		});
		remove = new JButton("Remove Shape");
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Canvas.selected != null) {
					if (Canvas.shapesList.contains(Canvas.selected)) {
						DShapeModel.listeners.remove(Canvas.selectedModel);
						Canvas.shapesList.remove(Canvas.selected);
						repaint();			
					}
				}
			}
		});
		
		shapePanel.setLayout(new BoxLayout(shapePanel, BoxLayout.X_AXIS)); 
		shapePanel.add(new JLabel("Add"));
		shapePanel.add(rect);
		shapePanel.add(oval);
		shapePanel.add(line);
		shapePanel.add(text);
		shapePanel.add(Box.createRigidArea(new Dimension(0,50))); // creates white space between the panels.
		
		colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.X_AXIS));
		colorPanel.add(setColor);
		colorPanel.add(Box.createRigidArea(new Dimension(0,50)));
		
		textDisplay = new JTextField(10);
		fonts = new JComboBox<Font>(allFonts);
		//this gets the actual font name into the box, without like java.awt.font blah blah
		fonts.setRenderer(new DefaultListCellRenderer() {
		   @Override
		   public Component getListCellRendererComponent(JList<?> list,
		         Object value, int index, boolean isSelected, boolean cellHasFocus) {
		      if (value != null) {
		         Font font = (Font) value;
		         value = font.getName();
		      }
		      return super.getListCellRendererComponent(list, value, index,
		            isSelected, cellHasFocus);
		   }
		});
		textDisplay.setMaximumSize(new Dimension(100, textDisplay.getPreferredSize().height));
		disableButtons();
		fontBox.setLayout(new BoxLayout(fontBox, BoxLayout.X_AXIS));
		fontBox.add(textDisplay);
		fontBox.add(Box.createRigidArea(new Dimension(20,0)));
		fontBox.add(fonts);
		colorPanel.add(Box.createRigidArea(new Dimension(0,50)));
		
		movePanel.setLayout(new BoxLayout(movePanel, BoxLayout.X_AXIS));
		movePanel.add(moveFront);
		movePanel.add(moveBack);
		movePanel.add(remove);
		movePanel.add(Box.createRigidArea(new Dimension(0,50)));
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(shapePanel);
		this.add(fontBox);
		this.add(colorPanel);
		this.add(movePanel);
		this.add(textBox);
		for(Component comp: this.getComponents()){
			((JComponent)comp).setAlignmentX(LEFT_ALIGNMENT); // aligns all the panels to the left margin.
		}
	}
	
	public static void enableButtons()
	{
		textDisplay.setEnabled(true);
		fonts.setEnabled(true);
	}
	
	public static void disableButtons()
	{
		textDisplay.setEnabled(false);
		fonts.setEnabled(false);
	}
}
