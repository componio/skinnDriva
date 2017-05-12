/*
 *  Document : GridModel.java
 *  Created on Sa, Feb 18 2012, 22:08:42
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
package net.componio.opencms.modules.eight.skinndriva.rd.engine.model;

import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigException;

import  java.io.StringWriter;
import  java.text.SimpleDateFormat;
import  java.util.Date;
import  org.opencms.jsp.CmsJspActionElement;

/**
 *
 * @author Robert Diawara
 */
public class GridModel extends ThemeEngineModel{
    /** Constant used to determine the orientation of the grid. */
    public static final int ORIENTATION_LEFT  = 1;
    /** Constant used to determine the orientation of the grid. */
    public static final int ORIENTATION_RIGHT = 2;
    
    /** Determines that the grid is for big screen sizes. */
    public static final int MODE_DESKTOP = 0;
    /** Determines that the grid is for medium sizes. */
    public static final int MODE_MEDIUM  = 1;
    /** Determines that the grid is for small sizes. */
    public static final int MODE_SMALL   = 2;
    /** Determines the minimum size limit for desktops in pixels. */
    public static final int DESKTOP_SIZE_LIMIT = 1024;
    
    /** Holds the mode which determines the screen size. */
    private int     mode;
    /** Holds the value of the property columnWidth. */
    private int[]   columnWidth;
    /** Holds the value of the property columnCount. */
    private int[]   columnCount;
    /** Holds the value of the property topMargin. */
    private int[]   topMargin;
    /** Holds the value of the property rightMargin. */
    private int[]   rightMargin;
    /** Holds the value of the property bottomMargin. */
    private int[]   bottomMargin;
    /** Holds the value of the property leftMargin. */
    private int[]   leftMargin;
    /** Holds the unit used for the grid dimensions. */
    private String  unit;
    /** Holds the orientation of the grid (left or right). */
    private int     orientation;
    /** Holds the value of the property headerHeight. */
    private int[]     headerHeight;
    /** Holds the value of the property toolbarHeight. */
    private int[]     toolbarHeight;
    /** Holds the value of the property footerHeight. */
    private int[]    footerHeight;
    /** Holds the value of the property cssFile. */
    private String[]  cssFile;
    /** Determines, which modes are enabled with the current grid. **/
    private boolean[] modesEnabled;


    
    /**
     * Default constructor
     */
    public GridModel(){
        this.mode = GridModel.MODE_DESKTOP;
        this.headerHeight = new int[]{180,90,90};
        this.footerHeight = new int[]{130,70,70};
        this.toolbarHeight = new int[]{130,70,70};
        this.columnCount = new int[]{12,6,2};
        this.columnWidth = new int[]{60,60,60};
        this.topMargin = new int[]{10,5,2};
        this.rightMargin = new int[]{10,5,2};
        this.bottomMargin = new int[]{10,5,2};
        this.leftMargin = new int[]{10,5,2};
        this.modesEnabled = new boolean[]{true, false, false};
        this.cssFile = new String[]{"", "", ""};
        this.unit = "px";
        this.orientation = GridModel.ORIENTATION_LEFT;
    }

    /**
     * Get the value of mode
     * @return the value of mode.
     */
    public int getMode() {
        return mode;
    }

    /**
     * Set the value of mode
     * @param p_mode new value of mode
     */
    public void setMode(int p_mode) {
        if((p_mode < GridModel.MODE_DESKTOP) || (p_mode > GridModel.MODE_SMALL)){
            throw new RuntimeException("Only values between " + String.valueOf(GridModel.MODE_DESKTOP) +
                  " and " + String.valueOf(GridModel.MODE_SMALL) + " are allowed for mode !");
        }
        if(!isModeEnabled(p_mode)){
            throw new RuntimeException("Can't set a mode, if it's not enabled. Please enable mode " + String.valueOf(p_mode) + " first !");
        }
        synchronized(this){
            this.mode = p_mode;
        }
    }
    
    /**
     * Enables a mode (desktop, medium size or small size).
     * @param p_mode The mode to be enabled.
     */
    public void enableMode(int p_mode){
        if((p_mode < GridModel.MODE_DESKTOP) || (p_mode > GridModel.MODE_SMALL)){
            throw new RuntimeException("Only values between " + String.valueOf(GridModel.MODE_DESKTOP) +
                  " and " + String.valueOf(GridModel.MODE_SMALL) + " are allowed for mode !");
        }
        synchronized(this){
            modesEnabled[p_mode] = true;
        }
    }
    
    /**
     * Disables a mode (desktop, medium size or small size).
     * @param p_mode The mode to be disabled.
     */
    public void disableMode(int p_mode){
        if((p_mode < GridModel.MODE_MEDIUM) || (p_mode > GridModel.MODE_SMALL)){
            throw new RuntimeException("Only modes between " + String.valueOf(GridModel.MODE_MEDIUM) +
                  " (medium size) and " + String.valueOf(GridModel.MODE_SMALL) + " (small size are allowed to be disabled !");
        }
        synchronized(this){
            modesEnabled[p_mode] = false;
            if(this.mode == p_mode){
                this.mode = GridModel.MODE_DESKTOP;
            }
        }
    }
    
    /**
     * Checks if a mode  (desktop, medium size or small size) is enabled for this grid model.
     * @param p_mode The mode to be checked.
     * @return 
     */
    public boolean isModeEnabled(int p_mode){
        if((p_mode < GridModel.MODE_DESKTOP) || (p_mode > GridModel.MODE_SMALL)){
            throw new RuntimeException("Only values between " + String.valueOf(GridModel.MODE_DESKTOP) +
                  " and " + String.valueOf(GridModel.MODE_SMALL) + " are allowed for mode !");
        }
        synchronized(this){
            return modesEnabled[p_mode];
        }
    }
    
    /**
     * Get the value of orientation
     * @return the value of orientation
     */
    public int getOrientation() {
        return orientation;
    }

    /**
     * Set the value of orientation
     * @param orientation new value of orientation
     */
    public void setOrientation(int orientation) {
        // Abort when specified an invalid value for the orientation.
        if((orientation != GridModel.ORIENTATION_LEFT) && (orientation != GridModel.ORIENTATION_RIGHT)){
            throw new RuntimeException("Tried to specify a wrong value for the orientation. Value has to be "
                    + String.valueOf(GridModel.ORIENTATION_LEFT) + " (left orientation) or " + 
                    String.valueOf(GridModel.ORIENTATION_RIGHT) + " (right orientation) !");
        }
        checkStatus(this.orientation, orientation);
        this.orientation = orientation;
    }

    /**
     * Get the value of columnCount
     * @return the value of columnCount
     */
    public int getColumnCount() {
        synchronized(this){
            return columnCount[getMode()];
        }
    }


    /**
     * Get the value of columnCount
     * @param p_mode The grid mode, which the column count is to be got for.
     * @return the value of columnCount
     */
    public int getColumnCount(int p_mode) {
        if((p_mode < GridModel.MODE_DESKTOP) || (p_mode > GridModel.MODE_SMALL)){
            throw new RuntimeException("Only values between " + String.valueOf(GridModel.MODE_DESKTOP) +
                  " and " + String.valueOf(GridModel.MODE_SMALL) + " are allowed for mode !");
        }
        if(!isModeEnabled(p_mode)){
            throw new RuntimeException("Can't get the value for a disabled mode. Please enabe mode " + 
                  String.valueOf(p_mode) + " first !");
        }
        synchronized(this){
            return columnCount[p_mode];
        }
    }

    /**
     * Set the value of columnCount
     * @param columnCount new value of columnCount
     * @throws ThemeConfigException When an invalid value is passed as parameter.
     */
    public void setColumnCount(int columnCount) throws ThemeConfigException{
        // Abort with an exception, when coluzmnCount has an invalid value.
        // Note that only 12 or 16 are accepted as value.
        if((columnCount != 2) && (columnCount != 3) && (columnCount != 6) && (columnCount != 12) && (columnCount != 16) && (columnCount != 24)){
            throw new ThemeConfigException("Tried to initialize the grid with an invalid column count. Only 2, 3, 6, 12, 16 and "
                    + "24 are allowed as values. Therefore the setter has been aborted !");
        }
        synchronized(this){
            checkStatus(this.columnCount[getMode()], columnCount);
            this.columnCount[getMode()] = columnCount;
        }
    }

    /**
     * Get the value of columnWidth
     * @return the value of columnWidth
     */
    public int getColumnWidth() {
        synchronized(this){
            return columnWidth[getMode()];
        }
    }


    /**
     * Get the value of columnWidth
     * @param p_mode The grid mode, which the column width is to be got for.
     * @return the value of columnWidth
     */
    public int getColumnWidth(int p_mode) {
        if((p_mode < GridModel.MODE_DESKTOP) || (p_mode > GridModel.MODE_SMALL)){
            throw new RuntimeException("Only values between " + String.valueOf(GridModel.MODE_DESKTOP) +
                  " and " + String.valueOf(GridModel.MODE_SMALL) + " are allowed for mode !");
        }
        if(!isModeEnabled(p_mode)){
            throw new RuntimeException("Can't get the value for a disabled mode. Please enabe mode " + 
                  String.valueOf(p_mode) + " first !");
        }
        synchronized(this){
            return columnWidth[p_mode];
        }
    }
    
    /**
     * Gets the column width as a formatted string also containg the unit which the value is related to.
     * @return The formatted column  width.
     */
    public String getFormattedColumnWidth(){
        return formatValue(getColumnWidth(), unit);
    }
    
    /**
     * Gets the column width as a formatted string also containing the unit which the value is related to.
     * @param p_mode The grid mode, which the formatted column width is to be got for.
     * @return The formatted column  width.
     */
    public String getFormattedColumnWidth(int p_mode){
        return formatValue(getColumnWidth(p_mode), unit);
    }
    
    /**
     * Set the value of columnWidth
     * @param columnWidth new value of columnWidth
     * @throws ThemeConfigException When trying to specify an invalid value for the columns width
     */
    public void setColumnWidth(int columnWidth) throws ThemeConfigException{
        // Abort with an exception, when an invalaid value was specified for the column width.
        if(columnWidth <= 0){
           throw new ThemeConfigException("Tried to specify an invalid value for the column width. Only values "
                   + "greater than 0 are allowed !");
        }
        synchronized(this){
            checkStatus(this.columnWidth[getMode()], columnWidth);
            this.columnWidth[getMode()] = columnWidth;
        }
    }

    /**
     * Get the value of topMargin
     * @return the value of topMargin
     */
    public int getTopMargin() {
        synchronized(this){
            return topMargin[getMode()];
        }
    }

    /**
     * Get the value of topMargin
     * @param p_mode The mode which the top margin is to be got for.
     * @return the value of topMargin
     */
    public int getTopMargin(int p_mode) {
        if((p_mode < GridModel.MODE_DESKTOP) || (p_mode > GridModel.MODE_SMALL)){
            throw new RuntimeException("Only values between " + String.valueOf(GridModel.MODE_DESKTOP) +
                  " and " + String.valueOf(GridModel.MODE_SMALL) + " are allowed for mode !");
        }
        if(!isModeEnabled(p_mode)){
            throw new RuntimeException("Can't get the value for a disabled mode. Please enabe mode " + 
                  String.valueOf(p_mode) + " first !");
        }
        synchronized(this){
            return topMargin[p_mode];
        }
    }

    /**
     * Gets the top margin as a formatted string also containing the unit which the value is related to.
     * @return The formatted top margin.
     */
    public String getFormattedTopMargin(){
        return formatValue(getTopMargin(), unit);
    }
    
    /**
     * Gets the top margin as a formatted string also containing the unit which the value is related to.
     * @param p_mode The mode which the formatted top margin is to be got for.
     * @return The formatted top margin.
     */
    public String getFormattedTopMargin(int p_mode){
        return formatValue(getTopMargin(p_mode), unit);
    }
    
    /**
     * Set the value of topMargin
     * @param topMargin new value of topMargin
     * @throws ThemeConfigException When tried to specify an invalid value for the property. 
     */
    public void setTopMargin(int topMargin) throws ThemeConfigException{
        // Abort with an exception, when tried to specify an invalid value for the top margin
        if((topMargin < 0) || (topMargin > 15)){
            throw new ThemeConfigException("Tried to specify an invalid value for the top margin. Only values "
                    + "between 0 and 15 are allowed !");
        }
        synchronized(this){
            checkStatus(this.topMargin[getMode()], topMargin);
            this.topMargin[getMode()] = topMargin;
        }
    }

    /**
     * Get the value of rightMargin
     * @return the value of rightMargin
     */
    public int getRightMargin() {
        synchronized(this){
            return rightMargin[getMode()];
        }
    }

    /**
     * Get the value of rightMargin
     * @param p_mode The mode which the right margin is to be got for.
     * @return the value of rightMargin
     */
    public int getRightMargin(int p_mode) {
        if((p_mode < GridModel.MODE_DESKTOP) || (p_mode > GridModel.MODE_SMALL)){
            throw new RuntimeException("Only values between " + String.valueOf(GridModel.MODE_DESKTOP) +
                  " and " + String.valueOf(GridModel.MODE_SMALL) + " are allowed for mode !");
        }
        if(!isModeEnabled(p_mode)){
            throw new RuntimeException("Can't get the value for a disabled mode. Please enabe mode " + 
                  String.valueOf(p_mode) + " first !");
        }
        synchronized(this){
            return rightMargin[p_mode];
        }
    }

    /**
     * Gets the right margin as a formatted string also containing the unit which the value is related to.
     * @return The formatted right margin.
     */
    public String getFormattedRightMargin(){
        return formatValue(getRightMargin(), unit);
    }
    
    /**
     * Gets the right margin as a formatted string also containing the unit which the value is related to.
     * @param p_mode The mode which the formatted right margin is to be got for.
     * @return The formatted right margin.
     */
    public String getFormattedRightMargin(int p_mode){
        return formatValue(getRightMargin(p_mode), unit);
    }
    
    /**
     * Set the value of rightMargin
     * @param rightMargin new value of rightMargin
     * @throws ThemeConfigException When tried to specify an invalid value for the property. 
     */
    public void setRightMargin(int rightMargin) throws ThemeConfigException{
        // Abort with an exception, when tried to specify an invalid value for the right margin
        if((rightMargin < 0) || (rightMargin > 15)){
            throw new ThemeConfigException("Tried to specify an invalid value for the right margin. Only values "
                    + "between 0 and 15 are allowed !");
        }
        synchronized(this){
            checkStatus(this.rightMargin[getMode()], rightMargin);
            this.rightMargin[getMode()] = rightMargin;
        }
    }

    /**
     * Get the value of bottomMargin
     * @return the value of bottomMargin
     */
    public int getBottomMargin() {
        synchronized(this){
            return bottomMargin[getMode()];
        }
    }


    /**
     * Get the value of bottomMargin
     * @param p_mode The mode, which the bottom margin is to b got for.
     * @return the value of bottomMargin
     */
    public int getBottomMargin(int p_mode) {
        if((p_mode < GridModel.MODE_DESKTOP) || (p_mode > GridModel.MODE_SMALL)){
            throw new RuntimeException("Only values between " + String.valueOf(GridModel.MODE_DESKTOP) +
                  " and " + String.valueOf(GridModel.MODE_SMALL) + " are allowed for mode !");
        }
        if(!isModeEnabled(p_mode)){
            throw new RuntimeException("Can't get the value for a disabled mode. Please enabe mode " + 
                  String.valueOf(p_mode) + " first !");
        }
        synchronized(this){
            return bottomMargin[p_mode];
        }
    }
    
    /**
     * Gets the bottom margin as a formatted string also containing the unit which the value is related to.
     * @return The formatted bottom margin.
     */
    public String getFormattedBottomMargin(){
        return formatValue(getBottomMargin(), unit);
    }
    
    /**
     * Gets the bottom margin as a formatted string also containing the unit which the value is related to.
     * @param p_mode The mode, which the formatted bottom margin is to b got for.
     * @return The formatted bottom margin.
     */
    public String getFormattedBottomMargin(int p_mode){
        return formatValue(getBottomMargin(p_mode), unit);
    }
    
    /**
     * Set the value of bottomMargin
     * @param bottomMargin new value of bottomMargin
     * @throws ThemeConfigException When tried to specify an invalid value for the property. 
     */
    public void setBottomMargin(int bottomMargin) throws ThemeConfigException{
        // Abort with an exception, when tried to specify an invalid value for the bottom margin
        if((bottomMargin < 0) || (bottomMargin > 15)){
            throw new ThemeConfigException("Tried to specify an invalid value for the bottom margin. Only values "
                    + "between 0 and 15 are allowed !");
        }
        synchronized(this){
            checkStatus(this.bottomMargin[getMode()], bottomMargin);
            this.bottomMargin[getMode()] = bottomMargin;
        }
    }

    /**
     * Get the value of leftMargin
     * @return the value of leftMargin
     */
    public int getLeftMargin() {
        synchronized(this){
            return leftMargin[getMode()];
        }
    }

    /**
     * Get the value of leftMargin
     * @param p_mode The mode, which the left margin is to be got for.
     * @return the value of leftMargin
     */
    public int getLeftMargin(int p_mode) {
        if((p_mode < GridModel.MODE_DESKTOP) || (p_mode > GridModel.MODE_SMALL)){
            throw new RuntimeException("Only values between " + String.valueOf(GridModel.MODE_DESKTOP) +
                  " and " + String.valueOf(GridModel.MODE_SMALL) + " are allowed for mode !");
        }
        if(!isModeEnabled(p_mode)){
            throw new RuntimeException("Can't get the value for a disabled mode. Please enabe mode " + 
                  String.valueOf(p_mode) + " first !");
        }
        synchronized(this){
            return leftMargin[p_mode];
        }
    }

    /**
     * Gets the left margin as a formatted string also containing the unit which the value is related to.
     * @return The formatted left margin.
     */
    public String getFormattedLeftMargin(){
        return formatValue(getLeftMargin(), unit);
    }
    
    /**
     * Gets the left margin as a formatted string also containing the unit which the value is related to.
     * @param p_mode The mode, which the formatted left margin is to be got for.
     * @return The formatted left margin.
     */
    public String getFormattedLeftMargin(int p_mode){
        return formatValue(getLeftMargin(p_mode), unit);
    }
    
    /**
     * Set the value of leftMargin
     * @param leftMargin new value of leftMargin
     * @throws ThemeConfigException When tried to specify an invalid value for the property. 
     */
    public void setLeftMargin(int leftMargin) throws ThemeConfigException{
        // Abort with an exception, when tried to specify an invalid value for the left margin
        if((leftMargin < 0) || (leftMargin > 15)){
            throw new ThemeConfigException("Tried to specify an invalid value for the left margin. Only values "
                    + "between 0 and 15 are allowed !");
        }
        synchronized(this){
            checkStatus(this.leftMargin[getMode()], leftMargin);
            this.leftMargin[getMode()] = leftMargin;
        }
    }

    /**
     * Get the value of pageWidth
     * @return the value of pageWidth
     */
    public int getPageWidth() {
        return getColumnCount() * getColumnWidth() + (getColumnCount() + 1) * (getLeftMargin() + getRightMargin());
    }
    
    /**
     * Get the value of pageWidth
     * @param p_mode The mode, which the page width is to be got for.
     * @return the value of pageWidth
     */
    public int getPageWidth(int p_mode) {
        if((p_mode < GridModel.MODE_DESKTOP) || (p_mode > GridModel.MODE_SMALL)){
            throw new RuntimeException("Only values between " + String.valueOf(GridModel.MODE_DESKTOP) +
                  " and " + String.valueOf(GridModel.MODE_SMALL) + " are allowed for mode !");
        }
        if(!isModeEnabled(p_mode)){
            throw new RuntimeException("Can't get the value for a disabled mode. Please enabe mode " + 
                  String.valueOf(p_mode) + " first !");
        }
        return getColumnCount(p_mode) * getColumnWidth(p_mode) + (getColumnCount(p_mode) + 1) * (getLeftMargin(p_mode) + getRightMargin(p_mode));
    }
    
    /**
     * Gets the page width as a formatted string also containing the unit which the value is related to.
     * @return The formatted page width.
    */
    public String getFormattedPageWidth(){
        return formatValue(getPageWidth(), unit);
    }
    
    /**
     * Gets the page width as a formatted string also containing the unit which the value is related to.
     * @param p_mode The mode, which the page width is to be got for.
     * @return The formatted page width.
    */
    public String getFormattedPageWidth(int p_mode){
        return formatValue(getPageWidth(p_mode), unit);
    }
    
    /**
     * Get the value of headerHeight
     * @return the value of headerHeight
     */
    public int getHeaderHeight() {
        synchronized(this){
            return headerHeight[getMode()];
        }
    }

    /**
     * Get the value of headerHeight
     * @param p_mode The mode, which the header height is to be got for.
     * @return the value of headerHeight
     */
    public int getHeaderHeight(int p_mode) {
        if((p_mode < GridModel.MODE_DESKTOP) || (p_mode > GridModel.MODE_SMALL)){
            throw new RuntimeException("Only values between " + String.valueOf(GridModel.MODE_DESKTOP) +
                  " and " + String.valueOf(GridModel.MODE_SMALL) + " are allowed for mode !");
        }
        if(!isModeEnabled(p_mode)){
            throw new RuntimeException("Can't get the value for a disabled mode. Please enabe mode " + 
                  String.valueOf(p_mode) + " first !");
        }
        synchronized(this){
            return headerHeight[p_mode];
        }
    }

    /**
     * Gets the header height as a formatted string also containing the unit which the value is related to.
     * @return The formatted header height.
     */
    public String getFormattedHeaderHeight() {
        return formatValue(getHeaderHeight(), unit);
    }

    /**
     * Gets the header height as a formatted string also containing the unit which the value is related to.
     * @param p_mode The mode, which the formatted header height is to be got for.
     * @return The formatted header height.
     */
    public String getFormattedHeaderHeight(int p_mode) {
        return formatValue(getHeaderHeight(p_mode), unit);
    }

    /**
     * Set the value of headerHeight
     * @param headerHeight new value of headerHeight
     */
    public void setHeaderHeight(int headerHeight) {
        synchronized(this){
            checkStatus(this.headerHeight[getMode()], headerHeight);
            this.headerHeight[getMode()] = headerHeight;
        }
    }


    /**
     * Get the value of toolbarHeight
     * @return the value of toolbarHeight
     */
    public int getToolbarHeight() {
        synchronized(this){
            return toolbarHeight[getMode()];
        }
    }

    /**
     * Get the value of toolbarHeight
     * @param p_mode The mode, which the toolbar height is to be got for.
     * @return the value of toolbarHeight
     */
    public int getToolbarHeight(int p_mode) {
        if((p_mode < GridModel.MODE_DESKTOP) || (p_mode > GridModel.MODE_SMALL)){
            throw new RuntimeException("Only values between " + String.valueOf(GridModel.MODE_DESKTOP) +
                  " and " + String.valueOf(GridModel.MODE_SMALL) + " are allowed for mode !");
        }
        if(!isModeEnabled(p_mode)){
            throw new RuntimeException("Can't get the value for a disabled mode. Please enabe mode " + 
                  String.valueOf(p_mode) + " first !");
        }
        synchronized(this){
            return toolbarHeight[p_mode];
        }
    }

    /**
     * Gets the toolbar height as a formatted string also containing the unit which the value is related to.
     * @return The formatted toolbar height
     */
    public String getFormattedToolbarHeight() {
        return formatValue(getToolbarHeight(), unit);
    }

    /**
     * Gets the toolbar height as a formatted string also containing the unit which the value is related to.
     * @param p_mode The mode, which the formatted toolbar height is to be got for.
     * @return The formatted toolbar height
     */
    public String getFormattedToolbarHeight(int p_mode) {
        return formatValue(getToolbarHeight(p_mode), unit);
    }

    /**
     * Set the value of toolbarHeight
     * @param toolbarHeight new value of toolbarHeight
     */
    public void setToolbarHeight(int toolbarHeight) {
        synchronized(this){
            checkStatus(this.toolbarHeight[getMode()], toolbarHeight);
            this.toolbarHeight[getMode()] = toolbarHeight;
        }
    }


    /**
     * Get the value of footerHeight
     * @return the value of footerHeight
     */
    public int getFooterHeight() {
        synchronized(this){
            return footerHeight[getMode()];
        }
    }

    /**
     * Get the value of footerHeight
     * @param p_mode The mode, which the footer height is to be got for.
     * @return the value of footerHeight
     */
    public int getFooterHeight(int p_mode) {
        if((p_mode < GridModel.MODE_DESKTOP) || (p_mode > GridModel.MODE_SMALL)){
            throw new RuntimeException("Only values between " + String.valueOf(GridModel.MODE_DESKTOP) +
                  " and " + String.valueOf(GridModel.MODE_SMALL) + " are allowed for mode !");
        }
        if(!isModeEnabled(p_mode)){
            throw new RuntimeException("Can't get the value for a disabled mode. Please enabe mode " + 
                  String.valueOf(p_mode) + " first !");
        }
        synchronized(this){
            return footerHeight[p_mode];
        }
    }

    /**
     * Gets the footer height as a formatted string also containing the unit which the value is related to.
     * @return The formatted footer height.
     */
    public String getFormattedFooterHeight() {
        return formatValue(getFooterHeight(), unit);
    }

    /**
     * Gets the footer height as a formatted string also containing the unit which the value is related to.
     * @param p_mode The mode, which the formatted footer height is to be got for.
     * @return The formatted footer height.
     */
    public String getFormattedFooterHeight(int p_mode) {
        return formatValue(getFooterHeight(p_mode), unit);
    }

    /**
     * Set the value of footerHeight
     * @param footerHeight new value of footerHeight
     */
    public void setFooterHeight(int footerHeight) {
        synchronized(this){
            checkStatus(this.footerHeight[getMode()], footerHeight);
            this.footerHeight[getMode()] = footerHeight;
        }
    }

    /**
     * Get the effective width of a set of columns in pixels.
     * @param p_columnCount The number of columns to get the width in pixels for.
     * @return The effective width in pixels.
     * @throws ThemeConfigException When tried to specify an invalid number of columns. 
     */
    public int getWidthForColumns(int p_columnCount) throws ThemeConfigException{
        // Abort with an exception, when tried to specify an invalid number of columns
        if((p_columnCount < 0) || (p_columnCount > this.columnCount[getMode()])){
            throw new ThemeConfigException("Tried to specify an invalid number of columns to get the effective width "
                    + "in pixels. Only a value between 1 and \"columnCount\" is allowed !");
        }
        return (p_columnCount * getColumnWidth()) + ((p_columnCount -1) * (getLeftMargin() + getRightMargin()));
    }

    /**
     * Get the effective width of a set of columns in pixels and as a formatted string, which also contains
     * the unit, which the value is related to
     * @param p_columnCount The number of columns to get the width in pixels for.
     * @return The effective width in pixels as a string with the related unit.
     * @throws ThemeConfigException When tried to specify an invalid number of columns. 
     */
    public String getFormattedWidthForColumns(int p_columnCount) throws ThemeConfigException{
        return formatValue(getWidthForColumns(p_columnCount), unit);
    }

    /**
     * Get the value of cssFile
     * @return the value of cssFile
     */
    public String getCssFile() {
        synchronized(this){
            return cssFile[getMode()];
        }
    }

    /**
     * Get the value of cssFile
     * @param p_mode The mode, which the CSS file is to be got for.
     * @return the value of cssFile
     */
    public String getCssFile(int p_mode) {
        if((p_mode < GridModel.MODE_DESKTOP) || (p_mode > GridModel.MODE_SMALL)){
            throw new RuntimeException("Only values between " + String.valueOf(GridModel.MODE_DESKTOP) +
                  " and " + String.valueOf(GridModel.MODE_SMALL) + " are allowed for mode !");
        }
        if(!isModeEnabled(p_mode)){
            throw new RuntimeException("Can't get the value for a disabled mode. Please enabe mode " + 
                  String.valueOf(p_mode) + " first !");
        }
        synchronized(this){
            return cssFile[p_mode];
        }
    }

    /**
     * Set the value of cssFile
     * @param cssFile new value of cssFile
     */
    public void setCssFile(String cssFile) {
        synchronized(this){
            checkStatus(this.cssFile[getMode()], cssFile);
            this.cssFile[getMode()] = cssFile;
        }
    }
    
    /**
     * Gets the unit for the dimensions defined in the grid
     * @return 
     */
    public String getUnit(){
        return unit;
    }
    
    /**
     * 
     * @param p_actionEl
     * @param p_fileName
     * @return 
     */
    public String generateGenericCSSCode(CmsJspActionElement p_actionEl, String p_fileName){
        String              result;
        SimpleDateFormat    format       = new SimpleDateFormat("E, MMM dd yyyy, HH:mm:ss");
        StringWriter        out          = new StringWriter();
        int                 initialMode;
        int                 lastLimit;
        int                 currentLimit;
        String              currentCss;
        String              fileName;
        
        synchronized(this){
            // Process the file name
            fileName = p_fileName;
            if((fileName != null) && (fileName.trim().length() > 0)){
                fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
            }else{
                fileName = "";
            }
            
            // Remember initial mode and start with desktop mode.
            initialMode = getMode();
            
            out.append("/*").append("\n");
            out.append(" Document : ").append(fileName).append("\n");

            out.append(" Created on ").append(format.format(new Date())).append("\n");
            out.append(" Copyright (C) 2012 Robert Diawara").append("\n");
            out.append("\n");
            out.append(" This file is part of skinnDriva.").append("\n");
            out.append("\n");
            out.append(" OpenCms Theme Engine is free software: you can redistribute it and/or modify").append("\n");
            out.append(" it under the terms of the GNU Lesser General Public License as published by").append("\n");
            out.append(" the Free Software Foundation, either version 3 of the License, or").append("\n");
            out.append(" (at your option) any later version.").append("\n");
            out.append("\n");
            out.append(" OpenCms Theme Engine is distributed in the hope that it will be useful,").append("\n");
            out.append(" but WITHOUT ANY WARRANTY; without even the implied warranty of").append("\n");
            out.append(" MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the").append("\n");
            out.append(" GNU Lesser General Public License for more details.").append("\n");
            out.append("\n");
            out.append(" You should have received a copy of the GNU Lesser General Public License").append("\n");
            out.append(" along with OpenCms Theme Engine.  If not, see <http://www.gnu.org/licenses/>.").append("\n");
            out.append("\n");
            out.append(" -----------------------------------------------------------------------------------").append("\n");
            out.append("\n");
            out.append(" Grid for OpenCms Theme Engine ~ Core CSS.  This is automatically generated CSS code.").append("\n");
            out.append(" Please do not modify.  Learn more ~ http://www.diawara.com/ \n*/\n\n");
            
            
            // Generate CSS code
            setMode(GridModel.MODE_DESKTOP);
            currentLimit = getPageWidth() + 30;
            currentCss = p_actionEl.link(getCssFile());
            out.append("/*The CSS File for large screens. */\n");
            out.append("@import url(").append(currentCss).append(") screen and (min-width: ").append(String.valueOf(currentLimit)).
                  append(unit).append(") and (min-device-width: ").append(String.valueOf(GridModel.DESKTOP_SIZE_LIMIT)).append("px);");
            lastLimit = currentLimit;
            
            if(isModeEnabled(GridModel.MODE_MEDIUM)){
                setMode(GridModel.MODE_MEDIUM);
                currentLimit = getPageWidth() + 30;
                currentCss = p_actionEl.link(getCssFile());
                out.append("\n\n/*The CSS File for medium size screens. */\n");
                out.append("@import url(").append(currentCss).append(") screen and (min-width: ").append(String.valueOf(currentLimit)).
                      append(unit).append(") and (max-width: ").append(String.valueOf(lastLimit - 1)).append(unit).
                      append(") and (min-device-width: ").append(String.valueOf(GridModel.DESKTOP_SIZE_LIMIT)).append("px);\n");
                out.append("@import url(").append(currentCss).append(") screen and (min-width: ").append(String.valueOf(currentLimit)).
                      append(unit).append(") and (max-width: ").append(String.valueOf(lastLimit - 1)).append(unit).
                      append(") and (max-device-width: ").append(String.valueOf(GridModel.DESKTOP_SIZE_LIMIT - 1)).append("px);");
                lastLimit = currentLimit;
                
                if(isModeEnabled(GridModel.MODE_SMALL)){
                    setMode(GridModel.MODE_SMALL);
                    currentCss = p_actionEl.link(getCssFile());
                    out.append("\n\n/*The CSS File for small size screens. */\n");
                    out.append("@import url(").append(currentCss).append(") screen and (max-width: ").append(String.valueOf(lastLimit - 1)).
                          append(unit).append(") and (min-device-width: ").append(String.valueOf(GridModel.DESKTOP_SIZE_LIMIT)).append("px);\n");
                    out.append("@import url(").append(currentCss).append(") screen and (max-width: ").append(String.valueOf(lastLimit - 1)).
                          append(unit).append(") and (max-device-width: ").append(String.valueOf(GridModel.DESKTOP_SIZE_LIMIT - 1)).append("px);");
                }
            }else if(isModeEnabled(GridModel.MODE_SMALL)){
                setMode(GridModel.MODE_SMALL);
                currentCss = p_actionEl.link(getCssFile());
                out.append("\n\n/*The CSS File for medium and small size screens. */\n");
                out.append("@import url(").append(currentCss).append(") screen and (max-width: ").append(String.valueOf(lastLimit - 1)).
                      append(unit).append(") and (min-device-width: ").append(String.valueOf(GridModel.DESKTOP_SIZE_LIMIT)).append("px);\n");
                out.append("@import url(").append(currentCss).append(") screen and (max-width: ").append(String.valueOf(lastLimit - 1)).
                      append(unit).append(") and (max-device-width: ").append(String.valueOf(GridModel.DESKTOP_SIZE_LIMIT - 1)).append("px);");
            }
            
            /*
            out.append("\n\n@media screen and (max-width: ").append(String.valueOf(lastLimit -1)).
                  append(unit).append(") and (min-device-width: ").append(String.valueOf(GridModel.DESKTOP_SIZE_LIMIT)).append("px){\n");
            out.append("}");
            */
            
            // Reset to remembered initial mode.
            setMode(initialMode);
            
            // Create the result string
            result = out.toString();
        }
        return result;
    }

    /**
     * Generates the CSS code which represents the implementation of the grid.
     * @return The CSS Code.
     * @throws ThemeConfigException When something fails during the code generation.
     */
    public String generateCSSCode() throws ThemeConfigException{
        StringWriter     out               = new StringWriter();
        int              loopCount;
        SimpleDateFormat format            = new SimpleDateFormat("E, MMM dd yyyy, HH:mm:ss");
        String           fileName;
        String           prefix;
        String           result;
        String           containerMaxWidth;
        String           containerMinWidth;
        
        synchronized(this){
            // Process the file name
            fileName    = getCssFile();
            if((fileName != null) && (fileName.trim().length() > 0)){
                fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
            }else{
                fileName = "";
            }
            
            // Process the prefix
            switch(getMode()){
                case GridModel.MODE_MEDIUM:
                    prefix = "m_";
                    containerMaxWidth = getFormattedPageWidth(GridModel.MODE_DESKTOP);
                    containerMinWidth = getFormattedPageWidth();
                    break;
                case GridModel.MODE_SMALL:
                    prefix = "s_";
                    containerMaxWidth = getFormattedPageWidth(GridModel.MODE_MEDIUM);
                    containerMinWidth = "310px";
                    break;
                default:
                    prefix = "";
                    containerMaxWidth = "100%";
                    containerMinWidth = getFormattedPageWidth(GridModel.MODE_DESKTOP);
            }

            out.append("/*").append("\n");
            out.append(" Document : ").append(fileName).append("\n");

            out.append(" Created on ").append(format.format(new Date())).append("\n");
            out.append(" Copyright (C) 2012 Robert Diawara").append("\n");
            out.append("\n");
            out.append(" This file is part of OpenCms Theme Engine.").append("\n");
            out.append("\n");
            out.append(" OpenCms Theme Engine is free software: you can redistribute it and/or modify").append("\n");
            out.append(" it under the terms of the GNU Lesser General Public License as published by").append("\n");
            out.append(" the Free Software Foundation, either version 3 of the License, or").append("\n");
            out.append(" (at your option) any later version.").append("\n");
            out.append("\n");
            out.append(" OpenCms Theme Engine is distributed in the hope that it will be useful,").append("\n");
            out.append(" but WITHOUT ANY WARRANTY; without even the implied warranty of").append("\n");
            out.append(" MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the").append("\n");
            out.append(" GNU Lesser General Public License for more details.").append("\n");
            out.append("\n");
            out.append(" You should have received a copy of the GNU Lesser General Public License").append("\n");
            out.append(" along with OpenCms Theme Engine.  If not, see <http://www.gnu.org/licenses/>.").append("\n");
            out.append("\n");
            out.append(" -----------------------------------------------------------------------------------").append("\n");
            out.append("\n");
            out.append(" Grid for OpenCms Theme Engine ~ Core CSS.  This is automatically generated CSS code.").append("\n");
            out.append(" Please do not modify.  Learn more ~ http://www.diawara.com/").append("\n");
            out.append("*/").append("\n");
            out.append("\n");
            out.append("\n");
            out.append("\n");
            out.append("/* `Individual formats").append("\n");
            out.append("----------------------------------------------------------------------------------------------------*/").append("\n");
            out.append("\n");
            out.append("#GRID_HEADER{").append("\n");
            out.append("    height              : ").append(getFormattedHeaderHeight()).append(";").append("\n");
            out.append("}").append("\n");
            out.append("\n");
            out.append("#GRID_FOOTER{").append("\n");
            out.append("    height              : ").append(getFormattedFooterHeight()).append(";").append("\n");
            out.append("}").append("\n");
            out.append("\n");
            out.append("#GRID_TOOLBAR{").append("\n");
            out.append("    height              : ").append(getFormattedToolbarHeight()).append(";").append("\n");
            out.append("}").append("\n");
            out.append("\n");
            out.append("/*").append("\n");
            out.append("  Forces backgrounds to span full width,").append("\n");
            out.append("  even if there is horizontal scrolling.").append("\n");
            out.append("  Increase this if your layout is wider.").append("\n");
            out.append("\n");
            out.append("  Note: IE6 works fine without this fix.").append("\n");
            out.append("*/").append("\n");
            out.append("\n");
            out.append("body {").append("\n");
            out.append("    min-width           : ").append(containerMinWidth).append(";\n");
            out.append("}").append("\n");
            out.append("\n");
            out.append("/* Container").append("\n");
            out.append("----------------------------------------------------------------------------------------------------*/").append("\n");
            out.append("\n");
            out.append(".").append(prefix).append("container_").append(String.valueOf(getColumnCount())).append(" {").append("\n");
            out.append("    margin-left         : auto;").append("\n");
            out.append("    margin-right        : auto;").append("\n");
            out.append("    width               : ").append(getFormattedPageWidth()).append(";").append("\n");
            out.append("    padding             : 0px 0px 0px 0px;\n");
            out.append("    z-index             : 10;\n");
            out.append("}").append("\n\n");
            out.append(".").append(prefix).append("container_full {").append("\n");
            out.append("    margin-left         : auto;").append("\n");
            out.append("    margin-right        : auto;").append("\n");
            out.append("    min-width           : ").append(containerMinWidth).append(";\n");
            out.append("    max-width           : ").append(containerMaxWidth).append(";\n");
            out.append("    padding             : 0px 0px 0px 0px;\n");
            out.append("    width               : 95%;").append("\n");
            out.append("    overflow            : auto;").append("\n");
            out.append("    box-sizing          : inherit;").append("\n");
            out.append("}").append("\n\n");
            out.append("/* Grid >> Global").append("\n");
            out.append("----------------------------------------------------------------------------------------------------*/").append("\n\n");
            out.append(".").append(prefix).append("grid_1");
            for(loopCount = 2; loopCount <= getColumnCount(); loopCount ++){
                out.append(",").append("\n").append(".").append(prefix).append("grid_").append(String.valueOf(loopCount));
            }
            out.append(" {").append("\n");
            out.append("    display             : inline;").append("\n");
            out.append("    float               : ").append(getOrientation() == GridModel.ORIENTATION_LEFT ? "left;\n" : "right;\n");
            out.append("    box-sizing          : border-box;").append("\n");
            out.append("    margin              : ").append(getFormattedTopMargin()).append(" ").append(getFormattedRightMargin()).append(" ").
                  append(getFormattedBottomMargin()).append(" ").append(getFormattedLeftMargin()).append(";").append("\n");
            out.append("}").append("\n\n");
            out.append(".").append(prefix).append("grid_full");
            out.append(" {").append("\n");
            out.append("    display             : inline;").append("\n");
            out.append("    float               : ").append(getOrientation() == GridModel.ORIENTATION_LEFT ? "left;\n" : "right;\n");
            out.append("    box-sizing          : border-box;").append("\n");
            out.append("    margin              : ").append(getFormattedTopMargin()).append(" 0px ").append(getFormattedBottomMargin()).append(" 0px;\n");
            out.append("}").append("\n\n");
            out.append(".").append(prefix).append("grid_full:first-child {\n");
            out.append("    margin-top          : 0px;\n}\n\n");
            out.append(".").append(prefix).append("push_1, .").append(prefix).append("pull_1");
            for(loopCount = 2; loopCount < getColumnCount(); loopCount ++){
                out.append(",").append("\n");
                out.append(".").append(prefix).append("push_").append(String.valueOf(loopCount)).append(", .").append(prefix).append("pull_").
                      append(String.valueOf(loopCount));
            }
            out.append(" {").append("\n");
            out.append("    position            : relative;").append("\n");
            out.append("}").append("\n");
            out.append("\n");
            out.append("/* Grid >> Children (Alpha ~ First, Omega ~ Last)").append("\n");
            out.append("----------------------------------------------------------------------------------------------------*/").append("\n");
            out.append("\n");
            out.append(".").append(prefix).append("alpha {").append("\n");
            out.append(orientation == GridModel.ORIENTATION_LEFT ? "    margin-left        : 0;" : "    margin-right        : 0;").append("\n");
            out.append("}").append("\n");
            out.append("\n");
            out.append(".").append(prefix).append("omega {").append("\n");
            out.append(orientation == GridModel.ORIENTATION_LEFT ? "    margin-right        : 0;" : "    margin-left         : 0;").append("\n");
            out.append("}").append("\n");
            out.append("\n");
            out.append("/* Grid >> individual box alignment (Left ~ Right)").append("\n");
            out.append("----------------------------------------------------------------------------------------------------*/").append("\n");
            out.append("\n");
            out.append(".").append(prefix).append("left_align {\n");
            out.append("    float               : left;\n");
            out.append("}\n\n");
            out.append(".").append(prefix).append("right_align {\n");
            out.append("    float               : right;\n");
            out.append("}\n\n");
            out.append("/* Grid >> ").append(String.valueOf(getColumnCount())).append(" Columns").append("\n");
            out.append("----------------------------------------------------------------------------------------------------*/").append("\n");
            out.append("\n");
            for(loopCount = 1; loopCount <= getColumnCount(); loopCount ++){
                out.append(".").append(prefix).append("container_").append(String.valueOf(getColumnCount())).append(" .").append(prefix).
                      append("grid_").append(String.valueOf(loopCount)).append(" {").append("\n");
                out.append("    width               : ").append(getFormattedWidthForColumns(loopCount)).append(";").append("\n");
                out.append("}").append("\n\n");
            }
            out.append(".").append(prefix).append("container_").append(String.valueOf(getColumnCount())).append(" .").append(prefix).append("grid_full,\n");
            out.append(".").append(prefix).append("container_full .").append(prefix).append("grid_full{\n");
            out.append("    width               : 100%;\n}\n\n");
                   
                    
            out.append("/* Invisible Elements").append("\n");
            out.append("----------------------------------------------------------------------------------------------------*/").append("\n");
            out.append(".").append(prefix).append("container_").append(String.valueOf(getColumnCount())).append(" .").append(prefix).
                  append("grid_hidden,\n.").append(prefix).append("container_full .").append(prefix).append("grid_hidden{\n");
            out.append("    display             : none;").append("\n");
            out.append("    visibility          : hidden;").append("\n");
            out.append("    font-size           : 0;").append("\n");
            out.append("    line-height         : 0;").append("\n");
            out.append("    width               : 0;").append("\n");
            out.append("    height              : 0;").append("\n");
            out.append("}\n\n");
            out.append("/* Prefix Extra Space >> ").append(String.valueOf(getColumnCount())).append(" Columns").append("\n");
            out.append("----------------------------------------------------------------------------------------------------*/").append("\n");
            out.append("\n");
            for(loopCount = 1; loopCount < getColumnCount(); loopCount ++){
                out.append(".").append(prefix).append("container_").append(String.valueOf(getColumnCount())).append(" .").append(prefix).
                      append("prefix_").append(String.valueOf(loopCount)).append(" {").append("\n");
                out.append(orientation == GridModel.ORIENTATION_LEFT ? "    padding-left        : " : "    padding-right       : ");
                out.append(formatValue(loopCount * (getLeftMargin() + getColumnWidth() + getRightMargin()), "px")).append(";").append("\n");
                out.append("}").append("\n\n");
            }
            out.append("/* Suffix Extra Space >> ").append(unit).append(" Columns").append("\n");
            out.append("----------------------------------------------------------------------------------------------------*/").append("\n");
            out.append("\n");
            for(loopCount = 1; loopCount < getColumnCount(); loopCount ++){
                out.append(".").append(prefix).append("container_").append(String.valueOf(getColumnCount())).append(" .").append(prefix).
                      append("suffix_").append(String.valueOf(loopCount)).append(" {").append("\n");

                out.append(orientation == GridModel.ORIENTATION_LEFT ? "    padding-right       : " : "    padding-left        : ");
                out.append(formatValue(loopCount * (getLeftMargin() + getColumnWidth() + getRightMargin()), "px")).append(";").append("\n");
                out.append("}").append("\n\n");
            }
            out.append("/* Push Space >> ").append(String.valueOf(getColumnCount())).append(" Columns").append("\n");
            out.append("----------------------------------------------------------------------------------------------------*/").append("\n");
            out.append("\n");
            for(loopCount = 1; loopCount < getColumnCount(); loopCount ++){
                out.append(".").append(prefix).append("container_").append(String.valueOf(getColumnCount())).append(" .").append(prefix).
                      append("push_").append(String.valueOf(loopCount)).append(" {").append("\n");
                out.append(orientation == GridModel.ORIENTATION_LEFT ? "    left                : " : "    right               : ");
                out.append(formatValue(loopCount * (getLeftMargin() + getColumnWidth() + getRightMargin()), "px")).append(";").append("\n");
                out.append("}").append("\n\n");
            }
            out.append("/* Pull Space >> ").append(String.valueOf(getColumnCount())).append(" Columns").append("\n");
            out.append("----------------------------------------------------------------------------------------------------*/").append("\n");
            out.append("\n");
            for(loopCount = 1; loopCount < getColumnCount(); loopCount ++){
                out.append(".").append(prefix).append("container_").append(String.valueOf(getColumnCount())).append(" .").append(prefix).
                      append("pull_").append(String.valueOf(loopCount)).append(" {").append("\n");
                out.append(orientation == GridModel.ORIENTATION_LEFT ? "    right                : -" : "    left               : -");
                out.append(formatValue(loopCount * (getLeftMargin() + getColumnWidth() + getRightMargin()), "px")).append(";").append("\n");
                out.append("}").append("\n\n");
            }
            out.append("/* Clear Floated Elements").append("\n");
            out.append("----------------------------------------------------------------------------------------------------*/").append("\n");
            out.append("\n");
            out.append("/* http://sonspring.com/journal/clearing-floats */").append("\n");
            out.append("\n");
            out.append(".").append(prefix).append("clear {").append("\n");
            out.append("    clear               : both;").append("\n");
            out.append("    display             : block;").append("\n");
            out.append("    overflow            : hidden;").append("\n");
            out.append("    visibility          : hidden;").append("\n");
            out.append("    width               : 0;").append("\n");
            out.append("    height              : 0;").append("\n"); 
            out.append("}").append("\n");
            out.append("\n");
            out.append("/* http://www.yuiblog.com/blog/2010/09/27/clearfix-reloaded-overflowhidden-demystified */").append("\n");
            out.append("\n");
            out.append(".clearfix:before,").append("\n");
            out.append(".clearfix:after,").append("\n");
            out.append(".").append(prefix).append("container_").append(String.valueOf(getColumnCount())).append(":before,").append("\n");
            out.append(".").append(prefix).append("container_").append(String.valueOf(getColumnCount())).append(":after {").append("\n");
            out.append("    content             : '.';").append("\n");
            out.append("    display             : block;").append("\n");
            out.append("    overflow            : hidden;").append("\n");
            out.append("    visibility          : hidden;").append("\n");
            out.append("    font-size           : 0;").append("\n");
            out.append("    line-height         : 0;").append("\n");
            out.append("    width               : 0;").append("\n");
            out.append("    height              : 0;").append("\n");
            out.append("}").append("\n");
            out.append("\n");
            out.append(".clearfix:after,").append("\n");
            out.append(".").append(prefix).append("container_").append(String.valueOf(getColumnCount())).append(":after {").append("\n");
            out.append("    clear               : both;").append("\n");
            out.append("}").append("\n");
            out.append("\n");
            out.append("/*").append("\n");
            out.append("  The following zoom:1 rule is specifically for IE6 + IE7.").append("\n");
            out.append("  Move to separate stylesheet or delete if invalid CSS is a problem.").append("\n");
            out.append("*/").append("\n");
            out.append("\n");
            out.append(".clearfix,").append("\n");
            out.append(".").append(prefix).append("container_").append(String.valueOf(getColumnCount())).append(" {").append("\n");
            out.append("    zoom                : 1;").append("\n");
            out.append("}");
            out.append("\n\n\n");
            
            out.append("/* Nested classic column layouts >> ").append(String.valueOf(getColumnCount())).append(" Columns").append("\n");
            out.append("----------------------------------------------------------------------------------------------------*/").append("\n");
            out.append(".").append(prefix).append("container_").append(String.valueOf(getColumnCount())).append(" .").append(prefix).append("table-layout,\n");
            out.append(".").append(prefix).append("container_full .").append(prefix).append("table-layout,\n");
            out.append(".").append(prefix).append("container_").append(String.valueOf(getColumnCount())).append(" .").append(prefix).append("grid-layout,\n");
            out.append(".").append(prefix).append("container_full .").append(prefix).append("grid-layout {\n");
            out.append("    width               : auto;\n");
            out.append("    height              : auto;\n");
            out.append("    overflow            : auto;\n");
            out.append("    z-index             : 10;\n");
            out.append("    box-sizing          : inherit;\n}\n\n");

            out.append(".").append(prefix).append("container_").append(String.valueOf(getColumnCount())).append(" .").append(prefix).append("grid-layout,\n");
            out.append(".").append(prefix).append("container_full .").append(prefix).append("grid-layout {\n");
            out.append("    margin              : ").append(getFormattedBottomMargin(getMode())).append(" ").append(getFormattedLeftMargin(getMode()))
                    .append(" ").append(getFormattedTopMargin(getMode())).append(" ").append(getFormattedRightMargin(getMode())).append(";\n");
            out.append("    padding             : 0px 0px 0px 0px;\n}\n\n");
            
            out.append(".").append(prefix).append("container_").append(String.valueOf(getColumnCount())).append(" .").append(prefix).append("table-layout,\n");
            out.append(".").append(prefix).append("container_full .").append(prefix).append("table-layout {\n");
            out.append("    margin              : 0px 0px 0px 0px;\n");
            out.append("    padding             : 0px 0px 0px 0px;\n}\n\n");
            
            out.append(".").append(prefix).append("container_").append(String.valueOf(getColumnCount())).append(" .").append(prefix).append("table-layout,\n");
            out.append(".").append(prefix).append("container_").append(String.valueOf(getColumnCount())).append(" .").append(prefix).append("grid-layout {\n");
            out.append("    width               : ").append(containerMinWidth).append(";\n}\n\n");
           
            out.append(".").append(prefix).append("container_full .").append(prefix).append("grid-layout,\n");
            out.append(".").append(prefix).append("container_full .").append(prefix).append("table-layout {\n");
            out.append("    min-width           : ").append(containerMinWidth).append(";\n}\n\n");
            
            out.append(".").append(prefix).append("container_").append(String.valueOf(getColumnCount())).append(" .").append(prefix).append("table-layout {\n");
            out.append("    display             : table;\n}\n\n");
            
            out.append(".").append(prefix).append("table-layout .").append(prefix).append("row {\n");
            out.append("    padding             : 0px 0px 0px 0px;\n");
            out.append("    margin              : 0px 0px 0px 0px;\n");
            out.append("    display             : table-row;\n}\n\n");
            
            out.append(".").append(prefix).append("table-layout .").append(prefix).append("row .").append(prefix).append("cell_1");
            for(loopCount = 2; loopCount <= getColumnCount(); loopCount ++){
                out.append(",\n.").append(prefix).append("table-layout .").append(prefix).append("row .").append(prefix).append("cell_").append(String.valueOf(loopCount));
            }
            out.append(" {\n");
            out.append("    box-sizing          : border-box;\n");
            out.append("    padding             : 0px 0px 0px 0px;\n");
            out.append("    margin              : 0px 0px 0px 0px;\n");
            out.append("    display             : table-cell;\n}\n\n");
            
            out.append(".").append(prefix).append("cell-spacing,\n");
            out.append(".").append(prefix).append("left-margin,\n");
            out.append(".").append(prefix).append("right-margin {\n");
            out.append("    padding             : 0px 0px 0px 0px;\n");
            out.append("    margin              : 0px 0px 0px 0px;\n");
            out.append("    display             : table-cell;\n");
            out.append("    font-size           : 0px;\n");
            out.append("    line-height         : 0px;\n}\n\n");
            
            out.append(".").append(prefix).append("cell-spacing {\n");
            out.append("    width               : ").append(String.valueOf(getLeftMargin() + getRightMargin())).append(getUnit()).append(";\n}\n\n");
            
            out.append(".").append(prefix).append("left-margin {\n");
            out.append("    width               : ").append(getFormattedLeftMargin()).append(";\n}\n\n");
            
            out.append(".").append(prefix).append("right-margin {\n");
            out.append("    width               : ").append(getFormattedRightMargin()).append(";\n}\n\n");
            
            for(loopCount = 1; loopCount <= getColumnCount(); loopCount ++){
                out.append(".").append(prefix).append("table-layout .").append(prefix).append("row .").append(prefix).append("cell_").append(String.valueOf(loopCount)).append(" {\n");
                out.append("    width               : ").append(getFormattedWidthForColumns(loopCount)).append(";\n}\n\n");
            }
            
            out.append("/* A box with zero margin and zero padding just containing other content and having the full screen width. \n");
            out.append("----------------------------------------------------------------------------------------------------*/\n");
            out.append(".").append(prefix).append("table-layout-box {\n");
            out.append("    padding             : 0px 0px 0px 0px;\n");
            out.append("    margin              : 0px 0px 0px 0px;\n");
            out.append("    background          : transparent;\n");
            out.append("    border              : none;\n}\n\n");
            
            result = out.toString();
        }
        return result;
    }
}
