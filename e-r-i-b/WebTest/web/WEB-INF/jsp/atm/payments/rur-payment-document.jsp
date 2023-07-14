<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<c:if test="${not empty form.response.document.rurPaymentDocument}">
    <c:set var="rurPaymentDocument" value="${form.response.document.rurPaymentDocument}"/>
    <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
        <tiles:insert page="fields-table.jsp" flush="false">
            <tiles:put name="data">
                <tiles:insert page="field.jsp" flush="false">
                    <tiles:put name="field" beanName="rurPaymentDocument" beanProperty="documentNumber"/>
                </tiles:insert>
                <tiles:insert page="field.jsp" flush="false">
                    <tiles:put name="field" beanName="rurPaymentDocument" beanProperty="documentDate"/>
                </tiles:insert>
                <tiles:insert page="field.jsp" flush="false">
                    <tiles:put name="field" beanName="rurPaymentDocument" beanProperty="receiverAccount"/>
                </tiles:insert>
                <c:if test="${not empty rurPaymentDocument.receiverPhone}">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="rurPaymentDocument" beanProperty="receiverPhone"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${not empty rurPaymentDocument.receiverName}">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="rurPaymentDocument" beanProperty="receiverName"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${not empty rurPaymentDocument.receiverAccount}">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="rurPaymentDocument" beanProperty="receiverAccount"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${not empty rurPaymentDocument.receiverINN}">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="rurPaymentDocument" beanProperty="receiverINN"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${not empty rurPaymentDocument.receiverAddress}">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="rurPaymentDocument" beanProperty="receiverAddress"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${not empty rurPaymentDocument.bankInfo.bank}">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="rurPaymentDocument" beanProperty="bankInfo.bank"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${not empty rurPaymentDocument.bankInfo.receiverBIC}">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="rurPaymentDocument" beanProperty="bankInfo.receiverBIC"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${not empty rurPaymentDocument.bankInfo.receiverCorAccount}">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="rurPaymentDocument" beanProperty="bankInfo.receiverCorAccount"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${not empty rurPaymentDocument.ground}">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="rurPaymentDocument" beanProperty="ground"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${not empty rurPaymentDocument.fromResource}">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="rurPaymentDocument" beanProperty="fromResource"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${not empty rurPaymentDocument.sellAmount}">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="rurPaymentDocument" beanProperty="sellAmount"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${not empty rurPaymentDocument.sellCurrency}">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="rurPaymentDocument" beanProperty="sellCurrency"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${not empty rurPaymentDocument.buyAmount}">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="rurPaymentDocument" beanProperty="buyAmount"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${not empty rurPaymentDocument.commission}">
                    <td>commission</td>
                    <td>money(not field)</td>
                    <td>Комиcсия</td>
                    <td><c:out value="${rurPaymentDocument.commission.amount}"/> <c:out value="${phiz:normalizeCurrencyCode(rurPaymentDocument.commission.currency.code)}"/></td>
                </c:if>
                <c:if test="${not empty rurPaymentDocument.admissionDate}">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="rurPaymentDocument" beanProperty="admissionDate"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${not empty rurPaymentDocument.messageToReceiver}">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="rurPaymentDocument" beanProperty="messageToReceiver"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${not empty rurPaymentDocument.receiverSubType}">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="rurPaymentDocument" beanProperty="receiverSubType"/>
                    </tiles:insert>
                </c:if>
            </tiles:put>
        </tiles:insert>
     </div>
</c:if>

    