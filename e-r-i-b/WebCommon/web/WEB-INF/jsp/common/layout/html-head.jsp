<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/widget-tags" prefix="widget" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<tiles:importAttribute/>

<head>
    <title><bean:message key="application.title" bundle="commonBundle"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251"/>
    <meta name="format-detection" content="telephone=no" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="icon" type="image/x-icon" href="${skinUrl}/images/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/systemAll.css"/>

    <%--кроме данной таблицы стилей и ie.css в коменте других быть не должно--%>
    <c:set var="contextName" value="${phiz:loginContextName()}"/>

    <c:if test="${contextName eq 'PhizIC'}">
        <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/style.css"/>
        <link rel="stylesheet" type="text/css" href="${skinUrl}/style.css"/>
        <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/pfpStyle.css"/>

        <c:if test="${!phiz:isModernCSS()}">
            <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/oldBrowserStyle.css"/>
        </c:if>

    </c:if>

    <c:if test="${contextName eq 'PhizIA'}">
        <link rel="stylesheet" type="text/css" href="${skinUrl}/roundBorder.css"/> <%--roundBorder.css д.б. перед commonStyle.css (т.к. commonStyle.css зависит от roundBorder.css) --%>
        <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/commonStyle.css"/>
        <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/switchery.css"/>
        <link rel="stylesheet" type="text/css" href="${skinUrl}/style.css"/>
        <link rel="stylesheet" type="text/css" href="${skinUrl}/pfpStyle.css"/>
        <link rel="stylesheet" type="text/css" href="${skinUrl}/window.css"/>
        <link rel="stylesheet" type="text/css" href="${skinUrl}/basket.css"/>
        <link rel="stylesheet" type="text/css" href="${skinUrl}/datePicker.css"/>
        <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/dragdealer.css"/>
    </c:if>
    <script type="text/javascript">
        document.webRoot='/${phiz:loginContextName()}';
        var skinUrl   = '${skinUrl}';
        var globalUrl = '${globalUrl}';

        window.resourceRoot = globalUrl;
    </script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/inf.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-ui-1.8.16.custom.min.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.number_format.js"></script>

    <script type="text/javascript">
       /* NanoInf.init({
            source:"${globalImagePath}/svf/Vishnu.swf",
            swfWidth:470,
            swfHeight:320,
            showWindow:false,
            transparent:false
        });*/
    </script>

    <c:if test="${phiz:isPhizIC()}">
        <%@ include file="/WEB-INF/jsp/common/script-vaultonline.jsp"  %>
    </c:if>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/DivFloat.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Utils.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Regions.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Array.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/tableProperties.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/select.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/select-sbt.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/customPlaceholder.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/dragdealer.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/formatedInput.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/imageInput.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/validators.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Templates.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/PaymentsFormHelp.js"></script>
	<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/commandButton.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/clientButton.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/clientButtonUtil.js"></script>
	<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/builder.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/iframerequest.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/cookies.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/layout.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/windows.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/longOffer.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/TextEditor.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Moment.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/json2.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/serializeToWin.js"></script>
    <%--библиотека для добавления масок ввода(например, для дат: __.__._____)--%>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.maskedinput.js"></script>
    <%--библиотека для "живого" поиска--%>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.autocomplete.js"></script>
    <%-- Подключаем  скрипт для работы с компонентом градусник--%>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Thermometer.js"></script>
    <%-- Подключаем скрипт для работы со слайдером--%>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/valueSlider.js"></script>
    <%-- Скрипт для работы select`a с функцией поиска --%>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/liveSearchComponent.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/liveSearchComponentScrolls.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.mousewheel.min.js"></script>
    <%--скроллинг детальной информации по кредиту--%>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/credit.detail.scroller.js"></script>
    <%-- Скрипты для поля с живым поиском --%>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/mixinObjects.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/liveSearchInput.js"></script>

    <c:if test="${needCrypto == 'true'}">
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Crypto.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/${phiz:cryptoProviderName()}Crypto.js"></script>
        <script type="text/javascript">setRSCryptoName('${phiz:cryptoProviderName()}');</script>
    </c:if>

    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/switchery.js"></script>
    <!--[if IE 8]>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/html5.js"></script>
        <style type="text/css">
            .css3 {
                behavior: url(${initParam.resourcesRealPath}/commonSkin/PIE.htc);
                position: relative;
            }
        </style>
    <![endif]-->

    <c:if test="${!empty aditionalHeaderData}">
        <tiles:insert attribute="aditionalHeaderData"></tiles:insert>
    </c:if>
    <%-- DATE PICKER --%>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.datePicker.js"></script>
    <!--[if IE 6]>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/iepngfix_tilebg.js"></script>
        <c:if test="${contextName eq 'PhizIC'}">
            <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/ie.css"/>
        </c:if>
        <link type="text/css" rel="stylesheet" href="${skinUrl}/ie.css"/>
	    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.bgiframe.min.js"></script>
    <![endif]-->

    <%-- Подключение фреймворка Dojo --%>
    <c:if test="${phiz:isDojoRequired()}">
        <%--custom dojo styles--%>
        <link rel="stylesheet" href="${initParam.resourcesRealPath}/commonSkin/dijit/sbrf.css">
        <%--dojo--%>
        <script type="text/javascript">
            var dojoConfig = {
                async: true,
                parseOnLoad: true,
                baseUrl: "${initParam.resourcesRealPath}/scripts/",     // корень со всеми js
                packages: [
                    { name: "dojo", location: "dojo" },                 // здесь лежит dojo
                    { name: "dijit", location: "dijit" },               // здесь лежит dijit
                    { name: "dojox", location: "dojox" },               // здесь лежит dojox
                    { name: "widget", location: "widget" }              // здесь лежат виджеты
                ]
            };
        </script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/dojo/dojo.js"></script>

        <%-- Подключение виджетов --%>
        <c:if test="${widget:isAvailableWidget() && !widget:isOldBrowser()}">
            <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/widget.css"/>
            <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/slidebar.css"/>
            <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/widget-catalog.css"/>

            <script type="text/javascript">
                 require(["widget/widget"]);
            </script>

            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.tinyscrollbar.min.js"></script>
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.disable.text.select.js"></script>
            
            <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/graphics.css"/>

            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-ui-1.8.16.custom.min.js"></script>
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.ui.extenddraggable.js"></script>
            
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.jqplot.js"></script>
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/graphics.js"></script>
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jqplot.pieRenderer.js"></script>

            <!-- Bar -->
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jqplot.barRenderer.js"></script>
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jqplot.categoryAxisRenderer.js"></script>

            <!-- date Plugin -->
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jqplot.dateAxisRenderer.js"></script>
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jqplot.canvasAxisTickRenderer.js"></script>
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jqplot.canvasTextRenderer.js"></script>
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jqplot.canvasAxisLabelRenderer.js"></script>
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jqplot.donutRenderer.js"></script>

            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jqplot.pointLabels.js"></script>

            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/MyFinancesDiagram.js"></script>
        </c:if>
    </c:if>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/KeyboardUtils.js"></script>
</head>
