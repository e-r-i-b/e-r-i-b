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
<c:set var="image" value="${globalUrl}/commonSkin/images"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>

    <c:if test="${empty pageTitle}">
        <c:set var="pageTitle" value="Единый центр аутентификации: ввод логина и пароля"/>
    </c:if>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251"/>
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <title>${pageTitle}</title>
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

    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/DivFloat.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Utils.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Regions.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/select.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/customPlaceholder.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Templates.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/PaymentsFormHelp.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/commandButton.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/clientButton.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/clientButtonUtil.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/builder.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/iframerequest.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/layout.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/windows.js"></script>
    <script type="text/javaScript" src="${initParam.resourcesRealPath}/scripts/cookies.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/longOffer.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/dropdown_menu_hack.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/customPlaceholder.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/serializeToWin.js"></script>

    <c:if test="${needCrypto == 'true'}">
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Crypto.js"></script>
        <script type="text/javascript"
                src="${initParam.resourcesRealPath}/scripts/${phiz:cryptoProviderName()}Crypto.js"></script>
        <script type="text/javascript">setRSCryptoName('${phiz:cryptoProviderName()}');</script>
    </c:if>

    <!--[if IE 6]>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/iepngfix_tilebg.js"></script>
	    <link type="text/css" rel="stylesheet" href="${globalUrl}/commonSkin/ie.css"/>
	    <link type="text/css" rel="stylesheet" href="${skinUrl}/ie.css"/>
    <![endif]-->
    <%@ include file="/WEB-INF/jsp/common/script-vaultonline.jsp"  %>

</head>
<body>
<tiles:insert definition="googleTagManager"/>
<div id="pageContent">
    <div class="main_container">
        <div class="pageBackground">
            <div class="pageContent">
                <%-- Блок "подождите" --%>
                <div id="loading" style="left:-3300px;">
                    <div id="loadingImg"><img src="${imagePath}/ajax-loader64.gif" alt=""/></div>
                    <span>Пожалуйста, подождите,<br/> Ваш запрос обрабатывается.</span>
                </div>
                <div id="tinted" class="opacity25"></div>
                <div id="tintedNet"></div>

                    <html:form show="true">
                        <div id="loginPage">
                            <div id="header">
                                <div class="roundTL">
                                    <div class="roundTR">
                                        <div class="hdrContainer">
                                            <div class="NewHeader">
                                                <div class="Logo">
                                                    <a class="logoImage logoImageText"
                                                       href="${phiz:calculateActionURL(pageContext, "/private/accounts.do")}"
                                                       onclick="return redirectResolved();">
                                                        <img src="${image}/logoHeader.png" alt="">
                                                    </a>
                                                </div>
                                                <div class="PhoneNambers">
                                                    <span>+7 (495) <span>500-55-50</span><span style="display:none;">_</span></span><br />
                                                    <span>8 (800) <span>555-55-50</span><span style="display:none;">_</span></span>
                                                </div>
                                            </div>

                                            <div class="topLineContainer">
                                                <div class="UserInfo">
                                                    <c:if test="${needHelp == 'true'}">
                                                        <c:set var="helpLink" value="${phiz:calculateActionURL(pageContext,'/help.do?id=')}${$$helpId}"/>
                                                        <a class="headHelpLink" href="#" onclick="openHelp('${helpLink}'); return false;" title="Открыть справку по системе" alt="Открыть справку по системе">
                                                            <span>Помощь</span>
                                                        </a>
                                                    </c:if>

                                                    <c:if test="${headerGroup == 'true'}">
                                                        <a onclick="return redirectResolved();"
                                                           href="${phiz:calculateActionURL(pageContext, "/logoff.do")}"
                                                           title="безопасный выход из системы" class="saveExit">
                                                            <div id="exit" alt="безопасный выход из системы"></div>
                                                            <span>Выход</span>
                                                        </a>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div id="wrapper">
                            <div id="content" class="noRightSection <c:if test="${centerContent == 'true'}">udboDataWidth</c:if>">
                                <div class="Login" id="LoginDiv">
                                    <tiles:insert attribute="data"/>
                                </div>
                            </div>
                        </div>
                    </html:form>

            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp-sbrf/common/layout/footer.jsp" %>
</body>
</html>