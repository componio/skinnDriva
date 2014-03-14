/*
 *  Document : ThemeFactory.java
 *  Created on Sa, Aug 06 2011, 20:08:09
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

import  java.util.ResourceBundle;
import  java.lang.reflect.Constructor;
import  java.lang.reflect.InvocationTargetException;

import  javax.servlet.jsp.PageContext;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;

import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigException;

/**
 * Provides factory methods to create controller classes for theme manipulation at the backend (VFS).
 * @author Robert Diawara
 */
public class ThemeFactory {
    /**
     * Initializes a implementation of the interface {@link I_ThemeEngineConfigController} which provides methods
     * to manipulate the theme engine at the VFS.
     * @param p_pageContext The JSP page context, which my be needed to initialize the controller class.
     * @param p_request The HTTP servlet request. May be necessary to initialize the controller class.
     * @param p_response The HTTP servlet response. May be necessary to initialize the controller class.
     * @return A fully initialized and ready to use controller object which implements {@link I_ThemeEngineConfigController}
     * and provides methods for the manipulation of the theme engine at the VFS.
     * @throws ThemeConfigException To signal, that something went wrong, while trying to initialize the controller object.
     */
    public static I_ThemeConfigController getConfigController(PageContext p_pageContext, HttpServletRequest p_request, HttpServletResponse p_response) 
            throws ThemeConfigException{

        I_ThemeConfigController result = null;
        String                  controllerClassName;
        Class                   controllerClass;
        Constructor             controllerConstructor;
        Class[]                 initTypes;
        Object[]                initArgs;
        ResourceBundle          res                    = ResourceBundle.getBundle("net.componio.opencms.modules.eight.skinndriva.rd.config");
        
        try{
            initTypes = new Class[]{PageContext.class, HttpServletRequest.class, HttpServletResponse.class};
            initArgs = new Object[]{p_pageContext, p_request, p_response};
            
            controllerClassName = res.getString("theme.controller.class");
            controllerClass = Class.forName(controllerClassName);
            controllerConstructor = controllerClass.getConstructor(initTypes);
            result = (I_ThemeConfigController)controllerConstructor.newInstance(initArgs);
        }catch(ClassNotFoundException clsEx){
            throw new ThemeConfigException(clsEx);
        }catch(InstantiationException instEx){
            throw new ThemeConfigException(instEx);
        }catch(IllegalAccessException accEx){
            throw new ThemeConfigException(accEx);
        }catch(NoSuchMethodException methEx){
            throw new ThemeConfigException(methEx);
        }catch(InvocationTargetException tgtEx){
            throw new ThemeConfigException(tgtEx);
        }
        return result;
    }

    /**
     * Initializes a controller object implementing the interface {@link I_ThemeConfigController}, wich provides methods
     * for the manipulation of theme configurations at the VFS.
     * @param p_pageContext The JSP page context, which my be needed to initialize the controller class.
     * @param p_request The HTTP servlet request. May be necessary to initialize the controller class.
     * @param p_response The HTTP servlet response. May be necessary to initialize the controller class.
     * @return A fully initialized and ready to use controller object, which implements {@link I_ThemeConfigController}
     * and provides access to the theme configurations at the backend.
     * @throws ThemeConfigException To signal, that something went wrong, while trying to initialize the controller object. 
     */
    public static I_ThemeEngineConfigController getEngineConfigController(PageContext p_pageContext, HttpServletRequest p_request, HttpServletResponse p_response) 
            throws ThemeConfigException{
        
        I_ThemeEngineConfigController result = null;
        String                        controllerClassName;
        Class                         controllerClass;
        Constructor                   controllerConstructor;
        Class[]                       initTypes;
        Object[]                      initArgs;
        ResourceBundle                res                    = ResourceBundle.getBundle("net.componio.opencms.modules.eight.skinndriva.rd.config");
        
        try{
            initTypes = new Class[]{PageContext.class, HttpServletRequest.class, HttpServletResponse.class};
            initArgs = new Object[]{p_pageContext, p_request, p_response};
            
            controllerClassName = res.getString("engine.controller.class");
            controllerClass = Class.forName(controllerClassName);
            controllerConstructor = controllerClass.getConstructor(initTypes);
            result = (I_ThemeEngineConfigController)controllerConstructor.newInstance(initArgs);
        }catch(ClassNotFoundException clsEx){
            throw new ThemeConfigException(clsEx);
        }catch(InstantiationException instEx){
            throw new ThemeConfigException(instEx);
        }catch(IllegalAccessException accEx){
            throw new ThemeConfigException(accEx);
        }catch(NoSuchMethodException methEx){
            throw new ThemeConfigException(methEx);
        }catch(InvocationTargetException tgtEx){
            throw new ThemeConfigException(tgtEx);
        }
        return result;
     }
}
