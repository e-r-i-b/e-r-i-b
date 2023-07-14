<%--
  Created by IntelliJ IDEA.
  User: mihaylov
  Date: 01.08.2010
  Time: 19:39:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/loans/payments/print">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="user" value="${form.user}"/>
<c:set var="loanLink" value="${form.loanLink}" scope="request"/>
<c:set var="loan" value="${loanLink.loan}" scope="request"/>
<c:set var="loanOffice" value="${loan.office}" scope="request"/>
<c:set var="loanParentOffice" value="${phiz:getTerBank(loanOffice.code)}" scope="request"/>
<c:set var="scheduleAbstract" value="${form.scheduleAbstract}" scope="request"/>


<tiles:insert definition="printDoc">
<tiles:put name="data" type="string">
<c:if test="${loanLink != null}">
    <br/>
    <table width="100%">
        <tr>
            <td>
                Сбербанк России ОАО
            </td>
        </tr>
        <tr>
            <td>
                ${loanParentOffice.name}
            </td>
        </tr>
        <tr>
            <td>
                ${loanOffice.name}
            </td>
        </tr>

        <tr>
            <td style="font-size:11pt;text-align:center;padding-top:20mm;padding-right:40mm;font: bold 11pt Arial;">
                График погашения кредита за период с <bean:write name="form" property="filter.fromDate" format="dd.MM.yy"/> по <bean:write name="form" property="filter.toDate" format="dd.MM.yy"/>
            </td>
        </tr>
        <tr>
            <td>
                ФИО клиента:&nbsp;
                <c:if test="${not empty user}">
                    <c:out value="${phiz:getFormattedPersonName(user.firstName, user.surName, user.patrName)}"/>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>
                Тип кредита: ${loan.description}
            </td>
        </tr>
        <tr>
            <td>
                Название кредита: <bean:write name="loanLink" property="name"/>
            </td>
        </tr>
        <tr>
            <td>
                Сумма: ${phiz:formatAmount(loan.loanAmount)}
            </td>
        </tr>
        <c:if test="${loan.rate != null}">
            <tr>
                <td>
                    Процент: ${loan.rate}%
                </td>
            </tr>
        </c:if>
        <tr>
            <td>
                Уже оплачено: ${phiz:formatAmount(scheduleAbstract.doneAmount)}
            </td>
        </tr>
        <tr>
            <td>
                Осталось оплатить: ${phiz:formatAmount(scheduleAbstract.remainAmount)}
            </td>
        </tr>
        <tr>
            <td>
                Дата окончания: ${phiz:formatDateWithStringMonth(loan.termEnd)}
            </td>
        </tr>
        <tr>
            <td>
                Штрафы и Пени: ${phiz:formatAmount(scheduleAbstract.penaltyAmount)}
            </td>
        </tr>
    </table>
    <c:set var="showOverpayment" value="false"/>
    <c:forEach items="${scheduleAbstract.schedules}" var="schedule">
        <c:if test="${not empty schedule.overpayment}">
            <c:set var="showOverpayment" value="true"/>
        </c:if>
    </c:forEach>
    <br/>
    <table cellpadding="0" cellspacing="0" style="width:70%;">
        <tr class="tblInfHeaderAbstrPrint">
            <td class="docTdLeftTopBorder" style="text-align:center;">№</td>
            <td class="docTdTopBorder" style="text-align:center;">Дата оплаты</td>
            <td class="docTdTopBorder" style="text-align:center;">Основной долг</td>
            <td class="docTdTopBorder" style="text-align:center;">Проценты</td>
            <td class="docTdTopBorder" style="text-align:center;">Пени и штрафы</td>
            <td class="docTdTopBorder" style="text-align:center;">Сумма оплаты</td>
            <c:if test="${showOverpayment}">
                <td class="docTdTopBorder" style="text-align:center;">Переплата</td>
            </c:if>
            <td class="docTdTopBorder" style="text-align:center;">Состояние</td>
            <c:set var="index" value="${index+1}"/>
        </tr>
    <c:set var="index" value="1"/>
    <c:forEach items="${scheduleAbstract.schedules}" var="schedule">
        <tr>
            <td class="docTdBorderSecond textPadding" style="text-align:left;">${index}</td>
            <td class="docTdBorderSecond textPadding" style="text-align:left;"><bean:write name='schedule' property="date.time" format="dd.MM.yyyy"/></td>
            <td class="docTdBorderSecond textPadding" style="text-align:left;">${phiz:formatAmount(schedule.principalAmount)}</td>
            <td class="docTdBorderSecond textPadding" style="text-align:left;">${phiz:formatAmount(schedule.interestsAmount)}</td>
            <c:set var="debt" value="${null}"/>
            <c:if test="${schedule.penaltyDateDebtItemMap != null}">
                <c:forEach var="listElement" items="${schedule.penaltyDateDebtItemMap}" varStatus="lineInfo">
                    <c:set var="debt" value="${phiz:getMoneyOperation(debt, listElement.value.amount, '+')}"/>
                </c:forEach>
            </c:if>
            <td class="docTdBorderSecond textPadding" style="text-align:left;">${phiz:formatAmount(debt)}</td>
            <td class="docTdBorderSecond textPadding" style="text-align:left;">${phiz:formatAmount(schedule.totalPaymentAmount)}</td>
            <c:if test="${showOverpayment}">
                <td class="docTdBorderSecond textPadding" style="text-align:left;">${phiz:formatAmount(schedule.overpayment)}</td>
            </c:if>
            <td class="docTdBorderSecond textPadding" style="text-align:left;">${schedule.paymentState.description}</td>
            <c:set var="index" value="${index+1}"/>
        </tr>
    </c:forEach>
    </table>
</c:if>
</tiles:put>
</tiles:insert>
</html:form>