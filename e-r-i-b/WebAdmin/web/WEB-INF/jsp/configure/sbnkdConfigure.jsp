<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>


<html:form action="/settings/sbnkd" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="showCheckbox" value="${form.replication}"/>

    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="configEdit"/>
        <tiles:put name="submenu" type="string" value="SBNKDSettings"/>
        <tiles:put name="data">

            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name"><bean:message key="settings.sbnkd.title" bundle="configureBundle"/></tiles:put>
                <tiles:put name="description"><bean:message key="settings.sbnkd.description" bundle="configureBundle"/></tiles:put>
                <tiles:put name="data">
                    <script type="text/javascript">
                        function saveTB()
                        {
                            var ids = document.getElementsByName("terbankIds");
                            var res = "";
                            for (var i = 0; i < ids.length; i++)
                            {
                                res = res + ids.item(i).value + ";";
                            }
                            $('#allowedTB').attr('value', res);
                            return true;
                        }
                        function toggleClientInformFields()
                        {
                            var disabled = !$("input[name=field(com.rssl.phizic.config.sbnkd.informClientAboutSBNKDStatus)]").is(":checked");
                            $("#clientInformFields").find("textarea").each(
                                    function()
                                    {
                                        this.readOnly = disabled;
                                    }
                            );
                        }
                        <%--Добавить ТБ--%>
                        function setDepartmentInfo(result)
                        {
                            var tbListTable = $('#tbListTable');
                            var tbList = tbListTable.find('table.standartTable tbody');
                            var resId = result['tb'];

                            if (tbList.find('input[value=' + resId + ']').length == 0)
                            {
                                tbListTable.find('div.tblNeedTableStyle').show();
                                tbListTable.find('div.workspace-box').remove();

                                var inputList = tbListTable.find('tr[class^="ListLine"] input');
                                tbList.append(
                                        '<tr class="ListLine' + inputList.length % 2 + '"><td class="listItem" align="center">' +
                                                '<input type="checkbox" name="terbankIds" value=' + resId + '>' +
                                                '</td><td class="listItem">' + result['name'] + '</td></tr>'
                                );
                            }
                        }

                        <%--Удалить выбранные ТБ--%>
                        function removeDepartment()
                        {
                            if (!checkSelection("terbankIds", "Не выбран ТБ."))
                                return;
                            var tbListTable = $('#tbListTable');

                            //удаляем строки с выбранными ТБ
                            tbListTable.find('tr[class^="ListLine"] input:checked').parent().parent().remove();

                            var inputList = tbListTable.find('tr[class^="ListLine"] input');
                            //если не осталось ни одного ТБ, прячем таблицу и добавляем сообщение
                            if (inputList.length == 0)
                            {
                                tbListTable.find('tr.tblInfHeader input')[0].checked = false;
                                tbListTable.find('div.tblNeedTableStyle').hide();
                                var emptyMsg = '<bean:message key="empty.tb.guest.message" bundle="depositsBundle"/>';
                                tbListTable.append(
                                        '<div class="workspace-box roundBorder redBlock"><div class="r-content">' + emptyMsg +
                                                '</div></div>'
                                );
                            }
                        }
                    </script>

                <table>
                    <tr>
                        <td colspan="3">
                            <fieldset>
                                 <legend>Лимиты</legend>
                                 <table cellpadding="0" cellspacing="0" width="100%">
                                     <tiles:insert definition="propertyField" flush="false">
                                         <tiles:put name="fieldName" value="com.rssl.phizic.config.sbnkd.limits.limitClaimeFromOnePhoneNumber"/>
                                         <tiles:put name="fieldDescription">Лимит количества заявок с одного номера мобильного телефона в месяц</tiles:put>
                                         <tiles:put name="textSize" value="10"/>
                                         <tiles:put name="textMaxLength" value="3"/>
                                         <tiles:put name="fieldHint">Введите количество заявок. 0 - нет ограничений</tiles:put>
                                         <tiles:put name="imagePath" value="${imagePath}"/>
                                     </tiles:insert>

                                     <tiles:insert definition="propertyField" flush="false">
                                         <tiles:put name="fieldName" value="com.rssl.phizic.config.sbnkd.limits.limitClaimeCardPerMonth"/>
                                         <tiles:put name="fieldDescription">Лимит количества заказанных карт в месяц</tiles:put>
                                         <tiles:put name="textSize" value="10"/>
                                         <tiles:put name="textMaxLength" value="3"/>
                                         <tiles:put name="fieldHint">Введите количество карт. 0 - нет ограничений</tiles:put>
                                         <tiles:put name="imagePath" value="${imagePath}"/>
                                     </tiles:insert>

                                     <tiles:insert definition="propertyField" flush="false">
                                         <tiles:put name="fieldName" value="com.rssl.phizic.config.sbnkd.limits.textMessageCardClaimeLimit"/>
                                         <tiles:put name="fieldDescription">Текст отображаемого сообщения при достижении лимита</tiles:put>
                                         <tiles:put name="requiredField" value="true"/>
                                         <tiles:put name="fieldHint">Введите текст отображаемого сообщения при достижении лимита</tiles:put>
                                         <tiles:put name="textSize" value="5"/>
                                         <tiles:put name="textMaxLength" value="55"/>
                                         <tiles:put name="fieldType" value="textarea"/>
                                     </tiles:insert>
                                 </table>
                            </fieldset>
                        </td>
                    </tr>

                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.sbnkd.allowedTB"/>
                        <tiles:put name="fieldDescription">Список ТБ доступных для гостей</tiles:put>
                        <tiles:put name="requiredField" value="true"/>
                        <tiles:put name="fieldType" value="custom"/>
                        <tiles:put name="customField">
                            <table cellpadding="0" cellspacing="0" width="100%">
                                <c:if test="${not showCheckbox}">
                                <tr>
                                    <td>
                                        <div style="float:right;">
                                            <tiles:insert definition="clientButton" flush="false">
                                                <tiles:put name="commandTextKey" value="button.add"/>
                                                <tiles:put name="commandHelpKey" value="button.add.help"/>
                                                <tiles:put name="bundle" value="personsBundle"/>
                                                <tiles:put name="onclick" value="openDepartmentsDictionary(setDepartmentInfo, 'null', 1, true);"/>
                                            </tiles:insert>
                                            <tiles:insert definition="clientButton" flush="false">
                                                <tiles:put name="commandTextKey" value="button.remove"/>
                                                <tiles:put name="commandHelpKey" value="button.remove.help"/>
                                                <tiles:put name="bundle" value="personsBundle"/>
                                                <tiles:put name="onclick" value="removeDepartment();"/>
                                            </tiles:insert>
                                        </div>

                                    </td>
                                </tr>
                                </c:if>

                                <tr>
                                    <td id="tbListTable">
                                        <tiles:insert definition="tableTemplate" flush="false">
                                            <tiles:put name="head">
                                                <c:if test="${not showCheckbox}">
                                                    <th width="20px" class="titleTable">
                                                        <input name="isSelectedAllItems" type="checkbox" style="border:none"
                                                               onclick="switchSelection('isSelectedAllItems','terbankIds')"/>
                                                    </th>
                                                </c:if>
                                                <th class="titleTable bold">
                                                    Территориальный банк
                                                </th>
                                            </tiles:put>

                                            <tiles:put name="data">
                                                <c:set var="departments" value="${isUseCasNsiDictionaries ? entityVisibilityForm.departments : form.departments}"/>
                                                <c:forEach var="department" items="${departments}" varStatus="lineInfo">
                                                    <tr class="ListLine${lineInfo.count%2}">
                                                        <c:if test="${not showCheckbox}">
                                                        <td class="listItem" align="center">
                                                            <input type="checkbox" name="terbankIds" value="${department.code.fields['region']}"/>
                                                        </td>
                                                        </c:if>
                                                        <td class="listItem" nowrap="true">
                                                            ${department.name}
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tiles:put>
                                            <tiles:put name="createEmpty" value="true"/>
                                            <tiles:put name="emptyMessage">
                                                <bean:message key="empty.tb.guest.message" bundle="depositsBundle"/>
                                            </tiles:put>
                                        </tiles:insert>
                                    </td>
                                </tr>
                            </table>
                            <c:set var="tb">
                                <bean:write name="form" property="field(com.rssl.iccs.sbnkd.allowedTB)"/>
                            </c:set>
                            <input type="text" value="${tb}" style="display:none" id="allowedTB" name="field(com.rssl.iccs.sbnkd.allowedTB)"/>
                        </tiles:put>
                    </tiles:insert>




                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.config.sbnkd.textMessageVipClient"/>
                        <tiles:put name="fieldDescription">Текст отображаемого vip клиенту сообщения</tiles:put>
                        <tiles:put name="requiredField" value="true"/>
                        <tiles:put name="fieldHint">Введите текст отображаемого vip клиенту сообщения</tiles:put>
                        <tiles:put name="textSize" value="5"/>
                        <tiles:put name="textMaxLength" value="55"/>
                        <tiles:put name="fieldType" value="textarea"/>
                    </tiles:insert>

                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.config.sbnkd.maxDebitCard"/>
                        <tiles:put name="fieldDescription">Максимальное количество дебетовых карт</tiles:put>
                        <tiles:put name="textSize" value="10"/>
                        <tiles:put name="textMaxLength" value="3"/>
                        <tiles:put name="fieldHint">Введите максимальное количество дебетовых карт</tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.config.sbnkd.maxDebitCardMessage"/>
                        <tiles:put name="fieldDescription">Отображаемое сообщение при достижении максимального количества заказанных карт</tiles:put>
                        <tiles:put name="requiredField" value="true"/>
                        <tiles:put name="fieldHint">Введите текст сообщения при достижении максимального количества заказанных карт</tiles:put>
                        <tiles:put name="textSize" value="5"/>
                        <tiles:put name="textMaxLength" value="55"/>
                        <tiles:put name="fieldType" value="textarea"/>
                    </tiles:insert>

                    <tiles:insert definition="propertyField" flush="false">
                       <tiles:put name="fieldName" value="com.rssl.phizic.config.sbnkd.defaultPackageMobileBank"/>
                       <tiles:put name="fieldDescription" value="Пакет мобильного банка по умолчанию"/>
                       <tiles:put name="fieldHint" value="Выберете пакет мобильного банка по умолчанию"/>
                       <tiles:put name="showHint" value="right"/>
                       <tiles:put name="fieldType" value="select"/>
                       <tiles:put name="selectItems" value="econom@Экономный пакет|full@Полный пакет"/>
                    </tiles:insert>

                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.config.sbnkd.informClientAboutSBNKDStatus"/>
                        <tiles:put name="fieldDescription" value="Информирование клиента по обработке заявки"/>
                        <tiles:put name="showHint" value="none"/>
                        <tiles:put name="fieldType" value="checkbox"/>
                        <tiles:put name="onclick" value="toggleClientInformFields()"/>
                    </tiles:insert>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.config.sbnkd.textMessageStartProcessingExternalSystem"/>
                        <tiles:put name="fieldDescription">Отображаемое сообщение после подтверждения заявки</tiles:put>
                        <tiles:put name="requiredField" value="true"/>
                        <tiles:put name="textSize" value="5"/>
                        <tiles:put name="textMaxLength" value="55"/>
                        <tiles:put name="fieldType" value="textarea"/>
                        <tiles:put name="showHint" value="none"/>
                    </tiles:insert>

                    <tr>
                        <td colspan="3">
                            <fieldset>
                                <table cellpadding="0" cellspacing="0" width="100%" id="clientInformFields">

                                    <tiles:insert definition="propertyField" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.phizic.config.sbnkd.textMessageErrorExternalSystem"/>
                                        <tiles:put name="fieldDescription">Отображаемое сообщение в случае возникновения ошибок</tiles:put>
                                        <tiles:put name="requiredField" value="true"/>
                                        <tiles:put name="textSize" value="5"/>
                                        <tiles:put name="textMaxLength" value="55"/>
                                        <tiles:put name="fieldType" value="textarea"/>
                                        <tiles:put name="showHint" value="none"/>
                                    </tiles:insert>

                                    <tiles:insert definition="propertyField" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.phizic.config.sbnkd.smsMessageErrorExternalSystem"/>
                                        <tiles:put name="fieldDescription">SMS-сообщение в случае возникновения ошибок</tiles:put>
                                        <tiles:put name="requiredField" value="true"/>
                                        <tiles:put name="textSize" value="5"/>
                                        <tiles:put name="textMaxLength" value="55"/>
                                        <tiles:put name="fieldType" value="textarea"/>
                                        <tiles:put name="showHint" value="none"/>
                                    </tiles:insert>

                                    <tiles:insert definition="propertyField" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.phizic.config.sbnkd.smsMessageNotCompleted"/>
                                        <tiles:put name="fieldDescription">SMS-сообщение в случае неполной обработки заявки</tiles:put>
                                        <tiles:put name="requiredField" value="true"/>
                                        <tiles:put name="textSize" value="5"/>
                                        <tiles:put name="textMaxLength" value="55"/>
                                        <tiles:put name="fieldType" value="textarea"/>
                                        <tiles:put name="showHint" value="none"/>
                                    </tiles:insert>

                                    <tiles:insert definition="propertyField" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.phizic.config.sbnkd.smsMessageSuccess"/>
                                        <tiles:put name="fieldDescription">SMS-сообщение в случае успешной обработки заявки</tiles:put>
                                        <tiles:put name="requiredField" value="true"/>
                                        <tiles:put name="textSize" value="5"/>
                                        <tiles:put name="textMaxLength" value="55"/>
                                        <tiles:put name="fieldType" value="textarea"/>
                                        <tiles:put name="showHint" value="none"/>
                                    </tiles:insert>

                                </table>
                            </fieldset>
                        </td>
                    </tr>

                    <script>toggleClientInformFields();</script>

                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.config.sbnkd.textMessageWaitAnswerExternalSystem"/>
                        <tiles:put name="fieldDescription">Отображаемое сообщение во время ожидания ответа от смежных АС</tiles:put>
                        <tiles:put name="requiredField" value="true"/>
                        <tiles:put name="fieldHint">Введите отображаемое сообщение во время ожидания ответа от смежных АС</tiles:put>
                        <tiles:put name="textSize" value="5"/>
                        <tiles:put name="textMaxLength" value="55"/>
                        <tiles:put name="fieldType" value="textarea"/>
                    </tiles:insert>

                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.config.sbnkd.textMessageErrorForbiddenRegion"/>
                        <tiles:put name="fieldDescription">Отображаемое сообщение при оформлении заявки в неразрешенном регионе</tiles:put>
                        <tiles:put name="requiredField" value="true"/>
                        <tiles:put name="fieldHint">Отображаемое сообщение при оформлении заявки в неразрешенном регионе</tiles:put>
                        <tiles:put name="textSize" value="5"/>
                        <tiles:put name="textMaxLength" value="55"/>
                        <tiles:put name="fieldType" value="textarea"/>
                    </tiles:insert>

                </table>
                </tiles:put>
            </tiles:insert>

            <tiles:put name="formButtons">
                <tiles:insert definition="commandButton" flush="false" service="SBNKDSettingsService">
                    <tiles:put name="commandKey"     value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle"  value="commonBundle"/>
                    <tiles:put name="postbackNavigation" value="true"/>
                    <tiles:put name="validationFunction" value="saveTB();"/>
                    <tiles:put name="isDefault" value="true"/>
                </tiles:insert>
                <tiles:insert definition="clientButton" flush="false" service="SBNKDSettingsService">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="onclick" value="window.location.reload();"/>
                </tiles:insert>
            </tiles:put>
        </tiles:put>
    </tiles:insert>
</html:form>