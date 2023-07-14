<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<tiles:insert definition="selectPaymentype">
<tiles:put name="data" type="string" value="Создать новый шаблон"/>
<tiles:put name="data" type="string">

<script language="JavaScript">
	function add(event)
	{
		var formName = getRadioValue(document.getElementsByName('selectedFormNames'));
		preventDefault(event);
		if (formName == null)
		{
			alert("Выберите вид платежа");
			return;
		}
		window.opener.newTemplate(formName);
		window.close();
	}
</script>
<table cellspacing="2" cellpadding="0" style="margin:10px">
<tr>
	<td class="filter">Выберите вид платежа:</td>
</tr>
<tr>
	<td>
		<input type="radio" name="selectedFormNames" value="RurPayment" style="border:none"/>
		&nbsp;
		Перевод рублей РФ
	</td>
</tr>
<tr>
	<td>
		<input type="radio" name="selectedFormNames" value="TaxPayment" style="border:none"/>
		&nbsp;
		Оплата налогов
	</td>
</tr>
<tr>
	<td>
		<input type="radio" name="selectedFormNames" value="CurrencyPayment" style="border:none"/>
		&nbsp;
		Перевод иностранной валюты
	</td>
</tr>
<tr>
	<td>
		<input type="radio" name="selectedFormNames"
		       value="GoodsAndServicesPayment&appointment=cellular-communication" style="border:none"/>
		&nbsp;
		Оплата услуг сотовой связи
	</td>
</tr>
<tr>
	<td>
		<input type="radio" name="selectedFormNames" value="GoodsAndServicesPayment&appointment=telephony"
		       style="border:none"/>
		&nbsp;
		Услуги телефонии
	</td>
</tr>
<tr>
	<td>
		<input type="radio" name="selectedFormNames" value="GoodsAndServicesPayment&appointment=ip-telephony"
		       style="border:none"/>
		&nbsp;
		Оплата Интернет и IP-телефонии
	</td>
</tr>
<tr>
	<td>
		<input type="radio" name="selectedFormNames"
		       value="GoodsAndServicesPayment&appointment=satellite-connection" style="border:none"/>
		&nbsp;
		Оплата коммерческого ТВ
	</td>
</tr>
<tr>
	<td>
		<input type="radio" name="selectedFormNames"
		       value="GoodsAndServicesPayment&appointment=credit-repayment" style="border:none"/>
		&nbsp;
		Погашение кредита
	</td>
</tr>
<tr>
	<td>
		<input type="radio" name="selectedFormNames"
		       value="GoodsAndServicesPayment&appointment=payment-system" style="border:none"/>
		&nbsp;
		Пополнение счета в платежной системе
	</td>
</tr>
<tr>
	<td>
		<input type="radio" name="selectedFormNames"
		       value="GoodsAndServicesPayment&appointment=gkh-payment" style="border:none"/>
		&nbsp;
		Оплата услуг ЖКХ для Москвы
	</td>
</tr>
<tr>
	<td>
		<input type="radio" name="selectedFormNames"
		       value="GoodsAndServicesPayment&appointment=electric-payment" style="border:none"/>
		&nbsp;
		Оплата электроэнергии
	</td>
</tr>

<tr>
	<td align="center">
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey"    value="button.next"/>
			<tiles:put name="commandHelpKey"    value="button.next"/>
			<tiles:put name="bundle"            value="paymentsBundle"/>
			<tiles:put name="onclick"           value="add(event)"/>
			<tiles:put name="image"             value="next.gif"/>
		</tiles:insert>
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey"    value="button.cancel"/>
			<tiles:put name="commandHelpKey"    value="button.cancel"/>
			<tiles:put name="bundle"            value="commonBundle"/>
			<tiles:put name="onclick"           value="window.close()"/>
			<tiles:put name="image"             value="back.gif"/>
		</tiles:insert>
	</td>
</tr>
</table>
</body>
</tiles:put>
</tiles:insert>