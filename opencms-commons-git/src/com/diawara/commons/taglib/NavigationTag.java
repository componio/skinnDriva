/*
 *  Document : NavigationTag
 *  Created on Mo, Jul 09 2012, 00:00
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

import  java.io.IOException;

import  java.util.List;
import  java.util.Set;
import  java.util.HashSet;

import  javax.servlet.jsp.JspException;
import  javax.servlet.jsp.tagext.BodyTagSupport;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;
import  javax.servlet.jsp.JspWriter;

import  org.opencms.jsp.CmsJspActionElement;
import  org.opencms.jsp.CmsJspNavElement;
import  org.opencms.jsp.CmsJspNavBuilder;

/**
 *
 * @author robert
 */
public class NavigationTag extends BodyTagSupport{
    /** Holds the value of the property startLevel. */
    private int    startLevel;
    /** Holds the value of the property styleBundle. */
    private String styleBundle;
    /** Holds the value of the property linkStyleBundle. */
    private String linkStyleBundle;
    /** Holds the navigation tree for the current folder. */
    private List<CmsJspNavElement>     navTree;
    /** Holds the action element to provide taglib functionality. */
    private CmsJspActionElement  actionEl;
    /** Holds a set with all leaf node in the navigation tree. */
    private Set<CmsJspNavElement> leafNodes;
    /** Holds a set with all nodes of the breadcrumb navigation. */
    private Set<CmsJspNavElement> navBreadCrumbSet;


    /**
     * Default constructor
     */
    public NavigationTag(){
        startLevel = 1;
        styleBundle = "menu";
        linkStyleBundle = "menu_link";
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
        if(startLevel < 1){
            throw new RuntimeException("Invalid value for the start level. Value should be a positive integer " +
                    "grater than 0. Value is " + String.valueOf(startLevel) + "!");
        }
        this.startLevel = startLevel;
    }

    /**
     * Get the value of styleBundle
     * @return the value of styleBundle
     */
    public String getStyleBundle() {
        return styleBundle;
    }

    /**
     * Set the value of styleBundle
     * @param styleBundle new value of styleBundle
     */
    public void setStyleBundle(String styleBundle) {
        if((styleBundle == null) || (styleBundle.trim().length() == 0)){
            throw new RuntimeException("The value for the attribute \"styleBundle\" is mandatory, but no value has been provided. " +
                    "Therefore the setter has been aborted.");
        }
        this.styleBundle = styleBundle;
    }

    /**
     * Get the value of linkStyleBundle
     * @return the value of linkStyleBundle
     */
    public String getLinkStyleBundle() {
        return linkStyleBundle;
    }

    /**
     * Set the value of linkStyleBundle
     * @param linkStyleBundle new value of linkStyleBundle
     */
    public void setLinkStyleBundle(String linkStyleBundle) {
        if((styleBundle == null) || (styleBundle.trim().length() == 0)){
            throw new RuntimeException("The value for the attribute \"linkStyleBundle\" is mandatory, but no value has been provided. " +
                    "Therefore the setter has been aborted.");
        }
        this.linkStyleBundle = linkStyleBundle;
    }

    /**
     * 
     */
    private void createNavBreaCrumbSet(){
        List<CmsJspNavElement> navBreadCrumb = actionEl.getNavigation().getNavigationBreadCrumb();

        navBreadCrumbSet = new HashSet<CmsJspNavElement>();
        navBreadCrumbSet.addAll(navBreadCrumb);
    }

    /**
     * 
     * @param p_element
     * @return
     */
    private boolean isInNavBreadCrumb(CmsJspNavElement p_element){
        return navBreadCrumbSet.contains(p_element);
    }

    /**
     * 
     * @param p_index
     * @return
     */
    private int createLevelLeafNodesSet(int p_index){
        CmsJspNavElement       currentEl        = null;
        CmsJspNavElement       currentSiteNavEl = null;
        int                    index            = p_index;
        int                    navIndex         = 0;
        int                    currentLevel;
        int                    level2Check;
        List<CmsJspNavElement> siteNav;

        // Get the current element, and its level and remember the level to check
        currentEl = navTree.get(index);
        currentLevel = currentEl.getNavTreeLevel();
        level2Check = currentLevel;

        // Get the site navigation for the folder
        siteNav = actionEl.getNavigation().getSiteNavigation(currentEl.getParentFolderName(), currentLevel + 1);

        do{
            // Check if the node is a leaf node
            if(((navIndex + 1) >= siteNav.size()) || (siteNav.get(navIndex + 1).getNavTreeLevel() <= level2Check)){
                leafNodes.add(currentEl);
            }

            // Increment indexes
            index ++;
            if(navIndex < siteNav.size()){
                do{
                    navIndex ++;
                }while((navIndex < siteNav.size()) && (siteNav.get(navIndex).getNavTreeLevel() > level2Check));
            }

            // Get the current element and its nav tree level
            if(index < navTree.size()){
                currentEl = navTree.get(index);
                currentLevel = currentEl.getNavTreeLevel();

                // If the next element is in the next level, then start
                // with checking the next level.
                if(currentLevel > level2Check){
                    index = createLevelLeafNodesSet(index);

                    if(index < navTree.size()){
                        // Get the current element
                        currentEl = navTree.get(index);

                        // Determine the tree level of the current element
                        currentLevel = currentEl.getNavTreeLevel();

                        // Synchronize with site navigation
                        do{
                            navIndex ++;
                            currentSiteNavEl = (navIndex < siteNav.size()) ? siteNav.get(navIndex) : null;
                        }while((navIndex < siteNav.size()) && (currentEl.getResourceName().equals(currentSiteNavEl.getResourceName())));
                        
                    }
                }
            }
        }while((index < navTree.size()) && (currentLevel >= level2Check));
        return index;
    }

    /**
     * 
     * @param p_node
     * @return
     */
    private boolean isLeafNode(CmsJspNavElement p_node){
        return leafNodes.contains(p_node);
    }

    /**
     *
     * @param p_actionEl
     * @param p_tree
     * @param p_index
     * @param p_code
     * @return
     * @throws IOException
     */
    private int renderNavTreeLevel(int p_index, StringBuffer p_code) throws IOException{
        CmsJspNavElement currentEl     = null;
        String           indent;
        StringBuffer     linkBuf;
        StringBuffer     indentBuffer  = null;
        String           currentLink;
        String           linkClass;
        String           itemClass;
        int              level2Render;
        int              currentLevel;
        int              loop;
        int              index         = p_index;

        // Get the current element
        currentEl = navTree.get(index);

        // Determine, which level to render
        currentLevel = currentEl.getNavTreeLevel();
        level2Render = currentLevel;

        // Create the string for indentation
        indentBuffer = new StringBuffer();
        for(loop = 1; loop < level2Render; loop ++){
            indentBuffer.append("    ");
        }
        indent = indentBuffer.toString();

        p_code.append(indent).append("<ul class=\"menu\">\n");
        do{
            linkBuf = new StringBuffer(currentEl.getParentFolderName());
            linkBuf.append(currentEl.getFileName());
            currentLink = actionEl.link(linkBuf.toString());

            if(isInNavBreadCrumb(currentEl)){
                linkClass = this.getLinkStyleBundle() + "_active";
                itemClass = this.getStyleBundle() + "_active";
            }else{
                linkClass = this.getLinkStyleBundle() + "_idle";
                itemClass = this.getStyleBundle() + "_idle";
            }
            /**
            if((navTree.size() - index <= 1) || (navTree.get(index + 1).getNavTreeLevel() <= currentLevel)){
                itemClass += "_nobullet";
            }
            */
            if(isLeafNode(currentEl)){
                itemClass += "_nobullet";
            }

            p_code.append(indent).append("    <li class=\"").append(itemClass).append("\">\n");
            p_code.append(indent).append("        <a class=\"").append(linkClass).append("\" href=\"").append(currentLink).append("\"/>\n");
            p_code.append(indent).append("            ").append(currentEl.getNavText()).append("\n");
            p_code.append(indent).append("        </a>\n");
            p_code.append(indent).append("    </li>\n");

            index ++;

            if(index < navTree.size()){
                // Get the current element
                currentEl = navTree.get(index);

                // Determine the tree level of the current element
                currentLevel = currentEl.getNavTreeLevel();

                // If the next element is in the next level, then start
                // with rendering the next level.
                if(currentLevel > level2Render){
                    index = renderNavTreeLevel(index, p_code);

                    if(index < navTree.size()){
                        // Get the current element
                        currentEl = navTree.get(index);

                        // Determine the tree level of the current element
                        currentLevel = currentEl.getNavTreeLevel();
                    }
                }
            }
        }while((index < navTree.size()) && (currentLevel >= level2Render));
        p_code.append(indent).append("</ul>\n");

        return index;
    }

    /**
     * 
     * @return
     * @throws JspException
     */
    private String renderNavTree() throws JspException{
        CmsJspNavBuilder           navBuilder;
        List<CmsJspNavElement>     navBreadcrumb;
        HttpServletRequest         request;
        HttpServletResponse        response;
        int                        navStartLevel        = getStartLevel() > 1 ? getStartLevel() - 1 : 0;
        int                        endLevel;
        StringBuffer               codeBuffer;
        String                     result               = "";

        synchronized(this){
            request = (HttpServletRequest)pageContext.getRequest();
            response  = (HttpServletResponse)pageContext.getResponse();
            actionEl = new CmsJspActionElement(pageContext, request, response);

            try{
                navBuilder = actionEl.getNavigation();
                navBreadcrumb = navBuilder.getNavigationBreadCrumb();
                endLevel = navBreadcrumb.size();
                //navTree = navBuilder.getNavigationTreeForFolder(1, endLevel);
                navTree = navBuilder.getNavigationTreeForFolder(navStartLevel, endLevel);
                leafNodes = new HashSet<CmsJspNavElement>();

                this.createLevelLeafNodesSet(0);
                this.createNavBreaCrumbSet();
                if(navTree.size() > 0){
                    codeBuffer = new StringBuffer();
                    renderNavTreeLevel(0, codeBuffer);
                    result = codeBuffer.toString();
                }
            }catch(IOException ioEx){
                throw new JspException(ioEx);
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
        JspWriter out = pageContext.getOut();

        try{
            out.write(this.renderNavTree());
        }catch(IOException ioEx){
            throw new JspException(ioEx);
        }
        return super.doEndTag();
    }
}
