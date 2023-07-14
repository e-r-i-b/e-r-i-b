<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/tags/mobile" prefix="mobile" %>


<html:form action="/private/autosubscriptions/payment/info/print" onsubmit="return setEmptyAction(event)">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="payment" value="${form.payment}"/>
<c:set var="bank" value="${payment.receiverBank}"/>
<c:set var="person" value="${form.activePerson}"/>

<c:set var="operationDate"><bean:write name="form" property="datePayment" format="dd.MM.yy"/></c:set>
<c:set var="operationTime"><bean:write name="form" property="datePayment" format="HH:mm:ss"/></c:set>

<c:set var="receiverName">
    <c:choose>
        <c:when test="${not empty payment.receiverNameForBill}">
            ${payment.receiverNameForBill}
        </c:when>
        <c:otherwise>
            ${payment.receiverName}
        </c:otherwise>
    </c:choose>
</c:set>
<c:set var="comission">
    <c:choose>
        <c:when test="${ not empty payment.commission}">
             <c:out value="${phiz:formatAmount(payment.commission)}"/>
        </c:when>
        <c:otherwise>
           0,00 руб.
        </c:otherwise>
    </c:choose>
</c:set>

<tiles:insert definition="atm">
    <tiles:put name="data" type="string">
        <c:if test="${not empty payment}">
            <DocumentCheck>
                <form>RurPayJurSB</form>
                <title>чек по операции</title>

                <operationDate>
                    <mobile:fieldBody
                            name="operationDate"
                            title="ДАТА ОПЕРАЦИИ"
                            type="date"
                            required="true"
                            editable="false"
                            visible="true"
                            changed="fslae"
                            isSum="false"
                            value="${operationDate}">
                    </mobile:fieldBody>
                </operationDate>

                <operationTime>
                    <mobile:fieldBody
                            name="operationTime"
                            title="время операции (МСК)"
                            type="string"
                            required="true"
                            editable="false"
                            visible="true"
                            changed="false"
                            isSum="false"
                            value="${operationTime}">
                    </mobile:fieldBody>
                </operationTime>

                <billingDocumentNumber>
                    <mobile:fieldBody
                            name="billingDocumentNumber"
                            title="номер операции"
                            type="integer"
                            required="true"
                            editable="false"
                            visible="true"
                            changed="false"
                            isSum="false"
                            value="${payment.idFromPaymentSystem}">
                    </mobile:fieldBody>
                </billingDocumentNumber>

                <RurPayJurSBDocumentCheck>

                    <fromResource>
                        <mobile:fieldBody
                                name="fromResource"
                                title="Отправитель"
                                type="resource"
                                required="true"
                                editable="false"
                                visible="true"
                                changed="false"
                                isSum="false"
                                value="card:0">
                                <mobile:fieldListItem value="card:0" title="№ карты: ${phiz:getCutCardNumber(payment.chargeOffCard)}"/>
                        </mobile:fieldBody>
                    </fromResource>

                    <amount>
                        <mobile:fieldBody
                                name="amount"
                                title="Сумма операции"
                                type="string"
                                required="true"
                                editable="false"
                                visible="true"
                                changed="fasle"
                                isSum="false"
                                value="${phiz:formatAmount(payment.amount)}">
                        </mobile:fieldBody>
                    </amount>

                    <comission>
                        <mobile:fieldBody
                                name="comission"
                                title="Комиссия"
                                type="string"
                                required="true"
                                editable="false"
                                visible="true"
                                changed="fasle"
                                isSum="false"
                                value="${comission}">
                        </mobile:fieldBody>
                    </comission>

                    <c:if test="${not empty payment.authorizeCode}">
                        <authCode>
                            <mobile:fieldBody
                                    name="authCode"
                                    title="Код авторизации"
                                    type="string"
                                    required="true"
                                    editable="false"
                                    visible="true"
                                    changed="fasle"
                                    isSum="false"
                                    value="${payment.authorizeCode}">
                            </mobile:fieldBody>
                        </authCode>
                    </c:if>

                    <keyFields>                     <%--Реквизиты плательщика (ФИО + ключевые поля)--%>
                        <field>
                            <mobile:fieldBody
                                    name="FIO"
                                    title="ФИО"
                                    type="string"
                                    required="true"
                                    editable="false"
                                    visible="true"
                                    changed="fasle"
                                    isSum="false"
                                    value="${phiz:getFormattedPersonName(person.firstName, person.surName, person.patrName)}">
                            </mobile:fieldBody>
                        </field>
                        <c:if test="${ not empty payment.extendedFields}">
                            <c:forEach items="${payment.extendedFields}" var="field">
                                <c:if test="${field.visible and field.key}">
                                    <c:set var="fieldValue">
                                        <c:choose>
                                            <c:when test="${field.type} == 'date'">
                                                <bean:write name="field" property="value" format="dd.MM.yy"/>
                                            </c:when>
                                            <c:otherwise>
                                                ${field.value}
                                            </c:otherwise>
                                        </c:choose>
                                    </c:set>
                                    <field>
                                        <mobile:fieldBody
                                                name="${field.externalId}"
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
                                                value="${field.value}"
                                                defaultValue="${field.defaultValue}"
                                                subType="${field.businessSubType}">
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
                    </keyFields>

                    <receiverFields>                <%--Реквизиты платежа--%>
                        <field>
                            <mobile:fieldBody
                                    name="nameService"
                                    title="ПОЛУЧАТЕЛЬ ПЛАТЕЖА"
                                    type="string"
                                    required="true"
                                    editable="false"
                                    visible="true"
                                    changed="fasle"
                                    isSum="false"
                                    value="${payment.receiverName}">
                            </mobile:fieldBody>
                        </field>
                        <c:if test="${ not empty payment.extendedFields}">
                            <c:forEach items="${payment.extendedFields}" var="rfield">
                                <c:if test="${rfield.visible and (not rfield.key)}">
                                    <c:set var="fieldValue">
                                        <c:choose>
                                            <c:when test="${field.type} == 'date'">
                                                <bean:write name="field" property="value" format="dd.MM.yy"/>
                                            </c:when>
                                            <c:otherwise>
                                                ${field.value}
                                            </c:otherwise>
                                        </c:choose>
                                    </c:set>
                                    <field>
                                        <mobile:fieldBody
                                                name="${rfield.externalId}"
                                                title="${rfield.name}"
                                                description="${rfield.description}"
                                                hint="${rfield.hint}"
                                                type="${rfield.type}"
                                                maxLength="${rfield.maxLength}"
                                                minLength="${rfield.minLength}"
                                                required="${rfield.required}"
                                                editable="${rfield.editable}"
                                                visible="${rfield.visible}"
                                                isSum="${rfield.mainSum}"
                                                value="${rfield.value}"
                                                defaultValue="${rfield.defaultValue}"
                                                subType="${rfield.businessSubType}">
                                            <c:if test="${not empty rfield.values}">
                                                <c:forEach var="valueItem" items="${rfield.values}">
                                                    <mobile:fieldListItem value="${valueItem.id}" title="${valueItem.value}"/>
                                                </c:forEach>
                                            </c:if>
                                        </mobile:fieldBody>
                                    </field>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </receiverFields>

                    <c:if test="${not empty receiverName}">
                        <receiverName>
                            <mobile:fieldBody
                                    name="receiverName"
                                    title="Получатель платежа"
                                    type="string"
                                    required="true"
                                    editable="false"
                                    visible="true"
                                    changed="fasle"
                                    isSum="false"
                                    value="${receiverName}">
                            </mobile:fieldBody>
                        </receiverName>
                    </c:if>

                    <c:if test="${not empty bank}">
                        <receiverBIC>
                            <mobile:fieldBody
                                    name="receiverBIC"
                                    title="БИК"
                                    type="string"
                                    required="true"
                                    editable="false"
                                    visible="true"
                                    changed="fasle"
                                    isSum="false"
                                    value="${bank.BIC}">
                            </mobile:fieldBody>
                        </receiverBIC>
                    </c:if>

                    <c:if test="${not empty payment.receiverINN}">
                        <receiverINN>
                            <mobile:fieldBody
                                    name="receiverINN"
                                    title="ИНН"
                                    type="string"
                                    required="true"
                                    editable="false"
                                    visible="true"
                                    changed="fasle"
                                    isSum="false"
                                    value="${payment.receiverINN}">
                            </mobile:fieldBody>
                        </receiverINN>
                    </c:if>

                    <c:if test="${not empty payment.receiverAccount}">
                        <receiverAccount>
                            <mobile:fieldBody
                                   name="receiverAccount"
                                   title="Счет"
                                   type="string"
                                   required="true"
                                   editable="false"
                                   visible="true"
                                   changed="fasle"
                                   isSum="false"
                                   value="${payment.receiverAccount}">
                            </mobile:fieldBody>
                        </receiverAccount>
                    </c:if>

                    <c:if test="${not empty bank}">
                        <receiverCorAccount>
                            <mobile:fieldBody
                                  name="receiverCorAccount"
                                  title="Корр. счет"
                                  type="string"
                                  required="true"
                                  editable="false"
                                  visible="true"
                                  changed="fasle"
                                  isSum="false"
                                  value="${bank.account}">
                            </mobile:fieldBody>
                        </receiverCorAccount>
                    </c:if>

                    <c:if test="${not empty bank}">
                        <receiverBankName>
                            <mobile:fieldBody
                                  name="receiverBankName"
                                  title="Наименование"
                                  type="string"
                                  required="true"
                                  editable="false"
                                  visible="true"
                                  changed="fasle"
                                  isSum="false"
                                  value="${bank.name}">
                            </mobile:fieldBody>
                        </receiverBankName>
                    </c:if>

                </RurPayJurSBDocumentCheck>
            </DocumentCheck>
        </c:if>
    </tiles:put>
</tiles:insert>
</html:form>