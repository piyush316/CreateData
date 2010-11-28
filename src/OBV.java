import java.util.ArrayList;

public class OBV {
	double PrevOBV;
	double CurrOBV;
	double PrevClose;
	int ticks;
	int count;
	public OBV(int n){
		ArrayList<Integer> VolumeSeries;
		PrevOBV = 0;
		CurrOBV = 0;
		PrevClose = 0;
		ticks=n;
		count =1;
	}

	public void update(double c,int v){

		
		PrevOBV = CurrOBV;
		if (count==1){
			CurrOBV=0;
		}
		else if (c > PrevClose){
			//if (count<ticks){
			//	CurrOBV=(PrevOBV*((count-1.0)/count))+(v*(1.0/count));
			//}
		
				CurrOBV = (PrevOBV*((ticks-1.0)/ticks)) + (v*(1.0/ticks));
			
		}
		else{
			if(c < PrevClose) {
				//if (count<ticks){
				//	CurrOBV=(PrevOBV*((count-1.0)/count))-(v*(1.0/count));
				//}
				
					CurrOBV = (PrevOBV*((ticks-1.0)/ticks)) - (v*(1.0/ticks));
				
				
				
			}
			else{
				CurrOBV = PrevOBV;
			}			
		}
		PrevClose = c;
		count++;
		
	}
	public double getOBV(){
		//System.out.println("OBV :"+CurrOBV);
		return CurrOBV;
	}

	

	public static void main(String[] args) {
		double OBV;
		
		OBV obv = new OBV(3);
		obv.update(31, 100);
		System.out.println(obv.getOBV());
		OBV = obv.getOBV();
		obv.update(35, 150);
		OBV = obv.getOBV();
		System.out.println(obv.getOBV());
		obv.update(32, 175);
		OBV = obv.getOBV();
		System.out.println(obv.getOBV());
		obv.update(44, 155);
		OBV = obv.getOBV();
		System.out.println(obv.getOBV());
		obv.update(43, 145);
		OBV = obv.getOBV();
		System.out.println(obv.getOBV());
		obv.update(40, 180);
		OBV = obv.getOBV();
		System.out.println(obv.getOBV());
		obv.update(33, 200);
		OBV = obv.getOBV();
		System.out.println(obv.getOBV());
		obv.update(34, 210);
		OBV = obv.getOBV();
		System.out.println(obv.getOBV());
	}

}

