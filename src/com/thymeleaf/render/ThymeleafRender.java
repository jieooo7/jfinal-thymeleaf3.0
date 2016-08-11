package com.thymeleaf.render;

import java.io.PrintWriter;

import javax.servlet.ServletContext;

import org.eclipse.jetty.server.Request;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.jfinal.config.Constants;
import com.jfinal.config.JFinalConfig;
import com.jfinal.core.JFinal;
import com.jfinal.core.JFinalFilter;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;


public class ThymeleafRender extends Render {

    private static TemplateEngine templateEngine;
    private static String encoding = "UTF-8";
    private static String contentType = "text/html; charset=" + encoding;
    private static String templateMode = "HTML5";
    private static String prefix = "/WEB-INF/template/";
    private static String suffix = ".html";
    
    public ThymeleafRender(String view) {
        this.view = view;
    }

    public static void setTemplateMode(String templateMode) {
        ThymeleafRender.templateMode = templateMode;
    }

    public static void setPrefix(String prefix) {
        ThymeleafRender.prefix = prefix;
    }

    public  void setSuffix(String suffix) {
        ThymeleafRender.suffix = suffix;
    }

    public static void init() {
        if (templateEngine != null) {
            return;
        }

        Constants me = JFinal.me().getConstants();
        encoding = me.getEncoding();
        contentType = "text/html; charset=" + encoding;

        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(JFinal.me().getServletContext());

        // XHTML is the default mode, but we will set it anyway for better understanding of code
        templateResolver.setTemplateMode(templateMode);
        templateResolver.setPrefix(prefix);
        templateResolver.setSuffix(suffix);
        //templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCharacterEncoding(encoding);
        // Set template cache TTL to 1 hour. If not set, entries would live in cache until expelled by LRU
        templateResolver.setCacheTTLMs(Long.valueOf(3600000L));

        templateResolver.setCacheable(!me.getDevMode());

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

    }

    @Override
    public void render() {
        response.setContentType(contentType);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale());
//        for (Enumeration<String> attrs=request.getAttributeNames(); attrs.hasMoreElements();) {
//            String attrName = attrs.nextElement();
//            context.setVariable(attrName, request.getAttribute(attrName));
//        }

        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            templateEngine.process(view, context, writer);
        } catch (Exception e) {
            throw new RenderException(e);
        }
        finally {
            if (writer != null)
                writer.close();
        }
    }
}
