package util;

import org.pegdown.PegDownProcessor;

/**
 * 处理将一个MarkDown格式字符串转换成html的类
 *
 * @author Tedu
 */
public class MarkDownHelper {

    private static PegDownProcessor pdp = new PegDownProcessor();

    public static String getHtmlStr(String md) {

        // 返回md格式转换后的字符串
        return pdp.markdownToHtml(md);

    }

}
