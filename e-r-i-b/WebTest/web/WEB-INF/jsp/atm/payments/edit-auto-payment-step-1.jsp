<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>

<html:html>
<head>
    <title>Редактирование автоплатежа (iqwave)</title>
</head>

<body>
<h1>Редактирование автоплатежа (iqwave): сохранение</h1>

<html:form action="/atm/editAutoPaymentPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <script type="text/javascript">
        $(document).ready(function()
        {
            var autoPaymentStartDate = $('.autoPaymentStartDate > td > input');
            var firstPaymentDate = $('.firstPaymentDate > td > input');

            if (autoPaymentStartDate.length > 0 && firstPaymentDate.length > 0)
            {
                function copyDateValue()
                {
                    firstPaymentDate.get(0).value = autoPaymentStartDate.get(0).value;
                }

                //на старте
                copyDateValue();
                //и навешиваем handler на событие
                autoPaymentStartDate.keyup(copyDateValue);
            }
        });
    </script>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>
        <tiles:put name="formName" value="EditAutoPaymentPayment"/>
        <tiles:put name="operation" value="next"/>

        <tiles:put name="data">
            <c:set var="initialData" value="${form.response.initialData.editAutoPaymentPayment}"/>
            <tiles:insert page="fields-table.jsp" flush="false">
                <tiles:put name="data">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="recipient"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="receiverName"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="fromResource"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="requisiteName"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="requisite"/>
                    </tiles:insert>
                    <c:if test="${not empty initialData.amount}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="initialData" beanProperty="amount"/>
                        </tiles:insert>
                    </c:if>

                    <c:set var="autoPaymentParameters" value="${initialData.autoPaymentParameters}"/>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="autoPaymentParameters" beanProperty="executionEventType"/>
                    </tiles:insert>
                    <c:choose>
                        <c:when test="${not empty autoPaymentParameters.periodic}">
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="autoPaymentParameters" beanProperty="periodic.autoPaymentStartDate"/>
                                <tiles:put name="tdClass" value="autoPaymentStartDate"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="autoPaymentParameters" beanProperty="periodic.firstPaymentDate"/>
                                <tiles:put name="tdClass" value="firstPaymentDate"/>
                            </tiles:insert>
                        </c:when>
                        <c:when test="${not empty autoPaymentParameters.reduseOfBalance}">
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="autoPaymentParameters" beanProperty="reduseOfBalance.autoPaymentFloorLimit"/>
                            </tiles:insert>
                            <c:if test="${autoPaymentParameters.reduseOfBalance.autoPaymentTotalAmountLimit != null}">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="autoPaymentParameters" beanProperty="reduseOfBalance.autoPaymentTotalAmountLimit"/>
                                </tiles:insert>
                            </c:if>
                        </c:when>
                        <c:when test="${not empty autoPaymentParameters.byInvoice}">
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="autoPaymentParameters" beanProperty="byInvoice.autoPaymentFloorLimit"/>
                            </tiles:insert>
                        </c:when>
                    </c:choose>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="autoPaymentParameters" beanProperty="autoPaymentName"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>