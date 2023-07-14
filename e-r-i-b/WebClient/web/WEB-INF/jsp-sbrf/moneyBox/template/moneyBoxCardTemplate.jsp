<%--
  Created by IntelliJ IDEA.
  User: saharnova
  Date: 18.09.14
  Time: 17:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<%-- Шаблон для копилки на странице детальной информации по карте.
id              - идентификатор всей копилки. Необязательный параметр (у всех копилок <div id="moneyBox${id}" .../>)
status          - статус копилки. Возможные значения: ACTIVE (исполняемая копилка), DRAFT (черновик), PAUSED (приостановленная копилка). Обязательный параметр.
typeSum         - тип суммы пополнения копилки. Возможные значения: FIXED_SUMMA (на фиксированную сумму), PERCENT_BY_ANY_RECEIPT (процент от зачисления), PERCENT_BY_DEBIT (процент от расхода). Обязательный параметр.
title           - название копилки. Обязательный параметр.
goal            - true/false. true = цель (по умолчанию false). Небязательный параметр (для приостановленных подписок вообще не учитывается).
onclick         - функция для действия при щелчке по заголовку. Обязательный параметр.

Параметры при типе FIXED_SUMMA.
amount          - сумма списания (без копеек и пробелов - 1000). Обязательный параметр.
codCurrency     - код валюты ISO(RUB, RUR = руб.; USD = $; EUR = €). Обязательный параметр.
period          - период списания. Возможные варианты: ONCE_IN_WEEK(раз в неделю"), ONCE_IN_MONTH(раз в месяц), ONCE_IN_QUARTER(раз в квартал), ONCE_IN_YEAR(раз в год). Обязательный параметр.
dateNearPay     - дата ближайщего платежа. Тип Calendar. Если передавать через beanName="откуда" beanProperty="свойство", то свойство не должно быть null. Обязательный параметр.
longOfferPayDay - структура - режим исполнения пополнений копилки.

Параметры при типах PERCENT_BY_ANY_RECEIPT и PERCENT_BY_DEBIT.
percent         - процент от суммы зачисления/расхода. Обязательный параметр.
maxAmount       - максимальная возможная сумма списания (например 1000). Так же необходимо указать код валюты через параметр codCurrency. Необязательные параметры.
--%>

<tiles:importAttribute/>
<c:if test="${status == 'ACTIVE'|| status == 'DRAFT' || status == 'PAUSED'}">
    <c:set var="bundle" value="moneyboxBundle"/>
    <c:set var="space" value="&ensp;"/>
    <c:set var="symbolCur" value="empty"/>
    <c:set var="upSymbolCur" value="empty"/>
    <c:if test="${typeSum == 'FIXED_SUMMA' || ((typeSum == 'PERCENT_BY_ANY_RECEIPT' || typeSum == 'PERCENT_BY_DEBIT') && not empty maxAmount)}">
        <%-- Вычисляем символ валюты по ISO коду--%>
        <c:set var="symbolCur" value="${phiz:getCurrencySign(codCurrency)}"/>
        <%-- Форматируем сумму с разделением разрядов --%>
        <c:choose>
            <c:when test="${typeSum == 'FIXED_SUMMA'}">
                <c:set var="sum" value="${amount}"/>
            </c:when>
            <c:otherwise>
                <c:set var="sum" value="${maxAmount}"/>
            </c:otherwise>
        </c:choose>
    </c:if>

    <div id="moneyBoxOne${id}" class="moneyBoxCardOne">

            <span class="moneyBoxTemplateCardTitle word-wrap" onclick="${onclick}">${title}</span>

            <div class="moneyBoxTemplateCardDesc">
                <c:choose>
                    <c:when test="${status == 'DRAFT' || status == 'PAUSED'}">
                        <span class="moneyBoxTemplateDescPause"><bean:message bundle="${bundle}" key="template.description.status.pause"/></span>
                    </c:when>
                    <c:when test="${status == 'ACTIVE' && not goal}">
                        <bean:message bundle="${bundle}" key="template.description.status.active.account"/>
                    </c:when>
                    <c:when test="${status == 'ACTIVE' && goal}">
                        <bean:message bundle="${bundle}" key="template.description.status.active.goal"/>
                    </c:when>
                </c:choose>
                <c:choose>
                    <c:when test="${typeSum == 'FIXED_SUMMA'}">
                        ${sum}${space}${symbolCur}
                        <bean:message bundle="${bundle}" key="template.description.period.${period}"/>
                        <c:choose>
                            <c:when test="${period == 'ONCE_IN_WEEK'}">
                                ${phiz:getDayOfWeekWord(dateNearPay)}.
                            </c:when>
                            <c:when test="${period == 'ONCE_IN_MONTH'}">
                                ${phiz:getDayOfDateWithoutNought(dateNearPay)}<bean:message bundle="${bundle}" key="template.description.period.dayOfMonth"/>.
                            </c:when>
                            <c:when test="${period == 'ONCE_IN_QUARTER'}">
                                ${phiz:getDayOfDateWithoutNought(dateNearPay)}<bean:message bundle="${bundle}" key="template.description.period.dayOfMonth"/>
                                ${phiz:getMonthOfQuarter(dateNearPay)}<bean:message bundle="${bundle}" key="template.description.period.monthOfQuarter"/>.
                            </c:when>
                            <c:when test="${period == 'ONCE_IN_YEAR'}">
                                ${phiz:formatDayWithStringMonthWithoutNought(dateNearPay)}.
                            </c:when>
                        </c:choose>
                        <c:if test="${status == 'ACTIVE'}">
                            <div class="moneyBoxTemplateCardDescNearestRefill">
                                <bean:message bundle="${bundle}" key="template.description.nearestRefill"/>
                                    ${phiz:formatDayWithStringMonthWithoutNought(dateNearPay)}.
                            </div>
                        </c:if>
                    </c:when>
                    <c:when test="${typeSum == 'PERCENT_BY_ANY_RECEIPT' || typeSum == 'PERCENT_BY_DEBIT'}">
                        ${percent}%
                        <c:set var="maxSumm" value="."/>
                        <c:if test="${not empty maxAmount}">
                            <c:set var="maxSumm">
                                <bean:message bundle="${bundle}" key="template.description.maxSum"/>
                                <c:choose>
                                    <c:when test="${symbolCur == 'руб.'}">
                                        ${sum}${space}${symbolCur}
                                    </c:when>
                                    <c:otherwise>
                                        ${sum}${space}${symbolCur}.
                                    </c:otherwise>
                                </c:choose>
                            </c:set>
                        </c:if>
                        <c:choose>
                            <c:when test="${typeSum == 'PERCENT_BY_ANY_RECEIPT'}">
                                <bean:message bundle="${bundle}" key="template.description.typeSum.PERCENT_BY_ANY_RECEIPT"/>${maxSumm}
                            </c:when>
                            <c:when test="${typeSum == 'PERCENT_BY_DEBIT'}">
                                <bean:message bundle="${bundle}" key="template.description.typeSum.PERCENT_BY_DEBIT"/>${maxSumm}
                            </c:when>
                        </c:choose>

                    </c:when>
                </c:choose>
            </div>

    </div>
</c:if>
