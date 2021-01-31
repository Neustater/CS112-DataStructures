package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Michael Neustater
 *
 */
public class Trie {
	
	// prevent instantiation
	private Trie() { }
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	public static TrieNode buildTrie(String[] allWords) {
		TrieNode root = new TrieNode(null,null,null);
		if(allWords.length > 0) {
			Indexes fIndex = new Indexes(0, (short) 0, (short) (allWords[0].length()-1));
			TrieNode fNode = new TrieNode(fIndex,null,null);
			root.firstChild = fNode;
		} else return root;
		
		for(int i=1; i<allWords.length; i++) {
			
		TrieNode ptr = root.firstChild;
		TrieNode prev = root;
		int start=0;
		int end;
		String ptrString = "";
		String currString = allWords[i];
		int inc = 0;
		boolean isChild = true;
		
		while(ptr != null && inc == 0) 
		{
			start = ptr.substr.startIndex;
			end = ptr.substr.endIndex;
			ptrString = allWords[ptr.substr.wordIndex].substring(start,end+1);
			
			while(ptrString.substring(0,inc+1).equals(currString.substring(start,start + inc + 1)))
			{
 				if(inc + 1 < ptrString.length())
 					inc++;
 					else	{inc ++; break;}
			}
			if(inc == 0) {
				prev = ptr;
				ptr = ptr.sibling;
			}
			if(inc != 0) {
				if(inc == ptrString.length()) {
					prev = ptr;
					ptr = ptr.firstChild;
					inc = 0;
					isChild = true;
					continue;
				} else break;
			}
				isChild = false;
		}
		
		if(inc != 0) {
			Indexes tempIndex = new Indexes(ptr.substr.wordIndex,(short) start,(short)(start + inc - 1));
			TrieNode tempNode = new TrieNode(tempIndex ,ptr,ptr.sibling);
			if(isChild == false)
				prev.sibling = tempNode;
			else prev.firstChild = tempNode;
			ptr.substr.startIndex = (short)(start + inc);
			prev=ptr;
		}
		
		Indexes newIndex = new Indexes(i,(short) (start + inc),(short)(currString.length() - 1));
		TrieNode newNode = new TrieNode(newIndex ,null,null);
		
		prev.sibling = newNode;
			
		
		}
		
		return root;
	}
	
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	public static ArrayList<TrieNode> completionList(TrieNode root, String[] allWords, String prefix) {
		ArrayList<TrieNode> matches = new ArrayList<TrieNode>();
		
		TrieNode searchHead = root;
		TrieNode ptr = root.firstChild;
		if(ptr == null) return matches;
		int start = ptr.substr.startIndex;
		int end = ptr.substr.endIndex;
		int i = ptr.substr.wordIndex;
		String ptrString = allWords[i].substring(start, end + 1 ); 
		
		while(ptr != null)
		{
			i = ptr.substr.wordIndex;
			start = ptr.substr.startIndex;
			end = ptr.substr.endIndex;
			ptrString = allWords[i].substring(start, end + 1 );
			
			if(start+1 > prefix.length())
				break;
			
			if(ptrString.charAt(0) == (prefix.charAt(start)))
			{
				if(ptr.firstChild != null) {
				searchHead = ptr;
				ptr = searchHead.firstChild;
				}
				else {
					searchHead = ptr;
					matches.add(searchHead);
					break;
				}
			}
			else {
				ptr = ptr.sibling;
			}
		}
		if(searchHead == root)
			return null;
		else
			leafFinder(matches, searchHead);
		
		return matches;
	}
	
	
	//helper recursive method which will find leaves once the searchHead has been located
	private static void leafFinder(ArrayList<TrieNode> matches, TrieNode parent){
		TrieNode ptr = parent.firstChild;
		while(ptr!=null) {
			if(ptr.firstChild == null) {
				matches.add(ptr);
				ptr = ptr.sibling;
			}
			else {
				leafFinder(matches, ptr);
				ptr = ptr.sibling;
			}
		}
		
		return;
	}
	
	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }
