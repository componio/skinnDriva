<%--
 Document : main_table.jsp
 Created on  08.03.2012, 21:34:39
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

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cms" uri="http://www.opencms.org/taglib/cms" %>
<%@ taglib prefix="core" uri="http://www.diawara.com/themeengine/taglibs/core" %>

<%-- Get all needed variables. --%>
<core:PageWidth var="pageWidth" formatted="true" theme="${theme}"/>
<core:LeftColumnWidth var="leftColumnWidth" formatted="true" theme="${theme}"/>
<core:CenterColumnInnerWidth var="centerColumnWidth" formatted="true" theme="${theme}"/>
<core:RightColumnWidth var="rightColumnWidth" formatted="true" theme="${theme}"/>
<core:BoxSpacing var="boxSpacing" formatted="true" theme="${theme}"/>
<core:ShowLeftHandBar var="showLeftHandBar" theme="${theme}"/>
<core:ShowRightHandBar var="showRightHandBar" theme="${theme}"/>
<core:ShowToolBar var="showToolBar" theme="${theme}"/>
<c:choose>
    <c:when test="${showLeftHandBar}">
        <c:choose>
            <c:when test="${showRightHandBar}">
                <c:set var="dataColSpan" scope="request" value="1"/>
            </c:when>
            <c:otherwise>
                <c:set var="dataColSpan" scope="request" value="2"/>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${showRightHandBar}">
                <c:set var="dataColSpan" scope="request" value="2"/>
            </c:when>
            <c:otherwise>
                <c:set var="dataColSpan" scope="request" value="3"/>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>


<%-- Comppute the page title --%>
<c:set var="title">
    <jsp:attribute name="value">
        <cms:property name="Title"/>
    </jsp:attribute>
</c:set>
<c:if test="${empty title}">
    <c:set var="title" value="Sample Page"/>
</c:if>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${title}</title>

        <%-- Include styles, scripts and favicon. --%>
        <core:ThemeStyles theme="${theme}" groups="Table"/>
        <core:ThemeScripts theme="${theme}" groups="Main"/>
        <core:FavIcon theme="${theme}"/>
    </head>
    <body>
        <table class="layout_skeleton" align="center" border="0" cellpadding="0" cellspacing="${boxSpacing}">
            <colgroup>
                <col width="${leftColumnWidth}"/>
                <col width="${centerColumnWidth}"/>
                <col width="${rightColumnWidth}"/>
            </colgroup>
            <tr>
                <td class="header" colspan="3">
                    <div class="header_main">
                        <div class="header_tl"></div>
                        <div class="header_tc"></div>
                        <div class="header_tr"></div>
                        <div class="header_bl"></div>
                        <div class="header_bc"></div>
                        <div class="header_br"></div>
                        <div class="header_lc"></div>
                        <div class="header_rc"></div>

                        <div class="header_menu">
                            <table cellspacing="0" cellpadding="0" height="100%" style="border:none;width:100%;table-layout:fixed">
                                <tr>
                                    <td class="header-menu-left">
                                        &nbsp;
                                    </td>
                                    <td class="header-menu">

                                        &nbsp;
                                    </td>
                                    <td class="header-menu-right">
                                        &nbsp;
                                    </td>
                                </tr>
                            </table>
                        </div>


                        <!-- This is the content area of the header. -->
                        <div class="header_content">
                            HEADER
                        </div>
                    </div>
                </td>
            </tr>
            <c:if test="${showToolBar}">
            <tr>
                <td class="toolbar" colspan="3">
                    <div class="toolbar_main">
                        <div class="content_tl"></div>
                        <div class="content_tc"></div>
                        <div class="content_tr"></div>
                        <div class="content_bl"></div>
                        <div class="content_bc"></div>
                        <div class="content_br"></div>
                        <div class="content_lc"></div>
                        <div class="content_rc"></div>
                        <div class="toolbar_content">
                        TOOLBAR
                        </div>
                    </div>
                </td>
            </tr>
            </c:if>
            <tr style="height:100%">
                <c:if test="${showLeftHandBar}">
                <td class="left_column">
                    <div class="lefthand_main">
                        <div class="sidebar_tl"></div>
                        <div class="sidebar_tc"></div>
                        <div class="sidebar_tr"></div>
                        <div class="sidebar_bl"></div>
                        <div class="sidebar_bc"></div>
                        <div class="sidebar_br"></div>
                        <div class="sidebar_lc"></div>
                        <div class="sidebar_rc"></div>
                        <div class="sidebar_content">
                        LEFT
                        </div>
                    </div>
                </td>
                </c:if>
                <td colspan="${dataColSpan}" class="content_column">
                     <div class="content_main">
                        <div class="content_tl"></div>
                        <div class="content_tc"></div>
                        <div class="content_tr"></div>
                        <div class="content_bl"></div>
                        <div class="content_bc"></div>
                        <div class="content_br"></div>
                        <div class="content_lc"></div>
                        <div class="content_rc"></div>
                        <div class="content">
                        CONTENT
                        </div>
                     </div>
                </td>
                <c:if test="${showRightHandBar}">
                <td class="right_column">
                     <div class="righthand_main">
                        <div class="sidebar_tl"></div>
                        <div class="sidebar_tc"></div>
                        <div class="sidebar_tr"></div>
                        <div class="sidebar_bl"></div>
                        <div class="sidebar_bc"></div>
                        <div class="sidebar_br"></div>
                        <div class="sidebar_lc"></div>
                        <div class="sidebar_rc"></div>
                        <div class="sidebar_content">
                        RIGHT
                        </div>
                     </div>
                </td>
                </c:if>
            </tr>
            <tr>
                <td class="footer" colspan="3">
                    <div class="footer_main">
                        <div class="content_tl"></div>
                        <div class="content_tc"></div>
                        <div class="content_tr"></div>
                        <div class="content_bl"></div>
                        <div class="content_bc"></div>
                        <div class="content_br"></div>
                        <div class="content_lc"></div>
                        <div class="content_rc"></div>
                        <div class="footer_content">
                        FOOTER
                        </div>
                    </div>
                </td>
            </tr>
        </table>
    </body>
</html>
