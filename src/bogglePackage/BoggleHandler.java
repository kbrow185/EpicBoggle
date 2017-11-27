package bogglePackage;

import java.util.ArrayList;

import org.json.JSONObject;

public class BoggleHandler implements Runnable {

	BoggleLogic boggleLogic;
	BoggleClient client;
	BoggleGUI boggleGUI;
	Boolean boggleRunning;

 	public BoggleHandler() {

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
				for (JSONObject message : client.getResponses()) {
					translateServerMessage(message); 
				}

				if (boggleRunning) {

					for (ArrayList<Integer> words : boggleGUI.gatherWords()) {
						if (boggleLogic.checkSubmission(words)) {
							JSONObject submission = new JSONObject();
							Integer[] positions = (Integer[]) words.toArray(new Integer[words.size()]);
							submission.put("GUESS", positions);
							sendClientMessage(submission);
						}
					}
				}
			} catch (Exception e) {
				boggleGUI.notifyUser(e.getMessage());
			}
		}

	}

	public void sendClientMessage(JSONObject message) {

		String json = message.keys().next();
		if (json.equals("login")) {
			client = new BoggleClient();
			boggleGUI.addToChatBox("Connecting to Server");
			boggleRunning=true;
		} else if (boggleRunning) {
			client.sendMessage(message);
			boggleGUI.addToChatBox("\n CMD:"+json + "\n");
		}

	}

	public void translateServerMessage(JSONObject message) {

		String response =message.optString("type").toUpperCase();
		//boggleGUI.addToChatBox(response);
		
		switch (response) {
		case ("ACKNOWLEDGE"):

			boggleGUI.addToChatBox(message.getString("message"));
			break;

		case ("DENY"):
			boggleGUI.addToChatBox(message.getString("message"));
			break;

		case ("CHAT"):
			String userName = message.optString("username");
			String dialog = message.optString("message");
			boggleGUI.addToChatBox(userName + ": " + dialog);
			break;

		case ("WORD"):
			boggleGUI.addToWordList(message.optString("word"));
			break;

		case ("GAMESTART"):
			int size = message.optJSONArray("gamestart").length();

			if (size > 0) {
				String letters = "";
				for (Object j : message.optJSONArray("gamestart")) {
					letters.concat(j.toString());
				}
				if (!boggleRunning) {
					boggleLogic = new BoggleLogic();
					boggleRunning = true;
				}
				boggleGUI.setUpBoard(letters.toCharArray());
				boggleGUI.addToChatBox("BOARD SETUP");
			}
			break;

		case ("GAMEEND"):
			boggleRunning = false;
			boggleLogic.endGame();
			boggleGUI.addToChatBox("GAME HAS ENDED");
			break;

		case ("POINTS"):
			boggleGUI.addPoints(message.optInt("points"));
			break;

		}

	}
}
