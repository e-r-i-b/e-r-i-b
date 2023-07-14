<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html:form action="/settings/connect/udbo" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="showCheckbox" value="${form.replication}"/>

    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="configEdit"/>
        <tiles:put name="submenu" type="string" value="ConnectUDBOSettings"/>
        <tiles:put name="data">

            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name"><bean:message key="settings.connect.udbo.title" bundle="configureBundle"/></tiles:put>
                <tiles:put name="description"><bean:message key="settings.connect.udbo.description" bundle="configureBundle"/></tiles:put>
                <tiles:put name="data">
                    <script type="text/javascript">
                        doOnLoad(function() {
                            var input = document.getElementById("field(com.rssl.iccs.remoteConnectionUDBO.allowedTB)");
                            var ids = document.getElementsByName("selectedIds");
                            var list = input.value.split(';');
                            for (var i = 0; i < ids.length; i++)
                            {
                                if (contain(ids.item(i).value, list))
                                    ids.item(i).checked = true;
                            }
                        });

                        function contain(elem, list)
                        {
                            for (var i = 0; i < list.length; i++)
                            {
                                if (list[i] == elem)
                                    return true;
                            }
                            return false;
                        }

                        function saveTB()
                        {
                            var ids = document.getElementsByName("selectedIds");
                            var res = "";
                            for (var i = 0; i < ids.length; i++)
                            {
                                if (ids.item(i).checked)
                                {
                                    if (res == "")
                                        res = ids.item(i).value;
                                    else
                                        res = res + ";" + ids.item(i).value;
                                }
                            }
                            var input = document.getElementById("field(com.rssl.iccs.remoteConnectionUDBO.allowedTB)");
                            input.value = res;
                            return true;
                        }

                        <c:set var="editRulesURL" value="${phiz:calculateActionURL(pageContext, '/settings/connect/udbo/editRules')}"/>
                        function deleteEnteredRules(id)
                        {
                            ajaxQuery("operation=ajax.deleteRules&id=" + id, "${editRulesURL}", function(data){window.location = "${phiz:calculateActionURL(pageContext, '/settings/connect/udbo')}";});
                        }
                    </script>


                <table>
                    <c:if test="${showCheckbox}">
                        <tr colspan="2">
                            <input type="checkbox" id="replicationSelect" value="com.rssl.iccs.remoteConnectionUDBO.allowedTB" name="selectedProperties"/>
                                                <label for="replicationSelect" id="replicationSelectLabel"><bean:message key="label.checkbox.replication.property" bundle="commonBundle"/></label>
                        </tr>
                    </c:if>
                    <tr>
                        <td class="Width200 LabelAll alignTop">Список ТБ доступных для самостоятельного подлючения УДБО:</td>
                        <c:set var="departments" value="${form.departments}"/>
                        <td class="smallDynamicGrid">
                            <table class="tblInf">
                                <tr class="tblInfHeader">
                                    <th width="20px" class="titleTable">
                                        <input name="isSelectedAllItems" type="checkbox" style="border:none"
                                               onclick="switchSelection('isSelectedAllItems','selectedIds')" <c:if test="${showCheckbox}">disabled=""</c:if>/>
                                    </th>
                                    <th class="titleTable bold" width="100%">
                                        ТБ
                                    </th>
                                </tr>
                                <c:forEach var="department" items="${departments}" varStatus="lineInfo">
                                    <tr class="ListLine${lineInfo.count%2}">
                                        <td class="listItem" align="center">
                                            <input type="checkbox" name="selectedIds" value="${department.code.fields['region']}" <c:if test="${showCheckbox}">disabled=""</c:if>>
                                        </td>
                                        <td class="listItem" nowrap="true">
                                            ${department.name}
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>

                    </tr>
                    <c:set var="tb">
                        <bean:write name="form" property="field(com.rssl.iccs.remoteConnectionUDBO.allowedTB)"/>
                    </c:set>
                    <input type="text" value="${tb}" style="display:none" id="field(com.rssl.iccs.remoteConnectionUDBO.allowedTB)" name="field(com.rssl.iccs.remoteConnectionUDBO.allowedTB)"/>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.timeColdPeriod"/>
                        <tiles:put name="fieldDescription">Продолжительность холодного периода (в часах)</tiles:put>
                        <tiles:put name="textSize" value="10"/>
                        <tiles:put name="textMaxLength" value="8"/>
                        <tiles:put name="fieldHint">Продолжительность в часах холодного периода</tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>

                    <tiles:insert definition="propertyField" flush="false">
                       <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.workWithoutUDBO"/>
                       <tiles:put name="fieldDescription" value="Возможность продолжить работу в СБОЛ при отказе от подключения УДБО"/>
                       <tiles:put name="fieldHint" value="Возможность продолжить работу в СБОЛ при отказе от подключения УДБО"/>
                       <tiles:put name="showHint" value="right"/>
                       <tiles:put name="fieldType" value="select"/>
                       <tiles:put name="selectItems" value="false@Запретить|true@Разрешить"/>
                    </tiles:insert>

                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.cancelConnectUDBOMessage.title"/>
                        <tiles:put name="fieldDescription">Заголовок сообщения при отказе от подключения УДБО</tiles:put>
                        <tiles:put name="textSize" value="50"/>
                        <tiles:put name="textMaxLength" value="200"/>
                        <tiles:put name="fieldHint">Заголовок сообщения при отказе от подключения УДБО</tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>

                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.cancelConnectUDBOMessage.text"/>
                        <tiles:put name="fieldDescription">Текст сообщения при отказе от подключения УДБО</tiles:put>
                        <tiles:put name="textSize" value="50"/>
                        <tiles:put name="textMaxLength" value="200"/>
                        <tiles:put name="fieldHint">Заголовок сообщения при отказе от подключения УДБО</tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>

                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.cancelAcceptInfoUDBOMessage.title"/>
                        <tiles:put name="fieldDescription">Заголовок сообщения при отказе от подтверждения информации при подключении УДБО</tiles:put>
                        <tiles:put name="textSize" value="50"/>
                        <tiles:put name="textMaxLength" value="200"/>
                        <tiles:put name="fieldHint">Заголовок сообщения при отказе от подключения УДБО</tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>

                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.cancelAcceptInfoUDBOMessage.text"/>
                        <tiles:put name="fieldDescription">Текст сообщения при отказе от подтверждения информации при подключении УДБО</tiles:put>
                        <tiles:put name="textSize" value="50"/>
                        <tiles:put name="textMaxLength" value="200"/>
                        <tiles:put name="fieldHint">Заголовок сообщения при отказе от подключения УДБО</tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>

                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.needGoVSP.message"/>
                        <tiles:put name="fieldDescription">Текст о необходимости клиенту обратиться в ВСП для подключения УДБО</tiles:put>
                        <tiles:put name="textSize" value="50"/>
                        <tiles:put name="textMaxLength" value="200"/>
                        <tiles:put name="fieldHint">Текст о необходимости клиенту обратиться в ВСП для подключения УДБО</tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>

                    <tiles:insert definition="propertyField" flush="false">
                       <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.allConnectionUDBONeedWaitConfirmState"/>
                       <tiles:put name="fieldDescription" value="Требовать подтверждение всех заявок в ЕРКЦ"/>
                       <tiles:put name="fieldHint" value="Требовать подтверждение всех заявок в ЕРКЦ"/>
                       <tiles:put name="showHint" value="right"/>
                       <tiles:put name="fieldType" value="select"/>
                       <tiles:put name="selectItems" value="false@Нет|true@Да"/>
                    </tiles:insert>

                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.ageClientForNeedWaitConfirmState"/>
                        <tiles:put name="fieldDescription">Возраст клиента выше которого требовать подтверждения</tiles:put>
                        <tiles:put name="textSize" value="10"/>
                        <tiles:put name="textMaxLength" value="8"/>
                        <tiles:put name="fieldHint">Критерии безопасности для обязательного подтверждения заявки на подключение УДБО в КЦ: возраст клиента</tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>

                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.moneyClientForNeedWaitConfirmState"/>
                        <tiles:put name="fieldDescription">Размер актива клиента выше которого требовать подтверждения</tiles:put>
                        <tiles:put name="textSize" value="10"/>
                        <tiles:put name="textMaxLength" value="12"/>
                        <tiles:put name="fieldHint">Критерии безопасности для обязательного подтверждения заявки на подключение УДБО в КЦ: размер активов</tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>

                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.сoldPeriodInfo.message"/>
                        <tiles:put name="fieldDescription">Текст оповещения клиента о том, что он работает в холодном периоде</tiles:put>
                        <tiles:put name="textSize" value="50"/>
                        <tiles:put name="textMaxLength" value="200"/>
                        <tiles:put name="fieldHint">Для вывода числа дней и даты окончания периода используйте [days/] и [before/] </tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>

                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.acceptConnectUDBOMessage"/>
                        <tiles:put name="fieldDescription">Текст чекбокса согласия с условиями договора</tiles:put>
                        <tiles:put name="textSize" value="50"/>
                        <tiles:put name="textMaxLength" value="200"/>
                        <tiles:put name="fieldHint">Заголовок сообщения при отказе от подключения УДБО</tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>

                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.smsMessageConnectionUDBOSuccess"/>
                        <tiles:put name="fieldDescription">смс сообщение об успешном подключени УДБО</tiles:put>
                        <tiles:put name="textSize" value="50"/>
                        <tiles:put name="textMaxLength" value="200"/>
                        <tiles:put name="fieldHint">Введие смс сообщение об успешном подключени УДБО. Для задания номера УДБО и даты подключения используйте [udbo_number/] и [udbo_date/]</tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>

                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.termText"/>
                        <tiles:put name="fieldDescription">Текст условий для заявления о подключении УДБО</tiles:put>
                        <tiles:put name="requiredField" value="true"/>
                        <tiles:put name="showHint" value="none"/>
                        <tiles:put name="textSize" value="3"/>
                        <tiles:put name="textMaxLength" value="55"/>
                        <tiles:put name="fieldType" value="textarea"/>
                    </tiles:insert>

                    <tr>
                        <td colspan="3">
                            <fieldset>
                                 <legend>Права клиента во время холодного периода</legend>
                                 <table cellpadding="0" cellspacing="0" width="100%">
                                     <tiles:insert definition="propertyField" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.openAccount"/>
                                        <tiles:put name="fieldDescription" value="Открытие вкладов"/>
                                        <tiles:put name="fieldHint" value="Открытие вкладов"/>
                                        <tiles:put name="showHint" value="right"/>
                                        <tiles:put name="fieldType" value="select"/>
                                        <tiles:put name="selectItems" value="false@Запретить|true@Разрешить"/>
                                     </tiles:insert>

                                     <tiles:insert definition="propertyField" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.openIMAccount"/>
                                        <tiles:put name="fieldDescription" value="открытие ОМС"/>
                                        <tiles:put name="fieldHint" value="открытие ОМС"/>
                                        <tiles:put name="showHint" value="right"/>
                                        <tiles:put name="fieldType" value="select"/>
                                        <tiles:put name="selectItems" value="false@Запретить|true@Разрешить"/>
                                     </tiles:insert>

                                     <tiles:insert definition="propertyField" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.cardToIMAccount"/>
                                        <tiles:put name="fieldDescription" value="перевод карта – ОМС"/>
                                        <tiles:put name="fieldHint" value="перевод карта – ОМС"/>
                                        <tiles:put name="showHint" value="right"/>
                                        <tiles:put name="fieldType" value="select"/>
                                        <tiles:put name="selectItems" value="false@Запретить|true@Разрешить"/>
                                     </tiles:insert>

                                     <tiles:insert definition="propertyField" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.cardToAccount"/>
                                        <tiles:put name="fieldDescription" value="перевод карта – вклад/счет"/>
                                        <tiles:put name="fieldHint" value="перевод карта – вклад/счет"/>
                                        <tiles:put name="showHint" value="right"/>
                                        <tiles:put name="fieldType" value="select"/>
                                        <tiles:put name="selectItems" value="false@Запретить|true@Разрешить"/>
                                     </tiles:insert>

                                     <tiles:insert definition="propertyField" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.cardToCard"/>
                                        <tiles:put name="fieldDescription" value="перевод карта – карта"/>
                                        <tiles:put name="fieldHint" value="перевод карта – карта"/>
                                        <tiles:put name="showHint" value="right"/>
                                        <tiles:put name="fieldType" value="select"/>
                                        <tiles:put name="selectItems" value="false@Запретить|true@Разрешить"/>
                                     </tiles:insert>

                                     <tiles:insert definition="propertyField" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.accountToCard"/>
                                        <tiles:put name="fieldDescription" value="перевод вклад/счет – карта"/>
                                        <tiles:put name="fieldHint" value="перевод вклад/счет – карта"/>
                                        <tiles:put name="showHint" value="right"/>
                                        <tiles:put name="fieldType" value="select"/>
                                        <tiles:put name="selectItems" value="false@Запретить|true@Разрешить"/>
                                     </tiles:insert>

                                     <tiles:insert definition="propertyField" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.IMAccountToCard"/>
                                        <tiles:put name="fieldDescription" value="перевод ОМС – карта"/>
                                        <tiles:put name="fieldHint" value="перевод ОМС – карта"/>
                                        <tiles:put name="showHint" value="right"/>
                                        <tiles:put name="fieldType" value="select"/>
                                        <tiles:put name="selectItems" value="false@Запретить|true@Разрешить"/>
                                     </tiles:insert>

                                     <tiles:insert definition="propertyField" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.toOtherBankAccount"/>
                                        <tiles:put name="fieldDescription" value="перевод на счет в другом банке"/>
                                        <tiles:put name="fieldHint" value="перевод на счет в другом банке"/>
                                        <tiles:put name="showHint" value="right"/>
                                        <tiles:put name="fieldType" value="select"/>
                                        <tiles:put name="selectItems" value="false@Запретить|true@Разрешить"/>
                                     </tiles:insert>

                                     <tiles:insert definition="propertyField" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.toOtherBankCard"/>
                                        <tiles:put name="fieldDescription" value="перевод на карту в другом банке (Visa transfer, MC money send)"/>
                                        <tiles:put name="fieldHint" value="перевод на карту в другом банке (Visa transfer, MC money send)"/>
                                        <tiles:put name="showHint" value="right"/>
                                        <tiles:put name="fieldType" value="select"/>
                                        <tiles:put name="selectItems" value="false@Запретить|true@Разрешить"/>
                                     </tiles:insert>

                                     <tiles:insert definition="propertyField" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.toOtherBankCardPhoneNumber"/>
                                        <tiles:put name="fieldDescription" value="перевод на карту в Сбербанке, на счет в Сбербанке, на карту в Сбербанке по номеру телефона"/>
                                        <tiles:put name="fieldHint" value="перевод на карту в Сбербанке, на счет в Сбербанке, на карту в Сбербанке по номеру телефона"/>
                                        <tiles:put name="showHint" value="right"/>
                                        <tiles:put name="fieldType" value="select"/>
                                        <tiles:put name="selectItems" value="false@Запретить|true@Разрешить"/>
                                     </tiles:insert>

                                     <tiles:insert definition="propertyField" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.toJur"/>
                                        <tiles:put name="fieldDescription" value="перевод юр. лицу по свободным реквизитам"/>
                                        <tiles:put name="fieldHint" value="перевод юр. лицу по свободным реквизитам"/>
                                        <tiles:put name="showHint" value="right"/>
                                        <tiles:put name="fieldType" value="select"/>
                                        <tiles:put name="selectItems" value="false@Запретить|true@Разрешить"/>
                                     </tiles:insert>

                                     <tiles:insert definition="propertyField" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.remoteConnectionUDBO.coldPeriodOperation.toDepo"/>
                                        <tiles:put name="fieldDescription" value="операции по счетам ДЕПО"/>
                                        <tiles:put name="fieldHint" value="операции по счетам ДЕПО"/>
                                        <tiles:put name="showHint" value="right"/>
                                        <tiles:put name="fieldType" value="select"/>
                                        <tiles:put name="selectItems" value="false@Запретить|true@Разрешить"/>
                                     </tiles:insert>
                                 </table>
                            </fieldset>
                        </td>
                    </tr>

                    <c:if test="${!showCheckbox}">
                        <tr>
                            <td class="Width200 LabelAll alignTop"><bean:message key="settings.connect.udbo.rules" bundle="configureBundle"/></td>
                            <td class="smallDynamicGrid">
                                <c:if test="${!empty form.claimRulesList}">
                                    <sl:collection id="listElement" model="no-pagination" property="claimRulesList" bundle="configureBundle">
                                        <sl:collectionItem title="settings.connect.udbo.rules.id" property="id"/>
                                        <sl:collectionItem title="settings.connect.udbo.rules.startDate">
                                            ${phiz:formatDateToStringOnPattern(listElement.startDate, 'dd.MM.yyyy HH:mm')}
                                        </sl:collectionItem>
                                        <sl:collectionItem title="settings.connect.udbo.rules.status">
                                            <c:choose>
                                                <c:when test="${listElement.status == 'ACTIVE'}">
                                                    <bean:message key="settings.connect.udbo.rules.status.ACTIVE" bundle="configureBundle"/>
                                                </c:when>
                                                <c:when test="${listElement.status == 'ENTERED'}">
                                                    <bean:message key="settings.connect.udbo.rules.status.ENTERED" bundle="configureBundle"/>
                                                </c:when>
                                                <c:when test="${listElement.status == 'ARCHIVAL'}">
                                                    <bean:message key="settings.connect.udbo.rules.status.ARCHIVAL" bundle="configureBundle"/>
                                                </c:when>
                                            </c:choose>
                                        </sl:collectionItem>
                                        <sl:collectionItem title="settings.connect.udbo.rules.text">
                                            <html:link action="/settings/connect/udbo/editRules.do?id=${listElement.id}">Текст условий</html:link>
                                            <c:if test="${listElement.status == 'ENTERED'}">
                                                <img class="iconButton" title="Удалить" alt="X" src="${globalUrl}/commonSkin/images/icon_trash.gif" onclick="deleteEnteredRules(${listElement.id});"/>
                                            </c:if>
                                        </sl:collectionItem>
                                    </sl:collection>
                                </c:if>
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.add"/>
                                    <tiles:put name="commandHelpKey" value="button.add.help"/>
                                    <tiles:put name="bundle"  value="localeBundle"/>
                                    <tiles:put name="action" value="/settings/connect/udbo/editRules"/>
                                </tiles:insert>
                            </td>
                        </tr>
                    </c:if>
                </table>
                </tiles:put>
            </tiles:insert>

            <tiles:put name="formButtons">
                <tiles:insert definition="commandButton" flush="false" service="ConnectUDBOSettingsService">
                    <tiles:put name="commandKey"     value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle"  value="commonBundle"/>
                    <tiles:put name="postbackNavigation" value="true"/>
                    <tiles:put name="validationFunction" value="saveTB();"/>
                </tiles:insert>
                <tiles:insert definition="clientButton" flush="false" service="ConnectUDBOSettingsService">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="onclick" value="window.location.reload();"/>
                </tiles:insert>
            </tiles:put>
        </tiles:put>
    </tiles:insert>
</html:form>