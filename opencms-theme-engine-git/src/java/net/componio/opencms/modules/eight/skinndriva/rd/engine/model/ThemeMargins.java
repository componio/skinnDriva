/*
 *  Document : ThemeMargins.java
 *  Created on  28.05.2011, 22:16:51
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

import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigInitializationException;

/**
 * Defines all box margins within a page which the the theme is applied to.
 * @author Robert Diawara
 */
public class ThemeMargins extends ThemeEngineModel{
    /** Holds the unit associated to the numeric margin values. */
    private String unit;

    /** Holds the value of the property 'topMargin', which defines the
     * top margin of all boxes in the page. */
    private int topMargin;

    /** Holds the value of the property 'rightMargin', which defines the
     * right margin of all boxes in the page. */
    private int rightMargin;

    /** Holds the value of the property 'bottomMargin', which defines the
     * bottom margin of all boxes in the page. */
    private int bottomMargin;

    /** Holds the value of the property 'leftMargin', which defines the
     * left margin of all boxes in the page. */
    private int leftMargin;

    /**
     * Default constructor. Is defined as private here, to avoid an initialization without assigning
     * the mandatory theme configuraiton.
     */
    private ThemeMargins(){
        this.topMargin = 15;
        this.rightMargin = 15;
        this.bottomMargin = 15;
        this.leftMargin = 15;
    }

    /**
     * Constructor. Initializes a new instance of <code>ThemeMargins</code> and assigns the mandatory
     * <code>ThemeConfig</code> instance passed with the parameter <code>p_owner</code>.
     * @param p_unit  The unit, which will be associated to the numeric values of the dimensions.
     * @throws ThemeConfigInitializationException When the unit has an invalid value.
     */
    public ThemeMargins(String p_unit) throws ThemeConfigInitializationException{
        // Abort with an exception, when the unit is invalid.
        if((p_unit != null) && (!p_unit.matches("^(pt|pc|in|mm|cm|px|em|ex|%)$"))){
            throw new ThemeConfigInitializationException("Could not initialize theme config margins, " +
                    "because of wrong pattern for the unit. Unit has to match \"^(pt|pc|in|mm|cm|px|em|ex|%)$\" !");
        }
        this.unit = p_unit != null ? p_unit : "";
        this.topMargin = 0;
        this.rightMargin = 0;
        this.bottomMargin = 0;
        this.leftMargin = 0;
    }
 
    /**
     * Gets the unit for the numeric margin values.
     * @return The unit
     */
    private String getUnit(){
        return ((unit == null) || (unit.trim().length() > 0)) ? unit : null;
    }

    /**
     * Getter for the property 'topMargin' defining the top margin for all boxes of the
     * pages, which a theme is applied to.
     * @return The top margin.
     */
    public int getTopMargin() {
        return topMargin;
    }

    /**
     * Gets the value of the top margin as a string with the unit directly associated to it,
     * so that it can be directly inserted into HTML code.
     * @return The top margin with the associated unit.
     */
    public String getFormattedTopMargin(){
        return String.valueOf(topMargin) + (getUnit() != null ? getUnit() : "");
    }

    /**
     * Setter for the property 'topMargin' defining the top margin for all boxes of the
     * pages, which a theme is applied to.
     * @param topMargin The value of the top margin to be set.
     */
    public void setTopMargin(int topMargin) {
        // Here, we only assign integers greated than 0. In case of an invalid value, we assign 0
        checkStatus(this.topMargin, topMargin);
        this.topMargin = topMargin >= 0 ? topMargin : 0;
    }

    /**
     * Getter for the property 'rightMargin' defining the right margin for all boxes of the
     * pages, which a theme is applied to.
     * @return The right margin.
     */
    public int getRightMargin() {
        return rightMargin;
    }

    /**
     * Gets the value of the right margin as a string with the unit directly associated to it,
     * so that it can be directly inserted into HTML code.
     * @return The right margin with the associated unit.
     */
    public String getFormattedRightMargin(){
        return String.valueOf(rightMargin) + (getUnit() != null ? getUnit() : "");
    }

    /**
     * Setter for the property 'rightMargin' defining the right margin for all boxes of the
     * pages, which a theme is applied to.
     * @param rightMargin The value of the right margin to be set.
     */
    public void setRightMargin(int rightMargin) {
        // Here, we only assign integers greated than 0. In case of an invalid value, we assign 0
        checkStatus(this.rightMargin, rightMargin);
        this.rightMargin = rightMargin >= 0 ? rightMargin : 0;
    }

    /**
     * Getter for the property 'bottomMargin' defining the bottom margin for all boxes of the
     * pages, which a theme is applied to.
     * @return The bottom margin.
     */
    public int getBottomMargin() {
        return bottomMargin;
    }

    /**
     * Gets the value of the bottom margin as a string with the unit directly associated to it,
     * so that it can be directly inserted into HTML code.
     * @return The bottom margin with the associated unit.
     */
    public String getFormattedBottomMargin(){
        return String.valueOf(bottomMargin) + (getUnit() != null ? getUnit() : "");
    }

    /**
     * Setter for the property 'bottomMargin' defining the bottom margin for all boxes of the
     * pages, which a theme is applied to.
     * @param bottomMargin The value of the bottom margin to be set.
     */
    public void setBottomMargin(int bottomMargin) {
        // Here, we only assign integers greated than 0. In case of an invalid value, we assign 0
        checkStatus(this.bottomMargin, bottomMargin);
        this.bottomMargin = bottomMargin >= 0 ? bottomMargin : 0;
    }

    /**
     * Getter for the property 'leftMargin' defining the left margin for all boxes of the
     * pages, which a theme is applied to.
     * @return The left margin.
     */
    public int getLeftMargin() {
        return leftMargin;
    }

    /**
     * Gets the value of the left margin as a string with the unit directly associated to it,
     * so that it can be directly inserted into HTML code.
     * @return The left margin with the associated unit.
     */
    public String getFormattedLeftMargin(){
        return String.valueOf(leftMargin) + (getUnit() != null ? getUnit() : "");
    }

    /**
     * Setter for the property 'leftMargin' defining the left margin for all boxes of the
     * pages, which a theme is applied to.
     * @param leftMargin The value of the left margin to be set.
     */
    public void setLeftMargin(int leftMargin) {
        // Here, we only assign integers greated than 0. In case of an invalid value, we assign 0
        checkStatus(this.leftMargin, leftMargin);
        this.leftMargin = leftMargin >= 0 ? leftMargin : 0;
    }
}
