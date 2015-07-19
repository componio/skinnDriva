/*
 *  Document : ThemeDimensionsDialog.java
 *  Created on Di, Nov 15 2011, 21:34:09
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

import  net.componio.opencms.modules.eight.skinndriva.rd.Messages;
import  net.componio.opencms.modules.eight.skinndriva.rd.engine.ThemeConfigException;

import  java.io.IOException;
import  java.util.Arrays;
import  java.util.List;
import  java.util.ArrayList;

import  javax.servlet.ServletException;
import  javax.servlet.jsp.PageContext;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;

import  org.opencms.workplace.CmsWorkplaceSettings;
import  org.opencms.workplace.CmsWidgetDialogParameter;
import  org.opencms.widgets.CmsInputWidget;
import  org.opencms.widgets.CmsVfsFileWidget;
import  org.opencms.widgets.CmsSelectWidget;
import  org.opencms.widgets.CmsSelectWidgetOption;


/**
 * Provides the widget dialog to edit the dimensions for a theme
 * @author Robert Diawara
 */
public class ThemeDimensionsDialog extends ThemeEngineWidgetDialog{
   /** The array defining the pages making up the dialog. */
    public static final String[]      PAGE_ARRAY = new String[]{"dimensions", "margins"};
    /** The key prefix for localized messages. Prevents duplicated keys. */
    public static final String        KEY_PREFIX = "ThemeDimensions";
    /** The list defining the pages making up the dialog. Built from PAGE_ARRAY*/
    public static final List<String>  PAGE_LIST  = Arrays.asList(PAGE_ARRAY);
    
    /** Holds the value of the property &quot;topMargin&quot;. */
    private int m_topMargin;
     /** Holds the value of the property &quot;rightMargin&quot;. */
    private int m_rightMargin;
     /** Holds the value of the property &quot;bottomMargin&quot;. */
    private int m_bottomMargin;
     /** Holds the value of the property &quot;leftMargin&quot;. */
    private int m_leftMargin;
     /** Holds the value of the property &quot;unit&quot;. */
    private String m_unit;
     /** Holds the value of the property &quot;boxSpacing&quot;. */
    private int m_boxSpacing;
     /** Holds the value of the property &quot;leftColumnInnerWidth&quot;. */
    private int m_leftColumnInnerWidth;
     /** Holds the value of the property &quot;centerColumnInnerWidth&quot;. */
    private int m_centerColumnInnerWidth;
     /** Holds the value of the property &quot;rightColumnInnerWidth&quot;. */
    private int m_rightColumnInnerWidth;
     /** Holds the value of the property &quot;headerInnerHeight&quot;. */
    private int m_headerInnerHeight;
     /** Holds the value of the property &quot;footerInnerHeight&quot;. */
    private int m_footerInnerHeight;
     /** Holds the value of the property &quot;toolbarInnerHeight&quot;. */
    private int m_toolbarInnerHeight;
     /** Holds the value of the property &quot;cssFile&quot;. */
    private String m_cssFile;
     /** Holds the value of the property &quot;mediumSizeCssFile&quot;. */
    private String m_mediumSizeCssFile;
     /** Holds the value of the property &quot;smallSizeCssFile&quot;. */
    private String m_smallSizeCssFile;

    /**
     * Public constructor with all JSP variables.
     * @param context The JSP page context.
     * @param req The HTTP servlet request
     * @param resp The HTTP servlet response
     */
    public ThemeDimensionsDialog(PageContext context, HttpServletRequest req, HttpServletResponse resp) {
        super(context, req, resp);
    }

    /**
     * Get the value of the top margin
     * @return the value of the top margin
     */
    public int getTopMargin() {
        return m_topMargin;
    }

    /**
     * Set the value of the top margin
     * @param topMargin new value of the top margin
     */
    public void setTopMargin(int topMargin) {
        this.m_topMargin = topMargin;
    }

    /**
     * Get the value of the right margin
     * @return the value of the right margin
     */
    public int getRightMargin() {
        return m_rightMargin;
    }

    /**
     * Set the value of the right margin
     * @param rightMargin new value of the right margin
     */
    public void setRightMargin(int rightMargin) {
        this.m_rightMargin = rightMargin;
    }

    /**
     * Get the value of the bottom margin
     * @return the value of the bottom margin
     */
    public int getBottomMargin() {
        return m_bottomMargin;
    }

    /**
     * Set the value of the bottom margin
     * @param bottomMargin new value of the bottom margin
     */
    public void setBottomMargin(int bottomMargin) {
        this.m_bottomMargin = bottomMargin;
    }

    /**
     * Get the value of the left margin
     * @return the value of the left margin
     */
    public int getLeftMargin() {
        return m_leftMargin;
    }

    /**
     * Set the value of the left margin
     * @param leftMargin new value of the left margin
     */
    public void setLeftMargin(int leftMargin) {
        this.m_leftMargin = leftMargin;
    }

    /**
     * Get the value of unit
     * @return the value of unit
     */
    public String getUnit() {
        return m_unit;
    }

    /**
     * Set the value of unit
     * @param unit new value of unit
     */
    public void setUnit(String unit) {
        this.m_unit = unit;
    }

    /**
     * Get the value of boxSpacing
     * @return the value of boxSpacing
     */
    public int getBoxSpacing() {
        return m_boxSpacing;
    }

    /**
     * Set the value of boxSpacing
     * @param boxSpaging new value of boxSpacing
     */
    public void setBoxSpacing(int boxSpaging) {
        this.m_boxSpacing = boxSpaging;
    }

    /**
     * Get the value of leftColumnInnerWidth
     * @return the value of leftColumnInnerWidth
     */
    public int getLeftColumnInnerWidth() {
        return m_leftColumnInnerWidth;
    }

    /**
     * Set the value of leftColumnInnerWidth
     * @param leftColumnInnerWidth new value of leftColumnInnerWidth
     */
    public void setLeftColumnInnerWidth(int leftColumnInnerWidth) {
        this.m_leftColumnInnerWidth = leftColumnInnerWidth;
    }

    /**
     * Get the value of centerColumnInnerWidth
     * @return the value of centerColumnInnerWidth
     */
    public int getCenterColumnInnerWidth() {
        return m_centerColumnInnerWidth;
    }

    /**
     * Set the value of centerColumnInnerWidth
     * @param centerColumnInnerWidth new value of centerColumnInnerWidth
     */
    public void setCenterColumnInnerWidth(int centerColumnInnerWidth) {
        this.m_centerColumnInnerWidth = centerColumnInnerWidth;
    }

    /**
     * Get the value of rightColumnInnerWidth
     * @return the value of rightColumnInnerWidth
     */
    public int getRightColumnInnerWidth() {
        return m_rightColumnInnerWidth;
    }

    /**
     * Set the value of rightColumnInnerWidth
     * @param rightColumnInnerWidth new value of rightColumnInnerWidth
     */
    public void setRightColumnInnerWidth(int rightColumnInnerWidth) {
        this.m_rightColumnInnerWidth = rightColumnInnerWidth;
    }

    /**
     * Get the value of headerInnerHeight
     * @return the value of headerInnerHeight
     */
    public int getHeaderInnerHeight() {
        return m_headerInnerHeight;
    }

    /**
     * Set the value of headerInnerHeight
     * @param headerInnerHeight new value of headerInnerHeight
     */
    public void setHeaderInnerHeight(int headerInnerHeight) {
        this.m_headerInnerHeight = headerInnerHeight;
    }

    /**
     * Get the value of footerInnerHeight
     * @return the value of footerInnerHeight
     */
    public int getFooterInnerHeight() {
        return m_footerInnerHeight;
    }

    /**
     * Set the value of footerInnerHeight
     * @param footerInnerHeight new value of footerInnerHeight
     */
    public void setFooterInnerHeight(int footerInnerHeight) {
        this.m_footerInnerHeight = footerInnerHeight;
    }

    /**
     * Get the value of toolbarInnerHeight
     * @return the value of toolbarInnerHeight
     */
    public int getToolbarInnerHeight() {
        return m_toolbarInnerHeight;
    }

    /**
     * Set the value of toolbarInnerHeight
     * @param toolbarInnerHeight new value of toolbarInnerHeight
     */
    public void setToolbarInnerHeight(int toolbarInnerHeight) {
        this.m_toolbarInnerHeight = toolbarInnerHeight;
    }

    /**
     * Get the value of cssFile
     * @return the value of cssFile
     */
    public String getCssFile() {
        return m_cssFile;
    }

    /**
     * Set the value of cssFile
     * @param cssFile new value of cssFile
     */
    public void setCssFile(String cssFile) {
        this.m_cssFile = cssFile;
    }

    /**
     * Get the value of mediumSizeCssFile
     * @return the value of mediumSizeCssFile
     */
    public String getMediumSizeCssFile() {
        return m_mediumSizeCssFile;
    }

    /**
     * Set the value of mediumSizeCssFile
     * @param mediumSizeCssFile new value of mediumSizeCssFile
     */
    public void setMediumSizeCssFile(String mediumSizeCssFile) {
        this.m_mediumSizeCssFile = mediumSizeCssFile;
    }

    /**
     * Get the value of smallSizeCssFile
     * @return the value of smallSizeCssFile
     */
    public String getSmallSizeCssFile() {
        return m_smallSizeCssFile;
    }

    /**
     * Set the value of smallSizeCssFile
     * @param smallSizeCssFile new value of smallSizeCssFile
     */
    public void setSmallSizeCssFile(String smallSizeCssFile) {
        this.m_smallSizeCssFile = smallSizeCssFile;
    }
    
    /**
     * First calls {@link ThemeEngineWidgetDialog#initPropertyValues(CmsWorkplaceSettings, HttpServletRequest) 
     * ThemeEngineWidgetDialog.initPropertyValues()} to load the needed theme configurations from backend, then
     * analyzes the request for workplace parameters and adjusts the workplace settings accordingly.
     * @param settings The workplace settings
     * @param request The current HTTP servlet request
     */
    @Override
    protected void initWorkplaceRequestValues(CmsWorkplaceSettings settings, HttpServletRequest request) {
        // set the dialog message prefix
        setKeyPrefix(KEY_PREFIX);
        
        initPropertyValues(settings, request);
        try{
            m_unit = getThemeConfig().getUnit();
            m_boxSpacing = getDimensions().getBoxSpacing();
            m_leftColumnInnerWidth = getDimensions().getLeftColumnInnerWidth();
            m_centerColumnInnerWidth = getDimensions().getCenterColumnInnerWidth();
            m_rightColumnInnerWidth = getDimensions().getRightColumnInnerWidth();
            m_headerInnerHeight = getDimensions().getHeaderInnerHeight();
            m_footerInnerHeight = getDimensions().getFooterInnerHeight();
            m_toolbarInnerHeight = getDimensions().getToolbarInnerHeight();
            m_cssFile = getDimensions().getCssFile();
            m_mediumSizeCssFile = getDimensions().getMediumSizeCssFile();
            m_smallSizeCssFile = getDimensions().getSmallSizeCssFile();
            if(getDimensions().hasMargins()){
                m_topMargin = getDimensions().getMargins().getTopMargin();
                m_rightMargin = getDimensions().getMargins().getRightMargin();
                m_bottomMargin = getDimensions().getMargins().getBottomMargin();
                m_leftMargin = getDimensions().getMargins().getLeftMargin();
            }else{
                m_topMargin = 10;
                m_rightMargin = 10;
                m_bottomMargin = 10;
                m_leftMargin = 10;
            }
        }catch(ThemeConfigException ex){
            throw new RuntimeException (ex);
        }
        super.initWorkplaceRequestValues(settings, request);
        
        // save the current state of the job
        setDialogObject(getThemeConfig());
    }

    /**
     * Commits all values on the given dialog page
     * @param dialogPage The dialog page
     * @return A list witl all exception which may occur during the execution.
     */
    @Override
    protected List commitWidgetValues(String dialogPage) {
        List            result = new ArrayList();
        String          tmp;

        try{
            if(ThemeDimensionsDialog.PAGE_ARRAY[0].equals(dialogPage)){
                m_unit = getParamValue("unit");
                getThemeConfig().setUnit(m_unit);
                
                tmp = getParamValue("boxSpacing");
                if((tmp != null) && (tmp.trim().length() > 0)){
                   m_boxSpacing = Integer.parseInt(tmp);
                   getDimensions().setBoxSpacing(m_boxSpacing);
                }
                
                tmp = getParamValue("leftColumnInnerWidth");
                if((tmp != null) && (tmp.trim().length() > 0)){
                   m_leftColumnInnerWidth = Integer.parseInt(tmp);
                   getDimensions().setLeftColumnInnerWidth(m_leftColumnInnerWidth);
                }
                
                tmp = getParamValue("centerColumnInnerWidth");
                if((tmp != null) && (tmp.trim().length() > 0)){
                   m_centerColumnInnerWidth = Integer.parseInt(tmp);
                   getDimensions().setCenterColumnInnerWidth(m_centerColumnInnerWidth);
                }
                
                tmp = getParamValue("rightColumnInnerWidth");
                if((tmp != null) && (tmp.trim().length() > 0)){
                   m_rightColumnInnerWidth = Integer.parseInt(tmp);
                   getDimensions().setRightColumnInnerWidth(m_rightColumnInnerWidth);
                }
                
                tmp = getParamValue("headerInnerHeight");
                if((tmp != null) && (tmp.trim().length() > 0)){
                   m_headerInnerHeight = Integer.parseInt(tmp);
                   getDimensions().setHeaderInnerHeight(m_headerInnerHeight);
                }
                
                tmp = getParamValue("footerInnerHeight");
                if((tmp != null) && (tmp.trim().length() > 0)){
                   m_footerInnerHeight = Integer.parseInt(tmp);
                   getDimensions().setFooterInnerHeight(m_footerInnerHeight);
                }
                
                tmp = getParamValue("toolbarInnerHeight");
                if((tmp != null) && (tmp.trim().length() > 0)){
                   m_toolbarInnerHeight = Integer.parseInt(tmp);
                   getDimensions().setToolbarInnerHeight(m_toolbarInnerHeight);
                }
                
                tmp = getParamValue("cssFile");
                if((tmp != null) && (tmp.trim().length() > 0)){
                   m_cssFile = tmp;
                   getDimensions().setCssFile(m_cssFile);
                }
                
                tmp = getParamValue("mediumSizeCssFile");
                if((tmp != null) && (tmp.trim().length() > 0)){
                   m_mediumSizeCssFile = tmp;
                   getDimensions().setMediumSizeCssFile(m_mediumSizeCssFile);
                }
                
                tmp = getParamValue("smallSizeCssFile");
                if((tmp != null) && (tmp.trim().length() > 0)){
                   m_smallSizeCssFile = tmp;
                   getDimensions().setSmallSizeCssFile(m_smallSizeCssFile);
                }
            }else{
                tmp = getParamValue("topMargin");
                if((tmp != null) && (tmp.trim().length() > 0)){
                    m_topMargin = Integer.parseInt(tmp);
                    if(m_topMargin >= 0){
                        getDimensions().getMargins().setTopMargin(m_topMargin);
                    }
                }
                
                tmp = getParamValue("rightMargin");
                if((tmp != null) && (tmp.trim().length() > 0)){
                    m_rightMargin = Integer.parseInt(tmp);
                    if(m_rightMargin >= 0){
                        getDimensions().getMargins().setRightMargin(m_rightMargin);
                    }
                }
                
                tmp = getParamValue("bottomMargin");
                if((tmp != null) && (tmp.trim().length() > 0)){
                    m_bottomMargin = Integer.parseInt(tmp);
                    if(m_bottomMargin >= 0){
                        getDimensions().getMargins().setBottomMargin(m_bottomMargin);
                    }
                }
                
                tmp = getParamValue("leftMargin");
                if((tmp != null) && (tmp.trim().length() > 0)){
                    m_leftMargin = Integer.parseInt(tmp);
                    if(m_leftMargin >= 0){
                        getDimensions().getMargins().setLeftMargin(m_leftMargin);
                    }
                }
            }
        }catch(ThemeConfigException ex){
            result.add(ex);
        }
        
        return result;
    }

    /**
     * Commits the edited object after pressing the "OK" button.
     * @throws IOException In case of errors forwarding to the required result page.
     * @throws ServletException In case of errors forwarding to the required result page.
     */
    @Override
    public void actionCommit() throws IOException, ServletException {
        try{
            getThemeConfig().setUnit(m_unit);
            getDimensions().setBoxSpacing(m_boxSpacing);
            getDimensions().setLeftColumnInnerWidth(m_leftColumnInnerWidth);
            getDimensions().setCenterColumnInnerWidth(m_centerColumnInnerWidth);
            getDimensions().setRightColumnInnerWidth(m_rightColumnInnerWidth);
            getDimensions().setHeaderInnerHeight(m_headerInnerHeight);
            getDimensions().setFooterInnerHeight(m_footerInnerHeight);
            getDimensions().setToolbarInnerHeight(m_toolbarInnerHeight);
            getDimensions().setCssFile(m_cssFile);
            getDimensions().setMediumSizeCssFile(m_mediumSizeCssFile);
            getDimensions().setSmallSizeCssFile(m_smallSizeCssFile);
            if(m_topMargin >= 0){
                getDimensions().getMargins().setTopMargin(m_topMargin);
            }
            if(m_rightMargin >= 0){
                getDimensions().getMargins().setRightMargin(m_rightMargin);
            }
            if(m_bottomMargin >= 0){
                getDimensions().getMargins().setBottomMargin(m_bottomMargin);
            }
            if(m_leftMargin >= 0){
                getDimensions().getMargins().setLeftMargin(m_leftMargin);
            }
            saveData();
            getSettings().setDialogObject(null);
            clear();
        }catch(ThemeConfigException ex){
            throw new ServletException(ex);
        }
   }

    /**
     * Defines the widgets to be used within the dialog.
     */
    @Override
    protected void defineWidgets() {
        addWidget(new CmsWidgetDialogParameter(this,"unit", "", PAGE_ARRAY[0], new CmsSelectWidget(getUnitWidgetConfiguration()), 0, 1));
        addWidget(new CmsWidgetDialogParameter(this,"boxSpacing", PAGE_ARRAY[0], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"leftColumnInnerWidth", PAGE_ARRAY[0], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"centerColumnInnerWidth", PAGE_ARRAY[0], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"rightColumnInnerWidth", PAGE_ARRAY[0], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"headerInnerHeight", PAGE_ARRAY[0], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"toolbarInnerHeight", PAGE_ARRAY[0], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"footerInnerHeight", PAGE_ARRAY[0], new CmsInputWidget()));
        addWidget(new CmsWidgetDialogParameter(this,"cssFile", "", PAGE_ARRAY[0], new CmsVfsFileWidget(), 0, 1));
        addWidget(new CmsWidgetDialogParameter(this,"mediumSizeCssFile", "", PAGE_ARRAY[0], new CmsVfsFileWidget(), 0, 1));
        addWidget(new CmsWidgetDialogParameter(this,"smallSizeCssFile", "", PAGE_ARRAY[0], new CmsVfsFileWidget(), 0, 1));
        addWidget(new CmsWidgetDialogParameter(this,"topMargin", "", PAGE_ARRAY[1], new CmsInputWidget(), 0, 1));
        addWidget(new CmsWidgetDialogParameter(this,"rightMargin", "", PAGE_ARRAY[1], new CmsInputWidget(), 0, 1));
        addWidget(new CmsWidgetDialogParameter(this,"bottomMargin", "", PAGE_ARRAY[1], new CmsInputWidget(), 0, 1));
        addWidget(new CmsWidgetDialogParameter(this,"leftMargin", "", PAGE_ARRAY[1], new CmsInputWidget(), 0, 1));
    }

    /**
     * Returns the allowed pages for this dialog.
     * @return The allowed pages for this dialog.
     */
    @Override
    protected String[] getPageArray() {
        return PAGE_ARRAY;
    }
    
    /**
     * Initializes the message object. By default the CmsWorkplaceMessages are initialized. 
     * Overrides the method <code>initMessages()</code> of the superclass for setting the needed bundles, using the 
     * <code>addMessages(String)</code> method.
     */
    @Override
    protected void initMessages() {
        // add default resource bundles
        addMessages(Messages.get().getBundleName());
        super.initMessages();
    }

    /**
     * Creates all select widget options needed for the unit select widget.
     * @return The select widget options.
     */
    private List<CmsSelectWidgetOption> getUnitWidgetConfiguration(){
        List<CmsSelectWidgetOption> result = new ArrayList<CmsSelectWidgetOption>();
        
        result.add(new CmsSelectWidgetOption("pt", false));
        result.add(new CmsSelectWidgetOption("pc", false));
        result.add(new CmsSelectWidgetOption("in", false));
        result.add(new CmsSelectWidgetOption("mm", false));
        result.add(new CmsSelectWidgetOption("cm", false));
        result.add(new CmsSelectWidgetOption("px", true));
        result.add(new CmsSelectWidgetOption("em", false));
        result.add(new CmsSelectWidgetOption("ex", false));
        result.add(new CmsSelectWidgetOption("%", false));
        return result;
    }
}
