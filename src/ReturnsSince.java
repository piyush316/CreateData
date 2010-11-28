import java.util.ArrayList;

//this variable takes a timestamp and retruns returns since then. if the current timestamp is older thant that it reutrns 0 
public class ReturnsSince {

	
	ArrayList<Double> priceSeries;
	public ReturnsSince(){
		priceSeries=new ArrayList<Double>();
	}
	
	public void update(double pr){
		priceSeries.add(pr);
	}
	
	public double  getRetSince(int n){
		
		double retS=0;
		if (priceSeries.size()>n){
			retS=(1.0*priceSeries.get(priceSeries.size()-1)-priceSeries.get(n-1))/(1.0*priceSeries.get(n-1));
		}
		return retS;
	}
	
	
	
	public static void main(String[] args) {
		ReturnsSince rs= new ReturnsSince();
		
		rs.update(33);
		System.out.println(rs.getRetSince(5));
		rs.update(44);
		System.out.println(rs.getRetSince(5));
		rs.update(45);
		System.out.println(rs.getRetSince(5));
		rs.update(46);
		System.out.println(rs.getRetSince(5));
		rs.update(47);
		System.out.println(rs.getRetSince(5));
		rs.update(471);
		System.out.println(rs.getRetSince(5));
		rs.update(4701);
		System.out.println(rs.getRetSince(5));
		rs.update(47001);
		System.out.println(rs.getRetSince(5));
	}
	
}
