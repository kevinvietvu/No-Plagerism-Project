import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.*;

public class Whiteboard extends JFrame {
	public String title;
	public static WhiteBoardServer server;
	public Canvas canvasBoard;
	
	/**
	 * Makes a new whiteboard
	 */
	public Whiteboard()
	{
		title = "Whiteboard";
		JFrame  frame = new JFrame(title);
	    frame.setLayout(new BorderLayout());
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    canvasBoard = new Canvas(this);
	    frame.add(canvasBoard, BorderLayout.CENTER);
	    
	    ControlPanel controls = new ControlPanel(canvasBoard);
	    controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
	    
	    canvasBoard.setControlPanel(controls);
	    
	    frame.add(controls,BorderLayout.WEST);

	    frame.pack();
	    frame.setVisible(true);
	}
	
	public static void main(String args[])
	{
		Whiteboard wb = new Whiteboard();
	
		Whiteboard wb2 = new Whiteboard();
		
		Whiteboard wb3 = new Whiteboard();



	}

	
}
