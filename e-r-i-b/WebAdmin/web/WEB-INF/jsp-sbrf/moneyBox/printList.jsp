<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="printAutoSubscriptions">
    <html>
        <body onload="javascript:print()">
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
            <table style="width:100%;height:400px" cellspacing="0" cellpadding="0">
                <tr height="20px"><td>&nbsp;<td/></tr>
                <tr>
                    <td align="center" valign="top">
                        <table cellspacing="0" cellpadding="0" border="1px" width="100%" class="tbl">
                            <tr align="center" class="titleDoc">
                                <td width="10%">Дата</td>
                                <td width="20%">Название</td>
                                <td width="20%">Карта списания</td>
                                <td width="10%">Счет зачисления</td>
                                <td width="20%">Вид копилки</td>
                                <td width="10%">Условия исполнения</td>
                                <td width="10%">Статус</td>
                            </tr>
                            <logic:iterate id="link" name="ListMoneyBoxForm" property="data">
                                <c:set var="moneyBox" value="${link.value}"/>
                                <c:set var="sumType" value="${moneyBox.sumType}"/>
                                <c:set var="amount" value="${moneyBox.amount}"/>
                                <c:set var="isClaim" value="${phiz:isInstance(moneyBox, 'com.rssl.phizic.business.documents.payments.CreateMoneyBoxPayment')}"/>

                                <tr align="center" class="listItem">
                                    <td>
                                        <c:set var="startDate" value="${isClaim ? moneyBox.dateCreated : moneyBox.startDate}"/>
                                        <c:out value="${phiz:сalendarToString(startDate)}"/>
                                    </td>
                                    <td>
                                        <c:out value="${moneyBox.friendlyName}"/>
                                    </td>
                                    <td>
                                        <c:out value="${phiz:getCutCardNumber(moneyBox.cardNumber)}"/>
                                    </td>
                                    <td>
                                        <c:out value="${moneyBox.accountNumber}"/>
                                    </td>
                                    <td>
                                        <c:out value="${sumType.description}"/>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${sumType == 'FIXED_SUMMA'}">
                                                <c:set var="executionEventType" value="${moneyBox.executionEventType}"/>
                                                <c:set var="payDay" value="${moneyBox.longOfferPayDay}"/>

                                                <c:out value="${phiz:formatAmount(amount)}"/>&nbsp;
                                                <bean:message key="executionType.${executionEventType}.desc" bundle="moneyBoxBundle"/>
                                                <c:choose>
                                                    <c:when test="${executionEventType == 'ONCE_IN_WEEK'}">
                                                        <bean:message bundle="moneyBoxBundle" key="executionType.dayOfWeek.desc"/>&nbsp;
                                                        <c:out value="${phiz:dayNumberToString(payDay.weekDay)}"/>.
                                                    </c:when>
                                                    <c:when test="${executionEventType == 'ONCE_IN_MONTH'}">
                                                        <c:out value="${payDay.day}"/><bean:message bundle="moneyBoxBundle" key="executionType.dayOfMonth.desc"/>.
                                                    </c:when>
                                                    <c:when test="${executionEventType == 'ONCE_IN_QUARTER'}">
                                                        <c:out value="${payDay.day}"/><bean:message bundle="moneyBoxBundle" key="executionType.dayOfMonth.desc"/>&nbsp;
                                                        <c:out value="${payDay.monthInQuarter}"/><bean:message bundle="moneyBoxBundle" key="executionType.monthOfQuarter.desc"/>.
                                                    </c:when>
                                                    <c:when test="${executionEventType == 'ONCE_IN_YEAR'}">
                                                        <c:out value="${payDay.day}"/>&nbsp;
                                                        ${phiz:monthNumberToString(payDay.monthInYear)}.
                                                    </c:when>
                                                </c:choose>
                                            </c:when>

                                            <c:when test="${sumType == 'PERCENT_BY_ANY_RECEIPT' || sumType == 'PERCENT_BY_DEBIT'}">
                                                <c:out value="${moneyBox.percent}"/><bean:message key="sumType.${sumType}.desc" bundle="moneyBoxBundle"/>&nbsp;
                                                <c:choose>
                                                    <c:when test="${isClaim}">
                                                        <c:set var="maxSumWrite" value="${moneyBox.amount}"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:catch>
                                                            <c:set var="maxSumWrite" value="${link.autoSubscriptionInfo.maxSumWritePerMonth}"/>
                                                        </c:catch>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:if test="${not empty maxSumWrite}">
                                                    <bean:message key="sumType.max.desc" bundle="moneyBoxBundle"/>
                                                    <c:out value="${phiz:formatAmount(maxSumWrite)}"/>
                                                </c:if>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <c:choose>
                                        <c:when test="${not empty moneyBox.autoPayStatusType }">
                                            <c:set var="statusText" value="${moneyBox.autoPayStatusType.description}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="statusText" value="Ожидает подтверждения клиентом"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <td>
                                        <c:out value="${statusText}"/>
                                    </td>
                                </tr>
                            </logic:iterate>
                        </table>
                    </td>
                </tr>
            </table>
        </body>
    </html>
</tiles:insert>