<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/log/csa/action">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<tiles:insert definition="personEdit">
<tiles:put name="submenu" type="string" value="Edit"/>
<tiles:put name="pageTitle" type="string">
	<bean:message key="label.actionlog.page.name" bundle="logBundle"/>
</tiles:put>
<tiles:put name="filter" type="string">
    <c:set var="colCount" value="2" scope="request"/>
    <!-- Период -->
    <tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label" value="label.period"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="data">
            <span style="white-space:nowrap;">
                с&nbsp;
                <span style="font-weight:normal;overflow:visible;cursor:default;">
                    <input type="text"
                            size="10" name="filter(fromDate)" class="dot-date-pick"
                            value="<bean:write name="form" property="filters.fromDate" format="dd.MM.yyyy"/>"/>
                    <input type="text"
                            size="8" name="filter(fromTime)" class="time-template"
                            value="<bean:write name="form" property="filters.fromTime" format="HH:mm:ss"/>"
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
            </span>
            <script type="text/javascript">
                addClearMasks(null,
                    function(event)
                    {
                        clearInputTemplate('filter(fromDate)','__.__.____');
                        clearInputTemplate('filter(toDate)','__.__.____');
                        clearInputTemplate('filter(fromTime)','__:__:__');
                        clearInputTemplate('filter(toTime)','__:__:__');
                    });
            </script>
        </tiles:put>
    </tiles:insert>

    <!-- Сотрудник -->
    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label" value="label.employeeFIO"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="name" value="employeeFio"/>
        <tiles:put name="size"   value="50"/>
        <tiles:put name="maxlength"  value="150"/>
        <tiles:put name="isDefault" value="ФИО сотрудника"/>
    </tiles:insert>

    <!-- Способ аутентификации -->
    <tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label" value="label.identification.type"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="mandatory" value="true"/>
        <tiles:put name="data">
            <html:select property="filter(identificationType)" styleClass="select">
                <html:option value="">все</html:option>
                <c:forEach items="${form.identificationTypes}" var="identificationType">
                    <html:option value="${identificationType}"><c:out value="${identificationType.description}"/></html:option>
                </c:forEach>
            </html:select>
        </tiles:put>
    </tiles:insert>

    <!-- Логин сотрудника -->
    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label" value="label.employeeLogin"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="name" value="employeeLogin"/>
        <tiles:put name="size"   value="50"/>
        <tiles:put name="maxlength"  value="50"/>
        <tiles:put name="isDefault" value="Логин сотрудника"/>
    </tiles:insert>

    <!-- Операция -->
    <tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label" value="label.csa.operation.type"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="mandatory" value="true"/>
        <tiles:put name="data">
            <html:select property="filter(operationType)" styleClass="select">
                <html:option value="">все</html:option>
                <c:forEach items="${form.operationTypes}" var="operationType">
                    <html:option value="${operationType}">
                        <c:set var="operationKey" value="csa.operation.type.${operationType}"/>
                        <bean:message key="${operationKey}" bundle="logBundle"/>
                    </html:option>
                </c:forEach>
            </html:select>
        </tiles:put>
    </tiles:insert>

    <!-- IP-адрес -->
    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label" value="label.ip.address"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="name" value="ipAddress"/>
        <tiles:put name="size"   value="50"/>
        <tiles:put name="maxlength"  value="150"/>
    </tiles:insert>

    <!-- Подтверждение -->
    <tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label" value="label.confirmation.type"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="mandatory" value="true"/>
        <tiles:put name="data">
            <html:select property="filter(confirmationType)" styleClass="select">
                <html:option value="">все</html:option>
                <html:option value="none">Не требуется</html:option>
                <html:option value="sms">SMS-пароль</html:option>
                <html:option value="card">пароль с чека</html:option>
            </html:select>
        </tiles:put>
    </tiles:insert>

</tiles:put>

<tiles:put name="data" type="string">
    <input type="hidden" name="id" value="<bean:write name="form" property="id"/>"/>
    <jsp:include page="actionLogData.jsp"/>
</tiles:put>
</tiles:insert>
</html:form>
