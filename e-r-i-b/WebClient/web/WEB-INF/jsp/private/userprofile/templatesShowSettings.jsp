<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"     prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"     prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"    prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"    prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags"                           prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"              prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"         prefix="fn" %>
<%@ taglib uri="http://struts.application-servers.com/layout"   prefix="sl"%>

<tiles:importAttribute/>

<html:form action="/private/userprofile/templatesShowSettings" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="userProfile">
        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Настройки"/>
                <tiles:put name="action" value="/private/userprofile/userSettings.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Настройка безопасности"/>
                <tiles:put name="action" value="/private/userprofile/accountSecurity.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Настройка видимости шаблонов"/>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <div id="profile"  onkeypress="onEnterKey(event);">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="title" value="Настройки"/>
                    <tiles:put name="data">
                        <tiles:insert definition="userSettings" flush="false">
                            <tiles:put name="data">
                                <c:set var="selectedTab" value="securetySettings"/>
                                <%@ include file="/WEB-INF/jsp/private/userprofile/userSettingsHeader.jsp" %>
                                <html:hidden property="field(channelType)" styleId="channelType" name="form"/>
                                <html:hidden property="field(changedIds)" styleId="changedIds" name="form"/>

                                <script type="text/javascript">
                                    var changedIds = new Array();
                                    var channels = ['atm', 'internet', 'sms', 'mobile'];

                                    $(document).ready(function()
                                    {
                                        function displayChannel(name)
                                        {
                                            $('#channelType').val(name);

                                            for (var i = 0; i < channels.length; i++)
                                            {
                                                if (channels[i] == name)
                                                {
                                                    $('#' + name).removeClass('transparent');
                                                    $('div[id^="use' + name + '"]').show();
                                                }
                                                else
                                                {
                                                    $('#' + channels[i]).addClass('transparent');
                                                    $('div[id^="use' + channels[i] + '"]').hide();
                                                }
                                            }

                                            checkedChannel(name);
                                        }

                                        $('#atm').click(function()
                                        {
                                            $('#channelType').val('atm');
                                            findCommandButton('button.filter').click();
                                        });

                                        $('#internet').click(function()
                                        {
                                            $('#channelType').val('internet');
                                            findCommandButton('button.filter').click();
                                        });

                                        $('#sms').click(function()
                                        {
                                            $('#channelType').val('sms');
                                            findCommandButton('button.filter').click();
                                        });

                                        $('#mobile').click(function()
                                        {
                                            $('#channelType').val('mobile');
                                            findCommandButton('button.filter').click();
                                        });

                                        $('input[type=checkbox]:first').click(function()
                                        {
                                            var channelType = document.getElementById('channelType');
                                            var startId = channelType.value + 'CheckBox';

                                            $('div[id^=use' + channelType.value + ']:not(div[id^=use' + channelType.value + 'Draft])').text(getText(this.checked));
                                            $('input[id^=' + startId + ']:not(input[id^=' + startId + 'Draft])').val(this.checked);

                                            var templatesList = $('div[id^=num]:not(div[id^=numDraft])');
                                            for (var i = 0; i < templatesList.size(); i++)
                                            {
                                                var id = templatesList[i].id;
                                                var templateId = id.substr('num'.length, id.length);
                                                if (this.checked){
                                                    $('#num'+templateId).removeClass('draftTemplate');
                                                    $('#create'+templateId).removeClass('draftTemplate');
                                                    $('#name'+templateId).removeClass('draftTemplate');
                                                    $('#status'+templateId).removeClass('draftTemplate');
                                                    $('#amount'+templateId).removeClass('draftTemplate');
                                                    $('#use' + channelType.value + templateId).removeClass('draftTemplate');
                                                }
                                                else
                                                {
                                                    $('#num'+templateId).addClass('draftTemplate');
                                                    $('#create'+templateId).addClass('draftTemplate');
                                                    $('#name'+templateId).addClass('draftTemplate');
                                                    $('#status'+templateId).addClass('draftTemplate');
                                                    $('#amount'+templateId).addClass('draftTemplate');
                                                    $('#use' + channelType.value + templateId).addClass('draftTemplate');
                                                }
                                            }

                                            switchSelection(this, 'selectedIds');
                                            updateCheckBox($('input[id^=' + startId + 'Draft]'), startId + 'Draft');
                                        });

                                        $('input[name=selectedIds]').click(function(){
                                            onTemplateClick(this);
                                        });

                                        function checkedChannel(channel)
                                        {
                                            var startId = channel + 'CheckBox';
                                            var channelArray = $('input[id^=' + startId + ']:not(input[id^=' + startId + 'Draft])');
                                            var draftArray = $('input[id^=' + startId + 'Draft]');

                                            updateCheckBox(channelArray, startId);
                                            updateCheckBox(draftArray, startId + 'Draft');
                                        }

                                        function updateCheckBox(channelArray, startId)
                                        {
                                            for (var i = 0; i <channelArray.size(); i++)
                                            {
                                                var id = channelArray[i].id;
                                                var templateId = id.substr(startId.length, id.length);
                                                var lineCheckBox = $("input[type=checkbox][value=" + templateId + "]");

                                                if (channelArray[i].value == 'true')
                                                    lineCheckBox.attr('checked', 'checked');
                                                else
                                                    lineCheckBox.removeAttr('checked');
                                            }
                                        }

                                        function init()
                                        {
                                            var channelType = document.getElementById('channelType');
                                            displayChannel(channelType.value);

                                            var startId = 'internetCheckBoxDraft';
                                            var draftArray = $('input[id^=' + startId + ']');
                                            for (var i = 0; i < draftArray.size(); i++)
                                            {
                                                var id = draftArray[i].id;
                                                var templateId = id.substr(startId.length, id.length);
                                                $('input[type=checkbox][value='+ templateId + ']').attr('disabled', 'disabled');
                                            }

                                            initializeChangedIds();
                                        }

                                        function initializeChangedIds()
                                        {
                                            var array = $('input[name=selectedIds]');
                                            for (var i = 0; i < array.length; i++)
                                            {
                                                changedIds[i] = new Array();
                                                changedIds[i][0] = array[i].value;
                                                changedIds[i][1] = array[i].checked;
                                            }
                                        }

                                        init();
                                    });

                                    function updateChangedIds()
                                    {
                                        var result = "";
                                        var array = $('input[name=selectedIds]');
                                        for (var i = 0; i < array.length; i++)
                                        {
                                            if (array[i].checked ^ changedIds[i][1])
                                            {
                                                if (result != "")
                                                    result += ",";
                                                result += changedIds[i][0];
                                            }
                                        }

                                        document.getElementById('changedIds').value = result;
                                        return true;
                                    }

                                    function getText(isChecked)
                                    {
                                        var channelType = document.getElementById('channelType');
                                        if (isChecked)
                                        {
                                            if (channelType.value == 'internet')
                                                return 'доступно для Сбербанк Онлайн';
                                            else if (channelType.value == 'mobile')
                                                return 'доступно для мобильных устройств';
                                            else if (channelType.value == 'atm')
                                                return 'доступно для устройств самообслуживания';
                                            else if (channelType.value == 'sms')
                                                return 'доступно для SMS-команд';
                                        }
                                        else
                                        {
                                            if (channelType.value == 'internet')
                                                return 'не доступно для Сбербанк Онлайн';
                                            else if (channelType.value == 'mobile')
                                                return 'не доступно для мобильных устройств';
                                            else if (channelType.value == 'atm')
                                                return 'не доступно для устройств самообслуживания';
                                            else if (channelType.value == 'sms')
                                                return 'не доступно для SMS-команд';
                                        }
                                    }

                                    function onTemplateClick(checkbox)
                                    {
                                        var channelType = document.getElementById('channelType');
                                        var templateId = checkbox.value;
                                        templateId = templateId.replace(/^\s+/, "").replace(/\s+$/, "");

                                        var hiddenCheckBox = $('#' + channelType.value + 'CheckBox' + templateId);

                                        if (hiddenCheckBox.val() == null)
                                            return;

                                        $('#use' + channelType.value + templateId).text(getText(checkbox.checked));
                                        hiddenCheckBox.val(checkbox.checked);

                                        if(checkbox.checked)
                                        {
                                            $('#num'+templateId).removeClass('draftTemplate');
                                            $('#create'+templateId).removeClass('draftTemplate');
                                            $('#name'+templateId).removeClass('draftTemplate');
                                            $('#status'+templateId).removeClass('draftTemplate');
                                            $('#amount'+templateId).removeClass('draftTemplate');
                                            $('#use' + channelType.value + templateId).removeClass('draftTemplate');
                                        }
                                        else
                                        {
                                            $('#num'+templateId).addClass('draftTemplate');
                                            $('#create'+templateId).addClass('draftTemplate');
                                            $('#name'+templateId).addClass('draftTemplate');
                                            $('#status'+templateId).addClass('draftTemplate');
                                            $('#amount'+templateId).addClass('draftTemplate');
                                            $('#use' + channelType.value + templateId).addClass('draftTemplate');
                                        }
                                    }

                                </script>

                                <div class="payments-tabs">
                                    <div class="clear"></div>
                                    <div><h2>Настройка видимости шаблонов</h2></div>
                                    <div class="clear"></div>
                                    <div class="greenContainer picInfoBlockSysView">
                                        <div>
                                            <bean:message bundle="userprofileBundle" key="text.templates.change.settings.help"/>
                                        </div>
                                    </div>
                                    <div class="clear"></div>
                                    <div class="filter triggerFilter" onkeypress="onEnterKey(event);">
                                        Шаблоны доступны:
                                        <div class="greenSelector"  id="internet">
                                            <span id="spanInSystem">
                                               <bean:message bundle="commonBundle" key="application.title"/>
                                            </span>
                                        </div>
                                        <c:if test="${phiz:impliesServiceRigid('ProductsRCSView')}">
                                            <div class="greenSelector transparent"  id="atm">
                                                <span id="spanInES">
                                                    банкоматы, терминалы
                                                </span>
                                            </div>
                                        </c:if>
                                        <div class="greenSelector transparent"  id="mobile">
                                            <span id="spanInMobile">
                                               мобильные устройства
                                            </span>
                                        </div>
                                        <c:if test="${phiz:isERMBConnectedPerson()}">
                                            <div class="greenSelector transparent"  id="sms">
                                                <span id="spanInSMS">
                                                   SMS
                                                </span>
                                            </div>
                                        </c:if>
                                    </div>
                                    <div class="filter-wrapper filter-area"></div>
                                    <img src="${globalUrl}/commonSkin/images/filter-tooth.gif" class="filter-tooth"/>
                                    <div class="clear"></div>

                                    <tiles:insert definition="window" flush="false">
                                        <tiles:put name="id" value="oneTimePasswordWindow"/>
                                    </tiles:insert>

                                    <div class="listContainer">
                                        <tiles:insert definition="simpleTableTemplate" flush="false" >
                                            <tiles:put name="id" value="templateList"/>
                                            <tiles:put name="grid">
                                                <sl:collection id="template" model="simple-pagination" property="templates" assignedPaginationSizes="20;50" assignedPaginationSize="20">
                                                    <sl:collectionParam id="selectType" value="checkbox"/>
                                                    <sl:collectionParam id="selectName" value="selectedIds"/>
                                                    <sl:collectionParam id="selectProperty" value="id"/>

                                                    <c:set var="templateId" value="${template.id}"/>
                                                    <c:set var="isDraftTemplate" value="${template.state.code == 'DRAFTTEMPLATE' || template.state.code == 'SAVED_TEMPLATE'}"/>
                                                    <c:set var="checkBoxStyleId">
                                                        <c:choose>
                                                            <c:when test="${isDraftTemplate}">Draft${templateId}</c:when>
                                                            <c:otherwise>${templateId}</c:otherwise>
                                                        </c:choose>
                                                    </c:set>
                                                    <c:set var="isAvailable">
                                                        <c:choose>
                                                            <c:when test="${form.fields.channelType == 'internet' && template.templateInfo.useInERIB}">true</c:when>
                                                            <c:when test="${form.fields.channelType == 'atm' && template.templateInfo.useInATM}">true</c:when>
                                                            <c:when test="${form.fields.channelType == 'sms' && template.templateInfo.useInERMB}">true</c:when>
                                                            <c:when test="${form.fields.channelType == 'mobile' && template.templateInfo.useInMAPI}">true</c:when>
                                                            <c:otherwise>false</c:otherwise>
                                                        </c:choose>
                                                    </c:set>
                                                    <c:set var="clazz">
                                                        <c:if test="${isDraftTemplate || not isAvailable}">draftTemplate</c:if>
                                                    </c:set>

                                                    <sl:collectionItem title="№">
                                                        <div class="${clazz}" id="num${checkBoxStyleId}">
                                                            <c:out value="${templateId}"/>
                                                        </div>
                                                    </sl:collectionItem>
                                                    <sl:collectionItem title="Создан">
                                                        <div class="${clazz}" id="create${checkBoxStyleId}">
                                                            <c:out value="${phiz:formatDateDependsOnSysDate(template.clientCreationDate, true, false)}"/>
                                                        </div>
                                                    </sl:collectionItem>
                                                    <sl:collectionItem title="Название">
                                                        <div class="${clazz} word-wrap" id="name${checkBoxStyleId}">
                                                            <c:out value="${template.templateInfo.name}"/>
                                                        </div>
                                                    </sl:collectionItem>
                                                    <sl:collectionItem title="Статус">
                                                        <div class="${clazz}" id="status${checkBoxStyleId}">
                                                            <c:choose>
                                                                <c:when test="${(template.formType == 'INTERNAL_TRANSFER' || template.formType == 'CONVERT_CURRENCY_TRANSFER' ||
                                                                    template.formType == 'IMA_PAYMENT' || template.formType == 'LOAN_PAYMENT') &&
                                                                    (template.state.code == 'WAIT_CONFIRM_TEMPLATE' || template.state.code == 'TEMPLATE')}">
                                                                    <bean:message bundle="userprofileBundle" key="templates.state.description.ACTIVE"/>
                                                                </c:when>
                                                                <c:otherwise><bean:message bundle="userprofileBundle" key="templates.state.description.${template.state.code}"/></c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                    </sl:collectionItem>
                                                    <sl:collectionItem title="Сумма">
                                                        <div class="${clazz}" id="amount${checkBoxStyleId}">
                                                            <c:choose>
                                                                <c:when test="${not empty template.chargeOffAmount}">
                                                                    <c:out value="${phiz:formatAmount(template.chargeOffAmount)}"/>
                                                                </c:when>
                                                                <c:when test="${not empty template.destinationAmount}">
                                                                    <c:out value="${phiz:formatAmount(template.destinationAmount)}"/>
                                                                </c:when>
                                                            </c:choose>
                                                        </div>
                                                    </sl:collectionItem>
                                                    <sl:collectionItem title="">
                                                        <html:hidden property="field(checked)" styleId="internetCheckBox${checkBoxStyleId}" name="form" value="${template.templateInfo.useInERIB}"/>
                                                        <div class="${clazz}" id="useinternet${checkBoxStyleId}">
                                                            <c:set var="key" value="templates.inERIB.available.${template.templateInfo.useInERIB}"/>
                                                            <bean:message bundle="userprofileBundle" key="${key}"/>
                                                        </div>
                                                    </sl:collectionItem>
                                                    <sl:collectionItem title="">
                                                        <html:hidden property="field(checked)" styleId="atmCheckBox${checkBoxStyleId}" name="form" value="${template.templateInfo.useInATM}"/>
                                                        <div class="${clazz}" id="useatm${checkBoxStyleId}">
                                                            <c:set var="key" value="templates.inUS.available.${template.templateInfo.useInATM}"/>
                                                            <bean:message bundle="userprofileBundle" key="${key}"/>
                                                        </div>
                                                    </sl:collectionItem>
                                                    <sl:collectionItem title="">
                                                        <html:hidden property="field(checked)" styleId="smsCheckBox${checkBoxStyleId}" name="form" value="${template.templateInfo.useInERMB}"/>
                                                        <div class="${clazz}" id="usesms${checkBoxStyleId}">
                                                            <c:set var="key" value="templates.inERMB.available.${template.templateInfo.useInERMB}"/>
                                                            <bean:message bundle="userprofileBundle" key="${key}"/>
                                                        </div>
                                                    </sl:collectionItem>
                                                    <sl:collectionItem title="">
                                                        <html:hidden property="field(checked)" styleId="mobileCheckBox${checkBoxStyleId}" name="form" value="${template.templateInfo.useInMAPI}"/>
                                                        <div class="${clazz}" id="usemobile${checkBoxStyleId}">
                                                            <c:set var="key" value="templates.inMP.available.${template.templateInfo.useInMAPI}"/>
                                                            <bean:message bundle="userprofileBundle" key="${key}"/>
                                                        </div>
                                                    </sl:collectionItem>
                                                </sl:collection>
                                            </tiles:put>
                                        </tiles:insert>
                                    </div>
                                </div>

                                <tiles:insert definition="window" flush="false">
                                    <tiles:put name="id" value="redirectRefused"/>
                                    <tiles:put name="data">
                                         <h2>Внимание!</h2>
                                         Для перехода на другую страницу, пожалуйста, сохраните изменения. Если Вы не хотите сохранять изменения, нажмите на кнопку «Отменить».
                                        <div class="buttonsArea">
                                            <tiles:insert definition="clientButton" flush="false">
                                                <tiles:put name="commandTextKey"    value="button.close"/>
                                                <tiles:put name="commandHelpKey"    value="button.close"/>
                                                <tiles:put name="bundle"            value="pfrBundle"/>
                                                <tiles:put name="viewType"          value="buttonGrey"/>
                                                <tiles:put name="onclick"           value="win.close('redirectRefused');"/>
                                            </tiles:insert>
                                        </div>
                                    </tiles:put>
                                </tiles:insert>

                                <div class="backToService bold">
                                    <html:link action="/private/userprofile/accountSecurity.do" onclick="return redirectResolved();" styleClass="blueGrayLink">
                                        <bean:message bundle="userprofileBundle" key="back.to.security"/>
                                    </html:link>
                                </div>
                                <%--для работы пагинации делаем скрытую кнопку button.filter, тк фильтр на странице не нужен--%>
                                <div style="display:none">
                                    <tiles:insert definition="commandButton" operation="EditClientTemplatesShowSettingsOperation" flush="false">
                                        <tiles:put name="commandKey"        value="button.filter"/>
                                        <tiles:put name="commandHelpKey"    value="button.filter.help"/>
                                        <tiles:put name="bundle"            value="commonBundle"/>
                                    </tiles:insert>
                                </div>
                                <div class="buttonsArea">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey"    value="button.cancel"/>
                                        <tiles:put name="commandHelpKey"    value="button.cancel.help"/>
                                        <tiles:put name="bundle"            value="commonBundle"/>
                                        <tiles:put name="action"            value="/private/userprofile/accountSecurity.do"/>
                                        <tiles:put name="viewType"          value="buttonGrey"/>
                                    </tiles:insert>
                                    <tiles:insert definition="commandButton" operation="EditClientTemplatesShowSettingsOperation" flush="false">
                                        <tiles:put name="commandKey"        value="button.save"/>
                                        <tiles:put name="commandHelpKey"    value="button.save.help"/>
                                        <tiles:put name="isDefault"         value="true"/>
                                        <tiles:put name="bundle"            value="commonBundle"/>
                                        <tiles:put name="validationFunction" value="updateChangedIds()"/>
                                    </tiles:insert>
                                </div>
                            </tiles:put>
                        </tiles:insert>
                    </tiles:put>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>