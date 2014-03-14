<%--
 Document : vfs-browser.jsp
 Created on Mi, Mai 01 2013, 14:00:41
 Copyright (C) 2013 Robert Diawara

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

<%@page import="org.opencms.file.CmsProperty"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="net.componio.opencms.modules.eight.skinndriva.rd.util.SchemaFixer" %>
<%@ page import="net.componio.opencms.modules.eight.skinndriva.rd.util.PropertyFixer" %>
<%@ page import="org.opencms.file.CmsPropertyDefinition" %>
<%@ page import="org.opencms.file.CmsProperty" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>VFS Browser (Fix Schema References)</title>
        <%
        PropertyFixer propertyFixer;
        String        propertyLog;
        
        propertyFixer = new PropertyFixer(pageContext, request, response, "/public/");
        propertyFixer.setTypePattern("^containerpage$");
        propertyFixer.setOriginalPattern("^\\/system\\/modules\\/com\\.diawara\\.opencms\\.themes\\/templates\\/(.+)$");
        propertyFixer.setPropertyName(CmsPropertyDefinition.PROPERTY_TEMPLATE);
        propertyFixer.setPropertyValue("");
        propertyFixer.setPropertyType(CmsProperty.TYPE_INDIVIDUAL);
        propertyLog = propertyFixer.run();
        pageContext.setAttribute("propertyLog", propertyLog);
        %>
    </head>
    <body>
        <h2>Report for Property Clearing in Folder &quot;public&quot; :</h2>
        ${propertyLog}
    </body>
</html>
