<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>

<script type="text/javascript">
    var receiverSubType; //тип счета получателя (ourAccount, externalAccount)
    var fromResourceFields; //тип ресурса списания (fromCardFields, fromAccountFields)

    $(document).ready(function()
    {
        init();
        refreshFromAndToResourceTypes();

        var eventType = document.getElementsByName("longOfferEventType")[0];
        changeEventType(eventType);

        setSumModifyOnKeyDown();
    });

    function init()
    {
        receiverSubType = document.getElementsByName('receiverSubType')[0].value;
        var fromResourceSelect = document.getElementsByName('fromResource')[0];
        var fromResource = fromResourceSelect.options[fromResourceSelect.selectedIndex].value;
        if (fromResource.indexOf('card') != -1)
            fromResourceFields = 'fromCardFields';
        else if (fromResource.indexOf('account') != -1)
            fromResourceFields = 'fromAccountFields';
    }

    //обновление полей в зависимости от типа счета получателя (receiverSubType) и типа ресурса списания (fromResource). Нужен однократно на старте
    function refreshFromAndToResourceTypes()
    {
        if (receiverSubType == 'ourAccount')
            $('.externalAccount').remove();
        else if (receiverSubType == 'externalAccount')
            $('.ourAccount').remove();
        else
            alert("Некорректный тип receiverSubType: [" + receiverSubType + "]. Длительное поручение можно создать только на вклад (ourAccount или externalAccount).");

        if (fromResourceFields == 'fromAccountFields')
            $('.fromCardFields').remove();
        else if (fromResourceFields == 'fromCardFields')
            $('.fromAccountFields').remove();
        else
            alert("Некорректный тип ресурса списания: [" + fromResourceFields + "]");
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
    <title>Создание автоплатежа перевода частному лицу</title>
</head>

<body>
<h1>Подключение автоплатежа</h1>

<html:form action="/mobileApi/rurPaymentLongOffer" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>
        <tiles:put name="operation" value="save"/>

        <tiles:put name="data">
            <c:set var="initialData" value="${form.response.initialData.rurPaymentLongOffer}"/>
            <tiles:insert page="fields-table.jsp" flush="false">
                <tiles:put name="data">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="documentNumber"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="documentDate"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="receiverSubType"/>
                    </tiles:insert>
                    
                    <c:set var="receiver" value="${initialData.receiver}"/>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="receiver" beanProperty="account"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="receiver" beanProperty="accountCurrency"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="receiver" beanProperty="surname"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="receiver" beanProperty="firstName"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="receiver" beanProperty="patrName"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="receiver" beanProperty="inn"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="receiver" beanProperty="address"/>
                    </tiles:insert>

                    <c:set var="bank" value="${initialData.bank}"/>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="bank" beanProperty="name"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="bank" beanProperty="bic"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="bank" beanProperty="corAccount"/>
                    </tiles:insert>

                    <c:set var="paymentDetails" value="${initialData.paymentDetails}"/>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="paymentDetails" beanProperty="ground"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="paymentDetails" beanProperty="fromResource"/>
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

                    <c:set var="fromAccountToOurAccount" value="${longOfferDetails.ourAccount.fromAccountFields}"/>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromAccountToOurAccount" beanProperty="eventType"/>
                        <tiles:put name="tdClass" value="ourAccount fromAccountFields"/>
                        <tiles:put name="onChange" value="changeEventType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromAccountToOurAccount" beanProperty="periodic.payDay"/>
                        <tiles:put name="tdClass" value="changedByEventType ourAccount fromAccountFields ONCE_IN_MONTH ONCE_IN_QUARTER ONCE_IN_HALFYEAR ONCE_IN_YEAR"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromAccountToOurAccount" beanProperty="periodic.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType ourAccount fromAccountFields ONCE_IN_MONTH ONCE_IN_QUARTER ONCE_IN_HALFYEAR ONCE_IN_YEAR"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromAccountToOurAccount" beanProperty="byAnyReceipt.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType ourAccount fromAccountFields BY_ANY_RECEIPT"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromAccountToOurAccount" beanProperty="onRemaind.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType ourAccount fromAccountFields ON_REMAIND"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromAccountToOurAccount" beanProperty="onOverDraft.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType ourAccount fromAccountFields ON_OVER_DRAFT"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromAccountToOurAccount" beanProperty="byCapital.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType ourAccount fromAccountFields BY_CAPITAL"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromAccountToOurAccount" beanProperty="byPercent.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType ourAccount fromAccountFields BY_PERCENT"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>

                    <c:set var="fromCardToOurAccount" value="${longOfferDetails.ourAccount.fromCardFields}"/>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromCardToOurAccount" beanProperty="eventType"/>
                        <tiles:put name="tdClass" value="ourAccount fromCardFields"/>
                        <tiles:put name="onChange" value="changeEventType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromCardToOurAccount" beanProperty="periodic.payDay"/>
                        <tiles:put name="tdClass" value="changedByEventType ourAccount fromCardFields ONCE_IN_MONTH ONCE_IN_QUARTER ONCE_IN_HALFYEAR ONCE_IN_YEAR"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromCardToOurAccount" beanProperty="periodic.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType ourAccount fromCardFields ONCE_IN_MONTH ONCE_IN_QUARTER ONCE_IN_HALFYEAR ONCE_IN_YEAR"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromCardToOurAccount" beanProperty="byAnyReceipt.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType ourAccount fromCardFields BY_ANY_RECEIPT"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromCardToOurAccount" beanProperty="onRemaind.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType ourAccount fromCardFields ON_REMAIND"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromCardToOurAccount" beanProperty="onOverDraft.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType ourAccount fromCardFields ON_OVER_DRAFT"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromCardToOurAccount" beanProperty="bySalary.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType ourAccount fromCardFields BY_SALARY"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromCardToOurAccount" beanProperty="byPension.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType ourAccount fromCardFields BY_PENSION"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>

                    <c:set var="fromAccountToExternalAccount" value="${longOfferDetails.externalAccount.fromAccountFields}"/>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromAccountToExternalAccount" beanProperty="eventType"/>
                        <tiles:put name="tdClass" value="externalAccount fromAccountFields"/>
                        <tiles:put name="onChange" value="changeEventType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromAccountToExternalAccount" beanProperty="periodic.payDay"/>
                        <tiles:put name="tdClass" value="changedByEventType externalAccount fromAccountFields ONCE_IN_MONTH ONCE_IN_QUARTER ONCE_IN_HALFYEAR ONCE_IN_YEAR"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromAccountToExternalAccount" beanProperty="periodic.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType externalAccount fromAccountFields ONCE_IN_MONTH ONCE_IN_QUARTER ONCE_IN_HALFYEAR ONCE_IN_YEAR"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromAccountToExternalAccount" beanProperty="byAnyReceipt.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType externalAccount fromAccountFields BY_ANY_RECEIPT"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromAccountToExternalAccount" beanProperty="onRemaind.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType externalAccount fromAccountFields ON_REMAIND"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromAccountToExternalAccount" beanProperty="byCapital.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType externalAccount fromAccountFields BY_CAPITAL"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromAccountToExternalAccount" beanProperty="byPercent.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType externalAccount fromAccountFields BY_PERCENT"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>

                    <c:set var="fromCardToExternalAccount" value="${longOfferDetails.externalAccount.fromCardFields}"/>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromCardToExternalAccount" beanProperty="eventType"/>
                        <tiles:put name="tdClass" value="externalAccount fromCardFields"/>
                        <tiles:put name="onChange" value="changeEventType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromCardToExternalAccount" beanProperty="periodic.payDay"/>
                        <tiles:put name="tdClass" value="changedByEventType externalAccount fromCardFields ONCE_IN_MONTH ONCE_IN_QUARTER ONCE_IN_HALFYEAR ONCE_IN_YEAR"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromCardToExternalAccount" beanProperty="periodic.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType externalAccount fromCardFields ONCE_IN_MONTH ONCE_IN_QUARTER ONCE_IN_HALFYEAR ONCE_IN_YEAR"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromCardToExternalAccount" beanProperty="byAnyReceipt.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType externalAccount fromCardFields BY_ANY_RECEIPT"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromCardToExternalAccount" beanProperty="onRemaind.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType externalAccount fromCardFields ON_REMAIND"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromCardToExternalAccount" beanProperty="bySalary.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType externalAccount fromCardFields BY_SALARY"/>
                        <tiles:put name="onChange" value="changeSumType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="fromCardToExternalAccount" beanProperty="byPension.sumType"/>
                        <tiles:put name="tdClass" value="changedByEventType externalAccount fromCardFields BY_PENSION"/>
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
