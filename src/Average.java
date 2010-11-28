import java.util.ArrayList;


public class Average {

	ArrayList<Integer> VolumeSeries;
	
	public Average(){
		VolumeSeries=new ArrayList<Integer>();
	}
	
	
	public void update(int v){
		VolumeSeries.add(0, v);
	}
	
	public double getAvgRatio(int n1,int n2){
		int i=0;
		double sum1=0.0;
		double sum2=0.0;
		double avg1=0.0;
		double avg2=0.0;
		for ( i=0;(i<VolumeSeries.size())&&(i<n1);i++){
			sum1=sum1+VolumeSeries.get(i);
		}
		avg1=sum1/i;
		
		for ( i=0;(i<VolumeSeries.size())&&(i<n2);i++){
			sum2=sum2+VolumeSeries.get(i);
		}
		avg2=sum2/i;
		//System.out.print(avg1 +" "+avg2);
		
		double ratio=avg1/avg2;
		
		return ratio;
		
	}
	
}
