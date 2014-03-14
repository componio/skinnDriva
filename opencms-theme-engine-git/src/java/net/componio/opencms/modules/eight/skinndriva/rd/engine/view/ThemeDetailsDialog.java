/*
 *  Document : ThemeDetailsDialog.java
 *  Created on Di, Nov 15 2011, 21:31:25
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
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeConfig;

import  java.io.IOException;
import  java.util.Arrays;
import  java.util.List;

import  javax.servlet.ServletException;
import  javax.servlet.jsp.PageContext;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;

import  org.opencms.workplace.CmsWidgetDialogParameter;
import  org.opencms.workplace.CmsWorkplaceSettings;
import  org.opencms.widgets.CmsInputWidget;
import  org.opencms.widgets.CmsCheckboxWidget;
import  org.opencms.widgets.CmsVfsFileWidget;

/**
 * Provides the widget dialog to edit the details for a theme
 * @author Robert Diawara
 */
public class ThemeDetailsDialog extends ThemeEngineWidgetDialog{
    /** The array defining the pages making up the dialog. */
    public static final String[]      PAGE_ARRAY = new String[]{"details"};
    /** The key prefix for localized messages. Prevents duplicated keys. */
    public static final String        KEY_PREFIX = "ThemeDetails";
    /** The list defining the pages making up the dialog. Built from PAGE_ARRAY*/
    public static final List<String>  PAGE_LIST  = Arrays.asList(PAGE_ARRAY);
    
    /**
     * Public constructor with all JSP variables.
     * @param context The JSP page context.
     * @param req The HTTP servlet request
     * @param resp The HTTP servlet response
     */
    public ThemeDetailsDialog(PageContext context, HttpServletRequest req, HttpServletResponse resp) {
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
        // set the dialog message prefix
        setKeyPrefix(KEY_PREFIX);
        initPropertyValues(settings, request);
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
        String themeId;
        try{
            themeId = getThemeConfig().getId();
            if((themeId == null) || (themeId.trim().length() == 0)){
                getThemeConfig().setId("New Theme");
            }
            saveData();
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
        ThemeConfig config = getThemeConfig();
        
        addWidget(new CmsWidgetDialogParameter(config,"name", PAGE_ARRAY[0], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(config,"favIcon", "", PAGE_ARRAY[0], new CmsVfsFileWidget(), 0, 1));
        addWidget(new CmsWidgetDialogParameter(config,"showLeftHandBar", PAGE_ARRAY[0], new CmsCheckboxWidget()));
        addWidget(new CmsWidgetDialogParameter(config,"showRightHandBar", PAGE_ARRAY[0], new CmsCheckboxWidget()));
        addWidget(new CmsWidgetDialogParameter(config,"showToolBar", PAGE_ARRAY[0], new CmsCheckboxWidget()));
        addWidget(new CmsWidgetDialogParameter(config,"mainTemplate", "", PAGE_ARRAY[0], new CmsVfsFileWidget(), 0, 1));
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
