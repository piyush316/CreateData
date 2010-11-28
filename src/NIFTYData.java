
import com.csvreader.CsvReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.swing.filechooser.FileNameExtensionFilter;

public class NIFTYData {
    CsvReader cr;
    HashMap<Integer, Double> Price;
    String expiry;//expiry of the NIFTY FUTURE
    double prevdayclose;
    String fn;
   
    public  NIFTYData(String file){
        //Load the File
    	fn=file;
    	Price = new HashMap<Integer, Double>();
        try{
            DateFormat dateFormat= new SimpleDateFormat("HH:mm:ss");
            cr=new CsvReader(file,'|');
            while(cr.readRecord()){
                expiry=cr.get(8);
                Date time=dateFormat.parse(cr.get(0));
                int t=(time.getHours()*60)+(time.getMinutes());
                double vwappr=Double.parseDouble(cr.get(2));
                Price.put(t,vwappr);
            }
            cr.close();
       
       
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    public String expiry(){
        return expiry;
    }
   
    
    //if no trades have been made for the NIFTY future in the minute we return the VWAP of 
    //the previous minute
    public double price(int t){
    	//System.out.println(Price.toString());
    	
    	try{	
    			while(Price.get(t)==null && t>0){
    				t=t-1;
    			}
    			return Price.get(t);
    	}
    	catch (Exception e){
    		System.out.println("No NIFTY future price found for "+fn+" "+t);   	
    		e.printStackTrace();
    	}
    	
    	return Price.get(t);
    }
   
}