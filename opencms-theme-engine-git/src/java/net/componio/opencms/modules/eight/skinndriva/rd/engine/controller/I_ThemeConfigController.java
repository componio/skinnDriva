/*
 *  Document : I_ThemeConfigController.java
 *  Created on Sa, Aug 06 2011, 20:18:28
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
package net.componio.opencms.modules.eight.skinndriva.rd.engine.controller;

import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeConfig;

import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigException;

import  java.util.ArrayList;

/**
 *
 * @author Robert Diawara
 */
public interface I_ThemeConfigController {
    /**
     * Loads a theme configuration from VFS
     * @param p_configId The ID of the theme configuration to be loaded.
     * @return The theme configuration, if one with a name matching the parameter <code>p_configId</code> was
     * found. Otherwise null.
     * @throws ThemeConfigException Wraps exceptions, which may occur in the underlying OpenCms calls.
     */
    public ThemeConfig loadThemeConfig(String p_configId) throws ThemeConfigException;
    
    /**
     * Lists all available theme configurations available on the system.
     * @return The list with all active theme configurations. If there are no active theme configurations, this
     * will return an empty ArrayList.
     * @throws ThemeConfigException Wraps all exceptions, which may occur at the underlying calls.
     */
    public ArrayList listThemeConfigs() throws ThemeConfigException; 
    
    /**
     * Creates a new theme configuration with the given id and returns its object representation.
     * @param p_configId The id of the new theme configuration to be created. If a theme configuration with this name
     * already exists, the name will be modified, by appending a number in brackets. By default 2 will be taken as
     * number. If a theme config with this id also exists, the number will be increased, until a unique id is found.
     * @return The new created theme configurations.
     * @throws ThemeConfigException Wraps all undelyinf exceptions with possibly may occur.
     */
    public ThemeConfig createThemeConfig(String p_configId) throws ThemeConfigException;
    
    /**
     * Stores a newly created theme configtation to the VFS
     * @param p_themeConfig The object representation of the theme configuration to be saved.
     * @throws ThemeConfigException Wraps all underlying exceptions, which may occur during the operation.
     */
    public void createThemeConfig(ThemeConfig p_themeConfig) throws ThemeConfigException;
    
    /**
     * Saves a theme configuration to the VFS. To check, if a theme configuration already exists on the VFS, the
     * <code>resourceId</code> property of the theme configuration object is used. If the property has a value, the 
     * engine will check if a resource with this id exists on VFS. It it exists, it will be overwritten. in all other 
     * cases a new resource
     * willbe created for the obejct on the VFS
     * @param p_config The theme configuration to be saved.
     * @throws ThemeConfigException wraps all underlying exception, which may occur during the operation
     */
    public void saveThemeConfig(ThemeConfig p_config) throws ThemeConfigException;
    
    /**
     * Deletes a theme configuration from the system.
     * @param p_configId The ID / name, which identifies the theme configuration.
     * @throws ThemeConfigException When the deletion fails
     */
    public void deleteThemeConfig(String p_configId) throws ThemeConfigException;
    
    /**
     * Checks if a theme configration with a given id exists on the VFS
     * @param p_configId The id to check against.
     * @return <code>true</code> if such a theme configuration exists, otherwise <code>false</code>
     * @throws ThemeConfigException Wraps any underlying exception, which may occur in this method or its subcalls.
     */
     public boolean existsThemeConfig(String p_configId) throws ThemeConfigException;
}
