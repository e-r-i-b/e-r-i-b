<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:importAttribute/>

<tiles:insert page="fields-table.jsp" flush="false">
    <tiles:put name="data">
        <c:set var="receiver" value="${document.receiver}"/>
        <c:if test="${not empty receiver.description}">
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="receiver" beanProperty="description"/>
            </tiles:insert>
        </c:if>
        <c:if test="${not empty receiver.name}">
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="receiver" beanProperty="name"/>
            </tiles:insert>
        </c:if>
        <c:if test="${not empty receiver.serviceName}">
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="receiver" beanProperty="serviceName"/>
            </tiles:insert>
        </c:if>
        <c:set var="bankDetails" value="${receiver.bankDetails}"/>
        <c:if test="${not empty bankDetails}">
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="bankDetails" beanProperty="INN"/>
            </tiles:insert>
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="bankDetails" beanProperty="account"/>
            </tiles:insert>
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="bankDetails" beanProperty="bank.name"/>
            </tiles:insert>
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="bankDetails" beanProperty="bank.BIC"/>
            </tiles:insert>
            <c:if test="${not empty bankDetails.bank.corAccount}">
                <tiles:insert page="field.jsp" flush="false">
                    <tiles:put name="field" beanName="bankDetails" beanProperty="bank.corAccount"/>
                </tiles:insert>
            </c:if>
        </c:if>

        <tiles:insert page="field.jsp" flush="false">
            <tiles:put name="field" beanName="document" beanProperty="fromResource"/>
        </tiles:insert>

        <c:set var="paymentDetails" value="${document.paymentDetails}"/>
        <c:set var="externalFields" value="${paymentDetails.externalFields}"/>
        <c:if test="${not empty externalFields}">
            <c:forEach items="${externalFields.field}" varStatus="i">
                <tiles:insert page="field.jsp" flush="false">
                    <tiles:put name="field" beanName="externalFields" beanProperty="field[${i.index}]"/>
                </tiles:insert>
            </c:forEach>
        </c:if>
    </tiles:put>
</tiles:insert>
<c:if test="${not empty paymentDetails.commission}">
    <b>Комиссия:</b> ${paymentDetails.commission.amount} ${paymentDetails.commission.currency.code}
</c:if>

