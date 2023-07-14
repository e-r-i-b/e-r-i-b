<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251"/>
    <meta name="format-detection" content="telephone=no">
    <title>
        <bean:message bundle="commonBundle" key="application.title"/>
    </title>

    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <link rel="icon" type="image/x-icon" href="${skinUrl}/skins/sbrf/images/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${skinUrl}/skins/commonSkin/roundBorder.css"/>
    <link rel="stylesheet" type="text/css" href="${skinUrl}/skins/commonSkin/payment.css"/>
    <link rel="stylesheet" type="text/css" href="${skinUrl}/skins/commonSkin/indicator-complexity.css"/>
    <link rel="stylesheet" type="text/css" href="${skinUrl}/skins/commonSkin/datePicker.css"/>

    <link rel="stylesheet" type="text/css" href="${skinUrl}/skins/sbrf/sliderStyle.css"/>
    <link rel="stylesheet" type="text/css" href="${skinUrl}/skins/sbrf/roundBorder.css"/>
    <link rel="stylesheet" type="text/css" href="${skinUrl}/skins/sbrf/csa/style.css"/>

    <c:if test="${csa:isModernCSS()}">
        <link rel="stylesheet" type="text/css" href="${skinUrl}/skins/sbrf/csa/modern.css"/>
    </c:if>

    <script type="text/javascript">
        document.webRoot='/${phiz:loginContextName()}';
        var skinUrl = '${skinUrl}';
        var globalUrl = '${skinUrl}/skins';
    </script>

    <script type="text/javascript" src="${skinUrl}/scripts/jquery-1.4.2.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/Utils.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/jquery.bxSlider.min.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/windows.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/DivFloat.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/csa/csaUtils.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/csa/csa.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/PaymentsFormHelp.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/select.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/customPlaceholder.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/serializeToWin.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/complexValueIndicator.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/Templates.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/cookies.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/json2.js"></script>
    <%@ include file="/WEB-INF/jsp/common/script-vaultonline-csa.jsp"  %>

    <%-- DATE PICKER --%>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.datePicker.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.tinyscrollbar.min.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.maskedinput.js"></script>
    <!--[if IE 6]>
        <script type="text/javascript" src="${skinUrl}/scripts/iepngfix_tilebg.js"></script>
        <link rel="stylesheet" type="text/css" href="${skinUrl}/skins/sbrf/csa/ie.css"/>
        <link rel="stylesheet" type="text/css" href="${skinUrl}/skins/commonSkin/ie.css"/>
    <![endif]-->
</head>