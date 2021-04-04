package dao;

import model.User;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDao {
    /**
     * 用户添加【注册功能】
     */
    public int add(String username, String password) throws SQLException {
        int result = 0;
        Connection connection = DBUtil.getConnection();
        String sql = "insert into user(username,password) values(?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        result = statement.executeUpdate();
        return result;
    }

    /**
     * 查询用户
     * @param username
     * @param password
     * @return
     */
    public User queryUser(String username, String password) throws SQLException {
        User user = new User();
        // jdbc 查询数据库
        Connection connection = DBUtil.getConnection();
        String sql = "select * from user where username=? and password=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        // 查询数据库
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
        }
        // 关闭数据库连接
        DBUtil.close(connection, statement, resultSet);
        return user;
    }
}
