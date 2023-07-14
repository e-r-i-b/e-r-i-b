<%--
  Created by IntelliJ IDEA.
  User: saharnova
  Date: 14.10.14
  Time: 18:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<html:form action="/private/moneybox/scheduleReport/print">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="moneyboxBundle"/>

    <c:set var="moneyBox" value="${form.link.value}"/>
    <c:set var="items" value="${form.scheduleItems}"/>
    <c:set var="card" value="${form.cardLink}"/>
    <c:set var="account" value="${form.accountLink}"/>
    <c:set var="sumType" value="${moneyBox.sumType}"/>

    <tiles:insert definition="printDoc">
        <tiles:put name="data" type="string">
            <c:if test="${moneyBox != null}">
                <table style="width:70%">
                    <tr>
                        <td style="font-size:11pt;text-align:center;padding-top:20px; padding-bottom: 20px; font: bold 11pt Arial;">
                            <h3><bean:message bundle="${bundle}" key="print.title"/></h3>
                        </td>
                    </tr>
                </table>

                <table class="moneyBoxPrintTitleTable">
                    <tr>
                        <td>
                            <bean:message bundle="${bundle}" key="print.name"/> <c:out value="${moneyBox.friendlyName}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <bean:message bundle="${bundle}" key="print.card"/> <c:out value="${phiz:getCutCardNumber(card.number)} [${card.name}]"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <bean:message bundle="${bundle}" key="print.account"/> <c:out value="${phiz:getFormattedAccountNumber(account.number)} [${account.name}]"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <bean:message bundle="${bundle}" key="print.type"/> <c:out value="${sumType.description}"/>
                        </td>
                    </tr>
                </table>
                <br>
                <table cellpadding="0" cellspacing="0" style="width:70%" class="moneyBoxPrintGraphTable">
                    <tr class="tblInfHeaderAbstrPrint">
                        <td class="docTdTopBorder" style="text-align:left;"><bean:message bundle="${bundle}" key="print.graphic.data"/></td>
                        <td class="docTdTopBorder" style="text-align:left;"><bean:message bundle="${bundle}" key="print.graphic.summ"/></td>
                        <td class="docTdTopBorder" style="text-align:left;"><bean:message bundle="${bundle}" key="print.graphic.state"/></td>
                        <td class="docTdTopBorder" style="text-align:left;"><bean:message bundle="${bundle}" key="print.graphic.reasonError"/></td>
                    </tr>
                    <c:forEach items="${items}" var="item">
                        <tr>
                            <td class="docTdBorderSecond textPadding" style="text-align:left;">
                                <c:out value="${phiz:formatDateToStringOnPattern(item.date, 'dd.MM.yyyy HH:mm')}"/>
                            </td>
                            <td class="docTdBorderSecond textPadding" style="text-align:left;">
                                <c:if test="${not empty item.summ}">
                                    <c:choose>
                                        <c:when test="${card.currency != null}">
                                            <c:out value="${phiz:formatBigDecimal(item.summ)} ${phiz:getCurrencySign(card.currency.code)}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${phiz:formatBigDecimal(item.summ)} ${phiz:getCurrencySign('RUB')}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </td>
                            <td class="docTdBorderSecond textPadding" style="text-align:left;">
                                <c:if test="${not empty item.status}">
                                    <bean:message bundle="${bundle}" key="payment.state.${item.status}"/>
                                </c:if>
                            </td>
                            <td class="docTdBorderSecond textPadding" style="text-align:left;">
                                <c:if test="${not empty item.rejectionCause}">
                                    <c:out value="${item.rejectionCause}"/>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>