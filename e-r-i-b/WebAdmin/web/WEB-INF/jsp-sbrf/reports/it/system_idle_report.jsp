<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/reports/it/system_idle/report">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set value="reportsBundle" var="bundle"/>
    <tiles:insert definition="reportsList">

        <tiles:put name="mainmenu" value="ReportsAndMonitoring"/>
        <tiles:put name="submenu" type="string" value="ITReport"/>
        <tiles:put name="pageTitle" type="string"><bean:message key="label.systemIdleITReport" bundle="${bundle}"/></tiles:put>
        <tiles:put name="data" type="string">
            <c:set var="date_report"><bean:message bundle="${bundle}" key="label.periodFromTo" arg0="${phiz:dateToString(form.reportStartDate.time)}" arg1="${phiz:dateToString(form.reportEndDate.time)}"/></c:set>

            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="text"><bean:message key="label.systemIdleITReport" bundle="${bundle}"/> <c:out value="${date_report}"/></tiles:put>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="data" bundle="${bundle}">
                        <sl:collectionItem  title="label.nextNumber">${listElement[0]}.</sl:collectionItem>
                        <sl:collectionItem  title="label.type" styleClass="align-center">
                            <c:set var="type" value="${listElement[1].type}"/>
                            <c:choose>
                                <c:when test="${type=='fullIdle'}">1</c:when>
                                <c:when test="${type=='partIdle'}">2</c:when>
                                <c:when test="${type=='jobIdle'}">3</c:when>
                            </c:choose>
                        </sl:collectionItem>
                        <sl:collectionItem  title="label.stopTime">
                            <fmt:formatDate value="${listElement[1].startDate.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
                        </sl:collectionItem>
                        <sl:collectionItem  title="label.startTime">
                            <fmt:formatDate value="${listElement[1].endDate.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
                        </sl:collectionItem>
                        <sl:collectionItem  title="label.idleTime">
                            <fmt:formatNumber value="${phiz:datesDiff(listElement[1].endDate, listElement[1].startDate)/1000/60}" pattern="0.00"/>
                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage"><bean:message key="label.empty3" bundle="${bundle}"/></tiles:put>
                <input name="id" type="hidden" value="${form.id}"/>
            </tiles:insert>

            <div class="paddLeft10">
                <div><h3>Признак</h3></div>
                <div>1 - Полная неработоспособность</div>
                <div>2 - Частичная неработоспособность</div>
                <div>3 - Не работает джоб</div>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>