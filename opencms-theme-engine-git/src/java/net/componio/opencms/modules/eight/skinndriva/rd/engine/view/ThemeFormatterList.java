/*
 *  Document : ThemeFormatterList.java
 *  Created on Fr, Okt 05 2012, 20:27:51
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
package net.componio.opencms.modules.eight.skinndriva.rd.engine.view;

import  javax.servlet.ServletException;
import  javax.servlet.jsp.PageContext;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;

import  net.componio.opencms.modules.eight.skinndriva.rd.Messages;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigException;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.controller.I_ThemeConfigController;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.controller.ThemeFactory;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeConfig;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeFormatter;

import  java.io.IOException;
import  java.util.ArrayList;
import  java.util.Map;
import  java.util.HashMap;
import  java.util.Iterator;
import  java.util.List;

import  org.opencms.main.CmsException;
import  org.opencms.main.CmsRuntimeException;
import  org.opencms.workplace.list.A_CmsListDialog;
import  org.opencms.workplace.list.CmsListItem;
import  org.opencms.workplace.list.CmsListMetadata;
import  org.opencms.workplace.list.CmsListOrderEnum;
import  org.opencms.workplace.list.CmsListColumnDefinition;
import  org.opencms.workplace.list.CmsListDirectAction;
import  org.opencms.i18n.I_CmsMessageBundle;
import  org.opencms.workplace.list.CmsListMultiAction;
import  org.opencms.workplace.list.CmsListSearchAction;

/**
 *
 * @author Robert Diawara
 */
public class ThemeFormatterList extends ThemeEngineListDialog{
    /** Constant for the list id. */
    public static final String LIST_ID                   = "themeformatters";
    
    /** Column name id constant for the column &quot;resource type&quot;. */
    public static final String LIST_COLUMN_RESOURCETYPE  = "crt";
    /** Column name id constant for the column &quot;container type&quot;. */
    public static final String LIST_COLUMN_CONTAINERTYPE = "cct";
    /** Column name id constant for the column &quot;jsp&quot;. */
    public static final String LIST_COLUMN_JSP           = "cjsp";
    /** Column name id constant for the column &quot;edit&quot;. */
    public static final String LIST_COLUMN_EDIT          = "ce";
    /** Column name id constant for the column &quot;delete&quot;. */
    public static final String LIST_COLUMN_DELETE        = "cd";
    
    /** List column action id constant for the action &quot;refresh&quot;. */
    public static final String LIST_ACTION_REFRESH      = "rf";
    /** List column action id constant for the action &quot;details&quot;. */
    public static final String LIST_ACTION_DETAILS      = "de";
    /** List column action id constant for the action &quot;edit&quot;. */
    public static final String LIST_ACTION_DEFAULT_EDIT = "ade";
    /** List column action id constant for the action &quot;edit&quot;. */
    public static final String LIST_ACTION_EDIT         = "ae";
    /** List column action id constant for the action &quot;delete&quot;. */
    public static final String LIST_ACTION_DELETE       = "ad";
    /** List column action id constant for the multiple action &quot;delete&quot;. */
    public static final String LIST_MACTION_DELETE      = "mad";
    /** List column action id constant for the multiple action &quot;publish&quot;. */
    public static final String LIST_MACTION_PUBLISH     = "map";
    
    /** Constant for column alignment. */
    public static final String COLUMN_ALIGN_LEFT        = "left";
    /** Constant for column alignment. */
    public static final String COLUMN_ALIGN_CENTER      = "center";
    /** Constant for column alignment. */
    public static final String COLUMN_ALIGN_RIGHT       = "right";
    
    /** Path constant for the edit icon. */
    public static final String ICON_EDIT                = "tools/skinndriva/icons/small/cog-Edit.png";
    
    /** Path constant for the formatter key URL parameter. */
    public static final String PARAM_FORMATTER_KEY      = "formatter_key";
    
    /** Holds the value of the property m_themeId. */
    private String m_themeId;
    
    
    /**
     * Public constructor. Creates a new ThemeFormatterList instance.
     * @param context  The request context to be assigned the new instance. Also used to initialize a new JSP action element
     * in the superclass.
     * @param req The servlet The servlet request to be assigned the new instance. Also used to initialize a new JSP action element
     * in the sulerclass.
     * @param res  The servlet response to be assigned the new instance. Also used to initialize a new JSP action element
     * in the superclass.
     */
    public ThemeFormatterList(PageContext context, HttpServletRequest req, HttpServletResponse res) {
        super(context, req, res, LIST_ID, Messages.get().container(Messages.GUI_FORMATTERS_LIST_DIALOG_GROUP_NAME_0), 
                LIST_COLUMN_RESOURCETYPE, CmsListOrderEnum.ORDER_ASCENDING, null);
        
        String themeId = req.getParameter(ThemeList.PARAM_THEME_ID);
        if(themeId != null){
            this.setThemeId(themeId);
            req.getSession().setAttribute(ThemeList.PARAM_THEME_ID, themeId);
        }else{
            themeId = (String)req.getSession().getAttribute(ThemeList.PARAM_THEME_ID);
            this.setThemeId(themeId);
        }
    }

    /**
     * Initializes the message object.
     */
    @Override
    protected void initMessages() {
        // add specific dialog resource bundle
        addMessages(Messages.get().getBundleName());
        super.initMessages();
    }
    

    /**
     * Creates the columns and add them to the given list metadata object. This method will be just executed once, 
     * the first time the constructor is called.
     * @param clm The list metadata, which the columns have to be added to.
     */
    @Override
    protected void setColumns(CmsListMetadata clm) {
        CmsListColumnDefinition editCol;
        CmsListColumnDefinition deleteCol;
        CmsListColumnDefinition resTypeCol;
        CmsListColumnDefinition containerTypeCol;
        CmsListColumnDefinition jspCol;
        
        CmsListDirectAction     editAction;
        CmsListDirectAction     deleteAction;

        I_CmsMessageBundle      msg          = Messages.get();
        
        try{
            editCol = new CmsListColumnDefinition(ThemeFormatterList.LIST_COLUMN_EDIT);
            editCol.setName(msg.container(Messages.GUI_FORMATTERS_EDIT_COL_NAME_0));
            editCol.setHelpText(msg.container(Messages.GUI_FORMATTERS_EDIT_COL_HELP_0));
            editCol.setWidth(msg.getBundle().getString(Messages.GUI_FORMATTERS_EDIT_COL_WIDTH_0));
            editCol.setAlign(this.getColumnAlign(Messages.GUI_FORMATTERS_EDIT_COL_ALIGN_0));
            editCol.setSorteable(this.getSorteable(Messages.GUI_FORMATTERS_EDIT_COL_SORTEABLE_0));
            
            editAction = new CmsListDirectAction(ThemeFormatterList.LIST_ACTION_EDIT);
            editAction.setName(msg.container(Messages.GUI_FORMATTERS_EDIT_ACTION_NAME_0));
            editAction.setHelpText(msg.container(Messages.GUI_FORMATTERS_EDIT_ACTION_HELP_0));
            editAction.setConfirmationMessage(null);
            editAction.setEnabled(true);
            editAction.setIconPath(ThemeFormatterList.ICON_EDIT);
            editCol.addDirectAction(editAction);
            clm.addColumn(editCol);
            
            
            deleteCol = new CmsListColumnDefinition(ThemeFormatterList.LIST_COLUMN_DELETE);
            deleteCol.setName(msg.container(Messages.GUI_FORMATTERS_DELETE_COL_NAME_0));
            deleteCol.setHelpText(msg.container(Messages.GUI_FORMATTERS_DELETE_COL_HELP_0));
            deleteCol.setWidth(msg.getBundle().getString(Messages.GUI_FORMATTERS_DELETE_COL_WIDTH_0));
            deleteCol.setAlign(this.getColumnAlign(Messages.GUI_FORMATTERS_DELETE_COL_ALIGN_0));
            deleteCol.setSorteable(this.getSorteable(Messages.GUI_FORMATTERS_DELETE_COL_SORTEABLE_0));
            
            deleteAction = new CmsListDirectAction(ThemeFormatterList.LIST_ACTION_DELETE);
            deleteAction.setName(msg.container(Messages.GUI_FORMATTERS_DELETE_ACTION_NAME_0));
            deleteAction.setHelpText(msg.container(Messages.GUI_FORMATTERS_DELETE_ACTION_HELP_0));
            deleteAction.setConfirmationMessage(msg.container(Messages.GUI_FORMATTERS_DELETE_ACTION_MSG_0));
            deleteAction.setIconPath(A_CmsListDialog.ICON_DELETE);
            deleteCol.addDirectAction(deleteAction);
            clm.addColumn(deleteCol);
            
            resTypeCol = new CmsListColumnDefinition(ThemeFormatterList.LIST_COLUMN_RESOURCETYPE);
            resTypeCol.setName(msg.container(Messages.GUI_FORMATTERS_RESOURCETYPE_COL_NAME_0));
            resTypeCol.setHelpText(msg.container(Messages.GUI_FORMATTERS_RESOURCETYPE_COL_HELP_0));
            resTypeCol.setWidth(msg.getBundle().getString(Messages.GUI_FORMATTERS_RESOURCETYPE_COL_WIDTH_0));
            resTypeCol.setAlign(this.getColumnAlign(Messages.GUI_FORMATTERS_RESOURCETYPE_COL_ALIGN_0));
            resTypeCol.setSorteable(this.getSorteable(Messages.GUI_FORMATTERS_RESOURCETYPE_COL_SORTEABLE_0));
            clm.addColumn(resTypeCol);

            containerTypeCol = new CmsListColumnDefinition(ThemeFormatterList.LIST_COLUMN_CONTAINERTYPE);
            containerTypeCol.setName(msg.container(Messages.GUI_FORMATTERS_CONTAINERTYPE_COL_NAME_0));
            containerTypeCol.setHelpText(msg.container(Messages.GUI_FORMATTERS_CONTAINERTYPE_COL_HELP_0));
            containerTypeCol.setWidth(msg.getBundle().getString(Messages.GUI_FORMATTERS_CONTAINERTYPE_COL_WIDTH_0));
            containerTypeCol.setAlign(this.getColumnAlign(Messages.GUI_FORMATTERS_CONTAINERTYPE_COL_ALIGN_0));
            containerTypeCol.setSorteable(this.getSorteable(Messages.GUI_FORMATTERS_CONTAINERTYPE_COL_SORTEABLE_0));
            clm.addColumn(containerTypeCol);
            
            jspCol = new CmsListColumnDefinition(ThemeFormatterList.LIST_COLUMN_JSP);
            jspCol.setName(msg.container(Messages.GUI_FORMATTERS_JSP_COL_NAME_0));
            jspCol.setHelpText(msg.container(Messages.GUI_FORMATTERS_JSP_COL_HELP_0));
            jspCol.setWidth(msg.getBundle().getString(Messages.GUI_FORMATTERS_JSP_COL_WIDTH_0));
            jspCol.setAlign(this.getColumnAlign(Messages.GUI_FORMATTERS_JSP_COL_ALIGN_0));
            jspCol.setSorteable(this.getSorteable(Messages.GUI_FORMATTERS_JSP_COL_SORTEABLE_0));
            clm.addColumn(jspCol); 
        }catch(CmsException cmsEx){
            throw new RuntimeException(cmsEx);
        }
    }

    /**
     * Handles every defined list multi action, by comparing getParamListAction() with the id of the action to execute.
     * @throws IOException In case of errors when including a required sub-element. 
     * @throws ServletException In case of errors when including a required sub-element.
     * @throws CmsRuntimeException To signal that an action is not supported.
     */
    @Override
    public void executeListMultiActions() throws IOException, ServletException, CmsRuntimeException {
        String                  resType;
        String                  containerType;
        CmsListItem             currentItem;
        List<CmsListItem>       items            = this.getSelectedItems();
        Iterator<CmsListItem>   itemsIt;
        I_ThemeConfigController configController;
        ThemeConfig             themeConfig;

        if(this.getParamListAction().equals(ThemeFormatterList.LIST_MACTION_DELETE)){
            if((items != null) && (items.size() > 0)){
                try{
                    configController = ThemeFactory.getConfigController(getPageContext(), getRequest(), getResponse());
                    themeConfig = configController.loadThemeConfig(getThemeId());
                    for(itemsIt = items.iterator(); itemsIt.hasNext();){
                        currentItem = itemsIt.next();
                        resType = String.valueOf(currentItem.get(ThemeFormatterList.LIST_COLUMN_RESOURCETYPE));
                        containerType = String.valueOf(currentItem.get(ThemeFormatterList.LIST_COLUMN_CONTAINERTYPE));
                        themeConfig.removeFormatter(resType, containerType);
                    }
                    configController.saveThemeConfig(themeConfig);
                    getRequest().setAttribute(ThemeList.PARAM_THEME, themeConfig);
                }catch(ThemeConfigException themeConfigEx){
                    throw new CmsRuntimeException(Messages.get().container(Messages.GUI_THEME_FORMATTERS_INIT_0), themeConfigEx);
                }
            }
        }
    }

    /**
     * Handles every defined list single action, by comparing getParamListAction() with the id of the action to execute.
     * @throws IOException In case of errors when including a required sub-element.
     * @throws ServletException In case of errors when including a required sub-element.
     * @throws CmsRuntimeException To signal that an action is not supported
     */
    @Override
    public void executeListSingleActions() throws IOException, ServletException, CmsRuntimeException {
        String                  resType;
        String                  containerType;
        String                  formatterKey;
        CmsListItem             selectedItem     = getSelectedItem();
        Map                     params           = new HashMap();
        I_ThemeConfigController configController;
        ThemeConfig             themeConfig;
        
        resType = String.valueOf(selectedItem.get(ThemeFormatterList.LIST_COLUMN_RESOURCETYPE));
        containerType = String.valueOf(selectedItem.get(ThemeFormatterList.LIST_COLUMN_CONTAINERTYPE));
        formatterKey = ThemeFormatter.getKey(resType, containerType);
        params.put(ThemeFormatterList.PARAM_FORMATTER_KEY, formatterKey);
        
        if (getParamListAction().equals(ThemeFormatterList.LIST_ACTION_EDIT)) {
            params.put(A_CmsListDialog.PARAM_ACTION, A_CmsListDialog.DIALOG_INITIAL);
            getRequest().getSession().removeAttribute(ThemeFormatterDialog.PARAM_FORMATTER);
            getToolManager().jspForwardTool(this, "/skinndriva/modify/formatters/details", params);
        }else if (getParamListAction().equals(ThemeFormatterList.LIST_ACTION_DELETE)) {
            try{
                configController = ThemeFactory.getConfigController(getPageContext(), getRequest(), getResponse());
                themeConfig = configController.loadThemeConfig(getThemeId());
                themeConfig.removeFormatter(resType, containerType);
                configController.saveThemeConfig(themeConfig);
                getRequest().setAttribute(ThemeList.PARAM_THEME, themeConfig);
            }catch(ThemeConfigException themeConfigEx){
                throw new CmsRuntimeException(Messages.get().container(Messages.GUI_THEME_FORMATTERS_DELETE_0), themeConfigEx);
            }
        }
    }

    /**
     * Lazy initialization for detail data. Fills the given detail column for every list item in 
     * <code>CmsHtmlList.getContent()</code>. Does not throw any kind of exception.
     * @param detailId The id of the detail to initialize.
     */
    @Override
    protected void fillDetails(String detailId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Generates a list with the list items to be displayed.
     * @return The list of generated items.
     * @throws CmsException To signal that something went wrong.
     */
    @Override
    protected List<CmsListItem> getListItems() throws CmsException {
        List<CmsListItem>       result           = new ArrayList<CmsListItem>(); 
        I_ThemeConfigController configController;
        ThemeConfig             themeConfig;
        ThemeFormatter[]        formatters;
        int                     loopCount;
        CmsListItem             currentItem;
        String                  groupStr;
        
        
        try{
            themeConfig = (ThemeConfig)getRequest().getAttribute(ThemeList.PARAM_THEME);
            if(themeConfig == null){
                configController = ThemeFactory.getConfigController(getPageContext(), getRequest(), getResponse());
                themeConfig = configController.loadThemeConfig(getThemeId());
            }
            formatters = themeConfig.listFormatters();
            for(loopCount = 0; loopCount < formatters.length; loopCount ++){
                currentItem = getList().newItem(formatters[loopCount].getKey());
                
                currentItem.set(ThemeFormatterList.LIST_COLUMN_RESOURCETYPE, formatters[loopCount].getResourceType());
                currentItem.set(ThemeFormatterList.LIST_COLUMN_CONTAINERTYPE, formatters[loopCount].getContainerType());
                currentItem.set(ThemeFormatterList.LIST_COLUMN_JSP, formatters[loopCount].getJsp());
                
                result.add(currentItem);
            }
        }catch(ThemeConfigException themeConfigEx){
            throw new CmsException(Messages.get().container(Messages.GUI_THEME_SCRIPTS_INIT_0), themeConfigEx);
        }
        return result;
    }

    /**
     * Adds the independent actions to the given list metadata object. This method will be just executed once, the 
     * first time the constructor is called.
     * @param clm The list metadata, which the independent actions have to be added to.
     */
    @Override
    protected void setIndependentActions(CmsListMetadata clm) {
        // makes the list searchable
        CmsListSearchAction searchAction = new CmsListSearchAction(clm.getColumnDefinition(ThemeFormatterList.LIST_COLUMN_RESOURCETYPE));
        searchAction.addColumn(clm.getColumnDefinition(ThemeFormatterList.LIST_COLUMN_CONTAINERTYPE));
        clm.setSearchAction(searchAction);
    }

    @Override
    protected void setMultiActions(CmsListMetadata clm) {
        I_CmsMessageBundle   msg               = Messages.get();
        CmsListMultiAction   deleteMultiAction;
        
        // add delete multi actions
        deleteMultiAction = new CmsListMultiAction(ThemeFormatterList.LIST_MACTION_DELETE);
        deleteMultiAction.setName(msg.container(Messages.GUI_FORMATTERS_DELETE_MULTI_ACTION_NAME_0));
        deleteMultiAction.setHelpText(msg.container(Messages.GUI_FORMATTERS_DELETE_MULTI_ACTION_HELP_0));
        deleteMultiAction.setConfirmationMessage(msg.container(Messages.GUI_FORMATTERS_DELETE_MULTI_ACTION_MSG_0));
        deleteMultiAction.setIconPath(A_CmsListDialog.ICON_MULTI_DELETE);

        clm.addMultiAction(deleteMultiAction);
    }
    

    /**
     * Get the value of the theme Id
     * @return the value of the theme id
     */
    public final String getThemeId() {
        return m_themeId;
    }

    /**
     * Set the value of the theme id
     * @param themeId new value of the theme id
     */
    public final void setThemeId(String themeId) {
        this.m_themeId = themeId;
    }
}
