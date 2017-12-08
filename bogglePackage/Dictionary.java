package bogglePackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Dictionary {

	private static String[] wordList;
	private static boolean exists;

	static {
		try {
			BufferedReader fileIn = new BufferedReader(
					new FileReader(new File(Dictionary.class.getResource("/dictionary.txt").toURI())));
			// Grabbed this line from:
			// https://stackoverflow.com/questions/11607270/how-to-check-whether-given-string-is-a-word
			// It grabs the next line and verifies if it is the end of the file.
			String line;
			ArrayList<String> words = new ArrayList<String>();
			while ((line = fileIn.readLine()) != null) {
				words.add(line);
			}
			exists = true;
			fileIn.close();
			wordList = (String[]) words.toArray(new String[0]);

		} catch (Exception e) {
			exists = false;
		}
	}

	public static boolean dictionaryExists() {
		return exists;
	}

	public static boolean findWord(String word) {
		return (dictionaryExists() && (Arrays.binarySearch(wordList, word.toUpperCase()) >= 0));
	}

}
