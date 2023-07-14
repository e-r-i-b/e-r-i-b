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
                        <c:set var="isOurProvider" value="${not empty provider}"/>
                        <c:if test="${isOurProvider and not empty provider.groupRisk}">
                            <c:set var="groupRiskRank" value="${provider.groupRisk.rank}"/>
                        </c:if>
                        <c:set var="isMobileLimitedScheme" value="${mobile:isMobileLimitedScheme()}"/>
                        <c:set var="isMobileLightScheme" value="${mobile:isMobileLightScheme()}"/>
                        <c:forEach var="field" items="${provider.fieldDescriptions}">
                            <c:if test="${form.createLongOffer != true or field.key == true}">
                                <c:set var="isDictField" value="${not isByTemplate and isOurProvider and (field.businessSubType eq 'phone' or field.businessSubType eq 'wallet')}"/>
                                <c:set var="editable" value="${field.editable and ((not isByTemplate and not(isMobileLightScheme and isDictField and groupRiskRank ne 'LOW')) or field.mainSum
                                        or (isOurProvider and not isMobileLimitedScheme and (groupRiskRank=='LOW' or (not field.saveInTemplate and not field.key)))
                                        or (not isOurProvider and field.type=='money'))}"/>
                                <c:choose>
                                    <c:when test="${field.type == 'choice'}">
                                        <c:set var="fieldType" value="agreement"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="fieldType" value="${field.type}"/>
                                    </c:otherwise>
                                </c:choose>

                                <c:set var="defaultValue" value="${form.fields[field.externalId]}"/>
                                <c:set var="isProviderDefaultValue" value="${false}"/>
                                <c:set var="dictFieldId" value="${form.fields['dictFieldId']}"/>
                                <%-- если берем свое дефолтное значение, то идентификатор в справочнике не актуальный --%>
                                <c:if test="${empty defaultValue}">
                                    <c:set var="defaultValue" value="${phiz:getDefaultValueProviderField(provider.synchKey, provider.code, field.externalId)}"/>
                                    <c:set var="dictFieldId" value="${null}"/>
                                    <c:set var="isProviderDefaultValue" value="${true}"/>
                                </c:if>

                                <c:if test="${isDictField}">
                                    <%-- для дефолтного значения ищем идентификатор в справочнике доверенных получателей --%>
                                    <c:if test="${dictFieldId == null && isProviderDefaultValue}">
                                        <c:set var="dictField" value="${phiz:getDefaultValueDictionary(field.businessSubType, defaultValue)}"/>
                                        <c:set var="defaultValue" value="${((not empty dictField) && phiz:isRequireMasking()) ? phiz:getCutPhoneNumber(defaultValue) : defaultValue}"/>
                                        <c:set var="dictFieldId" value="${not empty dictField ? dictField.id : null}"/>
                                    </c:if>

                                    <field>
                                        <mobile:fieldBody
                                                name="field(dictFieldId)"
                                                title="Получатель"
                                                type="dict"
                                                required="false"
                                                editable="true"
                                                visible="false"
                                                isSum="false"
                                                value="${dictFieldId}"/>
                                    </field>
                                </c:if>
                                <field>
                                    <mobile:fieldBody
                                            name="field(${field.externalId})"
                                            title="${field.name}"
                                            description="${field.description}"
                                            hint="${field.hint}"
                                            type="${fieldType}"
                                            maxLength="${field.maxLength}"
                                            minLength="${field.minLength}"
                                            required="${field.required}"
                                            editable="${editable}"
                                            visible="${field.visible}"
                                            isSum="${field.mainSum}"
                                            extendedDescId="${field.extendedDescId}"
                                            value="${defaultValue}"
                                            fieldDictType="${isDictField ? field.businessSubType : ''}"
                                            fieldInfoType="${isDictField ? field.businessSubType : ''}"
                                            dictTypeFieldName="field(dictFieldId)">
                                        <c:if test="${not empty field.values}">
                                            <c:forEach var="valueItem" items="${field.values}">
                                                <mobile:fieldListItem value="${valueItem.id}" title="${valueItem.value}"/>
                                            </c:forEach>
                                        </c:if>
                                    </mobile:fieldBody>
                                </field>
                            </c:if>
                        </c:forEach>
                    </fields>
                    <tiles:insert page="fromResourceFieldTemplate.jsp" flush="false">
                        <tiles:put name="name" value="fromResource"/>
                        <tiles:put name="title" value="Оплата с"/>
                        <c:if test="${not empty form.chargeOffResources}">
                            <tiles:put name="chargeOffResources" beanName="form" beanProperty="chargeOffResources"/>
                        </c:if>
                        <c:if test="${not empty form.fromResource}">
                            <tiles:put name="value" beanName="form" beanProperty="fromResource"/>
                        </c:if>
                    </tiles:insert>
                    <c:set var="autoPaymentSupported" value="false"/>
                    <c:if test="${not empty provider}">
                        <c:set var="isIQWProvider" value="${phiz:isIQWProvider(provider)}"/>
                        <c:set var="autoPaymentSupported" value="${provider.autoPaymentSupportedInApi and
                            ( (isIQWProvider and phiz:impliesServiceRigid('CreateAutoPaymentPayment'))
                            or (form.mobileApiVersion >= 5.10 and not isIQWProvider and phiz:impliesServiceRigid('ClientCreateAutoPayment') ))}"/>
                    </c:if>
                    <autoPaymentSupported>${autoPaymentSupported}</autoPaymentSupported>
                </RurPayJurSB>
            </initialData>
        </tiles:put>
    </tiles:insert>

</html:form>
