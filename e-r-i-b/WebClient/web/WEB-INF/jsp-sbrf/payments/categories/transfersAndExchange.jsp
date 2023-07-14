<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="person" value="${phiz:getPersonInfo()}"/>
<c:set var="availableIMAPayment" value="${phiz:impliesOperation('CreateFormPaymentOperation','IMAPayment')}"/>
    <%--Перевод между своими счетами--%>
    <tiles:insert definition="categoryTemplate" flush="false" service="InternalPayment" operation="CreateFormPaymentOperation">
        <tiles:put name="title">Перевод между своими счетами и картами</tiles:put>
        <tiles:put name="hint">
            Перевести деньги на Вашу карту, вклад или счет.
        </tiles:put>
        <tiles:put name="imagePath" value="${imagePath}/iconPmntList_InternalPayment.png"/>
        <tiles:put name="url"><c:url value="/private/payments/payment.do">
            <phiz:param name="category" value="${categoryId}"/>
            <phiz:param name="form" value="InternalPayment"/>
        </c:url></tiles:put>
        <tiles:put name="serviceId" value="InternalPayment"/>
    </tiles:insert>
    <%--Перевод частному лицу--%>
    <tiles:insert definition="categoryTemplate" flush="false" service="RurPayment" operation="CreateFormPaymentOperation">
        <tiles:put name="title">Перевод клиенту Сбербанка</tiles:put>
        <tiles:put name="hint">
            Перевести деньги частному лицу на карту, вклад или счет.
        </tiles:put>
        <tiles:put name="imagePath" value="${imagePath}/iconPmntList_RurPayment.png"/>
        <tiles:put name="url"><c:url value="/private/payments/payment.do">
            <phiz:param name="category" value="${categoryId}"/>
            <phiz:param name="form" value="RurPayment"/>
        </c:url></tiles:put>
        <tiles:put name="serviceId" value="RurPayment"/>
    </tiles:insert>
    <%-->Перевод организации и оплата услуг--%>
    <tiles:insert definition="categoryTemplate" flush="false" service="JurPayment" operation="CreateFormPaymentOperation">
        <tiles:put name="title">Перевод организации и оплата услуг </tiles:put>
        <tiles:put name="hint">
            Перевести деньги и оплатить услуги.
        </tiles:put>
        <tiles:put name="imagePath" value="${imagePath}/iconPmntList_JurPayment.png"/>
        <tiles:put name="url"><c:url value="/private/payments/jurPayment/edit.do">
            <phiz:param name="category" value="${categoryId}"/>
        </c:url></tiles:put>
        <tiles:put name="serviceId" value="JurPayment"/>
    </tiles:insert>
    <%--Обмен валюты--%>
    <tiles:insert definition="categoryTemplate" flush="false" service="ConvertCurrencyPayment" operation="CreateFormPaymentOperation">
        <tiles:put name="title">Обмен валюты</tiles:put>
        <tiles:put name="hint">
            Купить, продать или обменять иностранную валюту.
        </tiles:put>
        <tiles:put name="imagePath" value="${imagePath}/iconPmntList_ConvertCurrencyPayment.png"/>
        <tiles:put name="url"><c:url value="/private/payments/payment.do">
            <phiz:param name="category" value="${categoryId}"/>
            <phiz:param name="form" value="ConvertCurrencyPayment"/>
        </c:url></tiles:put>
        <tiles:put name="serviceId" value="ConvertCurrencyPayment"/>
    </tiles:insert>
    <c:if test="${availableIMAPayment}">
        <%--Покупка/продажа металла--%>
        <tiles:insert definition="categoryTemplate" flush="false" service="IMAPayment" operation="CreateFormPaymentOperation">
            <tiles:put name="title">Покупка/продажа металла</tiles:put>
            <tiles:put name="hint">
                Купить или продать драгоценный металл.
            </tiles:put>
            <tiles:put name="imagePath" value="${imagePath}/iconPmntList_IMAPayment.png"/>
            <tiles:put name="url"><c:url value="/private/payments/payment.do">
                <phiz:param name="category" value="${categoryId}"/>
                <phiz:param name="form" value="IMAPayment"/>
            </c:url></tiles:put>
            <tiles:put name="serviceId" value="IMAPayment"/>
        </tiles:insert>
    </c:if>

    <%--<tiles:insert definition="categoryTemplate" flush="false">
        <tiles:put name="title">Пополнение брокерских счетов</tiles:put>
        <tiles:put name="hint">
            Внести средства на брокерский счет.
        </tiles:put>
        <tiles:put name="imagePath" value="${imagePath}/icon_pmnt_money.png"/>
        <tiles:put name="url"><c:url value="/private/payments/payment.do">
            <phiz:param name="form" value="InternalPayment"/>
        </c:url></tiles:put>
    </tiles:insert>--%>
