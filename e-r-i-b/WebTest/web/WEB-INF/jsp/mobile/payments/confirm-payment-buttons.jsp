<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%-- Кнопки выбора стратегии подтверждения: sms, card, none. --%>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="response" value="${form.response}"/>
<c:if test="${not empty form.response.confirmStage.confirmType}">
    <c:set var="confirmType" value="${form.response.confirmStage.confirmType}"/>
</c:if>

<c:if test="${not empty confirmType}">
    <c:forEach var="strategy" items="${confirmType.strategy}">
        <c:set var="type" value="${strategy.type}"/>
        <c:choose>
            <c:when test="${type == 'smsp'}">
                <html:submit property="operation" value="confirmSMS" onclick="postToConfirm();"/>
            </c:when>
            <c:when test="${type == 'pushp'}">
               <html:submit property="operation" value="confirmPush" onclick="postToConfirm();"/>
           </c:when>
            <c:when test="${type == 'cardp'}">
                <html:submit property="operation" value="confirmCard" onclick="postToConfirm();"/>
            </c:when>
            <c:when test="${type == 'none'}">
                <c:if test="${form.editSupported}">
                    <html:submit property="operation" value="edit"/>
                </c:if>

                <html:submit property="operation" value="confirm" onclick="postToConfirm();"/>
            </c:when>
            <c:when test="${type == 'deny'}">
                Подтверждение запрещено!<br/>
                все равно отправить запрос на подтверждение: <html:submit property="operation" value="confirm" onclick="postToConfirm();"/> //malicious user emulation
            </c:when>
            <c:otherwise>
                Неизвестный тип стратегии подтверждения!
            </c:otherwise>
        </c:choose>
    </c:forEach>
</c:if>

<script type="text/javascript">
    function postToConfirm()
    {
        var confirmUrl = "${phiz:calculateActionURL(pageContext, '/mobileApi/confirmPayment')}?id=${response.document.id}";
        document.forms[0].action = confirmUrl;
    }
</script>