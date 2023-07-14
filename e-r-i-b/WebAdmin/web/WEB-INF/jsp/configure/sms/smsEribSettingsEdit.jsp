<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"   prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"   prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"  prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"  prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags"                         prefix="phiz"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"            prefix="c" %>

<html:form action="/sms/erib/settings/edit" onsubmit="return setEmptyAction(event);">
    <c:set var="form"         value="${phiz:currentForm(pageContext)}"/>
    <c:set var="isCustomMAPI" value="${form.fields.mapiTemplateType}"/>
    <c:set var="isCustomATM"  value="${form.fields.amtTemplateType}"/>
    <c:set var="isCustomSocial"  value="${form.fields.socialTemplateType}"/>

    <tiles:insert definition="configEdit">
        <tiles:importAttribute/>

        <c:set var="imagePath"       value="${skinUrl}/images"/>
        <c:set var="globalImagePath" value="${globalUrl}/images"/>

        <tiles:put name="submenu"   type="string" value="SmsSettingsErib"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="alignTable"  value="center"/>
                <tiles:put name="name"        value="Редактирование шаблона SMS"/>
                <tiles:put name="description" value="На данной странице можно отредактировать тексты СМС–сообщения в разрезе каналов обслуживания клиентов"/>
                <tiles:put name="data">
                    <html:hidden property="fields(id)"/>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Название шаблона:
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(description)" style="width: 100%" maxlength="255"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Приоритет:
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(priority)" style="width: 10%" maxlength="255"/>
                        </tiles:put>
                    </tiles:insert>
                    <%-- ЕРИБ --%>

                    <fieldset>
                        <legend>ЕРИБ</legend>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                Текст SMS
                            </tiles:put>
                            <tiles:put name="data">
                                <html:textarea styleId="eribText" property="fields(eribText)" rows="10" style="width:100%" onkeyup="countSymbols(this, 'eribCount')"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                Символов: <span id="eribCount">0</span> из 499. Без участия переменных.
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>

                    <c:if test="${not(form.fields['key'] == 'default')}">
                        <%-- МОБИЛЬНОЕ ПРИЛОЖЕНИЕ --%>
                        <fieldset>
                            <legend>Приложение</legend>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    Текст SMS
                                </tiles:put>
                                <tiles:put name="data">
                                    <input name="fields(mapiTemplateType)" type="radio" value="false" onclick="blockCustomSmsMessage(this)" ${not(isCustomMAPI) ? 'checked' : null}>Использовать сообщение ЕРИБ
                                </tiles:put>
                            </tiles:insert>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="data">
                                    <input name="fields(mapiTemplateType)" type="radio" value="true"  onclick="blockCustomSmsMessage(this)" ${not(isCustomMAPI) ? null : 'checked'}>Указать другой текст
                                </tiles:put>
                            </tiles:insert>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="data">
                                    <html:textarea styleId="customMApiText" property="fields(customMApiText)" rows="10" style="width:100%" onkeyup="countSymbols(this, 'MapiCount')">
                                        <c:out value="${isCustomMAPI ? form.fields.customMApiText : null}"/>
                                    </html:textarea>
                                </tiles:put>
                            </tiles:insert>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="data">
                                    Символов: <span id="MapiCount">0</span> из 499. Без участия переменных.
                                </tiles:put>
                            </tiles:insert>
                        </fieldset>
                        <%-- УСТРОЙСТВА САМООБСЛУЖИВАНИЯ --%>

                        <fieldset>
                            <legend>Устройство самообслуживания</legend>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    Текст SMS
                                </tiles:put>
                                <tiles:put name="data">
                                    <input name="fields(amtTemplateType)" type="radio" value="false" onclick="blockCustomSmsMessage(this)" ${not(isCustomATM) ? 'checked' : null}>Использовать сообщение ЕРИБ
                                </tiles:put>
                            </tiles:insert>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="data">
                                    <input name="fields(amtTemplateType)" type="radio" value="true"  onclick="blockCustomSmsMessage(this)"  ${not(isCustomATM) ?  null : 'checked'}>Указать другой текст
                                </tiles:put>
                            </tiles:insert>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="data">
                                    <html:textarea styleId="customAtmText" property="fields(customAtmText)" rows="10" style="width:100%" onkeyup="countSymbols(this, 'AtmCount')">
                                        <c:out value="${isCustomATM  ? form.fields.customAtmText  : null}"/>
                                    </html:textarea>
                                </tiles:put>
                            </tiles:insert>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="data">
                                    Символов: <span id="AtmCount">0</span> из 499. Без участия переменных.
                                </tiles:put>
                            </tiles:insert>
                        </fieldset>

                         <%-- СОЦИАЛЬНОЕ ПРИЛОЖЕНИЕ --%>

                        <fieldset>
                            <legend>Социальное приложение</legend>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    Текст SMS
                                </tiles:put>
                                <tiles:put name="data">
                                    <input name="fields(socialTemplateType)" type="radio" value="false" onclick="blockCustomSmsMessage(this)" ${not(isCustomSocial) ? 'checked' : null}>Использовать сообщение ЕРИБ
                                </tiles:put>
                            </tiles:insert>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="data">
                                    <input name="fields(socialTemplateType)" type="radio" value="true"  onclick="blockCustomSmsMessage(this)"  ${not(isCustomSocial) ?  null : 'checked'}>Указать другой текст
                                </tiles:put>
                            </tiles:insert>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="data">
                                    <html:textarea styleId="customSocialText" property="fields(customSocialText)" rows="10" style="width:100%" onkeyup="countSymbols(this, 'SocialCount')">
                                        <c:out value="${isCustomSocial  ? form.fields.customSocialText  : null}"/>
                                    </html:textarea>
                                </tiles:put>
                            </tiles:insert>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="data">
                                    Символов: <span id="SocialCount">0</span> из 499. Без участия переменных.
                                </tiles:put>
                            </tiles:insert>
                        </fieldset>
                    </c:if>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton"  flush="false">
                        <tiles:put name="commandTextKey"     value="button.cancel"/>
                        <tiles:put name="commandHelpKey"     value="button.cancel"/>
                        <tiles:put name="bundle"             value="commonBundle"/>
                        <tiles:put name="action"             value="sms/erib/settings/list.do"/>
                    </tiles:insert>

                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandHelpKey"     value="button.save.help"/>
                        <tiles:put name="bundle"             value="commonBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                        <tiles:put name="validationFunction" value="defineSendText();"/>
                    </tiles:insert>
                    <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/sms/erib/locale/save')}"/>
                    <c:if test="${not empty form.fields['id']}">
                        <tiles:insert definition="languageSelectForEdit" flush="false">
                            <tiles:put name="selectId" value="chooseLocale"/>
                            <tiles:put name="idName" value="id"/>
                            <tiles:put name="entityId" value="${form.fields['id']}"/>
                            <tiles:put name="styleClass" value="float"/>
                            <tiles:put name="editLanguageURL" value="${editLanguageURL}"/>
                        </tiles:insert>
                    </c:if>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

    <script type="text/javascript">
        var customMApiText = document.getElementById('customMApiText'),
             customAtmText = document.getElementById('customAtmText'),
             customSocialText = document.getElementById('customSocialText'),
                  eribText = document.getElementById('eribText');

        function defineSendText()
        {
            <c:if test="${not(form.fields['key'] == 'default')}">
                var mapiType = document.getElementsByName('fields(mapiTemplateType)');
                var atmType  = document.getElementsByName('fields(amtTemplateType)');
                var socialType  = document.getElementsByName('fields(socialTemplateType)');

                for (var i=0; i<mapiType.length; i++)
                {
                    if (mapiType[i].checked && mapiType[i].value == 'false')
                    {
                        customMApiText.value = eribText.value;
                        customMApiText.removeAttribute('disabled');
                    }

                    if (atmType[i].checked && atmType[i].value == 'false')
                    {
                        customAtmText.value = eribText.value;
                        customAtmText.removeAttribute('disabled');
                    }

                    if (socialType[i].checked && socialType[i].value == 'false')
                    {
                        customSocialText.value = eribText.value;
                        customSocialText.removeAttribute('disabled');
                    }
                }
            </c:if>

            return true;
        }

        function blockCustomSmsMessage(element)
        {
            <c:if test="${not(form.fields['key'] == 'default')}">
                var blocked;

                if (element.name == 'fields(mapiTemplateType)')
                {
                    blocked = customMApiText;
                }

                if (element.name == 'fields(amtTemplateType)')
                {
                    blocked = customAtmText;
                }

                if (element.name == 'fields(socialTemplateType)')
                {
                    blocked = customSocialText;
                }

                if (element.value == 'true')
                {
                    blocked.removeAttribute('disabled');
                }
                else
                {
                    blocked.setAttribute('disabled', 'disabled');
                }
            </c:if>
        }

        function init()
        {
            countSymbols(eribText,       'eribCount');

            <c:if test="${not(form.fields['key'] == 'default')}">
                countSymbols(customMApiText, 'MapiCount');
                countSymbols(customAtmText,  'AtmCount');
                countSymbols(customSocialText,  'SocialCount');

                if (${not(isCustomMAPI)})
                {
                    customMApiText.setAttribute('disabled', 'disabled');
                }

                if (${not(isCustomATM)})
                {
                    customAtmText.setAttribute('disabled', 'disabled');
                }

                if (${not(isCustomSocial)})
                {
                    customSocialText.setAttribute('disabled', 'disabled');
                }
            </c:if>
        }

        init();
    </script>
</html:form>