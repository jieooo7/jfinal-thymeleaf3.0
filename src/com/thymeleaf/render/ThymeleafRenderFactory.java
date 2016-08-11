package com.thymeleaf.render;

import com.jfinal.render.IMainRenderFactory;
import com.jfinal.render.Render;

/**
* ThymeleafRenderFactory. (Integration, Singleton, ThreadSafe)
*
* @author dafei (myaniu AT gmail DOT com)
*/
public class ThymeleafRenderFactory implements IMainRenderFactory {
    public ThymeleafRenderFactory() {
        ThymeleafRender.init();
    }


    public Render getRender(String view) {
        return new ThymeleafRender(view);
    }

    public String getViewExtension() {
        return ".html";
        //return WebEngine.getTemplateSuffix(JFinal.me().getServletContext());
    }

}