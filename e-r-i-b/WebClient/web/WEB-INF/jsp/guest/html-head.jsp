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

    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/clear.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/guest.css"/>

    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/datePicker.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/select-sbt.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/roundBorder.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/payment.css"/>
    <link rel="stylesheet" type="text/css" href="${skinUrl}/roundBorder.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/sbnkd.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/switchery.css"/>


    <script type="text/javascript">
        document.webRoot='/${phiz:loginContextName()}';
        var skinUrl = '${skinUrl}';
        var globalUrl = '${globalUrl}';

        window.resourceRoot = globalUrl;
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
    <%--<script type="text/javascript" src="${globalUrl}/scripts/select.js"></script>--%>
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

    <%-- Подключаем  скрипт для работы с компонентом градусник--%>
    <script type="text/javascript" src="${globalUrl}/scripts/Thermometer.js"></script>
    <%-- Подключаем скрипт для работы со слайдером--%>
    <script type="text/javascript" src="${globalUrl}/scripts/valueSlider.js"></script>
    <%-- Скрипт для работы select`a с функцией поиска --%>
    <script type="text/javascript" src="${globalUrl}/scripts/liveSearchComponent.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/liveSearchComponentScrolls.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/jquery.mousewheel.min.js"></script>
    <%--скроллинг детальной информации по кредиту--%>


    <c:if test="${needCrypto == 'true'}">
        <script type="text/javascript" src="${globalUrl}/scripts/Crypto.js"></script>
        <script type="text/javascript" src="${globalUrl}/scripts/${phiz:cryptoProviderName()}Crypto.js"></script>
        <script type="text/javascript">setRSCryptoName('${phiz:cryptoProviderName()}');</script>
    </c:if>

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

    <%-- DATE PICKER --%>
    <script type="text/javascript" src="${globalUrl}/scripts/jquery.datePicker.js"></script>
    <!--[if IE 6]>
    <script type="text/javascript" src="${globalUrl}/scripts/iepngfix_tilebg.js"></script>
    <c:if test="${contextName eq 'PhizIC'}">
        <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/ie.css"/>
    </c:if>
    <link type="text/css" rel="stylesheet" href="${skinUrl}/ie.css"/>
    <script type="text/javascript" src="${globalUrl}/scripts/jquery.bgiframe.min.js"></script>
    <![endif]-->

</head>