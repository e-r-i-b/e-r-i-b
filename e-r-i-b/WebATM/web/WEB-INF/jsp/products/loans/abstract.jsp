<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ include file="/WEB-INF/jsp/types/status.jsp"%>
<html:form action="/private/loans/abstract">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="scheduleAbstract" value="${form.scheduleAbstract}"/>

    <tiles:insert definition="atm" flush="false">
        <c:if test="${empty scheduleAbstract.schedules and form.error}">
            <tiles:put name="status">${STATUS_CRITICAL_ERROR}</tiles:put>
            <tiles:put name="errorDescription">АБС не доступна. Получение информации не возможно.</tiles:put>
        </c:if>
        <tiles:put name="data">
            <c:if test="${not empty scheduleAbstract.schedules}">
                <sl:collection id="schedule" property="scheduleAbstract.schedules" model="xml-list" title="elements">
                    <sl:collectionItem title="element">

                        <state>${schedule.paymentState}</state>
                        <paymentNumber>${schedule.paymentNumber}</paymentNumber>
                        <tiles:insert definition="atmDateTimeType" flush="false">
                            <tiles:put name="name" value="date"/>
                            <tiles:put name="calendar" beanName="schedule" beanProperty="date"/>
                        </tiles:insert>
                        <tiles:insert definition="atmMoneyType" flush="false">
                            <tiles:put name="name" value="principalAmount"/>
                            <tiles:put name="money" beanName="schedule" beanProperty="principalAmount"/>
                        </tiles:insert>
                        <tiles:insert definition="atmMoneyType" flush="false">
                            <tiles:put name="name" value="interestsAmount"/>
                            <tiles:put name="money" beanName="schedule" beanProperty="interestsAmount"/>
                        </tiles:insert>
                        <tiles:insert definition="atmMoneyType" flush="false">
                            <tiles:put name="name" value="totalPaymentAmount"/>
                            <tiles:put name="money" beanName="schedule" beanProperty="totalPaymentAmount"/>
                        </tiles:insert>
                        <c:if test="${schedule.penaltyDateDebtItemMap != null}">
                            <c:set var="debt" value="${null}"/>
                            <c:forEach var="listElement" items="${schedule.penaltyDateDebtItemMap}" varStatus="lineInfo">
                                <c:set var="debt" value="${phiz:getMoneyOperation(debt, listElement.value.amount, '+')}"/>
                            </c:forEach>

                            <c:if test="${debt != null}">
                                <tiles:insert definition="atmMoneyType" flush="false">
                                    <tiles:put name="name" value="fines" />
                                    <tiles:put name="money" beanName="debt"/>
                                </tiles:insert>
                            </c:if>
                        </c:if>
                    </sl:collectionItem>
                </sl:collection>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>