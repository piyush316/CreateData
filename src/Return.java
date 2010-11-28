import java.util.ArrayList;
public class Return {

	ArrayList<Double> PriceSeries;
	int window;
	int prev_rec;
	double prev_gap;
	double change;
	double open;
	double high;
	double low;
	public Return(int window){
		PriceSeries= new ArrayList<Double>();
		this.window=window;
		open=-10000;
		high=-10000;
		low=-10000;
	}
	
	
	public Return(int window, double change){
		PriceSeries= new ArrayList<Double>();
		this.window=window;
		this.change=change;
		open=-10000;
		high=-10000;
		low=-10000;
		prev_gap=0;
	}
	

	
	//Just updates the sequnce of price series.
	public void update(double currPrice){

		if (PriceSeries.size()<window+1){			
			PriceSeries.add(currPrice);			
		}
		else{
			double p1=PriceSeries.remove(0);
			PriceSeries.add(currPrice);
		}
	}
	
	
	public double gap(){
		double gap =prev_gap;
		double p1=PriceSeries.get(0);
		double currPrice=PriceSeries.get(PriceSeries.size()-1);
		if(((currPrice-p1)/p1)>change ){			
			gap =1;
			//System.out.println("HIT");
		}
		else if((p1-currPrice)/p1>change){
			gap=-1;
		}
		
		else{
			if (gap>0)
			{
				gap =gap - (1.0/window);//System.out.println("writing "+gap);
				if (gap <0){
					gap =0;
				}
			}
			else if(gap<0){
				gap =gap + (1.0/window);//System.out.println("writing "+gap);
				if (gap >0){
					gap =0;
				}
			}
			
			else{
				gap =0;
			}
		}
		prev_gap=gap;
		return gap;
		
	}
	
	public double getReturns(){
		double ret=0;
		if (PriceSeries.size()<window+1){			
			ret=0;		
		}
		else{
			double p1=PriceSeries.get(0);
			double currPrice=PriceSeries.get(PriceSeries.size()-1);
			double r=(currPrice-p1)/p1;
			
			//ret = Stats.abs(r);
			ret = r;
		}	
		return ret;		
	}
	public int getRecency(){
		int rec=prev_rec;

		double p1=PriceSeries.get(0);
		double currPrice=PriceSeries.get(PriceSeries.size()-1);
		if((PriceSeries.size()<window+1)){
			
			rec=25;
		}
		else if(((currPrice-p1)/p1)>change){			
			rec=0;
			//System.out.println("HIT");
		}
		else if (rec!=-5){
			rec=rec+1;
		}
		prev_rec=rec;
		return rec;
	}
	
	public double getOpenReturn(){
		double currPrice=PriceSeries.get(PriceSeries.size()-1);		
		if(open<0){
			open=currPrice;
		}
		double or=(currPrice-open)/open;
		return or;
	}
	
	
}
