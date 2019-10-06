import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class hackathon implements LoginInterface, GameInterface
{
	// Note the variables -- we have variables for the window and all of
	// the various panels.  Not all of these will be visible at the same
	// time however.  We also have 3 buttons which will initially be shown
	// to the user.
	private JFrame theWindow;
	private RoulettePanel theGame;
	private LoginPanel theLogin;
	private JPanel buttonPanel;
	private JButton quitButton, loginButton, playButton;
	private RoulettePlayer P;
	private RPList PL;
	
	public hackathon()
	{
		theWindow = new JFrame("EFIC");
		PL = new RPList("users.txt");

		loginButton = new JButton("Login EFIC");
		loginButton.setFont(new Font("Serif", Font.BOLD, 35));
		playButton = new JButton("Start suffering");
		playButton.setFont(new Font("Serif", Font.BOLD, 35));
		playButton.setEnabled(false); // cannot play until we have a Player
		quitButton = new JButton("Quit EFIC");
		quitButton.setFont(new Font("Serif", Font.BOLD, 35));
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2,1));
		buttonPanel.add(quitButton);
		buttonPanel.add(loginButton);
		//buttonPanel.add(playButton);
		
		ActionListener theListener = new GameListener();
		quitButton.addActionListener(theListener);
		loginButton.addActionListener(theListener);
		playButton.addActionListener(theListener);
		
		theWindow.setLayout(new GridLayout(1,3));
		theWindow.add(buttonPanel);

		theWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		theWindow.setSize(400,200);
		theWindow.setLocationRelativeTo(null);
		theWindow.setResizable(false);
		theWindow.setVisible(true);
		
	}
    private class GameListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == loginButton)
			{
				if (P != null)
				{
					JOptionPane.showMessageDialog(theWindow, P.getName() +
						" quitting without learning ");
				}
				// Create the LoginPanel and add it to the window.
				theLogin = new LoginPanel(PL, hackathon.this);
				theWindow.remove(buttonPanel);
				theWindow.add(theLogin);
				theWindow.pack();
			}
			else if (e.getSource() == playButton)
			{
				// Create the RoulettePanel and add it to the window.
				theGame = new RoulettePanel(P, hackathon.this);
				theWindow.remove(buttonPanel);
				theWindow.add(theGame);
				theWindow.pack();
			}
			else if (e.getSource() == quitButton)
			{
				if (P != null)
				{
					JOptionPane.showMessageDialog(theWindow, "Player " + P.getName() +
						" quitting without playing ");
				}
				PL.saveList();
				System.exit(0);
			}		
		}
	}
	
	// This method will be called from the LoginPanel after the RoulettePlayer
	// has been selected.  This allows the RoulettePlayer to be passed back to
	// this program.
	public void setPlayer(RoulettePlayer pl)
	{
		P = pl;
		buttonPanel.remove(loginButton);
		buttonPanel.add(playButton);
		playButton.setEnabled(true);
		playButton.setText("Start Suffering LOL");
		theWindow.remove(theLogin);
		theWindow.add(buttonPanel);
		theWindow.setLocationRelativeTo(null);
		theWindow.setResizable(false);
		theWindow.setSize(400,200);
	}
	
	// This method will be called from the RoulettePanel when it is finished with the
	// game.  It removes the RoulettePanel, adds back the buttonPanel and sets the
	// RoulettePlayer P back to null.
	public void gameOver()
	{
		theWindow.remove(theGame);
		theWindow.add(buttonPanel);
		P = null;
		playButton.setText("Start Game");
		playButton.setEnabled(false);
		theWindow.setLocationRelativeTo(null);
		theWindow.setResizable(false);
		theWindow.setSize(400,200);
		theWindow.repaint();
		
	}
	
	public static void main(String [] args)
	{
		UIManager.put("OptionPane.messageFont", new Font("TimesRoman", Font.BOLD, 35));
		UIManager.put("OptionPane.buttonFont", new Font("TimesRoman", Font.PLAIN, 35));
		UIManager.put("TextField.font", new Font("TimesRoman", Font.PLAIN, 35));
		new hackathon();
	}
}