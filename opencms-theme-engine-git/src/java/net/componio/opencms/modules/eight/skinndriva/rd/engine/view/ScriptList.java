/*
 *  Document : ScriptList.java
 *  Created on Mi, Nov 09 2011, 21:03:53
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
import  java.util.Map;
import  java.util.HashMap;
import  java.util.Iterator;

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
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigException;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeConfig;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ScriptReference;

/**
 * Provides the dialog with the list widget with the scripts assigned to a theme.
 * @author Robert Diawara
 */
public class ScriptList extends ThemeEngineListDialog{
    /** Constant for the list id. */
    public static final String LIST_ID                  = "theme_scripts";
    
    /** Column name id constant for the column &quot;uri&quot;. */
    public static final String LIST_COLUMN_URI          = "uri";
    /** Column name id constant for the column &quot;group&quot;. */
    public static final String LIST_COLUMN_GROUP        = "group";
    /** Column name id constant for the column &quot;visibility&quot;. */
    public static final String LIST_COLUMN_VISIBILITY   = "vis";
    /** Column name id constant for the column &quot;edit&quot;. */
    public static final String LIST_COLUMN_EDIT         = "ce";
    /** Column name id constant for the column &quot;delete&quot;. */
    public static final String LIST_COLUMN_DELETE       = "cd";
    
    /** List column action id constant for the action &quot;edit&quot;. */
    public static final String LIST_ACTION_EDIT         = "ae";
    /** List column action id constant for the action &quot;delete&quot;. */
    public static final String LIST_ACTION_DELETE       = "ad";
    /** List column action id constant for the multiple action &quot;delete&quot;. */
    public static final String LIST_MACTION_DELETE      = "mad";
     
    /** Path constant for the script edit icon. */
    public static final String ICON_SCRIPT_EDIT         = "tools/skinndriva/icons/small/script_edit.png";
    
    
    /** Holds the value of the property m_themeId. */
    private String m_themeId;


    /**
     * Public constructor.
     * @param context  The request context to be assigned the new instance. Also used to initialize a new JSP action element
     * in the superclass.
     * @param req The servlet request to be assigned the new instance. Also used to initialize a new JSP action element
     * in the sulerclass.
     * @param res  The servlet response to be assigned the new instance. Also used to initialize a new JSP action element
     * in the superclass.
     */
    public ScriptList(PageContext context, HttpServletRequest req, HttpServletResponse res) {
        super(context, req, res, LIST_ID, Messages.get().container(Messages.GUI_SCRIPT_LIST_DIALOG_GROUP_NAME_0), 
                LIST_COLUMN_URI, CmsListOrderEnum.ORDER_ASCENDING, null);
        
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
    
    /**
     * Handles every defined list multi action, by comparing getParamListAction() with the id of the action to execute.
     * @throws IOException In case of errors when including a required sub-element. 
     * @throws ServletException In case of errors when including a required sub-element.
     * @throws CmsRuntimeException To signal that an action is not supported.
     */
    @Override
    public void executeListMultiActions() throws IOException, ServletException, CmsRuntimeException {
        String                  script;
        List<CmsListItem>       items            = this.getSelectedItems();
        Iterator<CmsListItem>   itemsIt;
        I_ThemeConfigController configController;
        ThemeConfig             themeConfig;
        
        if(this.getParamListAction().equals(ScriptList.LIST_MACTION_DELETE)){
            if((items != null) && (items.size() > 0)){
                try{
                    configController = ThemeFactory.getConfigController(getPageContext(), getRequest(), getResponse());
                    themeConfig = configController.loadThemeConfig(getThemeId());
                    for(itemsIt = items.iterator(); itemsIt.hasNext();){
                        script = itemsIt.next().getId();
                        themeConfig.removeScriptReference(script);
                    }
                    configController.saveThemeConfig(themeConfig);
                    getRequest().setAttribute(ThemeList.PARAM_THEME, themeConfig);
                }catch(ThemeConfigException themeConfigEx){
                    throw new CmsRuntimeException(Messages.get().container(Messages.GUI_THEME_SCRIPTS_DELETE_0), themeConfigEx);
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
        String                  script           = getSelectedItem().getId();
        Map                     params           = new HashMap();
        I_ThemeConfigController configController;
        ThemeConfig             themeConfig;
        
        params.put(ThemeList.PARAM_SCRIPT_URI, script);
        if (getParamListAction().equals(ThemeList.LIST_ACTION_EDIT)) {
            params.put(A_CmsListDialog.PARAM_ACTION, A_CmsListDialog.DIALOG_INITIAL);
            getToolManager().jspForwardTool(this, "/skinndriva/modify/scripts", params);
        }else if (getParamListAction().equals(ThemeList.LIST_ACTION_DELETE)) {
            try{
                configController = ThemeFactory.getConfigController(getPageContext(), getRequest(), getResponse());
                themeConfig = configController.loadThemeConfig(getThemeId());
                themeConfig.removeScriptReference(script);
                configController.saveThemeConfig(themeConfig);
                getRequest().setAttribute(ThemeList.PARAM_THEME, themeConfig);
            }catch(ThemeConfigException themeConfigEx){
                throw new CmsRuntimeException(Messages.get().container(Messages.GUI_THEME_SCRIPTS_DELETE_0), themeConfigEx);
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
        ThemeConfig             themeConfig;
        ScriptReference[]       scripts;
        int                     loopCount;
        CmsListItem             currentItem;
        int                     visibility;
        String                  visibilityStr;
        String                  groupStr;
        
        try{
            themeConfig = (ThemeConfig)getRequest().getAttribute(ThemeList.PARAM_THEME);
            if(themeConfig == null){
                configController = ThemeFactory.getConfigController(getPageContext(), getRequest(), getResponse());
                themeConfig = configController.loadThemeConfig(getThemeId());
            }
            scripts = themeConfig.listScriptReferences();
            for(loopCount = 0; loopCount < scripts.length; loopCount ++){
                currentItem = getList().newItem(scripts[loopCount].getScriptURI());
                
                // Setup the string for the group;
                groupStr = scripts[loopCount].getGroup();
                
                // Format the string for the visibility.
                visibility = scripts[loopCount].getScriptVisibility();
                switch(visibility){
                    case ScriptReference.VISIBILITY_OFFLINE :
                        visibilityStr = "offline";
                        break;
                    case ScriptReference.VISIBILITY_ONLINE :
                        visibilityStr = "online";
                        break;
                    case ScriptReference.VISIBILITY_ALL :
                        visibilityStr = "all";
                        break;
                    case ScriptReference.VISIBILITY_NONE :
                        visibilityStr = "none";
                        break;
                    default:
                        visibilityStr = "none";
                }
                
                currentItem.set(ScriptList.LIST_COLUMN_URI, scripts[loopCount].getFullScriptURI().replace("&", "&amp;"));
                currentItem.set(ScriptList.LIST_COLUMN_GROUP, groupStr != null ? groupStr : "-");
                currentItem.set(ScriptList.LIST_COLUMN_VISIBILITY, visibilityStr);
                
                result.add(currentItem);
            }
        }catch(ThemeConfigException themeConfigEx){
            throw new CmsException(Messages.get().container(Messages.GUI_THEME_SCRIPTS_INIT_0), themeConfigEx);
        }
        return result;
    }

    /**
     * Creates the columns and add them to the given list metadata object. This method will be just executed once, 
     * the first time the constructor is called.
     * @param clm The list metadata, which the columns have to be added to.
     */
    @Override
    protected void setColumns(CmsListMetadata clm) {
        CmsListColumnDefinition uriCol;
        CmsListColumnDefinition groupCol;
        CmsListColumnDefinition visibilityCol;
        CmsListColumnDefinition editCol;
        CmsListColumnDefinition deleteCol;
        CmsListDirectAction     editAction;
        CmsListDirectAction     deleteAction;
        I_CmsMessageBundle      msg          = Messages.get();
        
        try{
            editCol = new CmsListColumnDefinition(ScriptList.LIST_COLUMN_EDIT);
            editCol.setName(msg.container(Messages.GUI_SCRIPT_EDIT_COL_NAME_0));
            editCol.setHelpText(msg.container(Messages.GUI_SCRIPT_EDIT_COL_HELP_0));
            editCol.setWidth(msg.getBundle().getString(Messages.GUI_SCRIPT_EDIT_COL_WIDTH_0));
            editCol.setAlign(this.getColumnAlign(Messages.GUI_SCRIPT_EDIT_COL_ALIGN_0));
            editCol.setSorteable(this.getSorteable(Messages.GUI_SCRIPT_EDIT_COL_SORTEABLE_0));
            
            editAction = new CmsListDirectAction(ScriptList.LIST_ACTION_EDIT);
            editAction.setName(msg.container(Messages.GUI_SCRIPT_EDIT_ACTION_NAME_0));
            editAction.setHelpText(msg.container(Messages.GUI_SCRIPT_EDIT_ACTION_HELP_0));
            editAction.setConfirmationMessage(null);
            editAction.setEnabled(true);
            editAction.setIconPath(ScriptList.ICON_SCRIPT_EDIT);
            editCol.addDirectAction(editAction);
            clm.addColumn(editCol);
            
            deleteCol = new CmsListColumnDefinition(ScriptList.LIST_COLUMN_DELETE);
            deleteCol.setName(msg.container(Messages.GUI_SCRIPT_DELETE_COL_NAME_0));
            deleteCol.setHelpText(msg.container(Messages.GUI_SCRIPT_DELETE_COL_HELP_0));
            deleteCol.setWidth(msg.getBundle().getString(Messages.GUI_SCRIPT_DELETE_COL_WIDTH_0));
            deleteCol.setAlign(this.getColumnAlign(Messages.GUI_SCRIPT_DELETE_COL_ALIGN_0));
            deleteCol.setSorteable(this.getSorteable(Messages.GUI_SCRIPT_DELETE_COL_SORTEABLE_0));
            
            deleteAction = new CmsListDirectAction(ThemeList.LIST_ACTION_DELETE);
            deleteAction.setName(msg.container(Messages.GUI_SCRIPT_DELETE_ACTION_NAME_0));
            deleteAction.setHelpText(msg.container(Messages.GUI_SCRIPT_DELETE_ACTION_HELP_0));
            deleteAction.setConfirmationMessage(msg.container(Messages.GUI_SCRIPT_DELETE_ACTION_MSG_0));
            deleteAction.setEnabled(true);
            deleteAction.setIconPath(A_CmsListDialog.ICON_DELETE);
            deleteCol.addDirectAction(deleteAction);
            clm.addColumn(deleteCol);
            
            uriCol = new CmsListColumnDefinition(ScriptList.LIST_COLUMN_URI);
            uriCol.setName(msg.container(Messages.GUI_SCRIPT_URI_COL_NAME_0));
            uriCol.setHelpText(msg.container(Messages.GUI_SCRIPT_URI_COL_HELP_0));
            uriCol.setWidth(msg.getBundle().getString(Messages.GUI_SCRIPT_URI_COL_WIDTH_0));
            uriCol.setAlign(this.getColumnAlign(Messages.GUI_SCRIPT_URI_COL_ALIGN_0));
            uriCol.setSorteable(this.getSorteable(Messages.GUI_SCRIPT_URI_COL_SORTEABLE_0));
            clm.addColumn(uriCol);

            groupCol = new CmsListColumnDefinition(ScriptList.LIST_COLUMN_GROUP);
            groupCol.setName(msg.container(Messages.GUI_SCRIPT_GROUP_COL_NAME_0));
            groupCol.setHelpText(msg.container(Messages.GUI_SCRIPT_GROUP_COL_HELP_0));
            groupCol.setWidth(msg.getBundle().getString(Messages.GUI_SCRIPT_GROUP_COL_WIDTH_0));
            groupCol.setAlign(this.getColumnAlign(Messages.GUI_SCRIPT_GROUP_COL_ALIGN_0));
            groupCol.setSorteable(this.getSorteable(Messages.GUI_SCRIPT_GROUP_COL_SORTEABLE_0));
            clm.addColumn(groupCol);

            visibilityCol = new CmsListColumnDefinition(ScriptList.LIST_COLUMN_VISIBILITY);
            visibilityCol.setName(msg.container(Messages.GUI_SCRIPT_VISIBILITY_COL_NAME_0));
            visibilityCol.setHelpText(msg.container(Messages.GUI_SCRIPT_VISIBILITY_COL_HELP_0));
            visibilityCol.setWidth(msg.getBundle().getString(Messages.GUI_SCRIPT_VISIBILITY_COL_WIDTH_0));
            visibilityCol.setAlign(this.getColumnAlign(Messages.GUI_SCRIPT_VISIBILITY_COL_ALIGN_0));
            visibilityCol.setSorteable(this.getSorteable(Messages.GUI_SCRIPT_VISIBILITY_COL_SORTEABLE_0));
            clm.addColumn(visibilityCol);
        }catch(CmsException cmsEx){
            throw new RuntimeException(cmsEx);
        }
    }

    /**
     * Adds the independent actions to the given list metadata object. This method will be just executed once, the 
     * first time the constructor is called.
     * @param clm The list metadata, which the independent actions have to be added to.
     */
    @Override
    protected void setIndependentActions(CmsListMetadata clm) {
        // makes the list searchable
        CmsListSearchAction searchAction = new CmsListSearchAction(clm.getColumnDefinition(ScriptList.LIST_COLUMN_URI));
        clm.setSearchAction(searchAction);
    }

    /**
     * Adds the multi actions to the given list metadata object. This method will be just executed once, the first 
     * time the constructor is called.
     * @param clm The list metadata, which the multi actions have to be added to.
     */
    @Override
    protected void setMultiActions(CmsListMetadata clm) {
        I_CmsMessageBundle   msg               = Messages.get();
        CmsListMultiAction   deleteMultiAction;
        
        // add delete multi actions
        deleteMultiAction = new CmsListMultiAction(ScriptList.LIST_MACTION_DELETE);
        deleteMultiAction.setName(msg.container(Messages.GUI_SCRIPT_DELETE_MULTI_ACTION_NAME_0));
        deleteMultiAction.setHelpText(msg.container(Messages.GUI_SCRIPT_DELETE_MULTI_ACTION_HELP_0));
        deleteMultiAction.setConfirmationMessage(msg.container(Messages.GUI_SCRIPT_DELETE_MULTI_ACTION_MSG_0));
        deleteMultiAction.setIconPath(A_CmsListDialog.ICON_MULTI_DELETE);

        clm.addMultiAction(deleteMultiAction);
    }
}
