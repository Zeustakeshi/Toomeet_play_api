package com.toomeet.toomeet_play_api.repository.video.category;

import com.toomeet.toomeet_play_api.entity.video.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    boolean existsByName(String name);
}
