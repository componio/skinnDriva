/*
 *  Created on 17. Januar 2005, 09:45
 *  Copyright (C) 2010 Robert Diawara
 *
 *  This file is part of OpenECS.
 *
 *  OpenECS is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  OpenECS is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with OpenECS.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.diawara.commons.etc;


import  java.lang.reflect.Method;
import  java.lang.reflect.InvocationTargetException;

import  java.util.StringTokenizer;

/**
 *
 * @author  robert
 */
public class PropertyGetter {
    
    /**
     * Holds value of property bean.
     */
    private Object bean;
    
    /**
     * Creates a new instance of PropertyGetter
     * @param p_Bean The bean which is to be associated to the property setter
     */
    public PropertyGetter(Object p_Bean) {
        // Abort with runtime exception if bean is null.
        if(p_Bean == null){
            throw new RuntimeException("The parameter \"p_Bean\" in PropertySetter.<init>(java.lang.Object) may not" +
            "be null !");
        }
        this.bean = p_Bean;
    }
    
    /**
     * Getter for property bean.
     * @return Value of property bean.
     */
    public Object getBean() {
        return this.bean;
    }
    
    /**
     * Gets the getter method for a property identified by the property name, which should be provided as value of the
     * parameter 'p_PropertyName'.
     * @param p_PropertyName The name of the property for which the getter is to be retrieved..
     * @return The getter method for the property, if one exists. Otherwise <code>null</code>
     */
    private Method getGetter(Object p_Bean, String p_PropertyName) {
        String methodPrefix   = "get";
        String methodName;
        Method result         = null;
        Method currentMethod;
        Method methodList[];
        Class  paramTypes[];
        Class  currentCls;
        int    loopCount;
        
        // Abort with an exception if parameter p_Bean is null.
        if(p_Bean == null){
            throw new RuntimeException("Parameter \"p_Bean\" in method getGetter(java.lang.String, " +
            "java.lang.Class) may not be null !");
        }
        
        // Abort with an eexception, when propertyName is null.
        if(p_PropertyName == null){
            throw new RuntimeException("Parameter \"p_PropertyName\" in method getGetter(java.lang.String, " +
            "java.lang.Class) may not be null !");
        }
        
        // Create method name.
        methodName = methodPrefix + p_PropertyName.trim().substring(0,1).toUpperCase() + p_PropertyName.trim().substring(1);
        
        // Search setter method in the hole hierarchy.
        for(currentCls = p_Bean.getClass();
        (currentCls != null) && (result == null); currentCls = currentCls.getSuperclass()){
            methodList = currentCls.getMethods();
            for(loopCount = 0; (loopCount < methodList.length) && (result == null); loopCount ++){
                currentMethod = methodList[loopCount];
                paramTypes = currentMethod.getParameterTypes();
                if((currentMethod.getName().equals(methodName)) && (paramTypes.length == 0)){
                    result = currentMethod;
                }
            }
        }
        
        return result;
    }
    
    /**
     * Returns a Class object representing the type of a property. If the property identified by the 
     * parameter p_PropertyName does not exist, the method will be aborted with a runtime exception.
     * @param p_PropertyName The name of the property.
     * @return The class object representing the property type.
     */
    public Class getPropertyType(String p_PropertyName){
    	Method getter = getGetter(bean, p_PropertyName);
    	
    	if(getter == null){
    		throw new RuntimeException("\"" + p_PropertyName + "\": No such property");
    	}
    	return getter.getReturnType();
    }
    
    /**
     * Gets the value of a bean property.
     * @param p_PropertyName The name of the property.
     * @return The value of the property.
     */
    public Object getBeanProperty(String p_PropertyName){
        StringTokenizer tk;
        String          currentToken;
        String          currentPath;
        Object          currentBean;
        Method          currentMethod;
        
        // Abort if p_PropertyName is null.
        if(p_PropertyName == null){
            throw new RuntimeException("Parameter \"p_PropertyName\" in method setBeanProperty(java.lang.String, Object)" +
            " may not be null !");
        }
        
        for(tk = new StringTokenizer(p_PropertyName, "."), currentPath="", currentBean = bean; tk.hasMoreTokens();){
            currentToken = tk.nextToken();
            currentPath = currentPath.equals("") ? currentToken : currentPath + "." + currentToken;
            currentMethod = getGetter(currentBean, currentToken);
            if(currentMethod == null){
                throw new RuntimeException("no getter for property \"" + currentToken + "\" found in \"" +
                bean.getClass().getName() + "\" !");
            }
            try{
                currentBean = currentMethod.invoke(currentBean, new Object[]{});
            }catch(IllegalAccessException | InvocationTargetException e){
                throw new RuntimeException("Could not get Property \"" + p_PropertyName + "\" !", e);
            }
        }
        return currentBean;
    }
    
}
