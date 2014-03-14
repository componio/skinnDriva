<%--
 Document : index.jsp
 Created on Mo, Mai 16 2011, 00:00
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

<%@ page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ page import="org.opencms.workplace.tools.modules.CmsModulesList" %>
<%@ page import="net.componio.opencms.modules.eight.skinndriva.rd.engine.view.ThemeList" %>

<% 
    ThemeList themesList = new ThemeList(pageContext, request, response);
    themesList.displayDialog();
%>
