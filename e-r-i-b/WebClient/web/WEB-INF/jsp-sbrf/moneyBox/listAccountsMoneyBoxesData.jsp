<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tiles:insert definition="mainWorkspace" flush="false">
    <tiles:put name="data">
        <c:choose>
            <c:when test="${phiz:isAccountForMoneyBoxMatched(accountLink)}">
                <c:forEach var="link" items="${form.data}">
                    <c:set var="item" value="${link.value}"/>
                    <tiles:insert definition="moneyBoxAccountTemplate" flush="false">
                        <tiles:put name="id" value="${item.number}"/>
                        <c:choose>
                            <c:when test="${item.sumType == 'FIXED_SUMMA'}">
                                <tiles:put name="amount" value="${phiz:formatAmountWithoutSpaceNoCents(item.amount.decimal)}"/>
                                <tiles:put name="longOfferPayDay" beanName="item" beanProperty="longOfferPayDay"/>
                                <c:if test="${item.amount!=null}">
                                    <tiles:put name="codCurrency" value="${item.amount.currency.code}"/>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${phiz:isInstance(item, 'com.rssl.phizic.business.documents.payments.CreateMoneyBoxPayment')}">
                                        <c:set var="maxSumWrite" value="${item.amount}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="maxSumWrite" value="${link.autoSubscriptionInfo.maxSumWritePerMonth}"/>
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${not empty maxSumWrite}">
                                    <tiles:put name="maxAmount" value="${phiz:formatBigDecimal(maxSumWrite.decimal)}"/>
                                </c:if>
                                <c:if test="${maxSumWrite!=null}">
                                    <tiles:put name="codCurrency" value="${maxSumWrite.currency.code}"/>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                        <tiles:put name="goal" value="${not empty target}"/>
                        <tiles:put name="cardName" value="${phiz:getCardUserName(phiz:getCardLink(item.cardNumber))}"/>
                        <tiles:put name="cardNumber" value="${item.cardNumber}"/>
                        <tiles:put name="percent" value="${item.percent}"/>
                        <tiles:put name="period" value="${item.executionEventType}"/>
                        <c:choose>
                            <c:when test="${phiz:isInstance(item,'com.rssl.phizic.business.documents.payments.CreateMoneyBoxPayment')}">
                                <tiles:put name="status" value="DRAFT"/>
                                <tiles:put name="onclick" value="openViewClaimMoneyBoxWindows('${item.id}');"/>
                            </c:when>
                            <c:when test="${item.autoPayStatusType == 'Paused'}">
                                <tiles:put name="status" value="PAUSED"/>
                                <tiles:put name="onclick" value="openViewLinkMoneyBoxWindows('${item.number}');"/>
                            </c:when>
                            <c:otherwise>
                                <tiles:put name="status" value="ACTIVE"/>
                                <tiles:put name="onclick" value="openViewLinkMoneyBoxWindows('${item.number}');"/>
                            </c:otherwise>
                        </c:choose>
                        <tiles:put name="title" value="${item.friendlyName}"/>
                        <tiles:put name="typeSum" value="${item.sumType}"/>
                        <c:if test="${not empty item.nextPayDate}">
                            <tiles:put name="dateNearPay" beanName="item" beanProperty="nextPayDate"/>
                        </c:if>
                    </tiles:insert>
                </c:forEach>
                <c:if test="${phiz:impliesService('CreateMoneyBoxPayment')}">
                    <tiles:insert definition="moneyBoxAccountTemplate" flush="false">
                        <tiles:put name="status" value="NEW"/>
                        <tiles:put name="onclick" value="openCreateMoneyBoxToAccountWindow('${accountLink.code}');"/>
                    </tiles:insert>
                </c:if>
            </c:when>
            <c:otherwise>
                <bean:message bundle="moneyboxBundle" key="moneybox.list.unavailable.message"/>
            </c:otherwise>
        </c:choose>
    </tiles:put>
    <tiles:insert definition="moneyBoxWindowCreator" flush="false"/>
    <tiles:insert definition="moneyBoxWindowViewer" flush="false"/>
</tiles:insert>