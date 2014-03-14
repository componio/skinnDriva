<%--
 Document : dimensions-main.jsp
 Created on So, Mrz 04 2012, 15:57:39
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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${title}</title>
        <core:ThemeStyles theme="${theme}" groups="Dimensions"/>
    </head>
    <body>
        <div id="CONTAINER">
            <!-- The header area -->
            <div id="HEADER" class="layout-container">
                <div id="HEADER-CONTENT">
                    <p>HEAD</p>
                </div>
            </div>

            <!-- The toolbar, if one is used -->
            <div id="TOOLBAR" class="layout-container">
                <div id="TOOLBAR-CONTENT">
                    <p>TOOL</p>
                </div>
            </div>

            <!-- The lefthand bar, if one is used -->
            <div id="LEFTHANDBAR" class="layout-container">
                <div class="content-box">
                    <p>LEFT</p>
                </div>
            </div>

            <!-- The data area -->
            <div id="DATAAREA_11" class="layout-container">
                <div class="content-box">
                    <p>DATA</p>
                </div>
            </div>

            <!-- The righthand bar, if one is used -->
            <div id="RIGHTHANDBAR" class="layout-container">
                <div class="content-box">
                    <p>RIGHT</p>
                </div>
            </div>


            <!-- Another lefthand bar, if one is used -->
            <div id="LEFTHANDBAR" class="layout-container">
                <div class="content-box">
                    <p>LEFT</p>
                </div>
            </div>

            <!-- Another data area -->
            <div id="DATAAREA_10" class="layout-container">
                <div class="content-box">
                    <p>DATA</p>
                </div>
            </div>

            <!-- Another data area -->
            <div id="DATAAREA_01" class="layout-container">
                <div class="content-box">
                    <p>DATA</p>
                </div>
            </div>

            <!-- Another righthand bar, if one is used -->
            <div id="RIGHTHANDBAR" class="layout-container">
                <div class="content-box">
                    <p>RIGHT</p>
                </div>
            </div>

            <!-- Another data area -->
            <div id="DATAAREA_00" class="layout-container">
                <div class="content-box">
                    <p>DATA</p>
                </div>
            </div>

            <!-- The footer, if one is used -->
            <div id="FOOTER" class="layout-container">
                <div id="FOOTER-CONTENT">
                    <p>FOOTER</p>
                </div>
            </div>
        </div>
    </body>
</html>
