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
 * This class is a dynamic property setter used tho set properties, which are only known at runtime. This will be done
 * by initializing an property setter usin the contructor. The contructor requires an instance of the bean, from which
 * the properties are to be got.
 * @author  robert
 */
public class PropertySetter {
    
    /**
     * Holds value of property bean.
     */
    private Object bean;
    
    /** Creates a new instance of PropertySetter 
     * @param p_Bean The bean which is to be associated to the property setter
     */
    public PropertySetter(Object p_Bean) {
        // Abort with runtime exception if bean is null.
        if(p_Bean == null){
            throw new RuntimeException("The parameter \"p_Bean\" in PropertySetter.<init>(java.lang.Object) may not" +
            "be null !");
        }
        this.bean = p_Bean;
    }
    
    /** Gets the setter method for a property identified by the property name, which should be provided as value of the
     * parameter 'p_PropertyName'.
     * @param p_PropertyName The name of the property for which the setter is to be retrieved.
     * @param p_PropertyType The type of the property.
     * @return The setter method for the property, if one exists. Otherwise <code>null</code>
     */
    private Method getSetter(Object p_Bean, String p_PropertyName, Class p_PropertyType){
        String methodPrefix   = "set";
        String methodName;
        Method result         = null;
        Method currentMethod;
        Method methodList[];
        Class  paramTypes[];
        Class  currentCls;
        int    loopCount;
        
        // Abort with an eexception, when propertyName is null
        if(p_PropertyName == null){
            throw new RuntimeException("Parameter \"p_PropertyName\" in method getSetter(java.lang.String, " +
            "java.lang.Class) may not be null !");
        }
        
        // Abort with an eexception, when propertyType is null
        if(p_PropertyType == null){
            throw new RuntimeException("Parameter \"p_PropertyType\" in method getSetter(java.lang.String, " +
            "java.lang.Class) may not be null !");
        }
        
        // Create method name.
        methodName = methodPrefix + p_PropertyName.trim().substring(0,1).toUpperCase() + p_PropertyName.trim().substring(1);
        
        // Search setter method in the hole hierarchy.
        for(currentCls = p_Bean.getClass(); 
        (currentCls != null) && (result == null); currentCls = currentCls.getSuperclass()){
            methodList = currentCls.getDeclaredMethods();
            //methodList = currentCls.getMethods();
            for(loopCount = 0; (loopCount < methodList.length) && (result == null); loopCount ++){
                currentMethod = methodList[loopCount];
                paramTypes = currentMethod.getParameterTypes();
                if((currentMethod.getName().equals(methodName)) && (paramTypes.length == 1)){
                    if(paramTypes[0].getName().equals(p_PropertyType.getName())){
                        result = currentMethod;
                    }
                }
            }
        }
        
        return result;
    }
    
    /** Gets the getter method for a property identified by the property name, which should be provided as value of the
     * parameter 'p_PropertyName'.
     * @param p_PropertyName The name of the property for which the getter is to be retrieved..
     * @return The getter method for the property, if one exists. Otherwise <code>null</code>
     */
    private Method getGetter(Object p_Bean, String p_PropertyName){
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
    
    /** Sets the value of a bean property.
     * @param p_PropertyName The name of the property.
     * @param p_PropertyValue The new value for the property.
     */
    public void setBeanProperty(String p_PropertyName, Object p_PropertyValue){
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
        
        // Abort if p_PropertyName is null.
        if(p_PropertyValue == null){
            throw new RuntimeException("Parameter \"p_PropertyValue\" in method setBeanProperty(java.lang.String, Object)" +
            " may not be null !");
        }
        
        for(tk = new StringTokenizer(p_PropertyName, "."), currentPath="", currentBean = bean; tk.hasMoreTokens();){
            currentToken = tk.nextToken();
            currentPath = currentPath.equals("") ? currentToken : currentPath + "." + currentToken;
            if(tk.hasMoreTokens()){
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
            }else{
                currentMethod = getSetter(currentBean, currentToken, p_PropertyValue.getClass());
                if(currentMethod == null){
                    if(p_PropertyValue.getClass().getName().equals(Integer.class.getName())){
                        currentMethod = getSetter(currentBean, currentToken, Integer.TYPE);
                    }else if(p_PropertyValue.getClass().getName().equals(Short.class.getName())){
                        currentMethod = getSetter(currentBean, currentToken, Short.TYPE);
                    }else if(p_PropertyValue.getClass().getName().equals(Long.class.getName())){
                        currentMethod = getSetter(currentBean, currentToken, Long.TYPE);
                    }else if(p_PropertyValue.getClass().getName().equals(Double.class.getName())){
                        currentMethod = getSetter(currentBean, currentToken, Double.TYPE);
                    }else if(p_PropertyValue.getClass().getName().equals(Float.class.getName())){
                        currentMethod = getSetter(currentBean, currentToken, Float.TYPE);
                    }else if(p_PropertyValue.getClass().getName().equals(Boolean.class.getName())){
                        currentMethod = getSetter(currentBean, currentToken, Boolean.TYPE);
                    }else if(p_PropertyValue.getClass().getName().equals(Character.class.getName())){
                        currentMethod = getSetter(currentBean, currentToken, Character.TYPE);
                    }else if(p_PropertyValue.getClass().getName().equals(Byte.class.getName())){
                        currentMethod = getSetter(currentBean, currentToken, Byte.TYPE);
                    }
                }
                if(currentMethod == null){
                    throw new RuntimeException("no setter for property \"" + currentToken + "\" found in \"" +
                    bean.getClass().getName() + "\" !");
                }
                try{
                    currentMethod.invoke(currentBean, new Object[]{p_PropertyValue});
                }catch(IllegalAccessException | InvocationTargetException e){
                    throw new RuntimeException("Could not get Property \"" + p_PropertyName + "\" !", e);
                }
            }
        }
    }
    
    /**
     * Getter for property bean.
     * @return Value of property bean.
     */
    public Object getBean() {
        return this.bean;
    }
}
