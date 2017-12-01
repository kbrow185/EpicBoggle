package bogglePackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import org.json.JSONObject;

public class BoggleClient implements Runnable {

	private int port;
	private String serverName;
	boolean serverStart;
	Socket clientSocket;
	PrintWriter dataOut;
	BufferedReader dataIn;
	ArrayList<JSONObject> jsons;
	boolean connected;

	public BoggleClient() {

		// serverName = "137.190.250.133";
		connected = true;
		serverName = "ec2-54-167-2-107.compute-1.amazonaws.com";
		port = 8989;
		jsons = new ArrayList<JSONObject>();
		new Thread(this).start();
	}

	@Override
	public void run() {
		try {
			clientSocket = new Socket(serverName, port);
			dataIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			dataOut = new PrintWriter(clientSocket.getOutputStream());
			JSONObject login = new JSONObject(JsonBuilder.JsonBuilderMethod("login", "", "username", "Captain Cool"));
			dataOut.println(login);
			dataOut.flush();
			System.out.println("connected To server.");
			while (true) {

				try {

					JSONObject response = new JSONObject(dataIn.readLine());
					jsons.add(response);
					System.out.println("Receive :" + response);

				} catch (IOException e) {
					// Will be booted from server if theres an issue. No fix is required here.
					connected = false;
				}
			}
		} catch (IOException e) {
			connected = false;
			// If theres an issue I can't do much with it here. The handler will see that there is no longer a connection and notify the user.
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
