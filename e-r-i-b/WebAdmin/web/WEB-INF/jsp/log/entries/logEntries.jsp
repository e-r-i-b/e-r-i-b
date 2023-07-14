<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/log/entries">

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="logMain">
<tiles:put name="submenu" type="string" value="LogEntries"/>
<tiles:put name="pageTitle" type="string">
	<bean:message key="label.entries.page.mame" bundle="logBundle"/>
</tiles:put>

<tiles:put name="filter" type="string">

    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label" value="label.client"/>
        <tiles:put name="bundle" value="personsBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="name" value="fio"/>
        <tiles:put name="size"   value="50"/>
        <tiles:put name="maxlength"  value="255"/>
        <tiles:put name="isDefault" value="Фамилия Имя Отчество"/>
    </tiles:insert>

    <tiles:insert definition="filterDataSpan" flush="false">
        <tiles:put name="time" value="true"/>
        <tiles:put name="name" value="Date"/>
        <tiles:put name="label" value="label.period"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="template" value="DATE_TEMPLATE"/>
    </tiles:insert>

    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label"  value="label.document"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="name"   value="dul"/>
        <tiles:put name="size"   value="32"/>
        <tiles:put name="maxlength"  value="32"/>
        <tiles:put name="isDefault" value="Серия номер"/>
    </tiles:insert>

    <tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label" value="label.application"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="data">
            <%@include file="/WEB-INF/jsp/log/applicationSelect.jsp"%>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="filterDateField" flush="false">
        <tiles:put name="label" value="label.birthday"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="name" value="birthday"/>
    </tiles:insert>

    <tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.id"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="loginId"/>
	</tiles:insert>


</tiles:put>

    <tiles:put name="data" type="string">
        <script type="text/javascript">
            function getToCommonJournalUrl(sessionId, date)
            {
                return "${phiz:calculateActionURL(pageContext,'/log/common')}" + "?filter(sessionId)=" + sessionId +
                      "&filter(fromDate)="+date+"&filter(toDate)=" +
                      "<fmt:formatDate value="${phiz:currentDate().time}" pattern="dd.MM.yyyy"/>";
            }
        </script>
        <jsp:include page="logEntriesData.jsp"/>
    </tiles:put>
</tiles:insert>
</html:form>
