<%--
  Created by IntelliJ IDEA.
  User: Gainanov
  Date: 26.02.2007
  Time: 17:07:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/mail/edit">
<tiles:insert definition="mailEdit">
<tiles:put name="pageTitle" type="string">
	<bean:message key="edit.title" bundle="mailBundle"/>
</tiles:put>
<tiles:put name="submenu" type="string" value="SentMailList"/>

<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.cancel"/>
		<tiles:put name="commandHelpKey" value="button.cancel.help"/>
		<tiles:put name="bundle" value="mailBundle"/>
		<tiles:put name="action" value="/private/mail/list.do"/>
	</tiles:insert>
</tiles:put>

<c:set var="form" value="${EditMailForm}"/>
<tiles:put name="data" type="string">


<input type="hidden" name="mailId" value="${form.mailId}"/>
<input type="hidden" id="itemMenu" value="Mails"/>
<tiles:insert definition="paymentForm" flush="false">
	<tiles:put name="id" value="EditLetter"/>
	<tiles:put name="name" value="Редактирование письма"/>
	<tiles:put name="description" value="Используйте данную форму редактирования письма	банку."/>
	<tiles:put name="data">
	<tr>
		<td colspan="2">
			<table cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td>&nbsp;</td>
				<td> Тип письма </td>
				<td> Номер </td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
					<html:select property="field(type)" styleClass="select" style="width:110px;">
						<html:option value="M">Письмо</html:option>
						<html:option value="Q">Вопрос</html:option>
						<html:option value="C">Жалоба</html:option>
						<html:option value="P">Проблема</html:option>
					</html:select>
				</td>
				<td>
					<html:text property="field(num)" readonly="true" style="width:110px;"/>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td colspan="2">Тема</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td colspan="2">
					<html:textarea property="field(subject)" cols="65" rows="2"/>
				</td>
			</tr>                                
			<tr>
				<td>&nbsp;</td>
				<td colspan="2">Текст</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td colspan="2">
					<html:textarea property="field(body)" cols="65" rows="9" style="text-align:justify;"/>
				</td>
			</tr>
	        </table>
		</td>
	</tr>
	</tiles:put>
	<tiles:put name="buttons">
		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey" value="button.save"/>
			<tiles:put name="commandHelpKey" value="button.save.help"/>
			<tiles:put name="bundle" value="mailBundle"/>			
			<tiles:put name="isDefault" value="true"/>
			<tiles:put name="postbackNavigation" value="true"/>
			<tiles:put name="image" value="iconSm_send.gif"/>
		</tiles:insert>
	</tiles:put>
	<tiles:put name="alignTable" value="center"/>
</tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>
