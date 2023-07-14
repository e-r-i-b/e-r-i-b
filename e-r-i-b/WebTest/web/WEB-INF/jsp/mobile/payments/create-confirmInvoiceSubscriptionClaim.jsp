<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c" %>
<%@ taglib uri="http://rssl.com/tags"                        prefix="phiz" %>

<c:set var="currentUrl" value="/mobileApi/confirmInvoiceSubscriptionClaim"/>
<c:set var="confirmUrl" value="${phiz:calculateActionURL(pageContext, currentUrl)}"/>

<html:form action="${currentUrl}" show="true">
    <c:set var="form"     value="${phiz:currentForm(pageContext)}"/>
    <c:set var="response" value="${form.response}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/basket/subscriptions/claim.do"/>
        <c:choose>
            <c:when test="${empty response.document.form}">
                <tiles:put name="formName" value="MakeAutoInvoiceSubscriptionService"/>
            </c:when>
            <c:otherwise>
                <tiles:put name="formName" value="${response.document.form}"/>
            </c:otherwise>
        </c:choose>

        <tiles:put name="data">
            <c:choose>
                <c:when test="${empty form.response.confirmStage.confirmType}">
                    <c:set var="confirmType" value="${form.response.confirmStage.confirmType}"/>
                    <table>
                        <tr>
                            <td>Идентификатор автоподписки (id):</td>
                            <td>
                                <input type="text" name="id">
                                <html:submit property="operation" value="init" onclick="postToConfirm();"/>
                            </td>
                        </tr>
                    </table>
                </c:when>
            </c:choose>
        </tiles:put>
    </tiles:insert>


    <script type="text/javascript">
        function postToConfirm()
        {
            <c:choose>
                <c:when test="${not empty response.document}">
                    <c:set var="params" value="?id=${response.document.id}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="params" value=""/>
                </c:otherwise>
            </c:choose>

            document.forms[0].action = "${phiz:calculateActionURL(pageContext, '/mobileApi/confirmInvoiceSubscriptionClaim')}${params}";
        }
    </script>
</html:form>
