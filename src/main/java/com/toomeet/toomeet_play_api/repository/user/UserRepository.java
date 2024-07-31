package com.toomeet.toomeet_play_api.repository.user;

import com.toomeet.toomeet_play_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("select " +
            "case when (count(w) > 0) then true else false end " +
            "from User u join u.watchedVideos w " +
            "where  w.id = :videoId and u.id = :userId")
    boolean existsVideoHistory(String videoId, String userId);
}
