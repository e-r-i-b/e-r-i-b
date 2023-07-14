<%@ page import="com.rssl.phizic.utils.*" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% pageContext.getRequest().setAttribute("mode", "Services");%>
<% pageContext.getRequest().setAttribute("userMode", "LogSys");%>

<html:form action="/log/system" onsubmit="return checkData(event);">
<tiles:insert definition="logMain">
	<tiles:put name="submenu" type="string" value="Messages"/>
	<tiles:put name="pageTitle" type="string">
		Выгрузка системного журнала
	</tiles:put>
	<tiles:put name="menu" type="string">
		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey" value="button.download"/>
			<tiles:put name="commandHelpKey" value="button.download"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="image" value=""/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.clear"/>
			<tiles:put name="commandHelpKey" value="button.clear"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="image" value=""/>
			<tiles:put name="onclick" value="clearFilter(event);"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
	</tiles:put>
	<!-- фильтр -->
	<tiles:put name="data" type="string">
		<table cellpadding="0" cellspacing="0" class="MaxSize">
		<tr>
		<td height="100%">
		<bean:define id="frm" name="DownloadLogFilterForm"/>
		<table cellspacing="4" cellpadding="1" class="filterBorder" onkeypress="onEnterKey(event);"
		       id="filterFields">
			<tr>
				<td class="filter" nowrap="true">
					Период&nbsp;с&nbsp;
					<html:text property="filter(fromDate)" styleClass="filterInput" size="10"
					           onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);onTabClick(event,'filter(toDate)')"/>
					&nbsp;по&nbsp;
					<html:text property="filter(toDate)" styleClass="filterInput" size="10"
					           onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/>
					&nbsp;
				</td>
			</tr>
		</table>
		<script type="text/javascript">
			function initTemplates()
			{
				var dt,dt1;
				setInputTemplate("filter(fromDate)", DATE_TEMPLATE);
				setInputTemplate("filter(toDate)", DATE_TEMPLATE);
				dt = getElement("filter(fromDate)");
				dt.defaultValue = "<%=DateHelper.toString(DateHelper.getCurrentDate().getTime()) %>";
				dt.value = dt.defaultValue;
				dt1 = getElement("filter(toDate)");
				dt1.defaultValue = dt.value;
				dt1.value = dt1.defaultValue;
			}
			addClearMasks(null, function(event)
			{
				clearInputTemplate("filter(fromDate)", DATE_TEMPLATE);
				clearInputTemplate("filter(toDate)", DATE_TEMPLATE);
			});
			function checkData(event)
			{
				preventDefault(event);
				if (!checkPeriod("filter(fromDate)", "filter(toDate)", "Период с", "Период по")) return false;
				return true;
			}
			initTemplates();
		</script>
	</td>
	</tr>
	</table>
	</tiles:put>
</tiles:insert>

</html:form>