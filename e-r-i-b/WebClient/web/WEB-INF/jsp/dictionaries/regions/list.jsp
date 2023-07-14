<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="standalone" value="${empty param['action']}"/>
<c:choose>
	<c:when test="${not standalone}">
		<c:set var="layoutDefinition" value="dictionaryBundle"/>
	</c:when>
	<c:otherwise>
		<c:set var="layoutDefinition" value="providersMain"/>
	</c:otherwise>
</c:choose>

<html:form action="/private/dictionary/regions/list" onsubmit="return setEmptyAction(event);">

    <c:set var="frm"     value="${ListRegionsForm}"/>
    <c:set var="regions" value="${frm.data}"/>

    <tiles:insert definition="${layoutDefinition}">
        <tiles:put name="needSave" type="string" value="false"/>
        <tiles:put name="submenu" type="string" value="Regions"/>

        <tiles:put name="pageTitle" type="string">
            <bean:message key="dictionary.title" bundle="regionsBundle"/>
        </tiles:put>

        <tiles:put name="menu" type="string">
            <c:choose>
	            <c:when test="${standalone}">
                    <script type="text/javascript">
                        function doEdit()
                        {
                            checkIfOneItem("selectedIds");
                            if(getSelectedQnt("selectedIds") > 1)
                            {
                                alert("Укажите одну запись.");
                                return;
                            }

                            <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/dictionaries/regions/edit')}"/>
                            var parentId = getRadioValue(document.getElementsByName("selectedIds"));
                            window.location = '${url}?parentId=' + parentId;
                        }
                        function doAdd()
                        {
                            if (!checkOneSelectionOrNothing('selectedIds', 'Выберите не более одного региона.'))
                                return;
                            var id = getRadioValue(document.getElementsByName("selectedIds"));
                            window.location = '${url}' + "?parentId=" + encodeURIComponent(id);
                        }
                    </script>
                    <tiles:insert definition="clientButton" flush="false" service="RegionDictionaryManagement" operation="EditRegionOperation">
                        <tiles:put name="commandTextKey" value="button.add"/>
                        <tiles:put name="commandHelpKey" value="button.add.help"/>
                        <tiles:put name="bundle"         value="regionsBundle"/>
                        <tiles:put name="onclick"        value="javascript:doAdd();"/>
                    </tiles:insert>
                </c:when>
                <c:otherwise>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"         value="regionsBundle"/>
                        <tiles:put name="onclick"        value="javascript:window.close()"/>
                    </tiles:insert>
                </c:otherwise>
	        </c:choose>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id"    value="RegionsList"/>
                <tiles:put name="text"><bean:message bundle="regionsBundle" key="listpage.title"/></tiles:put>
		        <tiles:put name="image" value=""/>
                <tiles:put name="buttons">
                    <c:choose>
                        <c:when test="${standalone}">
                            <script type="text/javascript">
                                function doEdit()
                                {
                                    checkIfOneItem("selectedIds");
                                    if(!checkSelection("selectedIds", "Укажите одну запись."))
                                        return;

                                    if (!checkOneSelection("selectedIds", 'Укажите только одну запись.'))
                                        return;

                                    <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/dictionaries/regions/edit')}"/>
                                    var id = getRadioValue(document.getElementsByName("selectedIds"));
                                    window.location = '${url}?id='+ id;
                                }
                                function doRemove()
                                {
                                    checkIfOneItem("selectedIds");
                                    if(checkSelection("selectedIds", "Укажите хотя бы одну запись."))
                                    {
                                        var ids = document.getElementsByName("selectedIds");
                                        var count = getSelectedQnt('selectedIds');
                                        var message = "Вы действительно хотите удалить ";

                                        count == 1 ? message = message + " запись региона" : message = message + count + " записи(ей) регионов";

                                        if (confirm(message + " из справочника?"))
                                            return true;
                                    }
                                    return false;
                                }
                            </script>
                            <tiles:insert definition="clientButton" flush="false" service="RegionDictionaryManagement" operation="EditRegionOperation">
                                <tiles:put name="commandTextKey" value="button.edit"/>
                                <tiles:put name="commandHelpKey" value="button.edit.help"/>
                                <tiles:put name="bundle"         value="regionsBundle"/>
                                <tiles:put name="image"          value="iconSm_edit.gif"/>
                                <tiles:put name="onclick"        value="javascript:doEdit();"/>
                            </tiles:insert>
                            <tiles:insert definition="commandButton" flush="false" service="RegionDictionaryManagement" operation="RemoveRegionOperation">
                                <tiles:put name="commandKey"     value="button.remove"/>
                                <tiles:put name="commandHelpKey" value="button.remove.help"/>
                                <tiles:put name="bundle"         value="regionsBundle"/>
                                <tiles:put name="image"          value="iconSm_delete.gif"/>
                                <tiles:put name="validationFunction">
                                    doRemove();
                                </tiles:put>
                            </tiles:insert>
                        </c:when>
                        <c:otherwise>
                            <script type="text/javascript">
                                function sendRegionData()
                                {
                                    checkIfOneItem("selectedIds");
                                    if (!checkOneSelection("selectedIds", "Выберите одну запись региона."))
                                       return;

                                    var ids = document.getElementsByName("selectedIds");
                                    for (var i = 0; i < ids.length; i++)
                                    {
                                        if (ids.item(i).checked)
                                        {
                                            var res = new Array();
                                            var elemName = ensureElementByName("name"+ids.item(i).value);
                                            var parentName = document.getElementById("parent"+ids.item(i).value);
                                            res['name'] = trim(elemName.value);
                                            res['id'] = ids.item(i).value;
                                            if (parentName == null)
                                                res['parent'] = "&nbsp;";
                                            else
                                                res['parent'] = parentName.value;
                                            window.opener.setRegionInfo(res);
                                            window.close();
                                            return;
                                        }
                                    }
                                    alert("Выберите запись региона!");
                                }
                            </script>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.choose"/>
                                <tiles:put name="commandHelpKey" value="button.choose.help"/>
                                <tiles:put name="bundle"         value="regionsBundle"/>
                                <tiles:put name="image"          value="iconSm_select.gif"/>
                                <tiles:put name="onclick">sendRegionData()</tiles:put>
                            </tiles:insert>
                        </c:otherwise>
                    </c:choose>
                </tiles:put>

                <tiles:put name="data">
                    <%@ include file="tree.jsp"%>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty regions}"/>
		        <tiles:put name="emptyMessage"><bean:message bundle="regionsBundle" key="empty.message"/></tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>