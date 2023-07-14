<%--
  Created by IntelliJ IDEA.
  User: saharnova
  Date: 18.09.14
  Time: 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- ������ ��� ������� �� �������� ��������� ���������� �� ������/����.
��� ������� �������� ����� ������� ���������� ���� ����������: status = NEW, onclick. ���� ����������, ����� ��� id ��������, �� � ��� ���� ��� ����������.

id              - ������������� ���� �������. �������������� �������� (� ���� ������� <div id="moneyBox${id}" .../>)
status          - ������ �������. ��������� ��������: NEW (��� �������� ����� �������), ACTIVE (����������� �������), DRAFT (��������), PAUSED (���������������� �������). ������������ ��������.
typeSum         - ��� ����� ���������� �������. ��������� ��������: FIXED_SUMMA (�� ������������� �����), PERCENT_BY_ANY_RECEIPT (������� �� ����������), PERCENT_BY_DEBIT (������� �� �������). ������������ ��������.
cardName        - ��� ����� ��������, � ������� ����������� �������. ������������ ��������.
cardNumber      - ����� �����. ������������ ��������.
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
<c:if test="${status == 'NEW' || status == 'ACTIVE'|| status == 'DRAFT' || status == 'PAUSED'}">
    <c:set var="bundle" value="moneyboxBundle"/>
    <c:set var="cardMaskNumber" value="${phiz:getCutCardNumber(cardNumber)}"/>
    <c:set var="symbolCur" value="empty"/>
    <c:set var="upSymbolCur" value="empty"/>
    <c:if test="${typeSum == 'FIXED_SUMMA' || ((typeSum == 'PERCENT_BY_ANY_RECEIPT' || typeSum == 'PERCENT_BY_DEBIT') && not empty maxAmount)}">
        <%-- ��������� ������ ������ �� ISO ����--%>
        <c:set var="symbolCur" value="${phiz:getCurrencySign(codCurrency)}"/>
        <c:choose>
            <c:when test="${symbolCur == '���.'}">
                <c:set var="upSymbolCur" value="�."/>
            </c:when>
            <c:otherwise>
                <c:set var="upSymbolCur" value="${symbolCur}"/>
            </c:otherwise>
        </c:choose>
        <%-- ��������� ������ ������ � ������ ������� --%>
        <c:choose>
            <c:when test="${typeSum == 'FIXED_SUMMA'}">
                <c:set var="sum" value="${amount}"/>
            </c:when>
            <c:otherwise>
                <c:set var="sum" value="${maxAmount}"/>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${status == 'ACTIVE'}">
                <c:choose>
                    <c:when test="${phiz:compareLength(sum, '100000') < 0}">
                        <c:set var="sizeFont" value="25"/>
                    </c:when>
                    <c:when test="${phiz:compareLength(sum, '1000000') < 0}">
                        <c:set var="sizeFont" value="22"/>
                    </c:when>
                    <c:when test="${phiz:compareLength(sum, '10000000') < 0}">
                        <c:set var="sizeFont" value="19"/>
                    </c:when>
                    <c:when test="${phiz:compareLength(sum, '100000000') < 0}">
                        <c:set var="sizeFont" value="17"/>
                    </c:when>
                    <c:when test="${phiz:compareLength(sum, '1000000000') < 0}">
                        <c:set var="sizeFont" value="15"/>
                    </c:when>
                    <c:when test="${phiz:compareLength(sum, '100000000000') < 0}">
                        <c:set var="sizeFont" value="13"/>
                    </c:when>
                    <c:when test="${phiz:compareLength(sum, '10000000000000') < 0}">
                        <c:set var="sizeFont" value="11"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="sizeFont" value="9"/>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:when test="${status == 'DRAFT' || status == 'PAUSED'}">
                <c:choose>
                    <c:when test="${phiz:compareLength(sum, '10000') < 0}">
                        <c:set var="sizeFont" value="25"/>
                    </c:when>
                    <c:when test="${phiz:compareLength(sum, '100000') < 0}">
                        <c:set var="sizeFont" value="23"/>
                    </c:when>
                    <c:when test="${phiz:compareLength(sum, '1000000') < 0}">
                        <c:set var="sizeFont" value="21"/>
                    </c:when>
                    <c:when test="${phiz:compareLength(sum, '100000000') < 0}">
                        <c:set var="sizeFont" value="17"/>
                    </c:when>
                    <c:when test="${phiz:compareLength(sum, '1000000000') < 0}">
                        <c:set var="sizeFont" value="15"/>
                    </c:when>
                    <c:when test="${phiz:compareLength(sum, '10000000000') < 0}">
                        <c:set var="sizeFont" value="13"/>
                    </c:when>
                    <c:when test="${phiz:compareLength(sum, '1000000000000') < 0}">
                        <c:set var="sizeFont" value="12"/>
                    </c:when>
                    <c:when test="${phiz:compareLength(sum, '10000000000000') < 0}">
                        <c:set var="sizeFont" value="10"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="sizeFont" value="9"/>
                    </c:otherwise>
                </c:choose>
            </c:when>
        </c:choose>
        <%-- ����������� ����� � ����������� �������� --%>
        <c:set var="sum" value="${phiz:getFormatAmountWithNoCentsGrouping(sum,',')}"/>
    </c:if>

    <c:set var="description">
        <div class="moneyBoxTemplateDesc">
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
                    ${sum} ${symbolCur}
                    <bean:message bundle="${bundle}" key="template.description.period.${period}"/>
                    <c:choose>
                        <c:when test="${period == 'ONCE_IN_WEEK'}">
                            <bean:message bundle="${bundle}" key="template.description.period.dayOfWeek.by"/> ${phiz:dayNumberToString(longOfferPayDay.weekDay)}<bean:message bundle="${bundle}" key="template.description.typeSum.FIXED_SUMMA"/>
                        </c:when>
                        <c:when test="${period == 'ONCE_IN_MONTH'}">
                            <c:out value="${longOfferPayDay.day}"/><bean:message bundle="${bundle}" key="template.description.period.dayOfMonth"/><bean:message bundle="${bundle}" key="template.description.typeSum.FIXED_SUMMA"/>
                        </c:when>
                        <c:when test="${period == 'ONCE_IN_QUARTER'}">
                            <c:out value="${longOfferPayDay.day}"/><bean:message bundle="${bundle}" key="template.description.period.dayOfMonth"/>
                            <c:out value="${longOfferPayDay.monthInQuarter}"/><bean:message bundle="${bundle}" key="template.description.period.monthOfQuarter"/><bean:message bundle="${bundle}" key="template.description.typeSum.FIXED_SUMMA"/>
                        </c:when>
                        <c:when test="${period == 'ONCE_IN_YEAR'}">
                            <c:out value="${longOfferPayDay.day}"/> ${phiz:monthNumberToString(longOfferPayDay.monthInYear)}<bean:message bundle="${bundle}" key="template.description.typeSum.FIXED_SUMMA"/>
                        </c:when>
                    </c:choose>

                    ${cardName} ${cardMaskNumber}
                    <c:if test="${status == 'ACTIVE'}">
                        <div class="moneyBoxTemplateDescNearestRefill">
                            <bean:message bundle="${bundle}" key="template.description.nearestRefill"/>
                            ${phiz:formatDayWithStringMonthWithoutNought(dateNearPay)}.
                        </div>
                    </c:if>
                </c:when>
                <c:when test="${typeSum == 'PERCENT_BY_ANY_RECEIPT' || typeSum == 'PERCENT_BY_DEBIT'}">
                    ${percent}%
                    <bean:message bundle="${bundle}" key="template.description.typeSum.${typeSum}"/>
                    <c:choose>
                        <c:when test="${not empty maxAmount}">
                            ${cardName} ${cardMaskNumber}<bean:message bundle="${bundle}" key="template.description.maxSum"/>
                            ${sum} ${symbolCur}
                        </c:when>
                        <c:otherwise>
                            ${cardName} ${cardMaskNumber}
                        </c:otherwise>
                    </c:choose>
                </c:when>
            </c:choose>
        </div>

    </c:set>

    <div id="moneyBox${id}" class="moneyBoxAccountAll moneyBoxCloudBlock <c:if test="${status == 'NEW'}">moneyBoxNewCloud</c:if>">
        <c:choose>
            <c:when test="${status == 'NEW'}">
                <div class="moneyBoxIconImg moneyBoxIconImgNew"></div>
                <div class="clear"></div>
                <span class="moneyBoxTemplateTitle word-wrap" onclick="${onclick}">
                    <bean:message bundle="${bundle}" key="template.title.new"/>
                </span>
            </c:when>
            <%------------------------------------------------%>
            <c:when test="${status == 'ACTIVE'}">
                <div class="moneyBoxImgActiveCloud">
                    <c:choose>
                        <c:when test="${typeSum == 'FIXED_SUMMA'}">
                            <div class="moneyBoxIconImg moneyBoxIconImgActiveFixPrice">
                                <span class="moneyBoxTemplateInCircleActive moneyBoxTemplateInCircleAmount" style="font-size:${sizeFont}px">${sum} ${upSymbolCur}</span>
                            </div>
                        </c:when>
                        <%--   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   --%>
                        <c:when test="${typeSum == 'PERCENT_BY_ANY_RECEIPT'}">
                            <div class="moneyBoxIconImg moneyBoxIconImgActivePerReceipt">
                                <span class="moneyBoxTemplateInCircleActive moneyBoxTemplateInCirclePercent">${percent}%</span>
                            </div>
                        </c:when>
                        <%--   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   --%>
                        <c:when test="${typeSum == 'PERCENT_BY_DEBIT'}">
                            <div class="moneyBoxIconImg moneyBoxIconImgActivePerDebit">
                                <span class="moneyBoxTemplateInCircleActive moneyBoxTemplateInCirclePercent">${percent}%</span>
                            </div>
                        </c:when>
                    </c:choose>
                </div>
                <div class="clear"></div>
                <div class="moneyBoxDescBlock">
                    <span class="moneyBoxTemplateTitle word-wrap" onclick="${onclick}">${title}</span>
                    ${description}
                </div>
            </c:when>
            <%------------------------------------------------%>
            <c:when test="${status == 'DRAFT' || status == 'PAUSED'}">
                <div class="moneyBoxImgPauseCloud">
                    <div class="moneyBoxIconImg moneyBoxIconImgPause">
                        <c:choose>
                            <c:when test="${typeSum == 'FIXED_SUMMA'}">
                                <span class="moneyBoxTemplateInCirclePause moneyBoxTemplateInCircleAmount" style="font-size:${sizeFont}px">${sum} ${upSymbolCur}</span>
                            </c:when>
                            <c:when test="${typeSum == 'PERCENT_BY_ANY_RECEIPT' || typeSum == 'PERCENT_BY_DEBIT'}">
                                <span class="moneyBoxTemplateInCirclePause moneyBoxTemplateInCirclePercent">${percent}%</span>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
                <div class="clear"></div>
                <div class="moneyBoxDescBlock">
                    <span class="moneyBoxTemplateTitle word-wrap" onclick="${onclick}">${title}</span>
                    ${description}
                </div>
            </c:when>
        </c:choose>
    </div>
</c:if>