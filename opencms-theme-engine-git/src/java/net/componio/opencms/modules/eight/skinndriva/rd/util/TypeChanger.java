/*
 *  Document : TypeChanger.java
 *  Created on So, Aug 18 2013, 14:17:39
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.opencms.file.CmsFile;
import org.opencms.file.CmsObject;
import org.opencms.file.CmsResource;
import org.opencms.loader.CmsLoaderException;
import org.opencms.main.CmsException;
import org.opencms.main.OpenCms;

/**
 *
 * @author Robert Diawara
 */
public class TypeChanger extends SchemaFixer{
    /** Holds the name of the new type to be set. */
    private String newTypeName;
    /** Holds the old type name in the schema to be replaced. */
    private String oldSchemaType;
    /** Holds the new type name for the schema which the old one is to be replaced by. */
    private String newSchemaType;

    /**
     * Default constructors
     * Defined as private here to avaoid an instantiaion without the mandatory parameters.
     */
    private TypeChanger(){
        super(null, null, null, null);
    }
    
    /**
     * 
     * @param p_pageContext
     * @param p_request
     * @param p_response
     * @param p_startFolder 
     */
    public TypeChanger(PageContext p_pageContext, HttpServletRequest p_request, HttpServletResponse p_response, String p_startFolder){
        super(p_pageContext, p_request, p_response, p_startFolder);
    }
    
    /**
     * Get the value of newTypeName
     * @return the value of newTypeName
     */
    public String getNewTypeName() {
        return newTypeName;
    }

    /**
     * Set the value of newTypeName
     * @param newTypeName new value of newTypeName
     */
    public void setNewTypeName(String newTypeName) {
        this.newTypeName = newTypeName;
    }

     /**
     * Get the value of newTypeId
     * @return the value of newTypeId
     * @throws CmsLoaderException  
     */
    public int getNewTypeId() throws CmsLoaderException{
        int result = 0;
        
        if((newTypeName == null) || (newTypeName.trim().length() == 0)){
            throw new RuntimeException("Could nort set new type for the resource. No type name defined !");
        }
        result = OpenCms.getResourceManager().getResourceType(newTypeName).getTypeId();
        return result;
    }
    
    /**
     * Get the value of oldSchemaType
     * @return the value of oldSchemaType
     */
    public String getOldSchemaType() {
        return oldSchemaType;
    }

    /**
     * Set the value of oldSchemaType
     * @param oldSchemaType new value of oldSchemaType
     */
    public void setOldSchemaType(String oldSchemaType) {
        this.oldSchemaType = oldSchemaType;
    }

    /**
     * Get the value of newSchemaType
     * @return the value of newSchemaType
     */
    public String getNewSchemaType() {
        return newSchemaType;
    }

    /**
     * Set the value of newSchemaType
     * @param newSchemaType new value of newSchemaType
     */
    public void setNewSchemaType(String newSchemaType) {
        this.newSchemaType = newSchemaType;
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
        CmsFile file;
        String  fileContent;
        String  message       = null;
        
        p_res.setType(getNewTypeId());
        
        if(p_res.isFile()){
            file = p_cmsObj.readFile(p_res);
            
            fileContent = new String(file.getContents());
            if(fileContent.indexOf(getOriginalString()) != -1){
                if((getOriginalString().trim().length() >0) && (getReplacementString().trim().length() > 0)){
                    fileContent = fileContent.replace(getOriginalString(), getReplacementString());
                    if(fileContent.indexOf(getOldSchemaType()) != -1){
                        if((getOldSchemaType().trim().length() >0) && (getNewSchemaType().trim().length() > 0)){
                            fileContent = fileContent.replace(getOldSchemaType(), getNewSchemaType());
                        }
                    }

                    
                    file.setContents(fileContent.getBytes());
                    lockResource(p_cmsObj, file);
                    p_cmsObj.writeFile(file);
                    unlockResource(p_cmsObj, file);
                }
                message = "<span style=\"color:blue\"> Type changed !</span>";
            }
            
        }
        return message;
    }
    
}
