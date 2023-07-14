<%@ page contentType="text/html;charset=windows-1251" language="java"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

{
    <phiz:messages  id="infoMessage" field="field" message="message" bundle="commonBundle">
        <c:set var="messageInfo">${messageInfo}
            <c:if test="${noFirstMessages == 'true'}">,</c:if>
            <c:set var="curInfo" value="${phiz:replaceNewLine(infoMessage,'<br>')}"/>
            <c:if test="${fn:length(curInfo) > 0}">
                "${phiz:escapeForJS(curInfo, false)}"
            </c:if>
        </c:set>
        <c:set var="noFirstMessages" value="true"/>
    </phiz:messages>

    "success" : true,
    "messages" : [${messageInfo}],
    "refresh" : true
}