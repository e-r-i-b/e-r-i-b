<%--
  Created by IntelliJ IDEA.
  User: tisov
  Date: 29.06.15
  Time: 19:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:form action="/fraud/async/confirm">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="fraudUrl" value="/fraud/async/confirm"/>
    <h1>Подтверждение вердикта по документу</h1>

    <div>
        <c:choose>
            <c:when test="${form.verdict == 'accept'}">
                <c:set var="messageTitle" value="Заполните причину подтверждения документа"/>
            </c:when>
            <c:when test="${form.verdict == 'refuse'}">
                <c:set var="messageTitle" value="Заполните причину октаза документа"/>
            </c:when>
            <c:otherwise>
                <c:set var="messageTitle" value="Заполните причину"/>
            </c:otherwise>
        </c:choose>
        <c:if test="${phiz:isOfficerTextActive() || form.verdict != 'accept'}">
            <div class="body">
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title" value="${messageTitle}"/>
                    <tiles:put name="data">
                        <input type="text" size="50" name="verdictText"/>
                    </tiles:put>
                </tiles:insert>
            </div>
        </c:if>
        <div class="floatRight marginTop20">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.continue"/>
                <tiles:put name="commandHelpKey" value="button.continue.help"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="onclick" value="confirmVerdict()"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="onclick" value="closeFraudWindow()"/>
            </tiles:insert>
        </div>

        <script type="text/javascript">
            function confirmVerdict()
            {
                var actionUrl = "${phiz:calculateActionURL(pageContext, fraudUrl)}";
                var verdictText = $("input[name='verdictText']")[0].value;
                var form = document.createElement("form");
                form.setAttribute("method", "post");
                form.setAttribute("action", actionUrl + "?"
                        +       "operation"    +   "=" +    "button.continue"
                        + "&" + "verdict"      +   "=" +    "${form.verdict}"
                        + "&" + "documentId"   +   "=" +    "${form.documentId}"
                        + "&" + "verdictText"  +   "=" +    verdictText
                        + "&" + "person"       +   "=" +    "${form.person}"
                        + "&" + "PAGE_TOKEN"   +   "=" +    "${sessionScope['PAGE_TOKEN']}");
                document.body.appendChild(form);
                form.submit();
            }
        </script>
    </div>
</html:form>

