import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Random;


public class Sample {
	//Balance the training data sets and copy new files to another folder
	//Copies all test files from input folder as they are

	public static void Run(String inputfolder, String outputfolder, String label1, String label2, String label3, String label4){
		File folder = new File(inputfolder);
        File[] listOfFiles = folder.listFiles(); 
        String[] filenames= new String[listOfFiles.length];
        for(int i=0;i<listOfFiles.length;i++){
            filenames[i]=listOfFiles[i].toString();
        }
        java.util.Arrays.sort(filenames);	
        System.out.println("1");
        for(int j=0;j<filenames.length;j++){
        	System.out.println("2");
        	String thefile=getFilename(filenames[j]);
        	if(thefile.contains("train")){//this implies its a training file
        		if(thefile.contains("long")){
        			samplefile(inputfolder,outputfolder,thefile, label1, label2);
        		}	
        		if(thefile.contains("short")){
        			samplefile(inputfolder,outputfolder,thefile, label3, label4);
        		}
        		
        	}
        	if(thefile.contains("test")){//this implies its a testing file
        		copyfile(inputfolder,outputfolder,thefile);
        	}  
        }
	}
	
    public static  String getHeader(String FileName){
        String s="";
        int count=0;
        try{
            BufferedReader br= new BufferedReader(new FileReader(FileName));
            String line=null;
            while ((line=br.readLine())!=null){
                count=line.replaceAll("[^ ]","").length();
                break;
            }
        for (int i=0;i<count-1;i++){
            int j=i+1;
            s=s+" t"+j;
        }
        s=s+" label";
        s=s+" id";
       s=s.trim();
           
        }
        catch (Exception e) {
            e.printStackTrace();
        }
   
        return s;
    }

	
	public static void samplefile(String inputfolder, String  outputfolder, String filename, String label1,String label2){
		try{
	        BufferedReader br = new BufferedReader(new FileReader(inputfolder+"/"+filename));
	        System.out.println("Sampling "+filename);
	        String outputfile=outputfolder+"/Sampled_"+filename;
			FileOutputStream fos2=new FileOutputStream(outputfile);
			PrintStream pw=new PrintStream(fos2);
	        
	        
	        String line=null;
            int highcount=0;
            int neutralcount=0;
            int small=0;
            int big=0;
           
            while((line=br.readLine())!=null){
                if (line.contains(label1)){
                    highcount++;
                }
                if (line.contains(label2)){
                    neutralcount++;
                }
            }
            br.close();
            System.out.println(highcount+" "+neutralcount);
            String tobesampled=null;
            int samplingratio;
            if (highcount>neutralcount){
                tobesampled=label1;
                small=neutralcount;
                big=highcount;
            }
            else{
                tobesampled=label2;
                samplingratio=(highcount*100)/neutralcount;
                small=highcount;
                big=neutralcount;
            }
            String header=getHeader(inputfolder+"/"+filename);
            br = new BufferedReader(new FileReader(inputfolder+"/"+filename));
            pw.println(header);
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
	
	public static void copyfile(String inputfolder, String outputfolder, String filename){
		
		try{
	        BufferedReader br = new BufferedReader(new FileReader(inputfolder+"/"+filename));
	        String outputfile=outputfolder+"/Sampled_"+filename;
	        String header=getHeader(inputfolder+"/"+filename);
			FileOutputStream fos2=new FileOutputStream(outputfile);
			PrintStream pw=new PrintStream(fos2);
	        String line=null;
	        pw.println(header);
            while((line=br.readLine())!=null){
                  pw.println(line);
            }
            pw.close();
            br.close();
            
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
	public static String getFilename(String file){
		String s=null;
		s=file.substring(file.lastIndexOf("/")+1,file.length());
		
		return s;
	}
	
	public static void main(String[] args) {
		Run(args[0],args[1], args[2], args[3], args[4], args[5]);
	}
	
	
}
