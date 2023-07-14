<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>
<c:set var="globalImagePath"    value="${globalUrl}/images"/>
<c:set var="imagePath"          value="${skinUrl}/images"/>

<html:form action="/group/risk/list" onsubmit="return setEmptyAction(event);">

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="dictionariesMain">
        <c:set var="bundle" value="groupsRiskBundle"/>
        <tiles:put name="submenu"   type="string" value="GroupRiskList"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message bundle="${bundle}" key="page.list.name"/>
        </tiles:put>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" operation="EditGroupRiskOperation" service="GroupRiskManagment" flush="false">
                <tiles:put name="commandTextKey"    value="button.add"/>
                <tiles:put name="commandHelpKey"    value="button.add.help"/>
                <tiles:put name="bundle"            value="${bundle}"/>
                <tiles:put name="action"            value="/group/risk/edit.do"/>
                <tiles:put name="viewType"          value="blueBorder"/>
            </tiles:insert>
        </tiles:put>
      
        <tiles:put name="data" type="string">
            <c:set var="canEdit" value="${phiz:impliesOperation('EditGroupRiskOperation','GroupRiskManagment')}"/>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="GroupRiskListTable"/>
                <tiles:put name="buttons">

                    <tiles:insert definition="commandButton" flush="false" operation="RemoveGroupRiskOperation" service="GroupRiskManagment">
                        <tiles:put name="commandKey"     value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove.help"/>
                        <tiles:put name="bundle"         value="${bundle}"/>
                        <tiles:put name="validationFunction">
                            checkSelection('selectedIds', 'Выберите хотябы одну группу риска!')
                        </tiles:put>
                        <tiles:put name="confirmText"    value="Удалить выбранные группы риска?"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="grid">
                    <sl:collection id="groupRisk" property="data" model="list">
                        <c:set var="logEntry" value="${groupRisk}"/>
                        <sl:collectionParam id="selectType" value="checkbox" condition="${canEdit}"/>
                        <sl:collectionParam id="selectProperty" value="id" condition="${canEdit}"/>
                        <sl:collectionParam id="selectName" value="selectedIds" condition="${canEdit}"/>

                        <sl:collectionItem title="Название">
                            <bean:write name='groupRisk' property="name" ignore="true"/><c:if test="${groupRisk.isDefault}"><c:out value="(по умолчанию)"></c:out></c:if>
                            <sl:collectionItemParam id="action" value="/group/risk/edit.do?id=${phiz:stringEncode(groupRisk.id)}" condition="${canEdit}"/>
                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage" value="Не найдено ни одной группы риска"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>