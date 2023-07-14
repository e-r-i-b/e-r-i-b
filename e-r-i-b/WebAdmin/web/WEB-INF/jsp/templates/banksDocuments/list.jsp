<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/templates/banksDocuments/list"  onsubmit="return setEmptyAction(event);">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

	<tiles:insert definition="logMain">
		<tiles:put name="needSave" type="string" value="false"/>
		<tiles:put name="submenu" type="string" value="BanksDocuments"/>
		<%--<tiles:put type="string" name="messagesBundle" value="templatesBundle"/>--%>
        <tiles:put name="pageTitle" type="string">
			Договоры банка
		</tiles:put>
         <!--меню-->
				<tiles:put name="menu" type="string">
					<tiles:insert definition="clientButton" flush="false">
						<tiles:put name="commandTextKey"     value="button.contract.add"/>
						<tiles:put name="commandHelpKey" value="button.contract.add.help"/>
						<tiles:put name="bundle"  value="templatesBundle"/>
						<tiles:put name="image"   value=""/>
						<tiles:put name="action"  value="/templates/banksDocuments/edit.do"/>
                        <tiles:put name="viewType" value="blueBorder"/>
					</tiles:insert>
				</tiles:put>
		<!--данные-->
		<tiles:put name="data" type="string">
			<c:set var="canEdit" value="${phiz:impliesOperation('EditBanksDocumentOperation','TemplatesDocumentsManagement')}"/>
			<c:set var="standalone" value="true"/>

			<tiles:insert definition="tableTemplate" flush="false">
				<tiles:put name="id" value="BankDocumentsList"/>
				<tiles:put name="text" value="Договоры банка"/>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
						<tiles:put name="commandKey"     value="button.contract.remove"/>
						<tiles:put name="commandHelpKey" value="button.contract.remove.help"/>
						<tiles:put name="bundle"  value="templatesBundle"/>
					</tiles:insert>
                </tiles:put>
				<tiles:put name="grid">
					<sl:collection id="item" property="data" model="list">
						<sl:collectionParam id="selectType" value="radio" condition="${not standalone}"/>
						<sl:collectionParam id="selectType" value="checkbox" condition="${standalone && canEdit}"/>

						<sl:collectionParam id="selectProperty" value="id" condition="${not standalone || (standalone && canEdit)}"/>
						<sl:collectionParam id="selectName" value="selectedIds" condition="${not standalone || (standalone && canEdit)}"/>
						<sl:collectionParam id="onRowClick" value="selectRow(this,'selectedIds');" condition="${not standalone}"/>
						<sl:collectionParam id="onRowDblClick" value="sendDepartmentData();" condition="${not standalone}"/>

						<sl:collectionItem title="Имя" property="name">
							<sl:collectionItemParam id="action" value="/templates/banksDocuments/edit.do?id=${item.id}" condition="${standalone && canEdit}"/>
						</sl:collectionItem>
						<sl:collectionItem title="Описание" property="description"/>
						<sl:collectionItem title="Дата">
							<fmt:formatDate value="${item.update.time}" pattern="dd.MM.yyyy"/>							
						</sl:collectionItem>
					</sl:collection>
				</tiles:put>

				<tiles:put name="isEmpty" value="${empty form.data}"/>
				<tiles:put name="emptyMessage" value="Не найдено ни одного документа"/>
			</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>