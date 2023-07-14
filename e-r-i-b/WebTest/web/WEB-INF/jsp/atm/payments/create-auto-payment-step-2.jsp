<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>
    <title>Создание автоплатежа</title>
</head>

<body>
<script type="text/javascript">
    $(document).ready(function()
    {
        //автоплатежи iqwave
        var autoPaymentType = document.getElementsByName("autoPaymentType")[0];
        if (autoPaymentType != undefined)
            changeAutoPaymentType(autoPaymentType);

        //шинные автоплатежи
        var autoSubscriptionType = document.getElementsByName("autoSubType")[0];
        if (autoSubscriptionType != undefined)
            changeAutoSubscriptionType(autoSubscriptionType);
    });

    // изменение типа автоплатежа iqwave
    function changeAutoPaymentType(item)
    {
        clearFields();
        
        var currentSelect = item.options[item.selectedIndex == undefined ? 0 : item.selectedIndex].value;
        if (currentSelect == 'ONCE_IN_QUARTER')
        {
            $('.months').show();
            $('.autoPaymentStartDate').show();
        }
        else if (currentSelect == 'ONCE_IN_MONTH')
        {
            $('.autoPaymentStartDate').show();
        }
        else if (currentSelect == 'ONCE_IN_YEAR')
        {
            $('.month').show().removeAttr('disabled');
            $('.autoPaymentStartDate').show();
        }
        else if (currentSelect == 'REDUSE_OF_BALANCE')
        {
            $('.floorLimit').show();
            $('.autoPaymentTotalAmountLimit').show();
        }
        else if(currentSelect == 'BY_INVOICE')
        {
            $('.floorLimit').show();
        }
    }

    // изменение типа шинного автоплатежа
    function changeAutoSubscriptionType(item)
    {
        clearFields();

        var currentSelect = item.options[item.selectedIndex == undefined ? 0 : item.selectedIndex].value;
        if (currentSelect == 'ALWAYS')
        {
            $('.always').show();
            $("tr.always > td > select").removeAttr('disabled');
            $("tr.invoice > td > select").attr('disabled', 'disabled');
        }
        else if (currentSelect == 'INVOICE')
        {
            $('.invoice').show();
            $("tr.invoice > td > select").removeAttr('disabled');
            $("tr.always > td > select").attr('disabled', 'disabled');
        }
    }

    function clearFields()
    {
        $('.floatField').hide();
    }

    function copyDate(fromElem, toIdElem)
    {
        var toElem = document.getElementById(toIdElem);
        toElem.value = fromElem.value;
    }

    
</script>
<h1>Сохранение</h1>

<html:form action="/atm/createAutoPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm">
        <tiles:put name="address" value="/private/payments/payment.do"/>

        <c:if test="${not empty form.response.confirmStage.confirmType}">
            <c:set var="confirmType" value="${form.response.confirmStage.confirmType}"/>
        </c:if>
        
        <tiles:put name="data">
            <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                <c:choose>
                    <%--iqwave автоплатеж--%>
                    <c:when test="${form.response.document.form == 'CreateAutoPaymentPayment'}">
                        <c:set var="document" value="${form.response.document.createAutoPaymentDocument}"/>
                        <tiles:insert page="fields-table.jsp" flush="false">
                            <tiles:put name="data">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="document" beanProperty="fromResource"/>
                                </tiles:insert>
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="document" beanProperty="autoPaymentParameters.autoPaymentType"/>
                                    <tiles:put name="onChange" value="changeAutoPaymentType(this);"/>
                                </tiles:insert>

                                <c:if test="${document.autoPaymentParameters.onceInYear != null}">
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="document" beanProperty="autoPaymentParameters.onceInYear.month"/>
                                        <tiles:put name="tdClass" value="floatField month"/>
                                    </tiles:insert>
                                </c:if>

                                <c:if test="${document.autoPaymentParameters.onceInQuarter != null}">
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="document" beanProperty="autoPaymentParameters.onceInQuarter.months"/>
                                        <tiles:put name="tdClass" value="floatField months"/>
                                    </tiles:insert>
                                </c:if>

                                <c:if test="${document.autoPaymentParameters.reduseOfBalance != null}">
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="document" beanProperty="autoPaymentParameters.reduseOfBalance.floorLimit"/>
                                        <tiles:put name="tdClass" value="floatField floorLimit"/>
                                    </tiles:insert>
                                </c:if>

                                <c:if test="${document.autoPaymentParameters.reduseOfBalance.autoPaymentTotalAmountLimit != null}">
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="document" beanProperty="autoPaymentParameters.reduseOfBalance.autoPaymentTotalAmountLimit"/>
                                        <tiles:put name="tdClass" value="floatField autoPaymentTotalAmountLimit"/>
                                    </tiles:insert>
                                </c:if>

                                <c:forEach items="${document.paymentDetails.externalFields.field}"  varStatus="i">
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="document" beanProperty="paymentDetails.externalFields.field[${i.index}]"/>
                                    </tiles:insert>
                                 </c:forEach>
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="document" beanProperty="autoPaymentParameters.autoPaymentName"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                        <table>
                            <tr class="floatField autoPaymentStartDate"><td>Дата начала:</td>    <td><html:text property="autoPaymentStartDate" onkeyup="copyDate(this, 'firstPaymentDate')"/></td></tr>
                            <tr  class="floatField autoPaymentStartDate"><td> Дата ближайшего платежа:</td>   <td><html:text property="firstPaymentDate" styleId="firstPaymentDate"/></td></tr>
                        </table>
                    </c:when>
                    <%--шинный автоплатеж: первый этап - заполнение полей обычного платежа--%>
                    <c:when test="${form.response.document.form == 'RurPayJurSB'}">
                        <c:set var="document" value="${form.response.document.rurPayJurSBDocument}"/>
                        <%--поля обычного платежа--%>
                        <%@include file="service-payment-document-fields.jsp"%>
                    </c:when>
                    <%--шинный автоплатеж: второй этап - заполнение параметров автоплатежа--%>
                    <c:when test="${form.response.document.form == 'CreateAutoSubscriptionPayment'}">
                        <c:set var="document" value="${form.response.document.createAutoSubscriptionDocument}"/>

                        <%--поля обычного платежа--%>
                        <%@include file="service-payment-document-fields.jsp"%>

                        <c:set var="autoSubParameters" value="${document.autoSubParameters}"/>
                        <tiles:insert page="fields-table.jsp" flush="false">
                            <tiles:put name="data">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="autoSubParameters" beanProperty="name"/>
                                </tiles:insert>
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="autoSubParameters" beanProperty="type"/>
                                    <tiles:put name="onChange" value="changeAutoSubscriptionType(this);"/>
                                </tiles:insert>
                                <c:if test="${not empty autoSubParameters.always}">
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="autoSubParameters" beanProperty="always.eventType"/>
                                        <tiles:put name="tdClass" value="floatField always"/>
                                    </tiles:insert>
                                    <c:if test="${not empty autoSubParameters.always.nextPayDate}">
                                        <tiles:insert page="field.jsp" flush="false">
                                            <tiles:put name="field" beanName="autoSubParameters" beanProperty="always.nextPayDate"/>
                                            <tiles:put name="tdClass" value="floatField always"/>
                                        </tiles:insert>
                                    </c:if>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="autoSubParameters" beanProperty="always.amount"/>
                                        <tiles:put name="tdClass" value="floatField always"/>
                                    </tiles:insert>
                                </c:if>
                                <c:if test="${not empty autoSubParameters.invoice}">
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="autoSubParameters" beanProperty="invoice.eventType"/>
                                        <tiles:put name="tdClass" value="floatField invoice"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="autoSubParameters" beanProperty="invoice.startDate"/>
                                        <tiles:put name="tdClass" value="floatField invoice"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="autoSubParameters" beanProperty="invoice.amount"/>
                                        <tiles:put name="tdClass" value="floatField invoice"/>
                                    </tiles:insert>
                                </c:if>
                                <c:if test="${not empty autoSubParameters.commission}">
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="autoSubParameters" beanProperty="commission"/>
                                    </tiles:insert>
                                </c:if>
                                <c:if test="${not empty autoSubParameters.commissionCurrency}">
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="autoSubParameters" beanProperty="commissionCurrency"/>
                                    </tiles:insert>
                                </c:if>
                                <c:if test="${not empty autoSubParameters.isWithCommission}">
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="autoSubParameters" beanProperty="isWithCommission"/>
                                    </tiles:insert>
                                </c:if>
                            </tiles:put>
                        </tiles:insert>
                    </c:when>
                </c:choose>
            </div>
        </tiles:put>

        <c:if test="${empty confirmType}">
            <tiles:put name="operation" value="next"/>
        </c:if>
    </tiles:insert>

    <c:url var="servicePaymentUrl" value="/atm/createAutoPayment.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
    </c:url>
    <html:link href="${servicePaymentUrl}">Начать сначала</html:link>
</html:form>
<br/>

</body>
</html:html>
