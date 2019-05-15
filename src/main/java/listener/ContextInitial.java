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
                //�������õ������¼���
                List<SmartArticle> list = JsoupHelper.getSmartArticleList();
                System.out.println("���湤��~");
                //ѭ�����ӵ����ݿ�
                SmartDao sd = new SmartDao();
                TagDao td = new TagDao();
                //��ѯ���б�ǩ
                Map<String, Integer> tags = td.getTagsMap();
                for (SmartArticle sa : list) {
                    int num = sd.addSmartArticle(sa);
                    //��������Ѵ���,����
                    if (num <= 0) {
                        continue;
                    }
                    //���ƥ��ı�ǩ
                    for (String key : tags.keySet()) {
                        if (sa.getTitle().indexOf(key) >= 0 || sa.getContent().indexOf(key) >= 0) {
                            sd.addSmartTag(num, tags.get(key));
                        }
                    }
                    //Ϊ������������ӱ�ǩ
                }
            }
        };
        //�趨������������
        Timer t = new Timer();
        t.schedule(task, 10000, 30 * 20000);
        System.out.println("Timer work!!");
    }
}
