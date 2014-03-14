/*
 *  Document : ShowRightHandBarTag.java
 *  Created on Fr, Feb 10 2012, 17:58:24
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

import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeConfig;
import  net.componio.opencms.modules.eight.skinndriva.rd.taglib.ThemeEngineThemeTagBase;
import  javax.servlet.jsp.JspException;

import  org.opencms.jsp.CmsJspActionElement;
import  org.opencms.file.CmsProperty;
import  org.opencms.main.CmsException;

/**
 *
 * @author Robert Diawara
 */
public class ShowRightHandBarTag extends ThemeEngineThemeTagBase{
    /** Holds the value of the property &quot;resource&quot;. */
    private String resource;
    /** True if left hand bar has to be visible. Otherwise false. */
    private boolean showRightHandBar;
    
    /**
     * Default constructor
     */
    public ShowRightHandBarTag(){
        // Set the result of the tag to the default value
        showRightHandBar = true;
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
        if((resource == null) || (resource.trim().length() == 0)){
            throw new RuntimeException("Error in class \"" + getClass().getName() + "\" ! Could not set the resource name "
                    + "because the attribute is null or an empty string. Therefore the setter has been aborted. Please "
                    + "ommit this attribute if you don't need it, or provide a valid value for!");
        }
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
     * @throws JspException 
     */
    @Override
    public int doEndTag() throws JspException {
        // Write out the rendered value
        this.writeTagResult(String.valueOf(showRightHandBar));
        
        return ThemeEngineThemeTagBase.SKIP_BODY;
    }

    /**
     * Process the start tag for this instance. This method is invoked by the JSP page implementation object.
     * The doStartTag method assumes that the properties pageContext and parent have been set. It also assumes that any 
     * properties exposed as attributes have been set too. When this method is invoked, the body has not yet been evaluated.
     * This method returns Tag.EVAL_BODY_INCLUDE or BodyTag.EVAL_BODY_BUFFERED to indicate that the body of the action 
     * should be evaluated or SKIP_BODY to indicate otherwise.
     * When a Tag returns EVAL_BODY_INCLUDE the result of evaluating the body (if any) is included into the current 
     * "out" JspWriter as it happens and then doEndTag() is invoked.
     * BodyTag.EVAL_BODY_BUFFERED is only valid if the tag handler implements BodyTag.
     * The JSP container will resynchronize the values of any AT_BEGIN and NESTED variables (defined by the associated 
     * TagExtraInfo or TLD) after the invocation of doStartTag(), except for a tag handler implementing BodyTag whose 
     * doStartTag() method returns BodyTag.EVAL_BODY_BUFFERED. 
     * @return EVAL_BODY_INCLUDE if the tag wants to process body, SKIP_BODY if it does not want to process it.
     * @throws JspException if an error occurred while processing this tag
     */
    @Override
    public int doStartTag() throws JspException {
        String              resURI        = getResource();
        CmsJspActionElement actionEl      = new CmsJspActionElement(pageContext, getRequest(), getResponse());
        CmsProperty         propertyObj;
        ThemeConfig         themeCfg      = getTheme();
        
        try{
            // If no resource URI provided, set it to the URI of the currently
            // requested resource.
            if(resURI == null){
                resURI = actionEl.info("opencms.request.uri");
            }

            // Try to read the result from VFS. If not available on VFS, try to read from theme. 
            propertyObj = actionEl.getCmsObject().readPropertyObject(resURI, "ShowRightHandBar", true);
            if((propertyObj != null) && (propertyObj.getValue() != null)){
                showRightHandBar =  Boolean.parseBoolean(propertyObj.getValue());
            }else{
                showRightHandBar = themeCfg != null ? themeCfg.isShowRightHandBar() : true;
            }
        }catch(CmsException cmsEx){
            throw new JspException(cmsEx);
        }
        
        // Return the standard tag result
        return showRightHandBar ? ThemeEngineThemeTagBase.EVAL_BODY_INCLUDE : ThemeEngineThemeTagBase.SKIP_BODY;
    }
}
