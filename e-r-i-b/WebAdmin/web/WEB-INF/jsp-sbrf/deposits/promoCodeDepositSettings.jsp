<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/deposits/promoCodeDepositSettings" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="fieldTitlePrefix" value="settings.deposits.promocodes.field"/>
    <c:set var="propertyPrefix" value="com.rssl.iccs.promocodes"/>

    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="deposits"/>
        <tiles:put name="submenu" type="string" value="PromoCodeDepositSettings"/>
        <tiles:put name="pageName" value="Настройка промо-кода для открытия вклада"/>
        <tiles:put name="data" type="string">
            <table>
                <%--Разрешенные символы--%>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="${propertyPrefix}.accessibleSymbols"/>
                    <tiles:put name="fieldDescription"><bean:message key="${fieldTitlePrefix}.accessibleSymbols" bundle="configureBundle"/></tiles:put>
                    <tiles:put name="requiredField" value="false"/>
                    <tiles:put name="showHint" value="none"/>
                    <tiles:put name="textSize" value="1"/>
                    <tiles:put name="textMaxLength" value="60"/>
                    <tiles:put name="fieldType" value="textarea"/>
                </tiles:insert>
                <%--Минимальное количество символов в промо-коде--%>
                <tiles:insert definition="propertyField" flush="false">
                   <tiles:put name="fieldName" value="${propertyPrefix}.minCountSymbols"/>
                   <tiles:put name="fieldDescription"><bean:message key="${fieldTitlePrefix}.minCountSymbols" bundle="configureBundle"/></tiles:put>
                   <tiles:put name="requiredField" value="false"/>
                   <tiles:put name="showHint" value="none"/>
                   <tiles:put name="textMaxLength" value="2"/>
                   <tiles:put name="textSize" value="2"/>
                </tiles:insert>
                <%--Максимальное количество символов в промо-коде--%>
                <tiles:insert definition="propertyField" flush="false">
                   <tiles:put name="fieldName" value="${propertyPrefix}.maxCountSymbols"/>
                   <tiles:put name="fieldDescription"><bean:message key="${fieldTitlePrefix}.maxCountSymbols" bundle="configureBundle"/></tiles:put>
                   <tiles:put name="requiredField" value="false"/>
                   <tiles:put name="showHint" value="none"/>
                   <tiles:put name="textMaxLength" value="2"/>
                   <tiles:put name="textSize" value="2"/>
                </tiles:insert>
            </table>

            <%--Защита от подбора промо-кода--%>
            <fieldset>
                <legend><bean:message key="${fieldTitlePrefix}.antiCracking" bundle="configureBundle"/></legend>
                <table>
                    <%--Лимит неудачных попыток--%>
                    <tiles:insert definition="propertyField" flush="false">
                       <tiles:put name="fieldName" value="${propertyPrefix}.maxUnsuccessfullIteration"/>
                       <tiles:put name="fieldDescription"><bean:message key="${fieldTitlePrefix}.maxUnsuccessfullIteration" bundle="configureBundle"/></tiles:put>
                       <tiles:put name="requiredField" value="false"/>
                       <tiles:put name="showHint" value="none"/>
                       <tiles:put name="textMaxLength" value="2"/>
                       <tiles:put name="textSize" value="2"/>
                    </tiles:insert>
                    <%--Время блокировки--%>
                    <tiles:insert definition="propertyField" flush="false">
                       <tiles:put name="fieldName" value="${propertyPrefix}.blockingTimeMinutes"/>
                       <tiles:put name="fieldDescription"><bean:message key="${fieldTitlePrefix}.blockingTimeMinutes" bundle="configureBundle"/></tiles:put>
                       <tiles:put name="requiredField" value="false"/>
                       <tiles:put name="showHint" value="none"/>
                       <tiles:put name="textMaxLength" value="8"/>
                       <tiles:put name="textSize" value="5"/>
                    </tiles:insert>
                </table>
            </fieldset>

            <%--Настройка сообщений--%>
            <fieldset>
                <legend><bean:message key="${fieldTitlePrefix}.messages" bundle="configureBundle"/></legend>
                <c:set var="propertyMessagePrefix" value="${propertyPrefix}.message"/>
                <c:set var="promoCodesMessagesMap" value="${form.promoCodesMessagesMap}"/>
                <c:forEach items="${promoCodesMessagesMap}" var="promoCodesMessageItem" varStatus="messageCounter">
                    <fieldset>
                        <table>
                            <c:set var="messageKey" value="MSG0${messageCounter.count}"/>
                            <c:set var="promoCodesMessage" value="${promoCodesMessagesMap[messageKey]}"/>
                            <%--Номер--%>
                            <c:set var="messageNumber" value="${promoCodesMessage.number}"/>
                            <tiles:insert definition="propertyField" flush="false">
                                <tiles:put name="fieldName" value="${promoCodesMessage.number}"/>
                                <tiles:put name="fieldDescription"><bean:message key="${fieldTitlePrefix}.number" bundle="configureBundle"/></tiles:put>
                                <tiles:put name="requiredField" value="false"/>
                                <tiles:put name="showHint" value="none"/>
                                <tiles:put name="textSize" value="5"/>
                                <tiles:put name="disabled" value="true"/>
                            </tiles:insert>
                            <%--Тип--%>
                            <tiles:insert definition="propertyField" flush="false">
                                <tiles:put name="fieldName" value="${propertyMessagePrefix}.${messageNumber}.type"/>
                                <tiles:put name="fieldDescription"><bean:message key="${fieldTitlePrefix}.type" bundle="configureBundle"/></tiles:put>
                                <tiles:put name="requiredField" value="false"/>
                                <tiles:put name="showHint" value="none"/>
                                <tiles:put name="textSize" value="13"/>
                                <tiles:put name="disabled" value="true"/>
                            </tiles:insert>
                            <%--Заголовок--%>
                            <c:set var="titleDefaultKey"     value="${propertyMessagePrefix}.${messageNumber}.title.default"/>
                            <c:set var="textDefaultKey"      value="${propertyMessagePrefix}.${messageNumber}.text.default"/>
                            <c:set var="isDefaultTitleField" value="${form.fields[titleDefaultKey]}"/>
                            <c:set var="isDefaultTextField"  value="${form.fields[textDefaultKey]}"/>

                            <c:set var="fieldName" value="${propertyMessagePrefix}.${messageNumber}.title"/>
                            <tiles:insert definition="propertyField" flush="false">
                                <tiles:put name="fieldName" value="${fieldName}"/>
                                <tiles:put name="fieldDescription"><bean:message key="${fieldTitlePrefix}.title" bundle="configureBundle"/></tiles:put>
                                <tiles:put name="requiredField" value="false"/>
                                <tiles:put name="showHint"  value="${isDefaultTitleField ? 'bottom' : 'none'}"/>
                                <tiles:put name="fieldHint"><bean:message key="value.by.default.hint" bundle="configureBundle"/></tiles:put>
                                <tiles:put name="textSize"  value="1"/>
                                <tiles:put name="textMaxLength" value="60"/>
                                <tiles:put name="fieldType" value="textarea"/>
                            </tiles:insert>
                            <tr>
                                <td/>
                                <td>
                                    <%--Панель кнопок редактирования стиля заголовка--%>
                                    <div class="buttonsPanel">
                                        <tiles:insert definition="bbMessageButtons" flush="false">
                                            <tiles:put name="textAreaId" value="id_${fieldName}"/>
                                            <tiles:put name="showTarifPlan" value="false"/>
                                            <tiles:put name="showTextAlign" value="false"/>
                                            <tiles:put name="showHyperlink" value="false"/>
                                        </tiles:insert>
                                    </div>
                                </td>
                            </tr>
                            <%--Текст--%>
                            <tr>
                                <c:set var="areaId" value="text_${messageNumber}"/>
                                <td class="Width200 LabelAll alignTop">
                                    <bean:message key="${fieldTitlePrefix}.text" bundle="configureBundle"/>:
                                </td>
                                <td class="alignTop">
                                    <div class="messageText">
                                        <html:textarea styleId="${areaId}" property="field(${propertyMessagePrefix}.${messageNumber}.text)" rows="2" cols="60"/>
                                    </div>
                                    <c:if test="${isDefaultTextField}">
                                        <div class="clear"></div>
                                        <img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px" style="padding: 5px 0 0 7px"/>&nbsp;
                                        <span class="propertyDescText">
                                            <bean:message key="value.by.default.hint" bundle="configureBundle"/>
                                        </span>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td/>
                                <td>
                                    <%--Панель кнопок редактирования стиля сообщения--%>
                                    <div class="buttonsPanel">
                                        <tiles:insert definition="bbMessageButtons" flush="false">
                                            <tiles:put name="textAreaId" value="${areaId}"/>
                                            <tiles:put name="showTarifPlan" value="false"/>
                                            <tiles:put name="showTextAlign" value="false"/>
                                        </tiles:insert>
                                    </div>
                                </td>
                            </tr>
                            <%--Когда отображается сообщение--%>
                            <tiles:insert definition="propertyField" flush="false">
                                <tiles:put name="fieldName" value="${propertyMessagePrefix}.${messageNumber}.event"/>
                                <tiles:put name="fieldDescription"><bean:message key="${fieldTitlePrefix}.event" bundle="configureBundle"/></tiles:put>
                                <tiles:put name="requiredField" value="false"/>
                                <tiles:put name="showHint" value="none"/>
                                <tiles:put name="textSize" value="1"/>
                                <tiles:put name="textMaxLength" value="60"/>
                                <tiles:put name="fieldType" value="textarea"/>
                                <tiles:put name="disabled" value="true"/>
                            </tiles:insert>
                        </table>
                    </fieldset>
                </c:forEach>
            </fieldset>
        </tiles:put>
        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="javascript:resetForm(event)"/>
            </tiles:insert>
            <%--<c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/mobileApi/configure')}"/>--%>
            <%--<tiles:insert definition="languageSelectForEdit" flush="false">--%>
                <%--<tiles:put name="selectId" value="chooseLocale"/>--%>
                <%--<tiles:put name="entityId" value=""/>--%>
                <%--<tiles:put name="styleClass" value="float"/>--%>
                <%--<tiles:put name="selectTitleOnclick" value="openEditLocaleWin();"/>--%>
                <%--<tiles:put name="editLanguageURL" value="${editLanguageURL}"/>--%>
            <%--</tiles:insert>--%>
        </tiles:put>
        <tiles:put name="formAlign" value="center"/>
    </tiles:insert>
</html:form>