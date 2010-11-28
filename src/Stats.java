import java.util.ArrayList;

public class Stats {

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
	
	public static double max(double x1, double x2){
		double x;
		if(x1>x2)
			x=x1;
		else x=x2;
			
	return x;
			
	}
	public static double Avg(ArrayList<Double> al){
		double av=0.0;
		double total=0.0;	
		for(int i=0;i<al.size();i++){
			total=total+al.get(i);
		}
		av=total/al.size();
		return av;
	}
	
	
	public static double EMA(ArrayList<Double> al, int n ){
		double ema=0.0;
		if (al.size()<=n){
			ema=Avg(al);
		}
		
		else{
			ArrayList<Double> temp= new ArrayList<Double>();
			
			for(int i=0;i<n;i++){
				temp.add(al.get(i));
			}
			 ema=Avg(temp);
			for(int i=n;i<al.size();i++){
				ema=(1/n)*al.get(i)+((n-1)/n)*ema;
			}
		}
		
		return ema;
		
		
	}
	
	public static double stdDev(ArrayList<Double> al){
		double d=0.0;
		double av=Avg(al);
		//System.out.println("AV"+av);
		for(int i=0;i<al.size();i++){
			double x=(al.get(i)-av)*(al.get(i)-av);
			if (x>0.05){
				//System.out.println(x);
			}
			d=d+(al.get(i)-av)*(al.get(i)-av);
			
		}		
		double stddev=Math.sqrt(d/al.size());
		//System.out.println(al.size());
		return stddev;
	}
	
}
