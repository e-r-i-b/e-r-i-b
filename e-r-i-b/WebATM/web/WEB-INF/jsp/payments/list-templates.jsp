<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="mobile" uri="http://rssl.com/tags/mobile" %>

<html:form action="/private/favourite/list/templates">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm">
        <tiles:put name="data" type="string">
            <sl:collection id="template" property="templates" model="xml-list" title="templates">
                <sl:collectionItem title="template">
                    <c:set var="formName" value="${template.formType.name}"/>
                    <c:set var="supported" value="${empty formName ? false : fn:contains(form.supportedForms, formName)}"/>
                    <c:choose>
                        <c:when test="${template.activityInfo.availablePay and supported}">
                            <status>available</status>
                        </c:when>
                        <c:otherwise>
                            <status>notAvailable</status>
                        </c:otherwise>
                    </c:choose>
                    <templateId>${template.id}</templateId>
                    <templateName><c:out value="${template.templateInfo.name}"/></templateName>
                    <c:set var="type" value="${mobile:getAPITemplateType(template)}"/>
                    <templateType>${type}</templateType>
                    <c:if test="${type == 'payment'}">
                        <form>${formName}</form>
                    </c:if>
                    
                    <c:set var="exactAmount" value="${template.exactAmount}"/>
                    <c:if test="${not empty exactAmount}">
                        <tiles:insert definition="atmMoneyType" flush="false">
                            <tiles:put name="name" value="amount"/>
                            <tiles:put name="decimal" value="${exactAmount.decimal}"/>
                            <tiles:put name="currencyCode" value="${exactAmount.currency.code}"/>
                        </tiles:insert>
                    </c:if>
                    <created>
                        <c:if test="${not empty template.clientCreationDate}">
                            ${mobile:calendarToString('%1$td.%1$tm.%1$tY', template.clientCreationDate)}
                        </c:if>
                    </created>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>
