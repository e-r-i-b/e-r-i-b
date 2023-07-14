<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>
<%--<span class="infoTitle backTransparent"><bean:message key="infoTitle.title" bundle="loansBundle"/></span>--%>
<%--<br/>--%>
<%--<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="180px">--%>
	<%--<tr>--%>
		<%--<td>--%>
<tiles:insert definition="leftMenuInset" service="BankLoanProducts">
	<tiles:put name="enabled" value="${submenu != 'loanProducts'}"/>
	<tiles:put name="action"  value="/private/loans/products/list"/>
	<tiles:put name="text"    value="��������� ��������"/>
	<tiles:put name="title"   value="��������� ��������"/>
</tiles:insert>

                             
<tiles:insert definition="leftMenuInsetGroup">
	<tiles:put name="enabled" value="${submenu != 'loanClaim'}"/>
	<tiles:put name="text"    value="������ �� ������"/>
	<tiles:put name="name"    value="LoanClaim"/>
	<tiles:put name="action"  value="/private/loans/claim.do?force=false"/>
	<tiles:put name="data">
		<div class='tabSubmenu' id="auto-generated-sub-menu"></div>
	</tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="LoanClaim">
	<tiles:put name="enabled" value="${submenu != 'LoanClaimList'}"/>
	<tiles:put name="action"  value="/private/claims/claims.do?name=LoanClaim"/>
	<tiles:put name="text"    value="���������� ������������ ������"/>
	<tiles:put name="title"   value="���������� ������������ ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="LoanCalculator">
	<tiles:put name="enabled" value="${submenu != 'loanCalculator'}"/>
	<tiles:put name="action"  value="/private/loans/calculator"/>
	<tiles:put name="text"    value="��������� �����������"/>
	<tiles:put name="title"   value="��������� �����������"/>
</tiles:insert>