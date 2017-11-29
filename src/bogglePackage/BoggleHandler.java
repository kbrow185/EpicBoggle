package bogglePackage;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class BoggleHandler implements Runnable {

	BoggleLogic boggleLogic;
	BoggleClient client;
	BoggleGUI boggleGUI;
	Boolean boggleRunning;
	Boolean connectedToServer;

 	public BoggleHandler() {
 		connectedToServer = false;
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
					boggleGUI.setTimer(boggleLogic.getTime());
					ArrayList<ArrayList<Integer>> letterList = boggleGUI.gatherWords();
					if(letterList.size()>0) {
						for (ArrayList<Integer> letters : letterList) {
							//System.out.println(letters.toString());
							
							for(int i : letters)
								System.out.print(i+",");
							
							
							if (boggleLogic.checkSubmission(letters)) {
								System.out.println("REAL WORD");
								JSONObject submission = new JSONObject(JsonBuilder.JsonBuilderMethod("GUESS",new JSONArray(letters)));
								sendClientMessage(submission);
							}
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
			connectedToServer=true;
		} else if (connectedToServer) {
			client.sendMessage(message);
		}

	}

	public void translateServerMessage(JSONObject message) {

		String response =message.optString("type").toUpperCase();
		
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
			
			switch(action) {
			
			case ("CHAT"):
				//System.out.println("Chat" +json.optString("chatMessage"));
				String dialog = json.optString("chatMessage");
				boggleGUI.addToChatBox("Chat: " +dialog);
				break;

			case ("WORD"):
				boggleGUI.addToWordList(json.optString("WORD"));
				break;

			case ("STARTGAME"):
				int size = json.optJSONArray("board").length();

				if (size > 0) {
					String letters = "";
					for (Object j : json.optJSONArray("board")) {
						letters+= j.toString();
					}
					System.out.println(letters);
					if (!boggleRunning) {
						boggleLogic = new BoggleLogic();
						boggleRunning = true;
					}
					boggleGUI.setUpBoard(letters.toCharArray());
					boggleLogic.resetBoard(letters.toCharArray());
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
