package controller;

import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理登录的Servlet
 * 获得用户名密码
 * 判断是否登录成功
 * 登录成功显示主页
 * 登录失败停留在登录页，并给出相应提示
 *
 * @author Tedu
 */
public class LoginServlet extends ControllerServlet {
    private static final long serialVersionUID = 1L;

    // 登录
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("name");
        String pwd = request.getParameter("pwd");
        User u = userDao.login(name, pwd);
        // 失败时
        if (u == null) {
            response.sendRedirect("logout.do?msg=1");
            return;
        }
        // 成功时
        request.getSession().setAttribute("user", u);
        response.sendRedirect("showIndex.do");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
