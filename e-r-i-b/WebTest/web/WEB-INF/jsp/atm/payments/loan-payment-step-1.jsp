<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>��������� �������</title>
</head>

<body>
<h1>��������� �������</h1>

<html:form action="/atm/loanPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>

        <c:if test="${not empty form.response.initialData}">
            <c:set var="initialData" value="${form.response.initialData.loanPayment}"/>
        </c:if>
        <c:if test="${not empty form.response.confirmStage.confirmType}">
            <c:set var="confirmType" value="${form.response.confirmStage.confirmType}"/>
        </c:if>
        
        <tiles:put name="data">
            <c:choose>
                <%--���� ���� initialData, ������ ��� ������ ���������� ����� �������--%>
                <c:when test="${not empty initialData}">
                    <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
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
                        <tr><td>amount</td><td><html:text property="amount"/></td></tr>
                    </div>
                </c:when>
                <%--���� ������ confirmType => ���� ������������--%>
                <c:when test="${not empty confirmType}">
                    <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                        <c:set var="payment" value="${form.response.document.loanPaymentDocument}"/>
                        <tiles:insert page="fields-table.jsp" flush="false">
                            <tiles:put name="data">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="payment" beanProperty="loan"/>
                                </tiles:insert>
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="payment" beanProperty="fromResource"/>
                                </tiles:insert>
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="payment" beanProperty="amount"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                    </div>
                </c:when>
            </c:choose>
        </tiles:put>

        <c:if test="${empty confirmType}">
            <tiles:put name="operation" value="next"/>
        </c:if>
        <tiles:put name="formName" value="LoanPayment"/>
    </tiles:insert>

    <c:url var="loanPaymentUrl" value="/atm/loanPayment.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
    </c:url>
    <html:link href="${loanPaymentUrl}">������ �������</html:link>
</html:form>

</body>
</html:html>
