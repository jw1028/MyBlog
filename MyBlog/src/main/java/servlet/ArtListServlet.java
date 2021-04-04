package servlet;

import dao.ArticleDao;
import model.Article;
import model.User;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created with Intellig IDEA
 * Description:
 * User: zjc
 * Date: 2021- 04 -04
 * Time: 17:37
 */
@WebServlet("/article")
public class ArtListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int success = -1;
        String msg = "";
        List<Article> list = null;
        // 1.获取参数
        HttpSession session = req.getSession(false);
        if (session == null) {
            // 用户未登录
            msg = "非法请求，请先登录！";
        } else {
            // 用户已经登录
            User user = (User) session.getAttribute("user");
            // 2.查询数据库
            int uid = user.getId();
            ArticleDao articleDao = new ArticleDao();
            try {
                // 查询到数据
                list = articleDao.getListByUID(uid);
                success = 1;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        // 3.构建和返回后端结果
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("success", success);
        result.put("msg", msg);
        result.put("list", list);
        JSONUtil.write(resp, result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
