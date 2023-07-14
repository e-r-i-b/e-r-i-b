<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="claimMain">
  <!-- ��������� -->
  <tiles:put name="pageTitle" type="string">������</tiles:put>
  <tiles:put name="data" type="string">
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<div style="width:800; height: auto">
<font class='listPayPart'>������</font>
<tiles:insert definition="ClaimsCards" service="DepositOpeningClaim" flush="false">
	<tiles:put name="serviceId" value="DepositOpeningClaim"/>
	<tiles:put name="image" value="${imagePath}/DepositOpeningClaim.gif"/>
	<tiles:put name="listPayTitle" value="�������� �����/������"/>
	<tiles:put name="description" value="������ � ���� ������ �� �������� �������� ����� ��� �������� ������."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="DepositClosingClaim" flush="false">
	<tiles:put name="serviceId" value="DepositClosingClaim"/>
	<tiles:put name="image" value="${imagePath}/DepositClosingClaim.gif"/>
	<tiles:put name="listPayTitle" value="�������� �����/������"/>
	<tiles:put name="description" value="������ � ���� ������ �� �������� �������� ����� ��� ��������� �������� �������� ������."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="DepositReplenishmentClaim" flush="false">
	<tiles:put name="serviceId" value="DepositReplenishmentClaim"/>
	<tiles:put name="image" value="${imagePath}/DepositReplenishmentClaim.gif"/>
	<tiles:put name="listPayTitle" value="��������� �����"/>
	<tiles:put name="description" value="������� �������� ������� � ������ ����� ��� ���������� �������� ������."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="InternalTransferClaim" flush="false">
	<tiles:put name="serviceId" value="InternalTransferClaim"/>
	<tiles:put name="image" value="${imagePath}/InternalTransferClaim.gif"/>
	<tiles:put name="listPayTitle" value="������� �������� �� ������"/>
	<tiles:put name="description" value="������� �������� ������� �� ������ �� ��� ����."/>
</tiles:insert>
 </div>
  </tiles:put>
</tiles:insert>