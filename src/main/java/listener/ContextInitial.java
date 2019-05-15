package listener;

import dao.SmartDao;
import dao.TagDao;
import entity.SmartArticle;
import util.JsoupHelper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ContextInitial implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //获得爬虫得到的文章集合
                List<SmartArticle> list = JsoupHelper.getSmartArticleList();
                System.out.println("爬虫工作~");
                //循环增加到数据库
                SmartDao sd = new SmartDao();
                TagDao td = new TagDao();
                //查询所有标签
                Map<String, Integer> tags = td.getTagsMap();
                for (SmartArticle sa : list) {
                    int num = sd.addSmartArticle(sa);
                    //如果文章已存在,跳过
                    if (num <= 0) {
                        continue;
                    }
                    //检查匹配的标签
                    for (String key : tags.keySet()) {
                        if (sa.getTitle().indexOf(key) >= 0 || sa.getContent().indexOf(key) >= 0) {
                            sd.addSmartTag(num, tags.get(key));
                        }
                    }
                    //为新增的文章添加标签
                }
            }
        };
        //设定周期运行爬虫
        Timer t = new Timer();
        t.schedule(task, 10000, 30 * 20000);
        System.out.println("Timer work!!");
    }
}
