<%@ page contentType="text/html;charset=windows-1251" language="java"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${ConfirmCreateTemplateForm}"/>
{
    "templateName" : "${phiz:escapeForJS(form.templateName, true)}",

    <c:set var="errorFields" value=""/>
    <c:set var="errors" value=""/>

    <phiz:messages  id="errorMessage" field="field" message="error" bundle="commonBundle">
        <c:set var="cOutErrorMessage">
            <c:out value="${errorMessage}"/>
        </c:set>
        <c:choose>
            <c:when test="${field != null}">
                <c:set var="errorFields">${errorFields}
                    <c:if test="${noFirstErrorField == 'true'}">,</c:if>
                    {
                        "name" : "<bean:write name='field' filter='false'/>",
                        "value": "${phiz:replaceNewLine(cOutErrorMessage,'<br>')}"
                    }
                </c:set>
                <c:set var="noFirstErrorField" value="true" scope="page"/>
            </c:when>
            <c:otherwise>
                <c:set var="errors">${errors}
                    <c:if test="${noFirstError == 'true'}">,</c:if>
                    "${phiz:replaceNewLine(cOutErrorMessage,'<br>')}"
                </c:set>
                <c:set var="noFirstError" value="true" scope="page"/>
            </c:otherwise>
        </c:choose>
    </phiz:messages>

    "errors" : [${errors}],
    "errorFields" : [${errorFields}]
}
