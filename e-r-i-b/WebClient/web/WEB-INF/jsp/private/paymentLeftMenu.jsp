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

<span class="infoTitle backTransparent">�������</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="180px">
	<tr>
		<td>
<tiles:insert definition="leftMenuLink" service="CurrencyPayment">
		<tiles:put name="enabled" value="${submenu != 'CurrencyPayment'}"/>
		<tiles:put name="action"  value="/private/payments/payment.do?form=CurrencyPayment"/>
		<tiles:put name="text"    value="������� ����������� ������"/>
		<tiles:put name="title"   value="������� ����������� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="GKHPayment">
		<tiles:put name="enabled" value="${submenu != 'GKHPayment'}"/>
		<tiles:put name="action"  value="/private/payments/payment.do?form=GKHPayment"/>
		<tiles:put name="text"    value="������ ����� ���"/>
		<tiles:put name="title"   value="������ ����� ���"/>
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
		<tiles:put name="text"    value="�������/������� ����������� ������"/>
		<tiles:put name="title"   value="�������/������� ����������� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="ConvertCurrencyPayment">
		<tiles:put name="enabled" value="${submenu != 'ConvertCurrencyPayment'}"/>
		<tiles:put name="action"  value="/private/payments/payment.do?form=ConvertCurrencyPayment"/>
		<tiles:put name="text"    value="��������� ����������� ������"/>
		<tiles:put name="title"   value="��������� ����������� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="GetTemplatesOperation" operation="*">
		<tiles:put name="enabled" value="${submenu != 'AllTemplates'}"/>
		<tiles:put name="action"  value="/private/templates.do"/>
		<tiles:put name="text"    value="��� �������"/>
		<tiles:put name="title"   value="��� �������"/>
</tiles:insert>
		</td>
	</tr>
</table>

<span class="infoTitle backTransparent">��������� ���������</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="180px">
	<tr>
		<td>
<tiles:insert definition="leftMenuLink" service="PaymentDocumentPreparation">
		<tiles:put name="enabled" value="${submenu != 'FILLFORM'}"/>
		<tiles:put name="action"  value="/private/PD4.do"/>
		<tiles:put name="text"    value="����� ��-4 � ��-4�� (�����)"/>
		<tiles:put name="title"   value="����� ��-4 � ��-4�� (�����)"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="PaymentDocumentPreparation">
		<tiles:put name="enabled" value="${submenu != 'FILLFORM'}"/>
		<tiles:put name="action"  value="/private/PD4.do&page=pay"/>
		<tiles:put name="text"    value="��������� ���������"/>
		<tiles:put name="title"   value="��������� ���������"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="PaymentDocumentPreparation">
		<tiles:put name="enabled" value="${submenu != 'FILLFORM'}"/>
		<tiles:put name="action"  value="/private/PD4.do&page=letter"/>
		<tiles:put name="text"    value="���������� ���������"/>
		<tiles:put name="title"   value="���������� ���������"/>
</tiles:insert>
		</td>
	</tr>
</table>
<span class="infoTitle backTransparent">���������� ���������</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="180px">
	<tr>
		<td>
<tiles:insert definition="leftMenuLink" service="ConvertCurrencyLongOfferPayment">
		<tiles:put name="enabled" value="${submenu != 'ConvertCurrencyLongOfferPayment'}"/>
		<tiles:put name="action"  value="/private/payments/payment.do?form=ConvertCurrencyLongOfferPayment"/>
		<tiles:put name="text"    value="�������� ���������"/>
		<tiles:put name="title"   value="�������� ���������"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="ShowLongOffer">
		<tiles:put name="enabled" value="${submenu != 'ShowLongOffer'}"/>
		<tiles:put name="action"  value="/private/accounts/showlong"/>
		<tiles:put name="text"    value="����� �� ����������"/>
		<tiles:put name="title"   value="����� �� ����������"/>
</tiles:insert>
		</td>
	</tr>
</table>