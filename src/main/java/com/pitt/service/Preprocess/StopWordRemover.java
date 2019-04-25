package com.pitt.service.Preprocess;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class StopWordRemover {
	private Set<String> v1 = new HashSet<>();
	private final Pattern p = Pattern.compile("how can i|how do i|how should i|how would you|what is the|how to use|is it possible|is there a|best way to|not able to|the difference between|how to");
	StopWordRemover() throws IOException{
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("src/main/resources/data/stopword.txt"))));
		String str;
		while((str = bufferedReader.readLine())!=null)//using the readLine to read a Line of the content
			v1.add(str);
		bufferedReader.close();

	}

	boolean isStopWord(String word) {
		for(String s: v1)
			if (s.equals(word))
				return true;
		return false;
	}

	String removeTriGram(String query) {
		//todo: remove the trigram
		Matcher m = p.matcher(query);
		StringBuffer removed = new StringBuffer();
		while (m.find())
			m.appendReplacement(removed,"");
		m.appendTail(removed);

		return removed.toString().trim();
	}

}
