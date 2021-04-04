package com.atguigu.demomongo.repositroy;

import com.atguigu.demomongo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepositroy extends MongoRepository<User,String> {
}
