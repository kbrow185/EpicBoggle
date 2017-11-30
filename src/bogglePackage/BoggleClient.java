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

	public BoggleClient() {

		//serverName = "ec2-34-236-147-3.compute-1.amazonaws.com";
		serverName = "ec2-54-167-2-107.compute-1.amazonaws.com";
		port = 8989;
		jsons = new ArrayList<JSONObject>();
		new Thread(this).start();
	}

	@Override
	public void run() {
		try {
			// TODO Auto-generated method stub
			clientSocket = new Socket(serverName, port);
			dataIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			dataOut = new PrintWriter(clientSocket.getOutputStream());
			JSONObject login = new JSONObject(JsonBuilder.JsonBuilderMethod("login","","username","Mr. Cool"));
			dataOut.println(login);
			dataOut.flush();
			System.out.println("connected To server.");
			// relay message on login

			while (true) {

				try {

					JSONObject response = new JSONObject(dataIn.readLine());
					jsons.add(response);
					System.out.println("Receive :"+response);

				} catch (IOException e) {
					e.printStackTrace();
					// Will be booted from server if theres an issue. No fix is required here.
					// The stack trace recieves valuable information from the server as to what went
					// wrong.
				}
			}
		} catch (IOException e) {
			// If theres an issue I can't do much with it here. There is a connection issue
			// and will likely kick the client.
			// The stack trace recieves valuable information from the server as to what went
			// wrong.
			e.printStackTrace();
		}
	}

	public void sendMessage(JSONObject message) {
		dataOut.println(message);
		dataOut.flush();
		System.out.println("Data out: " + message);
	}
	public ArrayList<JSONObject> getResponses(){
		
		ArrayList<JSONObject> temp = new ArrayList<JSONObject>(jsons);
		jsons.clear();
		return temp;
	}

}
