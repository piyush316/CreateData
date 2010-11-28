import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import com.csvreader.CsvReader;


public class RMtoSVMLight {


	
	public static void Run(String infilename){
		try{
			String outfilename="";
			String outfile2="";
			//reads a file
			CsvReader cr= new CsvReader(infilename,' ');
			PrintStream ps= new PrintStream(new FileOutputStream(infilename+".libsvm"));
			//reads the label
			int ctr=0;
			//cr.readHeaders();
			while(cr.readRecord()){
				int labelindex=cr.getColumnCount()-2;
				String toprint="";
				String label=cr.get(labelindex);
				//System.out.println(cr.get(labelindex));
				if(label.equals("HIGH")||label.equals("N_H")){
					toprint="+1 ";
				}
				else toprint="-1 ";
					
				
				ctr++;
				for(int i=0;i<labelindex;i++){
					if(labelindex-i>2){
						int p =i+1;
						toprint=toprint+p+":"+cr.get(i)+" ";
					}
					else{
						//System.out.println(cr.get(i));
						
					}
				}
				//System.out.println(ctr);
				//System.out.println(toprint);
				ps.println(toprint);
			}
			//prints 
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public static void main2(String[] args){
		Run(args[0]);
	}
	
	public static void main(String[] args) {
		//String filename=args[0];
		String filename="/home/pyrole/Strategies/SVM/data/TrainingData/NIFTY_P30_Sampled2/Sampled_normalized_trainlong0000580.csv";

		
		for(int i=0;i<590;i=i+10){
			String x="";
			if(i>=100){
				x=i+"";
			}
			else if(i>=10){
				x="0"+i;
			}
			else x="00"+i;
			
			filename="/home/pyrole/Strategies/SVM/data/TrainingData/NIFTY_P30_Variables4/trainlong0000"+x+".csv";
			Run(filename);
			 filename="/home/pyrole/Strategies/SVM/data/TrainingData/NIFTY_P30_Variables4/trainshort0000"+x+".csv";
			Run(filename);
			 filename="/home/pyrole/Strategies/SVM/data/TrainingData/NIFTY_P30_Variables4/testlong0000"+x+".csv";
			Run(filename);
			 filename="/home/pyrole/Strategies/SVM/data/TrainingData/NIFTY_P30_Variables4/testshort0000"+x+".csv";
			Run(filename);
		}
	}
}
