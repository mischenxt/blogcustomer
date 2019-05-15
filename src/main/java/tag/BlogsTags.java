package tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 将日期（long值）转换成指定格式的自定义标签
 *
 * @author Tedu
 */
@SuppressWarnings("serial")
public class BlogsTags extends TagSupport {

    //参数是long
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
        //转换成(2018-1-1 12:30)分的格式
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
