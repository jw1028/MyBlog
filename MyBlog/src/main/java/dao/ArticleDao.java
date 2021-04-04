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
        return list;
    }
}
