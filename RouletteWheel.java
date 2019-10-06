import javax.swing.*;
import java.awt.*;
import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class RouletteWheel extends JPanel implements Runnable
{

	// Need instance data.  Note that fundamentally your
	// RouletteWheel should contain an array of RouletteSquare,
	// initilized to each of the possible values on the wheel.
	private JPanel NumberPanel;

	// Example Times, calculate by the some machine learning algorithems
	private int[] timeExamples = new int[] {300, 200, 123, 10, 52, 123, 10, 98, 123, 10, 100, 123, 10, 52, 123, 10, 98, 123};
	
	private Activatable Act;
	private long delay=300;
	private long duration=5000;
	private int number;
	private boolean flag=false;
	private int seconds;
	private String [] subject;
	private int count = 1;
	private Timer timer;
	private final Font myFont = new Font("TimesRoman", Font.BOLD, 40);
    public RouletteWheel(Activatable X)
    {
		NumberPanel=new JPanel (new GridLayout(2,1));
		Act=X;
		//this.setLayout(new BorderLayout());
		this.add(NumberPanel);
		
       // Create a RouletteWheel.  The argument here is an Activatable
	   // that allows for the method call activate().  This reference
	   // should be stored and the activate() method should be called at
	   // the end of a wheel spin.
	  
    }

	public void set()
	{
		
		JLabel to_do=new JLabel();
		to_do.setText(subject[count-1]);
		seconds = timeExamples[count-1];
		to_do.setFont(myFont);
		NumberPanel.add(to_do);
		final JLabel label = new JLabel();
        NumberPanel.add(label,-1);
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
			{
                seconds--;
                int day = (int) TimeUnit.SECONDS.toDays(seconds);
                long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
                long minute = TimeUnit.SECONDS.toMinutes(seconds)
                        - (TimeUnit.SECONDS.toHours(seconds) * 60);
                long second = TimeUnit.SECONDS.toSeconds(seconds)
                        - (TimeUnit.SECONDS.toMinutes(seconds) * 60);
                label.setText(hours + " Hour(s), " + minute + " Minute(s) and "
                        + second + " Second(s)");
                if (seconds == 0) {
                    doSomething();
                }
				
            }
        });
        timer.start();

	}
	
	public void spin()
    {
        // Spin the wheel using a Thread and the fact that RouletteWheel
        // implements the Runnable interface.  For an example of using the
        // Runnable interface see RunnableTest.java.  After the run() method
        // completes the activate() method should be called (using the reference
        // passed in from the constructor).
        timer.stop();
    }

    public void keepgoing()
    {
        timer.restart();
    }

    public void doSomething()
    {
        timer.stop();
        Object[] options = {"OK"};
        int optionAdd = JOptionPane.showOptionDialog(null,
                                           "Time up! Next homework","Message",
                                           JOptionPane.PLAIN_MESSAGE,
                                           JOptionPane.QUESTION_MESSAGE,
                                           null,
                                           options,
                                           options[0]);

         if (optionAdd==1)
        {
           System.exit(0);
        }
        flag=false;
        NumberPanel.revalidate();
        NumberPanel.repaint();
    }

    public boolean time_up()
    {
        return flag;
    }
    public void get_job(String [] remain)
    {
        subject=remain;
    }

    public void go_one_more()
    {
        count++;
    }
    public int what_next()
    {
        return count;
    }
	
	
	public void run()
	{
		// implementation of the Runnable interface.  This should select 
		// RouletteSquare objects in a random way for a certain amount of time,
		// so that visually the user sees the wheel "moving".  This is done in
		// the run() method rather than the spin() method directly so that it
		// can be run in a Thread asynchronously
		Act.activate();
	}

}