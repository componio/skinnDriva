/*
 *  Document : DetailContentTag.java
 *  Created on Mi, Nov 09 2016, 20:20:46
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.opencms.ade.detailpage.CmsDetailPageUtil;
import org.opencms.file.CmsObject;
import org.opencms.file.CmsResource;
import org.opencms.jsp.CmsJspActionElement;
import org.opencms.main.OpenCms;

/**
 * 
 * @author Robert
 */
public class DetailContentTag extends CommonTagBase{
    /** Holds the Servlet Request. */
    private HttpServletRequest  request;
    
    /** Holds the  servlet Response. */
    private HttpServletResponse response;

    /**
     * 
     * @param p_url
     * @return 
     */
    private String getPhysicalDetailURI(){
        String uri;
        String result  = "";
        String prefix;
        CmsJspActionElement actionEl;
        CmsObject           cmsObj;
        
        actionEl = new CmsJspActionElement(pageContext, request, response);
        cmsObj = actionEl.getCmsObject();
        uri = request.getPathInfo();
        
        if((uri != null) && (uri.trim().length() > 0)){
            result = uri.trim();
            
            // Remove leading protocol string, if  there's one
            if(result.startsWith("http://")){
                prefix = "http://";
                result = result.substring(prefix.length());
            }else if(result.startsWith("https://")){
                prefix = "https://";
                result = result.substring(prefix.length());
            }else if(!result.startsWith("/")){
                result = "/" + result;
            }
            
            // Remove leading server name if there's one
            prefix = getRequest().getServerName();
            if(result.startsWith(prefix)){
                result = result.substring(prefix.length());
            }
            
            // Remove leading port number, if there's one
            prefix = ":" + String.valueOf(getRequest().getServerPort());
            if(result.startsWith(prefix)){
                result = result.substring(prefix.length());
            }
            
            // Remove possibly existing context path
            prefix = getRequest().getContextPath();
            if(result.startsWith(prefix)){
                result = result.substring(prefix.length());
            }
            
            // Remove possibly existing servlet path
            prefix = getRequest().getServletPath();
            if(result.startsWith(prefix)){
                result = result.substring(prefix.length());
            }
            
            // If the path is a detail page link, then resolve it.
            result = resolveDetailLink(cmsObj, result);
        }
        return result;
    }
    
    /**
     * 
     * @param p_cmsObj
     * @param p_url
     * @return 
     */
    private String resolveDetailLink(CmsObject p_cmsObj, String p_url){
        String      result = null;
        CmsResource res;
        String      siteRoot;
        String      path;
        String      url;
         
        if((p_url != null) && (p_url.trim().length() > 0)){
            try{
                url = p_url.trim().split("\\?")[0];
                res = CmsDetailPageUtil.lookupPage(p_cmsObj, url);
                if(res != null){
                    siteRoot = OpenCms.getSiteManager().getCurrentSite(p_cmsObj).getSiteRoot();
                    path = res.getRootPath();
                    if(!res.isFile()){
                        path += path.endsWith("/") ? "index.html" : "/index.html";
                    }
                    if(path.startsWith(siteRoot)){
                        path = path.substring(siteRoot.length());
                    }
                    result = p_cmsObj.existsResource(path) ? path : null;
                }
            }catch(Throwable t){
                result = p_url;
            }
        }
        return result;
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
     * @return
     * @throws JspException 
     */
    @Override
    public int doEndTag() throws JspException {
        
        request = getRequest();
        response = getResponse();
        
        writeTagResult(this.getPhysicalDetailURI());
        return BodyTagSupport.SKIP_BODY;
    }
}
