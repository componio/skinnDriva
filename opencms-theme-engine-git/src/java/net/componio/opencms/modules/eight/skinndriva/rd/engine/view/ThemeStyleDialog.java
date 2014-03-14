/*
 *  Document : ThemeScriptDialog.java
 *  Created on Di, Nov 15 2011, 21:41:11
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
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.StyleReference;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeConfig;

import  java.io.IOException;
import  java.util.Arrays;
import  java.util.List;
import  java.util.ArrayList;

import  javax.servlet.ServletException;
import  javax.servlet.jsp.PageContext;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;

import  org.opencms.workplace.CmsWorkplaceSettings;
import  org.opencms.workplace.CmsWidgetDialogParameter;
import  org.opencms.widgets.CmsVfsFileWidget;
import  org.opencms.widgets.CmsInputWidget;


/**
 * Provides the widget dialog to edit a script associated to a theme
 * @author Robert Diawara
 */
public class ThemeStyleDialog extends ThemeEngineWidgetDialog{
    /** The array defining the pages making up the dialog. */
    public static final String[]      PAGE_ARRAY             = new String[]{"style"};
    /** The key prefix for localized messages. Prevents duplicated keys. */
    public static final String        KEY_PREFIX             = "ThemeStyle";
    /** The list defining the pages making up the dialog. Built from PAGE_ARRAY*/
    public static final List<String>  PAGE_LIST              = Arrays.asList(PAGE_ARRAY);
    /** The script reference to be held by the session. */
    public static final String        PARAM_STYLE_REFERENCE  = "style_reference";
    
    /** Holds the value of the property scriptURI. */
    private String          m_styleURI;
    /** Holds the value of the property styleGroup. */
    private String          m_styleGroup;
    /** Holds the style reference being edited. */
    private StyleReference  m_styleReference;
    /** Holds the value of the property styleMedia. */
    private String          m_styleMedia;
    /** Holds the value of the property styleUserAgents. */
    private List<String>    m_styleUserAgents;
    
    /**
     * Public constructor with all JSP variables.
     * @param context The JSP page context.
     * @param req The HTTP servlet request
     * @param resp The HTTP servlet response
     */
    public ThemeStyleDialog(PageContext context, HttpServletRequest req, HttpServletResponse resp) {
        super(context, req, resp);
    }

    /**
     * Get the URI of the script to be edited in the dialog.
     * @return The URI of the script to be edited in the dialog.
     */
    public String getStyleURI() {
        return m_styleURI;
    }

    /**
     * Set the URI of the script.
     * @param scriptURI The new URI of the script
     */
    public void setStyleURI(String scriptURI) {
        this.m_styleURI = scriptURI;
    }

    /**
     * Get the value of scriptGroup
     * @return the value of scriptGroup
     */
    public String getStyleGroup() {
        return m_styleGroup;
    }

    /**
     * Set the value of scriptGroup
     * @param scriptGroup new value of scriptGroup
     */
    public void setStyleGroup(String scriptGroup) {
        this.m_styleGroup = scriptGroup;
    }

    /**
     * Get the value of styleMedia
     * @return the value of styleMedia
     */
    public String getStyleMedia() {
        return m_styleMedia;
    }

    /**
     * Set the value of styleMedia
     * @param styleMedia new value of styleMedia
     */
    public void setStyleMedia(String styleMedia) {
        this.m_styleMedia = styleMedia;
    }

    /**
     * Get the value of styleUserAgents
     * @return the value of styleUserAgents
     */
    public List<String> getStyleUserAgents() {
        return m_styleUserAgents;
    }

    /**
     * Set the value of styleUserAgents
     * @param styleUserAgents new value of styleUserAgents
     */
    public void setStyleUserAgents(List<String> styleUserAgents) {
        this.m_styleUserAgents = styleUserAgents;
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
        String         styleURI;
        ThemeConfig    themeCfg;
        
        // set the dialog message prefix
        setKeyPrefix(KEY_PREFIX);
        initPropertyValues(settings, request);
        m_styleReference = (StyleReference)request.getSession().getAttribute(ThemeStyleDialog.PARAM_STYLE_REFERENCE);
        
        if(m_styleReference != null){
            m_styleURI = m_styleReference.getURI();
            m_styleGroup = m_styleReference.getGroup();
            m_styleMedia = m_styleReference.getMedia();
            m_styleUserAgents = m_styleReference.getUserAgents();
        }else{
            styleURI       = request.getParameter(StylesList.PARAM_STYLE_URI);

            // If a script URI is existing, get the full script reference
            if(styleURI != null){
                themeCfg = getThemeConfig();
                m_styleReference = themeCfg.getStyle(styleURI);
                m_styleURI = m_styleReference.getURI();
                m_styleGroup = m_styleReference.getGroup();
                m_styleMedia = m_styleReference.getMedia();
                m_styleUserAgents = m_styleReference.getUserAgents();
            }else{
                m_styleReference = null;
                m_styleURI = "";
                m_styleGroup = null;
                m_styleMedia = null;
                m_styleUserAgents = new ArrayList();
            }
        }
        
        super.initWorkplaceRequestValues(settings, request);
        
        // save the current state of the job
        setDialogObject(getThemeConfig());
        request.getSession().setAttribute(ThemeStyleDialog.PARAM_STYLE_REFERENCE, m_styleReference);
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
            if(m_styleReference != null){
                m_styleReference.setURI(m_styleURI);
                m_styleReference.setGroup(m_styleGroup);
                m_styleReference.setMedia(m_styleMedia);
                m_styleReference.setUserAgents(m_styleUserAgents);
            }else{
                if(m_styleURI.trim().length() > 0){
                    m_styleReference = new StyleReference();
                    m_styleReference.setURI(m_styleURI);
                    m_styleReference.setGroup(m_styleGroup);
                    m_styleReference.setMedia(m_styleMedia);
                    m_styleReference.setUserAgents(m_styleUserAgents);
                    getThemeConfig().addStyle(m_styleReference.getURI(), m_styleReference.getGroup());
                }
            }
            saveData();
            themeConfig = getThemeConfig();
            if(themeConfig != null){
                getRequest().setAttribute(ThemeList.PARAM_THEME, themeConfig);
            }
            getSettings().setDialogObject(null);
            getRequest().getSession().removeAttribute(ThemeStyleDialog.PARAM_STYLE_REFERENCE);
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
       addWidget(new CmsWidgetDialogParameter(this, "styleURI", PAGE_ARRAY[0], 
               new CmsVfsFileWidget()));
       addWidget(new CmsWidgetDialogParameter(this, "styleGroup", "", PAGE_ARRAY[0], 
               new CmsInputWidget(), 0, 1));
       addWidget(new CmsWidgetDialogParameter(this, "styleMedia", "", PAGE_ARRAY[0], 
               new CmsInputWidget(), 0, 1));
       addWidget(new CmsWidgetDialogParameter(this, "styleUserAgents", PAGE_ARRAY[0], 
               new CmsInputWidget()));
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
