<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<html:form action="/templates/banksDocuments/edit"  onsubmit="return setEmptyAction(event);" enctype="multipart/form-data">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

	<tiles:insert definition="logMain">
		<tiles:put name="submenu" type="string" value="BanksDocuments"/>
		<%--<tiles:put type="string" name="messagesBundle" value="templatesBundle"/>--%>
        <tiles:put name="pageTitle" type="string">
			Договор банка
		</tiles:put>

		<!--меню-->
		<tiles:put name="menu" type="string">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey"     value="button.doc.list"/>
				<tiles:put name="commandHelpKey" value="button.doc.list.help"/>
				<tiles:put name="bundle"  value="templatesBundle"/>
				<tiles:put name="image" value=""/>
				<tiles:put name="action" value="/templates/banksDocuments/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

		<!--данные-->
		<html:hidden property="id"/>
		<tiles:put name="data" type="string">
			<tiles:insert definition="paymentForm" flush="false">
				<tiles:put name="id" value="editDocumentsTemplate"/>
				<tiles:put name="name" value="Договор банка"/>
				<tiles:put name="description" value="Используйте данную форму редактирования шаблона документа."/>
				<tiles:put name="data">
				<tr>
					<td class="Width120 LabelAll">Наименование<span class="asterisk">*</span></td>
                    <td><html:text property="field(name)" size="100" maxlength="256" styleClass="contactInput"/>&nbsp;</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">Описание</td>
					<td><html:text property="field(description)" size="100" maxlength="256" styleClass="contactInput"/>&nbsp;</td>
				</tr>
                <tr>
					<td class="Width120 LabelAll">Файл</td>
	                <td><phiz:file property="template" fileName="field(fileName)" fileId="id" styleClass="contactInput"/></td>
				</tr>
			</tiles:put>
			<tiles:put name="buttons">
		        <tiles:insert definition="commandButton" flush="false">
					<tiles:put name="postbackNavigation" value="true"/>
					<tiles:put name="commandKey"     value="button.doc.save"/>
					<tiles:put name="commandHelpKey" value="button.doc.save.help"/>
					<tiles:put name="bundle"  value="templatesBundle"/>
				</tiles:insert>
			</tiles:put>
			<tiles:put name="alignTable" value="center"/>
		</tiles:insert>

		</tiles:put>
	</tiles:insert>
</html:form>