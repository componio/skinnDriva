/*
 *  Document : isDetailRequestTag.java
 *  Created on So, Nov 13 2016, 13:10:10
 *  Copyright (C) 2016 Robert Diawara
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
package com.diawara.commons.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.opencms.jsp.util.CmsJspStandardContextBean;

/**
 * 
 * @author Robert
 */
public class IsDetailRequestTag extends CommonTagBase{

    /**
     * Process the end tag for this instance. This method is invoked by the JSP page implementation object on all Tag handlers.
     * This method will be called after returning from doStartTag. The body of the action may or may not have been evaluated, depending on 
     * the return value of doStartTag.
     * If this method returns EVAL_PAGE, the rest of the page continues to be evaluated. If this method returns SKIP_PAGE, the rest 
     * of the page is not evaluated, the request is completed, and the doEndTag() methods of enclosing tags are not invoked. If this request 
     * was forwarded or included from another page (or Servlet), only the current page evaluation is stopped.
     * The JSP container will resynchronize the values of any AT_BEGIN and AT_END variables (defined by the associated TagExtraInfo or TLD) 
     * after the invocation of doEndTag(). 
     * @return
     * @throws JspException 
     */
    @Override
    public int doEndTag() throws JspException {
        CmsJspStandardContextBean ctxBean;
        
        try{
            ctxBean = CmsJspStandardContextBean.getInstance(getRequest());
            writeTagResult(ctxBean.isDetailRequest());
        }catch(Exception ex){
            throw new JspException(ex);
        }
        
        return BodyTagSupport.SKIP_BODY;
    }

}
