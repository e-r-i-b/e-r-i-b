<%--
  User: Omeliyanchuk
  Date: 15.11.2006
  Time: 11:20:15
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>


<html:form action="/persons/groups" onsubmit="return setEmptyAction(event);">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<tiles:insert definition="personEdit">
		<tiles:put name="submenu" type="string" value="Groups"/>
		<tiles:put name="pageTitle" type="string">
			<bean:message key="edit.groups.title" bundle="personsBundle"/>
		</tiles:put>

		<tiles:put name="menu" type="string">
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey" value="button.save"/>
				<tiles:put name="commandHelpKey" value="button.save.help"/>
				<tiles:put name="bundle" value="personsBundle"/>
				<tiles:put name="image" value=""/>
				<tiles:put name="isDefault" value="true"/>
				<tiles:put name="postbackNavigation" value="true"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.close"/>
				<tiles:put name="commandHelpKey" value="button.close"/>
				<tiles:put name="bundle" value="commonBundle"/>
				<tiles:put name="image" value=""/>
				<tiles:put name="action" value="/persons/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

		<tiles:put name="data" type="string">
    		<c:set var="canEditGroup" value="${phiz:impliesOperation('EditGroupOperationC','PersonGroupManagement')}"/>
			<tiles:insert definition="tableTemplate" flush="false">
				<tiles:put name="id" value="GroupsList"/>
				<tiles:put name="grid">
					<sl:collection id="item" property="data" model="list" selectProperty="id" selectName="selectedIds" selectType="checkbox">
						<sl:collectionItem title="Наименование" property="name">
							<sl:collectionItemParam id="action" value="/groups/edit.do?id=${item.id}&category=${item.category}" condition="${canEditGroup}"/>
						</sl:collectionItem>
						<sl:collectionItem title="Описание" property="description"/>
					</sl:collection>
				</tiles:put>
				<tiles:put name="isEmpty" value="${empty form.data}"/>
				<tiles:put name="emptyMessage" value="Не найдено ни одной группы!"/>
			</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>