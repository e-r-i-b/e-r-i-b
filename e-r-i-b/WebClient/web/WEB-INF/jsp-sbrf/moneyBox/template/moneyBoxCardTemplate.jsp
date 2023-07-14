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

<%-- ������ ��� ������� �� �������� ��������� ���������� �� �����.
id              - ������������� ���� �������. �������������� �������� (� ���� ������� <div id="moneyBox${id}" .../>)
status          - ������ �������. ��������� ��������: ACTIVE (����������� �������), DRAFT (��������), PAUSED (���������������� �������). ������������ ��������.
typeSum         - ��� ����� ���������� �������. ��������� ��������: FIXED_SUMMA (�� ������������� �����), PERCENT_BY_ANY_RECEIPT (������� �� ����������), PERCENT_BY_DEBIT (������� �� �������). ������������ ��������.
title           - �������� �������. ������������ ��������.
goal            - true/false. true = ���� (�� ��������� false). ������������� �������� (��� ���������������� �������� ������ �� �����������).
onclick         - ������� ��� �������� ��� ������ �� ���������. ������������ ��������.

��������� ��� ���� FIXED_SUMMA.
amount          - ����� �������� (��� ������ � �������� - 1000). ������������ ��������.
codCurrency     - ��� ������ ISO(RUB, RUR = ���.; USD = $; EUR = �). ������������ ��������.
period          - ������ ��������. ��������� ��������: ONCE_IN_WEEK(��� � ������"), ONCE_IN_MONTH(��� � �����), ONCE_IN_QUARTER(��� � �������), ONCE_IN_YEAR(��� � ���). ������������ ��������.
dateNearPay     - ���� ���������� �������. ��� Calendar. ���� ���������� ����� beanName="������" beanProperty="��������", �� �������� �� ������ ���� null. ������������ ��������.
longOfferPayDay - ��������� - ����� ���������� ���������� �������.

��������� ��� ����� PERCENT_BY_ANY_RECEIPT � PERCENT_BY_DEBIT.
percent         - ������� �� ����� ����������/�������. ������������ ��������.
maxAmount       - ������������ ��������� ����� �������� (�������� 1000). ��� �� ���������� ������� ��� ������ ����� �������� codCurrency. �������������� ���������.
--%>

<tiles:importAttribute/>
<c:if test="${status == 'ACTIVE'|| status == 'DRAFT' || status == 'PAUSED'}">
    <c:set var="bundle" value="moneyboxBundle"/>
    <c:set var="space" value="&ensp;"/>
    <c:set var="symbolCur" value="empty"/>
    <c:set var="upSymbolCur" value="empty"/>
    <c:if test="${typeSum == 'FIXED_SUMMA' || ((typeSum == 'PERCENT_BY_ANY_RECEIPT' || typeSum == 'PERCENT_BY_DEBIT') && not empty maxAmount)}">
        <%-- ��������� ������ ������ �� ISO ����--%>
        <c:set var="symbolCur" value="${phiz:getCurrencySign(codCurrency)}"/>
        <%-- ����������� ����� � ����������� �������� --%>
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
                                    <c:when test="${symbolCur == '���.'}">
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
