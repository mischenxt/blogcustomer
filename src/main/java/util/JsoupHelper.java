package util;

import entity.SmartArticle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsoupHelper {
    private static final String TARGET_URL = "https://readhub.cn";

    public static List<SmartArticle> getSmartArticleList() {
        try {
            List<SmartArticle> list = new ArrayList<>();
            Document doc = Jsoup.connect(TARGET_URL).get();//����Ŀ����վ
            Elements els = doc.select("#itemList h2>a");//�ҳ�ָ���ĳ�����
            for (Element el : els) {//�������з��������ĳ�����
                //������������ӵ�����
                Document dd = Jsoup.connect("https://readhub.cn" + el.attr("href")).get();
                //�����Ϣ��װ��ʵ����
                Elements et = dd.select(".topicTitle___3DA7c span");
                Elements en = dd.select(".summary___3oqrM");
                SmartArticle sa = new SmartArticle();
                sa.setTitle(et.html());
                sa.setContent(en.html());
                sa.setArticleId(el.attr("href").substring(el.attr("href").lastIndexOf("/") + 1));
                list.add(sa);
            }
            return list;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
