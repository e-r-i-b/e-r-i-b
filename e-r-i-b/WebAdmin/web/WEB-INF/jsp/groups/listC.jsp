<%--
  User: Omeliyanchuk
  Date: 10.11.2006
  Time: 13:34:19
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>

<html:form action="/groups/list" onsubmit="return setEmptyAction(event);">
	<c:set var="standalone" value="${empty param['action']}"/>
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="canEdit" value="${phiz:impliesOperation('EditGroupOperationC','PersonGroupManagement')}"/>
    <c:set var="canView" value="${phiz:impliesOperation('ViewGroupOperationC',null)}"/>

	<c:choose>
		<c:when test="${standalone}">
			<c:set var="layout" value="personList"/>
		</c:when>
		<c:otherwise>
			<c:set var="layout" value="dictionary"/>
		</c:otherwise>
	</c:choose>

	<tiles:insert definition="${layout}">
		<tiles:put name="submenu" type="string" value="GroupList"/>
		<tiles:put name="pageTitle" type="string">
			<bean:message key="listGroup.title" bundle="groupsBundle"/>
		</tiles:put>
		<tiles:put name="menu" type="string">
			<c:choose>
				<c:when test="${standalone and canEdit}">
					<tiles:insert definition="clientButton" flush="false">
						<tiles:put name="commandTextKey" value="button.add"/>
						<tiles:put name="commandHelpKey" value="button.add.help"/>
						<tiles:put name="bundle" value="groupsBundle"/>
						<tiles:put name="image" value=""/>
						<tiles:put name="action" value="/groups/edit.do?category=${form.category}"/>
                        <tiles:put name="viewType" value="blueBorder"/>
					</tiles:insert>
				</c:when>
				<c:otherwise>
					<tiles:insert definition="clientButton" flush="false">
						<tiles:put name="commandTextKey" value="button.cancel"/>
						<tiles:put name="commandHelpKey" value="button.cancel.help"/>
						<tiles:put name="bundle" value="mailBundle"/>
						<tiles:put name="image" value=""/>
						<tiles:put name="onclick" value="window.close()"/>
                        <tiles:put name="viewType" value="blueBorder"/>
					</tiles:insert>
				</c:otherwise>
			</c:choose>
		</tiles:put>

		<tiles:put name="filter" type="string">
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.name"/>
                <tiles:put name="labelTextAlign" value="right"/>
                <tiles:put name="bundle" value="groupsBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="name"/>
                <tiles:put name="maxlength" value="25"/>
                <tiles:put name="size" value="28"/>
            </tiles:insert>            
		</tiles:put>

		<tiles:put name="data" type="string">
			<input type="hidden" name="category" value="${form.category}"/>
			<tiles:insert definition="tableTemplate" flush="false">
				<tiles:put name="id" value="personList"/>
				<tiles:put name="buttons">
					<c:choose>
						<c:when test="${standalone and canEdit}">
							<tiles:insert definition="commandButton" flush="false">
								<tiles:put name="commandKey" value="button.remove"/>
								<tiles:put name="commandHelpKey" value="button.remove.help"/>
								<tiles:put name="bundle" value="groupsBundle"/>
								<tiles:put name="validationFunction">
                                    function()
                                    {
                                        checkIfOneItem("selectedIds");
                                        return checkSelection('selectedIds', 'Выберите одну или несколько групп');
                                    }
                                </tiles:put>
								<tiles:put name="confirmText" value="Удалить выбранные группы?"/>
							</tiles:insert>
						</c:when>
						<c:when test="${canEdit}">
							<script type="text/javascript">
								function sendGroupData(event)
								{
									var ids = document.getElementsByName("selectedIds");
									preventDefault(event);
									for (i = 0; i < ids.length; i++)
									{
										if (ids.item(i).checked)
										{
											var r = ids.item(i).parentNode.parentNode;
											var a = new Array(2);
											a['name'] = trim(r.cells[1].innerHTML);
											a['id'] = ids.item(i).value;
											window.opener.setGroupData(a);
											window.close();
											return;
										}
									}
									alert("Выберите группу.");
								}
                                function clearFilter()
                                {
                                    var filterForm = document.getElementsByTagName("form")[0];
	                                filterForm.reset();
                                }
							</script>
							<tiles:insert definition="clientButton" flush="false">
								<tiles:put name="commandTextKey" value="button.select"/>
								<tiles:put name="commandHelpKey" value="button.select.help"/>
								<tiles:put name="bundle" value="mailBundle"/>
								<tiles:put name="image" value="iconSm_select.gif"/>
								<tiles:put name="onclick" value="sendGroupData()"/>
							</tiles:insert>
						</c:when>
					</c:choose>
				</tiles:put>

				<tiles:put name="grid">
				   <sl:collection id="item" property="data" model="list">
					  <sl:collectionParam id="selectType" value="radio" condition="${not standalone}"/>
					  <sl:collectionParam id="selectType" value="checkbox" condition="${standalone && canEdit}"/>

					  <sl:collectionParam id="selectProperty" value="id" condition="${not standalone || (standalone && canEdit)}"/>
					  <sl:collectionParam id="selectName" value="selectedIds" condition="${not standalone || (standalone && canEdit)}"/>
					  <sl:collectionParam id="onRowClick" value="selectRow(this,'selectedIds');" condition="${not standalone}"/>
					  <sl:collectionParam id="onRowDblClick" value="sendGroupData();" condition="${not standalone}"/>

                      <c:set var="nameFieldTitle">
                          <bean:message key="label.name" bundle="groupsBundle"/>
                      </c:set>

					  <sl:collectionItem title="${nameFieldTitle}" property="name">
						 <sl:collectionItemParam id="action" value="/groups/edit.do?id=${item.id}&category=${form.category}" condition="${standalone && (canEdit || canView)}"/>
					  </sl:collectionItem>
					  <sl:collectionItem title="Описание" property="description"/>
				   </sl:collection>
				</tiles:put>

				<tiles:put name="isEmpty" value="${empty form.data}"/>
				<tiles:put name="emptyMessage" value="Не найдено ни одной группы, <br/>соответствующей заданному фильтру!"/>
			</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>
