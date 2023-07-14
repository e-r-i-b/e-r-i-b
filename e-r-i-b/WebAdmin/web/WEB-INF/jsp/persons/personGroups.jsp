<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>

<html:form action="/person/groups" onsubmit="return setEmptyAction(event);">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<tiles:insert definition="personEdit">
		<tiles:put name="submenu" type="string" value="PersonGroups"/>

		<tiles:put name="data" type="string">
			<tiles:insert definition="tableTemplate" flush="false">
				<tiles:put name="id"    value="PersonGroups"/>
				<tiles:put name="text" value="Группы"/>
				<tiles:put name="grid">
					<sl:collection id="item" property="data" model="list">
						<sl:collectionItem title="Наименование" property="name"/>
						<sl:collectionItem title="Описание"     property="description"/>
					</sl:collection>
				</tiles:put>
				<tiles:put name="isEmpty" value="${empty form.data}"/>
				<tiles:put name="emptyMessage" value="Не найдено ни одной группы!"/>
			</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>