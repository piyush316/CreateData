import java.util.ArrayList;
import java.util.HashMap;


public class HighLow {
	
	ArrayList<Double> PriceSeries;
	
	
	public HighLow(){
		PriceSeries=new ArrayList<Double>();
	
	} 
	
	public void update(double currPrice){
		PriceSeries.add(currPrice);
		//System.out.println(PriceSeries.toString());
	}
	
	public int getHighDist(int n){//gives the high dist in the last n minutes
		double high =0;	
		int dist=0;
		int highdist=0;
		//double currPrice=PriceSeries.get(PriceSeries.size()-1);
		//System.out.println(PriceSeries.size());
		for (int i=PriceSeries.size();(i>0)&&dist<n+1;i--){
			double checkPrice=PriceSeries.get(i-1);
			if(checkPrice>=high){
				//System.out.print(checkPrice+">"+high);
				high=checkPrice;
				highdist=dist;
			}
			dist++;
		}
		
		return highdist;
	}
	
	
	public int getLowDist(int n){
		double low=100000000;
		int dist=0;
		int lowdist=0;
		for (int i=PriceSeries.size();(i>0)&&dist<n+1;i--){
			double checkPrice=PriceSeries.get(i-1);
			
			if(checkPrice<=low){
				low=checkPrice;
				lowdist=dist;
			}
			dist++;
		}		
		return lowdist;		
	}
	
	//returns the differnce between the number of upticks and down ticks in the last n ticks 
	public int getdiffutdt(int n){
		int dif=0;
		int dist=0;
		for(int i=PriceSeries.size()-1;(i>0)&&(dist<n+1);i--){
			if(PriceSeries.get(i)>PriceSeries.get(i-1)){
				dif++;
			}
			if(PriceSeries.get(i)<PriceSeries.get(i-1)){
				dif--;
			}
			dist++; 
		}
		return dif;
		
		
	}
	
	//returns the number of runs in the last n ticks. will take the value of 1 at open.
	public double fractionofruns(int n){
		int nruns=0;
		int dire=0;
		int dist=0;
		for(int i=PriceSeries.size()-1;(i>0)&&(dist<n+1);i--){
			if (PriceSeries.get(i)>PriceSeries.get(i-1)){
				if(dire>=0){
					dire=-1;
					nruns++;
				}
			}
			if (PriceSeries.get(i)<PriceSeries.get(i-1)){
				if (dire<=0){
					dire=1;
					nruns++;
				}	
			}
			dist++;
			
		}
		//if (PriceSeries.size()<n){
		//	nruns=nruns;
		//}
		if (dist==0){
			return 1;
		}
		
		return (nruns*1.0)/dist;
	}
	
}
