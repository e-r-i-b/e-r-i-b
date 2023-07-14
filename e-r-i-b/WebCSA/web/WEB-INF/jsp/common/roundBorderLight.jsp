<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<%--
    компонент для закругления углов на основе технологии CSS3
    data - данные
    color - цвет(стиль) закругления
        Доступны следующие  стили:
            greenBold - жирная зеленая рамка
            red - блок с красным фоном. (используется для сообщений с ошибками)
            orange - блок с оранжевым фоном. (используется для сообщений с предупреждениями)
            infMesRed - блок с красным фоном. (используется для сообщений с ошибками)
            infMesOrange - блок с оранжевым фоном. (используется для сообщений с предупреждениями)
            infMesGreen - блок с зеленым фоном.
--%>

<tiles:importAttribute/>

<c:if test="${color == ''}">
    <c:set var="color" value=""/>
</c:if>


<div class="workspace-box roundBorder ${color}">
    ${data}
    <div class="clear"></div>
</div>