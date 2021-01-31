package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

	public static String delims = " \t*+-/()[]";
	public static String delims2 = " \t*+-/()";
			
    /**
     * Populates the vars list with simple variables, and arrays lists with arrays
     * in the expression. For every variable (simple or array), a SINGLE instance is created 
     * and stored, even if it appears more than once in the expression.
     * At this time, values for all variables and all array items are set to
     * zero - they will be loaded from a file in the loadVariableValues method.
     * 
     * @param expr The expression
     * @param vars The variables array list - already created by the caller
     * @param arrays The arrays array list - already created by the caller
     */
    public static void 
    makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	/** DO NOT create new vars and arrays - they are already created before being sent in
    	 ** to this method - you just need to fill them in.
    	 **/
    	 
    	// = new Variable(token.nextToken());
    	 StringTokenizer token = new StringTokenizer(expr,delims);
    	 int length = token.countTokens();
    	 String[] tokens = new String[length];
    	 int j = 0;
         while(token.hasMoreTokens()) {
        	 tokens[j] = token.nextToken();
        	 j++;
         }
        int prevIndex = 0;
        for(int i = 0; i < tokens.length; i++) {
        	Array ar;
        	Variable var;
        	
        	if(tokens[i].charAt(0) >= '0' && tokens[i].charAt(0) <= '9' || tokens[i] == "")
        		continue;
        	int ptr = expr.indexOf(tokens[i], prevIndex);
        	int postCheck = ptr + tokens[i].length();
        	prevIndex = postCheck;
        	char ded = '?';
        	if(postCheck < expr.length())
        		ded = expr.charAt(postCheck);
        	
        	if(ded == '[') {
        		ar = new Array(tokens[i]);
        			if(!arrays.contains(ar))
        				arrays.add(ar);
        	}
        	else {
        		var = new Variable(tokens[i]);
        			if(!vars.contains(var))
        				vars.add(var);
        		}
        	}
    	
    }
    
    /**
     * Loads values for variables and arrays in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     * @param vars The variables array list, previously populated by makeVariableLists
     * @param arrays The arrays array list - previously populated by makeVariableLists
     */
    public static void 
    loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String tok = st.nextToken();
            Variable var = new Variable(tok);
            Array arr = new Array(tok);
            int vari = vars.indexOf(var);
            int arri = arrays.indexOf(arr);
            if (vari == -1 && arri == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                vars.get(vari).value = num;
            } else { // array symbol
            	arr = arrays.get(arri);
            	arr.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    arr.values[index] = val;              
                }
            }
        }
    }
    
    /**
     * Evaluates the expression.
     * 
     * @param vars The variables array list, with values for all variables in the expression
     * @param arrays The arrays array list, with values for all array items
     * @return Result of evaluation
     */
    
    public static float 
    evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	
    		
    //	 StringTokenizer token = new StringTokenizer(expr, delims2);
    	// int length = token.countTokens();
    //	 String[] tokens = new String[length];
    	// int j = 0;
        // while(token.hasMoreTokens()) {
       // 	 tokens[j] = token.nextToken();
        //	 j++;
        // }
       
        ArrayList<String> tokenList = new ArrayList<String>();
         
     	Stack<Float> mainNums = new Stack<Float>();
    	Stack<String> mainOpps = new Stack<String>();
    	Stack<Float> tempNums = new Stack<Float>();
    	Stack<String> tempOpps = new Stack<String>();
    	mainOpps.push("(");
    	
    	
    	
    	//////
    	
        int start = 0;
        int stop = 0;
        int prev = 0;
        for(int i = 0; i < expr.length(); i++) { 
        
       	 String temp = "";
       	if(expr.substring(i, i+1).equals("[")) {
       	i++;
       		
       		Stack<String> bracket = new Stack<String>();
       		bracket.push("[");
       			while(!bracket.isEmpty()) {
       				stop++;
       				if(expr.substring(i, i+1).equals("]"))
       					bracket.pop();
       				else if(expr.substring(i, i+1).equals("["))
       					bracket.push("[");
       				i++;
       			 //if((stop == prev && stop != 0) || (i == expr.length()-1 && stop !=0))
       	        //  tokenList.add(expr.substring(start,start+stop));
       			}
       			i--;
       		
       	//	i = i + expr.substring(i).indexOf(']');
       		
       	}
       	 if(expr.substring(i, i+1).equals("+")) {
       		 temp = "+";
       		 
       	 }
       	 else if(expr.substring(i, i+1).equals("-")) {
       		 temp = "-";
       	
      	 }
       	 else if(expr.substring(i, i+1).equals("*")) {
       		 temp = "*";
       		
       	 }
       	 else if(expr.substring(i, i+1).equals("/")) {
       		 temp = "/";
       		
       	 }
       	 else if(expr.substring(i, i+1).equals("(")) {
      		 temp = "(";
      		
      	 }
       	 else if(expr.substring(i, i+1).equals(")")) {
      		 temp = ")";
      		
      	 }
        else if(expr.substring(i, i+1).equals(" ")) {
         	 }
        else {
        	if(stop == 0)
        		start = i;
        	stop++;
        }
        if((stop == prev && stop != 0) || (i == expr.length()-1 && stop !=0)) {
        	tokenList.add(expr.substring(start,start+stop));
        	stop = 0;
        	prev = 0;
        }
        prev = stop;
       	 
       if(temp != "")
       		 mainOpps.push(temp);
        }
        
        
        ///////
        String[] tokens = new String[tokenList.size()];
        for(int j = 0; j < tokenList.size(); j++)
        	tokens[j] = tokenList.get(j);
    		
        for(int i = 0; i < tokens.length; i++) { 
        	boolean success = true;
        	try {
			 Integer.parseInt(tokens[i]);
			 
		 }catch(NumberFormatException e) {
			 success=false;
			 
			 for(int u = 0; u < vars.size(); u++) {
				 if(tokens[i].equals(vars.get(u).name))
					 mainNums.push((float)vars.get(u).value);
			 }
			 if(tokens[i].length() >= 3) { //arrays insert recursive example
			 for(int u = 0; u < arrays.size(); u++) {
				 int brac = tokens[i].indexOf('[');
				 String check = tokens[i].substring(0,brac);
				 
				 int pos =  (int) Expression.evaluate(tokens[i].substring(brac + 1,tokens[i].length()-1), vars, arrays);
				 if(check.equals(arrays.get(u).name))
					 mainNums.push((float)arrays.get(u).values[pos]);
			
			 }
			 }
			 
		 } if(success == true)
			 mainNums.push((float)Integer.parseInt(tokens[i]));
        }
        
        mainOpps.push(")");
        
        
        float q = mainNums.pop();
        if(mainNums.isEmpty())
        	return q;
        else 
        	mainNums.push(q);
        
        while(!mainOpps.isEmpty()) {
        	
        	
       // while(!mainOpps.isEmpty()) {
        	while(!mainOpps.peek().contentEquals("(")) {
        		
        		while(mainOpps.peek().equals(")"))
					tempOpps.push(mainOpps.pop());
        		if(!mainOpps.peek().equals(")")) {
        			tempNums.push(mainNums.pop());
        			tempOpps.push(mainOpps.pop());
        		}
        		
        		
        		/*if(mainOpps.peek().equals(")")){
        			tempNums.push(mainNums.pop());
        			tempOpps.push(mainOpps.pop());
        				while(mainOpps.peek().equals(")"))
        					tempOpps.push(mainOpps.pop());
        		} else {
        			
        			tempOpps.push(mainOpps.pop());
        			tempNums.push(mainNums.pop());
        		}
        		*/
        	}
        	tempNums.push(mainNums.pop());
        	
        	//if(!mainNums.isEmpty() )
        		//tempNums.push(mainNums.pop());
			
        	Stack<Float> aN = new Stack<Float>();
        	Stack<Float> mN = new Stack<Float>();
         	Stack<String> aO = new Stack<String>();
         	Stack<String> mO = new Stack<String>();
        		mainOpps.pop();
        		aN.push(tempNums.pop());
        	while(!tempOpps.peek().contentEquals(")")) {
        		aN.push(tempNums.pop());
        		aO.push(tempOpps.pop());
        	}
        		tempOpps.pop();
        		mN.push(aN.pop());
        	while(!aN.isEmpty()) {
            	mN.push(aN.pop());
            	mO.push(aO.pop());
            	}
        	
        	while(!mO.isEmpty()) {
        		if(mO.peek().equals("*")) {
        			float x = mN.pop();
        			float y = mN.pop();
        			float comb = x*y;
        			mO.pop();
        			mN.push(comb);
        		}
        		else if(mO.peek().equals("/")) {
        			float x = mN.pop();
        			float y = mN.pop();
        			float comb = x/y;
        			mO.pop();
        			mN.push(comb);
        		}else {
        			aN.push(mN.pop());
        			aO.push(mO.pop());
        		}
        	}
        	while(!aN.isEmpty()) {
            	mN.push(aN.pop());
            	mO.push(aO.pop());
            	}
        	
        	while(!mO.isEmpty()) {
        		if(mO.peek().equals("+")) {
        			float x = mN.pop();
        			float y = mN.pop();
        			float comb = x+y;
        			mO.pop();
        			mN.push(comb);
        		}
        		else if(mO.peek().equals("-")) {
        			float x = mN.pop();
        			float y = mN.pop();
        			float comb = x-y;
        			mO.pop();
        			mN.push(comb);
        		}else {
        			aN.push(mN.pop());
        			aO.push(mO.pop());
        		}
        	}	
        	if(!aN.isEmpty())
        		mN.push(aN.pop());
        	
        	mainNums.push(mN.pop());
        }
        	
        	while(!tempNums.isEmpty()) {
        		while(tempOpps.peek().equals(")")) {
        			mainOpps.push(tempOpps.pop());
        			break;
        		}
        		mainNums.push(tempNums.pop());
        		if(!tempOpps.isEmpty())
        			mainOpps.push(tempOpps.pop());	
        	}
        
    // }
    	
        return mainNums.pop();
}
}
