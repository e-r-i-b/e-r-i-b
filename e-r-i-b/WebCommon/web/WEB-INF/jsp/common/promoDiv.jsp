<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute ignore="true"/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<%--
	appointment - в Оплате товаров и услуг назначение платежа
    serviceId - id сервиса
	listPayTitle - заголовок
	description - описание
	maxLength - длина промо-кода
--%>

<c:set var="descriptions" value="${phiz:getPaymentDescription(serviceId, appointment)}"/>
<c:if test="${empty listPayTitle}">
    <c:set var="listPayTitle" value="${descriptions.description}"/>
</c:if>

<div id="promoDiv" class="promo-item">

    <div class="promo-item-text">
        <html:link styleClass="promoTxtlink" onclick="showButtonBlock()" href="#">${listPayTitle}</html:link>
        <span class="promoTxtDesc">${description}</span>
    </div>

    <div id="bowDiv">
        <img class="promoNew" src="${globalUrl}/commonSkin/images/promoNew.png" alt=""/>
    </div>
    <div class="showPromoBtn">
        <div id="buttonBlock">
            <input id="promoCodeInput" type="text" oninput="upperCaseText(this.id)" maxlength="${maxLength}"/>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="viewType" value="btnGreenSmall"/>
                <tiles:put name="btnId" value="addbutton"/>
                <tiles:put name="onclick">addCode();</tiles:put>
            </tiles:insert>
        </div>
        <div id="msg12Block" class="blockedContent">
            <span id="msg12BlockText" class="lockText"></span>
        </div>
        <c:if test="${phiz:isPersonHasPromoCodes()}">
            <div id="allCodesDiv">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.all.promocodes"/>
                    <tiles:put name="commandHelpKey" value="button.all.promocodes"/>
                    <tiles:put name="bundle" value="promocodesBundle"/>
                    <tiles:put name="viewType" value="blueGrayLink"/>
                    <tiles:put name="action" value="/private/userprofile/promoCodes"/>
                </tiles:insert>
            </div>
        </c:if>
    </div>

</div>

<script type="text/javascript">
    doOnLoad(function()
    {
        $('#msg12Block').hide();
        var promoCodeBlockedMessage = "${phiz:processBBCodeAndEscapeHtml(phiz:getPromoCodeBlockedMessage(),false)}";
        if (promoCodeBlockedMessage) {
            $('#msg12BlockText').html(promoCodeBlockedMessage);
            $('#msg12Block').show();
        }
    })
    function upperCaseText(elementId)
    {
        var element = $('#'+elementId);
        element.val(element.val().toUpperCase());
    }
    function showButtonBlock()
    {
        $('.showPromoBtn').toggle();
    }

    function addCode()
    {
        var params = {};
        params['operation'] = 'button.save';
        params['field(promoCode)'] = $('#promoCodeInput').val();

        ajaxQuery(convertAjaxParam (params), "${phiz:calculateActionURL(pageContext, '/private/async/promocodes/add')}",
                function (data)
                {
                    if (data.errorMessageNumber == '')
                    {
                        window.location = "${phiz:calculateActionURL(pageContext,'private/deposits/products/list.do?form=AccountOpeningClaim&withPromo=true')}";
                    }
                    else
                    {
                        var errorDiv = $("#errors");
                        errorDiv.find(".title").html(data.errorMessageTitle);
                        errorDiv.find(".messageContainer").html(data.errorMessageText);
                        errorDiv.show();
                        if (data.errorMessageNumber != 'MSG011')
                        {
                            $('#promoCodeInput').addClass('errPromo');
                        }
                        else
                        {
                            $('#msg12BlockText').html(data.error12MessageText);
                            $('#msg12Block').show();
                        }
                    }
                },
                "json", null);
    }
</script>

