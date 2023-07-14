<%--
  Created by IntelliJ IDEA.
  User: osminin
  Date: 30.04.2009
  Time: 16:59:39
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="standalone" value="${empty param['action']}"/>

<c:choose>
    <c:when test="${standalone}">
         <c:set var="layoutDefinition" value="externalSystemsMain"/>
    </c:when>
    <c:otherwise>
        <c:set var="layoutDefinition" value="dictionary"/>
    </c:otherwise>
</c:choose>

<html:form action="/private/externalSystems" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="${layoutDefinition}">
    <tiles:put name="mainmenu" value="ExternalSystems"/>
    <tiles:put name="submenu" value="ExternalSystems" type="string"/>
	<tiles:put name="pageTitle" type="string" value=" Справочник внешних систем"/>

    <tiles:put name="menu" type="string">
        <c:choose>
            <c:when test="${standalone}">
                <script type="text/javascript">
                    var addUrl = "${phiz:calculateActionURL(pageContext,'/dictionaries/editExternalSystem')}";
                    function doEdit()
                    {
                        checkIfOneItem("selectedIds");
                        if (!checkOneSelection("selectedIds", "Выберите одину систему!"))
                            return;
                        var id = getRadioValue(document.getElementsByName("selectedIds"));
                        window.location = addUrl + "?id=" + id;
                    }
                </script>

                <tiles:insert definition="clientButton" flush="false" operation="EditExternalSystemOperation">
                    <tiles:put name="commandTextKey" value="button.add.externalSystem"/>
                    <tiles:put name="commandHelpKey" value="button.add.externalSystem.help"/>
                    <tiles:put name="bundle"         value="dictionariesBundle"/>
                    <tiles:put name="action"         value="/dictionaries/editExternalSystem.do"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>

            </c:when>
            <c:otherwise>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    <tiles:put name="bundle" value="dictionariesBundle"/>
                    <tiles:put name="onclick" value="window.close();"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </c:otherwise>
        </c:choose>

    </tiles:put>

    <tiles:put name="filter" type="string">
        <tiles:insert definition="filterTextField" flush="false">
            <tiles:put name="label"  value="label.title"/>
            <tiles:put name="bundle" value="dictionariesBundle"/>
            <tiles:put name="name"   value="name"/>
        </tiles:insert>
        <tiles:insert definition="filterTextField" flush="false">
            <tiles:put name="label"  value="label.UID"/>
            <tiles:put name="bundle" value="dictionariesBundle"/>
            <tiles:put name="name"   value="uid"/>
        </tiles:insert>
        <tiles:insert definition="filterTextField" flush="false">
            <tiles:put name="label" value="label.URL"/>
            <tiles:put name="bundle" value="dictionariesBundle"/>
            <tiles:put name="name" value="url"/>
        </tiles:insert>
    </tiles:put>

    <tiles:put name="data" type="string">

    <script type="text/javascript">

            function sendExternalSystemData(event)
            {
                var ids = document.getElementsByName("selectedIds");
                preventDefault(event);
                for (var i = 0; i < ids.length; i++)
                {
                    if (ids.item(i).checked)
                    {
                        var r = ids.item(i).parentNode.parentNode;
                        var a = new Array(10);

                        a['externalSystemId'] = ids.item(i).value;
                        if (r.cells[3].innerHTML.indexOf('&nbsp;') == -1)
                            a['externalSystemName'] = trim(r.cells[3].innerHTML);
                        else a['externalSystemName'] = '';

                        window.opener.setExternalSystemInfo(a);
                        window.close();
                        return;
                    }
                }
                alert("Выберите офис.");
            }
    </script>

    <tiles:insert definition="tableTemplate" flush="false">
        <tiles:put name="id" value="ExternalSystemsListTable"/>
        <tiles:put name="buttons">
        <c:choose>
            <c:when test="${standalone}">
                <tiles:insert definition="clientButton" flush="false" operation="EditExternalSystemOperation">
                    <tiles:put name="commandTextKey" value="button.edit.externalSystem"/>
                    <tiles:put name="commandHelpKey" value="button.edit.externalSystem.help"/>
                    <tiles:put name="bundle"         value="dictionariesBundle"/>
                    <tiles:put name="onclick"        value="doEdit();"/>
                </tiles:insert>

                <tiles:insert definition="commandButton" flush="false" operation="EditExternalSystemOperation">
                    <tiles:put name="commandKey"     value="button.remove"/>
                    <tiles:put name="commandHelpKey" value="button.remove.externalSystem.help"/>
                    <tiles:put name="bundle"         value="dictionariesBundle"/>
                    <tiles:put name="validationFunction">
                        function()
                        {
                            checkIfOneItem("selectedIds");
                            return checkSelection('selectedIds', 'Выберите систему!');
                        }
                    </tiles:put>
                    <tiles:put name="confirmText"    value="Удалить выбранные системы?"/>
                </tiles:insert>
            </c:when>
            <c:otherwise>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.choose"/>
                    <tiles:put name="commandHelpKey" value="button.choose"/>
                    <tiles:put name="bundle"         value="dictionariesBundle"/>
                    <tiles:put name="onclick"        value="sendExternalSystemData(event);"/>
                </tiles:insert>
            </c:otherwise>
        </c:choose>
        </tiles:put>
        <tiles:put name="grid">
            <sl:collection id="listElement" model="list" property="data">
                <sl:collectionParam id="selectName" value="selectedIds"/>
                <sl:collectionParam id="selectType" value="checkbox" condition="${standalone}"/>
                <sl:collectionParam id="selectType" value="radio"    condition="${not standalone}"/>
                <sl:collectionParam id="selectProperty" value="id"/>

                <sl:collectionParam id="onRowClick" value="selectRow(this, 'selectedIds');" condition="${not standalone}"/>
                <sl:collectionParam id="onRowDblClick" value="sendExternalSystemData();" condition="${not standalone}"/>

                <sl:collectionItem title="Идентификатор" name="listElement" property="UID">
                        <sl:collectionItemParam id="action"
                                                value="dictionaries/editExternalSystem?id=${listElement.id}"
                                                condition="${standalone}"
                                            />
                </sl:collectionItem>
                <sl:collectionItem title="Наименование" name="listElement" property="name"/>
                <sl:collectionItem title="Адрес шлюза" name="listElement" property="URL"/>
            </sl:collection>
        </tiles:put>

        <tiles:put name="isEmpty" value="${empty ExternalSystemListForm.data}"/>
	    <tiles:put name="emptyMessage" value="Не найдено ни одной системы,<br>соответствующей заданному фильтру!"/>
    </tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>