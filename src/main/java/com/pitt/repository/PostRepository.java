package com.pitt.repository;

import com.pitt.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
//
////    @Query(value = "select p.Id,p.ParentId,p.CreationDate,p.Type,p.Summary from posts p where p.Id in :ids ",nativeQuery = true)
//    List<Post> findPostsBy(@Param("ids") List<Integer> Ids);


    List<Post> findPostsByParentIdOrderByIdDesc(Integer id);
}
