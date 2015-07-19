/*
 *  Document : ThemeStylesTag.java
 *  Created on Do, Jan 19 2012, 12:39:04
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

import  net.componio.opencms.modules.eight.skinndriva.rd.taglib.ThemeEngineThemeTagBase;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeConfig;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.StyleReference;

import  javax.servlet.jsp.JspException;

import  org.opencms.jsp.CmsJspActionElement;

import  java.util.HashSet;
import  java.util.List;
import  java.util.Iterator;

/**
 *
 * @author Robert Diawara
 */
public class ThemeStylesTag extends ThemeEngineThemeTagBase{
    /** The pattern, which the groups property has to be validated against. */
    public final static String GROUPS_PATTERN = "^([A-Za-z0-9\\_\\-\\.]+)(\\;([A-Za-z0-9\\_\\-\\.]+))*$";
    
    /** Holds the value of the property &quot;groups&quot;*/
    private String groups;

    /**
     * Get the value of groups
     * @return the value of groups
     */
    public String getGroups() {
        return groups;
    }

    /**
     * Set the value of groups
     * @param groups new value of groups
     */
    public void setGroups(String groups) {
        if((groups != null) && (groups.trim().length() > 0)){
            if(!groups.matches(ThemeScriptsTag.GROUPS_PATTERN)){
                throw new RuntimeException("Error in class \"" + getClass().getName() + "\" ! Could not set the groups "
                        + "attribute because it doesnt match the pattern. Please a value for the attribute, which matches the pattern !");
            }
            this.groups = groups;
        }else{
            this.groups = null;
        }
    }

    /**
     * 
     * @return 
     */
    protected HashSet<String> getGroupsSet(){
        HashSet<String> result = null;
        String[]        groupsList    = getGroups() != null ? getGroups().split(";") : null;
        int             loopCount;
        
        if((groupsList != null) && (groupsList.length > 0)){
            result = new HashSet<String>();
            for(loopCount = 0; loopCount < groupsList.length; loopCount ++){
                result.add(groupsList[loopCount]);
            }
        }
        
        return result;
    }

    /**
     * Checks if restrictions are present in form of regular expressions, which limit the
     * usage of a CSS style to a certain set of browsers. If regular expressions are present,
     * this method checks, if the user agent of the request matches one of them. If the user agent
     * matches one of them, this means that the CSS style represented by the <code>p_styleRef</code>
     * parameter has to be used for the current request. So the method returned <code>true</code>. Otherwise
     * The method returns <code>false</code>
     * @param p_styleRef Represents the CSS style, which the validity has to be checked for.
     * @return <code>true</code>, if all above mentioned conditions apply and the CSS style has to be used. 
     * Otherwise <code>false</code>
     */
    protected boolean isUserAgentValid(StyleReference p_styleRef){
        boolean          result    = false;
        String           userAgent;
        List<String>     regexList = p_styleRef.getUserAgents();
        Iterator<String> regexIt;
        
        // Get the user agent
        userAgent = getRequest().getHeader("User-Agent");
        if(userAgent == null){
            userAgent = "";
        }
        
        if((regexList != null) && (regexList.size() > 0)){
            for(regexIt = regexList.iterator(); (regexIt.hasNext()) && (!result);){
                result = userAgent.matches(regexIt.next());
            }
        }else{
            // No regular expressions present means no restrictions present. So we return
            // true in this case, because then the user agent is valid by default.
            result = true;
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
     * @return indication of whether to continue evaluating the JSP page.
     * @throws JspException if an error occurred while processing this tag
     */
    @Override
    public int doEndTag() throws JspException {
        ThemeConfig          themeCfg   = getTheme();
        StringBuffer         sb;
        String               result;
        StyleReference[]     stylesList;
        StyleReference       style;
        String               media;
        int                  loopCount;
        CmsJspActionElement  actionEl;
        HashSet<String>      groupsSet;
        String               currentGroup;
        
        if(themeCfg != null){
            stylesList = themeCfg.listStyles();
            if((stylesList != null) && (stylesList.length > 0)){
                actionEl = new CmsJspActionElement(pageContext, getRequest(), getResponse());
                groupsSet = getGroupsSet();
                sb = new StringBuffer();
                
                for(loopCount = 0; loopCount < stylesList.length; loopCount ++){
                    style = stylesList[loopCount];
                    
                    currentGroup = style.getGroup();
                    currentGroup = currentGroup != null ? currentGroup.trim() : "";
                    
                    if((groupsSet == null) || (groupsSet.contains(currentGroup))){
                        if(isUserAgentValid(style)){
                            sb.append("\n        <link rel=\"stylesheet\" type=\"text/css\" ");
                            
                            media = style.getMedia();
                            if((media != null) && (media.trim().length() > 0)){
                                sb.append("media=\"");
                                sb.append(media);
                                sb.append("\" ");
                            }
                            sb.append("href=\"");
                            sb.append(actionEl.link(style.getURI()));
                            sb.append("\" />");
                        }
                    }
                }
            }else{
                sb = new StringBuffer("\n        <!-- No styles defined for the theme ! -->");
            }
        }else{
            sb = new StringBuffer("\n        <!-- Could not render styles. No theme defined ! -->");
        }
        result = sb.toString();
            
        // Write out the result
        this.writeTagResult(result);
        
        // Return the tag result
        return ThemeEngineThemeTagBase.SKIP_BODY;
    }
}
