package com.toomeet.toomeet_play_api.repository;

import com.toomeet.toomeet_play_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
