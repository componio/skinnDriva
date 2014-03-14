/*
 *  Document : ThemeConfig.java
 *  Created on  28.05.2011, 22:03:24
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

import  java.util.List;
import  java.util.ArrayList;
import  java.util.HashMap;
import  java.util.Iterator;

import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigException;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigInitializationException;

/**
 * Theme config model, which holds all data of a theme engine configuration.
 * @author Robert Diawara
 */
public class ThemeConfig extends ThemeEngineModel{
    private String                           resourceId;
    private String                           name;
    private String                           unit;                 // The unit. 
    private boolean                          showLeftHandBar;
    private boolean                          showRightHandBar;
    private boolean                          showToolBar;
    private ThemeDimensions                  dimensions;
    private GridModel                        grid;
    private ArrayList<ScriptReference>       scriptReferences;     // A list with all script references contained in the theme configuration.
    private HashMap<String, ScriptReference> referencesMap;        // A hashmap with all script references contained in the theme configuration.
    private List<StyleReference>             styles;               // A list with all css styles used by all pages, which a theme is applied to.
    private HashMap<String, StyleReference>  stylesMap;            // A hashmap with all css styles used ba all pages, which a theme is applied to.
    private List<ThemeFormatter>             formatters;           // A list with all formatters assigned to a theme.
    private HashMap<String, ThemeFormatter>  formattersMap;        // A hashmap with all formatters assigned to a theme.
    private String                           headerTemplate;
    private String                           rightHandTemplate;
    private String                           footerTemplate;
    private String                           leftHandTemplate;
    private String                           toolbarTemplate;
    private String                           vfsPath;
    private String                           mainTemplate;
    private String                           favIcon;              // The favicon for the theme.


    /**
     * Default Constructor, which initializes the object with all
     * its default values.
     */
    public ThemeConfig(){
        scriptReferences = new ArrayList<ScriptReference>();
        referencesMap = new HashMap<String, ScriptReference>();
        styles = new ArrayList<StyleReference>();
        stylesMap = new HashMap<String, StyleReference>();
        formatters = new ArrayList<ThemeFormatter>();
        formattersMap = new HashMap<String, ThemeFormatter>();
        unit = null;
        resourceId = null;
        favIcon = null;
        showLeftHandBar = true;
        showRightHandBar = true;
        showToolBar = true;
        super.setNew();
    }
    
    /**
     * Gets the status of the bean. This status can only be changed or unchanged.
     * @return The status.
     */
    @Override
    public int getStatus(){
        int                       result    = super.getStatus();
        Iterator<ScriptReference> scriptsIt;
        
        if((grid != null) && (result == ThemeEngineModel.STATUS_UNCHANGED)){
            result = grid.getStatus();
        }
        if((dimensions != null) && (result == ThemeEngineModel.STATUS_UNCHANGED)){
            result = dimensions.getStatus();
        }
        if((scriptReferences != null) && (result == ThemeEngineModel.STATUS_UNCHANGED)){
            for(scriptsIt = scriptReferences.iterator(); (scriptsIt.hasNext()) && (result == ThemeEngineModel.STATUS_UNCHANGED);){
                result = scriptsIt.next().getStatus();
            }
        }
        return result;
    }
    
    /**
     * Sets the status to unchanged.
     */
    @Override
    public void setUnchanged(){
        Iterator<ScriptReference> scriptsIt;
        
        super.setUnchanged();
        if(dimensions != null){
            dimensions.setUnchanged();
        }
        if(scriptReferences !=null){
            for(scriptsIt = scriptReferences.iterator(); scriptsIt.hasNext();){
                scriptsIt.next().setUnchanged();
            }
        }
        if(hasGrid()){
            getGrid().setUnchanged();
        }
    }
    
    /**
     * Sets the status to unchanged.
     */
    @Override
    public void setNew(){
        Iterator<ScriptReference> scriptsIt;
        
        super.setNew();
        if(dimensions != null){
            dimensions.setNew();
        }
        if(scriptReferences !=null){
            for(scriptsIt = scriptReferences.iterator(); scriptsIt.hasNext();){
                scriptsIt.next().setNew();
            }
        }
        if(hasGrid()){
            getGrid().setNew();
        }
    }
    
    /**
     * Formats an integer value by converting it to a string and appending the unit of theme (if on is defined) to 
     * this string.
     * @param p_Value The integer value to be formatted
     * @return The formatted value.
     */
    public String formatValue(int p_Value){
        StringBuffer sb;
        
        sb = new StringBuffer(String.valueOf(p_Value));
        if(getUnit() != null){
            sb.append(getUnit());
        }
        return sb.toString();
    }
    
    /**
     * Resets the dimensions of the theme configuration.
     */
    public void clearDimensions(){
        dimensions = null;
        setChanged();
    }
    
    /**
     * Resets the grid of the theme configuration to null
     */
    public void clearGrid(){
        if(grid != null){
            grid = null;
            setChanged();
        }
    }


    /**
     * Adds a new script reference to the theme configuration.
     * @param p_scriptReference The script reference to be added.
     * @throws ThemeConfigException When the script reference is null, the script URI of
     * the script reference is null, or the script reference already exists within this
     * theme configuration.
     */
    public void addScriptReference(ScriptReference p_scriptReference) throws ThemeConfigException{
        // Abort with an exception, when the script reference to be
        // added is null.
        if(p_scriptReference == null){
            throw new ThemeConfigException("Could not add a null script reference !");
        }

        // Abort with an exception, when the name of the script within the
        // script reference to be added is null.
        if(p_scriptReference.getScriptURI() == null){
            throw new ThemeConfigException("Script reference is  not initialized with a correct script URI !");
        }

        // Abort with an exception, when the script reference already exists.
        if(referencesMap.containsKey(p_scriptReference.getScriptURI())){
            throw new ThemeConfigException("Can not add an already existing script reference !");
        }

        // Add the script reference, when everything went ok.
        scriptReferences.add(p_scriptReference);
        referencesMap.put(p_scriptReference.getScriptURI(), p_scriptReference);
        setChanged();
    }

    /**
     * Checks, if a script reference exists within this theme configuration.
     * @param p_scriptReference The script reference to be checked.
     * @return <code>true</code>, if the script reference exists, <code>false</code> otherwise.
     * @throws ThemeConfigException when the parameter <code>p_scriptReference</code> is null
     * or the script URI, which is a property of this parameter, is null.
     */
    public boolean hasScriptReference(ScriptReference p_scriptReference) throws ThemeConfigException{
        // Abort with an exception, when the script reference to be
        // added is null.
        if(p_scriptReference == null){
            throw new ThemeConfigException("Can not check against a null script reference !");
        }

        // Abort with an exception, when the name of the script within the
        // script reference to be added is null.
        if(p_scriptReference.getScriptURI() == null){
            throw new ThemeConfigException("Script reference is  not initialized with a correct script URI !");
        }

        // Return the result value.
        return referencesMap.containsKey(p_scriptReference.getScriptURI());
    }

    /**
     * Removes a script reference from the theme configuration.
     * @param p_scriptURI The URI of the theme configuration, which is to be removed
     * @return The removed script reference.
     */
    public ScriptReference removeScriptReference(String p_scriptURI){
        ScriptReference            result = null;
        Iterator<ScriptReference>  it;
        int                        index;
        ScriptReference            currentReference;

        // Proceed only, when a URI provided and a script reference with the URI exists.
        if((p_scriptURI != null) && (referencesMap.containsKey(p_scriptURI))){
            referencesMap.remove(p_scriptURI);
            for(it = scriptReferences.iterator(), index = 0; (it.hasNext() && (result == null)); index ++){
                currentReference = it.next();
                if(currentReference.getScriptURI().equals(p_scriptURI)){
                    result = scriptReferences.remove(index);
                }
            }
            setChanged();
        }
        return result;
    }
    
    /**
     * Does a reset, by removing all script references.
     */
    public void clearScriptReferences(){
        scriptReferences = new ArrayList<ScriptReference>();
        referencesMap = new HashMap<String, ScriptReference>();
        setChanged();
    }

    /**
     * Returns an array with all script reverences contained in the theme configuration.
     * @return An array with all script references contained in the theme configuration.
     */
    public ScriptReference[] listScriptReferences(){
        ScriptReference[]         result = new ScriptReference[scriptReferences.size()];
        int                       index;
        Iterator<ScriptReference> it;

        for(it = scriptReferences.iterator(), index = 0; it.hasNext(); index ++){
            result[index] = it.next();
        }
        return result;
    }

    /**
     * Returns a script reference identified by its uri If the uri identifying the script reference is null or empty
     * or a script reference with the given id doesn't exist, this method will return null.
     * @param p_referenceURI The uri identifying the script reference to be returned.
     * @return  The script reference, or null if none was found.
     */
    public ScriptReference getScriptReference(String p_referenceURI){
        ScriptReference           result  = null;
        ScriptReference           current = null;
        Iterator<ScriptReference> it;
        
        if(p_referenceURI != null){
            for(it = scriptReferences.iterator(); (it.hasNext()) && (result == null);){
                current = it.next();
                if(current.getScriptURI().equals(p_referenceURI)){
                    result = current;
                }
            }
        }
        return result;
    }
    
    /**
     * Returns the count of script references contained in the theme configuration.
     * @return The count of script references contained in the theme configration.
     */
    public int scriptReferenceCount(){
        return scriptReferences.size();
    }

    /**
     * Adds a new formatter to the theme configuration.
     * @param p_formatter The formatter to be added.
     * @throws ThemeConfigException When the formatter or one of its mandatory properties, which are
     * resourceType containerType and jsp, are null.
     */
    public void addFormatter(ThemeFormatter p_formatter) throws ThemeConfigException{
        String formatterKey;
        
        // Abort with an exception, when the formatter to be
        // added is null.
        if(p_formatter == null){
            throw new ThemeConfigException("Could not add a null formatter !");
        }

        // Abort with an exception, when mandatory fields are not set.
        if((p_formatter.getResourceType() == null) || (p_formatter.getResourceType().trim().length() == 0)){
            throw new ThemeConfigException("Mandatory property \"resourceType\" is not set !");
        }
        if((p_formatter.getContainerType() == null) || (p_formatter.getContainerType().trim().length() == 0)){
            throw new ThemeConfigException("Mandatory property \"containerType\" is not set !");
        }
        if((p_formatter.getJsp() == null) || (p_formatter.getJsp().trim().length() == 0)){
            throw new ThemeConfigException("Mandatory property \"jsp\" is not set !");
        }
        
        // Get the key of the formatter
        formatterKey = p_formatter.getKey();

        // Abort with an exception, when the formatter already exists.
        if(referencesMap.containsKey(formatterKey)){
            throw new ThemeConfigException("Can not add an already existing formatter !");
        }

        // Add the script reference, when everything went ok.
        formatters.add(p_formatter);
        formattersMap.put(formatterKey, p_formatter);
        setChanged();
    }

    /**
     * Checks, if a formatter exists within this theme configuration.
     * @param p_formatter The formatter to be checked.
     * @return <code>true</code>, if the formatter, <code>false</code> otherwise.
     * @throws ThemeConfigException when the parameter <code>p_formatter</code> is null
     * or one of its mandatory fields (<code>resourceType</code>, <code>containerType</code>, <code>jsp</code>)
     * is not set.
     */
    public boolean hasFormatter(ThemeFormatter p_formatter) throws ThemeConfigException{
        // Abort with an exception, when the formatter to be
        // added is null.
        if(p_formatter == null){
            throw new ThemeConfigException("Could not add a null formatter !");
        }

        // Abort with an exception, when mandatory fields are not set.
        if((p_formatter.getResourceType() == null) || (p_formatter.getResourceType().trim().length() == 0)){
            throw new ThemeConfigException("Mandatory property \"resourceType\" is not set !");
        }
        if((p_formatter.getContainerType() == null) || (p_formatter.getContainerType().trim().length() == 0)){
            throw new ThemeConfigException("Mandatory property \"containerType\" is not set !");
        }
        if((p_formatter.getJsp() == null) || (p_formatter.getJsp().trim().length() == 0)){
            throw new ThemeConfigException("Mandatory property \"jsp\" is not set !");
        }

        // Return the result value.
        return formattersMap.containsKey(p_formatter.getKey());
    }

    /**
     * Removes a formatter from the theme configuration.
     * @param p_resourceType The resource type, used to identify the formatter.
     * @param p_containerType The container type used to identify the fromatter.
     * @return The removed formatter, if one was found and removed. Otherwise null.
     */
    public ThemeFormatter removeFormatter(String p_resourceType, String p_containerType){
        ThemeFormatter             result = null;
        Iterator<ThemeFormatter>   it;
        int                        index;
        ThemeFormatter             currentFormatter;
        String                     key              = ThemeFormatter.getKey(p_resourceType, p_containerType);

        // Proceed only, when a URI provided and a script reference with the URI exists.
        if((key != null) && (formattersMap.containsKey(key))){
            formattersMap.remove(key);
            for(it = formatters.iterator(), index = 0; (it.hasNext() && (result == null)); index ++){
                currentFormatter = it.next();
                if(currentFormatter.getKey().equals(key)){
                    result = formatters.remove(index);
                }
            }
            setChanged();
        }
        return result;
    }
    
    /**
     * Does a reset, by removing all formatters.
     */
    public void clearFormatters(){
        formatters = new ArrayList<ThemeFormatter>();
        formattersMap = new HashMap<String, ThemeFormatter>();
        setChanged();
    }

    /**
     * Returns an array with all formatters contained in the theme configuration.
     * @return An array with all formatters contained in the theme configuration.
     */
    public ThemeFormatter[] listFormatters(){
        ThemeFormatter[]         result = new ThemeFormatter[formatters.size()];
        int                       index;
        Iterator<ThemeFormatter> it;

        for(it = formatters.iterator(), index = 0; it.hasNext(); index ++){
            result[index] = it.next();
        }
        return result;
    }

    /**
     * Returns a formatter identified by its resource type and its container type, or null if one of the identifiers
     * is not set or no formatter was found.
     * @param p_resourceType Resource type to identify the formatter.
     * @param p_containerType Container type to identify the formatter.
     * @return The formatter if the identifiers were valid and one was found. Otherwise null.
     */
    public ThemeFormatter getFormatter(String p_resourceType, String p_containerType){
        ThemeFormatter           result  = null;
        String                   key     = ThemeFormatter.getKey(p_resourceType, p_containerType);
        
        if(key != null){
            result = getFormatter(key);
        }
        return result;
    }
    
    /**
     * Returns a formatter identified by its key, or null if hte key is not set or no formatter was found.
     * @param p_formatterKey Resource type to identify the formatter.
     * @return The formatter if the key was valid and one was found. Otherwise null.
     */
    public ThemeFormatter getFormatter(String p_formatterKey){
        ThemeFormatter           result  = null;
        
        if(p_formatterKey != null){
            result = formattersMap.get(p_formatterKey);
        }
        return result;
    }
    
    /**
     * Returns the count of the formatters contained in the theme configuration.
     * @return The count of the formatters contained in the theme configration.
     */
    public int formatterCount(){
        return formatters.size();
    }
    
    /**
     * Adds a new style to the theme configuration.
     * @param p_style The style to be added.
     * @throws ThemeConfigException When the style to be added is null or already exists
     * within the theme configuration.
     */
    public void addStyle(String p_style) throws ThemeConfigException{
        StyleReference styleRef = new StyleReference();
        
        // Abort with an exception, when the style to be added is null.
        if(p_style == null){
            throw new ThemeConfigException("Could not add a null style !");
        }

        // Abort with an exception, when the style already exists.
        if(stylesMap.containsKey(p_style)){
            throw new ThemeConfigException("Can not add an already existing style !");
        }

        // Finally add the style, when everything went ok.
        styleRef = new StyleReference();
        styleRef.setURI(p_style);
        styles.add(styleRef);
        stylesMap.put(p_style, styleRef);
        setChanged();
    }

    /**
     * Adds a new style to the theme configuration.
     * @param p_style The style to be added.
     * @param p_group The group, which the style has to be assigned to.
     * @throws ThemeConfigException When the style to be added is null or already exists
     * within the theme configuration.
     */
    public void addStyle(String p_style, String p_group) throws ThemeConfigException{
        addStyle(p_style, p_group, null, null);
    }

    /**
     * Adds a new style to the theme configuration.
     * @param p_style The style to be added.
     * @param p_group The group, which the style has to be assigned to.
     * @param p_media The media, which the CSS style has to be used for.
     * @param p_userAgents A list of regular expressions, which determines the browsers which the CSS
     * style has to be used for.
     * @throws ThemeConfigException When the style to be added is null or already exists
     * within the theme configuration.
     */
    public void addStyle(String p_style, String p_group, String p_media, ArrayList<String> p_userAgents) throws ThemeConfigException{
        StyleReference styleRef = new StyleReference();
        
        // Abort with an exception, when the style to be added is null.
        if(p_style == null){
            throw new ThemeConfigException("Could not add a null style !");
        }

        // Abort with an exception, when the style already exists.
        if(stylesMap.containsKey(p_style)){
            throw new ThemeConfigException("Can not add an already existing style !");
        }

        // Finally add the style, when everything went ok.
        styleRef = new StyleReference();
        styleRef.setURI(p_style);
        if((p_group != null) && (p_group.trim().length() > 0)){
            styleRef.setGroup(p_group);
        }
        if((p_media != null) && (p_media.trim().length() > 0)){
            styleRef.setMedia(p_media);
        }
        if((p_userAgents != null) && (p_userAgents.size() > 0)){
            styleRef.setUserAgents(p_userAgents);
        }
        styles.add(styleRef);
        stylesMap.put(p_style, styleRef);
        setChanged();
    }
    
    /**
     * Checks if a style exists within the theme configuration.
     * @param p_style The style whose existance within the theme configuration has
     * to be checked.
     * @return <code>true</code> when the style exists, <code>false</code> otherwise.
     * @throws ThemeConfigException When the parameter, <code>p_style</code>, which
     * the style is passed with, is null.
     */
    public boolean hasStyle(String p_style) throws ThemeConfigException{
        // Abort with an exception, when the style to be added is null.
        if(p_style == null){
            throw new ThemeConfigException("Can not check against a null style !");
        }

        // Finally check and return the result.
        return stylesMap.containsKey(p_style);
    }

    /**
     * Checks if a grid is defined within the theme.
     * @return <code>true</code> if a grid is defined. Otherwise <code>false</code>
     */
    public boolean hasGrid(){
        return grid != null;
    }
    
    /**
     * Removes a style from the theme configuration.
     * @param p_style The style to be removed.
     * @return The removed style, when one was removed.
     */
    public StyleReference removeStyle(String p_style){
        StyleReference            result = null;
        Iterator<StyleReference>  it;
        int                       index;
        StyleReference            currentStyle;

        // Proceed only, when a URI provided and a script reference with the URI exists.
        if((p_style != null) && (stylesMap.containsKey(p_style))){
            stylesMap.remove(p_style);
            for(it = styles.iterator(), index = 0; (it.hasNext() && (result == null)); index ++){
                currentStyle = it.next();
                if(currentStyle.getURI().equals(p_style)){
                    result = styles.remove(index);
                }
            }
        }
        return result;
    }

    /**
     * Performs a reset, by removing all styles.
     */
    public void clearStyles(){
        styles.clear();
        stylesMap.clear();
        setChanged();
    }

    /**
     * Returns an array with all style uris contained in the theme configuration.
     * @return An array with all style uris contained in the theme configuration.
     */
    public String[] listStyleURIs(){
        String[]                 result = new String[styles.size()];
        int                      index;
        Iterator<StyleReference> it;

        for(it = styles.iterator(), index = 0; it.hasNext(); index ++){
            result[index] = it.next().getURI();
        }
        return result;
    }

    /**
     * Returns an array with all styles contained in the theme configuration.
     * @return An array with all styles contained in the theme configuration.
     */
    public StyleReference[] listStyles(){
        StyleReference[]         result = new StyleReference[styles.size()];
        int                      index;
        Iterator<StyleReference> it;

        for(it = styles.iterator(), index = 0; it.hasNext(); index ++){
            result[index] = it.next();
        }
        return result;
    }

    /**
     * Returns the number of styles assigned to this theme configurations.
     * @return The number of styles.
     */
    public int styleCount(){
        return styles.size();
    }

    /**
     * Getter for the property styles. Gets the styles assigned to the theme.
     * @return Al list with the styles assigned to the theme.
     */
    public List<String> getStyleURIs(){
        Iterator<StyleReference> stylesIt;
        List<String>             result   = new ArrayList<String>();
        
        if(styles != null){
            for(stylesIt = styles.iterator(); stylesIt.hasNext();){
                result.add(stylesIt.next().getURI());
            }
        }
        return result;
    }
    
    /**
     * Gets a stylesheet reference from the theme configuration
     * @param p_URI The URI of the stylesheet reference.
     * @return The stylesheet reference, if one with the specified URI was found.
     * Otherwise null.
     */
    public StyleReference getStyle(String p_URI){
        return stylesMap != null ? stylesMap.get(p_URI) : null;
    }
    
    /**
     * Setter for the property styles. Assigns styles to the theme.
     * @param p_styles The styles to be assigned to the theme.
     */
    public void setStyleURIs(List<String> p_styles){
        Iterator<String> stylesIt;
        StyleReference   currentStyle;
        
        checkStatus(this.styles, p_styles);
        if(p_styles != null){
            this.styles = new ArrayList<StyleReference>();
            this.stylesMap = new HashMap<String, StyleReference>();
            for(stylesIt = p_styles.iterator(); stylesIt.hasNext();){
                currentStyle = new StyleReference();
                currentStyle.setURI(stylesIt.next());
                this.styles.add(currentStyle);
                this.stylesMap.put(currentStyle.getURI(), currentStyle);
            }
        }else{
            this.styles = null;
            this.stylesMap = null;
        }
        setChanged();
    }
    
    /**
     * Getter gets the unit assigned to this theme.
     * @return The unit of this theme.
     */
    public String getUnit() {
       return ((unit == null) || (unit.trim().length() > 0)) ? unit : null;
    }

    /**
     * Set the value of unit
     * @param unit new value of unit
     */
    public void setUnit(String unit) {
        // Abort with an exception, when unit is not valid
        if((unit != null) && (!unit.matches("^(pt|pc|in|mm|cm|px|em|ex|%)$"))){
            throw new RuntimeException("Invalid value fir unit !");
        }
        checkStatus(this.unit, unit);
        this.unit = unit;
    }

    /**
     * Get the value of name
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     * @param name new value of name
     */
    public void setName(String name) {
        checkStatus(this.name, name);
        this.name = name;
    }

    /**
     * Get the value of id
     * @return the value of id
     */
    public String getId() {
        return name;
    }

    /**
     * Set the value of id
     * @param name new value of id
     */
    public void setId(String name) {
        checkStatus(this.name, name);
        this.name = name;
    }

    /**
     * Get the value of showLeftHandBar
     * @return the value of showLeftHandBar
     */
    public boolean isShowLeftHandBar() {
        return showLeftHandBar;
    }

    /**
     * Set the value of showLeftHandBar
     * @param showLeftHandBar new value of showLeftHandBar
     */
    public void setShowLeftHandBar(boolean showLeftHandBar) {
        checkStatus(this.showLeftHandBar, showLeftHandBar);
        this.showLeftHandBar = showLeftHandBar;
    }

    /**
     * Get the value of showRightHandBar
     * @return the value of showRightHandBar
     */
    public boolean isShowRightHandBar() {
        return showRightHandBar;
    }

    /**
     * Set the value of showRightHandBar
     * @param showRightHandBar new value of showRightHandBar
     */
    public void setShowRightHandBar(boolean showRightHandBar) {
        checkStatus(this.showRightHandBar, showRightHandBar);
        this.showRightHandBar = showRightHandBar;
    }

    /**
     * Get the value of showToolBar
     * @return the value of showToolBar
     */
    public boolean isShowToolBar() {
        return showToolBar;
    }

    /**
     * Set the value of showToolBar
     * @param showToolBar new value of showToolBar
     */
    public void setShowToolBar(boolean showToolBar) {
        checkStatus(this.showToolBar, showToolBar);
        this.showToolBar = showToolBar;
    }

    /**
     * Get the value of dimesions
     * @return the value of dimesions
     * @throws ThemeConfigInitializationException when the dimensions are not
     * initialized yet and the initialization fails
     */
    public ThemeDimensions getDimensions() throws ThemeConfigInitializationException{
        if(dimensions == null){
            dimensions = new ThemeDimensions(getUnit());
            setChanged();
        }
        return dimensions;
    }
    
    /**
     * Returns the grid, if one is defined.
     * @return The Grid
     */
    public GridModel getGrid(){
        if(grid == null){
            grid = new GridModel();
            setChanged();
        }
        return grid;
    }

    /**
     * Setter for the resource property resourceId
     * @param p_resourceId The resource id of the associated resource at VFS
     * @throws ThemeConfigException When the assignemt of the resource id fails, for which
     * reason ever.
     */
    public void setResourceId(String p_resourceId) throws ThemeConfigException{
        // Abort with an exception, if no resource id provided
        if(p_resourceId == null){
            throw new ThemeConfigException("Can not the resource id to null. Please provide a valid resource id !");
        }
        
        checkStatus(resourceId, p_resourceId);
        resourceId = p_resourceId;
    }
    
    /**
     * Gets the resource id of the associated resource at the VFS.
     * @return The resource id.
     */
    public String getResourceId(){
        return resourceId;
    }

    /**
     * Get the value of headerTemplate
     * @return the value of headerTemplate
     */
    public String getHeaderTemplate() {
        return ((headerTemplate != null) && (headerTemplate.trim().length() > 0)) ? headerTemplate : null;
    }

    /**
     * Set the value of headerTemplate
     * @param headerTemplate new value of headerTemplate
     */
    public void setHeaderTemplate(String headerTemplate) {
        checkStatus(this.headerTemplate, headerTemplate);
        this.headerTemplate = headerTemplate;
    }

    /**
     * Get the value of rightHandTemplate
     * @return the value of rightHandTemplate
     */
    public String getRightHandTemplate() {
        return ((rightHandTemplate != null) && (rightHandTemplate.trim().length() > 0)) ? rightHandTemplate : null;
    }

    /**
     * Set the value of rightHandTemplate
     *
     * @param rightHandTemplate new value of rightHandTemplate
     */
    public void setRightHandTemplate(String rightHandTemplate) {
        checkStatus(this.rightHandTemplate, rightHandTemplate);
        this.rightHandTemplate = rightHandTemplate;
    }

    /**
     * Get the value of footerTemplate
     * @return the value of footerTemplate
     */
    public String getFooterTemplate() {
        return ((footerTemplate != null) && (footerTemplate.trim().length() >0)) ? footerTemplate : null;
    }

    /**
     * Set the value of footerTemplate
     * @param footerTemplate new value of footerTemplate
     */
    public void setFooterTemplate(String footerTemplate) {
        checkStatus(this.footerTemplate, footerTemplate);
        this.footerTemplate = footerTemplate;
    }

    /**
     * Get the value of leftHandTemplate
     * @return the value of leftHandTemplate
     */
    public String getLeftHandTemplate() {
        return ((leftHandTemplate != null) && (leftHandTemplate.trim().length() > 0)) ? leftHandTemplate : null;
    }

    /**
     * Set the value of leftHandTemplate
     * @param leftHandTemplate new value of leftHandTemplate
     */
    public void setLeftHandTemplate(String leftHandTemplate) {
        checkStatus(this.leftHandTemplate, leftHandTemplate);
        this.leftHandTemplate = leftHandTemplate;
    }

    /**
     * Get the value of toolbarTemplate
     * @return the value of toolbarTemplate
     */
    public String getToolbarTemplate() {
        return ((toolbarTemplate != null) && (toolbarTemplate.trim().length() > 0)) ? toolbarTemplate : null;
    }

    /**
     * Set the value of toolbarTemplate
     * @param toolbarTemplate new value of toolbarTemplate
     */
    public void setToolbarTemplate(String toolbarTemplate) {
        checkStatus(this.toolbarTemplate, toolbarTemplate);
        this.toolbarTemplate = toolbarTemplate;
    }


    /**
     * Get the value of vfsPath
     * @return the value of vfsPath
     */
    public String getVfsPath() {
        return vfsPath;
    }

    /**
     * Set the value of vfsPath
     * @param vfsPath new value of vfsPath
     */
    public void setVfsPath(String vfsPath) {
        this.vfsPath = vfsPath;
    }


    /**
     * Get the value of mainTemplate
     * @return the value of mainTemplate
     */
    public String getMainTemplate() {
        return mainTemplate;
    }

    /**
     * Set the value of mainTemplate
     * @param mainTemplate new value of mainTemplate
     */
    public void setMainTemplate(String mainTemplate) {
        this.mainTemplate = mainTemplate;
    }

    /**
     * Get the value of favIcon
     * @return the value of favIcon
     */
    public String getFavIcon() {
        return (favIcon != null) && (favIcon.trim().length() > 0) ? favIcon : null;
    }

    /**
     * Set the value of favIcon
     * @param favIcon new value of favIcon
     */
    public void setFavIcon(String favIcon) {
        this.favIcon = favIcon;
    }
}
