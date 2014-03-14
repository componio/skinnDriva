/*
 *  Document : I_ThemeEngineConfigController.java
 *  Created on Do, Sep 08 2011, 01:02:34
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

import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeEngineConfig;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigException;

/**
 * The interface for controller classes, which offer backend access for the theme engine. The methods to load theme engine
 * configurations from backend and save them to backend, are declared here. Each class providing these backend functionalities
 * has to implement this interface.
 * @author robert
 */
public interface I_ThemeEngineConfigController {
    /**
     * Classes implemeting this interface should implement this method to load the theme engine configuration from backend
     * Theme engine configurations are represented by the class {@link net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeEngineConfig 
     * ThemeEngineConfig} or a subclass.
     * @return The theme engine configuration loaded from backend.
     * @throws ThemeConfigException To signal that something went wrong, while trying to load the theme engine configuration
     * from backend.
     */
    public ThemeEngineConfig loadThemeEngine() throws ThemeConfigException;
    
    /**
     * Classes implemeting this interface should implement this method to save the theme engine configuration to the backend.
     * Theme engine configurations are represented by the class {@link net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeEngineConfig 
     * ThemeEngineConfig} or a subclass.
     * @param p_engine The theme engine configuration to be saved to backend.
     * @throws ThemeConfigException To signal, that something went wrong wile trying to save the theme engine configuration
     * to the backend.
     */
    public void saveThemeEngine(ThemeEngineConfig p_engine) throws ThemeConfigException;
}
