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

<%@ page import="net.componio.opencms.modules.eight.skinndriva.rd.util.TypeChanger" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>VFS Browser (Fix Schema References)</title>
        <%
        TypeChanger   typeChanger;
        String        schemaLog;
        
        typeChanger = new TypeChanger(pageContext, request, response, "/");
        typeChanger.setTypePattern("^generic\\_article$");
        typeChanger.setOriginalString("opencms://system/modules/net.componio.opencms.modules.eight.skinndriva.rd/schemas/generic_article.xsd");
        typeChanger.setReplacementString("opencms://system/modules/net.componio.opencms.modules.eight.skinndriva.rd.skins.base/schemas/skinndriva_article.xsd");
        typeChanger.setNewTypeName("skinndriva_article");
        typeChanger.setOldSchemaType("GenericArticle");
        typeChanger.setNewSchemaType("SkinndrivaArticle");
        schemaLog = typeChanger.run();
        pageContext.setAttribute("schemaLog", schemaLog);
        %>
    </head>
    <body>
        <h2>Report for Schema Fixing  in Folder &quot;mobile&quot; :</h2>
        ${schemaLog}
    </body>
</html>
