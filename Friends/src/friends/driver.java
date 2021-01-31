package friends;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class driver {

		static Graph friendshipGraph = null;    
		
		public static void main (String[] args) throws FileNotFoundException {
			String docFile = "ftest.txt";
			Scanner sc = new Scanner(new File("assnsample.txt"));	
			Scanner sc1 = new Scanner(new File("subtest3.txt"));	
			Scanner sc2 = new Scanner(new File("sptest4.txt"));	
			Scanner sc3 = new Scanner(new File("subtest5.txt"));
			Scanner sc4 = new Scanner(new File("sptest1.txt"));
			Scanner sc5 = new Scanner(new File("subtest1_2.txt"));
			Scanner sc6 = new Scanner(new File("clqtest4.txt"));	
			Scanner sc7 = new Scanner(new File("conntest6.txt"));	
			Scanner sc8 = new Scanner(new File("subtest4.txt"));
			
			
				Graph assnsample = new Graph(sc);	
				Graph subtest3 = new Graph(sc1);	
				Graph sptest4 = new Graph(sc2);	
				Graph subtest5 = new Graph(sc3);
				Graph sptest1 = new Graph(sc4);
				Graph subtest1_2 = new Graph(sc5);
				Graph clqtest4 = new Graph(sc6);
				Graph conntest6= new Graph(sc7);
				Graph subtest4 = new Graph(sc8);
				
			
						System.out.println("Short Chain:");
						System.out.println(Friends.shortestChain(sptest1, "aparna" , "kaitlin" ));
						System.out.println(Friends.shortestChain(subtest3, "kaitlin" , "nick" ));
						System.out.println(Friends.shortestChain(assnsample, "nick" , "aparna" ));
						System.out.println(Friends.shortestChain(sptest4, "p1" , "p50" ));
						System.out.println(Friends.shortestChain(subtest5, "p1" , "p10" ));
						System.out.println(Friends.shortestChain(subtest5, "p301" , "p198" ));
						
				
						System.out.println("\nClique:");
						System.out.println(Friends.cliques(subtest1_2, "cornell" ));
						System.out.println(Friends.cliques(subtest1_2, "rutgers" ));
						System.out.println(Friends.cliques(subtest3, "rutgers" ));
						System.out.println(Friends.cliques(clqtest4, "rutgers" ));
						System.out.println(Friends.cliques(assnsample, "rutgers" ));
						System.out.println(Friends.cliques(subtest5, "rutgers" ));
						
				
						System.out.println("\nCons:");
						System.out.println(Friends.connectors(subtest1_2));
						System.out.println(Friends.connectors(clqtest4));
						System.out.println(Friends.connectors(subtest3));
						System.out.println(Friends.connectors(subtest4));
						System.out.println(Friends.connectors(assnsample));
						System.out.println(Friends.connectors(conntest6)); 
			}
		}
		
			