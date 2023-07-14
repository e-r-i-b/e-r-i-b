<%--
  Created by IntelliJ IDEA.
  User: gladishev
  Date: 19.11.2007
  Time: 15:36:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/errors/edit">
<tiles:insert definition="serviceMain">
	<tiles:put name="submenu" type="string" value="ErrorMessages"/>
    <tiles:put name="pageTitle" type="string">
        <bean:message key="edit.title" bundle="errorsBundle"/>
    </tiles:put>

    <tiles:put name="menu" type="string">
	    
        <tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey"     value="button.save"/>
			<tiles:put name="commandHelpKey" value="button.save.help"/>
			<tiles:put name="bundle"  value="errorsBundle"/>
			<tiles:put name="image"   value=""/>
			<tiles:put name="isDefault" value="true"/>
			<tiles:put name="postbackNavigation" value="true"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>

	    <tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey"     value="button.cancel"/>
			<tiles:put name="commandHelpKey" value="button.cancel.help"/>
			<tiles:put name="bundle"  value="errorsBundle"/>
			<tiles:put name="image"   value=""/>
			<tiles:put name="action" value="/errors/list.do"/>
		</tiles:insert>
    </tiles:put>

	<tiles:put name="data" type="string">
		<input type="hidden" name="id" value="${EditErrorMessageForm.id}"/>
        <tiles:insert definition="paymentForm" flush="false">
			<tiles:put name="name" value="Редактирование сообщения"/>
			<tiles:put name="description" value="Используйте данную форму редактирования сообщения об ошибке."/>
            <tiles:put name="buttons">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"     value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle"  value="errorsBundle"/>
                    <tiles:put name="isDefault" value="true"/>
                    <tiles:put name="postbackNavigation" value="true"/>
                </tiles:insert>
            </tiles:put>
			<tiles:put name="data">
                <tr>
                    <td>Тип ошибки</td>
                    <td>Система</td>
                </tr>
                <tr>
                    <td>
                        <html:select property="field(errorType)" styleClass="select">
                            <html:option value="1">Клиентская</html:option>
                            <html:option value="2">Системная</html:option>
                        </html:select></td>
                    <td>
                        <html:select property="field(system)" styleClass="select">
                            <html:option value="0">Рапида</html:option>
                            <html:option value="1">Retail</html:option>
                        </html:select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">&nbsp;Регулярное выражение</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <html:text property="field(regExp)" size="70" maxlength="256"/>
                    </td>
                </tr>

                <tr>
                    <td colspan="2">&nbsp;Сообщение в ИКФЛ</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <html:text property="field(message)" size="70" maxlength="256"/>
                    </td>
                </tr>
			</tiles:put>
			<tiles:put name="alignTable" value="center"/>
        </tiles:insert>                
		<script type="text/javascript">
			function setSelectValue(name, value)
			{
			   var elem = document.getElementsByName(name)[0];
				if (value=='Retail') elem.value=1;
				else elem.value = 0;

			}
			setSelectValue('field(system)','<c:out value="${EditErrorMessageForm.fields.system}"/>');
		</script>
	</tiles:put>
</tiles:insert>
</html:form>
