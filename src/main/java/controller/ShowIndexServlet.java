package controller;

import entity.Article;
import org.thymeleaf.context.Context;
import util.ThymeleafHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * ������ʾ��ҳ���µ�Servlet
 * ��ѯǰ��ƪ���£���Ҫ�����3��������
 * ����Ҳർ������
 *
 * @author Tedu
 */
public class ShowIndexServlet extends ControllerServlet {

    private static final long serialVersionUID = 1L;

    // ��ʾ��ҳ
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Context ctx = new Context();
        // ��ѯ��ҳҪ��ʾ������
        List<Article> topList = articleDao.getArticlesTopList();
        // �ö���һƪ
        ctx.setVariable("fristArticle", topList.get(0));
        // �ö�2-4
        ctx.setVariable("ThreeArticle", topList.subList(1, 4));
        // ��ͨ5-8
        ctx.setVariable("normalArticle", topList.subList(4, topList.size()));
        // ����Ҳ���������
        super.fillRightPlace(ctx);
        //����session����
        ctx.setVariable("user", request.getSession().getAttribute("user"));
        ThymeleafHelper.ThymeleafWrite("index", response, ctx);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
