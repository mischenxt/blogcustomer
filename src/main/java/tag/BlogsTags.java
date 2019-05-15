package tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * �����ڣ�longֵ��ת����ָ����ʽ���Զ����ǩ
 *
 * @author Tedu
 */
@SuppressWarnings("serial")
public class BlogsTags extends TagSupport {

    //������long
    private long date;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = this.pageContext.getOut();
        //ת����(2018-1-1 12:30)�ֵĸ�ʽ
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            out.println(sdf.format(new Date(date)));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }

}
