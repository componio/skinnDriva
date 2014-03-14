/*
 *  Document : ThemeGridDialog.java
 *  Created on So, Feb 26 2012, 10:19:48
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

import  net.componio.opencms.modules.eight.skinndriva.rd.Messages;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.GridModel;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeConfig;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigException;

import  java.io.IOException;
import  java.util.Arrays;
import  java.util.ArrayList;
import  java.util.List;

import  javax.servlet.ServletException;
import  javax.servlet.jsp.PageContext;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;

import  org.opencms.workplace.CmsWorkplaceSettings;
import  org.opencms.widgets.CmsInputWidget;
import  org.opencms.widgets.CmsSelectWidget;
import  org.opencms.widgets.CmsVfsFileWidget;
import  org.opencms.widgets.CmsSelectWidgetOption;
import  org.opencms.workplace.CmsWidgetDialogParameter;




/**
 *
 * @author Robert Diawara
 */
public class ThemeGridDialog extends ThemeEngineWidgetDialog{
    /** The array defining the pages making up the dialog. */
    public static final String[]      PAGE_ARRAY = new String[]{"grid"};
    /** The key prefix for localized messages. Prevents duplicated keys. */
    public static final String        KEY_PREFIX = "ThemeGrid";
    /** The list defining the pages making up the dialog. Built from PAGE_ARRAY*/
    public static final List<String>  PAGE_LIST  = Arrays.asList(PAGE_ARRAY);
    
    /** Holds the value of the property headerHeight. */
    private int m_headerHeight;
    /** Holds the value of the property footerHeight. */
    private int m_footerHeight;
    /** Holds the value of the property toolbarHeight. */
    private int m_toolbarHeight;
    /** Holds the value of the property columnCount. */
    private int m_columnCount;
    /** Holds the value of the property columnWidth. */
    private int m_columnWidth;
    /** Holds the value of the property orientation. */
    private String m_orientation;
    /** Holds the value of the property topMargin. */
    private int m_topMargin;
    /** Holds the value of the property rightMargin. */
    private int m_rightMargin;
    /** Holds the value of the property bottomMargin. */
    private int m_bottomMargin;
    /** Holds the value of the property leftMargin. */
    private int m_leftMargin;
    /** Holds the value of the property cssFile. */
    private String m_cssFile;

    /**
     * Public constructor with all JSP variables.
     * @param context The JSP page context.
     * @param req The HTTP servlet request
     * @param resp The HTTP servlet response
     */
    public ThemeGridDialog(PageContext context, HttpServletRequest req, HttpServletResponse resp) {
        super(context, req, resp);
    }

    /**
     * Get the value of headerHeight
     * @return the value of headerHeight
     */
    public int getHeaderHeight() {
        return m_headerHeight;
    }

    /**
     * Set the value of headerHeight
     * @param headerHeight new value of headerHeight
     */
    public void setHeaderHeight(int headerHeight) {
        this.m_headerHeight = headerHeight;
    }

    /**
     * Get the value of footerHeight
     * @return the value of footerHeight
     */
    public int getFooterHeight() {
        return m_footerHeight;
    }

    /**
     * Set the value of footerHeight
     * @param footerHeight new value of footerHeight
     */
    public void setFooterHeight(int footerHeight) {
        this.m_footerHeight = footerHeight;
    }

    /**
     * Get the value of toolbarHeight
     * @return the value of toolbarHeight
     */
    public int getToolbarHeight() {
        return m_toolbarHeight;
    }

    /**
     * Set the value of toolbarHeight
     * @param toolbarHeight new value of toolbarHeight
     */
    public void setToolbarHeight(int toolbarHeight) {
        this.m_toolbarHeight = toolbarHeight;
    }

    /**
     * Get the value of columnCount
     * @return the value of columnCount
     */
    public int getColumnCount() {
        return m_columnCount;
    }

    /**
     * Set the value of columnCount
     * @param columnCount new value of columnCount
     */
    public void setColumnCount(int columnCount) {
        this.m_columnCount = columnCount;
    }

    /**
     * Get the value of columnWidth
     * @return the value of columnWidth
     */
    public int getColumnWidth() {
        return m_columnWidth;
    }

    /**
     * Set the value of columnWidth
     * @param columnWidth new value of columnWidth
     */
    public void setColumnWidth(int columnWidth) {
        this.m_columnWidth = columnWidth;
    }

    /**
     * Get the value of orientation
     * @return the value of orientation
     */
    public String getOrientation() {
        return m_orientation;
    }

    /**
     * Set the value of orientation
     * @param orientation new value of orientation
     */
    public void setOrientation(String orientation) {
        this.m_orientation = orientation;
    }

    /**
     * Get the value of topMargin
     * @return the value of topMargin
     */
    public int getTopMargin() {
        return m_topMargin;
    }

    /**
     * Set the value of topMargin
     * @param topMargin new value of topMargin
     */
    public void setTopMargin(int topMargin) {
        this.m_topMargin = topMargin;
    }

    /**
     * Get the value of rightMargin
     * @return the value of rightMargin
     */
    public int getRightMargin() {
        return m_rightMargin;
    }

    /**
     * Set the value of rightMargin
     * @param rightMargin new value of rightMargin
     */
    public void setRightMargin(int rightMargin) {
        this.m_rightMargin = rightMargin;
    }

    /**
     * Get the value of bottomMargin
     * @return the value of bottomMargin
     */
    public int getBottomMargin() {
        return m_bottomMargin;
    }

    /**
     * Set the value of bottomMargin
     * @param bottomMargin new value of bottomMargin
     */
    public void setBottomMargin(int bottomMargin) {
        this.m_bottomMargin = bottomMargin;
    }

    /**
     * Get the value of leftMargin
     * @return the value of leftMargin
     */
    public int getLeftMargin() {
        return m_leftMargin;
    }

    /**
     * Set the value of leftMargin
     * @param leftMargin new value of leftMargin
     */
    public void setLeftMargin(int leftMargin) {
        this.m_leftMargin = leftMargin;
    }

    /**
     * Get the value of cssFile
     * @return the value of cssFile
     */
    public String getCssFile() {
        return m_cssFile;
    }

    /**
     * Set the value of cssFile
     * @param cssFile new value of cssFile
     */
    public void setCssFile(String cssFile) {
        this.m_cssFile = cssFile;
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
        GridModel   grid     = null;
        ThemeConfig themeCfg;
        
        // set the dialog message prefix
        setKeyPrefix(KEY_PREFIX);
        initPropertyValues(settings, request);
        
        themeCfg = getThemeConfig();
        grid = themeCfg != null ? themeCfg.getGrid() : null;
        if(grid != null){
            m_headerHeight = grid.getHeaderHeight();
            m_footerHeight = grid.getFooterHeight();
            m_toolbarHeight = grid.getToolbarHeight();
            m_columnCount = grid.getColumnCount();
            m_columnWidth = grid.getColumnWidth();
            m_orientation = grid.getOrientation() == GridModel.ORIENTATION_LEFT ? "left" : "right";
            m_topMargin = grid.getTopMargin();
            m_rightMargin = grid.getRightMargin();
            m_bottomMargin = grid.getBottomMargin();
            m_leftMargin = grid.getLeftMargin();
            m_cssFile = grid.getCssFile();
        }else{
            m_headerHeight = 180;
            m_footerHeight = 130;
            m_toolbarHeight = 130;
            m_columnCount = 12;
            m_columnWidth = 40;
            m_orientation = "left";
            m_topMargin = 10;
            m_rightMargin = 10;
            m_bottomMargin = 10;
            m_leftMargin = 10;
            m_cssFile = "";
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
        GridModel   grid     = null;
        ThemeConfig themeCfg;
        
        themeCfg = getThemeConfig();
        grid = themeCfg != null ? themeCfg.getGrid() : null;
        if(grid != null){
            try{
                grid.setHeaderHeight(m_headerHeight);
                grid.setFooterHeight(m_footerHeight);
                grid.setToolbarHeight(m_toolbarHeight);
                grid.setColumnCount(m_columnCount);
                grid.setColumnWidth(m_columnWidth);
                grid.setOrientation(m_orientation.equals("left") ? GridModel.ORIENTATION_LEFT : GridModel.ORIENTATION_RIGHT);
                grid.setTopMargin(m_topMargin);
                grid.setRightMargin(m_rightMargin);
                grid.setBottomMargin(m_bottomMargin);
                grid.setLeftMargin(m_leftMargin);
                grid.setCssFile(m_cssFile);
                
                saveData();
                getSettings().setDialogObject(null);
                clear();
            }catch(ThemeConfigException ex){
                throw new ServletException(ex);
            }
        }
    }

    /**
     * Defines the widgets to be used within the dialog.
     */
    @Override
    protected void defineWidgets() {
        addWidget(new CmsWidgetDialogParameter(this,"headerHeight", PAGE_ARRAY[0], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"footerHeight", PAGE_ARRAY[0], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"toolbarHeight", PAGE_ARRAY[0], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"columnCount", PAGE_ARRAY[0], new CmsSelectWidget(getColumnCountWidgetConfiguration())));
        addWidget(new CmsWidgetDialogParameter(this,"columnWidth", PAGE_ARRAY[0], new CmsSelectWidget(getColumnWidthWidgetConfiguration())));
        addWidget(new CmsWidgetDialogParameter(this,"orientation", PAGE_ARRAY[0], new CmsSelectWidget(getOrientationWidgetConfiguration())));
        addWidget(new CmsWidgetDialogParameter(this,"topMargin", PAGE_ARRAY[0], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"rightMargin", PAGE_ARRAY[0], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"bottomMargin", PAGE_ARRAY[0], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"leftMargin", PAGE_ARRAY[0], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"cssFile", PAGE_ARRAY[0], new CmsVfsFileWidget()));
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
     * Creates all select widget options needed for the column count select widget.
     * @return The select widget options.
     */
    private List<CmsSelectWidgetOption> getColumnCountWidgetConfiguration(){
        List<CmsSelectWidgetOption> result = new ArrayList<CmsSelectWidgetOption>();
        
        result.add(new CmsSelectWidgetOption("12", true));
        result.add(new CmsSelectWidgetOption("16", false));
        result.add(new CmsSelectWidgetOption("24", false));
        return result;
    }

    /**
     * Creates all select widget options needed for the column width select widget.
     * @return The select widget options.
     */
    private List<CmsSelectWidgetOption> getColumnWidthWidgetConfiguration(){
        List<CmsSelectWidgetOption> result = new ArrayList<CmsSelectWidgetOption>();
        
        result.add(new CmsSelectWidgetOption("30", false));
        result.add(new CmsSelectWidgetOption("35", false));
        result.add(new CmsSelectWidgetOption("40", true));
        result.add(new CmsSelectWidgetOption("45", false));
        result.add(new CmsSelectWidgetOption("50", false));
        result.add(new CmsSelectWidgetOption("55", false));
        result.add(new CmsSelectWidgetOption("60", false));
        result.add(new CmsSelectWidgetOption("65", false));
        result.add(new CmsSelectWidgetOption("70", false));
        return result;
    }

    /**
     * Creates all select widget options needed for the orientation select widget.
     * @return The select widget options.
     */
    private List<CmsSelectWidgetOption> getOrientationWidgetConfiguration(){
        List<CmsSelectWidgetOption> result = new ArrayList<CmsSelectWidgetOption>();
        
        result.add(new CmsSelectWidgetOption("left", true));
        result.add(new CmsSelectWidgetOption("right", false));
        return result;
    }
}
