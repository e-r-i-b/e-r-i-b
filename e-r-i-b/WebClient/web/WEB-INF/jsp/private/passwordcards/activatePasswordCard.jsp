<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 27.11.2006
  Time: 15:06:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/activatecard">
	<c:set var="form" value="${LastPasswordOnCardForm}"/>
	<c:set var="hasActiveCard" value="${form.hasActiveCard}"/>
	<tiles:insert definition="services">
	<tiles:put name="submenu" type="string" value="activateCard"/>
	<tiles:put name="mainmenu" value="ServicesClient"/>
		<tiles:put name="pageTitle" type="string">
			Активация новой карты ключей
		</tiles:put>

		<tiles:put name="data" type="string">
			<c:set var="cardNum" value="${form.oldCardId}"/>
			<c:set var="keyNum" value="${form.needKeyNumber}"/>
			<input type="hidden" name="oldCardId" id="oldCardId" value="${cardNum}"/>
			<input type="hidden" name="needKeyNumber" id="needKeyNumber" value="${keyNum}"/>
			<input type="hidden" name="returnPath" id="returnPath" value="${form.returnPath}"/>
			<c:if test="${form.success}">
				<script language="JavaScript">
					alert("Карта активирована!");
				</script>
			</c:if>
			<c:if test="${hasActiveCard}">

			<tiles:insert definition="paymentForm" flush="false">
				<tiles:put name="id" value="ActivateNewCard"/>
				<tiles:put name="name" value="Активация новой карты ключей"/>
				<tiles:put name="description" value="Используйте данную форму для активации новой карты ключей.."/>
				<tiles:put name="data">
					<tr>
						<td class="Width120 LabelAll">Ключ&nbsp;№<b>${keyNum}</b>&nbsp;из&nbsp;карты №<b>${cardNum}</b></td>
						<td>
							<input type="text" id="field(password)" name="field(password)" size="64"
									   maxlength="256"/>
						</td>
					</tr>
					<tr>
						<td class="Width120 LabelAll">№&nbsp;новой&nbsp;карты&nbsp;паролей</td>
						<td>
							<input type="text" id="field(number)" name="field(number)" size="64"
									   maxlength="256"/>
						</td>
					</tr>
				</tiles:put>
				<tiles:put name="buttons">
					<c:if test="${hasActiveCard}">
						<tiles:insert definition="commandButton" flush="false">
							<tiles:put name="commandKey" value="button.activate"/>
							<tiles:put name="commandHelpKey" value="button.activate.help"/>
							<tiles:put name="bundle" value="commonBundle"/>
							<tiles:put name="image" value="iconSm_activate.gif"/>
							<tiles:put name="isDefault" value="true"/>
						</tiles:insert>						
					</c:if>
				</tiles:put>
				<tiles:put name="alignTable" value="center"/>
			</tiles:insert>
		</c:if>
		<c:if test="${not hasActiveCard}">
				<table width="100%" cellspacing="0" cellpadding="0" class="MaxSize">
					<tr>
						<td valign="middle" align="center" class="error">
								<b>
									У вас нет активной карты ключей <br/>
									Обратитесь в банк
								</b>
								<script type="text/javascript">hideTitle("tableTitle");</script>
						</td>
					</tr>
				</table>
			</c:if>
		</tiles:put>
	</tiles:insert>
</html:form>