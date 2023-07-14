<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--  нопки выбора стратегии подтверждени€: sms, card, none. --%>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<c:if test="${not empty form.response.confirmStage.confirmType}">
    <c:set var="confirmType" value="${form.response.confirmStage.confirmType}"/>
</c:if>

<c:if test="${not empty confirmType}">
                <html:submit property="operation" value="confirm" onclick="postToConfirm();"/>
</c:if>

<script type="text/javascript">
    function postToConfirm()
    {
        var confirmUrl = "${phiz:calculateActionURL(pageContext, '/atm/confirmPayment')}";
        document.forms[0].action = confirmUrl;
    }
</script>