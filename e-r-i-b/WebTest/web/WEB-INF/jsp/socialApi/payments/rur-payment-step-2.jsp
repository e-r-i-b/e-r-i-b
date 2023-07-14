<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Перевод частному лицу</title>
</head>

<body>
<h1>Перевод частному лицу</h1>

<html:form action="/socialApi/rurPayment" show="true">
    <tiles:insert definition="social" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>
        <tiles:put name="data">
            <%@include file="rur-payment-document.jsp"%>
            <c:set var="curForm" value="${phiz:currentForm(pageContext)}"/>

            <c:if test="${not empty curForm.response.confirmStage.confirmInfo}">
                <c:set var="type" value="${curForm.response.confirmStage.confirmInfo.type}"/>
                <c:choose>
                    <c:when test="${type == 'smsp'}">
                        <html:text property="confirmSmsPassword"/>
                    </c:when>
                    <c:when test="${type == 'cardp'}">
                        <html:text property="confirmCardPassword"/>
                    </c:when>
                </c:choose>

                <c:choose>
                    <c:when test="${type == 'smsp' and (curForm.response.confirmStage.confirmInfo.smsp.attemptsRemain == 0
                        or curForm.response.confirmStage.confirmInfo.smsp.lifeTime <= 0)}">
                        <input type="button" value="новый SMS-пароль" onclick="newPassword();"/>
                    </c:when>
                    <c:otherwise>
                        <html:submit property="operation" value="confirm" onclick="postToConfirm();"/>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
<script type="text/javascript">
    function postToConfirm()
    {
        var confirmUrl = "${phiz:calculateActionURL(pageContext, '/socialApi/confirmPayment')}";
        document.forms[0].action = confirmUrl;
    }

    function newPassword()
    {
        document.forms[0].action = '${confirmUrl}?id=${response.document.id}&operation=confirmSMS';
        document.forms[0].submit();
    }
</script>
</body>
</html:html>