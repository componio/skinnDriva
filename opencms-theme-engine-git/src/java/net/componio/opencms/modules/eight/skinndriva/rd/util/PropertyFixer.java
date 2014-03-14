/*
 *  Document : PropertyFixer.java
 *  Created on Mo, Apr 22 2013, 23:41:24
 *  Copyright (C) 2013 Robert Diawara
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
package net.componio.opencms.modules.eight.skinndriva.rd.util;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import org.opencms.file.CmsObject;
import org.opencms.file.CmsProperty;
import org.opencms.file.CmsResource;
import org.opencms.main.CmsException;

/**
 *
 * @author Robert Diawara
 */
public class PropertyFixer extends A_TreeBrowser{
    /** Holds the name of the VFS property to be set. */
    private String propertyName;
    /** Holds the value of the VFS property to be set. */
    private String propertyValue;
    /** Holds the type of the VFS property (individual, or shared). */
    private String propertyType;
    /** Holds the pattern of the original property values to be replaced. */
    private String originalPattern;

    /**
     * Default constructors
     * Defined as private here to avaoid an instantiaion without the mandatory parameters.
     */
    private PropertyFixer(){
        super(null, null, null, null);
        
        this.originalPattern = "^(.*)$";
    }
    
    /**
     * 
     * @param p_pageContext
     * @param p_request
     * @param p_response
     * @param p_startFolder 
     */
    public PropertyFixer(PageContext p_pageContext, HttpServletRequest p_request, HttpServletResponse p_response, String p_startFolder){
        super(p_pageContext, p_request, p_response, p_startFolder);
        
        this.propertyType = CmsProperty.TYPE_INDIVIDUAL;
    }

    /**
     * 
     * @param p_cmsObj
     * @param p_res
     * @return
     * @throws CmsException 
     */
    @Override
    protected String processResource(CmsObject p_cmsObj, CmsResource p_res) throws CmsException {
        CmsProperty       prp           = new CmsProperty();
        CmsProperty       originalPrp;
        List<CmsProperty> prpList       = new ArrayList<CmsProperty>();
        String            originalValue;
        String            result;
        
        // Get the original value of the property
        originalPrp = p_cmsObj.readPropertyObject(p_res, getPropertyName(), false);
        originalValue = originalPrp != null ? originalPrp.getValue() : "";
        if((originalValue == null) || (originalValue.trim().length() == 0)){
            originalValue = "";
        }
        
        // And now, we only proceed, when the original value matches the pattern
        if(originalValue.matches(getOriginalPattern())){
            prp.setName(getPropertyName());
            prp.setValue(getPropertyValue(), getPropertyType());
            prpList.add(prp);

            lockResource(p_cmsObj, p_res);
            p_cmsObj.writePropertyObjects(p_res, prpList);
            this.unlockResource(p_cmsObj, p_res);
            
            result = "<span style=\"color:blue\">Original property value matched with the pattern. Resource successfully changed !</span>";
        }else{
            result = "<span style=\"color:red;font-style:italic\">Original property value didn't macht with the pattern. Resource skipped !</span>";
        }
       
        return result;
    }
    

    /**
     * Get the value of propertyName
     * @return the value of propertyName
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Set the value of propertyName
     * @param propertyName new value of propertyName
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    
    /**
     * Get the value of propertyValue
     * @return the value of propertyValue
     */
    public String getPropertyValue() {
        return propertyValue;
    }

    /**
     * Set the value of propertyValue
     * @param propertyValue new value of propertyValue
     */
    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    /**
     * Get the value of propertyType
     * @return the value of propertyType
     */
    public String getPropertyType() {
        return (propertyType != null) && (propertyType.trim().length() > 0) ? propertyType : CmsProperty.TYPE_INDIVIDUAL;
    }

    /**
     * Set the value of propertyType
     * @param propertyType new value of propertyType
     */
    public void setPropertyType(String propertyType) {
        if((!CmsProperty.TYPE_INDIVIDUAL.equals(propertyType)) && (!CmsProperty.TYPE_SHARED.equals(propertyType))){
            throw new RuntimeException("Invalid value for the property type. Values should be \"" +
                    CmsProperty.TYPE_INDIVIDUAL + "\" or \"" + CmsProperty.TYPE_SHARED + "\" !");
        }
        this.propertyType = propertyType;
    }

    /**
     * Get the value of originalPattern
     * @return the value of originalPattern
     */
    public String getOriginalPattern() {
        return (originalPattern != null) && (originalPattern.trim().length() > 0) ? originalPattern : "^(.*)$";
    }

    /**
     * Set the value of originalPattern
     * @param originalPattern new value of originalPattern
     */
    public void setOriginalPattern(String originalPattern) {
        this.originalPattern = originalPattern;
    }
}
