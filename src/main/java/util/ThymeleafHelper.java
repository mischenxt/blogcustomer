package util;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//��װ��Thymeleaf����Ҫ����
public class ThymeleafHelper {
    //Thymeleaf��ģ������
    private static TemplateEngine templateEngine;

    static {
        //��ʼ��Thymeleaf��ģ������
        templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("../");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("utf-8");
        templateEngine.setTemplateResolver(resolver);
        templateEngine.isInitialized();
    }

    public static void ThymeleafWrite(String path, HttpServletResponse response, Context ctx) throws IOException {
        //����ģ������,�����ַ���
        String result = templateEngine.process(path, ctx);
        response.setContentType("text/html;charset=utf-8");
        //���ַ��������ҳ����
        PrintWriter writer = response.getWriter();
        // ���洦����
        writer.write(result);
        writer.close();
    }

    public static void ThymeleafWrite(String path, HttpServletResponse response) throws IOException {
        //����ģ������,�����ַ���
        String result = templateEngine.process(path, new Context());
        response.setContentType("text/html;charset=utf-8");
        //���ַ��������ҳ����
        PrintWriter writer = response.getWriter();
        // ���洦����
        writer.write(result);
        writer.close();
    }

}
