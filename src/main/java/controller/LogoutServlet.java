package controller;

import org.thymeleaf.context.Context;
import util.ThymeleafHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理用户登出的Servelt
 * 清空session
 * 返回登录页
 *
 * @author Tedu
 */
public class LogoutServlet extends ControllerServlet {
    private static final long serialVersionUID = 1L;

    // 登出方法
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 清空session
        request.getSession().invalidate();
        Context ctx = new Context();
        String msg = request.getParameter("msg");
        ctx.setVariable("msg", msg);
        // 跳转到登录页
        ThymeleafHelper.ThymeleafWrite("login", response, ctx);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
