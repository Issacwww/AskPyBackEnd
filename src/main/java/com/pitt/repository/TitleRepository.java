package com.pitt.repository;

import com.pitt.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, Integer> {
    Title findTitleById(Integer id);
}
