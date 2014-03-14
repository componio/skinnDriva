<%--
 Document : CONTENT.jsp
 Created on Mo, Okt 15 2012, 21:48:50
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
<%@ taglib prefix="cms" uri="http://www.opencms.org/taglib/cms" %>
<%@ taglib prefix="core" uri="http://www.diawara.com/themeengine/taglibs/core" %>
<%@ taglib prefix="commons" uri="http://www.diawara.com/themeengine/taglibs/commons" %>

<%@ page import="org.opencms.main.OpenCms" %>
<%@ page import="org.opencms.jsp.util.CmsJspContentAccessBean" %>
<%@ page import="javax.servlet.jsp.PageContext" %>

<%-- Compute the type name of the container element. --%>
<cms:formatter var="content" val="value">
    <c:set var="containerType" value="${cms.container.type}"/>
    <c:set var="elementType" value="${cms.element.resource.typeId}"/>
</cms:formatter>

<%-- If the theme is unknown, get the name of the theme. --%>
<c:if test="${empty theme}">
    <c:set var="resourceURI">
        <jsp:attribute name="value">
            <cms:info property="opencms.request.uri"/>
        </jsp:attribute>
    </c:set>
    <core:Theme resource="${resourceURI}" var="theme" scope="request"/>
</c:if>

<%-- Get the URI of the formatter. --%>
<core:Formatter var="usedFormatter" scope="page" theme="${theme}" elementTypeId="${elementType}" containerType="${containerType}"/>

<%-- If a formatter was found, include it. --%>
<c:if test="${!empty usedFormatter}">
    <cms:include page="${usedFormatter}"/>
</c:if>