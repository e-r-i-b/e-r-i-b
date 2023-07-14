<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="mobile" uri="http://rssl.com/tags/mobile" %>

<html:form action="/private/favourite/list/templates">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    
    <tiles:insert definition="iphone">
        <tiles:put name="data" type="string">
            <sl:collection id="resultset" property="templates" model="xml-list" title="templates">
                <sl:collectionItem title="template">
                    <c:set var="formName" value="${mobile:getFormNameByFormType(resultset[5])}"/>
                    <c:set var="supported" value="${empty formName ? false : fn:contains(form.supportedForms, formName)}"/>
                    <available>${resultset[0] and supported}</available>
                    <status>${resultset[1]}</status>
                    <templateId>${resultset[2]}</templateId>
                    <templateName><c:out value="${resultset[3]}"/></templateName>
                    <c:set var="type" value="${resultset[4]}"/>
                    <templateType>${type}</templateType>
                    <c:if test="${type == 'payment'}">
                        <form>${formName}</form>
                    </c:if>
                    <c:if test="${form.mobileApiVersion >= 5.10}">
                        <c:set var="amount"   value="${resultset[6]}"/>
                        <c:set var="currency" value="${resultset[7]}"/>
                        <c:if test="${not empty amount and not empty currency}">
                            <tiles:insert definition="mobileMoneyType" flush="false">
                                <tiles:put name="name" value="amount"/>
                                <tiles:put name="decimal" value="${amount}"/>
                                <tiles:put name="currencyCode" value="${currency}"/>
                            </tiles:insert>
                        </c:if>
                    </c:if>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>