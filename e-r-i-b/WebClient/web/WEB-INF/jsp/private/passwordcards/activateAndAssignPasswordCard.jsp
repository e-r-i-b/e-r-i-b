<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/activatecard">
	<c:set var="form" value="${LastPasswordOnCardForm}"/>
	<tiles:insert definition="services">
	<tiles:put name="submenu" type="string" value="activateCard"/>
	<tiles:put name="mainmenu" value="ServicesClient"/>
		<tiles:put name="pageTitle" type="string">
			Активация новой карты ключей
		</tiles:put>
		<tiles:put name="data" type="string">
			<html:hidden property="returnPath"/>
			<c:if test="${form.success}">
				<script language="JavaScript">
					alert("Карта активирована!");
				</script>
			</c:if>
			<tiles:insert definition="paymentForm" flush="false">
				<tiles:put name="id" value="ActivateNewCard"/>
				<tiles:put name="name" value="Активация новой карты ключей"/>
				<tiles:put name="description" value="Используйте данную форму для активации новой карты ключей.."/>
				<tiles:put name="data">
					<tr>
						<td class="Width120 LabelAll">№&nbsp;новой&nbsp;карты&nbsp;паролей</td>
						<td>
							<html:text property="field(number)" maxlength="256" size="64"/>
						</td>
					</tr>
					<tr>
						<td class="Width120 LabelAll">Введите&nbsp;повторно&nbsp;№&nbsp;новой&nbsp;карты&nbsp;паролей</td>
						<td>
							<html:text property="field(renumber)" size="64" maxlength="256"/>
						</td>
					</tr>
				</tiles:put>
				<tiles:put name="buttons"> 					
					<tiles:insert definition="commandButton" flush="false">
						<tiles:put name="commandKey" value="button.activate"/>
						<tiles:put name="commandHelpKey" value="button.activate.help"/>
						<tiles:put name="bundle" value="commonBundle"/>
						<tiles:put name="isDefault" value="true"/>
					</tiles:insert>
				</tiles:put>
				<tiles:put name="alignTable" value="center"/>
			</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>
