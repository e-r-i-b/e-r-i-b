<%--
  User: Omeliyanchuk
  Date: 15.11.2006
  Time: 14:57:34
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
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<tiles:insert definition="employeesMain">
		<tiles:put name="submenu" type="string" value="Groups"/>
		<tiles:put name="pageTitle" type="string">
			<bean:message key="listGroup.title" bundle="groupsBundle"/>
		</tiles:put>
		<tiles:put name="menu" type="string">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.add"/>
				<tiles:put name="commandHelpKey" value="button.add.help"/>
				<tiles:put name="bundle" value="groupsBundle"/>
				<tiles:put name="image" value=""/>
				<tiles:put name="action" value="/groups/edit.do?category=${form.category}"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

        <tiles:put name="filter" type="string">
			<tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.title"/>
                <tiles:put name="labelTextAlign" value="right"/>
				<tiles:put name="bundle" value="employeesBundle"/>
				<tiles:put name="mandatory" value="false"/>
				<tiles:put name="name" value="name"/>
                <tiles:put name="maxlength" value="25"/>
                <tiles:put name="size" value="28"/>
			</tiles:insert>
		</tiles:put>

		<tiles:put name="data" type="string">
			<c:set var="canEdit" value="${phiz:impliesOperation('EditGroupOperationA','EmployeeGroupManagement')}"/>
			<input type="hidden" name="category" value="${form.category}"/>
			<tiles:insert definition="tableTemplate" flush="false">
				<tiles:put name="id" value="personList"/>
                <tiles:put name="buttons">
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
                </tiles:put>
				<tiles:put name="grid">
					<sl:collection id="item" property="data" model="list">
						<sl:collectionParam id="selectType" value="checkbox" condition="${canEdit}"/>

						<sl:collectionParam id="selectProperty" value="id" condition="${canEdit}"/>
						<sl:collectionParam id="selectName" value="selectedIds" condition="${canEdit}"/>

                        <c:set var="nameFieldTitle">
                            <bean:message key="label.title" bundle="employeesBundle"/>
                        </c:set>

						<sl:collectionItem title="${nameFieldTitle}" property="name">
							<sl:collectionItemParam id="action" value="/groups/edit.do?id=${item.id}&category=${form.category}" condition="${canEdit}"/>
						</sl:collectionItem>
						<sl:collectionItem title="Описание" property="description"/>
					</sl:collection>
				</tiles:put>

				<tiles:put name="isEmpty" value="${empty form.data}"/>
				<tiles:put name="emptyMessage" value="Не найдено ни одной группы, <br/>соответствующего заданному фильтру!"/>
			</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>