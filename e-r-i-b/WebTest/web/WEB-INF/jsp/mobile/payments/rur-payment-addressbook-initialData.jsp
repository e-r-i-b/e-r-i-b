<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>

<script type="text/javascript">
$(document).ready(function()
{
    var rurPaymentType = document.getElementsByName("receiverSubType")[0];
    changeType(rurPaymentType);
});

// изменнение типа автоплатежа
function changeType(item)
{
    clearFields();

    var receiverSubType = item.options[item.selectedIndex == undefined ? 0 : item.selectedIndex].value;
    $('.'+receiverSubType).show();
    $('.'+receiverSubType+' > td > input').removeAttr('disabled');
    $('.'+receiverSubType+' > td > select').removeAttr('disabled');
}

function clearFields()
{
    $('.floatField').hide();
    $('.floatField > td > input').attr('disabled', 'disabled');
    $('.floatField > td > select').attr('disabled', 'disabled');
}
</script>

<c:set var="initialData" value="${form.response.initialData.newRurPayment}"/>

<tiles:insert page="fields-table.jsp" flush="false">
    <tiles:put name="data">
        <tiles:insert page="field.jsp" flush="false">
            <tiles:put name="field" beanName="initialData" beanProperty="documentDate"/>
        </tiles:insert>
        <tiles:insert page="field.jsp" flush="false">
            <tiles:put name="field" beanName="initialData" beanProperty="documentNumber"/>
        </tiles:insert>
        <tiles:insert page="field.jsp" flush="false">
            <tiles:put name="field" beanName="initialData" beanProperty="receiverSubType"/>
            <tiles:put name="onChange" value="changeType(this);"/>
        </tiles:insert>
        <tiles:insert page="field.jsp" flush="false">
            <tiles:put name="field" beanName="initialData" beanProperty="cardFields.fromResource"/>
            <tiles:put name="tdClass" value="floatField ourCard masterCardExternalCard visaExternalCard otherCard ourContactToOtherCard ourContact ourPhone"/>
        </tiles:insert>
        <tiles:insert page="field.jsp" flush="false">
            <tiles:put name="field" beanName="initialData" beanProperty="cardFields.ourCard.externalCardNumber"/>
            <tiles:put name="tdClass" value="floatField ourCard"/>
        </tiles:insert>
        <c:if test="${not empty initialData.cardFields.masterCardExternalCard}">
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="initialData" beanProperty="cardFields.masterCardExternalCard.externalCardNumber"/>
                <tiles:put name="tdClass" value="floatField masterCardExternalCard"/>
            </tiles:insert>
        </c:if>
        <c:if test="${not empty initialData.cardFields.visaExternalCard}">
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="initialData" beanProperty="cardFields.visaExternalCard.externalCardNumber"/>
                <tiles:put name="tdClass" value="floatField visaExternalCard"/>
            </tiles:insert>
        </c:if>
        <c:if test="${not empty initialData.cardFields.otherCard}">
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="initialData" beanProperty="cardFields.otherCard.externalCardNumber"/>
                <tiles:put name="tdClass" value="floatField otherCard"/>
            </tiles:insert>
        </c:if>
        <c:if test="${not empty initialData.cardFields.ourContactToOtherCard}">
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="initialData" beanProperty="cardFields.ourContactToOtherCard.externalCardNumber"/>
                <tiles:put name="tdClass" value="floatField ourContactToOtherCard"/>
            </tiles:insert>
        </c:if>
        <c:if test="${not empty initialData.cardFields.ourContact}">
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="initialData" beanProperty="cardFields.ourContact.externalCardNumber"/>
                <tiles:put name="tdClass" value="floatField ourContact"/>
            </tiles:insert>
        </c:if>
        <c:if test="${not empty initialData.cardFields.ourPhone.externalPhoneNumber}">
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="initialData" beanProperty="cardFields.ourPhone.externalPhoneNumber"/>
                <tiles:put name="tdClass" value="ourPhone"/>
            </tiles:insert>
        </c:if>
        <c:if test="${not empty initialData.cardFields.messageToReceiver}">
            <tiles:insert page="field.jsp" flush="false">
                <tiles:put name="field" beanName="initialData" beanProperty="cardFields.messageToReceiver"/>
                <tiles:put name="tdClass" value="messageToReceiver"/>
            </tiles:insert>
        </c:if>
        <tiles:insert page="field.jsp" flush="false">
            <tiles:put name="field" beanName="initialData" beanProperty="buyAmount"/>
        </tiles:insert>
        <tiles:insert page="field.jsp" flush="false">
            <tiles:put name="field" beanName="initialData" beanProperty="isCardTransfer"/>
        </tiles:insert>
        <tiles:insert page="field.jsp" flush="false">
            <tiles:put name="field" beanName="initialData" beanProperty="isErrorCurrency"/>
        </tiles:insert>
        <tiles:insert page="field.jsp" flush="false">
            <tiles:put name="field" beanName="initialData" beanProperty="buyAmountCurrency"/>
        </tiles:insert>
        <tiles:insert page="field.jsp" flush="false">
            <tiles:put name="field" beanName="initialData" beanProperty="sellAmount"/>
        </tiles:insert>
        <tiles:insert page="field.jsp" flush="false">
            <tiles:put name="field" beanName="initialData" beanProperty="exactAmount"/>
        </tiles:insert>
        <tiles:insert page="field.jsp" flush="false">
            <tiles:put name="field" beanName="initialData" beanProperty="operationCode"/>
            <tiles:put name="tdClass" value="floatField ourAccount externalAccount"/>
        </tiles:insert>
        <tiles:insert page="field.jsp" flush="false">
            <tiles:put name="field" beanName="initialData" beanProperty="dictField"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
