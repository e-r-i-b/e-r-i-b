<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>�������� ����������� ��������� �������</title>
</head>

<body>
<h1>������������� �������</h1>

<html:form action="/mobileApi/loanPaymentLongOffer" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>
        <tiles:put name="formName" value="LoanPayment"/>
        <tiles:put name="operation" value="init"/>

        <tiles:put name="data">
            �������������� ��������� �� ���������
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>
