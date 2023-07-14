<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>

<html:form action="/employees/groups" onsubmit="return setEmptyAction(event);">
	<c:set var="form" scope="request" value="${phiz:currentForm(pageContext)}"/>

	<tiles:insert definition="employeesEdit">

		<tiles:put name="submenu" type="string" value="GroupList"/>

		<tiles:put name="pageTitle" type="string">
			<bean:message key="edit.groups.title" bundle="employeesBundle"/>
		</tiles:put>

		<tiles:put name="menu" type="string">
			<input type="hidden" name="resourcesType"/>
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.close"/>
				<tiles:put name="commandHelpKey" value="button.close"/>
				<tiles:put name="bundle" value="commonBundle"/>
				<tiles:put name="image" value=""/>
				<tiles:put name="action" value="/employees/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

		<tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="certList"/>
            <tiles:put name="text" value="Список групп"/>
            <tiles:put name="buttons">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle" value="employeesBundle"/>
                    <tiles:put name="isDefault" value="true"/>
				<tiles:put name="postbackNavigation" value="true"/>
			</tiles:insert>
            </tiles:put>
            <tiles:put name="grid">
                <sl:collection id="listElement" model="list" property="data">
                    <sl:collectionParam id="selectType" value="checkbox"/>
                    <sl:collectionParam id="selectName" value="selectedIds"/>
                    <sl:collectionParam id="selectProperty" value="id"/>
                    <sl:collectionItem title="Название" property="name">
                        <sl:collectionItemParam
                                id="action"
                                value="/groups/edit.do?id=${listElement.id}&category=${listElement.category}"
                                condition="${phiz:impliesService('PersonManagement')}"/>
                    </sl:collectionItem>
                    <sl:collectionItem title="Описание" property="description"/>
                </sl:collection>
            </tiles:put>
            <tiles:put name="isEmpty" value="${empty form.data}"/>
		    <tiles:put name="emptyMessage" value="Не найдено ни одной группы."/>
            </tiles:insert>
		</tiles:put>
	</tiles:insert>

</html:form>
