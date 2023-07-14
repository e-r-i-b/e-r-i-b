<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/payments/restrictions" onsubmit="return setEmptyAction(event);">
    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="showCheckbox" value="${form.replication}"/>

    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" type="string" value="tablePropertiesConfigEdit"/>
        <tiles:put name="submenu" type="string" value="EditPaymentRestrictionsSettings"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="additionalStyle" value="propertiesForm"/>
                <tiles:put name="name"><bean:message bundle="configureBundle" key="operation.restriction.page.title"/></tiles:put>
                <tiles:put name="description"><bean:message bundle="configureBundle" key="operation.restriction.page.desc"/></tiles:put>
                <tiles:put name="data">

                    <table class="formRowTbl">
                        <c:if test="${!showCheckbox}">
                            <tr>
                                <td>
                                    <bean:message bundle="configureBundle" key="operation.restriction.page.overall"/>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty form.overallLimit}">
                                            <input type="text" value="${form.overallLimit.amount.decimal}" readonly="true" maxlength="10" size="10"/>

                                            <img src="${imagePath}/hint.png" alt=""
                                                    <c:if test="${not empty form.overallLimit}">
                                                        title='Действует с <bean:write name="form" property="overallLimit.startDate.time" format="dd.MM.yyyy"/> по <bean:write name="form" property="overallLimit.endDate.time" format="dd.MM.yyyy"/>'
                                                    </c:if>
                                                 class="hint"/>

                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" disabled maxlength="10" size="10"/>
                                        </c:otherwise>
                                    </c:choose>

                                    <c:set var="url" value="/limits/overall/list.do"/>
                                    <a href="${phiz:calculateActionURL(pageContext, url)}">Подробнее...</a>
                                    <c:if test="${form.overallLimit.status == 'ARCHIVAL'}">
                                        <p><bean:message bundle="configureBundle" key="operation.restriction.page.overall.limit.expired"/></p>
                                    </c:if>
                                </td>
                            </tr>
                        </c:if>

                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.business.PaymentRestriction.RUB"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="operation.restriction.page.field.rub"/></tiles:put>
                            <tiles:put name="textSize" value="10"/>
                            <tiles:put name="textMaxLength" value="10"/>
                            <tiles:put name="showHint">none</tiles:put>
                            <tiles:put name="tdStyle" value="paymentLabel"/>
                            <tiles:put name="requiredField" value="true"/>
                        </tiles:insert>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.business.PaymentRestriction.EUR"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="operation.restriction.page.field.eur"/></tiles:put>
                            <tiles:put name="textSize" value="10"/>
                            <tiles:put name="textMaxLength" value="10"/>
                            <tiles:put name="showHint">none</tiles:put>
                            <tiles:put name="tdStyle" value="paymentLabel"/>
                            <tiles:put name="requiredField" value="true"/>
                        </tiles:insert>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.business.PaymentRestriction.USD"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="operation.restriction.page.field.usd"/></tiles:put>
                            <tiles:put name="textSize" value="10"/>
                            <tiles:put name="textMaxLength" value="10"/>
                            <tiles:put name="showHint">none</tiles:put>
                            <tiles:put name="tdStyle" value="paymentLabel"/>
                            <tiles:put name="requiredField" value="true"/>
                        </tiles:insert>
                        <tr <c:if test="${showCheckbox}">class="replicationCheckboxTR"</c:if>>
                            <td colspan="2" class="vertical-align">
                                <table class="formRowTbl">
                                    <tiles:insert definition="propertyField" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.business.simple.infoPersonPaymentLimit"/>
                                        <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="operation.restriction.page.field.info.limit"/></tiles:put>
                                        <tiles:put name="textSize" value="10"/>
                                        <tiles:put name="textMaxLength" value="10"/>
                                        <tiles:put name="showHint">none</tiles:put>
                                        <tiles:put name="tdStyle" value="paymentLabel"/>
                                        <tiles:put name="requiredField" value="true"/>
                                    </tiles:insert>
                                </table>
                            </td>
                            <td>
                                <table>
                                    <tiles:insert definition="propertyField" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.business.simple.infoPersonPaymentLimit.message"/>
                                        <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="operation.restriction.page.field.info.limit.message"/></tiles:put>
                                        <tiles:put name="fieldType" value="textarea"/>
                                        <tiles:put name="textSize" value="10"/>
                                        <tiles:put name="textMaxLength" value="30"/>
                                        <tiles:put name="showHint">none</tiles:put>
                                        <tiles:put name="tdStyle" value="paymentLabel"/>
                                    </tiles:insert>
                                </table>
                            </td>
                        </tr>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="job.orders.clear.days.count"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="operation.restriction.page.field.clear.days"/></tiles:put>
                            <tiles:put name="textSize" value="10"/>
                            <tiles:put name="textMaxLength" value="10"/>
                            <tiles:put name="showHint">none</tiles:put>
                            <tiles:put name="tdStyle" value="paymentLabel"/>
                            <tiles:put name="requiredField" value="true"/>
                        </tiles:insert>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="job.orders.notify.max.count"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="operation.restriction.page.field.notification.count"/></tiles:put>
                            <tiles:put name="textSize" value="10"/>
                            <tiles:put name="textMaxLength" value="10"/>
                            <tiles:put name="showHint">none</tiles:put>
                            <tiles:put name="tdStyle" value="paymentLabel"/>
                            <tiles:put name="requiredField" value="true"/>
                        </tiles:insert>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.business.simple.clearNotConfirmDocumentsPeriod"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="operation.restriction.page.field.not.confirm.period"/></tiles:put>
                            <tiles:put name="textSize" value="10"/>
                            <tiles:put name="textMaxLength" value="5"/>
                            <tiles:put name="showHint">none</tiles:put>
                            <tiles:put name="tdStyle" value="paymentLabel"/>
                            <tiles:put name="requiredField" value="true"/>
                        </tiles:insert>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.business.simple.clearWaitConfirmDocumentsPeriod"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="operation.restriction.page.field.wait.confirm.period"/></tiles:put>
                            <tiles:put name="textSize" value="10"/>
                            <tiles:put name="textMaxLength" value="5"/>
                            <tiles:put name="showHint">none</tiles:put>
                            <tiles:put name="tdStyle" value="paymentLabel"/>
                            <tiles:put name="requiredField" value="true"/>
                        </tiles:insert>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.business.simple.yandexPaymentId"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="operation.restriction.page.field.yandex.payment.id"/></tiles:put>
                            <tiles:put name="textSize" value="10"/>
                            <tiles:put name="textMaxLength" value="10"/>
                            <tiles:put name="showHint">none</tiles:put>
                            <tiles:put name="tdStyle" value="paymentLabel"/>
                            <tiles:put name="requiredField" value="true"/>
                        </tiles:insert>
                        <tr>
                            <td class="paymentLabel  alignTop">
                                <bean:message bundle="configureBundle" key="operation.restriction.page.field.loan"/>
                                <span class="asterisk">*</span>
                            </td>
                            <td class="alignTop">
                                <c:set var="loanSystemField"><bean:message bundle="configureBundle" key="operation.restriction.page.loan.table.system"/></c:set>
                                <c:set var="receptionStartTime" value="Время начала приема документов"/>
                                <c:set var="receptionEndTime" value="Время окончания приема документов"/>
                                <div class="float">
                                    <tiles:insert definition="tableProperties" flush="false">
                                        <tiles:put name="bundle" value="commonBundle"/>
                                        <tiles:put name="propertyId" value="loanRestrictionProperties"/>
                                        <tiles:put name="showCheckbox" value="${showCheckbox}"/>
                                        <tiles:put name="fieldValue" value="com.rssl.business.LoanReceptionTimeProperty."/>
                                        <tiles:put name="tableTitle">
                                            <div class="propertiesTable float"><bean:message bundle="configureBundle" key="operation.restriction.page.loan.table.title"/></div>
                                            <tiles:insert definition="floatMessageShadow" flush="false">
                                                <tiles:put name="id" value="loanProp"/>
                                                <tiles:put name="hintClass" value="rightAlignHint indent-top"/>
                                                <tiles:put name="data"><img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px"/></tiles:put>
                                                <tiles:put name="showHintImg" value="false"/>
                                                <tiles:put name="text">
                                                    <bean:message bundle="configureBundle" key="operation.restriction.page.loan.prop.desc"/>
                                                </tiles:put>
                                                <tiles:put name="dataClass" value="dataHint"/>
                                            </tiles:insert>
                                        </tiles:put>
                                        <tiles:put name="tableHead">
                                            <th class="titleTable Width20">
                                                <input type="checkbox" onclick="switchSelection(this,'loanRestrictionProperties_Ids');" ${showCheckbox ? "disabled" : ""}>
                                            </th>
                                            <th class="titleTable" >${loanSystemField}</th>
                                            <th class="titleTable width350" >
                                                <bean:message bundle="configureBundle" key="operation.restriction.page.loan.table.time"/>
                                            </th>
                                        </tiles:put>
                                        <tiles:put name="dataClass" value="dataHint"/>
                                    </tiles:insert>
                                </div>
                            </td>
                        </tr>
                        <tr></tr>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="mobile.api.contact.sync.first.limit"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="operation.restriction.page.field.mobileapi.synch.contacts.limit.first"/></tiles:put>
                            <tiles:put name="textSize" value="5"/>
                            <tiles:put name="textMaxLength" value="5"/>
                            <tiles:put name="showHint">none</tiles:put>
                            <tiles:put name="tdStyle" value="paymentLabel"/>
                            <tiles:put name="requiredField" value="true"/>
                        </tiles:insert>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="mobile.api.contact.sync.per.day.limit"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="operation.restriction.page.field.mobileapi.synch.contacts.limit.daily"/></tiles:put>
                            <tiles:put name="textSize" value="5"/>
                            <tiles:put name="textMaxLength" value="5"/>
                            <tiles:put name="showHint">none</tiles:put>
                            <tiles:put name="tdStyle" value="paymentLabel"/>
                            <tiles:put name="requiredField" value="true"/>
                        </tiles:insert>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="mobile.api.contact.sync.per.week.limit"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="operation.restriction.page.field.mobileapi.synch.contacts.limit.weekly"/></tiles:put>
                            <tiles:put name="textSize" value="5"/>
                            <tiles:put name="textMaxLength" value="5"/>
                            <tiles:put name="showHint">none</tiles:put>
                            <tiles:put name="tdStyle" value="paymentLabel"/>
                            <tiles:put name="requiredField" value="true"/>
                        </tiles:insert>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="global.basket.proxy.listener.enable.state"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="operation.restriction.page.field.basket.message.exchange"/></tiles:put>
                            <tiles:put name="fieldType" value="checkbox"/>
                            <tiles:put name="showHint">none</tiles:put>
                            <tiles:put name="tdStyle" value="paymentLabel"/>
                        </tiles:insert>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.phizic.service.basket.queue.working.mode"/>
                            <tiles:put name="fieldDescription">
                                <bean:message bundle="configureBundle" key="operation.restriction.page.field.basket.message.mode"/>
                            </tiles:put>
                            <tiles:put name="fieldType" value="radio"/>
                            <tiles:put name="fieldHint">
                                <bean:message bundle="configureBundle" key="operation.restriction.page.field.basket.message.mode.hint"/>
                            </tiles:put>
                            <tiles:put name="tdStyle" value="paymentLabel"/>
                            <tiles:put name="selectItems" value="esb@КСШ|autopay@AutoPay"/>
                        </tiles:insert>
                    </table>
                </tiles:put>

            <c:set var="fieldsFormatedForJSArray" value="${phiz:formatTableSettingToArray(form.fields,
    'loanRestrictionProperties_Id_|loanRestrictionProperties_loanSystemName_|loanRestrictionProperties_loanFromTime_|loanRestrictionProperties_loanToTime_|loanRestrictionProperties_timeZone_')}"/>
            <script type="text/javascript">
                var fieldsLRTProperty = {
                        loanRestrictionProperties_Id_:
                                {type: 'identifier', td: 1},
                        loanRestrictionProperties_loanSystemName_:
                                {type: 'input', td: 2,
                                 validators: [{template: templateObj.REQUIRED,
                                               message:'<bean:message bundle="commonBundle"
                                                         key="com.rssl.common.forms.validators.RequiredFieldValidator.message"
                                                         arg0="${loanSystemField}"/>'},
                                              {template: new templateItem ('TT-XXXXXXXX', '^\\d{2}[-][a-zA-Z,:]{1,}$'),
                                               message: '<bean:message bundle="configureBundle"
                                                         key="operation.restriction.loan.system.validation.format"/>'}]
                                },
                          loanRestrictionProperties_loanFromTime_:
                                {type: 'input', td: 3, style_class: 'short-time-template', style: "width:50px;",
                                    validators: [{template: templateObj.REQUIRED,
                                                  message: '<bean:message bundle="commonBundle"
                                                             key="com.rssl.common.forms.validators.RequiredFieldValidator.message"
                                                             arg0="${receptionStartTime}"/>'},
                                                 {template: templateObj.SHORT_TIME,
                                                  message: '<bean:message bundle="commonBundle"
                                                             key="com.rssl.common.forms.validators.TimeFieldValidator.message"
                                                             arg0="${receptionStartTime}"/>'}]
                                },
                            loanRestrictionProperties_loanToTime_:
                                {type: 'input', td: 3, before_text: '-', style_class: 'short-time-template', style: "width:50px;",
                                    validators: [{template: templateObj.REQUIRED,
                                                  message: '<bean:message bundle="commonBundle"
                                                             key="com.rssl.common.forms.validators.RequiredFieldValidator.message"
                                                             arg0="${receptionEndTime}"/>'},
                                                 {template: templateObj.SHORT_TIME,
                                                  message: '<bean:message bundle="commonBundle"
                                                             key="com.rssl.common.forms.validators.TimeFieldValidator.message"
                                                             arg0="${receptionEndTime}"/>'}]
                                },

                            loanRestrictionProperties_timeZone_:
                                {type: 'select', td: 3, before_text: '      ', style: "width:170px;font-size:11px;",
                                    options: {'0':'Калининград (MSK -01:00)',  '1':'Москва (MSK)',         '2':'Самара (MSK +01:00)',
                                              '3':'Екатеринбург (MSK +02:00)', '4':'Барнаул (MSK +03:00)', '5':'Красноярск (MSK +04:00)',
                                              '6':'Иркутск (MSK +05:00)',      '7':'Якутск (MSK +06:00)',  '8':'Владивосток (MSK +07:00)',
                                              '9':'Магадан (MSK +08:00)',      '10':'Камчатка (MSK +09:00)'}
                                }
                    };

                var initFieldsLRTProperty = ${fieldsFormatedForJSArray};
                var uniqFields = ["loanRestrictionProperties_loanSystemName_"];
                var loanTablePropertiesHelper = new TablePropertiesHelper('loanRestrictionProperties',
                        fieldsLRTProperty, initFieldsLRTProperty, '${imagePath}', uniqFields, false, true);

            </script>

            </tiles:insert>
        </tiles:put>

        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false"
                          operation="EditPaymentRestrictionsOperation">
                <tiles:put name="commandKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false"
                          operation="EditPaymentRestrictionsOperation">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="window.location.reload();"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

    <script>
        doOnLoad(function() {tablePropertiesFactory.registerHelper(loanTablePropertiesHelper);});
    </script>
</html:form>