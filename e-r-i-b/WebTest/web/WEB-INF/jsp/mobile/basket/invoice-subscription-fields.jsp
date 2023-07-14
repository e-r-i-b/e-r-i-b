<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:if test="${not empty form.response}">
    <c:set var="initialData" value="${form.response.document.createInvoiceSubscriptionPaymentDocument}"/>
    <tiles:insert page="../payments/fields-table.jsp" flush="false">
        <tiles:put name="data">
            <c:set var="receiver" value="${initialData.receiver}"/>
            <%--Наименование получателя--%>
            <c:if test="${not empty receiver.name}">
                <tiles:insert page="../payments/field.jsp" flush="false">
                    <tiles:put name="field" beanName="receiver" beanProperty="name"/>
                </tiles:insert>
            </c:if>
            <%--Описание получателя--%>
            <c:if test="${not empty receiver.description}">
                <tiles:insert page="../payments/field.jsp" flush="false">
                    <tiles:put name="field" beanName="receiver" beanProperty="description"/>
                </tiles:insert>
            </c:if>
            <%--Регион оплаты--%>
            <c:if test="${not empty receiver.region}">
                <tiles:insert page="../payments/field.jsp" flush="false">
                    <tiles:put name="field" beanName="receiver" beanProperty="region"/>
                </tiles:insert>
            </c:if>
            <%--ИНН получателя--%>
            <c:if test="${not empty receiver.inn}">
                <tiles:insert page="../payments/field.jsp" flush="false">
                    <tiles:put name="field" beanName="receiver" beanProperty="inn"/>
                </tiles:insert>
            </c:if>
            <%--Счет получателя--%>
            <c:if test="${not empty receiver.account}">
                <tiles:insert page="../payments/field.jsp" flush="false">
                    <tiles:put name="field" beanName="receiver" beanProperty="account"/>
                </tiles:insert>
            </c:if>
            <!--Банк получателя-->
            <c:set var="bank" value="${receiver.bank}"/>
            <c:if test="${not empty bank}">
                <!--Наименование банка-->
                <tiles:insert page="../payments/field.jsp" flush="false">
                    <tiles:put name="field" beanName="bank" beanProperty="name"/>
                </tiles:insert>
                <!--БИК банка-->
                <tiles:insert page="../payments/field.jsp" flush="false">
                    <tiles:put name="field" beanName="bank" beanProperty="bic"/>
                </tiles:insert>
                <!--Корсчёт банка-->
                <c:if test="${not empty bank.corAccount}">
                    <tiles:insert page="../payments/field.jsp" flush="false">
                        <tiles:put name="field" beanName="bank" beanProperty="corAccount"/>
                    </tiles:insert>
                </c:if>
            </c:if>
            <!--Услуга-->
            <c:set var="serviceName" value="${initialData.serviceName}"/>
            <c:if test="${not empty serviceName}">
                <tiles:insert page="../payments/field.jsp" flush="false">
                    <tiles:put name="field" beanName="initialData" beanProperty="serviceName"/>
                </tiles:insert>
            </c:if>
            <!-- Детали документа -->
            <c:set var="extendedFields" value="${initialData.subscriptionDetails.extendedFields}"/>
            <c:if test="${not empty extendedFields}">
                <c:forEach items="${extendedFields.field}" varStatus="i">
                    <tiles:insert page="../payments/field.jsp" flush="false">
                        <tiles:put name="field" beanName="extendedFields" beanProperty="field[${i.index}]"/>
                    </tiles:insert>
                </c:forEach>
            </c:if>

            <!--Детали платежа-->
            <!--Счет списания-->
            <tiles:insert page="../payments/field.jsp" flush="false">
                <tiles:put name="field" beanName="initialData" beanProperty="fromResource"/>
            </tiles:insert>

            <%--Название регулярного платежа--%>
            <tiles:insert page="../payments/field.jsp" flush="false">
                <tiles:put name="field" beanName="initialData" beanProperty="autoSubName"/>
            </tiles:insert>

            <%--Период оплаты (ONCE_IN_WEEK (раз в неделю), ONCE_IN_MONTH (раз в месяц), ONCE_IN_QUARTER (раз в квартал))--%>
            <c:set var="autoSubEvent" value="${initialData.autoSubEvent}"/>
            <tiles:insert page="../payments/field.jsp" flush="false">
               <tiles:put name="field" beanName="autoSubEvent" beanProperty="payType"/>
            </tiles:insert>
            <%--Момент оплаты: для ONCE_IN_WEEK - день недели, для ONCE_IN_MONTH число месяца, для ONCE_IN_QUARTER число и месяц квартала--%>
            <tiles:insert page="../payments/field.jsp" flush="false">
               <tiles:put name="field" beanName="autoSubEvent" beanProperty="payDate"/>
            </tiles:insert>

            <c:if test="${not empty initialData.accountingEntityId}">
                <tiles:insert page="../payments/field.jsp" flush="false">
                    <tiles:put name="field" beanName="initialData" beanProperty="accountingEntityId"/>
                </tiles:insert>
            </c:if>

            <c:if test="${not empty initialData.recipient}">
                <tiles:insert page="../payments/field.jsp" flush="false">
                    <tiles:put name="field" beanName="initialData" beanProperty="recipient"/>
                </tiles:insert>
            </c:if>
        </tiles:put>
    </tiles:insert>
</c:if>