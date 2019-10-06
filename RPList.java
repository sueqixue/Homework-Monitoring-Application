import java.util.*;
import java.io.*;
public class RPList
{
	String FName;
	int Anumber;
	int i=0;
	String name;
	String input;
	String password;
	double cash;
	double debt;
	String Q1Q;
	String Q1A;
	String Q2Q;
	String Q2A;
	RoulettePlayer [] RPlist;
	Question [] quest=new Question[2];
	public RPList (String name)
	{
		try
		{
			FName=name;
			File inFile = new File(FName);
			Scanner fileIn=new Scanner(inFile);
			Anumber=fileIn.nextInt();
			RPlist = new RoulettePlayer[Anumber];
			while(fileIn.hasNextLine())
			{
				input=fileIn.nextLine();
				Scanner sc= new Scanner(input).useDelimiter(",");
				if(sc.hasNext())
				{
					name=sc.next();
					password=sc.next();
					cash=sc.nextDouble();
					debt=sc.nextDouble();
					RoulettePlayer RP=new RoulettePlayer(name,password,cash,debt);
					if(sc.hasNext())
					{
						Q1Q=sc.next();
						Q1A=sc.next();
						Q2Q=sc.next();
						Q2A=sc.next();
						if(Q1Q!=null)
						{
							quest[0]=new Question(Q1Q,Q1A);
							quest[1]=new Question(Q2Q,Q2A);
						}
					}
					RP.addQuestions(quest);
					quest = new Question[2];
					RPlist[i]=RP;
					i++;
				}
				
				
			}
			
		}	
		catch (IOException e)
		{
			System.out.println("Problem with file -- cannot read");
		}
	}
	public String toString()
	{
		String temp="";
		for(int n=0; n<i;n++)
		{
			temp=temp+RPlist[n].toString()+"\n";
		}
		return temp;
	}
	public int getSize()
	{
		return i;
	}
	public int getASize()
	{
		return RPlist.length;
	}
	public boolean checkId(String id)
	{
		for(int n=0;n<i;n++)
		{
			if (RPlist[n].getName().equals(id))
			{
				return true;
			}
		}
		return false;
	}
	public RoulettePlayer getPlayerPassword(String id,String pass)
	{
		for(int n=0;n<i;n++)
		{
			if (RPlist[n].getName().equals(id)&&RPlist[n].getP().equals(pass))
			{
				return RPlist[n];
			}
		}
		return null;
	}
	public boolean add(RoulettePlayer P)
	{
		boolean flag=false;
		for(int n=0;n<i;n++)
		{
			if (RPlist[n].getName().equals(P.getName()))
			{
				System.out.println("Already exist. ");
				flag=false;
				return flag;
			}
			else
			{
				flag=true;
			}
		}
		
		if (RPlist.length==i)
		{
			RoulettePlayer [] NewRPlist= new RoulettePlayer [i*2];
			for(int n=0;n<i;n++)
			{
				NewRPlist[n]=RPlist[n];
			}
			RPlist=NewRPlist;
			RPlist[i]=P;
			i++;
		}
		return flag;
		
	}
	public String[] getQuestions(String name)
	{
		for(int n=0;n<i;n++)
		{
			if (RPlist[n].getName().equals(name))
			{
				return RPlist[n].getQuestions();
			}
		}
		return null;
	}
	public RoulettePlayer getPlayerQuestions(String id,  Question [] quest)
	{
		for(int n=0;n<i;n++)
		{
			if (RPlist[n].getName().equals(id))
			{
				if (RPlist[n].matchQuestions(quest))
				{
					return RPlist[n];
				}
			}
		}
		return null;
	}
	public void saveList()
	{
		try
		{
			String temp="";
			PrintWriter wfile=new PrintWriter(FName);
			wfile.println(i);	
			for (int n=0;n<i;n++)
			{
				temp=RPlist[n].saveString();
				wfile.println(temp);
			}
			wfile.close();
		}
		catch (IOException e)
		{
			System.out.println("Problem with file -- cannot read");
		}
	}
	public RoulettePlayer get(int n)
	{
		return RPlist[n];
	}
	public void change(int n, RoulettePlayer P)
	{
		RPlist[n]=P;
	}
	public void setPassword(String ID, String pass)
	{
		for(int n=0;n<i;n++)
		{
			if (RPlist[n].getName().equals(ID))
			{
				RPlist[n].setPassword(pass);
				break;
			}
		}
	}
}
