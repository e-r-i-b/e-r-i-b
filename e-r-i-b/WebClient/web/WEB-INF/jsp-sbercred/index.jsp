<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="paymentMain">
<tiles:put name="mainmenu" value="Payments"/>
<!-- ��������� -->
<tiles:put name="pageTitle" type="string">��������</tiles:put>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<!--������� �������� ���� ��������-->
<tiles:put name="data" type="string">
<div style="overflow-x:hidden">
<div style="width:650; height: 0%; overflow:hidden">
	<font class='listPayPart'>�������</font>
	<tiles:insert definition="paymentCards" service="ClientTemplatesManagement" flush="false">
		<tiles:put name="serviceId" value="ClientTemplatesManagement"/>
		<tiles:put name="image" value="${imagePath}/templatesPayments.gif"/>
		<tiles:put name="listPayTitle" value="������� ��������"/>
	</tiles:insert>
</div>
	<div style="width:650; height: 0%; overflow:hidden">
	<font class='listPayPart'>��������</font>
	<tiles:insert definition="paymentsPaymentCards" service="InternalPayment" flush="false">
		<tiles:put name="serviceId" value="InternalPayment"/>
		<tiles:put name="image" value="${imagePath}/InternalPayment.gif"/>
		<tiles:put name="listPayTitle" value="������� ����� �������"/>
		<tiles:put name="description" value="������� �������� ������� ����� ������ �������."/>
	</tiles:insert>
	<tiles:insert definition="paymentsPaymentCards" service="RurPayment" flush="false">
		<tiles:put name="serviceId" value="RurPayment"/>
		<tiles:put name="image" value="${imagePath}/RurPayment.gif"/>
		<tiles:put name="listPayTitle" value="������� ������ ��"/>
		<tiles:put name="description" value="������������ �������� ������� � ������ ����� �� ���� ����������� ��� ������������ ����."/>
	</tiles:insert>
	<tiles:insert definition="paymentsPaymentCards" service="CardReplenishmentPayment" flush="false">
		<tiles:put name="serviceId" value="CardReplenishmentPayment"/>
		<tiles:put name="image" value="${imagePath}/CardReplenishmentPayment.gif"/>
		<tiles:put name="listPayTitle" value="���������� ����������� �����"/>
		<tiles:put name="description" value="������� �������� ������� � ������ ����� ��� ���������� ����������� �����."/>
	</tiles:insert>
</div>
<div style="width:650; height: 0%; overflow:hidden">
	<font class='listPayPart'>������ ��������</font>
	<tiles:insert definition="paymentCards" service="PaymentReceiverList" flush="false">
		<tiles:put name="serviceId" value="PaymentList"/>
		<tiles:put name="action" value="/private/payments/common"/>
		<tiles:put name="params" value="&status=all"/>
		<tiles:put name="image" value="${imagePath}/AllPayments.gif"/>
		<tiles:put name="listPayTitle" value="��� �������"/>
	</tiles:insert>

	<tiles:insert definition="paymentCards" service="BankList" flush="false">
		<tiles:put name="serviceId" value="PaymentList"/>
		<tiles:put name="action" value="/private/payments/common"/>
		<tiles:put name="params" value="&status=uncompleted"/>
		<tiles:put name="image" value="${imagePath}/UncompletedPayments.gif"/>
		<tiles:put name="listPayTitle" value="������������� �������"/>
	</tiles:insert>
</div>
<div style="width:650; height: 0%; overflow:hidden">
	<font class='listPayPart'>�����������</font>
	<tiles:insert definition="paymentCards" service="PaymentReceiverList" flush="false">
		<tiles:put name="serviceId" value="PaymentReceiverList"/>
		<tiles:put name="action" value="/private/receivers/list"/>
		<tiles:put name="image" value="${imagePath}/HandBooksPh.gif"/>
		<tiles:put name="listPayTitle" value="���������� �����������"/>
	</tiles:insert>

	<tiles:insert definition="paymentCards" service="BankList" flush="false">
		<tiles:put name="serviceId" value="BankList"/>
		<tiles:put name="action" value="/private/dictionary/banks/national"/>
		<tiles:put name="image" value="${imagePath}/HandBooksBanks.gif"/>
		<tiles:put name="listPayTitle" value="���������� ������"/>
	</tiles:insert>
</div>
</div>
</tiles:put>
</tiles:insert>
