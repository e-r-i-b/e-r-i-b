<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/receivers/edit">

<tiles:insert definition="receiversList">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="isNew" value="${form.id == null}"/>
<tiles:put name="submenu" type="string" value="PaymentReceivers"/>
<tiles:put name="pageTitle" type="string" value="Справочник получателей. Ввод/редактирование получателя"/>

<!-- меню -->
<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.cancel"/>
		<tiles:put name="commandHelpKey" value="button.cancel"/>
		<tiles:put name="bundle" value="commonBundle"/>
		<tiles:put name="image" value=""/>
		<tiles:put name="action" value="/private/receivers/list.do"/>
	</tiles:insert>
</tiles:put>

<!-- собственно данные -->
<tiles:put name="data" type="string">
<script type="text/javascript">
	function setBankInfo(bankInfo)
	{
		setElement('field(bankName)', bankInfo["name"]);
		setElement('field(bankCode)', bankInfo["BIC"]);
		setElement('field(correspondentAccount)', bankInfo["account"]);
	}

	function selectTypePerson(type)
	{
		var inn = document.getElementById("receiverINN").value;
		if (type == 'J')
		{
			if (inn.length > 10)
				document.getElementById("receiverINN").value = inn.substring(0, 10);
			document.getElementById("receiverINN").maxLength = "10";
		}
		if (type == 'P')
		{
			document.getElementById("receiverINN").maxLength = "12";
		}
	}
</script>
<tiles:insert definition="paymentForm" flush="false">
<tiles:put name="id" value="EditPaymentReceiver"/>
<tiles:put name="name" value="Ввод/редактирование получателя"/>
<tiles:put name="description" value="Ввод/редактирование получателя"/>
<tiles:put name="buttons">
	<tiles:insert definition="commandButton" flush="false">
		<tiles:put name="commandKey" value="button.save"/>
		<tiles:put name="commandHelpKey" value="button.save"/>
		<tiles:put name="bundle" value="commonBundle"/>
		<tiles:put name="image" value="iconSm_save.gif"/>
		<tiles:put name="isDefault" value="true"/>
		<tiles:put name="postbackNavigation" value="true"/>
	</tiles:insert>
</tiles:put>
<tiles:put name="data">
	<tr>
		<td class="Width120 Label">Тип получателя<span class="asterisk">*</span>
		</td>
		<td>
			<c:choose>
				<c:when test="${isNew}">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<html:radio property="kind" value="J" onclick="selectTypePerson('J')">
									Юридическое лицо
								</html:radio>
							</td>
							<td>
								<html:radio property="kind" value="P" onclick="selectTypePerson('P')">
									Физическое лицо
								</html:radio>
							</td>
						</tr>
					</table>
				</c:when>
				<c:when test="${form.kind=='J'}">
					Юридическое лицо
					<html:hidden property="kind"/>
				</c:when>
				<c:when test="${form.kind=='P'}">
					<html:hidden property="kind"/>
					Физическое лицо
				</c:when>
			</c:choose>
			&nbsp;
		</td>
	</tr>
	<tr style="height:20px">
		<td class="Width120 Label">
			<bean:message key="label.receiver.alias" bundle="paymentsBundle"/>
			<span class="asterisk">*</span>
		</td>
		<td>
			<html:text property="field(alias)" size="80" readonly="${!isNew}"/>
		</td>
	</tr>
	<tr style="height:20px">
		<td class="Width120 Label">
			<nobr>
				<bean:message key="label.receiver.name" bundle="paymentsBundle"/>
				<span class="asterisk">*</span>
			</nobr>
		</td>
		<td>
			<html:text property="field(name)" size="80"/>
		</td>
	</tr>
	<tr>
		<td class="Width120 Label">
			<bean:message key="label.receiver.inn" bundle="paymentsBundle"/>
			<span class="asterisk">*</span>
		</td>
		<td>
			<html:text property="field(INN)" styleId="receiverINN" size="24" maxlength="12"/>
		</td>
	</tr>
	<tr>
		<td class="Width120 Label">
			<bean:message key="label.receiver.account" bundle="paymentsBundle"/>
			<span class="asterisk">*</span>
		</td>
		<td>
			<html:text property="field(account)" size="30" maxlength="25"/>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="text-align:left;" class="Width120 Label">
			&nbsp;
			<bean:message key="label.receiver.bank.title" bundle="paymentsBundle"/>
			<span class="asterisk">*</span>
		</td>
	</tr>
	<tr>
		<td class="Width120 Label">
			<nobr>
				<bean:message key="label.receiver.bank.name" bundle="paymentsBundle"/>
				<span class="asterisk">*</span>
			</nobr>
		</td>
		<td>
			<nobr>
			<html:text property="field(bankName)" styleId="field(bankName)" size="80"/>
			<input type="button" class="buttWhite smButt" style="height:18px;width:18;" onclick="javascript:openNationalBanksDictionary(setBankInfo,getFieldValue('field(bankName)'),getFieldValue('field(bankCode)'));" value="..."/>
			</nobr>
		</td>
	</tr>
	<tr>
		<td class="Width120 Label">
			<bean:message key="label.receiver.bank.bic" bundle="paymentsBundle"/>
			<span class="asterisk">*</span>
		</td>
		<td>
			<html:text property="field(bankCode)" styleId="field(bankCode)" size="24" maxlength="9"/>
		</td>
	</tr>
	<tr>
		<td class="Width120 Label">
			<bean:message key="label.receiver.bank.corAccount" bundle="paymentsBundle"/>
			<span class="asterisk">*</span>
		</td>
		<td>
			<html:text property="field(correspondentAccount)" size="24" maxlength="20"/>
		</td>
	</tr>
	<c:if test="${!isNew}">
		<html:hidden property="id"/>
	</c:if>
</tiles:put>
<tiles:put name="alignTable" value="center"/>
</tiles:insert>
<script type="text/javascript">
	selectTypePerson('${form.kind}');
</script>
</tiles:put>

</tiles:insert>
</html:form>
