<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<html:form action="/loans/configure">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="loansEarlyRepay"/>
        <tiles:put name="submenu" type="string" value="loansEarlyRepay"/>
        <tiles:put name="data" type="string">
            <tiles:importAttribute/>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="personsConfigure"/>
                <tiles:put name="name"><bean:message bundle="configureBundle" key="settings.loans.earlyloanrepayment.menuitem"/></tiles:put>
                <tiles:put name="description"><bean:message bundle="configureBundle" key="settings.loans.earlyloanrepayment.description"/></tiles:put>
                <tiles:put name="data">
                    <table>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.phizic.config.earlyloanrepayment.allowed"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.loans.earlyloanrepayment.allowed"/></tiles:put>
                            <tiles:put name="fieldType" value="checkbox"/>
                            <tiles:put name="showHint" value="none"/>
                        </tiles:insert>
                        <tr>
                            <td colspan=3>
                                <fieldset>
                                    <bean:message bundle="configureBundle" key="settings.loans.earlyloanrepayment.dateLabel"/>
                                    <table>
                                        <tiles:insert definition="propertyField" flush="false">
                                            <tiles:put name="fieldName" value="com.rssl.phizic.config.earlyloanrepayment.minDate"/>
                                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.loans.earlyloanrepayment.minDate"/></tiles:put>
                                            <tiles:put name="fieldType" value="text"/>
                                            <tiles:put name="showHint">none</tiles:put>
                                            <tiles:put name="textMaxLength" value="3"/>
                                            <tiles:put name="requiredField" value="true"/>
                                        </tiles:insert>
                                        <tiles:insert definition="propertyField" flush="false">
                                            <tiles:put name="fieldName" value="com.rssl.phizic.config.earlyloanrepayment.maxDate"/>
                                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.loans.earlyloanrepayment.maxDate"/></tiles:put>
                                            <tiles:put name="fieldType" value="text"/>
                                            <tiles:put name="showHint">none</tiles:put>
                                            <tiles:put name="textMaxLength" value="3"/>
                                            <tiles:put name="requiredField" value="true"/>
                                        </tiles:insert>
                                    </table>
                                </fieldset>
                            </td>
                        </tr>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.phizic.config.earlyloanrepayment.minAmount"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.loans.earlyloanrepayment.minAmount"/></tiles:put>
                            <tiles:put name="fieldType" value="text"/>
                            <tiles:put name="showHint" value="right"/>
                            <tiles:put name="inputDesc" value="%"/>
                            <tiles:put name="requiredField" value="true"/>
                            <tiles:put name="textMaxLength" value="2"/>
                        </tiles:insert>
                        <tr>
                            <td colspan=3>
                                <fieldset>
                                    <bean:message bundle="configureBundle" key="settings.loans.earlyloanrepayment.cancelAllowedLabel"/>
                                    <table>
                                        <tiles:insert definition="propertyField" flush="false">
                                            <tiles:put name="fieldName" value="com.rssl.phizic.config.earlyloanrepayment.cancelERIBAllowed"/>
                                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.loans.earlyloanrepayment.cancelERIBAllowed"/></tiles:put>
                                            <tiles:put name="fieldType" value="checkbox"/>
                                            <tiles:put name="showHint">none</tiles:put>
                                        </tiles:insert>
                                        <tiles:insert definition="propertyField" flush="false">
                                            <tiles:put name="fieldName" value="com.rssl.phizic.config.earlyloanrepayment.cancelVSPAllowed"/>
                                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.loans.earlyloanrepayment.cancelVSPAllowed"/></tiles:put>
                                            <tiles:put name="fieldType" value="checkbox"/>
                                            <tiles:put name="showHint">none</tiles:put>
                                        </tiles:insert>
                                    </table>
                                </fieldset>
                            </td>
                        </tr>
                    </table>
                </tiles:put>
            </tiles:insert>
            <tiles:put name="formButtons">
                <tiles:insert definition="commandButton" flush="false" operation="EarlyLoanPaymentSettingsOperation">
                    <tiles:put name="commandKey"     value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle"  value="commonBundle"/>
                    <tiles:put name="isDefault" value="true"/>
                </tiles:insert>
                <tiles:insert definition="clientButton" flush="false" operation="EarlyLoanPaymentSettingsOperation">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="onclick" value="window.location.reload();"/>
                </tiles:insert>
            </tiles:put>
        </tiles:put>
    </tiles:insert>
</html:form>
