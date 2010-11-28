import com.csvreader.CsvReader;
import java.util.HashMap;
public class CreateData {
	public static void read(){
		try{
			CsvReader cr=new CsvReader("/Users/diamond/Documents/workspace/CreateData/20040301.trd",'|');
			//Recency r= new Recency("price",0.0007);
			Return[] rt ={new Return(2), new Return(5), new Return(10), new Return(20)};
			Return[] rt2={new Return(2,0.0005), new Return(5,0.0005), new Return(10,0.0005), new Return(20,0.0005),new Return(2,0.0007), new Return(5,0.0007), new Return(10,0.0007), new Return(20,0.0007),new Return(2,0.001), new Return(5,0.001), new Return(10,0.002), new Return(20,0.002)};
			Return rt_open=new Return(2);
			HighLow hl=new HighLow();
			HhighLlow hhll=new HhighLlow();
			Average av = new Average();
			int itr=0;
			HashMap<Integer,Double> pr=new HashMap<Integer,Double>();
			RSI rsi=new RSI();
			while(cr.readRecord()){
				
				
				String vwap=cr.get(2);
				double vwappr=Double.parseDouble(vwap);
				String h=cr.get(5);
				String l=cr.get(6);
				double cl=Double.parseDouble(cr.get(4)) ;
				rsi.update(cl);
				
				System.out.print( rsi.getRSI(10));
				
				
				//double d=Double.parseDouble(s);
				/*
				double currhigh=Double.parseDouble(h);
				double currlow=Double.parseDouble(l);
				int qty=Integer.parseInt(cr.get(1));
				av.update(qty);
				System.out.print(av.getAvgRatio(2, 4)+" "+qty);
				*/
				
				/*
				hl.update(d);
				System.out.print(hl.getdiffutdt(5)+" "+d);
				*/
				/*
				hhll.update(currhigh, currlow);
				System.out.println(hhll.getConsHighs()+" "+hhll.getConsLows()+ " "+currhigh+" "+currlow);
				
				*/
				/*
				hl.update(d);
					System.out.print(hl.getLowDist(5)+" "+d);
				*/	
				//}
				
				/*
				String s=cr.get(2);
				double d=Double.parseDouble(s);
				//double rec=r.update(d);
				rt_open.update(d);
				double or=rt_open.getOpenReturn();
				System.out.println(or+" ");
				for(int j=0;j<rt.length;j++){
					
					rt[j].update(d);
					double ret=rt[j].getReturns();
					System.out.print(ret+" ");
				}
				*/
				
				/*
				for(int i=0;i<rt2.length;i++){
					//double ret=rt[i].update(d);
					//System.out.print(ret*100+ " ");
					
					rt2[i].update(d);
					int rec=rt2[i].getRecency();
					
					
					System.out.print(rec+ " ");
				}
				*/
				System.out.println("");
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		read();
	}

}
