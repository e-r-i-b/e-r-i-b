<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="standalone" value="${empty param['action']}"/>

<c:choose>
    <c:when test="${standalone}">
         <c:set var="layoutDefinition" value="routingList"/>
    </c:when>
    <c:otherwise>
        <c:set var="layoutDefinition" value="dictionary"/>
    </c:otherwise>
</c:choose>

<html:form action="/dictionaries/routing/node/list" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="${layoutDefinition}">
    <tiles:put name="mainmenu" value="ExternalSystems"/>
    <tiles:put name="submenu" value="Node" type="string"/>
    <tiles:put name="pageTitle" type="string"><bean:message bundle="nodeBundle" key="listpage.title"/></tiles:put>
    <tiles:put name="menu" type="string">
        <c:choose>
            <c:when test="${standalone}">
                <script type="text/javascript">
                    <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/dictionaries/routing/node/edit')}"/>
                    function doEdit()
                    {
                        checkIfOneItem("selectedIds");
                        if (!checkSelection("selectedIds", 'Укажите одну запись') || (!checkOneSelection("selectedIds", 'Укажите только одну запись')))
                            return;
                        var id = getRadioValue(document.getElementsByName("selectedIds"));
                        window.location = '${url}?id=' + id;
                    }
                </script>

                <tiles:insert definition="clientButton" flush="false" operation="EditNodeOperation">
                    <tiles:put name="commandTextKey" value="button.add"/>
                    <tiles:put name="commandHelpKey" value="button.add.help"/>
                    <tiles:put name="bundle"         value="nodeBundle"/>
                    <tiles:put name="action"         value="/dictionaries/routing/node/edit.do"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>

            </c:when>
            <c:otherwise>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel"/>
                    <tiles:put name="bundle" value="nodeBundle"/>
                    <tiles:put name="onclick" value="window.close();"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </c:otherwise>
        </c:choose>

    </tiles:put>

    <tiles:put name="filter" type="string">
        <tiles:insert definition="filterTextField" flush="false">
            <tiles:put name="label"  value="label.name"/>
            <tiles:put name="bundle" value="nodeBundle"/>
            <tiles:put name="name"   value="name"/>
            <tiles:put name="maxlength" value="128"/>
            <tiles:put name="size" value="50"/>
        </tiles:insert>
        <tiles:insert definition="filterTextField" flush="false">
            <tiles:put name="label"  value="label.URL"/>
            <tiles:put name="bundle" value="nodeBundle"/>
            <tiles:put name="name"   value="URL"/>
            <tiles:put name="maxlength" value="128"/>
            <tiles:put name="size" value="80"/>
        </tiles:insert>
    </tiles:put>

    <tiles:put name="data" type="string">

        <script type="text/javascript">

                function sendNodeData(event)
                {
                    checkIfOneItem("selectedIds");
                    if (!checkOneSelection("selectedIds", "Выберите один узел!"))
                       return;
                    var ids = document.getElementsByName("selectedIds");
                    for (var i = 0; i < ids.length; i++)
                    {
                        if (ids.item(i).checked)
                        {
                            var res = new Array(2);
                            res['id'] = ids.item(i).value;
                            window.opener.setNodeInfo(res);
                            window.close();
                            return;
                        }
                    }
                    alert("Выберите узел!");
                }
                function doRemove()
                {
                    checkIfOneItem("selectedIds");
                    if (checkSelection('selectedIds', 'Укажите хотя бы одну запись'))
                    {
                        return confirm("Вы действительно хотите удалить " + getSelectedQnt('selectedIds') + " записей узлов из справочника?");
                    }
                    return false;
                }
        </script>

        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="NodesListTable"/>
            <tiles:put name="buttons">
            <c:choose>
                <c:when test="${standalone}">
                    <tiles:insert definition="clientButton" flush="false" operation="EditNodeOperation">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit.help"/>
                        <tiles:put name="bundle"         value="nodeBundle"/>
                        <tiles:put name="onclick"        value="doEdit();"/>
                    </tiles:insert>

                    <tiles:insert definition="commandButton" flush="false" operation="RemoveNodeOperation">
                        <tiles:put name="commandKey"     value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove.help"/>
                        <tiles:put name="bundle"         value="nodeBundle"/>
                        <tiles:put name="validationFunction">
                            doRemove()
                        </tiles:put>
                    </tiles:insert>
                </c:when>
                <c:otherwise>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.send"/>
                        <tiles:put name="commandHelpKey" value="button.send.help"/>
                        <tiles:put name="bundle"         value="nodeBundle"/>
                        <tiles:put name="onclick"        value="sendNodeData(event);"/>
                    </tiles:insert>
                </c:otherwise>
            </c:choose>
            </tiles:put>
            <tiles:put name="grid">
                <sl:collection id="listElement" model="list" property="data">
                    <sl:collectionParam id="selectName"     value="selectedIds"/>
                    <sl:collectionParam id="selectType"     value="checkbox" condition="${standalone}"/>
                    <sl:collectionParam id="selectType"     value="radio"    condition="${not standalone}"/>
                    <sl:collectionParam id="selectProperty" value="id"/>

                    <sl:collectionParam id="onRowClick"     value="selectRow(this, 'selectedIds');" condition="${not standalone}"/>
                    <sl:collectionParam id="onRowDblClick"  value="sendNodeData();"                 condition="${not standalone}"/>

                    <sl:collectionItem title="Наименование" property="name"/>
                    <sl:collectionItem title="Адрес"        property="URL"/>
                </sl:collection>
            </tiles:put>

            <tiles:put name="isEmpty" value="${empty ListNodesForm.data}"/>
            <tiles:put name="emptyMessage"><bean:message bundle="nodeBundle" key="empty.message"/></tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>