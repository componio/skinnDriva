/*
 *  Document : ReplaceTag.java
 *  Created on Sun, Feb 21 2016, 12:27:23 PM
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

import  javax.servlet.jsp.JspException;
import  javax.servlet.jsp.tagext.BodyTagSupport; 

import  java.util.regex.Pattern;
import  java.util.regex.Matcher;

/**
 * Allows to do replacements based on regular expressions.
 * @author Robert Diawara
 */
public class ReplaceTag extends CommonTagBase{
    /** Holds the value of the property &quot;regex&quot; */
    private String regex;
    /** Holds the value of the property &quot;searchText&quot; */
    private String searchText;
    /** Holds the value of the property &quot;replacementString&quot; */
    private String replacementString;
    
    /**
     * Default Constructor.
     */
    public ReplaceTag(){
        this.regex = "^(.*)$";
        this.searchText = null;
        this.replacementString = null;
    }

    /**
     * Get the value of regex. This is the regular expression, which texts are to be checked against.
     * @return the value of regex
     */
    public String getRegex() {
        return ((regex != null) && (regex.trim().length() > 0)) ? regex : "^(.*)$";
    }

    /**
     * Set the value of regex. This is the regular expression, which texts are to be checked against.
     * @param regex new value of regex
     */
    public void setRegex(String regex) {
        // Abort with an exception, when the regex is null or empty.
        if((regex == null) || (regex.trim().length() == 0)){
            throw new RuntimeException("Null or empty values are not allowed for regex !");
        }
        this.regex = regex;
    }


    /**
     * Get the value of searchText. This is the string which will be tested against the regular expression.
     * If it matches, a replacement according to the replacement string here in this class, will be done.
     * @return the value of searchText
     */
    public String getSearchText() {
        return searchText;
    }

    /**
     * Set the value of searchText. This is the string which will be tested against the regular expression.
     * If it matches, a replacement according to the replacement string here in this class, will be done.
     * @param searchText new value of searchText
     */
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }


    /**
     * Get the value of replacementString. Matches of the regular expressions will be 
     * replaced by this replacement string. Back references $1 to $9 are allowed.
     * @return the value of replacementString
     */
    public String getReplacementString() {
        return ((replacementString != null) && (replacementString.trim().length() != 0)) ? replacementString : getSearchText();
    }

    /**
     * Set the value of replacementString. Matches of the regular expressions will be 
     * replaced by this replacement string. Back references $1 to $9 are allowed.
     * @param replacementString new value of replacementString
     */
    public void setReplacementString(String replacementString) {
        // Abort with an exception, when the replacement string is null or empty.
        if((replacementString == null) || (replacementString.trim().length() == 0)){
            throw new RuntimeException("Null or empty replacement strings not allowed.");
        }
        this.replacementString = replacementString;
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
        Pattern pattern;
        Matcher matcher;
        String  result  = "";
        
        pattern = Pattern.compile(this.getRegex());
        matcher = pattern.matcher(this.getSearchText());
        if(matcher.matches()){
            result = matcher.replaceAll(this.getReplacementString());
        }
        this.writeTagResult(result);
        return BodyTagSupport.SKIP_BODY;
    }

}