<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/loanClaim/find" onsubmit="return setEmptyAction(event);">
    <tiles:importAttribute/>

    <tiles:insert definition="LoanClaimFind">
        <tiles:put name="data" type="string">
            <tiles:put name="submenu" type="string" value="LoanClaimFind"/>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name"><bean:message bundle="loanclaimBundle" key="label.loan.claim.loanclaims"/></tiles:put>
                <tiles:put name="description"><bean:message bundle="loanclaimBundle" key="label.loan.claim.loanclaims.description"/></tiles:put>
                <tiles:put name="data">
                    <div class="loan-claim-find">
                        <div class="loan-claim-find-box">
                            <bean:message key="label.loan.claim.loanclaim.number" bundle="loanclaimBundle"/>
                            <html:text property="loanClaimNumber" size="35" maxlength="27"/>
                        </div>
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.find"/>
                            <tiles:put name="commandHelpKey" value="button.find"/>
                            <tiles:put name="bundle" value="loanclaimBundle"/>
                        </tiles:insert>
                    </div>
                    <tiles:put name="buttons">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.create"/>
                            <tiles:put name="commandHelpKey" value="button.create"/>
                            <tiles:put name="bundle" value="loanclaimBundle"/>
                        </tiles:insert>
                    </tiles:put>
                </tiles:put>
             </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>