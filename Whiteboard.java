import java.awt.BorderLayout;

import javax.swing.*;

public class Whiteboard extends JFrame {
	private JFrame frame; 
	String title;
	public Whiteboard(String title)
	{
		this.title = title;
	}
	
	public Whiteboard()
	{
		title = "Whiteboard";
	}
	
	public void run()
	{
	    frame = new JFrame(title);
	    frame.setLayout(new BorderLayout());
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    Canvas board = new Canvas();
	    frame.add(board, BorderLayout.CENTER);
	    
	    ControlPanel controls = new ControlPanel();
	    frame.add(controls,BorderLayout.WEST);
	
	    
	    frame.pack();
	    frame.setVisible(true);
	
	}
	public static void main(String args[])
	{
		Whiteboard wb = new Whiteboard();
		wb.run();
	}
}
