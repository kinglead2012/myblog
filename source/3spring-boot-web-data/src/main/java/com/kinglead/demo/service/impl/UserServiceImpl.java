package com.kinglead.demo.service.impl;

import com.kinglead.demo.entity.User;
import com.kinglead.demo.service.UserService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 新增用户
     */
    @Override
    public User insert(User user) {
        jdbcTemplate.update("insert into t_user(name,password) values(?,?)", user.getName(), user.getPassword());
        return user;
    }

    /**
     * 通过用户名和密码查询用户
     */
    @Override
    public User queryByNameAndPassword(User user) {
        return jdbcTemplate.queryForObject("select id, name, password from t_user where name = ? and password = ?", this::mapRowToUser, user.getName(), user.getPassword());
    }

    /**
     * 查询用户列表
     */
    @Override
    public List<Map<String, Object>> queryAll() {
        return jdbcTemplate.queryForList("select id, name, password from t_user");
    }

    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getLong("id"), rs.getString("name"), rs.getString("password"));
    }
}
