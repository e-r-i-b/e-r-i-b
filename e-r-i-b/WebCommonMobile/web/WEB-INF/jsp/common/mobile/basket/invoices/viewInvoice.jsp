<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="mobile" uri="http://rssl.com/tags/mobile" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<html:form action="/private/basket/invoices/viewInvoice">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="iphone">
        <tiles:put name="data" type="string">
            <c:set var="invoice" value="${form.invoice}"/>
            <c:set var="subscriptionName" value="${form.subscriptionName}"/>
            <c:set var="bankName" value="${form.bankName}"/>
            <c:set var="bankAccount" value="${form.bankAccount}"/>
            <c:set var="availableOperations" value="${form.operationAvailable}"/>
            <c:set var="globalImagePath" value="${globalUrl}/images"/>
            <c:set var="commonImagePath" value="${globalUrl}/commonSkin/images"/>
            <c:set var="actionUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/basket/invoice/process.do')}"/>
            <c:set var="paymentsUrl" value="${phiz:calculateActionURL(pageContext, '/private/payments.do')}"/>
            <c:set var="mainSum"/>
            <nameService>
                <c:out value="${invoice.nameService}"/>
            </nameService>
            <%--Счет выставлен по этой услуге--%>
            <subscriptionName>
                <c:out value="${subscriptionName}"/>
            </subscriptionName>

            <%--Получатель--%>
            <recipient>
                <%--Наименование--%>
                <name>
                    <c:out value="${invoice.recName}"/>
                </name>
                <%--ИНН--%>
                <inn>
                    <c:out value="${invoice.recInn}"/>
                </inn>
                <%--Счет--%>
                <account>
                    <c:out value="${invoice.recAccount}"/>
                </account>
                <%--Банк получателя--%>
                <recipientBank>
                    <%--Наименование банка--%>
                    <name>
                        <c:out value="${bankName}"/>
                    </name>
                    <%--БИК--%>
                    <bic>
                        <c:out value="${invoice.recBic}"/>
                    </bic>
                    <%--Корсчет--%>
                    <c:if test="${not empty invoice.recCorAccount || not empty bankAccount}">
                        <corAccount>
                            <c:choose>
                                <c:when test="${not empty invoice.recCorAccount}">
                                    <c:out value="${invoice.recCorAccount}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${bankAccount}"/>
                                </c:otherwise>
                            </c:choose>
                        </corAccount>
                    </c:if>
                </recipientBank>
            </recipient>

            <%--Детали платежа--%>
            <requisites>
                <c:forEach var="field" items="${invoice.requisitesAsList}">
                    <requsite>
                        <c:choose>
                            <c:when test="${not field.mainSum && field.visible}">
                                <title>
                                    <c:out value="${field.name}"/>
                                </title>
                                <value>
                                    <c:out value="${field.value}"/>
                                </value>
                            </c:when>
                            <c:when test="${field.mainSum}">
                                <c:set var="mainSum" value="${field}"/>
                            </c:when>
                        </c:choose>
                    </requsite>
                </c:forEach>
            </requisites>

            <%--Счет списания--%>
            <fromResource>
                <availableValues>
                    <c:set var="cardNumber" value="${invoice.cardNumber}"/>
                    <c:choose>
                        <c:when test="${phiz:getBasketInteractMode() == 'esb'}">
                            <c:forEach var="cardLink" items="${form.chargeOffResources}">
                                <valueItem>
                                    <value>${cardLink.code}</value>
                                    <selected>
                                        <c:choose>
                                            <c:when test="${cardLink.number == cardNumber}">
                                                <c:out value="true"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="false"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </selected>
                                    <displayedValue>
                                        <c:out value="${phiz:getCutCardNumber(cardLink.number)}"/>
                                        <c:set var="cardUserName" value="${phiz:getCardUserName(cardLink)}"/>
                                        <c:if test="${not empty cardUserName}">
                                            [<c:out value="${cardUserName}"/>]
                                        </c:if>
                                    </displayedValue>
                                </valueItem>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:set var="cardLink" value="${phiz:getCardLink(cardNumber)}"/>
                            <valueItem>
                                <value>${cardLink.code}</value>
                                <selected>true</selected>
                                <displayedValue>
                                    <c:out value="${phiz:getCutCardNumber(cardLink.number)}"/>
                                    <c:set var="cardUserName" value="${phiz:getCardUserName(cardLink)}"/>
                                    <c:if test="${not empty cardUserName}">
                                        [<c:out value="${cardUserName}"/>]
                                    </c:if>
                                </displayedValue>
                            </valueItem>
                        </c:otherwise>
                    </c:choose>
                </availableValues>
            </fromResource>

            <%--Сумма в рублях--%>
            <c:if test="${not empty mainSum}">
                <amount>
                    <bean:write name="mainSum" property="value" format="0.00"/>
                </amount>
            </c:if>
            <%--Комиссия в рублях--%>
            <c:if test="${not empty invoice.commission}">
                <commission>
                    <bean:write name="invoice" property="commission" format="0.00"/>
                </commission>
             </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>