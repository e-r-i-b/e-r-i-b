<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>

<c:choose>
    <%-- Автоплатеж по выставленному счету (sumType == BY_BILLING) --%>
    <c:when test="${autoSubscription.sumType == 'BY_BILLING'}">
        <bean:message bundle="financesBundle" key="lable.finance.calendar.autoSubscription.byBillingPayment.${autoSubscription.executionEventType}.prefix"/>
        <bean:message bundle="financesBundle" key="lable.finance.calendar.autoSubscription.byBillingPayment.suffix"/>
    </c:when>
    <c:otherwise>
        <bean:message bundle="financesBundle" key="lable.finance.calendar.autoSubscription.regularPayment"/>
        <br/>

        <bean:message bundle="financesBundle" key="lable.finance.calendar.autoSubscription.regularPayment.${autoSubscription.executionEventType}.prefix"/>
        <c:choose>
            <c:when test="${autoSubscription.executionEventType == 'ONCE_IN_WEEK'}">
                <c:out value="${phiz:getDayOfWeekWord(autoSubscription.payDate)}"/>
            </c:when>
            <c:when test="${autoSubscription.executionEventType == 'ONCE_IN_MONTH'}">
                <c:out value="${phiz:getDayOfDateWithoutNought(autoSubscription.payDate)}"/><bean:message bundle="financesBundle" key="lable.finance.calendar.autoSubscription.periodicity.dayOfMonth"/>
            </c:when>
            <c:when test="${autoSubscription.executionEventType == 'ONCE_IN_QUARTER'}">
                <c:out value="${phiz:getDayOfDateWithoutNought(autoSubscription.payDate)}"/><bean:message bundle="financesBundle" key="lable.finance.calendar.autoSubscription.periodicity.dayOfMonth"/>
                <c:out value="${phiz:getMonthOfQuarter(autoSubscription.payDate)}"/><bean:message bundle="financesBundle" key="lable.finance.calendar.autoSubscription.periodicity.monthOfQuarter"/>
            </c:when>
            <c:when test="${autoSubscription.executionEventType == 'ONCE_IN_HALFYEAR'}">
                <c:out value="${phiz:getDayOfDateWithoutNought(autoSubscription.payDate)}"/><bean:message bundle="financesBundle" key="lable.finance.calendar.autoSubscription.periodicity.dayOfMonth"/>
                <c:out value="${phiz:getMonthOfHalfYear(autoSubscription.payDate)}"/><bean:message bundle="financesBundle" key="lable.finance.calendar.autoSubscription.periodicity.monthOfHalfYear"/>
            </c:when>
            <c:when test="${autoSubscription.executionEventType == 'ONCE_IN_YEAR'}">
                <c:out value="${phiz:formatDayWithStringMonthWithoutNought(autoSubscription.payDate)}"/>
            </c:when>
        </c:choose>
    </c:otherwise>
</c:choose>