<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="guestEntryType" value="${form.request}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>Сбербанк Онлайн</title>
    <link rel="icon" type="image/x-icon" href="${globalUrl}/commonSkin/images/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/guest.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/systemAll.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/style.css"/>
    <link rel="stylesheet" type="text/css" href="${skinUrl}/style.css"/>

    <script type="text/javascript">
        window.resourceRoot = '${globalUrl}';
    </script>
    <script type="text/javascript" src="${globalUrl}/scripts/jquery-1.4.2.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/inf.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/jquery-ui-1.8.16.custom.min.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/jquery.number_format.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/DivFloat.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/Utils.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/Regions.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/Array.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/tableProperties.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/select.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/select-sbt.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/customPlaceholder.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/dragdealer.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/formatedInput.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/imageInput.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/validators.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/Templates.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/PaymentsFormHelp.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/commandButton.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/clientButton.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/clientButtonUtil.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/builder.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/iframerequest.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/cookies.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/layout.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/windows.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/longOffer.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/TextEditor.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/Moment.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/json2.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/serializeToWin.js"></script>
    <%--библиотека для добавления масок ввода(например, для дат: __.__._____)--%>
    <script type="text/javascript" src="${globalUrl}/scripts/jquery.maskedinput.js"></script>
    <%--библиотека для "живого" поиска--%>
    <script type="text/javascript" src="${globalUrl}/scripts/jquery.autocomplete.js"></script>
    <%-- Подключаем  скрипт для работы с компонентом градусник--%>
    <script type="text/javascript" src="${globalUrl}/scripts/Thermometer.js"></script>
    <%-- Подключаем скрипт для работы со слайдером--%>
    <script type="text/javascript" src="${globalUrl}/scripts/valueSlider.js"></script>
    <%-- Скрипт для работы select`a с функцией поиска --%>
    <script type="text/javascript" src="${globalUrl}/scripts/liveSearchComponent.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/liveSearchComponentScrolls.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/jquery.mousewheel.min.js"></script>
    <%--скроллинг детальной информации по кредиту--%>
    <script type="text/javascript" src="${globalUrl}/scripts/credit.detail.scroller.js"></script>
    <%-- Скрипты для поля с живым поиском --%>
    <script type="text/javascript" src="${globalUrl}/scripts/mixinObjects.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/liveSearchInput.js"></script>

    <script type="text/javascript" src="${globalUrl}/scripts/switchery.js"></script>
    <!--[if IE 8]>
    <script type="text/javascript" src="${globalUrl}/scripts/html5.js"></script>
    <style type="text/css">
        .css3 {
            behavior: url(${globalUrl}/commonSkin/PIE.htc);
            position: relative;
        }
    </style>
    <![endif]-->
    <script type="text/javascript" src="${globalUrl}/scripts/jquery.datePicker.js"></script>
    <!--[if IE 6]>
    <script type="text/javascript" src="${globalUrl}/scripts/iepngfix_tilebg.js"></script>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/ie.css"/>
    <link type="text/css" rel="stylesheet" href="${skinUrl}/ie.css"/>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.bgiframe.min.js"></script>
    <![endif]-->

    <c:if test="${!empty aditionalHeaderData}">
        <tiles:insert attribute="aditionalHeaderData"></tiles:insert>
    </c:if>
    <script type="text/javascript" src="${globalUrl}/scripts/KeyboardUtils.js"></script>
</head>
<body class="gPage">
<html:form action="/private/payments/confirm" show="true" onsubmit="this.onsubmit = function(){ return false; }; return setEmptyAction();" styleId="guestPersonData">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="metadata" value="${form.metadata}"/>
    <c:set var="isLoanCardClaim" value="${metadata.form.name == 'LoanCardClaim'}"/>
    <c:set var="isLoanCardClaimAvailable" value="${phiz:isLoanCardClaimAvailable(false)}"/>
    <div id="workspaceCSA" class="fullHeight">

        <div class="mainContent guest-payment">
            <div class="header noButtonRegistration">
                <a class="logoSB" href="http://www.sberbank.ru/">
                    <img src="${globalUrl}/commonSkin/images/guest/logoSB.png" height="72" width="289"/>
                </a>

                <div class="headPhones">
                    <div class="federal">
                        <span class="phoneIco"></span>
                        8 (800) 555 55 50
                    </div>
                    <div class="regional">+7 (495) 500-55-50</div>
                </div>
            </div>

            <div class="title">
                <p>Оформление кредита</p>

                <div class="g-num">
                    <c:set var="phoneNumber" value="${phiz:getGuestPhoneNumber()}"/>
                    Мобильный номер
                    <div class="g-num-mask">${phiz:getCutPhoneNumber(phoneNumber)}</div>
                </div>
            </div>

            <div class="wrapper">
                <div class="ears-content">
                    <div class="formContent">
                        <div class="formContentMargin">

                            <c:choose>
                                <c:when test="${(isLoanCardClaim and isLoanCardClaimAvailable) or !isLoanCardClaim}">
                                    <tiles:insert definition="paymentForm" flush="false">
                                        <tiles:put name="guest" value="${true}"/>
                                        <tiles:put name="id" value="${metadata.form.name}"/>
                                        <tiles:put name="name" value="${metadata.form.description}"/>
                                        <tiles:put name="description" value="${metadata.form.detailedDescription}"/>
                                        <c:if test="${isLoanCardClaim}">
                                            <tiles:put name="showHeader" value="false"/>
                                            <tiles:insert page="/WEB-INF/jsp-sbrf/payments/loanCardClaimHeader.jsp" flush="false">
                                                <tiles:put name="view" value="false"/>
                                                <tiles:put name="showTitle" value="false"/>
                                            </tiles:insert>
                                        </c:if>
                                        <tiles:put name="data" type="string">
                                            <span onkeypress="onEnterKey(event);">
                                                ${form.html}
                                            </span>
                                        </tiles:put>

                                        <c:set var="metadata" value="${form.metadata}"/>
                                        <c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
                                        <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.document)}"/>
                                        <c:set var="mode" value="${phiz:getUserVisitingMode()}"/>
                                        <c:set var="ajaxActionUrl" value="/private/async/payments/confirm"/>

                                        <tiles:put name="buttons">
                                            <div class="clear"></div>
                                            <tiles:insert definition="commandButton" flush="false">
                                                <tiles:put name="commandKey" value="button.edit"/>
                                                <tiles:put name="commandHelpKey" value="button.edit.help"/>
                                                <tiles:put name="bundle" value="paymentsBundle"/>
                                                <tiles:put name="stateObject" value="document"/>
                                                <tiles:put name="viewType" value="buttonGrey"/>
                                                <tiles:put name="imageUrl"       value="${globalUrl}/commonSkin/images/backIcon.png"/>
                                                <tiles:put name="imageHover"     value="${globalUrl}/commonSkin/images/backIconHover.png"/>
                                                <tiles:put name="imagePosition"  value="left"/>
                                            </tiles:insert>

                                            <div class="clear"></div>
                                            <div class="buttonsArea">
                                                <span class="clientButton chooseConfirmStrategy">
                                                    <tiles:insert definition="confirmButtons" flush="false">
                                                        <tiles:put name="ajaxUrl" value="${ajaxActionUrl}"/>
                                                        <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                                        <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                                                        <tiles:put name="message" value="ertretret"/>
                                                        <%--<tiles:put name="message" value="${message}"/>--%>
                                                        <tiles:put name="formName" value="${metadata.form.name}"/>
                                                        <tiles:put name="mode" value="${mode}"/>
                                                        <tiles:put name="stateObject" value="document"/>
                                                    </tiles:insert>
                                                </span>

                                                <%--<c:set var="hasSMS" value="${phiz:isContainStrategy(confirmStrategy,'sms')}"/>--%>
                                                <%--<c:set var="hasCard" value="${phiz:isContainStrategy(confirmStrategy,'card')}"/>--%>
                                                <%--<c:set var="hasCap" value="${phiz:isContainStrategy(confirmStrategy,'cap')}"/>--%>

                                                <%--<c:choose>--%>
                                                    <%--<c:when test="${not empty confirmRequest}">--%>
                                                        <%--<c:set var="ajaxActionUrl" value="/private/async/payments/confirm"/>--%>
                                                        <%--<span class="clientButton chooseConfirmStrategy">--%>
                                                            <%--<tiles:insert definition="confirmButtons" flush="false">--%>
                                                                <%--<tiles:put name="ajaxUrl" value="${ajaxActionUrl}"/>--%>
                                                                <%--<tiles:put name="confirmRequest" beanName="confirmRequest"/>--%>
                                                                <%--<tiles:put name="confirmStrategy" beanName="confirmStrategy"/>--%>
                                                                <%--<tiles:put name="message" value="${message}"/>--%>
                                                                <%--<tiles:put name="formName" value="${metadata.form.name}"/>--%>
                                                                <%--<tiles:put name="mode" value="${mode}"/>--%>
                                                                <%--<tiles:put name="stateObject" value="document"/>--%>
                                                            <%--</tiles:insert>--%>
                                                        <%--</span>--%>
                                                    <%--</c:when>--%>
                                                    <%--<c:otherwise>--%>
                                                        <%--<tiles:insert definition="commandButton" flush="false">--%>
                                                            <%--<tiles:put name="commandKey" value="button.confirm"/>--%>
                                                            <%--<tiles:put name="commandHelpKey" value="button.dispatch.help"/>--%>
                                                            <%--<tiles:put name="bundle" value="paymentsBundle"/>--%>
                                                            <%--<tiles:put name="isDefault" value="true"/>--%>
                                                            <%--<tiles:put name="stateObject" value="document"/>--%>
                                                        <%--</tiles:insert>--%>
                                                    <%--</c:otherwise>--%>
                                                <%--</c:choose>--%>

                                                <%--<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa.js"></script>--%>
                                                <%--<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa-support.js"></script>--%>
                                                <%--<c:if test="${phiz:isFSORSAActive()}">--%>
                                                    <%--&lt;%&ndash; формирование значения поля deviceTokenFSO для ФМ &ndash;%&gt;--%>
                                                    <%--<%@ include file="/WEB-INF/jsp/common/monitoring/fraud/pmfso.jsp"%>--%>
                                                <%--</c:if>--%>
                                                <%--<script type="text/javascript">--%>
                                                    <%--doOnLoad(function()--%>
                                                    <%--{--%>
                                                        <%--var button = findCommandButton("button.dispatch");--%>
                                                        <%--if (button)--%>
                                                        <%--{--%>
                                                            <%--button.validationFunction = function()--%>
                                                            <%--{--%>
                                                                <%--new RSAObject().toHiddenParameters();--%>

                                                                <%--<c:choose>--%>
                                                                <%--<c:when test="${metadata.form.name == 'AccountOpeningClaim' or--%>
                                                                                <%--metadata.form.name eq 'AccountOpeningClaimWithClose' or--%>
                                                                                <%--metadata.form.name eq 'IMAOpeningClaim' or--%>
                                                                                <%--metadata.form.name eq 'ChangeDepositMinimumBalanceClaim' or--%>
                                                                                <%--metadata.form.name eq 'AccountChangeInterestDestinationClaim'or--%>
                                                                                <%--metadata.form.name eq 'CreditReportPayment'}">--%>
                                                                <%--return checkClientAgreesCondition(button.commandText);--%>
                                                                <%--</c:when>--%>
                                                                <%--<c:otherwise>--%>
                                                                <%--return true;--%>
                                                                <%--</c:otherwise>--%>
                                                                <%--</c:choose>--%>
                                                            <%--}--%>
                                                        <%--}--%>
                                                    <%--});--%>
                                                <%--</script>--%>

                                                <div class="clear"></div>
                                            </div>
                                        </tiles:put>
                                    </tiles:insert>
                                </c:when>
                                <c:otherwise>
                                    <tiles:insert page="/WEB-INF/jsp/guest/loanClaimUnavailablePage.jsp" flush="false"/>
                                </c:otherwise>
                            </c:choose>

                        </div>
                    </div>
                </div>
                <div class="base">
                    <div class="main_row">
                        <div class="content_row"></div>
                    </div>
                    <div class="ears ears-b"></div>
                </div>
            </div>
        </div>

        <div class="clear"></div>
        <div class="footer">
            <div class="references">
                <a href="http://www.sberbank.ru/moscow/ru/person/contributions/max_stavka/">Информация о процентных ставках по договорам банковского вклада с физическими
                    лицами</a><br/>
                <a href="http://www.sberbank.ru/moscow/ru/investor_relations/disclosure/shareholders/">Информация о лицах, под контролем которых находится банк</a>

                <div class="copyright">
                    &copy 1997&#8211;2015 Сбербанк России, Россия, Москва, 117997, ул. Вавилова, д.19, тел. +7(495) 500
                    5550 <br/>
                    8 800 555 5550. Генеральная лицензия на осуществление банковских операций от 8 августа 2012
                    года. <br/>
                    Регистрационный номер &#8212; 1481.
                    <a class="icon" href="http://www.sberbank.ru/moscow/ru/important/"><span>Обратная связь.</span></a>
                </div>
            </div>
        </div>
        <div id="loading" style="left:-3300px;">
            <div id="loadingImg"><img src="${globalUrl}/commonSkin/images/ajax-loader64.gif"/></div>
            <span>Пожалуйста, подождите,<br/> Ваш запрос обрабатывается.</span>
        </div>
        <script type="text/javascript">
            var skinUrl = '${skinUrl}';
            var globalUrl = '${globalUrl}';
            document.webRoot = '/PhizIC';

        </script>
    </div>
</html:form>
</body>
</html>
