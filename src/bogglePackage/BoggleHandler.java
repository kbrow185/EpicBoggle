package bogglePackage;

import java.util.ArrayList;

import org.json.JSONArray;
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

					for (ArrayList<Integer> letters : boggleGUI.gatherWords()) {
						if (boggleLogic.checkSubmission(letters)) {
							JSONObject submission = new JSONObject(JsonBuilder.JsonBuilderMethod("guess",new JSONArray(letters)));
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

		String json = message.optString("type");
		if (json.equals("login")) {
			client = new BoggleClient();
			boggleGUI.addToChatBox("Connecting to Server");
			boggleRunning=true;
		} else if (boggleRunning) {
			client.sendMessage(message);
			//boggleGUI.addToChatBox("\n CMD:"+json + "\n");
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
		
		case("APPLICATION"):
			
			JSONObject json = message.getJSONObject("message");
			String action = json.getString("action").toUpperCase();
			//System.out.println("ACTION:" +action);
			
			switch(action) {
			
			case ("CHAT"):
				System.out.println("Chat" +json.optString("chatMessage"));
				String dialog = json.optString("chatMessage");
				boggleGUI.addToChatBox("Chat: " +dialog);
				break;

			case ("WORD"):
				boggleGUI.addToWordList(json.optString("WORD"));
				break;

			case ("STARTGAME"):
				int size = json.optJSONArray("board").length();
				System.out.println("ArraySize:" + size);
				if (size > 0) {
					String letters = "";
					for (Object j : json.optJSONArray("board")) {
						System.out.println(j.toString());
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
		break;
		}

	}
}
