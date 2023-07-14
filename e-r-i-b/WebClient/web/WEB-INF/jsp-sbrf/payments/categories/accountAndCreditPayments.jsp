<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%-- TODO: Проверка на УДБО в таком виде до исправления BUG026000 --%>
<c:set var="person" value="${phiz:getPersonInfo()}"/>


<tiles:insert definition="categoryTemplate" flush="false" service="AccountOpeningClaim"
              operation="CreateFormPaymentOperation">
    <tiles:put name="title">Открытие вклада</tiles:put>
    <tiles:put name="hint">
        Открыть новый вклад и перевести на него деньги.
    </tiles:put>
    <tiles:put name="imagePath" value="${imagePathGlobal}/iconPmntList_AccountOpeningClaim.jpg"/>
    <tiles:put name="url"><c:url value="/private/deposits/products/list.do">
        <phiz:param name="category" value="${categoryId}"/>
        <phiz:param name="form" value="AccountOpeningClaim"/>
    </c:url></tiles:put>
    <tiles:put name="serviceId" value="AccountOpeningClaim"/>
</tiles:insert>

<tiles:insert definition="categoryTemplate" flush="false" service="AccountClosingPayment"
              operation="CreateFormPaymentOperation">
    <tiles:put name="title">Закрытие вклада</tiles:put>
    <tiles:put name="hint">
        Закрыть вклад и перевести остаток на другой счёт или карту.
    </tiles:put>
    <tiles:put name="imagePath" value="${imagePathGlobal}/iconPmntList_AccountClosingPayment.jpg"/>
    <tiles:put name="url">
        <c:url value="/private/payments/payment.do">
            <phiz:param name="category" value="${categoryId}"/>
            <phiz:param name="form" value="AccountClosingPayment"/>
        </c:url>
    </tiles:put>
    <tiles:put name="serviceId" value="AccountClosingPayment"/>
</tiles:insert>

<tiles:insert definition="categoryTemplate" flush="false" service="AccountOpeningClaimWithClose"
              operation="CreateFormPaymentOperation">
    <tiles:put name="title">Открытие вклада (закрыть счет списания)</tiles:put>
    <tiles:put name="hint">
        Закрыть вклад и перевести остаток.
    </tiles:put>
    <tiles:put name="imagePath" value="${imagePathGlobal}/iconPmntList_AccountOpeningClaim.jpg"/>
    <tiles:put name="url">
        <c:url value="/private/deposits/products/list.do">
            <phiz:param name="category" value="${categoryId}"/>
            <phiz:param name="form" value="AccountOpeningClaimWithClose"/>
        </c:url>
    </tiles:put>
    <tiles:put name="serviceId" value="AccountOpeningClaimWithClose"/>
</tiles:insert>

<tiles:insert definition="categoryTemplate" flush="false" service="LoanPayment" operation="CreateFormPaymentOperation">
    <tiles:put name="title">Погашение кредита</tiles:put>
    <tiles:put name="hint">
        Погасить задолженность по кредиту.
    </tiles:put>
    <tiles:put name="imagePath" value="${imagePathGlobal}/iconPmntList_LoanPayment.jpg"/>
    <tiles:put name="url"><c:url value="/private/payments/payment.do">
        <phiz:param name="category" value="${categoryId}"/>
        <phiz:param name="form" value="LoanPayment"/>
    </c:url></tiles:put>
    <tiles:put name="serviceId" value="LoanPayment"/>
</tiles:insert>


<c:if test="${phiz:isPublishedCreditProductsExists()}">
    <tiles:insert definition="categoryTemplate" flush="false" service="LoanProduct"
                  operation="LoanProductListOperation">
        <tiles:put name="title"><bean:message key="category.operations.LOAN_OFFER" bundle="paymentServicesBundle"/></tiles:put>
        <tiles:put name="hint">
            <bean:message key="category.operations.LOAN_OFFER" bundle="paymentServicesBundle"/>
        </tiles:put>
        <tiles:put name="imagePath" value="${imagePathGlobal}/credit_64.jpg"/>
        <tiles:put name="url"><c:url value="/private/payments/loan_product.do"/></tiles:put>
        <tiles:put name="serviceId" value="LoanProduct"/>
    </tiles:insert>
</c:if>

<c:if test="${phiz:isPublishedCreditCardProductsExists()}">
    <tiles:insert definition="categoryTemplate" flush="false" service="LoanCardProduct" operation="LoanOfferListOperation">
        <tiles:put name="title"><bean:message key="category.operations.LOAN_CARD_OFFER" bundle="paymentServicesBundle"/></tiles:put>
        <tiles:put name="hint">
            <bean:message key="category.operations.LOAN_CARD_OFFER" bundle="paymentServicesBundle"/>
        </tiles:put>
        <tiles:put name="imagePath" value="${imagePathGlobal}/card_64.jpg"/>
        <tiles:put name="url"><c:url value="/private/payments/income_level.do"/></tiles:put>
        <tiles:put name="serviceId" value="LoanCardProduct"/>
    </tiles:insert>
</c:if>

<c:if test="${phiz:activeCardProductExists('VIRTUAL')}">
    <tiles:insert definition="categoryTemplate" flush="false" service="VirtualCardClaim" operation="CreateFormPaymentOperation">
        <tiles:put name="title"><bean:message key="category.operations.VIRTUAL_CARD_OFFER" bundle="paymentServicesBundle"/></tiles:put>
        <tiles:put name="hint">
            <bean:message key="category.operations.VIRTUAL_CARD_OFFER" bundle="paymentServicesBundle"/>
        </tiles:put>
        <tiles:put name="imagePath" value="${imagePathGlobal}/card_64.jpg"/>
        <tiles:put name="url"><c:url value="/private/payments/payment.do">
            <phiz:param name="form" value="VirtualCardClaim"/>
        </c:url></tiles:put>
        <tiles:put name="serviceId" value="VirtualCardClaim"/>
    </tiles:insert>
</c:if>

<tiles:insert definition="categoryTemplate" flush="false" service="IMAOpeningClaim"
              operation="CreateFormPaymentOperation">
    <tiles:put name="title">Открытие ОМС</tiles:put>
    <tiles:put name="hint">
        Открыть новый обезличенный металлический счет.
    </tiles:put>
    <tiles:put name="imagePath" value="${imagePathGlobal}/iconPmntList_IMAPayment.jpg"/>
    <tiles:put name="url">
        <c:url value="/private/ima/products/list.do">
            <phiz:param name="category" value="${categoryId}"/>
        </c:url>
    </tiles:put>
    <tiles:put name="serviceId" value="IMAOpeningClaim"/>
</tiles:insert>

<tiles:insert definition="categoryTemplate" flush="false" service="ReIssueCardClaim">
    <tiles:put name="title">Оформить заявку на перевыпуск карты</tiles:put>
    <tiles:put name="hint">
        Оформить заявку на перевыпуск карты.
    </tiles:put>
    <tiles:put name="imagePath" value="${imagePathGlobal}/card_64.jpg"/>
    <tiles:put name="url"><c:url value="/private/payments/payment.do">
        <phiz:param name="form" value="ReIssueCardClaim"/>
    </c:url></tiles:put>
    <tiles:put name="serviceId" value="ReIssueCardClaim"/>
</tiles:insert>