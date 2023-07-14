<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/tags/mobile" prefix="mobile" %>

<html:form action="/private/moneyboxes/payment/info/print" onsubmit="return setEmptyAction(event)">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="payment" value="${form.payment}"/>

<c:set var="operationDate"><bean:write name="form" property="datePayment" format="dd.MM.yy"/></c:set>
<c:set var="operationTime"><bean:write name="form" property="datePayment" format="HH:mm:ss"/></c:set>
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

<tiles:insert definition="iphone">
    <tiles:put name="data" type="string">
        <c:if test="${not empty payment}">
            <DocumentCheck>
                <form>InternalPayment</form>
                <title>перевод со счета карты на счет вклада</title>
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

                <documentNumber>
                    <mobile:fieldBody
                            name="documentNumber"
                            title="идентификатор операции"
                            type="integer"
                            required="true"
                            editable="false"
                            visible="true"
                            changed="false"
                            isSum="false"
                            value="${payment.idFromPaymentSystem}">
                    </mobile:fieldBody>
                </documentNumber>

                <InternalPaymentDocumentCheck>
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

                    <c:if test="${not empty payment.receiverAccount}">
                        <receiver>
                            <mobile:fieldBody
                                   name="receiverAccount"
                                   title="Получатель"
                                   type="string"
                                   required="true"
                                   editable="false"
                                   visible="true"
                                   changed="fasle"
                                   isSum="false"
                                   value="${payment.receiverAccount}">
                            </mobile:fieldBody>
                        </receiver>
                    </c:if>
                </InternalPaymentDocumentCheck>
            </DocumentCheck>
        </c:if>
    </tiles:put>
</tiles:insert>
</html:form>