<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="standalone" value="${empty param['action']}"/>
<c:choose>
	<c:when test="${not standalone}">
		<c:set var="layoutDefinition" value="dictionary"/>
	</c:when>
	<c:otherwise>
		<c:set var="layoutDefinition" value="departmentsMain"/>
	</c:otherwise>
</c:choose>

<html:form action="/departments/list" onsubmit="return setEmptyAction(event);">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="${layoutDefinition}">
<tiles:put name="submenu" type="string" value="List"/>
<tiles:put name="pageTitle" type="string">Список подразделений банка</tiles:put>

<tiles:put name="menu" type="string">
	<c:choose>
	    <c:when test="${standalone}">
			<tiles:insert definition="clientButton" flush="false" operation="EditDepartmentOperation">
				<tiles:put name="commandTextKey" value="button.add.branch"/>
				<tiles:put name="commandHelpKey" value="button.add.branch.help"/>
				<tiles:put name="bundle" value="departmentsBundle"/>
                <tiles:put name="onclick" value="doAdd();"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>

			<script type="text/javascript">
				var addUrl = "${phiz:calculateActionURL(pageContext,'/departments/edit')}";
				function doEdit()
				{
					checkIfOneItem("selectedIds");
                    if (!checkOneSelection("selectedIds", 'Выберите одно подразделение'))
						return;
					var synchKey = getRadioValue(document.getElementsByName("selectedIds"));
					window.location = addUrl + "?id=" + encodeURIComponent(synchKey);
				}

                function doAdd()
                {
                    if (!checkOneSelectionOrNothing('selectedIds', 'Выберите не более одного подразделения'))
                        return;
					var synchKey = getRadioValue(document.getElementsByName("selectedIds"));
                    var additionalParams = '';
                    if (synchKey != null)
                        additionalParams = "?parentId=" + encodeURIComponent(synchKey);
					window.location = addUrl + additionalParams;
                }

                function doReplicate()
                {
                    var paramValue = "";
                    $("input[name='selectedDeps']:checked").each(function(){
                        paramValue += (paramValue != "" ? "," : "") + this.value;
                    });

                    goTo("${phiz:calculateActionURL(pageContext, '/departments/replication')}?ids=" + paramValue);
                }
			</script>
		</c:when>
		<c:otherwise>
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.cancel"/>
				<tiles:put name="commandHelpKey" value="button.cancel.help"/>
				<tiles:put name="bundle"         value="departmentsBundle"/>
				<tiles:put name="onclick"        value="javascript:window.close()"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</c:otherwise>
	</c:choose>

</tiles:put>

<tiles:put name="filter" type="string">
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.name"/>
		<tiles:put name="bundle" value="passwordcardsBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="name"/>
        <tiles:put name="size" value="100"/>
        <tiles:put name="maxlength" value="256"/>
	</tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="DepartmentsList"/>
		<tiles:put name="buttons">
            <c:choose>
                <c:when test="${standalone}">
                    <tiles:insert definition="clientButton" flush="false" group="DepartmentsE,DepartmentsA">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit.department.help"/>
                        <tiles:put name="bundle" value="departmentsBundle"/>
                        <tiles:put name="onclick" value="doEdit();"/>
                    </tiles:insert>

                    <tiles:insert definition="commandButton" flush="false" operation="RemoveDepartmentOperation">
                        <tiles:put name="commandKey" value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove.department.help"/>
                        <tiles:put name="bundle" value="departmentsBundle"/>
                        <tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection('selectedIds', 'Выберите подразделение');
                            }
                        </tiles:put>
                        <tiles:put name="confirmText" value="Удалить выбранные подразделения?"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false" operation="ReplicateDepartmentsBackgroundOperation">
                        <tiles:put name="commandTextKey" value="button.replice"/>
                        <tiles:put name="commandHelpKey" value="button.replice.help"/>
                        <tiles:put name="bundle" value="departmentsBundle"/>
                        <tiles:put name="onclick" value="doReplicate();"/>
                    </tiles:insert>
                </c:when>
            <c:otherwise>
                <c:set var="isSupportMultipleChoice" value="${param['multipleChoice'] == 'true'}"/>
                <script type="text/javascript">
					function sendDepartmentData()
					{
                        checkIfOneItem("selectedIds");
                        <c:if test="${!isSupportMultipleChoice}">
                            if (!checkOneSelection("selectedIds", "Выберите одно подразделение!"))
                                return;
                        </c:if>

                        var ids = document.getElementsByName("selectedIds");
                        var checkedCount = 0;
                        for (var i = 0; i < ids.length; i++)
                        {
                            if (ids.item(i).checked)
                            {
                                checkedCount++;
                                var res = new Object();
                                var departmentId = ids.item(i).value;
                                var elemName = ensureElementByName("name"+departmentId);
                                res['name'] = trim(elemName.value);
                                res['id'] = departmentId;
                                res['parent'] = findParent(res);
                                res['tb'] = trim(ensureElementByName("tb"+departmentId).value);
                                res['osb'] = trim(ensureElementByName("osb"+departmentId).value);
                                res['vsp'] = trim(ensureElementByName("vsp"+departmentId).value);
                                if (getSelectedQnt("selectedParents") > 0)
                                    res['asParent'] = "true";

                                if(typeof(window.opener.____checkDepartmentsInfo) == "function")
                                {
                                    var error = window.opener.____checkDepartmentsInfo(res);
                                    if(error != null)
                                    {
                                        alert(error);
                                        return;
                                    }
                                }

                                window.opener.setDepartmentInfo(res);
                            }
                        }

                        if(checkedCount == 0)
                        {
                            alert("Выберите подразделение!");
                        }
                        else
                        {
                            window.close();
                        }
					}

                    function findParent(res)
                    {
                        var parentCell = $("#input" + res.id).closest("table").closest("td");
                        if(parentCell.length == 0)
                            return null;

                        var parentIdElements = $(parentCell).children("input[name='selectedIds']");
                        if(parentIdElements.length != 1)
                            return null;

                        var parent = new Object();
                        parent.id = parentIdElements.val();
                        parent.name = $(parentCell).children("input[name='name" + parent.id + "']").val();
                        parent.parent = findParent(parent);

                        return parent;
                    }

				</script>
				<tiles:insert definition="clientButton" flush="false">
					<tiles:put name="commandTextKey" value="button.find.department"/>
					<tiles:put name="commandHelpKey" value="button.find.department.help"/>
					<tiles:put name="bundle" value="departmentsBundle"/>
					<tiles:put name="onclick">sendDepartmentData()</tiles:put>
				</tiles:insert>
            </c:otherwise>
			</c:choose>
		</tiles:put>
        <tiles:put name="data">
            <%@ include file="tree.jsp"%>
        </tiles:put>
        <tiles:put name="isEmpty" value="${empty form.children}"/>
		<tiles:put name="emptyMessage" value="Не найдено ни одного подразделения, <br/>cоответствующего заданному фильтру!"/>
	</tiles:insert>

</tiles:put>
</tiles:insert>

</html:form>
