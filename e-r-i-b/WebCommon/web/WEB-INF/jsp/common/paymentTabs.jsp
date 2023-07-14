<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--
 омпонент дл€ отображени€ вкладок
tabItems - набор вкладок, состоит из компонентов tabItem
count - количество вкладок, если 2 вкладки, то count=2, если 3 вкладки, то count=3, иначе пусто
--%>

<tiles:importAttribute/>
<c:set var="clazz" value="secondMenuContainer"/>
<div class="${clazz}">
    <ul class="newSecondMenu newSecondMenu${count}">
        ${tabItems}
    </ul>
</div>
<div class="clear"></div>