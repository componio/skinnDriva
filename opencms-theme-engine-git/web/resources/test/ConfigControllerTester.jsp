<%-- 
 Document : ConfigControllerTester.jsp 
 Created on Fr, Aug 05 2011, 22:39:06
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
<%@page import="net.componio.opencms.modules.eight.skinndriva.rd.engine.controller.ThemeFactory"%>
<%@page import="net.componio.opencms.modules.eight.skinndriva.rd.engine.controller.I_ThemeConfigController"%>
<%@page import="net.componio.opencms.modules.eight.skinndriva.rd.engine.model.ThemeConfig"%>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%
        I_ThemeConfigController controller  = ThemeFactory.getConfigController(pageContext, request, response);
        ThemeConfig             themeConfig = controller.loadThemeConfig("Default");
        ArrayList<ThemeConfig>  configList  = controller.listThemeConfigs();
        
        themeConfig.setId("Default_Test");
        controller.saveThemeConfig(themeConfig);
        
        controller.createThemeConfig(themeConfig);
        
        controller.createThemeConfig("Just a Test");
        controller.createThemeConfig("Just a Test");
        %>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
