package com.pitt.service.Preprocess;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class QueryProcessor {
    private  StopWordRemover textRemover;
    private  WordNormalizer normalizer;
    private SynonymReplacer replacer;
    //for every input query, return 2 versions result: 1 for text, the other for code
    public QueryProcessor() throws IOException {
        textRemover = new StopWordRemover();
        normalizer  = new WordNormalizer();
        replacer = new SynonymReplacer();
    }

    //common parts, remove the most often trigram like 'how do i', which is useless for the ranking
    private String removeTriGram(String query){
        return textRemover.removeTriGram(query.toLowerCase().trim());
    }

    /**
     * Process the query in textual way
     * 1.remove the trigram
     * 2.unified the synonyms & abbreviation
     * 3.tokenize
     * 4.stop word elimination
     * 5.stemming
     * @param query user input
     * @return processed query
     */
    public String textProcessor(String query){
        query = removeTriGram(query);
        query = replacer.replace(query);
        StringBuilder T = new StringBuilder();
        DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(query));
        TokenizerFactory<? extends HasWord>  factory= PTBTokenizer.factory();
        factory.setOptions("untokenizable=noneDelete");
        //tokenize
        tokenizer.setTokenizerFactory(factory);
        for (List<HasWord> sent : tokenizer) {
            for (HasWord w : sent){
                String word = w.word();
                //remove stop words
                if(!textRemover.isStopWord(word)) {
                    //stem
                    T.append(normalizer.stem(word.toCharArray())).append(" ");
                }
            }
        }
        return T.toString().trim();
    }

    /**
     * Process the query in code way
     * 1.remove the trigram
     * 2.remove the punctuations i.e. (),{},[],?...
     * 3.remove some meaningless variable name(i.e. i,j,x) and numbers
     * @param query user input
     * @return processed query
     */
    public String codeProcessor(String query){
        query = removeTriGram(query);
        //remove punctuations
        StringBuilder res = new StringBuilder();
        query = query.replaceAll("\\[[^]]*]|\\{[^}]*}|\\([^)]*\\)|&gt;|&lt;|\\\\n|\\\\r|\\\\s|\\\\t|[-#~$^><=+*/)!&%?`|,.:;\"\'}{\\]\\[\\\\]"," ");
        String[] splitCode = query.split("\\s+");
        for (String s : splitCode)
            if (!s.matches("[a-zA-Z]{0,2}|[0-9]+")) //remove digital and single character
                res.append(s.trim()).append(" ");
        return res.toString();
    }

}
