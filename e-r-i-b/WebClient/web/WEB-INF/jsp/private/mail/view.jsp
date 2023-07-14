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

<html:form action="/private/mail/view">
	<tiles:insert definition="mailEdit">
		<tiles:put name="mainmenu" value="Mails"/>
		<tiles:put name="pageTitle" type="string">
			<bean:message key="view.title" bundle="mailBundle"/>
		</tiles:put>

		<c:set var="form" value="${ViewMailForm}"/>
		<input type="hidden" name="id" value="${form.id}"/>
		
		<tiles:put name="menu" type="string">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.close"/>
				<tiles:put name="commandHelpKey" value="button.close.help"/>
				<tiles:put name="bundle" value="mailBundle"/>
				<tiles:put name="action" value="/private/mail/list.do"/>
			</tiles:insert>
		</tiles:put>


		<c:set var="mail" value="${form.mail}"/>
		<c:set var="date" value="${mail.date.time}"/>
		<tiles:put name="data" type="string">

		<tiles:insert definition="paymentForm" flush="false">
		<tiles:put name="id" value="viewLetter"/>
		<tiles:put name="name" value="Просмотр письма"/>
		<tiles:put name="description" value="Просмотр письма."/>
		<tiles:put name="buttons">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.reply"/>
				<tiles:put name="commandHelpKey" value="button.reply.help"/>
				<tiles:put name="bundle"         value="mailBundle"/>
				<tiles:put name="image"          value="iconSm_reply.gif"/>
				<tiles:put name="action"         value="/private/mail/edit.do?mailId=${form.id}"/>
			</tiles:insert>
		</tiles:put>
		<tiles:put name="data">
			<tr>
				<td class="Width120 LabelAll"><b>Дата</b></td>
				<td>&nbsp;
					<bean:write name="date" format="dd.MM.yyyy HH:mm"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll"><b>Тема</b></td>
				<td>&nbsp;
					<bean:write name="mail" property="subject"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll"><b>Текст</b></td>
				<td width="420px" style="text-align:justify; padding-left: 11px;">
                   ${phiz:processBBCode(mail.body)}
				</td>
			</tr>
		</tiles:put>
		<tiles:put name="alignTable" value="center"/>
		</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>
