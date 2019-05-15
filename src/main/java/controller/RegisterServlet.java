package controller;

import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * �����û�ע���Servlet
 * ����û���Ϣ
 * ע���û�
 *
 * @author Tedu
 */
public class RegisterServlet extends ControllerServlet {

    private static final long serialVersionUID = 1L;

    // ע�᷽��
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("name");
        //�û�������
        if (userDao.existsUserbyName(name)) {
            response.sendRedirect("showreg.do?msg=1");
            return;
        }
        String pwd = request.getParameter("pwd");
        String email = request.getParameter("email");
        User u = new User();
        u.setUserName(name);
        u.setUserEmail(email);
        u.setUserPassword(pwd);
        u.setUserRole("defaultRole");
        u.setUserAvatar("default.jpg");
        // ע�ᣬ�����û�
        int num = userDao.registerUser(u);
        if (num > 0) {
            response.sendRedirect("logout.do");
        } else {
            response.sendRedirect("showreg.do?msg=2");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
