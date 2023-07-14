<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<c:set var="standalone" value="${empty param['action']}"/>
<c:set var="getManyProviders" value="${param['action'] == 'getProviders'}"/>
<c:choose>
	<c:when test="${not standalone}">
		<c:set var="layoutDefinition" value="dictionary"/>
	</c:when>
	<c:otherwise>
		<c:set var="layoutDefinition" value="recipientDictionariesEdit"/>
	</c:otherwise>
</c:choose>
<html:form action="/dictionaries/provider/fields/list" onsubmit="return setEmptyAction(event);">

    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="isEditable" value="${frm.editable}"/>
    <tiles:insert definition="${layoutDefinition}">
        <c:if test="${standalone}">
            <tiles:put name="submenu" value="Provider/Fields" type="string"/>
            <tiles:put name="needSave" value="false"/>
        </c:if>
        <tiles:put name="pageTitle" type="string"><bean:message bundle="providerBundle" key="label.fields"/></tiles:put>
        <tiles:put name="menu" type="string">
            <c:if test="${not standalone}">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    <tiles:put name="bundle" value="mailBundle"/>
                    <tiles:put name="image" value=""/>
                    <tiles:put name="onclick" value="window.close()"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </c:if>
            <c:if test="${isEditable && standalone}">
                <c:if test='${frm.providerState eq "NOT_ACTIVE" || frm.providerState eq "MIGRATION"}'>
                    <tiles:insert definition="commandButton" flush="false" operation="ActivateOrLockServiceProviderOperation">
                        <tiles:put name="commandKey" value="button.activate"/>
                        <tiles:put name="commandHelpKey" value="button.activate.help"/>
                        <tiles:put name="bundle" value="providerBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${frm.providerState eq 'ACTIVE'}">
                    <tiles:insert definition="commandButton" flush="false" operation="ActivateOrLockServiceProviderOperation">
                        <tiles:put name="commandKey" value="button.lock"/>
                        <tiles:put name="commandHelpKey" value="button.lock.help"/>
                        <tiles:put name="bundle" value="providerBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="validationFunction">checkProviderType('${frm.internetShop}');</tiles:put>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:if>
                <tiles:insert definition="clientButton" flush="false" operation="EditServiceProviderFieldOperation">
                    <tiles:put name="commandTextKey" value="button.add"/>
                    <tiles:put name="commandHelpKey" value="button.add.help"/>
                    <tiles:put name="bundle" value="providerBundle"/>
                    <tiles:put name="action" value="/dictionaries/provider/fields/edit.do?id=${frm.id}"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </c:if>
        </tiles:put>

        <tiles:put name="data" type="string">
             <script type="text/javascript">
                    function sendProviderFieldData(event)
                    {
                        var manySelection = <c:out value="${getManyProviders}"/>;

                        checkIfOneItem("selectedIds");
                        if(manySelection)
                        {
                            if (!checkSelection("selectedIds", 'Укажите записи'))
                                return;
                        }
                        else
                        {
                            if (!checkSelection("selectedIds", 'Укажите одну запись') || (!checkOneSelection("selectedIds", 'Укажите только одну запись')))
                                return;
                        }
                        var ids = document.getElementsByName("selectedIds");
                        var res = new Array();
                        for (var i = 0; i < ids.length; i++)
                        {
                            if (ids.item(i).checked)
                            {
                                var r = ids.item(i).parentNode.parentNode;
                                var a = new Array();
                                a['id']                     = ids.item(i).value;
                                a['field']                  = trim(getElementTextContent(r.cells[2]));

                                if (!manySelection)
                                {
                                    var message = window.opener.setProviderFieldData(a);
                                    if(message != null)
                                    {
                                        alert(message);
                                        return;
                                    }
                                    window.close();
                                    return;
                                }
                                res.push(a);
                            }
                        }
                        var message = window.opener.setProviderFieldData(a);
                        if(message != null)
                        {
                            alert(message);
                            return;
                        }
                        window.close();
                        return;
                    }
            </script>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="ProviderFiledsTable"/>
                <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/providers.js"></script>
                <tiles:put name="buttons">
                    <c:if test="${isEditable && standalone}">
                        <script type="text/javascript">
                            <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/dictionaries/provider/fields/edit')}"/>
                            function notTop()
                            {
                                var ids = document.getElementsByName("selectedIds");
                                if (ids.item(0).checked)
                                {
                                    alert("Невозможно переместить поле выше!");
                                    return false;
                                }
                                return true;
                            }
                            function notBottom()
                            {
                                var ids = document.getElementsByName("selectedIds");
                                if (ids.item(ids.length - 1).checked)
                                {
                                    alert("Невозможно переместить поле ниже!");
                                    return false;
                                }
                                return true;
                            }
                        </script>
                        <tiles:insert definition="commandButton" flush="false" operation="EditServiceProviderFieldOperation">
                            <tiles:put name="commandKey" value="button.up"/>
                            <tiles:put name="commandHelpKey" value="button.up.help"/>
                            <tiles:put name="bundle" value="providerBundle"/>
                            <tiles:put name="validationFunction">
                                function()
                                {
                                    checkIfOneItem("selectedIds");
                                    return checkSelection("selectedIds", 'Укажите одну запись')
                                        && checkOneSelection("selectedIds", 'Укажите только одну запись')
                                        && notTop();
                                }
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="commandButton" flush="false" operation="EditServiceProviderFieldOperation">
                            <tiles:put name="commandKey" value="button.down"/>
                            <tiles:put name="commandHelpKey" value="button.down.help"/>
                            <tiles:put name="bundle" value="providerBundle"/>
                            <tiles:put name="validationFunction">
                                function()
                                {
                                    checkIfOneItem("selectedIds");
                                    return checkSelection("selectedIds", 'Укажите одну запись')
                                        && checkOneSelection("selectedIds", 'Укажите только одну запись')
                                        && notBottom();
                                }
                            </tiles:put>
                        </tiles:insert>
                        <script type="text/javascript">
                            <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/dictionaries/provider/fields/edit')}"/>
                            function doEdit()
                            {
                                checkIfOneItem("selectedIds");
                                if (!checkSelection("selectedIds", 'Укажите одну запись') || (!checkOneSelection("selectedIds", 'Укажите только одну запись')))
                                    return;
                                var id = getRadioValue(document.getElementsByName("selectedIds"));
                                window.location = '${url}?fieldId=' + id;
                            }
                        </script>
                        <tiles:insert definition="clientButton" flush="false" operation="EditServiceProviderFieldOperation">
                            <tiles:put name="commandTextKey" value="button.edit"/>
                            <tiles:put name="commandHelpKey" value="button.edit.help"/>
                            <tiles:put name="bundle" value="providerBundle"/>
                            <tiles:put name="onclick" value="doEdit();"/>
                        </tiles:insert>

                        <tiles:insert definition="commandButton" flush="false" operation="RemoveServiceProviderFieldOperation">
                            <tiles:put name="commandKey" value="button.remove"/>
                            <tiles:put name="commandHelpKey" value="button.remove.help"/>
                            <tiles:put name="bundle" value="providerBundle"/>
                            <tiles:put name="validationFunction">
                                function()
                                {
                                    checkIfOneItem("selectedIds");
                                    return checkSelection("selectedIds", 'Укажите одну запись')
                                        && checkOneSelection("selectedIds", 'Укажите только одну запись');
                                }
                            </tiles:put>
                        </tiles:insert>
                    </c:if>

                    <c:if test="${not standalone}">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.send"/>
                            <tiles:put name="commandHelpKey" value="button.send"/>
                            <tiles:put name="bundle"         value="providerBundle"/>
                            <tiles:put name="onclick"        value="sendProviderFieldData(event);"/>
                        </tiles:insert>
                    </c:if>

                </tiles:put>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="data">
                        <sl:collectionParam id="selectType" value="${not(standalone || getManyProviders)?'radio':'checkbox'}"/>
                        <sl:collectionParam id="selectName" value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>
                        <c:if test="${not(standalone || getManyProviders)}">
                            <sl:collectionParam id="onRowDblClick"  value="sendProviderFieldData(event);"/>
                        </c:if>
                        
                        <c:choose>
                            <c:when test="${not(standalone || getManyProviders)}">
                                <sl:collectionItem title="Наименование" property="name"/>
                            </c:when>
                            <c:otherwise>
                                <sl:collectionItem title="Наименование" property="name" action="/dictionaries/provider/fields/edit.do?fieldId=${listElement.id}"/>
                            </c:otherwise>
                        </c:choose>
                        <sl:collectionItem title="Код поля"     property="externalId"/>
                        <sl:collectionItem title="Тип поля"     property="type"/>
                    </sl:collection>
                </tiles:put>

                <tiles:put name="emptyMessage"><bean:message bundle="providerBundle" key="empty.fields.message"/></tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>