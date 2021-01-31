package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author Michael Neustater
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		
		Node pol1 = poly1;	//ptr to poly 1
		Node pol2 = poly2;	//ptr to poly 2
		Node nullp = null;	//node which equals null
		Node nullpol = new Node(0,0,null); //node with 0 0 value
		
		
		
		if((pol1 == null && pol2 == null)) return nullp; //returns a node with value null if both ptrs are null
		if((pol1 == null)) pol1 = nullpol; //changes the value of the ptr to 0 to compute
		if((pol2 == null)) pol2 = nullpol;
		
		//creates variables for coefficients and degrees
		float co1 = pol1.term.coeff;
		float co2 = pol2.term.coeff;
		int exp1 = pol1.term.degree;
		int exp2 = pol2.term.degree;
		int fcase = 0; //used to see if head exists
		Node head = null;
		Node curr = null; //ptr for head list
		
		while(pol1 != null || pol2 != null){
			float sum; //sum of coefficients 
			int cexp; //holds current smallest exponent 
			if(pol1 != null) {
			co1 = pol1.term.coeff;
			exp1 = pol1.term.degree;
			}
			else{ //sets values to for when pol is null
			co1 = 0;
			exp1 = -1;
			}
			if(pol2 != null) {
			exp2 = pol2.term.degree;
			co2 = pol2.term.coeff;
			}
			else { //sets values to for when pol is null
			co2 = 0;
			exp2 = -1;
			}
			if(exp1 == exp2 && exp1!=-1) { //if degrees are the same adds coefficients as long as they are not null
				sum = co1 + co2;
				cexp  = exp1;
				pol1 = pol1.next;
				pol2 = pol2.next;
			}
			else if((exp2 == -1 || exp1<exp2) && exp1 != -1) { //if they are not the same, sets sum to the smallest degree as that is not null
				sum = co1;
				cexp  = exp1;
				pol1 = pol1.next;
			}
			else {
				sum = co2;
				cexp  = exp2;
				pol2 = pol2.next;
			}
			if(fcase == 0 && sum != 0.0) { //checks if head exists, if not sets it
				Node addin = new Node(sum,cexp,null);
				head = addin;
				curr = addin;
				fcase++;
				}
			else if(sum != 0.0){ //ensures that 0.0 is not added to the list
				Node addin = new Node(sum,cexp,null);
				curr.next = addin;
				curr = addin;
			
			}
			
		}
		if(head == null || head.term.coeff == 0) //sets a list of value 0 to null pointer
			return nullp;
		return head;
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		Node nullp = null;	//node which equals null
		if(poly1 == null || poly2 == null) return nullp;
		Node pol1 = poly1;
		
		Node com = null;	//head of combined polynomial from each iteration
		int fcase = 0;	//0 if com is null
		
		
			while(pol1 != null) {
				float co1 = pol1.term.coeff;	//gets coefficient of current pol1 pointer
				int deg1 = pol1.term.degree;	//gets degree of current pol1 pointer
				Node holder = null;		//Initializes head of the new list
				Node curr = null;		//Initializes pointer
				Node pol2 = poly2;		//creates a pointer for poly2
					while(pol2 != null) {
						float co2 = pol2.term.coeff;	//gets coefficient of current pol2 pointer
						int deg2 = pol2.term.degree;	//gets degree of current pol2 pointer
						if(holder == null) {	//checks to see if a head exists
							Node temp = new Node(co2*co1,deg2+deg1,null);	//creates a temporary node with values of multiplied element of pol1 and 2
							holder = temp;
							curr = temp;
							}
						else { //if head does not exist
						Node temp = new Node(co2*co1,deg2+deg1,null);
						curr.next = temp;
						curr = temp;
						}
						pol2 = pol2.next; //moves pointer to next element
					}
				if(fcase == 1) { //checks to see if com has a head (first iteration)
					com = holder;
					fcase++;
					}
				else { //if com has a head it adds to it
				com = add(com, holder);
				pol1 = pol1.next;
				}
			}
			
		
		return com;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		Node ptr = poly;
		float sum = 0;
		do {
			float ans = 1;
			for(int i = 0; i < ptr.term.degree; i++)
				ans = ans * x;
			ans *= ptr.term.coeff;
			sum += ans;
			ptr = ptr.next;
		}while(ptr != null);
		
		return sum;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
