<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Перевод между своими счетами</title>
</head>

<body>
<h1>Перевод между своими счетами</h1>

<script type="text/javascript">
    function setExactAmount(text)
    {
        var exactAmount = document.getElementById('exactAmount');
        if (exactAmount != null)
            exactAmount.value = text;
    }
</script>

<html:form action="/atm/internalPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    
    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>

        <c:if test="${not empty form.response.initialData}">
            <c:set var="initialData" value="${form.response.initialData.internalPayment}"/>
        </c:if>
        <c:if test="${not empty form.response.confirmStage.confirmType}">
            <c:set var="confirmType" value="${form.response.confirmStage.confirmType}"/>
        </c:if>

        <tiles:put name="data">
            <c:choose>
                <%--если есть initialData, значит это стадия заполнения полей платежа--%>
                <c:when test="${not empty initialData}">
                    <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                        <tiles:insert page="fields-table.jsp" flush="false">
                            <tiles:put name="data">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="initialData" beanProperty="fromResource"/>
                                </tiles:insert>
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="initialData" beanProperty="toResource"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                        <tr><td>buyAmount</td><td><html:text onfocus="setExactAmount('destination-field-exact')" property="buyAmount" value="${initialData.buyAmount.moneyType.value}"/></td></tr>
                        <tr><td>sellAmount</td><td><html:text onfocus="setExactAmount('charge-off-field-exact')"property="sellAmount" value="${initialData.sellAmount.moneyType.value}"/></td></tr>
                        <tr><td>exactAmount</td><td><html:text styleId="exactAmount" property="exactAmount" readonly="true" value="${initialData.exactAmount.stringType.value}"/></td></tr>
                    </div>
                </c:when>
                <%--если пришел confirmType => пора подтверждать--%>
                <c:when test="${not empty confirmType}">
                    <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                        <c:set var="payment" value="${form.response.document.internalPaymentDocument}"/>
                        <tiles:insert page="fields-table.jsp" flush="false">
                            <tiles:put name="data">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="payment" beanProperty="fromResource"/>
                                </tiles:insert>
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="payment" beanProperty="toResource"/>
                                </tiles:insert>
                                <c:if test="${not empty payment.course}">
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="payment" beanProperty="course"/>
                                    </tiles:insert>
                                </c:if>
                                <c:if test="${not empty payment.buyAmount}">
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="payment" beanProperty="buyAmount"/>
                                    </tiles:insert>
                                </c:if>
                                <c:if test="${not empty payment.sellAmount}">
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="payment" beanProperty="sellAmount"/>
                                    </tiles:insert>
                                </c:if>
                            </tiles:put>
                        </tiles:insert>
                    </div>
                </c:when>
            </c:choose>
        </tiles:put>

         <c:if test="${empty confirmType}">
            <tiles:put name="operation" value="save"/>
        </c:if>
        <tiles:put name="formName" value="InternalPayment"/>
    </tiles:insert>

    <c:url var="paymentUrl" value="/atm/internalPayment.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
    </c:url>
    <html:link href="${paymentUrl}">Начать сначала</html:link>
</html:form>

</body>
</html:html>
