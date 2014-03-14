/*
 *  Document : ThemeTag.java
 *  Created on Sa, Jan 14 2012, 20:03:07
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

import  com.diawara.commons.taglib.CommonTagBase;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeEngineConfig;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeConfig;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.controller.ThemeFactory;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.controller.I_ThemeEngineConfigController;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigException;

import  org.opencms.jsp.CmsJspActionElement;
import  org.opencms.main.CmsException;
import  org.opencms.file.CmsProperty;

import  javax.servlet.jsp.JspException;

/**
 *
 * @author Robert Diawara
 */
public class ThemeTag extends CommonTagBase{
    /** Holds the resource to get the theme for. */
    private String resource;
    
    /**
     * Default Constructor
     */
    public ThemeTag(){
        resource = null;
    }

    /**
     * Get the value of resource
     * @return the value of resource
     */
    public String getResource() {
        return ((resource != null) && (resource.trim().length() > 0)) ? resource : null;
    }

    /**
     * Set the value of resource
     * @param resource new value of resource
     */
    public void setResource(String resource) {
        this.resource = resource;
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
        I_ThemeEngineConfigController engineController;
        ThemeEngineConfig             engineConfig;
        ThemeConfig                   defaultTheme;
        ThemeConfig                   result        = null;
        ThemeConfig[]                 configList;
        int                           loopCount;
        String                        runtimeParam;
        String                        resultId      = null;
        String                        defaultId;
        CmsJspActionElement           actionEl;
        CmsProperty                   propertyDef;
        String                        res;
        
        try{
            // Load the engine confif from backend.
            engineController = ThemeFactory.getEngineConfigController(pageContext, getRequest(), getResponse());
            engineConfig = engineController.loadThemeEngine();
            
            // Get the id of the default theme
            defaultTheme = engineConfig.getDefaultTheme();
            defaultId = defaultTheme != null ? defaultTheme.getId() : null;
            
            // Check, if a runtime parameter is defined for the engine and if its passed with the request
            runtimeParam = engineConfig.getRuntimeParameter();
            if(runtimeParam != null){
                resultId = getRequest().getParameter(runtimeParam);
            }
            
            // If no runtime parameter retrieved, we try to get the id of the theme assigned to the page
            if(resultId == null){
                actionEl = new CmsJspActionElement(pageContext, getRequest(), getResponse());
                res = getResource();
                if((res == null) || (res.trim().length() == 0)){
                    res = actionEl.info("opencms.request.uri");
                }
                propertyDef = actionEl.getCmsObject().readPropertyObject(res, "Theme", true);
                resultId = propertyDef != null ? propertyDef.getValue(defaultId) : null;
            }
            
            // If theme id found, then get the theme with the respective id
            if(resultId != null){
                configList = engineConfig.listThemeConfigs();
                for(loopCount = 0, result = null; (loopCount < configList.length) && (result == null); loopCount ++){
                    if(configList[loopCount].getId().equals(resultId)){
                        result = configList[loopCount];
                    }
                }
                
                // If the theme was not found, maybe because the runtime parameter or the property of the
                // object is not correctly spelled, then switch to the default theme.
                if(result == null){
                    result = defaultTheme;
                }
            }
            
            // Write out the result
            this.writeTagResult(result);
        }catch(ThemeConfigException themeConfigEx){
            throw new JspException(themeConfigEx);
        }catch(CmsException cmsEx){
            throw new JspException(cmsEx);
        }
        
        // Return the tag result
        return CommonTagBase.SKIP_BODY;
    }
    
}
