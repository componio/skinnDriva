/*
 *  Document : ThemeEngineMainDialog.java
 *  Created on Mo, Dez 26 2011, 16:33:29
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

import  net.componio.opencms.modules.eight.skinndriva.rd.Messages;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigException;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeEngineConfig;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeConfig;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.controller.ThemeFactory;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.controller.I_ThemeEngineConfigController;

import  javax.servlet.jsp.PageContext;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;

import  java.io.IOException;
import  java.util.Arrays;
import  java.util.ArrayList;
import  java.util.List;
import  javax.servlet.ServletException;

import  org.opencms.workplace.CmsWorkplaceSettings;
import  org.opencms.widgets.CmsSelectWidgetOption;
import  org.opencms.workplace.CmsWidgetDialogParameter;
import  org.opencms.widgets.CmsInputWidget;
import  org.opencms.widgets.CmsSelectWidget;


/**
 * Provides the widget dialog to edit the details of the theme engine
 * @author Robert Diawara
 */
public class ThemeEngineMainDialog extends ThemeEngineWidgetDialog{
    /** The array defining the pages making up the dialog. */
    public static final String[]      PAGE_ARRAY  = new String[]{"engine"};
    /** The key prefix for localized messages. Prevents duplicated keys. */
    public static final String        KEY_PREFIX  = "ThemeEngine";
    /** The list defining the pages making up the dialog. Built from PAGE_ARRAY*/
    public static final List<String>  PAGE_LIST   = Arrays.asList(PAGE_ARRAY);
    
    /** Holds the theme engine configuration. */
    private ThemeEngineConfig m_engineConfig;
    /** Holds the default theme to be assigned to the engine. */
    private String m_defaultTheme;
    /** Holds the runtime parameter to be assigned to the engine. */
    private String m_runtimeParameter;

    /**
     * Public constructor with all JSP variables.
     * @param context The JSP page context.
     * @param req The HTTP servlet request
     * @param resp The HTTP servlet response
     */
    public ThemeEngineMainDialog(PageContext context, HttpServletRequest req, HttpServletResponse resp) {
        super(context, req, resp);
    }

    
    /**
     * Get the value of the default theme
     * @return The value of the default theme
     */
    public String getDefaultTheme() {
        return m_defaultTheme;
    }

    /**
     * Set the value of the default theme
     * @param defaultTheme new value of the default theme
     */
    public void setDefaultTheme(String defaultTheme) {
        this.m_defaultTheme = defaultTheme;
    }

    /**
     * Get the value of the runtime parameter of the theme engine
     * @return the value of the runtime parameter of the theme engine
     */
    public String getRuntimeParameter() {
        return m_runtimeParameter;
    }

    /**
     * Set the value of the runtime parameter of the theme engine
     * @param runtimeParameter new value of the runtime parameter of the theme engine
     */
    public void setRuntimeParameter(String runtimeParameter) {
        this.m_runtimeParameter = runtimeParameter;
    }

    /**
     * First calls {@link ThemeEngineWidgetDialog#initPropertyValues(CmsWorkplaceSettings, HttpServletRequest) 
     * ThemeEngineWidgetDialog.initPropertyValues()} to load the needed theme configurations from backend, then
     * analyzes the request for workplace parameters and adjusts the workplace settings accordingly.
     * @param settings The workplace settings
     * @param request The current HTTP servlet request
     */
    @Override
    protected void initWorkplaceRequestValues(CmsWorkplaceSettings settings, HttpServletRequest request) {
        I_ThemeEngineConfigController engineController = null;
        
        // set the dialog message prefix
        setKeyPrefix(KEY_PREFIX);
        initPropertyValues(settings, request);
        
        // Load the theme engine settings
        try{
            engineController = ThemeFactory.getEngineConfigController(getPageContext(), getRequest(), getResponse());
            m_engineConfig = engineController.loadThemeEngine();
            m_defaultTheme = m_engineConfig.getDefaultTheme() != null ? m_engineConfig.getDefaultTheme().getName() : "";
            m_runtimeParameter = m_engineConfig.getRuntimeParameter() != null ? m_engineConfig.getRuntimeParameter() : "";
        }catch(ThemeConfigException themeConfigEx){
            throw new RuntimeException(themeConfigEx);
        }
        
        
        super.initWorkplaceRequestValues(settings, request);
        
        // save the current state of the job
        setDialogObject(getThemeConfig());
    }

    /**
     * Commits the edited object after pressing the "OK" button.
     * @throws IOException In case of errors forwarding to the required result page.
     * @throws ServletException In case of errors forwarding to the required result page.
     */
    @Override
    public void actionCommit() throws IOException, ServletException {
        ThemeConfig[]                 themes           = m_engineConfig.listThemeConfigs();
        int                           loopCount;
        I_ThemeEngineConfigController engineController = null;
        
        // Copy values to the engine configuration
        m_engineConfig.setDefaultTheme(null);
        if(themes != null){
            for(loopCount = 0; loopCount < themes.length; loopCount ++){
                if(themes[loopCount].getName().equals(m_defaultTheme)){
                    m_engineConfig.setDefaultTheme(themes[loopCount]);
                }
            }
        }
        m_engineConfig.setRuntimeParameter(m_runtimeParameter);
        
        // Save theme engine settings
        try{
            engineController = ThemeFactory.getEngineConfigController(getPageContext(), getRequest(), getResponse());
            engineController.saveThemeEngine(m_engineConfig);
        }catch(ThemeConfigException themeConfigEx){
            throw new RuntimeException(themeConfigEx);
        }
    }

    /**
     * Defines the widgets to be used within the dialog.
     */
    @Override
    protected void defineWidgets() {
       addWidget(new CmsWidgetDialogParameter(this,"defaultTheme", PAGE_ARRAY[0], new CmsSelectWidget(getThemeWidgetConfiguration())));
       addWidget(new CmsWidgetDialogParameter(this,"runtimeParameter", "", PAGE_ARRAY[0], new CmsInputWidget(), 0, 1));
    }

    /**
     * Returns the allowed pages for this dialog.
     * @return The allowed pages for this dialog.
     */
    @Override
    protected String[] getPageArray() {
        return PAGE_ARRAY;
    }
    
    /**
     * Initializes the message object. By default the CmsWorkplaceMessages are initialized. 
     * Overrides the method <code>initMessages()</code> of the superclass for setting the needed bundles, using the 
     * <code>addMessages(String)</code> method.
     */
    @Override
    protected void initMessages() {
        // add default resource bundles
        addMessages(Messages.get().getBundleName());
        super.initMessages();
    }
    

    /**
     * Creates all select widget options needed for the &quot;Default Theme&quot; select widget.
     * @return The select widget options.
      */
    private List<CmsSelectWidgetOption> getThemeWidgetConfiguration(){
        ThemeConfig[]               themes    = m_engineConfig.listThemeConfigs();
        List<CmsSelectWidgetOption> result    = new ArrayList<CmsSelectWidgetOption>();
        int                         loopCount;
        boolean                     isDefault;
        
        if(themes != null){
            isDefault = true;
            for(loopCount = 0; loopCount < themes.length; loopCount ++){
                result.add(new CmsSelectWidgetOption(themes[loopCount].getName(), isDefault));
                isDefault = false;
            }
        }
        return result;
    }
}
