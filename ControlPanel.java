import java.awt.*;

import javax.swing.*;

public class ControlPanel extends JPanel {
	
	public ControlPanel()
	{
		super();
		this.add(new JLabel("Add"));
		this.addButtons();
	}
	
	public void addButtons()
	{
		
		JButton rect = new JButton("Rect");
		JButton oval = new JButton("Oval");
		JButton line = new JButton("Line");
		JButton text = new JButton("Text");
		JButton setColor = new JButton("Set Color");
		JButton moveFront = new JButton("Move To Front");
		JButton moveBack = new JButton("Move To Back");
		JButton remove = new JButton("Remove Shape");
		
		this.add(rect);
		this.add(oval);
		this.add(line);
		this.add(text);
		this.add(setColor);
		this.add(moveFront);
		this.add(moveBack);
		this.add(remove);
	}
}
