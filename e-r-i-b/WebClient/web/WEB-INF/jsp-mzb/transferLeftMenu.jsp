<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:importAttribute/>
<span class="headLinksLeftMenu">��������</span>
<br/>
<br/>
<span class="headLinksLeftSubMenu">�������</span>
<tiles:insert definition="leftMenuLink" service="ClientTemplatesManagement">
	<tiles:put name="enabled" value="${submenu != 'AllTemplates'}"/>
	<tiles:put name="action" value="/private/templates"/>
	<tiles:put name="text" value="������� ��������"/>
	<tiles:put name="title" value="������� ��������"/>
</tiles:insert>
<span class="headLinksLeftSubMenu">��������</span>
<tiles:insert definition="leftMenuLink" service="CardReplenishmentPayment">
	<tiles:put name="enabled" value="${submenu != 'CardReplenishmentPayment'}"/>
	<tiles:put name="action" value="/private/payments/payment.do?form=CardReplenishmentPayment"/>
	<tiles:put name="text" value="���������� ����������� �����"/>
	<tiles:put name="title" value="������� �������� ������� � ������ ����� ��� ���������� ����������� �����"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="InternalPayment">
	<tiles:put name="enabled" value="${submenu != 'InternalPayment'}"/>
	<tiles:put name="action" value="/private/payments/payment.do?form=InternalPayment"/>
	<tiles:put name="text" value="������� ����� �������"/>
	<tiles:put name="title" value="������� ����� �������"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="PurchaseSaleCurrencyPayment">
	<tiles:put name="enabled" value="${submenu != 'PurchaseSaleCurrencyPayment'}"/>
	<tiles:put name="action" value="/private/payments/payment.do?form=PurchaseSaleCurrencyPayment"/>
	<tiles:put name="text" value="�������/������� ������"/>
	<tiles:put name="title" value="�������/������� ������"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="RurPayment">
	<tiles:put name="enabled" value="${submenu != 'RurPayment'}"/>
	<tiles:put name="action" value="/private/payments/payment.do?form=RurPayment"/>
	<tiles:put name="text" value="������� ������ ��"/>
	<tiles:put name="title" value="������� ������ ��"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="TaxPayment">
	<tiles:put name="enabled" value="${submenu != 'TaxPayment'}"/>
	<tiles:put name="action" value="/private/payments/payment.do?form=TaxPayment"/>
	<tiles:put name="text" value="������ �������"/>
	<tiles:put name="title" value="������ �������"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="CurrencyPayment">
	<tiles:put name="enabled" value="${submenu != 'CurrencyPayment'}"/>
	<tiles:put name="action" value="/private/payments/payment.do?form=CurrencyPayment"/>
	<tiles:put name="text" value="������� ����������� ������"/>
	<tiles:put name="title" value="������� ����������� ������"/>
</tiles:insert>
<span class="headLinksLeftSubMenu">������ ��������</span>
<tiles:insert definition="leftMenuLink" service="PaymentList">
	<tiles:put name="enabled" value="${submenu != 'PaymentList/status=all'}"/>
	<tiles:put name="action" value="/private/payments/common.do?status=all"/>
	<tiles:put name="text" value="��� �������"/>
	<tiles:put name="title" value="��� �������"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="PaymentList">
	<tiles:put name="enabled" value="${submenu != 'PaymentList/status=uncompleted'}"/>
	<tiles:put name="action" value="/private/payments/common.do?status=uncompleted"/>
	<tiles:put name="text" value="������������� �������"/>
	<tiles:put name="title" value="������������� �������"/>
</tiles:insert>
<span class="headLinksLeftSubMenu">�����������</span>
<tiles:insert definition="leftMenuLink" service="PaymentReceiverList">
	<tiles:put name="enabled" value="${submenu != 'PaymentReceivers'}"/>
	<tiles:put name="action" value="/private/receivers/list"/>
	<tiles:put name="text" value="���������� �����������"/>
	<tiles:put name="title" value="���������� �����������"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="BankList">
	<tiles:put name="enabled" value="${submenu != 'Banks'}"/>
	<tiles:put name="action" value="/private/dictionary/banks/national"/>
	<tiles:put name="text" value="���������� ������"/>
	<tiles:put name="title" value="���������� ������"/>
</tiles:insert>