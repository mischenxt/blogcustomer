package controller;

import entity.Article;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * �����������۵�Servlet
 * �����������
 * �ж��Ƿ��¼�����û�е�¼���ص�¼ҳ��
 * ��������Ƿ�������ۣ��������۷���
 * �������۲���������������
 *
 * @author Tedu
 */
public class AddCommentServlet extends ControllerServlet {

    private static final long serialVersionUID = 1L;

    // ��������
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String reCommentId = request.getParameter("reCommentId");
        String reCommentName = request.getParameter("reCommentName");
        String commentOnId = request.getParameter("commentOnId");
        String commentContent = request.getParameter("commentContent");
        // ִ���������
        //��õ�¼�û�
        User user = (User) request.getSession().getAttribute("user");
        //���δ��¼��������¼ҳ��
        if (user == null) {
            response.sendRedirect("logout.do?msg=2");
            return;
        }
        // ��������Ƿ��������
        int aid = Integer.parseInt(commentOnId);
        Article a = articleDao.getArticleById(aid);
        // ����������ۣ�����
        if (a.getArticleCommentable() == 0) {
            response.sendRedirect("showArticle.do?aid=" + aid);
            return;
        }
        // ����һ������
        int num = commentDao.addComment(commentContent, commentOnId, reCommentId, reCommentName, user);
        if (num > 0) {
            // ����1������
            articleDao.addArticleComment(aid);
        }
        response.sendRedirect("showArticle.do?aid=" + aid);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
