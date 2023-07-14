<%--
  Created by IntelliJ IDEA.
  User: Rydvanskiy
  Date: 04.05.2010
  Time: 18:51:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute ignore="true"/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<%--
	appointment - в Оплате товаров и услуг назначение платежа
    serviceId - id сервиса
	action - экшен для формирования ссылок
	image - адрес картинки
	listPayTitle - заголовок
	description - описание
	params - параметры для ссылок
	notForm - признак не формы
	disabled - признак недоступности
--%>

<c:if test="${empty listPayTitle}">
    <c:set var="descriptions" value="${phiz:getPaymentDescription(serviceId, appointment)}"/>
    <c:set var="listPayTitle" value="${descriptions.description}"/>
</c:if>

<div class="payment-item ${disabled ? 'payment disabled' : ''}">

    <%-- IMG --%>

    <c:choose>
        <c:when test="${not empty globalImage}">
            <c:choose>
                <c:when test="${not empty globalImage}">
                    <c:set var="imagePath" value="${globalImagePath}/${globalImage}"/>
                </c:when>
                <c:when test="${not empty appointment}">
                    <c:set var="imagePath" value="${globalImagePath}/iconPmntList_${appointment}.jpg"/>
                </c:when>
                <c:otherwise>
                    <c:set var="imagePath" value="${globalImagePath}/iconPmntList_${serviceId}.jpg"/>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${not empty image}">
                    <c:set var="imagePath" value="${imagePath}/${image}"/>
                </c:when>
                <c:when test="${not empty appointment}">
                    <c:set var="imagePath" value="${imagePath}/iconPmntList_${appointment}.png"/>
                </c:when>
                <c:otherwise>
                    <c:set var="imagePath" value="${imagePath}/iconPmntList_${serviceId}.png"/>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${disabled}">
                <img src="${imagePath}" border="0"/>
        </c:when>
        <c:otherwise>
            <phiz:link action="${action}"
                       serviceId="${serviceId}">
                <c:if test="${not empty params}">
                    <c:forEach var="name" items="${params[0]}" varStatus="status">
                        <c:set var="value" value="${params[1][status.index]}"/>
                        <phiz:param name="${name}" value="${value}"/>
                    </c:forEach>
                </c:if>
                <c:choose>
				<c:when test="${notForm}">
                <phiz:param name="appointment" value="${appointment}"/>
                </c:when>
                <c:otherwise>
                    <phiz:param name="form" value="${serviceId}"/>
                    </c:otherwise>
                </c:choose>
                <img src="${imagePath}" border="0"/>
            </phiz:link>
        </c:otherwise>
    </c:choose>
    <%-- /IMG --%>
    <div>
        <c:choose>
            <c:when test="${disabled}">
                <span class="link">${listPayTitle}</span>
            </c:when>
            <c:otherwise>
                <phiz:link styleClass="orangeText" action="${action}"
                           serviceId="${serviceId}" onclick="${onclick};">
                    <c:if test="${not empty params}">
                        <c:forEach var="name" items="${params[0]}" varStatus="status">
                            <c:set var="value" value="${params[1][status.index]}"/>
                            <phiz:param name="${name}" value="${value}"/>
                        </c:forEach>
                    </c:if>
                    <c:choose>
                        <c:when test="${notForm}">
                            <phiz:param name="appointment" value="${appointment}"/>
                        </c:when>
                        <c:otherwise>
                            <phiz:param name="form" value="${serviceId}"/>
                        </c:otherwise>
                    </c:choose>
                    <span>${listPayTitle}</span>
                </phiz:link>
            </c:otherwise>
        </c:choose>
        <br/>
        <span>${description}</span></div>
</div>

