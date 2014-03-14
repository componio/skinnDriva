/*
 *  Document : ThemeConfigStub.java
 *  Created on  13.06.2011, 18:01:47
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
import  java.util.Date;

/**
 * Model representation for a theme config stub to be listed in overviews.
 * @author Robert Diawara
 */
public class ThemeConfigStub extends ThemeEngineModel{
    private String name;
    private Date dateCreated;

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
     * Get the value of dateCreated
     *
     * @return the value of dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Set the value of dateCreated
     *
     * @param dateCreated new value of dateCreated
     */
    public void setDateCreated(Date dateCreated) {
        checkStatus(this.dateCreated, dateCreated);
        this.dateCreated = dateCreated;
    }
}
