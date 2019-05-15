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
 * 处理显示文章的Servlet
 * 处理分页对象
 * 获得查询条件（按标签id还是文章标题）
 * 获得右侧导航内容
 *
 * @author Tedu
 */
public class ShowListServlet extends ControllerServlet {

    private static final long serialVersionUID = 1L;

    // 按条件查询文章列表
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //实例化分页实体，默认第一页
        Context ctx = new Context();
        PageInfo pi = new PageInfo();
        pi.setPageNum(1);
        pi.setPageSize(6);
        //判断是否是显示第一页
        if (request.getParameter("pageNum") != null) {
            //获得要显示的页码
            pi.setPageNum(Integer.parseInt(request.getParameter("pageNum")));
        }
        String tagid = request.getParameter("tid");
        String titlename = request.getParameter("title");
        // 传入条件，查询文章
        List<Article> list = articleDao.getArticlesByTag(tagid, titlename, pi);
        ctx.setVariable("normalArticle", list);
        ctx.setVariable("pi", pi);
        ctx.setVariable("tid", tagid);
        ctx.setVariable("title", titlename);
        ctx.setVariable("user", request.getSession().getAttribute("user"));
        // 填充右侧所有内容
        super.fillRightPlace(ctx);
        ThymeleafHelper.ThymeleafWrite("list", response, ctx);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
