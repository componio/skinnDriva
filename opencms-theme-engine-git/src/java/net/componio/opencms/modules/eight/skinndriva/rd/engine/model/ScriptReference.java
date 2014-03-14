/*
 *  Document : ScriptReference.java
 *  Created on  28.05.2011, 22:13:48
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

import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigInitializationException;

import  java.util.List;
import  java.util.ArrayList;
import  java.util.HashMap;
import  java.util.Iterator;

/**
 * Model representation of a reference to a script, which has to be included into all
 * pages, which a theme is applied to. Additionaly to the URI of the script, this contains
 * also the information if the script has only to be included into the online project, the
 * offline project or into both.
 * @author Robert Diawara
 */
public class ScriptReference extends ThemeEngineModel{
    /** Determines, that a script will be available in none of the projects. */
    public static final int VISIBILITY_NONE    = 0;
    
    /** Determines, that a script will be included and available in only offline projects. */
    public static final int VISIBILITY_OFFLINE = 1;

    /** Determines, that a script will be included and available in only online projects. */
    public static final int VISIBILITY_ONLINE  = 2;

    /** Determines, that a script will be included and available in all projects. */
    public static final int VISIBILITY_ALL     = 3;

    /** Holds the value of the property 'scriptURI'. */
    private String scriptURI;

    /** Holds the value of the property 'scriptVisibility'. */
    private int scriptVisibility;

    /** Holds the name of the group, which the script reference belongs tp. */
    private String group;

    /**
     * Default constructor. Creates a new instance and initializes it with default values.
     */
    public ScriptReference(){
        this.scriptVisibility = ScriptReference.VISIBILITY_ALL;
        this.scriptURI = null;
        this.parameters = new HashMap<String, String>();
        this.group = null;
    }

    /**
     * Get the value of scriptURI
     * @return the value of scriptURI
     */
    public String getScriptURI() {
        return scriptURI;
    }

    /**
     * Gets the full URI of the script with the appended query string
     * @return The full URI of the script with the query string
     */
    public String getFullScriptURI(){
        StringBuffer resultSb; 
        String       qryStr    = getQueryString();
        
        resultSb = new StringBuffer(getScriptURI());
        if(qryStr != null){
            resultSb.append("?");
            resultSb.append(qryStr);
        }
        return resultSb.toString();
    }
    
    /**
     * Set the value of scriptURI
     * @param scriptURI new value of scriptURI
     */
    public void setScriptURI(String scriptURI) {
        checkStatus(this.scriptURI, scriptURI);
        this.scriptURI = scriptURI;
    }


    /**
     * Get the value of scriptVisibility
     * @return the value of scriptVisibility
     */
    public int getScriptVisibility() {
        return scriptVisibility;
    }

    /**
     * Set the value of scriptVisibility
     * @param scriptVisibility new value of scriptVisibility
     * @throws ThemeConfigInitializationException When the new value is invalid.
     */
    public void setScriptVisibility(int scriptVisibility) throws ThemeConfigInitializationException{
        if((scriptVisibility < ScriptReference.VISIBILITY_NONE) || (scriptVisibility > ScriptReference.VISIBILITY_ALL)){
            throw new ThemeConfigInitializationException("scriptVisibility has to be a value between 0 and 3");
        }
        checkStatus(this.scriptVisibility, scriptVisibility);
        this.scriptVisibility = scriptVisibility;
    }

    /**
     * Indicates whether some other script reference is "equal to" this one.<br/>
     * The equals method implements an equivalence relation on non-null object references:
     * <ul>
     *     <li>
     *         t is reflexive: for any non-null reference value x, x.equals(x) should return true.
     *     </li>
     *     <li>
     *         It is symmetric: for any non-null reference values x and y, x.equals(y) should
     *         return true if and only if y.equals(x) returns true.
     *     </li>
     *     <li>
     *         It is transitive: for any non-null reference values x, y, and z, if x.equals(y)
     *         returns true and y.equals(z) returns true, then x.equals(z) should return true.
     *     </li>
     *     <li>
     *         It is consistent: for any non-null reference values x and y, multiple invocations of
     *         x.equals(y) consistently return true or consistently return false, provided no information
     *         used in equals comparisons on the objects is modified.
     *     </li>
     *     <li>
     *         For any non-null reference value x, x.equals(null) should return false.
     *     </li>
     * </ul>
     * The equals method for class Object implements the most discriminating possible equivalence relation on
     * objects; that is, for any non-null reference values x and y, this method returns true if and only if x and
     * y refer to the same object (x == y has the value true).<br/>
     * Note that it is generally necessary to override the hashCode method whenever this method is overridden,
     * so as to maintain the general contract for the hashCode method, which states that equal objects must have 
     * equal hash codes.
     * @param obj the reference object with which to compare.
     * @return <code>true</code> if this object is the same as the obj argument; <code>false</code> otherwise.
     * @see #hashCode()
    */
    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(Object obj) {
        boolean         result          = false;
        ScriptReference other           = obj != null ? (ScriptReference)obj : null;
        String          otherScriptURI;
        String          ownScriptURI;

        if(other != null){
            otherScriptURI = other.getScriptURI();
            ownScriptURI = getScriptURI();
            if((otherScriptURI == null) && (ownScriptURI == null)){
                result = other.getScriptVisibility() == getScriptVisibility();
            }else if((otherScriptURI != null) && (ownScriptURI != null)){
                result = ((otherScriptURI.equals(ownScriptURI)) && (other.getScriptVisibility() == getScriptVisibility()));
            }
        }
        return result;
    }

    /**
     * Returns a hash code value for the object. This method is supported for the benefit of hashtables such as those
     * provided by java.util.Hashtable.<br/>
     * The general contract of hashCode is:
     * <ul>
     *     <li>
     *         Whenever it is invoked on the same object more than once during an execution of a Java application,
     *         the hashCode method must consistently return the same integer, provided no information used in equals
     *         comparisons on the object is modified. This integer need not remain consistent from one execution of
     *         an application to another execution of the same application.
     *     </li>
     *     <li>
     *         If two objects are equal according to the equals(Object) method, then calling the hashCode method on
     *         each of the two objects must produce the same integer result.
     *     </li>
     *     <li>
     *         It is not required that if two objects are unequal according to the equals(java.lang.Object) method,
     *         then calling the hashCode method on each of the two objects must produce distinct integer results.
     *         However, the programmer should be aware that producing distinct integer results for unequal objects
     *         may improve the performance of hashtables.
     *     </li>
     * </ul>
     * As much as is reasonably practical, the hashCode method defined by class Object does return distinct integers
     * for distinct objects. (This is typically implemented by converting the internal address of the object into an
     * integer, but this implementation technique is not required by the JavaTM programming language.)
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.scriptURI != null ? this.scriptURI.hashCode() : 0);
        hash = 17 * hash + this.scriptVisibility;
        return hash;
    }


    private HashMap<String, String> parameters;

    /**
     * Get the list of URL parameters assigned to the script reference. Each url parameter is a key value pair which is
     * spelled the same way as a ke value pair in a resource bundle.
     * @return the value of parameters
     */
    public List<String> getParameters() {
        List<String>     result = null;
        String           currentKey;
        String           currentValue;
        Iterator<String> keysIt;
        StringBuffer     sb;
        
        if((this.parameters != null) && (this.parameters.size() > 0)){
            result = new ArrayList<String>();
            for(keysIt = this.parameters.keySet().iterator(); keysIt.hasNext();){
                currentKey = keysIt.next().trim();
                currentValue = this.parameters.get(currentKey);
                currentValue = currentValue != null ? currentValue.trim() : null;
                
                sb = new StringBuffer(currentKey);
                sb.append("=");
                sb.append(currentValue != null ? currentValue : "");
                result.add(sb.toString());
            }
        }
        
        return result;
    }

    /**
     * Gets the value of a parameters from the internal list of URL parameters.
     * @param p_paramName The name of the parameter
     * @return The value of the parameter
     */
    public String getParameter(String p_paramName){
        return ((p_paramName != null) && (this.parameters != null)) ? this.parameters.get(p_paramName) : null;
    }
    
    /**
     * Set the value of parameters
     * @param parameters new value of parameters
     */
    public void setParameters(List<String> parameters) {
        Iterator<String> paramsIt;
       
        if(parameters != null){
            if(this.parameters != null){
               this.parameters.clear();
            }
            for(paramsIt = parameters.iterator(); paramsIt.hasNext();){
                addParameter(paramsIt.next());
            }
        }
    }

    /**
     * Adds a new URL parameter to the internal list of URL parameters of the
     * script reference.
     * @param newParameter Rhew new URL parameter to be added.
     */
    public void addParameter(String newParameter){
        String           currentKey;
        String           currentValue;
        String[]         currentPair;
        int              loopCount;
        StringBuffer     sb            = null;
        
        // Proceed only, when a new Parameter is passed
        if((newParameter != null) && (newParameter.trim().length() > 0)){
            // Create the hashmap with parameters, when not done yet
            if(this.parameters == null){
                this.parameters = new HashMap<String, String>();
            }
            
            // Analyze the string and add the parameter to the hashmap
            currentPair = newParameter.split("=");
            
            if(currentPair.length > 0){
                currentKey = currentPair[0];
                for(loopCount = 1; loopCount < currentPair.length; loopCount ++){
                    if(loopCount == 1){
                        sb = new StringBuffer();
                    }else{
                        sb.append("=");
                    }
                    sb.append(currentPair[loopCount]);
                }
                currentValue = sb != null ? sb.toString() : null;
                this.parameters.put(currentKey, currentValue);
            }
        }
    }

    /**
     * Gets the parameter string to be appended to the script URI, when parameters are present.
     * @return The parameter string, when parameters are defined. Otherwise null.
     */
    public String getQueryString(){
        List<String> params   = getParameters();
        String       result   = null;
        Iterator     paramsIt;
        StringBuffer sb       = null;
        
        if(params != null){
            paramsIt = params.iterator();
            if(paramsIt.hasNext()){
                sb = new StringBuffer();
                sb.append(paramsIt.next());
            }
            while(paramsIt.hasNext()){
                sb.append("&");
                sb.append(paramsIt.next());
            }
            result = sb != null ? sb.toString() : null;
        }
        return result;
    }

    /**
     * Get the value of group
     * @return the value of group
     */
    public String getGroup() {
        return (group != null) && (group.trim().length() > 0) ? group : null;
    }

    /**
     * Set the value of group
     * @param group new value of group
     */
    public void setGroup(String group) {
        this.group = group;
    }
}
