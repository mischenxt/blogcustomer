package controller;

import entity.Article;
import org.thymeleaf.context.Context;
import util.PageInfo;
import util.ThymeleafHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * ������ʾ���µ�Servlet
 * �����ҳ����
 * ��ò�ѯ����������ǩid�������±��⣩
 * ����Ҳർ������
 *
 * @author Tedu
 */
public class ShowListServlet extends ControllerServlet {

    private static final long serialVersionUID = 1L;

    // ��������ѯ�����б�
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //ʵ������ҳʵ�壬Ĭ�ϵ�һҳ
        Context ctx = new Context();
        PageInfo pi = new PageInfo();
        pi.setPageNum(1);
        pi.setPageSize(6);
        //�ж��Ƿ�����ʾ��һҳ
        if (request.getParameter("pageNum") != null) {
            //���Ҫ��ʾ��ҳ��
            pi.setPageNum(Integer.parseInt(request.getParameter("pageNum")));
        }
        String tagid = request.getParameter("tid");
        String titlename = request.getParameter("title");
        // ������������ѯ����
        List<Article> list = articleDao.getArticlesByTag(tagid, titlename, pi);
        ctx.setVariable("normalArticle", list);
        ctx.setVariable("pi", pi);
        ctx.setVariable("tid", tagid);
        ctx.setVariable("title", titlename);
        ctx.setVariable("user", request.getSession().getAttribute("user"));
        // ����Ҳ���������
        super.fillRightPlace(ctx);
        ThymeleafHelper.ThymeleafWrite("list", response, ctx);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
