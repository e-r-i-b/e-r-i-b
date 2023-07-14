<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<span class="infoTitle backTransparent"><bean:message key="infoTitle.title" bundle="loansBundle"/></span>
<tiles:importAttribute/>

<tiles:insert definition="leftMenuLink" service="BankLoanProducts">
	<tiles:put name="enabled" value="${submenu != 'loanProducts'}"/>
	<tiles:put name="action"  value="/private/loans/products/list"/>
	<tiles:put name="text"    value="Кредитные продукты"/>
	<tiles:put name="title"   value="Кредитные продукты"/>
</tiles:insert>

<table class="subMenu">
	<tr><td>
		<tiles:insert definition="leftMenuLink" service="LoanClaim">
			<tiles:put name="enabled" value="${submenu != 'loanClaim'}"/>
			<tiles:put name="action"  value="/private/loans/claim"/>
			<tiles:put name="text"    value="Заявка на кредит"/>
			<tiles:put name="title"   value="Заявка на кредит"/>
		</tiles:insert>
	</td></tr>
</table>

<div class='tabSubmenu' id="auto-generated-sub-menu"></div>

<tiles:insert definition="leftMenuLink" service="LoanCalculator">
	<tiles:put name="enabled" value="${submenu != 'loanCalculator'}"/>
	<tiles:put name="action"  value="/private/loans/calculator"/>
	<tiles:put name="text"    value="Кредитный калькулятор"/>
	<tiles:put name="title"   value="Кредитный калькулятор"/>
</tiles:insert>
