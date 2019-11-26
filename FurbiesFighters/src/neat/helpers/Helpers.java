package neat.helpers;

import java.util.ArrayList;
import java.util.List;

public class Helpers {
	public static List<Float> generateOneHot(int choice, int size){
		List<Float> oneHot = new ArrayList<Float>();
		for(int i = 0; i < size; i++) {
			if(choice == i) 
				oneHot.add(1.0f);
			else
				oneHot.add(0.0f);
		}
		return oneHot;
	}
}
