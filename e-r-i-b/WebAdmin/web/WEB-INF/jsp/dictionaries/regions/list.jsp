<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="standalone" value="${empty param['action']}"/>
<c:choose>
	<c:when test="${not standalone}">
		<c:set var="layoutDefinition" value="dictionary"/>
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
                                alert("������� ���� ������.");
                                return;
                            }

                            <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/dictionaries/regions/edit')}"/>
                            var parentId = getRadioValue(document.getElementsByName("selectedIds"));
                            window.location = '${url}?parentId=' + parentId;
                        }
                        function doAdd()
                        {
                            if (!checkOneSelectionOrNothing('selectedIds', '�������� �� ����� ������ �������.'))
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
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" service="RegionDictionaryManagement" operation="SynchronizeRegionOperation">
                        <tiles:put name="commandKey"     value="button.synchronize"/>
                        <tiles:put name="commandHelpKey" value="button.synchronize.help"/>
                        <tiles:put name="bundle"         value="regionsBundle"/>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:when>
                <c:otherwise>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"         value="regionsBundle"/>
                        <tiles:put name="onclick"        value="javascript:window.close()"/>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:otherwise>
	        </c:choose>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id"    value="RegionsList"/>
                <tiles:put name="buttons">
                    <c:choose>
                        <c:when test="${standalone}">
                            <script type="text/javascript">
                                function doEdit()
                                {
                                    checkIfOneItem("selectedIds");
                                    if(!checkSelection("selectedIds", "������� ���� ������."))
                                        return;

                                    if (!checkOneSelection("selectedIds", '������� ������ ���� ������.'))
                                        return;

                                    <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/dictionaries/regions/edit')}"/>
                                    var id = getRadioValue(document.getElementsByName("selectedIds"));
                                    window.location = '${url}?id='+ id;
                                }
                                function doRemove()
                                {
                                    checkIfOneItem("selectedIds");
                                    if(checkSelection("selectedIds", "������� ���� �� ���� ������."))
                                    {
                                        var ids = document.getElementsByName("selectedIds");
                                        checkIfOneItem("selectedIds");
                                        var count = getSelectedQnt('selectedIds');
                                        var message = "�� ������������� ������ ������� ";

                                        count == 1 ? message = message + " ������ �������" : message = message + count + " ������(��) ��������";

                                        if (confirm(message + " �� �����������?"))
                                            return true;
                                    }
                                    return false;
                                }
                            </script>
                            <tiles:insert definition="clientButton" flush="false" service="RegionDictionaryManagement" operation="EditRegionOperation">
                                <tiles:put name="commandTextKey" value="button.edit"/>
                                <tiles:put name="commandHelpKey" value="button.edit.help"/>
                                <tiles:put name="bundle"         value="regionsBundle"/>
                                <tiles:put name="onclick"        value="javascript:doEdit();"/>
                            </tiles:insert>
                            <tiles:insert definition="commandButton" flush="false" service="RegionDictionaryManagement" operation="RemoveRegionOperation">
                                <tiles:put name="commandKey"     value="button.remove"/>
                                <tiles:put name="commandHelpKey" value="button.remove.help"/>
                                <tiles:put name="bundle"         value="regionsBundle"/>
                                <tiles:put name="validationFunction">
                                    doRemove();
                                </tiles:put>
                            </tiles:insert>
                        </c:when>
                        <c:otherwise>
                            <script type="text/javascript">
                                function sendRegionsData()
                                {
                                    var results = new Array();
                                    var c = 0;
                                    var ids = document.getElementsByName("selectedIds");
                                    for (var i = 0; i < ids.length; i++)
                                    {
                                        if (ids.item(i).checked)
                                        {
                                            var checked = 1;
                                            results[c] = ids.item(i).value;
                                            c++;
                                        }
                                    }
                                    if (!checked)
                                    {
                                        alert("�������� ������ �������!");
                                    }
                                    else
                                    {
                                        window.opener.setRegionInfo(results);
                                        window.close();
                                        return;
                                    }
                                }
                                //������� ��� �������.
                                function sendRegionData()
                                {
                                    checkIfOneItem("selectedIds");
                                    if (!checkOneSelection("selectedIds", "�������� ���� ������ �������."))
                                       return;

                                    var ids = document.getElementsByName("selectedIds");
                                    for (var i = 0; i < ids.length; i++)
                                    {
                                        if (ids.item(i).checked)
                                        {
                                            var res = new Array();
                                            var elemName = ensureElementByName("name"+ids.item(i).value);
                                            res['name'] = trim(elemName.value);
                                            res['id'] = ids.item(i).value;
                                            window.opener.setRegionInfo(res);
                                            window.close();
                                            return;
                                        }
                                    }
                                    alert("�������� ������ �������!");
                                }
                            </script>
                            <c:choose>
                                <c:when test="${param['action'] == 'getRegionsInfoFilter'}">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.choose"/>
                                        <tiles:put name="commandHelpKey" value="button.choose.help"/>
                                        <tiles:put name="bundle"         value="regionsBundle"/>
                                        <tiles:put name="onclick">sendRegionData()</tiles:put>
                                    </tiles:insert>
                                </c:when>
                                <c:otherwise>
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.choose"/>
                                        <tiles:put name="commandHelpKey" value="button.choose.help"/>
                                        <tiles:put name="bundle"         value="regionsBundle"/>
                                        <tiles:put name="onclick">sendRegionsData()</tiles:put>
                                    </tiles:insert>
                                </c:otherwise>
                            </c:choose>

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