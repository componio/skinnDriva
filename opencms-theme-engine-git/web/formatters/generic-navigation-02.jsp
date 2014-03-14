<%--
 Document : generic-navigation-02.jsp
 Created on  30.07.2012, 21:40:24
 Copyright (C) 2012 Robert Diawara

 This file is part of OpenCms Theme Engine.

 OpenCms Theme Engine is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 OpenCms Theme Engine is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with OpenCms Theme Engine.  If not, see <http://www.gnu.org/licenses/>.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cms" uri="http://www.opencms.org/taglib/cms" %>
<%@ taglib prefix="core" uri="http://www.diawara.com/themeengine/taglibs/core" %>
<%@ taglib prefix="commons" uri="http://www.diawara.com/themeengine/taglibs/commons" %>

<%@ page import="org.opencms.jsp.CmsJspNavBuilder" %>
<%@ page import="org.opencms.jsp.CmsJspActionElement" %>
<%@ page import="org.opencms.jsp.CmsJspNavElement" %>
<%@ page import="org.opencms.jsp.util.CmsJspContentAccessBean" %>
<%@ page import="org.opencms.jsp.util.CmsJspContentAccessValueWrapper" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="javax.servlet.jsp.PageContext" %>


<cms:formatter var="content" val="value">
    <%
    CmsJspActionElement    actionEl      = new CmsJspActionElement(pageContext, request, response);
    CmsJspNavBuilder       navBuilder    = actionEl.getNavigation();
    Map                    value         = ((CmsJspContentAccessBean)pageContext.findAttribute("content")).getValue();
    int                    navStartLevel;
    int                    navEndLevel;
    String                 rootFolder;
    List<CmsJspNavElement> folderSiteNav;

    // Process the start level.
    navStartLevel = Integer.parseInt(((CmsJspContentAccessValueWrapper)value.get("start-level")).getStringValue());
    navEndLevel = navStartLevel;
    if(navStartLevel > 0){
        navStartLevel --;
    }

    // Process the site navigation for the start folder
    rootFolder = navBuilder.getNavigationBreadCrumb().get(navStartLevel).getResourceName();
    folderSiteNav = navBuilder.getSiteNavigation(rootFolder, navEndLevel);

    pageContext.setAttribute("siteNav", folderSiteNav, PageContext.REQUEST_SCOPE);
    pageContext.setAttribute("startLevel", String.valueOf(navStartLevel), pageContext.REQUEST_SCOPE);
    pageContext.setAttribute("endLevel", String.valueOf(navStartLevel +1), pageContext.REQUEST_SCOPE);
    %>

    
    <ul id="sddm" class="sddm">
    <c:forEach items="${siteNav}" var="currentItem" varStatus="status">
        <c:set var="currentLink">
            <jsp:attribute name="value">
                <cms:link>${currentItem.parentFolderName}${currentItem.fileName}</cms:link>
            </jsp:attribute>
        </c:set>
        <c:choose>
            <c:when test="${currentItem.navTreeLevel <= startLevel}">
                <li>
                <a href="${currentLink}" onmouseover="mopen('m${status.index}')" onmouseout="mclosetime()">${currentItem.navText}</a>

                <%-- This is the output at the end of a first level menu item. --%>
                <c:if test="${(status.index >= (fn:length(siteNav) - 1)) || (siteNav[status.index + 1].navTreeLevel != endLevel)}">
                    </li>
                </c:if>
            </c:when>
            <c:otherwise>
                <%-- This is the output, when the current item is not the first item,
                and the level of the previous item is 1 --%>
                <c:if test="${(status.index > 0) && (siteNav[status.index - 1].navTreeLevel == startLevel)}">
                    <div class="menu_border" id="m${status.index - 1}" onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
                    <div class="menu_area">
                    <div class="menu_tl"></div>
                    <div class="menu_tc"></div>
                    <div class="menu_tr"></div>
                    <div class="menu_rc"></div>
                    <div class="menu_br"></div>
                    <div class="menu_bc"></div>
                    <div class="menu_bl"></div>
                    <div class="menu_lc"></div>
                </c:if>

                <a href="${currentLink}">${currentItem.navText}</a>

                <%-- This is the output if the current item is the last item with a navigation tree
                levvel grater than 1 but not the last element --%>
                <c:if test="${(status.index < (fn:length(siteNav) - 1)) && (siteNav[status.index + 1].navTreeLevel == startLevel)}">
                    </div>
                    </div>
                </c:if>

                <%-- This is the output when the current item is the last item and its nav tree level
                is grater than 1 --%>
                <c:if test="${status.index == (fn:length(siteNav) - 1)}">
                    </div>
                    </div>
                    </li>
                </c:if>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    </ul>
    <%--
    <div style="padding:0px 0px 0px 12px">
        <ul id="sddm" class="sddm">
            <li>
                <a href="#" onmouseover="mopen('m1')" onmouseout="mclosetime()">Home</a>
                <div class="menu_border" id="m1" onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
                    <div class="menu_area">
                        <div class="menu_tl"></div>
                        <div class="menu_tc"></div>
                        <div class="menu_tr"></div>
                        <div class="menu_rc"></div>
                        <div class="menu_br"></div>
                        <div class="menu_bc"></div>
                        <div class="menu_bl"></div>
                        <div class="menu_lc"></div>

                        <a href="#">HTML DropDown</a>
                        <a href="#">DHTML DropDown menu</a>
                        <a href="#">JavaScript DropDown</a>
                        <a href="#">DropDown Menu</a>
                        <a href="#">CSS DropDown</a>
                    </div>
                </div>
            </li>
            <li>
                <a href="#" onmouseover="mopen('m2')" onmouseout="mclosetime()">Download</a>
                <div class="menu_border" id="m2" onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
                    <div class="menu_area">
                        <div class="menu_tl"></div>
                        <div class="menu_tc"></div>
                        <div class="menu_tr"></div>
                        <div class="menu_rc"></div>
                        <div class="menu_br"></div>
                        <div class="menu_bc"></div>
                        <div class="menu_bl"></div>
                        <div class="menu_lc"></div>

                        <a href="#">ASP Dropdown</a>
                        <a href="#">Pulldown menu</a>
                        <a href="#">AJAX dropdown</a>
                        <a href="#">DIV dropdown</a>
                    </div>
                </div>
            </li>
            <li>
                <a href="#" onmouseover="mopen('m3')" onmouseout="mclosetime()">Order</a>
                <div class="menu_border" id="m3" onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
                    <div class="menu_area">
                        <div class="menu_tl"></div>
                        <div class="menu_tc"></div>
                        <div class="menu_tr"></div>
                        <div class="menu_rc"></div>
                        <div class="menu_br"></div>
                        <div class="menu_bc"></div>
                        <div class="menu_bl"></div>
                        <div class="menu_lc"></div>

                        <a href="#">Visa Credit Card</a>
                        <a href="#">Paypal</a>
                    </div>
                </div>
            </li>
            <li>
                <a href="#" onmouseover="mopen('m4')" onmouseout="mclosetime()">Help</a>
                <div class="menu_border" id="m4" onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
                    <div class="menu_area">
                        <div class="menu_tl"></div>
                        <div class="menu_tc"></div>
                        <div class="menu_tr"></div>
                        <div class="menu_rc"></div>
                        <div class="menu_br"></div>
                        <div class="menu_bc"></div>
                        <div class="menu_bl"></div>
                        <div class="menu_lc"></div>

                        <a href="#">Download Help File</a>
                        <a href="#">Read online</a>
                    </div>
                </div>
            </li>
            <li>
                <a href="#" onmouseover="mopen('m5')" onmouseout="mclosetime()">Contact</a>
                <div class="menu_border" id="m5" onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
                    <div class="menu_area">
                        <div class="menu_tl"></div>
                        <div class="menu_tc"></div>
                        <div class="menu_tr"></div>
                        <div class="menu_rc"></div>
                        <div class="menu_br"></div>
                        <div class="menu_bc"></div>
                        <div class="menu_bl"></div>
                        <div class="menu_lc"></div>

                        <a href="#">E-mail</a>
                        <a href="#">Submit Request Form</a>
                        <a href="#">Call Center</a>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    --%>
</cms:formatter>