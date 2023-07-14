<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>

<html:form action="/commissions/edit" onsubmit="return setEmptyAction(event);">
<c:set var="form" value="${EditCommissionForm}"/>

<tiles:insert definition="commissionEdit">
<tiles:put name="pageTitle"   type="string" value="Редактирование коммиссий банка"/>

<!--меню-->
<tiles:put name="menu" type="string">

	<tiles:insert definition="commandButton" flush="false">
		<tiles:put name="commandKey"     value="button.save"/>
		<tiles:put name="commandHelpKey" value="button.save"/>
		<tiles:put name="bundle"  value="commonBundle"/>
		<tiles:put name="image"   value=""/>
		<tiles:put name="isDefault" value="true"/>
		<tiles:put name="postbackNavigation" value="true"/>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>

	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.close"/>
		<tiles:put name="commandHelpKey" value="button.close.help"/>
		<tiles:put name="bundle"         value="commonBundle"/>
		<tiles:put name="image"          value=""/>
		<tiles:put name="action"         value="/commissions/list.do"/>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>

</tiles:put>

<tiles:put name="data" type="string">
	<c:set var="rules" value="${form.rules}"/>
	<c:set var="type" value="${form.type}"/>
	<c:set var="rulesClass" value="${rules[0]['class']}"/>
	<%-- TODO перересуйте --%>
	<style type="text/css">
		.tableInput {width:100%;height:100%;background-color:transparent;border-style:inset;}
	</style>

	<html:radio property="fields(rulesClass)" value="com.rssl.phizic.business.commission.GateRule">
		Из RS-Retail
	</html:radio>
	<div id="d1"/>

	<br/>
	<html:radio property="fields(rulesClass)" value="com.rssl.phizic.business.commission.FixedRule">
		Фиксированная сумма
	</html:radio>

	<sl:collection id="rule" name="rules" model="list">
		<sl:collectionItem width="30" title="Валюта" property="currencyCode"/>
		<sl:collectionItem title="Сумма">
			<html:text property="fields(${rule.currencyCode}_amount)" styleClass="tableInput"/>
		</sl:collectionItem>
	</sl:collection>

	<br/>
	<html:radio property="fields(rulesClass)" value="com.rssl.phizic.business.commission.RateRule">
		% от суммы платежа
	</html:radio>

	<sl:collection id="rule" name="rules" model="list">
		<sl:collectionItem width="30" title="Валюта" property="currencyCode"/>
		<sl:collectionItem title="%">
			<html:text property="fields(${rule.currencyCode}_rate)" styleClass="tableInput"/>
		</sl:collectionItem>
		<sl:collectionItem title="Минимальная сумма">
			<html:text property="fields(${rule.currencyCode}_minAmount)" styleClass="tableInput"/>
		</sl:collectionItem>
		<sl:collectionItem title="Максимальная сумма">
			<html:text property="fields(${rule.currencyCode}_maxAmount)" styleClass="tableInput"/>
		</sl:collectionItem>
	</sl:collection>

</tiles:put>

</tiles:insert>

</html:form>