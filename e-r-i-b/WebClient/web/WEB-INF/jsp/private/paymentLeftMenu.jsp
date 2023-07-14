<%--
  User: Zhuravleva
  Date: 18.11.2005
  Time: 18:30:52
--%>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<span class="infoTitle backTransparent">Платежи</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="180px">
	<tr>
		<td>
<tiles:insert definition="leftMenuLink" service="CurrencyPayment">
		<tiles:put name="enabled" value="${submenu != 'CurrencyPayment'}"/>
		<tiles:put name="action"  value="/private/payments/payment.do?form=CurrencyPayment"/>
		<tiles:put name="text"    value="Перевод иностранной валюты"/>
		<tiles:put name="title"   value="Перевод иностранной валюты"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="GKHPayment">
		<tiles:put name="enabled" value="${submenu != 'GKHPayment'}"/>
		<tiles:put name="action"  value="/private/payments/payment.do?form=GKHPayment"/>
		<tiles:put name="text"    value="Оплата услуг ЖКХ"/>
		<tiles:put name="title"   value="Оплата услуг ЖКХ"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="RurPayJurSB">
		<tiles:put name="enabled" value="${submenu != 'RurPayJurSB'}"/>
		<tiles:put name="action"  value="/private/payments/payment.do?form=RurPayJurSB"/>
		<tiles:put name="text"><bean:message key="label.payments.services" bundle="commonBundle"/></tiles:put>
		<tiles:put name="title"><bean:message key="label.payments.services" bundle="commonBundle"/></tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="PurchaseSaleCurrencyPayment">
		<tiles:put name="enabled" value="${submenu != 'PurchaseSaleCurrencyPayment'}"/>
		<tiles:put name="action"  value="/private/payments/payment.do?form=PurchaseSaleCurrencyPayment"/>
		<tiles:put name="text"    value="Покупка/продажа иностранной валюты"/>
		<tiles:put name="title"   value="Покупка/продажа иностранной валюты"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="ConvertCurrencyPayment">
		<tiles:put name="enabled" value="${submenu != 'ConvertCurrencyPayment'}"/>
		<tiles:put name="action"  value="/private/payments/payment.do?form=ConvertCurrencyPayment"/>
		<tiles:put name="text"    value="Конверсия иностранной валюты"/>
		<tiles:put name="title"   value="Конверсия иностранной валюты"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="GetTemplatesOperation" operation="*">
		<tiles:put name="enabled" value="${submenu != 'AllTemplates'}"/>
		<tiles:put name="action"  value="/private/templates.do"/>
		<tiles:put name="text"    value="Все шаблоны"/>
		<tiles:put name="title"   value="Все шаблоны"/>
</tiles:insert>
		</td>
	</tr>
</table>

<span class="infoTitle backTransparent">Платежные документы</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="180px">
	<tr>
		<td>
<tiles:insert definition="leftMenuLink" service="PaymentDocumentPreparation">
		<tiles:put name="enabled" value="${submenu != 'FILLFORM'}"/>
		<tiles:put name="action"  value="/private/PD4.do"/>
		<tiles:put name="text"    value="Форма ПД-4 и ПД-4сб (налог)"/>
		<tiles:put name="title"   value="Форма ПД-4 и ПД-4сб (налог)"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="PaymentDocumentPreparation">
		<tiles:put name="enabled" value="${submenu != 'FILLFORM'}"/>
		<tiles:put name="action"  value="/private/PD4.do&page=pay"/>
		<tiles:put name="text"    value="Платежное поручение"/>
		<tiles:put name="title"   value="Платежное поручение"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="PaymentDocumentPreparation">
		<tiles:put name="enabled" value="${submenu != 'FILLFORM'}"/>
		<tiles:put name="action"  value="/private/PD4.do&page=letter"/>
		<tiles:put name="text"    value="Инкассовое поручение"/>
		<tiles:put name="title"   value="Инкассовое поручение"/>
</tiles:insert>
		</td>
	</tr>
</table>
<span class="infoTitle backTransparent">Длительные поручения</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="180px">
	<tr>
		<td>
<tiles:insert definition="leftMenuLink" service="ConvertCurrencyLongOfferPayment">
		<tiles:put name="enabled" value="${submenu != 'ConvertCurrencyLongOfferPayment'}"/>
		<tiles:put name="action"  value="/private/payments/payment.do?form=ConvertCurrencyLongOfferPayment"/>
		<tiles:put name="text"    value="Оформить поручение"/>
		<tiles:put name="title"   value="Оформить поручение"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="ShowLongOffer">
		<tiles:put name="enabled" value="${submenu != 'ShowLongOffer'}"/>
		<tiles:put name="action"  value="/private/accounts/showlong"/>
		<tiles:put name="text"    value="Отчет об исполнении"/>
		<tiles:put name="title"   value="Отчет об исполнении"/>
</tiles:insert>
		</td>
	</tr>
</table>