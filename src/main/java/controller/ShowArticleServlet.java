package controller;

import entity.Article;
import entity.Comment;
import org.thymeleaf.context.Context;
import util.ThymeleafHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * ������һƪ���µ�Servlet
 * ���Ҫ��ʾ���µ�id
 * ִ��ǰһƪ����һƪ������Ķ�������Ķ��Ȳ�ѯ����
 * �����ƪ���µ�����
 * ����Ҳർ������
 *
 * @author Tedu
 */
public class ShowArticleServlet extends ControllerServlet {
    private static final long serialVersionUID = 1L;

    // ��ʾĳһƪ����
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Context ctx = new Context();
        // �������id
        int oid = Integer.parseInt(request.getParameter("aid"));
        // �������
        Article ar = articleDao.getArticleById(oid);
        // ��ѯǰһƪ�ͺ�һƪ����
        Article prev = articleDao.getArticlePrev(oid);
        Article next = articleDao.getArticleNext(oid);
        // ��ͬ��ǩ������Ķ�
        List<Article> sameArticle = articleDao.getSameTagArticle(oid);
        // ����Ķ�
        List<Article> ranArticle = articleDao.getRandomArticle();
        // ��ƪ���µ���������
        List<Comment> commList = commentDao.getCommentByArticleId(oid);
        // ��ҳ����������Ϣ���
        super.fillRightPlace(ctx);

        ctx.setVariable("ar", ar);
        ctx.setVariable("prev", prev);
        ctx.setVariable("next", next);
        ctx.setVariable("same", sameArticle);
        ctx.setVariable("ran", ranArticle);
        ctx.setVariable("comm", commList);
        ctx.setVariable("user", request.getSession().getAttribute("user"));
        ThymeleafHelper.ThymeleafWrite("article", response, ctx);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
