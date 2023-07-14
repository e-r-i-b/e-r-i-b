<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>

<html:form action="/private/payments/servicesPayments" onsubmit="return setEmptyAction()">
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="paymentService" value="${phiz:getPaymentServiceById(frm.serviceId)}"/>
    <c:set var="isSearch" value="${frm.searchServices != null and fn:length(fn:trim(frm.searchServices)) != 0}"/>
    <c:set var="fromResource" value="${param['fromResource']}"/>
    <c:set var="currentPage" value="${frm.currentPage}"/>

    <c:set var="serviceURL" value="/private/payments/servicesPayments.do" scope="request"/>
    <c:set var="providerURL" value="/private/payments/servicesPayments/edit.do" scope="request"/>
    <c:set var="categoryURL" value="/private/payments/category.do" scope="request"/>
    <c:set var="paymentListURL" value="/private/payments" scope="request"/>
    <c:set var="pageKind" value="index" scope="request"/>
    <c:set var="backURL" value="/private/payments.do"/>

    <tiles:insert definition="paymentMain">
        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name"><bean:message key="label.mainMenu.payments" bundle="commonBundle"/></tiles:put>
                <tiles:put name="action" value="/private/payments.do"/>
            </tiles:insert>

<%--  TODO КРЕНЕВ восстановить хлебные
            <c:if test="${not empty frm.parentIds}">
                <c:set var="parentServices" value="${frm.parentServices}"/>
                <c:set var="parentIds" value=""/>
                <c:forEach var="paymentServ" items="${fn:split(frm.parentIds,',' )}">
                    <tiles:insert definition="breadcrumbsLink" flush="false">
                        <tiles:put name="name">Оплата: ${parentServices[paymentServ]} </tiles:put>
                        <tiles:put name="action" value="/private/payments/servicesPayments.do?serviceId=${paymentServ}&parentIds=${parentIds}"/>
                        <tiles:put name="last" value="false"/>
                    </tiles:insert>
                    <c:set var="parentIds" value="${parentIds},${paymentServ}" />
                </c:forEach>
            </c:if>
--%>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name">Оплата: ${paymentService.name}</tiles:put>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <%@ include file="/WEB-INF/jsp-sbrf/payments/listPaymentServices.jsp"%>
        </tiles:put>
    </tiles:insert>
</html:form>