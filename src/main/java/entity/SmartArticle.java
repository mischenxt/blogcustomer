package entity;

import java.util.List;

public class SmartArticle {
    private int oid;//数据库自增列
    private String articleId;//文章唯一标识
    private String title;//文章标题
    private String content;//文章内容
    private int articleRandomDouble;//随机获取图片的
    private List<Tag> tags;

    public SmartArticle() {
    }

    public SmartArticle(int oid, String articleId, String title, String content, int articleRandomDouble) {
        super();
        this.oid = oid;
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.articleRandomDouble = articleRandomDouble;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getArticleRandomDouble() {
        return articleRandomDouble;
    }

    public void setArticleRandomDouble(int articleRandomDouble) {
        this.articleRandomDouble = articleRandomDouble;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
