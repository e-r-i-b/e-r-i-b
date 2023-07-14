<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/systemSettings/configure"  onsubmit="return setEmptyAction(event);">
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="impliesAdminSystemSettings" value="${phiz:impliesService('AdminSystemSettingsManagement')}"/>

<tiles:insert definition="propertiesForm">

    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

    <tiles:put name="tilesDefinition" type="string" value="tablePropertiesConfigEdit"/>
    <tiles:put name="submenu" type="string" value="SysSettings"/>
	<tiles:put name="pageTitle" type="string">Параметры системы. Настройки</tiles:put>
	<tiles:put name="pageDescription" type="string">Используйте форму для изменения настроек системы.</tiles:put>
	<tiles:put name="additionalStyle" type="string">width750</tiles:put>
	<tiles:put name="replicateAccessService" type="string" value="AdminSystemSettingsManagement"/>

    <tiles:put name="data" type="string">
        <table>
            <tiles:insert definition="propertyField" flush="false">
                <tiles:put name="fieldName" value="com.rssl.iccs.departments.allowed.level"/>
                <tiles:put name="fieldDescription">Уровень иерархии подразделений</tiles:put>
                <tiles:put name="textSize" value="5"/>
                <tiles:put name="textMaxLength" value="10"/>
                <tiles:put name="disabled" value="${not impliesAdminSystemSettings}"/>
                <tiles:put name="fieldHint">Уровень иерархии подразделений</tiles:put>
                <tiles:put name="imagePath" value="${imagePath}"/>
            </tiles:insert>

            <tiles:insert definition="propertyField" flush="false">
                <tiles:put name="fieldName" value="com.rssl.iccs.departments.admins.limit"/>
                <tiles:put name="fieldDescription">Количество администраторов на подразделение</tiles:put>
                <tiles:put name="textSize" value="5"/>
                <tiles:put name="textMaxLength" value="2"/>
                <tiles:put name="disabled" value="${not impliesAdminSystemSettings}"/>
                <tiles:put name="fieldHint">Количество администраторов, которых можно подключить к подразделению</tiles:put>
                <tiles:put name="imagePath" value="${imagePath}"/>
            </tiles:insert>

            <tiles:insert definition="propertyField" flush="false">
                <tiles:put name="fieldName" value="com.rssl.iccs.claim.working.life"/>
                <tiles:put name="fieldDescription">Срок хранения заявок</tiles:put>
                <tiles:put name="textSize" value="5"/>
                <tiles:put name="textMaxLength" value="2"/>
                <tiles:put name="disabled" value="${not impliesAdminSystemSettings}"/>
                <tiles:put name="fieldHint">По истечении указанного количества дней после отправки заявки в банк она будет автоматически удалена из системы</tiles:put>
                <tiles:put name="imagePath" value="${imagePath}"/>
            </tiles:insert>

            <tiles:insert definition="propertyField" flush="false">
                <tiles:put name="fieldName" value="com.rssl.iccs.gate.stub.connection.timeout"/>
                <tiles:put name="fieldDescription">Время ожидания ответа от шлюзов (в миллисекундах)</tiles:put>
                <tiles:put name="textSize" value="10"/>
                <tiles:put name="textMaxLength" value="10"/>
                <tiles:put name="disabled" value="${not impliesAdminSystemSettings}"/>
                <tiles:put name="fieldHint">Время ожидания ответа от шлюзов (в миллисекундах)</tiles:put>
                <tiles:put name="imagePath" value="${imagePath}"/>
            </tiles:insert>

            <tiles:insert definition="propertyField" flush="false">
                <tiles:put name="fieldName" value="send.delayed.payment.job.select.order"/>
                <tiles:put name="fieldDescription">Тип сортировки выборки платежей для обработки шедулером</tiles:put>
                <tiles:put name="fieldType" value="select"/>
                <tiles:put name="disabled" value="${not impliesAdminSystemSettings}"/>
                <tiles:put name="selectItems" value="ASC@Прямая|DESC@Обратная|RANDOM@Случайная"/>
                <tiles:put name="fieldHint">Укажите тип сортировки выборки платежей для обработки шедулером.</tiles:put>
                <tiles:put name="imagePath" value="${imagePath}"/>
            </tiles:insert>

            <tiles:insert definition="propertyField" flush="false">
                <tiles:put name="fieldName" value="com.rssl.iccs.phizic.payments.job.repeatSendRequestDocumentTimeout"/>
                <tiles:put name="fieldDescription">Регулярность опроса статусов операций по ОМС (чч:мм:сс)</tiles:put>
                <tiles:put name="textSize" value="10"/>
                <tiles:put name="textMaxLength" value="8"/>
                <tiles:put name="disabled" value="${not impliesAdminSystemSettings}"/>
                <tiles:put name="format" value="HH:mm:ss"/>
                <tiles:put name="fieldHint">По истечении указанного промежутка времени будет отправляться запрос для уточнения статуса</tiles:put>
                <tiles:put name="imagePath" value="${imagePath}"/>
            </tiles:insert>

            <tiles:insert definition="propertyField" flush="false">
                <tiles:put name="fieldName" value="com.rssl.iccs.phizic.catalog.aggreagation.interval"/>
                <tiles:put name="fieldDescription">Регулярность запуска процедуры агрегации каталога услуг (чч:мм:сс)</tiles:put>
                <tiles:put name="textSize" value="10"/>
                <tiles:put name="textMaxLength" value="8"/>
                <tiles:put name="disabled" value="${not impliesAdminSystemSettings}"/>
                <tiles:put name="format" value="HH:mm:ss"/>
                <tiles:put name="fieldHint">С указанной периодичностью будет запускать джоб агрегации каталога услуг. Если изменений в каталоге с предыдущего запуска не было, то агрегация не выполняется.</tiles:put>
                <tiles:put name="imagePath" value="${imagePath}"/>
            </tiles:insert>

            <tiles:insert definition="propertyField" flush="false">
                <tiles:put name="fieldName" value="com.rssl.business.default.extended.logging.time"/>
                <tiles:put name="fieldDescription">Время действия расширенного логирования (в часах)</tiles:put>
                <tiles:put name="textSize" value="10"/>
                <tiles:put name="textMaxLength" value="2"/>
                <tiles:put name="disabled" value="${not impliesAdminSystemSettings}"/>
                <tiles:put name="fieldHint">Укажите время, в течение которого будет выполняться расширенное логирование для выборанного клиента.</tiles:put>
                <tiles:put name="imagePath" value="${imagePath}"/>
            </tiles:insert>
        </table>

        <table>
        <tr>
            <td colspan="3">
                <fieldset>
                    <table cellpadding="0" cellspacing="0" width="100%">
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="max.jobs.count"/>
                            <tiles:put name="fieldDescription">Максимальное количество экземпляров одновременно работающих джобов</tiles:put>
                            <tiles:put name="textSize" value="10"/>
                            <tiles:put name="textMaxLength" value="5"/>
                            <tiles:put name="disabled" value="${not impliesAdminSystemSettings}"/>
                            <tiles:put name="fieldHint">Укажите максимальное количество одновременно работающих джобов.</tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="max.rows.for.job"/>
                            <tiles:put name="fieldDescription">Максимальное количество записей для обработки джобом</tiles:put>
                            <tiles:put name="textSize" value="10"/>
                            <tiles:put name="textMaxLength" value="5"/>
                            <tiles:put name="disabled" value="${not impliesAdminSystemSettings}"/>
                            <tiles:put name="fieldHint">Задайте максимальное количество записей, одновременно обрабатываемых джобом.</tiles:put>
                        </tiles:insert>
                    </table>
                </fieldset>
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <fieldset>
                     <legend>Автоматическая обработка биллинговых операций</legend>
                     <table cellpadding="0" cellspacing="0" width="100%">
                         <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="max.jobs.count.ResendESBPaymentJob"/>
                            <tiles:put name="fieldDescription" value="Максимальное количество экземпляров одновременно работающих джобов"/>
                            <tiles:put name="fieldHint" value="Укажите максимальное количество экземпляров одновременно работающих джобов."/>
                            <tiles:put name="showHint" value="right"/>
                            <tiles:put name="textSize" value="10"/>
                            <tiles:put name="textMaxLength" value="5"/>
                            <tiles:put name="disabled" value="${not impliesAdminSystemSettings}"/>
                         </tiles:insert>
                         <tiles:insert definition="propertyField" flush="false">
                             <tiles:put name="fieldName" value="max.rows.for.job.ResendESBPaymentJob"/>
                             <tiles:put name="fieldDescription" value="Количество документов для обработки"/>
                             <tiles:put name="fieldHint" value="Укажите количество документов, которые должны одновременно обрабатываться джобом."/>
                             <tiles:put name="showHint" value="right"/>
                             <tiles:put name="textSize" value="10"/>
                             <tiles:put name="textMaxLength" value="5"/>
                             <tiles:put name="disabled" value="${not impliesAdminSystemSettings}"/>
                         </tiles:insert>
                         <tiles:insert definition="propertyField" flush="false">
                              <tiles:put name="fieldName" value="document.confirmdate.expire.hour.ResendESBPaymentJob"/>
                              <tiles:put name="fieldDescription" value="Период выборки документов для обработки"/>
                              <tiles:put name="fieldHint" value="Укажите период (в часах), за который были получены документы, требующие последующей обработки. Данная настройка исключает обработку устаревших документов."/>
                              <tiles:put name="showHint" value="right"/>
                              <tiles:put name="textSize" value="10"/>
                              <tiles:put name="textMaxLength" value="5"/>
                              <tiles:put name="disabled" value="${not impliesAdminSystemSettings}"/>
                         </tiles:insert>
                         <tiles:insert definition="propertyField" flush="false">
                               <tiles:put name="fieldName" value="resend.payment.job.max.payment.try"/>
                               <tiles:put name="fieldDescription" value="Количество попыток повторной обработки платежа"/>
                               <tiles:put name="fieldHint" value="Укажите количество попыток для повторной обработки платежа джобом."/>
                               <tiles:put name="showHint" value="right"/>
                               <tiles:put name="textSize" value="10"/>
                               <tiles:put name="textMaxLength" value="5"/>
                               <tiles:put name="disabled" value="${not impliesAdminSystemSettings}"/>
                         </tiles:insert>
                         <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="send.delayed.payment.job.select.order.ResendESBPaymentJob"/>
                            <tiles:put name="fieldDescription" value="Способ выборки документов для обработки"/>
                            <tiles:put name="fieldHint" value="Укажите из списка способ выборки документов для их повторной обработки джобом."/>
                            <tiles:put name="showHint" value="right"/>
                            <tiles:put name="fieldType" value="select"/>
                            <tiles:put name="selectItems" value="ASC@Прямая|DESC@Обратная|RANDOM@Случайная"/>
                            <tiles:put name="disabled" value="${not impliesAdminSystemSettings}"/>
                         </tiles:insert>
                        </table>
                </fieldset>
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <fieldset>
                    <legend>Автоматическое уточнение статусов документов, зависших в АС «ЦОД»</legend>
                    <table cellpadding="0" cellspacing="0" width="100%">
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="restriction.number.requests.state.operation.DepoCodPaymentsExecutionJob"/>
                            <tiles:put name="fieldDescription" value="Максимальное количество запросов статуса операции"/>
                            <tiles:put name="fieldHint" value="Укажите максимальное количество запросов статуса операции по каждому документу. При превышении попыток документ будет переведен в статус «Ошибка»."/>
                            <tiles:put name="showHint" value="right"/>
                            <tiles:put name="textSize" value="10"/>
                            <tiles:put name="textMaxLength" value="2"/>
                            <tiles:put name="disabled" value="${not impliesAdminSystemSettings}"/>
                        </tiles:insert>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="document.confirmdate.expire.hour.DepoCodPaymentsExecutionJob"/>
                            <tiles:put name="fieldDescription" value="Время, по истечении которого отправленный документ станет устаревшим"/>
                            <tiles:put name="fieldHint" value="Укажите время в часах, по истечении которого отправленный документ станет устаревшим и будет переведен в статус «Ошибка»."/>
                            <tiles:put name="showHint" value="right"/>
                            <tiles:put name="textSize" value="10"/>
                            <tiles:put name="textMaxLength" value="3"/>
                            <tiles:put name="disabled" value="${not impliesAdminSystemSettings}"/>
                        </tiles:insert>
                    </table>
                </fieldset>
            </td>
        </tr>
        <tiles:insert definition="propertyField" flush="false">
           <tiles:put name="fieldName" value="com.rssl.iccs.deny.custom.rights"/>
           <tiles:put name="fieldDescription" value="Запрет установки сотрудникам индивидуальных прав"/>
           <tiles:put name="fieldHint" value="Укажите возможность запрета администратору устанавливать сотрудникам индивидуальные права доступа."/>
           <tiles:put name="showHint" value="right"/>
           <tiles:put name="fieldType" value="select"/>
           <tiles:put name="selectItems" value="false@Разрешить|true@Запретить"/>
           <tiles:put name="disabled" value="${not impliesAdminSystemSettings}"/>
        </tiles:insert>
        <tr>
            <td class="Width200 LabelAll"><bean:message bundle="configureBundle" key="mobile.provider.properties.title"/>:</td>
            <td colspan="2">&nbsp;
                <c:set var="providerCodeField"><bean:message bundle="configureBundle" key="mobile.provider.properties.provider.title"/></c:set>
                <c:set var="serviceCodeField"><bean:message bundle="configureBundle" key="mobile.provider.properties.service.title"/></c:set>
                <tiles:insert definition="tableProperties" flush="false">
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="fieldValue" value="com.rssl.business.mobileProvidersProperties_"/>
                    <tiles:put name="showCheckbox" value="${form.replication}"/>
                    <tiles:put name="propertyId" value="mobileProviderProperties"/>
                    <tiles:put name="tableTitle">
                        <div class="propertiesTable float"><bean:message bundle="configureBundle" key="mobile.provider.properties.table.title"/></div>
                        <tiles:insert definition="floatMessageShadow" flush="false">
                            <tiles:put name="id" value="mobileProvidersProp"/>
                            <tiles:put name="hintClass" value="rightAlignHint indent-top"/>
                            <tiles:put name="data"><img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px"></tiles:put>
                            <tiles:put name="showHintImg" value="false"/>
                            <tiles:put name="text">
                                <bean:message bundle="configureBundle" key="mobile.provider.properties.help"/>
                            </tiles:put>
                            <tiles:put name="dataClass" value="dataHint"/>
                            <tiles:put name="floatClassSyffix" value="Right"/>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="tableHead">
                        <th class="titleTable Width20">
                            <input type="checkbox" onclick="switchSelection(this,'mobileProviderProperties_Ids');" ${form.replication ? "disabled='disabled'" : ""}>
                        </th>
                        <th class="titleTable Width200" >${providerCodeField}</th>
                        <th class="titleTable" >${serviceCodeField}</th>
                    </tiles:put>
                </tiles:insert>
            </td>
        </tr>

        <tr>
            <td class="Width200 LabelAll"><bean:message bundle="configureBundle" key="old.doc.adapter.properties.title"/>:</td>
            <td colspan="2">&nbsp;
                <c:set var="adapterCodeField"><bean:message bundle="configureBundle" key="old.doc.adapter.properties.adapter.title"/></c:set>
                <tiles:insert definition="tableProperties" flush="false">
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="fieldValue" value="com.rssl.business.oldDocAdaptersProperties_"/>
                    <tiles:put name="showCheckbox" value="${form.replication}"/>
                    <tiles:put name="propertyId" value="oldDocAdapterProperties"/>
                    <tiles:put name="tableTitle">
                        <span class="oldDocAdapterProperty"><bean:message bundle="configureBundle" key="old.doc.adapter.properties.table.title"/></span>
                        <tiles:insert definition="floatMessageShadow" flush="false">
                            <tiles:put name="id" value="oldDocAdapterProp"/>
                            <tiles:put name="hintClass" value="rightAlignHint indent-top"/>
                            <tiles:put name="data"><img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px"></tiles:put>
                            <tiles:put name="showHintImg" value="false"/>
                            <tiles:put name="text">
                                <bean:message bundle="configureBundle" key="old.doc.adapter.properties.help"/>
                            </tiles:put>
                            <tiles:put name="dataClass" value="dataHint"/>
                            <tiles:put name="float" value=""/>
                            <tiles:put name="floatClassSyffix" value="OldDocAdapter"/>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="tableHead">
                        <th class="titleTable Width20">
                            <input type="checkbox" onclick="switchSelection(this,'oldDocAdapterProperties_Ids');" ${form.replication ? "disabled='disabled'" : ""}>
                        </th>
                        <th class="titleTable Width200" >${adapterCodeField}</th>
                    </tiles:put>
                </tiles:insert>
            </td>
        </tr>
        </table>
        <c:set var="MPFieldsFormatedForJSArray" value="${phiz:formatTableSettingToArray(form.fields,
                    'mobileProviderProperties_Id_|mobileProviderProperties_providerCode_|mobileProviderProperties_serviceCode_')}"/>
        <c:set var="ODAFieldsFormatedForJSArray" value="${phiz:formatTableSettingToArray(form.fields,
                    'oldDocAdapterProperties_Id_|oldDocAdapterProperties_adapterCode_')}"/>
        <script type="text/javascript">
             var fieldsMPProperties = {
                    mobileProviderProperties_Id_:
                            {type: 'identifier', td: 1},
                    mobileProviderProperties_providerCode_:
                            {type: 'input', td: 2,
                             validators: [{template: templateObj.REQUIRED,
                                           message: '<bean:message bundle="commonBundle"
                                                      key="com.rssl.common.forms.validators.RequiredFieldValidator.message"
                                                      arg0="${providerCodeField}"/>'},
                                          {template: new templateItem ('____', '^[a-zA-Z\\d]{1,20}$'),
                                           message: '<bean:message bundle="commonBundle"
                                                      key="com.rssl.common.forms.validators.CharDigitLengthFieldValidator.message"
                                                      arg0="${providerCodeField}" arg1="20"/>'}]
                            },
                    mobileProviderProperties_serviceCode_:
                            {type: 'input', td: 3,
                             validators: [{template: templateObj.REQUIRED,
                                           message: '<bean:message bundle="commonBundle"
                                                      key="com.rssl.common.forms.validators.RequiredFieldValidator.message"
                                                      arg0="${serviceCodeField}"/>'},
                                          {template: new templateItem ('____', '^[a-zA-Z\\d]{1,35}$'),
                                           message: '<bean:message bundle="commonBundle"
                                                      key="com.rssl.common.forms.validators.CharDigitLengthFieldValidator.message"
                                                      arg0="${serviceCodeField}" arg1="35"/>'}]
                            }
                };
            var uniqFields = ["mobileProviderProperties_providerCode_", "mobileProviderProperties_serviceCode_"];

            var initFieldsMPProperties = ${MPFieldsFormatedForJSArray};

            var mobileProviderPropertiesHelper = new TablePropertiesHelper('mobileProviderProperties',
                        fieldsMPProperties, initFieldsMPProperties, '${imagePath}', uniqFields, ${not impliesAdminSystemSettings});
            tablePropertiesFactory.registerHelper(mobileProviderPropertiesHelper);



             var fieldsODAProperties = {
                    oldDocAdapterProperties_Id_:
                            {type: 'identifier', td: 1},
                    oldDocAdapterProperties_adapterCode_:
                            {type: 'input', td: 2,
                             validators: [{template: templateObj.REQUIRED,
                                           message: '<bean:message bundle="commonBundle"
                                                      key="com.rssl.common.forms.validators.RequiredFieldValidator.message"
                                                      arg0="${adapterCodeField}"/>'},
                                          {template: new templateItem ('TT-XXXXXXXX', '^\\d{2}[-][a-zA-Z,:]{1,}$'),
                                           message: '<bean:message bundle="configureBundle"
                                                      key="old.doc.adapter.properties.code.validation.message"/>'}]
                            }
                };

            var initFieldsODAProperties = ${ODAFieldsFormatedForJSArray};
            var uniqFieldsODAProperties = ["oldDocAdapterProperties_adapterCode_"];
            var oldDocAdapterPropertiesHelper = new TablePropertiesHelper('oldDocAdapterProperties',
                    fieldsODAProperties, initFieldsODAProperties, '${imagePath}',uniqFieldsODAProperties, ${not impliesAdminSystemSettings});
            tablePropertiesFactory.registerHelper(oldDocAdapterPropertiesHelper);

             jQuery(function() {$('input[name=field(com.rssl.iccs.phizic.payments.job.repeatSendRequestDocumentTimeout)]').createMask('hh:mm:ss');});
             jQuery(function() {$('input[name=field(com.rssl.iccs.phizic.catalog.aggreagation.interval)]').createMask('hh:mm:ss');});
        </script>
    </tiles:put>
    <tiles:put name="formButtons">
        <tiles:insert definition="commandButton" flush="false" service="AdminSystemSettingsManagement">
            <tiles:put name="commandKey"     value="button.save"/>
            <tiles:put name="commandHelpKey" value="button.save.help"/>
            <tiles:put name="isDefault"            value="true"/>
            <tiles:put name="bundle"  value="commonBundle"/>
            <tiles:put name="postbackNavigation" value="true"/>
        </tiles:insert>
        <tiles:insert definition="clientButton" flush="false" service="AdminSystemSettingsManagement">
            <tiles:put name="commandTextKey"     value="button.cancel"/>
            <tiles:put name="commandHelpKey" value="button.cancel.help"/>
            <tiles:put name="bundle"  value="commonBundle"/>
            <tiles:put name="onclick" value="javascript:resetForm(event)"/>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="formAlign" value="center"/>
</tiles:insert>

</html:form>