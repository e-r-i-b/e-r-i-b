<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/claim/payments/view" onsubmit="return setEmptyAction(event)">
    <c:set var="frm" value="${phiz:currentForm(pageContext)}" scope="request"/>
    <c:set var="document" value="${frm.document}"/>

    <tiles:insert definition="loanClaim">
        <tiles:put name="submenu" type="string" value="LoanClaimFind"/>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function openOperationLog()
                {
                    openGuestOperationConfirmLog('${document.operationUID}');
                }
            </script>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id"            value=""/>
                <tiles:put name="alignTable"    value="center"/>
                <tiles:put name="name"          value="${title}"/>
                <tiles:put name="description"   value=""/>
                <tiles:put name="data">
                    <tiles:insert page="/WEB-INF/jsp/common/loan/loanClaimParametersBlock.jsp" flush="false">
                        <tiles:put name="document" beanName="frm" beanProperty="document"/>
                        <tiles:put name="description" value=""/>
                    </tiles:insert>
                    <div class="clear"></div>
                    ${frm.html}
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" operation="EmployeeConfirmFormPaymentOperation" service="LoanClaimEmployeeService" flush="false">
                        <tiles:put name="commandKey"        value="button.confirm"/>
                        <tiles:put name="commandHelpKey"    value="button.confirm.help"/>
                        <tiles:put name="bundle"            value="commonBundle"/>
                        <tiles:put name="stateObject"       value="document"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" service="LoanClaimEmployeeService" flush="false">
                        <tiles:put name="action" value="/loanClaim/find.do"/>
                        <tiles:put name="commandTextKey" value="button.findClaim"/>
                        <tiles:put name="commandHelpKey"    value="button.findClaim.help"/>
                        <tiles:put name="bundle"            value="paymentsBundle"/>
                    </tiles:insert>
                    <c:set var="owner" value="${document.owner}"/>
                    <c:set var="isExtendedLoanClaim" value="${frm.form == 'ExtendedLoanClaim'}"/>
                    <c:set var="isGuestOperationLogAvailable" value="${phiz:impliesOperation('ListGuestOperationConfirmOperation', 'LoanClaimEmployeeService')}"/>
                    <c:if test="${isExtendedLoanClaim && owner.guest && isGuestOperationLogAvailable}">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.operationLog"/>
                            <tiles:put name="commandHelpKey" value="button.operationLog.help"/>
                            <tiles:put name="bundle"         value="personsBundle"/>
                            <tiles:put name="onclick"        value="openOperationLog();"/>
                        </tiles:insert>
                    </c:if>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

    <c:if test="${not empty document.state.code and document.state.code =='REFUSED'
                and not empty document.refusingReason
                and (phiz:isInstance(document, 'com.rssl.phizic.business.documents.payments.LoanClaimBase')
                or phiz:isInstance(document, 'com.rssl.phizic.business.documents.payments.VirtualCardClaim'))}">
        <div id="stateDescription" onmouseover="showLayer('state','stateDescription','default');"
             onmouseout="hideLayer('stateDescription');" class="layerFon"
             style="position:absolute; display:none; width:400px; height:37px;overflow:auto;">
            <c:out value="${document.refusingReason}"/>
        </div>
    </c:if>
</html:form>
