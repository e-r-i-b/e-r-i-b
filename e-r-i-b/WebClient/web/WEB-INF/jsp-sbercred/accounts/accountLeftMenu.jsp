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
<span class="infoTitle backTransparent">����� � �����</span> <br>
<tiles:insert definition="leftMenuLink" service="Abstract">
	<tiles:put name="enabled" value="${submenu != 'Abstract'}"/>
	<tiles:put name="action"  value="/private/accounts/abstract.do?list=accounts"/>
	<tiles:put name="text"    value="������� �� ������"/>
	<tiles:put name="title"   value="��������� ������� �� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="Abstract">
	<tiles:put name="enabled" value="${submenu != 'Abstract'}"/>
	<tiles:put name="action"  value="/private/accounts/abstract.do?list=cards"/>
	<tiles:put name="text"    value="������� �� ������"/>
	<tiles:put name="title"   value="��������� ������� �� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="Abstract">
	<tiles:put name="enabled" value="${submenu != 'Abstract'}"/>
	<tiles:put name="action"  value="/private/accounts/abstract.do?list=deposits"/>
	<tiles:put name="text"    value="������� �� �������"/>
	<tiles:put name="title"   value="��������� ������� �� �������"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="DepositOpeningClaim">
	<tiles:put name="enabled" value="${submenu != 'DepositOpeningClaim'}"/>
	<tiles:put name="action"  value="/private/claims/claim.do?form=DepositOpeningClaim"/>
	<tiles:put name="text"    value="�������� �����"/>
	<tiles:put name="title"   value="������ � ���� ������ �� �������� �������� �����.
"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="DepositClosingClaim">
	<tiles:put name="enabled" value="${submenu != 'DepositClosingClaim'}"/>
	<tiles:put name="action"  value="/private/claims/claim.do?form=DepositClosingClaim"/>
	<tiles:put name="text"    value="�������� �����"/>
	<tiles:put name="title"   value="������ � ���� ������ �� ��������  �������� �����."/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="InternalPayment">
	<tiles:put name="enabled" value="${submenu != 'InternalPayment'}"/>
	<tiles:put name="action"  value="/private/payments/payment?form=InternalPayment"/>
	<tiles:put name="text"    value="������� ����� �������"/>
	<tiles:put name="title"   value="������� �������� ������� ����� ������ �������."/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="CardReplenishmentPayment">
	<tiles:put name="enabled" value="${submenu != 'CardReplenishmentPayment'}"/>
	<tiles:put name="action"  value="/private/payments/payment.do?form=CardReplenishmentPayment"/>
	<tiles:put name="text"    value="���������� ����������� �����"/>
	<tiles:put name="title"   value="������� �������� ������� � ������ ����� ��� ���������� ����������� �����."/>
</tiles:insert>