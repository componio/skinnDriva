/*
 *  Document : A_TreeBrowser.java
 *  Created on Mo, Apr 22 2013, 21:41:04
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

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.opencms.db.CmsPublishList;
import org.opencms.db.CmsResourceState;
import org.opencms.file.CmsObject;
import org.opencms.file.CmsProject;
import org.opencms.file.CmsRequestContext;
import org.opencms.file.CmsResource;
import org.opencms.file.CmsResourceFilter;
import org.opencms.jsp.CmsJspXmlContentBean;
import org.opencms.lock.CmsLock;
import org.opencms.main.CmsException;
import org.opencms.main.OpenCms;
import org.opencms.report.CmsHtmlReport;

/**
 *
 * @author Robert Diawara
 */
public abstract class A_TreeBrowser {
    /** Constant value for project name. */
    public static final String PRJ_SKINNDRIVA_MOD = "Skinndriva Modifications";
    /** Constant value for project name. */
    public static final String PRJ_OFFLINE        = "Offline";
    
    /* Holds the start folder of the tree to process. */
    private String startFolder;
    
    /** Holds the page context. */
    private PageContext         pageContext;
    
    /** Holds the servlet request. */
    private HttpServletRequest  request;
    
    /** Holds the servlet response. */
    private HttpServletResponse response;
    
    /** Holds the pattern for the type names to be processed. */
    private String typePattern;

    /**
     * The default constructor is defined as private here, to avoid
     * a creation of this class without the mandatory fields.
     */
    private A_TreeBrowser(){}
    
    /**
     * 
     * @param p_pageContext
     * @param p_request
     * @param p_response
     * @param p_startFolder 
     */
    public A_TreeBrowser(PageContext p_pageContext, HttpServletRequest p_request, HttpServletResponse p_response, String p_startFolder){
        // Abort with an exception, when no page context provided.
        if(p_pageContext == null){
            throw new RuntimeException("Missing mandatory parameter \"p_pageContext\" !");
        }

        // Abort with an exception, when no servlet request provided.
        if(p_request == null){
            throw new RuntimeException("Missing mandatory parameter \"p_request\" !");
        }

        // Abort with an exception, when no servlet response provided.
        if(p_response == null){
            throw new RuntimeException("Missing mandatory parameter \"p_response\" !");
        }

        // Abort with an exception, when no start folder provided.
        if((p_startFolder == null) || (p_startFolder.trim().length() == 0)){
            throw new RuntimeException("Missing mandatory parameter \"p_startFolder\" !");
        }
        
        this.pageContext = p_pageContext;
        this.request = p_request;
        this.response = p_response;
        this.startFolder = p_startFolder;
        this.typePattern = "^(.+)$";
    }
    
    /**
     * 
     * @param p_cmsObj
     * @param p_res
     * @return
     * @throws CmsException 
     */
    protected abstract String processResource(CmsObject p_cmsObj, CmsResource p_res) throws CmsException;
    
    /**
     * 
     * @param p_cmsObj
     * @param p_folderName
     * @throws CmsException 
     */
    private String browseFolder(CmsObject p_cmsObj, String p_folderName) throws CmsException{
        String                siteRoot;
        String                path;
        List<CmsResource>     files;
        List<CmsResource>     folders;
        Iterator<CmsResource> childrenIt;
        CmsResource           currentResource;
        String                msg;
        String                statusMsg;
        String                currentType;
        StringBuilder         strBuffer        = new StringBuilder();
        
        // Get the site root
        siteRoot = OpenCms.getSiteManager().getCurrentSite(p_cmsObj).getSiteRoot();
        
        // Process files in folder
        //files = p_cmsObj.getFilesInFolder(p_folderName);
        files = p_cmsObj.getResourcesInFolder(p_folderName, CmsResourceFilter.ALL);
        if(files != null){
            for(childrenIt = files.iterator(); childrenIt.hasNext();){
                currentResource = childrenIt.next();
                
                // Compose the path of the current resource
                path = currentResource.getRootPath();
                if(path.startsWith(siteRoot)){
                    path = path.substring(siteRoot.length());
                }
                
                // Get the type of the current file
                currentType = OpenCms.getResourceManager().getResourceType(currentResource).getTypeName();

                // Here, we proceed only when the type name of the current object matches the pattern
                if(currentType.matches(getTypePattern())){
                    // Process the resource and get the result message.
                    if(currentResource.getState().equals(CmsResourceState.STATE_UNCHANGED)){
                        switchToProject(p_cmsObj, A_TreeBrowser.PRJ_SKINNDRIVA_MOD);
                        statusMsg = "<span style=\"color:blue\">Resource is unused and will be published !</span>";
                    }else{
                        switchToProject(p_cmsObj, A_TreeBrowser.PRJ_OFFLINE);
                        statusMsg = "<span style=\"color:red;font-style:italic\">Resource is in use and can't be published !</span>";
                    }
                    msg = processResource(p_cmsObj, currentResource);

                    // Write log output.
                    strBuffer.append("    <li>\n");
                    strBuffer.append("        <p style=\"margin:5px 0px 0px 0px;font-weight:bold\">Processing : ").append(path).append("</p>\n");
                    if((msg != null) && (msg.trim().length() != 0)){
                        strBuffer.append("        <p style=\"margin:0px 0px 0px 0px\">").append(msg).append("</p>\n");
                    }
                    strBuffer.append("        <p style=\"margin:0px 0px 0px 0px\">").append(statusMsg).append("</p>\n");
                    strBuffer.append("    </li>\n");
                }
            }
        }
        
        // Process subfolders
        folders = p_cmsObj.getSubFolders(p_folderName);
        if(folders != null){
            for(childrenIt = folders.iterator(); childrenIt.hasNext();){
                currentResource = childrenIt.next();
                
                // Compose the path of the current resource
                path = currentResource.getRootPath();
                if(path.startsWith(siteRoot)){
                    path = path.substring(siteRoot.length());
                }
                
                if(currentResource.isFolder()){
                    strBuffer.append(browseFolder(p_cmsObj, path));
                }
            }
        }
        
        return strBuffer.toString();
    }
    
    /**
     * 
     * @return
     * @throws CmsException 
     */
    public String run() throws CmsException{
        CmsJspXmlContentBean cBean     = new CmsJspXmlContentBean(pageContext, request, response);
        CmsObject            cmsObj    = cBean.getCmsObject();
        StringBuilder        strBuffer = new StringBuilder();
        
        strBuffer.append("\n<ul>\n");
        strBuffer.append(browseFolder(cmsObj, startFolder));
        strBuffer.append("</ul>\n");
        
        this.publishProject(cmsObj, A_TreeBrowser.PRJ_SKINNDRIVA_MOD);
        return strBuffer.toString();
    }
    
    /**
     * Gets the start folder
     * @return 
     */
    public String getStartFolder() {
        return startFolder;
    }
 
    /**
     * Get the value of typePattern
     * @return the value of typePattern
     */
    public String getTypePattern() {
        return (typePattern != null) && (typePattern.trim().length() > 0) ? typePattern : "^(.+)$";
    }

    /**
     * Set the value of typePattern
     * @param typePattern new value of typePattern
     */
    public void setTypePattern(String typePattern) {
        this.typePattern = typePattern;
    }

     /**
     * Switches to another prokject. This is used to publish all resources, which are edited, together in one step
     * @param p_cmsObj The Cms object providing access to the wokplace
     * @param p_projectName The name of the project to switch to. If the project doesn't exist, it will be created.
     * After all the resources are published, it will be deleted again.
     * @throws CmsException When something goes wrong during the switch to the project.
     */
    protected void switchToProject(CmsObject p_cmsObj, String p_projectName) throws CmsException{
        CmsRequestContext    reqCtx;
        CmsProject           currentPrj;
        CmsProject           themeEnginePrj;

        // Assure, that we're in the right project. If not try to switch to the right Project.
        // If it does not exist, create it.
        reqCtx = p_cmsObj.getRequestContext(); 
        currentPrj = reqCtx.getCurrentProject();

        // If current project is not "Image Changes", try to switch to.
        if(!currentPrj.getName().equals(p_projectName)){
            // Try to get the project.
            try{
                themeEnginePrj = p_cmsObj.readProject(p_projectName);
            }catch(Exception ex){
                themeEnginePrj = null;
            }

            // If project is still null, then it doesn't exist, so try to create it
            if(themeEnginePrj == null){
                themeEnginePrj = p_cmsObj.createProject(p_projectName,
                        "Project, used to publish Skinndriva resources", "Users", "Administrators",
                        CmsProject.PROJECT_TYPE_NORMAL);
            }

            // Switch to the project.
            themeEnginePrj.setDeleteAfterPublishing(false);
            reqCtx.setCurrentProject(themeEnginePrj);
        }
    }


    /**
     * Publishes all resources, which were touched in a project.
     * @param p_cmsObj Provides acces to the OpenCms workplace
     * @param p_projectName The project to be published
     * @throws CmsException If something goes wrong during the publish process
     */
    protected void publishProject(CmsObject p_cmsObj, String p_projectName) throws CmsException{
        CmsPublishList       publishList;
        CmsHtmlReport        publishReport;
        String               siteRoot      = siteRoot = OpenCms.getSiteManager().getCurrentSite(p_cmsObj).getSiteRoot();

        switchToProject(p_cmsObj, p_projectName); 
        p_cmsObj.getRequestContext().setSiteRoot(siteRoot);
        publishList = OpenCms.getPublishManager().getPublishList(p_cmsObj);
        publishReport = new CmsHtmlReport(Locale.getDefault(), siteRoot);
        OpenCms.getPublishManager().publishProject(p_cmsObj, publishReport, publishList);
    }


    /**
     * 
     * @param p_cmsObj
     * @param p_res
     * @throws CmsException 
     */
    protected void lockResource(CmsObject p_cmsObj, CmsResource p_res) throws CmsException{
        CmsLock lock = p_cmsObj.getLock(p_res);
        
        if((lock == null) || (lock.isNullLock()) || (lock.isUnlocked())){
            p_cmsObj.lockResource(p_res);
        }else{
            p_cmsObj.changeLock(p_res);
        }
    }

    /**
     * 
     * @param p_cmsObj
     * @param p_res
     * @throws CmsException 
     */
    protected void unlockResource(CmsObject p_cmsObj, CmsResource p_res) throws CmsException{
        p_cmsObj.unlockResource(p_res);
    }
}
