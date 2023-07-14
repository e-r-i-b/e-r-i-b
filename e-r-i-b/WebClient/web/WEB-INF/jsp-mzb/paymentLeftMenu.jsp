<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:importAttribute/>
<span class="headLinksLeftMenu">�������</span>
<br/>
<br/>
<span class="headLinksLeftSubMenu">�������</span>
<tiles:insert definition="leftMenuLink" service="ClientTemplatesManagement">
	<tiles:put name="enabled" value="${submenu != 'AllTemplates'}"/>
	<tiles:put name="action" value="/private/templates"/>
	<tiles:put name="text" value="������� ��������"/>
	<tiles:put name="title" value="������� ��������"/>
</tiles:insert>
<span class="headLinksLeftSubMenu"><bean:message key="label.payments.services" bundle="commonBundle"/></span>
<tiles:insert definition="leftMenuLink" service="GoodsAndServicesPayment">
	<tiles:put name="enabled" value="${submenu != 'GoodsAndServicesPayment/cellular-communication'}"/>
	<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=cellular-communication"/>
	<tiles:put name="text" value="������ ������� �����"/>
	<tiles:put name="title" value="������ ������� �����"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="GoodsAndServicesPayment">
	<tiles:put name="enabled" value="${submenu != 'GoodsAndServicesPayment/telephony'}"/>
	<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=telephony"/>
	<tiles:put name="text" value="������ ���������"/>
	<tiles:put name="title" value="������ ���������"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="GoodsAndServicesPayment">
	<tiles:put name="enabled" value="${submenu != 'GoodsAndServicesPayment/ip-telephony'}"/>
	<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=ip-telephony"/>
	<tiles:put name="text" value="�������� � IP-���������"/>
	<tiles:put name="title" value="�������� � IP-���������"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="GoodsAndServicesPayment">
	<tiles:put name="enabled" value="${submenu != 'GoodsAndServicesPayment/satellite-connection'}"/>
	<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=satellite-connection"/>
	<tiles:put name="text" value="������������ �����������"/>
	<tiles:put name="title" value="������������ �����������"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="GoodsAndServicesPayment">
	<tiles:put name="enabled" value="${submenu != 'GoodsAndServicesPayment/credit-repayment'}"/>
	<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=credit-repayment"/>
	<tiles:put name="text" value="��������� ��������"/>
	<tiles:put name="title" value="��������� ��������"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="GoodsAndServicesPayment">
	<tiles:put name="enabled" value="${submenu != 'GoodsAndServicesPayment/gkh-payment'}"/>
	<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=gkh-payment"/>
	<tiles:put name="text" value="������ ����� ��� ��� �.������"/>
	<tiles:put name="title" value="������ ����� ��� ��� �.������"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="GoodsAndServicesPayment">
	<tiles:put name="enabled" value="${submenu != 'GoodsAndServicesPayment/electric-payment'}"/>
	<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=electric-payment"/>
	<tiles:put name="text" value="������ ��������������"/>
	<tiles:put name="title" value="������ ��������������"/>
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