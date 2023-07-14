<%@ page contentType="application/json;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${RegistrationForm}"/>

<csa:messages  id="errorMessage" field="field" message="error" bundle="commonBundle">
    <c:set var="fieldName" value="${field}"/>
    <c:set var="message" value="${phiz:replaceNewLine(errorMessage, ' ' )}"/>
</csa:messages>

<tiles:insert definition="jSonResponseFail">
    <tiles:put name="captcha" value="true"/>
    <%-- если капча была введена с ошибкой, то показываем сообщение --%>
    <c:if test="${not empty fieldName}">
        <tiles:put name="errorType" value="error"/>
        <tiles:put name="name" value="${fieldName}"/>
        <tiles:put name="value" value="${form.fields[fieldName]}"/>
        <tiles:put name="text" value="${message}"/>
        <tiles:put name="token" value="${sessionScope['org.apache.struts.action.TOKEN']}"/>
    </c:if>
</tiles:insert>