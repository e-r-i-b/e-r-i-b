<%@ page import="com.rssl.phizic.gate.GateSingleton" %>
<%@ page import="com.rssl.phizic.gate.currency.CurrencyRateService" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:importAttribute/>

<tiles:insert definition="leftMenuInset" service="InternalPayment">
   <tiles:put name="enabled" value="${submenu != 'InternalPayment'}"/>
   <tiles:put name="action" value="/private/payments/payment.do?form=InternalPayment"/>
   <tiles:put name="text" value="������� ����� �������"/>
   <tiles:put name="title" value="������� �������� ������� ����� ������ �������."/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="ConvertCurrencyPayment">
   <tiles:put name="enabled" value="${submenu != 'ConvertCurrencyPayment'}"/>
   <tiles:put name="action" value="/private/payments/payment.do?form=ConvertCurrencyPayment"/>
   <tiles:put name="text" value="�������� ������"/>
   <tiles:put name="title" value="�������� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="RurPayment">
   <tiles:put name="enabled" value="${submenu != 'RurPayment'}"/>
   <tiles:put name="action" value="/private/payments/payment.do?form=RurPayment"/>
   <tiles:put name="text" value="��������� ������"/>
   <tiles:put name="title" value="��������� ������"/>
</tiles:insert>
<c:if test="${phiz:useOwnAuthentication()}">
<tiles:insert definition="leftMenuInset" service="CardReplenishmentPayment">
   <tiles:put name="enabled" value="${submenu != 'CardReplenishmentPayment'}"/>
   <tiles:put name="action" value="/private/payments/payment.do?form=CardReplenishmentPayment"/>
   <tiles:put name="text" value="��������� �����"/>
   <tiles:put name="title" value="��������� �����"/>
</tiles:insert>
</c:if>

<tiles:insert definition="leftMenuInset" service="InternetOrderPayments">
   <tiles:put name="enabled" value="${submenu != 'InternetOrderPayments'}"/>
   <tiles:put name="action" value="/private/payments/internetShops/orderList.do"/>
   <tiles:put name="text" value="��� ��������-������"/>
   <tiles:put name="title" value="��� ��������-������"/> 
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="Abstract">
   <tiles:put name="enabled" value="${submenu != 'Abstract' || ShowAccountAbstractForm.copying}"/>
   <tiles:put name="action"  value="/private/accounts/abstract.do?list=accounts&copying=false"/>
   <tiles:put name="text"    value="������� �� ������"/>
   <tiles:put name="title"   value="��������� ������� �� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="Abstract">
   <tiles:put name="enabled" value="${submenu != 'Abstract' || !ShowAccountAbstractForm.copying}"/>
   <tiles:put name="action"  value="/private/accounts/abstract.do?list=accounts&copying=true"/>
   <tiles:put name="text"    value="������� � ���������"/>
   <tiles:put name="title"   value="��������� ������� � ���������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="PaymentList">
   <tiles:put name="enabled" value="${submenu != 'PaymentList'}"/>
   <tiles:put name="action" value="/private/payments/common.do?status=all"/>
   <tiles:put name="text" value="������� ��������"/>
   <tiles:put name="title" value="������� ��������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="PaymentReceiverList">
   <tiles:put name="enabled" value="${submenu != 'PaymentReceiverList'}"/>
    <c:choose>
        <c:when test="${phiz:impliesService('BillingServiceProvidersManagment')}">
            <tiles:put name="action" value="/private/receivers/list?kind=PJB"/>
        </c:when>
        <c:otherwise>
            <tiles:put name="action" value="/private/receivers/list?kind=PJ"/>
        </c:otherwise>
    </c:choose>
   <tiles:put name="text" value="������� ��������"/>
   <tiles:put name="title" value="������� ��������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="LossPassbookApplication">
   <tiles:put name="enabled" value="${submenu != 'LossPassbookApplication'}"/>
   <tiles:put name="action" value="/private/payments/payment.do?form=LossPassbookApplication"/>
   <tiles:put name="text" value="������� �� ����� ����������"/>
   <tiles:put name="title" value="������� �� ����� ����������"/>
</tiles:insert>


<tiles:insert definition="leftMenuInsetGroup" service="PaymentDocumentPreparation">
   <tiles:put name="text"    value="������ �������"/>
   <tiles:put name="title"   value="������ �������"/>
   <tiles:put name="name"    value="lmPrintForm"/> 
   <tiles:put name="enabled" value="${submenu != 'PrintForm'}"/>
   <tiles:put name="action"  value="/private/printForm"/>
   <tiles:put name="data">
        <tiles:insert definition="leftMenuInset" service="PaymentDocumentPreparation" flush="false">
           <tiles:put name="enabled" value="${submenu != 'PrintForm/pd'}"/>
           <tiles:put name="action" value="/private/PD4"/>
           <tiles:put name="text" value="������ ����� ��-4"/>
           <tiles:put name="title" value="������ ����� ��-4"/>
           <tiles:put name="parentName" value="PrintForm"/>
        </tiles:insert>

          <tiles:insert definition="leftMenuInset" service="PaymentDocumentPreparation" flush="false">
           <tiles:put name="enabled" value="${submenu != 'PrintForm/nalog'}"/>
           <tiles:put name="action" value="/private/PD4.do?page=nalog"/>
           <tiles:put name="text" value="������ ����� ��-4�� (�����)"/>
           <tiles:put name="title" value="������ ����� ��-4�� (�����)"/>
           <tiles:put name="parentName" value="PrintForm"/>
        </tiles:insert>

          <tiles:insert definition="leftMenuInset" service="PaymentDocumentPreparation" flush="false">
           <tiles:put name="enabled" value="${submenu != 'PrintForm/pay'}"/>
           <tiles:put name="action" value="/private/PD4.do?page=pay"/>
           <tiles:put name="text" value="������ ���������� ���������"/>
           <tiles:put name="title" value="������ ���������� ���������"/>
            <tiles:put name="parentName" value="PrintForm"/>
        </tiles:insert>

          <tiles:insert definition="leftMenuInset" service="PaymentDocumentPreparation" flush="false">
           <tiles:put name="enabled" value="${submenu != 'PrintForm/letter'}"/>
           <tiles:put name="action" value="/private/PD4.do?page=letter"/>
           <tiles:put name="text" value="������ ����������� ���������"/>
           <tiles:put name="title" value="������ ����������� ���������"/>
            <tiles:put name="parentName" value="PrintForm"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ShowCurrenciesRateOperation">
   <tiles:put name="enabled" value="${submenu != 'CurrenciesRate'}"/>
   <tiles:put name="action" value="/private/rate"/>
   <tiles:put name="text" value="����� �����"/>
   <tiles:put name="title" value="����� �����"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="ClientMailManagment">
   <tiles:put name="enabled" value="${submenu != 'ClientMailManagment'}"/>
   <tiles:put name="action" value="/private/mail/list"/>
   <tiles:put name="text" value="������ ������"/>
   <tiles:put name="title" value="��������� � ������ ������ �����."/>
</tiles:insert>

<%--
<%@ include file="currentDate.jsp" %>
<c:if test="${phiz:impliesOperation('ShowCurrenciesRateOperation','CurrenciesRateInfo')}">
<span class="infoTitle">����� �����</span>
<br/>
<br/>
            <table class="UserTab backTransparent" cellpadding="0" cellspacing="0">
            <tr style="padding-top:8px;" class="titleTable">
               <td>������</td>
               <td>�������</td>
               <td class="titleTable">�������</td>
            </tr>
            <tr>
               <td class="ListItem">USD</td>
               <td class="ListItem" align="center" >
                  <c:set var="rate" value="${phiz:getFormattedCurrencyRate(phiz:getRate('RUB','USD', 'BUY'), 2)}"/>
                  <c:choose>
                     <c:when test="${rate != ''}">${rate}</c:when>
                     <c:otherwise> &mdash; </c:otherwise>
                  </c:choose>
               </td>
               <td class="ListItem" align="center">
                   <c:set var="rate" value="${phiz:getFormattedCurrencyRate(phiz:getRate('USD','RUB', 'SALE'), 2)}"/>
                  <c:choose>
                     <c:when test="${rate != ''}">${rate}</c:when>
                     <c:otherwise> &mdash; </c:otherwise>
                  </c:choose>
               </td>
            </tr>
            <tr>
               <td class="ListItem">EUR</td>
               <td class="ListItem" align="center">
                  <c:set var="rate" value="${phiz:getFormattedCurrencyRate(phiz:getRate('RUB','EUR', 'BUY'), 2)}"/>
                  <c:choose>
                     <c:when test="${rate != ''}">${rate}</c:when>
                     <c:otherwise> &mdash; </c:otherwise>
                  </c:choose>
               </td>
               <td class="ListItem" align="center">
                   <c:set var="rate" value="${phiz:getFormattedCurrencyRate(phiz:getRate('EUR','RUB', 'SALE'), 2)}"/>
                  <c:choose>
                     <c:when test="${rate != ''}">${rate}</c:when>
                     <c:otherwise> &mdash; </c:otherwise>
                  </c:choose>
               </td>
            </tr>
</table>
</c:if>
<%@ include file="favorites.jsp" %>  --%>