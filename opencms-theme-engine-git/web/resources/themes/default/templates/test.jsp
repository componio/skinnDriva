<%--
 Document : test.jsp
 Created on  01.05.2012, 18:35:28
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
<%@ taglib prefix="cms" uri="http://www.opencms.org/taglib/cms" %>
<%@ taglib prefix="core" uri="http://www.diawara.com/themeengine/taglibs/core" %>

<%-- Comppute the page title --%>
<c:set var="title">
    <jsp:attribute name="value">
        <cms:property name="Title"/>
    </jsp:attribute>
</c:set>
<c:if test="${empty title}">
    <c:set var="title" value="Sample Page"/>
</c:if>

<%-- Compute styles for the header menu. --%>
<core:PageWidth var="pageWidth" theme="${theme}"/>
<core:HeaderInnerHeight var="headerInnerHeight" theme="${theme}"/>
<core:BoxSpacing var="boxSpacing" theme="${theme}"/>

<c:set var="unit" value="${theme.unit}"/>
<c:set var="headerMenuWidth" value="${pageWidth - 120}${unit}"/>
<c:set var="headerContentHeight" value="${headerInnerHeight - 40}${unit}"/>
<c:set var="headerMenuLeft" value="60${unit}"/>

<%-- Get page settings and compute the style for content area --%>
<core:ShowLeftHandBar var="showLeftHandBar" theme="${theme}"/>
<core:ShowRightHandBar var="showRightHandBar" theme="${theme}"/>
<core:ShowToolBar var="showToolBar" theme="${theme}"/>
<c:choose>
    <c:when test="${showLeftHandBar}">
        <c:choose>
            <c:when test="${showRightHandBar}">
                <c:set var="styleSuffix" value="11"/>
            </c:when>
            <c:otherwise>
                <c:set var="styleSuffix" value="10"/>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${showRightHandBar}">
                <c:set var="styleSuffix" value="01"/>
            </c:when>
            <c:otherwise>
                <c:set var="styleSuffix" value="00"/>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${title}</title>
        <core:ThemeStyles theme="${theme}" groups="Dimensions"/>
        <core:ThemeStyles theme="${theme}" groups="Decorations"/>
        <core:FavIcon theme="${theme}"/>
        <style type="text/css">
            div.header-content{
                position        : absolute;
                height          : ${headerContentHeight};
                z-index         : 8;
            }

            div.header-menu{
                width           : ${headerMenuWidth};
                left            : ${headerMenuLeft};
                vertical-align  : bottom;
                z-index         : 5;
            }
        </style>
        <cms:enable-ade/>
    </head>
    <body>
        <div id="CONTAINER">
            <!-- The header area -->
            <div class="header">
                <div style="position:relative;height:100%;width:100%;border:none">
                    <div id="header" class="header-content">
                        <div class="header_tl"></div>
                        <div class="header_tc"></div>
                        <div class="header_tr"></div>
                        <div class="header_bl"></div>
                        <div class="header_bc"></div>
                        <div class="header_br"></div>
                        <div class="header_lc"></div>
                        <div class="header_rc"></div>
                        <cms:container name="headerContainer" type="header"/>
                    </div>
                    <div class="header-menu">
                        <div class="header-menu-left"></div>
                        <div class="header-menu-right"></div>
                        TEST
                    </div>
                </div>
            </div>

            <div class="clear"></div>

            <!-- The toolbar, if one is used -->
            <c:if test="${showToolBar}">
                <div class="toolbar">
                    <div class="toolbar-content">
                        <div class="content_tl"></div>
                        <div class="content_tc"></div>
                        <div class="content_tr"></div>
                        <div class="content_bl"></div>
                        <div class="content_bc"></div>
                        <div class="content_br"></div>
                        <div class="content_lc"></div>
                        <div class="content_rc"></div>
                        <cms:container name="toolbarContainer" type="toolbar"/>
                    </div>
                </div>
                <div class="clear"></div>
            </c:if>

            <!-- The lefthand bar, if one is used -->
            <c:if test="${showLeftHandBar}">
                <div class="lefthandbar">
                    <div class="lefthandbar-content">
                        <div class="sidebar_tl"></div>
                        <div class="sidebar_tc"></div>
                        <div class="sidebar_tr"></div>
                        <div class="sidebar_bl"></div>
                        <div class="sidebar_bc"></div>
                        <div class="sidebar_br"></div>
                        <div class="sidebar_lc"></div>
                        <div class="sidebar_rc"></div>
                        <p>LEFT</p>
                    </div>
                </div>
            </c:if>

            <!-- The data area -->
            <div class="dataarea_${styleSuffix}">
                <div class="dataarea-content_${styleSuffix}">
                    <div class="content_tl"></div>
                    <div class="content_tc"></div>
                    <div class="content_tr"></div>
                    <div class="content_bl"></div>
                    <div class="content_bc"></div>
                    <div class="content_br"></div>
                    <div class="content_lc"></div>
                    <div class="content_rc"></div>
                    <cms:container name="contentContainer" type="content"/>
                </div>
            </div>

            <!-- The righthand bar, if one is used -->
            <c:if test="${showRightHandBar}">
                <div class="righthandbar">
                    <div class="righthandbar-content">
                        <div class="sidebar_tl"></div>
                        <div class="sidebar_tc"></div>
                        <div class="sidebar_tr"></div>
                        <div class="sidebar_bl"></div>
                        <div class="sidebar_bc"></div>
                        <div class="sidebar_br"></div>
                        <div class="sidebar_lc"></div>
                        <div class="sidebar_rc"></div>
                        <p>RIGHT</p>
                    </div>
                    <div class="vspace"></div>
                    <div class="righthandbar-content">
                        <div class="sidebar_tl"></div>
                        <div class="sidebar_tc"></div>
                        <div class="sidebar_tr"></div>
                        <div class="sidebar_bl"></div>
                        <div class="sidebar_bc"></div>
                        <div class="sidebar_br"></div>
                        <div class="sidebar_lc"></div>
                        <div class="sidebar_rc"></div>
                        <p>RIGHT</p>
                    </div>
                </div>
            </c:if>

            <div class="clear"></div>

            <!-- The footer, if one is used -->
            <div class="footer">
                <div class="footer-content">
                    <div class="content_tl"></div>
                    <div class="content_tc"></div>
                    <div class="content_tr"></div>
                    <div class="content_bl"></div>
                    <div class="content_bc"></div>
                    <div class="content_br"></div>
                    <div class="content_lc"></div>
                    <div class="content_rc"></div>
                    <cms:container name="footerContainer" type="footer"/>
                </div>
            </div>
        </div>
    </body>
</html>
