/*
 *  Document : ThemeEngineConfig.java
 *  Created on  28.05.2011, 22:02:19
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

import  java.util.ArrayList;
import  java.util.HashMap;
import  java.util.Iterator;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigException;

/**
 * Model representation of a theme engine configuration.
 * @author Robert Diawara
 */
public class ThemeEngineConfig extends ThemeEngineModel{
    /** Holds the VFS path to the folder, which contains all theme configurations. */
    private String                       configFolder;
    /** Defines the name of the URL parameter, which can be used at runtime to select a theme. */
    private String                       runtimeParameter;
    /** Holds the VFS path to the theme, which will be userd as default theme. */
    private ThemeConfig                  defaultTheme;
    /** Al list of all theme configurations contained in the engine configuration. */
    private ArrayList<ThemeConfig>       themes;
    /** A hashmap with all theme configurations contained in the engine configuration. */
    private HashMap<String, ThemeConfig> themesMap;

    /**
     * Default constructor. Initializes the object with default values.
     */
    public ThemeEngineConfig(){
        themes = new ArrayList<ThemeConfig>();
        themesMap = new HashMap<String, ThemeConfig>();
        super.setNew();
    }

    /**
     * Get the value of configFolder
     * @return the value of configFolder
     */
    public String getConfigFolder() {
        return configFolder;
    }

    /**
     * Set the value of configFolder
     * @param configFolder new value of configFolder
     */
    public void setConfigFolder(String configFolder) {
        this.checkStatus(this.configFolder, configFolder);
        this.configFolder = configFolder;
    }

    /**
     * Get the value of defaultTheme
     * @return the value of defaultTheme
     */
    public ThemeConfig getDefaultTheme() {
        return defaultTheme;
    }

    /**
     * Set the value of defaultTheme
     * @param defaultTheme new value of defaultTheme
     */
    public void setDefaultTheme(ThemeConfig defaultTheme) {
        this.checkStatus(this.defaultTheme, defaultTheme);
        this.defaultTheme = defaultTheme;
    }

    /**
     * Get the value of runtimeParameter
     * @return the value of runtimeParameter
     */
    public String getRuntimeParameter() {
        return ((runtimeParameter != null) && (runtimeParameter.trim().length() > 0)) ? runtimeParameter : null ;
    }

    /**
     * Set the value of runtimeParameter
     * @param runtimeParameter new value of runtimeParameter
     */
    public void setRuntimeParameter(String runtimeParameter) {
        this.runtimeParameter = runtimeParameter;
    }

    
    /**
     * Adds a new theme configuration to the theme engine configuration.
     * @param p_themeConfig The theme configuration to be added.
     * @throws ThemeConfigException When the theme configuration to be added is null, has
     * no name or already exists within the theme engine configuration.
     */
    public void addThemeConfig(ThemeConfig p_themeConfig) throws ThemeConfigException{
        // Abort with an exception, when the theme configuration to be
        // added is null.
        if(p_themeConfig == null){
            throw new ThemeConfigException("Could not add a null theme configuration !");
        }

        // Abort with an exception, when theme configuration has no name.
        if(p_themeConfig.getName() == null){
            throw new ThemeConfigException("can not add a theme configuration without a name !");
        }

        // Abort with an exception, when the theme configuration already exists.
        if(themesMap.containsKey(p_themeConfig.getName())){
            throw new ThemeConfigException("Can not add an already existing theme configuration !");
        }

        // Add the theme configuration, when everything went ok.
        themes.add(p_themeConfig);
        themesMap.put(p_themeConfig.getName(), p_themeConfig);
    }

    /**
     * Checks if a theme configuration exists within the engine configuration or not.
     * @param p_themeConfig The theme configuration whose existance within the engine
     * configuration has to be checked.
     * @return <code>true</code> if the theme configuration exists within the engine
     * configuration <code>false</code> otherwise.
     * @throws ThemeConfigException When the parameter <code>p_themeConfig</code>, which
     * defines the theme configuration is null or the theme configurations has no name.
     */
    public boolean existsThemeConfig(ThemeConfig p_themeConfig) throws ThemeConfigException{
        // Abort with an exception, when the theme configuration to be
        // added is null.
        if(p_themeConfig == null){
            throw new ThemeConfigException("Can not check against a null theme configuration !");
        }

        // Abort with an exception, when the name of the theme configuration to be added is null.
        if(p_themeConfig.getName() == null){
            throw new ThemeConfigException("The theme configuration is  not initialized with a correct name !");
        }

        // Return the result value.
        return themesMap.containsKey(p_themeConfig.getName());
    }

    /**
     * Removes a theme configuration from the engine configuration.
     * @param p_name The name of the theme configuration to be removed.
     * @return The removed theme configuration, if one was removed.
     */
    public ThemeConfig removeThemeConfig(String p_name){
        ThemeConfig            result = null;
        Iterator<ThemeConfig>  it;
        int                    index;
        ThemeConfig            currentTheme;

        // Proceed only, when a URI provided and a script reference with the URI exists.
        if((p_name != null) && (themesMap.containsKey(p_name))){
            themesMap.remove(p_name);
            for(it = themes.iterator(), index = 0; (it.hasNext() && (result == null)); index ++){
                currentTheme = it.next();
                if(currentTheme.getName().equals(p_name)){
                    result = themes.remove(index);
                }
            }
        }
        return result;
    }

    /**
     * Removes all theme configurations from the engine configuration.
     */
    public void clearThemeConfigs(){
        themes = new ArrayList<ThemeConfig>();
        themesMap = new HashMap<String, ThemeConfig>();
     }

    /**
     * Returns a an array representing the list of all theme configurations contained
     * in the theme engine configuration.
     * @return An array containing all theme configurations.
     */
    public ThemeConfig[] listThemeConfigs(){
        ThemeConfig[]         result = new ThemeConfig[themes.size()];
        int                   index;
        Iterator<ThemeConfig> it;

        for(it = themes.iterator(), index = 0; it.hasNext(); index ++){
            result[index] = it.next();
        }
        return result;
    }

    /**
     * Get the number of theme configurations contained in the theme engine
     * configuration.
     * @return The number of theme configurations.
     */
    public int ThemeConfigCount(){
        return themes.size();
    }
}
