<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>


<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<script type="text/javascript">
   document.imgPath="${imagePath}/";
</script>

<c:if test="${phiz:impliesService('InternalPayment') or phiz:impliesService('RurPayment') or phiz:impliesService('RurPayJurSB')}">
<span class="infoTitle backTransparent">�������</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="80%">
<tr>
    <td>
        <phiz:menuLink action="/private/payments/payment" param="form=InternalPayment" serviceId="InternalPayment" id="f1">
        ������� ����� �������<br>
        </phiz:menuLink>
    </td>
</tr>
<tr>
    <td>
        <phiz:menuLink action="/private/payments/payment" param="form=RurPayment" serviceId="RurPayment"  id="f2">
        ������� ����������� ����<br>
        </phiz:menuLink>

    </td>
</tr>
</table>
</c:if>
<c:if test="${phiz:impliesService('PurchaseCurrencyPayment') or phiz:impliesService('SaleCurrencyPayment') or phiz:impliesService('ConvertCurrencyPayment')}">
<span class="infoTitle backTransparent">����� ������</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="80%">
<tr>
    <td>
        <phiz:menuLink action="/private/payments/payment" param="form=PurchaseCurrencyPayment" serviceId="PurchaseCurrencyPayment" id="f4">
        ������� ����������� ������<br>
        </phiz:menuLink>
    </td>
</tr>
<tr>
    <td>
        <phiz:menuLink action="/private/payments/payment" param="form=SaleCurrencyPayment" serviceId="SaleCurrencyPayment"id="f5">
        ������� ����������� ������<br>
        </phiz:menuLink>
    </td>
</tr>
<tr>
    <td>
        <phiz:menuLink action="/private/payments/payment" param="form=ConvertCurrencyPayment" serviceId="ConvertCurrencyPayment" id="f6">
        ��������� ����������� ������<br>
        </phiz:menuLink>
    </td>
</tr>
</table>
<br>
</c:if>
<c:if test="${phiz:impliesService('PaymentDocumentPreparation')}">
<span class="infoTitle backTransparent">��������� ���������</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="80%">
<tr>
    <td>
        <phiz:menuLink action="/private/PD4" param="form=ConvertCurrencyPayment" serviceId="PaymentDocumentPreparation" id="f7">
        ����� ��-4 � ��-4�� (�����)<br>
        </phiz:menuLink>

    </td>
</tr>
<tr>
    <td>
        <phiz:menuLink action="/private/PD4" param="page=pay&form=ConvertCurrencyPayment" serviceId="PaymentDocumentPreparation" id="f8">
        ��������� ���������<br>
        </phiz:menuLink>

    </td>
</tr>
<tr>
    <td>
        <phiz:menuLink action="/private/PD4" param="page=letter&form=ConvertCurrencyPayment" serviceId="PaymentDocumentPreparation" id="f9">
        ���������� ���������<br>
        </phiz:menuLink>
    </td>
</tr>
</table>
<br>
</c:if>
<c:if test="${phiz:impliesService('LossPassbookApplication') or phiz:impliesService('AccountOpeningClaim') or phiz:impliesService('AccountClosingClaim') or phiz:impliesService('BlockingCardClaim')}">
<span class="infoTitle backTransparent">������</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="80%">
<tr>
    <td>
        <phiz:menuLink action="/private/payments/payment" param="form=LossPassbookApplication"
                       serviceId="LossPassbookApplication" id="f10">
        ������ �� ������ �������������� ������<br>
        </phiz:menuLink>
    </td>
</tr>
<tr>
    <td>
        <phiz:menuLink action="/private/payments/payment" param="form=AccountOpeningClaim"
                       serviceId="AccountOpeningClaim" id="f11">
        ������ �� �������� �����<br>
        </phiz:menuLink>
    </td>
</tr>
<tr>
	<td>
		<phiz:menuLink action="/private/payments/payment" param="form=AccountClosingClaim"
					   serviceId="AccountClosingClaim" id="f12">
		������ �� �������� �����<br>
		</phiz:menuLink>
	</td>
</tr>
<tr>
	<td>
		<phiz:menuLink action="/private/payments/payment" param="form=BlockingCardClaim"
					   serviceId="BlockingCardClaim" id="f13">
		������ �� ���������� �����<br>
		</phiz:menuLink>
	</td>
</tr>
</table>
<br>
</c:if>
<c:if test="${phiz:impliesService('PaymentList')}">
<span class="infoTitle backTransparent">������ ��������</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="80%">
<tr>
    <td>
        <phiz:menuLink action="/private/payments/common" param="status=all"
                       serviceId="PaymentList" id="f14">
        ��� �������
        </phiz:menuLink>
    </td>
</tr>
<tr>
    <td>
        <phiz:menuLink action="/private/payments/common" param="status=uncompleted"
                       serviceId="PaymentList" id="f15">
        ������������� �������
        </phiz:menuLink>
    </td>
</tr>
</table>
<br>
</c:if>
<c:if test="${phiz:impliesService('PaymentReceiver')}">
<span class="infoTitle backTransparent">���������� �����������</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="80%">
<tr>
    <td>
        <phiz:menuLink action="/private/receivers/list" param="form=PaymentReceiver"
                       serviceId="PaymentReceiver" id="f16">
        ���������� �����������
        </phiz:menuLink>
    </td>
</tr>
</table>
</c:if>
