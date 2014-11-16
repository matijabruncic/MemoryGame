package results;

import java.util.Comparator;

public class Sort implements Comparator<Score>{

	@Override
	public int compare(Score arg0, Score arg1) {
		if(arg0.getScore() < arg1.getScore()){
			return 1;
		}
		else if(arg0.getScore() > arg1.getScore()){
			return -1;
		}
		else{
			return 0;
		}
	}
	

}
