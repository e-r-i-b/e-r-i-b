<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/loanClaim/settings" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:importAttribute/>
    <tiles:insert definition="LoanClaimSettingsEdit">
        <tiles:put name="data" type="string">
            <tiles:put name="submenu" type="string" value="LoanClaimSettings"/>
            <tiles:put name="pageTitle" type="string">
                <bean:message key="label.loan.claim.settings" bundle="loanclaimBundle"/>
            </tiles:put>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="description">
                    <bean:message key="label.loan.claim.settings.description" bundle="loanclaimBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:checkbox property="callAvailable"/>
                    <bean:message key="label.loan.claim.callAvailable" bundle="loanclaimBundle"/>
                    <div class="descriptionBlock">
                        <bean:message key="label.loan.claim.callAvailable.description" bundle="loanclaimBundle"/>
                    </div>
                    <html:checkbox property="needConfirmDebitOperationERKC"  onclick="changeMinSumDebitOperationERKCAvailable()"/>
                    <bean:message key="label.loan.claim.needConfirmDebitOperationERKC" bundle="loanclaimBundle"/><br/><br/>
                    <fieldset id="minSumDebitOperationERKC" <c:if test="${not form.needConfirmDebitOperationERKC}">disabled</c:if>>
                        <bean:message key="label.loan.claim.minSumDebitOperationERKC" bundle="loanclaimBundle"/><br/>
                        <html:text property="minRUBSumDebitOperationERKC"/> руб. <br/>
                        <html:text property="minUSDSumDebitOperationERKC"/> USD <br/>
                        <html:text property="minEURSumDebitOperationERKC"/> EUR <br/>
                    </fieldset>
                    <html:checkbox property="lockOperationDebit"/>
                    <bean:message key="label.loan.claim.lockOperationDebit" bundle="loanclaimBundle"/> <br/>

                    <html:text property="periodLockedOperationDebit"/>
                    <bean:message key="label.loan.claim.periodLockedOperationDebit" bundle="loanclaimBundle"/><br/><br/>
                    <fieldset>
                        <bean:message key="label.loan.claim.maxSumUnlockRestriction" bundle="loanclaimBundle"/><br/>
                        RUB <html:text property="maxRUBSumUnlockRestriction"/> %<br/>
                        USD <html:text property="maxEURSumUnlockRestriction"/> %<br/>
                        EUR <html:text property="maxUSDSumUnlockRestriction"/> %<br/>
                    </fieldset>
                    <tiles:put name="buttons">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.save"/>
                            <tiles:put name="commandHelpKey" value="button.save"/>
                            <tiles:put name="bundle" value="commonBundle"/>
                        </tiles:insert>
                    </tiles:put>
                </tiles:put>
             </tiles:insert>
            <script type="text/javascript">
                function changeMinSumDebitOperationERKCAvailable()
                {
                    var minSumDebitOperationERKC = $('fieldset[id="minSumDebitOperationERKC"]');
                    if ($('input[name="needConfirmDebitOperationERKC"]')[0].checked == true)
                    {
                        minSumDebitOperationERKC.removeAttr('disabled');
                    }
                    else
                    {
                        minSumDebitOperationERKC.attr('disabled','disabled');
                    }
                }
            </script>
        </tiles:put>
    </tiles:insert>
</html:form>