import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import com.csvreader.CsvReader;
//Assumes train0.csv and test0.csv

public class Normalizer {

	
	public static String getFilename(String file){
		String s=null;
		s=file.substring(file.lastIndexOf("/")+1,file.length());
		
		return s;
	}
	public static void Run(String inputfolder, String outputfolder){
		File folder = new File(inputfolder);
        File[] listOfFiles = folder.listFiles(); 
        String[] filenames= new String[listOfFiles.length];
        for(int i=0;i<listOfFiles.length;i++){
            filenames[i]=listOfFiles[i].toString();
        }
        java.util.Arrays.sort(filenames);
        for(int j=0;j<filenames.length;j++){

        	String thefile=getFilename(filenames[j]);
        	if(thefile.contains("train")){//this implies its a training file
        		Normalize(inputfolder,outputfolder,thefile);
        	}      	
        }
	}
	
	public static void Normalize(String inputfolder,String outputfolder,String thefile){
		try{
			CsvReader cr= new CsvReader(inputfolder+"/"+thefile,' ');
			System.out.println(inputfolder+"/"+thefile);
			int first=1;
			int n=0;
			double[] avg;
			double[] stddev;
			cr.readHeaders();
			avg=new double[cr.getHeaderCount()-2];
			stddev=new double[cr.getHeaderCount()-2];
			cr= new CsvReader(inputfolder+"/"+thefile,' ');
			while(cr.readRecord()){
				if (first==1){
					first=0;
					for(int i=0;i<avg.length;i++){
						avg[i]=Double.parseDouble(cr.get(i));
					}
					
				}
				else{
					for(int i=0;i<avg.length;i++){
						//System.out.println(cr.get(i));
						avg[i]=avg[i]+Double.parseDouble(cr.get(i));				
					}
				}
				n++;
			}
			for (int i=0;i<avg.length;i++){
				avg[i]=avg[i]/n;
			}
			
			first=1;
			cr= new CsvReader(inputfolder+"/"+thefile,' ');
			while(cr.readRecord()){
				if (first==1){
					first=0;
					for(int i=0;i<avg.length;i++){
						double x=Double.parseDouble(cr.get(i));
						stddev[i]=(x-avg[i])*(x-avg[i]);						
					}					
				}
				else{
					for(int i=0;i<avg.length;i++){
						double x=Double.parseDouble(cr.get(i));
						stddev[i]=stddev[i] + ((x-avg[i])*(x-avg[i]));			
					}
				}

			}
			for (int i=0;i<avg.length;i++){			
				stddev[i]=Math.sqrt(stddev[i]/(n-1));
			}		
			System.out.println(avg[7]+" "+stddev[7]);
			//avg[7]=0;
			//stddev[7]=0.013;
			FileOutputStream fos=new FileOutputStream(outputfolder+"/normalized_"+thefile);
			PrintStream ps=new PrintStream(fos);
			//calculate z for training data
			cr= new CsvReader(inputfolder+"/"+thefile,' ');
			while(cr.readRecord()){
				String print="";
				for(int i=0;i<avg.length;i++){
					double x=Double.parseDouble(cr.get(i));
					double z=(x-avg[i])/stddev[i];
					//if(z>4) z=1;
					//else if (z<-4) z=-1;
					//else z=0;
					print =print +" "+z;
				}
				//printing the label and id
				
				print=print+" "+cr.get(avg.length);
				print=print+" "+cr.get(avg.length+1);
				ps.print(print.trim()+"\n");
			}
			ps.close();
			fos.close();
			
			
			String testfile=thefile.replaceAll("train", "test");
			System.out.println(testfile);
			FileOutputStream fos2=new FileOutputStream(outputfolder+"/normalized_"+testfile);
			PrintStream ps2=new PrintStream(fos2);
			//calculate z for training data
			cr= new CsvReader(inputfolder+"/"+testfile,' ');
			while(cr.readRecord()){
				String print="";
				for(int i=0;i<avg.length;i++){
					double x=Double.parseDouble(cr.get(i));
					double z=(x-avg[i])/stddev[i];
					print =print +" "+z;
				}
				//printing the label
				print=print+" "+cr.get(avg.length);
				print=print+" "+cr.get(avg.length+1);
				ps2.print(print.trim()+"\n");
			}
			ps2.close();
			fos2.close();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		Run(args[0], args[1]);
		//Normalize("/Users/diamond/test", "/Users/diamond/test","train.csv");
	}
}


