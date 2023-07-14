<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<html:form action="/private/autosubscriptions/abstract">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty form.scheduleItems}">
                <sl:collection id="scheduleItem" property="scheduleItems" model="xml-list" title="elements">
                    <sl:collectionItem title="element">
                        <id>${scheduleItem.number}</id>
                        <c:set var="dateTime" value="${scheduleItem.date}"/>
                        <tiles:insert definition="mobileDateTimeType" flush="false">
                            <tiles:put name="name" value="date"/>
                            <tiles:put name="calendar" beanName="dateTime"/>
                        </tiles:insert>
                        <c:if test="${not empty scheduleItem.summ}">
                            <tiles:insert definition="mobileMoneyType" flush="false">
                                <tiles:put name="name" value="amount"/>
                                <tiles:put name="decimal" value="${scheduleItem.summ}"/>
                                <tiles:put name="currencySign" value="${phiz:getCurrencySign('RUB')}"/>
                                <tiles:put name="currencyCode" value="RUB"/>
                            </tiles:insert>
                        </c:if>
                        <c:if test="${not empty scheduleItem.commission}">
                            <tiles:insert definition="mobileMoneyType" flush="false">
                                <tiles:put name="name" value="commission"/>
                                <tiles:put name="decimal" value="${scheduleItem.commission}"/>
                                <tiles:put name="currencySign" value="${phiz:getCurrencySign('RUB')}"/>
                                <tiles:put name="currencyCode" value="RUB"/>
                                </tiles:insert>
                        </c:if>
                        <c:if test="${not empty scheduleItem.status}">
                             <status>${scheduleItem.status}</status>
                        </c:if>
                        <c:if test="${not empty scheduleItem.rejectionCause}">
                             <rejectionCause><c:out value="${scheduleItem.rejectionCause}"/></rejectionCause>
                        </c:if>
                    </sl:collectionItem>
                </sl:collection>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>