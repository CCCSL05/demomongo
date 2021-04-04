package com.atguigu.demomongo;

import com.atguigu.demomongo.entity.User;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@SpringBootTest
class DemomongoApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    //添加操作
    @Test
    void create() {
        User user = new User();
        user.setAge(20);
        user.setName("ZhangSan");
        user.setEmail("4932200@qq.com");
        User user1 = mongoTemplate.insert(user);
        System.out.println(user1);
    }

    //查询所有
    @Test
    void findAll(){
        List<User> all = mongoTemplate.findAll(User.class);
        System.out.println(all);
    }

    //根据id查询
    @Test
    void findById(){
        // 6069b98a81dc8741bff2ca89
        User user = mongoTemplate.findById("6069b98a81dc8741bff2ca89", User.class);
        System.err.println(user);
    }

    //条件查询
    @Test
    void findUserlist(){
        // name=ZhangSan age=20
        Query query = new Query(Criteria.where("name").is("ZhangSan").and("age").is(20));
        List<User> users = mongoTemplate.find(query, User.class);
        System.err.println(users);
    }

    // 模糊查询
    @Test
    void findUsersLikeName(){
        String name="zhang";
        String regex= String.format("%s%s%s", "^.*", name, ".*$"); // .* 任意多个字符
        Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);//大小写不敏感
        Query query = new Query(Criteria.where("name").regex(pattern));
        List<User> users = mongoTemplate.find(query, User.class);
        System.err.println(users);
    }
    //分页查询
    @Test
    void findUsersPage(){
        int pageNo=1;
        int pageSize=10;
        //条件构建
        String name="zhang";
        String regex = String.format("%s%s%s", "^.*", name, ".*");
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));

        long count = mongoTemplate.count(query, User.class);//查询记录数
        //分页查询
        List<User> users = mongoTemplate.find(query.skip((pageNo - 1) * pageSize).limit(pageSize), User.class);

        System.err.println(count);
        System.out.println(users);
    }
    //修改
    @Test
    void updateUser(){
        //根据id查询 获取实体
        User user = mongoTemplate.findById("6069b98a81dc8741bff2ca89", User.class);

        //设置修改值
        user.setName("test_1");
        user.setAge(25);
        user.setEmail("493220990@qq.com");

        //调用方法进行修改
        Query query = new Query(Criteria.where("_id").is("6069b98a81dc8741bff2ca89"));
        Update update = new Update();
        update.set("name", user.getName());
        update.set("age", user.getAge());
        update.set("email", user.getEmail());

        UpdateResult upsert = mongoTemplate.upsert(query, update, User.class);
        long count = upsert.getModifiedCount();
        System.out.println(count);
    }
    //删除
    @Test
    void delete(){
        Query query = new Query(Criteria.where("_id").is("6069b98a81dc8741bff2ca89"));
        DeleteResult result = mongoTemplate.remove(query, User.class);
        long count = result.getDeletedCount();
        System.out.println(count);
    }
}







