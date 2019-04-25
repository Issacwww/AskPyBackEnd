package com.pitt.controller;

import com.pitt.entity.Post;
import com.pitt.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class QueryController {
    @Autowired
    private QueryService queryService;

    @GetMapping("/query")
    public String retrieveQuery(@RequestParam("query") String query){
        return queryService.RetrieveQuery(query);
    }

    @GetMapping("/query/{id}")
    public Set<Post> retrieveQAById(@PathVariable String id){ return queryService.RetrieveAsById(Integer.valueOf(id)); }

    @GetMapping("/post/{id}")
    public String getPostsByParentId(@PathVariable String id){ return queryService.getPostsByParentId(Integer.valueOf(id)); }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
