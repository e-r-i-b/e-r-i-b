<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="categoryTemplate" flush="false" service="InternalPayment" operation="CreateFormPaymentOperation">
    <tiles:put name="title">Выписка из пенсионного фонда России</tiles:put>
    <tiles:put name="hint">
        Запросить выписку из ПФР.
    </tiles:put>
    <tiles:put name="imagePath" value="${imagePath}/icon_pfr_RequestStatements.png"/>
    <tiles:put name="url"><c:url value="/private/payments/payment.do">
        <phiz:param name="category" value="${categoryId}"/>
        <phiz:param name="form" value="PFRStatementClaim"/>
    </c:url></tiles:put>
    <tiles:put name="serviceId" value="PFRStatementClaim"/>
</tiles:insert>