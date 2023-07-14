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

<c:set var="currentUrl" value="/mobileApi/confirmPayment"/>
<c:set var="confirmUrl" value="${phiz:calculateActionURL(pageContext, currentUrl)}"/>
<html:form action="${currentUrl}" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="response" value="${form.response}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/confirm.do"/>
        <c:if test="${not empty response.document.form}">
            <tiles:put name="formName" value="${response.document.form}"/>
        </c:if>

        <tiles:put name="data">
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
                            <input type="button" value="confirm" onclick="sendConfirm();"/>
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
                document.forms[0].action = '${confirmUrl}?id=${response.document.id}&operation=confirmSMS';
                document.forms[0].submit();
            }

            function sendConfirm()
            {
                document.forms[0].action = '${confirmUrl}?id=${response.document.id}&operation=confirm';
                document.forms[0].submit();
            }
        </script>
    </tiles:insert>
</html:form>

</body>
</html:html>
