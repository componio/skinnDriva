<%--
 Document : main.jsp
 Created on Fr, Nov 04 2011, 17:46:52
 Copyright (C) 2011 Robert Diawara

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

<%--
Here, we rertieve the theme and store it into a variable with scope request.
--%>
<core:Theme var="theme" scope="request"/>

<%--
Here, we implement the default behaviour for main template handling. We first try to get
the value of the "template" property of the object itself. It this one is empty, we take
the "mainTemplate" property of the theme. So by default always the value of the "mainTemplate"
property of the theme will be used as template, but can be overridden by the "template"
property of an object.
--%>
<c:set var="mainTemplate">
    <jsp:attribute name="value">
        <cms:property name="main-template"/>
    </jsp:attribute>
</c:set>
<c:if test="${empty mainTemplate}">
    <c:set var="mainTemplate" value="${theme.mainTemplate}"/>
</c:if>

<%-- Now we include the selected main template. --%>
<cms:include page="${mainTemplate}"/>