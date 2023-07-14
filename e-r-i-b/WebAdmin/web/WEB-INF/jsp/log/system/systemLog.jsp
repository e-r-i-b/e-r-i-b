<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<% pageContext.getRequest().setAttribute("mode", "Services");%>
<% pageContext.getRequest().setAttribute("userMode", "LogSys");%>

<c:set var="standalone" value="${empty param['action']}"/>
<c:choose>
	<c:when test="${standalone}">
		<c:set var="layout" value="personList"/>
	</c:when>
	<c:otherwise>
		<c:set var="layout" value="dictionary"/>
	</c:otherwise>
</c:choose>
<html:form action="/log/system">

<c:set var="form" value="${DownloadSystemLogForm}"/>

<tiles:insert definition="logMain">
<tiles:put name="submenu" type="string" value="Messages"/>
<tiles:put name="pageTitle" type="string">
	<bean:message key="label.system.page.name" bundle="logBundle"/>
</tiles:put>

<tiles:put name="filter" type="string">
    <tiles:put name="fastSearchFilter" value="true"/>
    <c:set var="colCount" value="2" scope="request"/>

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

    <%-- row 3--%>
    <tiles:insert definition="filterDateField" flush="false">
        <tiles:put name="label" value="label.birthDay"/>
        <tiles:put name="bundle" value="personsBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="name" value="birthday"/>
    </tiles:insert>

    <tiles:insert definition="filterEntryField" flush="false">
            <tiles:put name="label" value="label.message.type"/>
            <tiles:put name="bundle" value="logBundle"/>
            <tiles:put name="mandatory" value="false"/>
            <tiles:put name="data">
                <html:select property="filter(messageType)" styleClass="select">
                    <html:option value="">Все</html:option>
                    <html:option value="E"><bean:message key="system.log.layer.E" bundle="logBundle"/></html:option>
                    <html:option value="W"><bean:message key="system.log.layer.W" bundle="logBundle"/></html:option>
                    <html:option value="I"><bean:message key="system.log.layer.I" bundle="logBundle"/></html:option>
                    <html:option value="D"><bean:message key="system.log.layer.D" bundle="logBundle"/></html:option>
                    <html:option value="T"><bean:message key="system.log.layer.T" bundle="logBundle"/></html:option>
                </html:select>
            </tiles:put>
    </tiles:insert>
    <%-- row 4--%>
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.ip.address"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="maxlength" value="15"/>
			<tiles:put name="name" value="ipAddres"/>
	</tiles:insert>

	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.message.word"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="messageWord"/>
            <tiles:put name="maxlength" value="100"/>
            <tiles:put name="size" value="45"/>
	</tiles:insert>

    <%-- row 5 --%>
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.personId"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="loginId"/>
            <tiles:put name="isFastSearch" value="true"/>
            <tiles:put name="addClassLabel" value="mainJournal"/>
            <tiles:put name="addClassValue" value="mainJournal"/>
	</tiles:insert>

    <tiles:insert definition="filterEntryField" flush="false">
            <tiles:put name="label" value="label.source"/>
            <tiles:put name="bundle" value="logBundle"/>
            <tiles:put name="isFastSearch" value="true"/>
            <tiles:put name="data">
                <html:select property="filter(source)" styleClass="select">
                    <html:option value="">Все</html:option>
                    <html:option value="Core"><bean:message key="user.log.source.Core" bundle="logBundle"/></html:option>
                    <html:option value="Gate"><bean:message key="user.log.source.Gate" bundle="logBundle"/></html:option>
                    <html:option value="Scheduler"><bean:message key="user.log.source.Scheduler" bundle="logBundle"/></html:option>
                    <html:option value="Cache"><bean:message key="user.log.source.Cache" bundle="logBundle"/></html:option>
                    <html:option value="Web"><bean:message key="user.log.source.Web" bundle="logBundle"/></html:option>
                </html:select>
            </tiles:put>
    </tiles:insert>

   <%-- row 6 --%>
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.error.id"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="errorId"/>
            <tiles:put name="isFastSearch" value="true"/>
	</tiles:insert>

    <%--пустое поле--%>
    <tiles:insert definition="filterTextField" flush="false">
    </tiles:insert>

    <%-- row 7 --%>
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
</tiles:put>

<tiles:put name="data" type="string">
    <c:set var="isGuestJournal"><bean:write name="form" property="filters.isGuestLog"/></c:set>
    <c:choose>
        <c:when test="${(not empty isGuestJournal) && isGuestJournal}">
            <jsp:include page="systemGuestLogData.jsp"/>
        </c:when>
        <c:otherwise>
            <jsp:include page="systemLogData.jsp"/>
        </c:otherwise>
    </c:choose>
</tiles:put>
</tiles:insert>
<script type="text/javascript">
    chooseGuestJournal();
</script>
</html:form>
