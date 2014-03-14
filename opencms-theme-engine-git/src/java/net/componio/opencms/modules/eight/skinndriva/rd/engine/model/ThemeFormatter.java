/*
 *  Document : ThemeFormatter.java
 *  Created on Mi, Okt 03 2012, 11:43:19
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

import net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigException;

/**
 * Defines a formatter for a theme. This allows to define different formatters to be used
 * for the same content in different themes.
 * @author Robert Diawara
 */
public class ThemeFormatter {
    /** Holds the value of the property resourceType.*/
    private String  resourceType;
    /** Holds the value of the property containerType.*/
    private String containerType;
    /** Holds the value of the property jsp.*/
    private String  jsp;
    /** Holds the value of the property minWidth.*/
    private int     minWidth;
    /** Holds the value of the property maxWidth.*/
    private int     maxWidth;
    /** Holds the value of the property usedForPreview.*/
    private boolean usedForPreview;
    /** Holds the value of the property searchable.*/
    private boolean searchable;
    /** Holds the theme config, which the formatter belongs to. */
    private ThemeConfig themeConfig;

    /**
     * Default Constructor. Sets the default values for the properties.
     * Is declared as private here, to avoid an instatiation without the 
     * mandatory theme configuration.
     */
    private ThemeFormatter(){
        resourceType = null;
        containerType = null;
        jsp = null;
        minWidth = -1;
        maxWidth = -1;
        usedForPreview = false;
        searchable = false;
    }
    
    /**
     * Constructor to intatiate a theme config with the mandatory theme config.
     * @param p_themeConfig 
     */
    public ThemeFormatter(ThemeConfig p_themeConfig){
        this();
        // Abort with an exception, when no theme config provided.
        if(p_themeConfig == null){
            throw new RuntimeException("Can not initialize a formatter without the associated theme config !");
        }
        this.themeConfig = p_themeConfig;
    }

    /**
     * Get the value of resourceType for the formatter
     * @return the value of resourceType
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * Set the value of resourceType
     * @param resourceType new value of resourceType for the formatter
     * @throws ThemeConfigException When the operation fails because of
     * an unexpected internal exception.
     */
    public void setResourceType(String resourceType) throws ThemeConfigException{
        if((resourceType != null) && (!resourceType.equals(this.resourceType))){
            // Remove the existing formatter from the theme, if it exists
            if((this.containerType != null) && (this.themeConfig.getFormatter(this.resourceType, this.containerType) != null)){
                this.themeConfig.removeFormatter(this.resourceType, this.containerType);
            }
            this.resourceType = resourceType;
            // Add the changed formatter to the theme config, if its data are complete.
            if((this.resourceType != null) && (this.containerType != null) && (this.jsp != null)){
                this.themeConfig.addFormatter(this);
            }
        }
    }

    /**
     * Get the value of containerType for the formatter
     * @return the value of containerType
     */
    public String getContainerType() {
        return containerType;
    }

    /**
     * Set the value of containerType for the formatter
     * @param containerType new value of containerType
     * @throws ThemeConfigException When the operation fails because of
     * an unexpected internal exception.
     */
    public void setContainerType(String containerType) throws ThemeConfigException{
        if((containerType != null) && (!containerType.equals(this.containerType))){
            // Remove the existing formatter from the theme, if it exists
            if((this.resourceType != null) && (this.themeConfig.getFormatter(this.resourceType, this.containerType) != null)){
                this.themeConfig.removeFormatter(this.resourceType, this.containerType);
            }
            this.containerType = containerType;
            // Add the changed formatter to the theme config, if its data are complete.
            if((this.resourceType != null) && (this.containerType != null) && (this.jsp != null)){
                this.themeConfig.addFormatter(this);
            }
        }
    }

    /**
     * Get the value of jsp for the formatter
     * @return the value of jsp
     */
    public String getJsp() {
        return jsp;
    }

    /**
     * Set the value of jsp for the formatter
     * @param jsp new value of jsp
     * @throws ThemeConfigException When the operation fails because of
     * an unexpected internal exception.
     */
    public void setJsp(String jsp) throws ThemeConfigException{
        this.jsp = jsp;
        
        // Add the changed formatter to the theme config, if its data are complete.
        if((this.resourceType != null) && (this.containerType != null) && (this.jsp != null) && (!themeConfig.hasFormatter(this))){
            this.themeConfig.addFormatter(this);
        }
}

    /**
     * Get the value of the minimum width for the formatter
     * @return the value of minWidth
     */
    public int getMinWidth() {
        return minWidth;
    }

    /**
     * Set the value of the minimum width for the formatter
     * @param minWidth new value of minWidth
     */
    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    /**
     * Get the value of the maximum width for the formatter
     * @return the value of maxWidth
     */
    public int getMaxWidth() {
        return maxWidth;
    }

    /**
     * Set the value of the maximum width for the formatter
     * @param maxWidth new value of maxWidth
     */
    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    /**
     * Determines if the formatter is used for previews
     * @return the value of usedForPreview
     */
    public boolean isUsedForPreview() {
        return usedForPreview;
    }

    /**
     * Set the value of usedForPreview to determine if the 
     * formatter is used for previews
     * @param usedForPreview new value of usedForPreview
     */
    public void setUsedForPreview(boolean usedForPreview) {
        this.usedForPreview = usedForPreview;
    }

    /**
     * Get the value of searchable. This determines if the output
     * of the formatter is searchable orn not
     * @return the value of searchable
     */
    public boolean isSearchable() {
        return searchable;
    }

    /**
     * Set the value of searchable. This determines if the output
     * of the formatter is searchable orn not
     * @param searchable new value of searchable
     */
    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }
    
    /**
     * Get a key for the formatter, which is to be used as a unique key
     * for HashMaps.
     * @return The unique key for the formatter.
     */
    public String getKey(){
        return ThemeFormatter.getKey(getResourceType(), getContainerType());
    }
    
    /**
     * Get a key for a formatter, which is to be used as a unique key
     * @param p_resourceType The resource type used to compose the key
     * @param p_containerType The container type used to compose the key
     * @return The composed key, when the two passed parameters are valid.
     * Otherwise null.
     */
    public static String getKey(String p_resourceType, String p_containerType){
        String       result  = null;
        StringBuffer buf;
        String       resType = p_resourceType;
        String       cType   = p_containerType;
        
        if((resType != null) && (cType != null) && (resType.trim().length() != 0) && (cType.trim().length() != 0)){
            buf = new StringBuffer(resType);
            buf.append(":");
            buf.append(cType);
            result = buf.toString();
        }
        return result;
    }
}
