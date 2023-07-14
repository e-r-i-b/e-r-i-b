<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/payments/edit" onsubmit="return setEmptyAction(event);">

    <c:set var="frm" value="${phiz:currentForm(pageContext)}" scope="request"/>
    <c:if test="${phiz:isInstance(frm.document, 'com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription') and frm.document.longOffer}">
        <jsp:include page="./subscriptions/support-payment-${frm.metadata.name}.jsp" flush="false" />
    </c:if>

    <%--поскольку в админе пока не предполагается явно использовать создание всех платежей, используем definition для автоплатежей--%>
    <tiles:insert definition="autoSubscriptions">

        <tiles:put name="submenu" type="string" value="${subMenu}"/>

        <tiles:put name="menu" type="string">
            <%@ include file="/WEB-INF/jsp-sbrf/autopayments/resetClientInfoButton.jsp" %>
            <%@ include file="/WEB-INF/jsp-sbrf/autopayments/backToSubscriptionListButton.jsp" %>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id"            value="paymentFormId"/>
                <tiles:put name="name"          value="${title}"/>
                <tiles:put name="description"   value="${description}"/>
                <tiles:put name="data"          type="string">
                    <div class="paymentsForm">
                        ${frm.html}
                    </div>
                </tiles:put>
                <tiles:put name="alignTable" value="center"/>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" service="${serviceName}" operation="${operationName}" flush="false">
                        <tiles:put name="commandTextKey"        value="button.cancel"/>
                        <tiles:put name="commandHelpKey"        value="button.cancel.help"/>
                        <tiles:put name="bundle"                value="commonBundle"/>
                        <tiles:put name="action"                value="${linkButtonCancel}"/>
                    </tiles:insert>

                    <c:if test="${isAutoSubscription and isCreationClaim}">
                        <tiles:insert definition="commandButton" service="${serviceName}" operation="${operationName}" flush="false">
                            <tiles:put name="commandKey"            value="button.edit"/>
                            <tiles:put name="commandTextKey"        value="button.prev"/>
                            <tiles:put name="commandHelpKey"        value="button.prev.help"/>
                            <tiles:put name="bundle"                value="commonBundle"/>
                        </tiles:insert>
                    </c:if>

                    <tiles:insert definition="commandButton" service="${serviceName}" operation="${operationName}" flush="false">
                        <tiles:put name="commandKey"            value="button.save"/>
                        <tiles:put name="commandTextKey"        value="${buttonSaveName}"/>
                        <tiles:put name="commandHelpKey"        value="${buttonSaveName}.help"/>
                        <tiles:put name="bundle"                value="commonBundle"/>
                        <tiles:put name="isDefault"             value="true"/>
                        <tiles:put name="stateObject"           value="document"/>
                    </tiles:insert>

                    <c:if test="${isAutoTransfer}">
                        <tiles:insert definition="commandButton" service="${serviceName}" operation="${operationName}" flush="false">
                            <tiles:put name="commandKey"        value="button.edit"/>
                            <tiles:put name="commandTextKey"    value="button.prev"/>
                            <tiles:put name="commandHelpKey"    value="button.prev.help"/>
                            <tiles:put name="bundle"            value="commonBundle"/>
                            <tiles:put name="stateObject"       value="document"/>
                        </tiles:insert>
                    </c:if>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>