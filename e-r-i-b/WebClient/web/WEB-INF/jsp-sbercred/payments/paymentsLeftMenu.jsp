<%--
  User: Zhuravleva
  Date: 24.10.2007
  Time: 13:25:46
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<span class="infoTitle backTransparent">Шаблоны</span>
<br/>

<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="80%">
<tr>
<td>
<tiles:insert definition="leftMenuLink" service="ClientTemplatesManagement">
		<tiles:put name="enabled" value="${submenu != 'AllTemplates'}"/>
		<tiles:put name="action"  value="/private/templates.do"/>
		<tiles:put name="text"    value="Шаблоны платежей"/>
		<tiles:put name="title"   value="Шаблоны платежей"/>
</tiles:insert>
</td>
</tr>
</table>

<span class="infoTitle backTransparent">Переводы</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="80%">
<tr>
<td>
<tiles:insert definition="leftMenuLink" service="InternalPayment">
	<tiles:put name="enabled" value="${submenu != 'InternalPayment'}"/>
	<tiles:put name="action"  value="/private/payments/payment?form=InternalPayment"/>
	<tiles:put name="text"    value="Перевод между счетами"/>
	<tiles:put name="title"   value="Перевод денежных средств между вашими счетами"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="RurPayment">
	<tiles:put name="enabled" value="${submenu != 'RurPayment'}"/>
	<tiles:put name="action"  value="/private/payments/payment?form=RurPayment"/>
	<tiles:put name="text"    value="Перевод рублей РФ"/>
	<tiles:put name="title"   value="Перечисление денежных средств с вашего счета на счет физического или юридического лица"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="CardReplenishmentPayment">
	<tiles:put name="enabled" value="${submenu != 'CardReplenishmentPayment'}"/>
	<tiles:put name="action" value="/private/payments/payment.do?form=CardReplenishmentPayment"/>
	<tiles:put name="text" value="Пополнение пластиковой карты"/>
	<tiles:put name="title" value="Перевод денежных средств с вашего счета для пополнения пластиковой карты"/>
</tiles:insert>
</td>
</tr>
</table>

<span class="infoTitle backTransparent">Журнал платежей</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="80%">
<tr>
<td>
<tiles:insert definition="leftMenuLink" service="PaymentList">
	<tiles:put name="enabled" value="${submenu != 'PaymentList/status=all'}"/>
	<tiles:put name="action" value="/private/payments/common.do?status=all"/>
	<tiles:put name="text"    value="Все платежи"/>
	<tiles:put name="title"   value="Все платежи"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="PaymentList">
	<tiles:put name="enabled" value="${submenu != 'PaymentList/status=uncompleted'}"/>
	<tiles:put name="action" value="/private/payments/common.do?status=uncompleted"/>
	<tiles:put name="text"    value="Незавершенные платежи"/>
	<tiles:put name="title"   value="Незавершенные платежи"/>
</tiles:insert>
</td>
</tr>
</table>

<span class="infoTitle backTransparent">Справочники</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="80%">
<tr>
<td>
<tiles:insert definition="leftMenuLink" service="PaymentReceiverList">
	<tiles:put name="enabled" value="${submenu != 'PaymentReceiverList'}"/>
	<tiles:put name="action"  value="/private/receivers/list.do"/>
	<tiles:put name="text"    value="Справочник получателей"/>
	<tiles:put name="title"   value="Справочник получателей"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="BankList">
	<tiles:put name="enabled" value="${submenu != 'BankList'}"/>
	<tiles:put name="action"  value="/private/dictionary/banks/national"/>
	<tiles:put name="text"    value="Справочник банков"/>
	<tiles:put name="title"   value="Справочник банков"/>
</tiles:insert>
</td>
</tr>
</table>