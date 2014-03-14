/*
 *  Document : DefaultController.java
 *  Created on  13.06.2011, 23:39:06
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


package net.componio.opencms.modules.eight.skinndriva.rd.engine.controller;

import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigException;

import  javax.servlet.jsp.JspException;
import  javax.servlet.jsp.PageContext;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;

import  org.opencms.main.OpenCms;
import  org.opencms.module.CmsModuleManager;
import  org.opencms.module.CmsModule;
import  org.opencms.jsp.CmsJspXmlContentBean;
import  org.opencms.jsp.I_CmsXmlContentContainer;
import  org.opencms.main.CmsException;
import  org.opencms.file.CmsResource;
import  org.opencms.file.CmsObject;
import  org.opencms.db.CmsPublishList;
import  org.opencms.report.CmsHtmlReport;
import  org.opencms.file.CmsProject;
import  org.opencms.file.CmsRequestContext;
import  org.opencms.publish.CmsPublishJobEnqueued;
import  org.opencms.publish.CmsPublishJobRunning;

import  java.util.Locale;
import  java.util.List;
import  java.util.Iterator;

/**
 * The base class for all theme engine controller classes, implementing all methods,
 * elements and properties common to all theme engine controller classes.
 * @author Robert Diawara
 */
public class DefaultController {
    /** Holds the page context, which is needed by all controller classes at runtime. */
    private PageContext         pageContext;
    /** Holds the servlet request, which is needed by all controller classes at runtime. */
    private HttpServletRequest  request;
    /** Holds the servlet response, which is needed by all controller classes at runtime. */
    private HttpServletResponse response;
    /** Holds the locale needed for operations at runtime. */
    private Locale locale;
    
    /** Constant defining the name of the project, which changed resources have
     * to be associated to.  */
    public static final String THEME_ENGINE_PRJ    = "Theme Engine Publish";
    
    /** Constant with the name of the offline project. */
    public static final String OFFLINE_PRJ         = "Offline";

    /**
     * Default constructor, which is protected her to avoid an initialization without
     * providing the mandatory page context, servlet request and servlet response.
     */
    protected DefaultController(){
        pageContext = null;
        request = null;
        response = null;
    }

    /**
     * Constructor providing all mandatory parameters needed for the initialization of
     * a theme engine controller class.
     * @param p_pageContext The page context needed by the theme engine controller an runtime.
     * @param p_request The servlet request needed by the theme engine controller an runtime.
     * @param p_response The servlet response needed by the theme engine controller an runtime.
     * @throws ThemeConfigException when one of the parameters <code>p_pageContext</code>,
     * <code>p_request</code> or <code>p_response</code> is null.
     */
    public DefaultController(PageContext p_pageContext, HttpServletRequest p_request, HttpServletResponse p_response)
            throws ThemeConfigException{

        // Abort with an exception when no page context provided.
        if(p_pageContext == null){
            throw new ThemeConfigException("A theme engine controller can not be initialized without a page context." +
                    "please provide a page context !");
        }
        // Abort with an exception when no servlet request provided.
        if(p_request == null){
            throw new ThemeConfigException("A theme engine controller can not be initialized without a servlet request." +
                    "please provide a servlet request !");
        }
        // Abort with an exception when no servlet response provided.
        if(p_response == null){
            throw new ThemeConfigException("A theme engine controller can not be initialized without a servlet response." +
                    "please provide a servlet response !");
        }

        // Initialize fields
        this.pageContext = p_pageContext;
        this.request = p_request;
        this.response = p_response;
    }

    /**
     * Gets the page context
     * @return The page context
     */
    protected PageContext getPageContext(){
        return this.pageContext;
    }

    /**
     * Gets the servlet request
     * @return The servlet request
     */
    protected HttpServletRequest getRequest(){
        return this.request;
    }

    /**
     * Gets the servlet response
     * @return The servlet response
     */
    protected HttpServletResponse getResponse(){
        return this.response;
    }
    
    /**
     * Checks, depending on the request header, if caching has to be
     * used or not.
     * @return  true, if caching has to be used. Otherwise false.
     */
    protected boolean useCaching(){
        boolean            result       = true;
        HttpServletRequest req          = getRequest();
        String             cacheControl;
        
        if(req != null){
            cacheControl = req.getHeader("Cache-Control");
            if(cacheControl != null){
                result = !cacheControl.equals("no-cache");
            }
        }
        return result;
    }
    
    /**
     * Gets the VFS path of the theme engine configuration.
     * @return The VFS path of the theme engine configuration.
     * @throws JspException when one of the calls to the underlying OpenCms API, necessary
     * to perform this operation, fails.
     */
    public String getConfigURI() throws JspException{
        CmsModuleManager         moduleManager    = OpenCms.getModuleManager();
        CmsModule                module           = moduleManager.getModule("net.componio.opencms.modules.eight.skinndriva.rd");

        return module.getParameter("config", "/system/modules/net.componio.opencms.modules.eight.skinndriva.rd/config/engine.conf");
    }

   /**
     * Gets the UUID of the theme engine which is currently used. This mainly used to generate unique
     * resource names for the themes built withtin this engine. This avoids name conflicts, when the themes
     * are exported and deployed to another OpenCms instance.
     * @return The UUID of the theme engine.
     * @throws ThemeConfigException  When one of the underlying calls fails.
     */
    public String getEngineUUID() throws ThemeConfigException{
        String result = null;
        String                   engineURI;
        CmsJspXmlContentBean     contentBean      = new CmsJspXmlContentBean(getPageContext(), getRequest(), getResponse());
        I_CmsXmlContentContainer contentContainer;
      
        try{
            engineURI = this.getConfigURI(); 
            contentContainer = contentBean.contentload("singleFile", engineURI, false);
            if(contentContainer.hasMoreResources()){
                result = contentContainer.getXmlDocument().getFile().getResourceId().getStringValue();
            }
         }catch(JspException jspEx){
            throw new ThemeConfigException(jspEx);
        }
        
        return result;
    }

    /**
     * Gets the VFS path of the folder, where all theme configurations are located.
     * @return The VFS path of the folder, where all theme configurations are located.
     * @throws JspException when one of the calls to the underlying OpenCms API, necessary
     * to perform this operation, fails.
     */
    public String getConfigFolder() throws JspException{
        String                   configURI        = getConfigURI();
        CmsJspXmlContentBean     contentBean      = new CmsJspXmlContentBean(getPageContext(), getRequest(), getResponse());
        I_CmsXmlContentContainer contentContainer;
        String                   result           = null;

        contentContainer = contentBean.contentload("singleFile", configURI, false);
        if(contentContainer.hasMoreResources()){
            result = this.contentShow(contentBean, contentContainer, "config-folder", null);
        }

        return result;
    }

    /**
     * Returns a selected content element String value from an XML content container.
     * @param p_contentBean The content bean, used to load the content from the content
     * container.
     * @param p_container The XML content container to read the content from.
     * @param p_element The element to show
     * @param p_default The value to set as default, when the element was not found.
     * @return The selected content element String value from the given XML content container
     */
    protected String contentShow(CmsJspXmlContentBean p_contentBean, I_CmsXmlContentContainer p_container, String p_element, String p_default){
        String result  = null;
        String element = escapeRegex(p_element);
        
        result = p_contentBean.contentshow(p_container, p_element);
        if(result.matches("^\\?\\?\\?\\ ((.*)\\/)?" + element + "\\ \\?\\?\\?$")){
            result = p_default;
        }
        return result;
    }

    /**
     * Returns a selected content element String value from an XML content container.
     * @param p_contentBean The content bean, used to load the content from the content
     * container.
     * @param p_container The XML content container to read the content from.
     * @param p_default The value to set as default, when the element was not found.
     * @return The selected content element String value from the given XML content container
     */
    protected String contentShow(CmsJspXmlContentBean p_contentBean, I_CmsXmlContentContainer p_container, String p_default){
        String result = null;

        result = p_contentBean.contentshow(p_container);
        if(result.startsWith("??? ")){
            result = p_default;
        }
        return result;
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
                        "Temporary Project, used to publish Theme Engine resources", "Users", "Administrators",
                        CmsProject.PROJECT_TYPE_TEMPORARY);
            }

            // Switch to the project.
            themeEnginePrj.setDeleteAfterPublishing(true);
            reqCtx.setCurrentProject(themeEnginePrj);
        }
    }

    /**
     * Publishes all resources, which were touched in the current project.
     * @param p_cmsObj Provides acces to the OpenCms workplace
     * @param p_publishResources An array list containing all resources to be published.
     * @throws CmsException If something goes wrong during the publish process
     */
    protected void publishResources(CmsObject p_cmsObj, List<CmsResource> p_publishResources) throws CmsException{
        CmsPublishList       publishList;
        CmsHtmlReport        publishReport;

         
        if((p_publishResources != null) && (p_publishResources.size() > 0)){
            p_cmsObj.getRequestContext().setSiteRoot("/");
            publishList = OpenCms.getPublishManager().getPublishList(p_cmsObj);
            publishReport = new CmsHtmlReport(Locale.getDefault(), "/");
            OpenCms.getPublishManager().publishProject(p_cmsObj, publishReport, publishList);
        }
    }
    
    /**
     * Checks if a resource is being published and stops the exectution till the resource is published.
     * @param p_res The resoure, for which the execution has to be stopped
     * @throws CmsException If something goes wrong wile waiting.
     */
    protected void waitWhilePublishing(CmsResource p_res) throws CmsException{
        while(isEnqueuedForPublish(p_res)){
            try{
                Thread.sleep(500);
            }catch(InterruptedException ex){
                return;
            }
        }
    }

    /**
     * Checks if a resource is enqueued for publishing.
     * @param p_res The resource to be checked.
     * @return <code>True</code> if the reousce is enqueued for publishing or is currently being published. Otherwise 
     * <code>false</code>.
     * @throws CmsException If the check fails an an exception is thrown by one of the underlying calls.
     */
    protected boolean isEnqueuedForPublish(CmsResource p_res) throws CmsException{
        boolean                         result             = false;
        List<CmsPublishJobEnqueued>     queue              = OpenCms.getPublishManager().getPublishQueue();
        Iterator<CmsPublishJobEnqueued> queueIt;
        Iterator<CmsResource>           resIt;
        CmsPublishJobRunning            runningJob;
        String                          resourcePath;

        // Proceed only, when the passed resource is not null
        if(p_res != null){
            resourcePath = p_res.getRootPath();
            
           // Check if the resources are listed within one of the publish jobs queued in the publish queue
            for(queueIt = queue.iterator(); (queueIt.hasNext()) && (!result);){
                for(resIt = queueIt.next().getPublishList().getAllResources().iterator(); (resIt.hasNext()) && (!result);){
                    result = resIt.next().getRootPath().equals(resourcePath);
                }
            }

            // If not found in the publish queue, check if the resources are published with the currently
            // running publish job (if there's one).
            if(!result){
                runningJob = OpenCms.getPublishManager().getCurrentPublishJob();
                if(runningJob != null){
                    for(resIt = runningJob.getPublishList().getAllResources().iterator(); (resIt.hasNext()) && (!result);){
                        result = resIt.next().getRootPath().equals(resourcePath);
                    }
                }
            }
        }
        return result;
    }


    /**
     * Check is a resource is enqueued for publishing, and if it is, removes it from the publish queue.
     * @param p_cmsObj Provides the access to the OpenCms workplace.
     * @param p_res The resource to be checked.
     * @return <code>>True</code> if the resource was enqueued an removed from publish queue, <code>false</code> 
     * otherwise.
     * @throws CmsException When one of the underlying calls throws an exception.
     */
    protected boolean removePublishLock(CmsObject p_cmsObj, CmsResource p_res) throws CmsException{
        boolean                         result             = false;
        List<CmsPublishJobEnqueued>     queue              = OpenCms.getPublishManager().getPublishQueue();
        CmsPublishJobEnqueued           currentJob;
        Iterator<CmsPublishJobEnqueued> queueIt;
        Iterator<CmsResource>           resIt;
        String                          resourcePath;
        boolean                         endLoop;

        // Proceed only, when the passed resource is not null
        if(p_res != null){
            resourcePath = p_res.getRootPath();
            
            // Check if the resource is listed within one of the publish jobs queued in the publish queue
            // and if it is, then remove the caontaining queue job.
            for(queueIt = queue.iterator(); (queueIt.hasNext()) && (!result);){
                currentJob = queueIt.next();

                resIt = currentJob.getPublishList().getAllResources().iterator();
                endLoop = !resIt.hasNext();
                while(!endLoop){
                    result = resIt.next().getRootPath().equals(resourcePath);
                    if(result){
                        OpenCms.getPublishManager().abortPublishJob(p_cmsObj, currentJob, true);
                    }
                    endLoop = (result || !resIt.hasNext());
                }
            }
        }
        return result;
   }

    /**
     * Gets the qualified name of a resource including its path at the VFS, but without the site root.
     * @param p_cmsObj Provides access to the VFS
     * @param p_resource Pepresents the VFS resource, which the qualified name is to retrieved from.
     * @return The qualified name of the resource.
     */
    protected String getQualifiedResourceName(CmsObject p_cmsObj, CmsResource p_resource){
        String result   = p_resource.getRootPath();
        String siteRoot;
        
        if(!result.startsWith("/system")){
            siteRoot = OpenCms.getSiteManager().getCurrentSite(p_cmsObj).getSiteRoot();
            if(result.startsWith(siteRoot)){
                result = result.substring(siteRoot.length());
            }
        }
        return result;
    }

    /**
     * Escapes the special characters in a string so that the string is usable as regular expression.
     * @param p_regex The string to be escaped
     * @return The escaped string
     */
    protected String escapeRegex(String p_regex){
        String result = p_regex;
        
        result = result.replace("\\", "\\\\");
        result = result.replace("[", "\\[");
        result = result.replace("]", "\\]");
        result = result.replace("{", "\\{");
        result = result.replace("}", "\\}");
        result = result.replace("?", "\\?");
        result = result.replace("-", "\\-");
        result = result.replace(".", "\\.");
        result = result.replace("/", "\\/");
        return result;
    }
}
