<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:importAttribute/>

<%--
    Блок с всплывающей подсказкой (страница Платежи и переводы, Просмотр категории и шаги выбора поставщика услуг)
    title   - заголовок блока
    hint    - текст подсказки
    imagePath - путь к изображению
    url  -  ссылка, по которой переходим кликнув по заголовку
    serviceId  - соответсвующий сервис
--%>

<c:if test="${empty categoryTemplateCounter}">
    <c:set var="categoryTemplateCounter" value="0" scope="request"/>
</c:if>

<c:set var="categoryTemplateCounter" value="${categoryTemplateCounter+1}" scope="request"/>

<div class="payment ${disabled?'disabled':''}" id="Payment${categoryTemplateCounter}">
    <table cellpadding="0" cellspacing="0">
        <tr>
            <td class="paymentIcon">
                <c:choose>
                    <c:when test="${disabled}">
                        <img src="${imagePath}" alt="" border="0"/>
                    </c:when>
                    <c:when test="${empty serviceId}">
                        <phiz:link url="${fn:trim(url)}">
                            <img src="${imagePath}" alt="" border="0"/>
                        </phiz:link>
                    </c:when>
                    <c:otherwise>
                        <phiz:link url="${fn:trim(url)}" serviceId="${serviceId}">
                            <img src="${imagePath}" alt="" border="0"/>
                        </phiz:link>
                    </c:otherwise>
                </c:choose>

            </td>
            <td class="paymentTitle">
                <c:choose>
                    <c:when test="${disabled}">
                        ${title}
                    </c:when>
                    <c:otherwise>
                        <phiz:link url="${fn:trim(url)}" serviceId="${serviceId}" styleClass="paymentLinkTitle">${title}</phiz:link>
                        <c:if test="${!empty hint}"><span class="hidable">${hint}</span></c:if>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>

        <tr>
            <td colspan="2">
                <div class="paymentHint" style="display: none">${hintLink}</div>
            </td>
        </tr>

    </table>
</div>
