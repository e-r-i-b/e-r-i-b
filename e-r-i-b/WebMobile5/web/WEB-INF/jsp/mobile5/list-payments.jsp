<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/private/payments/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <sl:collection id="listElement" property="data" model="xml-list" title="operations">
                <sl:collectionItem title="operation">
                    <c:set var="businessDocument" value="${listElement[9]}" scope="request"/>
                    <c:set var="stateCode" value="${businessDocument.state.code}"/>
                    <c:set var="isMobilePayment" value="${businessDocument.creationType == 'mobile'}"/>
                    <c:set var="formName" value="${businessDocument.formName}"/>
                    <c:set var="isLongOffer" value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractLongOfferDocument') and businessDocument.longOffer}"/>
                    <id>${businessDocument.id}</id>
                    <c:choose>
                        <c:when test="${stateCode=='SENDED' || stateCode=='DISPATCHED' || stateCode=='STATEMENT_READY' || stateCode=='PARTLY_EXECUTED' || stateCode=='ERROR'}">
                            <state>DISPATCHED</state>
                        </c:when>
                        <c:when test="${stateCode=='SENT'}">
                            <state>UNKNOW</state>
                        </c:when>
                        <c:otherwise>
                            <state>${stateCode}</state>
                        </c:otherwise>
                    </c:choose>

                    <tiles:insert definition="mobileDateTimeType" flush="false">
                        <tiles:put name="name" value="date"/>
                        <tiles:put name="calendar" beanName="businessDocument" beanProperty="dateCreated"/>
                    </tiles:insert>

                    <c:set var="from">
                        <tiles:insert definition="historyTemplateFrom" flush="false" />
                    </c:set>

                    <c:set var="to">
                        <tiles:insert definition="historyTemplateTo" flush="false" />
                    </c:set>

                    <c:set var="to" value="${fn:trim(to)}" />
                    <c:set var="from" value="${fn:trim(from)}" />
                    <c:if test="${from != ''}"><from>${phiz:fixLineTransfer( from )}</from></c:if>
                    <c:if test="${to != ''}"><to>${phiz:fixLineTransfer( to )}</to></c:if>

                    <description>
                        <c:set var="paymentFormDescription" value="paymentform.${formName}"/>
                        <c:set var="paymentDesccription">
                        <c:choose>
                            <c:when test="${isLongOffer}">
                                <c:if test="${not phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.RefuseAutoPaymentImpl')}">
                                    Автоплатеж:
                                </c:if>
                                <c:choose>
                                    <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.AutoPaymentBase') and not empty businessDocument.nameService}">
                                        <c:out value="${businessDocument.nameService}"/>
                                    </c:when>
                                    <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.JurPayment') and not empty businessDocument.serviceName}">
                                        <c:out value="${businessDocument.serviceName}"/>
                                    </c:when>
                                    <c:when test="${not empty paymentFormDescription}">
                                        <bean:message bundle="paymentsBundle" key="${paymentFormDescription}" failIfNone="false"/>
                                    </c:when>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <bean:message bundle="paymentsBundle" key="${paymentFormDescription}" failIfNone="false"/>
                            </c:otherwise>
                        </c:choose>
                        </c:set>
                        ${phiz:replaceSpaces(paymentDesccription)}
                    </description>

                    <c:set var="sum" value="" />
                    <c:set var="sign" value="" />
                    <c:choose>
                        <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.gate.payments.CurrencyExchangeTransfer') and (empty businessDocument.chargeOffAmount or businessDocument.chargeOffAmount.decimal == '0.00')}"> <!-- Это обмен валюты и не была указана сумма списания-->
                            <c:set var="sum" value="${businessDocument.destinationAmount}" />
                            <c:set var="sign" value="+" />
                        </c:when>
                        <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument') && not empty businessDocument.chargeOffAmount}">
                            <c:set var="sum" value="${businessDocument.chargeOffAmount}" />
                            <c:set var="sign" value="-" />
                        </c:when>
                        <c:otherwise>
                            <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument') && not empty businessDocument.destinationAmount}">
                                <c:set var="sum" value="${businessDocument.destinationAmount}" />
                                <c:set var="sign" value="-" />
                            </c:if>
                        </c:otherwise>
                    </c:choose>

                    <c:if test="${not empty sum}">
                        <tiles:insert definition="mobileMoneyType" flush="false">
                            <tiles:put name="name" value="operationAmount" />
                            <tiles:put name="money" beanName="sum"/>
                            <tiles:put name="sign" value="${sign}"/>
                        </tiles:insert>
                    </c:if>

                    <isMobilePayment>${isMobilePayment}</isMobilePayment>

                    <c:set var="supportedActions"  value="${phiz:getDocumentSupportedActions(businessDocument)}"/>
                    <copyable>${supportedActions['isCopyAllowed']}</copyable>

                    <c:if test="${form.mobileApiVersion >= 5.10}">
                        <templatable>
                            <c:choose>
                                <c:when test="${not empty supportedActions['isTemplateSupported']}">
                                    ${supportedActions['isTemplateSupported']}
                                </c:when>
                                <c:otherwise>false</c:otherwise>
                            </c:choose>
                        </templatable>
                    </c:if>

                    <type>${phiz:getPaymentTypeByDocument(businessDocument)}</type>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>