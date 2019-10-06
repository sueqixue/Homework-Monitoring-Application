import java.util.*;
public class RoulettePlayer
{
	protected String name;
	protected String password;
	protected double cash;
	protected double debt;
	protected Question [] question;
	protected String [] s=new String[2];
	boolean flag;
	public RoulettePlayer(String IN,String IP,double IC, double ID)
	{
		name=IN;
		password=IP;
		cash=IC;
		debt=ID;
	}
	public RoulettePlayer(String IN, String IP)
	{
		name=IN;
		password=IP;
	}
	public RoulettePlayer()
	{
		
	}
	public void updateMoney(double delta)
	{
		cash=cash+delta;
	}
	public void borrow(double bmoney)
	{
		cash=cash+bmoney;
		debt=debt+bmoney;
	}
	public double getMoney()
	{
		return cash;
	}
	public void payBack(double PM)
	{
		if (PM>debt)
		{
			System.out.println("Amount: "+PM+ " is more than borrowed: " +debt);
			System.out.println("Only paying back: "+debt);
			cash=cash-debt;
			debt=0.0;
		}
		else{
			if(cash>debt){
				debt=debt-PM;
				cash=cash-PM;
		}
			else if (cash<debt){
				System.out.println("Amount: "+PM+ "is more than cash you have now: " +cash);
				debt=debt-cash;
				System.out.println("Only paying back: "+cash);
				cash=0.0;
				
			}
		}
	}
	public String getName()
	{
		return name;
	}
	public String getP()
	{
		return password;
	}
	public String toString()
	{
		StringBuilder S = new StringBuilder();
		S.append("Name: " + name+" ");
		S.append(" cash: " + cash);
		S.append(" debt: "+debt);
		return S.toString();
	}
	public boolean hasMoney()
	{
		if (cash>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public void showAllData()
	{
		System.out.println("Name "+ name);
		System.out.println("Password "+password);
		System.out.println("Cash "+cash);
		System.out.println("debt "+debt);
		if (question==null)
		{
			System.out.println("Question: None");
		}
		else
		{
			System.out.println("Q "+ question[0].getQ()+" A: "+question[0].getA());
			System.out.println("Q "+ question[1].getQ()+" A: "+question[1].getA());
		}
	}
	public void addQuestions(Question [] quest)
	{
		question=quest;
		
	}
	public void setPassword(String PA)
	{
		password=PA;
	}
	
	public boolean matchQuestions(Question[]quest)
	{
		if(question==null)
			return false;
		else if (question.length==quest.length)
		{
			for(int i=0; i<2; i++)
			{
				if(!(question[i].equals(quest[i])))
				{
					flag=false;
				}
				else
				{
					flag=true;
				}
			}
			return flag;
		}
			return false;

	}
	public boolean equals(RoulettePlayer A)
	{
		if (name.equals(A.getName())&&password.equals(A.getP()))
		{
			return true;
		}
		else{
			return false;
		}
	}
	public String[] getQuestions()
	{
		String [] temp = new String[2];
		if(question[0]!=null&&question[1]!=null)
		{
			for(int i=0;i<2;i++)
			{
				temp[i]=question[i].getQ();
			}
		}
		return temp;
	}
	public String saveString() 
	{
		String s=(name+","+password+","+cash+","+debt);
		if(question[0]!=null)
		{
			for (int i=0;i<2;i++)
			{
				s=s+(","+question[i].getQ()+","+question[i].getA());
			}
		}
		return s;
	}
	
	
}