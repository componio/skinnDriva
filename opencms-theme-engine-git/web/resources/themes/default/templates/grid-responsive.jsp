<%--
 Document : grid12.jsp
 Created on So, Mrz 04 2012, 17:03:59
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

<!DOCTYPE HTML>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
        <title>${title}</title>
        <core:ThemeResponsiveStyles theme="${theme}" groups="Responsive"/>
    </head>
    <body>
        <h1>
            <a href="http://www.skinndriva.com">skinnDriva Grid System</a>
        </h1>
        <div class="container_12 m_container_6 s_container_full">
            <h2>
                OpenCms Grid
            </h2>
            <div class="grid_12 m_grid_6 s_grid_full">
                <p>
                    940
                </p>
            </div>
            <div class="clear m_clear s_clear"></div>
            <!-- end .grid_12 -->
            <div class="grid_1 m_grid_1 s_grid_full">
                <p>
                    60
                </p>
            </div>
            <div class="grid_hidden m_grid_hidden s_clear"></div>
            <!-- end .grid_1 -->
            <div class="grid_11 m_grid_5 s_grid_full">
                <p>
                    860
                </p>
            </div>
            <!-- end .grid_11 -->
            <div class="clear m_clear s_clear"></div>
            <div class="grid_2 m_grid_2 s_grid_full">
                <p>
                    140
                </p>
            </div>
            <!-- end .grid_2 -->
            <div class="grid_hidden m_grid_hidden s_clear"></div>
            <div class="grid_10 m_grid_4 s_grid_full">
                <p>
                    780
                </p>
            </div>
            <!-- end .grid_10 -->
            <div class="clear m_clear s_clear"></div>
            <div class="grid_3 m_grid_3 s_grid_full">
                <p>
                    220
                </p>
            </div>
            <!-- end .grid_3 -->
            <div class="grid_hidden m_grid_hidden s_clear"></div>
            <div class="grid_9 m_grid_3 s_grid_full">
                <p>
                    700
                </p>
            </div>
            <!-- end .grid_9 -->
            <div class="clear m_clear s_clear"></div>
            <div class="grid_4 m_grid_4 s_grid_full">
                <p>
                    300
                </p>
            </div>
            <!-- end .grid_4 -->
            <div class="grid_hidden m_grid_hidden s_clear"></div>
            <div class="grid_8 m_grid_2 s_grid_full">
                <p>
                    620
                </p>
            </div>
            <!-- end .grid_8 -->
            <div class="clear m_clear s_clear"></div>
            <div class="grid_5 m_grid_5 s_grid_full">
                <p>
                    380
                </p>
            </div>
            <!-- end .grid_5 -->
            <div class="grid_hidden m_grid_hidden s_clear"></div>
            <div class="grid_7 m_grid_1 s_grid_full">
                <p>
                    540
                </p>
            </div>
            <!-- end .grid_7 -->
            <div class="clear"></div>
            <div class="grid_6 m_grid_hidden s_grid_hidden">
                <p>
                    460
                </p>
            </div>
            <!-- end .grid_6 -->
            <div class="grid_6 m_grid_hidden s_grid_hidden">
                <p>
                    460
                </p>
            </div>
            <!-- end .grid_6 -->
            <div class="clear m_clear s_clear"></div>
            <div class="grid_1 suffix_11 m_grid_1 m_suffix_5 s_grid_full">
                <p>
                    60
                </p>
            </div>
            <!-- end .grid_1.suffix_11 -->
            <div class="clear m_clear s_clear"></div>
            <div class="grid_1 prefix_1 suffix_10 m_grid_1 m_prefix_1 m_suffix_4 s_grid_full">
                <p>
                    60
                </p>
            </div>
            <!-- end .grid_1.prefix_1.suffix_10 -->
            <div class="clear m_clear s_clear"></div>
            <div class="grid_1 prefix_2 suffix_9 m_grid_1 m_prefix_2 m_suffix_3 s_grid_full">
                <p>
                    60
                </p>
            </div>
            <!-- end .grid_1.prefix_2.suffix_9 -->
            <div class="clear m_clear s_clear"></div>
            <div class="grid_1 prefix_3 suffix_8 m_grid_1 m_prefix_3 m_suffix_2 s_grid_full">
                <p>
                    60
                </p>
            </div>
            <!-- end .grid_1.prefix_3.suffix_8 -->
            <div class="clear m_clear s_clear"></div>
            <div class="grid_1 prefix_4 suffix_7 m_grid_1 m_prefix_4 m_suffix_1 s_grid_full">
                <p>
                    60
                </p>
            </div>
            <!-- end .grid_1.prefix_4.suffix_7 -->
            <div class="clear m_clear s_clear"></div>
            <div class="grid_1 prefix_5 suffix_6 m_grid_1 m_prefix_5 s_grid_full">
                <p>
                    60
                </p>
            </div>
            <!-- end .grid_1.prefix_5.suffix_6 -->
            <div class="clear"></div>
            <div class="grid_1 prefix_6 suffix_5 m_grid_hidden s_grid_hidden">
                <p>
                    60
                </p>
            </div>
            <!-- end .grid_1.prefix_6.suffix_5 -->
            <div class="clear"></div>
            <div class="grid_1 prefix_7 suffix_4 m_grid_hidden s_grid_hidden">
                <p>
                    60
                </p>
            </div>
            <!-- end .grid_1.prefix_7.suffix_4 -->
            <div class="clear"></div>
            <div class="grid_1 prefix_8 suffix_3 m_grid_hidden s_grid_hidden">
                <p>
                    60
                </p>
            </div>
            <!-- end .grid_1.prefix_8.suffix_3 -->
            <div class="clear"></div>
            <div class="grid_1 prefix_9 suffix_2 m_grid_hidden s_grid_hidden">
                <p>
                    60
                </p>
            </div>
            <!-- end .grid_1.prefix_9.suffix_2 -->
            <div class="clear"></div>
            <div class="grid_1 prefix_10 suffix_1 m_grid_hidden s_grid_hidden">
                <p>
                    60
                </p>
            </div>
            <!-- end .grid_1.prefix_10.suffix_1 -->
            <div class="clear"></div>
            <div class="grid_1 prefix_11 m_grid_hidden s_grid_hidden">
                <p>
                    60
                </p>
            </div>
            <!-- end .grid_1.prefix_11 -->
            <div class="clear"></div>
            <div class="grid_6 push_6 m_grid_3 m_push_3 s_grid_hidden">
                <div class="grid_1 alpha m_grid_1 m_alpha">
                    <p>
                        60
                    </p>
                </div>
                <!-- end .grid_1.alpha -->
                <div class="grid_5 omega m_grid_2 m_omega">
                    <p>
                        380
                    </p>
                </div>
                <!-- end .grid_5.omega -->
                <div class="clear"></div>
                <div class="grid_3 alpha m_grid_2 m_alpha">
                    <p>
                        220
                    </p>
                </div>
                <!-- end .grid_3.alpha -->
                <div class="grid_3 omega m_grid_1 m_omega">
                    <p>
                        220
                    </p>
                </div>
                <!-- end .grid_3.omega -->
            </div>
            <!-- end .grid_6.push_6 -->
            <div class="grid_6 pull_6 m_grid_3 m_pull_3 s_grid_hidden">
                <div class="grid_3 alpha m_grid_1 m_alpha">
                    <p>
                        220
                    </p>
                </div>
                <!-- end .grid_3.alpha -->
                <div class="grid_3 omega m_grid_2 m_omega">
                    <p>
                        220
                    </p>
                </div>
                <!-- end .grid_3.omega -->
                <div class="clear"></div>
                <div class="grid_1 alpha m_grid_2 m_alpha">
                    <p>
                        60
                    </p>
                </div>
                <!-- end .grid_1.alpha -->
                <div class="grid_5 omega m_grid_1 m_omega">
                    <p>
                        380
                    </p>
                </div>
                <!-- end .grid_5.omega -->
            </div>
            <!-- end .grid_6.pull_6 -->
        </div>
        <!-- end .container_12 -->
    </body>
</html>
