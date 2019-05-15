package util;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//封装了Thymeleaf的主要功能
public class ThymeleafHelper {
    //Thymeleaf的模板引擎
    private static TemplateEngine templateEngine;

    static {
        //初始化Thymeleaf的模板引擎
        templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("../");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("utf-8");
        templateEngine.setTemplateResolver(resolver);
        templateEngine.isInitialized();
    }

    public static void ThymeleafWrite(String path, HttpServletResponse response, Context ctx) throws IOException {
        //套用模板引擎,返回字符串
        String result = templateEngine.process(path, ctx);
        response.setContentType("text/html;charset=utf-8");
        //将字符串输出到页面上
        PrintWriter writer = response.getWriter();
        // 保存处理结果
        writer.write(result);
        writer.close();
    }

    public static void ThymeleafWrite(String path, HttpServletResponse response) throws IOException {
        //套用模板引擎,返回字符串
        String result = templateEngine.process(path, new Context());
        response.setContentType("text/html;charset=utf-8");
        //将字符串输出到页面上
        PrintWriter writer = response.getWriter();
        // 保存处理结果
        writer.write(result);
        writer.close();
    }

}
