/*
 *  Document : SchemaFixer.java
 *  Created on Mo, Apr 22 2013, 23:41:06
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
import org.opencms.main.CmsException;

/**
 *
 * @author Robert Diawara
 */
public class SchemaFixer extends A_TreeBrowser{
    /** Holds the original string, which is to be replaced. */
    private String originalString;
    /** Holds the string by which the original string is to be replaced. */
    private String replacementString;

    /**
     * Default constructors
     * Defined as private here to avaoid an instantiaion without the mandatory parameters.
     */
    private SchemaFixer(){
        super(null, null, null, null);
    }
    
    /**
     * 
     * @param p_pageContext
     * @param p_request
     * @param p_response
     * @param p_startFolder 
     */
    public SchemaFixer(PageContext p_pageContext, HttpServletRequest p_request, HttpServletResponse p_response, String p_startFolder){
        super(p_pageContext, p_request, p_response, p_startFolder);
    }

    /**
     * Get the value of originalString
     * @return the value of originalString
     */
    public String getOriginalString() {
        return originalString != null ? originalString : "";
    }

    /**
     * Set the value of originalString
     * @param originalString new value of originalString
     */
    public void setOriginalString(String originalString) {
        this.originalString = originalString;
    }

    /**
     * Get the value of replacementString
     * @return the value of replacementString
     */
    public String getReplacementString() {
        return replacementString != null ? replacementString : "";
    }

    /**
     * Set the value of replacementString
     * @param replacementString new value of replacementString
     */
    public void setReplacementString(String replacementString) {
        this.replacementString = replacementString;
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
        String  message     = null;
        
        if(p_res.isFile()){
            file = p_cmsObj.readFile(p_res);
            
            fileContent = new String(file.getContents());
            if(fileContent.indexOf(getOriginalString()) != -1){
                if((getOriginalString().trim().length() >0) && (getReplacementString().trim().length() > 0)){
                    fileContent = fileContent.replace(getOriginalString(), getReplacementString());
                    file.setContents(fileContent.getBytes());
                    lockResource(p_cmsObj, file);
                    p_cmsObj.writeFile(file);
                    unlockResource(p_cmsObj, file);
                }
                message = "<span style=\"color:blue\">Found pattern \"" + getOriginalString() + "\" !</span>";
            }else{
                message = "<span style=\"color:red;font-style:italic\">Pattern not found !</span>";
            }
        }
        
        return message;
    }
    
}
