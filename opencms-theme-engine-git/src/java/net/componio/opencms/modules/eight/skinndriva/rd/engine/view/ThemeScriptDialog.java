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
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ScriptReference;
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
import  org.opencms.widgets.CmsSelectWidgetOption;
import  org.opencms.workplace.CmsWidgetDialogParameter;
import  org.opencms.widgets.CmsVfsFileWidget;
import  org.opencms.widgets.CmsInputWidget;
import  org.opencms.widgets.CmsSelectWidget;


/**
 * Provides the widget dialog to edit a script associated to a theme
 * @author Robert Diawara
 */
public class ThemeScriptDialog extends ThemeEngineWidgetDialog{
    /** The array defining the pages making up the dialog. */
    public static final String[]      PAGE_ARRAY  = new String[]{"script"};
    /** The key prefix for localized messages. Prevents duplicated keys. */
    public static final String        KEY_PREFIX  = "ThemeScript";
    /** The list defining the pages making up the dialog. Built from PAGE_ARRAY*/
    public static final List<String>  PAGE_LIST   = Arrays.asList(PAGE_ARRAY);
    
    /** Constant for the script visibility, defining that the script is available in all projects. */
    public static final String VISIBILITY_ALL     = "all";
    /** Constant for the script visibility, defining that the script is available only in the offline project. */
    public static final String VISIBILITY_OFFLINE = "offline";
    /** Constant for the script visibility, defining that the script is available only in the online project. */
    public static final String VISIBILITY_ONLINE  = "online";
    
    /** Holds the value of the property scriptReference. */
    private ScriptReference m_scriptReference;
    /** Holds the value of the property scriptURI. */
    private String          m_scriptURI;
    /** Holds the value of the property scriptVisiblility. */
    private int             m_scriptVisibility;
    /** Holds the value of the property scriptParameters. */
    private List<String>    m_scriptParameters;
    /** Holds the value of the property scriptGroup. */
    private String          m_scriptGroup;
    
    /**
     * Public constructor with all JSP variables.
     * @param context The JSP page context.
     * @param req The HTTP servlet request
     * @param resp The HTTP servlet response
     */
    public ThemeScriptDialog(PageContext context, HttpServletRequest req, HttpServletResponse resp) {
        super(context, req, resp);
    }

    /**
     * Get the script to be edited in the dialog.
     * @return The script to be edited in the dialog.
     */
    public ScriptReference getScriptReference() {
        return m_scriptReference;
    }

    /**
     * Get the URI of the script to be edited in the dialog.
     * @return The URI of the script to be edited in the dialog.
     */
    public String getScriptURI() {
        return m_scriptURI;
    }

    /**
     * Set the URI of the script.
     * @param scriptURI The new URI of the script
     */
    public void setScriptURI(String scriptURI) {
        this.m_scriptURI = scriptURI;
    }

    /**
     * Get the visibility of the script to be edited in the dialog.
     * @return The visibility of the script to be edited in the dialog.
     */
    public String getScriptVisibility() {
        String result = null;
        switch(m_scriptVisibility){
            case  ScriptReference.VISIBILITY_ALL :
                result = ThemeScriptDialog.VISIBILITY_ALL;
                break;
            case ScriptReference.VISIBILITY_OFFLINE :
                result = ThemeScriptDialog.VISIBILITY_OFFLINE;
                break;
            case ScriptReference.VISIBILITY_ONLINE :
                result = ThemeScriptDialog.VISIBILITY_ONLINE;
                break;
                
        }
        return result;
    }

    /**
     * Set the visibility of the script.
     * @param scriptVisibility The new visibility of the script
     */
    public void setScriptVisibility(String scriptVisibility) {
        if(ThemeScriptDialog.VISIBILITY_ALL.equals(scriptVisibility)){
            this.m_scriptVisibility = ScriptReference.VISIBILITY_ALL;
        }else if(ThemeScriptDialog.VISIBILITY_OFFLINE.equals(scriptVisibility)){
            this.m_scriptVisibility = ScriptReference.VISIBILITY_OFFLINE;
        }else if(ThemeScriptDialog.VISIBILITY_ONLINE.equals(scriptVisibility)){
            this.m_scriptVisibility = ScriptReference.VISIBILITY_ONLINE;
        }
    }

    /**
     * Get the value of scriptParameters
     * @return the value of scriptParameters
     */
    public List<String> getScriptParameters() {
        return m_scriptParameters;
    }

    /**
     * Set the value of m_scriptParameters
     * @param scriptParameters new value of scriptParameters
     */
    public void setScriptParameters(List<String> scriptParameters) {
        this.m_scriptParameters = scriptParameters;
    }

    /**
     * Get the value of scriptGroup
     * @return the value of scriptGroup
     */
    public String getScriptGroup() {
        return m_scriptGroup;
    }

    /**
     * Set the value of scriptGroup
     * @param scriptGroup new value of scriptGroup
     */
    public void setScriptGroup(String scriptGroup) {
        this.m_scriptGroup = scriptGroup;
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
        String scriptURI = request.getParameter(ThemeList.PARAM_SCRIPT_URI);
        
        // set the dialog message prefix
        setKeyPrefix(KEY_PREFIX);
        initPropertyValues(settings, request);
        
        // Try to get the script URI
        if(scriptURI != null){
            request.getSession().setAttribute(ThemeList.PARAM_SCRIPT_URI, scriptURI);
        }else{
            scriptURI = (String)request.getSession().getAttribute(ThemeList.PARAM_SCRIPT_URI);
        }
        
        // If a script URI is existing, get the full script reference
        if(scriptURI != null){
            m_scriptReference = getThemeConfig().getScriptReference(scriptURI);
            m_scriptURI = m_scriptReference.getScriptURI();
            m_scriptVisibility = m_scriptReference.getScriptVisibility();
            m_scriptGroup = m_scriptReference.getGroup();
            m_scriptParameters = m_scriptReference.getParameters() != null ? m_scriptReference.getParameters() : new ArrayList<String>();
        }else{
            m_scriptReference = null;
            m_scriptURI = "";
            m_scriptVisibility = ScriptReference.VISIBILITY_ALL;
            m_scriptGroup = null;
            m_scriptParameters = new ArrayList<String>();
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
        ThemeConfig  themeConfig;
        
        try{
            if(m_scriptReference != null){
                m_scriptReference.setScriptURI(m_scriptURI);
                m_scriptReference.setScriptVisibility(m_scriptVisibility);
                m_scriptReference.setGroup(m_scriptGroup);
                m_scriptReference.setParameters(m_scriptParameters);
            }else{
                if(m_scriptURI.trim().length() > 0){
                    m_scriptReference = new ScriptReference();
                    m_scriptReference.setScriptURI(m_scriptURI);
                    m_scriptReference.setScriptVisibility(m_scriptVisibility);
                    m_scriptReference.setGroup(m_scriptGroup);
                    m_scriptReference.setParameters(m_scriptParameters);
                    getThemeConfig().addScriptReference(m_scriptReference);
                }
            }
            saveData();
            themeConfig = getThemeConfig();
            if(themeConfig != null){
                getRequest().setAttribute(ThemeList.PARAM_THEME, themeConfig);
            }
            getSettings().setDialogObject(null);
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
       addWidget(new CmsWidgetDialogParameter(this, "scriptURI", PAGE_ARRAY[0], 
               new CmsVfsFileWidget()));
       addWidget(new CmsWidgetDialogParameter(this, "scriptVisibility", "", PAGE_ARRAY[0], 
               new CmsSelectWidget(getVisibilityWidgetConfiguration()), 0, 1));
       addWidget(new CmsWidgetDialogParameter(this, "scriptGroup", "", PAGE_ARRAY[0], 
               new CmsVfsFileWidget(), 0, 1));
       addWidget(new CmsWidgetDialogParameter(this, "scriptParameters", PAGE_ARRAY[0], 
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

    /**
     * Creates all select widget options needed for the visibility select widget.
     * @return The select widget options.
     */
    private List<CmsSelectWidgetOption> getVisibilityWidgetConfiguration(){
        List<CmsSelectWidgetOption> result = new ArrayList<CmsSelectWidgetOption>();
        
        result.add(new CmsSelectWidgetOption(ThemeScriptDialog.VISIBILITY_OFFLINE, false));
        result.add(new CmsSelectWidgetOption(ThemeScriptDialog.VISIBILITY_ONLINE, false));
        result.add(new CmsSelectWidgetOption(ThemeScriptDialog.VISIBILITY_ALL, true));
        return result;
    }
}
