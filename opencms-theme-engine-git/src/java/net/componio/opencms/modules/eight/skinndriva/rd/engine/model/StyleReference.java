/*
 *  Document : StyleReference.java
 *  Created on Do, Jan 26 2012, 20:54:42
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
package net.componio.opencms.modules.eight.skinndriva.rd.engine.model;

import  java.util.List;
import  java.util.ArrayList;

/**
 *
 * @author Robert Diawara
 */
public class StyleReference {
    /** Holds the initial URI of the Style. */
    private String initialURI;
    /** Holds the URI of the Style. */
    private String URI;
    /** Holds the group, which the script is assigned to. */
    private String group;
    /** Tue if the URI was initially set. Otherwise false. */
    private boolean uriInitialized;
    /** Holds a list of regular expressions, matching the user agents
     * which this style reference has to be valid for.*/
    private List<String> userAgents;
    /** Holds the media for the style sheet. */
    private String media;
    /** Determines, if the style sheet has to be used for large screens. */
    private boolean usedForLargeScreens;
    /** Determines, if the style sheet has to be used for medium screens. */
    private boolean usedForMediumScreens;
    /** Determines, if the style sheet has to be used for small screens. */
    private boolean usedForSmallScreens;



    
    /**
     * Default constructor
     */
    public StyleReference(){
        initialURI = null;
        uriInitialized = false;
        URI = null;
        group = null;
        userAgents = new ArrayList<String>();
        usedForLargeScreens = true;
        usedForMediumScreens = true;
        usedForSmallScreens = true;
    }

    /**
     * Get the value of initialURI
     * @return the value of initialURI
     */
    public String getinitialURI() {
        return ((initialURI != null) && (initialURI.trim().length() > 0)) ? initialURI : null;
    }

    /**
     * Get the value of URI
     * @return the value of URI
     */
    public String getURI() {
        return ((URI != null) && (URI.trim().length() > 0)) ? URI : null;
    }

    /**
     * Set the value of URI
     * @param URI new value of URI
     */
    public void setURI(String URI) {
        this.URI = URI;
        if(!this.uriInitialized){
            this.initialURI = URI;
            this.uriInitialized = true;
        }
    }

    /**
     * Get the value of group
     * @return the value of group
     */
    public String getGroup() {
        return ((group != null) && (group.trim().length() > 0)) ? group : null;
    }

    /**
     * Set the value of group
     * @param group new value of group
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     *  Indicates whether some other style reference is "equal to" this one.
     * The equals method implements an equivalence relation on non-null object references:
     * <ul>
     *     <li>
     *         It is reflexive: for any non-null reference value x, x.equals(x) should return true.
     *     </li>
     *     <li>
     *         It is symmetric: for any non-null reference values x and y, x.equals(y) should return true if and 
     *         only if y.equals(x) returns true.
     *     </li>
     *     <li>
     *         It is transitive: for any non-null reference values x, y, and z, if x.equals(y) returns true 
     *         and y.equals(z) returns true, then x.equals(z) should return true.
     *     </li>
     *     <li>
     *         It is consistent: for any non-null reference values x and y, multiple invocations of x.equals(y) 
     *         consistently return true or consistently return false, provided no information used in equals comparisons 
     *         on the objects is modified.
     *     </li>
     *     <li>
     *         For any non-null reference value x, x.equals(null) should return false. 
     *     </li>
     * </ul>
     * The equals method for class Object implements the most discriminating possible equivalence relation on objects; 
     * that is, for any non-null reference values x and y, this method returns true if and only if x and y refer to the 
     * same object (x == y has the value true).
     * 
     * Note that it is generally necessary to override the hashCode method whenever this method is overridden, so as to 
     * maintain the general contract for the hashCode method, which states that equal objects must have equal hash codes. 
     * @param p_other the reference style reference with which to compare with. 
     * @return true if this style reference is the same as the p_other argument; false otherwise.
     */
    @Override
    public boolean equals(Object p_other){
        boolean        result       = false;
        StyleReference other;
        
        if(p_other != null){
            other  = (StyleReference)p_other;
            
            // Compare URIs
            if(URI != null){
                if(other.getURI() != null){
                    result = other.getURI().equals(URI);
                }else{
                    result = false;
                }
            }else{
                result = other.getURI() == null;
            }
            
            // Compare groups
            if(result){
                if(group != null){
                    if(other.getGroup() != null){
                        result = other.getGroup().equals(group);
                    }else{
                        result = false;
                    }
                }else{
                    result = other.getGroup() == null;
                }
            }
            
            // Compare media
            if(result){
                if(media != null){
                    if(other.getMedia() != null){
                        result = other.getMedia().equals(media);
                    }else{
                        result = false;
                    }
                }else{
                    result = other.getMedia() == null;
                }
            }
            
            // Compare user agents
            if(result){
                if(userAgents != null){
                    if(other.getUserAgents() != null){
                        result = other.getUserAgents().equals(userAgents);
                    }else{
                        result = false;
                    }
                }else{
                    result = other.getUserAgents() == null;
                }
            }
            
        }
        return result;
    }

    /**
     * Returns a hash code value for the object. This method is supported for the benefit of hashtables such as those 
     * provided by java.util.Hashtable.
     * The general contract of <code>hashCode</code> is:
     * <ul>
     *     <li>
     *         Whenever it is invoked on the same object more than once during an execution of a Java application, the 
     *         hashCode method must consistently return the same integer, provided no information used in equals 
     *         comparisons on the object is modified. This integer need not remain consistent from one execution of 
     *         an application to another execution of the same application.
     *     </li>
     *     <li>
     *         If two objects are equal according to the equals(Object) method, then calling the hashCode method on 
     *         each of the two objects must produce the same integer result.
     *     </li>
     *     <li>
     *         It is not required that if two objects are unequal according to the <code>equals(java.lang.Object)</code> 
     *         method, then calling the hashCode method on each of the two objects must produce distinct integer results. 
     *         However, the programmer should be aware that producing distinct integer results for unequal objects may 
     *         improve the performance of hashtables. 
     *     </li>
     * </ul>
     * As much as is reasonably practical, the hashCode method defined by class Object does return distinct integers for 
     * distinct objects. (This is typically implemented by converting the internal address of the object into an integer, 
     * but this implementation technique is not required by the JavaTM programming language.)      
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.URI != null ? this.URI.hashCode() : 0);
        hash = 59 * hash + (this.group != null ? this.group.hashCode() : 0);
        return hash;
    }



    /**
     * Get the value of media
     * @return the value of media
     */
    public String getMedia() {
        return media;
    }

    /**
     * Set the value of media
     * @param media new value of media
     */
    public void setMedia(String media) {
        this.media = media;
    }
    
    /**
     * Get the value of usedForLargeScreens
     * @return the value of usedForLargeScreens
     */
    public boolean isUsedForLargeScreens() {
        return usedForLargeScreens;
    }

    /**
     * Set the value of usedForLargeScreens
     * @param usedForLargeScreens new value of usedForLargeScreens
     */
    public void setUsedForLargeScreens(boolean usedForLargeScreens) {
        this.usedForLargeScreens = usedForLargeScreens;
    }

    /**
     * Get the value of usedForMediumScreens
     * @return the value of usedForMediumScreens
     */
    public boolean isUsedForMediumScreens() {
        return usedForMediumScreens;
    }

    /**
     * Set the value of usedForMediumScreens
     * @param usedForMediumScreens new value of usedForMediumScreens
     */
    public void setUsedForMediumScreens(boolean usedForMediumScreens) {
        this.usedForMediumScreens = usedForMediumScreens;
    }
    
    /**
     * Get the value of usedForSmallScreens
     * @return the value of usedForSmallScreens
     */
    public boolean isUsedForSmallScreens() {
        return usedForSmallScreens;
    }

    /**
     * Set the value of usedForSmallScreens
     * @param usedForSmallScreens new value of usedForSmallScreens
     */
    public void setUsedForSmallScreens(boolean usedForSmallScreens) {
        this.usedForSmallScreens = usedForSmallScreens;
    }
    
    
    /**
     * Get the value of userAgents
     * @return the value of userAgents
     */
    public List<String> getUserAgents() {
        return userAgents;
    }

    /**
     * Set the value of userAgents
     * @param userAgents new value of userAgents
     */
    public void setUserAgents(List<String> userAgents) {
        this.userAgents = userAgents;
    }
}
