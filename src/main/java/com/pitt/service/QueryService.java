package com.pitt.service;

import com.pitt.entity.Post;

import java.util.List;
import java.util.Set;

public interface QueryService {
     String  RetrieveQuery(String query);
     Set<Post> RetrieveAsById(Integer id);
     Post getPostById(Integer id);
     String getPostsByParentId(Integer id);
}
