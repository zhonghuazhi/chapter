package com.smart.dao;

import com.smart.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zhonghua on 16/4/3.
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param userName
     * @param password
     * @return
     */
    public int getMatchCount(String userName, String password) {

        String sql = "SELECT COUNT(*) FROM t_user WHERE user_name = ? AND password = ?";
        return jdbcTemplate.queryForInt(sql, new Object[]{userName, password});
    }

    /**
     * @param userName
     * @return
     */
    public User findUserByUserName(final String userName) {

        String sql = "SELECT user_id ,user_name FROM t_user WHERE user_name = ?";
        final User user = new User();

        jdbcTemplate.query(sql, new Object[]{userName}, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(userName);
            }
        });
        return user;
    }

    /**
     *
     * @param user
     */
    public void updateLoginInfo(User user) {

        String sql = "UPDATE t_user SET last_visit = ? ,last_ip = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, new Object[]{user.getLastVisit(), user.getLastIp()});
    }
}