<%--
  Created by IntelliJ IDEA.
  User: Egorova
  Date: 08.09.2008
  Time: 15:56:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute name="mainmenu" ignore="true"/>
<c:set var="mode" scope="request" value="${mainmenu}"/>

<tiles:insert definition="mainMenuInset" module="Info">
	<tiles:put name="activity" value="${mode == 'Info'}"/>
	<tiles:put name="action" value="/private/accounts"/>
	<tiles:put name="text" value="Мой банк"/>
</tiles:insert>

<tiles:insert definition="mainMenuInset" module="Payments">
	<tiles:put name="activity" value="${mode == 'Payments'}"/>
	<tiles:put name="action" value="private/payments"/>
	<tiles:put name="text" value="Платежи"/>
</tiles:insert>

<tiles:insert definition="mainMenuInset" module="Deposits">
	<tiles:put name="activity" value="${mode == 'Deposits'}"/>
	<tiles:put name="action" value="/private/deposits"/>
	<tiles:put name="text" value="Вклады"/>
</tiles:insert>

<tiles:insert definition="mainMenuInset" module="Loans">
	<tiles:put name="activity" value="${mode == 'Loans'}"/>
	<tiles:put name="action" value="/private/loans/products/list"/>
	<tiles:put name="text" value="Кредиты"/>
</tiles:insert>

<tiles:insert definition="mainMenuInset" module="ClientClaims">
	<tiles:put name="activity" value="${mode == 'ClientClaims'}"/>
	<tiles:put name="action" value="/private/claims"/>
	<tiles:put name="text" value="Заявки"/>
</tiles:insert>

<tiles:insert definition="mainMenuInset" service="ClientMailManagement">
	<tiles:put name="activity" value="${mode == 'ClientMailManagement'}"/>
	<tiles:put name="action" value="/private/mail/list"/>
	<tiles:put name="text" value="Почта"/>
</tiles:insert>

<tiles:insert definition="mainMenuInset">
	<tiles:put name="activity" value="${mode == 'ServicesClient'}"/>
	<tiles:put name="action" value="/private/about"/>
	<tiles:put name="text" value="Сервис"/>
</tiles:insert>