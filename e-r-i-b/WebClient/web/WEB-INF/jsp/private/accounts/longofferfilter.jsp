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
  <!-- ��������� -->
  <tiles:put name="pageTitle" type="string">
        ����� �� ���������� ���������� ���������
  </tiles:put>
<!-- ������ -->
 

<tiles:put name="data" type="string">
<tiles:insert definition="paymentForm" flush="false">
    <tiles:put name="id" value="filterFields"/>
    <tiles:put name="data">
	<tr>
		<td>
			<table>
				<tr>
					<td class="filter">����/�����<span class="asterisk">*</span>:&nbsp;</td>
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
			&nbsp;������<span class="asterisk">*</span>&nbsp;c:
				<html:text name="ShowLongOfferForm" property="fromDateString"
				           size="10" style="width:60px;"
				           onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);onTabClick(event,'toDateString')"/>
			&nbsp;��&nbsp;
				<html:text name="ShowLongOfferForm" property="toDateString" size="10"
				           style="width:60px;" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/>
			&nbsp;<br/>
			<script language="JavaScript">
				function openPrint(event)
				{
					if (checkData(event) == true)
					{   /*todo ����������� �������� ���������� ����� �����*/
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
						else alert("�������� �����/�����");
					}
				}
			</script>
        </td>
	</tr>
	<tr>
		<td class="filter">
			��� ������:&nbsp;
			<select name="orderType" size="1">
				<option selected value="type1">
					�� ������� (������������) ���� �� ������ ����� ���������� ��� � ������
				</option>
				<option value="type2">
					�� ������� � ������� (���������) � ����������� ������� ����������� ������
				</option>
				<option value="type3">
					�� ������� (������������) �������� ������� � ������ � ������ ����������� ���
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
		if (!checkPeriod("fromDateString", "toDateString", "������ �", "������ ��", true)) return false;
		setEmptyAction(event);
		return true;
	}
	function callAbstractOperation(event, action, confirm)
	{
		preventDefault(event);
		res = getElementValue("selectedResources");
		if (res == "") alert("�������� �����/�����");
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
<!--������-->

</tiles:insert>
</html:form>