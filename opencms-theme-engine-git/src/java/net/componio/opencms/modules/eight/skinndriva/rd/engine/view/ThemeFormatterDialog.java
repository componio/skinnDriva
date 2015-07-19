/*
 *  Document : ThemeFormatterDialog.java
 *  Created on Mi, Okt 03 2012, 19:55:34
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
package net.componio.opencms.modules.eight.skinndriva.rd.engine.view;

import  java.io.IOException;
import  java.util.Arrays;
import  java.util.List;

import  net.componio.opencms.modules.eight.skinndriva.rd.Messages;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigException;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeFormatter;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeConfig;

import  javax.servlet.ServletException;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;
import  javax.servlet.jsp.PageContext;

import  org.opencms.widgets.CmsCheckboxWidget;
import  org.opencms.widgets.CmsInputWidget;
import  org.opencms.widgets.CmsTypeComboWidget;
import  org.opencms.widgets.CmsVfsFileWidget;
import  org.opencms.workplace.CmsWidgetDialogParameter;
import  org.opencms.workplace.CmsWorkplaceSettings;

/**
 *
 * @author Robert Diawara
 */
public class ThemeFormatterDialog extends ThemeEngineWidgetDialog{
    /** The array defining the pages making up the dialog. */
    public static final String[]      PAGE_ARRAY             = new String[]{"formatter"};
    /** The key prefix for localized messages. Prevents duplicated keys. */
    public static final String        KEY_PREFIX             = "ThemeFormatter";
    /** The list defining the pages making up the dialog. Built from PAGE_ARRAY*/
    public static final List<String>  PAGE_LIST              = Arrays.asList(PAGE_ARRAY);
    
    /** Path constant for the formatter key URL parameter. */
    public static final String PARAM_FORMATTER               = "formatter";
    
    /** Holds the value of the property <code>formatter</code> */
    private ThemeFormatter m_formatter;
    /** Holds the value of the property <code>resourceType</code> */
    private String         m_resourceType;
    /** Holds the value of the property <code>containerType</code> */
    private String         m_containerType;
    /** Holds the value of the property <code>formatterJsp</code> */
    private String         m_formatterJsp;
    /** Holds the value of the property <code>minWidth</code> */
    private int            m_minWidth;
    /** Holds the value of the property <code>maxWidth</code> */
    private int            m_maxWidth;
    /** Holds the value of the property <code>usedForPreview</code> */
    private boolean        m_usedForPreview;

    /**
     * Get the value of resourceType
     * @return the value of resourceType
     */
    public String getResourceType() {
        return m_resourceType;
    }

    /**
     * Set the value of resourceType
     * @param resourceType new value of resourceType
     */
    public void setResourceType(String resourceType) {
        this.m_resourceType = resourceType;
    }


    /**
     * Get the value of containerType
     * @return the value of containerType
     */
    public String getContainerType() {
        return m_containerType;
    }

    /**
     * Set the value of containerType
     * @param containerType new value of containerType
     */
    public void setContainerType(String containerType) {
        this.m_containerType = containerType;
    }


    /**
     * Get the value of formatterJsp
     * @return the value of formatterJsp
     */
    public String getFormatterJsp() {
        return m_formatterJsp;
    }

    /**
     * Set the value of formatterJsp
     * @param formatterJsp new value of formatterJsp
     */
    public void setFormatterJsp(String formatterJsp) {
        this.m_formatterJsp = formatterJsp;
    }

    /**
     * Get the value of minWidth
     * @return the value of minWidth
     */
    public String getMinWidth() {
        return m_minWidth > -1 ? String.valueOf(m_minWidth) : "";
    }

    /**
     * Set the value of minWidth
     * @param minWidth new value of minWidth
     */
    public void setMinWidth(String minWidth) {
        this.m_minWidth = (minWidth != null) && (minWidth.trim().length() > 0) ? Integer.parseInt(minWidth) : -1 ;
    }

    /**
     * Get the value of maxWidth
     * @return the value of maxWidth
     */
    public String getMaxWidth() {
        return m_maxWidth > -1 ? String.valueOf(m_maxWidth) : "";
    }

    /**
     * Set the value of maxWidth
     * @param maxWidth new value of maxWidth
     */
    public void setMaxWidth(String maxWidth) {
        this.m_maxWidth = (maxWidth != null) && (maxWidth.trim().length() > 0) ? Integer.parseInt(maxWidth) : -1 ;
    }

    /**
     * Get the value of usedForPreview
     * @return the value of usedForPreview
     */
    public boolean isUsedForPreview() {
        return m_usedForPreview;
    }

    /**
     * Set the value of usedForPreview
     * @param usedForPreview new value of usedForPreview
     */
    public void setUsedForPreview(boolean usedForPreview) {
        this.m_usedForPreview = usedForPreview;
    }

    /**
     * Public constructor with all JSP variables.
     * @param context The JSP page context.
     * @param req The HTTP servlet request
     * @param resp The HTTP servlet response
     */
    public ThemeFormatterDialog(PageContext context, HttpServletRequest req, HttpServletResponse resp) {
        super(context, req, resp);
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
        String          formatterKey;
        ThemeConfig     themeCfg;
        int             tmpMaxWidth;
        boolean         tmpUsedForPreview;
        
        // set the dialog message prefix
        setKeyPrefix(KEY_PREFIX);
        initPropertyValues(settings, request);
        m_formatter = (ThemeFormatter)request.getSession().getAttribute(ThemeFormatterDialog.PARAM_FORMATTER);
        
        if(m_formatter != null){
            m_resourceType = m_formatter.getResourceType();
            m_containerType = m_formatter.getContainerType();
            m_formatterJsp = m_formatter.getJsp();
            m_minWidth = m_formatter.getMinWidth();
            m_maxWidth = m_formatter.getMaxWidth();
            m_usedForPreview = m_formatter.isUsedForPreview();
        }else{
            formatterKey = request.getParameter(ThemeFormatterList.PARAM_FORMATTER_KEY);

            // If a script URI is existing, get the full script reference
            if(formatterKey != null){
                themeCfg = getThemeConfig();
                
                m_formatter = themeCfg.getFormatter(formatterKey);
                
                m_resourceType = m_formatter.getResourceType();
                m_containerType = m_formatter.getContainerType();
                m_formatterJsp = m_formatter.getJsp();
                m_minWidth = m_formatter.getMinWidth();
                m_maxWidth = m_formatter.getMaxWidth();
                m_usedForPreview = m_formatter.isUsedForPreview();
            }else{
                m_resourceType = null;
                m_containerType = null;
                m_formatterJsp = null;
                m_minWidth = -1;
                m_maxWidth = -1;
                m_usedForPreview = false;
            }
        }
        
        super.initWorkplaceRequestValues(settings, request);
        
        // save the current state of the job
        setDialogObject(getThemeConfig());
        request.getSession().setAttribute(ThemeFormatterDialog.PARAM_FORMATTER, m_formatter);
    }

    /**
     * Commits the edited object after pressing the "OK" button.
     * @throws IOException In case of errors forwarding to the required result page.
     * @throws ServletException In case of errors forwarding to the required result page.
     */
    @Override
    public void actionCommit() throws IOException, ServletException {
        ThemeConfig  themeConfig;
        
        try{
            themeConfig = getThemeConfig();
            if(m_formatter != null){
                m_formatter.setResourceType(m_resourceType);
                m_formatter.setContainerType(m_containerType);
                m_formatter.setJsp(m_formatterJsp);
                m_formatter.setMinWidth(m_minWidth);
                m_formatter.setMaxWidth(m_maxWidth);
                m_formatter.setUsedForPreview(m_usedForPreview);
            }else{
                if((m_resourceType != null) && (m_containerType != null) && 
                        (m_resourceType.trim().length() > 0) && (m_containerType.trim().length() > 0)) {
                    m_formatter = new ThemeFormatter(themeConfig);
                    m_formatter.setResourceType(m_resourceType);
                    m_formatter.setContainerType(m_containerType);
                    m_formatter.setJsp(m_formatterJsp);
                    m_formatter.setMinWidth(m_minWidth);
                    m_formatter.setMaxWidth(m_maxWidth);
                    m_formatter.setUsedForPreview(m_usedForPreview);
                    //getThemeConfig().addFormatter(m_formatter);
                }
            }
            saveData();
            if(themeConfig != null){
                getRequest().setAttribute(ThemeList.PARAM_THEME, themeConfig);
            }
            getSettings().setDialogObject(null);
            getRequest().getSession().removeAttribute(ThemeFormatterDialog.PARAM_FORMATTER);
            clear();
        }catch(ThemeConfigException ex){
            throw new ServletException(ex);
        }
    }

    /**
     * Defines the widgets to be used within the dialog.
     */
    @Override
    protected void defineWidgets() {
        addWidget(new CmsWidgetDialogParameter(this,"resourceType", PAGE_ARRAY[0], new CmsTypeComboWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"containerType", PAGE_ARRAY[0], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"formatterJsp", PAGE_ARRAY[0], new CmsVfsFileWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"minWidth", "", PAGE_ARRAY[0], new CmsInputWidget(), 0, 1));
        addWidget(new CmsWidgetDialogParameter(this,"maxWidth", "", PAGE_ARRAY[0], new CmsInputWidget(), 0, 1));
        addWidget(new CmsWidgetDialogParameter(this,"usedForPreview", "", PAGE_ARRAY[0], new CmsCheckboxWidget(), 1, 1));
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
}
