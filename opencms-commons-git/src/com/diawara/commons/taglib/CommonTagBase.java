/*
 *  Document : CommonTagBase.java
 *  Created on So, Jan 08 2012, 12:14:01
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
package com.diawara.commons.taglib;

import  javax.servlet.jsp.tagext.BodyTagSupport; 
import  javax.servlet.jsp.PageContext;
import  javax.servlet.jsp.JspException;

import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;

import  java.io.IOException;


/**
 *
 * @author Robert Diawara
 */
public class CommonTagBase extends BodyTagSupport{
    private   String              scope;
    private   String              var;
    

    public static final String SCOPE_PATTERN = "^(application)|(session)|(request)|(page)$";
    
    
    public CommonTagBase(){
        scope    = "page";
        var      = null;
    }
    

    /**
     * Get the value of scope converted to an integer
     * @return the value of scope
     */
    public int getScopeValue() {
        int result;
        
        if(scope.equals("appication")){
            result = PageContext.APPLICATION_SCOPE;
        }else if(scope.equals("session")){
            result = PageContext.SESSION_SCOPE;
        }else if(scope.equals("request")){
            result = PageContext.REQUEST_SCOPE;
        }else if(scope.equals("page")){
            result = PageContext.PAGE_SCOPE;
        }else{
            result = PageContext.PAGE_SCOPE;
        }
        return result;
    }

    /**
     * Get the value of scope
     * @return the value of scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * Set the value of scope
     * @param scope new value of scope
     */
    public void setScope(String scope) {
        // Abort with an exception, when the value for the scope is not valid.
        if((scope == null) || (!scope.matches(CommonTagBase.SCOPE_PATTERN))){
            throw new RuntimeException("Error in class \"" + getClass().getName() + "\"! Could not set the scope, because "
                    + "the value \"" + scope + "\" doesn't match the pattern + \"" + CommonTagBase.SCOPE_PATTERN +
                    "\". Please provide a valid scope or ommmit the attribute in your tag.");
        }
        this.scope = scope;
    }

    /**
     * Get the value of var
     * @return the value of var
     */
    public String getVar() {
        return var;
    }

    /**
     * Set the value of var
     * @param var new value of var
     */
    public void setVar(String var) {
        if((var == null) || (var.trim().length() == 0)){
            throw new RuntimeException("Error in class \"" + getClass().getName() + "\" ! Could not set the variable name "
                    + "because the attribute is null or an empty string. Therefore the setter has been aborted. Please "
                    + "ommit the variable name if you don't need it, or provide a valid one!");
        }
        this.var = var;
    }
    
    /**
     * 
     * @param p_value 
     */
    protected void setVariableValue(Object p_value){
        if(this.var != null){
            if(p_value != null){
                pageContext.setAttribute(getVar(), p_value, getScopeValue());
            }else{
                pageContext.removeAttribute(getVar(), getScopeValue());
            }
        }
    }
    
    /**
     * 
     */
    protected void clearVariableValue(){
        if(getVar() != null){
            pageContext.removeAttribute(var, getScopeValue());
        }
    }
    
    /**
     * 
     * @param p_tagResult
     * @throws JspException 
     */
    protected void writeTagResult(Object p_tagResult) throws JspException{
        if(getVar() != null){
            setVariableValue(p_tagResult);
        }else{
            try{
                pageContext.getOut().write(p_tagResult != null ? String.valueOf(p_tagResult) : "null");
            }catch(IOException ex){
                throw new JspException(ex);
            }
        }
    }

    /**
     * Gets the HTTP servlet request based on which the page containing the tag was invoked.
     * @return The HTTP servlet request.
     */
    protected HttpServletRequest getRequest(){
        return (HttpServletRequest)pageContext.getRequest();
    }

     /**
     * Gets the HTTP servlet response associated th the invokec page which contains the tag.
     * @return The HTTP servlet response
     */
    protected HttpServletResponse getResponse(){
        return (HttpServletResponse)pageContext.getResponse();
    }
}
