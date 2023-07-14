<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--
    Стикер для вывода информации по связанным с документам подпискам.
    Работает если у клиента есть право на корзину платежей.

    cssClass - класс для позиционирования стикера.
 --%>
<tiles:importAttribute/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:if test="${phiz:impliesOperation('ViewPaymentsBasketOperation', 'PaymentBasketManagment')
        and not empty form.links and (empty documentType or not empty form.links[documentType])}">
<div class="${cssClass}">
    <div class="linkedPaymentsList float">
        <div class="back1">
            <div class="clip"></div>
            <div class="caption"><bean:message bundle="userprofileBundle" key="user.sticker.caption"/></div>
            <c:set var="groupName" value=""/>
            <c:set var="firstLine" value="true"/>
            <c:set var="hasPermission" value="${phiz:impliesOperation('ViewPaymentsBasketOperation', '')}"/>
            <c:set var="linkToSubscription" value="${phiz:calculateActionURL(pageContext, '/private/basket/subscription/view')}"/>
            <c:forEach var="entity" items="${empty documentType ? form.links : form.links[documentType]}">
                <c:choose>
                    <c:when test="${groupName != entity.groupName}">
                        <c:if test="${firstLine != 'true'}">
                            </div>
                        </c:if>
                        <c:set var="firstLine" value="false"/>
                        <c:set var="groupName" value="${entity.groupName}"/>
                        <div class="servicesBlock">
                            <div class="servicesBlockTitle">${groupName}</div>
                    </c:when>
                </c:choose>
                <div class="linkService">
                    <c:choose>
                        <c:when test="${hasPermission}">
                            <a href="${linkToSubscription}?id=${entity.id}">${entity.invoiceName}</a>
                        </c:when>
                        <c:otherwise>
                            ${entity.invoiceName}
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:forEach>
            <c:if test="${firstLine != 'true'}">
                </div>
            </c:if>
        </div>
    </div>
</div>
</c:if>