package bogglePackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import org.json.JSONObject;

public class BoggleClient implements Runnable {

	private Socket clientSocket;
	private PrintWriter dataOut;
	private BufferedReader dataIn;
	private ArrayList<JSONObject> jsons;
	private boolean connected;

	public BoggleClient() {
		connected = true;
		jsons = new ArrayList<JSONObject>();
		new Thread(this).start();
	}

	@Override
	public void run() {

		clientSetup();

		while (connected) {
			try {

				JSONObject response = new JSONObject(dataIn.readLine());
				jsons.add(response);
				System.out.println("Receive :" + response);

			} catch (IOException e) {
				// Will be disconnected from server if there's an issue. 
				//This will catch the exception and throw a flag to prevent future client use.
				connected = false;
			}

		}
	}

	private void clientSetup() {
		try {
			String serverName = "ec2-34-224-216-23.compute-1.amazonaws.com";
			int port = 8989;
			clientSocket = new Socket(serverName, port);
			dataIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			dataOut = new PrintWriter(clientSocket.getOutputStream());
			JSONObject login = new JSONObject(JsonBuilder.JsonBuilderMethod("login", "", "username", "Captain Cool"));
			sendMessage(login);
		} catch (IOException e) {
			connected = false;
			// If there's an issue I can't do much with it here.
			// Will be disconnected from server if there's an issue. 
			//This will catch the exception and throw a flag for future client use.
		}
	}

	public void sendMessage(JSONObject message) {
		dataOut.println(message);
		dataOut.flush();
		System.out.println("Data out: " + message);
	}

	public ArrayList<JSONObject> getResponses() {

		ArrayList<JSONObject> temp = new ArrayList<JSONObject>(jsons);
		jsons.clear();
		return temp;
	}

	public boolean isServerConnected() {
		return connected;
	}

}
