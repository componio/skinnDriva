<%--
 Document : grid16.jsp
 Created on So, Mrz 04 2012, 17:04:21
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

<%-- Comppute the page title --%>
<c:set var="title">
    <jsp:attribute name="value">
        <cms:property name="Title"/>
    </jsp:attribute>
</c:set>
<c:if test="${empty title}">
    <c:set var="title" value="Sample Page"/>
</c:if>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${title}</title>
        <core:ThemeStyles theme="${theme}" groups="Grid"/>
    </head>
    <body>
        <h1>
            <a href="http://960.gs/">960 Grid System</a>
        </h1>
        <div class="container_16">
            <h2>
                16 Column Grid
            </h2>
            <div class="grid_16">
                <p>
                    940
                </p>
            </div>
            <!-- end .grid_16 -->
            <div class="clear"></div>
            <div class="grid_1">
                <p>
                    40
                </p>
            </div>
            <!-- end .grid_1 -->
            <div class="grid_15">
                <p>
                    880
                </p>
            </div>
            <!-- end .grid_15 -->
            <div class="clear"></div>
            <div class="grid_2">
                <p>
                    100
                </p>
            </div>
            <!-- end .grid_2 -->
            <div class="grid_14">
                <p>
                    820
                </p>
            </div>
            <!-- end .grid_14 -->
            <div class="clear"></div>
            <div class="grid_3">
                <p>
                    160
                </p>
            </div>
            <!-- end .grid_3 -->
            <div class="grid_13">
                <p>
                    760
                </p>
            </div>
            <!-- end .grid_13 -->
            <div class="clear"></div>
            <div class="grid_4">
                <p>
                    220
                </p>
            </div>
            <!-- end .grid_4 -->
            <div class="grid_12">
                <p>
                    700
                </p>
            </div>
            <!-- end .grid_12 -->
            <div class="clear"></div>
            <div class="grid_5">
                <p>
                    280
                </p>
            </div>
            <!-- end .grid_5 -->
            <div class="grid_11">
                <p>
                    640
                </p>
            </div>
            <!-- end .grid_11 -->
            <div class="clear"></div>
            <div class="grid_6">
                <p>
                    340
                </p>
            </div>
            <!-- end .grid_6 -->
            <div class="grid_10">
                <p>
                    580
                </p>
            </div>
            <!-- end .grid_10 -->
            <div class="clear"></div>
            <div class="grid_7">
                <p>
                    400
                </p>
            </div>
            <!-- end .grid_7 -->
            <div class="grid_9">
                <p>
                    520
                </p>
            </div>
            <!-- end .grid_9 -->
            <div class="clear"></div>
            <div class="grid_8">
                <p>
                    460
                </p>
            </div>
            <!-- end .grid_8 -->
            <div class="grid_8">
                <p>
                    460
                </p>
            </div>
            <!-- end .grid_8 -->
            <div class="clear"></div>
            <div class="grid_1 suffix_15">
                <p>
                    40
                </p>
            </div>
            <!-- end .grid_1.suffix_15 -->
            <div class="clear"></div>
            <div class="grid_1 prefix_1 suffix_14">
                <p>
                    40
                </p>
            </div>
            <!-- end .grid_1.prefix_1.suffix_14 -->
            <div class="clear"></div>
            <div class="grid_1 prefix_2 suffix_13">
                <p>
                    40
                </p>
            </div>
            <!-- end .grid_1.prefix_2.suffix_13 -->
            <div class="clear"></div>
            <div class="grid_1 prefix_3 suffix_12">
                <p>
                    40
                </p>
            </div>
            <!-- end .grid_1.prefix_3.suffix_12 -->
            <div class="clear"></div>
            <div class="grid_1 prefix_4 suffix_11">
                <p>
                    40
                </p>
            </div>
            <!-- end .grid_1.prefix_4.suffix_11 -->
            <div class="clear"></div>
            <div class="grid_1 prefix_5 suffix_10">
                <p>
                    40
                </p>
            </div>
            <!-- end .grid_1.prefix_5.suffix_10 -->
            <div class="clear"></div>
            <div class="grid_1 prefix_6 suffix_9">
                <p>
                    40
                </p>
            </div>
            <!-- end .grid_1.prefix_6.suffix_9 -->
            <div class="clear"></div>
            <div class="grid_1 prefix_7 suffix_8">
                <p>
                    40
                </p>
            </div>
            <!-- end .grid_1.prefix_7.suffix_8 -->
            <div class="clear"></div>
            <div class="grid_1 prefix_8 suffix_7">
                <p>
                    40
                </p>
            </div>
            <!-- end .grid_1.prefix_8.suffix_7 -->
            <div class="clear"></div>
            <div class="grid_1 prefix_9 suffix_6">
                <p>
                    40
                </p>
            </div>
            <!-- end .grid_1.prefix_9.suffix_6 -->
            <div class="clear"></div>
            <div class="grid_1 prefix_10 suffix_5">
                <p>
                    40
                </p>
            </div>
            <!-- end .grid_1.prefix_10.suffix_5 -->
            <div class="clear"></div>
            <div class="grid_1 prefix_11 suffix_4">
                <p>
                    40
                </p>
            </div>
            <!-- end .grid_1.prefix_11.suffix_4 -->
            <div class="clear"></div>
            <div class="grid_1 prefix_12 suffix_3">
                <p>
                    40
                </p>
            </div>
            <!-- end .grid_1.prefix_12.suffix_3 -->
            <div class="clear"></div>
            <div class="grid_1 prefix_13 suffix_2">
                <p>
                    40
                </p>
            </div>
            <!-- end .grid_1.prefix_13.suffix_2 -->
            <div class="clear"></div>
            <div class="grid_1 prefix_14 suffix_1">
                <p>
                    40
                </p>
            </div>
            <!-- end .grid_1.prefix_14.suffix_1 -->
            <div class="clear"></div>
            <div class="grid_1 prefix_15">
                <p>
                    40
                </p>
            </div>
            <!-- end .grid_1.prefix_15 -->
            <div class="clear"></div>
            <div class="grid_8 push_8">
                <div class="grid_1 alpha">
                    <p>
                        40
                    </p>
                </div>
                <!-- end .grid_1.alpha -->
                <div class="grid_7 omega">
                    <p>
                        400
                    </p>
                </div>
                <!-- end .grid_7.omega -->
                <div class="clear"></div>
                <div class="grid_4 alpha">
                    <p>
                        220
                    </p>
                </div>
                <!-- end .grid_4.alpha -->
                <div class="grid_4 omega">
                    <p>
                        220
                    </p>
                </div>
                <!-- end .grid_4.omega -->
            </div>
            <!-- end .grid_8.push_8 -->
            <div class="grid_8 pull_8">
                <div class="grid_4 alpha">
                    <p>
                        220
                    </p>
                </div>
                <!-- end .grid_4.alpha -->
                <div class="grid_4 omega">
                    <p>
                        220
                    </p>
                </div>
                <!-- end .grid_4.omega -->
                <div class="clear"></div>
                <div class="grid_1 alpha">
                    <p>
                        40
                    </p>
                </div>
                <!-- end .grid_1.alpha -->
                <div class="grid_7 omega">
                    <p>
                        400
                    </p>
                </div>
                <!-- end .grid_7.omega -->
            </div>
            <!-- end .grid_8.pull_8 -->
        </div>
        <!-- end .container_16 -->
    </body>
</html>
