<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>

<c:set var="standalone" value="${empty param['dict']}"/>
<c:choose>
	<c:when test="${standalone}">
		<c:set var="layoutDefinition" value="passwordCards"/>
	</c:when>
	<c:otherwise>
		<c:set var="layoutDefinition" value="dictionary"/>
	</c:otherwise>
</c:choose>

<html:form action="/passwordcards/list" onsubmit="return setStandalone(event);">
<tiles:insert definition="${layoutDefinition}">
<tiles:put name="submenu" value="List"/>
<tiles:put name="pageTitle" value="Карты ключей"/>

<tiles:put name="menu" type="string">
	<script type="text/javascript">
		function setStandalone(event)
		{
			if (!checkNumber("filter(passwordsCount)", "Количество ключей")) return false;
			if (!checkPeriod("filter(fromDate)", "filter(toDate)", "Создана с", "Создана по")) return false;
			preventDefault(event)
		<c:if test="${!standalone}">
			var frm = document.forms.item(0);
			frm.action = frm.action + "?dict=true";
		</c:if>
			return true;
		}
	</script>
	<c:if test="${standalone}">

		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey" value="button.remove"/>
			<tiles:put name="commandHelpKey" value="button.remove.help"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="image" value=""/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
	</c:if>
	<c:if test="${not standalone}">
        <tiles:insert definition="clientButton" flush="false">
		   <tiles:put name="commandTextKey" value="button.give"/>
		   <tiles:put name="commandHelpKey" value="button.give.help"/>
		   <tiles:put name="bundle"         value="passwordcardsBundle"/>
		   <tiles:put name="onclick">
		     javascript:addCards(event);
		   </tiles:put>
            <tiles:put name="viewType" value="blueBorder"/>
	    </tiles:insert>
        <tiles:insert definition="clientButton" flush="false">
		   <tiles:put name="commandTextKey" value="button.cancel"/>
		   <tiles:put name="commandHelpKey" value="button.cancel.help"/>
		   <tiles:put name="bundle"         value="passwordcardsBundle"/>
		   <tiles:put name="onclick">
		     javascript:window.close();
		   </tiles:put>
            <tiles:put name="viewType" value="blueBorder"/>
	    </tiles:insert>
		<script type="text/javascript">
			function addCards()
			{
                checkIfOneItem("selectedIds");
				var qnt = getSelectedQnt("selectedIds");
				if (qnt == 0) alert("Выберите карты ключей");
				else
				{
					window.opener.doAdd(document.all.namedItem("selectedIds"));
					window.close();
				}
			}
		</script>
	</c:if>
</tiles:put>

<!-- фильтр -->
<tiles:put name="filter" type="string">
	<bean:define id="frm" name="ListUnassignedPasswordCardsForm"/>
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.number"/>
			<tiles:put name="bundle" value="passwordcardsBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="number"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.passwordsCount"/>
			<tiles:put name="bundle" value="passwordcardsBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="passwordsCount"/>
	</tiles:insert>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.Date"/>
		<tiles:put name="bundle" value="passwordcardsBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="Date"/>
		<tiles:put name="template" value="DATE_TEMPLATE"/>
	</tiles:insert>
	<script type="text/javascript">
		function initTemplates()
		{
			//setInputTemplate('passportSeries', PASSPORT_SERIES_TEMPLATE);
			//setInputTemplate('passportNumber', PASSPORT_NUMBER_TEMPLATE);
		}

		function submit(event)
		{
			var frm = window.opener.document.forms.item(0);
			frm.action = '';
			frm.submit();
		}
		initTemplates();
	</script>
</tiles:put>


<tiles:put name="data" type="string">
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="PasswordcardsList"/>
		<tiles:put name="grid">
	    <sl:collection model="list"
	                   property="data"
	                   id="listElement"
	                   bundle="personsBundle"
	                   emptyKey="list.empty"
	                   selectName="selectedIds"
	                   selectProperty="id">

		    <sl:collectionItem title="label.number" property="number"/>

		    <sl:collectionItem title="label.issueDate" property="issueDate" />
			<%--format="dd.MM.yy"--%>
		    <sl:collectionItem title="label.passwordsCount" property="passwordsCount"/>
	    </sl:collection>
		</tiles:put>
        <tiles:put name="emptyMessage" value="Не найдено ни одной карты ключей,<br>соответствующей заданному фильтру!"/>
    </tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>