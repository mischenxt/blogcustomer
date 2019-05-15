package controller;

import org.thymeleaf.context.Context;
import util.ThymeleafHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * �����û��ǳ���Servelt
 * ���session
 * ���ص�¼ҳ
 *
 * @author Tedu
 */
public class LogoutServlet extends ControllerServlet {
    private static final long serialVersionUID = 1L;

    // �ǳ�����
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ���session
        request.getSession().invalidate();
        Context ctx = new Context();
        String msg = request.getParameter("msg");
        ctx.setVariable("msg", msg);
        // ��ת����¼ҳ
        ThymeleafHelper.ThymeleafWrite("login", response, ctx);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
