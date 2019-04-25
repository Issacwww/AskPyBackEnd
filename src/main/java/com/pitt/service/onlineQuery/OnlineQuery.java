package com.pitt.service.onlineQuery;

import com.pitt.service.Preprocess.MyIndexReader;
import com.pitt.service.Preprocess.QueryProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

//import org.junit.Test;

public class OnlineQuery {
    private static MyIndexReader textIndexReader;
    private static MyIndexReader codeIndexReader;
    private static QueryProcessor processor;
    private static Logger logger = LoggerFactory.getLogger(OnlineQuery.class);
    public OnlineQuery() throws  IOException{
        textIndexReader = new MyIndexReader("text");
        codeIndexReader = new MyIndexReader("code");
        processor = new QueryProcessor();
    }
    //combine text scores with code
    public HashMap<Integer, Float> search(String query,int TopN){
        HashMap<Integer,Float> combineRes = new HashMap<>();
        TreeMap<Integer,Float> textRes = new TreeMap<>(), codeRes = new TreeMap<>();
        logger.info("query before process " + query);
        query = processor.textProcessor(query);
        logger.info("query after process " + query);
        if (query!=null && !"".equals(query.trim()))
            textRes = textIndexReader.search(query,TopN);
        query = processor.codeProcessor(query);
        if (query!=null && !"".equals(query.trim()))
            codeRes = codeIndexReader.search(query,TopN);
        if (textRes.isEmpty()&&codeRes.isEmpty())
            return combineRes;

        for(Map.Entry<Integer,Float> entry:textRes.entrySet()) {
            int id = entry.getKey();
            float tScore = entry.getValue();
            if (codeRes.containsKey(id))
                tScore = Math.max(tScore, codeRes.get(id));
            combineRes.put(id, tScore);
        }
        return combineRes;
    }


}
