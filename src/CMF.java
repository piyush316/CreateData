import java.util.ArrayList;

public class CMF {

	double CurrCalc;
	ArrayList<Integer> VolumeSeries;
	ArrayList<Double> WindowSumSeries;

	public CMF(){
		VolumeSeries = new ArrayList<Integer>();
		WindowSumSeries = new ArrayList<Double>();
	}
	public void update(double h, double l,double c,int v){
//		System.out.print("High "+h+" Low "+l+" Close "+c+" Vol. "+v);
		if (h-l != 0)
			CurrCalc = (((c-l)-(h-c))/(h-l))*v;
		else
			CurrCalc = 0;
//		System.out.println("Curr cacl"+CurrCalc);
		WindowSumSeries.add(0,CurrCalc);
		VolumeSeries.add(0,v);		
	}
	public double getCMF(int WindowSize){
		double sum1 = 0;
		int sum2 = 0;
		double CMF = 0;
		int limit;
//		System.out.println("Series Length :"+WindowSumSeries.size()+" Window "+WindowSize);
		if (WindowSumSeries.size() < WindowSize)
			limit = WindowSumSeries.size()-1;
		else
			limit = WindowSize-1;
		for (int i = 0;i<=limit;i++){
			sum1 += WindowSumSeries.get(i);
			sum2 += VolumeSeries.get(i);
//			System.out.println("Inter :"+i+" Window Sum :"+sum1+" Vol Sum :"+sum2);
		}
		CMF = sum1/sum2;
//		System.out.println("CMF :"+ CMF);
		return CMF;
	}
	public static void main(String[] args) {
		double CurrCMF;
		CMF cmf = new CMF();
		cmf.update(40, 25, 31, 100);
		CurrCMF = cmf.getCMF(3);
		cmf.update(45, 32, 35, 150);
		CurrCMF = cmf.getCMF(3);
		cmf.update(41, 31, 32, 175);
		CurrCMF = cmf.getCMF(3);
		cmf.update(48, 32, 44, 155);
		CurrCMF = cmf.getCMF(3);
		cmf.update(49, 33, 43, 145);
		CurrCMF = cmf.getCMF(3);
		cmf.update(46, 39, 40, 180);
		CurrCMF = cmf.getCMF(3);
		cmf.update(39, 32, 33, 200);
		CurrCMF = cmf.getCMF(3);
		cmf.update(38, 31, 34, 210);
		CurrCMF = cmf.getCMF(3);
	}

}

