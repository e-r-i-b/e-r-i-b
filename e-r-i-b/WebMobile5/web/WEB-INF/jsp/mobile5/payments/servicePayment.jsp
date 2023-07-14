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
                        <c:forEach var="field" items="${provider.fieldDescriptions}">
                            <c:set var="editable" value="${field.editable and (not isByTemplate or field.mainSum
                                    or (isOurProvider and not isMobileLimitedScheme and (groupRiskRank=='LOW' or (not field.saveInTemplate and not field.key)))
                                    or (not isOurProvider and field.type=='money'))}"/>
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
                                        value="${form.fields[field.externalId]}">
                                    <c:if test="${not empty field.values}">
                                        <c:forEach var="valueItem" items="${field.values}">
                                            <mobile:fieldListItem value="${valueItem.id}" title="${valueItem.value}"/>
                                        </c:forEach>
                                    </c:if>
                                </mobile:fieldBody>
                            </field>
                        </c:forEach>
                    </fields>
                    <tiles:insert page="../../common/mobile/payments/fromResourceFieldTemplate.jsp" flush="false">
                        <tiles:put name="name" value="fromResource"/>
                        <tiles:put name="title" value="ќплата с"/>
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
