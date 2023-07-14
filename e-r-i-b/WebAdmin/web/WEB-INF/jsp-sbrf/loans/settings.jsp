<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/loan/settings" onsubmit="return setEmptyAction(event);">
    <tiles:importAttribute/>
    <tiles:insert definition="LoanSettingsEdit">
        <tiles:put name="data" type="string">
            <tiles:put name="submenu" type="string" value="LoanSettings"/>
            <tiles:put name="pageTitle" type="string">
                <bean:message key="label.loan.settings" bundle="loanclaimBundle"/>
            </tiles:put>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="description">
                    <bean:message key="label.loan.settings.description" bundle="loanclaimBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:checkbox property="getCreditAtLogon"/>
                    <bean:message key="label.loan.getCreditAtLogon" bundle="loanclaimBundle"/>
                    <tiles:put name="buttons">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.save"/>
                            <tiles:put name="commandHelpKey" value="button.save"/>
                            <tiles:put name="bundle" value="commonBundle"/>
                        </tiles:insert>
                    </tiles:put>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>