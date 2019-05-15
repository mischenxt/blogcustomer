package controller;

import dao.*;
import entity.Article;
import entity.Link;
import entity.Tag;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.List;
import java.util.Map;

/**
 * 所有自定义servlet类的父类
 * 其中包含了所有实体对应的Dao类的对象，供子类调用
 * 还包含了多个Servlet需要调用的一个方法fillRightPlace
 * fillRightPlace方法用于将页面右侧所有推荐阅读的内容查询出并存入作用域
 *
 * @author Tedu
 */
public abstract class ControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    //声明各种dao类
    protected ArticleDao articleDao;
    protected CommentDao commentDao;
    protected LinkDao linkDao;
    protected OptionDao optionDao;
    protected TagDao tagDao;
    protected UserDao userDao;
    protected SmartDao smartDao;

    @Override
    public void init() throws ServletException {
        //在Servlet初始化方法中实例化所有dao类
        articleDao = new ArticleDao();
        commentDao = new CommentDao();
        linkDao = new LinkDao();
        optionDao = new OptionDao();
        tagDao = new TagDao();
        userDao = new UserDao();
        smartDao = new SmartDao();
    }

    protected void fillRightPlace(Context ctx) {
        // TODO Auto-generated method stub
        // 最新发布
        List<Article> newList = articleDao.getArticlesNewList();
        // 评论最多
        List<Article> commentList = articleDao.getArticlesCommentList();
        // 浏览最多
        List<Article> viewList = articleDao.getArticlesViewList();
        // 标签
        List<Tag> tagList = tagDao.getTopTag();
        // System.out.println(tagList.size());
        // 友情链接
        List<Link> linkList = linkDao.getTopLinks();
        // 微博资料
        Map<String, String> map = optionDao.getOptions();
        ctx.setVariable("newList", newList);
        ctx.setVariable("commentList", commentList);
        ctx.setVariable("viewList", viewList);
        ctx.setVariable("tagList", tagList);
        ctx.setVariable("linkList", linkList);
        ctx.setVariable("options", map);
    }
}
