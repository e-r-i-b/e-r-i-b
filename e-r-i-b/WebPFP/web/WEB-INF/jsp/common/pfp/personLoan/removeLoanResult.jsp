<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/async/editPfpLoan">
    <tiles:insert definition="webModulePage">
        <tiles:put name="data">

            <phiz:messages  id="error" bundle="pfpBundle" field="field" message="error">
                <c:set var="errors">${errors}${error}</c:set>
            </phiz:messages>

            <c:choose>
                <c:when test="${not empty errors}">
                    <c:out value="${errors}"/>
                </c:when>
                <c:otherwise>
                    removeSuccessful
                </c:otherwise>
            </c:choose>
        </tiles:put>
    </tiles:insert>
</html:form>