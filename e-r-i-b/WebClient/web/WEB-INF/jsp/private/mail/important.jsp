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

<html:form action="/private/mail/important" onsubmit="return setEmptyAction(event);">
	<tiles:insert definition="mailEdit">
		<tiles:put name="mainmenu" value="Mails"/>
		<tiles:put name="pageTitle" type="string">
			<bean:message key="view.title" bundle="mailBundle"/>
		</tiles:put>

		<tiles:put name="menu" type="string">
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey" value="button.next"/>
				<tiles:put name="commandHelpKey" value="button.next.help"/>
				<tiles:put name="bundle" value="mailBundle"/>
				<tiles:put name="image" value="iconSm_next.gif"/>
			</tiles:insert>
		</tiles:put>

		<c:set var="form" value="${ViewMailForm}"/>
		<c:set var="mail" value="${form.mail}"/>
		<c:set var="date" value="${mail.date.time}"/>
		<html:hidden property="id" value="${form.id}"/>
		<tiles:put name="data" type="string">
			<tiles:insert definition="paymentForm" flush="false">
				<tiles:put name="id" value="viewLetter"/>
				<tiles:put name="name" value="Просмотр письма"/>
				<tiles:put name="description" value="Просмотр письма."/>
				<tiles:put name="data">
					<tr>
						<td class="Width120 LabelAll"><b>Дата</b></td>
						<td>&nbsp;
							<bean:write name="date" format="dd.MM.yyyy"/>
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
						<td width="420px" style="text-align:justify;">&nbsp;
							<bean:write name="mail" property="body"/>&nbsp;
						</td>
					</tr>
				</tiles:put>
				<tiles:put name="alignTable" value="center"/>
			</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>
