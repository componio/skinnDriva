/*
 *  Document : DataAreaWidthTag.java
 *  Created on Sa, Jan 21 2012, 20:20:37
 *  Copyright (C) 2012 Robert Diawara
 * 
 *  This file is part of OpenCms Theme Engine.
 * 
 *  OpenCms Theme Engine is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  OpenCms Theme Engine is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 * 
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with OpenCms Theme Engine.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.componio.opencms.modules.eight.skinndriva.rd.taglib.core;

import  net.componio.opencms.modules.eight.skinndriva.rd.taglib.ThemeEngineDimensionsTagBase;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeConfig;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigException;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeDimensions;

import  javax.servlet.jsp.JspException;

import  org.opencms.jsp.CmsJspActionElement;
import  org.opencms.file.CmsProperty;
import  org.opencms.main.CmsException;

/**
 *
 * @author Robert Diawara
 */
public class DataAreaWidthTag extends ThemeEngineDimensionsTagBase{

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
        ThemeConfig         themeCfg     = getTheme();
        String              result;
        int                 pageLayout;
        CmsJspActionElement actionEl;
        String              resourceName;
        boolean             showLeftHandBar;
        boolean             showRightHandBar;
        String              tmpStr;
        CmsProperty         property;
        
        try{
            // Try to get the value from the theme configuration.
            if(themeCfg != null){
                actionEl = new CmsJspActionElement(pageContext, getRequest(), getResponse());
                resourceName = actionEl.info("opencms.request.uri");
                
                // Check if the left hand bar is visible for the current resource.
                property = actionEl.getCmsObject().readPropertyObject(resourceName, "ShowLeftHandBar", false);
                tmpStr = String.valueOf(themeCfg.isShowLeftHandBar());
                showLeftHandBar = Boolean.parseBoolean(property != null ? property.getValue(tmpStr) : tmpStr);
                
               
                // Check if the right hand bar is visible for the current resource.
                property = actionEl.getCmsObject().readPropertyObject(resourceName, "ShowRightHandBar", false);
                tmpStr = String.valueOf(themeCfg.isShowRightHandBar());
                showRightHandBar = Boolean.parseBoolean(property != null ? property.getValue(tmpStr) : tmpStr);
                
                // Compute the page layout
                if(showLeftHandBar){
                    if(showRightHandBar){
                        pageLayout = ThemeDimensions.THREE_COL_LAYOUT;
                    }else{
                        pageLayout = ThemeDimensions.TWO_COL_LAYOUT_NORIGHTPANE;
                    }
                }else{
                    if(showRightHandBar){
                        pageLayout = ThemeDimensions.TWO_COL_LAYOUT_NOLEFTPANE;
                    }else{
                        pageLayout = ThemeDimensions.ONE_COL_LAYOUT;
                    }
                }
                
                // Get the result
                if(isFormatted()){
                    result = themeCfg.getDimensions().getFormattedDataAreaWidth(pageLayout);
                }else{
                    result = String.valueOf(themeCfg.getDimensions().getDataAreaWidth(pageLayout));
                }
            }else{
                throw new JspException("Can't render the tag value. The theme specified with the attribute \"theme\" "
                        + "is not available !");
            }
        }catch(ThemeConfigException themeConfigEx){
            throw new JspException(themeConfigEx);
        }catch(CmsException cmsEx){
            throw new JspException(cmsEx);
        }
        
        // Write out the rendered value
        this.writeTagResult(result);
        
        // Return the tag result
        return ThemeEngineDimensionsTagBase.SKIP_BODY;
    }
}
