<%--
 Document : script_add.jsp
 Created on Mo, Dez 12 2011, 21:38:35
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
<%@ page import="net.componio.opencms.modules.eight.skinndriva.rd.engine.view.ThemeScriptDialog" %>
<%@ page import="net.componio.opencms.modules.eight.skinndriva.rd.engine.view.ThemeList" %>

<% 
    request.getSession().removeAttribute(ThemeList.PARAM_SCRIPT_URI);
    ThemeScriptDialog wp = new ThemeScriptDialog(pageContext, request, response);
    wp.displayDialog();
%>
