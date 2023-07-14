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
	<tiles:put name="text" value="Шаблоны платежей"/>
	<tiles:put name="title" value="Шаблоны платежей"/>
</tiles:insert>

<tiles:insert definition="leftMenuInsetGroup">
	<tiles:put name="text"    value="Переводы"/>
	<tiles:put name="name"    value="Payment"/>
    <tiles:put name="enabled" value="${submenu != 'InternalPayment' and submenu != 'RurPayment' and submenu != 'CurrencyPayment' and submenu != 'TaxPayment' and submenu != 'ContactPayment' and submenu != 'CardReplenishmentPayment'}"/>
	<tiles:put name="data">
		<tiles:insert definition="leftMenuInset" service="InternalPayment"  flush="false">
			<tiles:put name="enabled" value="${submenu != 'InternalPayment'}"/>
			<tiles:put name="action" value="/private/payments/payment.do?form=InternalPayment"/>
			<tiles:put name="text" value="Перевод между счетами"/>
			<tiles:put name="title" value="Перевод денежных средств между вашими счетами"/>
			<tiles:put name="parentName" value="Payment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="RurPayment"  flush="false">
			<tiles:put name="enabled" value="${submenu != 'RurPayment'}"/>
			<tiles:put name="action" value="/private/payments/payment.do?form=RurPayment"/>
			<tiles:put name="text" value="Рублевый перевод"/>
			<tiles:put name="title" value="Перечисление денежных средств с вашего счета на счет физического или юридического лица"/>
			<tiles:put name="parentName" value="Payment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="CurrencyPayment"  flush="false">
			<tiles:put name="enabled" value="${submenu != 'CurrencyPayment'}"/>
			<tiles:put name="action" value="/private/payments/payment.do?form=CurrencyPayment"/>
			<tiles:put name="text" value="Перевод иностранной валюты"/>
			<tiles:put name="title" value="Перевод денежных средств с вашего счета в иностранной валюте"/>
			<tiles:put name="parentName" value="Payment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="TaxPayment"  flush="false">
			<tiles:put name="enabled" value="${submenu != 'TaxPayment'}"/>
			<tiles:put name="action" value="/private/payments/payment.do?form=TaxPayment"/>
			<tiles:put name="text" value="Налоговый платеж"/>
			<tiles:put name="title" value="Перечисление денежных средств с вашего счета в счет оплаты налогов."/>
			<tiles:put name="parentName" value="Payment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="ContactPayment"  flush="false">
			<tiles:put name="enabled" value="${submenu != 'ContactPayment'}"/>
			<tiles:put name="action" value="/private/payments/payment.do?form=ContactPayment"/>
			<tiles:put name="text" value="Перевод по сети Contact"/>
			<tiles:put name="title" value="Перевод денежных средств с вашего счета для выдачи получателю наличными в точках платежной сети Contact"/>
			<tiles:put name="parentName" value="Payment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="CardReplenishmentPayment"  flush="false">
			<tiles:put name="enabled" value="${submenu != 'CardReplenishmentPayment'}"/>
			<tiles:put name="action" value="/private/payments/payment.do?form=CardReplenishmentPayment"/>
			<tiles:put name="text" value="Пополнение пластиковой карты"/>
			<tiles:put name="title" value="Перевод денежных средств с вашего счета для пополнения пластиковой карты"/>
			<tiles:put name="parentName" value="Payment"/>
		</tiles:insert>
	</tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInsetGroup">
   <tiles:put name="text"    value="Обмен валюты"/>
   <tiles:put name="name"    value="CurrencyPayment"/>
    <tiles:put name="enabled" value="${submenu != 'PurchaseSaleCurrencyPayment' and submenu != 'ConvertCurrencyPayment'}"/>
   <tiles:put name="data">
        <tiles:insert definition="leftMenuInset" service="PurchaseSaleCurrencyPayment" flush="false">
            <tiles:put name="enabled" value="${submenu != 'PurchaseSaleCurrencyPayment'}"/>
            <tiles:put name="action" value="/private/payments/payment.do?form=PurchaseSaleCurrencyPayment"/>
            <tiles:put name="text" value="Покупка/продажа иностранной валюты"/>
            <tiles:put name="title" value="Покупка/продажа иностранной валюты"/>
            <tiles:put name="parentName" value="CurrencyPayment"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" service="ConvertCurrencyPayment" flush="false">
            <tiles:put name="enabled" value="${submenu != 'ConvertCurrencyPayment'}"/>
            <tiles:put name="action" value="/private/payments/payment.do?form=ConvertCurrencyPayment"/>
            <tiles:put name="text" value="Обменять валюту"/>
            <tiles:put name="title" value="Обменять валюту"/>
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
			<tiles:put name="text" value="Оплата услуг ЖКХ"/>
			<tiles:put name="title" value="Перечисление денежных средств с вашего счета для оплаты жилищно-коммунальных услуг по форме единого платежного документа."/>
			<tiles:put name="parentName" value="GoodsAndServicePayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="ElectricPayment" flush="false">
			<tiles:put name="enabled" value="${submenu != 'ElectricPayment'}"/>
			<tiles:put name="action" value="/private/payments/payment.do?form=ElectricPayment"/>
			<tiles:put name="text" value="Оплата электроэнергии"/>
			<tiles:put name="title" value="Перевод денежных средств в счет оплаты электроэнергии"/>
			<tiles:put name="parentName" value="GoodsAndServicePayment"/>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
<tiles:insert definition="leftMenuInsetGroup">
	<tiles:put name="text"    value="Оплата товаров и услуг по сети Contact"/>
	<tiles:put name="name"    value="ContactPayment"/>
    <tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment') and !fn:contains(submenu,'-contact')}"/>
	<tiles:put name="data">
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/cellular-communication-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=cellular-communication-contact"/>
			<tiles:put name="text" value="Оплата услуг сотовой связи"/>
			<tiles:put name="title" value="Оплата услуг сотовой связи"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/inet-connection-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=inet-connection-contact"/>
			<tiles:put name="text" value="Интернет"/>
			<tiles:put name="title" value="Интернет"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/ip-telephony-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=ip-telephony-contact"/>
			<tiles:put name="text" value="IP-телефония"/>
			<tiles:put name="title" value="IP-телефония"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/telephony-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=telephony-contact"/>
			<tiles:put name="text" value="Услуги телефонии"/>
			<tiles:put name="title" value="Услуги телефонии"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/satellite-connection-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=satellite-connection-contact"/>
			<tiles:put name="text" value="Коммерческое телевидение"/>
			<tiles:put name="title" value="Коммерческое телевидение"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/credit-repayment-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=credit-repayment-contact"/>
			<tiles:put name="text" value="Погашение кредитов"/>
			<tiles:put name="title" value="Погашение кредитов"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/policy-payment-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=policy-payment-contact"/>
			<tiles:put name="text" value="Страховой полис"/>
			<tiles:put name="title" value="Страховой полис"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/game-portals-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=game-portals-contact"/>
			<tiles:put name="text" value="Игровые порталы"/>
			<tiles:put name="title" value="Игровые порталы"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/hotel-payment-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=hotel-payment-contact"/>
			<tiles:put name="text" value="Гостиницы"/>
			<tiles:put name="title" value="Гостиницы"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/realty-operation-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=realty-operation-contact"/>
			<tiles:put name="text" value="Недвижимость"/>
			<tiles:put name="title" value="Недвижимость"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/travel-agency-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=travel-agency-contact"/>
			<tiles:put name="text" value="Турпоездки"/>
			<tiles:put name="title" value="Турпоездки"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/inet-shops-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=inet-shops-contact"/>
			<tiles:put name="text" value="Интернет-магазины"/>
			<tiles:put name="title" value="Интернет-магазины"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/air-ticket-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=air-ticket-contact"/>
			<tiles:put name="text" value="Авиабилеты"/>
			<tiles:put name="title" value="Авиабилеты"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/payment-system-contact')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=payment-system-contact"/>
			<tiles:put name="text" value="Платежные системы"/>
			<tiles:put name="title" value="Платежные системы"/>
			<tiles:put name="parentName" value="ContactPayment"/>
		</tiles:insert>
	</tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInsetGroup">
	<tiles:put name="text"    value="Оплата товаров и услуг через платежную систему Rapida"/>
	<tiles:put name="name"    value="RapidaPayment"/>
    <tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment') and !fn:contains(submenu,'-rapida')}"/>
	<tiles:put name="data">
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment" flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/cellular-communication-rapida')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=cellular-communication-rapida"/>
			<tiles:put name="text" value="Оплата сотовой связи"/>
			<tiles:put name="title" value="Оплата сотовой связи"/>
			<tiles:put name="parentName" value="RapidaPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/telephony-rapida')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=telephony-rapida"/>
			<tiles:put name="text" value="Услуги телефонии"/>
			<tiles:put name="title" value="Услуги телефонии"/>
			<tiles:put name="parentName" value="RapidaPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/ip-telephony-rapida')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=ip-telephony-rapida"/>
			<tiles:put name="text" value="Интернет и IP-телефония"/>
			<tiles:put name="title" value="Интернет и IP-телефония"/>
			<tiles:put name="parentName" value="RapidaPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/satellite-connection-rapida')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=satellite-connection-rapida"/>
			<tiles:put name="text" value="Коммерческое телевидение"/>
			<tiles:put name="title" value="Коммерческое телевидение"/>
			<tiles:put name="parentName" value="RapidaPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/credit-repayment-rapida')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=credit-repayment-rapida"/>
			<tiles:put name="text" value="Погашение кредитов"/>
			<tiles:put name="title" value="Погашение кредитов"/>
			<tiles:put name="parentName" value="RapidaPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/payment-system-rapida')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=payment-system-rapida"/>
			<tiles:put name="text" value="Пополнение счета в платежной системе"/>
			<tiles:put name="title" value="Пополнение счета в платежной системе"/>
			<tiles:put name="parentName" value="RapidaPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/gkh-payment-rapida')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=gkh-payment-rapida"/>
			<tiles:put name="text" value="Оплата услуг ЖКХ для г.Москва"/>
			<tiles:put name="title" value="Оплата услуг ЖКХ для г.Москва"/>
			<tiles:put name="parentName" value="RapidaPayment"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="GoodsAndServicesPayment"  flush="false">
			<tiles:put name="enabled" value="${!fn:startsWith(submenu,'GoodsAndServicesPayment/electric-payment-rapida')}"/>
			<tiles:put name="action" value="/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=electric-payment-rapida"/>
			<tiles:put name="text" value="Оплата электроэнергии"/>
			<tiles:put name="title" value="Оплата электроэнергии"/>
			<tiles:put name="parentName" value="RapidaPayment"/>
		</tiles:insert>
	</tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInsetGroup">
	<tiles:put name="text"    value="Журнал операций"/>
	<tiles:put name="name"    value="PaymentList"/>
    <tiles:put name="enabled" value="${submenu != 'PaymentList/status=all' and submenu != 'PaymentList/status=uncompleted'}"/>
	<tiles:put name="data">
		<tiles:insert definition="leftMenuInset" service="PaymentList" flush="false">
			<tiles:put name="enabled" value="${submenu != 'PaymentList/status=all'}"/>
			<tiles:put name="action" value="/private/payments/common.do?status=all"/>
			<tiles:put name="text" value="Все операции"/>
			<tiles:put name="title" value="Все операции"/>
			<tiles:put name="parentName" value="PaymentList"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="PaymentList" flush="false">
			<tiles:put name="enabled" value="${submenu != 'PaymentList/status=uncompleted'}"/>
			<tiles:put name="action" value="/private/payments/common.do?status=uncompleted"/>
			<tiles:put name="text" value="Незавершенные операции"/>
			<tiles:put name="title" value="Незавершенные операции"/>
			<tiles:put name="parentName" value="PaymentList"/>
		</tiles:insert>
	</tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInsetGroup">
	<tiles:put name="text"    value="Справочники"/>
	<tiles:put name="name"    value="Dictionaries"/>
    <tiles:put name="enabled" value="${submenu != 'PaymentReceiverList' and submenu != 'BankList' and submenu != 'ReceiverContact'}"/>
	<tiles:put name="data">
		<tiles:insert definition="leftMenuInset" service="PaymentReceiverList" flush="false">
			<tiles:put name="enabled" value="${submenu != 'PaymentReceiverList'}"/>
			<tiles:put name="action" value="/private/receivers/list?kind=PJ"/>
			<tiles:put name="text" value="Справочник получателей"/>
			<tiles:put name="title" value="Справочник получателей"/>
			<tiles:put name="parentName" value="Dictionaries"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="BankList" flush="false">
			<tiles:put name="enabled" value="${submenu != 'BankList'}"/>
			<tiles:put name="action" value="/private/dictionary/banks/national"/>
			<tiles:put name="text" value="Справочник банков"/>
			<tiles:put name="title" value="Справочник банков"/>
			<tiles:put name="parentName" value="Dictionaries"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="ContactReceiverList" flush="false">
			<tiles:put name="enabled" value="${submenu != 'ReceiverContact'}"/>
			<tiles:put name="action" value="/private/contact/receivers/list.do?kind=C"/>
			<tiles:put name="text" value="Справочник получателей Contact"/>
			<tiles:put name="parentName" value="Dictionaries"/>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
