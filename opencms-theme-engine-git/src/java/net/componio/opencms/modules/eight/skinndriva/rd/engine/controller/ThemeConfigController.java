/*
 *  Document : ThemeConfigController.java
 *  Created on  10.06.2011, 00:36:23
 *  Copyright (C) 2011 Robert Diawara
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


package net.componio.opencms.modules.eight.skinndriva.rd.engine.controller;

import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeConfig;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ScriptReference;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeDimensions;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeMargins;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.StyleReference;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.GridModel;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeFormatter;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigException;

import  javax.servlet.jsp.JspException;
import  javax.servlet.jsp.PageContext;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;

import  org.opencms.jsp.CmsJspXmlContentBean;
import  org.opencms.jsp.I_CmsXmlContentContainer;
import  org.opencms.file.CmsResourceFilter;
import  org.opencms.main.OpenCms;
import  org.opencms.main.CmsException;
import  org.opencms.file.CmsObject;
import  org.opencms.file.CmsResource;
import  org.opencms.file.CmsFile;
import  org.opencms.xml.content.CmsXmlContent;
import  org.opencms.xml.content.CmsXmlContentFactory;

import  java.util.ArrayList;
import  java.util.List;
import  java.util.Iterator;
import  java.text.DecimalFormat;
import  java.util.Locale;
import  java.util.regex.Pattern;
import  java.util.regex.Matcher;
import  java.util.HashMap;
import org.opencms.jsp.CmsJspActionElement;

/**
 * Provides methods to access, save and get theme configurations and theme
 * engine configurations on the system.
 * @author Robert Diawara
 */
public class ThemeConfigController extends DefaultController implements I_ThemeConfigController{
    /** Holds a hash map with all cached themes. */
    private static HashMap<String, ThemeConfig> cachedThemes     = null;
    
    /** Holds a list with all cached themes. */
    private static List<ThemeConfig>            cachedThemesList = null;
    
    /** Constants for the field names of an theme config object at VFS. */
    private static final class FieldNames{
        public static final String NAME                  = "name";
        public static final String FAVICON               = "favicon";
        public static final String SHOWLEFTHANDBAR       = "show-lefthand-bar";
        public static final String SHOWRIGHTHANDBAR      = "show-righthand-bar"; 
        public static final String SHOWTOOLBAR           = "show-toolbar"; 
        public static final String MAINTEMPLATE          = "main-template";
        public static final String RESPONSIVECSS         = "responsive-css-file";
        private static final class DIMENSIONS{
            public static final String _ROOT             = "dimensions";
            public static final String UNIT              = "dimensions[1]/unit";
            public static final String BOXSPACING        = "dimensions[1]/box-spacing";
            public static final String LEFTCOLUMNWIDTH   = "dimensions[1]/left-column-inner-width";
            public static final String CENTERCOLUMNWIDTH = "dimensions[1]/center-column-inner-width";
            public static final String RIGHTCOLUMNWIDTH  = "dimensions[1]/right-column-inner-width";
            public static final String HEADERHEIGHT      = "dimensions[1]/header-inner-height";
            public static final String TOOLBARHEIGHT     = "dimensions[1]/toolbar-inner-height";
            public static final String FOOTERHEIGHT      = "dimensions[1]/footer-inner-height";
            public static final String CSSFILE           = "dimensions[1]/css-file";
            public static final String MEDIUMCSSFILE     = "dimensions[1]/medium-size-css-file";
            public static final String SMALLCSSFILE      = "dimensions[1]/small-size-css-file";
            private static final class MARGINS{
                public static final String _ROOT         = "dimensions[1]/margins";
                public static final String TOP           = "dimensions[1]/margins[1]/top-margin";
                public static final String RIGHT         = "dimensions[1]/margins[1]/right-margin";
                public static final String BOTTOM        = "dimensions[1]/margins[1]/bottom-margin";
                public static final String LEFT          = "dimensions[1]/margins[1]/left-margin";
            }
        }
        private static final class GRID{
            public static final String _ROOT             = "grid";
            public static final String HEADERHEIGHT      = "header-height";
            public static final String FOOTERHEIGHT      = "footer-height";
            public static final String TOOLBARHEIGHT     = "toolbar-height";
            public static final String COLUMNCOUNT       = "column-count";
            public static final String COLUMNWIDTH       = "column-width";
            public static final String ORIENTATION       = "orientation";
            public static final String TOPMARGIN         = "top-margin";
            public static final String RIGHTMARGIN       = "right-margin";
            public static final String BOTTOMMARGIN      = "bottom-margin";
            public static final String LEFTMARGIN        = "left-margin";
            public static final String CSSFILE           = "css-file";
        }
        private static final class MEDIUM_SIZE_GRID{
            public static final String _ROOT             = "medium-size-grid";
        }
        private static final class SMALL_SIZE_GRID{
            public static final String _ROOT             = "small-size-grid";
        }
        private static final class SCRIPTS{
            public static final String _ROOT             = "scripts";
            public static final String INCLUDE           = "scripts[1]/script-include";
            public static final String SCRIPT            = "script";
            public static final String VISIBILITY        = "visibility";
            public static final String GROUP             = "group";
            public static final String PARAMETER         = "parameter";
        }
        private static final class STYLES{
            public static final String _ROOT             = "styles";
            private static final class STYLE{
                public static final String _ROOT         = "styles[1]/style";
                public static final String URI           = "uri";
                public static final String GROUP         = "group";
                public static final String MEDIA         = "media";
                public static final String LARGESCREEN   = "large-screen";
                public static final String MEDIUMSCREEN  = "medium-screen";
                public static final String SMALLSCREEN   = "small-screen";
                public static final String USERAGENT     = "user-agent";
            }
        }
        private static final class FORMATTERS{
            public static final String _ROOT             = "formatters";
            private static final class FORMATTER{
                public static final String _ROOT         = "formatters[1]/formatter-include";
                public static final String RESOURCETYPE  = "resource-type";
                public static final String CONTAINERTYPE = "container-type";
                public static final String JSP           = "jsp";
                public static final String MINWIDTH      = "minimum-width";
                public static final String MAXWIDTH      = "maximum-width";
                public static final String PREVIEW       = "preview";
                public static final String SEARCHABLE    = "searchable";
            }
        }
        private static final class INCLUDES{
            public static final String _ROOT             = "includes";
            public static final String HEADER            = "includes[1]/header-include";
            public static final String RIGHTHAND         = "includes[1]/righthand-include";
            public static final String FOOTER            = "includes[1]/footer-include";
            public static final String LEFTHAND          = "includes[1]/lefthand-include";
            public static final String TOOLBAR           = "includes[1]/toolbar-include";
        }
    }

    /**
     * Default constructor, which is private her to avoid an initialization without
     * providing the mandatory page context, servlet request and servlet response.
     */
    private ThemeConfigController(){}

    /**
     * Constructor providing all mandatory parameters needed for the initialization of
     * the theme engine config controller.
     * @param p_pageContext The page context needed by the theme engine controller an runtime.
     * @param p_request The servlet request needed by the theme engine controller an runtime.
     * @param p_response The servlet response needed by the theme engine controller an runtime.
     * @throws ThemeConfigException when one of the parameters <code>p_pageContext</code>,
     * <code>p_request</code> or <code>p_response</code> is null.
     */
    public ThemeConfigController(PageContext p_pageContext, HttpServletRequest p_request, HttpServletResponse p_response)
            throws ThemeConfigException{
        super(p_pageContext, p_request, p_response);

    }

    /**
     * Loads a theme configuration from VFS
     * @param p_configId The ID of the theme configuration to be loaded.
     * @return The theme configuration, if one with a name matching the parameter <code>p_configId</code> was
     * found. Otherwise null.
     * @throws ThemeConfigException Wraps exceptions, which may occur in the underlying OpenCms calls.
     */
    @Override
    public ThemeConfig loadThemeConfig(String p_configId) throws ThemeConfigException{
        String                   configURI;
        CmsJspXmlContentBean     contentBean;
        I_CmsXmlContentContainer contentContainer;
        ThemeConfig              result           = null;

        // Abort with an exception when no config id provided.
        if(p_configId == null){
            throw new ThemeConfigException("Can not load a theme configuration, when no id provided !");
        }

        synchronized(ThemeConfigController.class){
            if((ThemeConfigController.cachedThemes != null) && (useCaching()) && (ThemeConfigController.cachedThemes.containsKey(p_configId))){
                result = ThemeConfigController.cachedThemes.get(p_configId);
            }else{
                try{
                    contentBean = new CmsJspXmlContentBean(getPageContext(), getRequest(), getResponse());
                    configURI = getConfigFolder();
                    configURI  += "theme_%(number).conf|theme_config";
                    contentContainer = contentBean.contentload("allInFolder", configURI, false);
                    while((contentContainer.hasMoreResources()) && (result == null)){
                        result = loadResourceData(contentBean, contentContainer, "^(" + this.escapeRegularExpression(p_configId) + ")$");
                    }
                }catch(JspException jspEx){
                    throw new ThemeConfigException(jspEx);
                }
                if(result != null){
                    result.setUnchanged();
                    ThemeConfigController.cachedThemes.put(p_configId, result);
                }
            }
        }
        return result;
    }

    /**
     * Lists all available theme configurations available on the system.
     * @return The list with all active theme configurations. If there are no active theme configurations, this
     * will return an empty ArrayList.
     * @throws ThemeConfigException Wraps all exceptions, which may occur at the underlying calls.
     */
    @Override
    public ArrayList<ThemeConfig> listThemeConfigs() throws ThemeConfigException{
        String                   configURI;
        CmsJspXmlContentBean     contentBean;
        I_CmsXmlContentContainer contentContainer;
        ThemeConfig              current;
        ArrayList<ThemeConfig>   result            = new ArrayList<ThemeConfig>();

        synchronized(ThemeConfigController.class){
            if((this.useCaching()) && (ThemeConfigController.cachedThemesList != null)){
                result.addAll(ThemeConfigController.cachedThemesList);
            }else{
                try{
                    // Initialize the cached themes list and hash map
                    ThemeConfigController.cachedThemesList = new ArrayList<ThemeConfig>();
                    ThemeConfigController.cachedThemes = new HashMap<String, ThemeConfig>();
                    
                    contentBean = new CmsJspXmlContentBean(getPageContext(), getRequest(), getResponse());
                    configURI = getConfigFolder();
                    configURI  += "theme_%(number).conf|theme_config";
                    contentContainer = contentBean.contentload("allInFolder", configURI, false);
                    while(contentContainer.hasMoreResources()){
                        current = loadResourceData(contentBean, contentContainer, "^(.*)$");
                        if(current != null){
                            current.setUnchanged();
                            result.add(current);

                            // Cache the current Item
                            ThemeConfigController.cachedThemesList.add(current);
                            ThemeConfigController.cachedThemes.put(current.getId(), current);
                        }
                    }
                }catch(JspException jspEx){
                    throw new ThemeConfigException(jspEx);
                }
            }
        }
        return result;
    }

    /**
     * Creates a new theme configuration with the given id and returns its object representation.
     * @param p_configId The id of the new theme configuration to be created. If a theme configuration with this name
     * already exists, the name will be modified, by appending a number in brackets. By default 2 will be taken as
     * number. If a theme config with this id also exists, the number will be increased, until a unique id is found.
     * @return The new created theme configurations.
     * @throws ThemeConfigException Wraps all undelyinf exceptions with possibly may occur.
     */
    @Override
    public ThemeConfig createThemeConfig(String p_configId) throws ThemeConfigException{
        ThemeConfig          result      = new ThemeConfig();
        CmsJspXmlContentBean contentBean = new CmsJspXmlContentBean(getPageContext(), getRequest(), getResponse());
        CmsObject            cmsObj      = contentBean.getCmsObject();
        int                  typeId;
        String               resName;
        CmsResource          res;
        String               configId;
        List<CmsResource>    changedRes;
        
        synchronized(ThemeConfigController.class){
            try{
                configId = validateConfigId(result);

                typeId = OpenCms.getResourceManager().getResourceType("theme_config").getTypeId();
                resName = composeResourceName(cmsObj);
                res = cmsObj.createResource(resName, typeId);
                result.setId(configId);
                result.setVfsPath(resName);

                result.setShowLeftHandBar(true);
                result.setShowRightHandBar(true);
                result.getDimensions().setBoxSpacing(10);
                result.getDimensions().setLeftColumnInnerWidth(250);
                result.getDimensions().setCenterColumnInnerWidth(800);
                result.getDimensions().setRightColumnInnerWidth(250);
                result.getDimensions().setHeaderInnerHeight(150);
                result.getDimensions().setToolbarInnerHeight(100);
                result.getDimensions().setFooterInnerHeight(100);

                // Switch to the theme engine project
                switchToProject(cmsObj, DefaultController.THEME_ENGINE_PRJ);
        
                changedRes = writeResourceData(cmsObj, res, result);
                result.setResourceId(res.getResourceId().getStringValue());
                this.publishResources(cmsObj, changedRes);
        
                // Switch back to the offline project
                switchToProject(cmsObj, DefaultController.OFFLINE_PRJ);
                result.setUnchanged();

                // Cache the saved theme
                if(ThemeConfigController.cachedThemes == null){
                    ThemeConfigController.cachedThemes = new HashMap<String, ThemeConfig>();
                }
                ThemeConfigController.cachedThemes.put(result.getId(), result);
                if(ThemeConfigController.cachedThemesList == null){
                    ThemeConfigController.cachedThemesList = new ArrayList<ThemeConfig>();
                }
                ThemeConfigController.cachedThemesList.add(result);
            }catch(CmsException cmsEx){
                throw new ThemeConfigException(cmsEx);
            }catch(JspException jspEx){
                throw new ThemeConfigException(jspEx);
            }catch(Exception ex){
                throw new ThemeConfigException(ex);
            }
        }
        
        return result;
    }
    
    /**
     * Stores a newly created theme configtation to the VFS
     * @param p_themeConfig The object representation of the theme configuration to be saved.
     * @throws ThemeConfigException Wraps all underlying exceptions, which may occur during the operation.
     */
    @Override
    public void createThemeConfig(ThemeConfig p_themeConfig) throws ThemeConfigException{
        CmsJspXmlContentBean  contentBean  = new CmsJspXmlContentBean(getPageContext(), getRequest(), getResponse());
        CmsObject             cmsObj       = contentBean.getCmsObject();
        int                   typeId;
        CmsResource           newResource;
        String                resName;
        String                configId;
        List<CmsResource>     changedRes   = null;

        // Abort with an exception when no theme config provided
        if(p_themeConfig == null){
            throw new ThemeConfigException("Could not create a null theme configuration");
        }
        
         
        // Create the resource
        synchronized(ThemeConfigController.class){
            try{
                configId = validateConfigId(p_themeConfig);
                if(!p_themeConfig.getId().equals(configId)){
                    p_themeConfig.setId(configId);
                }

                typeId  = OpenCms.getResourceManager().getResourceType("theme_config").getTypeId();
                resName = composeResourceName(cmsObj);
                p_themeConfig.setVfsPath(resName);
                
                // Switch to the theme engine project
                switchToProject(cmsObj, DefaultController.THEME_ENGINE_PRJ);
        
                newResource = cmsObj.createResource(resName, typeId);
                if(newResource.isFile()){
                    changedRes = this.writeResourceData(cmsObj, newResource, p_themeConfig);
                    p_themeConfig.setResourceId(newResource.getResourceId().getStringValue());
                }
                publishResources(cmsObj, changedRes);
                p_themeConfig.setUnchanged();
        
                // Switch back to the offline project
                switchToProject(cmsObj, DefaultController.OFFLINE_PRJ);

                // Cache the saved theme
                if(ThemeConfigController.cachedThemes == null){
                    ThemeConfigController.cachedThemes = new HashMap<String, ThemeConfig>();
                }
                ThemeConfigController.cachedThemes.put(p_themeConfig.getId(), p_themeConfig);
                if(ThemeConfigController.cachedThemesList == null){
                    ThemeConfigController.cachedThemesList = new ArrayList<ThemeConfig>();
                }
                ThemeConfigController.cachedThemesList.add(p_themeConfig);
        }catch(CmsException cmsEx){
                throw new ThemeConfigException(cmsEx);
            }catch(JspException jspEx){
                throw new ThemeConfigException(jspEx);
            }catch(Exception ex){
                throw new ThemeConfigException(ex);
            }
        }
    }
    
    /**
     * Saves a theme configuration to the VFS. To check, if a theme configuration already exists on the VFS, the
     * <code>resourceId</code> property of the theme configuration object is used. If the property has a value, the 
     * engine will check if a resource with this id exists on VFS. It it exists, it will be overwritten. in all other 
     * cases a new resource
     * willbe created for the obejct on the VFS
     * @param p_config The theme configuration to be saved.
     * @throws ThemeConfigException wraps all underlying exception, which may occur during the operation
     */
    @Override
    public void saveThemeConfig(ThemeConfig p_config) throws ThemeConfigException{
        String                   configURI;
        CmsJspXmlContentBean     contentBean;
        I_CmsXmlContentContainer contentContainer;
        CmsObject                cmsObj;
        String                   siteRoot;
        String                   resName;
        CmsResource              res              = null;
        String                   currentResId;
        StringBuffer             sb;
        String                   resId;
        String                   configId;
        int                      loopCount;
        HashMap                  idMap;
        List<CmsResource>        changedRes;
        
        // Abort with an exception when no theme config provided
        if(p_config == null){
            throw new ThemeConfigException("Could not save a null theme configuration");
        }
        
         
        try{
            resId = p_config.getResourceId();
            if(resId == null){
                this.createThemeConfig(p_config);
            }else{
                synchronized(ThemeConfigController.class){
                    configId = validateConfigId(p_config);
                    p_config.setId(configId);

                    // Here, we search for the existing theme configuration on the system.
                    contentBean = new CmsJspXmlContentBean(getPageContext(), getRequest(), getResponse());
                    cmsObj = contentBean.getCmsObject();

                    configURI = getConfigFolder();
                    configURI  += "theme_%(number).conf|theme_config";
                    contentContainer = contentBean.contentload("allInFolder", configURI, false);
                    while((contentContainer.hasMoreResources()) && (res == null)){
                        currentResId = contentContainer.getXmlDocument().getFile().getResourceId().getStringValue();
                        if(resId.equals(currentResId)){
                            res = contentContainer.getXmlDocument().getFile();
                        }
                    }

                    // If the resource was found then save the data to it.
                    if(res != null){
                        // Switch to the theme engine project
                        switchToProject(cmsObj, DefaultController.THEME_ENGINE_PRJ);

                        cmsObj.lockResource(res);
                        changedRes = writeResourceData(cmsObj, res, p_config);

//                      siteRoot = OpenCms.getSiteManager().getCurrentSite(cmsObj).getSiteRoot();
//                      resName = res.getRootPath();
//                      if(resName.startsWith(siteRoot)){
//                          resName = resName.substring(siteRoot.length());
//                      }

                        p_config.setUnchanged();
                        cmsObj.unlockResource(res);
                        publishResources(cmsObj, changedRes);

                        // Switch back to the offline project
                        switchToProject(cmsObj, DefaultController.OFFLINE_PRJ);
                    }else{
                        throw new ThemeConfigException("Couldn't replace a theme configuration with the resource id \"" +resId +
                                "\". No such theme configuration found in VFS !");
                    }

                    // Cache the saved theme
                    if(ThemeConfigController.cachedThemes == null){
                        ThemeConfigController.cachedThemes = new HashMap<String, ThemeConfig>();
                    }
                    ThemeConfigController.cachedThemes.put(p_config.getId(), p_config);
                }
            }
        }catch(JspException jspEx){
            throw new ThemeConfigException(jspEx);
        }catch(CmsException cmsEx){
            throw new ThemeConfigException(cmsEx);
        }catch(Exception ex){
            throw new ThemeConfigException(ex);
        }
    }
    
    /**
     * Deletes a theme configuration from the system.
     * @param p_configId The ID / name, which identifies the theme configuration.
     * @throws ThemeConfigException When the deletion fails
     */
    @Override
    public void deleteThemeConfig(String p_configId) throws ThemeConfigException{
        ThemeConfig config  = loadThemeConfig(p_configId);
        String                vfsPath;
        CmsObject             cmsObj;
        Iterator<ThemeConfig> configIt;
        ThemeConfig           current;
        List<ThemeConfig>     newList;
        
        if(config != null){
            vfsPath = config.getVfsPath();
            cmsObj  = new CmsJspXmlContentBean(getPageContext(), getRequest(), getResponse()).getCmsObject();
            
            try{
                synchronized(ThemeConfigController.class){
                    // Switch to the theme engine project
                    switchToProject(cmsObj, DefaultController.THEME_ENGINE_PRJ);

                    // Perform the delete operation
                    cmsObj.lockResource(vfsPath);
                    cmsObj.deleteResource(vfsPath, CmsResource.DELETE_PRESERVE_SIBLINGS);

                    // Publish the deletion
                    OpenCms.getPublishManager().publishResource(cmsObj, vfsPath);

                    // Switch back to the offline project
                    switchToProject(cmsObj, DefaultController.OFFLINE_PRJ);
                    
                    // Remove the deleted object from cache.
                    if((ThemeConfigController.cachedThemes != null) && (ThemeConfigController.cachedThemes.containsKey(p_configId))){
                       ThemeConfigController.cachedThemes.remove(p_configId); 
                    }
                    if(ThemeConfigController.cachedThemesList != null){
                        newList = new ArrayList<ThemeConfig>();
                        for(configIt = ThemeConfigController.cachedThemesList.iterator(); configIt.hasNext();){
                            current = configIt.next();
                            if(!current.getId().equals(p_configId)){
                                newList.add(current);
                            }
                        }
                        ThemeConfigController.cachedThemesList = newList;
//                      newList = null;
                    }
                }
            }catch(CmsException cmsEx){
                throw new ThemeConfigException(cmsEx);
            }catch(Exception cmsEx){
                throw new ThemeConfigException(cmsEx);
            }
        }
    }
    
    /**
     * Checks if a theme configuration with a given id exists on the VFS
     * @param p_configId The id to check against.
     * @return <code>true</code> if such a theme configuration exists, otherwise <code>false</code>
     * @throws ThemeConfigException Wraps any underlying exception, which may occur in this method or its subcalls.
     */
    @Override
    public boolean existsThemeConfig(String p_configId) throws ThemeConfigException{
        return getIdMap().containsKey(p_configId);
    }
    
    /**
     * Private helper method used to read XML contents from VFS and to transform them into an object representation of a
     * theme configuration.
     * @param p_contentBean Offers the access to XML contents to be loaded from VFS
     * @param p_container The container, which to get the content from.
     * @param p_filter The name of the loaded theme config has to match this regular expression, otherwise the execution
     * will be skipped. This can be used for filtering theme configurations according to certain criterial
     * @return The object representation of the theme configration, which was loaded from VFS
     * @throws JspException Will be thrown when some undelying operations fail.
     * @throws ThemeConfigException Wraps exceptions thrown by underlying operations.
     */
    private ThemeConfig loadResourceData(CmsJspXmlContentBean p_contentBean, I_CmsXmlContentContainer p_container, String p_filter)
            throws JspException, ThemeConfigException{
        
        CmsFile                  file;
        String                   resourcePath;
        ThemeConfig              result               = null;
        GridModel                grid;
        String                   tmp;
        int                      tmpInt;
        String                   filter               = p_filter != null ? p_filter : "^(.*)$";
        String                   currentParam;
        ScriptReference          currentScript;
        ThemeFormatter           currentFormatter;
        String                   styleUri;
        String                   styleGroup;
        String                   styleMedia;
        String                   userAgent;
        List<String>             userAgents;
        I_CmsXmlContentContainer scriptsContainer;
        I_CmsXmlContentContainer stylesContainer;
        I_CmsXmlContentContainer formattersContainer;
        I_CmsXmlContentContainer paramsContainer;
        I_CmsXmlContentContainer gridContainer;
        I_CmsXmlContentContainer userAgentsContainer;
        boolean                  useForLargeScreens;
        boolean                  useForMediumScreens;
        boolean                  useForSmallScreens;

        tmp = contentShow(p_contentBean, p_container, "name", null);
        if((tmp != null) && (tmp.matches(filter))){
            file = p_container.getXmlDocument().getFile();
            resourcePath = this.getQualifiedResourceName(p_contentBean.getCmsObject(), file);
            
            result = new ThemeConfig();
            result.setResourceId(file.getResourceId().getStringValue());
            result.setName(tmp);
            result.setVfsPath(resourcePath);

            tmp = contentShow(p_contentBean, p_container, FieldNames.SHOWLEFTHANDBAR, "true");
            result.setShowLeftHandBar(tmp.toLowerCase().equals("true"));

            tmp = contentShow(p_contentBean, p_container, FieldNames.SHOWRIGHTHANDBAR, "true");
            result.setShowRightHandBar(tmp.toLowerCase().equals("true"));

            tmp = contentShow(p_contentBean, p_container, FieldNames.SHOWTOOLBAR, "true");
            result.setShowToolBar(tmp.toLowerCase().equals("true"));

            tmp = contentShow(p_contentBean, p_container, FieldNames.FAVICON, null);
            result.setFavIcon(tmp);

            tmp = contentShow(p_contentBean, p_container, FieldNames.MAINTEMPLATE, null);
            result.setMainTemplate(tmp);

            tmp = contentShow(p_contentBean, p_container, FieldNames.RESPONSIVECSS, null);
            result.setResponsiveCssFile(tmp);

            tmp = contentShow(p_contentBean, p_container, FieldNames.DIMENSIONS.UNIT, "px");
            result.setUnit(tmp);

            tmp = contentShow(p_contentBean, p_container, FieldNames.DIMENSIONS.BOXSPACING, "0");
            result.getDimensions().setBoxSpacing(Integer.parseInt(tmp));

            tmp = contentShow(p_contentBean, p_container, FieldNames.DIMENSIONS.LEFTCOLUMNWIDTH, "250");
            result.getDimensions().setLeftColumnInnerWidth(Integer.parseInt(tmp));

            tmp = contentShow(p_contentBean, p_container, FieldNames.DIMENSIONS.CENTERCOLUMNWIDTH, "800");
            result.getDimensions().setCenterColumnInnerWidth(Integer.parseInt(tmp));

            tmp = contentShow(p_contentBean, p_container, FieldNames.DIMENSIONS.RIGHTCOLUMNWIDTH, "250");
            result.getDimensions().setRightColumnInnerWidth(Integer.parseInt(tmp));

            tmp = contentShow(p_contentBean, p_container, FieldNames.DIMENSIONS.HEADERHEIGHT, "150");
            result.getDimensions().setHeaderInnerHeight(Integer.parseInt(tmp));

            tmp = contentShow(p_contentBean, p_container, FieldNames.DIMENSIONS.TOOLBARHEIGHT, "100");
            result.getDimensions().setToolbarInnerHeight(Integer.parseInt(tmp));

            tmp = contentShow(p_contentBean, p_container, FieldNames.DIMENSIONS.FOOTERHEIGHT, "100");
            result.getDimensions().setFooterInnerHeight(Integer.parseInt(tmp));

            tmpInt = Integer.parseInt(contentShow(p_contentBean, p_container, FieldNames.DIMENSIONS.MARGINS.TOP, "0"));
            if(tmpInt != 0){
                result.getDimensions().getMargins().setTopMargin(tmpInt);
            }

            tmpInt = Integer.parseInt(contentShow(p_contentBean, p_container, FieldNames.DIMENSIONS.MARGINS.RIGHT, "0"));
            if(tmpInt != 0){
                result.getDimensions().getMargins().setRightMargin(tmpInt);
            }

            tmpInt = Integer.parseInt(contentShow(p_contentBean, p_container, FieldNames.DIMENSIONS.MARGINS.BOTTOM, "0"));
            if(tmpInt != 0){
                result.getDimensions().getMargins().setBottomMargin(tmpInt);
            }

            tmpInt = Integer.parseInt(contentShow(p_contentBean, p_container, FieldNames.DIMENSIONS.MARGINS.LEFT, "0"));
            if(tmpInt != 0){
                result.getDimensions().getMargins().setLeftMargin(tmpInt);
            }
            
            tmp = contentShow(p_contentBean, p_container, FieldNames.DIMENSIONS.CSSFILE, null);
            if(tmp != null){
                result.getDimensions().setCssFile(tmp);
            }
            
            tmp = contentShow(p_contentBean, p_container, FieldNames.DIMENSIONS.MEDIUMCSSFILE, null);
            if(tmp != null){
                result.getDimensions().setMediumSizeCssFile(tmp);
            }
            
            tmp = contentShow(p_contentBean, p_container, FieldNames.DIMENSIONS.SMALLCSSFILE, null);
            if(tmp != null){
                result.getDimensions().setSmallSizeCssFile(tmp);
            }
            
            // load the grid
            gridContainer = p_contentBean.contentloop(p_container, FieldNames.GRID._ROOT);
            if(gridContainer.hasMoreResources()){
                grid = result.getGrid();
                grid.setMode(GridModel.MODE_DESKTOP);
                
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.HEADERHEIGHT, "180"));
                if(tmpInt != 0){
                    grid.setHeaderHeight(tmpInt);
                }
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.FOOTERHEIGHT, "130"));
                if(tmpInt != 0){
                    grid.setFooterHeight(tmpInt);
                }
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.TOOLBARHEIGHT, "130"));
                if(tmpInt != 0){
                    grid.setToolbarHeight(tmpInt);
                }
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.COLUMNCOUNT, "12"));
                if(tmpInt != 0){
                    grid.setColumnCount(tmpInt);
                }
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.COLUMNWIDTH, "60"));
                if(tmpInt != 0){
                    grid.setColumnWidth(tmpInt);
                }
                tmp = contentShow(p_contentBean, gridContainer, FieldNames.GRID.ORIENTATION, "left");
                tmpInt = tmp.equals("left") ? GridModel.ORIENTATION_LEFT : GridModel.ORIENTATION_RIGHT;
                grid.setOrientation(tmpInt);
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.TOPMARGIN, "10"));
                if(tmpInt != 0){
                    grid.setTopMargin(tmpInt);
                }
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.RIGHTMARGIN, "10"));
                if(tmpInt != 0){
                    grid.setRightMargin(tmpInt);
                }
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.BOTTOMMARGIN, "10"));
                if(tmpInt != 0){
                    grid.setBottomMargin(tmpInt);
                }
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.LEFTMARGIN, "10"));
                if(tmpInt != 0){
                    grid.setLeftMargin(tmpInt);
                }
                tmp = contentShow(p_contentBean, gridContainer, FieldNames.GRID.CSSFILE, null);
                if(tmp != null){
                    grid.setCssFile(tmp);
                }
             }
            
            // load the medium size grid
            gridContainer = p_contentBean.contentloop(p_container, FieldNames.MEDIUM_SIZE_GRID._ROOT);
            if(gridContainer.hasMoreResources()){
                grid = result.getGrid();
                grid.enableMode(GridModel.MODE_MEDIUM);
                grid.setMode(GridModel.MODE_MEDIUM);
                
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.HEADERHEIGHT, "90"));
                if(tmpInt != 0){
                    grid.setHeaderHeight(tmpInt);
                }
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.FOOTERHEIGHT, "70"));
                if(tmpInt != 0){
                    grid.setFooterHeight(tmpInt);
                }
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.TOOLBARHEIGHT, "70"));
                if(tmpInt != 0){
                    grid.setToolbarHeight(tmpInt);
                }
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.COLUMNCOUNT, "6"));
                if(tmpInt != 0){
                    grid.setColumnCount(tmpInt);
                }
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.COLUMNWIDTH, "60"));
                if(tmpInt != 0){
                    grid.setColumnWidth(tmpInt);
                }
                tmp = contentShow(p_contentBean, gridContainer, FieldNames.GRID.ORIENTATION, "left");
                tmpInt = tmp.equals("left") ? GridModel.ORIENTATION_LEFT : GridModel.ORIENTATION_RIGHT;
                grid.setOrientation(tmpInt);
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.TOPMARGIN, "5"));
                if(tmpInt != 0){
                    grid.setTopMargin(tmpInt);
                }
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.RIGHTMARGIN, "5"));
                if(tmpInt != 0){
                    grid.setRightMargin(tmpInt);
                }
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.BOTTOMMARGIN, "5"));
                if(tmpInt != 0){
                    grid.setBottomMargin(tmpInt);
                }
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.LEFTMARGIN, "5"));
                if(tmpInt != 0){
                    grid.setLeftMargin(tmpInt);
                }
                tmp = contentShow(p_contentBean, gridContainer, FieldNames.GRID.CSSFILE, null);
                if(tmp != null){
                    grid.setCssFile(tmp);
                }
                grid.setMode(GridModel.MODE_DESKTOP);
            }

            // load the small size grid
            gridContainer = p_contentBean.contentloop(p_container, FieldNames.SMALL_SIZE_GRID._ROOT);
            if(gridContainer.hasMoreResources()){
                grid = result.getGrid();
                grid.enableMode(GridModel.MODE_SMALL);
                grid.setMode(GridModel.MODE_SMALL);
                
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.HEADERHEIGHT, "90"));
                if(tmpInt != 0){
                    grid.setHeaderHeight(tmpInt);
                }
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.FOOTERHEIGHT, "70"));
                if(tmpInt != 0){
                    grid.setFooterHeight(tmpInt);
                }
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.TOOLBARHEIGHT, "70"));
                if(tmpInt != 0){
                    grid.setToolbarHeight(tmpInt);
                }
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.COLUMNCOUNT, "2"));
                if(tmpInt != 0){
                    grid.setColumnCount(tmpInt);
                }
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.COLUMNWIDTH, "60"));
                if(tmpInt != 0){
                    grid.setColumnWidth(tmpInt);
                }
                tmp = contentShow(p_contentBean, gridContainer, FieldNames.GRID.ORIENTATION, "left");
                tmpInt = tmp.equals("left") ? GridModel.ORIENTATION_LEFT : GridModel.ORIENTATION_RIGHT;
                grid.setOrientation(tmpInt);
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.TOPMARGIN, "2"));
                if(tmpInt != 0){
                    grid.setTopMargin(tmpInt);
                }
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.RIGHTMARGIN, "2"));
                if(tmpInt != 0){
                    grid.setRightMargin(tmpInt);
                }
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.BOTTOMMARGIN, "2"));
                if(tmpInt != 0){
                    grid.setBottomMargin(tmpInt);
                }
                tmpInt = Integer.parseInt(contentShow(p_contentBean, gridContainer, FieldNames.GRID.LEFTMARGIN, "2"));
                if(tmpInt != 0){
                    grid.setLeftMargin(tmpInt);
                }
                tmp = contentShow(p_contentBean, gridContainer, FieldNames.GRID.CSSFILE, null);
                if(tmp != null){
                    grid.setCssFile(tmp);
                }
                grid.setMode(GridModel.MODE_DESKTOP);
            }

            // Add all script references
            scriptsContainer = p_contentBean.contentloop(p_container, FieldNames.SCRIPTS.INCLUDE);
            while(scriptsContainer.hasMoreResources()){
                currentScript = new ScriptReference();

                tmp = contentShow(p_contentBean, scriptsContainer, FieldNames.SCRIPTS.SCRIPT, null);
                if(tmp != null){
                    // Set the script URI
                    currentScript.setScriptURI(tmp);

                    // Set the visisbility of the script
                    tmp = contentShow(p_contentBean, scriptsContainer, FieldNames.SCRIPTS.VISIBILITY, "all");
                    if(tmp.equals("offline")){
                        currentScript.setScriptVisibility(ScriptReference.VISIBILITY_OFFLINE);
                    }else if(tmp.equals("online")){
                        currentScript.setScriptVisibility(ScriptReference.VISIBILITY_ONLINE);
                    }else if(tmp.equals("all")){
                        currentScript.setScriptVisibility(ScriptReference.VISIBILITY_ALL);
                    }else{
                        currentScript.setScriptVisibility(ScriptReference.VISIBILITY_ALL);
                    }
                    
                    // Add the group, if one exists
                    tmp = contentShow(p_contentBean, scriptsContainer, FieldNames.SCRIPTS.GROUP, null);
                    currentScript.setGroup(tmp);
                    
                    // Add the parameters
                    paramsContainer = p_contentBean.contentloop(scriptsContainer, FieldNames.SCRIPTS.PARAMETER);
                    while(paramsContainer.hasMoreResources()){
                        currentParam = this.contentShow(p_contentBean, paramsContainer, null);
                        if(currentParam != null){
                            currentScript.addParameter(currentParam);
                        }
                    }
                    
                    // Add the scriot reference to the theme config
                    result.addScriptReference(currentScript);
                }
            }

            // Add all styles
            stylesContainer = p_contentBean.contentloop(p_container, FieldNames.STYLES.STYLE._ROOT);
            while(stylesContainer.hasMoreResources()){
                // Get the fields from backend
                styleUri = this.contentShow(p_contentBean, stylesContainer, FieldNames.STYLES.STYLE.URI, null);
                styleGroup = this.contentShow(p_contentBean, stylesContainer, FieldNames.STYLES.STYLE.GROUP, null);
                styleMedia = this.contentShow(p_contentBean, stylesContainer, FieldNames.STYLES.STYLE.MEDIA, null);
                useForLargeScreens = this.contentShow(p_contentBean, stylesContainer, FieldNames.STYLES.STYLE.LARGESCREEN, "true").equals("true");
                useForMediumScreens = this.contentShow(p_contentBean, stylesContainer, FieldNames.STYLES.STYLE.MEDIUMSCREEN, "true").equals("true");
                useForSmallScreens = this.contentShow(p_contentBean, stylesContainer, FieldNames.STYLES.STYLE.SMALLSCREEN, "true").equals("true");
                
                
                
                
                userAgents = new ArrayList<String>();
                
                // Compose the list with user agents from backend
                userAgentsContainer = p_contentBean.contentloop(stylesContainer, FieldNames.STYLES.STYLE.USERAGENT);
                while(userAgentsContainer.hasMoreResources()){
                    userAgent = this.contentShow(p_contentBean, userAgentsContainer, null);
                    if(userAgent != null){
                        userAgents.add(userAgent);
                    }
                }
                
                // Add the style to the theme config
                if(styleUri != null){
                    result.addStyle(styleUri, styleGroup, styleMedia, userAgents, 
                          useForLargeScreens, useForMediumScreens, useForSmallScreens);
                }
            }
            
            // Add all formatters
            formattersContainer = p_contentBean.contentloop(p_container, FieldNames.FORMATTERS.FORMATTER._ROOT);
            while(formattersContainer.hasMoreResources()){
                currentFormatter = new ThemeFormatter(result);
                
                tmp = contentShow(p_contentBean, formattersContainer, FieldNames.FORMATTERS.FORMATTER.RESOURCETYPE, null);
                if(tmp != null){
                    currentFormatter.setResourceType(tmp);
                }
                
                tmp = contentShow(p_contentBean, formattersContainer, FieldNames.FORMATTERS.FORMATTER.CONTAINERTYPE, null);
                if(tmp != null){
                    currentFormatter.setContainerType(tmp);
                }
                
                tmp = contentShow(p_contentBean, formattersContainer, FieldNames.FORMATTERS.FORMATTER.JSP, null);
                if(tmp != null){
                    currentFormatter.setJsp(tmp);
                }
                
                tmpInt = Integer.parseInt(contentShow(p_contentBean, formattersContainer, FieldNames.FORMATTERS.FORMATTER.MINWIDTH, "-1"));
                currentFormatter.setMinWidth(tmpInt);
                
                tmpInt = Integer.parseInt(contentShow(p_contentBean, formattersContainer, FieldNames.FORMATTERS.FORMATTER.MAXWIDTH, "-1"));
                currentFormatter.setMaxWidth(tmpInt);
                
                tmp = contentShow(p_contentBean, formattersContainer, FieldNames.FORMATTERS.FORMATTER.PREVIEW, "false");
                currentFormatter.setUsedForPreview(tmp.toLowerCase().equals("true"));
                
                //tmp = contentShow(p_contentBean, formattersContainer, FieldNames.FORMATTERS.FORMATTER.SEARCHABLE, "false");
                //currentFormatter.setSearchable(tmp.toLowerCase().equals("true"));
                
                // Add the formatter
                if(!result.hasFormatter(currentFormatter)){
                    result.addFormatter(currentFormatter);
                }
            }

            // Add all template includes
            tmp = contentShow(p_contentBean, p_container, FieldNames.INCLUDES.HEADER, null);
            if(tmp != null){
                result.setHeaderTemplate(tmp);
            }
            tmp = contentShow(p_contentBean, p_container, FieldNames.INCLUDES.RIGHTHAND, null);
            if(tmp != null){
                result.setRightHandTemplate(tmp);
            }
            tmp = contentShow(p_contentBean, p_container, FieldNames.INCLUDES.FOOTER, null);
            if(tmp != null){
                result.setFooterTemplate(tmp);
            }
            tmp = contentShow(p_contentBean, p_container, FieldNames.INCLUDES.LEFTHAND, null);
            if(tmp != null){
                result.setLeftHandTemplate(tmp);
            }
            tmp = contentShow(p_contentBean, p_container, FieldNames.INCLUDES.TOOLBAR, null);
            if(tmp != null){
                result.setToolbarTemplate(tmp);
            }
        }

        return result;
    }

    /**
     * Private helper method used to get the integer index value, which will be appended to the name of a theme 
     * configration, when its created on VFS
     * @param p_cmsObj Provides access to the VFS resource, which are to be analyzed to generate the integer id.
     * @param p_configFolder The folder, the content is to be checked from.
     * @return The integer id formatted as string. It can the directly be appended to the name for the new resource.
     * @throws CmsException When some of the VFS access operations fails.
     */
    private String getFileNameIndex(CmsObject p_cmsObj, String p_configFolder) throws CmsException{
        int                   result        = 1;
        int                   typeId        = OpenCms.getResourceManager().getResourceType("theme_config").getTypeId();
        CmsResourceFilter     filter        = CmsResourceFilter.requireType(typeId);
        List                  configs       = p_cmsObj.getResourcesInFolder(p_configFolder, filter);
        Iterator<CmsResource> configsIt;
        CmsResource           currentRes;
        String                currentName;
        int                   currentIndex;
        Pattern               pattern;
        Matcher               matcher;


        if(configs != null){
            //pattern = Pattern.compile("^(theme_)(\\d{1,3})(\\.conf)$");
            pattern = Pattern.compile("^((theme_)?[0-9a-f\\-]+)_(\\d{1,3})(\\.conf)$");
            for(configsIt = configs.iterator(); configsIt.hasNext();){
                currentRes = configsIt.next();
                currentName = currentRes.getName();

                matcher = pattern.matcher(currentName);
                if(matcher.matches()){
                    //currentIndex = Integer.parseInt(matcher.replaceAll("$2"));
                    currentIndex = Integer.parseInt(matcher.replaceAll("$3"));
                    if(currentIndex >= result){
                        result = currentIndex + 1;
                    }
                }
            }
        }
        return new DecimalFormat("000").format(result);
    }

    /**
     * Internal helper method used to generate resource names, when new resources are created
     * @param p_cmsObj Provides access to existing VFS resources to validate the name for a new resource.
     * @return The new Generated resource name.
     * @throws JspException When an error occurs during the retrieval of the name of the configuration folder, where
     * the new resource has to be stored in.
     * @throws CmsException When one of the underlying VFS operatioins fails.
     * @throws ThemeConfigException When one of the underlying operatioins fails.
     */
    protected String composeResourceName(CmsObject p_cmsObj) throws JspException, CmsException, ThemeConfigException{
        String                result;
        String                indexStr;

        result = getConfigFolder();
        indexStr = getFileNameIndex(p_cmsObj, result);
        if(!result.endsWith("/")){
            result += "/";
        }
        result += (getEngineUUID() + "_" + indexStr + ".conf"); 

        return result;
    }
 
    
    /**
     * Internal helper method, which takes over the object representation of a theme configuration, and writes it as 
     * XML content to the VFS.
     * @param p_cmsObj Provides access to the VFS resource, where the theme configration has to be stored in.
     * @param p_res Represents the resource, where the theme configuration has to be stored in.
     * @param p_config The object representation of the theme configuration, whose content has to be written to VFS.
     * @return A list with the created and modified VFS resources related to the theme, so that these resources can be
     * published later.
     * @throws CmsException When one of the underlying VFS operations fails.
     * @throws ThemeConfigException Wraps other underlying exception, which may occurr during the 
     * execution of this method.
     */
    protected List<CmsResource> writeResourceData(CmsObject p_cmsObj, CmsResource p_res, ThemeConfig p_config) throws CmsException, 
            ThemeConfigException{
        List<CmsResource> result              = new ArrayList<CmsResource>();
        CmsResource       gridResource;
        CmsResource       dimensionsResource;
        CmsFile           file                = p_cmsObj.readFile(p_res);
        CmsXmlContent     fileContent         = CmsXmlContentFactory.unmarshal(p_cmsObj, file);
        Locale            locale              = OpenCms.getLocaleManager().getDefaultLocale(p_cmsObj, p_res);
        ThemeDimensions   dim                 = p_config.getDimensions();
        GridModel         grid;
        ThemeMargins      margins             = dim.getMargins();
        ScriptReference[] scripts             = p_config.listScriptReferences();
        StyleReference[]  styles              = p_config.listStyles();
        ThemeFormatter[]  formatters          = p_config.listFormatters();
        List<String>      paramList;
        Iterator<String>  paramsIt;
        Iterator<String>  userAgentsIt;
        int               paramCount;
        int               scriptCount;
        int               stylesCount;
        int               formattersCount;
        int               loopCount;
        int               userAgentsCount;
        StringBuffer      baseSb;
        StringBuffer      genericSb;
        String            visibilityPath;
        String            currentValue;
        String            currentGroup;
        String            groupPath;
        String            paramPath;
        String            styleUriPath;
        String            styleGroupPath;
        String            styleMediaPath;
        String            styleLargeScreenPath;
        String            styleMediumScreenPath;
        String            styleSmallScreenPath;
        String            styleUserAgentPath;
        String            orientationStr;
        String            gridPath;
        String            formatterResTypePath;
        String            formatterContainerTypePath;
        String            formatterJspPath;
        String            formatterMinWidthPath;
        String            formatterMaxWidthPath;
        String            formatterPreviewPath;
        int               gridMode;
        
        // Write the Name and the booleans which determine, if the lefthand bar and the
        // righthand bar have to be displayed
        fileContent.getValue(FieldNames.NAME, locale).setStringValue(
                p_cmsObj, p_config.getName());
        fileContent.getValue(FieldNames.SHOWLEFTHANDBAR,locale).setStringValue(p_cmsObj, String.valueOf(
                p_config.isShowLeftHandBar()));
        fileContent.getValue(FieldNames.SHOWRIGHTHANDBAR,locale).setStringValue(p_cmsObj, String.valueOf(
                p_config.isShowRightHandBar()));
        fileContent.getValue(FieldNames.SHOWTOOLBAR,locale).setStringValue(p_cmsObj, String.valueOf(
                p_config.isShowToolBar()));
        
        if(!fileContent.getValues(FieldNames.FAVICON, locale).isEmpty()){
            fileContent.removeValue(FieldNames.FAVICON, locale, 0);
        }
        if((p_config.getFavIcon() != null) && (p_config.getFavIcon().trim().length() > 0)){
            fileContent.addValue(p_cmsObj, FieldNames.FAVICON, locale, 0).setStringValue(p_cmsObj, p_config.getFavIcon());
        }
        
        if(!fileContent.getValues(FieldNames.MAINTEMPLATE, locale).isEmpty()){
            fileContent.removeValue(FieldNames.MAINTEMPLATE, locale, 0);
        }
        if((p_config.getMainTemplate() != null) && (p_config.getMainTemplate().trim().length() > 0)){
            fileContent.addValue(p_cmsObj, FieldNames.MAINTEMPLATE, locale, 0).setStringValue(p_cmsObj, p_config.getMainTemplate());
        }
        
        if(!fileContent.getValues(FieldNames.RESPONSIVECSS, locale).isEmpty()){
            fileContent.removeValue(FieldNames.RESPONSIVECSS, locale, 0);
        }
        if((p_config.getResponsiveCssFile() != null) && (p_config.getResponsiveCssFile().trim().length() > 0)){
            fileContent.addValue(p_cmsObj, FieldNames.RESPONSIVECSS, locale, 0).setStringValue(p_cmsObj, p_config.getResponsiveCssFile());
        }
        
        // Write the dimensions
        if((p_config.getUnit() != null) && (p_config.getUnit().trim().length() > 0)){
            if(fileContent.getValues(FieldNames.DIMENSIONS.UNIT, locale).isEmpty()){
                fileContent.addValue(p_cmsObj, FieldNames.DIMENSIONS.UNIT, locale, 0).setStringValue(
                        p_cmsObj, p_config.getUnit());
            }else{
                fileContent.getValue(FieldNames.DIMENSIONS.UNIT, locale).setStringValue(
                        p_cmsObj, p_config.getUnit());
            }
        }
        fileContent.getValue(FieldNames.DIMENSIONS.BOXSPACING, locale).setStringValue(
                p_cmsObj, String.valueOf(dim.getBoxSpacing()));
        fileContent.getValue(FieldNames.DIMENSIONS.LEFTCOLUMNWIDTH, locale).setStringValue(
                p_cmsObj, String.valueOf(dim.getLeftColumnInnerWidth()));
        fileContent.getValue(FieldNames.DIMENSIONS.CENTERCOLUMNWIDTH, locale).setStringValue(
                p_cmsObj, String.valueOf(dim.getCenterColumnInnerWidth()));
        fileContent.getValue(FieldNames.DIMENSIONS.RIGHTCOLUMNWIDTH, locale).setStringValue(
                p_cmsObj, String.valueOf(dim.getRightColumnInnerWidth()));
        fileContent.getValue(FieldNames.DIMENSIONS.HEADERHEIGHT, locale).setStringValue(
                p_cmsObj, String.valueOf(dim.getHeaderInnerHeight()));
        fileContent.getValue(FieldNames.DIMENSIONS.TOOLBARHEIGHT, locale).setStringValue(
                p_cmsObj, String.valueOf(dim.getToolbarInnerHeight()));
        fileContent.getValue(FieldNames.DIMENSIONS.FOOTERHEIGHT, locale).setStringValue(
                p_cmsObj, String.valueOf(dim.getFooterInnerHeight()));

        if(!fileContent.getValues(FieldNames.DIMENSIONS.CSSFILE, locale).isEmpty()){
            fileContent.removeValue(FieldNames.DIMENSIONS.CSSFILE, locale, 0);
        }
        if((dim.getCssFile() != null) && (dim.getCssFile().trim().length() > 0)){
             fileContent.addValue(p_cmsObj, FieldNames.DIMENSIONS.CSSFILE, locale, 0).setStringValue(
                     p_cmsObj, dim.getCssFile());
        }
        
        if(!fileContent.getValues(FieldNames.DIMENSIONS.MEDIUMCSSFILE, locale).isEmpty()){
            fileContent.removeValue(FieldNames.DIMENSIONS.MEDIUMCSSFILE, locale, 0);
        }
        if((dim.getMediumSizeCssFile() != null) && (dim.getMediumSizeCssFile().trim().length() > 0)){
             fileContent.addValue(p_cmsObj, FieldNames.DIMENSIONS.MEDIUMCSSFILE, locale, 0).setStringValue(
                     p_cmsObj, dim.getMediumSizeCssFile());
        }
        
        if(!fileContent.getValues(FieldNames.DIMENSIONS.SMALLCSSFILE, locale).isEmpty()){
            fileContent.removeValue(FieldNames.DIMENSIONS.SMALLCSSFILE, locale, 0);
        }
        if((dim.getSmallSizeCssFile() != null) && (dim.getSmallSizeCssFile().trim().length() > 0)){
             fileContent.addValue(p_cmsObj, FieldNames.DIMENSIONS.SMALLCSSFILE, locale, 0).setStringValue(
                     p_cmsObj, dim.getSmallSizeCssFile());
        }
        
        if(margins.getTopMargin() != 0){
            if(fileContent.getValues(FieldNames.DIMENSIONS.MARGINS._ROOT, locale).isEmpty()){
                fileContent.addValue(p_cmsObj, FieldNames.DIMENSIONS.MARGINS._ROOT, locale, 0);
            }
            fileContent.getValue(FieldNames.DIMENSIONS.MARGINS.TOP, locale).setStringValue(
                    p_cmsObj, String.valueOf(margins.getTopMargin()));
        }
        if(margins.getRightMargin() != 0){
            if(fileContent.getValues(FieldNames.DIMENSIONS.MARGINS._ROOT, locale).isEmpty()){
                fileContent.addValue(p_cmsObj, FieldNames.DIMENSIONS.MARGINS._ROOT, locale, 0);
            }
            fileContent.getValue(FieldNames.DIMENSIONS.MARGINS.RIGHT, locale).setStringValue(
                    p_cmsObj, String.valueOf(margins.getRightMargin()));
        }
        if(margins.getBottomMargin() != 0){
            if(fileContent.getValues(FieldNames.DIMENSIONS.MARGINS._ROOT, locale).isEmpty()){
                fileContent.addValue(p_cmsObj, FieldNames.DIMENSIONS.MARGINS._ROOT, locale, 0);
            }
            fileContent.getValue(FieldNames.DIMENSIONS.MARGINS.BOTTOM, locale).setStringValue(
                    p_cmsObj, String.valueOf(margins.getBottomMargin()));
        }
        if(margins.getLeftMargin() != 0){
            if(fileContent.getValues(FieldNames.DIMENSIONS.MARGINS._ROOT, locale).isEmpty()){
                fileContent.addValue(p_cmsObj, FieldNames.DIMENSIONS.MARGINS._ROOT, locale, 0);
            }
            fileContent.getValue(FieldNames.DIMENSIONS.MARGINS.LEFT, locale).setStringValue(
                    p_cmsObj, String.valueOf(margins.getLeftMargin()));
        }
        
        // Write Grid settings
        if(!fileContent.getValues(FieldNames.GRID._ROOT, locale).isEmpty()){
            fileContent.removeValue(FieldNames.GRID._ROOT, locale, 0);
        }
        if(!fileContent.getValues(FieldNames.MEDIUM_SIZE_GRID._ROOT, locale).isEmpty()){
            fileContent.removeValue(FieldNames.MEDIUM_SIZE_GRID._ROOT, locale, 0);
        }
        if(!fileContent.getValues(FieldNames.SMALL_SIZE_GRID._ROOT, locale).isEmpty()){
            fileContent.removeValue(FieldNames.SMALL_SIZE_GRID._ROOT, locale, 0);
        }
        if(p_config.hasGrid()){
            grid = p_config.getGrid();
            gridMode = grid.getMode();
            orientationStr = grid.getOrientation() == GridModel.ORIENTATION_LEFT ? "left" : "right";
            
            grid.setMode(GridModel.MODE_DESKTOP);
            gridPath = FieldNames.GRID._ROOT + "[1]/";
            fileContent.addValue(p_cmsObj, FieldNames.GRID._ROOT, locale, 0);
            
            fileContent.getValue(gridPath + FieldNames.GRID.HEADERHEIGHT, locale).setStringValue(p_cmsObj, 
                    String.valueOf(grid.getHeaderHeight()));
            fileContent.getValue(gridPath + FieldNames.GRID.FOOTERHEIGHT, locale).setStringValue(p_cmsObj, 
                    String.valueOf(grid.getFooterHeight()));
            fileContent.getValue(gridPath + FieldNames.GRID.TOOLBARHEIGHT, locale).setStringValue(p_cmsObj, 
                    String.valueOf(grid.getToolbarHeight()));
            fileContent.getValue(gridPath + FieldNames.GRID.COLUMNCOUNT, locale).setStringValue(p_cmsObj, 
                    String.valueOf(grid.getColumnCount()));
            fileContent.getValue(gridPath + FieldNames.GRID.COLUMNWIDTH, locale).setStringValue(p_cmsObj, 
                    String.valueOf(grid.getColumnWidth()));
            fileContent.getValue(gridPath + FieldNames.GRID.ORIENTATION, locale).setStringValue(p_cmsObj, 
                    orientationStr);
            fileContent.getValue(gridPath + FieldNames.GRID.TOPMARGIN, locale).setStringValue(p_cmsObj, 
                    String.valueOf(grid.getTopMargin()));
            fileContent.getValue(gridPath + FieldNames.GRID.RIGHTMARGIN, locale).setStringValue(p_cmsObj, 
                    String.valueOf(grid.getRightMargin()));
            fileContent.getValue(gridPath + FieldNames.GRID.BOTTOMMARGIN, locale).setStringValue(p_cmsObj, 
                    String.valueOf(grid.getBottomMargin()));
            fileContent.getValue(gridPath + FieldNames.GRID.LEFTMARGIN, locale).setStringValue(p_cmsObj, 
                    String.valueOf(grid.getLeftMargin()));
            fileContent.getValue(gridPath + FieldNames.GRID.CSSFILE, locale).setStringValue(p_cmsObj, 
                    grid.getCssFile());
            
            if(grid.isModeEnabled(GridModel.MODE_MEDIUM)){
                grid.setMode(GridModel.MODE_MEDIUM);
                gridPath = FieldNames.MEDIUM_SIZE_GRID._ROOT + "[1]/";
                fileContent.addValue(p_cmsObj, FieldNames.MEDIUM_SIZE_GRID._ROOT, locale, 0);
                fileContent.getValue(gridPath + FieldNames.GRID.HEADERHEIGHT, locale).setStringValue(p_cmsObj, 
                        String.valueOf(grid.getHeaderHeight()));
                fileContent.getValue(gridPath + FieldNames.GRID.FOOTERHEIGHT, locale).setStringValue(p_cmsObj, 
                        String.valueOf(grid.getFooterHeight()));
                fileContent.getValue(gridPath + FieldNames.GRID.TOOLBARHEIGHT, locale).setStringValue(p_cmsObj, 
                        String.valueOf(grid.getToolbarHeight()));
                fileContent.getValue(gridPath + FieldNames.GRID.COLUMNCOUNT, locale).setStringValue(p_cmsObj, 
                        String.valueOf(grid.getColumnCount()));
                fileContent.getValue(gridPath + FieldNames.GRID.COLUMNWIDTH, locale).setStringValue(p_cmsObj, 
                        String.valueOf(grid.getColumnWidth()));
                fileContent.getValue(gridPath + FieldNames.GRID.ORIENTATION, locale).setStringValue(p_cmsObj, 
                        orientationStr);
                fileContent.getValue(gridPath + FieldNames.GRID.TOPMARGIN, locale).setStringValue(p_cmsObj, 
                        String.valueOf(grid.getTopMargin()));
                fileContent.getValue(gridPath + FieldNames.GRID.RIGHTMARGIN, locale).setStringValue(p_cmsObj, 
                        String.valueOf(grid.getRightMargin()));
                fileContent.getValue(gridPath + FieldNames.GRID.BOTTOMMARGIN, locale).setStringValue(p_cmsObj, 
                        String.valueOf(grid.getBottomMargin()));
                fileContent.getValue(gridPath + FieldNames.GRID.LEFTMARGIN, locale).setStringValue(p_cmsObj, 
                        String.valueOf(grid.getLeftMargin()));
                fileContent.getValue(gridPath + FieldNames.GRID.CSSFILE, locale).setStringValue(p_cmsObj, 
                        grid.getCssFile());
            }
        
            if(grid.isModeEnabled(GridModel.MODE_SMALL)){           
                grid.setMode(GridModel.MODE_SMALL);
                gridPath = FieldNames.SMALL_SIZE_GRID._ROOT + "[1]/";
                fileContent.addValue(p_cmsObj, FieldNames.SMALL_SIZE_GRID._ROOT, locale, 0);
                fileContent.getValue(gridPath + FieldNames.GRID.HEADERHEIGHT, locale).setStringValue(p_cmsObj, 
                        String.valueOf(grid.getHeaderHeight()));
                fileContent.getValue(gridPath + FieldNames.GRID.FOOTERHEIGHT, locale).setStringValue(p_cmsObj, 
                        String.valueOf(grid.getFooterHeight()));
                fileContent.getValue(gridPath + FieldNames.GRID.TOOLBARHEIGHT, locale).setStringValue(p_cmsObj, 
                        String.valueOf(grid.getToolbarHeight()));
                fileContent.getValue(gridPath + FieldNames.GRID.COLUMNCOUNT, locale).setStringValue(p_cmsObj, 
                        String.valueOf(grid.getColumnCount()));
                fileContent.getValue(gridPath + FieldNames.GRID.COLUMNWIDTH, locale).setStringValue(p_cmsObj, 
                        String.valueOf(grid.getColumnWidth()));
                fileContent.getValue(gridPath + FieldNames.GRID.ORIENTATION, locale).setStringValue(p_cmsObj, 
                        orientationStr);
                fileContent.getValue(gridPath + FieldNames.GRID.TOPMARGIN, locale).setStringValue(p_cmsObj, 
                        String.valueOf(grid.getTopMargin()));
                fileContent.getValue(gridPath + FieldNames.GRID.RIGHTMARGIN, locale).setStringValue(p_cmsObj, 
                        String.valueOf(grid.getRightMargin()));
                fileContent.getValue(gridPath + FieldNames.GRID.BOTTOMMARGIN, locale).setStringValue(p_cmsObj, 
                        String.valueOf(grid.getBottomMargin()));
                fileContent.getValue(gridPath + FieldNames.GRID.LEFTMARGIN, locale).setStringValue(p_cmsObj, 
                        String.valueOf(grid.getLeftMargin()));
                fileContent.getValue(gridPath + FieldNames.GRID.CSSFILE, locale).setStringValue(p_cmsObj, 
                        grid.getCssFile());

                grid.setMode(gridMode);
            }
        }
        
        // Write the script references, if there are.
        scriptCount = fileContent.getValues(FieldNames.SCRIPTS._ROOT, locale).size();
        while(scriptCount > 0){
            fileContent.removeValue(FieldNames.SCRIPTS._ROOT, locale, 0);
            scriptCount = fileContent.getValues(FieldNames.SCRIPTS._ROOT, locale).size();
        }
        if(scripts.length > 0){
            fileContent.addValue(p_cmsObj, FieldNames.SCRIPTS._ROOT, locale, 0);

            for(loopCount = 0; loopCount < scripts.length; loopCount ++){
                if(fileContent.getValues(FieldNames.SCRIPTS.INCLUDE, locale).size() <= loopCount){
                    fileContent.addValue(p_cmsObj, FieldNames.SCRIPTS.INCLUDE, locale, loopCount);
                }
                baseSb = new StringBuffer(FieldNames.SCRIPTS.INCLUDE);
                baseSb.append("[");
                baseSb.append(String.valueOf(loopCount + 1));
                baseSb.append("]/");
                baseSb.append(FieldNames.SCRIPTS.SCRIPT);
                fileContent.getValue(baseSb.toString(), locale).setStringValue(p_cmsObj, scripts[loopCount].getScriptURI());

                baseSb = new StringBuffer(FieldNames.SCRIPTS.INCLUDE);
                baseSb.append("[");
                baseSb.append(String.valueOf(loopCount + 1));
                baseSb.append("]/");
                
                genericSb = new StringBuffer(baseSb);
                genericSb.append(FieldNames.SCRIPTS.VISIBILITY);
                visibilityPath = genericSb.toString();
                
                genericSb = new StringBuffer(baseSb);
                genericSb.append(FieldNames.SCRIPTS.GROUP);
                groupPath = genericSb.toString();
                
                genericSb = new StringBuffer(baseSb);
                genericSb.append(FieldNames.SCRIPTS.PARAMETER);
                paramPath = genericSb.toString();
                
                // Write the visibility of the script
                switch(scripts[loopCount].getScriptVisibility()){
                    case ScriptReference.VISIBILITY_OFFLINE :
                        fileContent.getValue(visibilityPath, locale).setStringValue(p_cmsObj, "offline");
                        break;
                    case ScriptReference.VISIBILITY_ONLINE :
                        fileContent.getValue(visibilityPath, locale).setStringValue(p_cmsObj, "online");
                        break;
                    case ScriptReference.VISIBILITY_ALL :
                        fileContent.getValue(visibilityPath, locale).setStringValue(p_cmsObj, "all");
                        break;
                    case ScriptReference.VISIBILITY_NONE :
                        fileContent.getValue(visibilityPath, locale).setStringValue(p_cmsObj, "all");
                        break; 
                }
                
                // Write the group of the script reference
                currentGroup = scripts[loopCount].getGroup();
                if(!fileContent.getValues(groupPath, locale).isEmpty()){
                    fileContent.removeValue(groupPath, locale, 0);
                }
                if((currentGroup != null) && (currentGroup.trim().length() > 0)){
                    fileContent.addValue(p_cmsObj, groupPath, locale, 0).setStringValue(p_cmsObj, currentGroup);
                }
                
                // Write the parameters for the script
                paramCount = fileContent.getValues(paramPath, locale).size();
                while(paramCount > 0){
                    fileContent.removeValue(paramPath, locale, 0);
                    paramCount = fileContent.getValues(paramPath, locale).size();
                }
                paramList = scripts[loopCount].getParameters();
                if(paramList != null){
                    for(paramsIt = paramList.iterator(), paramCount = 0; paramsIt.hasNext(); paramCount ++){
                        fileContent.addValue(p_cmsObj, paramPath, locale, paramCount).setStringValue(p_cmsObj, paramsIt.next());
                    }
                }
            }
        }
        
        // Write styles
        stylesCount = fileContent.getValues(FieldNames.STYLES._ROOT, locale).size();
        if(stylesCount > 0){
            fileContent.removeValue(FieldNames.STYLES._ROOT, locale, 0);
        }
        if(styles.length > 0){
            fileContent.addValue(p_cmsObj, FieldNames.STYLES._ROOT, locale, 0);
            
            // Process new content
            for(loopCount = 0; loopCount < styles.length; loopCount ++){
                baseSb = new StringBuffer(FieldNames.STYLES.STYLE._ROOT);
                baseSb.append("[");
                baseSb.append(loopCount + 1);
                baseSb.append("]/");
                
                genericSb = new StringBuffer(baseSb);
                genericSb.append(FieldNames.STYLES.STYLE.URI);
                styleUriPath = genericSb.toString();
                
                genericSb = new StringBuffer(baseSb);
                genericSb.append(FieldNames.STYLES.STYLE.GROUP);
                styleGroupPath = genericSb.toString();
                
                genericSb = new StringBuffer(baseSb);
                genericSb.append(FieldNames.STYLES.STYLE.MEDIA);
                styleMediaPath = genericSb.toString();
                
                genericSb = new StringBuffer(baseSb);
                genericSb.append(FieldNames.STYLES.STYLE.USERAGENT);
                styleUserAgentPath = genericSb.toString();
                
                genericSb = new StringBuffer(baseSb);
                genericSb.append(FieldNames.STYLES.STYLE.LARGESCREEN);
                styleLargeScreenPath = genericSb.toString();
                
                genericSb = new StringBuffer(baseSb);
                genericSb.append(FieldNames.STYLES.STYLE.MEDIUMSCREEN);
                styleMediumScreenPath = genericSb.toString();
                
                genericSb = new StringBuffer(baseSb);
                genericSb.append(FieldNames.STYLES.STYLE.SMALLSCREEN);
                styleSmallScreenPath = genericSb.toString();
                
                // Add the element for the style, if not present yet.
                if(fileContent.getValues(FieldNames.STYLES.STYLE._ROOT, locale).size() <= loopCount){
                    fileContent.addValue(p_cmsObj, FieldNames.STYLES.STYLE._ROOT, locale, loopCount);
                }else{
                    // Remove the element for the group, if there's one
                    if(!fileContent.getValues(styleGroupPath, locale).isEmpty()){
                        fileContent.removeValue(styleGroupPath, locale, 0);
                    }
                    // Remove the element for media, if there's one
                    if(!fileContent.getValues(styleMediaPath, locale).isEmpty()){
                        fileContent.removeValue(styleMediaPath, locale, 0);
                    }
                    // Remove all elements for user agents, if there are
                    while(!fileContent.getValues(styleUserAgentPath, locale).isEmpty()){
                        fileContent.removeValue(styleUserAgentPath, locale, 0);
                    }
                    // Remove the flag for large screen usage, if there is one
                    while(!fileContent.getValues(styleLargeScreenPath, locale).isEmpty()){
                        fileContent.removeValue(styleLargeScreenPath, locale, 0);
                    }
                    // Remove the flag for medium screen usage, if there is one
                    while(!fileContent.getValues(styleMediumScreenPath, locale).isEmpty()){
                        fileContent.removeValue(styleMediumScreenPath, locale, 0);
                    }
                    // Remove the flag for small screen usage, if there is one
                    while(!fileContent.getValues(styleSmallScreenPath, locale).isEmpty()){
                        fileContent.removeValue(styleSmallScreenPath, locale, 0);
                    }
                }
                 
                // Add the URI
                fileContent.getValue(styleUriPath, locale).setStringValue(p_cmsObj, styles[loopCount].getURI());
                
                
                // Add the group
                if(styles[loopCount].getGroup() != null){
                    fileContent.addValue(p_cmsObj, styleGroupPath, locale, 0).setStringValue(p_cmsObj, styles[loopCount].getGroup());
                }
                
                // Add the media attribute
                if(styles[loopCount].getMedia() != null){
                    fileContent.addValue(p_cmsObj, styleMediaPath, locale, 0).setStringValue(p_cmsObj, styles[loopCount].getMedia());
                }
                
                // Add the user agents
                if(styles[loopCount].getUserAgents() != null){
                    for(userAgentsIt = styles[loopCount].getUserAgents().iterator(), userAgentsCount = 0; userAgentsIt.hasNext(); userAgentsCount ++){
                        fileContent.addValue(p_cmsObj, styleUserAgentPath, locale, userAgentsCount).setStringValue(p_cmsObj, userAgentsIt.next());
                    }
                }
                
                // Add the flag for large screen usage
                fileContent.addValue(p_cmsObj, styleLargeScreenPath, locale, 0).setStringValue(p_cmsObj, styles[loopCount].isUsedForLargeScreens() ? "true" : "false");
                
                // Add the flag for medium screen usage
                fileContent.addValue(p_cmsObj, styleMediumScreenPath, locale, 0).setStringValue(p_cmsObj, styles[loopCount].isUsedForMediumScreens()? "true" : "false");
                
                // Add the flag for small screen usage
                fileContent.addValue(p_cmsObj, styleSmallScreenPath, locale, 0).setStringValue(p_cmsObj, styles[loopCount].isUsedForSmallScreens()? "true" : "false");
            }
        }
        
        // Write formatters
        formattersCount = fileContent.getValues(FieldNames.FORMATTERS._ROOT, locale).size();
        if(formattersCount > 0){
            fileContent.removeValue(FieldNames.FORMATTERS._ROOT, locale, 0);
        }
        if(formatters.length > 0){
            fileContent.addValue(p_cmsObj, FieldNames.FORMATTERS._ROOT, locale, 0);
            
            // Process new content
            for(loopCount = 0; loopCount < formatters.length; loopCount ++){
                baseSb = new StringBuffer(FieldNames.FORMATTERS.FORMATTER._ROOT);
                baseSb.append("[");
                baseSb.append(loopCount + 1);
                baseSb.append("]/");
                
                genericSb = new StringBuffer(baseSb);
                genericSb.append(FieldNames.FORMATTERS.FORMATTER.RESOURCETYPE);
                formatterResTypePath = genericSb.toString();
                
                genericSb = new StringBuffer(baseSb);
                genericSb.append(FieldNames.FORMATTERS.FORMATTER.CONTAINERTYPE);
                formatterContainerTypePath = genericSb.toString();
                
                genericSb = new StringBuffer(baseSb);
                genericSb.append(FieldNames.FORMATTERS.FORMATTER.JSP);
                formatterJspPath = genericSb.toString();
                
                genericSb = new StringBuffer(baseSb);
                genericSb.append(FieldNames.FORMATTERS.FORMATTER.MINWIDTH);
                formatterMinWidthPath = genericSb.toString();
                
                genericSb = new StringBuffer(baseSb);
                genericSb.append(FieldNames.FORMATTERS.FORMATTER.MAXWIDTH);
                formatterMaxWidthPath = genericSb.toString();
                
                genericSb = new StringBuffer(baseSb);
                genericSb.append(FieldNames.FORMATTERS.FORMATTER.PREVIEW);
                formatterPreviewPath = genericSb.toString();
                
                //genericSb = new StringBuffer(baseSb);
                //genericSb.append(FieldNames.FORMATTERS.FORMATTER.SEARCHABLE);
                //formatterSearchablePath = genericSb.toString();
                
                // Add the element for the formatters, if not present yet, otherwise delete
                // all optional fields of the existing one.
                if(fileContent.getValues(FieldNames.FORMATTERS.FORMATTER._ROOT, locale).size() <= loopCount){
                    fileContent.addValue(p_cmsObj, FieldNames.FORMATTERS.FORMATTER._ROOT, locale, loopCount);
                }else{
                    // Remove the element for the minimum width, if there's one
                    if(!fileContent.getValues(formatterMinWidthPath, locale).isEmpty()){
                        fileContent.removeValue(formatterMinWidthPath, locale, 0);
                    }
                    // Remove the element for the maximum width, if there's one
                    if(!fileContent.getValues(formatterMaxWidthPath, locale).isEmpty()){
                        fileContent.removeValue(formatterMaxWidthPath, locale, 0);
                    }
                    /// Remove the element for the property "Use for preview", if there's one
                    if(!fileContent.getValues(formatterPreviewPath, locale).isEmpty()){
                        fileContent.removeValue(formatterPreviewPath, locale, 0);
                    }
                }
                 
                // Add the resource type
                fileContent.getValue(formatterResTypePath, locale).setStringValue(p_cmsObj, formatters[loopCount].getResourceType());
                 
                // Add the container type
                fileContent.getValue(formatterContainerTypePath, locale).setStringValue(p_cmsObj, formatters[loopCount].getContainerType());
                 
                // Add the jsp name
                fileContent.getValue(formatterJspPath, locale).setStringValue(p_cmsObj, formatters[loopCount].getJsp());
                
                // Add the minimum width, when set
                if(formatters[loopCount].getMinWidth() >= 0){
                    fileContent.addValue(p_cmsObj, formatterMinWidthPath, locale, 0).setStringValue(
                            p_cmsObj, String.valueOf(formatters[loopCount].getMinWidth()));
                }
                
                // Add the maximum width, when set
                if(formatters[loopCount].getMaxWidth() >= 0){
                    fileContent.addValue(p_cmsObj, formatterMaxWidthPath, locale, 0).setStringValue(
                            p_cmsObj, String.valueOf(formatters[loopCount].getMaxWidth()));
                }
                
                // Add the value for the property "Use for preview", when set
                if(formatters[loopCount].isUsedForPreview()){
                    fileContent.addValue(p_cmsObj, formatterPreviewPath, locale, 0).setStringValue(
                            p_cmsObj, String.valueOf(formatters[loopCount].isUsedForPreview()));
                }
            }
        }  
        
        
        
        // Write template includes
        if(fileContent.getValues(FieldNames.INCLUDES._ROOT, locale).isEmpty()){
            fileContent.addValue(p_cmsObj, FieldNames.INCLUDES._ROOT, locale, 0);
        }
        currentValue = p_config.getHeaderTemplate();
        if(currentValue != null){
            if(fileContent.getValues(FieldNames.INCLUDES.HEADER, locale).isEmpty()){
                fileContent.addValue(p_cmsObj, FieldNames.INCLUDES.HEADER, locale, 0).setStringValue(p_cmsObj, currentValue);
            }else{
                fileContent.getValue(FieldNames.INCLUDES.HEADER, locale).setStringValue(p_cmsObj, currentValue);
            }
        }
        currentValue = p_config.getRightHandTemplate();
        if(currentValue != null){
            if(fileContent.getValues(FieldNames.INCLUDES.RIGHTHAND, locale).isEmpty()){
                fileContent.addValue(p_cmsObj, FieldNames.INCLUDES.RIGHTHAND, locale, 0).setStringValue(p_cmsObj, currentValue);
            }else{
                fileContent.getValue(FieldNames.INCLUDES.RIGHTHAND, locale).setStringValue(p_cmsObj, currentValue);
            }
        }
        currentValue = p_config.getFooterTemplate();
        if(currentValue != null){
            if(fileContent.getValues(FieldNames.INCLUDES.FOOTER, locale).isEmpty()){
                fileContent.addValue(p_cmsObj, FieldNames.INCLUDES.FOOTER, locale, 0).setStringValue(p_cmsObj, currentValue);
            }else{
                fileContent.getValue(FieldNames.INCLUDES.FOOTER, locale).setStringValue(p_cmsObj, currentValue);
            }
        }
        currentValue = p_config.getLeftHandTemplate();
        if(currentValue != null){
            if(fileContent.getValues(FieldNames.INCLUDES.LEFTHAND, locale).isEmpty()){
                fileContent.addValue(p_cmsObj, FieldNames.INCLUDES.LEFTHAND, locale, 0).setStringValue(p_cmsObj, currentValue);
            }else{
                fileContent.getValue(FieldNames.INCLUDES.LEFTHAND, locale).setStringValue(p_cmsObj, currentValue);
            }
        }
        currentValue = p_config.getToolbarTemplate();
        if(currentValue != null){
            if(fileContent.getValues(FieldNames.INCLUDES.TOOLBAR, locale).isEmpty()){
                fileContent.addValue(p_cmsObj, FieldNames.INCLUDES.TOOLBAR, locale, 0).setStringValue(p_cmsObj, currentValue);
            }else{
                fileContent.getValue(FieldNames.INCLUDES.TOOLBAR, locale).setStringValue(p_cmsObj, currentValue);
            }
        }
        
        file.setContents(fileContent.marshal()); 
        p_cmsObj.writeFile(file);
        result.add(file);
        
        // Genetate the CSS for the grid
        gridResource = writeGridCss(p_cmsObj, p_config);
        if(gridResource != null){
            result.add(gridResource);
        }
        
        // Genetate the CSS for the dimensions
        dimensionsResource = writeDimensionsCss(p_cmsObj, p_config);
        if(dimensionsResource != null){
            result.add(dimensionsResource);
        }
        
        // Return the list of created or modified resources
        return result;
    }
    
    /**
     * Generates the CSS code for the theme grid and writes it to the VFS
     * @param p_cmsObj Provides access to the VFS resource, where the CSS code has to be stored in.
     * @param p_config The theme configuration which the grid is defined in.
     * @return A CmsResource representing the VFS resource, where the CSS code is saved to.
     * @throws CmsException When one of the underlying VFS operations fails.
     * @throws ThemeConfigException Wraps other underlying exception, which may occur during the 
     * execution of this method. 
     */
    protected CmsResource writeGridCss(CmsObject p_cmsObj, ThemeConfig p_config) throws CmsException, ThemeConfigException{
        GridModel           grid         = p_config.hasGrid() ? p_config.getGrid() : null;
        String              resName;
        CmsResource         res;
        CmsFile             file;
        CmsResource         result       = null;
        int                 initialMode;
        CmsJspActionElement actionEl;
        
        if(grid != null){
            synchronized(grid){
                if(((grid.isChanged()) || (p_config.isChanged()))){
                    // Generate the CSS file with the media queries.
                    resName = p_config.getResponsiveCssFile();
                    if((resName != null) && (resName.trim().length() > 0)){
                        res = p_cmsObj.readResource(resName);
                        if(res.isFile()){
                            actionEl = new CmsJspActionElement(getPageContext(), getRequest(), getResponse());

                            file = p_cmsObj.readFile(res);
                            p_cmsObj.lockResource(file);
                            file.setContents(grid.generateGenericCSSCode(actionEl, resName).getBytes());
                            p_cmsObj.writeFile(file);
                            p_cmsObj.unlockResource(file);
                            result = file;
                        }

                    }

                    initialMode = grid.getMode();

                    // Write the CSS file for normal desktop screens
                    grid.setMode(GridModel.MODE_DESKTOP);
                    resName = grid.getCssFile();
                    if((resName != null) && (resName.trim().length() > 0)){
                        res = p_cmsObj.readResource(resName);
                        if(res.isFile()){
                            file = p_cmsObj.readFile(res);
                            p_cmsObj.lockResource(file);
                            file.setContents(grid.generateCSSCode().getBytes());
                            p_cmsObj.writeFile(file);
                            p_cmsObj.unlockResource(file);
                            result = file;
                        }
                    }

                    // Write the CSS file for medium sized screens
                    if(grid.isModeEnabled(GridModel.MODE_MEDIUM)){
                        grid.setMode(GridModel.MODE_MEDIUM);
                        resName = grid.getCssFile();
                        if((resName != null) && (resName.trim().length() > 0)){
                            res = p_cmsObj.readResource(resName);
                            if(res.isFile()){
                                file = p_cmsObj.readFile(res);
                                p_cmsObj.lockResource(file);
                                file.setContents(grid.generateCSSCode().getBytes());
                                p_cmsObj.writeFile(file);
                                p_cmsObj.unlockResource(file);
                                result = file;
                            }

                        }
                    }

                    // write the CSS file for small screens
                    if(grid.isModeEnabled(GridModel.MODE_SMALL)){
                        grid.setMode(GridModel.MODE_SMALL);
                        resName = grid.getCssFile();
                        if((resName != null) && (resName.trim().length() > 0)){
                            res = p_cmsObj.readResource(resName);
                            if(res.isFile()){
                                file = p_cmsObj.readFile(res);
                                p_cmsObj.lockResource(file);
                                file.setContents(grid.generateCSSCode().getBytes());
                                p_cmsObj.writeFile(file);
                                p_cmsObj.unlockResource(file);
                                result = file;
                            }

                        }
                    }

                    grid.setMode(initialMode);
                }
            }
        }
        return result;
    }
    
    /**
     * Generates the CSS code for the theme dimensions and writes it to the VFS
     * @param p_cmsObj Provides access to the VFS resource, where the CSS code has to be stored in.
     * @param p_config The theme configuration which the dimensions are defined for.
     * @return A CmsResource representing the VFS resource, where the CSS code is saved to.
     * @throws CmsException When one of the underlying VFS operations fails.
     * @throws ThemeConfigException Wraps other underlying exception, which may occurr during the 
     * execution of this method. 
     */
    protected CmsResource writeDimensionsCss(CmsObject p_cmsObj, ThemeConfig p_config) throws CmsException, ThemeConfigException{
        ThemeDimensions dim     = p_config.getDimensions();
        String          resName;
        CmsResource     res;
        CmsFile         file;
        CmsResource     result  = null;
        
        if(dim != null){
            resName = dim.getCssFile();
            if((resName != null) && (resName.trim().length() > 0)){
                res = p_cmsObj.readResource(resName);
                if(res.isFile()){
                    file = p_cmsObj.readFile(res);
                    p_cmsObj.lockResource(file);
                    file.setContents(dim.generateCSSCode().getBytes());
                    p_cmsObj.writeFile(file);
                    p_cmsObj.unlockResource(file);
                    result = file;
                }

            }
        }
        return result;
    }
    
    /**
     * Internal helper method used as unitity to get a hashmap with all theme config ids mapped to the respective resource
     * ids on VFS. This is used to check if a theme configuration exists on VFS or not.
     * @return The hashmap as desribed above.
     * @throws ThemeConfigException Wrpas any other exception, which may occur during the execution of this method.
     */
    private HashMap<String, String> getIdMap() throws ThemeConfigException{
        HashMap<String, String>  result = new HashMap<String, String>();
        String                   id;
        String                   resourceId;
        String                   configURI;
        CmsJspXmlContentBean     contentBean;
        I_CmsXmlContentContainer contentContainer;

        synchronized(ThemeConfigController.class){
            try{
                contentBean = new CmsJspXmlContentBean(getPageContext(), getRequest(), getResponse());
                configURI = getConfigFolder();
                configURI  += "theme_%(number).conf|theme_config";
                contentContainer = contentBean.contentload("allInFolder", configURI, false);
                while(contentContainer.hasMoreResources()){
                    id = contentShow(contentBean, contentContainer, "name", "");
                    resourceId = contentContainer.getXmlDocument().getFile().getResourceId().getStringValue();
                    result.put(id, resourceId);
                }
            }catch(JspException jspEx){
                throw new ThemeConfigException(jspEx);
            }
        }
        
        return result;
    }
    
    /**
     * Private helper method, used to escap all special characters in a string to make it usable as a regular expression.
     * @param p_regex The string to be escaped.
     * @return The escaped string.
     */
    private String escapeRegularExpression(String p_regex){
        String result;
        
        result = p_regex.replace("\\", "\\\\");
        result = result.replace("(", "\\(");
        result = result.replace(")", "\\)");
        result = result.replace("[", "\\[");
        result = result.replace("]", "\\]");
        result = result.replace(".", "\\.");
        result = result.replace("+", "\\+");
        result = result.replace("?", "\\?");
        result = result.replace("*", "\\*");
        result = result.replace("-", "\\-");
        result = result.replace(" ", "\\s");
        
        return result;
    }
    
    /**
     * Protected helper method used to generate a unique id for a theme configuration, when its
     * id is already assigned to another theme configuration at the VFS.
     * @param p_config The theme configuration, whose id is to be validated.
     * @return A new uique id, when the id of the theme configuration was not unique, otherwise if
     * the id was unique, the unchanged id of the theme configuration.
     * @throws ThemeConfigException When something fails during the processing.
     */
    protected String validateConfigId(ThemeConfig p_config) throws ThemeConfigException{
        String                  result    = null;
        String                  configId;
        String                  resId;
        HashMap<String, String> idMap;
        int                     loopCount;
        StringBuffer            sb;
        
        if(p_config != null){
            synchronized(ThemeConfigController.class){
                configId = p_config.getName();
                result = p_config.getName();
                resId = p_config.getResourceId();
                idMap = getIdMap();

                // When the current theme config is not a new one and already exists on
                // VFS, then remove its id from the id map. 
                if((idMap.containsKey(result)) && (idMap.get(result).equals(resId))){
                    idMap.remove(result);
                }

                // If a theme config with the same id already exists, then modify the
                // until its unique
                loopCount = 2;
                while(idMap.containsKey(result)){
                    sb = new StringBuffer(configId);
                    sb.append(" (");
                    sb.append(loopCount);
                    sb.append(")");
                    result = sb.toString();
                    loopCount ++;
                }
            }
        }
        return result;
    }
   
        
    /**
     * Gets the URI of a theme config identified by its it.
     * @param p_cmsObj The CMS object, which provides access to the VFS
     * @param p_configName The name which identifies the theme configuration whose URI is to retrieved.
     * @return The URI if such a theme configuration exists. Otherwise null.
     * @throws ThemeConfigException When something goes wrong during the operation.
     */
    public String getThemeURI(CmsObject p_cmsObj, String p_configName) throws ThemeConfigException{
        String                   configURI;
        CmsJspXmlContentBean     contentBean      = new CmsJspXmlContentBean(this.getPageContext(),getRequest(), getResponse());
        I_CmsXmlContentContainer contentContainer;
        String                   currentName;
        String                   result           = null;
        
        try{
            configURI = getConfigFolder();
            configURI  += "theme_%(number).conf|theme_config";
            contentContainer = contentBean.contentload("allInFolder", configURI, false);
            while((contentContainer.hasMoreResources()) && (result == null)){
                currentName = this.contentShow(contentBean, contentContainer, "name", "");
                if(p_configName.equals(currentName)){
                    result = contentContainer.getXmlDocument().getFile().getRootPath();
               }
            }
            return result;
        }catch(JspException jspEx){
            throw new ThemeConfigException(jspEx);
        }catch(Exception ex){
            throw new ThemeConfigException(ex);
        }
    } 
}
