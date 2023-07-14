<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:importAttribute/>

<tiles:insert page="fields-table.jsp" flush="false">
    <tiles:put name="data">
        <c:set var="receiver" value="${autoSubPayment.receiver}"/>
        <c:if test="${not empty receiver.name}">
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="receiver" beanProperty="name"/>
            </tiles:insert>
        </c:if>
        <c:if test="${not empty receiver.service}">
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="receiver" beanProperty="service"/>
            </tiles:insert>
        </c:if>
        <tiles:insert page="field.jsp" flush="false">
            <tiles:put name="field" beanName="receiver" beanProperty="inn"/>
        </tiles:insert>
        <tiles:insert page="field.jsp" flush="false">
            <tiles:put name="field" beanName="receiver" beanProperty="account"/>
        </tiles:insert>
        <c:set var="bank" value="${receiver.bank}"/>
        <c:if test="${not empty bank}">
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="bank" beanProperty="name"/>
            </tiles:insert>
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="bank" beanProperty="bic"/>
            </tiles:insert>
            <c:if test="${not empty bank.corAccount}">
                <tiles:insert page="field.jsp" flush="false">
                    <tiles:put name="field" beanName="bank" beanProperty="corAccount"/>
                </tiles:insert>
            </c:if>
        </c:if>

        <tiles:insert page="field.jsp" flush="false">
            <tiles:put name="field" beanName="autoSubPayment" beanProperty="fromResource"/>
        </tiles:insert>

        <c:set var="extendedFields" value="${autoSubPayment.paymentDetails.externalFields}"/>
        <c:if test="${not empty extendedFields}">
            <c:forEach items="${extendedFields.field}" varStatus="i">
                <tiles:insert page="field.jsp" flush="false">
                    <tiles:put name="field" beanName="extendedFields" beanProperty="field[${i.index}]"/>
                </tiles:insert>
            </c:forEach>
        </c:if>
        <c:if test="${not empty autoSubPayment.paymentDetails.promoCode}">
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="autoSubPayment" beanProperty="paymentDetails.promoCode"/>
            </tiles:insert>
        </c:if>

        <c:set var="autoSubDetails" value="${autoSubPayment.autoSubDetails}"/>
        <tiles:insert page="field.jsp" flush="false">
            <tiles:put name="field" beanName="autoSubDetails" beanProperty="startDate"/>
        </tiles:insert>
        <c:if test="${not empty autoSubDetails.updateDate}">
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="autoSubDetails" beanProperty="updateDate"/>
            </tiles:insert>
        </c:if>
        <tiles:insert page="field.jsp" flush="false">
            <tiles:put name="field" beanName="autoSubDetails" beanProperty="name"/>
        </tiles:insert>
        <tiles:insert page="field.jsp" flush="false">
            <tiles:put name="field" beanName="autoSubDetails" beanProperty="type"/>
        </tiles:insert>
        <c:if test="${not empty autoSubDetails.always}">
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="autoSubDetails" beanProperty="always.eventType"/>
            </tiles:insert>
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="autoSubDetails" beanProperty="always.nextPayDate"/>
            </tiles:insert>
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="autoSubDetails" beanProperty="always.amount"/>
            </tiles:insert>
        </c:if>
        <c:if test="${not empty autoSubDetails.invoice}">
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="autoSubDetails" beanProperty="invoice.eventType"/>
            </tiles:insert>
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="autoSubDetails" beanProperty="invoice.startDate"/>
            </tiles:insert>
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="autoSubDetails" beanProperty="invoice.amount"/>
            </tiles:insert>
        </c:if>

    </tiles:put>
</tiles:insert>