<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="categoryTemplate" flush="false" service="JurPayment" operation="CreateFormPaymentOperation">
    <tiles:put name="title">Оплата налогов по реквизитам</tiles:put>
    <tiles:put name="hint">Оплата налогов и сборов (ПД-4, квитанции и т.п.).</tiles:put>
    <tiles:put name="imagePath" value="${imagePath}/iconPmntList_TaxPayment.png"/>
    <tiles:put name="url"><c:url value="/private/payments/jurPayment/edit.do">
        <phiz:param name="category" value="${categoryId}"/>
        <phiz:param name="fromResource" value="${param['fromResource']}"/>
    </c:url></tiles:put>
    <tiles:put name="serviceId" value="JurPayment"/>
</tiles:insert>
