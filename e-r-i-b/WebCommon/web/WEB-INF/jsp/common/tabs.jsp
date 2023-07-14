<%--
  Created by IntelliJ IDEA.
  User: kligina
  Date: 01.02.2011
  Time: 14:47:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--
Компонент для отображения вкладок
tabItems - набор вкладок, состоит из компонентов tabItem
--%>

<tiles:importAttribute/>
<div id="tabs">
    <div class="leftTop">
        <div class="rightTop">
            <div class="centerTop">
                <ul>
                    ${tabItems}
                </ul>
            </div>
        </div>
    </div>
    <div class="clear"></div>
</div>

<%--расчет max-width для закладок с текстом--%>
<style type="text/css">
    #tabs ul li.text {
        max-width: ${((100 - (iconCounter * 4)) / (tabItemCounter - iconCounter)) - 1}%;
    }
</style>
