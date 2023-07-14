<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/accounts/showlong" onsubmit="return checkData(event);">

<tiles:insert definition="paymentMain">

<tiles:put name="mainmenu" value="Info"/>
	<tiles:put name="submenu" type="string" value="ShowLongOffer"/>
  <!-- заголовок -->
  <tiles:put name="pageTitle" type="string">
        Отчет об исполнении длительных поручений
  </tiles:put>
<!-- фильтр -->
 

<tiles:put name="data" type="string">
<tiles:insert definition="paymentForm" flush="false">
    <tiles:put name="id" value="filterFields"/>
    <tiles:put name="data">
	<tr>
		<td>
			<table>
				<tr>
					<td class="filter">Счет/карта<span class="asterisk">*</span>:&nbsp;</td>
					<td>
						<html:select property="selectedResources" multiple="true" size="3" styleClass="filterSelect"
			                 style="width:300px;border:0" onkeydown="onTabClick(event,'fromDateString')">
							<html:options property="resourceLinks" labelProperty="resourceNames"/>
						</html:select>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="filter">
			&nbsp;Период<span class="asterisk">*</span>&nbsp;c:
				<html:text name="ShowLongOfferForm" property="fromDateString"
				           size="10" style="width:60px;"
				           onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);onTabClick(event,'toDateString')"/>
			&nbsp;по&nbsp;
				<html:text name="ShowLongOfferForm" property="toDateString" size="10"
				           style="width:60px;" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/>
			&nbsp;<br/>
			<script language="JavaScript">
				function openPrint(event)
				{
					if (checkData(event) == true)
					{   /*todo реализовать передачу параметров через форму*/
						var add;
						var req;
						var copying;
						add = 0;
						req = 'private/accounts/printlong.do?';
						userId = <bean:write name="ShowLongOfferForm" property="user.id"/>;
						req = req + "id=" + userId + "&";
						req = req + "fromDateString=" + document.ShowLongOfferForm.fromDateString.value + "&";
						req = req + "toDateString=" + document.ShowLongOfferForm.toDateString.value + "&";
						req = req + "type=" + document.ShowLongOfferForm.orderType.value;
						box = document.ShowLongOfferForm.selectedResources;
						for (var i = 0; i < box.length; i++)
						{
							if (box.options[i].selected == true)
							{
								add = add + 1;
								req = req + "&sel=" + box.options[i].value;
							}
						}
						if (add != 0)
						{
							openWindow(event,'${pageContext.request.contextPath}'+'/'+ req);
						}
						else alert("Выберите счета/карты");
					}
				}
			</script>
        </td>
	</tr>
	<tr>
		<td class="filter">
			Тип отчета:&nbsp;
			<select name="orderType" size="1">
				<option selected value="type1">
					на перевод (перечисление) сумм на другие счета физических лиц в рублях
				</option>
				<option value="type2">
					на покупку и продажу (конверсию) в безналичном порядке иностранной валюты
				</option>
				<option value="type3">
					на перевод (перечисление) денежных средств в рублях в пользу юридических лиц
				</option>
			</select>
        </td>
	</tr>
    </tiles:put>
    <tiles:put name="buttons">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.print"/>
            <tiles:put name="commandHelpKey" value="button.print"/>
            <tiles:put name="bundle" value="paymentsBundle"/>
            <tiles:put name="image" value="iconSm_print.gif"/>
            <tiles:put name="onclick" value="openPrint(event);"/>
        </tiles:insert>

        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="onclick" value="clearFilter(event);"/>
            <tiles:put name="commandTextKey" value="button.clear"/>
            <tiles:put name="commandHelpKey" value="button.clear.help"/>
            <tiles:put name="bundle" value="paymentsBundle"/>
            <tiles:put name="image" value=""/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>

<script language="JavaScript">

	function initTemplates()
	{
		var el = getElement("fromDateString");
		el.defaultValue = "<bean:write name="ShowLongOfferForm" property="operDate"/>";
		el = getElement("toDateString");
		el.defaultValue = "<bean:write name="ShowLongOfferForm" property="currentDate"/>";
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
		if (!checkPeriod("fromDateString", "toDateString", "Период с", "Период по", true)) return false;
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

	function goToAccounts(event)
	{
		clearFilter(event);
		callOperation(event, '<bean:message key="button.close" bundle="commonBundle"/>');
	}
	initTemplates();
</script>
</tiles:put>   
<!--данные-->

</tiles:insert>
</html:form>