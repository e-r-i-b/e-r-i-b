<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>

<script type="text/javascript">
    var fromResourceFields; //тип ресурса списания (fromCardFields, fromAccountFields)
    var fieldClasses; //имена классов в зависимости от типа ресурса списания

    $(document).ready(function()
    {
        init();
        refreshFromResourceTypes();

        var eventType = document.getElementsByName("longOfferEventType")[0];
        changeEventType(eventType);

        setSumModifyOnKeyDown();
    });

    function init()
    {
        var fromResourceSelect = document.getElementsByName('fromResource')[0];
        var fromResource = fromResourceSelect.options[fromResourceSelect.selectedIndex].value;
        if (fromResource.indexOf('card') != -1)
            fromResourceFields = 'fromCardFields';
        else if (fromResource.indexOf('account') != -1)
            fromResourceFields = 'fromAccountFields';
    }

    //обновление полей в зависимости от типа ресурса списания (fromResource). Нужен однократно на старте
    function refreshFromResourceTypes()
    {
        if (fromResourceFields == 'fromAccountFields')
            $('.fromCardFields').remove();
        else if (fromResourceFields == 'fromCardFields')
            $('.fromAccountFields').remove();
    }

    //изменение типа исполнения автоплатежа (eventType)
    function changeEventType(select)
    {
        hideFields('changedByEventType');

        var eventType = select.options[select.selectedIndex == undefined ? 0 : select.selectedIndex].value;
        $('.'+eventType).show();
        $('.'+eventType+' > td > input').removeAttr('disabled');
        $('.'+eventType+' > td > select').removeAttr('disabled');

        var sumType = $('.'+eventType+' > td > select[name=longOfferSumType]').get(0);
        changeSumType(sumType);
    }

    //изменение типа суммы автоплатежа (sumType)
    function changeSumType(select)
    {
        hideFields('changedBySumType');

        var sumType = select.options[select.selectedIndex == undefined ? 0 : select.selectedIndex].value;
        $('.'+sumType).show();
        $('.'+sumType+' > td > input').removeAttr('disabled');
    }

    function hideFields(className)
    {
        $('.'+className).hide();
        $('.'+className+' > td > input').attr('disabled', 'disabled');
        $('.'+className+'  > td > select').attr('disabled', 'disabled');
    }

    //установить событие: при вводе символов в поля с суммами взводить флаг isSumModify
    function setSumModifyOnKeyDown()
    {
        $('.changedBySumType > td > input').keydown(function() {
            $('input[name=isSumModify]').val('true');
        });
    }
</script>

<html:html>
<head>
    <title>Создание автоплатежа перевода между своими счетами</title>
</head>

<body>
<h1>Создание автоплатежа перевода между своими счетами: сохранение заявки</h1>

<html:form action="/atm/internalPaymentLongOffer" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address"   value="/private/payments/payment.do"/>
        <tiles:put name="operation" value="save"/>

        <tiles:put name="data">
            <c:set var="initialData" value="${form.response.initialData.internalPaymentLongOffer}"/>
            <tiles:insert page="fields-table.jsp" flush="false">
                <tiles:put name="data">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="documentNumber"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="documentDate"/>
                    </tiles:insert>

                    <c:set var="paymentDetails" value="${initialData.paymentDetails}"/>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="paymentDetails" beanProperty="fromResource"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="paymentDetails" beanProperty="toResource"/>
                    </tiles:insert>
                    <c:if test="${not empty paymentDetails.admissionDate}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="paymentDetails" beanProperty="admissionDate"/>
                        </tiles:insert>
                    </c:if>

                    <c:set var="longOfferDetails" value="${initialData.longOfferDetails}"/>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="longOfferDetails" beanProperty="startDate"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="longOfferDetails" beanProperty="endDate"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="longOfferDetails" beanProperty="firstPaymentDate"/>
                    </tiles:insert>

                    <c:set var="fromAccountFields" value="${longOfferDetails.fromAccountFields}"/>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromAccountFields" beanProperty="eventType"/>
                        <tiles:put name="tdClass" value="fromAccountFields"/>
                        <tiles:put name="onChange" value="changeEventType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromAccountFields" beanProperty="periodic.payDay"/>
                        <tiles:put name="tdClass" value="changedByEventType fromAccountFields ONCE_IN_MONTH ONCE_IN_QUARTER ONCE_IN_HALFYEAR ONCE_IN_YEAR"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromAccountFields" beanProperty="periodic.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType fromAccountFields ONCE_IN_MONTH ONCE_IN_QUARTER ONCE_IN_HALFYEAR ONCE_IN_YEAR"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromAccountFields" beanProperty="byAnyReceipt.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType fromAccountFields BY_ANY_RECEIPT"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromAccountFields" beanProperty="onRemaind.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType fromAccountFields ON_REMAIND"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromAccountFields" beanProperty="byCapital.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType fromAccountFields BY_CAPITAL"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromAccountFields" beanProperty="byPercent.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType fromAccountFields BY_PERCENT"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>

                    <c:set var="fromCardFields" value="${longOfferDetails.fromCardFields}"/>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromCardFields" beanProperty="eventType"/>
                        <tiles:put name="tdClass" value="fromCardFields"/>
                        <tiles:put name="onChange" value="changeEventType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromCardFields" beanProperty="periodic.payDay"/>
                        <tiles:put name="tdClass" value="changedByEventType fromCardFields ONCE_IN_MONTH ONCE_IN_QUARTER ONCE_IN_HALFYEAR ONCE_IN_YEAR"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromCardFields" beanProperty="periodic.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType fromCardFields ONCE_IN_MONTH ONCE_IN_QUARTER ONCE_IN_HALFYEAR ONCE_IN_YEAR"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromCardFields" beanProperty="byAnyReceipt.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType fromCardFields BY_ANY_RECEIPT"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromCardFields" beanProperty="onRemaind.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType fromCardFields ON_REMAIND"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromCardFields" beanProperty="bySalary.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType fromCardFields BY_SALARY"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromCardFields" beanProperty="byPension.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType fromCardFields BY_PENSION"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>

                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="longOfferDetails" beanProperty="priority"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="longOfferDetails" beanProperty="isSumModify"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="longOfferDetails" beanProperty="sellAmountFields.sellAmount"/>
                        <tiles:put name="tdClass" value="changedBySumType FIXED_SUMMA REMAIND_OVER_SUMMA"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="longOfferDetails" beanProperty="sellAmountFields.exactAmount"/>
                        <tiles:put name="tdClass" value="changedBySumType FIXED_SUMMA REMAIND_OVER_SUMMA"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="longOfferDetails" beanProperty="buyAmountFields.buyAmount"/>
                        <tiles:put name="tdClass" value="changedBySumType FIXED_SUMMA_IN_RECIP_CURR REMAIND_IN_RECIP"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="longOfferDetails" beanProperty="buyAmountFields.exactAmount"/>
                        <tiles:put name="tdClass" value="changedBySumType FIXED_SUMMA_IN_RECIP_CURR REMAIND_IN_RECIP"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="longOfferDetails" beanProperty="percentFields.percent"/>
                        <tiles:put name="tdClass" value="changedBySumType PERCENT_OF_REMAIND"/>
                    </tiles:insert>

                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
</body>
</html:html>