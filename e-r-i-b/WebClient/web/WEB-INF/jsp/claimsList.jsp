<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="claimList">
  <!-- ��������� -->
  <tiles:put name="pageTitle" type="string">������</tiles:put>
  <tiles:put name="data" type="string">

<div id="workspace" style="position:absolute; width:100%">
<div class="pmntListGroup">
<div class='pmntListGroupTitle'>������</div>
<tiles:insert definition="ClaimsCards" service="DepositOpeningClaim" flush="false">
	<tiles:put name="serviceId" value="DepositOpeningClaim"/>
	<tiles:put name="listPayTitle" value="�������� �����/������"/>
	<tiles:put name="description" value="������ � ���� ������ �� �������� ����� ��� �������� ������."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="DepositClosingClaim" flush="false">
	<tiles:put name="serviceId" value="DepositClosingClaim"/>
	<tiles:put name="listPayTitle" value="�������� �����/������"/>
	<tiles:put name="description" value="������ � ���� ������ �� �������� ����� ��� ��������� �������� �������� ������."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="BankcellLeasingClaim" flush="false">
	<tiles:put name="serviceId" value="BankcellLeasingClaim"/>
	<tiles:put name="listPayTitle" value="������ �������� ������"/>
	<tiles:put name="description" value="������ � ���� ������ �� ������������ �������� ������ � ����� �� ������ �����."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="DepositReplenishmentClaim" flush="false">
	<tiles:put name="serviceId" value="DepositReplenishmentClaim"/>
	<tiles:put name="listPayTitle" value="��������� �����"/>
	<tiles:put name="description" value="������� �������� ������� � ������ ����� ��� ���������� �������� ������."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="SecuritiesOperationsClaim" flush="false">
	<tiles:put name="serviceId" value="SecuritiesOperationsClaim"/>
	<tiles:put name="listPayTitle" value="�������� � ������� ��������"/>
	<tiles:put name="description" value="������ � ���� ������ �� ���������� �������� � ������� �������� � ����� �� ������ �����."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="InternalTransferClaim" flush="false">
	<tiles:put name="serviceId" value="InternalTransferClaim"/>	
	<tiles:put name="listPayTitle" value="������� �������� �� ������"/>
	<tiles:put name="description" value="������� �������� ������� �� ������ �� ��� ����."/>
</tiles:insert>
</div>
    <div style="clear:both;"></div>
<div class="pmntListGroup">
<div class='pmntListGroupTitle'>����������� �����</div>

<tiles:insert definition="ClaimsCards" service="UnblockingCardClaim" flush="false">
	<tiles:put name="serviceId" value="UnblockingCardClaim"/>
	<tiles:put name="listPayTitle" value="������������� �����"/>
	<tiles:put name="description" value="������ � ���� ������ �� ������������� ����������� �����."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="SMSInformationClaim" flush="false">
	<tiles:put name="serviceId" value="SMSInformationClaim"/>
	<tiles:put name="listPayTitle" value="SMS - ��������������"/>
	<tiles:put name="description" value="������ � ���� ������ �� SMS-�������������� �� ��������� � ����������� �����."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="NotReIssueCardClaim" flush="false">
	<tiles:put name="serviceId" value="NotReIssueCardClaim"/>
	<tiles:put name="listPayTitle" value="����� �� ����������� �����"/>
	<tiles:put name="description" value="������ � ���� ������ �� ������ �� ����������� ����������� �����."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="ReIssueCardClaim" flush="false">
	<tiles:put name="serviceId" value="ReIssueCardClaim"/>
	<tiles:put name="listPayTitle" value="���������� �����"/>
	<tiles:put name="description" value="������ � ���� ������ �� ���������� ����������� �����."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="CardChargeLimitClaim" flush="false">
	<tiles:put name="serviceId" value="CardChargeLimitClaim"/>
	<tiles:put name="listPayTitle" value="��������� ������ ��� ���.����"/>
	<tiles:put name="description" value="������ �� ��������� ������ ������������ ������� ��� �������������� ���� �������."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="IssueCardClaim" flush="false">
	<tiles:put name="serviceId" value="IssueCardClaim"/>
	<tiles:put name="listPayTitle" value="������ ����������� �����"/>
	<tiles:put name="description" value="������ � ���� ������ �� ������ ����������� �����."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="BlockingCardClaim" flush="false">
	<tiles:put name="serviceId" value="BlockingCardClaim"/>
	<tiles:put name="listPayTitle" value="���������� �����"/>
	<tiles:put name="description" value="������ � ���� ������ �� ���������� ����������� �����."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="IssueAdditionalCardClaim" flush="false">
	<tiles:put name="serviceId" value="IssueAdditionalCardClaim"/>
	<tiles:put name="listPayTitle" value="������������ �������������� �����"/>
	<tiles:put name="description" value="������ � ���� ������ �� ������������ �������������� ����������� �����."/>
</tiles:insert>


<tiles:insert definition="ClaimsCards" service="StopListCardClaim" flush="false">
	 <tiles:put name="serviceId" value="StopListCardClaim"/>
	 <tiles:put name="listPayTitle" value="���������� ����� � ����-����"/>
	 <tiles:put name="description" value="��������� �� ���������� ����� � ����-����."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="CardMootTransClaim" flush="false">
	 <tiles:put name="serviceId" value="CardMootTransClaim"/>	 
	 <tiles:put name="listPayTitle" value="������������� ������� ����������"/>
	 <tiles:put name="description" value="��������� �� ������������� ������� ���������� �� ����������� �����."/>
</tiles:insert>

 </div>
</div>
<script type="text/javascript">
    changeDivSize("workspace");
</script>

</tiles:put>
</tiles:insert>