import java.awt.*;

import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class ControlPanel extends JPanel {
	
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
				DRectModel bounds = new DRectModel(random, random2, random3, random4);
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
		JButton setColor = new JButton("Set Color");
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
				d.setC(newColor);
				Canvas.selected.modelChanged(d);
				repaint();
			} 
		  }
		} );
		JButton moveFront = new JButton("Move To Front");
		moveFront.addActionListener(new ActionListener() { 
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
		JButton moveBack = new JButton("Move To Back");
		moveBack.addActionListener(new ActionListener() { 
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
		JButton remove = new JButton("Remove Shape");
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Canvas.selected != null)
				{
					if (Canvas.shapesList.contains(Canvas.selected))
					{
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
		
		JTextField font = new JTextField();
		font.setMaximumSize(new Dimension(100, font.getPreferredSize().height));
		fontBox.setLayout(new BoxLayout(fontBox, BoxLayout.X_AXIS));
		fontBox.add(font);
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
}
