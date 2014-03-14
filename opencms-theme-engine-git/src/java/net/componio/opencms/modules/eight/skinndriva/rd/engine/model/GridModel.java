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

/**
 *
 * @author Robert Diawara
 */
public class GridModel extends ThemeEngineModel{
    /** Constant used to determine the orientation of the grid. */
    public static final int ORIENTATION_LEFT  = 1;
    /** Constant used to determine the orientation of the grid. */
    public static final int ORIENTATION_RIGHT = 2;
    
    /** Holds the value of the property columnWidth. */
    private int    columnWidth;
    /** Holds the value of the property columnCount. */
    private int    columnCount;
    /** Holds the value of the property topMargin. */
    private int    topMargin;
    /** Holds the value of the property rightMargin. */
    private int    rightMargin;
    /** Holds the value of the property bottomMargin. */
    private int    bottomMargin;
    /** Holds the value of the property leftMargin. */
    private int    leftMargin;
    /** Holds the unit used for the grid dimensions. */
    private String unit;
    /** Holds the orientation of the grid (left or right). */
    private int    orientation;
    /** Holds the value of the property headerHeight. */
    private int    headerHeight;
    /** Holds the value of the property toolbarHeight. */
    private int    toolbarHeight;
    /** Holds the value of the property footerHeight. */
    private int    footerHeight;
    /** Holds the value of the property cssFile. */
    private String cssFile;



    
    /**
     * Default constructor
     */
    public GridModel(){
        this.headerHeight = 180;
        this.footerHeight = 130;
        this.toolbarHeight = 130;
        this.columnCount = 12;
        this.columnWidth = 60;
        this.topMargin = 10;
        this.rightMargin = 10;
        this.bottomMargin = 10;
        this.leftMargin = 10;
        this.unit = "px";
        this.orientation = GridModel.ORIENTATION_LEFT;
    }


    /**
     * Get the value of orientation
     *
     * @return the value of orientation
     */
    public int getOrientation() {
        return orientation;
    }

    /**
     * Set the value of orientation
     *
     * @param orientation new value of orientation
     */
    public void setOrientation(int orientation) {
        // Abort when specified an invalid value for the orientation.
        if((orientation != GridModel.ORIENTATION_LEFT) && (orientation != GridModel.ORIENTATION_RIGHT)){
            throw new RuntimeException("Tried to specify a wrong value for the orientation. Value has to be "
                    + String.valueOf(GridModel.ORIENTATION_LEFT) + " (left orientation) or " + 
                    String.valueOf(GridModel.ORIENTATION_RIGHT) + " (right orientation) !");
        }
        this.orientation = orientation;
    }

    /**
     * Get the value of columnCount
     * @return the value of columnCount
     */
    public int getColumnCount() {
        return columnCount;
    }

    /**
     * Set the value of columnCount
     * @param columnCount new value of columnCount
     * @throws ThemeConfigException When an invalid value is passed as parameter.
     */
    public void setColumnCount(int columnCount) throws ThemeConfigException{
        // Abort with an exception, when coluzmnCount has an invalid value.
        // Note that only 12 or 16 are accepted as value.
        if((columnCount != 12) && (columnCount != 16) && (columnCount != 24)){
            throw new ThemeConfigException("Tried to initialize the grid with an invalid column count. Only 12, 16 and "
                    + "24 are allowed as values. Therefore the setter has been aborted !");
        }
        checkStatus(this.columnCount, columnCount);
        this.columnCount = columnCount;
    }

    /**
     * Get the value of columnWidth
     * @return the value of columnWidth
     */
    public int getColumnWidth() {
        return columnWidth;
    }

    /**
     * Gets the column width as a formatted string also containg the unit which the value is related to.
     * @return The formatted column  width.
     */
    public String getFormattedColumnWidth(){
        return formatValue(getColumnWidth(), unit);
    }
    
    /**
     * Set the value of columnWidth
     * @param columnWidth new value of columnWidth
     * @throws ThemeConfigException When trying to specify an invalid value for the columns width
     */
    public void setColumnWidth(int columnWidth) throws ThemeConfigException{
        // Abort with an exception, when an invalaid value was specified for the column width.
        if((columnWidth % 5 != 0) || (columnWidth < 30) || (columnWidth > 70)){
           throw new ThemeConfigException("Tried to specify an invalid value for the column width. Only values "
                   + "between 30 and 70 and divisible by 5 are allowed !");
        }
        checkStatus(this.columnWidth, columnWidth);
        this.columnWidth = columnWidth;
    }

    /**
     * Get the value of topMargin
     * @return the value of topMargin
     */
    public int getTopMargin() {
        return topMargin;
    }

    /**
     * Gets the top margin as a formatted string also containg the unit which the value is related to.
     * @return The formatted top margin.
     */
    public String getFormattedTopMargin(){
        return formatValue(getTopMargin(), unit);
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
        checkStatus(this.topMargin, topMargin);
        this.topMargin = topMargin;
    }

    /**
     * Get the value of rightMargin
     * @return the value of rightMargin
     */
    public int getRightMargin() {
        return rightMargin;
    }

    /**
     * Gets the right margin as a formatted string also containg the unit which the value is related to.
     * @return The formatted right margin.
     */
    public String getFormattedRightMargin(){
        return formatValue(getRightMargin(), unit);
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
        checkStatus(this.rightMargin, rightMargin);
        this.rightMargin = rightMargin;
    }

    /**
     * Get the value of bottomMargin
     * @return the value of bottomMargin
     */
    public int getBottomMargin() {
        return bottomMargin;
    }

    /**
     * Gets the bottom margin as a formatted string also containg the unit which the value is related to.
     * @return The formatted bottom margin.
     */
    public String getFormattedBottomMargin(){
        return formatValue(getBottomMargin(), unit);
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
        checkStatus(this.bottomMargin, bottomMargin);
        this.bottomMargin = bottomMargin;
    }

    /**
     * Get the value of leftMargin
     * @return the value of leftMargin
     */
    public int getLeftMargin() {
        return leftMargin;
    }

    /**
     * Gets the left margin as a formatted string also containg the unit which the value is related to.
     * @return The formatted left margin.
     */
    public String getFormattedLeftMargin(){
        return formatValue(getLeftMargin(), unit);
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
        checkStatus(this.leftMargin, leftMargin);
        this.leftMargin = leftMargin;
    }

    /**
     * Get the value of pageWidth
     * @return the value of pageWidth
     */
    public int getPageWidth() {
        return this.columnCount * (columnWidth + leftMargin + rightMargin);
    }
    
    /**
     * Gets the page width as a formatted string also containg the unit which the value is related to.
     * @return The formatted page width.
    */
    public String getFormattedPageWidth(){
        return formatValue(getPageWidth(), unit);
    }
    
    /**
     * Get the value of headerHeight
     * @return the value of headerHeight
     */
    public int getHeaderHeight() {
        return headerHeight;
    }

    /**
     * Gets the header height as a formatted string also containg the unit which the value is related to.
     * @return The formatted header height.
     */
    public String getFormattedHeaderHeight() {
        return formatValue(getHeaderHeight(), unit);
    }

    /**
     * Set the value of headerHeight
     * @param headerHeight new value of headerHeight
     */
    public void setHeaderHeight(int headerHeight) {
        checkStatus(this.headerHeight, headerHeight);
        this.headerHeight = headerHeight;
    }


    /**
     * Get the value of toolbarHeight
     * @return the value of toolbarHeight
     */
    public int getToolbarHeight() {
        return toolbarHeight;
    }

    /**
     * Gets the toolbar height as a formatted string also containg the unit which the value is related to.
     * @return The formatted toolbar height
     */
    public String getFormattedToolbarHeight() {
        return formatValue(getToolbarHeight(), unit);
    }

    /**
     * Set the value of toolbarHeight
     * @param toolbarHeight new value of toolbarHeight
     */
    public void setToolbarHeight(int toolbarHeight) {
        checkStatus(this.toolbarHeight, toolbarHeight);
        this.toolbarHeight = toolbarHeight;
    }


    /**
     * Get the value of footerHeight
     * @return the value of footerHeight
     */
    public int getFooterHeight() {
        return footerHeight;
    }

    /**
     * Gets the footer height as a formatted string also containg the unit which the value is related to.
     * @return The formatted footer height.
     */
    public String getFormattedFooterHeight() {
        return formatValue(getFooterHeight(), unit);
    }

    /**
     * Set the value of footerHeight
     * @param footerHeight new value of footerHeight
     */
    public void setFooterHeight(int footerHeight) {
        checkStatus(this.footerHeight, footerHeight);
        this.footerHeight = footerHeight;
    }

    /**
     * Get the effective width of a set of columns in pixels.
     * @param p_columnCount The number of columns to get the width in pixels for.
     * @return The effective width in pixels.
     * @throws ThemeConfigException When tried to specify an invalid number of columns. 
     */
    public int getWidthForColumns(int p_columnCount) throws ThemeConfigException{
        // Abort with an exception, when tried to specify an invalid number of columns
        if((p_columnCount < 0) || (p_columnCount > this.columnCount)){
            throw new ThemeConfigException("Tried to specify an invalid number of columns to get the effective width "
                    + "in pixels. Only a value between 1 and \"columnCount\" is allowed !");
        }
        return (p_columnCount * columnWidth) + ((p_columnCount -1) * (leftMargin + rightMargin));
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
        return cssFile;
    }

    /**
     * Set the value of cssFile
     * @param cssFile new value of cssFile
     */
    public void setCssFile(String cssFile) {
        checkStatus(this.cssFile, cssFile);
        this.cssFile = cssFile;
    }

    /**
     * Generates the CSS code which represents the implentation of the grid.
     * @return The CSS Code.
     * @throws ThemeConfigException When something fails during the code generation.
     */
    public String generateCSSCode() throws ThemeConfigException{
        StringWriter     out        = new StringWriter();
        int              loopCount;
        SimpleDateFormat format     = new SimpleDateFormat("E, MMM dd yyyy, HH:mm:ss");
        String           fileName   = getCssFile();
        
        if((fileName != null) && (fileName.trim().length() > 0)){
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
        }else{
            fileName = "";
        }
        
        writeln(out, "/*");
        write  (out, " Document : ");
        writeln(out, fileName);
        write  (out, " Created on ");
        writeln(out, format.format(new Date()));
        writeln(out, " Copyright (C) 2012 Robert Diawara");
        writeln(out);
        writeln(out, " This file is part of OpenCms Theme Engine.");
        writeln(out);
        writeln(out, " OpenCms Theme Engine is free software: you can redistribute it and/or modify");
        writeln(out, " it under the terms of the GNU Lesser General Public License as published by");
        writeln(out, " the Free Software Foundation, either version 3 of the License, or");
        writeln(out, " (at your option) any later version.");
        writeln(out);
        writeln(out, " OpenCms Theme Engine is distributed in the hope that it will be useful,");
        writeln(out, " but WITHOUT ANY WARRANTY; without even the implied warranty of");
        writeln(out, " MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the");
        writeln(out, " GNU Lesser General Public License for more details.");
        writeln(out);
        writeln(out, " You should have received a copy of the GNU Lesser General Public License");
        writeln(out, " along with OpenCms Theme Engine.  If not, see <http://www.gnu.org/licenses/>.");
        writeln(out);
        writeln(out, " -----------------------------------------------------------------------------------");
        writeln(out);
        writeln(out, "  Grid for OpenCms Theme Engine ~ Core CSS.  This is automatically generated CSS code.");
        writeln(out, "  Please do not modify.  Learn more ~ http://www.diawara.com/");
        writeln(out, "*/");
        writeln(out);
        writeln(out);
        writeln(out);
        writeln(out, "/* `Individual formats");
        writeln(out, "----------------------------------------------------------------------------------------------------*/");
        writeln(out);
        writeln(out, "#GRID_HEADER{");
        write  (out, "    height             : ");
        write  (out, getFormattedHeaderHeight());
        writeln(out, ";");
        writeln(out, "}");
        writeln(out);
        writeln(out, "#GRID_FOOTER{");
        write  (out, "    height             : ");
        write  (out, getFormattedFooterHeight());
        writeln(out, ";");
        writeln(out, "}");
        writeln(out);
        writeln(out, "#GRID_TOOLBAR{");
        write  (out, "    height             : ");
        write  (out, getFormattedToolbarHeight());
        writeln(out, ";");
        writeln(out, "}");
        writeln(out);
        writeln(out, "/*");
        writeln(out, "  Forces backgrounds to span full width,");
        writeln(out, "  even if there is horizontal scrolling.");
        writeln(out, "  Increase this if your layout is wider.");
        writeln(out);
        writeln(out, "  Note: IE6 works fine without this fix.");
        writeln(out, "*/");
        writeln(out);
        writeln(out, "body {");
        writeln(out, "    min-width          : " + getFormattedPageWidth() + ";");
        writeln(out, "}");
        writeln(out);
        writeln(out, "/* `Container");
        writeln(out, "----------------------------------------------------------------------------------------------------*/");
        writeln(out);
        write  (out, ".container_");
        write  (out, String.valueOf(columnCount));
        writeln(out, " {");
        writeln(out, "    margin-left        : auto;");
        writeln(out, "    margin-right       : auto;");
        write  (out, "    width              : ");
        write  (out, getFormattedPageWidth());
        writeln(out, ";");
        writeln(out, "}");
        writeln(out);
        writeln(out, "/* `Grid >> Global");
        writeln(out, "----------------------------------------------------------------------------------------------------*/");
        writeln(out);
        write  ( out, ".grid_1");
        for(loopCount = 2; loopCount <= columnCount; loopCount ++){
            writeln(out, ",");
            write(out, ".grid_");
            write(out, String.valueOf(loopCount));
        }
        writeln(out, " {");
        writeln(out, "    display            : inline;");
        writeln(out, "    float              : left;");
        write  (out, "    margin             : ");
        write  (out, getFormattedTopMargin());
        write  (out, " ");
        write  (out, getFormattedRightMargin());
        write  (out, " ");
        write  (out, getFormattedBottomMargin());
        write  (out, " ");
        write  (out, getFormattedLeftMargin());
        writeln(out, ";");
        writeln(out, "}");
        writeln(out);
        write  (out, ".push_1, .pull_1");
        for(loopCount = 2; loopCount < columnCount; loopCount ++){
            writeln(out, ",");
            write(out, ".push_");
            write(out, String.valueOf(loopCount));
            write(out, ", .pull_");
            write(out, String.valueOf(loopCount));
        }
        writeln(out, " {");
        writeln(out, "    position           : relative;");
        writeln(out, "}");
        writeln(out);
        writeln(out, "/* `Grid >> Children (Alpha ~ First, Omega ~ Last)");
        writeln(out, "----------------------------------------------------------------------------------------------------*/");
        writeln(out);
        writeln(out, ".alpha {");
        writeln(out, orientation == GridModel.ORIENTATION_LEFT ? "    margin-left        : 0;" : "    margin-right       : 0;");
        writeln(out, "}");
        writeln(out);
        writeln(out, ".omega {");
        writeln(out, orientation == GridModel.ORIENTATION_LEFT ? "    margin-right       : 0;" : "    margin-left        : 0;");
        writeln(out, "}");
        writeln(out);
        write  (out, "/* `Grid >> ");
        write  (out, String.valueOf(columnCount));
        writeln(out, " Columns");
        writeln(out, "----------------------------------------------------------------------------------------------------*/");
        writeln(out);
        for(loopCount = 1; loopCount <= columnCount; loopCount ++){
            write(out, ".container_");
            write(out, String.valueOf(columnCount));
            write(out, " .grid_");
            write(out, String.valueOf(loopCount));
            writeln(out, " {");
            write(out, "    width              : ");
            write(out, getFormattedWidthForColumns(loopCount));
            writeln(out, ";");
            writeln(out, "}");
            writeln(out);
        }
        write  (out, "/* Prefix Extra Space >> ");
        write  (out, String.valueOf(columnCount));
        writeln(out, " Columns");
        writeln(out, "----------------------------------------------------------------------------------------------------*/");
        writeln(out);
        for(loopCount = 1; loopCount < columnCount; loopCount ++){
            write(out, ".container_");
            write(out, String.valueOf(columnCount));
            write(out, " .prefix_");
            write(out, String.valueOf(loopCount));
            writeln(out, " {");
            write(out, orientation == GridModel.ORIENTATION_LEFT ? "    padding-left       : " : "    padding-right      : ");
            write(out, this.formatValue(loopCount * (leftMargin + columnWidth + rightMargin), "px"));
            writeln(out, ";");
            writeln(out, "}");
            writeln(out);
        }
        write  (out, "/* Suffix Extra Space >> ");
        write  (out, String.valueOf(columnCount));
        writeln(out, " Columns");
        writeln(out, "----------------------------------------------------------------------------------------------------*/");
        writeln(out);
        for(loopCount = 1; loopCount < columnCount; loopCount ++){
            write(out, ".container_");
            write(out, String.valueOf(columnCount));
            write(out, " .suffix_");
            write(out, String.valueOf(loopCount));
            writeln(out, " {");
            write(out, orientation == GridModel.ORIENTATION_LEFT ? "    padding-right      : " : "    padding-left       : ");
            write(out, this.formatValue(loopCount * (leftMargin + columnWidth + rightMargin), "px"));
            writeln(out, ";");
            writeln(out, "}");
            writeln(out);
        }
        write  (out, "/* Push Space >> ");
        write  (out, String.valueOf(columnCount));
        writeln(out, " Columns");
        writeln(out, "----------------------------------------------------------------------------------------------------*/");
        writeln(out);
        for(loopCount = 1; loopCount < columnCount; loopCount ++){
            write(out, ".container_");
            write(out, String.valueOf(columnCount));
            write(out, " .push_");
            write(out, String.valueOf(loopCount));
            writeln(out, " {");
            write(out, orientation == GridModel.ORIENTATION_LEFT ? "    left               : " : "    right              : ");
            write(out, this.formatValue(loopCount * (leftMargin + columnWidth + rightMargin), "px"));
            writeln(out, ";");
            writeln(out, "}");
            writeln(out);
        }
        write  (out, "/* Pull Space >> ");
        write  (out, String.valueOf(columnCount));
        writeln(out, " Columns");
        writeln(out, "----------------------------------------------------------------------------------------------------*/");
        writeln(out);
        for(loopCount = 1; loopCount < columnCount; loopCount ++){
            write(out, ".container_");
            write(out, String.valueOf(columnCount));
            write(out, " .pull_");
            write(out, String.valueOf(loopCount));
            writeln(out, " {");
            write(out, orientation == GridModel.ORIENTATION_LEFT ? "    left               : -" : "    right              : -");
            write(out, this.formatValue(loopCount * (leftMargin + columnWidth + rightMargin), "px"));
            writeln(out, ";");
            writeln(out, "}");
            writeln(out);
        }
        writeln(out, "/* Clear Floated Elements");
        writeln(out, "----------------------------------------------------------------------------------------------------*/");
        writeln(out);
        writeln(out, "/* http://sonspring.com/journal/clearing-floats */");
        writeln(out);
        writeln(out, ".clear {");
        writeln(out, "    clear              : both;");
        writeln(out, "    display            : block;");
        writeln(out, "    overflow           : hidden;");
        writeln(out, "    visibility         : hidden;");
        writeln(out, "    width              : 0;");
        writeln(out, "    height             : 0;");
        writeln(out, "}");
        writeln(out);
        writeln(out, "/* http://www.yuiblog.com/blog/2010/09/27/clearfix-reloaded-overflowhidden-demystified */");
        writeln(out);
        writeln(out, ".clearfix:before,");
        writeln(out, ".clearfix:after,");
        write  (out, ".container_");
        write  (out, String.valueOf(columnCount));
        writeln(out, ":before,");
        write  (out, ".container_");
        write  (out, String.valueOf(columnCount));
        writeln(out, ":after {");
        writeln(out, "    content            : '.';");
        writeln(out, "    display            : block;");
        writeln(out, "    overflow           : hidden;");
        writeln(out, "    visibility         : hidden;");
        writeln(out, "    font-size          : 0;");
        writeln(out, "    line-height        : 0;");
        writeln(out, "    width              : 0;");
        writeln(out, "    height             : 0;");
        writeln(out, "}");
        writeln(out);
        writeln(out, ".clearfix:after,");
        write  (out, ".container_");
        write  (out, String.valueOf(columnCount));
        writeln(out, ":after {");
        writeln(out, "    clear              : both;");
        writeln(out, "}");
        writeln(out);
        writeln(out, "/*");
        writeln(out, "  The following zoom:1 rule is specifically for IE6 + IE7.");
        writeln(out, "  Move to separate stylesheet or delete if invalid CSS is a problem.");
        writeln(out, "*/");
        writeln(out);
        writeln(out, ".clearfix,");
        write  (out, ".container_");
        write  (out, String.valueOf(columnCount));
        writeln(out, " {");
        writeln(out, "    zoom               : 1;");
        writeln(out, "}");
        
        return out.toString();
    }
    
    /**
     * Writes data to a StringWriter
     * @param p_out The StringWriter, where the data are to be written to.
     * @param p_str The string to be written to the StringWriter
     */
    private void write(StringWriter p_out, String p_str){
        p_out.append(p_str);
    }
    
    /**
     * Writes data to a StringWriter and begins a new line after the data have been written.
     * @param p_out The StringWriter, where the data are to be written to.
     * @param p_str The string to be written to the StringWriter
     */
    private void writeln(StringWriter p_out, String p_str){
        p_out.append(p_str);
        p_out.append("\n");
    }
    
    /**
     * Writes a new line to a StrinWriter.
     * @param p_out The StringWriter, where the newline character has to be written to.
     */
    private void writeln(StringWriter p_out){
        p_out.append("\n");
    }
}
