<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/templates/documents/edit" enctype="multipart/form-data">
	<tiles:insert definition="logMain">
		<tiles:put name="submenu" type="string" value="TemplatesDoc"/>
		<%--<tiles:put type="string" name="messagesBundle" value="templatesBundle"/>--%>
        <tiles:put name="pageTitle" type="string">
			Шаблон документа
		</tiles:put>

		<!--меню-->
		<tiles:put name="menu" type="string">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey"     value="button.templates.list"/>
				<tiles:put name="commandHelpKey" value="button.templates.list.help"/>
				<tiles:put name="bundle"  value="templatesBundle"/>
				<tiles:put name="image"   value=""/>
				<tiles:put name="action"  value="/templates/documents/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

		<!--данные-->
		<tiles:put name="data" type="string">
			<html:hidden property="id"/>
			<html:hidden property="field(saveBigFile)"/>
			<html:hidden property="field(saveBigFileQuestion)"/>
			<tiles:insert definition="paymentForm" flush="false">
				<tiles:put name="id" value="editDocumentsTemplate"/>
				<tiles:put name="name" value="Шаблон документа"/>
				<tiles:put name="description" value="Используйте данную форму редактирования шаблона документа."/>
				<tiles:put name="data">
				<tr>
					<td class="Width200 LabelAll">Наименование шаблона<span class="asterisk">*</span></td>
                    <td><html:text property="field(name)" size="100" maxlength="256" styleClass="contactInput"/>&nbsp;</td>
				</tr>
				<tr>
					<td class="Width200 LabelAll">Описание шаблона</td>
					<td><html:text property="field(description)" size="100" maxlength="256" styleClass="contactInput"/>&nbsp;</td>
				</tr>
				<tr>
					<td class="Width200 LabelAll">Формат шаблона</td>
					<td>
						<html:select property="field(fileType)" styleClass="contactInput">
							<html:option value=".HTML">HTML</html:option>
							<html:option value=".DOC">Microsoft Word</html:option>
							<html:option value=".XLS">Microsoft Excel</html:option>
						</html:select>
					</td>
				</tr>
                <tr>
					<td class="Width200 LabelAll">Имя файла<span class="asterisk">*</span></td>
					<td><phiz:file property="template" fileName="field(fileName)" styleClass="contactInput" size="50" fileId="id"/></td>
				</tr>
			</tiles:put>
			<tiles:put name="buttons">
		        <tiles:insert definition="commandButton" flush="false">
					<tiles:put name="postbackNavigation" value="true"/>
					<tiles:put name="commandKey"     value="button.save"/>
					<tiles:put name="commandHelpKey" value="button.save.help"/>
					<tiles:put name="bundle"  value="templatesBundle"/>
				</tiles:insert>
			</tiles:put>
			<tiles:put name="alignTable" value="center"/>
		</tiles:insert>
		</tiles:put>
	</tiles:insert>
	<script>
		var saveBigFileQuestion = document.getElementsByName('field(saveBigFileQuestion)')[0].value;
		if(saveBigFileQuestion == "true")
			if(confirm("Размер файла превышает 5Мб. Вы действительно хотите сохранить такой файл шаблона?"))
			{
				document.getElementsByName('field(saveBigFile)')[0].value = true;
				findCommandButton('button.save').click();
			}
	</script>
</html:form>