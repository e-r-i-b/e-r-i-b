<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Погашение кредита</title>
</head>

<body>
<h1>Погашение кредита</h1>

<html:form action="/mobileApi/loanPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>
        <tiles:put name="formName" value="LoanPayment"/>
        <tiles:put name="operation" value="next"/>

        <c:set var="initialData" value="${form.response.initialData.loanPayment}"/>

        <tiles:put name="data">
            <tiles:insert page="fields-table.jsp" flush="false">
                <tiles:put name="data">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="availableFromResources"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="availableLoanResources"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
            <tr><td>amount</td><td><html:text property="amount" value="${initialData.amount.moneyType.value}"/></td></tr>
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>
