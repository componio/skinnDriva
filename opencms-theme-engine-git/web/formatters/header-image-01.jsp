<%--
 Document : header-image-01.jsp
 Created on  21.08.2012, 20:29:57
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
    <c:forEach var="instance" items="${content.valueList['instance']}" varStatus="status">
        <c:remove var="currentTheme"/>
        <c:remove var="currentLink"/>
        <c:if test="${instance.value['theme'].isSet}">
            <c:set var="currentTheme" value="${instance.value['theme']}"/>
        </c:if>
        <c:if test="${empty theme}">
            <core:Theme var="theme" scope="request"/>
        </c:if>

        <c:if test="${(empty currenrTheme) || (currentTheme == theme)}">
            <%-- Set defaults --%>
            <c:set var="style" value="position:absolute"/>
            <c:set var="hOrientation" value="left"/>
            <c:set var="vOrientation" value="top"/>
            <c:set var="alt" value="image"/>

            <c:if test="${instance.value['h-orientation'].isSet}">
                <c:set var="hOrientation" value="${instance.value['h-orientation']}"/>
            </c:if>
            <c:if test="${instance.value['v-orientation'].isSet}">
                <c:set var="vOrientation" value="${instance.value['v-orientation']}"/>
            </c:if>
            <c:if test="${instance.value['alt'].isSet}">
                <c:set var="alt" value="${instance.value['alt']}"/>
            </c:if>

            <c:set var="style" value="${style};${hOrientation}:${instance.value['h-pos']}px"/>
            <c:set var="style" value="${style};${vOrientation}:${instance.value['v-pos']}px"/>
            <c:if test="${instance.value['width'].isSet}">
                <c:set var="style" value="${style};width:${instance.value['width']}px"/>
            </c:if>
            <c:if test="${instance.value['height'].isSet}">
                <c:set var="style" value="${style};height:${instance.value['height']}px"/>
            </c:if>

            <div class="image_frame" style="${style}">
                <c:remove var="linkURI"/>
                <c:remove var="linkText"/>
                <c:remove var="linkTitle"/>
                <c:forEach var="link" items="${instance.valueList['img-link']}" varStatus="status">
                    <c:set var="linkText" value="${link.value['link-text']}"/>
                    <c:set var="linkURI">
                        <jsp:attribute name="value">
                            <cms:link>${link.value['link-uri']}</cms:link>
                        </jsp:attribute>
                    </c:set>
                    <c:set var="linkTarget" value="_self"/>
                    <c:if test="${link.value['link-target'].isSet}">
                        <c:set var="linkTarget" value="${link.value['link-target']}"/>
                    </c:if>
                    <c:if test="${link.value['link-title'].isSet}">
                        <c:set var="linkTitle" value="${link.value['link-title']}"/>
                    </c:if>
                </c:forEach>

                <c:choose>
                    <c:when test="${!empty linkURI}">
                        <c:choose>
                            <c:when test="${!empty linkTitle}">
                                <a href="${linkURI}" target="${linkTarget}" title="${linkTitle}">
                                    <img border="0" alt="${alt}" src="<cms:link>${instance.value['img-source']}</cms:link>"/>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="${linkURI}" target="${linkTarget}">
                                    <img border="0" alt="${alt}" src="<cms:link>${instance.value['img-source']}</cms:link>"/>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <img border="0" alt="${alt}" src="<cms:link>${instance.value['img-source']}</cms:link>"/>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>
    </c:forEach>
</cms:formatter>