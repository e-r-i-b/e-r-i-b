<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/dictionaries/provider/overview" onsubmit="return setEmptyAction(event);" enctype="multipart/form-data">
<tiles:insert definition="recipientDictionariesEdit">
<c:set var="frm" value="${phiz:currentForm(pageContext)}" scope="request"/>
<c:set var="isNew" value="${empty frm.id}"/>
<c:set var="isEditable" value="${frm.editable}" scope="request"/>
<c:set var="impliesSetupGroupRisk" value="${phiz:impliesOperation('SetupGroupRiskServiceProvidersOperation', 'EditGroupRiskServiceProvider')}"/>
<c:set var="isManagementExtended" value="${phiz:impliesOperation('EditServiceProviderVisibilityAndFederalOperation', null)}" scope="request"/>
<c:set var="isFederalDisabled" value="${!isEditable || !isManagementExtended}" scope="request"/>

    <tiles:put name="submenu" value="Provider/Overview"/>

    <tiles:put name="menu" type="string">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.print"/>
            <tiles:put name="commandHelpKey" value="button.print.help"/>
            <tiles:put name="bundle" value="providerBundle"/>
            <tiles:put name="onclick" value="doPrintProvider(${frm.id})"/>
            <tiles:put name="viewType" value="blueBorder"/>
        </tiles:insert>

        <c:if test="${isEditable}">
            <c:if test="${not isNew}">
                <c:if test='${frm.fields.state eq "NOT_ACTIVE" || frm.fields.state eq "MIGRATION"}'>
                    <tiles:insert definition="commandButton" flush="false"
                                  operation="ActivateOrLockServiceProviderOperation">
                        <tiles:put name="commandKey" value="button.activate"/>
                        <tiles:put name="commandHelpKey" value="button.activate.help"/>
                        <tiles:put name="bundle" value="providerBundle"/>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:if>
                <c:if test='${frm.fields.state eq "ACTIVE"}'>
                    <tiles:insert definition="commandButton" flush="false"
                                  operation="ActivateOrLockServiceProviderOperation">
                        <tiles:put name="commandKey" value="button.lock"/>
                        <tiles:put name="commandHelpKey" value="button.lock.help"/>
                        <tiles:put name="bundle" value="providerBundle"/>
                        <tiles:put name="validationFunction">checkProviderType('${frm.internetShop}');</tiles:put>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:if>
            </c:if>
        </c:if>
        <c:if test="${!isEditable && impliesSetupGroupRisk}">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.setupGroupRisk"/>
                <tiles:put name="commandHelpKey" value="button.setupGroupRisk.help"/>
                <tiles:put name="bundle" value="providerBundle"/>
                <tiles:put name="postbackNavigation" value="true"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </c:if>
    </tiles:put>

    <tiles:put name="data" type="string">
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/providers.js"></script>
        <script type="text/javascript">
            function setBankDetails()
            {
                <c:if test="${isEditable}">
                    var bankDetails = document.getElementById("bankDetailsYes");
                    if (bankDetails != null)
                    {
                        var isChecked = bankDetails.checked;

                        ensureElement("inn").disabled            = false;
                        ensureElement("kpp").disabled            = false;
                        ensureElement("account").disabled        = false;
                        ensureElement("bankCode").disabled       = false;
                        ensureElement("bankName").disabled       = false;
                        ensureElement("corAccount").disabled     = false;
                        ensureElement("bankButton").disabled     = false;
                        ensureElement("isFillBankDetails").value = !isChecked;
                    }
                </c:if>
            }

            function setBankDetailOnReady()
            {
                <c:if test="${isEditable}">
                    var bankDetails = document.getElementById("bankDetailsYes");
                    if (bankDetails != null)
                    {
                        ensureElement("inn").disabled            = false;
                        ensureElement("kpp").disabled            = false;
                        ensureElement("account").disabled        = false;
                        ensureElement("bankCode").disabled       = false;
                        ensureElement("bankName").disabled       = false;
                        ensureElement("corAccount").disabled     = false;
                        ensureElement("bankButton").disabled     = false;
                    }
                </c:if>
            }

            function showHideFacilitatorProperties(checkbox)
            {
                var div = $('#facilitatorProperties');
                if (checkbox.checked)
                    div.show();
                else
                    div.hide();
            }

            function confirmFacilitatorProperties(checkbox){
                if (!confirm("<bean:message bundle="providerBundle" key="facilitator.overview.checkbox.confirm"/>"))
                {
                    //при нажатии кнопки "Отмена" необходимо откатить изменение
                    checkbox.checked = !checkbox.checked;
                }
                clearLoadMessage();
            }

            function useESB(value)
            {
                disableOrEnableField('url', value);
            }

            $(document).ready(function(){
                var type = getElement('field(providerType)');
                <c:if test="${!isNew}">
                    type.disabled = true;
                </c:if>
                selectProviderType(type != null ? type.value : "BILLING");
                setBankDetailOnReady();
                showHideFacilitatorProperties(ensureElementByName('field(isFasilitator)'));
                useESB(getField('useESB').checked);
            });
        </script>

        <tiles:insert definition="paymentForm" flush="false">
            <tiles:put name="name">
                <bean:message bundle="providerBundle" key="edit.name"/>
            </tiles:put>
            <tiles:put name="description">
                <bean:message bundle="providerBundle" key="edit.description"/>
            </tiles:put>

            <tiles:put name="data">
                <html:hidden property="id" name="frm"/>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.state" bundle="providerBundle"/>
                    </tiles:put>
                    <tiles:put name="needMargin" value="true"/>
                    <tiles:put name="data">
                        <bean:message key="label.provider.state.${frm.fields.state}" bundle="providerBundle"/>
                    </tiles:put>
                </tiles:insert>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.field.group.risk" bundle="providerBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:select property="field(groupRiskId)" name="frm" styleClass="contactInput" disabled="${!impliesSetupGroupRisk}">
                            <c:choose>
                                <c:when test="${empty frm.groupsRisk}">
                                    <html:option value="" key="label.field.group.risk.empty.list" bundle="providerBundle"/>
                                </c:when>
                                <c:otherwise>
                                    <html:option value="" key="label.field.group.risk.not.select" bundle="providerBundle"/>
                                </c:otherwise>
                            </c:choose>
                            <c:forEach var="groupRisk" items="${frm.groupsRisk}">
                                <html:option value="${groupRisk.id}">
                                    <bean:write name="groupRisk" property="name"/><c:if test="${groupRisk.isDefault}">&nbsp;(по умолчанию)</c:if>
                                </html:option>
                            </c:forEach>
                        </html:select>
                    </tiles:put>
                </tiles:insert>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.type" bundle="providerBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:select property="field(providerType)" name="frm" styleClass="contactInput" disabled="${!isEditable}"
                                     onchange="selectProviderType(getElementValue('field(providerType)'))">
                            <html:option value="BILLING"    key="label.provider.type.billing"   bundle="providerBundle"/>
                            <html:option value="TAXATION"   key="label.provider.type.taxation"  bundle="providerBundle"/>
                            <html:option value="EXTERNAL"   key="label.provider.type.external"  bundle="providerBundle"/>
                        </html:select>
                        <html:hidden property="field(providerType)" name="frm"/>
                    </tiles:put>
                </tiles:insert>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.subType" bundle="providerBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:select property="field(subType)" name="frm" styleClass="contactInput" disabled="${!isEditable}">
                            <html:option value="">Не указан</html:option>
                            <html:option value="mobile" key="label.provider.subType.mobile" bundle="providerBundle"/>
                            <html:option value="wallet" key="label.provider.subType.wallet" bundle="providerBundle"/>
                        </html:select>
                        <html:hidden property="field(subType)" name="frm"/>
                    </tiles:put>
                </tiles:insert>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.name" bundle="providerBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:hidden property="field(id)" name="frm"/>
                        <html:text property="field(name)" name="frm" size="40" maxlength="160" styleClass="contactInput" disabled="${!isEditable}"/>
                    </tiles:put>
                </tiles:insert>

                <div id="legalNameRow" class="showBilling showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.legal.name" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(legalName)" name="frm" size="40" maxlength="250" styleClass="contactInput" disabled="${!isEditable}"/>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div id="aliasRow" class="showBilling showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.alias.name" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(alias)" name="frm" size="40" maxlength="250" styleClass="contactInput" disabled="${!isEditable}"/>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div id="aliasRow" class="showBilling showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.sortPriority" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <c:set var="priority" value="${form.fields.sortPriority}"/>
                            <tiles:insert definition="scrollTemplate2" flush="false">
                                <tiles:put name="id" value="income"/>
                                <tiles:put name="minValue" value="0"/>
                                <tiles:put name="maxValue" value="100"/>
                                <tiles:put name="currValue" value="${empty priority ? 0 : priority}"/>
                                <tiles:put name="fieldName" value="fields(sortPriority)"/>
                                <tiles:put name="step" value="1"/>
                                <tiles:put name="round" value="0"/>
                                <tiles:put name="maxInputLength" value="3"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div id="popularRow" class="showBilling">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.show.popular" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <html:radio property="field(popular)" name="frm" value="true" disabled="${!isEditable}"/> <bean:message key="label.yes" bundle="providerBundle"/>
                            <html:radio property="field(popular)" name="frm" value="false" disabled="${!isEditable}"/> <bean:message key="label.no" bundle="providerBundle"/>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div id="propsOnlineRow" class="showBilling">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.props.online" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <html:radio property="field(propsOnline)" name="frm" value="true" disabled="${!isEditable}"/> <bean:message key="label.provider.yes" bundle="providerBundle"/>
                            <html:radio property="field(propsOnline)" name="frm" value="false" disabled="${!isEditable}"/> <bean:message key="label.provider.no" bundle="providerBundle"/>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div  id="isFillBankDetailsRow" class="showBilling showTax">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.fill.bank.detailse" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <input type="hidden" id="isFillBankDetails" value="${not frm.fields.bankDetails}"/>
                            <html:radio property="field(bankDetails)" name="frm" value="true"  styleId="bankDetailsYes" disabled="${!isEditable}" onclick="setBankDetails()"/> <bean:message key="label.provider.yes" bundle="providerBundle"/>
                            <html:radio property="field(bankDetails)" name="frm" value="false" styleId="bankDetailsNo" disabled="${!isEditable}" onclick="setBankDetails()"/> <bean:message key="label.provider.no"  bundle="providerBundle"/>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div id="bankDetailsRow">
                    <fieldset>
                        <legend>
                            <bean:message key="label.bank.detailse" bundle="providerBundle"/>
                        </legend>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.inn" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text styleId="inn" property="field(inn)" name="frm" size="40" styleClass="contactInput" maxlength="12" disabled="${!(isEditable)}"/>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.kpp" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:text styleId="kpp" property="field(kpp)" name="frm" size="40" styleClass="contactInput" maxlength="9" disabled="${!(isEditable)}"/>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.account" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="necessaryClassName" value="showBilling showTax"/>
                            <tiles:put name="data">
                                <html:text styleId="account" property="field(account)" name="frm" size="40" styleClass="contactInput" maxlength="25" disabled="${!(isEditable)}"/>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.bic" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="necessaryClassName" value="showBilling showTax"/>
                            <tiles:put name="data">
                                <html:text styleId="bankCode" property="field(bankCode)" name="frm" size="40" styleClass="contactInput" readonly="true" disabled="${!(isEditable)}"/>
                                <input type="button" class="buttWhite smButt" id="bankButton"
                                       onclick="openNationalBanksDictionary(setBankInfo,getFieldValue('field(bankName)'),getFieldValue('field(bankCode)'));"
                                       value="..." disabled="${!(isEditable)}"/>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.bankName" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="necessaryClassName" value="showBilling showTax"/>
                            <tiles:put name="data">
                                <html:text styleId="bankName" property="field(bankName)" name="frm" size="40" styleClass="contactInput" disabled="${!isEditable}"/>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.corAccount" bundle="providerBundle"/>
                                <span id="corAccountSpace" class="showTax">&nbsp;&nbsp;</span>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:text styleId="corAccount" property="field(corAccount)" name="frm" size="40" styleClass="contactInput" readonly="true" disabled="${!isEditable}"/>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.clear"/>
                                    <tiles:put name="commandHelpKey" value="button.clear.help"/>
                                    <tiles:put name="bundle" value="providerBundle"/>
                                    <tiles:put name="onclick" value="clearBankDetailFields();"/>
                                    <tiles:put name="enabled" value="${isEditable}"/>
                                    <tiles:put name="viewType" value="buttonGrayNew"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>
                </div>
                <div id="codeSBOLRow" class="showBilling showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.provider.code.SBOL" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(codeRecipientSBOL)" name="frm" size="40"  maxlength="32" styleClass="contactInput" disabled="${!isEditable}"/>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.providerCode" bundle="providerBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:text property="field(providerCode)" name="frm" size="40" maxlength="20" styleClass="contactInput" disabled="${!isEditable}"/>
                    </tiles:put>
                </tiles:insert>

                <div id="payTypeRow" class="showTax">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.pay.type" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="field(payType)" name="frm" styleClass="contactInput" disabled="${!isEditable}">
                                <html:option value="TAX"    key="label.pay.type.tax"    bundle="providerBundle"/>
                                <html:option value="BUDGET" key="label.pay.type.budget" bundle="providerBundle"/>
                                <html:option value="OFF_BUDGET" key="label.pay.type.off_budget" bundle="providerBundle"/>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div id="useESB" class="showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.external.useESB" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:checkbox property="field(useESB)" onchange="useESB(this.checked)" name="frm" value="true"/>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div id="url" class="showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.external.url" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(url)" name="frm" size="40" maxlength="256" styleClass="contactInput" disabled="${!isEditable}"/>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div id="backUrl" class="showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.external.backUrl" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="field(backUrlAction)" name="frm" styleClass="contactInput" disabled="${!isEditable}">
                                <html:option value="false" key="lable.backUrl.stay" bundle="providerBundle"/>
                                <html:option value="true" key="lable.backUrl.goBack" bundle="providerBundle"/>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div id="backUrl" class="showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.external.backUrl.url" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(providerBackUrl)" name="frm" size="40" maxlength="1000" styleClass="contactInput" disabled="${!isEditable}"/>
                        </tiles:put>
                    </tiles:insert>
                </div>

                <div id="checkOrder" class="showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.external.сheckOrder" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:select property="field(checkOrder)" name="frm" styleClass="contactInput" disabled="${!isEditable}">
                                <html:option value="true" key="lable.checkOrder.check" bundle="providerBundle"/>
                                <html:option value="false" key="lable.checkOrder.notCheck" bundle="providerBundle"/>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>
                </div>

                <div id="formName" class="showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.external.form.name" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="field(formName)" name="frm" styleClass="contactInput" disabled="${!isEditable}">
                                <html:option value="FNSPayment"><bean:message key="label.external.form.name.fns" bundle="providerBundle"/></html:option>
                                <html:option value="ExternalProviderPayment"><bean:message key="label.external.form.name.external.provider" bundle="providerBundle"/></html:option>
                                <html:option value="AirlineReservationPayment"><bean:message key="label.external.form.name.airline.reservation" bundle="providerBundle"/></html:option>
                                <html:option value="RurPayJurSB"><bean:message key="label.external.form.name.uec" bundle="providerBundle"/></html:option>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>
                </div>

                <div id="sendChargeOffInfo" class="showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.external.sendChargeOffInfo" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:checkbox property="field(sendChargeOffInfo)" name="frm" value="true"/>
                        </tiles:put>
                    </tiles:insert>
                </div>

                <div id="availableMBCheck" class="showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.external.availableMBCheck" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:checkbox property="field(availableMBCheck)" onclick="confirmFacilitatorProperties(this)" name="frm" value="true"/>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div id="availableMobileCheckout" class="showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.external.availableMobileCheckout" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:checkbox property="field(availableMobileCheckout)" onclick="confirmFacilitatorProperties(this)" name="frm" value="true"/>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div id="isFasilitator" class="showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.external.facilitator" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:checkbox property="field(isFasilitator)" name="frm" value="true" onchange="showHideFacilitatorProperties(this);"/>
                        </tiles:put>
                    </tiles:insert>
                </div>

                <div id="facilitatorProperties" class="showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.external.facilitator.properties" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <div class="provoderPropertiesGroup">
                                <table>
                                    <tr>
                                        <td>
                                            <bean:message key="label.external.facilitator.properties.mobileCheckout" bundle="providerBundle"/><br/>
                                            <html:checkbox property="field(mobileCheckoutProperty)" name="frm" value="true"/>
                                        </td>
                                        <td>
                                            <bean:message key="label.external.facilitator.properties.EInvoicing" bundle="providerBundle"/><br/>
                                            <html:checkbox property="field(eInvoicingProperty)" name="frm" value="true"/>
                                        </td>
                                        <td>
                                            <bean:message key="label.external.facilitator.properties.mbCheck" bundle="providerBundle"/><br/>
                                            <html:checkbox property="field(mbCheckProperty)" name="frm" value="true"/>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </tiles:put>
                    </tiles:insert>
                </div>

                <div id="billingRow" class="showBilling showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.billing" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(billingName)" name="frm" size="40" styleClass="contactInput" readonly="true" disabled="${!isEditable}"/>
                            <c:if test="${isEditable}">
                                <html:hidden property="field(billingId)" name="frm"/>
                                <input type="button" class="buttWhite smButt"
                                       onclick="openBillingsDictionary(setBillingInfo);" value="..."/>
                            </c:if>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div id="serviceNameRow" class="showBilling showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.service.name" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(nameService)" name="frm" size="40" maxlength="150" styleClass="contactInput" disabled="${!isEditable}"/>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div id="serviceCodeRow" class="showBilling showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.service.code" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(serviceCode)" name="frm" size="40" styleClass="contactInput" disabled="${!isEditable}"/>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div id="accountTypeRow" class="showBilling showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.account.type" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="field(accountType)" name="frm" styleClass="contactInput" disabled="${!isEditable}">
                                <html:option value="ALL"     key="label.account.type.all"     bundle="providerBundle"/>
                                <html:option value="DEPOSIT" key="label.account.type.deposit" bundle="providerBundle"/>
                                <html:option value="CARD"    key="label.account.type.card"    bundle="providerBundle"/>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div id="debtSupportedRow" class="showBilling">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.debt.supported" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="field(debtSupported)" name="frm" styleClass="contactInput" disabled="${!isEditable}">
                                <html:option value="true" key="label.debt.supported.yes" bundle="providerBundle"/>
                                <html:option value="false" key="label.debt.supported.no" bundle="providerBundle"/>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div id="federalRow" class="showBilling">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.provider.is.federal" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="field(federal)" name="frm" styleClass="contactInput" disabled="${isFederalDisabled}" styleId="isFederal">
                                <html:option value="true" key="label.yes" bundle="providerBundle"/>
                                <html:option value="false" key="label.no" bundle="providerBundle"/>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <c:if test="${isFederalDisabled}">
                    <html:hidden property="field(federal)" name="frm"/>
                </c:if>
                <div id="fullPaymentRow" class="showTax">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.full.payment" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:radio property="field(fullPayment)" name="frm" value="true" disabled="${!isEditable}"/> <bean:message key="label.full.payment.yes" bundle="providerBundle"/>
                            <html:radio property="field(fullPayment)" name="frm" value="false" disabled="${!isEditable}"/> <bean:message key="label.full.payment.no"  bundle="providerBundle"/>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <fieldset>
                   <legend>
                       <bean:message key="label.payment.parameters" bundle="providerBundle"/>
                   </legend>
                   <tiles:insert definition="simpleFormRow" flush="false">
                       <tiles:put name="title">
                           <bean:message key="label.department" bundle="providerBundle"/>
                       </tiles:put>
                       <tiles:put name="isNecessary" value="true"/>
                       <tiles:put name="data">
                           <html:hidden property="field(departmentId)" name="frm"/>
                           <html:text property="field(departmentName)" name="frm" size="40" styleClass="contactInput" readonly="true" disabled="${!isEditable}"/>
                           <c:if test="${isEditable}">
                               <input type="button" class="buttWhite" onclick="openDepartmentsDictionary(setDepartmentInfo, 'null');"
                                      value="..."/>
                           </c:if>
                       </tiles:put>
                   </tiles:insert>
                   <div id="transitAccountRow" class="showBilling showTax">
                       <tiles:insert definition="simpleFormRow" flush="false">
                           <tiles:put name="title">
                               <bean:message key="label.transAccount" bundle="providerBundle"/>
                           </tiles:put>
                           <tiles:put name="isNecessary" value="true"/>
                           <tiles:put name="data">
                               <html:text property="field(transitAccount)" name="frm" maxlength="25" size="40" styleClass="contactInput" disabled="${!isEditable}"/>
                           </tiles:put>
                       </tiles:insert>
                   </div>
                </fieldset>
                <div id="groundRow" class="showBilling">
                   <fieldset>
                       <legend>
                           <bean:message key="label.ground" bundle="providerBundle"/>
                       </legend>
                           <tiles:insert definition="simpleFormRow" flush="false">
                               <tiles:put name="data">
                                   <html:radio property="field(ground)" name="frm" value="false" disabled="${!isEditable}"/> <bean:message key="label.not.form.ground"bundle="providerBundle"/>
                               </tiles:put>
                           </tiles:insert>
                           <tiles:insert definition="simpleFormRow" flush="false">
                               <tiles:put name="data">
                                   <html:radio property="field(ground)" name="frm" value="true" disabled="${!isEditable}"/> <bean:message key="label.form.ground" bundle="providerBundle"/>
                               </tiles:put>
                           </tiles:insert>
                           <tiles:insert definition="simpleFormRow" flush="false">
                               <tiles:put name="title">
                                   <bean:message key="label.separator" bundle="providerBundle"/>
                               </tiles:put>
                               <tiles:put name="isNecessary" value="true"/>
                               <tiles:put name="data">
                                   <html:text property="field(separator1)" name="frm"  size="40" maxlength="1" styleClass="contactInput" disabled="${!isEditable}"/>
                               </tiles:put>
                           </tiles:insert>
                       <tiles:insert definition="simpleFormRow" flush="false">
                           <tiles:put name="title">
                               <bean:message key="label.value.separator" bundle="providerBundle"/>
                           </tiles:put>
                           <tiles:put name="isNecessary" value="true"/>
                           <tiles:put name="data">
                               <html:text property="field(separator2)" name="frm" size="40" maxlength="1" styleClass="contactInput" disabled="${!isEditable}"/>
                           </tiles:put>
                       </tiles:insert>
                   </fieldset>
                </div>
                <div id="eng4000nsiRow" class="showBilling showTax">
                   <tiles:insert definition="simpleFormRow" flush="false">
                       <tiles:put name="title">
                           <bean:message key="label.eng4000nsi" bundle="providerBundle"/>
                       </tiles:put>
                       <tiles:put name="data">
                           <html:text property="field(eng4000nsi)" name="frm" size="40" styleClass="contactInput" maxlength="9" disabled="${!isEditable}"/>
                       </tiles:put>
                   </tiles:insert>
                </div>
                <tiles:insert definition="simpleFormRow" flush="false">
                   <tiles:put name="title">
                       <bean:message key="label.comment" bundle="providerBundle"/>
                   </tiles:put>
                   <tiles:put name="isNecessary" value="true"/>
                   <tiles:put name="data">
                       <html:textarea property="field(comment)" name="frm" cols="40" rows="5" styleClass="contactInput" disabled="${!isEditable}"/>
                   </tiles:put>
                </tiles:insert>
                <tiles:insert definition="simpleFormRow" flush="false">
                   <tiles:put name="title">
                       <bean:message key="label.provider.help" bundle="providerBundle"/>
                   </tiles:put>
                   <tiles:put name="data">
                       <html:textarea property="field(tip)" styleId="shortText" name="frm" cols="40" rows="5" styleClass="contactInput" disabled="${!isEditable}"/>
                   </tiles:put>
                </tiles:insert>
                <tiles:insert definition="simpleFormRow" flush="false">
                   <tiles:put name="title">
                       <bean:message key="label.commission.message" bundle="providerBundle"/>
                   </tiles:put>
                   <tiles:put name="data">
                       <html:textarea property="field(commissionMessage)" styleId="shortText" name="frm" cols="40" rows="5" styleClass="contactInput" disabled="${!isEditable}"/>
                   </tiles:put>
                </tiles:insert>
                <div id="nameOnBillRow" class="showBilling showExternal">
                   <tiles:insert definition="simpleFormRow" flush="false">
                       <tiles:put name="title">
                           <bean:message key="label.name.on.bill" bundle="providerBundle"/>
                       </tiles:put>
                       <tiles:put name="data">
                           <html:text property="field(nameOnBill)" name="frm" size="40" styleClass="contactInput" maxlength="250" disabled="${!isEditable}"/>
                       </tiles:put>
                   </tiles:insert>
                </div>
                <div id="imageSenderRow" class="showBilling showExternal">
                   <tiles:insert definition="simpleFormRow" flush="false">
                       <tiles:put name="title">
                           <bean:message key="label.logo" bundle="providerBundle"/>
                       </tiles:put>
                       <tiles:put name="data">
                           <tiles:insert definition="imageInput" flush="false">
                               <tiles:put name="id"  value="Icon"/>
                               <tiles:put name="maxSize"  value="40"/>
                               <tiles:put name="readonly" value="${!isEditable}"/>
                           </tiles:insert>
                       </tiles:put>
                   </tiles:insert>
                </div>
                <div id="imageHelpRow" class="showBilling showExternal">
                   <tiles:insert definition="simpleFormRow" flush="false">
                       <tiles:put name="title">
                           <bean:message key="label.image.help" bundle="providerBundle"/>
                       </tiles:put>
                       <tiles:put name="data">
                           <html:text property="field(imageHelp)" name="frm" size="40" maxlength="25" styleClass="contactInput" disabled="${!isEditable}"/>
                       </tiles:put>
                   </tiles:insert>
                </div>
                <div id="phoneNumberRow">
                   <tiles:insert definition="simpleFormRow" flush="false">
                       <tiles:put name="title">
                           <bean:message key="label.provider.phoneNumber" bundle="providerBundle"/>
                       </tiles:put>
                       <tiles:put name="data">
                           <html:text property="field(phoneNumber)" name="frm" size="40" maxlength="15" styleClass="contactInput" disabled="${!isEditable}"/>
                       </tiles:put>
                   </tiles:insert>
                </div>

                <div id="templateSupported" class="showBilling">
                   <tiles:insert definition="simpleFormRow" flush="false">
                       <tiles:put name="title">
                           <bean:message key="label.templatesupported" bundle="providerBundle"/>
                       </tiles:put>
                       <tiles:put name="data">
                           <html:checkbox property="field(templateSupported)" name="frm" value="true"  disabled="${!isEditable}"/>
                       </tiles:put>
                   </tiles:insert>
                </div>
                <div id="planingForDeactivate" class="showBilling">
                   <tiles:insert definition="simpleFormRow" flush="false">
                       <tiles:put name="title">
                           <bean:message key="label.planingForDeactivate" bundle="providerBundle"/>
                       </tiles:put>
                       <tiles:put name="data">
                           <html:checkbox property="field(planingForDeactivate)" name="frm" value="true"  disabled="${!isEditable}"/>
                       </tiles:put>
                   </tiles:insert>
                </div>
                <div id="editPaymentSupported" class="showBilling">
                   <tiles:insert definition="simpleFormRow" flush="false">
                       <tiles:put name="title">
                           <bean:message key="label.editPaymentSupported" bundle="providerBundle"/>
                       </tiles:put>
                       <tiles:put name="data">
                           <html:checkbox property="field(editPaymentSupported)" name="frm" value="true" disabled="${!isEditable}"/>
                           <bean:message key="description.editPaymentSupported" bundle="providerBundle"/>
                       </tiles:put>
                   </tiles:insert>
                </div>
                <jsp:include page="configurationVisibilityInChannels.jsp" />
                <div id="smsAliasRow" class="showBilling">
                   <fieldset>
                       <legend>
                           <bean:message key="label.ERMB.alias" bundle="providerBundle"/>
                       </legend>
                       <c:if test="${not empty frm.smsAliases}">
                           <tiles:insert definition="simpleFormRow" flush="false">
                               <tiles:put name="data">
                                   <tiles:insert definition="commandButton" flush="false"
                                                 operation="EditServiceProvidersOperation">
                                       <tiles:put name="commandKey" value="button.removeSmsAlias"/>
                                       <tiles:put name="commandHelpKey" value="button.removeSmsAlias"/>
                                       <tiles:put name="bundle" value="providerBundle"/>
                                       <tiles:put name="postbackNavigation" value="true"/>
                                       <tiles:put name="validationFunction" value="validateSelection()"/>
                                       <tiles:put name="viewType" value="buttonGrayNew"/>
                                   </tiles:insert>
                               </tiles:put>
                           </tiles:insert>
                       </c:if>

                       <tiles:insert definition="simpleTableTemplate" flush="false" >
                           <tiles:put name="grid">
                               <sl:collection id="alias"  property="smsAliases" model="list" bundle="providerBundle">
                                   <c:if test="${isEditable}">
                                       <sl:collectionParam id="selectType" value="checkbox"/>
                                       <sl:collectionParam id="selectName" value="selectedIds"/>
                                       <sl:collectionParam id="selectProperty" value="id"/>
                                   </c:if>
                                   <sl:collectionItem title="label.sms.alias" value="${alias.name}" width="80%"/>
                                   <c:set var='url'>${phiz:calculateActionURL(pageContext,'/dictionaries/provider/sms/alias.do')}</c:set>
                                   <sl:collectionItem title="label.sms.alias.fields">
                                       <tiles:insert definition="clientButton" operation="EditSmsAliasFieldOperation" flush="false">
                                           <tiles:put name="commandTextKey" value="button.edit.smsAlias.threePoints"/>
                                           <tiles:put name="commandHelpKey" value="button.edit.smsAlias.threePoints.help"/>
                                           <tiles:put name="bundle" value="providerBundle"/>
                                           <tiles:put name="onclick">openAliasWindow('${url}', ${alias.id});</tiles:put>
                                           <tiles:put name="viewType" value="buttonGrayNew"/>
                                       </tiles:insert>
                                   </sl:collectionItem>
                               </sl:collection>
                           </tiles:put>
                           <tiles:put name="isEmpty" value="${empty frm.smsAliases}"/>
                           <tiles:put name="emptyMessage" value="У поставщика услуг нет смс псевдонимов"/>
                       </tiles:insert>
                       <tiles:insert definition="simpleFormRow" flush="false">
                           <tiles:put name="data">
                               <c:if test="${isEditable}">
                                   <input maxlength="20" size="20" name="field(smsAlias)">
                                   <tiles:insert definition="commandButton" flush="false">
                                       <tiles:put name="commandKey" value="button.addSmsAlias"/>
                                       <tiles:put name="commandHelpKey" value="button.addSmsAlias"/>
                                       <tiles:put name="bundle" value="providerBundle"/>
                                       <tiles:put name="postbackNavigation" value="true"/>
                                       <tiles:put name="viewType" value="buttonGrayNew"/>
                                   </tiles:insert>
                               </c:if>
                           </tiles:put>
                       </tiles:insert>
                    </fieldset>
                </div>
                <div id="smsTemplateRow" class="showBilling">
                   <fieldset>
                       <legend>
                           <bean:message key="label.template.legend" bundle="providerBundle"/>
                       </legend>
                       <div id="mobilebankCodeRow" class="showBilling">
                           <tiles:insert definition="simpleFormRow" flush="false">
                               <tiles:put name="title">
                                   <bean:message key="label.mobilebank.code" bundle="providerBundle"/>
                               </tiles:put>
                               <tiles:put name="data">
                                   <html:text property="field(mobilebankCode)" name="frm" size="40" maxlength="32" styleClass="contactInput" disabled="${!isEditable}"/>
                               </tiles:put>
                           </tiles:insert>
                       </div>
                       <tiles:insert definition="simpleFormRow" flush="false">
                           <tiles:put name="title">
                               <bean:message key="label.mobilebank.standart" bundle="providerBundle"/>
                           </tiles:put>
                           <tiles:put name="data">
                               <html:checkbox property="field(standartSmsTemplate)" name="frm" value="true"
                                              onclick="toogleSmsTemplateAdditionalInfo();" disabled="${!isEditable}"/>
                           </tiles:put>
                       </tiles:insert>
                       <tiles:insert definition="simpleFormRow" flush="false">
                           <tiles:put name="title">
                               <bean:message key="label.mobilebank.format" bundle="providerBundle"/>
                           </tiles:put>
                           <tiles:put name="data">
                               <html:text property="field(format)" name="frm" size="40" maxlength="255"
                                          styleClass="contactInput" disabled="${!isEditable}"/>
                               <html:text property="field(defaultFormat)" name="frm" size="40" maxlength="255" styleClass="contactInput" disabled="true"/>
                           </tiles:put>
                       </tiles:insert>
                       <tiles:insert definition="simpleFormRow" flush="false">
                           <tiles:put name="title">
                               <bean:message key="label.mobilebank.example" bundle="providerBundle"/>
                           </tiles:put>
                           <tiles:put name="data">
                               <html:text property="field(example)" name="frm" size="40" maxlength="255"
                                          styleClass="contactInput" disabled="${!isEditable}"/>
                               <html:text property="field(defaultExample)" name="frm" size="40" maxlength="255" styleClass="contactInput" disabled="true"/>
                           </tiles:put>
                       </tiles:insert>
                       <tiles:insert definition="simpleFormRow" flush="false">
                           <tiles:put name="data">
                               Параметр #counter# обозначает номер шаблона для данного поставщика.
                               Т.е. первым созданным шаблоном будет "код_поставщика 1 [сумма]", вторым - "код_поставщика 2 [сумма]".
                           </tiles:put>
                       </tiles:insert>
                   </fieldset>
                </div>
                <div id="smsTemplateRow" class="showBilling">
                    <fieldset>
                        <legend>
                            <bean:message key="label.bar.supported.legend" bundle="providerBundle"/>
                        </legend>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.bar.supported" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:checkbox property="field(isBarSupported)" name="frm" value="true" disabled="${!isEditable}"/>
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>
                </div>
                <br/>
                <div>
                    <html:checkbox property="field(isOfflineAvailable)" name="frm" value="true" disabled="${!isEditable}"/>
                    <bean:message key="label.offline.available" bundle="providerBundle"/>
                </div>
                <br/>

                <fieldset>
                    <legend>Графическая подсказка</legend>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">Изображение</tiles:put>
                        <tiles:put name="data">
                            <tiles:insert definition="imageInput" flush="false">
                                <tiles:put name="id"  value="Help"/>
                                <tiles:put name="maxSize"  value="40"/>
                                <tiles:put name="readonly" value="${!isEditable}"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Название подсказки
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(imageHelpTitle)" name="frm" size="40" maxlength="100" styleClass="contactInput" disabled="${!isEditable}"/>
                        </tiles:put>
                    </tiles:insert>
                </fieldset>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.credit.card.deny" bundle="providerBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:checkbox property="field(IsCreditCardAccessible)" name="frm" value="true" disabled="${!isEditable}"/>
                    </tiles:put>
                </tiles:insert>

                <script type="text/javascript">
                    toogleSmsTemplateAdditionalInfo();
                    toogleAvaiblePayments();
                </script>
            </tiles:put>

            <tiles:put name="buttons">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    <tiles:put name="bundle" value="providerBundle"/>
                    <tiles:put name="action" value="/private/dictionaries/provider/list.do"/>
                </tiles:insert>
                <c:choose>
                    <c:when test="${phiz:impliesOperation('SetupGroupRiskServiceProvidersOperation', 'EditGroupRiskServiceProvider')}">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.setupGroupRisk"/>
                            <tiles:put name="commandHelpKey" value="button.setupGroupRisk.help"/>
                            <tiles:put name="bundle" value="providerBundle"/>
                            <tiles:put name="postbackNavigation" value="true"/>
                        </tiles:insert>
                    </c:when>
                    <c:otherwise>
                        <tiles:insert definition="commandButton" flush="false" operation="EditServiceProvidersOperation">
                            <tiles:put name="commandKey" value="button.save"/>
                            <tiles:put name="commandHelpKey" value="button.save.help"/>
                            <tiles:put name="bundle" value="providerBundle"/>
                            <tiles:put name="postbackNavigation" value="true"/>
                        </tiles:insert>
                    </c:otherwise>
                </c:choose>
                <c:if test="${not empty frm.id}">
                    <tiles:insert definition="languageSelectForEdit" operation="EditServiceProvidersLanguageOperation" flush="false">
                        <tiles:put name="selectId" value="chooseLocale"/>
                        <tiles:put name="entityId" value="${frm.id}"/>
                        <tiles:put name="styleClass" value="float"/>
                        <tiles:put name="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/provider/fields/language/save')}"/>
                    </tiles:insert>
                </c:if>
            </tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>