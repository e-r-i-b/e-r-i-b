<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/payments/confirm" onsubmit="return setEmptyAction(event)">

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
                <tiles:put name="id"            value=""/>
                <tiles:put name="alignTable"    value="center"/>
                <tiles:put name="name"          value="${title}"/>
                <tiles:put name="description"   value="Внимательно проверьте реквизиты платежа и нажмите на кнопку «Подтвердить»."/>
                <tiles:put name="data">
                    ${frm.html}
                </tiles:put>
                <tiles:put name="buttons">
                    <script type="text/javascript">
                        function printStatement(event)
                        {
                            <c:choose>
                                <c:when test="${isEditClaim}">
                                    <c:set var="url" value="/private/payments/print-edit-claim.do"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="url" value="/private/payments/print.do"/>
                                </c:otherwise>
                            </c:choose>
                            openWindow(event, "${phiz:calculateActionURL(pageContext, url)}?id=${frm.id}");
                        }
                    </script>

                    <tiles:insert definition="clientButton" operation="EditPaymentOperation" service="${serviceName}" flush="false">
                        <tiles:put name="commandTextKey"    value="button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="button.cancel.help"/>
                        <tiles:put name="bundle"            value="autopaymentsBundle"/>
                        <tiles:put name="action"            value="${linkButtonCancel}"/>
                    </tiles:insert>

                    <tiles:insert definition="commandButton" operation="EditPaymentOperation" service="${serviceName}" flush="false">
                        <tiles:put name="commandKey"        value="button.edit"/>
                        <tiles:put name="commandHelpKey"    value="button.edit.help"/>
                        <tiles:put name="bundle"            value="autopaymentsBundle"/>
                        <tiles:put name="stateObject"       value="document"/>
                    </tiles:insert>

                    <tiles:insert definition="clientButton" operation="EmployeeConfirmFormPaymentOperation" service="${serviceName}" flush="false">
                        <tiles:put name="commandTextKey"    value="button.print"/>
                        <tiles:put name="commandHelpKey"    value="button.print.help"/>
                        <tiles:put name="bundle"            value="autopaymentsBundle"/>
                        <tiles:put name="onclick"           value="printStatement(event)"/>
                    </tiles:insert>

                    <tiles:insert definition="commandButton" operation="EmployeeConfirmFormPaymentOperation" service="${serviceName}" flush="false">
                        <tiles:put name="commandKey"        value="button.confirm"/>
                        <tiles:put name="commandHelpKey"    value="button.confirm.help"/>
                        <tiles:put name="bundle"            value="autopaymentsBundle"/>
                        <tiles:put name="stateObject"       value="document"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

    <c:if test="${not empty frm.document.state.code and frm.document.state.code =='REFUSED'
                and not empty frm.document.refusingReason
                and (phiz:isInstance(frm.document, 'com.rssl.phizic.business.documents.payments.LoanClaimBase')
                or phiz:isInstance(frm.document, 'com.rssl.phizic.business.documents.payments.VirtualCardClaim'))}">
        <div id="stateDescription" onmouseover="showLayer('state','stateDescription','default');"
             onmouseout="hideLayer('stateDescription');" class="layerFon"
             style="position:absolute; display:none; width:400px; height:37px;overflow:auto;">
            <c:out value="${frm.document.refusingReason}"/>
        </div>
    </c:if>
</html:form>
