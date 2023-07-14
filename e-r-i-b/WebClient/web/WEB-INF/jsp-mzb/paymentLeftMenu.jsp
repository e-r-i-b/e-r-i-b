<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:importAttribute/>
<span class="headLinksLeftMenu">Платежи</span>
<br/>
<br/>
<span class="headLinksLeftSubMenu">Шаблоны</span>
<tiles:insert definition="leftMenuLink" service="ClientTemplatesManagement">
	<tiles:put name="enabled" value="${submenu != 'AllTemplates'}"/>
	<tiles:put name="action" value="/private/templates"/>
	<tiles:put name="text" value="Шаблоны платежей"/>
	<tiles:put name="title" value="Шаблоны платежей"/>
</tiles:insert>
<span class="headLinksLeftSubMenu"><bean:message key="label.payments.services" bundle="commonBundle"/></span>
<tiles:insert definition="leftMenuLink" service="GoodsAndServicesPayment">
	<tiles:put name="enabled" value="${submenu != 'GoodsAndServicesPayment/cellular-communication'}"/>
	<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=cellular-communication"/>
	<tiles:put name="text" value="Оплата сотовой связи"/>
	<tiles:put name="title" value="Оплата сотовой связи"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="GoodsAndServicesPayment">
	<tiles:put name="enabled" value="${submenu != 'GoodsAndServicesPayment/telephony'}"/>
	<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=telephony"/>
	<tiles:put name="text" value="Услуги телефонии"/>
	<tiles:put name="title" value="Услуги телефонии"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="GoodsAndServicesPayment">
	<tiles:put name="enabled" value="${submenu != 'GoodsAndServicesPayment/ip-telephony'}"/>
	<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=ip-telephony"/>
	<tiles:put name="text" value="Интернет и IP-телефония"/>
	<tiles:put name="title" value="Интернет и IP-телефония"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="GoodsAndServicesPayment">
	<tiles:put name="enabled" value="${submenu != 'GoodsAndServicesPayment/satellite-connection'}"/>
	<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=satellite-connection"/>
	<tiles:put name="text" value="Коммерческое телевидение"/>
	<tiles:put name="title" value="Коммерческое телевидение"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="GoodsAndServicesPayment">
	<tiles:put name="enabled" value="${submenu != 'GoodsAndServicesPayment/credit-repayment'}"/>
	<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=credit-repayment"/>
	<tiles:put name="text" value="Погашение кредитов"/>
	<tiles:put name="title" value="Погашение кредитов"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="GoodsAndServicesPayment">
	<tiles:put name="enabled" value="${submenu != 'GoodsAndServicesPayment/gkh-payment'}"/>
	<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=gkh-payment"/>
	<tiles:put name="text" value="Оплата услуг ЖКХ для г.Москва"/>
	<tiles:put name="title" value="Оплата услуг ЖКХ для г.Москва"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="GoodsAndServicesPayment">
	<tiles:put name="enabled" value="${submenu != 'GoodsAndServicesPayment/electric-payment'}"/>
	<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=electric-payment"/>
	<tiles:put name="text" value="Оплата электроэнергии"/>
	<tiles:put name="title" value="Оплата электроэнергии"/>
</tiles:insert>
<span class="headLinksLeftSubMenu">Журнал платежей</span>
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