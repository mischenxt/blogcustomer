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
            Document doc = Jsoup.connect(TARGET_URL).get();//爬虫目标网站
            Elements els = doc.select("#itemList h2>a");//找出指定的超链接
            for (Element el : els) {//遍历所有符合条件的超链接
                //访问这个超链接的内容
                Document dd = Jsoup.connect("https://readhub.cn" + el.attr("href")).get();
                //获得信息封装到实体中
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
