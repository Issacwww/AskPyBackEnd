package com.pitt.service.Preprocess;

/**
 * This is for INFSCI 2140 in 2018
 * 
 */
public class WordNormalizer {
	// Essential private methods or variables can be added.

	// YOU MUST IMPLEMENT THIS METHOD.
	public char[] lowercase(char[] chars) {
		// Transform the word uppercase characters into lowercase.
		for (int i = 0;i < chars.length; i++)
			chars[i] = Character.toLowerCase(chars[i]);
		return chars;
	}

	// YOU MUST IMPLEMENT THIS METHOD.
	String stem(char[] chars) {
		Stemmer s = new Stemmer();
		s.add(chars, chars.length);
		s.stem();
		return s.toString();
	}

}
