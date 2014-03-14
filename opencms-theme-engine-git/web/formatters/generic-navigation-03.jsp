<%--
 Document : generic-navigation-03.jsp
 Created on  16.08.2012, 22:25:42
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

<%@ page import="org.opencms.jsp.CmsJspNavBuilder" %>
<%@ page import="org.opencms.jsp.CmsJspActionElement" %>
<%@ page import="org.opencms.jsp.CmsJspNavElement" %>
<%@ page import="org.opencms.jsp.util.CmsJspContentAccessBean" %>
<%@ page import="org.opencms.jsp.util.CmsJspContentAccessValueWrapper" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<cms:formatter var="content" val="value">
    <%
    CmsJspActionElement    actionEl      = new CmsJspActionElement(pageContext, request, response);
    CmsJspNavBuilder       navBuilder    = actionEl.getNavigation();
    Map                    value         = ((CmsJspContentAccessBean)pageContext.findAttribute("content")).getValue();
    List<CmsJspNavElement> navBreadCrumb;
    int                    startLevel;

    // Get the start level
    startLevel = Integer.parseInt(((CmsJspContentAccessValueWrapper)value.get("start-level")).getStringValue());
    if(startLevel > 0){
        startLevel --;
    }

    // Get the breadcrumb navigation
    navBreadCrumb = navBuilder.getNavigationBreadCrumb(startLevel, true);

    pageContext.setAttribute("navBreadCrumb", navBreadCrumb, PageContext.REQUEST_SCOPE);
    %>

    <div id="BreadBrumbNavFrame">
        <div id="bread_crumb_nav">
            <%--BreadCrumb Navigation --%>
            <c:forEach items="${navBreadCrumb}" var="currentItem" varStatus="status">
                <c:set var="currentLink">
                    <jsp:attribute name="value">
                        <cms:link>${currentItem.parentFolderName}${currentItem.fileName}</cms:link>
                    </jsp:attribute>
                </c:set>
                <c:if test="${status.index > 0}">
                    <c:out value="> "/>
                </c:if>
                <a href="${currentLink}">${currentItem.navText}</a>
            </c:forEach>
        </div>
    </div>
</cms:formatter>