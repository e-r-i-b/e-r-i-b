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

<html:form action="/dictionaries/routing/adapter/list" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="${layoutDefinition}">
    <tiles:put name="mainmenu" value="ExternalSystems"/>
    <tiles:put name="submenu" value="Adapter" type="string"/>
    <tiles:put name="pageTitle" type="string"><bean:message bundle="adapterBundle" key="listpage.title"/></tiles:put>
    <tiles:put name="menu" type="string">
        <c:choose>
            <c:when test="${standalone}">
                <script type="text/javascript">
                    <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/dictionaries/routing/adapter/edit')}"/>
                    function doEdit()
                    {
                        checkIfOneItem("selectedIds");
                        if (!checkSelection("selectedIds", 'Укажите одну запись') || (!checkOneSelection("selectedIds", 'Укажите только одну запись')))
                            return;
                        var id = getRadioValue(document.getElementsByName("selectedIds"));
                        window.location = '${url}?id=' + id;
                    }
                </script>

                <tiles:insert definition="clientButton" flush="false" operation="EditAdapterOperation">
                    <tiles:put name="commandTextKey" value="button.add"/>
                    <tiles:put name="commandHelpKey" value="button.add.help"/>
                    <tiles:put name="bundle"         value="adapterBundle"/>
                    <tiles:put name="action"         value="/dictionaries/routing/adapter/edit.do"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>

            </c:when>
            <c:otherwise>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel"/>
                    <tiles:put name="bundle" value="adapterBundle"/>
                    <tiles:put name="onclick" value="window.close();"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </c:otherwise>
        </c:choose>

    </tiles:put>

    <tiles:put name="filter" type="string">
        <tiles:insert definition="filterTextField" flush="false">
            <tiles:put name="label"  value="label.name"/>
            <tiles:put name="bundle" value="adapterBundle"/>
            <tiles:put name="name"   value="name"/>
            <tiles:put name="maxlength" value="64"/>
        </tiles:insert>
        <tiles:insert definition="filterTextField" flush="false">
            <tiles:put name="label"  value="label.UUID"/>
            <tiles:put name="bundle" value="adapterBundle"/>
            <tiles:put name="name"   value="UUID"/>
            <tiles:put name="maxlength" value="128"/>
        </tiles:insert>
    </tiles:put>

    <tiles:put name="data" type="string">

        <script type="text/javascript">

                function sendAdapterData(event)
                {
                    if (!checkOneSelection("selectedIds", "Выберите один адаптер!"))
                       return;
                    var ids = document.getElementsByName("selectedIds");
                    for (var i = 0; i < ids.length; i++)
                    {
                        if (ids.item(i).checked)
                        {
                            var row = ids.item(i).parentNode.parentNode;
                            var res = new Array();
                            res['id']   = ids.item(i).value;
                            res['name'] = trim(row.cells[1].innerHTML);
                            res['UUID'] = trim(row.cells[2].innerHTML);
                            res['extOffices'] = trim(row.cells[3].innerHTML);
                            window.opener.setAdapterInfo(res);
                            window.close();
                            return;
                        }
                    }
                    alert("Выберите адаптер!");
                 }
                function doRemove()
                {
                    checkIfOneItem("selectedIds");
                    if (checkSelection('selectedIds', 'Укажите хотя бы одну запись'))
                    {
                        return confirm("Вы действительно хотите удалить " + getSelectedQnt('selectedIds') + " записей адаптеров из справочника?");
                    }
                    return false;
                }
        </script>

        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="AdapterListTable"/>
            <tiles:put name="buttons">
            <c:choose>
                <c:when test="${standalone}">
                    <tiles:insert definition="clientButton" flush="false" operation="EditAdapterOperation">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit.help"/>
                        <tiles:put name="bundle"         value="adapterBundle"/>
                        <tiles:put name="onclick"        value="doEdit();"/>
                    </tiles:insert>

                    <tiles:insert definition="commandButton" flush="false" operation="RemoveAdapterOperation">
                        <tiles:put name="commandKey"     value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove.help"/>
                        <tiles:put name="bundle"         value="adapterBundle"/>
                        <tiles:put name="validationFunction">
                            doRemove()
                        </tiles:put>
                    </tiles:insert>
                </c:when>
                <c:otherwise>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.send.paymentService"/>
                        <tiles:put name="commandHelpKey" value="button.send.paymentService.help"/>
                        <tiles:put name="bundle"         value="adapterBundle"/>
                        <tiles:put name="onclick"        value="sendAdapterData(event);"/>
                    </tiles:insert>
                </c:otherwise>
            </c:choose>
            </tiles:put>
            <tiles:put name="grid">
                <sl:collection id="listElement" model="list" property="data" selectBean="adapter">
                    <c:set var="nodeType" value="${listElement[0]}"/>
                    <c:set var="adapter" value="${listElement[1]}"/>

                    <sl:collectionParam id="selectName"     value="selectedIds"/>
                    <sl:collectionParam id="selectType"     value="checkbox" condition="${standalone}"/>
                    <sl:collectionParam id="selectType"     value="radio"    condition="${not standalone}"/>
                    <sl:collectionParam id="selectProperty" value="id"/>

                    <sl:collectionParam id="onRowClick"     value="selectRow(this, 'selectedIds');" condition="${not standalone}"/>
                    <sl:collectionParam id="onRowDblClick"  value="sendAdapterData();"              condition="${not standalone}"/>

                    <sl:collectionItem title="Наименование" value="${adapter.name}"/>
                    <sl:collectionItem title="Идентификатор" value="${adapter.UUID}"/>
                    <sl:collectionItem hidden="true" value="${not empty nodeType ? phiz:isSupportExternalOffice(nodeType) : ''}"/>
                </sl:collection>
            </tiles:put>
            <tiles:put name="isEmpty" value="${empty ListAdaptersForm.data}"/>
            <tiles:put name="emptyMessage"><bean:message bundle="adapterBundle" key="empty.message"/></tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>