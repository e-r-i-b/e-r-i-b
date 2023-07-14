<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/widget-tags" prefix="widget" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="bundleName" value="commonBundle"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>

<html:form action="/private/accounts" onsubmit="return setEmptyAction(event)">
<tiles:insert definition="accountInfo">
<tiles:put name="mainmenu" value="Info"/>
<tiles:put name="enabledLink" value="false"/>
<tiles:put name="showRates" value="true"/>
<tiles:put name="menu" type="string"/>
<tiles:put name="data" type="string">

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="isBottomLoanOffer" value="${not empty form.bottomLoanOffers}"/>
<c:set var="isBottomLoanCardOffer" value="${not empty form.bottomLoanCardOffers}"/>
<c:set var="hasRegularPassportRF" value="${phiz:hasRegularPassportRF()}"/>

<%-- Сообщение о том, что клиент использует устаревшую версию браузера --%>
<c:if test="${widget:isAvailableWidget() && widget:showOldBrowserMessage() && !form.needShowPreRegistrationWindow}">
    <%@ include file="oldBrowserMessage.jsp"%>
</c:if>

<c:set var="authenticationContext" value="${phiz:getCurrentAuthenticationContext()}"/>
<c:if test="${form.newSelfRegistrationDesign && form.completeRegistration}">
    <div id="registrationCompleteInfoBlock">
        <div class="control" onclick="$('#registrationCompleteInfoBlock').hide();">
            <div class="closeImg" title="Закрыть"></div>
        </div>
        <div class="content">
            <div class="registrationMessageTitle"><bean:message key="user.registration.complete.message.title" bundle="commonBundle"/></div>
            <div class="registrationMessageText"><bean:message key="user.registration.complete.message.text" bundle="commonBundle"/></div>
        </div>
    </div>
</c:if>
<c:if test="${authenticationContext != null && authenticationContext.lastLoginDate == null && authenticationContext.loginType == 'CSA'}">
    <c:set var="securityType" value="${phiz:restoreSecurityType()}"/>
    <c:if test="${securityType == 'HIGHT' || securityType == 'MIDDLE'}">
        <tiles:insert page="/WEB-INF/jsp-sbrf/accounts/securityInfoBlock.jsp" flush="false">
            <tiles:put name="securityType" value="${securityType}"/>
        </tiles:insert>
    </c:if>
</c:if>

<c:set var="loanOffer" value="${phiz:getLoanOffer(true)}"/>
<c:choose>
    <c:when test="${not empty loanOffer}">
        <tiles:insert definition="roundBorderLight" flush="false">
            <tiles:put name="color" value="infMesGreen"/>
            <tiles:put name="data">
                <div class="notice noticeTick">
                    <div class="noticeTitle">Вам одобрен кредит &laquo;<c:out value="${loanOffer['loanProductName']}"/>&raquo;</div>
                    Для получения кредита подтвердите ваше согласие с индивидуальными условиями кредитования
                </div>
            </tiles:put>
        </tiles:insert>
    </c:when>
    <c:otherwise>
        <%@ include file="offerNotification.jsp"%>
        <%@ include file="advertisingBlocks.jsp"%>
    </c:otherwise>
</c:choose>

<c:set var="settingsUrl" value="${phiz:calculateActionURL(pageContext, '/private/favourite/list')}"/>

<div class = "titleMain">
<%--блок с картами--%>
<c:set var="cardLinks" value="${form.activeCards}"/>
<c:if test="${not empty cardLinks}">
    <c:catch var="error">
        <tiles:insert definition="mainWorkspace" flush="false">
            <c:set var="cardCount" value="${phiz:getClientProductLinksCount('CARD')}"/>
            <c:set var="cardLinksCount" value="${fn:length(cardLinks)}"/>
            <c:set var="cardListUrl" value="${phiz:calculateActionURL(pageContext, '/private/cards/list')}"/>
            <tiles:put name="title">
                <a href="${cardListUrl}">Карты</a>
            </tiles:put>
            <tiles:put name="control">
                <a class="blueGrayLink productOnMainHeaderLink" href="${cardListUrl}" title="К списку">
                    Все карты
                </a>
                <a href="${settingsUrl}" class="productSettingsImg" title="Настройка">
                    <span class="blueGrayLink" title="Настройка">
                        Настройка
                    </span>
                </a>
            </tiles:put>
            <tiles:put name="data">
                <c:set var="page" value="main" scope="request"/>
                <c:set var="cardInfoLink" value="true" scope="request"/>
                <logic:iterate id="listElement" name="ShowAccountsForm" property="activeCards" indexId="i">
                    <c:set var="cardLink" value="${listElement}" scope="request"/>
                    <c:if test="${cardLink.main}">
                        <c:set var="mainNumber" value="${cardLink.number}"/>
                        <c:set var="countAdditionalCards" value="${cardLink.countAdditionalCards}" scope="request"/>
                        <c:set var="countAddCards" value="-1"/>
                    </c:if>
                    <c:set var="resourceAbstract" value="${form.cardAbstract[listElement]}" scope="request"/>
                    <c:set var="showArrow" value="${not empty mainNumber and cardLink.mainCardNumber eq mainNumber}" scope="request"/>
                    <tiles:insert page="cardTemplate.jsp" flush="false"/>
                    <c:set var="countAddCards" value="${countAddCards+1}"/>
                    <c:if test="${cardLinksCount - 1 > i && (empty countAdditionalCards || countAdditionalCards <= countAddCards)}">
                        <div class="productDivider"></div>
                    </c:if>
                </logic:iterate>
            </tiles:put>
        </tiles:insert>
    </c:catch>
    <c:if test="${not empty error}">
        ${phiz:setException(error, pageContext)}
    </c:if>
</c:if>

<%--счета--%>
<c:set var="accounts" value="${form.activeAccounts}"/>
<c:if test="${not empty accounts}">
    <c:catch var="error">
        <tiles:insert definition="mainWorkspace" flush="false">
            <c:set var="availableTargets" value="${phiz:impliesOperation('GetTargetOperation', 'TargetsService')}"/>
            <c:set var="allAccountsCount" value="${fn:length(accounts)}"/>
            <c:set var="accountListUrl" value="${phiz:calculateActionURL(pageContext, '/private/accounts/list')}"/>
            <tiles:put name="title">
                <a href="${accountListUrl}">Вклады</a>
            </tiles:put>
            <tiles:put name="control">
                <a class="blueGrayLink productOnMainHeaderLink" href="${accountListUrl}" title="К списку">
                    Все вклады и счета
                </a>
                <a href="${settingsUrl}" class="productSettingsImg" title="Настройка">
                    <span class="blueGrayLink" title="Настройка">
                        Настройка
                    </span>
                </a>
            </tiles:put>
            <tiles:put name="data">
                <c:set var="accountInfoLink" value="true" scope="request"/>
                <logic:iterate id="listElement" name="ShowAccountsForm" property="activeAccounts" indexId="i">
                    <c:set var="target" value="" scope="request"/>
                    <c:if test="${availableTargets}">
                        <c:set var="target" value="${listElement.target}" scope="request"/>
                    </c:if>
                    <c:set var="accountLink" value="${listElement}" scope="request"/>
                    <c:set var="resourceAbstract" value="${form.accountAbstract[listElement]}" scope="request"/>
                    <c:set var="abstractError" value="${form.accountAbstractErrors[listElement]}" scope="request"/>
                    <c:choose>
                        <c:when test="${not empty target}">
                            <c:set var="targetInfoLink" value="true" scope="request"/>
                            <c:set var="isDetailInfoPage" value="false" scope="request"/>
                            <tiles:insert page="targetTemplate.jsp" flush="false"/>
                        </c:when>
                        <c:otherwise>
                            <tiles:insert page="accountTemplate.jsp" flush="false"/>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${allAccountsCount - 1 > i}">
                        <div class="productDivider"></div>
                    </c:if>
                </logic:iterate>
            </tiles:put>
        </tiles:insert>
    </c:catch>
    <c:if test="${not empty error}">
        ${phiz:setException(error, pageContext)}
    </c:if>
</c:if>

<%--кредиты--%>
<c:set var="loanLinks" value="${form.activeLoans}"/>
<c:if test="${not empty loanLinks}">
    <jsp:include page="../loans/annLoanMessageWindow.jsp" flush="false"/>
    <c:catch var="error">
        <tiles:insert definition="mainWorkspace" flush="false">
            <c:set var="loanCount" value="${phiz:getClientProductLinksCount('LOAN')}"/>
            <c:set var="loanLinksCount" value="${fn:length(loanLinks)}"/>
            <c:set var="loanListUrl" value="${phiz:calculateActionURL(pageContext, '/private/loans/list')}"/>
            <tiles:put name="title">
                <a href="${loanListUrl}">Кредиты</a>
            </tiles:put>
            <tiles:put name="control">
                <a class="blueGrayLink productOnMainHeaderLink" href="${loanListUrl}" title="К списку">
                    Все кредиты
                </a>
                <a href="${settingsUrl}" class="productSettingsImg" title="Настройка">
                    <span class="blueGrayLink" title="Настройка">
                        Настройка
                    </span>
                </a>
            </tiles:put>
            <tiles:put name="data">
                <logic:iterate id="listElement" name="ShowAccountsForm" property="activeLoans" indexId="i">
                    <c:set var="loanLink" value="${listElement}" scope="request"/>
                    <tiles:insert page="/WEB-INF/jsp-sbrf/loans/loanTemplate.jsp" flush="false"/>
                    <c:if test="${loanLinksCount - 1 > i}">
                        <div class="productDivider"></div>
                    </c:if>
                </logic:iterate>
            </tiles:put>
        </tiles:insert>
    </c:catch>
    <c:if test="${not empty error}">
        ${phiz:setException(error, pageContext)}
    </c:if>
</c:if>

<%--счета депо--%>
<c:set var="depoAccountLinks" value="${form.depoAccounts}"/>
<c:if test="${not empty depoAccountLinks}">
    <c:catch var="error">
        <tiles:insert definition="mainWorkspace" flush="false">
            <c:set var="depoAccountCount" value="${phiz:getClientProductLinksCount('DEPO_ACCOUNT')}"/>
            <c:set var="depoAccountLinksCount" value="${fn:length(depoAccountLinks)}"/>
            <c:set var="depoListUrl" value="${phiz:calculateActionURL(pageContext, '/private/depo/list')}"/>
            <tiles:put name="title">
                <a href="${depoListUrl}">Счета депо</a>
            </tiles:put>
            <tiles:put name="control">
                <a class="blueGrayLink productOnMainHeaderLink" href="${depoListUrl}" title="К списку">
                    Все счета депо
                </a>
                <a href="${settingsUrl}" class="productSettingsImg" title="Настройка">
                    <span class="blueGrayLink" title="Настройка">
                        Настройка
                    </span>
                </a>
            </tiles:put>
            <tiles:put name="data">
                <logic:iterate id="listElement" name="ShowAccountsForm" property="depoAccounts" indexId="i">
                    <c:set var="depoAccountLink" value="${listElement}" scope="request"/>
                    <tiles:insert page="/WEB-INF/jsp/depo/depoAccountTemplate.jsp" flush="false"/>
                    <c:if test="${depoAccountLinksCount - 1> i}">
                        <div class="productDivider"></div>
                    </c:if>
                </logic:iterate>
            </tiles:put>
        </tiles:insert>
    </c:catch>
    <c:if test="${not empty error}">
        ${phiz:setException(error, pageContext)}
    </c:if>
</c:if>

<%--металические счета--%>
<c:set var="imAccountLinks" value="${form.imAccounts}"/>
<c:if test="${not empty imAccountLinks}">
    <c:catch var="error">
        <tiles:insert definition="mainWorkspace" flush="false">
            <c:set var="imAccountCount" value="${phiz:getClientProductLinksCount('IM_ACCOUNT')}"/>
            <c:set var="imAccountLinksCount" value="${fn:length(imAccountLinks)}"/>
            <c:set var="imaListUrl" value="${phiz:calculateActionURL(pageContext, '/private/ima/list')}"/>
            <tiles:put name="title">
                <a href="${imaListUrl}">Металлические счета</a>
            </tiles:put>
            <tiles:put name="control">
                <a class="blueGrayLink productOnMainHeaderLink" href="${imaListUrl}" title="К списку">
                    Все счета
                </a>
                <a href="${settingsUrl}" class="productSettingsImg" title="Настройка">
                    <span class="blueGrayLink" title="Настройка">
                        Настройка
                    </span>
                </a>
            </tiles:put>
            <tiles:put name="data">
                <c:set var="bottomOn" value="true" scope="request"/>
                <c:set var="imaInfoLink" value="true" scope="request"/>
                <logic:iterate id="listElement" name="ShowAccountsForm" property="imAccounts" indexId="i">
                    <c:set var="imAccountLink" value="${listElement}" scope="request"/>
                    <c:set var="imAccountAbstract" value="${form.imAccountAbstract[listElement]}"
                           scope="request"/>
                    <tiles:insert page="/WEB-INF/jsp/private/ima/imaTemplate.jsp" flush="false"/>
                    <c:if test="${imAccountLinksCount - 1 > i}">
                        <div class="productDivider"></div>
                    </c:if>
                </logic:iterate>
            </tiles:put>
        </tiles:insert>
    </c:catch>
    <c:if test="${not empty error}">
        ${phiz:setException(error, pageContext)}
    </c:if>
</c:if>
<c:set var="pfrLink" value="${form.pfrLink}"/>
<c:if test="${pfrLink.showInSystem and pfrLink.showInMain}">
    <c:set var="pfrListUrl" value="${phiz:calculateActionURL(pageContext, '/private/npf/list')}"/>
    <c:catch var="error">
        <tiles:insert definition="mainWorkspace" flush="false">
            <tiles:put name="title" value="Пенсионные программы" />
            <tiles:put name="data">
                <jsp:include page="/WEB-INF/jsp/private/pfr/pfrTemplate.jsp" flush="false">
                    <jsp:param name="pfrClaims" value="${form.pfrClaims}"/>
                    <jsp:param name="hideShowInMainCheckBox" value="true"/>
                </jsp:include>
            </tiles:put>
        </tiles:insert>
    </c:catch>
    <c:if test="${not empty error}">
        ${phiz:setException(error, pageContext)}
    </c:if>
</c:if>
<%--программа лояльности--%>
<c:set var="loyaltyProgramLink" value="${form.loyaltyProgramLink}" scope="request"/>
<c:if test="${phiz:impliesService('LoyaltyProgramRegistrationClaim') && (loyaltyProgramLink == null || loyaltyProgramLink.showInMain)}">
    <c:catch var="error">
        <tiles:insert definition="mainWorkspace" flush="false">
            <tiles:put name="title" value="Спасибо от Сбербанка" />
            <tiles:put name="data">
                <c:set var="bottomOn" value="true" scope="request"/>
                <c:set var="loyaltyInfoLink" value="true" scope="request"/>
                <c:set var="loyaltyInfoUrl" value="${phiz:calculateActionURL(pageContext, '/private/loyalty/detail')}" scope="request"/>
                <div id="loyaltyProgramInfo">
                    <tiles:insert page="/WEB-INF/jsp/private/loyaltyProgram/loyaltyProgramTemplate.jsp" flush="false"/>
                </div>
            </tiles:put>
        </tiles:insert>
    </c:catch>
</c:if>
<%--Предложения банка--%>
<%@ include file="offer.jsp"%>

<c:if test="${phiz:impliesService('ViewNewsManagment')}">
    <c:set var="news" value="${phiz:getClientNews()}" scope="request"/>
    <div id="mainPageNewsContainer">
        <c:if test="${not empty news}">
            <div class="mainPageNews">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="title" value="События"/>
                    <tiles:put name="control">
                       <a class="blueGrayLink productOnMainHeaderLink" href="${phiz:calculateActionURL(pageContext, "/private/news/dayList")}" title="к списку">События дня</a>
                       <a class="blueGrayLink" href="${phiz:calculateActionURL(pageContext,"/private/news/list")}" title="к списку">Все события</a>
                    </tiles:put>
                    <tiles:put name="data">
                        <tiles:insert page="/WEB-INF/jsp-sbrf/news.jsp" flush="false"/>
                    </tiles:put>
                </tiles:insert>
            </div>
        </c:if>
    </div>
</c:if>
</div>
<c:if test="${not empty param['completeRegistration'] and not form.newSelfRegistrationDesign}">
    <c:set var="param['completeRegistration']" value=""/>
    <tiles:insert definition="window" flush="false">
        <tiles:put name="id" value="mesWin"/>
        <tiles:put name="styleClass" value="mesWin"/>
        <tiles:put name="data" type="string">
            <div class="messageContainer">
                <div>&nbsp;</div>
                Спасибо! Регистрация прошла успешно. Вы можете изменить логин и пароль, а также настроить собственные параметры безопасности
                в разделе «Настройки».
            </div>
            <div class="buttonsArea">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey"    value="button.close"/>
                    <tiles:put name="commandHelpKey"    value="button.close"/>
                    <tiles:put name="bundle"            value="securityBundle"/>
                    <tiles:put name="viewType"          value="simpleLink"/>
                    <tiles:put name="onclick"           value="win.close('mesWin');"/>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>

    <script type="text/javascript">
       doOnLoad(function() {win.open("mesWin");});
    </script>
</c:if>

<c:if test="${form.needShowPreRegistrationWindow}">
    <tiles:insert definition="window" flush="false">
        <tiles:put name="id" value="preregistrationMessage"/>
        <tiles:put name="styleClass" value="preregistrationMessage"/>
        <tiles:put name="notShowCloseButton" value="${form.hardRegistrationMode}"/>
        <tiles:put name="closeCallback" value="onClosePreRegistrationWindow"/>
        <tiles:put name="data" type="string">
            <div class="title">
                <h2>${form.titlePreRegistrationMessage}</h2>
            </div>

            <div class="selfRegistration">
                <html:img src="${globalImagePath}/safeSbrf.jpg"/>
            </div>

           <div class="messageContainer">
                ${form.preRegistrationMessage}
            </div>

            <div class="buttonsArea">
                <c:if test="${not form.hardRegistrationMode and not form.existCSALogin}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey"    value="button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="button.cancel"/>
                        <tiles:put name="bundle"            value="securityBundle"/>
                        <tiles:put name="viewType"          value="simpleLink"/>
                        <tiles:put name="onclick"           value="win.close('preregistrationMessage');"/>
                    </tiles:insert>
                </c:if>

                <c:if test="${not form.hardRegistrationMode and form.existCSALogin}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey"    value="button.close"/>
                        <tiles:put name="commandHelpKey"    value="button.close"/>
                        <tiles:put name="bundle"            value="securityBundle"/>
                        <tiles:put name="viewType"          value="simpleLink"/>
                        <tiles:put name="onclick"           value="win.close('preregistrationMessage');"/>
                    </tiles:insert>
                </c:if>

                <c:if test="${not form.existCSALogin}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey"    value="button.gotoregistration"/>
                        <tiles:put name="commandHelpKey"    value="button.gotoregistration"/>
                        <tiles:put name="bundle"            value="securityBundle"/>
                        <tiles:put name="onclick"           value="location.href = 'registration.do'"/>
                        <tiles:put name="isDefault"         value="true"/>
                    </tiles:insert>
                </c:if>
            </div>
        </tiles:put>
    </tiles:insert>
    <script type="text/javascript">
        function onClosePreRegistrationWindow()
        {
            <c:if test="${phiz:impliesOperation('ViewAdvertisingBlockOperation', 'ViewAdvertisingBlock')}">
                getNextBunner();
            </c:if>

            setTimeout(function() {win.setGlobalWorkSpace(false, document);}, 250);
            return true;
        }
    </script>
</c:if>

</tiles:put>
</tiles:insert>

<c:if test="${form.needShowPreRegistrationWindow}">
    <script type="text/javascript">
        function showPreregistrationMessage()
        {
            win.setGlobalWorkSpace(true, document);
            win.aditionalData('preregistrationMessage', {closeCallback: onClosePreRegistrationWindow});
            win.open('preregistrationMessage');
        }

        $(document).ready(function()
            {
                showPreregistrationMessage();
            });
    </script>
</c:if>

<c:if test="${phiz:needKasperskyScript()}">
    <script type="text/javascript" src="https://af.kaspersky-labs.com/sb/kljs"></script>
</c:if>

    <c:if test="${phiz:isScriptsRSAActive()}">
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/hashtable.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa-support.js"></script>

        <%-- подготовка данных по deviceTokenFSO для ВС ФМ --%>
        <%@ include file="/WEB-INF/jsp/common/monitoring/fraud/pmfso-support.jsp"%>

        <script type="text/javascript">
            <%-- формирование основных данных для ФМ --%>
            new RSAObject().toHiddenParameters();
        </script>
    </c:if>
</html:form>
