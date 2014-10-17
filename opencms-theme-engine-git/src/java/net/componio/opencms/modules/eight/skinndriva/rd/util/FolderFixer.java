/*
 *  Document : FolderFixer.java
 *  Created on So, Sep 22 2013, 12:11:08
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

import org.opencms.file.CmsObject;
import org.opencms.file.CmsResource;
import org.opencms.main.CmsException;
import org.opencms.main.OpenCms;

/**
 *
 * @author Robert Diawara
 */
public class FolderFixer extends A_TreeBrowser{
    
    /**
     * The default constructor is defined as private here, to avoid
     * a creation of this class without the mandatory fields.
     */
    private FolderFixer(){
        super(null, null, null, null);
    }

    /**
     * 
     * @param p_pageContext
     * @param p_request
     * @param p_response
     * @param p_startFolder 
     */
    public FolderFixer(PageContext p_pageContext, HttpServletRequest p_request, HttpServletResponse p_response, String p_startFolder){
        super(p_pageContext, p_request, p_response, p_startFolder);
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
        String parentFolderName;
        String folderName;
        String resTypeName;
        String result           = null;
        int    newTypeId;
        
        parentFolderName = CmsResource.getParentFolder(p_res.getRootPath());
        if(parentFolderName.matches("^(.*)\\/\\.content(\\/)?$")){
            folderName = p_res.getName();
            resTypeName = OpenCms.getResourceManager().getResourceType(p_res).getTypeName();
            
            // Fix folder names and folder types
            if(folderName.matches("^(\\.)?custom\\_navigation$")){
                p_cmsObj.renameResource(folderName, "skinndriva_link_list");
                if(resTypeName.matches("^((custom\\_navigation|generic\\_article)\\_gallery)|folder$")){
                    newTypeId = OpenCms.getResourceManager().getResourceType("skinndriva_link_list_gallery").getTypeId();
                    p_res.setType(newTypeId);
                }
            }else if(folderName.matches("^(\\.)?generic\\_article$")){
                p_cmsObj.renameResource(folderName, "skinndriva_article");
                if(resTypeName.matches("^(generic\\_article\\_gallery)|folder$")){
                    newTypeId = OpenCms.getResourceManager().getResourceType("skinndriva_article_gallery").getTypeId();
                    p_res.setType(newTypeId);
                }
            }else if(folderName.matches("^(\\.)?generic\\_navigation$")){
                p_cmsObj.renameResource(folderName, "skinndriva_navigation");
                if(resTypeName.matches("^(generic\\_(article|navigation)\\_gallery)|folder$")){
                    newTypeId = OpenCms.getResourceManager().getResourceType("skinndriva_navigation_gallery").getTypeId();
                    p_res.setType(newTypeId);
                }
            }else if(folderName.matches("^(\\.)?generic\\_news$")){
                p_cmsObj.renameResource(folderName, "skinndriva_news");
                if(resTypeName.matches("^(generic\\_(article|news)\\_gallery)|folder$")){
                    newTypeId = OpenCms.getResourceManager().getResourceType("skinndriva_news_gallery").getTypeId();
                    p_res.setType(newTypeId);
                }
            }else if(folderName.matches("^(\\.)?header\\_image$")){
                p_cmsObj.renameResource(folderName, "skinndriva_image");
                if(resTypeName.matches("^((generic\\_article|header\\_image)\\_gallery)|folder$")){
                    newTypeId = OpenCms.getResourceManager().getResourceType("skinndriva_image_gallery").getTypeId();
                    p_res.setType(newTypeId);
                }
            }else{
                if(!folderName.matches("^(\\.)?(new|detail)$")){
                    if(resTypeName.matches("^(generic\\_article\\_gallery)|folder$")){
                        newTypeId = OpenCms.getResourceManager().getResourceType("skinndriva_common_gallery").getTypeId();
                        p_res.setType(newTypeId);
                    }
                }
            }
        }
        return result;
    }
    
}
