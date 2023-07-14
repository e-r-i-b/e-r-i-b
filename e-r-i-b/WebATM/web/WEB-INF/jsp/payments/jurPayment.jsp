<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/tags/mobile" prefix="mobile" %>

<html:form action="/private/payments/jurPayment/edit" >
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="payment">
        <tiles:put name="data" type="string">
            <c:choose>
                <%-- A. Получатель из нашего справочника --%>
                <c:when test="${not empty form.serviceProviderList}">
                    <sl:collection id="serviceProvider" property="serviceProviderList" model="xml-list" title="billingPayments">
                        <sl:collectionItem title="payment">
                            <tiles:insert definition="billingPayment" flush="false">
                                <tiles:put name="serviceProvider" beanName="serviceProvider"/>
                            </tiles:insert>
                        </sl:collectionItem>
                    </sl:collection>
                </c:when>

                <%-- B. Получатель ещё не опрелён либо не из нашего справочника --%>
                <c:otherwise>
                    <initialData>
                        <form>JurPayment</form>
                        <JurPayment>
                            <operationUID>${form.operationUID}</operationUID>
                            <receiverAccount>
                                <mobile:fieldBody
                                        name="field(receiverAccount)"
                                        title="Номер счета"
                                        hint="Введите номер счета получателя (от 20 до 25 цифр без точек и пробелов)."
                                        type="integer" minLength="20" maxLength="25"
                                        required="true" editable="${empty form.externalProviders}" visible="true"
                                        value="${form.fields['receiverAccount']}"/>
                            </receiverAccount>
                            <receiverINN>
                                <mobile:fieldBody
                                        name="field(receiverINN)"
                                        title="ИНН"
                                        hint="Укажите Идентификационный Номер Налогоплательщика.
                                                     У организаций ИНН состоит из 10 цифр, у индивидуальных предпринимателей – из 12 цифр."
                                        type="integer" minLength="10" maxLength="12"
                                        required="true" editable="${empty form.externalProviders}" visible="true"
                                        value="${form.fields['receiverINN']}"/>
                            </receiverINN>
                            <receiverBIC>
                                <mobile:fieldBody
                                        name="field(receiverBIC)"
                                        title="БИК"
                                        hint="Введите банковский идентификационный код. БИК может состоять только из 9 цифр."
                                        type="integer" minLength="9" maxLength="9"
                                        required="true" editable="${empty form.externalProviders}" visible="true"
                                        value="${form.fields['receiverBIC']}"/>
                            </receiverBIC>
                            <c:if test="${not empty form.externalProviders}">
                                <externalReceiver>
                                    <mobile:fieldBody
                                            name="field(externalReceiverId)"
                                            title="Получатель"
                                            type="list"
                                            required="true" editable="true" visible="true"
                                            value="${form.fields['externalReceiverId']}">
                                        <c:forEach items="${form.externalProviders}" var="provider">
                                            <mobile:fieldListItem value="${provider.synchKey}" title="${provider.name}"/>
                                        </c:forEach>
                                    </mobile:fieldBody>
                                </externalReceiver>
                            </c:if>
                            <availableFromResources>
                                <mobile:fieldBody
                                        name="fromResource"
                                        title="Счет списания"
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
                        </JurPayment>
                    </initialData>
                </c:otherwise>
            </c:choose>
        </tiles:put>
    </tiles:insert>
</html:form>
