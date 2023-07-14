<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>


<% pageContext.getRequest().setAttribute("mode", "Services");%>
<% pageContext.getRequest().setAttribute("userMode", "LogSys");%>
<html:form action="/log/common">

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="logMain">
<tiles:put name="submenu" type="string" value="Common"/>
<tiles:put name="pageTitle" type="string">
	<bean:message key="label.comlog.page.name" bundle="logBundle"/>
</tiles:put>

<tiles:put name="filter" type="string">
    <tiles:put name="fastSearchFilter" value="true"/>
    <c:set var="colCount" value="2" scope="request"/>

    <input type="hidden" name="filter(sessionId)" value="${form.filters.sessionId}"/>
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
        <tiles:put name="data">
            <%@include file="/WEB-INF/jsp/log/applicationSelect.jsp"%>
        </tiles:put>
    </tiles:insert>

    <%-- row 3 --%>   
    <tiles:insert definition="filterDateField" flush="false">
        <tiles:put name="label" value="label.birthDay"/>
        <tiles:put name="bundle" value="personsBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="name" value="birthday"/>
    </tiles:insert>

    <tiles:insert definition="filterEmptytField" flush="false">
    </tiles:insert>

    <%-- row 4 --%>
    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label" value="label.loginId"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="name" value="loginId"/>
        <tiles:put name="isFastSearch" value="true"/>
    </tiles:insert>

   <tiles:insert definition="filterEntryField" flush="false">
			<tiles:put name="label" value="label.log.type"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="mandatory" value="false"/>
            <tiles:put name="isFastSearch" value="true"/>
			<tiles:put name="data">
                <html:checkbox property="filter(showUserLog)" styleClass="logJournal"/>
                <bean:message key="label.showLogEntries.user" bundle="logBundle"/>

            </tiles:put>
    </tiles:insert>

    <%-- row 5 --%>
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
            <c:if test="${phiz:impliesOperation('ListDepartmentsOperation', 'DepartmentsManagement') or
                          phiz:impliesOperation('ListDepartmentsOperation','DepartmentsViewing')}">
                <input type="button" class="buttWhite smButt"
                       onclick="openDepartmentsDictionary(setDepartmentInfo, getElementValue('field(departmentName)'));"
                       value="..."/>
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
                           clearInputTemplate('filter(fromDate)', '__.__.____');
                           clearInputTemplate('filter(toDate)', '__.__.____');
                           clearInputTemplate('filter(fromTime)', '__:__:__');
                           clearInputTemplate('filter(toTime)', '__:__:__');
                       });
           </script>
        </tiles:put>
    </tiles:insert>

   <tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label" value="label.empty"/>
        <tiles:put name="bundle" value="claimsBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="isFastSearch" value="true"/>
        <tiles:put name="data">
                   <html:checkbox property="filter(showSystemLog)" styleClass="logJournal"/>
                   <bean:message key="label.showLogEntries.system" bundle="logBundle"/>
                   <br/>
                   <html:checkbox property="filter(showMessageLog)" styleClass="logJournal"/>
                   <bean:message key="label.showLogEntries.message" bundle="logBundle"/>
        </tiles:put>
   </tiles:insert>
    
</tiles:put>

<tiles:put name="data" type="string">
    <jsp:include page="commonLogData.jsp" flush="false"/>
</tiles:put>
</tiles:insert>
</html:form>
