package controller;

import entity.Article;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理新增评论的Servlet
 * 获得评论内容
 * 判断是否登录，如果没有登录返回登录页面
 * 检查文章是否可以评论，不可评论返回
 * 新增评论并更新文章评论数
 *
 * @author Tedu
 */
public class AddCommentServlet extends ControllerServlet {

    private static final long serialVersionUID = 1L;

    // 新增评论
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String reCommentId = request.getParameter("reCommentId");
        String reCommentName = request.getParameter("reCommentName");
        String commentOnId = request.getParameter("commentOnId");
        String commentContent = request.getParameter("commentContent");
        // 执行添加评论
        //获得登录用户
        User user = (User) request.getSession().getAttribute("user");
        //如果未登录，跳到登录页面
        if (user == null) {
            response.sendRedirect("logout.do?msg=2");
            return;
        }
        // 检查文章是否可以评论
        int aid = Integer.parseInt(commentOnId);
        Article a = articleDao.getArticleById(aid);
        // 如果不可评论，返回
        if (a.getArticleCommentable() == 0) {
            response.sendRedirect("showArticle.do?aid=" + aid);
            return;
        }
        // 增加一条评论
        int num = commentDao.addComment(commentContent, commentOnId, reCommentId, reCommentName, user);
        if (num > 0) {
            // 增加1评论数
            articleDao.addArticleComment(aid);
        }
        response.sendRedirect("showArticle.do?aid=" + aid);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
