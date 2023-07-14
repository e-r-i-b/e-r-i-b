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
			<tiles:put name="isDefault" value="true"/>
			<tiles:put name="postbackNavigation" value="true"/>
            <tiles:put name="viewType" value="buttonGrey"/>
		</tiles:insert>

	    <tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey"     value="button.cancel"/>
			<tiles:put name="commandHelpKey" value="button.cancel.help"/>
			<tiles:put name="bundle"  value="errorsBundle"/>
			<tiles:put name="action" value="/errors/list.do"/>
            <tiles:put name="viewType" value="buttonGrey"/>
		</tiles:insert>
    </tiles:put>

	<tiles:put name="data" type="string">
		<input type="hidden" name="id" value="${EditErrorMessageForm.id}"/>
		<table cellpadding="0" cellspacing="0" class="editNewsTab">
			<tr>
				<td colspan="3">
					<table class="MaxSize">
						<tr>
							<td align="center" class="silverBott paperTitle">
								<table height="100%" width="420px" cellspacing="0" cellpadding="0">
									<tr>
										<td class="titleHelp"><br>
											<span class="formTitle">Редактирование сообщения</span>
											<br/>
											Используйте данную форму редактирования сообщения об ошибке.
										</td>
									</tr>
								</table>
							</td>
							<td width="100%">&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>Тип ошибки</td>
				<td>Система</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
					<html:select property="field(errorType)" styleClass="select">
						<html:option value="1">Клиентская</html:option>
						<html:option value="2">Системная</html:option>
					</html:select></td>
				<td>
					<html:select property="field(system)" styleClass="select">
						<html:option value="1">Retail</html:option>
					</html:select>
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td>&nbsp;Регулярное выражение</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="2">
					<html:text property="field(regExp)" size="70" maxlength="256"/>
				</td>
			</tr>

			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;Сообщение в ИКФЛ</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td colspan="2">
					<html:text property="field(message)" size="70" maxlength="256"/>
				</td>
			</tr>
			<tr><td colspan="2">&nbsp;</td></tr>
		</table>
		<script type="text/javascript">
			function setSelectValue(name, value)
			{
			   var elem = document.getElementsByName(name)[0];
				if (value=='Retail') elem.value=1;
				else elem.value = 1;

			}
			setSelectValue('field(system)','<c:out value="${EditErrorMessageForm.fields.system}"/>');
		</script>
	</tiles:put>
</tiles:insert>
</html:form>
