<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<html:form action="/mobileApi/configure" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="configEdit"/>
        <tiles:put name="submenu" type="string" value="MobileApiSettings"/>
        <tiles:put name="pageName" value="Общие настройки мобильного API"/>
        <tiles:put name="additionalStyle" type="string">width750</tiles:put>
        <tiles:put name="pageDescription" value="Используйте форму для изменения общих настроек mAPI."/>
        <tiles:put name="data" type="string">
            <fieldset>
                <legend>Версия API</legend>
                <table>
                    <table>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="mobile.api.versions"/>
                        <tiles:put name="fieldDescription">Поддерживаемые версии</tiles:put>
                        <tiles:put name="requiredField" value="true"/>
                        <tiles:put name="showHint" value="none"/>
                        <tiles:put name="textSize" value="55"/>
                    </tiles:insert>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="mobile.api.invalid.version.text"/>
                        <tiles:put name="fieldDescription"><bean:message key="mobile.api.invalid.version.text" bundle="configureBundle"/></tiles:put>
                        <tiles:put name="requiredField" value="true"/>
                        <tiles:put name="showHint" value="none"/>
                        <tiles:put name="textSize" value="3"/>
                        <tiles:put name="textMaxLength" value="55"/>
                        <tiles:put name="fieldType" value="textarea"/>
                    </tiles:insert>
                    </table>
                </table>
            </fieldset>
            <fieldset>
                <legend>Настройка работы с устройствами со взломанными ОС</legend>
                <table>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="mobile.api.jailbreak.enabled"/>
                        <tiles:put name="fieldDescription">Работа с устройств со взломанными ОС</tiles:put>
                        <tiles:put name="requiredField" value="true"/>
                        <tiles:put name="showHint" value="none"/>
                        <tiles:put name="fieldType" value="radio"/>
                        <tiles:put name="selectItems" value="true@Разрешена|false@Запрещена"/>
                    </tiles:insert>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="mobile.api.jailbreak.enabled.text"/>
                        <tiles:put name="fieldDescription"><bean:message key="mobile.api.jailbreak.enabled.text" bundle="configureBundle"/></tiles:put>
                        <tiles:put name="requiredField" value="true"/>
                        <tiles:put name="showHint" value="none"/>
                        <tiles:put name="textSize" value="3"/>
                        <tiles:put name="textMaxLength" value="55"/>
                        <tiles:put name="fieldType" value="textarea"/>
                    </tiles:insert>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="mobile.api.jailbreak.disabled.text"/>
                        <tiles:put name="fieldDescription"><bean:message key="mobile.api.jailbreak.disabled.text" bundle="configureBundle"/></tiles:put>
                        <tiles:put name="requiredField" value="true"/>
                        <tiles:put name="showHint" value="none"/>
                        <tiles:put name="textSize" value="3"/>
                        <tiles:put name="textMaxLength" value="55"/>
                        <tiles:put name="fieldType" value="textarea"/>
                    </tiles:insert>
                </table>
            </fieldset>

            <fieldset>
                <legend><bean:message bundle="configureBundle" key="mobile.api.settings.search.service.provider"/></legend>
                <table>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="operations.history.max.search.period.in.days"/>
                        <tiles:put name="fieldDescription">
                            <bean:message bundle="configureBundle" key="mobile.api.settings.search.service.provider.description"/>
                        </tiles:put>
                        <tiles:put name="requiredField" value="true"/>
                        <tiles:put name="fieldHint">
                            <bean:message bundle="configureBundle" key="mobile.api.settings.search.service.provider.hint"/>
                        </tiles:put>
                        <tiles:put name="inputDesc">
                            <bean:message bundle="configureBundle" key="mobile.api.settings.day"/>
                        </tiles:put>
                        <tiles:put name="showHint" value="right"/>
                        <tiles:put name="textSize" value="3"/>
                    </tiles:insert>
                </table>
           </fieldset>
            <%--Настройки быстрой оплаты любого мобильного телефона--%>
            <fieldset>
                <legend>
                    <bean:message bundle="configureBundle" key="mobile.api.settings.pay.phonenumber"/>
                </legend>
                <table>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="mobile.api.settings.field.pay.phonenumber.sum.default"/>
                        <tiles:put name="fieldDescription">
                            <bean:message bundle="configureBundle" key="mobile.api.settings.field.pay.phonenumber.sum.description"/>
                        </tiles:put>
                        <tiles:put name="textSize" value="6"/>
                        <tiles:put name="textMaxLength" value="10"/>
                        <tiles:put name="format" value="###,##0.00"/>
                        <tiles:put name="showHint" value="right"/>
                        <tiles:put name="fieldHint">
                            <bean:message bundle="configureBundle" key="mobile.api.settings.field.pay.phonenumber.sum.hint"/>
                        </tiles:put>
                        <tiles:put name="inputDesc"><bean:message bundle="configureBundle" key="currencyes.rub"/></tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="mobile.api.settings.field.pay.phonenumber.count"/>
                        <tiles:put name="fieldDescription">
                            <bean:message bundle="configureBundle" key="mobile.api.settings.field.pay.phonenumber.count.description"/>
                        </tiles:put>
                        <tiles:put name="requiredField" value="true"/>
                        <tiles:put name="textSize" value="6"/>
                        <tiles:put name="textMaxLength" value="3"/>
                        <tiles:put name="showHint" value="right"/>
                        <tiles:put name="fieldHint">
                            <bean:message bundle="configureBundle" key="mobile.api.settings.field.pay.phonenumber.count.hint"/>
                        </tiles:put>
                    </tiles:insert>
                </table>
            </fieldset>
            <fieldset>
                <legend>Краудгифтинг</legend>
                <table>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="mobile.api.fund.limit.daily.senders.count"/>
                        <tiles:put name="fieldDescription">
                            <bean:message key="mobile.api.fund.limit.daily.senders.count" bundle="configureBundle"/>
                        </tiles:put>
                        <tiles:put name="requiredField" value="true"/>
                        <tiles:put name="textSize" value="6"/>
                        <tiles:put name="textMaxLength" value="10"/>
                        <tiles:put name="showHint" value="right"/>
                        <tiles:put name = "fieldHint">
                            <bean:message key="mobile.api.fund.limit.daily.senders.count.description" bundle="configureBundle"/>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.lifetime-request-on-fundraiser"/>
                        <tiles:put name="fieldDescription">
                            <bean:message key="com.rssl.iccs.lifetime-request-on-fundraiser" bundle="configureBundle"/>
                        </tiles:put>
                        <tiles:put name="requiredField" value="true"/>
                        <tiles:put name="textSize" value="3"/>
                        <tiles:put name="textMaxLength" value="3"/>
                        <tiles:put name="inputDesc" value="дней"/>
                        <tiles:put name="showHint" value="right"/>
                        <tiles:put name="fieldHint">
                            <bean:message bundle="configureBundle" key="com.rssl.iccs.lifetime-request-on-fundraiser.description"/>
                        </tiles:put>
                    </tiles:insert>
                </table>
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
            <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/mobileApi/configure')}"/>
            <tiles:insert definition="languageSelectForEdit" flush="false">
                <tiles:put name="selectId" value="chooseLocale"/>
                <tiles:put name="entityId" value=""/>
                <tiles:put name="styleClass" value="float"/>
                <tiles:put name="selectTitleOnclick" value="openEditLocaleWin(this);"/>
                <tiles:put name="editLanguageURL" value="${editLanguageURL}"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="formAlign" value="center"/>
    </tiles:insert>
</html:form>