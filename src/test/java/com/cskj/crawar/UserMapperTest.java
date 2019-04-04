package com.cskj.crawar;

import com.cskj.crawar.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert() throws Exception {
        userMapper.addUser("张三","12");
        userMapper.addUser("李四","12");
        userMapper.addUser("王五","12");
        Assert.assertEquals(3, userMapper.findAllUser().size());
    }
}