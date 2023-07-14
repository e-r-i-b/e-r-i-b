<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<% pageContext.getRequest().setAttribute("mode","Services");%>
<% pageContext.getRequest().setAttribute("userMode","Forms");%>

<html:form action="/forms/payment-forms"  onsubmit="return setEmptyAction(event);">
	<tiles:insert definition="logMain">
		<tiles:put name="submenu" type="string" value="Forms"/>
		<tiles:put type="string" name="messagesBundle" value="formsBundle"/>
        <tiles:put name="pageTitle" type="string">
			Формы платежей
		</tiles:put>

		<!--меню-->
		<tiles:put name="menu" type="string">
			<script type="text/javascript">
				var addUrl = "${phiz:calculateActionURL(pageContext,'/forms/editPrintForm')}";

				function editPrintForm()
				{
					if (!checkOneSelection("selectedIds", "Выберите одну форму платежа"))
					return;

					var id = getRadioValue(document.getElementsByName("selectedIds"))
					window.location = addUrl + "?formName=" + id;
				}
			</script>
			<tiles:insert definition="commandButton" flush="false" operation="UploadFormOperation">
				<tiles:put name="commandKey"     value="button.add"/>
				<tiles:put name="commandHelpKey" value="button.add.help"/>
				<tiles:put name="bundle"  value="formsBundle"/>
				<tiles:put name="image"   value="add.gif"/>
                <tiles:put name="viewType" value="buttonGrey"/>
			</tiles:insert>
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"     value="button.download"/>
				<tiles:put name="commandHelpKey" value="button.download.help"/>
				<tiles:put name="bundle"  value="formsBundle"/>
				<tiles:put name="image"   value="save.gif"/>
				<tiles:put name="postbackNavigation" value="true"/>
                <tiles:put name="viewType" value="buttonGrey"/>
			</tiles:insert>
			<tiles:insert definition="clientButton" operation="UpdateFormOperation" flush="false">
				<tiles:put name="commandTextKey"     value="button.printForms"/>
				<tiles:put name="commandHelpKey" value="button.printForms"/>
				<tiles:put name="bundle"  value="formsBundle"/>
				<tiles:put name="onclick" value="editPrintForm();"/>
                <tiles:put name="viewType" value="buttonGrey"/>
			</tiles:insert>

		</tiles:put>

		<!--данные-->
		<tiles:put name="data" type="string">
			<table cellpadding="0" cellspacing="0" class="MaxSize">
			<tr>
			<td height="100%">
			<table cellspacing="0" cellpadding="0" width="100%" class="userTab" id="tableTitle">
				<!-- заголовок списка -->
				<tr class="titleTable">
					<td  width="20px">
						<html:checkbox property="selectAll" style="border:none" onclick="switchSelection('selectAll')"/>
					</td>
					<td width="200px">Имя</td>
					<td>Описание</td>
				</tr>
				<!-- строки списка -->
				<% int lineNumber = 0;%>
				<c:forEach items="${GetPaymentFormListForm.forms}" var="listElement">
					<% lineNumber++;%>
					<tr class="ListLine<%=lineNumber%2%>">
						<td align=center class="ListItem" width="20px">
							<html:multibox property="selectedIds" style="border:none">
								<bean:write name="listElement" property="name"/>
							</html:multibox>
						</td>
						<td class="ListItem">&nbsp;<bean:write name="listElement" property="name"/>&nbsp;</td>
						<td class="ListItem">&nbsp;<bean:write name="listElement" property="description"/>&nbsp;</td>
					</tr>
				</c:forEach>
			</table>
			<% if ( lineNumber == 0 )
			{%>
			<table width="100%" cellpadding="4">
				<tr><td class="messageTab" align="center">Не найдено ни одной формы платежа</td></tr>
			</table>
			<script>hideTitle();</script>
			<%}%>
			</td>
			</tr>
			</table>
		</tiles:put>
	</tiles:insert>
</html:form>