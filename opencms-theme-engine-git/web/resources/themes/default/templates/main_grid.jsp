<%--
 Document : main_grid.jsp
 Created on  08.03.2012, 21:35:30
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

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cms" uri="http://www.opencms.org/taglib/cms" %>
<%@ taglib prefix="core" uri="http://www.diawara.com/themeengine/taglibs/core" %>

<c:set var="columnCount" value="${theme.grid.columnCount}"/>
<c:set var="columnWidth" value="${theme.grid.columnWidth}"/>
<c:set var="leftMargin" value="${theme.grid.leftMargin}"/>
<c:set var="rightMargin" value="${theme.grid.rightMargin}"/>


<c:choose>
    <c:when test="${(columnCount == 12) && (columnWidth == 60) && (leftMargin == 10) && (rightMargin == 10)}">
        <cms:include page="/system/modules/net.componio.opencms.modules.eight.skinndriva.rd/resources/themes/samples/templates/grid-12.jsp"/>
    </c:when>
    <c:when test="${(columnCount == 16) && (columnWidth == 40) && (leftMargin == 10) && (rightMargin == 10)}">
        <cms:include page="/system/modules/net.componio.opencms.modules.eight.skinndriva.rd/resources/themes/samples/templates/grid-16.jsp"/>
    </c:when>
    <c:when test="${(columnCount == 24) && (columnWidth == 30) && (leftMargin == 5) && (rightMargin == 5)}">
        <cms:include page="/system/modules/net.componio.opencms.modules.eight.skinndriva.rd/resources/themes/samples/templates/grid-24.jsp"/>
    </c:when>
    <c:otherwise>
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>${title}</title>
                <style type="text/css">
                    h1, h2, h2, p, span{
                        font-family         : Arial;
                    }
                    h1, h2, h2{
                        font-weight         : bold;
                    }
                    div.main_content{
                        width               :400px;
                        margin-left         : auto;
                        margin-right        : auto;
                    }
                    td{
                        background-color    : rgb(230,230,230);
                        font-family         : "Courier New";
                        padding             : 2px 3px 2px 3px;
                    }
                    li{
                        font-family         : "Courier New";
                    }
                </style>
            </head>
            <body>
                <div class="main_content">
                    <h1 style="color:red">No Demo Available</h1>
                    <p>There's no demo page available for the dimensions, defined for your grid</p>
                    <p>Your dimensions are :</p>
                    <ul>
                        <li>Column Count&nbsp;: ${columnCount}</li>
                        <li>Column Width&nbsp;: ${columnWidth}</li>
                        <li>Left Margin&nbsp;&nbsp;: ${leftMargin}</li>
                        <li>Right Margin&nbsp;: ${rightMargin}</li>
                    </ul>
                    <p>Demo pages are only available for the following standard dimensions :</p>
                    <table align="left" border="0" cellspacing="1" style="table-layout: auto">
                        <colgroup>
                            <col style="width:75px"/>
                            <col style="width:75px"/>
                            <col style="width:75px"/>
                            <col style="width:75px"/>
                        </colgroup>
                        <tr>
                            <td style="font-weight:bold">Column Count</td>
                            <td style="font-weight:bold">Column Width</td>
                            <td style="font-weight:bold">Left Margin</td>
                            <td style="font-weight:bold">Right Margin</td>
                        </tr>
                        <tr>
                            <td>12</td>
                            <td>60</td>
                            <td>10</td>
                            <td>10</td>
                        </tr>
                        <tr>
                            <td>16</td>
                            <td>40</td>
                            <td>10</td>
                            <td>10</td>
                        </tr>
                        <tr>
                            <td>24</td>
                            <td>30</td>
                            <td>5</td>
                            <td>5</td>
                        </tr>
                    </table>
               </div>
            </body>
        </html>
    </c:otherwise>
</c:choose>