package com.atguigu.demomongo;

import com.atguigu.demomongo.entity.User;
import com.atguigu.demomongo.repositroy.UserRepositroy;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.regex.Pattern;

@SpringBootTest
class DemomongoRepositoryTest {

    @Autowired
    private UserRepositroy userRepositroy;

    //添加操作
    @Test
    void create() {
        User user = new User();
        user.setAge(15);
        user.setName("mary");
        user.setEmail("3332200@qq.com");
        User user1 = userRepositroy.save(user);
        System.out.println(user1);
    }

    //查询所有
    @Test
    void findAll(){
        List<User> all = userRepositroy.findAll();
        System.err.println(all);
    }

    //根据id查询
    @Test
    void findById(){
        User user = userRepositroy.findById("6069cf64e71a1c29e9cafb31").get();
        System.out.println(user);
    }

    //条件查询
    @Test
    void findUserlist(){
        User user = new User();
        user.setAge(15);
        user.setName("mary");

        Example<User> example = Example.of(user); //注入实例对象，根据模板条件查询
        List<User> all = userRepositroy.findAll(example);
        System.err.println(all);
    }

    // 模糊查询
    @Test
    void findUsersLikeName(){
        User user = new User();
        user.setName("m");

        //设置模糊查询 匹配规则
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        Example<User> example = Example.of(user,matcher); //注入实例对象，匹配规则

        List<User> all = userRepositroy.findAll(example);
        System.err.println(all);
    }
    //分页查询
    @Test
    void findUsersPage(){
        //设置分页参数
        //0代表第一页
        Pageable pageable = PageRequest.of(0,3);

        User user = new User();
        user.setName("mary");

        Example<User> example = Example.of(user);
        Page<User> page = userRepositroy.findAll(example, pageable);
        System.err.println(page);
    }
    //修改
    @Test
    void updateUser(){
        User user = userRepositroy.findById("6069cf64e71a1c29e9cafb31").get();
        user.setAge(15);
        user.setName("zhang");
        user.setEmail("27017@qq.com");
        User user1 = userRepositroy.save(user);
        System.out.println(user1);
    }
    //删除
    @Test
    void delete(){
        userRepositroy.deleteById("6069cf418ff9c90772499f13");// 没有返回值 - -
    }
}







