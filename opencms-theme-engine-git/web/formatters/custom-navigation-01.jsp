<%--
 Document : custom-navigation-01.jsp
 Created on  20.08.2012, 22:45:39
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
    <c:if test="${value['title'].isSet}">
        <c:set var="navTitle" value="${value['title'].stringValue}"/>
    </c:if>
    <div class="custom_side_bar_nav">
        <c:if test="${!empty navTitle}">
            <h1>${navTitle}</h1>
        </c:if>
        <ul class="custom_side_bar_nav">
            <c:forEach var="menuItem" items="${content.valueList['nav-item']}" varStatus="status">
                <c:set var="linkText" value="${menuItem.value['link-text']}"/>
                <c:set var="linkURI">
                    <jsp:attribute name="value">
                        <cms:link>${menuItem.value['link-uri']}</cms:link>
                    </jsp:attribute>
                </c:set>
                <c:set var="linkTarget" value="_self"/>
                <c:if test="${menuItem.value['link-target'].isSet}">
                    <c:set var="linkTarget" value="${menuItem.value['link-target']}"/>
                </c:if>
                <li>
                    <c:choose>
                        <c:when test="${menuItem.value['link-title'].isSet}">
                            <c:set var="linkTitle" value="${menuItem.value['link-title']}"/>
                            <a href="${linkURI}" target="${linkTarget}" title="${linkTitle}">${linkText}</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${linkURI}" target="${linkTarget}">${linkText}</a>
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:forEach>
        </ul>
    </div>
</cms:formatter>