/*
 *  Document : ThemeEngineListDialog.java
 *  Created on Do, Nov 10 2011, 16:16:34
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
package net.componio.opencms.modules.eight.skinndriva.rd.engine.view;

import  org.opencms.workplace.list.A_CmsListDialog;
import  org.opencms.jsp.CmsJspActionElement;
import  org.opencms.i18n.CmsMessageContainer;
import  org.opencms.workplace.list.CmsListOrderEnum;
import  org.opencms.workplace.list.CmsListColumnAlignEnum;
import  org.opencms.i18n.CmsMessageException;

import  net.componio.opencms.modules.eight.skinndriva.rd.Messages;

import  javax.servlet.jsp.PageContext;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;



/**
 * Provides dialogs with list widgets for the theme engine. All methods shared by all list widgets in the theme engine
 * are provided here, so that they don't need to be reimplemented for each single list widget in the theme engine.
 * @author Robert Diawara
 */
public abstract class ThemeEngineListDialog extends A_CmsListDialog{
    private PageContext         pageContext; // Holds the page context
    private HttpServletRequest  request;     // Holds the servlet request for the current page
    private HttpServletResponse response;    // Holds the servlet response for the current page
    
    /**
     * Protected constructor, used by public constructor of the subclasses.
     * @param ctx The request context to be assigned the new instance. Also used to initialize a new JSP action element.
     * @param req The servlet request to be assigned the new instance. Also used to initialize a new JSP action element.
     * @param resp The servlet response to be assigned the new instance. Also used to initialize a new JSP action element.
     * @param listId The id of the displayed list.
     * @param listName The name of the displayed list.
     * @param sortedColId The a priory sorted column.
     * @param sortOrder The order of the sorted column.
     * @param searchableColId The column to search into.
     */
    protected ThemeEngineListDialog(PageContext ctx, HttpServletRequest req, HttpServletResponse resp, java.lang.String listId, CmsMessageContainer listName, 
            java.lang.String sortedColId, CmsListOrderEnum sortOrder, java.lang.String searchableColId){
        super(new CmsJspActionElement(ctx, req, resp), listId, listName, sortedColId, sortOrder, searchableColId);
            
        this.pageContext = ctx;
        this.request = req;
        this.response = resp;
    }   
    
    /**
     * 
     * @param ctx The request context to be assigned the new instance. Also used to initialize a new JSP action element.
     * @param req The servlet request to be assigned the new instance. Also used to initialize a new JSP action element.
     * @param resp The servlet response to be assigned the new instance. Also used to initialize a new JSP action element.
     * @param listId The id of the displayed list.
     * @param listName The name of the displayed list.
     * @param sortedColId The a priory sorted column.
     * @param sortOrder The order of the sorted column.
     * @param searchableColId The column to search into.
     * @param lazy If this parameter is true, the list should load only load the list items of the current page, if possible.
     */
    protected ThemeEngineListDialog(PageContext ctx, HttpServletRequest req, HttpServletResponse resp, String listId, 
            CmsMessageContainer listName, String sortedColId, CmsListOrderEnum sortOrder, java.lang.String searchableColId, boolean lazy){
        super(new CmsJspActionElement(ctx, req, resp), listId, listName, sortedColId, sortOrder, searchableColId, lazy);
            
        this.pageContext = ctx;
        this.request = req;
        this.response = resp;
}

    /**
     * Gets the alignment for a column from the resource bundle. If the alignment is not defined, a CmsMessageException
     * will be thrown. If its value in the resource bundle is not a proper one, this method will return 
     * <code>CmsListColumnAlignEnum.ALIGN_LEFT</code> as default value.
     * @param p_key The key for the alignment in the resource bundle.
     * @return The alignment of the column. If not properly defined, <code>CmsListColumnAlignEnum.ALIGN_LEFT</code>;
     * @throws CmsMessageException When the access to the message bundle fails.
     */
    protected CmsListColumnAlignEnum getColumnAlign(String p_key) throws CmsMessageException{
        CmsListColumnAlignEnum result = CmsListColumnAlignEnum.ALIGN_LEFT;
        String                 tmp    = Messages.get().getBundle().getString(p_key);
        
        if(tmp != null){
            tmp = tmp.toLowerCase();
            if(ThemeList.COLUMN_ALIGN_LEFT.equals(tmp)){
                result = CmsListColumnAlignEnum.ALIGN_LEFT;
            }else if(ThemeList.COLUMN_ALIGN_CENTER.equals(tmp)){
                result = CmsListColumnAlignEnum.ALIGN_CENTER;
            }else if(ThemeList.COLUMN_ALIGN_RIGHT.equals(tmp)){
                result = CmsListColumnAlignEnum.ALIGN_RIGHT;
            }else{
                result = CmsListColumnAlignEnum.ALIGN_LEFT;
            }
        }
        return result;
    }
    
    /**
     * Gets the &quot;sorteable&quot; attribute for a column from the resource bundle, to determine id acolumn is
     * sorteable. If the value assigned to the key in the resource bundle is not a proper boolean value (true, yes, on, 
     * false, no or off), <code>false</code> will be returned as default value. If no value is defined at all, 
     * a <code>CmsMessageException</code> will be thrown.
     * @param p_key The key for the alignment in the resource bundle.
     * @return <code>true</code>, if the column has to be sorteable. Otherwise <code>false</code>.
     * @throws CmsMessageException When the access to the message bundle fails.
     */
    protected boolean getSorteable(String p_key) throws CmsMessageException{
        boolean result = false;
        String  tmp    = Messages.get().getBundle().getString(p_key);
        
        if(tmp != null){
            tmp = tmp.toLowerCase();
            result = ((tmp.equals("true")) || (tmp.equals("yes")) || (tmp.equals("on")));
        }
        return result;
    }


    /**
     * Get the value of the pageContext
     * @return the value of pageContext
     */
    public PageContext getPageContext() {
        return pageContext;
    }

    /**
     * Get the value of the servlet request
     * @return the value of request
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Get the value of the servlet response
     * @return the value of response
     */
    public HttpServletResponse getResponse() {
        return response;
    }

}
