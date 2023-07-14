<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 26.10.2006
  Time: 13:44:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<!--Заголовок раздела-->
<!--<table width="93%" cellpadding="0" cellspacing="0">-->
    <!--<tr><td class="infoTitle leftMenuHeader">-->
        <!--Клиенты-->
    <!--</td></tr>-->
<!--</table>-->

<c:if test="${phiz:impliesOperation('GetPersonsListOperation', 'PersonsViewing') || phiz:impliesOperation('GetPersonsListOperation', 'PersonManagement')}">
    <tiles:insert definition="leftMenuInset">
        <tiles:put name="enabled" value="${submenu != 'PersonList'}"/>
        <tiles:put name="action"  value="persons/list.do"/>
        <tiles:put name="text"    value="Список клиентов"/>
        <tiles:put name="title"   value="Список клиентов"/>
    </tiles:insert>
</c:if>
<tiles:insert definition="leftMenuInset" operation="GetDeletedPersonListOperation">
	<tiles:put name="enabled" value="${submenu != 'PersonArchive'}"/>
	<tiles:put name="action"  value="persons/archiveList.do"/>
	<tiles:put name="text"    value="Архив клиентов"/>
	<tiles:put name="title"   value="Архив клиентов"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="PersonGroupManagement">
	<tiles:put name="enabled" value="${submenu != 'GroupList'}"/>
	<tiles:put name="action"  value="groups/list.do?category=C"/>
	<tiles:put name="text"    value="Группы"/>
	<tiles:put name="title"   value="Группы"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ListClientOperation">
	<tiles:put name="enabled" value="${submenu != 'ClientListFull'}"/>
	<tiles:put name="action"  value="/persons/list/full"/>
	<tiles:put name="text"><bean:message key="form.list.full.leftMenu.title" bundle="personsBundle"/></tiles:put>
	<tiles:put name="title"><bean:message key="form.list.full.leftMenu.title" bundle="personsBundle"/></tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="UnloadMDMInfoService">
    <tiles:put name="enabled" value="${submenu != 'UnloadMDMInfo'}"/>
    <tiles:put name="action"  value="/persons/mdm/unload/info"/>
    <tiles:put name="text"><bean:message  key="form.unload.mdm.information" bundle="personsBundle"/></tiles:put>
    <tiles:put name="title"><bean:message key="form.unload.mdm.information" bundle="personsBundle"/></tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="EditMigrationInfoOperation">
	<tiles:put name="enabled" value="${submenu ne 'EditMigrationInfo'}"/>
	<tiles:put name="action"  value="/persons/migration/info"/>
	<tiles:put name="text"><bean:message key="left.menu.migration.full" bundle="migrationClientsBundle"/></tiles:put>
	<tiles:put name="title"><bean:message key="left.menu.migration.full" bundle="migrationClientsBundle"/></tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ListMigrationClientsOperation">
	<tiles:put name="enabled" value="${submenu ne 'ListMigrationUsers'}"/>
	<tiles:put name="action"  value="/persons/migration/list"/>
	<tiles:put name="text"><bean:message key="left.menu.migration.selected" bundle="migrationClientsBundle"/></tiles:put>
	<tiles:put name="title"><bean:message key="left.menu.migration.selected" bundle="migrationClientsBundle"/></tiles:put>
</tiles:insert>
