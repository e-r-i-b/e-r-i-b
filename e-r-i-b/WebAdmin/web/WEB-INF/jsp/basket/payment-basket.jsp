<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/persons/basket/editbasket">
<c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>
<tiles:importAttribute/>
<c:set var="editFlatUrl" value="${phiz:calculateActionURL(pageContext,'/private/async/basket/accounting/editFlat')}"/>
<c:set var="editHouseUrl" value="${phiz:calculateActionURL(pageContext,'/private/async/basket/accounting/editHouse')}"/>
<c:set var="editGarageUrl" value="${phiz:calculateActionURL(pageContext,'/private/async/basket/accounting/editGarage')}"/>
<c:set var="editCarUrl" value="${phiz:calculateActionURL(pageContext,'/private/async/basket/accounting/editCar')}"/>
<c:set var="newBillsToPay" value="false"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<tiles:insert definition="personEdit">
<tiles:put name="submenu" type="string" value="EditBasket"/>
<tiles:put name="needSave" value="false"/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="commonImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="helpLink" value="${phiz:calculateActionURL(pageContext,'/help.do?id=/private/receivers/list')}"/>
<c:set var="faqLink" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'q19')}"/>
<c:set var="faqLinkPersonal" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'm19')}"/>
<c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/userprofile/basket')}"/>
<c:set var="viewSubscriptionUrl" value="${phiz:calculateActionURL(pageContext, '/persons/basket/subscription/view.do')}"/>
<c:set var="viewDocumentUrl" value="${phiz:calculateActionURL(pageContext, '/private/payments/default-action.do')}"/>
<c:set var="viewAutoPaymentUrl" value="${phiz:calculateActionURL(pageContext, '/private/autosubscriptions/info.do')}"/>
<c:set var="operationUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/basket/subscription/claim')}"/>
<c:set var="autoCreateUrl" value="${phiz:calculateActionURL(pageContext, '/private/userprofile/basket/subscription/create')}"/>

<tiles:put name="data" type="string">
<script type="text/javascript">
    function resolveInvoiceSubError(autoSubId, type)
    {
        ajaxQuery(
                "id=" + autoSubId + "&operation=" + type,
                "${operationUrl}",
                function (data)
                {
                    if (data.state == 'success')
                    {
                        if (data.refresh)
                        {
                            window.location.reload();
                            return;
                        }

                        $("#error-buttons-" + autoSubId).hide();
                        $("#invoiceSubscription" + autoSubId + " .is-statusError").empty();
                    }
                    else if (data.state == 'fail')
                    {
                        if (data.errors != null && data.errors.length > 0)
                        {
                            for (var i = 0; i < data.errors.length; i++)
                                addError(data.errors[i]);
                        }
                    }
                }, "json");
    }

    function removeAutoInvoiceSub(event, autoSubId)
    {
        cancelEvent(event);
        resolveInvoiceSubError(autoSubId, "button.remove");
    }

    function autoCreate(id)
    {
        window.location = '${autoCreateUrl}?autoId=' + id;
    }

    function recoverySubscription(id)
    {
        if (confirmOperation != undefined)
        {
            confirmOperation.openFormConfirmWindow('button.confirmSMS&formName=RecoveryInvoiceSubscriptionClaim&id=' + id, '${operationUrl}');
        }
    }
</script>


<c:set var="invoiceWinId" value="objectEntityDiv"/>

<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="${invoiceWinId}"/>
    <tiles:put name="loadAjaxUrl" value="${editHouseUrl}"/>
</tiles:insert>
<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="oneTimePasswordWindow"/>
</tiles:insert>

<div>
    <span class="Title floatLeft">
        Автопоиск счетов к оплате
    </span>
    <span class="il-beta floatLeft">
        &#946;
    </span>
</div>
<div class="clear"></div>

<ul class="pb-entitiesList">
    <c:forEach items="${form.accountingEntities}" var="item">
        <li class="pb-entitiesListElem">
            <tiles:insert definition="serviceGroup" flush="false">
                <tiles:put name="name" value="${item.name}"/>
                <tiles:put name="type" value="${item.type}"/>
                <tiles:put name="id" value="${item.id}"/>
            </tiles:insert>
        </li>
    </c:forEach>
</ul>
<div class="clear"></div>
<div id="otherServices" class="pb-otherServices">
    Другие услуги
</div>

<div class="sortableWrapper">
    <ul class="activeList pb-subscriptionsList" name="subListNone">
        <div class="pb-listTitle">
            Автопоиск ищет счета по услугам
        </div>
        <c:forEach items="${form.activeSubscriptions['none']}" var="subscription">
            <c:set var="state" value="${subscription.state}"/>
            <c:choose>
                <c:when test="${state == 'FAKE_SUBSCRIPTION_PAYMENT'}">
                    <c:set var="nextState" value=""/>
                </c:when>
                <c:otherwise>
                    <c:set var="nextState" value="${subscription.nextState}"/>
                </c:otherwise>
            </c:choose>
            <tiles:insert definition="invoiceSubscription" flush="false">
                <tiles:put name="topTitle" value=""/>
                <tiles:put name="type" value=""/>
                <tiles:put name="state" value="${state}"/>
                <tiles:put name="nextState" value="${nextState}"/>
                <tiles:put name="name" value="${subscription.name}"/>
                <tiles:put name="provider" value="${subscription.recName}"/>
                <tiles:put name="id" value="${subscription.id}"/>
                <tiles:put name="errorType" value="${subscription.errorType}"/>
                <tiles:put name="statusMessage" value="${subscription.errorInfo == null || subscription.errorInfo.type != 'LIST' ? '' : subscription.errorInfo.text}"/>
                <tiles:put name="notPaid" value="${subscription.numberOfNotPaidInvoices}"/>
                <tiles:put name="delayed" value="${subscription.numberOfDelayedInvoices}"/>
                <tiles:put name="date" value="${phiz:formatDayWithStringMonth(subscription.delayDate)}"/>
                <tiles:put name="imageId" value="${form.imageIds[id]}"/>
            </tiles:insert>
        </c:forEach>
        <c:forEach items="${form.autoSubscriptions}" var="subscription">
            <tiles:insert definition="invoiceSubscription" flush="false">
                <tiles:put name="topTitle" value=""/>
                <tiles:put name="type" value="is-autoPayment"/>
                <tiles:put name="state" value="AUTO_SUBSCRIPTION"/>
                <tiles:put name="nextState" value=""/>
                <tiles:put name="name" value="${subscription.value.friendlyName}"/>
                <tiles:put name="provider" value="${subscription.serviceProvider.name}"/>
                <tiles:put name="id" value="${form.detailInfo[subscription.value.number].number}"/>
                <tiles:put name="errorType" value=""/>
                <tiles:put name="statusMessage" value=""/>
                <tiles:put name="notPaid" value=""/>
                <tiles:put name="delayed" value=""/>
                <tiles:put name="date" value=""/>
                <tiles:put name="imageId" value="${subscription.serviceProvider.imageId}"/>
            </tiles:insert>
        </c:forEach>
    </ul>
</div>
<ul class="stoppedList pb-subscriptionsList" name="subListNone">
    <div class="pb-listTitle">
        <bean:message key="delayed.subscription.block.title" bundle="basketBundle"/>
    </div>
    <c:forEach items="${form.stoppedSubscriptions['none']}" var="subscription">
        <c:set var="state" value="${subscription.state}"/>
        <c:choose>
            <c:when test="${state == 'FAKE_SUBSCRIPTION_PAYMENT'}">
                <c:set var="nextState" value=""/>
            </c:when>
            <c:otherwise>
                <c:set var="nextState" value="${subscription.nextState}"/>
            </c:otherwise>
        </c:choose>
        <tiles:insert definition="invoiceSubscription" flush="false">
            <tiles:put name="topTitle" value=""/>
            <tiles:put name="type" value=""/>
            <tiles:put name="name" value="${subscription.name}"/>
            <tiles:put name="state" value="${state}"/>
            <tiles:put name="nextState" value="${nextState}"/>
            <tiles:put name="provider" value="${subscription.recName}"/>
            <tiles:put name="id" value="${subscription.id}"/>
            <tiles:put name="errorType" value="${subscription.errorType}"/>
            <tiles:put name="statusMessage" value="${subscription.errorInfo == null || subscription.errorInfo.type != 'LIST' ? '' : subscription.errorInfo.text}"/>
            <tiles:put name="notPaid" value="${subscription.numberOfNotPaidInvoices}"/>
            <tiles:put name="delayed" value="${subscription.numberOfDelayedInvoices}"/>
            <tiles:put name="imageId" value="${form.imageIds[id]}"/>
            <c:if test="${not empty subscription.documentStatus}">
                <tiles:put name="documentStatus" value="${subscription.documentStatus}"/>
            </c:if>
        </tiles:insert>
    </c:forEach>
</ul>
<ul class="recommendedList pb-subscriptionsList" name="subListNone">
    <div class="pb-listTitle">
        <bean:message key="recommended.subscription.block.title" bundle="basketBundle"/>
    </div>
    <c:forEach items="${form.recommendedSubscriptions['none']}" var="subscription">
        <c:set var="state" value="${subscription.state}"/>
        <c:choose>
            <c:when test="${state == 'FAKE_SUBSCRIPTION_PAYMENT'}">
                <c:set var="nextState" value=""/>
            </c:when>
            <c:otherwise>
                <c:set var="nextState" value="${subscription.nextState}"/>
            </c:otherwise>
        </c:choose>
        <c:if test="${nextState != 'DELETED'}">
            <tiles:insert definition="invoiceSubscription" flush="false">
                <tiles:put name="topTitle" value=""/>
                <tiles:put name="type" value=""/>
                <tiles:put name="name" value="${subscription.name}"/>
                <tiles:put name="state" value="${state}"/>
                <tiles:put name="nextState" value="${nextState}"/>
                <tiles:put name="provider" value="${subscription.recName}"/>
                <tiles:put name="id" value="${subscription.id}"/>
                <tiles:put name="errorType" value=""/>
                <tiles:put name="statusMessage" value=""/>
                <tiles:put name="notPaid" value="${subscription.numberOfNotPaidInvoices}"/>
                <tiles:put name="delayed" value="${subscription.numberOfDelayedInvoices}"/>
                <tiles:put name="imageId" value="${form.imageIds[id]}"/>
            </tiles:insert>
        </c:if>
    </c:forEach>
</ul>
</tiles:put>
</tiles:insert>

<script type="text/javascript">
var checkDragDrop = false;
var checkReqButtonPressed = false;

function refreshBasketPage()
{
    $(".is-typeActive, .is-typeOther").each(function ()
    {
        $(this).find('.is-additionalTop').hide();
    });
    $(".sg-serviceGroup").each(function ()
    {
        var list = $(this).find('.is-invoiceSubscription');
        if (list.length > 0)
        {
            $(this).find('.sg-chooseButton').show();
            $(this).find('.sg-emptyPart').hide();
        }
        else
        {
            $(this).find('.sg-chooseButton').hide();
            $(this).find('.sg-emptyPart').show();
        }

        $(this).find('.is-typeActive').each(function (index)
        {
            if (index == 0)
                $(this).find('.is-additionalTop').show();
        });

        $(this).find('.is-typeOther').each(function (index)
        {
            if (index == 0)
                $(this).find('.is-additionalTop').show();
        });
    });

    $(".pb-otherServices").hide();
    $(".pb-subscriptionsList").each(function ()
    {
        var list = $(this).find('.is-invoiceSubscription');
        if (list.length > 0)
        {
            if ($(this).find('.pb-listTitle'))
                $(this).find('.pb-listTitle').show();
            $(this).removeClass('pb-emptyList');
            if ($(this).attr("name") == "subListNone")
                $(".pb-otherServices").show();
        }
        else
        {
            if ($(this).find('.pb-listTitle'))
                $(this).find('.pb-listTitle').hide();
            $(this).addClass('pb-emptyList');
        }
    });
    $(".is-invoiceSubscription").each(function ()
    {
        refreshFakeLayerHeight($(this).find('.fakeLayer'));
    });
}

function getEntityId(list)
{
    var listName = list.attr("name");
    var id = listName.replace("subList", "");
    if (id == "None")
        return null;
    return id;
}

function getSubscriptionId(invoiceSubscription)
{
    var elementId = invoiceSubscription.attr("id");
    var id = elementId.replace("invoiceSubscription", "");
    return id;
}

function getSubscriptionType(invoiceSubscription)
{
    var sub = $(invoiceSubscription);
    if (sub.hasClass("documentType"))
        return "documentType";

    if (sub.hasClass("autoPaymentType"))
        return "autoPayment";

    if (sub.hasClass("invoiceSubscriptionType"))
        return "invoiceSubType";

    return "";
}

function showServicesList(accEntId)
{
    var url = '${phiz:calculateActionURL(pageContext, '/private/userprofile/listServices')}';
    location.href = url + '?' + 'accountingEntityId=' + accEntId;
}

doOnLoad(function ()
{
    refreshBasketPage();

    $(".text-gray").bind("click", function ()
    {
        checkReqButtonPressed = true;
        $(this).parent('.is-center').find('.is-reqHiddenList').show();
        $(this).parent('.is-center').find('.is-hideReqList').show();
        $(this).hide();
        refreshFakeLayerHeight($(this).parent('.is-center').parent('.is-main').find('.fakeLayer'));
    });
    $(".is-hideReqList").bind("click", function ()
    {
        checkReqButtonPressed = true;
        $(this).parent('.is-reqHiddenList').hide();
        $(this).parent('.is-reqHiddenList').parent('.is-center').find('.is-showReqList').show();
        refreshFakeLayerHeight($(this).parent('.is-center').parent('.is-main').find('.fakeLayer'));
    });
});

function refreshFakeLayerHeight(fakeLayer)
{
    $(fakeLayer).height($(fakeLayer).parent('.is-main').innerHeight() + 20);
    $(fakeLayer).find('.fakeLayerColorsRC').height($(fakeLayer).innerHeight() - 14);
    var isTop = $(fakeLayer).parents('.r-content').find('.is-top');
    if (isTop.height() > 0)
    {
        $(fakeLayer).height($(fakeLayer).parent('.is-main').innerHeight() + isTop.innerHeight()).addClass('is-top-hover') + 15;
        $(fakeLayer).find('.fakeLayerColorsRC').height($(fakeLayer).innerHeight() + 15);
    }

}
function openEditWindow(type, id)
{
    var element = document.getElementById('${invoiceWinId}');
    element.dataWasLoaded = false;

    var ajaxUrl = document.getElementById('hiddenAjaxUrl${invoiceWinId}');
    var editEntityUrl = getEditEntityUrl(type);

    if (editEntityUrl == null)
    {
        return false;
    }

    ajaxUrl.value = editEntityUrl;

    if (id != null)
    {
        ajaxUrl.value += '?id=' + id;
    }

    win.open('${invoiceWinId}');
    return false;
}

function getEditEntityUrl(type)
{
    if (type == 'FLAT')
    {
        return '${editFlatUrl}';
    }
    if (type == 'GARAGE')
    {
        return '${editGarageUrl}';
    }
    if (type == 'HOUSE')
    {
        return '${editHouseUrl}';
    }
    if (type == 'CAR')
    {
        return '${editCarUrl}';
    }
    return null;
}

function viewSubscription(id, type, stateCode)
{
    if (checkDragDrop || checkReqButtonPressed)
    {
        checkDragDrop = false;
        checkReqButtonPressed = false;
    }
    else
    {
        /*var actionUrl;
        if (type == 'document')
        {
            var formName = 'CreateInvoiceSubscriptionPayment';
            actionUrl = '${viewDocumentUrl}' + '&id=' + id + '&objectFormName=' + formName + '&stateCode=' + stateCode;
        }
        else if (type == 'autoPayment')
            actionUrl = '${viewAutoPaymentUrl}' + '&id=' + id;
        else if (type == 'invoiceSubscription')
            actionUrl = '${viewSubscriptionUrl}' + '&id=' + id;*/
        if (type == 'invoiceSubscription')
        {
            window.location = '${viewSubscriptionUrl}' + '?personId='+ ${form.person} + '&id=' + id;
        }

    }
}

<c:if test="${newBillsToPay}">
$(document).ready(function ()
{
    callAjaxActionMethod('${phiz:calculateActionURL(pageContext,'/private/async/userprofile/profileNovelties')}', 'setOldBillsToPay');
});
</c:if>
</script>

</html:form>

