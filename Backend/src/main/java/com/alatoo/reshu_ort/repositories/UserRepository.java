package com.alatoo.reshu_ort.repositories;

import com.alatoo.reshu_ort.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

}
