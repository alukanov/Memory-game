package MemoryGameGrafic;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Scanner;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PlatePanel extends JPanel implements Serializable {

	private final int GRID_SIZE;
	private final int ROWS;
	private int score;
	private int numOfTrays;
	private int sucssesPercent;
	private Buttons[] plateButtons;
	private Buttons firstPressedButton;
	private Buttons secondPressedButton;
	private IconArray newIconArray;
	private GameControlPanel gameControlPanel;

	public PlatePanel(int GRID_SIZE) {

		this.GRID_SIZE = GRID_SIZE;
		ROWS = (int) (Math.sqrt(GRID_SIZE));
		plateButtons = new Buttons[GRID_SIZE];
		newIconArray = new IconArray(GRID_SIZE);
		gameControlPanel = new GameControlPanel();

		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(ROWS);
		setLayout(gridLayout);

		for (int i = 0; i < GRID_SIZE / 2; i++) {
			this.plateButtons[i] = new Buttons(newIconArray.getIcons()[i], "" + i);
			this.plateButtons[i].addActionListener(new NameButtonListener());
		}
		for (int i = GRID_SIZE / 2, j = 0; i < GRID_SIZE; i++, j++) {
			this.plateButtons[i] = new Buttons(newIconArray.getIcons()[i], "" + j);
			this.plateButtons[i].addActionListener(new NameButtonListener());
		}
		this.shufflePlateButtons();

		for (int i = 0; i < GRID_SIZE; i++) {
			add(this.plateButtons[i]);
		}
	}

	private void shufflePlateButtons() {
		Buttons tmp = null;
		for (int i = 0; i < 1000; i++) {
			int firstCardnumber = (int) (Math.random() * GRID_SIZE);
			int secondCardNumber = (int) (Math.random() * GRID_SIZE);
			tmp = this.plateButtons[firstCardnumber];
			this.plateButtons[firstCardnumber] = this.plateButtons[secondCardNumber];
			this.plateButtons[secondCardNumber] = tmp;
		}
	}

	public class NameButtonListener implements ActionListener, Serializable {
		@Override
		public void actionPerformed(ActionEvent e) {

			Buttons button = (Buttons) e.getSource();

			if (firstPressedButton == null) {
				firstPressedButton = button;
				firstPressedButton.setIcon(firstPressedButton.icon);
				return;
			}
			if (secondPressedButton == null) {
				secondPressedButton = button;
				secondPressedButton.setIcon(secondPressedButton.icon);
				return;
			}
			if ((firstPressedButton != button) && (secondPressedButton != button)
					&& (firstPressedButton != secondPressedButton)) {
				if (firstPressedButton.getName().equals(secondPressedButton.getName())) {
					firstPressedButton.setEnabled(false);
					secondPressedButton.setEnabled(false);
					firstPressedButton = button;
					firstPressedButton.setIcon(firstPressedButton.icon);
					secondPressedButton = null;
					score++;
					numOfTrays++;
					gameControlPanel.getSucssessedTrays().setText("" + score);

					// Win message and high score update

					if (score == (GRID_SIZE / 2 - 1)) {
						score++;
						numOfTrays++;
						gameControlPanel.getSucssessedTrays().setText("" + score);
						gameControlPanel.getTrays().setText("" + numOfTrays);
						sucssesPercent = (int) (((double) score / (double) numOfTrays) * 100);
						Icon win = new ImageIcon("icons/win.png");
						JOptionPane.showMessageDialog(null, "You win! Your sucssess rate is: " + sucssesPercent + " %!",
								"You win!", JOptionPane.INFORMATION_MESSAGE, win);
						updateHighScoreFile();
					}

				} else {
					firstPressedButton.setIcon(null);
					firstPressedButton = button;
					firstPressedButton.setIcon(firstPressedButton.icon);
					secondPressedButton.setIcon(null);
					secondPressedButton = null;
					numOfTrays++;
				}
			}
			if ((firstPressedButton == secondPressedButton) || (secondPressedButton == button)) {
				firstPressedButton.setIcon(null);
				firstPressedButton = null;
				secondPressedButton.setIcon(null);
				secondPressedButton = null;
				numOfTrays++;
			}
			gameControlPanel.getTrays().setText("" + numOfTrays);
		}
	}

	private void updateHighScoreFile() {
		File file;
		Scanner sc = null;
		PrintStream output = null;
		StringBuilder sb;

		try {
			int percentInASCII;
			String string;
			String subString;
			boolean isAdded = false;

			file = new File("highScore.txt");
			sc = new Scanner(file);
			sb = new StringBuilder();

			while (sc.hasNextLine()) {
				string = sc.nextLine();
				percentInASCII = string.lastIndexOf(37);
				subString = string.substring(percentInASCII - 3, percentInASCII);
				subString = subString.trim();
				int currentSucssesPercent = Integer.parseInt(subString);
				if ((sucssesPercent > currentSucssesPercent) && (!isAdded)) {
					String stringName = JOptionPane.showInputDialog(null, "New high score! Input your name!",
							"New high score!", JOptionPane.INFORMATION_MESSAGE);
					string = string.replaceAll("(\\w+) ", stringName);
					string = string.replaceAll("(\\d+)%", "  " + sucssesPercent + "%");
					sb.append(string + "\n");
					isAdded = true;
				} else {
					sb.append(string + "\n");
				}

			}
			JOptionPane.showMessageDialog(null, sb.toString(), "High score board", JOptionPane.INFORMATION_MESSAGE);

			sc.close();

			output = new PrintStream(file);
			output.print(sb.toString());

		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "File is missing!", "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			if (output != null) {
				output.close();
			}
			if (sc != null) {
				sc.close();
			}
		}
	}

	public GameControlPanel getGameControlPanel() {
		return gameControlPanel;
	}

	public int getScore() {
		return score;
	}

	public int getNumOfTrays() {
		return numOfTrays;
	}

}
