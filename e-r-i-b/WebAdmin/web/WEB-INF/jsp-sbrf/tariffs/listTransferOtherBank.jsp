<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<html:form action="/dictionary/tariffs/transferOtherBank">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="listTariffTransferOtherBank">
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false"
                          service="TariffTransferOtherBankService"
                          operation="EditTariffTransferOtherBankOperation">
                <tiles:put name="commandTextKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add"/>
                <tiles:put name="bundle"         value="tariffsBundle"/>
                <tiles:put name="action"         value="/dictionary/tariffs/transferOtherBank/edit.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey"         value="button.remove"/>
                <tiles:put name="commandHelpKey"     value="button.remove.help"/>
                <tiles:put name="commandTextKey"     value="button.remove"/>
                <tiles:put name="bundle"             value="tariffsBundle"/>
                <tiles:put name="validationFunction">
                    checkSelection('selectedIds', 'Выберите тариф для удаления');
                </tiles:put>
                <tiles:put name="confirmText"><bean:message key="tariff.delete.confirm.text" bundle="tariffsBundle"/></tiles:put>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name"><bean:message key="tariff.transfer.other.bank.list.title" bundle="tariffsBundle"/></tiles:put>
                <tiles:put name="description" type="string">
                    <bean:message key="tariff.transfer.other.bank.list.description" bundle="tariffsBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <div class="smallDynamicGrid">
                        <tiles:insert definition="tableTemplate" flush="false">
                            <tiles:put name="grid" type="string">
                                <sl:collection id="tariff" model="list" property="tariffsInOtherBank" bundle="tariffsBundle">
                                    <sl:collectionParam id="selectName" value="selectedIds"/>
                                    <sl:collectionParam id="selectType" value="checkbox"/>
                                    <sl:collectionParam id="selectProperty" value="id"/>

                                    <sl:collectionItem title="tariff.currency.field.name">
                                        <c:if test="${not empty tariff}">
                                            <phiz:link action="/dictionary/tariffs/transferOtherBank/edit"
                                                       serviceId="TariffTransferOtherBankService"
                                                       operationClass="EditTariffTransferOtherBankOperation">
                                                <phiz:param name="id" value="${tariff.id}"/> 
                                                ${phiz:getCurrencySign(tariff.currencyCode)}
                                            </phiz:link>
                                        </c:if>
                                    </sl:collectionItem>

                                     <sl:collectionItem title="tariff.commission.field.name">
                                        <c:if test="${not empty tariff}">
                                            <c:set var="isEmptyDesc" value="true"/>
                                            <c:if test="${not empty tariff.percent}">
                                                <bean:write name="tariff" property="percent" format="###,##0.00"/>
                                                <bean:message key="tariff.percent" bundle="tariffsBundle"/>
                                                <c:set var="isEmptyDesc" value="false"/>
                                            </c:if>
                                            <c:if test="${not empty tariff.minAmount}">
                                                <c:if test="${!isEmptyDesc}">, </c:if>
                                                <bean:message key="tariff.min.amount" bundle="tariffsBundle"/>
                                                <bean:write name="tariff" property="minAmount" format="###,##0.00"/>
                                                <c:set var="isEmptyDesc" value="false"/>
                                            </c:if>
                                            <c:if test="${not empty tariff.maxAmount}">
                                                <c:if test="${!isEmptyDesc}">, </c:if>
                                                <bean:message key="tariff.max.amount" bundle="tariffsBundle"/>
                                                <bean:write name="tariff" property="maxAmount" format="###,##0.00"/>
                                                <c:set var="isEmptyDesc" value="false"/>
                                            </c:if>
                                        </c:if>
                                    </sl:collectionItem>
                                </sl:collection>
                            </tiles:put>
                            <tiles:put name="isEmpty" value="${fn:length(form.tariffsInOtherBank) == 0}"/>
                            <tiles:put name="emptyMessage" type="string">
                                <bean:message key="tariff.not.specified" bundle="tariffsBundle"/>
                            </tiles:put>
                        </tiles:insert>
                    </div>
                </tiles:put>
            </tiles:insert>

        </tiles:put>
    </tiles:insert>

</html:form>
