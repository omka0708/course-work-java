package com.mirea.coursework.repo;

import com.mirea.coursework.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
