<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Создание автоперевода P2P между моими картами</title>
</head>

<body>

<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>

<script type="text/javascript">
$(document).ready(function()
{
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

<h1>Создание автоплатежа P2P</h1>

<html:form action="/mobileApi/createP2PAutoTransfer" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do?form=CreateP2PAutoTransferClaim"/>
        <tiles:put name="formName" value="CreateP2PAutoTransferClaim"/>
        <tiles:put name="operation" value="makeAutoTransfer"/>

        <tiles:put name="data">
            <c:set var="initialData" value="${form.response.initialData.createP2PAutoTransferClaim}"/>
            <c:set var="receiver" value="${initialData.receiver}"/>
            <tiles:insert page="fields-table.jsp" flush="false">
                <tiles:put name="data">
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
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>
