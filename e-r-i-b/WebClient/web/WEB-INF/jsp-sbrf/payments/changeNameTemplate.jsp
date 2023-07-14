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
    <c:set var="newTemplateName" value="${form.fields['newTemplateName']}"/>
    "newTemplateName" : "${fn:replace(newTemplateName, "\"", "\\\"")}",
    "errors" : [
                <phiz:messages  id="errorMessage" field="field" message="error" bundle="commonBundle">
                    <c:if test="${noFirst == 'true'}">,</c:if>
                    <c:set var="curError" value="${phiz:replaceNewLine(errorMessage,'')}"/>
                    <c:if test="${fn:length(curError) > 0}">
                        "${curError}"
                    </c:if>
                    <c:set var="noFirst" value="true"/>
                </phiz:messages>
    ]
}

