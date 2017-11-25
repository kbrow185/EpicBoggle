package bogglePackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Dictionary {

	BufferedReader fileIn;
	ArrayList<String> wordList;

	public Dictionary() throws Exception {
		try {
			fileIn = new BufferedReader(new FileReader("/Assets/dictionary.txt"));

			// Grabbed this line from:
			// https://stackoverflow.com/questions/11607270/how-to-check-whether-given-string-is-a-word
			// It grabs the next line and verifies if it is the end of the file.
			String line;
			while ((line = fileIn.readLine()) != null) {
				wordList.add(line);
			}
		} catch (FileNotFoundException e) {
			throw new Exception("Dictionary File not found.");
		} catch (IOException e) {
			throw new Exception("Unable to import word.");
		}
	}

	public boolean findWord(String word) {

		return (Collections.binarySearch(wordList, word) >= 0);

	}

}
