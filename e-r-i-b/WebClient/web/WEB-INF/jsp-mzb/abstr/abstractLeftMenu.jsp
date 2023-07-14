<%--
  User: Zhuravleva
  Date: 24.05.2006
  Time: 18:57:20
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>
<span class="headLinksLeftMenu">Счета и карты</span> <br>

<tiles:insert definition="leftMenuLink" service="Abstract">
	<tiles:put name="enabled" value="${submenu != 'Abstract/list=accounts'}"/>
	<tiles:put name="action"  value="/private/accounts/abstract.do?list=accounts"/>
	<tiles:put name="text"    value="Выписка по счетам"/>
	<tiles:put name="title"   value="Получение выписки по счетам"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="Abstract">
	<tiles:put name="enabled" value="${submenu != 'Abstract/list=cards'}"/>
	<tiles:put name="action"  value="/private/accounts/abstract.do?list=cards"/>
	<tiles:put name="text"    value="Выписка по картам"/>
	<tiles:put name="title"   value="Получение выписки по картам"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="AccountOpeningClaim">
	<tiles:put name="enabled" value="${submenu != 'AccountOpeningClaim'}"/>
	<tiles:put name="action"  value="/private/claims/claim.do?form=AccountOpeningClaim"/>
	<tiles:put name="text"    value="Открытие счета"/>
	<tiles:put name="title"   value="Подача в банк заявки на открытие текущего счета."/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="AccountClosingClaim">
	<tiles:put name="enabled" value="${submenu != 'AccountClosingClaim'}"/>
	<tiles:put name="action"  value="/private/claims/claim.do?form=AccountClosingClaim"/>
	<tiles:put name="text"    value="Закрытие счета"/>
	<tiles:put name="title"   value="Подача в банк заявки на закрытие  текущего счета."/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="InternalPayment">
	<tiles:put name="enabled" value="${submenu != 'InternalPayment'}"/>
	<tiles:put name="action"  value="/private/payments/payment?form=InternalPayment"/>
	<tiles:put name="text"    value="Перевод между счетами"/>
	<tiles:put name="title"   value="Перевод денежных средств между вашими счетами."/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="CardReplenishmentPayment">
	<tiles:put name="enabled" value="${submenu != 'CardReplenishmentPayment'}"/>
	<tiles:put name="action"  value="private/payments/payment.do?form=CardReplenishmentPayment"/>
	<tiles:put name="text"    value="Пополнение пластиковой карты"/>
	<tiles:put name="title"   value="Перевод денежных средств с вашего счета для пополнения пластиковой карты."/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="PaymentList">
	<tiles:put name="enabled" value="${submenu != 'PaymentList'}"/>
	<tiles:put name="action"  value="/private/payments/common?status=all"/>
	<tiles:put name="text"    value="Журнал операций"/>
	<tiles:put name="title"   value="Журнал операций"/>
</tiles:insert>
