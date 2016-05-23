import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

public class WhiteBoardServer extends Thread {
	private static final int DEFAULT_PORT = 11584;
	private static ServerSocket serverSocket;
	private int port;
	private Canvas canvas;
	private static ArrayList<ObjectOutputStream> clientStreams = new ArrayList<>();
	private static Socket clientSocket = null;
	

    /**
     * Makes new Server for a whiteboard
     * @param port 
     * @param canvas
     */
	public WhiteBoardServer(int port, Canvas canvas) 
	{
		this.canvas = canvas;
		this.port = port;
		System.out.println("Server button clicked");
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server was able to get on port: " + port);
		} catch (IOException e) {
			canvas.getControlPanel().getStatus().setText("N/A");
			JOptionPane.showMessageDialog(null, "Could not listen on port: " + port, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	
	public WhiteBoardServer() 
	{
		port = DEFAULT_PORT;
		System.out.println("Server button clicked");
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server was able to get on port: " + port);
		} catch (IOException e) {
			canvas.getControlPanel().getStatus().setText("N/A");
			JOptionPane.showMessageDialog(null, "Could not listen on port: " + port, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
	}
	
	/**
	 * Returns server socket 
	 * @return serverSocket
	 */
	public static ServerSocket getServerSocket() {
		return serverSocket;
	}

	//runs as a separate thread to accept incoming clients
	public void run() 
	{
       		while (true) {
	        
	        	try {
	        	clientSocket = serverSocket.accept();
	        	ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
	        	clientStreams.add(output);
	
	        	System.out.println("New client: " + clientSocket.getPort());
	        		for (DShape shape : canvas.getShapesList()) {
	        			DShapeModel model = shape.getModel();
	        			String xmlModel = ObjectToXML(model);
	        			output.writeObject("add");
						output.writeObject(xmlModel);
	        		}
	        	}
	        	catch (IOException e) {
	        	System.out.println("Accept failed: " + port);
	        	System.exit(-1);	
	        	}
	    }
	}
	

    /**
     * Changes a model into an XML string
     * @param DShapeModel
     */
	public static String ObjectToXML(DShapeModel d)
	{
		String xml = "";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XMLEncoder xmlEncoder = new XMLEncoder(baos);
		xmlEncoder.writeObject(d);
		xmlEncoder.close();
		xml = baos.toString();
		return xml;
	}

	/**
	 * Sends xml data to all connected clients
	 * @param command
	 * @param xmlModel
	 */
	public static void send(String command, String xmlModel)
	{
		for (int i = 0; i < clientStreams.size(); i++) {
			if (serverSocket != null && !serverSocket.isClosed() && clientSocket != null) {
				//System.out.println("Sent: " + command + " " + xmlModel);
				try {
					clientStreams.get(i).writeObject(command);
					clientStreams.get(i).writeObject(xmlModel);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Exception when sending data", "Error", JOptionPane.ERROR_MESSAGE);
				}
		    }
		}
	}

}

