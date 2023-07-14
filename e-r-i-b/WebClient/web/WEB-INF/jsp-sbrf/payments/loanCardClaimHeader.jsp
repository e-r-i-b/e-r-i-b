<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<tiles:importAttribute/>
<c:set var="form"      value="${phiz:currentForm(pageContext)}"/>
<c:set var="stateCode" value="${form.document.state.code}"/>
<c:set var="document" value="${form.document}"/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="metadata" value="${form.metadata}"/>

<c:if test="${showTitle}">
    <div class="Title" style="margin-top: 15px; margin-bottom: 15px;">
        <span class="word-wrap">
            <c:choose>
                <c:when test="${metadata.form.name == 'ExtendedLoanClaim'}">
                    Оформление кредитной карты
                </c:when>
                <c:otherwise>
                    Оформление кредитной карты
                </c:otherwise>
            </c:choose>
        </span>
    </div>
</c:if>
<div id="myRegion">
    <c:if test="${document.owner.guest}">
        <span class="regionTtl">Мой регион:</span>
        <c:set var="isConfirm" value="${document.state.code eq 'SAVED'}"/>
        <c:choose>
            <c:when test="${!view && !isConfirm}">
                <span id="regionNameSpan" onclick="win.open('personRegionsDiv'); return false" class="regionUserSelect">
                   ${document.regionName}
                </span>
            </c:when>
            <c:otherwise>
                ${document.regionName}
            </c:otherwise>
        </c:choose>
        <c:if test="${!view && !isConfirm}">
            <img alt="" class="stickpin" src="${skinUrl}/images/stickpin-s.png">
        </c:if>
        <input id="regionId" type="hidden" name="regionId" value="${document.regionId}"/>
        <input id="regionName" type="hidden" name="regionName" value="${document.regionName}"/>
        <input id="personTB" type="hidden" name="personTB" value="${document.personTB}"/>
        <div class="dividerArea"> </div>
    </c:if>
</div>
<c:set var="regionUrl" value="${phiz:calculateActionURL(pageContext,'/dictionaries/loan-card-claim-regions/list')}"/>
<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="personRegionsDiv"/>
    <tiles:put name="loadAjaxUrl" value="${regionUrl}?isOpening=true&id=0&oldId=${document.regionId}"/>
    <tiles:put name="styleClass" value="regionsDiv"/>
</tiles:insert>

<c:choose>
    <c:when test="${metadata.form.name == 'ExtendedLoanClaim'}">
        <c:set var="offerId" value="${document.loanOfferId}"/>
    </c:when>
    <c:otherwise>
        <c:set var="offerId" value="${document.offerId}"/>
    </c:otherwise>
</c:choose>

<c:if test="${offerId != null && offerId != ''}">
    <table class="paymentHeader">
        <tbody>
        <tr>
            <td>
                <img class="icon" src="${globalImagePath}/iconPmntList_cardClaim.jpg" alt=""/>
            </td>
            <td>
                <span class="size16">У Вас изменились паспортные данные?</span><br><br>
                <span class="size13">Если да &ndash; перед оформлением нужно сообщить их в отделение Сбербанка.<br>Мы не сможем выдать Вам карту по старому паспорту.</span><br>
            </td>
        </tr>
        </tbody>
</table>
</c:if>
<script type="text/javascript">
    $(document).ready(function()
    {
        var stickpin = $(".stickpin");
        stickpin.animate({
            marginTop: 2,
            opacity: 1
        }, 800);
        return false;
    });
    function updateRegionsFieldForCard(regionSelector)
    {
        $('[name="regionId"]').val(regionSelector.currentRegionId);
        $('[name="regionName"]').val(regionSelector.currentRegionName);
        $('[name="personTB"]').val(regionSelector.currentRegionCodeTB);
        $('#regionNameSpan').html(regionSelector.currentRegionName);
        $('[name="osb"]').val("");
        $('[name="vsp"]').val("");
        win.close(regionSelectorWindowId);
    }
    var regionSelectorWindowId = 'personRegionsDiv';

    var parameters =
    {
        customDictionaryUrl: '/dictionaries/loan-card-claim-regions/list',
        windowId: regionSelectorWindowId,
        click:
        {
            getParametersCallback: function(id){return id > 0? 'isOpening=false&setCnt=true&id=' + id: '';},
            updateRegionsFieldForCard: updateRegionsFieldForCard
        },
        choose:
        {
            useAjax: false,
            afterActionCallback: function(myself)
            {
                setCurrentRegion(myself);
            }
        }
    };
    initializeRegionSelector(parameters);
</script>