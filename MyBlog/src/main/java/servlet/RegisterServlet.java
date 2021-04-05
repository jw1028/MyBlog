package servlet;


import dao.UserDao;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet("/reg")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        // 定义返回给前端的参数
        int success = 0;
        String msg = "";
        // 1.获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username != null && !username.equals("") &&
                password != null && !password.equals("")
        ) {
            // 2.【业务逻辑处理】操作数据库添加用户
            UserDao userDao = new UserDao();
            try {
                success = userDao.add(username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            success = 0;
            msg = "参数不全，无法注册成功";
        }
        // 3.返回结果
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("success", success);
        result.put("msg", msg);
        JSONUtil.write(resp, result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       this.doGet(req,resp);
    }
}
