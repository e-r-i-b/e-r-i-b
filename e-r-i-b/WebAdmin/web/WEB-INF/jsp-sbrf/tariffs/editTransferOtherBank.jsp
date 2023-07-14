<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/dictionary/tariffs/transferOtherBank/edit" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="isEdit" value="${form.id != null && form.id != 0}"/>

    <tiles:insert definition="editTariffTransferOtherBank">
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name" type="string"><bean:message key="tariff.transfer.other.bank.edit.title" bundle="tariffsBundle"/></tiles:put>
                <tiles:put name="description"><bean:message key="tariff.transfer.other.bank.edit.description" bundle="tariffsBundle"/></tiles:put>
                <tiles:put name="data" type="string">
                     <script type="text/javascript">
                        function changeCurrency()
                        {
                            var codeToText = new Array();
                            codeToText["RUB"] = '${phiz:getCurrencySign("RUB")}';
                            codeToText["USD"] = '${phiz:getCurrencySign("USD")}';
                            codeToText["EUR"] = '${phiz:getCurrencySign("EUR")}';

                            var currencyCode = ensureElement("currencyCode").value;
                            $("#minAmountCurr").html(codeToText[currencyCode]);
                            $("#maxAmountCurr").html(codeToText[currencyCode]);
                        }

                        $(document).ready(function(){changeCurrency();});
                    </script>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="tariff.currency.operation.field.name" bundle="tariffsBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="field(currency)" disabled="${isEdit}" styleId="currencyCode" onchange="changeCurrency()">
                                <html:option value="RUB">810(${phiz:getCurrencySign("RUB")})</html:option>
                                <html:option value="USD">840(${phiz:getCurrencySign("USD")})</html:option>
                                <html:option value="EUR">978(${phiz:getCurrencySign("EUR")})</html:option>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <h3><bean:message key="tariff.commission.field.name" bundle="tariffsBundle"/></h3>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="tariff.percent.field.name" bundle="tariffsBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(percent)" maxlength="18"/> %
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="tariff.min.amount.min.amount" bundle="tariffsBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(minAmount)" maxlength="18" styleClass="moneyField"/> <span id="minAmountCurr"></span>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="tariff.min.amount.max.amount" bundle="tariffsBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(maxAmount)" maxlength="18" styleClass="moneyField"/> <span id="maxAmountCurr"></span>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle" value="tariffsBundle"/>
                        <tiles:put name="action" value="/dictionary/tariffs/transferOtherBank.do"/>
                    </tiles:insert>

                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"  value="tariffsBundle"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

</html:form>
