/*
 *  Document : ThemeList.java
 *  Created on Sa, Okt 08 2011, 16:27:50
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

import  java.io.IOException;
import  java.util.List;
import  java.util.ArrayList;
import  java.util.Iterator;
import  java.util.Map;
import  java.util.HashMap;

import  javax.servlet.ServletException;
import  javax.servlet.jsp.PageContext;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;

import  org.opencms.main.CmsException;
import  org.opencms.main.CmsRuntimeException;
import  org.opencms.workplace.list.A_CmsListDialog;
import  org.opencms.workplace.list.CmsListItem;
import  org.opencms.workplace.list.CmsListMetadata;
import  org.opencms.workplace.list.CmsListOrderEnum;
import  org.opencms.workplace.list.CmsListColumnDefinition;
import  org.opencms.workplace.list.CmsListDirectAction;
import  org.opencms.workplace.list.CmsListMultiAction;
import  org.opencms.workplace.list.CmsListSearchAction;
import  org.opencms.i18n.I_CmsMessageBundle;

import  net.componio.opencms.modules.eight.skinndriva.rd.Messages;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.controller.ThemeFactory;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.controller.I_ThemeConfigController;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeConfig;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigException;

/**
 * Provides the dialog with the list widget with the themes, when editing the theme configuration.
 * @author Robert Diawara
 */
public class ThemeList extends ThemeEngineListDialog{
    /** Constant for the list id. */
    public static final String LIST_ID                  = "opencmecthemes";
    
    /** Column name id constant for the column &quot;name&quot;. */
    public static final String LIST_COLUMN_NAME         = "cn";
    /** Column name id constant for the column &quot;type&quot;. */
    public static final String LIST_COLUMN_TYPE         = "ct";
    /** Column name id constant for the column &quot;path&quot;. */
    public static final String LIST_COLUMN_PATH         = "cp";
    /** Column name id constant for the column &quot;edit&quot;. */
    public static final String LIST_COLUMN_EDIT         = "ce";
    /** Column name id constant for the column &quot;delete&quot;. */
    public static final String LIST_COLUMN_DELETE       = "cd";
    
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
    /** Constant for URL parameter. */
    public static final String PARAM_THEME_ID           = "theme_id";
    /** Constant for URL parameter and request attribute. */
    public static final String PARAM_THEME              = "theme";
    /** Constant for URL parameter. */
    public static final String PARAM_SCRIPT_URI         = "script_uri";
    /** Constant for URL parameter. */
    public static final String PARAM_SCRIPT_REFERENCE   = "script_reference";
    /** Constant for URL parameter. */
    public static final String PARAM_SCRIPT             = "script";
    
    /**
     * Public constructor. Creates a new ThemeList instance.
     * @param context  The request context to be assigned the new instance. Also used to initialize a new JSP action element
     * in the superclass.
     * @param req The servlet The servlet request to be assigned the new instance. Also used to initialize a new JSP action element
     * in the sulerclass.
     * @param res  The servlet response to be assigned the new instance. Also used to initialize a new JSP action element
     * in the superclass.
     */
    public ThemeList(PageContext context, HttpServletRequest req, HttpServletResponse res) {
        super(context, req, res, LIST_ID, Messages.get().container(Messages.GUI_THEME_LIST_DIALOG_GROUP_NAME_0), 
                LIST_COLUMN_NAME, CmsListOrderEnum.ORDER_ASCENDING, null);
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
        CmsListColumnDefinition nameCol;
        CmsListColumnDefinition typeCol;
        CmsListColumnDefinition pathCol;
        CmsListColumnDefinition editCol;
        CmsListColumnDefinition deleteCol;
        
        CmsListDirectAction     editAction;
        CmsListDirectAction     deleteAction;

        I_CmsMessageBundle      msg          = Messages.get();
        
        try{
            editCol = new CmsListColumnDefinition(ThemeList.LIST_COLUMN_EDIT);
            editCol.setName(msg.container(Messages.GUI_THEME_EDIT_COL_NAME_0));
            editCol.setHelpText(msg.container(Messages.GUI_THEME_EDIT_COL_HELP_0));
            editCol.setWidth(msg.getBundle().getString(Messages.GUI_THEME_EDIT_COL_WIDTH_0));
            editCol.setAlign(this.getColumnAlign(Messages.GUI_THEME_EDIT_COL_ALIGN_0));
            editCol.setSorteable(this.getSorteable(Messages.GUI_THEME_EDIT_COL_SORTEABLE_0));
            
            editAction = new CmsListDirectAction(ThemeList.LIST_ACTION_EDIT);
            editAction.setName(msg.container(Messages.GUI_THEME_EDIT_ACTION_NAME_0));
            editAction.setHelpText(msg.container(Messages.GUI_THEME_EDIT_ACTION_HELP_0));
            editAction.setConfirmationMessage(null);
            editAction.setEnabled(true);
            editAction.setIconPath(ThemeList.ICON_EDIT);
            editCol.addDirectAction(editAction);
            clm.addColumn(editCol);
            
            deleteCol = new CmsListColumnDefinition(ThemeList.LIST_COLUMN_DELETE);
            deleteCol.setName(msg.container(Messages.GUI_THEME_DELETE_COL_NAME_0));
            deleteCol.setHelpText(msg.container(Messages.GUI_THEME_DELETE_COL_HELP_0));
            deleteCol.setWidth(msg.getBundle().getString(Messages.GUI_THEME_DELETE_COL_WIDTH_0));
            deleteCol.setAlign(this.getColumnAlign(Messages.GUI_THEME_DELETE_COL_ALIGN_0));
            deleteCol.setSorteable(this.getSorteable(Messages.GUI_THEME_DELETE_COL_SORTEABLE_0));
            
            deleteAction = new CmsListDirectAction(ThemeList.LIST_ACTION_DELETE);
            deleteAction.setName(msg.container(Messages.GUI_THEME_DELETE_ACTION_NAME_0));
            deleteAction.setHelpText(msg.container(Messages.GUI_THEME_DELETE_ACTION_HELP_0));
            deleteAction.setConfirmationMessage(msg.container(Messages.GUI_THEME_DELETE_ACTION_MSG_0));
            deleteAction.setIconPath(A_CmsListDialog.ICON_DELETE);
            deleteCol.addDirectAction(deleteAction);
            clm.addColumn(deleteCol);
            
            nameCol = new CmsListColumnDefinition(ThemeList.LIST_COLUMN_NAME);
            nameCol.setName(msg.container(Messages.GUI_THEME_NAME_COL_NAME_0));
            nameCol.setHelpText(msg.container(Messages.GUI_THEME_NAME_COL_HELP_0));
            nameCol.setWidth(msg.getBundle().getString(Messages.GUI_THEME_NAME_COL_WIDTH_0));
            nameCol.setAlign(this.getColumnAlign(Messages.GUI_THEME_NAME_COL_ALIGN_0));
            nameCol.setSorteable(this.getSorteable(Messages.GUI_THEME_NAME_COL_SORTEABLE_0));
            clm.addColumn(nameCol);

            typeCol = new CmsListColumnDefinition(ThemeList.LIST_COLUMN_TYPE);
            typeCol.setName(msg.container(Messages.GUI_THEME_TYPE_COL_NAME_0));
            typeCol.setHelpText(msg.container(Messages.GUI_THEME_TYPE_COL_HELP_0));
            typeCol.setWidth(msg.getBundle().getString(Messages.GUI_THEME_TYPE_COL_WIDTH_0));
            typeCol.setAlign(this.getColumnAlign(Messages.GUI_THEME_TYPE_COL_ALIGN_0));
            typeCol.setSorteable(this.getSorteable(Messages.GUI_THEME_TYPE_COL_SORTEABLE_0));
            clm.addColumn(typeCol);

            
            pathCol = new CmsListColumnDefinition(ThemeList.LIST_COLUMN_PATH);
            pathCol.setName(msg.container(Messages.GUI_THEME_PATH_COL_NAME_0));
            pathCol.setHelpText(msg.container(Messages.GUI_THEME_PATH_COL_HELP_0));
            pathCol.setWidth(msg.getBundle().getString(Messages.GUI_THEME_PATH_COL_WIDTH_0));
            pathCol.setAlign(this.getColumnAlign(Messages.GUI_THEME_PATH_COL_ALIGN_0));
            pathCol.setSorteable(this.getSorteable(Messages.GUI_THEME_PATH_COL_SORTEABLE_0));
            clm.addColumn(pathCol);
            
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
        String                  theme;
        List<CmsListItem>       items            = this.getSelectedItems();
        Iterator<CmsListItem>   itemsIt;
        I_ThemeConfigController configController;

        if(this.getParamListAction().equals(ThemeList.LIST_MACTION_DELETE)){
            if((items != null) && (items.size() > 0)){
                try{
                    configController = ThemeFactory.getConfigController(getPageContext(), getRequest(), getResponse());
                    for(itemsIt = items.iterator(); itemsIt.hasNext();){
                        theme = itemsIt.next().getId();
                        configController.deleteThemeConfig(theme);
                    }
                }catch(ThemeConfigException themeConfigEx){
                    throw new CmsRuntimeException(Messages.get().container(Messages.GUI_THEME_SCRIPTS_INIT_0), themeConfigEx);
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
        CmsListItem             itm              = getSelectedItem();
        String                  theme            = itm.getId();
        Map                     params           = new HashMap();
        I_ThemeConfigController configController;
        
        params.put(ThemeList.PARAM_THEME_ID, theme);
        if(getParamListAction().equals(ThemeList.LIST_ACTION_EDIT)) {
            params.put(A_CmsListDialog.PARAM_ACTION, A_CmsListDialog.DIALOG_INITIAL);
            getToolManager().jspForwardTool(this, "/skinndriva/modify", params);
        }else if(getParamListAction().equals(ThemeList.LIST_ACTION_DELETE)){
            try{
                configController = ThemeFactory.getConfigController(getPageContext(), getRequest(), getResponse());
                configController.deleteThemeConfig(theme);
            }catch(ThemeConfigException themeConfigEx){
                throw new ServletException(themeConfigEx);
            }catch(Exception cmsEx){
                throw new ServletException(cmsEx);
            }
        }
    }

    /**
     * Lazy initialization for detail data. Fills the given detail column for every list item in 
     * <code>CmsHtmlList.getContent()</code>. Does not throw any kind of exception.
     * @param detailId The id of the detail to initialize.
     */
    @Override
    protected void fillDetails(String detailId) {}

    /**
     * Generates a list with the list items to be displayed.
     * @return The list of generated items.
     * @throws CmsException To signal that something went wrong.
     */
    @Override
    protected List<CmsListItem> getListItems() throws CmsException {
        List<CmsListItem>       result           = new ArrayList<CmsListItem>();
        I_ThemeConfigController configController;
        List<ThemeConfig>       themeConfigs;
        Iterator<ThemeConfig>   themeConfigsIt;
        ThemeConfig             currentConfig;
        CmsListItem             currentItem;
        
        try{
            configController = ThemeFactory.getConfigController(getPageContext(), getRequest(), getResponse());
            themeConfigs = configController.listThemeConfigs();
            for(themeConfigsIt = themeConfigs.iterator(); themeConfigsIt.hasNext();){
                currentConfig = themeConfigsIt.next();
                currentItem = getList().newItem(currentConfig.getName());
                
                currentItem.set(ThemeList.LIST_COLUMN_NAME, currentConfig.getName());
                currentItem.set(ThemeList.LIST_COLUMN_TYPE, Messages.get().getBundle().getString("fileicon.theme_config"));
                currentItem.set(ThemeList.LIST_COLUMN_PATH, currentConfig.getVfsPath());
                
                result.add(currentItem);
            }
        }catch(ThemeConfigException themeConfigEx){
            throw new CmsException(Messages.get().container(Messages.GUI_THEME_CONTROLLER_INIT_0), themeConfigEx);
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
        CmsListSearchAction searchAction = new CmsListSearchAction(clm.getColumnDefinition(ThemeList.LIST_COLUMN_NAME));
        searchAction.addColumn(clm.getColumnDefinition(ThemeList.LIST_COLUMN_PATH));
        clm.setSearchAction(searchAction);
    }

    /**
     * Adds the multi actions to the given list metadata object. This method will be just executed once, the first 
     * time the constructor is called.
     * @param clm The list metadata, which the multi actions have to be added to.
     */
    @Override
    protected void setMultiActions(CmsListMetadata clm) {
        I_CmsMessageBundle  msg                = Messages.get();
        CmsListMultiAction  deleteMultiAction;
        //CmsListMultiAction  publishMultiAction;
        
        // add delete multi action
        deleteMultiAction = new CmsListMultiAction(ThemeList.LIST_MACTION_DELETE);
        deleteMultiAction.setName(msg.container(Messages.GUI_THEME_DELETE_MULTI_ACTION_NAME_0));
        deleteMultiAction.setHelpText(msg.container(Messages.GUI_THEME_DELETE_MULTI_ACTION_HELP_0));
        deleteMultiAction.setConfirmationMessage(msg.container(Messages.GUI_THEME_DELETE_MULTI_ACTION_MSG_0));
        deleteMultiAction.setIconPath(A_CmsListDialog.ICON_MULTI_DELETE);
        /*
        publishMultiAction = new CmsListMultiAction(ThemeList.LIST_MACTION_PUBLISH);
        publishMultiAction.setName(msg.container(Messages.GUI_THEME_PUBLISH_MULTI_ACTION_NAME_0));
        publishMultiAction.setHelpText(msg.container(Messages.GUI_THEME_PUBLISH_MULTI_ACTION_HELP_0));
        publishMultiAction.setConfirmationMessage(msg.container(Messages.GUI_THEME_PUBLISH_MULTI_ACTION_MSG_0));
        publishMultiAction.setIconPath(A_CmsListDialog.ICON_MULTI_ACTIVATE);
        */
        clm.addMultiAction(deleteMultiAction);
        //clm.addMultiAction(publishMultiAction);
    }
}
