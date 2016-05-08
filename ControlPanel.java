import java.awt.*;

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
		JPanel shapes = new JPanel();
		JPanel color = new JPanel();
		JPanel move = new JPanel();
		JTextArea box = new JTextArea();
		box.setBorder(new LineBorder(Color.gray, 2));
		box.setEditable(false);
		
		JButton rect = new JButton("Rect");
		JButton oval = new JButton("Oval");
		JButton line = new JButton("Line");
		JButton text = new JButton("Text");
		JButton setColor = new JButton("Set Color");
		JButton moveFront = new JButton("Move To Front");
		JButton moveBack = new JButton("Move To Back");
		JButton remove = new JButton("Remove Shape");
		
		shapes.add(new JLabel("Add"));
		shapes.add(rect);
		shapes.add(oval);
		shapes.add(line);
		shapes.add(text);
		
		color.add(setColor);
		
		move.add(moveFront);
		move.add(moveBack);
		move.add(remove);
		
		this.add(shapes);
		this.add(color);
		this.add(move);
		this.add(box);
	}
}
