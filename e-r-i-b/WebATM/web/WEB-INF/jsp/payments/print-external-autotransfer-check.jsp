<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/tags/mobile" prefix="mobile" %>

<c:if test="${not empty payment}">
    <DocumentCheck>
        <form>CreateP2PAutoTransferClaim</form>
        <title>чек по операции</title>

        <c:set var="operationDate"><bean:write name="form" property="datePayment" format="dd.MM.yy"/></c:set>
        <c:set var="operationTime"><bean:write name="form" property="datePayment" format="HH:mm:ss"/></c:set>
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

        <RurPaymentDocumentCheck>
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

            <c:set var="comission">
                <c:choose>
                    <c:when test="${not empty payment.commission}">
                        <c:out value="${phiz:formatAmount(payment.commission)}"/>
                    </c:when>
                    <c:otherwise>
                        0,00 руб.
                    </c:otherwise>
                </c:choose>
            </c:set>

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

            <c:if test="${not empty payment.receiverCard}">
                <receiver>
                    <mobile:fieldBody
                            name="receiverAccount"
                            title="Счет"
                            type="string"
                            required="true"
                            editable="false"
                            visible="true"
                            changed="fasle"
                            isSum="false"
                            value="${payment.receiverCard}">
                    </mobile:fieldBody>
                </receiver>
            </c:if>
        </RurPaymentDocumentCheck>
    </DocumentCheck>
</c:if>