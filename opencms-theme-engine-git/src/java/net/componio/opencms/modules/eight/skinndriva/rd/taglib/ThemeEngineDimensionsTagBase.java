/*
 *  Document : ThemeEngineDimensionsTagBase.java
 *  Created on Sa, Jan 21 2012, 20:36:39
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

/**
 *
 * @author Robert Diawara
 */
public class ThemeEngineDimensionsTagBase extends ThemeEngineThemeTagBase{
    /** Holds the value of the property &quot;formatted&quot;. */
    private boolean formatted;

    /**
     * Default constructor
     */
    public ThemeEngineDimensionsTagBase(){
        formatted = false;
    }
    
    /**
     * Get the value of formatted
     * @return the value of formatted
     */
    public boolean isFormatted() {
        return formatted;
    }

    /**
     * Set the value of formatted
     * @param formatted new value of formatted
     */
    public void setFormatted(boolean formatted) {
        this.formatted = formatted;
    }

}
