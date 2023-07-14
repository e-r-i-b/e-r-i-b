<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/tags/mobile" prefix="mobile" %>

<html:form action="/private/payments/servicesPayments/edit" >
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="provider" value="${form.serviceProvider}"/>

    <tiles:insert definition="payment">
        <tiles:put name="data" type="string">
            <initialData>
                <form>RurPayJurSB</form>
                <RurPayJurSB>
                    <operationUID>${form.operationUID}</operationUID>
                    <fields>
                        <c:set var="isByTemplate" value="${not empty form.template and form.template > 0}"/>
                        <c:set var="isOurProvider" value="${not empty form.serviceProvider}"/>
                        <c:set var="isItunesProvider" value="${phiz:isITunesProvider(form.serviceProvider.synchKey, form.serviceProvider.code)}"/>
                        <c:if test="${isOurProvider and not empty form.serviceProvider.groupRisk}">
                            <c:set var="groupRiskRank" value="${form.serviceProvider.groupRisk.rank}"/>
                        </c:if>
                        <c:if test="${isItunesProvider}">
                            <field>
                                <mobile:fieldBody
                                    name="field(itunesAgreement)"
                                    title="Соглашение с условиями покупки"
                                    type="list"
                                    required="false"
                                    editable="true"
                                    visible="true"
                                    isSum="false">
                                    <mobile:fieldListItem value="itunesAgreementText" title="${phiz:getItunesAgreementATM()}"/>
                                </mobile:fieldBody>
                            </field>
                        </c:if>
                        <c:forEach var="field" items="${provider.fieldDescriptions}">
                            <c:if test="${field.type != 'choice'}">
                                <c:set var="editable" value="${field.editable and (not isByTemplate or field.mainSum or not isOurProvider or groupRiskRank=='LOW')}"/>
                                <c:set var="value">
                                    <c:choose>
                                        <%--специально для костылей в АТМ (CHG087529)--%>
                                        <c:when test="${isItunesProvider and field.mainSum and not isByTemplate and not form.copying}">
                                               ${null}
                                        </c:when>
                                        <c:otherwise>
                                               ${form.fields[field.externalId]}
                                        </c:otherwise>
                                    </c:choose>
                                </c:set>
                                <field>
                                    <mobile:fieldBody
                                            name="field(${field.externalId})"
                                            title="${field.name}"
                                            description="${field.description}"
                                            hint="${field.hint}"
                                            type="${field.type}"
                                            maxLength="${field.maxLength}"
                                            minLength="${field.minLength}"
                                            required="${field.required}"
                                            editable="${editable}"
                                            visible="${field.visible}"
                                            isSum="${field.mainSum}"
                                            value="${value}"
                                            defaultValue="${field.defaultValue}"
                                            subType="${field.businessSubType}">
                                        <c:if test="${not empty field.values}">
                                            <c:forEach var="valueItem" items="${field.values}">
                                                <mobile:fieldListItem value="${valueItem.id}" title="${valueItem.value}"/>
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${not empty field.fieldValidationRules}">
                                            <c:forEach var="validationRule" items="${field.fieldValidationRules}">
                                                <c:set var="parameter" value="${validationRule.parameters}"/>
                                                <c:forEach items="${parameter}" var="entry">
                                                    <c:if test="${entry.key == validationRule.fieldValidationRuleType}">
                                                        <c:set var="parameter" value="${entry.value}"/>
                                                    </c:if>
                                                </c:forEach>
                                                <mobile:validatorItem type="${validationRule.fieldValidationRuleType}" message="${validationRule.errorMessage}" parameter="${parameter}"/>
                                            </c:forEach>
                                        </c:if>
                                    </mobile:fieldBody>
                                </field>
                            </c:if>
                        </c:forEach>
                    </fields>
                    <availableFromResources>
                        <mobile:fieldBody
                                name="fromResource"
                                title="Оплата с"
                                type="resource" required="true" editable="true" visible="true"
                                value="${form.fromResource}">
                            <c:forEach var="link" items="${form.chargeOffResources}">
                                <c:set var="balance">
                                    <c:if test="${not empty link.rest}">
                                        <c:out value="${phiz:formatDecimalToAmount(link.rest.decimal, 2)}"/>
                                    </c:if>
                                </c:set>
                                <c:choose>
                                    <c:when test="${link.resourceType == 'CARD'}">
                                        <c:set var="displayedValue">
                                            <c:out value="${phiz:getCutCardNumber(link.number)} [${link.name}] ${balance} ${phiz:getCurrencySign(link.currency.code)}"/>
                                        </c:set>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="displayedValue">
                                            <c:out value="${phiz:getFormattedAccountNumber(link.number)} [${link.name}] ${balance} ${phiz:getCurrencySign(link.currency.code)}"/>
                                        </c:set>
                                    </c:otherwise>
                                </c:choose>
                                <mobile:fieldListItem value="${link.code}" title="${displayedValue}" link="${link}"/>
                            </c:forEach>
                        </mobile:fieldBody>
                    </availableFromResources>
                    <c:set var="isIQWProvider" value="${phiz:isIQWProvider(provider)}"/>
                    <c:set var="autoPaymentSupported" value="${provider.autoPaymentSupportedInATM and
                        ( (isIQWProvider and phiz:impliesServiceRigid('CreateAutoPaymentPayment'))
                        or (not isIQWProvider and phiz:impliesServiceRigid('ClientCreateAutoPayment') ))}"/>
                    <autoPaymentSupported>${autoPaymentSupported}</autoPaymentSupported>

                    <documentNumber>
                        <mobile:fieldBody
                                name="documentNumber"
                                title="Номер документа"
                                type="integer"
                                required="true"
                                editable="false"
                                visible="true"
                                value="${form.document.documentNumber}">
                        </mobile:fieldBody>
                   </documentNumber>

                </RurPayJurSB>
            </initialData>
        </tiles:put>
    </tiles:insert>

</html:form>
