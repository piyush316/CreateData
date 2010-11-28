/*
 * This class calculates the recency of a fluctuation
 */


public class Recency {
	
	public String type;
	public int previousrecency;
	public double previoustick;
	
	public double change;
	public Recency(String s,double x){
		type=s;
		change=x;
		previousrecency=-10000;
		previoustick=-10000;
		
	}
	
	
	public static int abs(int x){
		if (x<0)
			return -1*x;
		else return x;
	}
	
	public static double abs(double x){
		if (x<0)
			return -1*x;
		else return x;
	}
	
	public int update( double currtick){
	
		int d=previousrecency;
		if((previoustick-currtick)/previoustick>change&&previoustick!=-10000){
			System.out.println("true");
			d=0;
		}
		else if (d==-10000){
			d= -10000;
		}
		
		else {
			d= d+1;
		}
		previoustick=currtick;
		previousrecency=d;
		System.out.println(d);
		return d;
	}
	
	public int update(int volume){
		int d=0;
		
		
			return d;
	}
 
	public static void main(String[] args) {
		
	}
	
	
}
