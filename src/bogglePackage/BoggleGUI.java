package bogglePackage;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import java.awt.Font;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Component;
import javax.swing.SwingConstants;
import org.json.JSONObject;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class BoggleGUI {

	private JFrame frame;
	private ArrayList<ArrayList<Integer>> submittedWordPositions;
	private ArrayList<JSONObject> submittedCommands;
	private ArrayList<Integer> currentWord;
	private int playerScore;
	private HashMap<String, Component> componentMap;

	public BoggleGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		playerScore = 0;
		submittedWordPositions = new ArrayList<ArrayList<Integer>>();
		submittedCommands = new ArrayList<JSONObject>();
		
		currentWord= new ArrayList<Integer>();
				
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBounds(100, 100, 425, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem connectToServerButton = new JMenuItem("ConnectToServer");
		connectToServerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JSONObject connect = new JSONObject();
				connect.put("login", "Kenyon");
				submittedCommands.add(connect);
			}
		});
		mnFile.add(connectToServerButton);

		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				frame.setVisible(false);
				frame.dispose();
			}
		});
		mnFile.add(mntmQuit);

		JMenu mnGame = new JMenu("Game");
		menuBar.add(mnGame);

		JMenuItem startGameButton = new JMenuItem("Start Game");
		startGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JSONObject connect = new JSONObject();
				connect.put("play", "play");
				submittedCommands.add(connect);
			}
		});
		mnGame.add(startGameButton);

		/*
		 * JMenuItem joinGameButton = new JMenuItem("Join Game");
		 * joinGameButton.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent arg0) {
		 * 
		 * JSONObject connect = new JSONObject(); connect.put("play", "play");
		 * submittedCommands.add(connect); } }); mnGame.add(joinGameButton);
		 */
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(10, 11, 269, 243);
		frame.getContentPane().add(panel);
		panel.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		JButton letterTile0 = new JButton("Z");
		letterTile0.setBackground(new Color(255, 215, 0));
		letterTile0.setFont(new Font("Georgia", Font.BOLD, 38));
		componentMap.put("letterTile0", letterTile0);
		letterTile0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addLetter(0);
			}
		});
		panel.add(letterTile0, "2, 2");

		JButton letterTile1 = new JButton("Z");
		letterTile1.setBackground(new Color(255, 215, 0));
		letterTile1.setFont(new Font("Georgia", Font.BOLD, 38));
		componentMap.put("letterTile1", letterTile1);
		letterTile1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addLetter(1);
			}
		});
		panel.add(letterTile1, "4, 2");

		JButton letterTile2 = new JButton("Z");
		letterTile2.setBackground(new Color(255, 215, 0));
		letterTile2.setFont(new Font("Georgia", Font.BOLD, 38));
		componentMap.put("letterTile2", letterTile2);
		letterTile2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addLetter(2);
			}
		});
		panel.add(letterTile2, "6, 2");

		JButton letterTile3 = new JButton("Z");
		letterTile3.setBackground(new Color(255, 215, 0));
		letterTile3.setFont(new Font("Georgia", Font.BOLD, 38));
		componentMap.put("letterTile3", letterTile3);
		letterTile3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addLetter(3);
			}
		});
		panel.add(letterTile3, "8, 2");

		JButton letterTile4 = new JButton("Z");
		letterTile4.setBackground(new Color(255, 215, 0));
		letterTile4.setFont(new Font("Georgia", Font.BOLD, 38));
		componentMap.put("letterTile4", letterTile4);
		letterTile4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addLetter(4);
			}
		});
		panel.add(letterTile4, "2, 4");

		JButton letterTile5 = new JButton("Z");
		letterTile5.setBackground(new Color(255, 215, 0));
		letterTile5.setFont(new Font("Georgia", Font.BOLD, 38));
		componentMap.put("letterTile5", letterTile5);
		letterTile5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addLetter(5);
			}
		});
		panel.add(letterTile5, "4, 4");

		JButton letterTile6 = new JButton("Z");
		letterTile6.setBackground(new Color(255, 215, 0));
		letterTile6.setFont(new Font("Georgia", Font.BOLD, 38));
		componentMap.put("letterTile6", letterTile6);
		letterTile6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addLetter(6);
			}
		});
		panel.add(letterTile6, "6, 4");

		JButton letterTile7 = new JButton("Z");
		letterTile7.setBackground(new Color(255, 215, 0));
		letterTile7.setFont(new Font("Georgia", Font.BOLD, 38));
		componentMap.put("letterTile7", letterTile7);
		letterTile7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addLetter(7);
			}
		});
		panel.add(letterTile7, "8, 4");

		JButton letterTile8 = new JButton("Z");
		letterTile8.setBackground(new Color(255, 215, 0));
		letterTile8.setFont(new Font("Georgia", Font.BOLD, 38));
		componentMap.put("letterTile8", letterTile8);
		letterTile8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addLetter(8);
			}
		});
		panel.add(letterTile8, "2, 6");

		JButton letterTile9 = new JButton("Z");
		letterTile9.setBackground(new Color(255, 215, 0));
		letterTile9.setFont(new Font("Georgia", Font.BOLD, 38));
		componentMap.put("letterTile9", letterTile9);
		letterTile9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addLetter(9);
			}
		});
		panel.add(letterTile9, "4, 6");

		JButton letterTile10 = new JButton("Z");
		letterTile10.setBackground(new Color(255, 215, 0));
		letterTile10.setFont(new Font("Georgia", Font.BOLD, 38));
		componentMap.put("letterTile10", letterTile10);
		letterTile10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addLetter(10);
			}
		});
		panel.add(letterTile10, "6, 6");

		JButton letterTile11 = new JButton("Z");
		letterTile11.setBackground(new Color(255, 215, 0));
		letterTile11.setFont(new Font("Georgia", Font.BOLD, 38));
		componentMap.put("letterTile11", letterTile11);
		letterTile11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addLetter(11);
			}
		});
		panel.add(letterTile11, "8, 6");

		JButton letterTile12 = new JButton("Z");
		letterTile12.setBackground(new Color(255, 215, 0));
		letterTile12.setFont(new Font("Georgia", Font.BOLD, 38));
		componentMap.put("letterTile12", letterTile12);
		letterTile12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addLetter(12);
			}
		});
		panel.add(letterTile12, "2, 8");

		JButton letterTile13 = new JButton("Z");
		letterTile13.setBackground(new Color(255, 215, 0));
		letterTile13.setFont(new Font("Georgia", Font.BOLD, 38));
		componentMap.put("letterTile13", letterTile13);
		letterTile13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addLetter(13);
			}
		});
		panel.add(letterTile13, "4, 8");

		JButton letterTile14 = new JButton("Z");
		letterTile14.setBackground(new Color(255, 215, 0));
		letterTile14.setFont(new Font("Georgia", Font.BOLD, 38));
		componentMap.put("letterTile14", letterTile14);
		letterTile14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addLetter(14);
			}
		});
		panel.add(letterTile14, "6, 8");

		JButton letterTile15 = new JButton("Z");
		letterTile15.setBackground(new Color(255, 215, 0));
		letterTile15.setFont(new Font("Georgia", Font.BOLD, 38));
		componentMap.put("letterTile15", letterTile15);
		letterTile15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addLetter(15);
			}
		});
		panel.add(letterTile15, "8, 8");

		JTextArea wordPool = new JTextArea();
		wordPool.setEditable(false);
		wordPool.setText("Word List");
		wordPool.setBounds(289, 11, 110, 243);
		frame.getContentPane().add(wordPool);

		JTextPane chatNotificationsBox = new JTextPane();
		chatNotificationsBox.setEditable(false);
		chatNotificationsBox.setText("Chat/ Notifications");
		chatNotificationsBox.setBounds(10, 334, 269, 71);
		frame.getContentPane().add(chatNotificationsBox);

		JTextField enterChatTextBox = new JTextField();
		enterChatTextBox.setBounds(10, 409, 162, 20);
		frame.getContentPane().add(enterChatTextBox);
		enterChatTextBox.setColumns(10);

		JTextField submitWordText = new JTextField();
		submitWordText.setEditable(false);
		submitWordText.setHorizontalAlignment(SwingConstants.CENTER);
		submitWordText.setFont(new Font("Georgia", Font.BOLD, 18));
		submitWordText.setText("Word");
		submitWordText.setForeground(Color.WHITE);
		submitWordText.setBackground(Color.BLUE);
		submitWordText.setBounds(130, 266, 149, 39);
		frame.getContentPane().add(submitWordText);
		submitWordText.setColumns(10);
		
		JButton submitChatButton = new JButton("Submit Chat");
		submitChatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JSONObject chat = new JSONObject();
				chat.put("chat", submitWordText.getText());
				submittedCommands.add(chat);
				submitWordText.setText("");
			}
		});
		submitChatButton.setBounds(182, 408, 97, 23);
		frame.getContentPane().add(submitChatButton);

		

		JTextPane errorMessageText = new JTextPane();
		errorMessageText.setFont(new Font("Georgia", Font.BOLD, 14));
		errorMessageText.setBackground(Color.BLACK);
		errorMessageText.setText("ERROR MESSAGE");
		errorMessageText.setForeground(Color.ORANGE);
		errorMessageText.setBounds(289, 366, 110, 63);
		frame.getContentPane().add(errorMessageText);

		JButton submitButton = new JButton("Submit");
		submitButton.setFont(new Font("Georgia", Font.BOLD, 18));
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					submittedWordPositions.add(currentWord);
					currentWord.clear();
				} catch (Exception e) {

				}
				clearEntry();
			}
		});
		submitButton.setForeground(Color.WHITE);
		submitButton.setBackground(Color.CYAN);
		submitButton.setBounds(289, 265, 110, 40);
		submitButton.setMnemonic(KeyEvent.VK_ENTER);
		frame.getContentPane().add(submitButton);

		JTextPane timerText = new JTextPane();
		timerText.setEditable(false);
		timerText.setForeground(Color.WHITE);
		timerText.setBackground(Color.BLACK);
		timerText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 40));
		timerText.setText("60");
		timerText.setBounds(314, 316, 61, 52);
		frame.getContentPane().add(timerText);

		JButton clearButton = new JButton("Clear");
		clearButton.setBackground(Color.CYAN);
		clearButton.setForeground(Color.WHITE);
		clearButton.setFont(new Font("Georgia", Font.BOLD, 18));
		clearButton.setMnemonic(KeyEvent.VK_ESCAPE);
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearEntry();
			}
		});
		clearButton.setBounds(10, 265, 104, 40);
		frame.getContentPane().add(clearButton);

		JLabel label = new JLabel("============");
		label.setForeground(Color.CYAN);
		label.setBounds(10, 309, 97, 14);
		frame.getContentPane().add(label);

		JLabel label_1 = new JLabel("============");
		label_1.setForeground(Color.CYAN);
		label_1.setBounds(182, 309, 97, 14);
		frame.getContentPane().add(label_1);

		JLabel scoreLabel = new JLabel("000");
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setFont(new Font("Georgia", Font.BOLD, 14));
		scoreLabel.setForeground(Color.CYAN);
		scoreLabel.setBounds(111, 308, 71, 14);
		frame.getContentPane().add(scoreLabel);

		// utilized this for loop from:
		// https://stackoverflow.com/questions/4958600/get-a-swing-component-by-name
		// Is helpful in gathering GUI components by name instead of having a ton of
		// class variables.
		componentMap = new HashMap<String, Component>();
		Component[] components = frame.getContentPane().getComponents();
		for (int i = 0; i < components.length; i++) {
			componentMap.put(components[i].getName(), components[i]);
		}
		frame.setVisible(true);
	}

	private void addLetter(int position) {
		currentWord.add(position);
	}
	
	private void clearEntry() {
		((JTextField) (componentMap.get("submitWordText"))).setText("");
		currentWord.clear();
	}

	public void addToChatBox(String message) {
		JTextPane chatNotification = ((JTextPane) (componentMap.get("chatNotificationsBox")));
		chatNotification.setText(chatNotification.getText() + message + "\n");

	}

	public void setTimer(String time) {
		((JLabel) (componentMap.get("timerText"))).setText(time);
	}

	public ArrayList<ArrayList<Integer>> gatherWords() {
		ArrayList<ArrayList<Integer>> returnWords = new ArrayList<ArrayList<Integer>>(submittedWordPositions);
		submittedWordPositions.clear();
		return returnWords;
	}

	public void notifyUser(String message) {
		((JLabel) (componentMap.get("errorMessageText"))).setText(message);

	}

	public void addToWordList(String word) {
		((JTextArea) (componentMap.get("wordPool"))).append(word + "\n");
	}

	public void setUpBoard(char[] board) {
		((JTextArea) (componentMap.get("wordPool"))).setText("");
		playerScore = 0;
		((JLabel) (componentMap.get("scoreLabel"))).setText(String.valueOf(playerScore));

		for (int i = 0; i < componentMap.size(); i++) {
			((JButton) componentMap.get("Button" + i)).setText(String.valueOf(board[i]));
		}
		notifyUser("Board is ready.");
	}

	public void addPoints(int points) {
		playerScore += points;
		((JLabel) (componentMap.get("scoreLabel"))).setText(String.valueOf(playerScore));
	}

	public ArrayList<JSONObject> getCommands() {
		return submittedCommands;
	}

	public Boolean isRunning() {
		return frame.isVisible();
	}
}
