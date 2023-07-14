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
            <sl:collection id="template" property="templates" model="xml-list" title="templates">
                <sl:collectionItem title="template">
                    <c:set var="formName" value="${template.formType.name}"/>
                    <c:set var="supported" value="${empty formName ? false : fn:contains(form.supportedForms, formName)}"/>
                    <available>${template.activityInfo.availablePay and supported}</available>
                    <status>${template.state.code}</status>
                    <templateId>${template.id}</templateId>
                    <templateName><c:out value="${template.templateInfo.name}"/></templateName>
                    <c:set var="type" value="${mobile:getAPITemplateType(template)}"/>
                    <templateType>${type}</templateType>
                    <c:if test="${type == 'payment'}">
                        <form>${formName}</form>
                    </c:if>
                    <c:set var="exactAmount" value="${template.exactAmount}"/>
                    <c:if test="${form.mobileApiVersion >= 5.10 && exactAmount != null}">
                        <c:set var="amount"   value="${exactAmount.decimal}"/>
                        <c:set var="currency" value="${exactAmount.currency.code}"/>
                        <c:if test="${not empty amount and not empty currency}">
                            <tiles:insert definition="mobileMoneyType" flush="false">
                                <tiles:put name="name" value="amount"/>
                                <tiles:put name="decimal" value="${amount}"/>
                                <tiles:put name="currencyCode" value="${currency}"/>
                            </tiles:insert>
                        </c:if>
                    </c:if>

                    <c:if test="${phiz:getApiVersionNumber().solid >= 910}">
                        <hasReminder>${not empty template.reminderInfo}</hasReminder>
                    </c:if>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>