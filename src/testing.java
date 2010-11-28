import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.csvreader.CsvReader;


public class testing {

	public static void main(String[] args) {
		String filename="/home/pyrole/Strategies/SVM/data/Filtered/RELIANCE_VWAP/20061124.trd";
		String s=filename.substring(filename.lastIndexOf("/")+1,filename.length());
        NIFTYData nift= new NIFTYData("/home/pyrole/Strategies/NIFTY_FO_RECENT/NIFTY_VWAP/"+s);
		double prevniftyclose=ClosingPrices.niftyprevdayclose(filename.substring(filename.lastIndexOf("/")+1,filename.length()-4), "/home/pyrole/Strategies/Close/NIFTY_PIYUSHFINAL", nift.expiry());
		double prevniftyclose2=ClosingPrices.tendayclose(filename.substring(filename.lastIndexOf("/")+1,filename.length()-4), "/home/pyrole/Strategies/Close/NIFTY_PIYUSHFINAL", nift.expiry());
		System.out.println(prevniftyclose+ " "+prevniftyclose2);
	}
	
	 public static void main2(String[] args) {
		 try{
			 CsvReader cr = new CsvReader("/home/pyrole/dates");
			 while(cr.readRecord()){
				 //System.out.println(cr.get(0));
				 String s1= cr.get(0);
				 String s2=s1.substring(s1.length()-12, s1.length()-4);
				// System.out.println(s2);
				 DateFormat dateFormat= new SimpleDateFormat("yyyyMMdd");
				// System.out.println(dateFormat.parse(s2));
				 //System.out.println(dateFormat.parse(s2).getDay());
				 if(dateFormat.parse(s2).getDay()>5 || dateFormat.parse(s2).getDay()<1){
					 System.out.println(s1);
				 }
				 
			 }
		 }
		 catch (Exception e) {
			// TODO: handle exception
		}
	}
}
