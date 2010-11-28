import java.util.ArrayList;

import com.csvreader.CsvReader;
public class ClosingPrices {

   //Gets the NIFTY closing price for  (date-1) on that file of the future with that expriy
    public static double niftyprevdayclose(String date, String filename, String expiry){
        double pr=0.0;
        try{
        	CsvReader cr= new CsvReader(filename,' ');
        	int found=0;
        	String Close="";
        	while(cr.readRecord()){
        		
        		if (date.equals(cr.get(7)) ){
        			System.out.println(date);
        			found=1;
        			break;
        		}
        		Close=cr.get(6);
        		
        	}
        	if (found==0){
        		System.err.println("could not get prev NIFTY close for "+ date+" "+expiry );
        		System.exit(0);
        	}        	
        	pr = Double.parseDouble(Close);
        	
        	
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        		
        return pr;
       
    }
    
    public static double tendayclose(String date, String filename, String expiry){
        double pr=0.0;
        try{
        	CsvReader cr= new CsvReader(filename,' ');
        	int found=0;
        	String Close="";
        	ArrayList<Double> prices=new ArrayList<Double>();
        	while(cr.readRecord()){

        		if (date.equals(cr.get(7))){
        			found=1;
        			break;
        		}
        		Close=cr.get(6);
        		//if the size of the arraylist is less than 10 insert and dont remove
        		if(prices.size()<10){
        			prices.add(Double.parseDouble(Close));
        		}
        		else {
        			prices.add(Double.parseDouble(Close));
        			prices.remove(0);
        		}	
        	}
        	if (found==0){
        		System.err.println("could not get prev NIFTY close for "+ date+" "+expiry );
        		System.exit(0);
        	}        	
        	pr = prices.get(0);
        	
        	
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        		
        return pr;
    }
    
    public static double stockprevDayClose(String day, String filename){
        double price =0.0;
        try{
        	CsvReader cr= new CsvReader(filename,' ');
        	//find the date in the file
        	double prevclose=0;
        	int found=0;
        	String prev="";
        	while(cr.readRecord()){
        		
        		if(cr.get(0).equals(day)){
        			price=Double.parseDouble(prev);
        			found =1;
        			break;
        		}
        		prev=cr.get(3);
        	}
        	
        	if(found ==0){
        		System.err.println("could not get prev close for "+ day );
        		System.exit(0);
        	}
        }
        catch (Exception e) {
        	e.printStackTrace();
		}
       
        return price;
    }
    
    public static double returnsfromClose(double curr, double prevclose,String toprint){
    	if (prevclose==0){
    		return 0;
    	}
    	
    	double ret=(curr -prevclose)/prevclose;
    	
    	if (ret>0.35 || ret<-0.35){
    		System.out.println("Possible SPLIT in "+ toprint);
    		ret=0.0;    		
    	}
    	return ret;
    }
    
    public static double NiftyreturnsfromClose(double curr, double prevclose,String toprint){
    	if (prevclose==0){
    		return 0;
    	}
    	
    	double ret=(curr -prevclose)/prevclose;

    	return ret;
    }
    
}