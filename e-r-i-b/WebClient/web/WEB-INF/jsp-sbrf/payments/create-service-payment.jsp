<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--@elvariable id="form" type="com.rssl.phizic.web.actions.payments.forms.EditServicePaymentForm"--%>

<%-- Первый шаг ввода данных платежа поставщику услуг --%>
<html:form action="/private/payments/servicesPayments/edit" onsubmit="return setEmptyAction();">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:choose>
        <%-- Для оплаты УЭК (первый шаг редактирования) используется отдельная jsp --%>
        <c:when test="${form.UECPayOrder}">
            <tiles:insert definition="paymentCurrent">
                <tiles:put name="mainmenu" value="Payments"/>

                <tiles:put name="breadcrumbs">
                    <tiles:insert definition="breadcrumbsLink" flush="false">
                        <tiles:put name="main" value="true"/>
                        <tiles:put name="action" value="/private/accounts.do"/>
                    </tiles:insert>
                    <tiles:insert definition="breadcrumbsLink" flush="false">
                        <tiles:put name="name"><bean:message key="label.mainMenu.payments" bundle="commonBundle"/></tiles:put>
                        <tiles:put name="action" value="/private/payments.do"/>
                    </tiles:insert>
                    <c:set var="serviceProvider" value="${phiz:getServiceProvider(form.recipient)}"/>
                    <tiles:insert definition="breadcrumbsLink" flush="false">
                        <tiles:put name="name" >Оплата: ${serviceProvider.name}</tiles:put>
                        <tiles:put name="last" value="true"/>
                    </tiles:insert>
                </tiles:put>

                <tiles:put name="data" type="string">
                    <tiles:insert page="create-uec-payment-data.jsp" flush="false">
                        <tiles:put name="serviceProvider"  beanName="serviceProvider"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            <tiles:insert page="servicesPayment.jsp" flush="false"/>
        </c:otherwise>
    </c:choose>
</html:form>
