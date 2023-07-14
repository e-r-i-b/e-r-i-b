<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:importAttribute/>
<span class="infoTitle backTransparent">������</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="180">
	<tr>
		<td>
<tiles:insert definition="leftMenuLink">
	<tiles:put name="enabled" value="${submenu != 'deposits'}"/>
	<tiles:put name="action"  value="/private/deposits"/>
	<tiles:put name="text"    value="������ �������"/>
	<tiles:put name="title"   value="������ �������"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink">
	<tiles:put name="enabled" value="${submenu != 'depositProducts'}"/>
	<tiles:put name="action"  value="/private/deposits/products/list"/>
	<tiles:put name="text"    value="���������� �������� �����"/>
	<tiles:put name="title"   value="���������� �������� �����"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="DepositOpeningClaim">
	<tiles:put name="enabled" value="${submenu != 'DepositOpeningClaim'}"/>
	<tiles:put name="action" value="/private/claims/claim.do?form=DepositOpeningClaim"/>
	<tiles:put name="text" value="������ �� �������� ������"/>
	<tiles:put name="title" value="������ � ���� ������ �� �������� �������� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="DepositClosingClaim">
	<tiles:put name="enabled" value="${submenu != 'DepositClosingClaim'}"/>
	<tiles:put name="action" value="/private/claims/claim.do?form=DepositClosingClaim"/>
	<tiles:put name="text" value="������ �� �������� ������"/>
	<tiles:put name="title" value="������ � ���� ������ �� ��������� �������� �������� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="DepositReplenishmentClaim">
	<tiles:put name="enabled" value="${submenu != 'DepositReplenishmentClaim'}"/>
	<tiles:put name="action" value="/private/claims/claim.do?form=DepositReplenishmentClaim"/>
	<tiles:put name="text" value="��������� �����"/>
	<tiles:put name="title" value="������� �������� ������� � ������ ����� ��� ���������� �������� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="InternalTransferClaim">
	<tiles:put name="enabled" value="${submenu != 'InternalTransferClaim'}"/>
	<tiles:put name="action" value="/private/claims/claim.do?form=InternalTransferClaim"/>
	<tiles:put name="text" value="������� �������� �� ������"/>
	<tiles:put name="title" value="������� �������� ������� �� ������ �� ��� ����"/>
</tiles:insert>
		</td>
	</tr>
</table>