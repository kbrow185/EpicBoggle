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
	}

	@Override
	public void run() {

		while (boggleGUI.isRunning()) {
			try {
				Thread.sleep(500);
				for (JSONObject message : boggleGUI.getCommands()) {
					sendClientMessage(message);
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

		if (message.optString("type").equalsIgnoreCase("login")) {
			client = new BoggleClient();
		} else if (client != null && boggleRunning) {
			client.sendMessage(message);
		}

	}

	public void translateServerMessage(JSONObject message) {

		switch (message.optString("type")) {
		case ("ACK"):

			boggleGUI.addToChatBox(message.getString("message"));
			// Will pull out string message "XXX successfully logged into game"
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
			}
			break;

		case ("GAMEEND"):
			boggleRunning = false;
			boggleLogic.endGame();
			break;

		case ("POINTS"):
			boggleGUI.addPoints(message.optInt("points"));
			break;

		}

	}
}
