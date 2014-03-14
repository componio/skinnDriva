/*
 *  Document : ThemeEngineWidgetDialog.java
 *  Created on Di, Nov 15 2011, 21:15:16
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
package net.componio.opencms.modules.eight.skinndriva.rd.engine.view;

import  javax.servlet.jsp.PageContext;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;

import  org.opencms.workplace.CmsWidgetDialog;
import  org.opencms.workplace.CmsWorkplaceSettings;

import  net.componio.opencms.modules.eight.skinndriva.rd.engine.controller.ThemeFactory;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.controller.I_ThemeConfigController;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigException;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeConfig;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeDimensions;

/**
 * Base class for dialogs in the theme engine that use the OpenCms widgets without XML content. All functionalities needed
 * by all widget dialogs in the theme engine are subclassing this class. So the common functionalities needed by all
 * these classes have not to be reimplemented in each one of these classes.
 * @author Robert Diawara
 */
public abstract class ThemeEngineWidgetDialog extends CmsWidgetDialog{
    /** Holds the page context */
    private   PageContext             pageContext;
    /** Holds the servlet request for the current page */
    private   HttpServletRequest      request;      
    /** Holds the servlet response for the current page */
    private   HttpServletResponse     response; 
    /** Holds the theme config */
    private ThemeConfig             m_themeConfig;
    /** The config controller used to execute operation */
    private   I_ThemeConfigController m_configController; 

    
    
    /**
     * Public constructor with all JSP variables.
     * @param context The JSP page context.
     * @param req The HTTP servlet request
     * @param resp The HTTP servlet response
     */
    public ThemeEngineWidgetDialog(PageContext context, HttpServletRequest req, HttpServletResponse resp) {
        super(context, req, resp);
    }
    
    /**
     * This method will be called from the {@link ThemeDetailsDialog#initWorkplaceRequestValues(org.opencms.workplace.CmsWorkplaceSettings, 
     * javax.servlet.http.HttpServletRequest) initWorkplaceRequestValues} methods of the subclasses to assign the values to the
     * the JSP variable properties and load all needed theme configurations from the backend.
     * @param settings The workplace settings.
     * @param request The current HTTP servlet request.
     */
    protected void initPropertyValues(CmsWorkplaceSettings settings, HttpServletRequest request) {
        String  themeId = request.getParameter(ThemeList.PARAM_THEME_ID);
            
        this.request     = request;
        this.response    = getJsp().getResponse();
        this.pageContext = getJsp().getJspContext();
        
        // Create the config controller
        try{
            m_configController = ThemeFactory.getConfigController(getPageContext(), getRequest(), getResponse());
        }catch(ThemeConfigException themeConfigEx){
            throw new RuntimeException(themeConfigEx);
        }

        m_themeConfig = (ThemeConfig)getDialogObject();
        
        if(m_themeConfig == null){
            // Try to find the selected theme config.
            if(themeId != null){
                request.getSession().setAttribute(ThemeList.PARAM_THEME_ID, themeId);
            }else{
                themeId = (String)request.getSession().getAttribute(ThemeList.PARAM_THEME_ID);
            }
            if(themeId != null){
                try{
                    m_themeConfig = m_configController.loadThemeConfig(themeId);
                }catch(ThemeConfigException themeConfigEx){
                    throw new RuntimeException(themeConfigEx);
                }

            // If no selected theme config found, create a new one.
            }else{
                try{
                    m_themeConfig = new ThemeConfig();
                    m_themeConfig.setId("New Theme");
                    m_themeConfig.setUnit("px");
                    m_themeConfig.getDimensions().setBoxSpacing(10);
                    m_themeConfig.getDimensions().setLeftColumnInnerWidth(250);
                    m_themeConfig.getDimensions().setCenterColumnInnerWidth(800);
                    m_themeConfig.getDimensions().setRightColumnInnerWidth(250);
                    m_themeConfig.getDimensions().setHeaderInnerHeight(150);
                    m_themeConfig.getDimensions().setToolbarInnerHeight(100);
                    m_themeConfig.getDimensions().setFooterInnerHeight(100);
                }catch(ThemeConfigException themeConfigEx){
                    throw new RuntimeException(themeConfigEx);
                }
            }
        }
    }
    
    /**
     * Resets the dialog and clears all theme configurations, which were loaded from the backend before.
     */
    protected void clear(){
        m_themeConfig = null;
        m_configController = null;
    }
    
    /**
     * Saves all changes, which where done on the theme configurations, to the backend.
     * @throws ThemeConfigException To signal, that something went wrong, while trying to save data to the backend.
     */
    protected void saveData() throws ThemeConfigException{
        if((m_configController != null) && (m_themeConfig != null)){
            m_configController.saveThemeConfig(m_themeConfig);
        }
    }

    /**
     * Get the value of the JSP page context
     * @return the value of the JSP page context
     */
    public PageContext getPageContext() {
        return pageContext;
    }

    /**
     * Set the value of the JSP page context
     * @param pageContext new value of the JSP page context
     */
    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    /**
     * Get the value of the HTTP servlet request
     * @return the value of the HTTP servlet request
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Set the value of the HTTP servlet request
     * @param request new value of the HTTP servlet request
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Get the value of the HTTP servlet response
     * @return the value of the HTTP servlet response
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * Set the value of the HTTP servlet response
     * @param response new value of the HTTP servlet response
     */
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
    
    /**
     * Get the theme config, which was previously loaded from backend.
     * @return the theme config.
     */
    public ThemeConfig getThemeConfig() {
        return m_themeConfig;
    }

    /**
     * Gets the dimensions for the theme configuration, which was previously loaded from backend.
     * @return The dimensions for the theme configuration.
     * @throws ThemeConfigException To signal, that something went wrong wile retrieving the dimensions for the
     * theme configuration.
     */
    public ThemeDimensions getDimensions() throws ThemeConfigException{
        return getThemeConfig().getDimensions();
    }
}
