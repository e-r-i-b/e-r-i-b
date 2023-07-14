<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>Начальные условия платежа</title>
    </head>

    <body>
        <h1>Заполнение полей платежа</h1>

        <html:form action="/atm/createP2PAutoTransfer" show="true">
            <tiles:insert definition="atm" flush="false">
                <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

                <tiles:put name="formName"  value="CreateP2PAutoTransferClaim"/>
                <tiles:put name="address"   value="/private/payments/payment.do"/>
                <tiles:put name="data">
                    <c:if test="${not empty form.response and not empty form.response.initialData}">
                        <c:set var="document" value="${form.response.initialData.createP2PAutoTransferClaim}"/>
                    </c:if>
                    <c:if test="${not empty document}">
                        <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                            <tiles:insert page="fields-table.jsp" flush="false">
                                <tiles:put name="data">
                                    <c:set var="receiver" value="${document.receiver}"/>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="receiver" beanProperty="receiverType"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="receiver" beanProperty="receiverSubType"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="receiver" beanProperty="receiverName"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="document" beanProperty="receiver.externalCardNumber"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="document" beanProperty="receiver.externalPhoneNumber"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="document" beanProperty="messageToReceiver"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="receiver" beanProperty="toResource"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="document" beanProperty="fromResource"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                        </div>
                    </c:if>
                </tiles:put>

                <tiles:put name="operation" value="makeLongOffer"/>
            </tiles:insert>
        </html:form>
        <br/>
    </body>
</html:html>
