/*
 *  Document : IsOnlineProjectTag.java
 *  Created on Sa, Jan 21 2012, 20:11:19
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
package net.componio.opencms.modules.eight.skinndriva.rd.taglib.commons;

import  com.diawara.commons.taglib.CommonTagBase;
import javax.servlet.jsp.JspException;

import  org.opencms.jsp.CmsJspActionElement;
import  org.opencms.file.CmsObject;
import  org.opencms.file.CmsProject;

/**
 *
 * @author Robert Diawara
 */
public class IsOnlineProjectTag extends CommonTagBase{
    /** true if the project is an online projec. Otherwise false. */
    private boolean onlineProject;

    /**
     * 
     */
    public IsOnlineProjectTag(){
        this.onlineProject = false;
    }
    /**
     * Get the value of onlinePoject
     * @return the value of onlinePoject
     */
    public boolean isOnlinePoject() {
        return this.onlineProject;
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
        String result = String.valueOf(this.onlineProject);
        
        
        // Write out the rendered value
        this.writeTagResult(result);
        
        // Return the tag result
        return CommonTagBase.SKIP_BODY;
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
        int tagResult;
        CmsObject           cmsObj;
        CmsProject          currentProject;
        
        cmsObj = new CmsJspActionElement(pageContext, getRequest(), getResponse()).getCmsObject();
        currentProject = cmsObj.getRequestContext().getCurrentProject();
        this.onlineProject = currentProject.isOnlineProject();
        tagResult = onlineProject ? CommonTagBase.EVAL_BODY_INCLUDE : CommonTagBase.SKIP_BODY;
        
        return tagResult;
    }
    
}
