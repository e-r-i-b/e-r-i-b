<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib prefix="mobile" uri="http://rssl.com/tags/mobile" %>

<html:form action="/private/basket/subscription/view">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="invoice" value="${form.invoiceSubscription}"/>
    <c:set var="state" value="${invoice.state}"/>
    <c:set var="accountingEntityName" value="${form.accountingEntityName}"/>
    <c:set var="isRecoveryAvailable" value="${state == 'STOPPED'}"/>
    <c:set var="isEmptyAutoPayId" value="${invoice.autoPayId == null || invoice.autoPayId == ''}"/>
    <c:set var="requisites" value="${invoice.requisitesAsList}"/>
    <c:set var="serviceCategory" value="${phiz:getServiceCategoryByCode(invoice.codeService)}"/>
    <c:set var="errorInfo" value="${invoice.errorInfo}"/>
    <c:set var="isExistNotKeyField" value="false"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <invoice>
                <id>${form.id}</id>

                <name>${invoice.name}</name>

                <recName>${invoice.recName}</recName>

                <nameService>${invoice.nameService}</nameService>

                <state>${invoice.state}</state>

                <card>${phiz:getCutCardNumber(invoice.chargeOffCard)}</card>

                <billName>
                    <c:choose>
                        <c:when test="${not empty serviceCategory}">
                            <c:out value="${serviceCategory.accountName}"/>
                        </c:when>
                        <c:otherwise>
                            Другие услуги
                        </c:otherwise>
                    </c:choose>
                </billName>

                <group>
                    <c:choose>
                        <c:when test="${not empty accountingEntityName}">
                            <c:out value="${accountingEntityName}"/>
                        </c:when>
                        <c:otherwise>
                            Другие услуги
                        </c:otherwise>
                    </c:choose>
                </group>

                <billDate>${phiz:createInvoiceInfo(invoice)}</billDate>

                <c:if test="${errorInfo != null && errorInfo.type == 'FORM'}">
                    <errorInfo>${errorInfo.text}</errorInfo>
                </c:if>

                <isRecoverAutoSubscription>
                    <c:out value="${not empty invoice.autoSubExternalId}"/>
                </isRecoverAutoSubscription>

                <%-- реквизиты --%>

                <requisites>

                    <keyFields>
                        <c:forEach var="field" items="${requisites}">
                            <c:choose>
                                <c:when test="${field.key}">
                                    <field>
                                        <c:set var="fieldValue" value="${empty field.value ? field.defaultValue : field.value}"/>
                                        <mobile:fieldBody
                                                name="field(${field.externalId})"
                                                title="${field.name}"
                                                description="${field.description}"
                                                hint="${field.hint}"
                                                type="${field.type}"
                                                maxLength="${field.maxLength}"
                                                minLength="${field.minLength}"
                                                required="${field.required}"
                                                editable="${field.editable}"
                                                visible="${field.visible}"
                                                isSum="${field.mainSum}"
                                                value="${phiz:getMaskedValue(field.mask, field.value)}">
                                            <c:if test="${not empty field.values}">
                                                <c:forEach var="valueItem" items="${field.values}">
                                                    <mobile:fieldListItem value="${valueItem.id}" title="${valueItem.value}"/>
                                                </c:forEach>
                                            </c:if>
                                        </mobile:fieldBody>
                                    </field>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="isExistNotKeyField" value="true"/>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </keyFields>

                    <notKeyField>
                        <c:if test="${isExistNotKeyField}">
                            <c:forEach var="field" items="${requisites}">
                                <c:if test="${not field.key}">
                                    <field>
                                        <c:set var="fieldValue" value="${empty field.value ? field.defaultValue : field.value}"/>
                                        <mobile:fieldBody
                                                name="field(${field.externalId})"
                                                title="${field.name}"
                                                description="${field.description}"
                                                hint="${field.hint}"
                                                type="${field.type}"
                                                maxLength="${field.maxLength}"
                                                minLength="${field.minLength}"
                                                required="${field.required}"
                                                editable="${field.editable}"
                                                visible="${field.visible}"
                                                isSum="${field.mainSum}"
                                                value="${phiz:getMaskedValue(field.mask, field.value)}">
                                            <c:if test="${not empty field.values}">
                                                <c:forEach var="valueItem" items="${field.values}">
                                                    <mobile:fieldListItem value="${valueItem.id}" title="${valueItem.value}"/>
                                                </c:forEach>
                                            </c:if>
                                        </mobile:fieldBody>
                                    </field>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </notKeyField>

                </requisites>

            </invoice>
        </tiles:put>
    </tiles:insert>
</html:form>
