<%--
  User: usachev
  Date: 29.01.15
  Description: Шаблон для страницы, отображающейся после успешной подачи экспресс-заявки
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<c:set var="hasAccount" value="${phiz:hasGuestAnyAccount()}"/>
<c:set var="hasPhoneInMobileBank" value="${phiz:hasGuestPhoneInMB()}"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>Сбербанк Онлайн</title>
    <link rel="icon" type="image/x-icon" href="${globalUrl}/commonSkin/images/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/roundBorder.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/guest.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/sbnkd.css"/>
    <script type="text/javascript" src="${globalUrl}/scripts/DivFloat.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/jquery-1.4.2.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/Utils.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/windows.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/customPlaceholder.js"></script>
    <c:if test="${!(hasAccount || hasPhoneInMobileBank)}">
        <script type="text/javascript">
            var guestPathRegistration = "${phiz:calculateActionURL(pageContext, "guest/registration.do")}";
            var needCaptcha = ${phiz:needShowCaptchaGuestRegistration(pageContext.request)};
            var urlCaptcha = "/${phiz:loginContextName()}/registration/captcha.png";
            var token = "${phiz:getTokenForGuestRegistration(pageContext)}";
        </script>
        <script type="text/javascript" src="${globalUrl}/scripts/guest/guestRegistration.js"></script>
    </c:if>
</head>
<body class="gPage">
    <tiles:insert definition="guestAfterClaimRegistration">
        <tiles:put name="content" value="${content}"/>
        <tiles:put name="topMessage" value="${topMessage}"/>
        <tiles:put name="documentID" value="${documentID}"/>
        <tiles:put name="documentType" value="${documentType}"/>
        <tiles:put name="contentHeader" value="${contentHeader}"/>
        <tiles:put name="showRegBtn" value="${hasPhoneInMobileBank}"/>
        <tiles:put name="showRegistration" value="${hasAccount}"/>
    </tiles:insert>
</body>
</html>