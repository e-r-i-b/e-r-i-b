<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/persons/basket/subscription/view" onsubmit="return setEmptyAction();">

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="invoice" value="${form.invoiceSubscription}"/>
<c:set var="state" value="${invoice.state}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="providerIcon" value="${form.providerIcon}"/>
<c:set var="accountingEntityName" value="${form.accountingEntityName}"/>
<c:set var="isRecoveryAvailable" value="${state == 'STOPPED'}"/>
<c:set var="isEmptyAutoPayId" value="${invoice.autoPayId == null || invoice.autoPayId == ''}"/>
<c:set var="showButtons" value="${!isEmptyAutoPayId && (state == 'ACTIVE' || state == 'STOPPED')}"/>

<c:choose>
    <c:when test="${not empty providerIcon}">
        <c:set var="image" value="${phiz:getAddressImage(providerIcon, pageContext)}"/>
    </c:when>
    <c:otherwise>
        <c:set var="image" value="${imagePath}/defaultProviderIcon.jpg"/>
    </c:otherwise>
</c:choose>

<tiles:insert definition="personEdit">
<tiles:put name="leftMenu" type="string" value=""/>
<tiles:put name="needSave" value="false"/>
<tiles:put name="pageTitle" value="Просмотр услуги"/>
<tiles:put name="data" type="string">

<div class="invoiceSubscription">
<html:hidden property="id"/>
<div>
    <h1 class="fixInvoicePaymentWidth"><c:out value="${invoice.recName}"/></h1>
    <img src="${image}" border="0" class="floatRight"/>
</div>
<div class="clear"></div>
<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title" value="Услуга:"/>
    <tiles:put name="data">
        <c:out value="${invoice.nameService}"/>
    </tiles:put>
</tiles:insert>

<c:set var="requisites" value="${invoice.requisitesAsList}"/>
<c:set var="isExistNotKeyField" value="false"/>
<div id="keyFields">
    <c:forEach var="keyField" items="${requisites}">
        <c:if test="${keyField.visible}">
            <c:choose>
                <c:when test="${keyField.key}">
                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title" value="${keyField.name}"/>
                        <tiles:put name="data">
                            <c:out value="${phiz:getMaskedValue(keyField.mask, keyField.value)}"/>
                        </tiles:put>
                    </tiles:insert>
                </c:when>
                <c:otherwise>
                    <c:set var="isExistNotKeyField" value="true"/>
                </c:otherwise>
            </c:choose>
        </c:if>
    </c:forEach>
</div>

<c:if test="${isExistNotKeyField}">
    <div id="notKeyFieldOpen">
        <div class="notKeyFieldShow" onclick="hideOrShowNotKeyFields(true);">
            <span class="blueGrayLinkDotted">Показать полные реквизиты</span>
        </div>
    </div>

    <div id="notKeyFieldsHide" style="display: none">
        <div class="notKeyFieldHide" onclick="hideOrShowNotKeyFields();">
            <span class="blueGrayLinkDotted">Скрыть полные реквизиты</span>
        </div>
        <c:forEach var="notKeyField" items="${requisites}">
            <c:if test="${not notKeyField.key && notKeyField.visible}">
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title" value="${notKeyField.name}"/>
                    <tiles:put name="data">
                        <c:out value="${phiz:getMaskedValue(notKeyField.mask, notKeyField.value)}"/>
                    </tiles:put>
                </tiles:insert>
            </c:if>
        </c:forEach>
    </div>
</c:if>

<div class="greyDottedLine"></div>
<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title" value="Счет списания:"/>
    <tiles:put name="data">
        <c:set var="cardNumber" value="${invoice.chargeOffCard}"/>
        <c:set var="cardLink" value="${phiz:getCardLink(cardNumber)}"/>
        <c:set var="card" value="${cardLink.value}"/>
        <c:out value="${phiz:getCutCardNumber(cardNumber)}"/>&nbsp;
        [<c:out value="${phiz:getCardUserName(cardLink)}"/>]&nbsp;
        <c:if test="${not empty card.availableLimit}">
            <c:out value="${phiz:formatAmount(card.availableLimit)}"/>
        </c:if>
    </tiles:put>
</tiles:insert>

<c:set var="serviceCategory" value="${phiz:getServiceCategoryByCode(invoice.codeService)}"/>
<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title" value="Название выставляемого счета:"/>
    <tiles:put name="data">
        <c:choose>
            <c:when test="${not empty serviceCategory}">
                <c:out value="${serviceCategory.accountName}"/>
            </c:when>
            <c:otherwise>
                Другие услуги
            </c:otherwise>
        </c:choose>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title" value="Группа:"/>
    <tiles:put name="data">
        <c:choose>
            <c:when test="${not empty accountingEntityName}">
                <c:out value="${accountingEntityName}"/>
            </c:when>
            <c:otherwise>
                Другие услуги
            </c:otherwise>
        </c:choose>
    </tiles:put>
</tiles:insert>

<div class="formRowMargin">
    Счета по данной услуге выставляются
    <div>
        <c:out value="${phiz:createInvoiceInfo(invoice)}"/>
    </div>
</div>

<c:set var="errorInfo" value="${invoice.errorInfo}"/>
<c:if test="${errorInfo != null && errorInfo.type == 'FORM'}">
    <div class="invoice-sub-error">
        <c:out value="${errorInfo.text}"/>
    </div>
    <div class="clear"></div>
</c:if>

<c:if test="${empty invoice.confirmStrategyType && showButtons}">
    <div class="buttonsArea iebuttons" id="confirmButtonBlock">
        <tiles:insert definition="clientButton" flush="false" service="PaymentBasketManagment" operation="ConfirmInvoiceSubscriptionOperation">
            <tiles:put name="commandTextKey" value="button.confirmSMS"/>
            <tiles:put name="commandHelpKey" value="button.confirmSMS"/>
            <tiles:put name="bundle" value="securityBundle"/>
            <tiles:put name="onclick" value=""/>
        </tiles:insert>
    </div>
</c:if>

<div class="buttonsArea iebuttons">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.cancel"/>
        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
        <tiles:put name="bundle" value="basketBundle"/>
        <tiles:put name="action" value="/persons/basket/editbasket.do?person=${form.personId}"/>
        <tiles:put name="viewType" value="buttonGrey"/>
    </tiles:insert>
    <c:set var="editUrl" value="/private/basket/subscription/edit.do"/>
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.edit"/>
        <tiles:put name="commandHelpKey" value="button.edit.help"/>
        <tiles:put name="bundle" value="commonBundle"/>
        <tiles:put name="enabled" value="false"/>
    </tiles:insert>

        <c:set var="operationUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/basket/subscription/claim')}"/>
        <div class="invoiceSubscriptionOperations">

            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.recoverySub"/>
                <tiles:put name="commandHelpKey" value="button.recoverySub.help"/>
                <tiles:put name="bundle" value="basketBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="enabled" value="false"/>
            </tiles:insert>

            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.delaySub"/>
                <tiles:put name="commandHelpKey" value="button.delaySub.help"/>
                <tiles:put name="bundle" value="basketBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="onclick" value=""/>
				<tiles:put name="enabled" value="false"/>
            </tiles:insert>

            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.autoSub"/>
                <tiles:put name="commandHelpKey" value="button.autoSub.help"/>
                <tiles:put name="bundle" value="basketBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="enabled" value="false"/>
            </tiles:insert>

            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.remove"/>
                <tiles:put name="commandHelpKey" value="button.remove"/>
                <tiles:put name="bundle" value="basketBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="enabled" value="false"/>
            </tiles:insert>
        </div>
</div>

<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="oneTimePasswordWindow"/>
</tiles:insert>

<script type="text/javascript">
    function hideOrShowNotKeyFields(display)
    {
        if (display)
        {
            $('#notKeyFieldOpen').hide();
            $('#notKeyFieldsHide').show();
        }
        else
        {
            $('#notKeyFieldOpen').show();
            $('#notKeyFieldsHide').hide();
        }
    }

    function doEdit()
    {
        var url = "${phiz:calculateActionURL(pageContext, editUrl)}";
        window.location = url + "?id=${invoice.id}";
    }

    <%--Подтверждение операции--%>
    function clickConfirmButton(formName)
    {
        if (confirmOperation != undefined)
        {
            confirmOperation.openFormConfirmWindow('button.confirmSMS&formName=' + formName, '${operationUrl}');
        }
    }

    function clickConfirmSubscriptionButton()
    {
        if (confirmOperation != undefined)
        {
            <c:set var="confirmUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/basket/subscription/confirm')}"/>
            confirmOperation.openFormConfirmWindow('button.confirmSMS', '${confirmUrl}');
        }
    }
</script>
</div>


</tiles:put>
</tiles:insert>

</html:form>