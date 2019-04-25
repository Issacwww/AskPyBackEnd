package com.pitt.service;

import com.google.gson.Gson;
import com.pitt.entity.Post;
import com.pitt.entity.Title;
import com.pitt.repository.PostRepository;
import com.pitt.repository.TitleRepository;
import com.pitt.service.onlineQuery.OnlineQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class QueryServiceImpl implements QueryService{
    private static Logger logger = LoggerFactory.getLogger(QueryService.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TitleRepository titleRepository;

    private static Gson gson = new Gson();

    private static OnlineQuery onlineQuery;

    static {
        try {
            onlineQuery = new OnlineQuery();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String RetrieveQuery(String query) {
        Set<Post> resultList = new TreeSet<>();
        Map<Integer,Float> retrieveList = new HashMap<>();
        long begin = System.currentTimeMillis();
        // retrieve the ids from index using query terms
        // then calculate the score and store in Map<Id, score>
        logger.info("Query for " + query);
        try {
            retrieveList = onlineQuery.search(query,100);
        }catch (Exception e){
            e.printStackTrace();
        }
        logger.info("index retrieval finished in " + (System.currentTimeMillis() - begin) / 1000.0 + " secs");

        List<Post> posts = postRepository.findAllById(retrieveList.keySet());

        //get info and ranking
        for (Post p: posts) {
            p.setTitle(titleRepository.findTitleById(p.getParentId()).getTitle());
            p.setScore(retrieveList.getOrDefault(p.getId(),0.0f));
            resultList.add(p);
        }

        logger.info("whole query finished in " + (System.currentTimeMillis() - begin) / 1000.0 + " secs");
        return "{\"posts\": "+ gson.toJson(resultList) + "}";
    }


    public Set<Post> RetrieveAsById(Integer id) {
        //return a set of answers by id without knowing what id it is
        return new HashSet<>();
    }

    public Post getPostById(Integer id){
        Post p = postRepository.findById(id).get();
        Title t = titleRepository.findTitleById(p.getParentId());
        p.setTitle(t.getTitle());
        return p;
    }

    public String getPostsByParentId(Integer id){
       List<Post> posts = postRepository.findPostsByParentIdOrderByIdDesc(id);
       Title title = titleRepository.findTitleById(id);
       Post question = new Post();
       for (Post p : posts){
           if (p.getId() == id) {
               question = p;
               posts.remove(p);
               break;
           }
       }
       question.setTitle(title.getTitle());
       return "{\"question\": " + gson.toJson(question) + ",\"answers\": " + gson.toJson(posts) + " }";
    }
}
