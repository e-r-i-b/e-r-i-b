<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:importAttribute/>
<span class="headLinksLeftMenu">Переводы</span>
<br/>
<br/>
<span class="headLinksLeftSubMenu">Шаблоны</span>
<tiles:insert definition="leftMenuLink" service="ClientTemplatesManagement">
	<tiles:put name="enabled" value="${submenu != 'AllTemplates'}"/>
	<tiles:put name="action" value="/private/templates"/>
	<tiles:put name="text" value="Шаблоны платежей"/>
	<tiles:put name="title" value="Шаблоны платежей"/>
</tiles:insert>
<span class="headLinksLeftSubMenu">Переводы</span>
<tiles:insert definition="leftMenuLink" service="CardReplenishmentPayment">
	<tiles:put name="enabled" value="${submenu != 'CardReplenishmentPayment'}"/>
	<tiles:put name="action" value="/private/payments/payment.do?form=CardReplenishmentPayment"/>
	<tiles:put name="text" value="Пополнение пластиковой карты"/>
	<tiles:put name="title" value="Перевод денежных средств с вашего счета для пополнения пластиковой карты"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="InternalPayment">
	<tiles:put name="enabled" value="${submenu != 'InternalPayment'}"/>
	<tiles:put name="action" value="/private/payments/payment.do?form=InternalPayment"/>
	<tiles:put name="text" value="Перевод между счетами"/>
	<tiles:put name="title" value="Перевод между счетами"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="PurchaseSaleCurrencyPayment">
	<tiles:put name="enabled" value="${submenu != 'PurchaseSaleCurrencyPayment'}"/>
	<tiles:put name="action" value="/private/payments/payment.do?form=PurchaseSaleCurrencyPayment"/>
	<tiles:put name="text" value="Покупка/продажа валюты"/>
	<tiles:put name="title" value="Покупка/продажа валюты"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="RurPayment">
	<tiles:put name="enabled" value="${submenu != 'RurPayment'}"/>
	<tiles:put name="action" value="/private/payments/payment.do?form=RurPayment"/>
	<tiles:put name="text" value="Перевод рублей РФ"/>
	<tiles:put name="title" value="Перевод рублей РФ"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="TaxPayment">
	<tiles:put name="enabled" value="${submenu != 'TaxPayment'}"/>
	<tiles:put name="action" value="/private/payments/payment.do?form=TaxPayment"/>
	<tiles:put name="text" value="Оплата налогов"/>
	<tiles:put name="title" value="Оплата налогов"/>
</tiles:insert>
<tiles:insert definition="leftMenuLink" service="CurrencyPayment">
	<tiles:put name="enabled" value="${submenu != 'CurrencyPayment'}"/>
	<tiles:put name="action" value="/private/payments/payment.do?form=CurrencyPayment"/>
	<tiles:put name="text" value="Перевод иностранной валюты"/>
	<tiles:put name="title" value="Перевод иностранной валюты"/>
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
<span class="headLinksLeftSubMenu">Справочники</span>
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