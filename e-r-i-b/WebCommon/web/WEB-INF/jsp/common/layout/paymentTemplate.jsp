<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<%--
Компонент для отображения информации по доступным для пользователя переводам
title - текст ссылки (название перевода)
link - ссылка
className - название класса
serviceName - название сервиса (услуги)
showService - доступность сервиса
--%>

<c:if test="${showService}">
    <div class="${not empty className ? className : ' '}">
        <phiz:link url="${link}" styleClass="orangeText">
            <phiz:param name="form" value="${serviceName}"/>
            <c:if test="${not empty receiverType}">
                <phiz:param name="receiverType" value="${receiverType}"/>
            </c:if>
            <c:if test="${not empty image}">
                <div class="categoryIconWight">
                    <img class="icon" src="${image}" border="0"/>
                </div>
            </c:if>
            <div class="paymentLink"><span>${title}</span></div>
            <div class="clear"></div>
        </phiz:link>
    </div>
    <c:set var="someServiceShowed" value="true"/>
</c:if>