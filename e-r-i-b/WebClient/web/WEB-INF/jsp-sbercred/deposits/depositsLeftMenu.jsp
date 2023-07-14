<%--
  Created by IntelliJ IDEA.
  User: Zhuravleva
  Date: 19.11.2007
  Time: 19:37:29
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: Zhuravleva
  Date: 24.05.2006
  Time: 18:57:20
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<br/>
<br/>
<span class="infoTitle backTransparent">Вклады</span>
<tiles:importAttribute/>

<tiles:insert definition="leftMenuLink">
	<tiles:put name="enabled" value="${submenu != 'depositProducts'}"/>
	<tiles:put name="action"  value="/private/deposits/products/list"/>
	<tiles:put name="text"    value="Депозитные продукты"/>
	<tiles:put name="title"   value="Депозитные продукты"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink">
	<tiles:put name="enabled" value="${submenu != 'depositCalculator'}"/>
	<tiles:put name="action"  value="/private/deposits/calculator"/>
	<tiles:put name="text"    value="Калькулятор вкладов"/>
	<tiles:put name="title"   value="Калькулятор вкладов"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink">
	<tiles:put name="enabled" value="${submenu != 'DepositOpeningClaim'}"/>
	<tiles:put name="action"  value="/private/claims/claim.do?form=DepositOpeningClaim"/>
	<tiles:put name="text"    value="Заявка на открытие вклада"/>
	<tiles:put name="title"   value="Заявка на открытие вклада"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink">
	<tiles:put name="enabled" value="${submenu != 'DepositClosingClaim'}"/>
	<tiles:put name="action"  value="/private/claims/claim.do?form=DepositClosingClaim"/>
	<tiles:put name="text"    value="Заявка на закрытие вклада"/>
	<tiles:put name="title"   value="Заявка на закрытие вклада"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink">
	<tiles:put name="enabled" value="${submenu != 'DepositReplenishmentClaim'}"/>
	<tiles:put name="action"  value="/private/claims/claim.do?form=DepositReplenishmentClaim"/>
	<tiles:put name="text"    value="Пополнение вклада"/>
	<tiles:put name="title"   value="Пополнение вклада"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink">
	<tiles:put name="enabled" value="${submenu != 'InternalTransferClaim'}"/>
	<tiles:put name="action"  value="/private/claims/claim.do?form=InternalTransferClaim"/>
	<tiles:put name="text"    value="Списание средств со вклада"/>
	<tiles:put name="title"   value="Списание средств со вклада"/>
</tiles:insert>


