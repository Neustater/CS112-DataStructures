package lse;

import java.io.*;
import java.util.*;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}
	
	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
	throws FileNotFoundException {
		
		
		HashMap<String,Occurrence> index = new HashMap<String,Occurrence>();
		
		
		Scanner sc = new Scanner(new File(docFile));
		while (sc.hasNext()) {
			String curIndex = getKeyword(sc.next());
			if(curIndex != null) {
				Occurrence prevOccur = index.get(curIndex);
			if(prevOccur == null) {
				Occurrence indexInsert = new Occurrence(docFile, 1);
				index.put(curIndex, indexInsert);
			}
			else
				{int m = prevOccur.frequency;
			
			Occurrence indexInsert = new Occurrence(docFile, (m+1));
			index.put(curIndex, indexInsert);
				}
			}
			}
		sc.close();
		
		return index;
	}
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
		/** COMPLETE THIS METHOD **/
		Iterator<Map.Entry<String,Occurrence>> iter = kws.entrySet().iterator();
		
		while (iter.hasNext()) {
			Map.Entry<String,Occurrence> entry = (Map.Entry<String,Occurrence>) iter.next();
			
			ArrayList<Occurrence> list = keywordsIndex.get((String)entry.getKey());
			if(list == null) {
				 list = new ArrayList<Occurrence>();
				 list.add((Occurrence)entry.getValue());
				keywordsIndex.put((String)entry.getKey(), list);
				insertLastOccurrence( keywordsIndex.get((String)entry.getKey()));
				
			}else
			{
			 list.add(list.size(), (Occurrence)entry.getValue());
			 insertLastOccurrence( keywordsIndex.get((String)entry.getKey()));
			}
	        } 

		
		
	}
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation(s), consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * NO OTHER CHARACTER SHOULD COUNT AS PUNCTUATION
	 * 
	 * If a word has multiple trailing punctuation characters, they must all be stripped
	 * So "word!!" will become "word", and "word?!?!" will also become "word"
	 * 
	 * See assignment description for examples
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyword(String word) {
		String wordMod = word;
		wordMod.replaceAll("\\s","");
		String LL = wordMod.substring(wordMod.length()-1);
		while(LL.equals(".") || LL.equals(",") || LL.equals("?") || LL.equals(":") || LL.equals(";") || LL.equals("!")){ //checks if any trailing punctuation exists
			wordMod = wordMod.substring(0,wordMod.length()-1);
			if(wordMod.length() == 0)
				break;//creates new substring without last character
			LL = wordMod.substring(wordMod.length()-1);
			}
		//wordMod.replaceAll("\\s","");
		//wordMod.replaceAll(".","");
		//wordMod.replaceAll(",","");
		//wordMod.replaceAll("?","");
		//wordMod.replaceAll(";","");
		//wordMod.replaceAll(":","");
		//wordMod.replaceAll("!","");
		wordMod = wordMod.toLowerCase();
			
		boolean isAlpha = wordMod.matches("[a-zA-Z]+");		//checks if phrase contains any non-alphanumeric characters
		
		
		Iterator<String> itr = noiseWords.iterator();
			while(itr.hasNext()) {
				if(wordMod.equals(itr.next()))
					return null;
			}
		
		
		if(isAlpha == true && !wordMod.contentEquals(""))
			return wordMod;
		else
			return null;
	}
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		/** COMPLETE THIS METHOD **/
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		int i = occs.size();
		if(i == 1) return null;
		Occurrence instance = occs.get(i - 1);
		occs.remove(i-1);
		i--;
		int L = 0;
		int R = i-1;
		int mid = 0;
		
		ArrayList<Integer> returnList = new ArrayList<Integer>();
		Occurrence ptr = occs.get(0);
		
		
		while(L <= R) {
			mid = (L + R) / 2; returnList.add(mid);
			ptr = occs.get(mid);	
			if(instance.frequency == ptr.frequency) {
			break;
			}
			else if(instance.frequency < ptr.frequency) {
				L = mid + 1;
			} else	R = mid - 1;
			
		}
		
		if(instance.frequency < ptr.frequency)
			occs.add((mid + 1), instance);
		else occs.add((mid), instance);
		
			
		//System.out.println(returnList);
		return returnList;
	}
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. 
	 * 
	 * Note that a matching document will only appear once in the result. 
	 * 
	 * Ties in frequency values are broken in favor of the first keyword. 
	 * That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2 also with the same 
	 * frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * 
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * See assignment description for examples
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, 
	 *         returns null or empty array list.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		
		ArrayList<String> returnList = new ArrayList<String>();
		
		ArrayList<Occurrence> w1 = null;
		ArrayList<Occurrence> w2 = null;
		if(keywordsIndex.get(kw1) != null);
			w1 = keywordsIndex.get(kw1);
		if(keywordsIndex.get(kw2) != null);
			w2 = keywordsIndex.get(kw2);
		
		int i1 = 0;
		int i2 = 0;
		int int1 = 0;
		int int2 = 0;
		String insert;
		
		if(w1 == null)
			if(w2 == null)
				return null;
			else
			while(i2 < w2.size()-1 || i2 < 5) {
				returnList.add(w2.get(i2).document);
				i2++;
			}
		else if(w2 == null)
			while(i1 < w1.size()-1 || i1 < 5) {
				returnList.add(w1.get(i1).document);
				i1++;
			}
		else {
			while(returnList.size() < 5) {
				if(i1<w1.size() && i1 > -1)
					int1 = w1.get(i1).frequency;
				else
					int1 = -1;
					
				if(i2<w2.size() && i2 > -1)
					int2 = w2.get(i2).frequency;
				else
					int2 = -1;
				
				if(int1 == -1 && int2 == -1)
					break;
				if(int1 < int2) {
					insert = w2.get(i2).document;
					i2++;}
				else {
					insert = w1.get(i1).document;
					i1++;
				}
				if(returnList.indexOf(insert) == -1)
					returnList.add(insert);
			}
		}
		
		
		return returnList;
	
	}
}
