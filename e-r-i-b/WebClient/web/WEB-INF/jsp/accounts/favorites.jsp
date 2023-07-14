<%--
  User: IIvanova
  Date: 12.04.2006
  Time: 17:49:03
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<tiles:insert definition="leftMenuLink" service="Abstract">
	<tiles:put name="enabled" value="${submenu != 'Abstract'}"/>
	<tiles:put name="action"  value="/private/accounts/abstract"/>
	<tiles:put name="text"    value="Выписка по счетам"/>
	<tiles:put name="title"   value="Получение выписки по вкладам"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="MobilePayment">
	<tiles:put name="enabled" value="${submenu != 'MobilePayment'}"/>
	<tiles:put name="action"  value="/private/payments/payment?form=MobilePayment"/>
	<tiles:put name="text"    value="Оплата мобильной связи"/>
	<tiles:put name="title"   value="Оплата мобильной связи"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="InternetPayment">
	<tiles:put name="enabled" value="${submenu != 'InternetPayment'}"/>
	<tiles:put name="action"  value="/private/payments/payment?form=InternetPayment"/>
	<tiles:put name="text"    value="Оплата Интернет"/>
	<tiles:put name="title"   value="Оплата Интернет"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="GKHPayment">
	<tiles:put name="enabled" value="${submenu != 'GKHPayment'}"/>
	<tiles:put name="action"  value="/private/payments/payment?form=GKHPayment"/>
	<tiles:put name="text"    value="Оплата услуг ЖКХ"/>
	<tiles:put name="title"   value="Оплата услуг ЖКХ"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="InternalPayment">
	<tiles:put name="enabled" value="${submenu != 'InternalPayment'}"/>
	<tiles:put name="action"  value="/private/payments/payment?form=InternalPayment"/>
	<tiles:put name="text"    value="Перевод между счетами"/>
	<tiles:put name="title"   value="Перевод между счетами"/>
</tiles:insert>