<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:html>
<head>
    <title>Редактирование автоплатежа (P2P)</title>
</head>

<body>


<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>

<script type="text/javascript">
$(document).ready(function()
{
    clearFields();
    var exist = document.getElementsByName("externalPhoneNumber")[0];
    if (exist && exist.value && 0 !== exist.value.length) {
        $('select[name=receiverType]').val('ph');
        $('select[name=receiverSubType]').val('ourPhone');
        $('.ph.ourPhone').show();
        $('.ph.ourPhone > td > input').removeAttr('disabled');
        $('.ph.ourPhone > td > select').removeAttr('disabled');
        return;
    }
    exist = document.getElementsByName("externalCardNumber")[0];
    if (exist && exist.value && 0 !== exist.value.length) {
        $('select[name=receiverType]').val('ph');
        $('select[name=receiverSubType]').val('ourCard');
        $('.ph.ourCard').show();
        $('.ph.ourCard > td > input').removeAttr('disabled');
        $('.ph.ourCard > td > select').removeAttr('disabled');
        return;
    }
    var paymentType = document.getElementsByName("receiverType")[0];
    changeType(paymentType);
});

function changeType(item)
{
    clearFields();

    var selected = item.options[item.selectedIndex == undefined ? 0 : item.selectedIndex].value;

    if (selected == 'ph') {
        $('select[name=receiverSubType]').val('ourCard');
        $('.ph.ourCard').show();
        $('.ph.ourCard' + ' > td > input').removeAttr('disabled');
        $('.ph.ourCard' + ' > td > select').removeAttr('disabled');
    }
    else {
        $('select[name=receiverSubType]').val('severalCard');
        $('.several.severalCard').show();
        $('.several.severalCard' + ' > td > input').removeAttr('disabled');
        $('.several.severalCard' + ' > td > select').removeAttr('disabled');
    }
}

function changeSubType(item)
{
    clearFields();

    var selected = item.options[item.selectedIndex == undefined ? 0 : item.selectedIndex].value;

    if (selected == 'ourPhone' || selected == 'ourCard') {
        $('select[name=receiverType]').val('ph');
        $('.ph.' + selected).show();
        $('.ph.' + selected + ' > td > input').removeAttr('disabled');
        $('.ph.' + selected + ' > td > select').removeAttr('disabled');
    }
    else {
        $('select[name=receiverType]').val('several');
        $('.several.severalCard').show();
        $('.several.severalCard' + ' > td > input').removeAttr('disabled');
        $('.several.severalCard' + ' > td > select').removeAttr('disabled');
    }
}

function clearFields()
{
    $('.floatField').hide();
    $('.floatField > td > input').attr('disabled', 'disabled');
    $('.floatField > td > select').attr('disabled', 'disabled');
}
</script>


<h1>Редактирование автоплатежа (P2P): сохранение</h1>

<html:form action="/mobileApi/editAutoTransferPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>
        <tiles:put name="formName" value="EditP2PAutoTransferClaim"/>
        <tiles:put name="operation" value="save"/>

        <tiles:put name="data">
            <c:set var="initialData" value="${form.response.initialData.editP2PAutoTransferClaim}"/>
            <c:set var="receiver" value="${initialData.receiver}"/>
            <c:set var="autoSubDetails" value="${initialData.autoSubDetails}"/>
            <c:set var="always" value="${autoSubDetails.always}"/>
            <tiles:insert page="fields-table.jsp" flush="false">
                <tiles:put name="data">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="number"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="fromResource"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="receiver" beanProperty="toResource"/>
                        <tiles:put name="tdClass" value="floatField several severalCard"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="receiver" beanProperty="externalPhoneNumber"/>
                        <tiles:put name="tdClass" value="floatField ph ourPhone"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="receiver" beanProperty="externalCardNumber"/>
                        <tiles:put name="tdClass" value="floatField ph ourCard"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="receiver" beanProperty="receiverType"/>
                        <tiles:put name="onChange" value="changeType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="receiver" beanProperty="receiverSubType"/>
                        <tiles:put name="onChange" value="changeSubType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="messageToReceiver"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="always" beanProperty="longOfferEventType"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="autoSubDetails" beanProperty="autoSubName"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="autoSubDetails" beanProperty="autoSubType"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="always" beanProperty="sellAmount"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="autoSubDetails" beanProperty="longOfferStartDate"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
</body>
</html:html>