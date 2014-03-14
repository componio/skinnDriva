/*
 *  Document : PropertyTag.java
 *  Created on Sa, Jan 21 2012, 20:11:47
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

import  javax.servlet.jsp.JspException;

import  org.opencms.jsp.CmsJspActionElement;
import  org.opencms.file.CmsProperty;
import  org.opencms.main.CmsException;

/**
 *
 * @author Robert Diawara
 */
public class PropertyTag extends CommonTagBase{
    /** Holds the value of the property &quot;resource&quot;. */
    private String resource;
    /** Holds the value of the property &quot;default&quot;. */
    private String defaultPropertyValue;
    /** Holds the value of the property &quot;search&quot;. */
    private boolean search;
    /** Holds the value of the property &quot;name&quot;. */
    private String name;

    /**
     * Get the value of name
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     * @param name new value of name
     */
    public void setName(String name) {
        if((name == null) || (name.trim().length() == 0)){
            throw new RuntimeException("Error in class \"" + getClass().getName() + "\" ! Could not set the property name "
                    + "because the attribute is null or an empty string. Therefore the setter has been aborted. Please "
                    + "provide a valid value for!");
        }
        this.name = name;
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
     * Get the value of default
     * @return the value of default
     */
    public String getDefault() {
        return defaultPropertyValue;
    }

    /**
     * Set the value of default
     * @param defaultPropertyValue new value of default
     */
    public void setDefault(String defaultPropertyValue) {
        if((defaultPropertyValue == null) || (defaultPropertyValue.trim().length() == 0)){
            throw new RuntimeException("Error in class \"" + getClass().getName() + "\" ! Could not set the default "
                    + "because the attribute is null or an empty string. Therefore the setter has been aborted. Please "
                    + "ommit this attribute if you don't need it, or provide a valid value for!");
        }
        this.defaultPropertyValue = defaultPropertyValue;
    }

    /**
     * Get the value of search
     * @return the value of search
     */
    public boolean isSearch() {
        return search;
    }

    /**
     * Set the value of search
     * @param search new value of search
     */
    public void setSearch(boolean search) {
        this.search = search;
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
        String              resURI        = getResource();
        CmsJspActionElement actionEl      = new CmsJspActionElement(pageContext, getRequest(), getResponse());
        CmsProperty         propertyObj;
        String              result;
        
        try{
            // If no resource URI provided, set it to the URI of the currently
            // requested resource.
            if(resURI == null){
                resURI = actionEl.info("opencms.request.uri");
            }

            // Read the result from VFS
            propertyObj = actionEl.getCmsObject().readPropertyObject(resURI, getName(), isSearch());
            if(getDefault() != null){
                result = propertyObj != null ? propertyObj.getValue(getDefault()) : getDefault();
            }else{
                result = propertyObj != null ? propertyObj.getValue() : null;
            }
        
            // Write out the rendered value
            this.writeTagResult(result);
        }catch(CmsException cmsEx){
            throw new JspException(cmsEx);
        }
        
        // Return the tag result
        return CommonTagBase.SKIP_BODY;
    }

}
