<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/currencyRate/configure" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="fields" value="form.fields"/>
    <c:set var="tarifPlanConfigList" value="${form.tarifPlanConfigList}"/>
    <c:set var="fields" value="${form.fields}"/>
    <tiles:insert definition="configEdit">
        <tiles:put name="submenu" type="string" value="CurrencyRateConfig"/>
        <tiles:put name="data" type="string">
            <tiles:importAttribute/>
            <c:set var="globalImagePath" value="${globalUrl}/images"/>
            <c:set var="imagePath" value="${skinUrl}/images"/>

            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name" value="Настройка курсов"/>
                <tiles:put name="description">
                    На данной форме Вы можете настроить отображение стандартного курса при совершении конверсионных операций.
                </tiles:put>
                <tiles:put name="data">
                    <sl:collection id="tarifPlanConfig" model="list" property="tarifPlanConfigList">
                        <c:set var="tarifPlanCodeTypeDescription" value="${tarifPlanConfig.name}"/>
                        <c:set var="needShowStandartRateField" value="needShowStandartRate_${tarifPlanConfig.code}"/>
                        <c:set var="needShowStandartRate" value="${fields[needShowStandartRateField]}"/>
                        <c:set var="privilegedRateField" value="privilegedRate_${tarifPlanConfig.code}"/>
                        <c:set var="privilegedRate" value="${fields[privilegedRateField]}"/>
                        <c:if test="${! empty tarifPlanCodeTypeDescription}">
                            <tr>
                                <td>
                                    <fieldset>
                                        <legend>Тарифный план &laquo;${tarifPlanConfig.name}&raquo;</legend>
                                        <div class="currencyRateSettings">
                                            <div class="standartRate">
                                                <div class="label">
                                                    Отображать стандартный курс при совершении конверсионных операций:
                                                </div>
                                                <div class="radio">
                                                    <div>
                                                        <input name="field(needShowStandartRate_${tarifPlanConfig.code})" type="radio" value="1"
                                                               ${(needShowStandartRate == '1') ? "checked" : null}>Отображать
                                                    </div>
                                                    <div>
                                                        <input name="field(needShowStandartRate_${tarifPlanConfig.code})" type="radio" value="0"
                                                               ${(needShowStandartRate == '0') ? "checked" : null}>Скрывать
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="privilegedRate">
                                                <div class="label">
                                                    Сообщение, отображаемое при использовании льготного курса:
                                                </div>
                                                <div class="radio">
                                                    <input name="field(privilegedRate_${tarifPlanConfig.code})" type="radio" value="0" onchange="initTarifMessage('${tarifPlanConfig.code}');"
                                                           ${(privilegedRate == '0') ? "checked" : null}>Не отображать
                                                    <div class="messageGroup">
                                                        <div class="messageRadio">
                                                            <input name="field(privilegedRate_${tarifPlanConfig.code})" type="radio" value="1"
                                                                   ${(privilegedRate == '1') ? "checked" : null}>
                                                        </div>
                                                        <c:set var="areaId" value="privilegedRateMessage_${tarifPlanConfig.code}"/>
                                                        <div class="messageText">
                                                            <html:textarea styleId="${areaId}" property="field(privilegedRateMessage_${tarifPlanConfig.code})"
                                                                           rows="5" cols="70">
                                                            </html:textarea>
                                                        </div>
                                                        <%--Панель кнопок редактирования стиля сообщения--%>
                                                        <div class="buttonsPanel">
                                                            <tiles:insert definition="bbMessageButtons" flush="false">
                                                                <tiles:put name="textAreaId" value="${areaId}"/>
                                                            </tiles:insert>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/currencyRate/configure/locale/save')}"/>
                                        <c:set var="languageSelectorId" value="chooseLocale${tarifPlanConfig.id}"/>
                                        <tiles:insert definition="languageSelectForEdit" flush="false">
                                            <tiles:put name="selectId" value="${languageSelectorId}"/>
                                            <tiles:put name="entityId" value="${tarifPlanConfig.id}"/>
                                            <tiles:put name="styleClass" value="floatRight"/>
                                            <tiles:put name="multiSelect" value="true"/>
                                            <tiles:put name="selectTitleOnclick" value="openEditLocaleWin(this);"/>
                                            <tiles:put name="editLanguageURL" value="${editLanguageURL}"/>
                                        </tiles:insert>
                                    </fieldset>
                                </td>
                            </tr>
                        </c:if>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false" service="CurrencyRateConfigureService">
                        <tiles:put name="commandKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false" service="CurrencyRateConfigureService">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="onclick" value="javascript:resetForm(event)"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="alignTable" value="center"/>
            </tiles:insert>
            <script type="text/javascript">
                function initTarifMessage(tarifName)
                {
                    if (tarifName == undefined)
                        return;
                    var message = document.getElementById("privilegedRateMessage_"+tarifName);
                    if (message == undefined)
                        return;
                    message.value = "";
                }
            </script>
        </tiles:put>
    </tiles:insert>
</html:form>