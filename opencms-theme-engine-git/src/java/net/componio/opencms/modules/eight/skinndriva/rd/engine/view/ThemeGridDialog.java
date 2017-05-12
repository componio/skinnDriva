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

import  com.diawara.commons.etc.PropertySetter;




/**
 *
 * @author Robert Diawara
 */
public class ThemeGridDialog extends ThemeEngineWidgetDialog{
    /** The array defining the pages making up the dialog. */
    public static final String[]      PAGE_ARRAY = new String[]{"common", "grid", "medium-size-grid", "small-size-grid"};
    /** The key prefix for localized messages. Prevents duplicated keys. */
    public static final String        KEY_PREFIX = "ThemeGrid";
    /** The list defining the pages making up the dialog. Built from PAGE_ARRAY*/
    public static final List<String>  PAGE_LIST  = Arrays.asList(PAGE_ARRAY);
    
    /** Holds the value of the property headerHeight. */
    private int m_headerHeight;
    /** Holds the value of the property headerHeight for medium size grids. */
    private int m_mediumSizeHeaderHeight;
    /** Holds the value of the property headerHeight for small size grids. */
    private int m_smallSizeHeaderHeight;
    /** Holds the value of the property footerHeight. */
    private int m_footerHeight;
    /** Holds the value of the property footerHeight for medium size grids. */
    private int m_mediumSizeFooterHeight;
    /** Holds the value of the property footerHeight for small size grids. */
    private int m_smallSizeFooterHeight;
    /** Holds the value of the property toolbarHeight. */
    private int m_toolbarHeight;
    /** Holds the value of the property toolbarHeight for medium size grids. */
    private int m_mediumSizeToolbarHeight;
    /** Holds the value of the property toolbarHeight for small size grids. */
    private int m_smallSizeToolbarHeight;
    /** Holds the value of the property columnCount. */
    private int m_columnCount;
    /** Holds the value of the property columnCount for medium size grids. */
    private int m_mediumSizeColumnCount;
    /** Holds the value of the property columnCount for small size grids. */
    private int m_smallSizeColumnCount;
    /** Holds the value of the property columnWidth. */
    private int m_columnWidth;
    /** Holds the value of the property columnWidth for medium size grids. */
    private int m_mediumSizeColumnWidth;
    /** Holds the value of the property columnWidth for small size grids. */
    private int m_smallSizeColumnWidth;
    /** Holds the value of the property orientation. */
    private String m_orientation;
    /** Holds the value of the property topMargin. */
    private int m_topMargin;
    /** Holds the value of the property topMargin for medium size grids. */
    private int m_mediumSizeTopMargin;
    /** Holds the value of the property topMargin for small size grids. */
    private int m_smallSizeTopMargin;
    /** Holds the value of the property rightMargin. */
    private int m_rightMargin;
    /** Holds the value of the property rightMargin for medium size grids. */
    private int m_mediumSizeRightMargin;
    /** Holds the value of the property rightMargin for small size grids. */
    private int m_smallSizeRightMargin;
    /** Holds the value of the property bottomMargin. */
    private int m_bottomMargin;
    /** Holds the value of the property bottomMargin for medium size grids. */
    private int m_mediumSizeBottomMargin;
    /** Holds the value of the property bottomMargin for small size grids. */
    private int m_smallSizeBottomMargin;
    /** Holds the value of the property leftMargin. */
    private int m_leftMargin;
    /** Holds the value of the property leftMargin for medium size grids. */
    private int m_mediumSizeLeftMargin;
    /** Holds the value of the property leftMargin for small size grids. */
    private int m_smallSizeLeftMargin;
    /** Holds the value of the property cssFile. */
    private String m_cssFile;
     /** Holds the value of the property &quot;mediumSizeCssFile&quot;. */
    private String m_mediumSizeCssFile;
     /** Holds the value of the property &quot;smallSizeCssFile&quot;. */
    private String m_smallSizeCssFile;
    /** Holds the name of the CSS file, which contains all media queries. */
    private String m_responsiveCssFile;


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
     * Get the value of mediumSizeHeaderHeight
     *
     * @return the value of mediumSizeHeaderHeight
     */
    public int getMediumSizeHeaderHeight() {
        return m_mediumSizeHeaderHeight;
    }

    /**
     * Set the value of mediumSizeHeaderHeight
     *
     * @param mediumSizeHeaderHeight new value of mediumSizeHeaderHeight
     */
    public void setMediumSizeHeaderHeight(int mediumSizeHeaderHeight) {
        this.m_mediumSizeHeaderHeight = mediumSizeHeaderHeight;
    }

    /**
     * Get the value of smallSizeHeaderHeight
     *
     * @return the value of smallSizeHeaderHeight
     */
    public int getSmallSizeHeaderHeight() {
        return m_smallSizeHeaderHeight;
    }

    /**
     * Set the value of smallSizeHeaderHeight
     *
     * @param smallSizeHeaderHeight new value of smallSizeHeaderHeight
     */
    public void setSmallSizeHeaderHeight(int smallSizeHeaderHeight) {
        this.m_smallSizeHeaderHeight = smallSizeHeaderHeight;
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
     * Get the value of mediumSizeFooterheight
     *
     * @return the value of mediumSizeFooterheight
     */
    public int getMediumSizeFooterHeight() {
        return m_mediumSizeFooterHeight;
    }

    /**
     * Set the value of mediumSizeFooterheight
     *
     * @param mediumSizeFooterHeight new value of mediumSizeFooterheight
     */
    public void setMediumSizeFooterHeight(int mediumSizeFooterHeight) {
        this.m_mediumSizeFooterHeight = mediumSizeFooterHeight;
    }

    /**
     * Get the value of smallSizeFooterHeight
     *
     * @return the value of smallSizeFooterHeight
     */
    public int getSmallSizeFooterHeight() {
        return m_smallSizeFooterHeight;
    }

    /**
     * Set the value of smallSizeFooterHeight
     *
     * @param smallSizeFooterHeight new value of smallSizeFooterHeight
     */
    public void setSmallSizeFooterHeight(int smallSizeFooterHeight) {
        this.m_smallSizeFooterHeight = smallSizeFooterHeight;
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
     * Get the value of mediumSizeToolbarHeight
     *
     * @return the value of mediumSizeToolbarHeight
     */
    public int getMediumSizeToolbarHeight() {
        return m_mediumSizeToolbarHeight;
    }

    /**
     * Set the value of mediumSizeToolbarHeight
     *
     * @param mediumSizeToolbarHeight new value of mediumSizeToolbarHeight
     */
    public void setMediumSizeToolbarHeight(int mediumSizeToolbarHeight) {
        this.m_mediumSizeToolbarHeight = mediumSizeToolbarHeight;
    }

    /**
     * Get the value of smallSizeToolbarHeight
     *
     * @return the value of smallSizeToolbarHeight
     */
    public int getSmallSizeToolbarHeight() {
        return m_smallSizeToolbarHeight;
    }

    /**
     * Set the value of smallSizeToolbarHeight
     *
     * @param smallSizeToolbarHeight new value of smallSizeToolbarHeight
     */
    public void setSmallSizeToolbarHeight(int smallSizeToolbarHeight) {
        this.m_smallSizeToolbarHeight = smallSizeToolbarHeight;
    }

    /**
     * Get the value of columnCount
     * @return the value of columnCount
     */
    public int getColumnCount() {
        return m_columnCount;
    }
    
    /**
     * Get the value of mediumSizeColumnCount
     *
     * @return the value of mediumSizeColumnCount
     */
    public int getMediumSizeColumnCount() {
        return m_mediumSizeColumnCount;
    }

    /**
     * Set the value of mediumSizeColumnCount
     *
     * @param mediumSizeColumnCount new value of mediumSizeColumnCount
     */
    public void setMediumSizeColumnCount(int mediumSizeColumnCount) {
        this.m_mediumSizeColumnCount = mediumSizeColumnCount;
    }

    /**
     * Get the value of smallSizeColumnCount
     *
     * @return the value of smallSizeColumnCount
     */
    public int getSmallSizeColumnCount() {
        return m_smallSizeColumnCount;
    }

    /**
     * Set the value of smallSizeColumnCount
     *
     * @param smallSizeColumnCount new value of smallSizeColumnCount
     */
    public void setSmallSizeColumnCount(int smallSizeColumnCount) {
        this.m_smallSizeColumnCount = smallSizeColumnCount;
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
     * Get the value of mediumSizeColumnWidth
     *
     * @return the value of mediumSizeColumnWidth
     */
    public int getMediumSizeColumnWidth() {
        return m_mediumSizeColumnWidth;
    }

    /**
     * Set the value of mediumSizeColumnWidth
     *
     * @param mediumSizeColumnWidth new value of mediumSizeColumnWidth
     */
    public void setMediumSizeColumnWidth(int mediumSizeColumnWidth) {
        this.m_mediumSizeColumnWidth = mediumSizeColumnWidth;
    }

    /**
     * Get the value of smallSizeColumnWidth
     *
     * @return the value of smallSizeColumnWidth
     */
    public int getSmallSizeColumnWidth() {
        return m_smallSizeColumnWidth;
    }

    /**
     * Set the value of smallSizeColumnWidth
     *
     * @param smallSizeColumnWidth new value of smallSizeColumnWidth
     */
    public void setSmallSizeColumnWidth(int smallSizeColumnWidth) {
        this.m_smallSizeColumnWidth = smallSizeColumnWidth;
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
     * Get the value of mediumSizeTopMargin
     *
     * @return the value of mediumSizeTopMargin
     */
    public int getMediumSizeTopMargin() {
        return m_mediumSizeTopMargin;
    }

    /**
     * Set the value of mediumSizeTopMargin
     *
     * @param mediumSizeTopMargin new value of mediumSizeTopMargin
     */
    public void setMediumSizeTopMargin(int mediumSizeTopMargin) {
        this.m_mediumSizeTopMargin = mediumSizeTopMargin;
    }

    /**
     * Get the value of smallSizeTopMargin
     *
     * @return the value of smallSizeTopMargin
     */
    public int getSmallSizeTopMargin() {
        return m_smallSizeTopMargin;
    }

    /**
     * Set the value of smallSizeTopMargin
     *
     * @param smallSizeTopMargin new value of smallSizeTopMargin
     */
    public void setSmallSizeTopMargin(int smallSizeTopMargin) {
        this.m_smallSizeTopMargin = smallSizeTopMargin;
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
     * Get the value of mediumSizeRightMargin
     *
     * @return the value of mediumSizeRightMargin
     */
    public int getMediumSizeRightMargin() {
        return m_mediumSizeRightMargin;
    }

    /**
     * Set the value of mediumSizeRightMargin
     *
     * @param mediumSizeRightMargin new value of mediumSizeRightMargin
     */
    public void setMediumSizeRightMargin(int mediumSizeRightMargin) {
        this.m_mediumSizeRightMargin = mediumSizeRightMargin;
    }

    /**
     * Get the value of smallSizeRightMargin
     *
     * @return the value of smallSizeRightMargin
     */
    public int getSmallSizeRightMargin() {
        return m_smallSizeRightMargin;
    }

    /**
     * Set the value of smallSizeRightMargin
     *
     * @param smallSizeRightMargin new value of smallSizeRightMargin
     */
    public void setSmallSizeRightMargin(int smallSizeRightMargin) {
        this.m_smallSizeRightMargin = smallSizeRightMargin;
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
     * Get the value of mediumSizeBottomMargin
     *
     * @return the value of mediumSizeBottomMargin
     */
    public int getMediumSizeBottomMargin() {
        return m_mediumSizeBottomMargin;
    }

    /**
     * Set the value of mediumSizeBottomMargin
     *
     * @param mediumSizeBottomMargin new value of mediumSizeBottomMargin
     */
    public void setMediumSizeBottomMargin(int mediumSizeBottomMargin) {
        this.m_mediumSizeBottomMargin = mediumSizeBottomMargin;
    }

    /**
     * Get the value of smallSizeBottomMargin
     *
     * @return the value of smallSizeBottomMargin
     */
    public int getSmallSizeBottomMargin() {
        return m_smallSizeBottomMargin;
    }

    /**
     * Set the value of smallSizeBottomMargin
     *
     * @param smallSizeBottomMargin new value of smallSizeBottomMargin
     */
    public void setSmallSizeBottomMargin(int smallSizeBottomMargin) {
        this.m_smallSizeBottomMargin = smallSizeBottomMargin;
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
     * Get the value of mediumSizeLeftMargin
     *
     * @return the value of mediumSizeLeftMargin
     */
    public int getMediumSizeLeftMargin() {
        return m_mediumSizeLeftMargin;
    }

    /**
     * Set the value of mediumSizeLeftMargin
     *
     * @param mediumSizeLeftMargin new value of mediumSizeLeftMargin
     */
    public void setMediumSizeLeftMargin(int mediumSizeLeftMargin) {
        this.m_mediumSizeLeftMargin = mediumSizeLeftMargin;
    }

    /**
     * Get the value of smallSizeLeftMargin
     *
     * @return the value of smallSizeLeftMargin
     */
    public int getSmallSizeLeftMargin() {
        return m_smallSizeLeftMargin;
    }

    /**
     * Set the value of smallSizeLeftMargin
     *
     * @param smallSizeLeftMargin new value of smallSizeLeftMargin
     */
    public void setSmallSizeLeftMargin(int smallSizeLeftMargin) {
        this.m_smallSizeLeftMargin = smallSizeLeftMargin;
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
     * Get the value of mediumSizeCssFile
     * @return the value of mediumSizeCssFile
     */
    public String getMediumSizeCssFile() {
        return m_mediumSizeCssFile;
    }

    /**
     * Set the value of mediumSizeCssFile
     * @param mediumSizeCssFile new value of mediumSizeCssFile
     */
    public void setMediumSizeCssFile(String mediumSizeCssFile) {
        this.m_mediumSizeCssFile = mediumSizeCssFile;
    }

    /**
     * Get the value of smallSizeCssFile
     * @return the value of smallSizeCssFile
     */
    public String getSmallSizeCssFile() {
        return m_smallSizeCssFile;
    }

    /**
     * Set the value of smallSizeCssFile
     * @param smallSizeCssFile new value of smallSizeCssFile
     */
    public void setSmallSizeCssFile(String smallSizeCssFile) {
        this.m_smallSizeCssFile = smallSizeCssFile;
    }
    
    /**
     * Get the value of responsiveCssFile
     * @return the value of responsiveCssFile
     */
    public String getResponsiveCssFile() {
        return m_responsiveCssFile;
    }

    /**
     * Set the value of responsiveCssFile
     * @param responsiveCssFile new value of responsiveCssFile
     */
    public void setResponsiveCssFile(String responsiveCssFile) {
        this.m_responsiveCssFile = responsiveCssFile;
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
        GridModel   grid;
        ThemeConfig themeCfg;
        int         gridMode;
        boolean     mediumModeEnabled;
        boolean     smallModeEnabled;
        
        // set the dialog message prefix
        setKeyPrefix(KEY_PREFIX);
        initPropertyValues(settings, request);
        
        themeCfg = getThemeConfig();
        
        if(themeCfg != null){
            m_responsiveCssFile = themeCfg.getResponsiveCssFile();
            grid = themeCfg.getGrid();
        }else{
            m_responsiveCssFile = "";
            grid = null;
        }
        
        if(grid != null){
            gridMode = grid.getMode();
            
            grid.setMode(GridModel.MODE_DESKTOP);
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
            
            mediumModeEnabled = grid.isModeEnabled(GridModel.MODE_MEDIUM);
            if(mediumModeEnabled){
                grid.setMode(GridModel.MODE_MEDIUM);
            }
            m_mediumSizeHeaderHeight = mediumModeEnabled ? grid.getHeaderHeight() :90;
            m_mediumSizeFooterHeight = mediumModeEnabled ? grid.getFooterHeight() : 70;
            m_mediumSizeToolbarHeight = mediumModeEnabled ? grid.getToolbarHeight() : 70;
            m_mediumSizeColumnCount = mediumModeEnabled ? grid.getColumnCount() : 6;
            m_mediumSizeColumnWidth = mediumModeEnabled ? grid.getColumnWidth() : 60;
            m_mediumSizeTopMargin = mediumModeEnabled ? grid.getTopMargin() : 5;
            m_mediumSizeRightMargin = mediumModeEnabled ? grid.getRightMargin() : 5;
            m_mediumSizeBottomMargin = mediumModeEnabled ? grid.getBottomMargin() : 5;
            m_mediumSizeLeftMargin = mediumModeEnabled ? grid.getLeftMargin() : 5;
            m_mediumSizeCssFile = mediumModeEnabled ? grid.getCssFile() : "";
            
            smallModeEnabled = grid.isModeEnabled(GridModel.MODE_SMALL);
            if(smallModeEnabled){
                grid.setMode(GridModel.MODE_SMALL);
            }
            m_smallSizeHeaderHeight = smallModeEnabled ? grid.getHeaderHeight() : 90;
            m_smallSizeFooterHeight = smallModeEnabled ? grid.getFooterHeight() : 70;
            m_smallSizeToolbarHeight = smallModeEnabled ? grid.getToolbarHeight() : 70;
            m_smallSizeColumnCount = smallModeEnabled ? grid.getColumnCount() : 2;
            m_smallSizeColumnWidth = smallModeEnabled ? grid.getColumnWidth() : 60;
            m_smallSizeTopMargin = smallModeEnabled ? grid.getTopMargin() : 2;
            m_smallSizeRightMargin = smallModeEnabled ? grid.getRightMargin() : 2;
            m_smallSizeBottomMargin = smallModeEnabled ? grid.getBottomMargin() : 2;
            m_smallSizeLeftMargin = smallModeEnabled ? grid.getLeftMargin() : 2;
            m_smallSizeCssFile = smallModeEnabled ? grid.getCssFile() : "";
        
            grid.setMode(gridMode);
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

            m_mediumSizeHeaderHeight = 90;
            m_mediumSizeFooterHeight = 70;
            m_mediumSizeToolbarHeight = 70;
            m_mediumSizeColumnCount = 6;
            m_mediumSizeColumnWidth = 60;
            m_mediumSizeTopMargin = 5;
            m_mediumSizeRightMargin = 5;
            m_mediumSizeBottomMargin = 5;
            m_mediumSizeLeftMargin = 5;
            m_mediumSizeCssFile = "";

            m_smallSizeHeaderHeight = 90;
            m_smallSizeFooterHeight = 70;
            m_smallSizeToolbarHeight = 70;
            m_smallSizeColumnCount = 2;
            m_smallSizeColumnWidth = 60;
            m_smallSizeTopMargin = 2;
            m_smallSizeRightMargin = 2;
            m_smallSizeBottomMargin = 2;
            m_smallSizeLeftMargin = 2;
            m_smallSizeCssFile = "";
        }
        
        super.initWorkplaceRequestValues(settings, request);
        
        // save the current state of the job
        setDialogObject(getThemeConfig());
    }
    
    /**
     * 
     * @param p_bean
     * @param p_dialogProperty
     * @param p_beanProperty 
     */
    protected void commitIntWidgetValue(Object p_bean, String p_beanProperty, String p_dialogProperty){
        String         tmpStr       = getParamValue(p_dialogProperty);
        int            tmpInt;
        PropertySetter beanSetter   = new PropertySetter(p_bean);
        PropertySetter dialogSetter = new PropertySetter(this);
        

        if((tmpStr != null) && (tmpStr.trim().length() > 0)){
           tmpInt = Integer.parseInt(tmpStr);
           
           beanSetter.setBeanProperty(p_beanProperty, tmpInt);
           dialogSetter.setBeanProperty(p_dialogProperty, tmpInt);
        }
    }
    
    /**
     * 
     * @param p_bean
     * @param p_dialogProperty
     * @param p_beanProperty 
     */
    protected void commitStringWidgetValue(Object p_bean, String p_beanProperty, String p_dialogProperty){
        String         tmpStr       = getParamValue(p_dialogProperty);
        PropertySetter beanSetter   = new PropertySetter(p_bean);
        PropertySetter dialogSetter = new PropertySetter(this);
        

        if((tmpStr != null) && (tmpStr.trim().length() > 0)){
           beanSetter.setBeanProperty(p_beanProperty, tmpStr);
           dialogSetter.setBeanProperty(p_dialogProperty, tmpStr);
        }
    }
    
     /**
     * Commits all values on the given dialog page
     * @param dialogPage The dialog page
     * @return A list with all exceptions which may occur during the execution.
     */
    @Override
    protected List commitWidgetValues(String dialogPage) {
        List            result      = new ArrayList();
        ThemeConfig     themeCfg;
        GridModel       grid;
        String          dlgPage     = dialogPage != null ? dialogPage : getParamPage();
        String          tmp;
 
        themeCfg = getThemeConfig();
        if(themeCfg != null){
            grid = themeCfg.getGrid();

            if(ThemeGridDialog.PAGE_ARRAY[0].equals(dlgPage)){
                tmp = getParamValue("orientation");
                grid.setOrientation(((tmp != null) && (tmp.toLowerCase().equals("left"))) ? GridModel.ORIENTATION_LEFT : GridModel.ORIENTATION_RIGHT);
                commitStringWidgetValue(themeCfg, "responsiveCssFile", "responsiveCssFile");
            }else{
                if(ThemeGridDialog.PAGE_ARRAY[1].equals(dlgPage)){
                    grid.enableMode(GridModel.MODE_DESKTOP);
                    grid.setMode(GridModel.MODE_DESKTOP);
                    try{
                        commitIntWidgetValue(grid, "headerHeight", "headerHeight");
                        commitIntWidgetValue(grid, "footerHeight", "footerHeight");
                        commitIntWidgetValue(grid, "toolbarHeight", "toolbarHeight");
                        commitIntWidgetValue(grid, "columnCount", "columnCount");
                        commitIntWidgetValue(grid, "columnWidth", "columnWidth");
                        commitIntWidgetValue(grid, "topMargin", "topMargin");
                        commitIntWidgetValue(grid, "rightMargin", "rightMargin");
                        commitIntWidgetValue(grid, "bottomMargin", "bottomMargin");
                        commitIntWidgetValue(grid, "leftMargin", "leftMargin");
                        commitStringWidgetValue(grid, "cssFile", "cssFile");
                    } catch (Throwable ex) {
                        result.add(ex);
                    }
                }else if(ThemeGridDialog.PAGE_ARRAY[2].equals(dlgPage)){
                    grid.enableMode(GridModel.MODE_MEDIUM);
                    grid.setMode(GridModel.MODE_MEDIUM);
                    try{
                        commitIntWidgetValue(grid, "headerHeight", "mediumSizeHeaderHeight");
                        commitIntWidgetValue(grid, "footerHeight", "mediumSizeFooterHeight");
                        commitIntWidgetValue(grid, "toolbarHeight", "mediumSizeToolbarHeight");
                        commitIntWidgetValue(grid, "columnCount", "mediumSizeColumnCount");
                        commitIntWidgetValue(grid, "columnWidth", "mediumSizeColumnWidth");
                        commitIntWidgetValue(grid, "topMargin", "mediumSizeTopMargin");
                        commitIntWidgetValue(grid, "rightMargin", "mediumSizeRightMargin");
                        commitIntWidgetValue(grid, "bottomMargin", "mediumSizeBottomMargin");
                        commitIntWidgetValue(grid, "leftMargin", "mediumSizeLeftMargin");
                        commitStringWidgetValue(grid, "cssFile", "mediumSizeCssFile");
                    } catch (Throwable ex) {
                        result.add(ex);
                    }
                }else if(ThemeGridDialog.PAGE_ARRAY[3].equals(dlgPage)){
                    grid.enableMode(GridModel.MODE_SMALL);
                    grid.setMode(GridModel.MODE_SMALL);
                    try{
                        commitIntWidgetValue(grid, "headerHeight", "smallSizeHeaderHeight");
                        commitIntWidgetValue(grid, "footerHeight", "smallSizeFooterHeight");
                        commitIntWidgetValue(grid, "toolbarHeight", "smallSizeToolbarHeight");
                        commitIntWidgetValue(grid, "columnCount", "smallSizeColumnCount");
                        commitIntWidgetValue(grid, "columnWidth", "smallSizeColumnWidth");
                        commitIntWidgetValue(grid, "topMargin", "smallSizeTopMargin");
                        commitIntWidgetValue(grid, "rightMargin", "smallSizeRightMargin");
                        commitIntWidgetValue(grid, "bottomMargin", "smallSizeBottomMargin");
                        commitIntWidgetValue(grid, "leftMargin", "smallSizeLeftMargin");
                        commitStringWidgetValue(grid, "cssFile", "smallSizeCssFile");
                    } catch (Throwable ex) {
                        result.add(ex);
                    }
                }
            }
        }
        return result;
    }
    /**
     * Commits the edited object after pressing the "OK" button.
     * @throws IOException In case of errors forwarding to the required result page.
     * @throws ServletException In case of errors forwarding to the required result page.
     */
    @Override
    public void actionCommit() throws IOException, ServletException {
        GridModel   grid;
        ThemeConfig themeCfg;
        int         gridMode;
        
        themeCfg = getThemeConfig();
        grid = themeCfg != null ? themeCfg.getGrid() : null;
        if((grid != null) && (themeCfg != null)){
            try{
                themeCfg.setResponsiveCssFile(m_responsiveCssFile);
                
                gridMode = grid.getMode();
                
                grid.setMode(GridModel.MODE_DESKTOP);
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
                
                grid.enableMode(GridModel.MODE_MEDIUM);
                grid.setMode(GridModel.MODE_MEDIUM);
                grid.setHeaderHeight(m_mediumSizeHeaderHeight);
                grid.setFooterHeight(m_mediumSizeFooterHeight);
                grid.setToolbarHeight(m_mediumSizeToolbarHeight);
                grid.setColumnCount(m_mediumSizeColumnCount);
                grid.setColumnWidth(m_mediumSizeColumnWidth);
                grid.setTopMargin(m_mediumSizeTopMargin);
                grid.setRightMargin(m_mediumSizeRightMargin);
                grid.setBottomMargin(m_mediumSizeBottomMargin);
                grid.setLeftMargin(m_mediumSizeLeftMargin);
                grid.setCssFile(m_mediumSizeCssFile);
                
                grid.enableMode(GridModel.MODE_SMALL);
                grid.setMode(GridModel.MODE_SMALL);
                grid.setHeaderHeight(m_smallSizeHeaderHeight);
                grid.setFooterHeight(m_smallSizeFooterHeight);
                grid.setToolbarHeight(m_smallSizeToolbarHeight);
                grid.setColumnCount(m_smallSizeColumnCount);
                grid.setColumnWidth(m_smallSizeColumnWidth);
                grid.setTopMargin(m_smallSizeTopMargin);
                grid.setRightMargin(m_smallSizeRightMargin);
                grid.setBottomMargin(m_smallSizeBottomMargin);
                grid.setLeftMargin(m_smallSizeLeftMargin);
                grid.setCssFile(m_smallSizeCssFile);
                
                grid.setMode(gridMode);
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
        addWidget(new CmsWidgetDialogParameter(this,"orientation", PAGE_ARRAY[0], new CmsSelectWidget(getOrientationWidgetConfiguration())));
        addWidget(new CmsWidgetDialogParameter(this,"responsiveCssFile", PAGE_ARRAY[0], new CmsVfsFileWidget()));

        addWidget(new CmsWidgetDialogParameter(this,"headerHeight", PAGE_ARRAY[1], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"footerHeight", PAGE_ARRAY[1], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"toolbarHeight", PAGE_ARRAY[1], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"columnCount", PAGE_ARRAY[1], new CmsSelectWidget(getColumnCountWidgetConfiguration())));
        /*addWidget(new CmsWidgetDialogParameter(this,"columnWidth", PAGE_ARRAY[1], new CmsSelectWidget(getColumnWidthWidgetConfiguration())));*/
        addWidget(new CmsWidgetDialogParameter(this,"columnWidth", PAGE_ARRAY[1], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"topMargin", PAGE_ARRAY[1], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"rightMargin", PAGE_ARRAY[1], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"bottomMargin", PAGE_ARRAY[1], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"leftMargin", PAGE_ARRAY[1], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"cssFile", PAGE_ARRAY[1], new CmsVfsFileWidget()));

        addWidget(new CmsWidgetDialogParameter(this,"mediumSizeHeaderHeight", PAGE_ARRAY[2], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"mediumSizeFooterHeight", PAGE_ARRAY[2], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"mediumSizeToolbarHeight", PAGE_ARRAY[2], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"mediumSizeColumnCount", PAGE_ARRAY[2], new CmsSelectWidget(getColumnCountWidgetConfiguration())));
        /*addWidget(new CmsWidgetDialogParameter(this,"mediumSizeColumnWidth", PAGE_ARRAY[2], new CmsSelectWidget(getColumnWidthWidgetConfiguration())));*/
        addWidget(new CmsWidgetDialogParameter(this,"mediumSizeColumnWidth", PAGE_ARRAY[2], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"mediumSizeTopMargin", PAGE_ARRAY[2], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"mediumSizeRightMargin", PAGE_ARRAY[2], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"mediumSizeBottomMargin", PAGE_ARRAY[2], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"mediumSizeLeftMargin", PAGE_ARRAY[2], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"mediumSizeCssFile", PAGE_ARRAY[2], new CmsVfsFileWidget()));

        addWidget(new CmsWidgetDialogParameter(this,"smallSizeHeaderHeight", PAGE_ARRAY[3], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"smallSizeFooterHeight", PAGE_ARRAY[3], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"smallSizeToolbarHeight", PAGE_ARRAY[3], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"smallSizeColumnCount", PAGE_ARRAY[3], new CmsSelectWidget(getColumnCountWidgetConfiguration())));
        /*addWidget(new CmsWidgetDialogParameter(this,"smallSizeColumnWidth", PAGE_ARRAY[3], new CmsSelectWidget(getColumnWidthWidgetConfiguration())));*/
        addWidget(new CmsWidgetDialogParameter(this,"smallSizeColumnWidth", PAGE_ARRAY[3], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"smallSizeTopMargin", PAGE_ARRAY[3], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"smallSizeRightMargin", PAGE_ARRAY[3], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"smallSizeBottomMargin", PAGE_ARRAY[3], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"smallSizeLeftMargin", PAGE_ARRAY[3], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"smallSizeCssFile", PAGE_ARRAY[3], new CmsVfsFileWidget()));
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
        List<CmsSelectWidgetOption> result = new ArrayList<>();
        
        result.add(new CmsSelectWidgetOption("2", true));
        result.add(new CmsSelectWidgetOption("3", true));
        result.add(new CmsSelectWidgetOption("6", true));
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
        List<CmsSelectWidgetOption> result = new ArrayList<>();
        
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
        List<CmsSelectWidgetOption> result = new ArrayList<>();
        
        result.add(new CmsSelectWidgetOption("left", true));
        result.add(new CmsSelectWidgetOption("right", false));
        return result;
    }
}
