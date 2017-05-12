/*
 *  Document : BreadCrumbNavigationTag.java
 *  Created on Mi, Nov 09 2016, 20:19:54
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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.opencms.ade.detailpage.CmsDetailPageUtil;
import org.opencms.file.CmsObject;
import org.opencms.file.CmsResource;
import org.opencms.jsp.CmsJspActionElement;
import org.opencms.jsp.CmsJspNavBuilder;

import org.opencms.jsp.CmsJspNavElement;
import org.opencms.jsp.util.CmsJspStandardContextBean;
import org.opencms.main.CmsException;


public class BreadCrumbNavigationTag extends CommonTagBase{
    /** Holds the Servlet Request. */
    private HttpServletRequest  request;
    
    /** Holds the  servlet Response. */
    private HttpServletResponse response;
    
    /** holds the start level of the naviagtion. */
    private int startLevel;
    
    /** Holds the folder (optional) fot which the navigation has to be built. */
    private String folder;
    
    /**
     * Default constructor.
     */
    public BreadCrumbNavigationTag(){
        startLevel = 0;
        folder = null;
    }

    /**
     * Get the value of startLevel
     * @return the value of startLevel
     */
    public int getStartLevel() {
        return startLevel;
    }

    /**
     * Set the value of startLevel
     * @param startLevel new value of startLevel
     */
    public void setStartLevel(int startLevel) {
        // Abort with an Exception, when the provided start level is not a positive integer.
        if(startLevel < 0){
            throw new RuntimeException("Start level should be a non negative integer !");
        }
        this.startLevel = startLevel;
    }

    /**
     * Getter for folder for which the navigation has to be built. If not provided, the current
     * VFS resource will be used.
     * @return The folder for which the navigation has to be built.
     */
    public String getFolder() {
        return folder;
    }

    /**
     * Sets the  folder, for which the navigation has to be built. If not provided, the current
     * VFS resource will p_folder used.
     * @param p_folder  The folder.
     */
    public void setFolder(String p_folder) {
        this.folder = ((p_folder != null) && (p_folder.trim().length() > 0)) ? p_folder : null;
    }
    
    
   /**
     * 
     * @param p_url
     * @return 
     */
    private boolean isInternalLink(String p_url){
        boolean       result;
        String        url           = p_url.trim();
        StringBuilder regexBuilder  = new StringBuilder();
        String        regex;
        String        srvName;
        String        ctxPath;
        String        servletPath;
        
        
        // Fix the url, if necessary
        if(!url.matches("^(http(s?)\\:\\/)?\\/.*$")){
            url = "/" + url;
        }
        
        // Compose server name.
        srvName = escapeString4Regex(request.getServerName());
        
        // Compute context path
        ctxPath = request.getContextPath();
        if(ctxPath.startsWith("/")){
            ctxPath = ctxPath.substring(1);
        }
        ctxPath = escapeString4Regex(ctxPath);
        
        // Compute servlet path
        servletPath = request.getServletPath();
        if(servletPath.startsWith("/")){
            servletPath = servletPath.substring(1);
        }
        servletPath = escapeString4Regex(servletPath);
        
        // Here, we compose a regular expression in the form :
        // ^(((((((https?\\:\\/\\/)?<server name>(\\:[0-9]+)?)?\\/)?<context path>)?\\/)?<servlet path>)?\\/)((?!<servlet path>).*)$
        regexBuilder.append("^(((((((https?\\:\\/\\/)?").append(srvName).append("(\\:[0-9]+)?)?\\/)?");
        regexBuilder.append(ctxPath).append(")?\\/)?");
        regexBuilder.append(servletPath).append(")?\\/)((?!").append(servletPath).append(").*)$");
        regex = regexBuilder.toString();
        
        // Now we checked the passed URL against the regular expression
        result = url.matches(regex);
        
        return result;
    }
    
    /**
     * 
     * @param p_URI
     * @return 
     */
    private boolean isDetailURI(CmsObject p_cmsObj, String p_URI)throws CmsException{
        boolean     result         = false;
        String      resourceName;
        String      uri;
        String      resourcePath[];
        CmsResource res;
        Pattern     pattern1;
        Pattern     pattern2;
        Matcher     matcher1;
        Matcher     matcher2;
        
        if((p_URI != null) && (p_URI.trim().length() > 1)){
            // Here, we first check, if the URI has a query String predeeded by a slash and fix this 
            // since this may cause problems.
            pattern1 = Pattern.compile("^(.+)(\\/\\?)(.*)$");
            pattern2 = Pattern.compile("^(.+)(\\?)(.*)$");
            matcher1 = pattern1.matcher(p_URI);
            matcher2 = pattern2.matcher(p_URI);
            
            if(matcher1.matches()){
                uri =matcher1.replaceAll("$1");
            }else if(matcher2.matches()){
                uri =matcher2.replaceAll("$1");
            }else{
                uri = p_URI;
            }
            
            
            resourcePath = uri.split("/");
            resourceName = resourcePath[resourcePath.length - 1];
            
            if((resourceName != null) && (resourceName.trim().length() > 0)){
                res = CmsDetailPageUtil.lookupPage(p_cmsObj, uri);
                if(res != null){
                    result = !res.getName().equals(resourceName);
                }
            }
        }
        
        return result;
    }


    /**
     * 
     * @param p_url
     * @return 
     */
    private String formatInternalURI(String p_url){
        String result  = "";
        String prefix;
        
        if((p_url != null) && (p_url.trim().length() > 0)){
            result = p_url.trim();
            
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
        List<CmsJspNavElement>    result     = null;
        List<CmsJspNavElement>    temp;
        CmsJspActionElement       actionEl;
        CmsObject                 cmsObj;
        CmsJspNavBuilder          navBuilder;
        CmsJspStandardContextBean ctxBean;
        String                    refererURL;
        String                    refererURI;
        CmsResource               referer;
        
        try{
            request = getRequest();
            response = getResponse();
            actionEl = new CmsJspActionElement(pageContext, request, response);
            cmsObj = actionEl.getCmsObject();
            ctxBean = CmsJspStandardContextBean.getInstance(request);
            
            if(!ctxBean.isDetailRequest()){
                navBuilder = actionEl.getNavigation();
                if(folder != null){
                    result = navBuilder.getNavigationBreadCrumb(folder, getStartLevel(), -1, true);
                }else{
                    result = navBuilder.getNavigationBreadCrumb(getStartLevel(), true);
                }
            }else{
                refererURL = request.getHeader("Referer");
                navBuilder = actionEl.getNavigation();
                
                if((refererURL != null) && (refererURL.trim().length() > 0) && (isInternalLink(refererURL))){
                    refererURI = formatInternalURI(refererURL);
                    if(!isDetailURI(cmsObj, refererURI)){
                        referer = cmsObj.readResource(refererURI);
                        if(referer.isFile()){
                            refererURI = CmsResource.getParentFolder(refererURI);
                        }
                        result = navBuilder.getNavigationBreadCrumb(refererURI, getStartLevel(), -1, true);
                    }else{
                        // Here we build a result with only the root element since the referer is also a detail URI.
                        if(folder != null){
                            temp = navBuilder.getNavigationBreadCrumb(folder, getStartLevel(), -1, true);
                        }else{
                            temp = navBuilder.getNavigationBreadCrumb(getStartLevel(), true);
                        }
                        if(temp.size() > 0){
                            result = new ArrayList<>();
                            result.add(temp.get(0));
                        }else{
                            result = null;
                        }
                    }
                }else{
                    // Here we build a result with only the root element since the referer is  an external link.
                    if(folder != null){
                        temp = navBuilder.getNavigationBreadCrumb(folder, getStartLevel(), -1, true);
                    }else{
                        temp = navBuilder.getNavigationBreadCrumb(getStartLevel(), true);
                    }
                    if(temp.size() > 0){
                        result = new ArrayList<>();
                        result.add(temp.get(0));
                    }else{
                        result = null;
                    }
                }
            }
        }catch(CmsException cmsEx){
            throw new JspException(cmsEx);
        }
        
        this.writeTagResult(result);
        return BodyTagSupport.SKIP_BODY;
    }
}
