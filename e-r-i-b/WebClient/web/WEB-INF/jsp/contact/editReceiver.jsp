<%--
  User: Omeliyanchuk
  Date: 19.12.2006
  Time: 15:51:52
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/contact/receivers/edit" onsubmit="return setEmptyAction(event)">

<c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
<tiles:insert definition="receiversList">
	<tiles:put name="submenu" type="string" value="ReceiverContact"/>
	<tiles:put name="pageTitle" type="string">
		<bean:message key="receiver.title" bundle="contactBundle"/>
	</tiles:put>

	<!--меню-->
	<tiles:put name="menu" type="string">
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.close"/>
			<tiles:put name="commandHelpKey" value="button.close.help"/>
			<tiles:put name="bundle"         value="contactBundle"/>
			<tiles:put name="action"         value="/private/contact/receivers/list.do?kind=C"/>
		</tiles:insert>
	</tiles:put>

	<tiles:put name="data" type="string">

	<script type="text/javascript">
		function setBankInfo(Info)
		{
			setElement('field(contactBankCode)',Info["code"]);
			setElement('field(contactBankName)',Info["name"]);
			setElement('field(contactBankPhone)',Info["phone"]);
			setElement('field(bank)',Info["code"]);
		}
		
	</script>
	<html:hidden property="kind" value="C"/>
	<tiles:insert definition="paymentForm" flush="false">
			<tiles:put name="id" value="EditReceiver"/>
			<tiles:put name="name" value="Редактирование информации о получателе"/>
			<tiles:put name="description" value="Редактирование информации о получателе."/>
			<tiles:put name="buttons">
				<tiles:insert definition="commandButton" flush="false">
					<tiles:put name="commandKey"     value="button.save"/>
					<tiles:put name="commandHelpKey" value="button.save.help"/>
					<tiles:put name="bundle"  value="contactBundle"/>
					<tiles:put name="isDefault" value="true"/>
					<tiles:put name="postbackNavigation" value="true"/>
					<tiles:put name="image" value="iconSm_save.gif"/>
				</tiles:insert>
			</tiles:put>
			<tiles:put name="data">
				<tr>
					<td class="Width120 Label">Фамилия<span class="asterisk">*</span></td>
					<td><html:text property="field(surName)" size="20"/></td>
				</tr>
				<tr>
					<td class="Width120 Label">Имя<span class="asterisk">*</span></td>
					<td><html:text property="field(firstName)" size="20"/></td>
				</tr>
				<tr>
					<td class="Width120 Label">Отчество</td>
					<td><html:text property="field(patrName)" size="20"/></td>
				</tr>
				<tr>
					<td class="Width120 Label">Дата рождения</td>
					<td><html:text property="field(birthDay)" size="20" maxlength="12" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);"/></td>
				</tr>
				<tr>
					<td class="Width120 Label">
						Код банка<span class="asterisk">*</span>
					</td>
					<td><html:text property="field(contactBankCode)" readonly="true" size="8" styleId="field(contactBankCode)"/>
						<input type="button" class="buttWhite smButt" onclick="javascript:openContactMembersDictionary(setBankInfo,getFieldValue('field(contactBankCode)'));" value="..."/>
					</td>
				</tr>
				<tr>
					<td class="Width120 Label">Наименование банка</td>
					<td><html:text property="field(contactBankName)" readonly="true" size="50"/></td>
				</tr>
				<tr>
					<td class="Width120 Label">Телефон</td>
					<td><html:text property="field(contactBankPhone)" readonly="true" size="20"/></td>
				</tr>
				<tr>
					<td class="Width120 Label">Доп. информация</td>
					<td><html:text property="field(addInfo)" size="50"/></td>
				</tr>
				<html:hidden property="field(bank)"/>
			</tiles:put>
			<tiles:put name="alignTable" value="center"/>
		</tiles:insert>
	</tiles:put>

</tiles:insert>

</html:form>