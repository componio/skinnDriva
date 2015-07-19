/*
 *  Document : ThemeResponsiveStylesTag.java
 *  Created on Sat, Jun 27 2015, 7:35:52 PM
 *  Copyright (C) 2015 Robert Diawara
 * 
 *  This file is part of skinnDriva.
 * 
 *  skinnDriva is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  skinnDriva is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 * 
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with OpenCms Theme Engine.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.componio.opencms.modules.eight.skinndriva.rd.taglib.core;

import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import net.componio.opencms.modules.eight.skinndriva.rd.engine.model.GridModel;
import net.componio.opencms.modules.eight.skinndriva.rd.engine.model.StyleReference;
import net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeConfig;
import net.componio.opencms.modules.eight.skinndriva.rd.taglib.ThemeEngineThemeTagBase;
import org.opencms.jsp.CmsJspActionElement;

/**
 * 
 * @author Robert Diawara
 */
public class ThemeResponsiveStylesTag extends ThemeStylesTag{

    private String generateCode(){
        ThemeConfig         themeCfg     = getTheme();
        StringBuilder       out          = new StringBuilder();
        StyleReference[]    stylesList;
        int                 loopCount;
        GridModel           grid;
        int                 lastLimit;
        int                 currentLimit;
        String              currentCss;
        String              responsiveCss;
        String              largeScreenCss;
        String              currentMedia;
        HashSet<String>     groupsSet;
        String              currentGroup;
        HttpServletRequest  request      = (HttpServletRequest)pageContext.getRequest();
        HttpServletResponse response     = (HttpServletResponse)pageContext.getResponse();
        CmsJspActionElement actionEl     = new CmsJspActionElement(pageContext, request, response);

        if(themeCfg != null){
            grid = themeCfg.getGrid();
            stylesList = themeCfg.listStyles();
            groupsSet = getGroupsSet();
            responsiveCss = themeCfg.getResponsiveCssFile();
            
            out.append("\n        <!-- Code generated by ResponsiveStyles tag #######################################################################-->");
            out.append("\n        <!--[if gte IE 9 | !IE ]><!-->");
            if((responsiveCss != null) && (responsiveCss.trim().length() > 0)){
                responsiveCss = actionEl.link(responsiveCss);
                
                out.append("\n            <!--The CSS File for the responsive layout.-->\n");
                out.append("            <link rel=\"stylesheet\" type=\"text/css\" href=\"").append(responsiveCss).append("\"/>\n\n");
            }
            out.append("            <!--The CSS Files for large screens. -->");

            currentLimit = grid.getPageWidth(GridModel.MODE_DESKTOP) + 30;
            for(loopCount = 0; loopCount < stylesList.length; loopCount ++){
                currentGroup = stylesList[loopCount].getGroup();
                currentGroup = currentGroup != null ? currentGroup.trim() : "";
                    
                if((stylesList[loopCount].isUsedForLargeScreens()) && ((groupsSet == null) || (groupsSet.contains(currentGroup)))){
                    currentMedia = stylesList[loopCount].getMedia();
                    
                    if((currentMedia == null) || (currentMedia.contains("all")) || (currentMedia.contains("screen"))){
                        currentCss = actionEl.link(stylesList[loopCount].getURI());
                        out.append("\n            <link rel=\"stylesheet\" type=\"text/css\" href=\"").append(currentCss).
                              append("\" media=\"screen and (min-width: ").append(String.valueOf(currentLimit)).append(grid.getUnit()).
                              append(") and (min-device-width: ").append(String.valueOf(GridModel.DESKTOP_SIZE_LIMIT)).append("px)\"/>");
                    }
                }
            }
            lastLimit = currentLimit;

            if(grid.isModeEnabled(GridModel.MODE_MEDIUM)){
                out.append("\n\n            <!--The CSS Files for medium size screens. -->");

                currentLimit = grid.getPageWidth(GridModel.MODE_MEDIUM) + 30;
                for(loopCount = 0; loopCount < stylesList.length; loopCount ++){
                    currentGroup = stylesList[loopCount].getGroup();
                    currentGroup = currentGroup != null ? currentGroup.trim() : "";
                    
                    if((stylesList[loopCount].isUsedForMediumScreens())  && ((groupsSet == null) || (groupsSet.contains(currentGroup)))){
                        currentMedia = stylesList[loopCount].getMedia();

                        if((currentMedia == null) || (currentMedia.contains("all")) || (currentMedia.contains("screen"))){
                            currentCss = actionEl.link(stylesList[loopCount].getURI());
                            out.append("\n            <link rel=\"stylesheet\" type=\"text/css\" href=\"").append(currentCss).
                              append("\" media=\"screen and (min-width: ").append(String.valueOf(currentLimit)).append(grid.getUnit()).
                                  append(") and (max-width: ").append(String.valueOf(lastLimit - 1)).append(grid.getUnit()).
                                  append(") and (min-device-width: ").append(String.valueOf(GridModel.DESKTOP_SIZE_LIMIT)).append("px)\"/>\n");
                            out.append("            <link rel=\"stylesheet\" type=\"text/css\" href=\"").append(currentCss).
                              append("\" media=\"screen and (min-width: ").append(String.valueOf(currentLimit)).append(grid.getUnit()).
                                  append(") and (max-width: ").append(String.valueOf(lastLimit - 1)).append(grid.getUnit()).
                                  append(") and (max-device-width: ").append(String.valueOf(GridModel.DESKTOP_SIZE_LIMIT - 1)).append("px)\"/>\n");
                        }
                    }
                }
                lastLimit = currentLimit;

                if(grid.isModeEnabled(GridModel.MODE_SMALL)){
                    out.append("\n\n            <!--The CSS Files for small size screens. -->");

                    for(loopCount = 0; loopCount < stylesList.length; loopCount ++){
                        currentGroup = stylesList[loopCount].getGroup();
                        currentGroup = currentGroup != null ? currentGroup.trim() : "";

                        if((stylesList[loopCount].isUsedForSmallScreens())  && ((groupsSet == null) || (groupsSet.contains(currentGroup)))){
                            currentMedia = stylesList[loopCount].getMedia();

                            if((currentMedia == null) || (currentMedia.contains("all")) || (currentMedia.contains("screen"))){
                                currentCss = actionEl.link(stylesList[loopCount].getURI());
                                out.append("\n            <link rel=\"stylesheet\" type=\"text/css\" href=\"").append(currentCss).
                                      append("\" media=\"screen and (max-width: ").append(String.valueOf(lastLimit - 1)).append(grid.getUnit()).
                                      append(") and (min-device-width: ").append(String.valueOf(GridModel.DESKTOP_SIZE_LIMIT)).append("px)\"/>");
                                out.append("\n            <link rel=\"stylesheet\" type=\"text/css\" href=\"").append(currentCss).
                                      append("\" media=\"screen and (max-width: ").append(String.valueOf(lastLimit - 1)).append(grid.getUnit()).
                                      append(") and (max-device-width: ").append(String.valueOf(GridModel.DESKTOP_SIZE_LIMIT - 1)).append("px)\"/>\n");
                            }
                        }
                    }
                }
            }else if(grid.isModeEnabled(GridModel.MODE_SMALL)){
                out.append("\n\n            <!--The CSS Files for medium and small size screens. -->\n");

                currentLimit = grid.getPageWidth(GridModel.MODE_SMALL) + 30;
                for(loopCount = 0; loopCount < stylesList.length; loopCount ++){
                    currentGroup = stylesList[loopCount].getGroup();
                    currentGroup = currentGroup != null ? currentGroup.trim() : "";

                    if((stylesList[loopCount].isUsedForSmallScreens())  && ((groupsSet == null) || (groupsSet.contains(currentGroup)))){
                        currentMedia = stylesList[loopCount].getMedia();

                        if((currentMedia == null) || (currentMedia.contains("all")) || (currentMedia.contains("screen"))){
                            currentCss = actionEl.link(stylesList[loopCount].getURI());
                            out.append("\n            <link rel=\"stylesheet\" type=\"text/css\" href=\"").append(currentCss).append("\" media=\"screen and (max-width: ").
                                  append(String.valueOf(lastLimit - 1)).append(grid.getUnit()).append(") and (min-device-width: ").append(String.valueOf(GridModel.DESKTOP_SIZE_LIMIT)).
                                  append("px)\"/>");
                            out.append("\n            <link rel=\"stylesheet\" type=\"text/css\" href=\"").append(currentCss).append("\" media=\"screen and (max-width: ").
                                  append(String.valueOf(lastLimit - 1)).append(grid.getUnit()).append(") and (max-device-width: ").append(String.valueOf(GridModel.DESKTOP_SIZE_LIMIT - 1)).
                                  append("px)\"/>\n");
                        }
                    }
                }
            }
            largeScreenCss = grid.getCssFile(GridModel.MODE_DESKTOP);

            out.append("        <!--<![endif]-->\n\n");
            out.append("\n        <!--For older IE versions, we just provide the CSS Files for the large screens.-->");
            out.append("\n        <!--[if lt IE 9]>\n");
            
            if((largeScreenCss != null) && (largeScreenCss.trim().length() > 0)){
                largeScreenCss = actionEl.link(largeScreenCss);
                
                out.append("            <link rel=\"stylesheet\" type=\"text/css\" href=\"").append(actionEl.link(largeScreenCss)).append("\"/>\n\n");
            }
            for(loopCount = 0; loopCount < stylesList.length; loopCount ++){
                currentGroup = stylesList[loopCount].getGroup();
                currentGroup = currentGroup != null ? currentGroup.trim() : "";
                    
                if((stylesList[loopCount].isUsedForLargeScreens()) && ((groupsSet == null) || (groupsSet.contains(currentGroup)))){
                    currentMedia = stylesList[loopCount].getMedia();
                    
                    if((currentMedia == null) || (currentMedia.contains("all")) || (currentMedia.contains("screen"))){
                        currentCss = actionEl.link(stylesList[loopCount].getURI());
                        out.append("\n            <link rel=\"stylesheet\" type=\"text/css\" href=\"").append(currentCss).append("\"/>");
                    }
                }
            }
            out.append("\n        <![endif]-->");
            out.append("\n        <!-- End of code generated by ResponsiveStyles tag ################################################################-->");
        }
        return out.toString();
    }
    
    /**
     * Process the end tag for this instance. This method is invoked by the JSP page implementation object on all Tag handlers.
     * This method will be called after returning from doStartTag. The body of the action may or may not have been evaluated, depending on 
     * the return value of doStartTag.
     * If this method returns EVAL_PAGE, the rest of the page continues to be evaluated. If this method returns SKIP_PAGE, the rest 
     * of the page is not evaluated, the request is completed, and the doEndTag() methods of enclosing tags are not invoked. If this request 
     * was forwarded or included from another page (or Servlet), only the current page evaluation is stopped.
     * The JSP container will resynchronize the values of any AT_BEGIN and AT_END variables (defined by the associated TagExtraInfo or TLD) 
     * after the invocation of doEndTag(). 
     * @return indication of whether to continue evaluating the JSP page.
     * @throws JspException if an error occurred while processing this tag
     */
    @Override
    public int doEndTag() throws JspException {
        String code = this.generateCode();
        
        this.writeTagResult(code);
            
        // Return the tag result
        return ThemeEngineThemeTagBase.SKIP_BODY;
    }
}