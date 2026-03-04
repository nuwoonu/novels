package com.example.novels.novel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.novels.novel.entity.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    @Modifying
    @Query("DELETE FROM Grade g where g.novel.id = :id")
    void deleteByNovel(@Param("id") Long id);
}
