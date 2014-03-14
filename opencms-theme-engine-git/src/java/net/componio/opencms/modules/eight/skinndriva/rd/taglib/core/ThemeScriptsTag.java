/*
 *  Document : ThemeScriptsTag.java
 *  Created on Do, Jan 19 2012, 12:39:26
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
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ScriptReference;

import  javax.servlet.jsp.JspException;

import  org.opencms.jsp.CmsJspActionElement;
import  org.opencms.file.CmsObject;
import  org.opencms.file.CmsRequestContext;

import  java.util.HashSet;

/**
 *
 * @author Robert Diawara
 */
public class ThemeScriptsTag extends ThemeEngineThemeTagBase{
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
    private HashSet<String> getGroupsSet(){
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
        ThemeConfig          themeCfg          = getTheme();
        StringBuffer         sb                = null;
        String               result;
        ScriptReference[]    references;
        CmsJspActionElement  actionEl;
        CmsObject            cmsObj;
        CmsRequestContext    requestCtx;
        int                  currentProjectType;
        int                  currentVisibility;
        String               currentGroup;
        int                  loopCount;
        HashSet<String>      groupsSet;
        
        // Proceed only, when a theme configuration found
        if(themeCfg != null){
            references = themeCfg.listScriptReferences();
            if((references != null) && (references.length > 0)){
                groupsSet = getGroupsSet();
                actionEl = new CmsJspActionElement(pageContext, getRequest(), getResponse());
                cmsObj = actionEl.getCmsObject();
                requestCtx = cmsObj.getRequestContext();
                
                // Check if the current project type is offline or online
                if(requestCtx.getCurrentProject().isOnlineProject()){
                    currentProjectType = ScriptReference.VISIBILITY_ONLINE;
                }else{
                    currentProjectType = ScriptReference.VISIBILITY_OFFLINE;
                }
                
                // Start rendering
                sb = new StringBuffer();
                for(loopCount = 0; loopCount < references.length; loopCount ++){
                    currentVisibility = references[loopCount].getScriptVisibility();
                    currentGroup = references[loopCount].getGroup();
                    currentGroup = currentGroup != null ? currentGroup.trim() : "";
                    
                    if((currentVisibility == ScriptReference.VISIBILITY_ALL) || (currentVisibility == currentProjectType)){
                        if((groupsSet == null) || (groupsSet.contains(currentGroup))){
                            if((currentVisibility == ScriptReference.VISIBILITY_ALL) || (currentVisibility == currentProjectType)){
                                sb.append("\n        <script type=\"text/JavaScript\" language=\"JavaScript\" src=\"");
                                sb.append(actionEl.link(references[loopCount].getFullScriptURI()));
                                sb.append("\"></script>");
                            }
                        }
                    }
                    
                }
            }else{
                sb = new StringBuffer("\n        <!-- No scripts defined for the theme ! -->");
            }
        }else{
            sb = new StringBuffer("\n        <!-- Could not render scripts. No theme defined ! -->");
        }
        result = sb.toString();
            
        // Write out the result
        this.writeTagResult(result);
        
        // Return the tag result
        return ThemeEngineThemeTagBase.SKIP_BODY;
    }
    
}
