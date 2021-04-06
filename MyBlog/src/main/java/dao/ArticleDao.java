package dao;


import model.Article;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleDao {
    /**
     * 个人文章列表
     */
    public List<Article> getListByUID(int uid) throws SQLException {
        List<Article> list = new ArrayList<Article>();
        Connection connection = DBUtil.getConnection();
        String sql = "select * from article where user_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, uid);
        // 查询数据库并返回结果
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Article article = new Article();
            article.setId(resultSet.getInt("id"));
            article.setReadCount(resultSet.getInt("rcount"));
            article.setTitle(resultSet.getString("title"));
            article.setContent(resultSet.getString("content"));
            article.setCreateTime(resultSet.getDate("create_time"));
            list.add(article);
        }
        DBUtil.close(connection, statement, resultSet);
        return list;
    }

    /**
     * 删除文章
     */

    public int delArticle(int id) throws SQLException {
        int result = 0;
        Connection connection = DBUtil.getConnection();
        String sql = "delete from article where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,id);
        // 真正的执行操作
        result = statement.executeUpdate();
        // 关闭数据库连接
        DBUtil.close(connection, statement, null);
        return result;
    }

    /**
     * 添加文章
     */
    public int addArticle(String title, String content, int uid) throws SQLException {
        int result = 0;

        Connection connection = DBUtil.getConnection();
        String sql = "insert into article(title,content,user_id) values(?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, title);
        statement.setString(2, content);
        statement.setInt(3, uid);

        // 执行sql
        result = statement.executeUpdate();
        DBUtil.close(connection, statement, null);
        return result;
    }

    /**
     * 修改文章
     */
    public int modifiyArticle(String title, String content, int id) throws SQLException {
        int result = 0;
        Connection connection = DBUtil.getConnection();
        String sql = "update article set title=?, content=? where id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, title);
        statement.setString(2, content);
        statement.setInt(3, id);
        result = statement.executeUpdate();
        DBUtil.close(connection, statement, null);
        return result;
    }

    /**
     * 查询文章
     * @param id
     * @return
     */
    public Article queryArticle(int id) throws SQLException {
        Article article = new Article();
        Connection connection = DBUtil.getConnection();
        String sql = "select * from article where id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            article.setTitle(resultSet.getString("title"));
            article.setContent(resultSet.getString("content"));
        }
        return article;
    }
}

