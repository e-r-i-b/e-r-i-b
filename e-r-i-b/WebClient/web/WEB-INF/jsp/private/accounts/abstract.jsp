<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/accounts/abstract" onsubmit="return checkData(event);">

<tiles:insert definition="abstractList">

  <tiles:put name="mainmenu" value="Info"/>
  <tiles:put name="submenu" type="string" value="Abstract"/>
  <!-- заголовок -->
  <tiles:put name="pageTitle" type="string">
        Пользователь: <bean:write name="ShowAccountAbstractForm" property="user.fullName"/>. Выписки по счетам и картам
  </tiles:put>

<!-- меню -->
<tiles:put name="menu" type="string">

	<tiles:insert definition="commandButton" flush="false">
		<tiles:put name="commandKey" value="button.close"/>
		<tiles:put name="commandHelpKey" value="button.close.help"/>
		<tiles:put name="bundle" value="commonBundle"/>
		<tiles:put name="image" value=""/>
	</tiles:insert>
</tiles:put>
<!-- фильтр -->
<tiles:put name="filter" type="string">
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.accountCard"/>
		<tiles:put name="bundle" value="accounts"/>
		<tiles:put name="mandatory" value="true"/>
		<tiles:put name="data">
			<html:select property="selectedResources" multiple="true" size="3"
			             onkeydown="onTabClick(event,'fromDateString')">
				<html:options property="resourceLinks" labelProperty="resourceNames"/>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.date"/>
		<tiles:put name="bundle" value="accounts"/>
		<tiles:put name="mandatory" value="true"/>
		<tiles:put name="data">
			&nbsp;c:
			<html:text name="ShowAccountAbstractForm" property="fromDateString"
			           size="10" style="width:60px;"
			           onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);onTabClick(event,'toDateString')"/>
			&nbsp;по&nbsp;
			<html:text name="ShowAccountAbstractForm" property="toDateString"
			           size="10" style="width:60px;"
			           onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/>
		</tiles:put>
	</tiles:insert>
	<c:url value="/print.do" var="printUrl"/>
	<script language="JavaScript">
		function openPrint(event)
		{
			if (checkData(event) == true)
			{   /*todo реализовать передачу параметров через форму*/
				var req;
				var copying;
				req = 'print.do?';
				copying = <bean:write name="ShowAccountAbstractForm" property="copying"/>;
				;
				userId = <bean:write name="ShowAccountAbstractForm" property="user.id"/>;
				req = req + "id=" + userId + "&";
				req = req + "copying=" + copying + "&";
				req = req + "fromDateString=" + document.ShowAccountAbstractForm.fromDateString.value + "&";
				req = req + "toDateString=" + document.ShowAccountAbstractForm.toDateString.value + "&";
				box = document.ShowAccountAbstractForm.selectedResources;
				for (var i = 0; i < box.length; i++)
				{
					if (box.options[i].selected == true)
					{
						req = req + "&sel=" + box.options[i].value;
					}
				}
				openWindow(event, req);
			}
		}
	</script>

	<script type="text/javascript">
		function initTemplates()
		{
			var el = getElement("fromDateString");
			el.defaultValue = "<bean:write name="ShowAccountAbstractForm" property="operDate"/>";
			el = getElement("toDateString");
			el.defaultValue = "<bean:write name="ShowAccountAbstractForm" property="currentDate"/>";
			setInputTemplate("fromDateString", DATE_TEMPLATE);
			setInputTemplate("toDateString", DATE_TEMPLATE);
		}

		function clearMasks(event)
		{
			clearInputTemplate("fromDateString", DATE_TEMPLATE);
			clearInputTemplate("toDateString", DATE_TEMPLATE);
		}
		function checkData(event)
		{

			if (!checkPeriod("fromDateString", "toDateString", "Период с", "Период по", true)){
				clearLoadMessage();				
				return false;
			}
			setEmptyAction(event);
			return true;
		}
		function callAbstractOperation(event, action, confirm)
		{
			preventDefault(event);
			res = getElementValue("selectedResources");
			if (res == "") alert("Выберите счета/карты");
			else callOperation(event, action, confirm);
		}
		initTemplates();
	</script>
</tiles:put>
<!--данные-->

</tiles:insert>
</html:form>
