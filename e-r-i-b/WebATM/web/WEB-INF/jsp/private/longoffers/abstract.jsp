<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<html:form action="/private/longoffers/abstract">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty form.scheduleItems}">
                <sl:collection id="scheduleItem" property="scheduleItems" model="xml-list" title="elements">
                    <sl:collectionItem title="element">
                        <c:set var="dateTime" value="${scheduleItem.date}"/>
                        <c:if test="${not empty dateTime}">
                            <tiles:insert definition="atmDateTimeType" flush="false">
                                <tiles:put name="name" value="date"/>
                                <tiles:put name="calendar" beanName="dateTime"/>
                            </tiles:insert>
                        </c:if>
                        <tiles:insert definition="atmMoneyType" flush="false">
                            <tiles:put name="name" value="amount"/>
                            <tiles:put name="money" beanName="scheduleItem" beanProperty="amount"/>
                        </tiles:insert>
                        <status>${scheduleItem.state}</status>
                    </sl:collectionItem>
                </sl:collection>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>