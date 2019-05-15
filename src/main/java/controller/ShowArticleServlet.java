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
 * 处理单查一篇文章的Servlet
 * 获得要显示文章的id
 * 执行前一篇、后一篇、随机阅读、相关阅读等查询操作
 * 获得这篇文章的评论
 * 获得右侧导航内容
 *
 * @author Tedu
 */
public class ShowArticleServlet extends ControllerServlet {
    private static final long serialVersionUID = 1L;

    // 显示某一篇文章
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Context ctx = new Context();
        // 获得文章id
        int oid = Integer.parseInt(request.getParameter("aid"));
        // 浏览文章
        Article ar = articleDao.getArticleById(oid);
        // 查询前一篇和后一篇文章
        Article prev = articleDao.getArticlePrev(oid);
        Article next = articleDao.getArticleNext(oid);
        // 相同标签的相关阅读
        List<Article> sameArticle = articleDao.getSameTagArticle(oid);
        // 随机阅读
        List<Article> ranArticle = articleDao.getRandomArticle();
        // 这篇文章的所有评论
        List<Comment> commList = commentDao.getCommentByArticleId(oid);
        // 将页面上所有信息填充
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
