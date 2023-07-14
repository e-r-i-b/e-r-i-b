<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<% pageContext.getRequest().setAttribute("mode", "Services");%>
<% pageContext.getRequest().setAttribute("userMode", "LogUser");%>
<c:set var="standalone" value="${empty param['action']}"/>
<c:choose>
	<c:when test="${standalone}">
		<c:set var="layout" value="personList"/>
	</c:when>
	<c:otherwise>
		<c:set var="layout" value="dictionary"/>
	</c:otherwise>
</c:choose>
<html:form action="/log/operations">

<c:set var="form" value="${DownloadLogFilterForm}"/>

<tiles:insert definition="logMain">
<tiles:put name="submenu" type="string" value="Operation"/>
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
    <tiles:insert definition="filter2TextField" flush="false">
        <tiles:put name="label" value="label.document"/>
        <tiles:put name="bundle" value="claimsBundle"/>
        <tiles:put name="name"   value="series"/>
        <tiles:put name="size"   value="5"/>
        <tiles:put name="maxlength"  value="16"/>
        <tiles:put name="isDefault" value="Серия"/>
        <tiles:put name="name2"   value="number"/>
        <tiles:put name="size2"   value="10"/>
        <tiles:put name="maxlength2"  value="16"/>
        <tiles:put name="default2" value="Номер"/>
    </tiles:insert>

	<tiles:insert definition="filterEntryField" flush="false">
			<tiles:put name="label" value="label.application"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="mandatory" value="false"/>
            <tiles:put name="addClassLabel" value="mainJournal"/>
            <tiles:put name="addClassValue" value="mainJournal"/>
			<tiles:put name="data">
                <%@include file="/WEB-INF/jsp/log/applicationSelect.jsp"%>
			</tiles:put>
	</tiles:insert>

    <%-- row 3 --%>
    <tiles:insert definition="filterDateField" flush="false">
        <tiles:put name="label" value="label.birthDate"/>
        <tiles:put name="bundle" value="claimsBundle"/>
        <tiles:put name="name" value="birthday"/>
    </tiles:insert>
    
	<tiles:insert definition="filterEntryField" flush="false">
			<tiles:put name="label" value="label.message.type"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="data">
				<html:select property="filter(type)" styleClass="select">
					<html:option value="">Все</html:option>
					<html:option value="S"><bean:message key="user.log.type.success" bundle="logBundle"/></html:option>
					<html:option value="B"><bean:message key="user.log.type.security" bundle="logBundle"/></html:option>
					<html:option value="E"><bean:message key="user.log.type.system.error" bundle="logBundle"/></html:option>
					<html:option value="U"><bean:message key="user.log.type.user.error" bundle="logBundle"/></html:option>
				</html:select>
			</tiles:put>
	</tiles:insert>

     <%-- row 4 --%>
    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label" value="label.personId"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="name" value="loginId"/>
        <tiles:put name="isFastSearch" value="true"/>
        <tiles:put name="addClassLabel" value="mainJournal"/>
        <tiles:put name="addClassValue" value="mainJournal"/>
    </tiles:insert>

	<tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label" value="label.department.name"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="isFastSearch" value="true"/>
        <tiles:put name="data">
            <html:text  property="filter(departmentName)" readonly="true" style="width:200px"/>
            <html:hidden  property="filter(departmentId)"/>
            <html:hidden property="filters(TB)"/>
            <html:hidden property="filters(OSB)"/>
            <html:hidden property="filters(VSP)"/>
            <html:hidden  property="filter(withChildren)"/>
            <c:if test="${phiz:impliesOperation('ListDepartmentsOperation', 'DepartmentsManagement') or phiz:impliesOperation('ListDepartmentsOperation','DepartmentsViewing')}">
                <input type="button" class="buttWhite" onclick="openDepartmentsDictionary(setDepartmentInfo, getElementValue('field(departmentName)'))" value="..."/>
            </c:if>

            <script type="text/javascript">
               function setDepartmentInfo(result)
               {
                   setElement("filter(departmentName)",result['name']);
                   setElement("filters(TB)",result['tb']);
                   setElement("filters(OSB)",result['osb']);
                   setElement("filters(VSP)",result['vsp']);
                   if (result['asParent'] != null) setElement("filter(withChildren)",'true');
                   else setElement("filter(withChildren)",null);
               }
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

    <%-- row 5 --%>
    <tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label" value="label.log.guest.type"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="isFastSearch" value="true"/>
        <tiles:put name="data">
            <html:checkbox property="filter(isGuestLog)" styleClass="logJournal" onclick="chooseGuestJournal();"/>
            <script type="text/javascript">
                function chooseGuestJournal()
                {
                    if ($("input[name='filter(isGuestLog)']").attr("checked") == true)
                    {
                        $(".mainJournal").css("display", "none");
                        $(".guestJournal").css("display", "");
                    }
                    else
                    {
                        $(".mainJournal").css("display", "");
                        $(".guestJournal").css("display", "none");
                    }
                };
            </script>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label" value="label.log.guest.phone.number"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="name" value="phoneNumber"/>
        <tiles:put name="size"   value="11"/>
        <tiles:put name="maxlength" value="11"/>
        <tiles:put name="addClassLabel" value="guestJournal"/>
        <tiles:put name="addClassValue" value="guestJournal"/>
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
    <c:set var="isGuestJournal"><bean:write name="form" property="filters.isGuestLog"/></c:set>
    <c:choose>
        <c:when test="${(not empty isGuestJournal) && isGuestJournal}">
            <jsp:include page="operationGuestLogData.jsp"/>
        </c:when>
        <c:otherwise>
            <jsp:include page="operationLogData.jsp"/>
        </c:otherwise>
    </c:choose>
</tiles:put>
</tiles:insert>
<script type="text/javascript">
    chooseGuestJournal();
</script>
</html:form>
