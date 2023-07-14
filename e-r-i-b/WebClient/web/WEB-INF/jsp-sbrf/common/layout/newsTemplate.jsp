<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>

    <title><bean:message key="application.title" bundle="commonBundle"/></title>
    <link rel="icon" type="image/x-icon" href="${skinUrl}/images/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/style.css"/>       
    <link rel="stylesheet" type="text/css" href="${skinUrl}/style.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/login.css">
    <link rel="stylesheet" type="text/css" href="${skinUrl}/login.css"/>
    <c:if test="${!phiz:isModernCSS()}">
        <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/oldBrowserStyle.css"/>
    </c:if>

    <script type="text/javascript">
        document.webRoot = '/${phiz:loginContextName()}';
        var skinUrl = '${skinUrl}';
        var globalUrl = '${globalUrl}';
    </script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/DivFloat.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Utils.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Templates.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/PaymentsFormHelp.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/commandButton.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Crypto.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/builder.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/iframerequest.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/layout.js"></script>
    <script type="text/javaScript" src="${initParam.resourcesRealPath}/scripts/cookies.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/customPlaceholder.js"></script>
    <%-- DATE PICKER --%>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.datePicker.js"></script>
    <%--библиотека для добавления масок ввода(например, для дат: __.__._____)--%>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.maskedinput.js"></script>
    <script type="text/javascript"
            src="${initParam.resourcesRealPath}/scripts/dropdown_menu_hack.js"></script>
    <script type="text/javascript"
            src="${initParam.resourcesRealPath}/scripts/${phiz:cryptoProviderName()}Crypto.js"></script>
    <script type="text/javascript">setRSCryptoName('${phiz:cryptoProviderName()}');</script>

    <!--[if IE 6]>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/iepngfix_tilebg.min.js"></script>
	    <link type="text/css" rel="stylesheet" href="${skinUrl}/ie.css"/>
    <![endif]-->
    <%@ include file="/WEB-INF/jsp/common/script-vaultonline.jsp"  %>
</head>
<body>
<tiles:insert definition="googleTagManager"/>
<%-- Блок "подождите" --%>
<div id="loading" style="left:-3300px;">
    <div id="loadingImg"><img src="${imagePath}/ajax-loader64.gif" alt=""/></div>
    <span>Пожалуйста, подождите,<br/> Ваш запрос обрабатывается.</span>
</div>
<div id="tinted" class="opacity25"></div>
<div id="tintedNet"></div>


    <div id="loginPage">
        <div id="header">
            <div class="hdrContainer">
                <div id="profileHdr">
                    <div class="profileHdrLeftBrdr">
                        <div class="profileHdrRightBrdr">
                            <div class="phones">
                                <span>Справочная служба: </span><span>+7 (495) 500 5550</span><span>8 (800) 555 5550</span>
                            </div>
                        </div>
                     </div>
                    <div class="clear"></div>
                </div>
                <div class="logo">
                    <a href="${phiz:calculateActionURL(pageContext, "/private/accounts.do")}"><img src="${imagePath}/sbrf_online_logo.png" alt=""/></a>
                </div>
            </div>
        </div>
    </div>
    <div id="wrapper">
        <div id="content">
            <div class="Login" id="newsDiv" onkeypress="onEnterKey(event);">
                <tiles:insert attribute="data"/>
            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/jsp-sbrf/common/layout/footer.jsp" %>
</body>
</html>