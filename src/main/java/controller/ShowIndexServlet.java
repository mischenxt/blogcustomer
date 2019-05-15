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
 * 处理显示主页文章的Servlet
 * 查询前八篇文章，按要求分在3个集合中
 * 获得右侧导航内容
 *
 * @author Tedu
 */
public class ShowIndexServlet extends ControllerServlet {

    private static final long serialVersionUID = 1L;

    // 显示主页
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Context ctx = new Context();
        // 查询主页要显示的文章
        List<Article> topList = articleDao.getArticlesTopList();
        // 置顶第一篇
        ctx.setVariable("fristArticle", topList.get(0));
        // 置顶2-4
        ctx.setVariable("ThreeArticle", topList.subList(1, 4));
        // 普通5-8
        ctx.setVariable("normalArticle", topList.subList(4, topList.size()));
        // 填充右侧所有内容
        super.fillRightPlace(ctx);
        //加载session内容
        ctx.setVariable("user", request.getSession().getAttribute("user"));
        ThymeleafHelper.ThymeleafWrite("index", response, ctx);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
