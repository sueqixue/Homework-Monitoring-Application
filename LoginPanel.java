import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class LoginPanel extends JPanel
{
	private JButton SumbitButton;
    private JTextField JPlayerName;
    private JPasswordField JPlayerPassword;
    private RPList RP;
    private JFrame theWindow;
    private JPanel topPanel;
	private JPanel bottomPanel;
    private JPanel belowPanel;
    private JLabel theMessage,thePassword,theID;
    private JButton b1,b2;
    private String PlayerName,PlayerPassword;
    private ControlListener theListener;
    private final Font myFont = new Font("TimesRoman", Font.BOLD, 20);
    private boolean flag=false;
    private RoulettePlayer P = new RoulettePlayer();
    private LoginInterface L_temp;
    public LoginPanel( RPList RP1, LoginInterface L)
    {
        RP=RP1;
        L_temp=L;
        topPanel = new JPanel();
        theMessage = new JLabel("Please login to the EFIC");
        thePassword=new JLabel(" Password: ");
        theID=new JLabel(" User ID: ");
		theMessage.setFont(myFont);
        thePassword.setFont(myFont);
        theID.setFont(myFont);
        topPanel.add(theMessage);
        bottomPanel=new JPanel(new GridLayout(2,2));
		//loadData();
        JPlayerName= new JTextField(10);
		JPlayerPassword=new JPasswordField(10);
        PlayerName=JPlayerName.getText();
        PlayerPassword=new String(JPlayerPassword.getPassword());
        
        bottomPanel.add(theID);
        bottomPanel.add(JPlayerName);
        bottomPanel.add(thePassword);
        bottomPanel.add(JPlayerPassword);
        
        belowPanel= new JPanel(new GridLayout(1,2));
        b1 = new JButton("Submit");  b1.setFont(myFont);
        b2 = new JButton("Cancel");  b2.setFont(myFont);
        belowPanel.add(b1);
        belowPanel.add(b2);
        theListener = new ControlListener();
		b1.addActionListener(theListener);
        b2.addActionListener(theListener);

        this.setLayout(new BorderLayout());
        this.add(topPanel,BorderLayout.NORTH);
		this.add(bottomPanel);
        this.add(belowPanel,BorderLayout.SOUTH);
       
    }
    	private class ControlListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JButton theEventer=(JButton)e.getSource();
            if (e.getSource() == b1)
            {
                 PlayerName=JPlayerName.getText();
                 PlayerPassword=new String(JPlayerPassword.getPassword());
                 boolean foundId = RP.checkId(PlayerName);
                if (foundId)//check ID and password(in case that username already exist)
                {
                    P = RP.getPlayerPassword(PlayerName, PlayerPassword);
				    if (P != null)
				    {
                        flag=true;
                        Object[] options = {"OK"};
                        /*int optionAdd = JOptionPane.showOptionDialog(null,
                                                "Welcome back "+P.getName()+" !","Let us start the learning process!",
                                                JOptionPane.PLAIN_MESSAGE,
                                                JOptionPane.QUESTION_MESSAGE,
                                                null,
                                                options,
                                                options[0]);
                        if (optionAdd==1)
                        {
                            System.exit(0);
                        }*/
                        L_temp.setPlayer(P);
                    }
                    else
                    {
                        
                        Object[] options = {"OK"};
                        int optionAdd = JOptionPane.showOptionDialog(null,
                                                "Password does not match "+PlayerName,"Login Failed",
                                                JOptionPane.PLAIN_MESSAGE,
                                                JOptionPane.QUESTION_MESSAGE,
                                                null,
                                                options,
                                                options[0]);
                        if (optionAdd==1)
                        {
                            System.exit(0);
                        }
                    }
                }
                    else
                    {
                         Object[] options = {"OK"};
                            int optionAdd = JOptionPane.showOptionDialog(null,
                                                "ID "+PlayerName+" was not found!","Login Failed",
                                                JOptionPane.PLAIN_MESSAGE,
                                                JOptionPane.QUESTION_MESSAGE,
                                                null,
                                                options,
                                                options[0]);
                        if (optionAdd==1)
                        {
                                System.exit(0);
                        }
                        
                    }
            }
            else
            {
                    
                flag=true;
                System.exit(0);
            }   
            
		}
    } 
    

    //private void loadData()
	//{
		
		//RP=new RPList("players.txt");
	//}
    //public static void main(String[]args)
    //{
        //new LoginPanel();
    //}
    
}