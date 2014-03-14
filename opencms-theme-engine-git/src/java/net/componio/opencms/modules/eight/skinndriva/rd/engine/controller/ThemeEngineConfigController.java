/*
 *  Document : ThemeEngineConfigController.java
 *  Created on  10.06.2011, 00:37:09
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

import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeEngineConfig;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeConfig;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigException;

import  javax.servlet.jsp.JspException;
import  javax.servlet.jsp.PageContext;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;

import  org.opencms.jsp.CmsJspXmlContentBean;
import  org.opencms.jsp.I_CmsXmlContentContainer;
import  org.opencms.main.CmsException;
import  org.opencms.file.CmsResource;
import  org.opencms.file.CmsObject;
import  org.opencms.file.CmsFile;
import  org.opencms.xml.content.CmsXmlContent;
import  org.opencms.xml.content.CmsXmlContentFactory;
import  org.opencms.main.OpenCms;

import  java.util.ArrayList;
import  java.util.Iterator;
import  java.util.Locale;
import  java.util.List;

/**
 * 
 * @author Robert Diawara
 */
public class ThemeEngineConfigController  extends DefaultController implements I_ThemeEngineConfigController{
    /** Holds a theme config controller instance to manage the
     * associated theme configs.  */
    private ThemeConfigController configController;
    
    /**Holds a cached instance of the theme engine. */
    private static ThemeEngineConfig cachedEngine = null;
    
    /** Constants for the field names of an theme engine config object at VFS. */
    private static final class FieldNames{
        public static final String DEFAULT_THEME      = "default-theme";
        public static final String CONFIG_FOLDER      = "config-folder";
        public static final String RUNTIME_PARAMETER  = "runtime-parameter";
    }

    /**
     * Default constructor, which is private her to avoid an initialization without
     * providing the mandatory page context, servlet request and servlet response.
     */
    private ThemeEngineConfigController(){}

    /**
     * Constructor providing all mandatory parameters needed for the initialization of
     * the theme engine config controller.
     * @param p_pageContext The page context needed by the theme engine controller an runtime.
     * @param p_request The servlet request needed by the theme engine controller an runtime.
     * @param p_response The servlet response needed by the theme engine controller an runtime.
     * @throws ThemeConfigException when one of the parameters <code>p_pageContext</code>,
     * <code>p_request</code> or <code>p_response</code> is null.
     */
    public ThemeEngineConfigController(PageContext p_pageContext, HttpServletRequest p_request, HttpServletResponse p_response)
            throws ThemeConfigException{
        super(p_pageContext, p_request, p_response);
        configController = new ThemeConfigController(p_pageContext, p_request, p_response);
    }
    

    /**
     * Loads the theme engine configuration from the VFS
     * @return The loaded theme engine configuration.
     * @throws ThemeConfigException When one of the underlying calls fails.
     */
    @Override
    public ThemeEngineConfig loadThemeEngine() throws ThemeConfigException{
        String                   engineURI;
        CmsJspXmlContentBean     contentBean;
        I_CmsXmlContentContainer contentContainer;
        ThemeEngineConfig        result           = null;
       
        synchronized(ThemeEngineConfigController.class){
            // When caching is used, then return the cached engine, if one exists
            // Otherwise reload the engine, cache it and return it.
            if((useCaching()) && (ThemeEngineConfigController.cachedEngine != null)){
                result = ThemeEngineConfigController.cachedEngine;
            }else{
                try{
                    contentBean = new CmsJspXmlContentBean(getPageContext(), getRequest(), getResponse());
                    engineURI = this.getConfigURI(); 
                    contentContainer = contentBean.contentload("singleFile", engineURI, false);
                    if(contentContainer.hasMoreResources()){
                        result = this.loadResourceData(contentBean, contentContainer);
                    }
                    result.setUnchanged();
                 }catch(JspException jspEx){
                    throw new ThemeConfigException(jspEx);
                }

                // Cache the loaded engine
                ThemeEngineConfigController.cachedEngine = result;
            }
        }
       
        return result;
    }
    
    /**
     * Saves a theme engine configuration to the VFS
     * @param p_engine The theme engine configuration to be saved.
     * @throws ThemeConfigException Wraps all exceptions which may be thrown by one of the underlying calls.
     */
    @Override
    public void saveThemeEngine(ThemeEngineConfig p_engine) throws ThemeConfigException{
        String                   engineURI;
        CmsJspXmlContentBean     contentBean       = new CmsJspXmlContentBean(getPageContext(), getRequest(), getResponse());
        I_CmsXmlContentContainer contentContainer;
        CmsObject                cmsObj            = contentBean.getCmsObject();
        ArrayList<CmsResource>   publishResources  = new ArrayList<CmsResource>();
        CmsResource              res;
        ThemeConfig[]            themeConfigs;
        ThemeConfig              currentThemeConfig;
        int                      loopCount;
        CmsResource              currentRes;
        int                      typeId;
        String                   resName;
        String                   configId;
        List<CmsResource>        changedRes;
        
        try{
            synchronized(ThemeEngineConfigController.class){
                // Switch to the theme engine project
                switchToProject(cmsObj, DefaultController.THEME_ENGINE_PRJ);

                if((p_engine.isChanged()) || (p_engine.isNew())){
                    engineURI = this.getConfigURI(); 
                    contentContainer = contentBean.contentload("singleFile", engineURI, false);
                    if(contentContainer.hasMoreResources()){
                        res = contentContainer.getXmlDocument().getFile();
                        writeResourceData(cmsObj, res, p_engine);
                        publishResources.add(res);
                    }
                }

                // Loop through all theme configurations, tocheck which ones are to be saved
                themeConfigs = p_engine.listThemeConfigs();
                for(loopCount = 0; loopCount < themeConfigs.length; loopCount ++){
                    currentThemeConfig = themeConfigs[loopCount];
                    if((currentThemeConfig.isChanged()) || (currentThemeConfig.isNew())){
                        configId = configController.validateConfigId(currentThemeConfig);
                        if(!configId.equals(currentThemeConfig.getId())){
                            currentThemeConfig.setId(configId);
                        }

                        if(currentThemeConfig.getResourceId() != null){
                            changedRes = new ArrayList<CmsResource>();
                            changedRes.add(this.writeThemeConfigResourceData(cmsObj, currentThemeConfig));
                        }else{
                            typeId = OpenCms.getResourceManager().getResourceType("theme_config").getTypeId();
                            resName = configController.composeResourceName(cmsObj);
                            currentRes = cmsObj.createResource(resName, typeId);
                            changedRes = configController.writeResourceData(cmsObj, currentRes, currentThemeConfig);
                        }
                        publishResources.addAll(changedRes);
                    }
                }

                // Publish all resources
                this.publishResources(cmsObj, publishResources);

                // Switch back to the offline project
                switchToProject(cmsObj, DefaultController.OFFLINE_PRJ);
                
                ThemeEngineConfigController.cachedEngine = p_engine;
            }
        }catch(JspException jspEx){
            throw new ThemeConfigException(jspEx);
        }catch(CmsException cmsEx){
            throw new ThemeConfigException(cmsEx);
        }
    }
    
    /**
     * Private helper method used to read XML contents from VFS and to transform them into an object representation of a
     * theme engine configuration.
     * @param p_contentBean Offers the access to XML contents to be loaded from VFS
     * @param p_container The container, which to get the content from.
     * @return The object representation of the theme configration, which was loaded from VFS
     * @throws JspException Will be thrown when some undelying operations fail.
     * @throws ThemeConfigException Wraps exceptions thrown by underlying operations.
     */
    private ThemeEngineConfig loadResourceData(CmsJspXmlContentBean p_contentBean, I_CmsXmlContentContainer p_container)
            throws JspException, ThemeConfigException{
        ThemeEngineConfig        result                   = new ThemeEngineConfig();
        String                   defaultThemeURI;
        String                   defaultThemeName         = "";
        CmsJspXmlContentBean     detaultThemeContentBean  = new CmsJspXmlContentBean(getPageContext(), getRequest(), getResponse());
        I_CmsXmlContentContainer defaultThemeContainer;
        ArrayList<ThemeConfig>   themeConfigs;
        Iterator<ThemeConfig>    configIt;
        ThemeConfig              currentTheme;
        
        // Read the config folder URI from backend and set it
        result.setConfigFolder(contentShow(p_contentBean, p_container, "config-folder", null));
        result.setRuntimeParameter(contentShow(p_contentBean , p_container, "runtime-parameter", null));
        
        // Get the id of the default theme configuration
        defaultThemeURI = this.contentShow(p_contentBean, p_container, "default-theme", null);
        defaultThemeContainer  = detaultThemeContentBean.contentload("singleFile", defaultThemeURI, false);
        if(defaultThemeContainer.hasMoreResources()){
            defaultThemeName = contentShow(detaultThemeContentBean , defaultThemeContainer, "name", null);
        }
        
        // Set the list of theme configs and the default theme config.
        themeConfigs = configController.listThemeConfigs();
        for(configIt = themeConfigs.iterator(); configIt.hasNext();){
            currentTheme = configIt.next();
            result.addThemeConfig(currentTheme);
            if(currentTheme.getName().equals(defaultThemeName)){
                result.setDefaultTheme(currentTheme);
            }
        }
        
        return result;
    }
    
    /**
     * Internal helper method, which takes over the object representation of a theme configuration, and writes it as 
     * XML content to the VFS.
     * @param p_cmsObj Provides access to the VFS resource, where the theme configration has to be stored in.
     * @param p_res Represents the resource, where the theme configuration has to be stored in.
     * @param p_config The object representation of the theme configuration, whose content has to be written to VFS.
     * @throws CmsException When one of the underlying VFS operations fails.
     * @throws ThemeConfigInitializationException Wraps other underlying exception, which may occurr during the 
     * execution of this method.
     */
    private void writeResourceData(CmsObject p_cmsObj, CmsResource p_res, ThemeEngineConfig p_config) throws CmsException, 
            ThemeConfigException, JspException{
        CmsFile                 file               = p_cmsObj.readFile(p_res);
        CmsXmlContent           fileContent        = CmsXmlContentFactory.unmarshal(p_cmsObj, file);
        Locale                  locale             = OpenCms.getLocaleManager().getDefaultLocale(p_cmsObj, p_res);
        String                  defaultConfigURI;
       
        // Lock the resource
        p_cmsObj.lockResource(p_res);

        // Write field values of the engine config
        defaultConfigURI = configController.getThemeURI(p_cmsObj, p_config.getDefaultTheme().getName());
        fileContent.getValue(FieldNames.DEFAULT_THEME, locale).setStringValue(p_cmsObj, defaultConfigURI);
        fileContent.getValue(FieldNames.CONFIG_FOLDER, locale).setStringValue(p_cmsObj, p_config.getConfigFolder());
        if(!fileContent.getValues(FieldNames.RUNTIME_PARAMETER, locale).isEmpty()){
            fileContent.removeValue(FieldNames.RUNTIME_PARAMETER, locale, 0);
        }
        if((p_config.getRuntimeParameter() != null) && (p_config.getRuntimeParameter().trim().length() > 0)){
            fileContent.addValue(p_cmsObj, FieldNames.RUNTIME_PARAMETER, locale, 0).setStringValue(p_cmsObj, p_config.getRuntimeParameter());
        }
        

        file.setContents(fileContent.marshal()); 
        p_cmsObj.writeFile(file);

        // Unlock the resource
        p_cmsObj.unlockResource(p_res);
    }
   
        
    /**
     * Private helper method, used to write the data of a theme engine configuration (including all single changed
     * theme configurations) to the VFS.
     * @param p_cmsObj Provides access to the VFS.
     * @param p_config The theme configuration to be saved to the VFS.
     * @return The CmsResource object representing the theme configuration at the VFS.
     * @throws ThemeConfigException Wrapping all exceptions which may occur, when some of the underlying calls fails.
     */
    private CmsResource writeThemeConfigResourceData(CmsObject p_cmsObj, ThemeConfig p_config) throws ThemeConfigException{
        String                   configId         = p_config.getResourceId();
        String                   configURI;
        CmsJspXmlContentBean     contentBean      = new CmsJspXmlContentBean(this.getPageContext(),getRequest(), getResponse());
        I_CmsXmlContentContainer contentContainer;
        CmsResource              res              = null;
        String                   currentResId;
        
        try{
            configURI = getConfigFolder();
            configURI  += "theme_%(number).conf|theme_config";
            contentContainer = contentBean.contentload("allInFolder", configURI, false);
            while((contentContainer.hasMoreResources()) && (res == null)){
                currentResId = contentContainer.getXmlDocument().getFile().getResourceId().getStringValue();
                if(configId.equals(currentResId)){
                    res = contentContainer.getXmlDocument().getFile();
                    p_cmsObj.lockResource(res);
                    configController.writeResourceData(p_cmsObj, res, p_config);
                    p_cmsObj.unlockResource(res);
                }
            }
            return res;
        }catch(JspException jspEx){
            throw new ThemeConfigException(jspEx);
        }catch(Exception ex){
            throw new ThemeConfigException(ex);
        }
    } 
}
