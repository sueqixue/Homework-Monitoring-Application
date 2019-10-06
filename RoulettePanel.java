import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RoulettePanel extends JPanel implements Activatable
{
    private RoulettePlayer P;
    private JFrame JF;
    private GameInterface L_temp;
    private JFrame theWindow;
	private JPanel leftPanel,rightPanel;
	private JButton setButton, checkButton, spinButton,quitButton, continueButton;
	private final Font myFont = new Font("TimesRoman", Font.BOLD, 40);
    private JLabel theMessage;
    private Activatable A;
    private JTextArea info;
    private RouletteWheel theWheel;
    private double money=0.0;
    private String type,choose;
    //private RouletteBet bet=null;
    private char wtype='f';
	private RPList RP=new RPList("players.txt");
	private String [] str;
	private int count=0;
    public RoulettePanel(RoulettePlayer RP, GameInterface L)
    {
        P=RP;
        L_temp=L;
        Object[] options = {"OK"};
        /*int optionAdd = JOptionPane.showOptionDialog(null,
                                           "Welcome to EFIC Study Monitoring System","Message",
                                           JOptionPane.PLAIN_MESSAGE,
                                           JOptionPane.QUESTION_MESSAGE,
                                           null,
                                           options,
                                           options[0]);*/

	    String s = JOptionPane.showInputDialog(null, "Welcome to EFIC Study Monitoring System. \nPlease enter the number of today's assigments: ");
        while (true)
        {
            if (!isNumeric(s) || s.equals("0")) {
                s = JOptionPane.showInputDialog(null, "Please enter a valid number (>= 1): ");
            }
            else {
                break;
            }
        }
        int size = Integer.parseInt(s);
        str = new String[size];

        while (count != size)
        {
         count++;
         String myWork=JOptionPane.showInputDialog(null,"Enter the link/Drag the "+ count +" file: ");
         str[count-1] = myWork;
         
        }

        /*if (optionAdd==1)
        {
           System.exit(0);
        }*/

        theMessage = new JLabel("Schedual of "+ RP.getName());
  	    theMessage.setFont(myFont);
        ActionListener listen = new ButtonListener();

 	    setButton = new JButton("Start");  
 	    setButton.setFont(myFont);
  	    setButton.addActionListener(listen);
  
 	    spinButton = new JButton("pause");  
        spinButton.setFont(myFont);
        spinButton.addActionListener(listen);
        spinButton.setEnabled(false);

        continueButton=new JButton("continue");
        continueButton.setFont(myFont);
        continueButton.addActionListener(listen);
        continueButton.setEnabled(false);
  
  		checkButton = new JButton("Next");  
  		checkButton.setFont(myFont);
  		checkButton.addActionListener(listen);
  		checkButton.setEnabled(false);

        quitButton=new JButton("Quit");
        quitButton.setFont(myFont);
  		quitButton.addActionListener(listen);
  		quitButton.setEnabled(false);

  
  		leftPanel =new JPanel(new GridLayout(6,1));
        leftPanel.add(theMessage);
        leftPanel.add(setButton);
        leftPanel.add(spinButton);
        leftPanel.add(continueButton);
        leftPanel.add(checkButton);
        leftPanel.add(quitButton);
        
        theWheel = new RouletteWheel(this);

        this.setPreferredSize(new Dimension(700, 300));
        this.setLayout(new BorderLayout());
        this.add(leftPanel,BorderLayout.WEST);
        this.add(theWheel,BorderLayout.EAST);
       
    }
    private class ButtonListener implements ActionListener
	{
		int count=0;
		public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == setButton)
            {
                theWheel.get_job(str);
                theMessage.setText("Start studying!!!");
                setButton.setEnabled(false);
                spinButton.setEnabled(true);
                checkButton.setEnabled(true);
                quitButton.setEnabled(true);
                continueButton.setEnabled(false);
                theWheel.set();
                
            }
            else if (e.getSource() == spinButton)
            {
                theWheel.spin();
                spinButton.setEnabled(false);
                continueButton.setEnabled(true);
                
            }
            else if (e.getSource()== continueButton)
            {
                theWheel.keepgoing();
                spinButton.setEnabled(true);
                continueButton.setEnabled(false);
                
            }
            else if (e.getSource() == checkButton)
            {
                //theWheel.reset_label();
                checkButton.setEnabled(false);
                count=theWheel.what_next();
                theWheel.go_one_more();
                Object[] options = {"OK"};

                int sub=str.length-count;
                int optionAdd = JOptionPane.showOptionDialog(null,
                                           "Congratulation! " +count+" finished "+ sub+" more." ,"Message",
                                           JOptionPane.PLAIN_MESSAGE,
                                           JOptionPane.QUESTION_MESSAGE,
                                           null,
                                           options,
                                           options[0]);
                if (optionAdd==1)
                {
                    System.exit(0);
                }

                theWheel.set();

                if(count==str.length)
                {
                    String s = JOptionPane.showInputDialog(null, "All homework are done. DO u wanna to keep going? Y/N");
                    while(!s.equals("y")&&!s.equals("n"))
                    {
                         s = JOptionPane.showInputDialog(null, "please enter Y/N");
                    }
                    if(s.equals("y"))
                    {
                        setButton.setEnabled(true);
                    }
                }
                else
                {
                    setButton.setEnabled(true);
                }
            }
            else
            {
                count=theWheel.what_next();
                if(count<str.length)
                {
                    String s = JOptionPane.showInputDialog(null, "Does not finish assignment yet. Are u sure to exit? y/n");
                    while(!s.equals("y")&&!s.equals("n"))
                    {
                         s = JOptionPane.showInputDialog(null, "please enter Y/N");
                    }
                    if(s.equals("y"))
                    {
                        setButton.setEnabled(true);
                        L_temp.gameOver();
                    }

                }
                else
                {
                    L_temp.gameOver();
                }
                
            }
        }
	}

    public boolean isNumeric(String strNum)
    {
        try
        {
            double d = Double.parseDouble(strNum);
        } 
        catch (NumberFormatException | NullPointerException nfe) 
        {
            return false;
        }
        return true;
    }
    
    // Called by the RouletteWheel class after the spin() method has completed.
	// This method can be called because a reference to the WheelTest object is
	// passed to the RouletteWheel in the constructor (see above).  In this case
	// the method enables the checkButton so that the results can be checked and
	// the setButton so that the wheel can be reset if desired.
	public void activate()
	{
		checkButton.setEnabled(true);
		setButton.setEnabled(true);
	}
}
	
	
		
