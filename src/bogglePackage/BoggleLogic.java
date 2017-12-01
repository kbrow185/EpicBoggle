package bogglePackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.Timer;

public class BoggleLogic {

	private Boolean running;
	private int timeRemaining;
	private Timer gameTimer;
	private char[] boardLetters;
	private int boardWidth;
	private Dictionary dictionary;

	public BoggleLogic() throws FileNotFoundException {

		try {
			dictionary = new Dictionary();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Dictionary File Not Found.");
		}

		running = false;
		ActionListener timerTask = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (timeRemaining > 0) {
					timeRemaining--;
				} else {
					endGame();
				}

			}
		};
		gameTimer = new Timer(1000, timerTask);
	}

	public void resetBoard(char[] letters) {
		boardWidth = (int) Math.sqrt(letters.length);
		boardLetters = letters;
		timeRemaining = 60;
		gameTimer.restart();
		gameStart();
	}

	public void endGame() {
		running = false;
		gameTimer.stop();
	}

	public String getTime() {
		return String.valueOf(timeRemaining);
	}

	private void gameStart() {
		running = true;
		gameTimer.start();
	}

	public boolean checkSubmission(ArrayList<Integer> positions) {

		Boolean valid = false;

		if (running) {
			StringBuilder word = new StringBuilder();

			for (int spot : positions) {
				word.append(boardLetters[spot]);
			}
			valid = (checkWord(word.toString()) && checkPositions(positions));

		}

		return valid;
	}

	private boolean checkWord(String word) {
		return dictionary.findWord(word);
	}

	private boolean checkPositions(ArrayList<Integer> positions) {

		boolean result = true;

		for (int i = 1; i < positions.size(); i++) {

			if (!(isPositionValid(positions.get(i - 1), positions.get(i)))) {
				result = false;
			}
		}
		return result;

	}

	private boolean isPositionValid(int firstValue, int nextValue) {

		ArrayList<Integer> possibleSpots = new ArrayList<Integer>(Arrays.asList(-boardWidth - 1, -boardWidth,
				-boardWidth + 1, -1, 1, boardWidth - 1, boardWidth, boardWidth + 1));
		Integer n = nextValue - firstValue;

		if (possibleSpots.contains(n)) {
			// new ArrayList<Integer>(Arrays.asList(dims.clone());

			// Checks for ]top of board
			if (!(firstValue % boardWidth == 0)) {
				checkAndRemove(possibleSpots, -5);
				checkAndRemove(possibleSpots, -1);
				checkAndRemove(possibleSpots, 3);
			}
			// checks for bottom of board
			if (firstValue + 1 % boardWidth == 0) {
				checkAndRemove(possibleSpots, -3);
				checkAndRemove(possibleSpots, 1);
				checkAndRemove(possibleSpots, 5);

			}
			// checks for top of board
			if (firstValue < boardWidth) {
				checkAndRemove(possibleSpots, -5);
				checkAndRemove(possibleSpots, -4);
				checkAndRemove(possibleSpots, -3);

			}
			// checks for bottom of board
			int roof = boardWidth * (boardWidth - 1);
			if (firstValue >= (roof)) {
				checkAndRemove(possibleSpots, 3);
				checkAndRemove(possibleSpots, 4);
				checkAndRemove(possibleSpots, 5);
			}
		}

		return (possibleSpots.contains(n));

	}

	private void checkAndRemove(ArrayList<Integer> list, int item) {
		if (list.contains(item)) {
			list.remove(list.indexOf(item));
		}
	}

}
