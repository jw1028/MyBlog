package servlet;

import dao.UserDao;
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


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int success = -1; // 1:登录成功
        String msg = "";// 错误信息
        // 1.得出前端传递的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        // 2.去数据库验证用户名和密码【业务】
        if (username != null && !username.equals("") &&
                password != null && !password.equals("")
        ) {
            // 参数正确，执行数据库查询
            UserDao userDao = new UserDao();
            try {
                // 查询数据库
                User user = userDao.queryUser(username, password);
                if (user.getId() > 0) {
                    // 查到用户了，也就是用户名和密码是正确
                    success = 1;
                    // 将用户信息存放到 session
                    HttpSession session = req.getSession(); // 用来创建会话
                    // 将用户信息存放到当前session
                    session.setAttribute("user", user);
                } else {
                    success = 0;
                    msg = "用户名或密码输出错误！";
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            // 参数不完整，非法请求
            msg = "非法请求，参数不完整";
        }
        // 3.返回结果
        HashMap<String, Object> result = new HashMap<String,Object>();
        result.put("success", success);
        result.put("msg", msg);
        JSONUtil.write(resp, result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }

}
