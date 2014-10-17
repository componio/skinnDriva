/*
 *  Document : ThemeDimensions.java
 *  Created on  29.05.2011, 12:47:54
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


package net.componio.opencms.modules.eight.skinndriva.rd.engine.model; 

import net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigException;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigInitializationException;

import  java.io.StringWriter;
import  java.text.SimpleDateFormat;
import  java.util.Date;

/**
 *
 * @author Robert Diawara
 */
public class ThemeDimensions extends ThemeEngineModel{
    /** Holds the unit associated to the numeric margin values. */
    private String unit;
    /** Holds the value of the property boxSpacing. */
    private int          boxSpacing;
    /** Holds the value of the property leftColumnInnerWidth. */
    private int          leftColumnInnerWidth;
    /** Holds the value of the property centerColumnInnerWidth. */
    private int          centerColumnInnerWidth;
    /** Holds the value of the property rightColumnInnerWidth. */
    private int          rightColumnInnerWidth;
    /** Holds the value of the property headerInnerHeight. */
    private int          headerInnerHeight;
    /** Holds the value of the property toolbarInnerHeight. */
    private int          toolbarInnerHeight;
    /** Holds the value of the property footerInnerHeight. */
    private int          footerInnerHeight;
    /** Holds the margins which will be applied to these dimesions. */
    private ThemeMargins margins;
    /** Holds the value of the property cssFile. */
    private String cssFile;

    /** Integer constant defining a three column layout. */
    public static final int THREE_COL_LAYOUT           = 0;
    /** Integer constant defining a two column layout with the left pane invisible and the right pane visible. */
    public static final int TWO_COL_LAYOUT_NOLEFTPANE  = 1;
    /** Integer constant defining a two column layout with the left pane visible and the right pane invisible. */
    public static final int TWO_COL_LAYOUT_NORIGHTPANE = 2;
    /** Integer constant defining a one column layount. */
    public static final int ONE_COL_LAYOUT             = 3;

    /**
     * Gets the currently active layout, depending on if the left pane and / or the
     * right pane of a page are visible or not.
     * @param p_leftPaneVisible True if the left pane is visible. False otherwise.
     * @param p_rightPaneVisible True if the right pane is visible. False otherwise.
     * @return An integer, identifying the currently active layout.
     */
    public static int getLayout(boolean p_leftPaneVisible, boolean p_rightPaneVisible){
        int result;

        if(p_leftPaneVisible){
            if(p_rightPaneVisible){
                result = ThemeDimensions.THREE_COL_LAYOUT;
            }else{
                result = ThemeDimensions.TWO_COL_LAYOUT_NORIGHTPANE;
            }
        }else{
            if(p_rightPaneVisible){
                result = ThemeDimensions.TWO_COL_LAYOUT_NOLEFTPANE;
            }else{
                result = ThemeDimensions.ONE_COL_LAYOUT;
            }
        }
        return result;
    }

    /**
     * Default constructor. Is defined as private here, to avoid an initialization without assigning
     * the mandatory theme configuraiton.
     */
    private ThemeDimensions(){
    }

    /**
     * Gets the status of the bean. This status can only be changed or unchanged.
     * @return The status.
     */
    @Override
    public int getStatus(){
        int marginsStatus = margins != null ? margins.getStatus() : ThemeEngineModel.STATUS_UNCHANGED;

        return marginsStatus == ThemeEngineModel.STATUS_CHANGED ? marginsStatus : super.getStatus();
    }
    
    /**
     * Sets the status to unchanged.
     */
    @Override
    public void setUnchanged(){
        super.setUnchanged();
        if(margins != null){
            margins.setUnchanged();
        }
    }
    
    
    /**
     * Constructor. Initializes a new instance of <code>ThemeDimensions</code> and assigns the mandatory
     * <code>ThemeConfig</code> instance passed with the parameter <code>p_owner</code>.
     * @param p_unit
     * @throws ThemeConfigInitializationException When the unit has an invalid value.
     */
    public ThemeDimensions(String p_unit) throws ThemeConfigInitializationException{
        // Abort with an exception, when the unit is invalid.
        if((p_unit != null) && (!p_unit.matches("^(pt|pc|in|mm|cm|px|em|ex|%)$"))){
            throw new ThemeConfigInitializationException("Could not initialize theme config margins, " +
                    "because of wrong pattern for the unit. Unit has to match \"^(pt|pc|in|mm|cm|px|em|ex|%)$\" !");
        }
        this.unit = p_unit;
        margins = null;
    }

    /**
     * Is just used to check if the integer value identifying a layount is valid and
     * to throw runtime exception, if it is not.
     * @param p_layout The integer value to be checked.
     */
    private void validateLayout(int p_layout){
        int min = ThemeDimensions.THREE_COL_LAYOUT;
        int max = ThemeDimensions.ONE_COL_LAYOUT;

        if((p_layout < min) || (p_layout > max)){
            throw new RuntimeException("Invalid value for layout. Value has to be between " + String.valueOf(min) +
                    " and " + String.valueOf(max) + "!");
        }
    }

    /**
     * Checks, if the margins have been set.
     * @return <code>True</code> if margins are set. Otherwise <code>false</code>.
     */
    public boolean hasMargins(){
        return this.margins != null;
    }
    
   /**
    * Gets the margins associated to these dimensions. If there are no margins available yet, then
    * they will be created and initialized each with zero (0) as default value.
    * @return The margins
    * @throws ThemeConfigInitializationException If the initialization of the margins fails because
    * of an invalid unit.
    */
    public ThemeMargins getMargins() throws ThemeConfigInitializationException{
        if(this.margins == null){
            this.margins = new ThemeMargins(getUnit());
            setChanged();
        }
        return this.margins;
    }

    /**
     * Removes all margins if there are some initialized.
     */
    public void clearMargins(){
        this.margins = null;
        setChanged();
    }

    /**
     * Gets the unit for the numeric margin values.
     * @return The unit
     */
    private String getUnit(){
        return ((unit == null) || (unit.trim().length() > 0)) ? unit : null;
    }

    /**
     * Get the value of boxSpacing
     * @return the value of boxSpacing
     */
    public int getBoxSpacing() {
        return boxSpacing;
    }

    /**
     * Gets the formatted version of the box spacing as a string, with
     * the unit associated to it, so that it can be directly inserted into HTML code.
     * @return The formatted inner width of the left column.
     */
    public String getFormattedBoxSpacing(){
        return String.valueOf(getBoxSpacing()) + (getUnit() != null ? getUnit() : "");
    }
    
    /**
     * Set the value of boxSpacing
     * @param boxSpacing new value of boxSpacing
     */
    public void setBoxSpacing(int boxSpacing) {
        checkStatus(this.boxSpacing, boxSpacing);
        this.boxSpacing = boxSpacing;
    }

    /**
     * Get the value of leftColumnInnerWidth
     * @return the value of leftColumnInnerWidth
     */
    public int getLeftColumnInnerWidth() {
        return leftColumnInnerWidth;
    }

    /**
     * Gets the formatted version of the left column inner width as a string, with
     * the unit associated to it, so that it can be directly inserted into HTML code.
     * @return The formatted inner width of the left column.
     */
    public String getFormattedLeftColumnInnerWidth(){
        return String.valueOf(getLeftColumnInnerWidth()) + (getUnit() != null ? getUnit() : "");
    }

    /**
     * Get the total width of the left column, including all margins.
     * @return The total width of the left column, including all margins.
     */
    public int getLeftColumnWidth(){
        int result = getLeftColumnInnerWidth();

        if(margins != null){
            result += (margins.getLeftMargin() + margins.getRightMargin());
        }
        return result;
    }

    /**
     * Gets the formatted version of the left column total width, with the unit associated to it.
     * The total width is the inner width plus all margins.
     * @return The formatted total width of the left column, including all margins.
     */
    public String getFormattedLeftColumnWidth(){
        return String.valueOf(getLeftColumnWidth()) + (getUnit() != null ? getUnit() : "");
    }

    /**
     * Set the value of leftColumnInnerWidth
     * @param leftColumnInnerWidth new value of leftColumnInnerWidth
     */
    public void setLeftColumnInnerWidth(int leftColumnInnerWidth) {
        checkStatus(this.leftColumnInnerWidth, leftColumnInnerWidth);
        this.leftColumnInnerWidth = leftColumnInnerWidth;
    }

    /**
     * Get the value of centerColumnInnerWidth
     * @return the value of centerColumnInnerWidth
     */
    public int getCenterColumnInnerWidth() {
        return centerColumnInnerWidth;
    }

    /**
     * Gets the formatted version of the center column inner width as string with
     * the unit associated to it, so that it can be directly inserted into HTML code.
     * @return The formatted inner width of the center column.
     */
    public String getFormattedCenterColumnInnerWidth(){
        return String.valueOf(centerColumnInnerWidth) + (getUnit() != null ? getUnit() : "");
    }

    /**
     * Get the total width of the center column, including its left and right margin.
     * @return The total width of the center column, including its left and right margin.
     */
    public int getCenterColumnWidth(){
        int result = getCenterColumnInnerWidth();

        if(margins != null){
            result += (margins.getLeftMargin() + margins.getRightMargin());
        }
        return result;
    }

    /**
     * Gets the formatted version of the center column total width with the unit associated to it.
     * So it can directly be inserted into HRML code. The total width is the inner width plus the
     * left margin and the right margin.
     * @return The formatted total width of the center column, including the left and right margin.
     */
    public String getFormattedCenterColumnWidth(){
        return String.valueOf(getCenterColumnWidth()) + (getUnit() != null ? getUnit() : "");
    }

    /**
     * Set the value of centerColumnInnerWidth
     * @param centerColumnInnerWidth The  new value of centerColumnInnerWidth
     */
    public void setCenterColumnInnerWidth(int centerColumnInnerWidth) {
        checkStatus(this.centerColumnInnerWidth, centerColumnInnerWidth);
        this.centerColumnInnerWidth = centerColumnInnerWidth;
    }

    /**
     * Get the value of rightColumnInnerWidth
     * @return the value of rightColumnInnerWidth
     */
    public int getRightColumnInnerWidth() {
        return rightColumnInnerWidth;
    }

    /**
     * Gets the formatted version of the right column inner width as string with
     * the unit associated to it, so that it can be directly inserted into HTML code.
     * @return The formatted inner width of the right column.
     */
    public String getFormattedRightColumnInnerWidth(){
        return String.valueOf(rightColumnInnerWidth) + (getUnit() != null ? getUnit() : "");
    }

    /**
     * Get the total width of the right column, including its left and right margin.
     * @return The total width of the right column, including its left and right margin.
     */
    public int getRightColumnWidth(){
        int result = getRightColumnInnerWidth();

        if(margins != null){
            result += (margins.getLeftMargin() + margins.getRightMargin());
        }
        return result;
    }

    /**
     * Gets the formatted version of the right column total width with the unit associated to it.
     * So it can directly be inserted into HRML code. The total width is the inner width plus the
     * left and right margin.
     * @return The formatted total width of the right column, including its left and right margin.
     */
    public String getFormattedRightColumnWidth(){
        return String.valueOf(getRightColumnWidth()) + (getUnit() != null ? getUnit() : "");
    }

    /**
     * Set the value of rightColumnInnerWidth
     * @param rightColumnInnerWidth new value of rightColumnInnerWidth
     */
    public void setRightColumnInnerWidth(int rightColumnInnerWidth) {
        checkStatus(this.rightColumnInnerWidth, rightColumnInnerWidth);
        this.rightColumnInnerWidth = rightColumnInnerWidth;
    }

    /**
     * Get the value of headerInnerHeight
     * @return the value of headerInnerHeight
     */
    public int getHeaderInnerHeight() {
        return headerInnerHeight;
    }

    /**
     * Gets the formatted version of the header inner height as string with
     * the unit associated to it, so that it can be directly inserted into HTML code.
     * @return The formatted inner height of the header.
     */
    public String getFormattedHeaderInnerHeight(){
        return String.valueOf(getHeaderInnerHeight()) + (getUnit() != null ? getUnit() : "");
    }

    /**
     * Get the total height of the header, including its top and bottom margin.
     * @return The total height of the header, including its top and bottom margin.
     */
    public int getHeaderHeight(){
        int result = getHeaderInnerHeight();

        if(margins != null){
            result +=(margins.getTopMargin() + margins.getBottomMargin());
        }
        return result;
    }

    /**
     * Gets the formatted version of the header total height with the unit associated to it.
     * So it can directly be inserted into HRML code. The total height is the inner height plus the
     * top and bottom margin.
     * @return The formatted total height of the header, including its top and bottom margin.
     */
    public String getFormattedHeaderHeight(){
        return String.valueOf(getHeaderHeight()) + (getUnit() != null ? getUnit() : "");
    }

    /**
     * Set the value of headerInnerHeight
     * @param headerInnerHeight new value of headerInnerHeight
     */
    public void setHeaderInnerHeight(int headerInnerHeight) {
        checkStatus(this.headerInnerHeight, headerInnerHeight);
        this.headerInnerHeight = headerInnerHeight;
    }

    /**
     * Get the value of toolbarInnerHeight
     * @return the value of toolbarInnerHeight
     */
    public int getToolbarInnerHeight() {
        return toolbarInnerHeight;
    }

    /**
     * Gets the formatted version of the toolbar inner height as string with
     * the unit associated to it, so that it can be directly inserted into HTML code.
     * @return The formatted inner height of the toolbar.
     */
     public String getFormattedToolbarInnerHeight(){
        return String.valueOf(getToolbarInnerHeight()) + (getUnit() != null ? getUnit() : "");
    }

    /**
     * Get the total height of the toolbar, including its top and bottom margin.
     * @return The total height of the toolbar, including its top and bottom margin.
     */
    public int getToolbarHeight(){
        int result = getToolbarInnerHeight();

        if(margins != null){
            result += (margins.getTopMargin() + margins.getBottomMargin());
        }
        return result;
    }

    /**
     * Gets the formatted version of the toolbar total height with the unit associated to it.
     * So it can directly be inserted into HRML code. The total height is the inner height plus the
     * top and bottom margin.
     * @return The formatted total height of the toolbar, including its top and bottom margin.
     */
    public String getFormattedToolbarHeight(){
        return String.valueOf(getToolbarHeight()) + (getUnit() != null ? getUnit() : "");
    }

    /**
     * Set the value of toolbarInnerHeight
     * @param toolbarInnerHeight new value of toolbarInnerHeight
     */
    public void setToolbarInnerHeight(int toolbarInnerHeight) {
        checkStatus(this.toolbarInnerHeight, toolbarInnerHeight);
        this.toolbarInnerHeight = toolbarInnerHeight;
    }

    /**
     * Get the value of footerInnerHeight
     * @return the value of footerInnerHeight
     */
    public int getFooterInnerHeight() {
        return footerInnerHeight;
    }

    /**
     * Gets the formatted version of the footer inner height as string with
     * the unit associated to it, so that it can be directly inserted into HTML code.
     * @return The formatted inner height of the footer.
     */
    public String getFormattedFooterInnerHeight(){
        return String.valueOf(getFooterInnerHeight()) + (getUnit() != null ? getUnit() : "");
    }

    /**
     * Get the total height of the footer, including its top and bottom margin.
     * @return The total height of the footer, including its top and bottom margin.
     */
    public int getFooterHeight(){
        int result = getFooterInnerHeight();

        if(margins != null){
            result += (margins.getTopMargin() + margins.getBottomMargin());
        }
        return result;
    }

    /**
     * Gets the formatted version of the footer total height with the unit associated to it.
     * So it can directly be inserted into HRML code. The total height is the inner height plus the
     * top and bottom margin.
     * @return The formatted total height of the footer, including its top and bottom margin.
     */
    public String getFormattedFooterHeight(){
        return String.valueOf(getFooterHeight()) + (getUnit() != null ? getUnit() : "");
    }

    /**
     * Set the value of footerInnerHeight
     * @param footerInnerHeight new value of footerInnerHeight
     */
    public void setFooterInnerHeight(int footerInnerHeight) {
        checkStatus(this.footerInnerHeight, footerInnerHeight);
        this.footerInnerHeight = footerInnerHeight;
    }

    /**
     * Get the value of dataAreaInnerWidth
     * @param p_layout As the data area inner with depends on the layout, the layout
     * which the data area inner width has to be computed for, will be passed with
     * this parameter.
     * @return the value of dataAreaInnerWidth
     */
    public int getDataAreaInnerWidth(int p_layout) {
        int result;
        
        validateLayout(p_layout);
        switch(p_layout){
            case ThemeDimensions.THREE_COL_LAYOUT :
                result = getCenterColumnInnerWidth();
                break;
            case ThemeDimensions.TWO_COL_LAYOUT_NOLEFTPANE :
                result = getLeftColumnInnerWidth() + getBoxSpacing() + getCenterColumnInnerWidth();
                if(margins != null){
                    result += (margins.getRightMargin() + margins.getLeftMargin());
                }
                break;
            case ThemeDimensions.TWO_COL_LAYOUT_NORIGHTPANE :
                result = getCenterColumnInnerWidth() + getBoxSpacing() + getRightColumnInnerWidth();
                if(margins != null){
                    result += (margins.getRightMargin() + margins.getLeftMargin());
                }
                break;
            case ThemeDimensions.ONE_COL_LAYOUT :
                result = getPageInnerWidth();
                break;
            default:
                result = 0;
        }
        return result;
    }

    /**
     * Gets the formatted version of the data area inner width as string with
     * the unit associated to it, so that it can be directly inserted into HTML code.
     * @param p_layout As the formatted data area inner with depends on the layout,
     * the layout which the formatted data area inner width has to be computed for,
     * will be passed with this parameter.
     * @return The formatted inner width of the data area.
     */
    public String getFormattedDataAreaInnerWidth(int p_layout){
        return String.valueOf(getDataAreaInnerWidth(p_layout)) + (getUnit() != null ? getUnit() : "");
    }

    /**
     * Get the total width of the data area, including its left and right margin.
     * @param p_layout As the data area with depends on the layout, the layout which
     * it has to be computed for, will be passed with this parameter.
     * @return The total width of the data area, including its left and right margin.
     */
    public int getDataAreaWidth(int p_layout){
        int result = getDataAreaInnerWidth(p_layout);

        if(margins != null){
            result += (margins.getLeftMargin() + margins.getRightMargin());
        }
        return result;
    }

    /**
     * Gets the formatted version of the data area width with the unit associated to it.
     * So it can directly be inserted into HRML code. The total width is the inner width plus the
     * left margin and the right margin.
     * @param p_layout As the formatted data area with depends on the layout, the layout which
     * it has to be computed for, will be passed with this parameter.
     * @return The formatted total width of the center column, including the left and right margin.
     */
    public String getFormattedDataAreaWidth(int p_layout){
        return String.valueOf(getDataAreaWidth(p_layout)) + (getUnit() != null ? getUnit() : "");
    }

    /**
     * Get the inner width of the complete page.
     * @return The inner with of the complete page.
     */
    public int getPageInnerWidth(){
        int result = leftColumnInnerWidth + centerColumnInnerWidth + rightColumnInnerWidth + 2 * boxSpacing;

        if(margins != null){
            result += (2 * (margins.getLeftMargin() + margins.getRightMargin()));
        }
        return result;
     }

    /**
     * Gets the inner width of the complete page formatted as string, including the unit,
     * so that it can directly be inserted into HTML code.
     * @return The formatted inner width of the complete page.
     */
    public String getFormattedPageInnerWidth(){
        return String.valueOf(getPageInnerWidth()) + (getUnit() != null ? getUnit() : "");
    }

    /**
     * Gets the total width of the page including all margins.
     * @return The total width of the page.
     */
    public int getPageWidth(){
        int result = leftColumnInnerWidth + centerColumnInnerWidth + rightColumnInnerWidth + 4 * boxSpacing;

        if(margins != null){
            result += (3 * (margins.getLeftMargin() + margins.getRightMargin()));
        }
        return result;
    }

    /**
     * Gets the string version of the total width including all margins and with the
     * unit associated to it.
     * @return The formatted total width of the page.
     */
    public String getFormattedPageWidth(){
        return String.valueOf(getPageWidth()) + (getUnit() != null ? getUnit() : "");
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
     * Generates the CSS code which implements the dimensions for the theme.
     * @return The generated CSS code.
     */
    public String generateCSSCode(){
        StringWriter     out                 = new StringWriter();
        SimpleDateFormat format              = new SimpleDateFormat("E, MMM dd yyyy, HH:mm:ss");
        String           fileName            = getCssFile();
        String           topMargin           = "0";
        String           rightMargin         = "0";
        String           leftMargin          = "0";
        String           contentBottomMargin;
        String           spacing             = getFormattedBoxSpacing();   
        
        // Compute the file name
        if((fileName != null) && (fileName.trim().length() > 0)){
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
        }else{
            fileName = "";
        }
        
        // Get the margins
        if(hasMargins()){
            try{
                topMargin = getMargins().getFormattedTopMargin();
                rightMargin = getMargins().getFormattedRightMargin();
                leftMargin = getMargins().getFormattedLeftMargin();
                //contentBottomMargin = String.valueOf(getMargins().getBottomMargin() + getBoxSpacing()) + getUnit();
                contentBottomMargin = String.valueOf(getMargins().getBottomMargin()) + getUnit();
            }catch(ThemeConfigException ex){
                throw new RuntimeException(ex);
            }
        }else{
            contentBottomMargin = getFormattedBoxSpacing();
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
        writeln(out, "  Dimensions for OpenCms Theme Engine ~ Core CSS.  This is automatically generated CSS code.");
        writeln(out, "  Please do not modify.  Learn more ~ http://www.diawara.com/");
        writeln(out, "*/");
        writeln(out);
        writeln(out);
        writeln(out);
        writeln(out, "div#CONTAINER{");
        writeln(out, "    width              : " + getFormattedPageWidth() + ";");
        writeln(out, "    margin-left        : auto;");
        writeln(out, "    margin-right       : auto;");
        writeln(out, "}");
        writeln(out);
        writeln(out, "div.header{");
        writeln(out, "    height             : " + getFormattedHeaderHeight() + ";");
        writeln(out, "    margin             : " + spacing + " " + spacing + " " + spacing + " " + spacing + ";");
        writeln(out, "}");
        writeln(out);
        writeln(out, "div.header-content{");
        writeln(out, "    position           : relative;");
        writeln(out, "    top                : " + topMargin + ";");
        writeln(out, "    left               : " + leftMargin + ";");
        writeln(out, "    height             : " + getFormattedHeaderInnerHeight() + ";");
        writeln(out, "    width              : " + getFormattedDataAreaInnerWidth(ThemeDimensions.ONE_COL_LAYOUT) + ";");
        writeln(out, "    overflow           : visible;");
        writeln(out, "}");
        writeln(out);
        writeln(out, "div.toolbar{");
        writeln(out, "    height             : " + getFormattedToolbarHeight() + ";");
        writeln(out, "    margin             : 0" + getUnit() + " " + spacing + " " + spacing + " " + spacing + ";");
        writeln(out, "}");
        writeln(out);
        writeln(out, "div.toolbar-content{");
        writeln(out, "    position           : relative;");
        writeln(out, "    top                : " + topMargin + ";");
        writeln(out, "    left               : " + leftMargin + ";");
        writeln(out, "    height             : " + getFormattedToolbarInnerHeight() + ";");
        writeln(out, "    width              : " + getFormattedDataAreaInnerWidth(ThemeDimensions.ONE_COL_LAYOUT) + ";");
        writeln(out, "    overflow           : visible;");
        writeln(out, "}");
        writeln(out);
        writeln(out, "div.lefthandbar{");
        writeln(out, "    display            : inline;");
        writeln(out, "    float              : left;");
        writeln(out, "    width              : " + getFormattedLeftColumnWidth() + ";");
        writeln(out, "    margin             : 0" + getUnit() + " 0" + getUnit() + " " + spacing + " " + spacing + ";");
        writeln(out, "}");
        writeln(out);
        writeln(out, "div.lefthandbar-content{");
        writeln(out, "    width              : " + getFormattedLeftColumnInnerWidth() + ";");
        writeln(out, "    min-height         : 20px;");
        writeln(out, "    position           : relative;");
        writeln(out, "    margin             : " + topMargin + " " + rightMargin + " " + contentBottomMargin + " " + leftMargin + ";");
        writeln(out, "}");
        writeln(out);
        writeln(out, "div.dataarea_00{");
        writeln(out, "    display            : inline;");
        writeln(out, "    float              : left;");
        writeln(out, "    width              : " + getFormattedDataAreaWidth(ThemeDimensions.ONE_COL_LAYOUT) + ";");
        writeln(out, "    margin             : 0" + getUnit() + " " + spacing + " " + spacing + " " + spacing + ";");
        writeln(out, "}");
        writeln(out);
        writeln(out, "div.dataarea-content_00{");
<<<<<<< HEAD
        writeln(out, "    width              : " + getFormattedDataAreaInnerWidth(ThemeDimensions.ONE_COL_LAYOUT) + ";");
=======
        writeln(out, "    width              : " + getFormattedDataAreaInnerWidth(ThemeDimensions.ONE_COL_LAYOUT) + ";"); 
>>>>>>> 658c3789ba306595d25a6d602e528835fc324c56
        writeln(out, "    min-height         : 20px;");
        writeln(out, "    position           : relative;");
        writeln(out, "    margin             : " + topMargin + " " + rightMargin + " " + contentBottomMargin + " " + leftMargin + ";");
        writeln(out, "}");
        writeln(out);
        writeln(out, "div.dataarea_01{");
        writeln(out, "    display            : inline;");
        writeln(out, "    float              : left;");
        writeln(out, "    width              : " + getFormattedDataAreaWidth(ThemeDimensions.TWO_COL_LAYOUT_NOLEFTPANE) + ";");
        writeln(out, "    margin             : 0" + getUnit() + " 0" + getUnit() + " " + spacing + " " + spacing + ";");
        writeln(out, "}");
        writeln(out);
        writeln(out, "div.dataarea-content_01{");
        writeln(out, "    width              : " + getFormattedDataAreaInnerWidth(ThemeDimensions.TWO_COL_LAYOUT_NOLEFTPANE) + ";");
        writeln(out, "    min-height         : 20px;");
        writeln(out, "    position           : relative;");
        writeln(out, "    margin             : " + topMargin + " " + rightMargin + " " + contentBottomMargin + " " + leftMargin + ";");
        writeln(out, "}");
        writeln(out);
        writeln(out, "div.dataarea_10{");
        writeln(out, "    display            : inline;");
        writeln(out, "    float              : left;");
        writeln(out, "    width              : " + getFormattedDataAreaWidth(ThemeDimensions.TWO_COL_LAYOUT_NORIGHTPANE) + ";");
        writeln(out, "    margin             : 0" + getUnit() + " 0" + getUnit() + " " + spacing + " " + spacing + ";");
        writeln(out, "}");
        writeln(out);
        writeln(out, "div.dataarea-content_10{");
        writeln(out, "    width              : " + getFormattedDataAreaInnerWidth(ThemeDimensions.TWO_COL_LAYOUT_NORIGHTPANE) + ";");
        writeln(out, "    min-height         : 20px;");
        writeln(out, "    position           : relative;");
        writeln(out, "    margin             : " + topMargin + " " + rightMargin + " " + contentBottomMargin + " " + leftMargin + ";");
        writeln(out, "}");
        writeln(out);
        writeln(out, "div.dataarea_11{");
        writeln(out, "    display            : inline;");
        writeln(out, "    float              : left;");
        writeln(out, "    width              : " + getFormattedDataAreaWidth(ThemeDimensions.THREE_COL_LAYOUT) + ";");
        writeln(out, "    margin             : 0" + getUnit() + " 0" + getUnit() + " " + spacing + " " + spacing + ";");
        writeln(out, "}");
        writeln(out);
        writeln(out, "div.dataarea-content_11{");
        writeln(out, "    width              : " + getFormattedDataAreaInnerWidth(ThemeDimensions.THREE_COL_LAYOUT) + ";");
        writeln(out, "    min-height         : 20px;");
        writeln(out, "    position           : relative;");
        writeln(out, "    margin             : " + topMargin + " " + rightMargin + " " + contentBottomMargin + " " + leftMargin + ";");
        writeln(out, "}");
        writeln(out);
        writeln(out, "div.righthandbar{");
        writeln(out, "    display            : inline;");
        writeln(out, "    float              : right;");
        writeln(out, "    width              : " + getFormattedRightColumnWidth() + ";");
        writeln(out, "    margin             : 0" + getUnit() + " " + spacing + " " + spacing + " " + spacing + ";");
        writeln(out, "}");
        writeln(out);
        writeln(out, "div.righthandbar-content{");
        writeln(out, "    width              : " + getFormattedRightColumnInnerWidth() + ";");
        writeln(out, "    min-height         : 20px;");
        writeln(out, "    position           : relative;");
        writeln(out, "    margin             : " + topMargin + " " + rightMargin + " " + contentBottomMargin + " " + leftMargin + ";");
        writeln(out, "}");
        writeln(out);
        writeln(out, "div.footer{");
        writeln(out, "    display            : inline;");
        writeln(out, "    float              : left;");
        writeln(out, "    margin             : 0" + getUnit() + " " + spacing + " " + spacing + " " + spacing + ";");
        writeln(out, "}");
        writeln(out);
        writeln(out, "div.footer-content{");
        writeln(out, "    height             : " + getFormattedFooterInnerHeight() + ";");
        writeln(out, "    width              : " + getFormattedDataAreaInnerWidth(ThemeDimensions.ONE_COL_LAYOUT) + ";");
        writeln(out, "    position           : relative;");
        writeln(out, "    margin             : " + topMargin + " " + rightMargin + " " + contentBottomMargin + " " + leftMargin + ";");
        writeln(out, "}");
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
        writeln(out, "div.dummy{");
        writeln(out, "    position           : relative;");
        writeln(out, "    height             : 1px;");
        writeln(out, "    width              : 100%;");
        writeln(out, "    border             : none;");
        writeln(out, "}");
        writeln(out);
        writeln(out, ".vspace {");
        writeln(out, "    clear              : both;");
        writeln(out, "    display            : block;");
        writeln(out, "    overflow           : hidden;");
        writeln(out, "    visibility         : hidden;");
        writeln(out, "    width              : 0;");
        writeln(out, "    height             : 0;");
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
