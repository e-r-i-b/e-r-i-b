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

    <html:form action="/mobileApi/imaPayment" show="true">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        
        <tiles:insert definition="mobile" flush="false">
            <tiles:put name="address" value="/private/payments/payment.do"/>
            <tiles:put name="formName" value="IMAPayment"/>
            <tiles:put name="operation" value="save"/>

            <tiles:put name="data">
                <c:set var="initialData" value="${form.response.initialData.IMAPayment}"/>
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
                <tr><td>buyAmount</td><td><html:text onfocus="setExactAmount('destination-field-exact')" value="${initialData.buyAmount.moneyType.value}" property="buyAmount"/></td></tr>
                <tr><td>sellAmount</td><td><html:text onfocus="setExactAmount('charge-off-field-exact')" value="${initialData.sellAmount.moneyType.value}" property="sellAmount"/></td></tr>
                <tr><td>exactAmount</td><td><html:text styleId="exactAmount" value="${initialData.exactAmount.stringType.value}" property="exactAmount" readonly="true"/></td></tr>
                <tr><td>operationCode</td><td><html:text styleId="operationCode" property="operationCode"/></td></tr>
            </tiles:put>
        </tiles:insert>
    </html:form>

    </body>
</html:html>
