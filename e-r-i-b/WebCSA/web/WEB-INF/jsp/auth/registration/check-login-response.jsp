<%@ page contentType="application/json;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<c:set var="form" value="${RegistrationForm}"/>
<c:if test="${form.state == 'success'}">
    <tiles:insert definition="jSonResponseSuccess">
    </tiles:insert>
</c:if>
<c:if test="${form.state == 'fail'}">
    <csa:messages  id="errorMessage" field="field" message="error" bundle="commonBundle">
        <c:set var="fieldName" value="${field}"/>
        <c:set var="message" value="${csa:replaceNewLine(errorMessage, ' ' )}"/>
    </csa:messages>

    <tiles:insert definition="jSonResponseFail">
        <tiles:put name="errorType" value="error"/>
        <c:if test="${not empty fieldName}">
            <tiles:put name="name" value="${fieldName}"/>
            <tiles:put name="value" value="${form.fields[fieldName]}"/>
        </c:if>
        <tiles:put name="text" value="${message}"/>
        <tiles:put name="token" value="${sessionScope['org.apache.struts.action.TOKEN']}"/>
    </tiles:insert>
</c:if>