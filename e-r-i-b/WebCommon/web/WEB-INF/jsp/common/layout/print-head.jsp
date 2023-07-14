<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>

<head>
    <title><bean:message key="application.title" bundle="commonBundle"/></title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251"/>
    <link rel="icon" type="image/x-icon" href="${skinUrl}/images/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/systemAll.css"/>

    <c:set var="contextName" value="${phiz:loginContextName()}"/>
    <c:if test="${contextName eq 'PhizIC'}">
        <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/style.css"/>
    </c:if>

    <link rel="stylesheet" type="text/css" href="${skinUrl}/style.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/print.css"/>
    <script type="text/javascript">
        document.webRoot='/${phiz:loginContextName()}';
        var skinUrl = '${skinUrl}';
        var globalUrl = '${globalUrl}';
    </script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Utils.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Regions.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/tableProperties.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/select.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/validators.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Templates.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/PaymentsFormHelp.js"></script>
	<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/commandButton.js"></script>
	<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Crypto.js"></script>
	<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/builder.js"></script>
	<%--<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/prototype-1.6.0.2.js"></script>--%>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/iframerequest.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/cookies.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/layout.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/DivFloat.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/windows.js"></script>
	<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/${phiz:cryptoProviderName()}Crypto.js"></script>
	<script type="text/javascript">setRSCryptoName('${phiz:cryptoProviderName()}');</script>

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

    <c:if test="${!phiz:isModernCSS()}">
        <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/oldBrowserStyle.css"/>
    </c:if>
    <c:if test="${phiz:isPhizIC()}">
        <%@ include file="/WEB-INF/jsp/common/script-vaultonline.jsp"  %>
    </c:if>
</head>
