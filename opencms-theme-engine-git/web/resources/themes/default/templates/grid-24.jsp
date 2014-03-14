<%--
 Document : grid24.jsp
 Created on So, Mrz 04 2012, 17:04:39
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
        <div class="container_24">
            <h2>
                24 Column Grid
            </h2>
            
            <div class="grid_24">
                <p>
                    950
                </p>
            </div>
            <!-- end .grid_24 -->
            
            <div class="clear"></div>
            
            <div class="grid_1">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1 -->
            
            <div class="grid_23">
                <p>
                    910
                </p>
            </div>
            <!-- end .grid_23 -->
            
            <div class="clear"></div>
            
            <div class="grid_2">
                <p>
                    70
                </p>
            </div>
            <!-- end .grid_2 -->
            
            <div class="grid_22">
                <p>
                    870
                </p>
            </div>
            <!-- end .grid_2 -->
            
            <div class="clear"></div>
            
            <div class="grid_3">
                <p>
                    110
                </p>
            </div>
            <!-- end .grid_3 -->
            
            <div class="grid_21">
                <p>
                    830
                </p>
            </div>
            <!-- end .grid_21 -->
            
            <div class="clear"></div>
            
            <div class="grid_4">
                <p>
                    150
                </p>
            </div>
            <!-- end .grid_4 -->
            
            <div class="grid_20">
                <p>
                    790
                </p>
            </div>
            <!-- end .grid_20 -->
            
            <div class="clear"></div>
            
            <div class="grid_5">
                <p>
                    190
                </p>
            </div>
            <!-- end .grid_5 -->
            
            <div class="grid_19">
                <p>
                    750
                </p>
            </div>
            <!-- end .grid_19 -->
            
            <div class="clear"></div>
            
            <div class="grid_6">
                <p>
                    230
                </p>
            </div>
            <!-- end .grid_6 -->
            
            <div class="grid_18">
                <p>
                    710
                </p>
            </div>
            <!-- end .grid_18 -->
            
            <div class="clear"></div>
            
            <div class="grid_7">
                <p>
                    270
                </p>
            </div>
            <!-- end .grid_7 -->
            
            <div class="grid_17">
                <p>
                    670
                </p>
            </div>
            <!-- end .grid_17 -->
            
            <div class="clear"></div>
            
            <div class="grid_8">
                <p>
                    310
                </p>
            </div>
            <!-- end .grid_8 -->
            
            <div class="grid_16">
                <p>
                    630
                </p>
            </div>
            <!-- end .grid_16 -->
            
            <div class="clear"></div>
            
            <div class="grid_9">
                <p>
                    350
                </p>
            </div>
            <!-- end .grid_9 -->
            
            <div class="grid_15">
                <p>
                    590
                </p>
            </div>
            <!-- end .grid_15 -->
            
            <div class="clear"></div>
            
            <div class="grid_10">
                <p>
                    390
                </p>
            </div>
            <!-- end .grid_10 -->
            
            <div class="grid_14">
                <p>
                    550
                </p>
            </div>
            <!-- end .grid_14 -->
            
            <div class="clear"></div>
            
            <div class="grid_11">
                <p>
                    430
                </p>
            </div>
            <!-- end .grid_11 -->
            
            <div class="grid_13">
                <p>
                    510
                </p>
            </div>
            <!-- end .grid_13 -->
            
            <div class="clear"></div>
            
            <div class="grid_12">
                <p>
                    470
                </p>
            </div>
            <!-- end .grid_12 -->
            
            <div class="grid_12">
                <p>
                    470
                </p>
            </div>
            <!-- end .grid_12 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 suffix_23">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.suffix_23 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 prefix_1 suffix_22">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_1.suffix_22 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 prefix_2 suffix_21">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_2.suffix_21 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 prefix_3 suffix_20">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_3.suffix_20 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 prefix_4 suffix_19">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_4.suffix_19 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 prefix_5 suffix_18">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_5.suffix_18 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 prefix_6 suffix_17">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_6.suffix_17 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 prefix_7 suffix_16">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_7.suffix_16 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 prefix_8 suffix_15">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_8.suffix_15 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 prefix_9 suffix_14">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_9.suffix_14 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 prefix_10 suffix_13">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_10.suffix_13 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 prefix_11 suffix_12">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_11.suffix_12 -->
            
            <div class="grid_1 prefix_12 suffix_11">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_12.suffix_11 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 prefix_13 suffix_10">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_13.suffix_10 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 prefix_14 suffix_9">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_14.suffix_9 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 prefix_15 suffix_8">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_15.suffix_8 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 prefix_16 suffix_7">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_16.suffix_7 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 prefix_17 suffix_6">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_17.suffix_6 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 prefix_18 suffix_5">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_18.suffix_5 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 prefix_19 suffix_4">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_19.suffix_4 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 prefix_20 suffix_3">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_20.suffix_3 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 prefix_21 suffix_2">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_21.suffix_2 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 prefix_22 suffix_1">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_22.suffix_1 -->
            
            <div class="clear"></div>
            
            <div class="grid_1 prefix_23">
                <p>
                    30
                </p>
            </div>
            <!-- end .grid_1.prefix_23 -->
            
            <div class="clear"></div>
            
            <div class="grid_12 push_12">
                <div class="grid_6 alpha">
                    <p>
                        230
                    </p>
                </div>
                <!-- end .grid_6.alpha -->

                <div class="grid_6 omega">
                    <p>
                      230
                    </p>
                </div>
                <!-- end .grid_6.omega -->

                <div class="clear"></div>

                <div class="grid_1 alpha">
                    <p>
                      30
                    </p>
                </div>
                <!-- end .grid_1.alpha -->

                <div class="grid_11 omega">
                    <p>
                      430
                    </p>
                </div>
                <!-- end .grid_11.omega -->
            </div>
            <!-- end .grid_12.push_12 -->
            
            <div class="grid_12 pull_12">
                <div class="grid_1 alpha">
                    <p>
                      30
                    </p>
                </div>
                <!-- end .grid_1.alpha -->
                
                <div class="grid_11 omega">
                    <p>
                      430
                    </p>
                </div>
                <!-- end .grid_11.omega -->
                
                <div class="clear"></div>
                
                <div class="grid_6 alpha">
                    <p>
                        230
                    </p>
                </div>
                <!-- end .grid_6.alpha -->
                
                <div class="grid_6 omega">
                    <p>
                        230
                    </p>
                </div>
                <!-- end .grid_6.omega -->
            </div>
            <!-- end .grid_12.pull_12 -->
        </div>
        <!-- end .container_24 -->
    </body>
</html>
