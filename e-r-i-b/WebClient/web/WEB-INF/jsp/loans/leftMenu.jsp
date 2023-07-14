<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>

<tiles:insert definition="leftMenuInset" service="LoanClaim">
	<tiles:put name="enabled" value="${submenu != 'loanClaim'}"/>
	<tiles:put name="action"  value="/loan/claims/create.do"/>
	<tiles:put name="text"    value="Заявка на кредит"/>
	<tiles:put name="title"   value="Заявка на кредит"/>
</tiles:insert>

<div class='tabSubmenu' id="auto-generated-sub-menu"></div>

<tiles:insert definition="leftMenuInset" service="LoanClaim">
	<tiles:put name="enabled" value="${submenu != 'LoanClaimList'}"/>
	<tiles:put name="action"  value="/loan/claim/find.do"/>
	<tiles:put name="text"    value="Результаты рассмотрения заявок"/>
	<tiles:put name="title"   value="Результаты рассмотрения заявок"/>
</tiles:insert>