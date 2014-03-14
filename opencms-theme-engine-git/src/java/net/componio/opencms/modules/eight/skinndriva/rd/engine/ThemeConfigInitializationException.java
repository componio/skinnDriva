/*
 *  Document : ThemeConfigInitializationException.java
 *  Created on  28.05.2011, 23:10:52
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


package net.componio.opencms.modules.eight.skinndriva.rd.engine;

/**
 *
 * @author robert
 */
public class ThemeConfigInitializationException extends ThemeConfigException {

    /**
     * Creates a new instance of <code>ThemeConfigInitializationException</code> without detail message.
     */
    public ThemeConfigInitializationException() {
    }


    /**
     * Constructs an instance of <code>ThemeConfigInitializationException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ThemeConfigInitializationException(String msg) {
        super(msg);
    }
    
    /**
     * Constructs a new ThemeConfigInitializationException with the specified cause and a detail message of 
     * (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause). 
     * This constructor is useful for exceptions that are little more than wrappers for other throwables (for example, 
     * PrivilegedActionException).  
     * @param p_cause the cause (which is saved for later retrieval by the Throwable.getCause() method). (A null value 
     * is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public ThemeConfigInitializationException(Exception p_cause){
        super(p_cause);
    }
}
