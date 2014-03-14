/*
 *  Document : FormatterTag.java
 *  Created on Mo, Okt 08 2012, 19:55:38
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
package net.componio.opencms.modules.eight.skinndriva.rd.taglib.core;

import net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeConfig;
import net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeFormatter;
import net.componio.opencms.modules.eight.skinndriva.rd.taglib.ThemeEngineThemeTagBase;

import javax.servlet.jsp.JspException;
import org.opencms.main.CmsException;
import org.opencms.main.OpenCms;

/**
 *
 * @author Robert Diawara
 */
public class FormatterTag extends ThemeEngineThemeTagBase{
    private String elementType;
    private String containerType;
    private int    elementTypeId;

    /**
     * Get the value of elementType
     * @return the value of elementType
     */
    public String getElementType() {
        return elementType;
    }

    /**
     * Set the value of elementType
     * @param elementType new value of elementType
     */
    public void setElementType(String elementType) {
        // Abort with an exception, when no resource type is provided
        if((elementType == null) || (elementType.trim().length() == 0)){
            throw new RuntimeException("Tag attribute \"elementType\" is mandatory and can not be null");
        }
        try{
            this.elementTypeId = OpenCms.getResourceManager().getResourceType(elementType).getTypeId();
            this.elementType = elementType;
        }catch(CmsException ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * Get the value of elementTypeId
     * @return the value of elementTypeId
     */
    public int getElementTypeId() {
        return elementTypeId;
    }

    /**
     * Set the value of elementTypeId
     * @param elementTypeId new value of elementTypeId
     */
    public void setElementTypeId(int elementTypeId) {
        try{
            setElementType(OpenCms.getResourceManager().getResourceType(elementTypeId).getTypeName());
            this.elementTypeId = elementTypeId;
        }catch(CmsException ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * Get the value of containerType
     * @return the value of containerType
     */
    public String getContainerType() {
        return containerType;
    }

    /**
     * Set the value of containerType
     * @param containerType new value of containerType
     */
    public void setContainerType(String containerType) {
        // Abort with an exception, when no container type is provided
        if((containerType == null) || (containerType.trim().length() == 0)){
            throw new RuntimeException("Tag attribute \"containerType\" is mandatory and can not be null");
        }
        this.containerType = containerType;
    }

    /**
     * Process the end tag for this instance. This method is invoked by the JSP page implementation object on all Tag handlers.
     * This method will be called after returning from doStartTag. The body of the action may or may not have been evaluated, depending on 
     * the return value of doStartTag.
     * If this method returns EVAL_PAGE, the rest of the page continues to be evaluated. If this method returns SKIP_PAGE, the rest 
     * of the page is not evaluated, the request is completed, and the doEndTag() methods of enclosing tags are not invoked. If this request 
     * was forwarded or included from another page (or Servlet), only the current page evaluation is stopped.
     * The JSP container will resynchronize the values of any AT_BEGIN and AT_END variables (defined by the associated TagExtraInfo or TLD) 
     * after the invocation of doEndTag(). 
     * @return indication of whether to continue evaluating the JSP page.
     * @throws JspException if an error occurred while processing this tag
     */
    @Override
    public int doEndTag() throws JspException {
        ThemeConfig    themeCfg = getTheme();
        String         result   = null;
        ThemeFormatter formatter;
        
        // Proceed only, when a theme configuration found
        if(themeCfg != null){
            formatter = themeCfg.getFormatter(getElementType(), getContainerType());
            if(formatter != null){
                result = formatter.getJsp();
                if((result != null) && (result.trim().length() == 0)){
                    result = null;
                }
            }
        }
            
        // Write out the result
        this.writeTagResult(result);
        
        // Return the tag result
        return ThemeEngineThemeTagBase.SKIP_BODY;
    }
}
