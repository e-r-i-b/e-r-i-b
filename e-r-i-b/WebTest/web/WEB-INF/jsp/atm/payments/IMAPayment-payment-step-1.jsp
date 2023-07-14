<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>Покупка/продажа OMC</title>
    </head>

    <body>
    <h1>Покупка/продажа OMC</h1>

    <script type="text/javascript">
        function setExactAmount(text)
        {
            var exactAmount = document.getElementById('exactAmount');
            if (exactAmount != null)
                exactAmount.value = text;
        }
    </script>

    <html:form action="/atm/IMAPayment" show="true">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        
        <tiles:insert definition="atm" flush="false">
            <tiles:put name="address" value="/private/payments/payment.do"/>

            <tiles:put name="data">
                <c:choose>
                    <c:when test="${not empty form.response.initialData}">
                        <c:set var="initialData" value="${form.response.initialData.IMAPayment}"/>

                        <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                            <tiles:insert page="fields-table.jsp" flush="false">
                                <tiles:put name="data">
                                     <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="documentNumber"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="documentDate"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="fromResource"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="toResource"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                            <tr><td>buyAmount</td><td><html:text onfocus="setExactAmount('destination-field-exact')" property="buyAmount"/></td></tr>
                            <tr><td>sellAmount</td><td><html:text onfocus="setExactAmount('charge-off-field-exact')" property="sellAmount"/></td></tr>
                            <tr><td>exactAmount</td><td><html:text styleId="exactAmount" property="exactAmount" readonly="true"/></td></tr>
                        </div>
                    </c:when>
                    <%--если пришел confirmType => пора подтверждать--%>
                    <c:when test="${not empty form.response.confirmStage.confirmType}">
                        <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                            <c:set var="payment" value="${form.response.document.IMAPaymentDocument}"/>
                            <tiles:insert page="fields-table.jsp" flush="false">
                                <tiles:put name="data">
                                     <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="payment" beanProperty="documentNumber"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="payment" beanProperty="documentDate"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="payment" beanProperty="fromResource"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="payment" beanProperty="toResource"/>
                                    </tiles:insert>
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
                                    <c:if test="${not empty payment.exactAmount}">
                                        <tiles:insert page="field.jsp" flush="false">
                                            <tiles:put name="field" beanName="payment" beanProperty="exactAmount"/>
                                        </tiles:insert>
                                    </c:if>
                                    <c:if test="${not empty payment.course}">
                                        <tiles:insert page="field.jsp" flush="false">
                                            <tiles:put name="field" beanName="payment" beanProperty="course"/>
                                        </tiles:insert>
                                    </c:if>
                                </tiles:put>
                            </tiles:insert>
                        </div>
                    </c:when>
                </c:choose>
            </tiles:put>

            <c:if test="${empty form.response.confirmStage.confirmType}">
                <tiles:put name="operation" value="save"/>
            </c:if>
            <tiles:put name="formName" value="IMAPayment"/>
        </tiles:insert>

        <c:url var="IMAPaymentUrl" value="/atm/IMAPayment.do">
            <c:param name="url" value="${form.url}"/>
            <c:param name="cookie" value="${form.cookie}"/>
            <c:param name="proxyUrl" value="${form.proxyUrl}"/>
            <c:param name="proxyPort" value="${form.proxyPort}"/>
        </c:url>
        <html:link href="${IMAPaymentUrl}">Начать сначала</html:link>
    </html:form>

    </body>
</html:html>
