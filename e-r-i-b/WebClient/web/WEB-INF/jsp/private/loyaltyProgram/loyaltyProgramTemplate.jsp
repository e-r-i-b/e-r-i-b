<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- Информация о программе лояльности --%>
<c:set var="imagePath" value="${globalUrl}/commonSkin/images"/>
<script type="text/javascript">
    <c:set var="ajaxDataURL" value="${phiz:calculateActionURL(pageContext, '/private/async/loyalty/info.do')}"/>
    function updateBalance()
    {
        hideButton('buttonFindBalance');
        hideButton('buttonRegist');
        hideButton('buttonUpdate');
        hideListOfOperation();
        $("#abstractLoader").css("display", "block");
        $(".updateTime").css("visibility", "hidden");
        ajaxQuery(null, '${ajaxDataURL}',
            function(data)
            {
                $("#abstractLoader").css("display", "none");
                if (data.button == 'buttonUpdate')
                {
                    $("#loyaltyBalance").html(data.balance + ' <span class="thanks"><bean:message bundle="loyaltyBundle" key="label.thanks"/></span>');
                    initProductTitle();
                    var updateTime = $(".updateTime");
                    updateTime.text('<bean:message bundle="loyaltyBundle" key="label.updated"/> ' + data.updateDate);
                    updateTime.css("visibility", "visible");
                    $('#balance').removeClass('invisible');
                    $('.updateTime').removeClass('invisible');
                    $('.productText').show();
                }
                $('#' + data.button).parent().removeClass('invisible');
                if (data.error  != undefined && data.error != '')
                {
                    $("#loyaltyErrorDiv").html(data.error);
                    $("#loyaltyErrors").show();
                }
                else
                {
                    $("#loyaltyErrors").hide();
                }
            },
            "json", false
        );
    }

    function hideButton(name)
    {
        var parent = $('#' + name).parent();
        if (!parent.hasClass('invisible'))
            parent.addClass('invisible');
    }
</script>
<div class="loyaltyProgramTemplate">
    <tiles:insert definition="productTemplate" flush="false">
        <tiles:put name="productViewBacklight" value="false"/>
        <c:if test="${loyaltyInfoLink == true}">
            <tiles:put name="src" value="${loyaltyInfoUrl}"/>
        </c:if>
        <c:set var="linkActive" value="${loyaltyProgramLink != null && loyaltyProgramLink.state == 'ACTIVE' && loyaltyProgramLink.balance != null}"/>
        <tiles:put name="img" value="${imagePath}/loyaltyProgramIcon.jpg"/>
        <c:choose>
            <c:when test="${detailsPage}">
                <tiles:put name="titleClass" value="mainProductTitle mainProductDetailTitle"/>
                <tiles:put name="operationsBlockPosition" value="rightPosition"/>
            </c:when>
            <c:otherwise>
                <tiles:put name="titleClass" value="mainProductTitle mainProductTitleLight"/>
            </c:otherwise>
        </c:choose>
        <tiles:put name="title"><bean:message bundle="loyaltyBundle" key="label.bonus.account"/></tiles:put>
        <tiles:put name="centerData">
            <c:set var="updateFormatDate" value=""/>
            <c:if test="${linkActive}">
                <c:set var="updateDate" value="${phiz:formatDateDependsOnSysDate(loyaltyProgramLink.updateTime, true, false)}"/>
                <c:set var="updateFormatDate" value="${fn:toLowerCase(updateDate)}"/>
            </c:if>
            <div id="balance" class="alignRight <c:if test='${not linkActive}'>invisible</c:if>">
                <c:choose>
                    <c:when test="${detailsPage}">
                        <c:set var="spanClass" value="detailAmount"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="spanClass" value="overallAmount"/>
                    </c:otherwise>
                </c:choose>

                <span class="${spanClass} nowrap" id="loyaltyBalance">
                    <c:if test="${linkActive}">${phiz:formatBigDecimal(loyaltyProgramLink.balance)}</c:if>
                    <span class="thanks"><bean:message bundle="loyaltyBundle" key="label.thanks"/></span>
                </span>

                <c:if test="${!detailsPage}">
                    <div class="clear"></div>
                    <div class="updateTime <c:if test='${not linkActive}'>invisible</c:if>"><bean:message bundle="loyaltyBundle" key="label.updated"/> ${updateFormatDate}</div>
                </c:if>
            </div>
            <c:if test="${detailsPage}">
                <div class="findBalanceTop">
                    <img src='${imagePath}/ajaxLoader.gif' alt='' title='' id='abstractLoader' style="display:none;" class="floatRight"/>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.find.balance"/>
                        <tiles:put name="commandHelpKey" value="button.find.balance"/>
                        <tiles:put name="bundle" value="loyaltyBundle"/>
                        <tiles:put name="viewType" value="buttonGrayNew"/>
                        <tiles:put name="onclick" value="updateBalance();cancelBubbling(event);"/>
                        <tiles:put name="btnId" value="buttonFindBalance"/>
                        <c:if test="${linkActive}">
                            <tiles:put name="typeBtn" value="invisible"/>
                        </c:if>
                    </tiles:insert>
                </div>
            </c:if>
        </tiles:put>

        <tiles:put name="leftData">
            <c:set var="displayText" value="${loyaltyProgramLink == null || loyaltyProgramLink.state == 'UNREGISTERED' ? 'none': ''} "/>
            <c:if test="${showPassword}">
                <p class="productText" style="display: ${displayText}"><bean:message bundle="loyaltyBundle" key="label.contact.password"/>: <b>${form.callCentrePassw}</b></p>
            </c:if>
            <c:set var="loyaltyAsyncUrl"  value="${phiz:calculateActionURL(pageContext,'/private/async/loyalty.do')}"/>
            <p class="productText" style="display: ${displayText}">
                <b class="loyaltyInfo">
                    <bean:message bundle="loyaltyBundle" key="label.partners"/>
                    <a class="orangeText" onclick="loyaltyWindow('${loyaltyAsyncUrl}', event);">
                        <span><bean:message bundle="loyaltyBundle" key="label.high.bonus"/></span>
                        <span class="loyaltyInfoIcon"></span>
                    </a>
                </b>
            </p>
        </tiles:put>

        <tiles:put name="rightBlockId"  value="loyaltyBalanceInfo"/>
        <tiles:put name="rightData">
            <div class="fixWidthButton">
                <c:choose>
                    <c:when test="${detailsPage}">
                        <span class="updateTime <c:if test='${not linkActive}'>invisible</c:if>"><bean:message bundle="loyaltyBundle" key="label.updated"/> ${updateFormatDate}</span>
                    </c:when>
                    <c:otherwise>
                        <img src='${imagePath}/ajaxLoader.gif' alt='' title='' id='abstractLoader' style="display:none;"/>
                    </c:otherwise>
                </c:choose>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.update"/>
                    <tiles:put name="commandHelpKey" value="button.update"/>
                    <tiles:put name="bundle" value="loyaltyBundle"/>
                    <tiles:put name="viewType" value="buttonGrayNew"/>
                    <tiles:put name="onclick" value="updateBalance();cancelBubbling(event);"/>
                    <tiles:put name="btnId" value="buttonUpdate"/>
                    <c:if test="${not linkActive}">
                        <tiles:put name="typeBtn" value="invisible"/>
                    </c:if>
                </tiles:insert>
                <c:if test="${!detailsPage}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.find.balance"/>
                        <tiles:put name="commandHelpKey" value="button.find.balance"/>
                        <tiles:put name="bundle" value="loyaltyBundle"/>
                        <tiles:put name="viewType" value="buttonGrayNew"/>
                        <tiles:put name="onclick" value="updateBalance();cancelBubbling(event);"/>
                        <tiles:put name="btnId" value="buttonFindBalance"/>
                        <c:if test="${linkActive || loyaltyProgramLink.state == 'UNREGISTERED'}">
                            <tiles:put name="typeBtn" value="invisible"/>
                        </c:if>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.find.balance"/>
                        <tiles:put name="commandHelpKey" value="button.find.balance"/>
                        <tiles:put name="bundle" value="loyaltyBundle"/>
                        <tiles:put name="viewType" value="buttonGrayNew"/>
                        <tiles:put name="action" value="/private/payments/payment.do?form=LoyaltyProgramRegistrationClaim"/>
                        <tiles:put name="btnId" value="buttonRegist"/>
                        <c:if test="${loyaltyProgramLink == null || loyaltyProgramLink.state != 'UNREGISTERED'}">
                            <tiles:put name="typeBtn" value="invisible"/>
                        </c:if>
                    </tiles:insert>
                </c:if>
            </div>
        </tiles:put>
        <c:if test="${!detailsPage}">
            <tiles:put name="bottomData">
                <div class="loyaltyErrorShort">
                    <tiles:insert definition="warningBlock" flush="false">
                        <tiles:put name="regionSelector" value="loyaltyErrors"/>
                        <tiles:put name="data">
                            <div id="loyaltyErrorDiv"></div>
                        </tiles:put>
                    </tiles:insert>
                </div>
            </tiles:put>
        </c:if>
    </tiles:insert>
</div>

