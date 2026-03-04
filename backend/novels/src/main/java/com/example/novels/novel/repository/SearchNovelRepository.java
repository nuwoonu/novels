package com.example.novels.novel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchNovelRepository {
    Object[] getNovelById(Long Id);

    Page<Object[]> list(Long genreId, String keyword, Pageable pageable);
}
