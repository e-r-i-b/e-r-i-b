<%--
  Created by IntelliJ IDEA.
  User: Rydvanskiy
  Date: 01.05.2010
  Time: 14:57:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<c:if test="${phiz:impliesService('InternalPayment') || phiz:impliesService('ConvertCurrencyPayment')
                || phiz:impliesService('RurPayment') || phiz:impliesService('TaxPayment') || phiz:impliesService('LoanPayment') ||  phiz:impliesService('AccountOpeningClaim')}">
                <%-- ������� ����� ����� --%>
    <div class="bankServices">
        <h1>������ �����</h1>
        <div class="clear"></div>
            <br/>
        <div class="background-none">
            <tiles:insert definition="paymentsPaymentCardsDiv" service="InternalPayment" operation="CreateFormPaymentOperation"
                          flush="false">
                <tiles:put name="serviceId" value="InternalPayment"/>
                <tiles:put name="description">��������� ������ � ������ ����� �� ������ ���� ��� �����.</tiles:put>
            </tiles:insert>

            <tiles:insert definition="paymentsPaymentCardsDiv" service="ConvertCurrencyPayment" operation="CreateFormPaymentOperation"
                          flush="false">
                <tiles:put name="serviceId" value="ConvertCurrencyPayment"/>
                <tiles:put name="description">������, ������� ��� �������� ����������� ������.</tiles:put>
            </tiles:insert>

            <tiles:insert definition="paymentsPaymentCardsDiv" service="RurPayment" operation="CreateFormPaymentOperation" flush="false">
                <tiles:put name="serviceId" value="RurPayment"/>
                <tiles:put name="description">��������� ������ ����������� ��� �������� ���� �� ���� ��� �����.</tiles:put>
            </tiles:insert>

            <tiles:insert definition="paymentsPaymentCardsDiv" service="TaxPayment" operation="CreateFormPaymentOperation" flush="false">
                <tiles:put name="serviceId" value="TaxPayment"/>
                <tiles:put name="description">�������� ������������� �� ��������� �����������.</tiles:put>
            </tiles:insert>

            <tiles:insert definition="paymentsPaymentCardsDiv" service="LoanPayment" operation="CreateFormPaymentOperation" flush="false">
                <tiles:put name="serviceId" value="LoanPayment"/>
                <tiles:put name="description">�������� ������������� �� ��������.</tiles:put>
            </tiles:insert>

            <tiles:insert definition="paymentsPaymentCardsDiv" service="AccountClosingPayment" operation="CreateFormPaymentOperation" flush="false">
                <tiles:put name="serviceId" value="AccountClosingPayment"/>
                <tiles:put name="description">������� �����.</tiles:put>
            </tiles:insert>

            <div class="clear"></div>
        </div>
    </div>
</c:if>