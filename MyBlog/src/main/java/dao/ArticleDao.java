package dao;


import model.Article;
import model.vo.ArticleVO;
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
    public List<Article> getListByID(int uid) throws SQLException {
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
            article.setReadCount(resultSet.getInt("readCount"));
            article.setTitle(resultSet.getString("title"));
            article.setContent(resultSet.getString("content"));
            article.setCreateTime(resultSet.getDate("createTime"));
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
     * 通过id查询文章查询文章
     */
    public Article getArticleById(int id) throws SQLException {
        ArticleVO article = new ArticleVO();
        Connection connection = DBUtil.getConnection();
        String sql = "select a.*,u.username from article a left join user u on a.user_id=u.id where a.id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            article.setTitle(resultSet.getString("title"));
            article.setContent(resultSet.getString("content"));
            article.setCreateTime(resultSet.getDate("create_time"));
            article.setReadCount(resultSet.getInt("rcount"));
            article.setUsername(resultSet.getString("username"));
        }
        DBUtil.close(connection, statement, resultSet);
        return article;
    }
    /**
     * 获取所有人的文章列表
     */
    public List<ArticleVO> getAllList() throws SQLException {
        List<ArticleVO> list = new ArrayList<ArticleVO>();
        Connection connection = DBUtil.getConnection();
        String sql = "select a.*,u.username from article a left join user u on a.user_id=u.id";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            ArticleVO vo = new ArticleVO();
            vo.setId(resultSet.getInt("id"));
            vo.setUsername(resultSet.getString("username"));
            vo.setTitle(resultSet.getString("title"));
            vo.setReadCount(resultSet.getInt("rcount"));
            vo.setCreateTime(resultSet.getDate("create_time"));
            list.add(vo);
        }
        DBUtil.close(connection, statement, resultSet);
        return list;
    }

    /**
     * 根据分页查询文章列表
     */

    public List<ArticleVO> getListByPage(int cpage, int psize) throws SQLException {
        List<ArticleVO> list = new ArrayList<ArticleVO>();
        Connection connection = DBUtil.getConnection();
        String sql = "select a.*,u.username from article a left join user u on a.user_id=u.id limit ?,?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, (cpage - 1) * psize);
        statement.setInt(2, psize);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            ArticleVO vo = new ArticleVO();
            vo.setId(resultSet.getInt("id"));
            vo.setUsername(resultSet.getString("username"));
            vo.setTitle(resultSet.getString("title"));
            vo.setReadCount(resultSet.getInt("rcount"));
            vo.setCreateTime(resultSet.getDate("create_time"));
            list.add(vo);
        }
        DBUtil.close(connection, statement, resultSet);
        return list;
    }

    /**
     * 阅读量增加
     */
    public int readCountAdd (int id) throws SQLException {
        int result = 0;
        Connection connection = DBUtil.getConnection();
        String sql = "update article set rcount=rcount+1 where id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        result = statement.executeUpdate();
        DBUtil.close(connection, statement, null);
        return result;
    }
}

