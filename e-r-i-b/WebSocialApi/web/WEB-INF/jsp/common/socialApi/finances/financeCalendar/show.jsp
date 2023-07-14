<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/private/finances/financeCalendar/show">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="currentDate" value="${phiz:currentDate()}"/>
    <tiles:insert definition="financesStructure" flush="false">
        <tiles:put name="data">
            <sl:collection id="data" property="calendarData" model="xml-list" title="financeCalendar">
                <sl:collectionItem title="calendarDay">
                    <date>
                        <fmt:formatDate value="${data.date.time}" pattern="dd.MM.yyyy"/>
                    </date>
                    <c:if test="${data.dateType != 'FUTURE'}">
                        <outcome>${data.outcomeSum}</outcome>
                        <income>${data.incomeSum}</income>
                    </c:if>
                    <c:if test="${data.dateType != 'PAST'}">
                        <autoSubscriptionsCount>${data.autoSubscriptionsCount}</autoSubscriptionsCount>
                        <paymentsAmount>${data.paymentsAmount}</paymentsAmount>
                    </c:if>
                    <c:if test="${data.dateType == 'TODAY'}">
                        <cardsBalance>${form.cardsBalance}</cardsBalance>
                    </c:if>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>