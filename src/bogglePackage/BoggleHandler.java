package bogglePackage;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;

public class BoggleHandler implements Runnable {

	BoggleLogic boggleLogic;
	BoggleClient client;
	BoggleGUI boggleGUI;
	Boolean boggleRunning;
	Boolean clientConnecting;

	public BoggleHandler() {
		clientConnecting = false;
		boggleRunning = false;
		boggleGUI = new BoggleGUI();
		new Thread(this).start();
	}

	@Override
	public void run() {

		while (boggleGUI.isRunning()) {
			try {
				Thread.sleep(200);
				for (JSONObject message : boggleGUI.getCommands()) {
					sendClientMessage(message);
				}

				if (clientConnecting) {
						for (JSONObject message : client.getResponses()) {
							translateServerMessage(message);
						}
				}

				if (boggleRunning) {
					if(!client.isServerConnected()) {
						throw new Exception("You've lost connection to the server.");
					}
					
					boggleGUI.setTimer(boggleLogic.getTime());
					ArrayList<ArrayList<Integer>> letterList = boggleGUI.gatherWords();
					if (letterList.size() > 0) {
						for (ArrayList<Integer> letters : letterList) {

							if (boggleLogic.checkSubmission(letters) &&client.isServerConnected() ) {
								JSONObject submission = new JSONObject(
										JsonBuilder.JsonBuilderMethod("GUESS", "positions", new JSONArray(letters)));
								sendClientMessage(submission);
							} 
							else {
								boggleGUI.notifyUser("INVALID ENTRY");
							}
						}
					}
				}
			} catch (Exception e) {
				displayError(e.getMessage());
			}
		}

	}

	public void sendClientMessage(JSONObject message) {

		String json = message.optString("type");
		if (json.equals("login")) {
			client = new BoggleClient();
			boggleGUI.addToChatBox("Connecting to Server");
			clientConnecting = true;
		} else if (clientConnecting && client.isServerConnected()) {
			client.sendMessage(message);
		} else {
			displayError("There is no connection to server.");
		}

	}
	
	public void translateServerMessage(JSONObject message) {

		String response = message.optString("type").toUpperCase();

		switch (response) {
		
		case("ERROR"):
			boggleGUI.addToChatBox("ERROR:" +message.getString("message"));
			break;
		case ("ACKNOWLEDGE"):

			boggleGUI.addToChatBox(message.getString("message"));
			break;

		case ("DENY"):
			boggleGUI.addToChatBox(message.getString("message"));
			break;

		case ("APPLICATION"):

			JSONObject application = message.getJSONObject("message");
			String action = application.getString("action").toUpperCase();
			switch (action) {
			

			
			
			case ("CHAT"):
				String dialog = application.optString("chatMessage");
				boggleGUI.addToChatBox("Chat: " + dialog);
				break;

			case ("WORD"):
				boggleGUI.addToWordList(application.optString("word"));
				break;

			case ("STARTGAME"):
				int size = application.optJSONArray("board").length();

				if (size > 0) {
					String letters = "";
					for (Object j : application.optJSONArray("board")) {
						letters += j.toString();
					}

					if (!boggleRunning) {
						try {

							boggleLogic = new BoggleLogic();
							boggleRunning = true;
							boggleGUI.setUpBoard(letters.toCharArray());
							boggleLogic.resetBoard(letters.toCharArray());
							boggleGUI.addToChatBox("GAME STARTED");
						} catch (FileNotFoundException e) {
							displayError(e.getMessage());
						}
					}

				}
				break;

			case ("GAMEEND"):
				boggleRunning = false;
				boggleLogic.endGame();
				boggleGUI.addToChatBox("GAME HAS ENDED");
				boggleGUI.notifyUser("");
				break;

			case ("POINTS"):
				int points = application.optInt("points");
				if (points > 0) {
					boggleGUI.addPoints(points);
					boggleGUI.notifyUser(points + " Points!");
				} else {
					boggleGUI.notifyUser("Invalid Word");
				}
				break;
			}
			break;
		}

	}

	private void displayError(String error) {
		JOptionPane.showMessageDialog(null, error);
	}
}
