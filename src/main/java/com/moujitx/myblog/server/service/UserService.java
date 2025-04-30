package com.moujitx.myblog.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.moujitx.myblog.server.entity.User;
import com.moujitx.myblog.server.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Function: (User)表服务接口
 * Author: MOUJITX
 * Date: 2024-07-21 01:05:55
 */

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 新增数据
     */
    public void insert(User user) {
        user.setUuid("");
        userMapper.insert(user);
    }

    /**
     * 修改数据
     */
    public void update(User user) {
        userMapper.updateById(user);
    }

    /**
     * 通过主键删除数据
     */
    public void delete(String uuid) {
        userMapper.deleteById(uuid);
    }

    /**
     * 删除多条数据
     */
    public void batchDelete(List<String> ids) {
        userMapper.deleteBatchIds(ids);
    }

    /**
     * 查询全部数据
     */
    public List<User> selectAll() {
        MPJLambdaWrapper<User> wrapper = new MPJLambdaWrapper<User>()
                .selectAll(User.class);
        return userMapper.selectList(wrapper);
    }

    /**
     * 通过ID查询单条数据
     */
    public User selectById(String uuid) {
        MPJLambdaWrapper<User> wrapper = new MPJLambdaWrapper<User>()
                .selectAll(User.class)
                .eq(User::getUuid, uuid);
        return userMapper.selectOne(wrapper);
    }

    /**
     * 多条件查询
     */
    public List<User> select(User user) {
        MPJLambdaWrapper<User> wrapper = new MPJLambdaWrapper<User>()
                .selectAll(User.class);

        return userMapper.selectList(wrapper);
    }

    /**
     * 多条件分页查询
     */
    public Page<User> selectPage(User user, Integer pageNum, Integer pageSize) {
        MPJLambdaWrapper<User> wrapper = new MPJLambdaWrapper<User>()
                .selectAll(User.class);

        return userMapper.selectPage(new Page<>(pageNum, pageSize, true), wrapper);
    }

    /**
     * 唯一值查询
     */
    public User selectByUsername(String username) {
        MPJLambdaWrapper<User> queryWrapper = new MPJLambdaWrapper<>();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }

    public User selectByUsernameAndPassword(String username, String password) {
        MPJLambdaWrapper<User> queryWrapper = new MPJLambdaWrapper<>();
        queryWrapper.eq("username", username)
                .eq("password", password);
        return userMapper.selectOne(queryWrapper);
    }
}
