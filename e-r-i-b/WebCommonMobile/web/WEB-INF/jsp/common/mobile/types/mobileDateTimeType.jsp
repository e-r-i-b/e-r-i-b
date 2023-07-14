<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<tiles:importAttribute ignore="true"/>
<fmt:setLocale value="ru-RU"/>
<%--
  Компонента для формирования данных типа DateTime.
  Формат в ЕРИБ API v1.04 и v2.00: 01.10.1994T13:02.14
  Формат в ЕРИБ API v3.00 и выше: 01.10.1994T13:02:14
  name - название поля
  calendar - дата типа java.util.Calendar или java.util.Date
  pattern - формат отображения даты. Дефолтное значение необходимо устанавливать в tiles-components.xml конкретного модуля.
--%>

<c:if test="${not empty calendar and name!=''}">
    <c:choose>
        <c:when test="${phiz:isInstance(calendar, 'java.util.Calendar')}">
            <${name}><fmt:formatDate value="${calendar.time}" pattern="${pattern}"/></${name}>
        </c:when>
        <c:when test="${phiz:isInstance(calendar, 'java.util.Date')}">
            <${name}><fmt:formatDate value="${calendar}" pattern="${pattern}"/></${name}>
        </c:when>
    </c:choose>
</c:if>
