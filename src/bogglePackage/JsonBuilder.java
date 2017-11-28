package bogglePackage;

public class JsonBuilder {
	
	public static String JsonBuilderMethod(String type, String message, String type2, String message2) {
		return "{type:"+type+", message:" +message +"{"+type2+":"+message2+"}}";
		
	}
	public static String JsonBuilderMethod(String type, String Message) {
		return "{type:"+type+", message:" +Message+"}";
		
	}
	public static String JsonBuilderMethod(String gameAction, Object actionInfo) {
		
		return "{type: 'application', message:"
				+ "{ module: 'Boggle_Of_Epicness',"
				+ "action:"+ gameAction+","+actionInfo.toString()+ "}}";
					
	}
	public static String JsonBuilderMethod(String gameAction) {
		
		return "{type: 'application', message:"
				+ "{ module: 'Boggle_Of_Epicness',"
				+ "action:"+ gameAction+ "}}";
					
	}

}
