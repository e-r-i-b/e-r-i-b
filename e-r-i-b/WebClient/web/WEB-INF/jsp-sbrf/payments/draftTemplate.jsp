<%@ page contentType="text/html;charset=windows-1251" language="java"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${EditTemplateForm}"/>
{
    <phiz:messages  id="errorMessage" field="field" message="error" bundle="commonBundle">
        <c:choose>
            <c:when test="${field != null}">
                <c:set var="errorFields">${errorFields}
                    <c:if test="${noFirstErrorField == 'true'}">,</c:if>
                    <c:set var="cOutErrorMessage">
                        <c:out value="${errorMessage}"/>
                    </c:set>
                    <c:set var="curErrorField" value="${phiz:replaceNewLine(cOutErrorMessage,'<br>')}"/>
                    {
                        "name" : "<bean:write name='field' filter='false'/>",
                        "value": "${phiz:escapeForJS(curErrorField, false)}"
                    }
                </c:set>
                <c:set var="noFirstErrorField" value="true" scope="page"/>
            </c:when>
            <c:otherwise>
                <c:set var="errors">${errors}
                    <c:if test="${noFirstError == 'true'}">,</c:if>
                    <c:set var="curError" value="${phiz:replaceNewLine(errorMessage,'<br>')}"/>
                    "${phiz:escapeForJS(curError, false)}"
                </c:set>
                <c:set var="noFirstError" value="true" scope="page"/>
            </c:otherwise>
        </c:choose>
    </phiz:messages>

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

    "errors" : [${errors}],
    "errorFields" : [${errorFields}],
    "messages" : [${messageInfo}]
}