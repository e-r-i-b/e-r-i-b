<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"  prefix="bean" %>
<!-- file CODSettings.jsp -->
<!-- generated code -->

<c:set var="helpString" value="/help.do?id=/dictionaries/routing/adapter/settings/edit${EditAdapterSettingsForm.nodeType}"/>
<c:set var="helpLink" value="${phiz:calculateActionURL(pageContext, helpString)}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<table>
<script type="text/javascript">
    function setDepartmentInfo(resource)
    {
        setElement("field(com.rssl.gate.office.code.region.number)", resource['region']);
    }
</script>

<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="com.rssl.phisicgate.sbrf.ws.cod.url"/>
    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="COD.com.rssl.phisicgate.sbrf.ws.cod.url"/></tiles:put>
    <tiles:put name="textSize" value="60"/>
    <tiles:put name="fieldHint">Укажите адрес веб-сервиса для адаптера АС "ЦОД".</tiles:put>
    <tiles:put name="imagePath" value="${imagePath}"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>

<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="com.rssl.phisicgate.sbrf.ws.sbol.url"/>
    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="COD.com.rssl.phisicgate.sbrf.ws.sbol.url"/></tiles:put>
    <tiles:put name="textSize" value="60"/>
    <tiles:put name="fieldHint">Укажите адрес веб-сервиса Сбербанка для передачи необработанных сообшений.</tiles:put>
    <tiles:put name="imagePath" value="${imagePath}"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>
<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="com.rssl.phizic.wsgate.listener.url"/>
    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="COD.com.rssl.phizic.wsgate.listener.url"/></tiles:put>
    <tiles:put name="textSize" value="60"/>
    <tiles:put name="fieldHint">Укажите адрес обратного веб-сервиса на стороне ИКФЛ.</tiles:put>
    <tiles:put name="imagePath" value="${imagePath}"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>
    <tr>
        <td class="vertical-align rightAlign">
            <bean:message bundle="adapterBundle" key="COD.com.rssl.gate.office.code.region.number"/>
        </td>
        <td>
            <tiles:insert definition="propertyInput" flush="false">
                <tiles:put name="fieldName" value="com.rssl.gate.office.code.region.number"/>
                <tiles:put name="textSize" value="4"/>
                <tiles:put name="textMaxLength" value="2"/>
                <tiles:put name="readonly" value="true"/>
            </tiles:insert>
            <c:if test="${!form.replication}">
                <input class="buttWhite smButt" id="officeSelectButton" type="button" value="..." onclick="openAllowedTBDictionary(setDepartmentInfo, true)"/>
            </c:if>
            <div class="clear"></div>
            <span class="propertyDescText">Укажите код тербанка, автоматизируемого через данный узел.</span>
        </td>
    </tr>

    <tr>
        <td class="vertical-align rightAlign">
            <bean:message bundle="adapterBundle" key="COD.ourTBCodes"/>
        </td>
        <td>
            <c:set var="tbCodeField"><bean:message bundle="configureBundle" key="ourTBCodes.properties.tb.code"/></c:set>
            <tiles:insert definition="tableProperties" flush="false">
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="fieldValue" value="com.rssl.phizic.gate.OurTBcodes"/>
                <tiles:put name="showCheckbox" value="${form.replication}"/>
                <tiles:put name="propertyId" value="ourTBCodesProperties"/>
                <tiles:put name="tableTitle">
                    <div class="propertiesTable float"><bean:message bundle="configureBundle" key="ourTBCodes.properties.table.title"/></div>
                    <tiles:insert definition="floatMessageShadow" flush="false">
                        <tiles:put name="id" value="ourTBCodesProp"/>
                        <tiles:put name="hintClass" value="rightAlignHint indent-top"/>
                        <tiles:put name="data"><img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px"></tiles:put>
                        <tiles:put name="showHintImg" value="false"/>
                        <tiles:put name="text">
                            <bean:message bundle="configureBundle" key="ourTBCodes.properties.help"/>
                        </tiles:put>
                        <tiles:put name="dataClass" value="dataHint"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="tableHead">
                    <th class="titleTable Width20">
                        <input type="checkbox" onclick="switchSelection(this,'ourTBCodesProperties_Ids');" ${form.replication ? "disabled='disabled'" : ""}>
                    </th>
                    <th class="titleTable Width200" >${tbCodeField}</th>
                </tiles:put>
            </tiles:insert>
        </td>
    </tr>

<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="com.rssl.gate.connection.timeout"/>
    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="COD.com.rssl.gate.connection.timeout"/></tiles:put>
    <tiles:put name="textSize" value="10"/>
    <tiles:put name="fieldHint">Укажите тайм-аут соединения с АС "ЦОД" в миллисекундах.</tiles:put>
    <tiles:put name="imagePath" value="${imagePath}"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>
<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="wrong_office_region"/>
    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="COD.wrong_office_region"/></tiles:put>
    <tiles:put name="textSize" value="2"/>
    <tiles:put name="fieldHint">Укажите тербанк, для которого нужно проверять наличие ОСБ в справочнике.</tiles:put>
    <tiles:put name="imagePath" value="${imagePath}"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>
    <tr>
        <td class="vertical-align rightAlign">
            <bean:message bundle="adapterBundle" key="COD.wrong_office_branches"/>
        </td>
        <td>
            <c:set var="osbCodeField"><bean:message bundle="configureBundle" key="wrong.office.branches.properties.osb"/></c:set>
            <tiles:insert definition="tableProperties" flush="false">
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="fieldValue" value="wrong_office_branches"/>
                <tiles:put name="showCheckbox" value="${form.replication}"/>
                <tiles:put name="propertyId" value="wrongOfficeBranchesProperties"/>
                <tiles:put name="tableTitle">
                    <div class="propertiesTable float"><bean:message bundle="configureBundle" key="wrong.office.branches.properties.table.title"/></div>
                    <tiles:insert definition="floatMessageShadow" flush="false">
                        <tiles:put name="id" value="wrongOfficeBranchesProp"/>
                        <tiles:put name="hintClass" value="rightAlignHint indent-top"/>
                        <tiles:put name="data"><img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px"></tiles:put>
                        <tiles:put name="showHintImg" value="false"/>
                        <tiles:put name="text">
                            <bean:message bundle="configureBundle" key="wrong.office.branches.properties.help"/>
                        </tiles:put>
                        <tiles:put name="dataClass" value="dataHint"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="tableHead">
                    <th class="titleTable Width20">
                        <input type="checkbox" onclick="switchSelection(this,'wrongOfficeBranchesProperties_Ids');" ${form.replication ? "disabled='disabled'" : ""}>
                    </th>
                    <th class="titleTable Width200" >${osbCodeField}</th>
                </tiles:put>
            </tiles:insert>
        </td>
    </tr>

<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="right_office_branch"/>
    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="COD.right_office_branch"/></tiles:put>
    <tiles:put name="textSize" value="4"/>
    <tiles:put name="fieldHint">Укажите код ОСБ, которым заменяется приходящий код ОСБ, если он находится в перечне wrong_office_branches</tiles:put>
    <tiles:put name="imagePath" value="${imagePath}"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>

<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="com.rssl.gate.use.depo.cod"/>
    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="COD.com.rssl.gate.use.depo.cod"/></tiles:put>
    <tiles:put name="fieldHint">Для того чтобы запросы в АС «ЦОД» отправлялись через новый веб-сервис, установите флажок в этом поле.</tiles:put>
    <tiles:put name="fieldType" value="checkbox"/>
    <tiles:put name="imagePath" value="${imagePath}"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>
    <tr>
        <td colspan="2">
            <fieldset class="width975">
                <legend>Способ формирования платежного поручения</legend>
                <table cellpadding="0" cellspacing="0" width="100%">
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.gate.payments.AccountJurTransfer.usePaymentOrder"/>
                        <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="COD.com.rssl.phizic.gate.payments.AccountJurTransfer.usePaymentOrder"/></tiles:put>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems" value="false@Стандартный режим|true@Полноформатный режим"/>
                        <tiles:put name="fieldHint">Укажите способ формирования платежного поручения.</tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                        <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    </tiles:insert>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.gate.payments.AccountJurIntraBankTransfer.usePaymentOrder"/>
                        <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="COD.com.rssl.phizic.gate.payments.AccountJurIntraBankTransfer.usePaymentOrder"/></tiles:put>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems" value="false@Стандартный режим|true@Полноформатный режим"/>
                        <tiles:put name="fieldHint">Укажите способ формирования платежного поручения.</tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                        <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    </tiles:insert>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.gate.payments.AccountRUSTaxPayment.usePaymentOrder"/>
                        <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="COD.com.rssl.phizic.gate.payments.AccountRUSTaxPayment.usePaymentOrder"/></tiles:put>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems" value="false@Стандартный режим|true@Полноформатный режим"/>
                        <tiles:put name="fieldHint">Укажите способ формирования платежного поручения.</tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                        <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    </tiles:insert>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment.usePaymentOrder"/>
                        <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="COD.com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment.usePaymentOrder"/></tiles:put>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems" value="false@Стандартный режим|true@Полноформатный режим"/>
                        <tiles:put name="fieldHint">Укажите способ формирования платежного поручения.</tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                        <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    </tiles:insert>
                </table>
            </fieldset>
        </td>
    </tr>

    <c:set var="WOBFieldsFormatedForJSArray" value="${phiz:formatTableSettingToArray(EditAdapterSettingsForm.fields,
            'wrongOfficeBranchesProperties_Id_|wrongOfficeBranchesProperties_osbCode_')}"/>
    <c:set var="OTCFieldsFormatedForJSArray" value="${phiz:formatTableSettingToArray(EditAdapterSettingsForm.fields,
            'ourTBCodesProperties_Id_|ourTBCodesProperties_tbCode_')}"/>

    <script type="text/javascript">
        var fieldsWOBProperties = {
            wrongOfficeBranchesProperties_Id_:
                    {type: 'identifier', td: 1},
            wrongOfficeBranchesProperties_osbCode_:
                    {type: 'input', td: 2,
                        validators: [{template: templateObj.REQUIRED,
                                      message: '<bean:message bundle="commonBundle"
                                                 key="com.rssl.common.forms.validators.RequiredFieldValidator.message"
                                                 arg0="${osbCodeField}"/>'},
                                     {template: new templateItem ('____', '^\\d{1,4}$'),
                                      message: '<bean:message bundle="commonBundle"
                                                 key="com.rssl.common.forms.validators.DigitRangeFieldValidator.message"
                                                 arg0="${osbCodeField}" arg1="1" arg2="4"/>'}]
                    }
        };

        var initFieldsWOBProperties = ${WOBFieldsFormatedForJSArray};
        var unicWOBProperties = ["wrongOfficeBranchesProperties_osbCode_"];
        var wrongOfficeBranchesHelper = new TablePropertiesHelper('wrongOfficeBranchesProperties',
                                            fieldsWOBProperties, initFieldsWOBProperties, '${imagePath}', unicWOBProperties);
        tablePropertiesFactory.registerHelper(wrongOfficeBranchesHelper);


        var fieldsOTCProperties = {
            ourTBCodesProperties_Id_:
                    {type: 'identifier', td: 1},
            ourTBCodesProperties_tbCode_:
                    {type: 'input', td: 2,
                        validators: [{template: templateObj.REQUIRED,
                                      message: '<bean:message bundle="commonBundle"
                                                 key="com.rssl.common.forms.validators.RequiredFieldValidator.message"
                                                 arg0="${tbCodeField}"/>'},
                                     {template: new templateItem ('____', '^[-\\d]{1}\\d{1}$'),
                                      message: '<bean:message bundle="commonBundle"
                                                 key="com.rssl.common.forms.validators.DigitLengthFieldValidator.message"
                                                 arg0="${tbCodeField}" arg1="2"/>'}]
                    }
        };

        var initFieldsOTCProperties = ${OTCFieldsFormatedForJSArray};
        var uniqOTCProperties =["ourTBCodesProperties_tbCode_"];
        var ourTBCodesPropertiesHelper = new TablePropertiesHelper('ourTBCodesProperties',
                                            fieldsOTCProperties, initFieldsOTCProperties, '${imagePath}', uniqOTCProperties);
        tablePropertiesFactory.registerHelper(ourTBCodesPropertiesHelper);
    </script>
<!-- end generated code -->

</table>