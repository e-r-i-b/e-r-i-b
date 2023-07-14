<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<script type="text/javascript">
   document.imgPath="${imagePath}/";
</script>

<span class="infoTitle backTransparent">Платежи</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="80%">
<tr>
    <td>
        <phiz:menuLink action="/private/payments/payment" param="form=InternalPayment" serviceId="InternalPayment" id="f1">
        Перевод между счетами<br>
        </phiz:menuLink>
    </td>
</tr>
<tr>
    <td>
        <phiz:menuLink action="/private/payments/payment" param="form=RurPayment" serviceId="RurPayment"  id="f2">
        Перевод физическому лицу<br>
        </phiz:menuLink>

    </td>
</tr>
<tr>
    <td>
        <phiz:menuLink action="/private/payments/payment" param="form=PurchaseCurrencyPayment" serviceId="PurchaseCurrencyPayment" id="f4">
        Покупка иностранной валюты<br>
        </phiz:menuLink>
    </td>
</tr>
<tr>
    <td>
        <phiz:menuLink action="/private/payments/payment" param="form=SaleCurrencyPayment" serviceId="SaleCurrencyPayment"id="f5">
        Продажа иностранной валюты<br>
        </phiz:menuLink>
    </td>
</tr>
<tr>
    <td>
        <phiz:menuLink action="/private/payments/payment" param="form=ConvertCurrencyPayment" serviceId="ConvertCurrencyPayment" id="f6">
        Конверсия иностранной валюты<br>
        </phiz:menuLink>
    </td>
</tr>
</table>
<br>
<span class="infoTitle backTransparent">Платежные документы</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="80%">
<tr>
    <td>
        <phiz:menuLink action="/private/PD4" param="form=ConvertCurrencyPayment" serviceId="PaymentDocumentPreparation" id="f7">
        Форма ПД-4 и ПД-4сб (налог)<br>
        </phiz:menuLink>

    </td>
</tr>
<tr>
    <td>
        <phiz:menuLink action="/private/PD4" param="page=pay&form=ConvertCurrencyPayment" serviceId="PaymentDocumentPreparation" id="f8">
        Платежное поручение<br>
        </phiz:menuLink>

    </td>
</tr>
<tr>
    <td>
        <phiz:menuLink action="/private/PD4" param="page=letter&form=ConvertCurrencyPayment" serviceId="PaymentDocumentPreparation" id="f9">
        Инкассовое поручение<br>
        </phiz:menuLink>
    </td>
</tr>
</table>
<br>
<span class="infoTitle backTransparent">Заявки</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="80%">
<tr>
    <td>
        <phiz:menuLink action="/private/payments/payment" param="form=LossPassbookApplication"
                       serviceId="LossPassbookApplication" id="f10">
        Заявка об утрате сберегательной книжки<br>
        </phiz:menuLink>
    </td>
</tr>
</table>
<br>
<span class="infoTitle backTransparent">Журнал операций</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="80%">
<tr>
    <td>
        <phiz:menuLink action="/private/payments/common" param="status=all"
                       serviceId="PaymentList" id="f11">
        Все платежи
        </phiz:menuLink>
    </td>
</tr>
<tr>
    <td>
        <phiz:menuLink action="/private/payments/common" param="status=uncompleted"
                       serviceId="PaymentList" id="f12">
        Незавершенные платежи
        </phiz:menuLink>
    </td>
</tr>
</table>
