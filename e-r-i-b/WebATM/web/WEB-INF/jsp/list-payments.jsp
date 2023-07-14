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

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="data">
            <sl:collection id="listElement" property="data" model="xml-list" title="operations">
                <sl:collectionItem title="operation">
                    <c:set var="businessDocument" value="${listElement[9]}" scope="request"/>
                    <c:set var="stateCode" value="${businessDocument.state.code}"/>
                    <c:set var="formName" value="${businessDocument.formName}"/>
                    <c:set var="creationType" value="${businessDocument.creationType}"/>
                    <c:set var="isLongOffer" value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractLongOfferDocument') and businessDocument.longOffer}"/>
                        <id>${businessDocument.id}</id>
                        <c:if test="${not empty stateCode}">
                             <state>${stateCode}</state>
                        </c:if>

                        <tiles:insert definition="atmDateTimeType" flush="false">
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
                            <c:set var="paymentFormDescriptionBundle" value="paymentform.${businessDocument.formName}"/>
                            <c:choose>
                                <%--���������� ��������� (���������� ��������)--%>
                                <c:when test="${isLongOffer}">
                                    <c:choose>
                                        <c:when test="${formName == 'RurPayJurSB' or
                                                        formName == 'CreateAutoPaymentPayment' or
                                                        formName == 'InternalPayment' or
                                                        formName == 'RurPayment' or
                                                        formName == 'LoanPayment'}">
                                            �������� �����������:
                                        </c:when>
                                        <c:when test="${formName == 'EditAutoSubscriptionPayment' or formName == 'EditAutoPaymentPayment'}">
                                            �������������� �����������:
                                        </c:when>
                                        <c:when test="${formName == 'DelayAutoSubscriptionPayment'}">
                                            ������������ �����������:
                                        </c:when>
                                        <c:when test="${formName == 'RecoveryAutoSubscriptionPayment'}">
                                            ������������� �����������:
                                        </c:when>
                                        <c:when test="${formName == 'CloseAutoSubscriptionPayment' or formName == 'RefuseAutoPaymentPayment'}">
                                            ������ �����������:
                                        </c:when>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.RefuseAutoPaymentImpl') or phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.CreateAutoPaymentImpl')}">
                                            <c:out value="${businessDocument.friendlyName}"/>
                                        </c:when>
                                        <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.AutoPaymentBase') and not empty businessDocument.nameService}">
                                            <c:out value="${businessDocument.nameService}"/>
                                        </c:when>
                                        <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.JurPayment') and not empty businessDocument.serviceName}">
                                            <c:out value="${businessDocument.serviceName}"/>
                                        </c:when>
                                        <c:when test="${not empty paymentFormDescriptionBundle}">
                                            <bean:message bundle="paymentsBundle" key="${paymentFormDescriptionBundle}" failIfNone="false"/>
                                        </c:when>
                                    </c:choose>
                                </c:when>
                                <%--������ ���������� ���������� ��������--%>
                                <c:when test="${formName == 'RefuseLongOffer'}">
                                    <bean:message bundle="paymentsBundle" key="paymentform.RefuseLongOffer" failIfNone="false"/>
                                    <c:out value="${businessDocument.name}"/>
                                </c:when>
                                <c:when test="${not empty businessDocument}">
                                    <c:choose>
                                        <c:when test="${formName == 'RurPayment'}">
                                            <c:set var="subType" value="${businessDocument.receiverSubType}"/>
                                            <c:choose>
                                                <c:when test="${subType == 'ourPhone' or subType == 'ourCard' or subType == 'ourAccount'}">
                                                    ������� ������� ���������
                                                </c:when>
                                                <c:otherwise>
                                                    ������� � ������ ����
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:when test="${formName == 'AccountOpeningClaim' && businessDocument.fromPersonalFinance}">
                                            <bean:message bundle="paymentsBundle" key="paymentform.AccountOpeningClaim.fromPersonalFinance"/>
                                        </c:when>
                                        <c:when test="${formName == 'AccountClosingPayment' && businessDocument.fromPersonalFinance}">
                                            <bean:message bundle="paymentsBundle" key="paymentform.AccountClosingPayment.fromPersonalFinance"/>
                                        </c:when>
                                        <c:otherwise>
                                            <bean:message bundle="paymentsBundle" key="${paymentFormDescriptionBundle}" failIfNone="false"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    ���������� ����������� ������������
                                </c:otherwise>
                            </c:choose>
                        </description>

                        <c:set var="sum" value="" />
                        <c:set var="sign" value="" />
                        <c:choose>
                            <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.gate.payments.CurrencyExchangeTransfer') and (empty businessDocument.chargeOffAmount or businessDocument.chargeOffAmount.decimal == '0.00')}"> <!-- ��� ����� ������ � �� ���� ������� ����� ��������-->
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

                        <tiles:insert definition="atmMoneyType" flush="false">
                            <tiles:put name="name" value="operationAmount" />
                            <tiles:put name="money" beanName="sum"/>
                            <tiles:put name="sign" value="${sign}"/>
                        </tiles:insert>


                        <c:set var="supportedActions"  value="${phiz:getDocumentSupportedActions(businessDocument)}"/>
                        <templatable>
                            <c:choose>
                                <c:when test="${not empty supportedActions['isTemplateSupported']}">
                                    ${supportedActions['isTemplateSupported']}
                                </c:when>
                                <c:otherwise>false</c:otherwise>
                            </c:choose>
                        </templatable>

                        <copyable>${supportedActions['isCopyAllowed']}</copyable>

                        <creationType>${creationType}</creationType>

                        <type>${phiz:getPaymentTypeByDocument(businessDocument)}</type>

                        <c:set var="formTagVal">
                            <c:choose>
                                <c:when test="${isLongOffer and formName == 'InternalPayment'}">
                                    InternalPaymentLongOffer
                                </c:when>
                                <c:when test="${isLongOffer and formName == 'RurPayment'}">
                                    RurPaymentLongOffer
                                </c:when>
                                <c:when test="${isLongOffer and formName == 'LoanPayment'}">
                                    LoanPaymentLongOffer
                                </c:when>
                                <c:otherwise>${formName}</c:otherwise>
                            </c:choose>
                        </c:set>

                        <form>${formTagVal}</form>
                </sl:collectionItem>
           </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>