<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251"/>
    <meta name="format-detection" content="telephone=no">
    <title>
        <bean:message bundle="commonBundle" key="application.title"/>
    </title>

    <link rel="icon" type="image/x-icon" href="${skinUrl}/skins/sbrf/images/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${skinUrl}/skins/sbrf/csa/reg.css"/>
    <link rel="stylesheet" type="text/css" href="${skinUrl}/skins/commonSkin/roundBorder.css"/>
    <link rel="stylesheet" type="text/css" href="${skinUrl}/skins/sbrf/roundBorder.css"/>
    <c:if test="${csa:isModernCSS()}">
        <link rel="stylesheet" type="text/css" href="${skinUrl}/skins/sbrf/csa/modern.css"/>
    </c:if>
    <link rel="stylesheet" type="text/css" href="${skinUrl}/skins/sbrf/csa/style.css"/>

    <script type="text/javascript">
        document.webRoot='/${phiz:loginContextName()}';
        var skinUrl = '${skinUrl}';
        var globalUrl = '${skinUrl}/skins';
    </script>
    <script type="text/javascript" src="${skinUrl}/scripts/csa/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/Utils.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/DivFloat.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/csa/reg.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/windows.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/cookies.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/PaymentsFormHelp.js"></script>
    <%@ include file="/WEB-INF/jsp/common/script-vaultonline-csa.jsp"  %>
</head>

