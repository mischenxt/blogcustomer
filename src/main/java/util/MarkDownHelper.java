package util;

import org.pegdown.PegDownProcessor;

/**
 * ����һ��MarkDown��ʽ�ַ���ת����html����
 *
 * @author Tedu
 */
public class MarkDownHelper {

    private static PegDownProcessor pdp = new PegDownProcessor();

    public static String getHtmlStr(String md) {

        // ����md��ʽת������ַ���
        return pdp.markdownToHtml(md);

    }

}
