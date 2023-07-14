<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>

<c:set var="initialData" value="${form.response.initialData.rurPayment}"/>

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
    $('.' + receiverSubType).show();
    $('.' + receiverSubType + ' > td > input').removeAttr('disabled');
    $('.' + receiverSubType + ' > td > select').removeAttr('disabled');
}

function clearFields()
{
   $('.floatField').hide();
    $('.floatField > td > input').attr('disabled', 'disabled');
    $('.floatField > td > select').attr('disabled', 'disabled');
}
</script>

<c:choose>
    <%--если есть initialData, значит это стадия заполнения полей платежа--%>
    <c:when test="${not empty initialData}">
        <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
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
                        <tiles:put name="onChange" value="changeType(this);"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="cardFields.fromResource"/>
                        <tiles:put name="tdClass" value="floatField ourCard ourPhone masterCardExternalCard visaExternalCard"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="cardFields.ourCard.externalCardNumber"/>
                        <tiles:put name="tdClass" value="floatField ourCard"/>
                    </tiles:insert>
                    <c:if test="${not empty initialData.cardFields.ourCard.messageToReceiver}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="initialData" beanProperty="cardFields.ourCard.messageToReceiver"/>
                            <tiles:put name="tdClass" value="floatField ourCard"/>
                        </tiles:insert>
                    </c:if>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="cardFields.ourPhone.externalPhoneNumber"/>
                        <tiles:put name="tdClass" value="floatField ourPhone"/>
                    </tiles:insert>
                    <c:if test="${not empty initialData.cardFields.ourPhone.messageToReceiver}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="initialData" beanProperty="cardFields.ourPhone.messageToReceiver"/>
                            <tiles:put name="tdClass" value="floatField ourPhone"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty initialData.cardFields.masterCardExternalCard}">
                        <script type="text/javascript">alert('masterCardExternalCard '+${initialData.cardFields.masterCardExternalCard});</script>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="initialData" beanProperty="cardFields.masterCardExternalCard.externalCardNumber"/>
                            <tiles:put name="tdClass" value="floatField masterCardExternalCard"/>
                        </tiles:insert>
                    </c:if>
                    <%--<c:if test="${not empty initialData.cardFields.masterCardExternalCard.messageToReceiver}">--%>
                        <%--<tiles:insert page="field.jsp" flush="false">--%>
                            <%--<tiles:put name="field" beanName="initialData" beanProperty="cardFields.masterCardExternalCard.messageToReceiver"/>--%>
                            <%--<tiles:put name="tdClass" value="floatField ourCard"/>--%>
                        <%--</tiles:insert>--%>
                    <%--</c:if>--%>
                    <c:if test="${not empty initialData.cardFields.visaExternalCard}">
                        <script type="text/javascript">alert('visaExternalCard '+${initialData.cardFields.visaExternalCard});</script>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="initialData" beanProperty="cardFields.visaExternalCard.externalCardNumber"/>
                            <tiles:put name="tdClass" value="floatField visaExternalCard"/>
                        </tiles:insert>
                    </c:if>
                    <%--<c:if test="${not empty initialData.cardFields.visaExternalCard.messageToReceiver}">--%>
                        <%--<tiles:insert page="field.jsp" flush="false">--%>
                            <%--<tiles:put name="field" beanName="initialData" beanProperty="cardFields.visaExternalCard.messageToReceiver"/>--%>
                            <%--<tiles:put name="tdClass" value="floatField ourCard"/>--%>
                        <%--</tiles:insert>--%>
                    <%--</c:if>--%>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="accountFields.receiverAccountInternal"/>
                        <tiles:put name="tdClass" value="floatField ourAccount externalAccount"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="accountFields.fromResource"/>
                        <tiles:put name="tdClass" value="floatField ourAccount externalAccount"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="accountFields.receiverSurname"/>
                        <tiles:put name="tdClass" value="floatField ourAccount externalAccount"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="accountFields.receiverFirstName"/>
                        <tiles:put name="tdClass" value="floatField ourAccount externalAccount"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="accountFields.receiverPatrName"/>
                        <tiles:put name="tdClass" value="floatField ourAccount externalAccount"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="accountFields.receiverINN"/>
                        <tiles:put name="tdClass" value="floatField ourAccount externalAccount"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="accountFields.receiverAddress"/>
                        <tiles:put name="tdClass" value="floatField ourAccount externalAccount"/>
                    </tiles:insert>

                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="accountFields.bankInfo.bank"/>
                        <tiles:put name="tdClass" value="floatField ourAccount externalAccount"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="accountFields.bankInfo.receiverBIC"/>
                        <tiles:put name="tdClass" value="floatField ourAccount externalAccount"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="accountFields.bankInfo.receiverCorAccount"/>
                        <tiles:put name="tdClass" value="floatField ourAccount externalAccount"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="accountFields.ground"/>
                        <tiles:put name="tdClass" value="floatField ourAccount externalAccount"/>
                    </tiles:insert>

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
                </tiles:put>
            </tiles:insert>
        </div>
    </c:when>
</c:choose>
