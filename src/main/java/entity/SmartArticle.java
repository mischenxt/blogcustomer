package entity;

import java.util.List;

public class SmartArticle {
    private int oid;//���ݿ�������
    private String articleId;//����Ψһ��ʶ
    private String title;//���±���
    private String content;//��������
    private int articleRandomDouble;//�����ȡͼƬ��
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
