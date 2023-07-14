<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"   prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"   prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"  prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"  prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags"                         prefix="phiz"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"            prefix="c" %>

<html:form action="/sms/csa/settings/edit" onsubmit="return setEmptyAction(event);">

    <tiles:insert definition="configEdit">
        <tiles:importAttribute/>

        <c:set var="imagePath"       value="${skinUrl}/images"/>
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <c:set var="form"         value="${phiz:currentForm(pageContext)}"/>

        <tiles:put name="submenu"   type="string" value="SmsSettingsCSA"/>
        <tiles:put name="pageTitle" type="string">Редактирование шаблона SMS</tiles:put>
        <tiles:put name="data" type="string">

            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id"            value="editCSASms"/>
                <tiles:put name="alignTable"    value="center"/>
                <tiles:put name="name"          value="Редактирование шаблона SMS"/>
                <tiles:put name="description"   value="На данной странице можно отредактировать тексты СМС–сообщения"/>
                <tiles:put name="data">
                    <html:hidden property="fields(id)"/>

                    <tr>
                        <td>
                            <table width="100%" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td><br/></td>
                                    <td>
                                        <label>
                                            <nobr>Название шаблона:<span class="asterisk">*</span></nobr>
                                            <html:text property="fields(description)" style="width: 80%" maxlength="255"/>
                                        </label>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <fieldset>
                                <legend>ЦСА</legend>
                                <table width="100%" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td style="vertical-align: top;" class="LabelAllTitle"><nobr>Текст SMS</nobr></td>
                                        <td>
                                            <label>
                                                <html:textarea styleId="csaText" property="fields(csaText)" rows="10" cols="100" onkeyup="countSymbols(this, 'csaCount')"/>
                                            </label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td>Символов: <span id="csaCount">0</span> из 499. Без участия переменных.</td>
                                    </tr>
                                </table>
                            </fieldset>
                        </td>
                    </tr>
                </tiles:put>

                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton"  flush="false">
                        <tiles:put name="commandTextKey"     value="button.cancel"/>
                        <tiles:put name="commandHelpKey"     value="button.cancel"/>
                        <tiles:put name="bundle"             value="commonBundle"/>
                        <tiles:put name="action"             value="sms/csa/settings/list.do"/>
                    </tiles:insert>

                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandHelpKey"     value="button.save.help"/>
                        <tiles:put name="bundle"             value="commonBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/sms/csa/locale/save')}"/>
                    <c:if test="${not empty form.id}">
                        <tiles:insert definition="languageSelectForEdit" flush="false">
                            <tiles:put name="selectId" value="chooseLocale"/>
                            <tiles:put name="idName" value="id"/>
                            <tiles:put name="entityId" value="${form.id}"/>
                            <tiles:put name="styleClass" value="float"/>
                            <tiles:put name="editLanguageURL" value="${editLanguageURL}"/>
                        </tiles:insert>
                    </c:if>
                </tiles:put>
            </tiles:insert>

            <script type="text/javascript">
                var eribText = document.getElementById('csaText');
                countSymbols(eribText, 'csaCount');
            </script>
        </tiles:put>
    </tiles:insert>
</html:form>