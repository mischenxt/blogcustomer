package controller;

import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * �����¼��Servlet
 * ����û�������
 * �ж��Ƿ��¼�ɹ�
 * ��¼�ɹ���ʾ��ҳ
 * ��¼ʧ��ͣ���ڵ�¼ҳ����������Ӧ��ʾ
 *
 * @author Tedu
 */
public class LoginServlet extends ControllerServlet {
    private static final long serialVersionUID = 1L;

    // ��¼
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("name");
        String pwd = request.getParameter("pwd");
        User u = userDao.login(name, pwd);
        // ʧ��ʱ
        if (u == null) {
            response.sendRedirect("logout.do?msg=1");
            return;
        }
        // �ɹ�ʱ
        request.getSession().setAttribute("user", u);
        response.sendRedirect("showIndex.do");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
