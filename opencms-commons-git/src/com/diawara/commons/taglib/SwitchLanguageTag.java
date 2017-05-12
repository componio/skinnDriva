/*
 *  Document : SwitchLanguageTag.java
 *  Created on Fr, Feb 26 2016, 21:03:54
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

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.opencms.file.CmsObject;
import org.opencms.file.CmsResource;

import org.opencms.jsp.CmsJspActionElement;
import org.opencms.jsp.util.CmsJspStandardContextBean;
import org.opencms.main.OpenCms;


public class SwitchLanguageTag extends CommonTagBase{
    /** Holds the value of the property &quot;locale&quot; */
    private String locale;
    /** Holds the root folder for all pages of the given language. */
    private String rootFolder;
    
    /**
     * Default Constructor.
     */
    public SwitchLanguageTag(){
    }

    /**
     * Get the value of regex. This is the regular expression, which texts are to be checked against.
     * @return the value of regex
     */
    private String getRegex() {
        return "^(\\/.+\\/(en|de|fr|es|pt|it)\\/)(.*)$";
    }

    /**
     * Get the value of searchText. This is  the URI of the JSP which this tag was called from and also
     * the string which will be tested against the regular expression. If it matches, a replacement 
     * according to the replacement string here in this class, will be done.
     * @param p_cmsObj Offers access to the VFS.
     * @return the value of searchText
     * @throws javax.servlet.jsp.JspException When execution fails due to an unexpected error
     */
    private String getResourceURI(CmsObject p_cmsObj) throws JspException{
        CmsJspActionElement       actionEl;
        String                    result;
        CmsResource               res;
        String                    siteRoot;
        CmsJspStandardContextBean ctxBean;
        
        try{
            actionEl = new CmsJspActionElement(pageContext, getRequest(), getResponse());
            result = actionEl.info("opencms.request.uri");
            ctxBean = CmsJspStandardContextBean.getInstance(getRequest());
            if(ctxBean.isDetailRequest()){
                res = ctxBean.getDetailContent();
                if(res != null){
                    p_cmsObj = actionEl.getCmsObject();
                    siteRoot = OpenCms.getSiteManager().getCurrentSite(p_cmsObj).getSiteRoot();
                    result = res.getRootPath();

                    // remove the possibly existing leading site root
                    if(result.startsWith(siteRoot)){
                        result = result.substring(siteRoot.length());
                    }
                }
            }
        }catch(Exception ex){
            throw new JspException(ex);
        }
        
        return result;
    }

    /**
     * Get the value of replacementString. Matches of the regular expressions will be 
     * replaced by this replacement string. Back references $1 to $9 are allowed.
     * @return the value of replacementString
     * @throws javax.servlet.jsp.JspException
     */
    public String getReplacementString() throws JspException{
        StringBuilder    resultBuilder = new StringBuilder();
        
        resultBuilder.append(getRootFolder()).append("$3");
        return resultBuilder.toString();
    }


    /**
     * Get the value of rootFolder
     * @return the value of rootFolder
     */
    public String getRootFolder() {
        return rootFolder;
    }

    /**
     * Set the value of rootFolder
     * @param rootFolder new value of rootFolder
     */
    public void setRootFolder(String rootFolder) {
        this.rootFolder = rootFolder;
    }
    
    /**
     * Get the value of locale. This is the locale to switch to for the currently displayed page.
     * @return the value of locale
     */
    public String getLocale() {
        return ((locale != null) && (locale.trim().length() > 0)) ? locale : "";
    }

    /**
     * Set the value of locale This is the locale to switch to for the currently displayed page.
     * @param locale new value of locale
     */
    public void setLocale(String locale) {
        // Abort, if a null or empty locale provided.
        if((locale == null) || (locale.trim().length() == 0)){
            throw new RuntimeException("Null or empty values are not allowed for the locale !");
        }
        this.locale = locale;
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
        Pattern                   pattern;
        Matcher                   matcher;
        StringBuilder             resultBuilder  = new StringBuilder();
        String                    result;
        CmsObject                 cmsObj         = new CmsJspActionElement(pageContext, getRequest(), getResponse()).getCmsObject();
        String                    resURI         = this.getResourceURI(cmsObj);
        Locale                    currentLocale  = cmsObj.getRequestContext().getLocale();
        
        if(!currentLocale.equals(new Locale(this.getLocale()))){
            pattern = Pattern.compile(this.getRegex());
            matcher = pattern.matcher(resURI);
            if(matcher.matches()){
                resultBuilder.append(matcher.replaceAll(this.getReplacementString()));

                // Here, we first check, if the path is valid and the resource exists.
                result = cmsObj.existsResource(resultBuilder.toString()) ? resultBuilder.toString() : null;
            }else{
                resultBuilder.append(!resURI.endsWith("/") ? resURI : resURI.substring(0, resURI.length() - 1));
                resultBuilder.append(resultBuilder.indexOf("?") == -1 ? "?" : "&");
                resultBuilder.append("__locale=").append(this.getLocale());

                result = resultBuilder.toString();
            }
        }else{
            result = null;
        }
        
        this.writeTagResult(result);
        return BodyTagSupport.SKIP_BODY;
    }

}
