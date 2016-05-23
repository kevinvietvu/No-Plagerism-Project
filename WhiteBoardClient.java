import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

class WhiteBoardClient extends Thread {
	public String name;
	public int port;
	public final String DEFAULT_HOST_NAME = "127.0.0.1";
	public static DShapeModel model;
	public Canvas canvas;
	public Thread client;

	/**
	 * Makes new client for a whiteboard
	 * 
	 * @param Canvas
	 * @param host
	 * @param port
	 */
	WhiteBoardClient(Canvas canvas, String host, int port) 
	{
		this.canvas = canvas;
		this.name = host;
		this.port = port;
	}

	/**
	 * Connect to the server, loop getting output from server runs as thread
	 */
	public void run() 
	{
		try {
			// make connection to the server name/port
			Socket toServer = new Socket(name, port);
			// get input stream to read from server and wrap in object input
			// stream
			ObjectInputStream in = new ObjectInputStream(toServer.getInputStream());
			System.out.println("client: connected on port " + port);

			while (true) {
				// Get the xml string, decode to a DShapeModel object.
				// Blocks in readObject(), waiting for server to send something.
				String verb = (String) in.readObject();
				String xmlString = (String) in.readObject();
				@SuppressWarnings("resource")
				XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(xmlString.getBytes()));
				model = (DShapeModel) decoder.readObject();
				decoder.close();
				if (verb.equals("add")) {
					canvas.addShape(model);
				} else if (verb.equals("remove")) {
					canvas.removeShape(model);
				} else if (verb.equals("moveFront")) {
					canvas.moveFront(model);
				} else if (verb.equals("moveBack")) {
					canvas.moveBack(model);
				} else if (verb.equals("change")) {
					canvas.change(model);
				}
			}
		} catch (Exception e) { // IOException and ClassNotFoundException
			canvas.controls.status.setText("N/A");
			JOptionPane.showMessageDialog(null, "Connection refused", "Error", JOptionPane.ERROR_MESSAGE);
			
		}
	}

}
