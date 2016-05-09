import java.awt.*;
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
				DRectModel bounds = new DRectModel(random, random2, 60, 45);
				DRect rect = new DRect();
				rect.info = bounds;
				Canvas.shapesList.add(rect);
				
			}
		});
		JButton oval = new JButton("Oval");
		oval.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int random = (int )(Math.random() * 400 + 1);
				int random2 = (int )(Math.random() * 400 + 1);
				DOvalModel bounds = new DOvalModel(random, random2, 60, 45);
				DOval oval = new DOval();
				oval.info = bounds;
				Canvas.shapesList.add(oval);
			}
		});
		JButton line = new JButton("Line");
		JButton text = new JButton("Text");
		JButton setColor = new JButton("Set Color");
		JButton moveFront = new JButton("Move To Front");
		JButton moveBack = new JButton("Move To Back");
		JButton remove = new JButton("Remove Shape");
		
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
