import com.csvreader.CsvReader;
import java.util.Random;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Date;
public class NiftyLIP {
    public static int predictiontime;
    public static String StockName;
    public static String readlongtrain;
    public static String readshorttrain;
    public static String readlongtest;
    public static String readshorttest;
    public static String read(String filename){

        //first reads the file and creates a map of the time:price which can be used to create the target variables 
        //reiterates through the HashMap to create 4 more hashmaps one checking if the price has gone up
        //20 bips after 30 mins the other checking if the price has gone down more than 20 bips in the
        //next 30 mins...
        //a third checking if the price has gone up 20 bips anytime in the next 30 min and a fourth checking
        //anytime in the next 30 mins.
        String file="";
        readlongtrain="";
        readshorttrain="";
        readlongtest="";
        readshorttest="";	
        try{
            //CsvReader cr=new CsvReader("/home/pyrole/Strategies/SVM/data/Filtered/RELIANCE_VWAP/20061124.trd",'|');

        	CsvReader cr=new CsvReader(filename,'|');
            HashMap<Integer,Double> hm=new HashMap<Integer, Double>();
            DateFormat dateFormat= new SimpleDateFormat("HH:mm:ss");
            Return[] rt ={new Return (1,0.0010), new Return(2,0.0015), new Return(5,0.0020), new Return(10,0.0025),  new Return(30,0.0035), new Return(2,0.0020)};
            //Return[] rt ={new Return (1,0.0010), new Return(2,0.0015), new Return(5,0.0020), new Return(10,0.0025)};
            //Return[] rt
            
            Return[] extragaps={new Return(1,0.002), new Return(1,0.004), new Return (2,0.003), new Return(3,0.005),new Return(5,.0050), new Return(10,0.0050), new Return(10,0.0100), new Return(30,0.0050), new Return(30,0.0100), new Return(60,0.0100)};
            // 1 min 20, 1min 40, 2 min30 , 3 min 505, 50

            HighLow hl=new HighLow();
            HhighLlow hhll=new HhighLlow();
            Average av=new Average();
            Average av2=new Average();
            RelStrengthIndex[] rsi= {new RelStrengthIndex(5), new RelStrengthIndex(15), new RelStrengthIndex(30), new RelStrengthIndex(60)};
            OBV[] obv= {new OBV(10), new OBV(30), new OBV(60)};
            CMF cmf=new CMF();
            ADX[] adx = {new ADX(10), new ADX(30), new ADX(60)};
            Volatility volt= new Volatility();
            Return[] niftyreturns={new Return(5), new Return(10), new Return(30), new Return(60)};
            
            while(cr.readRecord()){
                if(cr.get(0).length()<2){
                	System.out.println("Error in date format in "+filename);
                }
                Date time=dateFormat.parse(cr.get(0));
                int t=(time.getHours()*60)+(time.getMinutes());
                double vwappr=Double.parseDouble(cr.get(2));
                hm.put(t,vwappr);
            }
            cr=new CsvReader(filename,'|');
            
            
            String s=filename.substring(filename.lastIndexOf("/")+1,filename.length());   
            NIFTYData nift= new NIFTYData("/home/pyrole/Strategies/NIFTY_FO_RECENT/NIFTY_VWAP/"+s);
            double prevniftyclose=ClosingPrices.niftyprevdayclose(filename.substring(filename.lastIndexOf("/")+1,filename.length()-4), "/home/pyrole/Strategies/Close/NIFTY_PIYUSHFINAL", nift.expiry());
            double nifttendayclose=ClosingPrices.tendayclose(filename.substring(filename.lastIndexOf("/")+1,filename.length()-4), "/home/pyrole/Strategies/Close/NIFTY_PIYUSHFINAL", nift.expiry());
            //double prevstockclose=ClosingPrices.stockprevDayClose(filename.substring(filename.lastIndexOf("/")+1,filename.length()-4), "/home/pyrole/Strategies/Close/Stocks/"+StockName);
            
            
            
            while(cr.readRecord()){
                String print ="";
                String print_long="";
                String print_short="";

                Date time=dateFormat.parse(cr.get(0));
                int t=(time.getHours()*60)+(time.getMinutes());
                //print=print+" "+filename+":"+t;
                int  vol=Integer.parseInt(cr.get(1));
                double vwappr=Double.parseDouble(cr.get(2));
                //double high=Double.parseDouble(cr.get(5));
                //double low=Double.parseDouble(cr.get(6));
                //int ntrades=Integer.parseInt(cr.get(7));
                
                
                
                //VARIABLES
                
                //Time Elapsed 
                print=print+" "+t;
                
                
                //Returns DO NOT MODIFY.. GAP depends on updating Return
                for (int i=0;i<rt.length;i++){
                    rt[i].update(vwappr);
                   print=print+" "+rt[i].getReturns();
                   
                }
                print = print+" "+rt[2].getOpenReturn();
             
                //GAP
                for (int i=0;i<rt.length;i++){
                	
                   print=print+" "+rt[i].gap();
                }
                
                
                for (int i=0;i<extragaps.length;i++){
                	extragaps[i].update(vwappr);
                    print=print+" "+extragaps[i].gap();
                 }
               
                //HighLowDist
                //hl.update(vwappr);
               // print =print+" "+hl.getHighDist(5)+" "+ hl.getHighDist(10)+" "+hl.getHighDist(30)+" "+hl.getHighDist(60)+" "+hl.getHighDist(60)+" "+hl.getHighDist(500);
                //print =print+" "+hl.getLowDist(5)+" "+ hl.getLowDist(10)+" "+hl.getLowDist(30)+" "+hl.getLowDist(60)+" "+hl.getLowDist(60)+" "+hl.getLowDist(500);
                //print =print+" "+hl.getdiffutdt(5)+" "+hl.getdiffutdt(15)+" "+hl.getdiffutdt(30)+" "+hl.getdiffutdt(60)+" "+hl.getdiffutdt(500);
                //fraction of runs 
                //print=print +" "+hl.fractionofruns(15)+" "+hl.fractionofruns(30)+" "+hl.fractionofruns(60)+" "+hl.fractionofruns(500);
                
                //Higher or lower highs lows
                //hhll.update(high,low);
                
                //print=print +" "+hhll.getConsHighs()+" "+hhll.getConsLows();
                
                
                
              
                //Average Volume Ratio
               av.update(vol);     
                print=print+" "+av.getAvgRatio(5, 15)+" "+ av.getAvgRatio(5, 60)+" "+ av.getAvgRatio(10, 120);
               
                
                //Volatility
                volt.update(vwappr);
                print= print+" "+volt.getVolin(5)+" "+volt.getVolin(15)+" "+volt.getVolin(30)+" "+volt.getVolin(60);
               
                
                //Average Number of Trades Ratio
                //av2.update(ntrades);
                //print =print+" "+av2.getAvgRatio(5, 15)+" "+av2.getAvgRatio(5, 60)+" "+ av2.getAvgRatio(10, 120);
             
                /*
                //RSI
                for(int i=0;i<rsi.length;i++){
                    rsi[i].update(vwappr);
                    print=print + " "+rsi[i].getRSI();
                }
             
                //OBV
                 */ 
                for(int i=0;i< obv.length;i++){		
                	obv[i].update(vwappr, vol);
                	print = print +" "+obv[i].getOBV();
                }
               
                //CMF
                //cmf.update(high, low, vwappr, vol);
               // print =print+" "+cmf.getCMF(10)+" "+cmf.getCMF(20)+" "+cmf.getCMF(30)+" "+cmf.getCMF(60);
                /*   
                //ADX
                for (int i=0;i<adx.length;i++){
                  //  adx[i].update(high, low, vwappr);
                  //  print=print + " "+adx[i].getADX();
                }
          ///   
*/

                //print = print +" "+prevniftyclose;
                //print = print +" "+prevstockclose;
                //System.out.println("a");
                //print =print +" "+ClosingPrices.returnsfromClose(vwappr, prevstockclose,filename);
                //System.out.println("b");
                print =print + " "+ ClosingPrices.NiftyreturnsfromClose(nift.price(t), prevniftyclose,filename);
                print =print + " "+ ClosingPrices.NiftyreturnsfromClose(nift.price(t), nifttendayclose, filename);
                double currnifty=nift.price(t);
                //for (int i=0;i<niftyreturns.length;i++){
                  //  niftyreturns[i].update(currnifty);
                  // print=print+" "+niftyreturns[i].getReturns();
                   
               // }
                
         
                if(hm.get(t+1)!=null && hm.get(t+predictiontime)!=null ){
                	
                    String print1=print+" "+highTarget(hm, t+1);//+"_"+lowTarget(hm, t+1);
                    String print2=print+" "+lowTarget(hm, t+1);
                    //print=print+" "+lowTarget(hm,t+1);
                    print1=print1+" "+filename+":"+time.getHours()+":"+time.getMinutes();
                    print2 =print2+" "+filename+":"+time.getHours()+":"+time.getMinutes();
                    file=file+print.trim()+"\n";
                    readlongtest=readlongtest+print1.trim()+"\n";
                    readshorttest=readshorttest+print2.trim()+"\n";
                    readlongtrain=readlongtrain+print1.trim()+"\n";
                    readshorttrain=readshorttrain+print2.trim()+"\n";
                   // double ret=getthereturns(hm, t+1);
                    
                    //if (ret>0.002 || ret<-0.002){
                    		//System.out.println("60");
                    //	 readlongtrain=readlongtrain+print1.trim()+"\n";
                     //    readshorttrain=readshorttrain+print2.trim()+"\n";
                   // }
                    /*
                    else if (ret>0.002 || ret<-0.002){
                   	 readlongtrain=readlongtrain+print1.trim()+"\n";
                        readshorttrain=readshorttrain+print2.trim()+"\n";
                   	 readlongtrain=readlongtrain+print1.trim()+"\n";
                        readshorttrain=readshorttrain+print2.trim()+"\n";
                   	 
                   }*/
                      // 	readlongtrain=readlongtrain+print1.trim()+"\n";
                      //  readshorttrain=readshorttrain+print2.trim()+"\n";
                    
                    
                    //System.out.print(t+" "+time.getHours()+":"+time.getMinutes()+" "+filename);
                    //System.out.println(hm.get(t+1)+" "+hm.get(t+predictiontime)+" "+highTarget(hm, t+1)+" "+lowTarget(hm, t+1));
                }
              
             
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return file;
    }
 
    public static String highTarget(HashMap<Integer,Double> h, int time){
      String r="N_L";
      // System.out.println(h.get(time)+" "+h.get(time+29));
     
       if ((1.005*(h.get(time)))<h.get(time+predictiontime-1))
       {r="HIGH";}
       return r;
    }
 
    public static String lowTarget(HashMap<Integer,Double> h, int time){
        String r="N_H";
        //System.out.println(h.get(time)+" "+h.get(time+15));
     
        if ((0.995*(h.get(time)))>h.get(time+predictiontime-1))
        {r="LOW";}
        return r;
    }
    
    public static double getthereturns(HashMap<Integer,Double> h, int time){
    	double ret=0.0;
    	ret=(h.get(time+predictiontime-1)-h.get(time))/h.get(time);
    	if(ret>0.01 || ret < -.01)
    	System.out.println(ret);
    	
    	return ret;
    }

 
    //Takes a dataset as input and balances it on its target variables
    public static void balanceH(String inputfile, String outputfile){
        try{
            BufferedReader br = new BufferedReader(new FileReader(inputfile));
            PrintWriter pw = new PrintWriter(new FileWriter(outputfile));
            String line=null;
            int highcount=0;
            int neutralcount=0;
            int small=0;
            int big=0;
         
            while((line=br.readLine())!=null){
                if (line.contains("HIGH")){
                    highcount++;
                }
                if (line.contains("N_L")){
                    neutralcount++;
                }
            }
            br.close();
         
            System.out.println(highcount+" "+neutralcount);
            String tobesampled=null;
            int samplingratio;
            if (highcount>neutralcount){
                tobesampled="H";
                small=neutralcount;
                big=highcount;
            }
            else{
                tobesampled="N";
                samplingratio=(highcount*100)/neutralcount;
                small=highcount;
                big=neutralcount;
            }
            br = new BufferedReader(new FileReader(inputfile));
            line=null;
            Random r= new Random();
            while((line=br.readLine())!=null){
                if(line.contains(tobesampled)){
                    if (r.nextInt(big) < small){
                        pw.println(line);
                    }
                }
                else{
                    pw.println(line);
                }
            }
            pw.close();
            br.close();
         
         
 
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
  
    public static void CreateContinuousData(int training_period, int testing_period, String inputfolder, String outputfolder){
            
        File folder = new File(inputfolder);
        File[] listOfFiles = folder.listFiles();
        String[] filenames= new String[listOfFiles.length];
        for(int i=0;i<listOfFiles.length;i++){
            filenames[i]=listOfFiles[i].toString();
        }
        java.util.Arrays.sort(filenames);
        for(int i=0; i+training_period+testing_period<=filenames.length;i=i+testing_period){
            try{
             
                FileOutputStream foslong= new FileOutputStream(new File(outputfolder+"/trainlong"+getSortableString(i)+".csv"));
                FileOutputStream fosshort= new FileOutputStream(new File(outputfolder+"/trainshort"+getSortableString(i)+".csv"));
                FileOutputStream fos2long= new FileOutputStream(new File(outputfolder+"/testlong"+getSortableString(i)+".csv"));
                FileOutputStream fos2short= new FileOutputStream(new File(outputfolder+"/testshort"+getSortableString(i)+".csv"));
                PrintStream p= new PrintStream(foslong);
                PrintStream p2= new PrintStream(fosshort);
                PrintStream p3= new PrintStream(fos2long);
                PrintStream p4= new PrintStream(fos2short);
                System.out.println(outputfolder);
                String testlong="";
                String trainlong="";
                String testshort="";
                String trainshort="";
                for (int j=i;j<i+training_period;j++){
                	NiftyLIP.read(filenames[j]); 
                	//System.out.println(readlong);
                    trainlong=trainlong+readlongtrain;
                    trainshort=trainshort+readshorttrain;
                }
                for(int j2=i+training_period;j2<i+training_period+testing_period;j2++){
                	NiftyLIP.read(filenames[j2]);
                    testlong=testlong+readlongtest;
                    testshort=testshort+readshorttest;
                }
                p.print(trainlong);
                
                p2.print(trainshort);
                p3.print(testlong);                
                p4.print(testshort);
                
                p.close();
                p2.close();
                p3.close();
                p4.close();
                foslong.close();
                fos2long.close();
                fosshort.close();
                fos2short.close();
                
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
  
   public static String getSortableString(int i){
        //1 -> 0000001
        String s="";
        if(i<10){
            s="000000"+i;          
        }
        else if(i<100){
            s="00000"+i;
        }
        else if(i<1000){
            s="0000"+i;
        }
        else if(i<10000){
            s="000"+i;
        }
        else if(i<100000){
            s="00"+i;
        }

        return s;
      
      
    }
  
    public static void main(String[] args){
    	predictiontime=Integer.parseInt(args[4]);
        StockName=args[5];
        System.out.println("working using "+ predictiontime);
        CreateContinuousData(Integer.parseInt(args[2]), Integer.parseInt(args[3]), args[0], args[1]);
 
    }
 
}