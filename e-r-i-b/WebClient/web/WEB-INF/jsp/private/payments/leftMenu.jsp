<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>

<tiles:insert definition="leftMenuInset" service="ClientTemplatesManagement">
	<tiles:put name="enabled" value="${submenu != 'AllTemplates'}"/>
	<tiles:put name="action" value="/private/templates.do"/>
	<tiles:put name="text" value="������� ��������"/>
	<tiles:put name="title" value="������� ��������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInsetGroup">
	<tiles:put name="text"    value="��������"/>
	<tiles:put name="name"    value="Payment"/>
    <tiles:put name="enabled" value="${submenu != 'InternalPayment' and submenu != 'RurPayment' and submenu != 'CurrencyPayment' and submenu != 'TaxPayment' and submenu != 'ContactPayment' and submenu != 'CardReplenishmentPayment'}"/>
	<tiles:put name="data">
		<tiles:insert definition="leftMenuInset" service="InternalPayment"  flush="false">
			<tiles:put name="enabled" value="${submenu != 'InternalPayment'}"/>
			<tiles:put name="action" value="/private/payments/payment.do?form=InternalPayment"/>
			<tiles:put name="text" value="������� ����� �������"/>
			<tiles:put name="title" value="������� �������� ������� ����� ������ �������"/>
			<tiles:put name="parentName" value="Payment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="RurPayment"  flush="false">
			<tiles:put name="enabled" value="${submenu != 'RurPayment'}"/>
			<tiles:put name="action" value="/private/payments/payment.do?form=RurPayment"/>
			<tiles:put name="text" value="�������� �������"/>
			<tiles:put name="title" value="������������ �������� ������� � ������ ����� �� ���� ����������� ��� ������������ ����"/>
			<tiles:put name="parentName" value="Payment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="CurrencyPayment"  flush="false">
			<tiles:put name="enabled" value="${submenu != 'CurrencyPayment'}"/>
			<tiles:put name="action" value="/private/payments/payment.do?form=CurrencyPayment"/>
			<tiles:put name="text" value="������� ����������� ������"/>
			<tiles:put name="title" value="������� �������� ������� � ������ ����� � ����������� ������"/>
			<tiles:put name="parentName" value="Payment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="TaxPayment"  flush="false">
			<tiles:put name="enabled" value="${submenu != 'TaxPayment'}"/>
			<tiles:put name="action" value="/private/payments/payment.do?form=TaxPayment"/>
			<tiles:put name="text" value="��������� ������"/>
			<tiles:put name="title" value="������������ �������� ������� � ������ ����� � ���� ������ �������."/>
			<tiles:put name="parentName" value="Payment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="ContactPayment"  flush="false">
			<tiles:put name="enabled" value="${submenu != 'ContactPayment'}"/>
			<tiles:put name="action" value="/private/payments/payment.do?form=ContactPayment"/>
			<tiles:put name="text" value="������� �� ���� Contact"/>
			<tiles:put name="title" value="������� �������� ������� � ������ ����� ��� ������ ���������� ��������� � ������ ��������� ���� Contact"/>
			<tiles:put name="parentName" value="Payment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="CardReplenishmentPayment"  flush="false">
			<tiles:put name="enabled" value="${submenu != 'CardReplenishmentPayment'}"/>
			<tiles:put name="action" value="/private/payments/payment.do?form=CardReplenishmentPayment"/>
			<tiles:put name="text" value="���������� ����������� �����"/>
			<tiles:put name="title" value="������� �������� ������� � ������ ����� ��� ���������� ����������� �����"/>
			<tiles:put name="parentName" value="Payment"/>
		</tiles:insert>
	</tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInsetGroup">
   <tiles:put name="text"    value="����� ������"/>
   <tiles:put name="name"    value="CurrencyPayment"/>
    <tiles:put name="enabled" value="${submenu != 'PurchaseSaleCurrencyPayment' and submenu != 'ConvertCurrencyPayment'}"/>
   <tiles:put name="data">
        <tiles:insert definition="leftMenuInset" service="PurchaseSaleCurrencyPayment" flush="false">
            <tiles:put name="enabled" value="${submenu != 'PurchaseSaleCurrencyPayment'}"/>
            <tiles:put name="action" value="/private/payments/payment.do?form=PurchaseSaleCurrencyPayment"/>
            <tiles:put name="text" value="�������/������� ����������� ������"/>
            <tiles:put name="title" value="�������/������� ����������� ������"/>
            <tiles:put name="parentName" value="CurrencyPayment"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" service="ConvertCurrencyPayment" flush="false">
            <tiles:put name="enabled" value="${submenu != 'ConvertCurrencyPayment'}"/>
            <tiles:put name="action" value="/private/payments/payment.do?form=ConvertCurrencyPayment"/>
            <tiles:put name="text" value="�������� ������"/>
            <tiles:put name="title" value="�������� ������"/>
            <tiles:put name="parentName" value="CurrencyPayment"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInsetGroup">
	<tiles:put name="text"><bean:message key="label.payments.services" bundle="commonBundle"/></tiles:put>
	<tiles:put name="name"    value="GoodsAndServicePayment"/>
    <tiles:put name="enabled" value="${submenu != 'GKHPayment' and submenu != 'ElectricPayment'}"/>
	<tiles:put name="data">
		<tiles:insert definition="leftMenuInset" service="GKHPayment" flush="false">
			<tiles:put name="enabled" value="${submenu != 'GKHPayment'}"/>
			<tiles:put name="action" value="/private/payments/payment.do?form=GKHPayment"/>
			<tiles:put name="text" value="������ ����� ���"/>
			<tiles:put name="title" value="������������ �������� ������� � ������ ����� ��� ������ �������-������������ ����� �� ����� ������� ���������� ���������."/>
			<tiles:put name="parentName" value="GoodsAndServicePayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="ElectricPayment" flush="false">
			<tiles:put name="enabled" value="${submenu != 'ElectricPayment'}"/>
			<tiles:put name="action" value="/private/payments/payment.do?form=ElectricPayment"/>
			<tiles:put name="text" value="������ ��������������"/>
			<tiles:put name="title" value="������� �������� ������� � ���� ������ ��������������"/>
			<tiles:put name="parentName" value="GoodsAndServicePayment"/>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
<tiles:insert definition="leftMenuInsetGroup">
	<tiles:put name="text"    value="������ ������� � ����� �� ���� Contact"/>
	<tiles:put name="name"    value="ContactPayment"/>
    <tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment') and !fn:contains(submenu,'-contact')}"/>
	<tiles:put name="data">
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/cellular-communication-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=cellular-communication-contact"/>
			<tiles:put name="text" value="������ ����� ������� �����"/>
			<tiles:put name="title" value="������ ����� ������� �����"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/inet-connection-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=inet-connection-contact"/>
			<tiles:put name="text" value="��������"/>
			<tiles:put name="title" value="��������"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/ip-telephony-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=ip-telephony-contact"/>
			<tiles:put name="text" value="IP-���������"/>
			<tiles:put name="title" value="IP-���������"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/telephony-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=telephony-contact"/>
			<tiles:put name="text" value="������ ���������"/>
			<tiles:put name="title" value="������ ���������"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/satellite-connection-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=satellite-connection-contact"/>
			<tiles:put name="text" value="������������ �����������"/>
			<tiles:put name="title" value="������������ �����������"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/credit-repayment-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=credit-repayment-contact"/>
			<tiles:put name="text" value="��������� ��������"/>
			<tiles:put name="title" value="��������� ��������"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/policy-payment-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=policy-payment-contact"/>
			<tiles:put name="text" value="��������� �����"/>
			<tiles:put name="title" value="��������� �����"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/game-portals-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=game-portals-contact"/>
			<tiles:put name="text" value="������� �������"/>
			<tiles:put name="title" value="������� �������"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/hotel-payment-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=hotel-payment-contact"/>
			<tiles:put name="text" value="���������"/>
			<tiles:put name="title" value="���������"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/realty-operation-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=realty-operation-contact"/>
			<tiles:put name="text" value="������������"/>
			<tiles:put name="title" value="������������"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/travel-agency-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=travel-agency-contact"/>
			<tiles:put name="text" value="����������"/>
			<tiles:put name="title" value="����������"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/inet-shops-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=inet-shops-contact"/>
			<tiles:put name="text" value="��������-��������"/>
			<tiles:put name="title" value="��������-��������"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/air-ticket-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=air-ticket-contact"/>
			<tiles:put name="text" value="����������"/>
			<tiles:put name="title" value="����������"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/payment-system-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=payment-system-contact"/>
			<tiles:put name="text" value="��������� �������"/>
			<tiles:put name="title" value="��������� �������"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
	</tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInsetGroup">
	<tiles:put name="text"    value="������ ������� � ����� ����� ��������� ������� Rapida"/>
	<tiles:put name="name"    value="RapidaPayment"/>
    <tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment') and !fn:contains(submenu,'-rapida')}"/>
	<tiles:put name="data">
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment" flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/cellular-communication-rapida')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=cellular-communication-rapida"/>
			<tiles:put name="text" value="������ ������� �����"/>
			<tiles:put name="title" value="������ ������� �����"/>
			<tiles:put name="parentName" value="RapidaPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/telephony-rapida')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=telephony-rapida"/>
			<tiles:put name="text" value="������ ���������"/>
			<tiles:put name="title" value="������ ���������"/>
			<tiles:put name="parentName" value="RapidaPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/ip-telephony-rapida')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=ip-telephony-rapida"/>
			<tiles:put name="text" value="�������� � IP-���������"/>
			<tiles:put name="title" value="�������� � IP-���������"/>
			<tiles:put name="parentName" value="RapidaPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/satellite-connection-rapida')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=satellite-connection-rapida"/>
			<tiles:put name="text" value="������������ �����������"/>
			<tiles:put name="title" value="������������ �����������"/>
			<tiles:put name="parentName" value="RapidaPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/credit-repayment-rapida')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=credit-repayment-rapida"/>
			<tiles:put name="text" value="��������� ��������"/>
			<tiles:put name="title" value="��������� ��������"/>
			<tiles:put name="parentName" value="RapidaPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/payment-system-rapida')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=payment-system-rapida"/>
			<tiles:put name="text" value="���������� ����� � ��������� �������"/>
			<tiles:put name="title" value="���������� ����� � ��������� �������"/>
			<tiles:put name="parentName" value="RapidaPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/gkh-payment-rapida')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=gkh-payment-rapida"/>
			<tiles:put name="text" value="������ ����� ��� ��� �.������"/>
			<tiles:put name="title" value="������ ����� ��� ��� �.������"/>
			<tiles:put name="parentName" value="RapidaPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/electric-payment-rapida')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=electric-payment-rapida"/>
			<tiles:put name="text" value="������ ��������������"/>
			<tiles:put name="title" value="������ ��������������"/>
			<tiles:put name="parentName" value="RapidaPayment"/>
		</tiles:insert>
	</tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInsetGroup">
	<tiles:put name="text"    value="������ ��������"/>
	<tiles:put name="name"    value="PaymentList"/>
    <tiles:put name="enabled" value="${submenu != 'PaymentList/status=all' and submenu != 'PaymentList/status=uncompleted'}"/>
	<tiles:put name="data">
		<tiles:insert definition="leftMenuInset" service="PaymentList" flush="false">
			<tiles:put name="enabled" value="${submenu != 'PaymentList/status=all'}"/>
			<tiles:put name="action" value="/private/payments/common.do?status=all"/>
			<tiles:put name="text" value="��� ��������"/>
			<tiles:put name="title" value="��� ��������"/>
			<tiles:put name="parentName" value="PaymentList"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="PaymentList" flush="false">
			<tiles:put name="enabled" value="${submenu != 'PaymentList/status=uncompleted'}"/>
			<tiles:put name="action" value="/private/payments/common.do?status=uncompleted"/>
			<tiles:put name="text" value="������������� ��������"/>
			<tiles:put name="title" value="������������� ��������"/>
			<tiles:put name="parentName" value="PaymentList"/>
		</tiles:insert>
	</tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInsetGroup">
	<tiles:put name="text"    value="�����������"/>
	<tiles:put name="name"    value="Dictionaries"/>
    <tiles:put name="enabled" value="${submenu != 'PaymentReceiverList' and submenu != 'BankList' and submenu != 'ReceiverContact'}"/>
	<tiles:put name="data">
		<tiles:insert definition="leftMenuInset" service="PaymentReceiverList" flush="false">
			<tiles:put name="enabled" value="${submenu != 'PaymentReceiverList'}"/>
			<tiles:put name="action" value="/private/receivers/list?kind=PJ"/>
			<tiles:put name="text" value="���������� �����������"/>
			<tiles:put name="title" value="���������� �����������"/>
			<tiles:put name="parentName" value="Dictionaries"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="BankList" flush="false">
			<tiles:put name="enabled" value="${submenu != 'BankList'}"/>
			<tiles:put name="action" value="/private/dictionary/banks/national"/>
			<tiles:put name="text" value="���������� ������"/>
			<tiles:put name="title" value="���������� ������"/>
			<tiles:put name="parentName" value="Dictionaries"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="ContactReceiverList" flush="false">
			<tiles:put name="enabled" value="${submenu != 'ReceiverContact'}"/>
			<tiles:put name="action" value="/private/contact/receivers/list.do?kind=C"/>
			<tiles:put name="text" value="���������� ����������� Contact"/>
			<tiles:put name="parentName" value="Dictionaries"/>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
