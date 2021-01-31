package friends;

import java.util.ArrayList;

import structures.Queue;
import structures.Stack;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null or empty array list if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
		
		if (g == null) {
			return null;
		}
		if (p1 == null) {
			return null;
		}
		if (p2 == null) {
			return null;
		}
		
		int s = g.map.get(p1);
		int d = g.map.get(p2);
		
		if(g.members[s].first == null || g.members[d] == null)
			return null;
		
		ArrayList<String> shortestChain = new ArrayList<String>();
		
		ArrayList<Integer> done = new ArrayList<Integer>();
		
		int[] pred = new int[g.members.length];
		pred[s] = -1;
		done.add(s);
		
			
			int inum = g.members[s].first.fnum;
			Friend iCheck = g.members[s].first;
			while(iCheck.next != null) {
				done.add(inum);
				pred[inum] = s;
				iCheck = iCheck.next;
				inum = iCheck.fnum;		
			}
			done.add(inum);
			pred[inum] = s;
			
			int prevSize = -1;
			
			while(!done.contains(d) && done.size() <= g.members.length && done.size() != prevSize)
			{
				prevSize = done.size();
				for(int i = 1; i < done.size();i++) {
					if(done.contains(d))
						break;
					int fnum = g.members[done.get(i)].first.fnum;
					Friend fCheck = g.members[done.get(i)].first;
					while(fCheck.next != null) {
						if(done.contains(d))
							break;
						if(!done.contains(fnum)) {
							done.add(fnum);
							pred[fnum] = done.get(i);
						}
						fCheck = fCheck.next;
						fnum = fCheck.fnum;		
					}
					if(!done.contains(fnum)) {
						done.add(fnum);
						pred[fnum] = done.get(i);
					}
					
				}	
			}
			if(!done.contains(d))
				return null;
		
			Stack<Integer> chain = new Stack<Integer>();
			
			int ptr = d;
			while(ptr != -1)
			{
				chain.push(ptr);
				ptr = pred[ptr];
			}
				
		while(!chain.isEmpty())
			shortestChain.add(g.members[chain.pop()].name);

		return shortestChain;
	}
	
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null or empty array list if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
		
		if (g == null || school == null) {
			return null;
		}
		
		ArrayList<ArrayList<String>> master = new ArrayList<ArrayList<String>>();
		ArrayList<Integer> done = new ArrayList<Integer>();
		
		Person[] members = g.members;
		
		
		//while(done.size() < g.members.length && done.size() != prevSize)
		
			//prevSize = done.size();
				for(int i = 0; i < members.length; i++) {
					if(done.contains(i) || g.members[i].school == null || !g.members[i].school.equals(school))
						continue;
					
					ArrayList<String> subList = new ArrayList<String>();
					done.add(i);
					subList.add(g.members[i].name);
					
					if(members[i].first != null) {
						Friend fCheck = g.members[i].first;
						if(!done.contains(fCheck.fnum)) {
							done.add(fCheck.fnum);
						if(g.members[fCheck.fnum].school != null && g.members[fCheck.fnum].school.contentEquals(school))
							subList.add(g.members[fCheck.fnum].name);
						
						if(fCheck.next != null)
						do
						{
							fCheck = fCheck.next;
							if(!done.contains(fCheck.fnum)) {
								done.add(fCheck.fnum);
							if(g.members[fCheck.fnum].school != null && g.members[fCheck.fnum].school.contentEquals(school))
								subList.add(g.members[fCheck.fnum].name);
							}
						} while(fCheck.next != null);
						}
						
						
						for(int j = 0; j < subList.size(); j++) {
							String name = subList.get(j);
							int y = 0;
							while(!name.equals(members[y].name))
								y++;
							if(members[y].first != null) {
								Friend sCheck = g.members[y].first;	//find index in members
								if(!done.contains(sCheck.fnum)) {
									done.add(sCheck.fnum);
								if(g.members[sCheck.fnum].school != null && g.members[sCheck.fnum].school.contentEquals(school))
									subList.add(g.members[sCheck.fnum].name);
									
								if(sCheck.next != null)
								do
								{
									sCheck = sCheck.next;
									if(!done.contains(sCheck.fnum)) {
										done.add(sCheck.fnum);
									if(g.members[sCheck.fnum].school != null && g.members[sCheck.fnum].school.contentEquals(school))
										subList.add(g.members[sCheck.fnum].name);
									}
								} while(sCheck.next != null);
							}
						}
								
					}
				}
					master.add(subList);
			}
		
		
		if(master.size() !=0)
		return master;
		return null;
		
	}
	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null or empty array list if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {
		if(g == null)
			return null;
		
		ArrayList<String> cons = new ArrayList<String>();
		
		Person[] members = g.members;
		
		for(int i = 0; i < members.length; i++) {
			
			Friend check = g.members[i].first;
			Friend ptr;
			int checkNum;
			
			while(check != null)
			{
				ptr = members[check.fnum].first;
				while(ptr != null) {
					if(ptr.fnum == i) {
						ptr = ptr.next;
						continue;
						}
				checkNum = check.fnum;
				
				int s = i;
				int d = ptr.fnum;
				
				if(g.members[s].first == null || g.members[d] == null)
					return null;
				
				ArrayList<Integer> done = new ArrayList<Integer>();
				
				int[] pred = new int[g.members.length];
				pred[s] = -1;
				done.add(s);
					
					int inum = g.members[s].first.fnum;
					Friend iCheck = g.members[s].first;
					while(iCheck.next != null) {
						if(inum != checkNum) {
							done.add(inum);
							pred[inum] = s;
							}
						iCheck = iCheck.next;
						inum = iCheck.fnum;		
					}
					if(inum != checkNum) {
						done.add(inum);
						pred[inum] = s;
					}
					
					int prevSize = -1;
					
					while(!done.contains(d) && done.size() <= g.members.length && done.size() != prevSize)
					{
						prevSize = done.size();
						for(int j = 1; j < done.size();j++) {
							if(done.contains(d))
								break;
							int fnum = g.members[done.get(j)].first.fnum;
							Friend fCheck = g.members[done.get(j)].first;
							while(fCheck.next != null) {
								if(done.contains(d))
									break;
								if(!done.contains(fnum) && fnum != checkNum) {
									done.add(fnum);
									pred[fnum] = done.get(j);
								}
								fCheck = fCheck.next;
								fnum = fCheck.fnum;		
							}
							if(!done.contains(fnum) && fnum != checkNum) {
								done.add(fnum);
								pred[fnum] = done.get(j);
							}
							
						}	
					}
					if(!done.contains(d) && !cons.contains(g.members[checkNum].name))
						cons.add(g.members[checkNum].name);
					ptr = ptr.next;
				}
				
				check = check.next;
			}
			
		}
	
		return cons;
		
	}
}

