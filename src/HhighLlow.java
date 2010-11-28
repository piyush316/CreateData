//counts higher highslows and lower high lows
public class HhighLlow {
	double prevhigh;
	double prevlow;
	int conshighs;
	int conslows;
	
	public HhighLlow(){
		prevhigh=0;
		prevlow=100000000;
		int conshighs=0;
		int conslows=0;
	}
	
	public void update(double currhigh, double currlow){
		if ((currlow>prevlow)&&currhigh>prevhigh){
			conshighs++;
		}
		else conshighs=0;
		
		if ((currlow<prevlow)&&currhigh<prevhigh){
			conslows++;
		}
		else conslows=0;
		prevhigh=currhigh;
		prevlow=currlow;
		
	}
	
	
	public int getConsHighs(){
		return conshighs;
	}
	
	public int getConsLows(){
		return conslows;
	}
	
}
