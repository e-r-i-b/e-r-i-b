<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="standalone" value="${empty param['action']}"/>
<c:choose>
	<c:when test="${standalone}">
		<c:set var="layout" value="personList"/>
	</c:when>
	<c:otherwise>
		<c:set var="layout" value="dictionary"/>
	</c:otherwise>
</c:choose>
<html:form action="/log/csa/operations">

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="logMain">
<tiles:put name="submenu" type="string" value="CSAOperationLog"/>
<tiles:put name="pageTitle" type="string">
	<bean:message key="label.operation.page.name" bundle="logBundle"/>
</tiles:put>

<tiles:put name="filter" type="string">
    <tiles:put name="fastSearchFilter" value="true"/>
    <c:set var="colCount" value="2" scope="request"/>
    <%--  row 1 --%>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.client"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="fio"/>
        <tiles:put name="size"   value="50"/>
        <tiles:put name="maxlength"  value="255"/>
        <tiles:put name="isDefault" value="Фамилия Имя Отчество"/>
	</tiles:insert>

	<tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label" value="label.period"/>
		<tiles:put name="bundle" value="logBundle"/>
		<tiles:put name="mandatory" value="false"/>
        <tiles:put name="data">
            <nobr>
                с&nbsp;
                <span style="font-weight:normal;overflow:visible;cursor:default;">
                    <input type="text"
                            size="10" name="filter(fromDate)" class="dot-date-pick"
                            value="<bean:write name="form" property="filters.fromDate" format="dd.MM.yyyy"/>"/>
                    <input type="text"
                            size="8" name="filter(fromTime)"   class="time-template"
                            value="<bean:write name="form" property="filters.fromTime" format="HH:mm:ss" />"
                            onkeydown="onTabClick(event,'filter(toDate)');"/>
                </span>
                    &nbsp;по&nbsp;
                <span style="font-weight:normal;cursor:default;">
                    <input type="text"
                            size="10" name="filter(toDate)" class="dot-date-pick"
                            value="<bean:write name="form" property="filters.toDate" format="dd.MM.yyyy"/>"/>

                    <input type="text"
                            size="8" name="filter(toTime)" class="time-template"
                            value="<bean:write name="form" property="filters.toTime" format="HH:mm:ss"/>"/>
                </span>
            </nobr>
        </tiles:put>
	</tiles:insert>    

    <%-- row 2 --%>
    <tiles:insert definition="filterTextField" flush="false">
            <tiles:put name="label" value="label.passport"/>
            <tiles:put name="bundle" value="logBundle"/>
            <tiles:put name="mandatory" value="false"/>
            <tiles:put name="name" value="number"/>
            <tiles:put name="size"   value="16"/>
            <tiles:put name="maxlength"  value="20"/>
            <tiles:put name="isDefault" value="Паспорт"/>
    </tiles:insert>

    <%-- row 3 --%>
    <tiles:insert definition="filterDateField" flush="false">
        <tiles:put name="label" value="label.birthDate"/>
        <tiles:put name="bundle" value="claimsBundle"/>
        <tiles:put name="name" value="birthday"/>
    </tiles:insert>

    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label" value="label.login"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="name" value="login"/>
        <tiles:put name="isFastSearch" value="true"/>
    </tiles:insert>
    
	<tiles:insert definition="filterEntryField" flush="false">
			<tiles:put name="label" value="label.operation.state"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="data">
				<html:select property="filter(state)" styleClass="select">
					<html:option value="">Все</html:option>
                    <c:forEach var="state" items="${form.CSAOperationsStates}">
                        <html:option value="${state}"><c:out value="${state.description}"/></html:option>
                    </c:forEach>
				</html:select>
			</tiles:put>
	</tiles:insert>

    <tiles:insert definition="filterEntryField" flush="false">
			<tiles:put name="label" value="label.operation.name"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="data">
				<html:select property="filter(type)" styleClass="select">
					<html:option value="">Все</html:option>
                    <c:forEach var="operation" items="${form.CSAOperations}">
                        <html:option value="${operation}"><c:out value="${operation.description}"/></html:option>
                    </c:forEach>
				</html:select>
			</tiles:put>
	</tiles:insert>

	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.department.code"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="departmentCode"/>
            <tiles:put name="isFastSearch" value="true"/>
	</tiles:insert>
</tiles:put>

<tiles:put name="additionalFilterButtons" type="string">
	<tiles:insert definition="commandButton" flush="false">
		<tiles:put name="commandKey" value="button.download"/>
		<tiles:put name="commandHelpKey" value="button.download"/>
		<tiles:put name="bundle" value="logBundle"/>
		<tiles:put name="autoRefresh" value="true"/>
	</tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
    <jsp:include page="operationLogData.jsp"/>
</tiles:put>
</tiles:insert>
</html:form>
