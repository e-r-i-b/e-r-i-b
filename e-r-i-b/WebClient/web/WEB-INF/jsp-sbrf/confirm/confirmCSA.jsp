<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/payments/confirmCSA" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="listFormName" value="${form.metadata.listFormName}"/>
    <tiles:insert definition="paymentCurrent">
        <tiles:put name="mainmenu" value="Info"/>
        <tiles:put name="submenu" type="string" value="${listFormName}"/>
        <tiles:put name="pageTitle" type="string">
            <c:out value="${form.metadata.form.description}"/>
        </tiles:put>
        <tiles:put name="menu" type="string"/>
        <tiles:put name="data" type="string">
            <iframe class="MaxSize" frameborder="0" src="${form.CSAPath}"></iframe>
        </tiles:put>
    </tiles:insert>
</html:form>

