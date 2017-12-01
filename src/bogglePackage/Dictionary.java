package bogglePackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class Dictionary {

	BufferedReader fileIn;
	ArrayList<String> wordList;

	public Dictionary() throws FileNotFoundException {
		try {
			wordList = new ArrayList<String>();
			fileIn = new BufferedReader(new FileReader(new File(getClass().getResource("/dictionary.txt").toURI())));

			// Grabbed this line from:
			// https://stackoverflow.com/questions/11607270/how-to-check-whether-given-string-is-a-word
			// It grabs the next line and verifies if it is the end of the file.
			String line;
			while ((line = fileIn.readLine()) != null) {
				wordList.add(line);
			}
		} catch (Exception e) {
			throw new FileNotFoundException();
		}
	}

	public boolean findWord(String word) {

		return (Collections.binarySearch(wordList, word.toUpperCase()) >= 0);

	}

}
