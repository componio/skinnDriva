/*
 *  Document : ThemeEngineModel.java
 *  Created on So, Sep 11 2011, 18:58:31
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

import java.util.List;
import java.util.Iterator;

/**
 * The base class for all theme engine model classes. Implements the changement of the status, when a property of
 * a model class is changed. This allows to automatically decide if an object is to be saved to VFS or not, when the
 * engine processes a list of objects.
 * @author Robert Diawara
 */
public class ThemeEngineModel {
    /** Constant defining the "unchanged" status of a bean. derived from this class. */
    public static final int STATUS_NEW       = 0;
    /** Constant defining the "unchanged" status of a bean. derived from this class. */
    public static final int STATUS_UNCHANGED = 1;
    /** Constant defining the "changed" status of a bean. derived from this class. */
    public static final int STATUS_CHANGED   = 2;
    
    /** Holds the value of the property status. */
    private int status;
    
    /**
     * Default constructor instatiating a theme engine model class. 
     */
    public ThemeEngineModel(){
        this.status = ThemeEngineModel.STATUS_UNCHANGED;
    }
    
    /**
     * Sets the status to unchanged.
     */
    public void setNew(){
        this.status = ThemeEngineModel.STATUS_NEW;
    }
    
    /**
     * Sets the status to unchanged.
     */
    public void setUnchanged(){
        this.status = ThemeEngineModel.STATUS_UNCHANGED;
    }
    
    /**
     * Sets the status to changed.
     */
    public void setChanged(){
        this.status = ThemeEngineModel.STATUS_CHANGED;
    }
    
    /**
     * Gets the status of the bean. This status can only be changed or unchanged.
     * @return The status.
     */
    public int getStatus(){
        return this.status;
    }
    
    /**
     * Checks if a property of the bean has been chaged or not.
     * @return True, if a propety of the bean has bean changed since the last save to or load from VFS. 
     * False otherwise.
     */
    public boolean isChanged(){
        return getStatus() == ThemeEngineModel.STATUS_CHANGED;
    }
    
    /**
     * Checks if a bean is newly created.
     * @return True, The bean has not been modified since its creation. Otherwise false 
     * False otherwise.
     */
    public boolean isNew(){
        return getStatus() == ThemeEngineModel.STATUS_NEW;
    }
    
    /**
     * Can be called to set the status of the bean, when a property is changed. by comparing the current value of the 
     * property and the new value of the property. If they are different, the status will be set to changed, regardless 
     * if one of them or both are null or not. Otherwise the status will stay as it is.
     * @param currentValue The current value of the property.
     * @param newValue The new value of the property to be set.
     */
    public void checkStatus(Object currentValue, Object newValue){
        int tmp;
        
        if(currentValue != null){
            if(newValue != null){
                if((currentValue instanceof List) && (newValue instanceof List)){
                    tmp = listsEqual((List)currentValue, (List)newValue) ? ThemeEngineModel.STATUS_UNCHANGED : ThemeEngineModel.STATUS_CHANGED;
                }else{
                    tmp = currentValue.equals(newValue) ? ThemeEngineModel.STATUS_UNCHANGED : ThemeEngineModel.STATUS_CHANGED;
                }
            }else{
                tmp = ThemeEngineModel.STATUS_CHANGED;
            }
        }else{
            if(newValue != null){
                tmp = ThemeEngineModel.STATUS_CHANGED;
            }else{
                tmp = ThemeEngineModel.STATUS_UNCHANGED;
            }
        }
        
        if((status != ThemeEngineModel.STATUS_CHANGED) && (tmp == ThemeEngineModel.STATUS_CHANGED)){
            status = tmp;
        }
    }
    

    /**
     * Compares two lists and returns true if they are equal. Two lists are equal, when they have the same length and
     * the elements at the respective position are equal pairwise.
     * @param list1 The first list
     * @param list2 The second list
     * @return True if the lists are equal. Otherwise false.
     */
    private boolean listsEqual(List list1, List list2){
        boolean  result  = false;
        Iterator list1It;
        Iterator list2It;
        Object   obj1;
        Object   obj2;
        
        if((list1 != null) && (list2 != null) && (list1.size() == list2.size())){
            for(list1It = list1.iterator(), list2It = list2.iterator(), result = true; (list1.iterator().hasNext()) && (list2It.hasNext()) && (result);){
                obj1 = list1It.next();
                obj2 = list2It.next();
                result = obj1.equals(obj2);
            }
        }
        return result;
    }
    
    /**
     * Can be called to set the status of the bean, when a property is changed. by comparing the current value of the 
     * property and the new value of the property. If they are different, the status will be set to changed. Otherwise 
     * the status will stay as it is.
     * @param currentValue The current value of the property.
     * @param newValue The new value of the property to be set.
     */
    public void checkStatus(int currentValue, int newValue){
        int tmp;
        
        tmp = currentValue == newValue ? ThemeEngineModel.STATUS_UNCHANGED : ThemeEngineModel.STATUS_CHANGED;
       
        if(status != ThemeEngineModel.STATUS_CHANGED){
            status = tmp;
        }
    }

    /**
     * Formats an integer value, by converting it into a string which also contains the
     * unit associated to the value.
     * @param p_unit The unit to associate to the value.
     * @param p_value The integer value to be formatted.
     * @return The integer value converted into a formatted string
     */
    protected String formatValue(int p_value, String p_unit){
        StringBuffer sb;
        
        sb = new StringBuffer(String.valueOf(p_value));
        sb.append(p_unit);
        return sb.toString();
    }
}
