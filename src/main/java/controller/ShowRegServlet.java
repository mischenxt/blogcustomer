package controller;

import org.thymeleaf.context.Context;
import util.ThymeleafHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ShowRegServlet extends ControllerServlet {
    private static final long serialVersionUID = 1L;

    // 注册
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Context ctx = new Context();
        //获得状态码,1为用户名重复2为注册失败
        String msg = request.getParameter("msg");
        ctx.setVariable("msg", msg);
        ThymeleafHelper.ThymeleafWrite("reg", response, ctx);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
