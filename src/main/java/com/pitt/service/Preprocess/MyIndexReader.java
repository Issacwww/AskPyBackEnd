package com.pitt.service.Preprocess;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.TreeMap;

public class MyIndexReader {

	private DirectoryReader ireader;
	private IndexSearcher isearcher;

	private static Logger logger = LoggerFactory.getLogger(MyIndexReader.class);

	public MyIndexReader(String dataType) throws IOException {

		Directory directory;
		String target;
		if (dataType.equals("text")) {
			target = "src/main/resources/data/aitext";
		} else {
			target = "src/main/resources/data/aicode";
		}
//		String target = resource.getFile().getPath();
		directory = FSDirectory.open(Paths.get(target));
		ireader = DirectoryReader.open(directory);
		isearcher = new IndexSearcher(ireader);
	}

	public TreeMap<Integer, Float> search(String query, int TopN) {
		TreeMap<Integer, Float> socres = new TreeMap<>();
		try{
			Query theQ = new QueryParser("CONTENT", new WhitespaceAnalyzer()).parse(query);
			TopFieldDocs topFieldDocs = isearcher.search(theQ, TopN, Sort.RELEVANCE, true, true);
			float maxRef = topFieldDocs.getMaxScore();
			ScoreDoc[] scoreDoc = topFieldDocs.scoreDocs;
			for (ScoreDoc score : scoreDoc) {
				socres.put(Integer.valueOf(ireader.document(score.doc).get("DOCNO")), score.score / maxRef);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return socres;
	}
}
