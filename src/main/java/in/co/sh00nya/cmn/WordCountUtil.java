package in.co.sh00nya.cmn;

import java.util.HashSet;
import java.util.Set;

public class WordCountUtil {

	private static final String DELIM = "[ \\.]";

	/**
	 * Returns unique number of words in sentence
	 * Creates new set per request to gather unique words.
	 * @param line
	 * @return
	 */
	public static int countWord(String line) {
		Set<String> wordSet = new HashSet<String>();
		if (line != null && line.trim().length() > 0) {
			for (String word : line.split(DELIM)) {
				if (word.trim().length() > 0) {
					wordSet.add(word.trim());
				}
			}
		}
		return wordSet.size();
	}

	/**
	 * Returns unique number of words. Uses set provided to gather unique words.
	 * Resets set size to 0 before using it. 
	 * @param line
	 * @param wordSet
	 * @return
	 */
	public static int countWord(String line, Set<String> wordSet) {
		if(wordSet == null)
			return 0;
		wordSet.clear();
		if (line != null && line.trim().length() > 0) {
			for (String word : line.split(DELIM)) {
				if (word.trim().length() > 0)
					wordSet.add(word.trim());
			}
		}
		return wordSet.size();
	}
	
}
