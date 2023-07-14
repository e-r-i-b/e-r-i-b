<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Подтверждение платежа</title>
</head>

<body>
<h1>Подтверждение</h1>

<c:set var="currentUrl" value="/atm/confirmPayment"/>
<c:set var="confirmUrl" value="${phiz:calculateActionURL(pageContext, currentUrl)}"/>
<html:form action="${currentUrl}" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/payments/confirm.do"/>

        <tiles:put name="data">
            <c:set var="response" value="${form.response}"/>

            <table>
                <tr>
                    <td>id:</td>
                    <td><html:text property="id" value="${response.document.id}" disabled="true"/></td>
                </tr>
                <tr>
                    <td>form:</td>
                    <td><input type="text" value="${response.document.form}" disabled/></td>
                </tr>
                <tr>
                    <td>document status:</td>
                    <td><input type="text" value="${response.document.status}" disabled/></td>
                </tr>
            </table>
            <c:choose>
                <%--пришел confirmInfo => инициирован один из способов подтверждения--%>
                <c:when test="${not empty response.confirmStage.confirmInfo}">
                    <c:set var="type" value="${response.confirmStage.confirmInfo.type}"/>
                    <c:choose>
                        <c:when test="${type == 'smsp'}">
                            <html:text property="confirmSmsPassword"/>
                        </c:when>
                        <c:when test="${type == 'cardp'}">
                            <html:text property="confirmCardPassword"/>
                        </c:when>
                    </c:choose>
                    <c:choose>
                        <%--для смс-подтверждения в тестилке кнопку "получить новый смс-пароль" отображаем только если
                            кончились попытки ввода пароля или время жизни пароля истекло--%>
                        <c:when test="${type == 'smsp' and (response.confirmStage.confirmInfo.smsp.attemptsRemain == 0
                            or response.confirmStage.confirmInfo.smsp.lifeTime <= 0)}">
                            <input type="button" value="новый SMS-пароль" onclick="newPassword();"/>
                        </c:when>
                        <c:otherwise>
                            <html:submit property="operation" value="confirm"/>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <%--иначе (произошла ошибка при подтверждении, и страница подтверждения отображается повторно) - выведем еще раз кнопки для подтверждения--%>
                <c:otherwise>
                    <tiles:insert page="confirm-payment-buttons.jsp" flush="false"/>
                </c:otherwise>
            </c:choose>
        </tiles:put>
        <script type="text/javascript">
            function newPassword()
            {
                document.forms[0].action = ${confirmUrl};
                document.forms[0].submit();
            }
        </script>
    </tiles:insert>

    <c:set var="formName" value="${response.document.form}"/>
    <c:set var="url">
        <c:choose>
            <c:when test="${formName eq 'RurPayJurSB'}">/atm/servicePayment.do</c:when>
            <c:when test="${formName eq 'JurPayment'}">/atm/jurPayment.do</c:when>
            <c:when test="${formName eq 'InternalPayment'}">/atm/internalPayment.do</c:when>
            <c:when test="${formName eq 'RurPayment'}">/atm/rurPayment.do</c:when>
        </c:choose>
    </c:set>
    <c:if test="${not empty url}">
        <c:url var="paymentUrl" value="${url}">
            <c:param name="url" value="${form.url}"/>
            <c:param name="cookie" value="${form.cookie}"/>
            <c:param name="proxyUrl" value="${form.proxyUrl}"/>
            <c:param name="proxyPort" value="${form.proxyPort}"/>
        </c:url>
        <html:link href="${paymentUrl}">Начать сначала</html:link>
    </c:if>
</html:form>

</body>
</html:html>
