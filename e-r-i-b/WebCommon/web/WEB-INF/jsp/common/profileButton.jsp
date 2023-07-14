<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>
<%--
commandText- текст на кнопке
onclick- функция, вызывающаяся при клике
viewType- класс
--%>

<div class="${viewType}" onclick="${onclick}">
    <c:if test="${not empty icon}">
        <div class="${icon}"></div>
    </c:if>
    <span class="${textStyle}">
        ${commandText}
    </span>
</div>
