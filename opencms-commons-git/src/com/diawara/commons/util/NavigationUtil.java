/*
 *  Document : NavigationUtil.java
 *  Created on Fr, Jan 22 2016, 18:01:19
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
package com.diawara.commons.util;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.opencms.file.CmsObject;
import org.opencms.file.CmsProperty;
import org.opencms.jsp.CmsJspActionElement;
import org.opencms.jsp.CmsJspNavBuilder;
import org.opencms.jsp.CmsJspNavElement;
import org.opencms.main.CmsException;


public class NavigationUtil {
    /** Holds the context of the current JSP, which the code is running in. */
    private PageContext pageContext;
    /** Holds the current servlet request. */
    private HttpServletRequest request;
    /** Holds the current servlet response. */
    private HttpServletResponse response;    
    /** Holds the name of the general CSS style for menus. */
    private String navStyle;
    /** Holds the CSS stale name for the different tree levels. */
    private String levelStylePattern;
    /** Holds the path (optional) for which the naviagtion has to be built. */
    private String folder;
    
    
    public static final String FONT_AWESOME          = "Font Awesome";
    public static final String FOUNDATION_ICON_FONTS = "Foundation Icon Fonts";
    

    private CmsJspActionElement        actionEl;
    private Iterator<CmsJspNavElement> siteNavIt;
    private CmsJspNavElement           currentEl;
    private CmsJspNavElement           nextEl;
    private String                     navBreadCrumbPath;
    private int                        itmCount;
    private String                     defaultIconSet;
    private String                     defaultIcon;
    private String                     mobileCollapsedIconStyle;
    private String                     mobileExpandedIconStyle;





    /**
     * The default constructor which is private here to avoid an instantiation
     * while the mandatory fields are missing.
     */
    private NavigationUtil(){}
    
    /**
     * 
     * @param p_pageContext the context of the current JSP, which the code is running in.
     * @param p_request the current servlet request.
     * @param p_response the current servlet response.
     * @param p_navStyle the name of the general CSS style for menus. Will be added to the value
     * of the class attribute of all unordered lists representing the menu items.
     * @param p_levelStylePattern the pattern for CSS style name for the different tree levels, whichill be added to the value
     * of the class attribute of the unordered list representing the menu items of a tree level.
     */
    public NavigationUtil(PageContext p_pageContext, HttpServletRequest p_request, HttpServletResponse p_response, 
            String p_navStyle, String p_levelStylePattern){
        
        // Abort with an exception when the mandatory page context is missing.
        if(p_pageContext == null){
            throw new RuntimeException("Can't create NavigationUtil. Missing mandatory page context !");
        }
        // Abort with an exception, when the mandatory servlet request is missing.
        if(p_request == null){
            throw new RuntimeException("Can't create NavigationUtil. Missing mandatory servlet request !");
        }
        // Abort with an exception, when the mandatory servlet response is missing.
        if(p_response == null){
            throw new RuntimeException("Can't create NavigationUtil. Missing mandatory servlet response !");
        }
        // Abort with an exception, when the mandatory navigation style is missing.
        if((p_navStyle == null) || (p_navStyle.trim().length() == 0)){
            throw new RuntimeException("Can't create NavigationUtil. Missing mandatory navigation style !");
        }
        // Abort with an exception, when the mandatory level style pattern is missing.
        if((p_levelStylePattern == null) || (p_levelStylePattern.trim().length() == 0)){
            throw new RuntimeException("Can't create NavigationUtil. Missing mandatory level style pattern !");
        }
        
        this.pageContext = p_pageContext;
        this.request = p_request;
        this.response = p_response;
        this.navStyle = p_navStyle;
        this.levelStylePattern = p_levelStylePattern;
        this.defaultIconSet = NavigationUtil.FONT_AWESOME;
        this.defaultIcon = "folder-o";
        this.mobileCollapsedIconStyle = "fa fa-hand-o-left";
        this.mobileExpandedIconStyle = "fa fa-hand-o-down";
    }
    
    /**
     * 
     * @param p_pageContext the context of the current JSP, which the code is running in.
     * @param p_request the current servlet request.
     * @param p_response the current servlet response.
     * @param p_navStyle the name of the general CSS style for menus. Will be added to the value
     * of the class attribute of all unordered lists representing the menu items.
     * @param p_levelStylePattern the pattern for CSS style name for the different tree levels, whichill be added to the value
     * of the class attribute of the unordered list representing the menu items of a tree level.
     * @param p_path The path for which the navigation has to be created. If not provided, the path of the current VFS resource
     * will be used.
     */
    public NavigationUtil(PageContext p_pageContext, HttpServletRequest p_request, HttpServletResponse p_response, 
            String p_navStyle, String p_levelStylePattern, String p_path){
        
        this(p_pageContext, p_request, p_response, p_navStyle, p_levelStylePattern);
        this.folder = ((p_path != null) && (p_path.trim().length() > 0)) ? p_path : null;
    }
    

    /**
     * Get the value of pageContext which is the context of the current JSP, which the code is running in.
     * @return the value of pageContext
     */
    public PageContext getPageContext() {
        return pageContext;
    }

    /**
     * Get the current servlet request
     * @return the value of request
     */
    protected HttpServletRequest getRequest() {
        return request;
    }
    
    /**
     * Get the current servlet response
     * @return the value of response
     */
    protected HttpServletResponse getResponse() {
        return response;
    }
    
    /**
     * Get the value of navStyle which is part of the value of the class attribute of all unordered 
     * lists representing the menu items.
     * @return the value of navStyle
     */
    public String getNavStyle() {
        return navStyle;
    }

    /**
     * Get the value of levelStylePattern which is the pattern of the class name responsible for formatting
     * a level of items in the menu tree.
     * @return the value of levelStylePattern
     */
    public String getLevelStylePattern() {
        return levelStylePattern;
    }
    
    /**
     * Get the value of defaultIconType. This determines, which set of we fonts to use for
     * icons. Currently &quot;Font Awesome&quot; and "Foundation Icon Fonts&quot; are
     * supported.
     * @return the value of defaultIconSet
     */
    public String getDefaultIconSet() {
        return defaultIconSet;
    }

    /**
     * Set the value of defaultIconSet. This determines, which set of we fonts to use for
     * icons. Currently &quot;Font Awesome&quot; and "Foundation Icon Fonts&quot; are
     * supported.
     * @param defaultIconSet new value of defaultIconSet
     */
    public void setDefaultIconType(String defaultIconSet) {
        if((!NavigationUtil.FONT_AWESOME.equals(defaultIconSet)) && (!NavigationUtil.FOUNDATION_ICON_FONTS.equals(defaultIconSet))){
            throw new RuntimeException("Invalid value for default icon set. Only \"" + NavigationUtil.FONT_AWESOME +
                    "\" or \"" + NavigationUtil.FOUNDATION_ICON_FONTS + "\" are allowed as values !");
        }
        this.defaultIconSet = defaultIconSet;
    }
    
    /**
     * Get the value of defaultIcon This is the Icon, which will be set by
     * default for a menu item, when no other icons is explicitely defined.
     * @return the value of defaultIcon
     */
    public String getDefaultIcon() {
        return defaultIcon;
    }

    /**
     * Set the value of defaultIcon. This is the Icon, which will be set by
     * default for a menu item, when no other icons is explicitely defined.
     * @param defaultIcon new value of defaultIcon
     */
    public void setDefaultIcon(String defaultIcon) {
        this.defaultIcon = defaultIcon;
    }
    
    /**
     * Get the value of mobileCollapsedIcon
     * @return the value of mobileCollapsedIcon
     */
    public String getMobileCollapsedIconStyle() {
        return mobileCollapsedIconStyle;
    }

    /**
     * Set the value of mobileCollapsedIcon
     * @param mobileCollapsedIconStyle new value of mobileCollapsedIcon
     */
    public void setMobileCollapsedIconStyle(String mobileCollapsedIconStyle) {
        this.mobileCollapsedIconStyle = mobileCollapsedIconStyle;
    }
    
    /**
     * Get the value of mobileExpandedIcon
     * @return the value of mobileExpandedIcon
     */
    public String getMobileExpandedIconStyle() {
        return mobileExpandedIconStyle;
    }

    /**
     * Set the value of mobileExpandedIcon
     * @param mobileExpandedIconStyle new value of mobileExpandedIcon
     */
    public void setMobileExpandedIconStyle(String mobileExpandedIconStyle) {
        this.mobileExpandedIconStyle = mobileExpandedIconStyle;
    }

    /**
     * Get the path for which the navigation has to be built. If not present, the path
     * of the current VFS resource will be taken.
     * @return 
     */
    public String getFolder() {
        return folder;
    }
    
    
    /**
     * 
     * @param p_level
     * @param p_indent
     * @param p_navElement
     * @return 
     */
    private String computeIconCode(int p_level, StringBuilder p_indent, CmsJspNavElement p_navElement)throws CmsException{
        CmsObject     cmsObj        = actionEl.getCmsObject();
        CmsProperty   iconSetPrp; 
        String        iconSet;
        CmsProperty   iconPrp; 
        String        icon; 
        StringBuilder resultBuilder;
        StringBuilder style         = null;
        
        // Lookup the icon set
        iconSetPrp = cmsObj.readPropertyObject(p_navElement.getResource(), "NavIconType", false);
        iconSet = ((iconSetPrp != null) && (iconSetPrp.getValue() != null)) ? iconSetPrp.getValue() : null;

        // Look up the icon
        iconPrp = cmsObj.readPropertyObject(p_navElement.getResource(), "NavIcon", false);
        icon = ((iconPrp != null) && (iconPrp.getValue() != null)) ? iconPrp.getValue() : null;
        
        // Missing navigation icons will only re replaced by defaults for 
        // first level menu items.
        if(p_level == 0){
            // Get the default icon set, if its not provided with the menu item.
            if(iconSet == null){
                iconSet = getDefaultIconSet();
            }
            
            // Get the icon, if its not provided with the menu item.
            if(icon == null){
                icon = getDefaultIcon();
            }
        }
        
        // If icon settings found, process the code for the icon.
        if((iconSet != null) && (icon != null)){
        // Compute the CSS style for the icon
            style = new StringBuilder();
            switch (iconSet) {
                case "Font Awesome":
                    style.append("fa fa-");
                    break;
                case "Foundation Icon Fonts":
                    style.append("fi-");
                    break;
            }
            style.append(icon);
        }
        
        // Compute the code
        resultBuilder = new StringBuilder();
        resultBuilder.append(p_indent).append("            <div class=\"menu_icon\">\n");
        if(style != null){
            resultBuilder.append(p_indent).append("                <i class=\"").append(style).append("\">&nbsp;</i>\n");
        }else{
            resultBuilder.append(p_indent).append("                &nbsp;\n");
        }
        resultBuilder.append(p_indent).append("            </div>\n");
        
        return resultBuilder.toString();
    }
    
    /**
     * 
     * @param p_level
     * @param p_indent
     * @param p_navElement
     * @return 
     */
    private String computeMobileIconCode(int p_level, StringBuilder p_indent, CmsJspNavElement p_navElement)throws CmsException{
        CmsObject     cmsObj        = actionEl.getCmsObject();
        CmsProperty   iconSetPrp; 
        String        iconSet;
        CmsProperty   iconPrp; 
        String        icon; 
        StringBuilder resultBuilder;
        StringBuilder style         = null;
        
        // Lookup the icon set
        iconSetPrp = cmsObj.readPropertyObject(p_navElement.getResource(), "NavIconType", false);
        iconSet = ((iconSetPrp != null) && (iconSetPrp.getValue() != null)) ? iconSetPrp.getValue() : null;

        // Look up the icon
        iconPrp = cmsObj.readPropertyObject(p_navElement.getResource(), "NavIcon", false);
        icon = ((iconPrp != null) && (iconPrp.getValue() != null)) ? iconPrp.getValue() : null;
        
        // Missing navigation icons will only re replaced by defaults for 
        // first level menu items.
        if(p_level == 0){
            // Get the default icon set, if its not provided with the menu item.
            if(iconSet == null){
                iconSet = getDefaultIconSet();
            }
            
            // Get the icon, if its not provided with the menu item.
            if(icon == null){
                icon = getDefaultIcon();
            }
        }
        
        // If icon settings found, process the code for the icon.
        if((iconSet != null) && (icon != null)){
        // Compute the CSS style for the icon
            style = new StringBuilder();
            switch (iconSet) {
                case "Font Awesome":
                    style.append("fa fa-");
                    break;
                case "Foundation Icon Fonts":
                    style.append("fi-");
                    break;
            }
            style.append(icon);
        }
        
        // Compute the code
        resultBuilder = new StringBuilder();
        resultBuilder.append(p_indent).append("        <div class=\"mobile_menu_icon\">\n");
        if(style != null){
            resultBuilder.append(p_indent).append("            <i class=\"").append(style).append("\">&nbsp;</i>\n");
        }else{
            resultBuilder.append(p_indent).append("            &nbsp;\n");
        }
        resultBuilder.append(p_indent).append("        </div>\n");
        
        return resultBuilder.toString();
    }
    
    /**
     * 
     * @param p_Level
     * @param p_navIt
     * @param p_currentEl
     * @return 
     */
    private String getHtmlCode4Level(int p_level)throws CmsException{
        int                        level           = p_level;
        StringBuilder              resultBuilder   = new StringBuilder();
        StringBuilder              indent          = new StringBuilder();
        int                        i;
        String                     levelStyle      = this.getLevelStylePattern().replace("%(number)", String.valueOf(level + 1));
        String                     nextLevelStyle  = this.getLevelStylePattern().replace("%(number)", String.valueOf(level + 2));
        String                     currentLink;
        boolean                    endOfLoop       = false;
        String                     breadCrumbStyle;
        String                     alignment;
        
        // Compute string for indentation
        for(i = 0; i < level; i ++){
            indent.append("                ");
        }
        
        resultBuilder.append(indent).append("<ul class=\"simple_drop_down_menu ").append(levelStyle).append("\">\n");
        while((currentEl != null) && (!endOfLoop)){
            // Increase the first level item count, if we're at the first level.
            if(p_level == 0){
                itmCount ++;
            }
            
            nextEl = siteNavIt.hasNext() ? siteNavIt.next() : null;
            
            currentLink = new StringBuilder().append(currentEl.getParentFolderName()).append(currentEl.getFileName()).toString();
            currentLink = actionEl.link(currentLink);
            
            breadCrumbStyle = (navBreadCrumbPath.startsWith(currentEl.getResource().getRootPath())) ? "selected" : "idle";
            
            resultBuilder.append(indent).append("    <li class=\"").append(levelStyle).append("\">\n");
            resultBuilder.append(indent).append("        <div class=\"menu_item ").append(breadCrumbStyle).append("\">\n");
            resultBuilder.append(this.computeIconCode(level, indent, currentEl));
            resultBuilder.append(indent).append("            <a href=\"").append(currentLink).append("\">\n");
            resultBuilder.append(indent).append("                ").append(currentEl.getNavText()).append("\n");
            resultBuilder.append(indent).append("            </a>\n");
            if((nextEl != null) && (nextEl.getNavTreeLevel() > currentEl.getNavTreeLevel())){
                currentEl = nextEl;
                alignment = itmCount <= 4 ? "align_right" : "align_left";
                
                resultBuilder.append(indent).append("            <div class=\"submenu ").append(alignment).append(" ").append(nextLevelStyle).append("\">\n");
                resultBuilder.append(getHtmlCode4Level(p_level + 1));
                resultBuilder.append(indent).append("            </div>\n");
            }
            resultBuilder.append(indent).append("        </div>\n");
            resultBuilder.append(indent).append("    </li>\n");
            
            endOfLoop = ((nextEl == null) || (nextEl.getNavTreeLevel() < currentEl.getNavTreeLevel()));
            currentEl = nextEl;
        }
        resultBuilder.append(indent).append("</ul>\n");
        
        return resultBuilder.toString();
    }
    
    /**
     * 
     * @param p_Level
     * @param p_navIt
     * @param p_currentEl
     * @return 
     */
    private String getMobileHtmlCode4Level(int p_level, String p_parentItemId)throws CmsException{
        int                        level           = p_level;
        StringBuilder              resultBuilder   = new StringBuilder();
        StringBuilder              indent          = new StringBuilder();
        int                        i;
        String                     levelStyle      = this.getLevelStylePattern().replace("%(number)", String.valueOf(level + 1));
        String                     currentLink;
        boolean                    endOfLoop       = false;
        String                     breadCrumbStyle;
        StringBuilder              itemId;
        int                        childItmCount   = 0;
        DecimalFormat              counterFormat   = new DecimalFormat("00");
        
        // Compute string for indentation
        for(i = 0; i < level; i ++){
            indent.append("        ");
        }
        
        resultBuilder.append(indent).append("<div");
        if((p_parentItemId != null) && (p_parentItemId.trim().length() > 0)){
            resultBuilder.append(" id=\"").append(p_parentItemId).append("_CHILDREN\" style=\"visibility:hidden;display:none\"");
        }
        resultBuilder.append(" class=\"").append(levelStyle).append("\">\n");
        
        while((currentEl != null) && (!endOfLoop)){
            // Build the item id
            itemId = new StringBuilder();
            if((p_parentItemId != null) && (p_parentItemId.trim().length() > 0)){
                itemId.append(p_parentItemId).append("-").append(counterFormat.format(childItmCount));
            }else{
                itemId.append("MOBILE_ITM-").append(counterFormat.format(itmCount));
            }
            
            // Increase child item counter
            childItmCount ++;
            
            // Increase the first level item count, if we're at the first level.
            if(p_level == 0){
                itmCount ++;
            }
            
            nextEl = siteNavIt.hasNext() ? siteNavIt.next() : null;
            
            currentLink = new StringBuilder().append(currentEl.getParentFolderName()).append(currentEl.getFileName()).toString();
            currentLink = actionEl.link(currentLink);
            
            breadCrumbStyle = (navBreadCrumbPath.startsWith(currentEl.getResource().getRootPath())) ? "selected" : "idle";
            
            resultBuilder.append(indent).append("    <div id=\"").append(itemId.toString()).append("\" class=\"mobile_menu_item ").append(breadCrumbStyle).append("\">\n");
            resultBuilder.append(this.computeMobileIconCode(level, indent, currentEl));
            resultBuilder.append(indent).append("        <a href=\"").append(currentLink).append("\">\n");
            resultBuilder.append(indent).append("            ").append(currentEl.getNavText()).append("\n");
            resultBuilder.append(indent).append("        </a>\n");
            if((nextEl != null) && (nextEl.getNavTreeLevel() > currentEl.getNavTreeLevel())){
                resultBuilder.append(indent).append("        <div id=\"").append(itemId).append("_EXPAND_BTN\" class=\"toggle-icon-collapsed\" onclick=\"expandChildren(event, '")
                        .append(itemId).append("')\" style=\"visibility:visible;display:block\">\n");
                resultBuilder.append(indent).append("            <i class=\"").append(getMobileCollapsedIconStyle()).append("\">&nbsp;</i>\n");
                resultBuilder.append(indent).append("        </div>\n");
                resultBuilder.append(indent).append("        <div id=\"").append(itemId).append("_COLLAPSE_BTN\" class=\"toggle-icon-expanded\" onclick=\"collapseChildren(event, '")
                        .append(itemId).append("')\" style=\"visibility:hidden;display:none\">\n");
                resultBuilder.append(indent).append("            <i class=\"").append(getMobileExpandedIconStyle()).append("\">&nbsp;</i>\n");
                resultBuilder.append(indent).append("        </div>\n");
                
                currentEl = nextEl;
                resultBuilder.append(getMobileHtmlCode4Level(p_level + 1, itemId.toString()));
            }
            resultBuilder.append(indent).append("    </div>\n");
            
            endOfLoop = ((nextEl == null) || (nextEl.getNavTreeLevel() < currentEl.getNavTreeLevel()));
            currentEl = nextEl;
        }
        resultBuilder.append(indent).append("</div>\n");
        
        return resultBuilder.toString();
    }
    
    /**
     * @param p_startLevel
     * @param p_endLevel
     * @return 
     * @throws javax.servlet.jsp.JspException 
     */
    public String getHtmlCode(int p_startLevel, int p_endLevel) throws JspException{
        CmsJspNavBuilder           navBuilder;
        int                        navStartLevel;
        int                        navEndLevel;
        String                     rootFolder;
        List<CmsJspNavElement>     folderSiteNav;
        List<CmsJspNavElement>     navBreadCrumb;
        StringBuilder              resultBuilder = null;

        
        synchronized(this){
            actionEl = new CmsJspActionElement(pageContext, request, response);
            navBuilder    = actionEl.getNavigation();

            // Process the start level and end level.
            navStartLevel = p_startLevel;
            navEndLevel = p_endLevel;
            if(navStartLevel < 0){
                throw new RuntimeException("Invalid value of the start level(" + 
                        String.valueOf(navStartLevel) + "). Value should be greater than or equal to 0 !");
            }
            if(navEndLevel < navStartLevel){
                throw new RuntimeException("Cant proceed, when the end level(" + 
                        String.valueOf(navEndLevel) + ") is smaller the start level(" + 
                        String.valueOf(navStartLevel) + ") !");
            }

            // Process the site navigation for the start folder
            if(folder != null){
                navBreadCrumb = navBuilder.getNavigationBreadCrumb(folder, navStartLevel, navEndLevel, true);
            }else{
                navBreadCrumb = navBuilder.getNavigationBreadCrumb();
            }
            navBreadCrumbPath = navBreadCrumb.get(navBreadCrumb.size() - 1).getResource().getRootPath();
            rootFolder = navBreadCrumb.get(navStartLevel).getResourceName();
            folderSiteNav = navBuilder.getSiteNavigation(rootFolder, navEndLevel);

            // Generate Code
            siteNavIt = folderSiteNav.iterator();
            if(siteNavIt.hasNext()){
                itmCount = 0;
                currentEl = siteNavIt.next();
                resultBuilder = new StringBuilder();
                try {
                    resultBuilder.append("\n").append(getHtmlCode4Level(0));
                } catch (CmsException cmsEx) {
                    throw new JspException(cmsEx);
                }
            }
        }
        return resultBuilder != null ? resultBuilder.toString() : "";
    }
    
    
    /**
     * @param p_startLevel
     * @return 
     * @throws javax.servlet.jsp.JspException 
     */
    public String getMobileHtmlCode(int p_startLevel) throws JspException{
        CmsJspNavBuilder           navBuilder;
        int                        navStartLevel;
        int                        navEndLevel;
        String                     rootFolder;
        List<CmsJspNavElement>     folderSiteNav;
        List<CmsJspNavElement>     navBreadCrumb;
        String                     result        = "";
        StringBuilder              resultBuilder = null;

        
        synchronized(this){
            actionEl = new CmsJspActionElement(pageContext, request, response);
            navBuilder    = actionEl.getNavigation();

            // Process the start level and end level.
            navStartLevel = p_startLevel;
            navEndLevel = p_startLevel + 1;
            if(navStartLevel < 0){
                throw new RuntimeException("Invalid value of the start level(" + 
                        String.valueOf(navStartLevel) + "). Value should be greater than or equal to 0 !");
            }
            if(navEndLevel < navStartLevel){
                throw new RuntimeException("Cant proceed, when the end level(" + 
                        String.valueOf(navEndLevel) + ") is smaller the start level(" + 
                        String.valueOf(navStartLevel) + ") !");
            }

            // Process the site navigation for the start folder
            if(folder != null){
                navBreadCrumb = navBuilder.getNavigationBreadCrumb(folder, navStartLevel, navEndLevel, true);
            }else{
                navBreadCrumb = navBuilder.getNavigationBreadCrumb();
            }
            navBreadCrumbPath = navBreadCrumb.get(navBreadCrumb.size() - 1).getResource().getRootPath();
            
            // Here, we determine which is the root folder for the naviagtion.
            rootFolder = navBreadCrumb.get(navStartLevel).getResourceName();
            
            // Here, we get the Navigation for the folder.
            folderSiteNav = navBuilder.getSiteNavigation(rootFolder, navEndLevel);

            // Generate Code
            siteNavIt = folderSiteNav.iterator();
            if(siteNavIt.hasNext()){
                itmCount = 0;
                currentEl = siteNavIt.next();
                resultBuilder = new StringBuilder();
                try {
                    resultBuilder.append("\n").append(getMobileHtmlCode4Level(0, null));
                } catch (CmsException cmsEx) {
                    throw new JspException(cmsEx);
                }
            }
        }
        return resultBuilder != null ? resultBuilder.toString() : "";
    }
}
