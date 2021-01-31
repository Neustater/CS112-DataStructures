package lse;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class Runner {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
			System.out.println("Dummy");
			LittleSearchEngine test = new LittleSearchEngine();
			test.makeIndex("docs.txt", "noisewords.txt");
			
			int i = 0;
			Occurrence t;
			ArrayList<Occurrence> testA = new ArrayList<Occurrence>();
			
			int[] freq = {82,76,71,71,70,65,61,56,54,51,48,45,41,36,34,30,25,20,20,18,17,17,14,12,17};
			while(i<freq.length) {
				t = new Occurrence("text" + i, freq[i]);
				i++;
				testA.add(t);
				
			}
			
			System.out.println(test.insertLastOccurrence(testA));
			
			
			System.out.println("IS NOT YOU");
			
			
	}

}
