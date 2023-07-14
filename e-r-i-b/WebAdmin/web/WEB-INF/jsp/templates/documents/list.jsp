<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>

<html:form action="/templates/documents/list"  onsubmit="return setEmptyAction(event);">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

	<tiles:insert definition="logMain">
		<tiles:put name="needSave" type="string" value="false"/>
		<tiles:put name="submenu" type="string" value="TemplatesDoc"/>
		<%--<tiles:put type="string" name="messagesBundle" value="templatesBundle"/>--%>
        <tiles:put name="pageTitle" type="string">
			Шаблоны документов банка
		</tiles:put>
          <!--меню-->
				<tiles:put name="menu" type="string">
					<tiles:insert definition="clientButton" flush="false">
						<tiles:put name="commandTextKey"     value="button.doc.add"/>
						<tiles:put name="commandHelpKey" value="button.doc.add.help"/>
						<tiles:put name="bundle"  value="templatesBundle"/>
						<tiles:put name="image"   value=""/>
						<tiles:put name="action"  value="/templates/documents/edit.do"/>
                        <tiles:put name="viewType" value="blueBorder"/>
					</tiles:insert>
				</tiles:put>
		<!--данные-->
		<tiles:put name="data" type="string">
			<script type="text/javascript">
			var addUrl = "${phiz:calculateActionURL(pageContext,'/templates/documents/edit')}";
			function doEdit()
			{
                checkIfOneItem("selectedIds");
				if (!checkOneSelection("selectedIds", "Выберите шаблон!"))
					return;
				var id = getRadioValue(document.getElementsByName("selectedIds"));
				window.location = addUrl + "?id=" + id;
			}
			</script>
			<tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="documentsTemplate"/>
            <tiles:put name="text" value="Шаблоны документов"/>
            <tiles:put name="buttons">
                  <tiles:insert definition="clientButton" flush="false">
						<tiles:put name="commandTextKey"     value="button.edit"/>
						<tiles:put name="commandHelpKey" value="button.edit.help"/>
						<tiles:put name="bundle"  value="templatesBundle"/>
						<tiles:put name="onclick" value="doEdit();"/>
					</tiles:insert>
					<tiles:insert definition="commandButton" flush="false">
						<tiles:put name="commandKey"     value="button.remove"/>
						<tiles:put name="commandHelpKey" value="button.remove.help"/>
						<tiles:put name="bundle"  value="templatesBundle"/>
					</tiles:insert>
            </tiles:put>
            <tiles:put name="grid">
                        <sl:collection id="listElement" model="list" property="data" selectType="checkbox" selectName="selectedIds" selectProperty="id">
                            <sl:collectionItem title="Наименование шаблона" property="name">
                                <sl:collectionItemParam
                                        id="action"
                                        value="/templates/documents/edit.do?id=${listElement.id}"
                                        condition="${phiz:impliesService('PersonManagement')}"/>
                            </sl:collectionItem>
                            <sl:collectionItem title="Описание шаблона" property="description"/>
                            <sl:collectionItem title="Формат шаблона">
                                <sl:collectionItemParam id="value" value="HTML" condition="${listElement.fileType=='.HTML'}"/>
                                <sl:collectionItemParam id="value" value="Microsoft Word" condition="${listElement.fileType=='.DOC'}"/>
                                <sl:collectionItemParam id="value" value="Microsoft Excel" condition="${listElement.fileType=='.XLS'}"/>
                            </sl:collectionItem>
                            <sl:collectionItem title="Дата добавления/изменения">
                                <c:if test="${not empty listElement}">
                                    <bean:write name="listElement" property="update.time" format="dd.MM.yyyy"/>&nbsp;
                                </c:if>
                            </sl:collectionItem>
                        </sl:collection>
                    </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage" value="Не найдено ни одного шаблона."/>
            </tiles:insert>             
		</tiles:put>
	</tiles:insert>
</html:form>