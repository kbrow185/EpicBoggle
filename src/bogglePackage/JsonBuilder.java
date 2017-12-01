package bogglePackage;

import org.json.JSONArray;

public class JsonBuilder {

	public static String JsonBuilderMethod(String type, String message, String type2, String message2) {
		return "{type:" + type + ", message:" + message + "{" + type2 + ":" + message2 + "}}";

	}// "application","","action","CHAT","chatMessage",enterChatTextBox.getText()

	public static String JsonBuilderMethod(String type, String message, String type2, String message2, String type3,
			String message3) {
		return "{type:" + type + ", message:" + message + "{" + type2 + ":" + message2 + "," + type3 + ":" + message3
				+ "}}";

	}

	public static String JsonBuilderMethod(String type, String Message) {
		return "{type:" + type + ", message:" + Message + "}";

	}

	public static String JsonBuilderMethod(String gameAction, String objectName, JSONArray word) {

		return "{type: 'application', message:" + "{ module: 'Boggle_Of_Epicness'," + "action:" + gameAction + ","
				+ objectName + ":" + word + "}}";

	}

	public static String JsonBuilderMethod(String gameAction) {

		return "{type: 'application', message:" + "{ module: 'Boggle_Of_Epicness'," + "action:" + gameAction + "}}";

	}

}
