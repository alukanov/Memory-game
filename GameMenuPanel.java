package MemoryGameGrafic;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameMenuPanel extends JPanel implements Serializable{

	private static final int EASY_GRID_SIZE = 16;
	private static final int MEDIUM_GRID_SIZE = 36;
	private static final int HARD_GRID_SIZE = 64;
	private static final Font FONT = new Font("Comic Sans MS", Font.PLAIN, 24);
	private JFrame menuFrame;
	private JFrame gameFrame;
	private PlatePanel platePanel;

	public GameMenuPanel() {
		menuFrame = new JFrame("Memory game");
		menuFrame.setSize(550, 465);
		menuFrame.setLocation(600, 300);
		menuFrame.setResizable(false);
		menuFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel welcomeLabel = new JLabel(" Welcome to pump up your brain memory game!!!");
		welcomeLabel.setFont(FONT);

		Icon icon = new ImageIcon("icons/start1.png");
		JButton button = new JButton(icon);

		JLabel welcomeLabel1 = new JLabel(" ");
		JLabel welcomeLabel2 = new JLabel("Select dificulty:");
		welcomeLabel2.setFont(FONT);
		JLabel welcomeLabel3 = new JLabel(" ");

		JButton easyButton = new JButton("   EASY 4x4   ");
		easyButton.setFont(FONT);
		JLabel welcomeLabel4 = new JLabel(" ");

		JButton mediumButton = new JButton("MEDIUM 6x6");
		mediumButton.setFont(FONT);
		JLabel welcomeLabel5 = new JLabel(" ");

		JButton hardButton = new JButton("   HARD 8x8   ");
		hardButton.setFont(FONT);

		menuFrame.add(welcomeLabel, BorderLayout.NORTH);
		menuFrame.add(new JLabel("                                                           "), BorderLayout.WEST);

		add(button);
		add(welcomeLabel1);
		add(welcomeLabel2);
		add(welcomeLabel3);
		add(easyButton);
		add(welcomeLabel4);
		add(mediumButton);
		add(welcomeLabel5);
		add(hardButton);

		menuFrame.add(this, BorderLayout.CENTER);

		easyButton.addActionListener(new SizeListener(EASY_GRID_SIZE));
		mediumButton.addActionListener(new SizeListener(MEDIUM_GRID_SIZE));
		hardButton.addActionListener(new SizeListener(HARD_GRID_SIZE));

		menuFrame.setVisible(true);
	}

	private class SizeListener implements ActionListener, Serializable {
		int GRID_SIZE;

		public SizeListener(int GRID_SIZE) {
			super();
			this.GRID_SIZE = GRID_SIZE;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			gameFrame = new JFrame("Memory game");
			gameFrame.setSize(1200, 1024);
			gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			platePanel = new PlatePanel(GRID_SIZE);
			gameFrame.add(platePanel, BorderLayout.CENTER);
			gameFrame.add(platePanel.getGameControlPanel(), BorderLayout.EAST);

			gameFrame.setVisible(true);
			menuFrame.dispatchEvent(new WindowEvent(menuFrame, WindowEvent.WINDOW_CLOSING));

		}

	}
	
}
