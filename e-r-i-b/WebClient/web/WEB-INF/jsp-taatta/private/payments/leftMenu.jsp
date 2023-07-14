<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<span class="infoTitle backTransparent">Шаблоны</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="180px">
	<tr>
		<td>
			<tiles:insert definition="leftMenuLink" service="ClientTemplatesManagement">
				<tiles:put name="enabled" value="${submenu != 'AllTemplates'}"/>
				<tiles:put name="action" value="/private/templates.do"/>
				<tiles:put name="text" value="Шаблоны платежей"/>
				<tiles:put name="title" value="Шаблоны платежей"/>
			</tiles:insert>
		</td>
	</tr>
</table>
<span class="infoTitle backTransparent">Платежи</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="180px">
	<tr>
		<td>
<tiles:insert definition="leftMenuLink" service="InternalPayment">
	<tiles:put name="enabled" value="${submenu != 'InternalPayment'}"/>
	<tiles:put name="action" value="/private/payments/payment.do?form=InternalPayment"/>
	<tiles:put name="text" value="Перевод между счетами"/>
	<tiles:put name="title" value="Перевод денежных средств между вашими счетами"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="RurPayment">
	<tiles:put name="enabled" value="${submenu != 'RurPayment'}"/>
	<tiles:put name="action" value="/private/payments/payment.do?form=RurPayment"/>
	<tiles:put name="text" value="Рублевый перевод"/>
	<tiles:put name="title" value="Перечисление денежных средств с вашего счета на счет физического или юридического лица"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="CurrencyPayment">
	<tiles:put name="enabled" value="${submenu != 'CurrencyPayment'}"/>
	<tiles:put name="action" value="/private/payments/payment.do?form=CurrencyPayment"/>
	<tiles:put name="text" value="Перевод иностранной валюты"/>
	<tiles:put name="title" value="Перевод денежных средств с вашего счета в иностранной валюте"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="TaxPayment">
	<tiles:put name="enabled" value="${submenu != 'TaxPayment'}"/>
	<tiles:put name="action" value="/private/payments/payment.do?form=RurPayment"/>
	<tiles:put name="text" value="Налоговый платеж"/>
	<tiles:put name="title" value="Перечисление денежных средств с вашего счета в счет оплаты налогов."/>
</tiles:insert>
		</td>
	</tr>
</table>
<span class="infoTitle backTransparent">Обмен валюты</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="180px">
	<tr>
		<td>
          <tiles:insert definition="leftMenuLink" service="ConvertCurrencyPayment">
	        <tiles:put name="enabled" value="${submenu != 'ConvertCurrencyPayment'}"/>
	        <tiles:put name="action" value="/private/payments/payment.do?form=ConvertCurrencyPayment"/>
	        <tiles:put name="text" value="Конверсия иностранной валюты"/>
	        <tiles:put name="title" value="Конверсия иностранной валюты"/>
           </tiles:insert>
		</td>
	</tr>
</table>
<span class="infoTitle backTransparent">Журнал платежей</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="180px">
	<tr>
		<td>
<tiles:insert definition="leftMenuLink" service="PaymentList">
	<tiles:put name="enabled" value="${submenu != 'PaymentList/status=all'}"/>
	<tiles:put name="action" value="/private/payments/common.do?status=all"/>
	<tiles:put name="text" value="Все платежи"/>
	<tiles:put name="title" value="Все платежи"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="PaymentList">
	<tiles:put name="enabled" value="${submenu != 'PaymentList/status=uncompleted'}"/>
	<tiles:put name="action" value="/private/payments/common.do?status=uncompleted"/>
	<tiles:put name="text" value="Незавершенные платежи"/>
	<tiles:put name="title" value="Незавершенные платежи"/>
</tiles:insert>
		</td>
	</tr>
</table>

<span class="infoTitle backTransparent">Справочники</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="180px">
	<tr>
		<td>
<tiles:insert definition="leftMenuLink" service="PaymentReceiverList">
	<tiles:put name="enabled" value="${submenu != 'PaymentReceivers'}"/>
	<tiles:put name="action" value="/private/receivers/list"/>
	<tiles:put name="text" value="Справочник получателей"/>
	<tiles:put name="title" value="Справочник получателей"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="BankList">
	<tiles:put name="enabled" value="${submenu != 'Banks'}"/>
	<tiles:put name="action" value="/private/dictionary/banks/national"/>
	<tiles:put name="text" value="Справочник банков"/>
	<tiles:put name="title" value="Справочник банков"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="KBKList">
	<tiles:put name="enabled" value="${submenu != 'KBKList'}"/>
	<tiles:put name="action" value="/private/dictionary/KBK"/>
	<tiles:put name="text" value="Справочник КБК"/>
	<tiles:put name="title" value="Справочник ИБК"/>
</tiles:insert>
		</td>
	</tr>
</table>
