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
 * �����Զ���servlet��ĸ���
 * ���а���������ʵ���Ӧ��Dao��Ķ��󣬹��������
 * �������˶��Servlet��Ҫ���õ�һ������fillRightPlace
 * fillRightPlace�������ڽ�ҳ���Ҳ������Ƽ��Ķ������ݲ�ѯ��������������
 *
 * @author Tedu
 */
public abstract class ControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    //��������dao��
    protected ArticleDao articleDao;
    protected CommentDao commentDao;
    protected LinkDao linkDao;
    protected OptionDao optionDao;
    protected TagDao tagDao;
    protected UserDao userDao;
    protected SmartDao smartDao;

    @Override
    public void init() throws ServletException {
        //��Servlet��ʼ��������ʵ��������dao��
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
        // ���·���
        List<Article> newList = articleDao.getArticlesNewList();
        // �������
        List<Article> commentList = articleDao.getArticlesCommentList();
        // ������
        List<Article> viewList = articleDao.getArticlesViewList();
        // ��ǩ
        List<Tag> tagList = tagDao.getTopTag();
        // System.out.println(tagList.size());
        // ��������
        List<Link> linkList = linkDao.getTopLinks();
        // ΢������
        Map<String, String> map = optionDao.getOptions();
        ctx.setVariable("newList", newList);
        ctx.setVariable("commentList", commentList);
        ctx.setVariable("viewList", viewList);
        ctx.setVariable("tagList", tagList);
        ctx.setVariable("linkList", linkList);
        ctx.setVariable("options", map);
    }
}
