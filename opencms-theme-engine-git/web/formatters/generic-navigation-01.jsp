<%--
 Document : generic-navigation-01.jsp
 Created on  08.07.2012, 16:41:05
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

<cms:formatter var="content" val="value">
    <c:set var="navStartLevel" value="${value['start-level'].stringValue}"/>
    <c:if test="${value['title'].isSet}">
        <c:set var="navTitle" value="${value['title'].stringValue}"/>
    </c:if>

    <div style="position:relative;width:100%;padding:5px 0px 10px 0px">
        <c:if test="${!empty navTitle}">
            <h1>${navTitle}</h1>
        </c:if>
        <commons:TreeNavigation linkStyleBundle="menu_link" styleBundle="menu" startLevel="${navStartLevel}"/>
    </div>
</cms:formatter>