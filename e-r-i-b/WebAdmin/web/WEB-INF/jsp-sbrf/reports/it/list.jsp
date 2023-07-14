<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/reports/it/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set value="reportsBundle" var="bundle"/>
    <tiles:insert definition="reportsList">

        <tiles:put name="mainmenu" value="ReportsAndMonitoring"/>
        <tiles:put name="submenu" type="string" value="ITReport"/>
        <tiles:put name="pageTitle" type="string"><bean:message key="label.title" bundle="${bundle}"/></tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                    <tiles:put name="buttons" >
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.remove"/>
		                    <tiles:put name="commandHelpKey" value="button.remove.help"/>
		                    <tiles:put name="bundle" value="reportsBundle"/>
		                    <tiles:put name="isDefault" value="false"/>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="text"><bean:message key="label.history.it" bundle="${bundle}"/></tiles:put>

                    <tiles:put name="grid">
                        <sl:collection id="listElement" model="list" property="data" selectType="checkbox" selectName="selectedIds" selectProperty="id" bundle="${bundle}" >
                            <sl:collectionItem title="label.number" property="id">
                                <c:set var="link" value="${listElement.linkToReport}"/>
                                <c:if test="${not empty link}">
                                    <sl:collectionItemParam id="action" value="${link}"/>
                                </c:if>
                            </sl:collectionItem>
                            <sl:collectionItem   title="label.createDate">
                                <c:if test="${not empty listElement and not empty listElement.date}">
                                    <bean:write name="listElement" property="date.time" format="dd.MM.yyyy" filter="true" />
                                </c:if>
                            </sl:collectionItem>
                            <sl:collectionItem  title="label.nameReport">
                                <c:out value="${listElement.typeReportNameByCode}" default="-"/>
                            </sl:collectionItem>
                            <sl:collectionItem  title="label.periodReport">
                                <c:if test="${not empty listElement}">
                                    <c:if test="${ not empty listElement.startDate }">
                                        <bean:write name="listElement" property="startDate.time" format="dd.MM.yyyy" filter="true" />
                                    </c:if>

                                    <c:if test="${ not empty listElement.endDate }">
                                        - <bean:write name="listElement" property="endDate.time" format="dd.MM.yyyy" filter="true" />
                                    </c:if>
                                </c:if>
                            </sl:collectionItem>
                            <sl:collectionItem title="label.state">
                                    <c:out value="${listElement.stateNameByCode}" default="-"/>
                            </sl:collectionItem>
                        </sl:collection>
                    </tiles:put>
                
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                    <tiles:put name="emptyMessage"><bean:message key="label.empty" bundle="${bundle}"/></tiles:put>
            </tiles:insert>

        </tiles:put>
    </tiles:insert>
</html:form>