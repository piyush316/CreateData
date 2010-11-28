import java.util.ArrayList;
public class Volatility {
	public ArrayList<Double> minute_ret;
	public double prev_price;
	
	public Volatility(){
		minute_ret=new ArrayList<Double>();
		prev_price=-10000;
	}
	public void update(double price){
		double ret=0.0;
		if (prev_price>0){
			ret=(price-prev_price)/prev_price;
			minute_ret.add(0, ret);
		}
		prev_price=price;
	}
	public double getVolin(int n){
		double v1=0.0;
		if(n>minute_ret.size()){
			n=minute_ret.size();			
		}
		ArrayList temp=new ArrayList<Double>();
		for(int i=0;i<n;i++){
			temp.add(minute_ret.get(i));			
		}
		if (temp.size()>0){
			v1=Stats.stdDev(temp);
		}
		else v1=0;
		return v1;
	}
	
	public static void main(String[] args) {
		
		Volatility v= new Volatility();
		v.update(5);
		v.update(10);
		v.update(10);
		v.update(10);
		v.update(10);
		v.update(10);
		v.update(10);
		v.update(10);
		v.update(10);
		v.update(10);
		v.update(10);
		v.update(10);
		v.update(10);
		v.update(10);
		v.update(10);
		v.update(10);
		
		System.out.println(v.getVolin(23));
	}
	
	
}
