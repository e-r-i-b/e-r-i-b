<%--
  Created by IntelliJ IDEA.
  User: egorova
  Date: 17.03.2009
  Time: 13:40:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<html:form action="/skins/edit"  onsubmit="return setEmptyAction(event);">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="skinsEdit">
		<tiles:put name="submenu" type="string" value="ManageSkins"/>

	    <tiles:put name="menu" type="string">
		    <tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.close"/>
				<tiles:put name="commandHelpKey" value="button.close.help"/>
				<tiles:put name="bundle"  value="commonBundle"/>
				<tiles:put name="image"   value=""/>
				<tiles:put name="action"  value="skins/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
	    </tiles:put>

	    <tiles:put name="data" type="string">
		    <script type="text/javascript">
                function updateFieldsState()
                {
                    <c:if test="${form.readonly}">
                        return false;
                    </c:if>
                    // 1. Получаем поля
                    var clientCheckbox = document.getElementsByName("field(client)");
                    var commonRadio = document.getElementsByName("field(common)");
                    var groupsCheckbox = document.getElementsByName("skinGroupIds");

                    // 2. Вычисляем правильное значение флажков
                    var isClient = getRadioValue(clientCheckbox) == "on";
                    var isCommon = isClient && getRadioValue(commonRadio) == "true";

                    // 3. Выставляем значения и доступность флажков
                    if (!isClient)
                        setRadioValue(commonRadio, false);
                    if (!isClient || isCommon)
                        clearRadioChecked(groupsCheckbox);
                    disableOrEnableRadio(commonRadio, !isClient);
                    disableOrEnableRadio(groupsCheckbox, !isClient || isCommon);
                }

                function checkAdminSkin()
				{
                    <c:if test="${form.readonly}">
                        return false;
                    </c:if>
					var newSkinUrl = getElement("field(path)");
					if (newSkinUrl==null || newSkinUrl.value==null || newSkinUrl.value=="")
					{
						alert("Введите значение в поле <bean:message key="label.path" bundle="skinsBundle"/>");
						return;
					}
					window.open("${phiz:calculateActionURL(pageContext, '/skins/adminMaket.do')}?skinUrl=" + newSkinUrl.value, "", "menubar=1,resizable=1,location=1,status=1,scrollbars=1,width=1204,height=768");
				}

                function checkClientSkin()
                {
                    <c:if test="${form.readonly}">
                        return false;
                    </c:if>
                    var newSkinUrl = getElement("field(path)");
                    if (newSkinUrl==null || newSkinUrl.value==null || newSkinUrl.value=="")
                    {
                        alert("Введите значение в поле <bean:message key="label.path" bundle="skinsBundle"/>");
                        return;
                    }
                    window.open("${phiz:calculateActionURL(pageContext, '/skins/clientMaket.do')}?skinUrl=" + newSkinUrl.value, "", "menubar=1,resizable=1,location=1,status=1,scrollbars=1,width=1204,height=768");
                }

                addEventListenerEx(window, 'load', updateFieldsState, false);
			</script>
		   <tiles:insert definition="paymentForm" flush="false">
			<tiles:put name="id" value="skins"/>
			<tiles:put name="name" value="Создание/Редактирование стиля"/>
			<tiles:put name="description" value="Используйте форму для создания нового или редактирования существующего стиля"/>
			<tiles:put name="data">

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.name" bundle="skinsBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:text property="field(name)" size="40" maxlength="32" disabled="${form.readonly}"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.path" bundle="skinsBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:text property="field(path)" size="40" maxlength="100" disabled="${form.readonly}"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.apply-to" bundle="skinsBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:checkbox property="field(admin)" disabled="${form.readonly || !form.changeAdminSkinAllowed}"/> <bean:message key="label.admin-application" bundle="skinsBundle"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="data">
                        <html:link href="#" onclick="checkAdminSkin();">
                            <bean:message key="button.check-admin-skin" bundle="skinsBundle"/>
                        </html:link>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="data">
                        <html:checkbox property="field(client)" disabled="${form.readonly}" onclick="updateFieldsState();"/> <bean:message key="label.client-application" bundle="skinsBundle"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="data">
                        <html:link href="#" onclick="checkClientSkin();">
                            <bean:message key="button.check-client-skin" bundle="skinsBundle"/>
                        </html:link>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.available" bundle="skinsBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:radio property="field(common)" value="true" onclick="updateFieldsState();" disabled="${form.readonly}"/> <bean:message key="label.for-all-clients" bundle="skinsBundle"/> <br/>
                        <html:radio property="field(common)" value="false" onclick="updateFieldsState();" disabled="${form.readonly}"/> <bean:message key="label.for-clients-of-specific-groups" bundle="skinsBundle"/>
                        <div>
                            <logic:iterate id="group" name="form" property="groups">
                                <html:multibox property="skinGroupIds" style="border:none" disabled="${form.readonly}">
                                    <bean:write name="group" property="id"/>
                                </html:multibox>
                                <bean:write name="group" property="name"/>
                                <br/>
                            </logic:iterate>
                        </div>
                    </tiles:put>
                </tiles:insert>
			</tiles:put>

			<tiles:put name="buttons">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.clear"/>
                    <tiles:put name="commandHelpKey" value="button.clear.help"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="action" value="/skins/edit.do?id=${form.id}"/>
                </tiles:insert>
                <c:if test="${!form.readonly}">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"  value="commonBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </c:if>
			</tiles:put>
		 </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>