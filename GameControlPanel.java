package MemoryGameGrafic;

import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameControlPanel extends JPanel implements Serializable {

	private static final Font FONT = new Font("Comic Sans MS", Font.PLAIN, 24);
	private JLabel trays;
	private JLabel sucssessedTrays;
	private JButton newGameButton;

	public GameControlPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		newGameButton = new JButton("New game ");
		newGameButton.addActionListener(new NewGameButtonListener());

		add(newGameButton);

		JButton highScoreButton = new JButton("High score");
		highScoreButton.addActionListener(new HighScoreButtonListener());
		add(highScoreButton);

		add(new JLabel("Number of trays:"));
		trays = new JLabel("0");
		trays.setFont(FONT);
		add(trays);

		add(new JLabel("Sucssessed trays:"));
		sucssessedTrays = new JLabel("0");
		sucssessedTrays.setFont(FONT);
		add(sucssessedTrays);

		JButton saveGame = new JButton("Save game ");
		saveGame.addActionListener(new SaveGameButtonListener());
		add(saveGame);

		JButton loadGame = new JButton("Load Game");
		loadGame.addActionListener(new LoadGameButtonListener());
		add(loadGame);
	}

	class SaveGameButtonListener implements ActionListener, Serializable {
		@Override
		public void actionPerformed(ActionEvent e) {
			File save = new File("Save.txt");
			OutputStream os = null;
			ObjectOutputStream oos = null;
			try {
				os = new FileOutputStream(save);
				oos = new ObjectOutputStream(os);
				oos.writeObject(getParent().getParent().getParent().getParent());

			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				try {
					oos.close();
					os.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	class HighScoreButtonListener implements ActionListener, Serializable {
		@Override
		public void actionPerformed(ActionEvent e) {
			File file;
			Scanner sc = null;
			StringBuilder sb;
			try {
				file = new File("highScore.txt");
				sc = new Scanner(file);
				sb = new StringBuilder();

				while (sc.hasNextLine()) {
					sb.append(sc.nextLine() + "\n");
				}
				JOptionPane.showMessageDialog(null, sb.toString(), "High score board", JOptionPane.INFORMATION_MESSAGE);

			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "File is missing!", "Error", JOptionPane.ERROR_MESSAGE);
			} finally {
				if (sc != null) {
					sc.close();
				}
			}
		}
	}

	class LoadGameButtonListener implements ActionListener, Serializable {
		@Override
		public void actionPerformed(ActionEvent e) {
			File save = new File("Save.txt");
			InputStream is = null;
			ObjectInputStream ois = null;

			try {
				is = new FileInputStream(save);
				ois = new ObjectInputStream(is);
				JFrame game = (JFrame) ois.readObject();
				game.repaint();
				game.setVisible(true);

				// Parent frame closing
				(getParent().getParent().getParent().getParent()).dispatchEvent(new WindowEvent(
						(Window) (getParent().getParent().getParent().getParent()), WindowEvent.WINDOW_CLOSING));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} finally {
				try {
					ois.close();
					is.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	class NewGameButtonListener extends JPanel implements ActionListener, Serializable {
		@Override
		public void actionPerformed(ActionEvent e) {
			GameStart.main(null);
			// Parent frame closing
			newGameButton.getParent().getParent().getParent().getParent().getParent()
					.dispatchEvent(new WindowEvent(
							(Window) (newGameButton.getParent().getParent().getParent().getParent().getParent()),
							WindowEvent.WINDOW_CLOSING));
		}
	}

	public JLabel getTrays() {
		return trays;
	}

	public JLabel getSucssessedTrays() {
		return sucssessedTrays;
	}

}
