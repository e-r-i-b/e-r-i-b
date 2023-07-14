<%--
  Created by IntelliJ IDEA.
  User: akrenev
  Date: 27.01.2012
  Time: 17:10:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<c:set var="personFullName"     value="${form.fields['personFullName']}"/>
<c:set var="personStatus"       value="${form.fields['personStatus']}"/>
<c:set var="loginBlock"         value="${form.fields['loginBlock']}"/>
<c:set var="isCancelation"      value="${personStatus == 'W'}"/>
<c:set var="isErrorCancelation" value="${personStatus == 'E'}"/>
<c:set var="isNewOrTemplate"    value="${personStatus == 'T'}"/>
<c:set var="isSignAgreement"    value="${personStatus == 'S'}"/>
<c:set var="isLock"             value="${not form.fields['loginBlock'] and not isCancelation and not isErrorCancelation and not isSignAgreement}"/>

<tiles:insert definition="infoBlock" flush="false">
	<tiles:put name="image" value="anonym.jpg"/>
	<tiles:put name="data">
        <div class="size18 personName"><c:out value="${personFullName}"/></div>
        <div class="infoBlockStatus">
            <c:choose>
                <c:when test="${isNewOrTemplate}">
                    <img src="${imagePath}/iconSm_activate.gif" width="12px" height="12px" alt="" border="0"/>&nbsp;Подключение
                </c:when>
                <c:when test="${isLock}">
                    <img src="${imagePath}/lock.gif" width="12px" height="12px" alt="" border="0"/>&nbsp;Заблокирован
                </c:when>
                <c:when test="${isCancelation}">
                    Прекращение обслуживания
                </c:when>
                <c:when test="${isErrorCancelation}">
                    Ошибка расторжения
                </c:when>
                <c:when test="${isSignAgreement}">
                    Подписание заявления
                </c:when>
                <c:otherwise>
                    Aктивный
                </c:otherwise>
            </c:choose>
        </div>
        <c:if test="${isModified}">
            <div class="withoutRegistration">
                <br><img src="${imagePath}/important.gif" width="12px" height="12px" alt="" border="0"/>
                <span class="attention">Анкета не зарегистрирована</span>
            </div>
        </c:if>
	</tiles:put>
</tiles:insert>
