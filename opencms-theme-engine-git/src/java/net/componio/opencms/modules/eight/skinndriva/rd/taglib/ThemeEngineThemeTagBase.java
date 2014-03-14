/*
 *  Document : ThemeEngineThemeTagBase.java
 *  Created on Do, Jan 19 2012, 12:47:34
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
package net.componio.opencms.modules.eight.skinndriva.rd.taglib;

import  com.diawara.commons.taglib.CommonTagBase;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeConfig;

/**
 * The base class for all there related tags
 * @author Robert Diawara
 */
public class ThemeEngineThemeTagBase extends CommonTagBase{
    /** Holds the the config to get the styles from. */
    private ThemeConfig theme;

    /**
     * 
     */
    public ThemeEngineThemeTagBase(){
        theme = null;
    }
    
    /**
     * Get the value of theme
     * @return the value of theme
     */
    public ThemeConfig getTheme() {
        return theme;
    }

    /**
     * Set the value of theme
     * @param theme new value of theme
     */
    public void setTheme(ThemeConfig theme) {
        if(theme == null){
            throw new RuntimeException("Error in class \"" + getClass().getName() + "\" ! Could not set the theme "
                    + "because the attribute is null or an empty string. Therefore the setter has been aborted. Please "
                    + "provide a valid theme!");
        }
        this.theme = theme;
    }

}
