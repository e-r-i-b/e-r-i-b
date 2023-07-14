<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/deposits/list" onsubmit="return setEmptyAction(event);">

<tiles:insert definition="deposits">
    <tiles:put name="submenu" type="string" value="List"/>
	<tiles:put name="pageTitle" type="string">Список депозитных продуктов</tiles:put>

<!--меню-->
<tiles:put name="menu" type="string">

	<script type="text/javascript">
		var addUrl = "${phiz:calculateActionURL(pageContext,'/deposits/edit')}";

		function editDP()
		{
            checkIfOneItem("selectedIds");
			if(!checkSelection("selectedIds", "Выберите депозитный продукт"))
				return;

			var id = getRadioValue(document.getElementsByName("selectedIds"))

			window.location = addUrl + "?id=" + id;
		}

        function openAllowedDepartmentsListWindow()
        {
            <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/deposits/allowedDepartments/list')}"/>
            window.open(
                    "${url}",
                    'departments',
                    "resizable=1,menubar=0,toolbar=0,scrollbars=1");
        }
        function doReplic(result)
        {
            window.location = addUrl + "?departmentId=" + result['id'];
        }
	</script>

	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.add"/>
		<tiles:put name="commandHelpKey" value="button.add.help"/>
		<tiles:put name="bundle"         value="depositsBundle"/>
		<tiles:put name="onclick"        value="openDepartmentsDictionary(doReplic)"/>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>

	<tiles:insert definition ="commandButton" flush="false">
		<tiles:put name="commandKey"     value="button.replic"/>
		<tiles:put name="commandHelpKey" value="button.replic.help"/>
		<tiles:put name="bundle"         value="depositsBundle"/>
        <tiles:put name="validationFunction">
            openAllowedDepartmentsListWindow()
        </tiles:put>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>

</tiles:put>

<tiles:put name="filter" type="string">
    <tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label" value="label.department.name"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="data">
            <html:text  property="filter(departmentName)" readonly="true" style="width:200px"/>
            <html:hidden property="filter(departmentId)"/>
            <c:if test="${phiz:impliesOperation('ListDepartmentsOperation', 'DepartmentsManagement') or phiz:impliesOperation('ListDepartmentsOperation','DepartmentsViewing')}">
                <input type="button" class="buttWhite" style="height:18px;width:18px;"onclick="selectFilterDepartment();" value="..."/>
            </c:if>

            <script type="text/javascript">
               function setDepartmentInfo(result)
               {
                   setElement("filter(departmentId)",result['id']);
                   setElement("filter(departmentName)",result['name']);
               }

               function selectFilterDepartment()
               {
                   var params = "";
                   if (params.length > 0) params = "&" + params;
                   window.setDepartmentInfo = setDepartmentInfo;
                   window.open(
                           '${pageContext.request.contextPath}/deposits/filterDepartments/list.do?button=filter'+ params,
                           'Offices',
                           "resizable=1,menubar=0,toolbar=0,scrollbars=1");
               }
           </script>
        </tiles:put>
    </tiles:insert>
</tiles:put>

    <script type="text/javascript">
        function setSelectedDepartmentsInfo(departments)
            {
                setElement('field(selectedDeps)', departments);
                var button = new CommandButton("button.replic", "");
                button.click();
            }
    </script>

	<c:set var="form" value="${ListDepositProductsBankForm}"/>
	<!-- данные -->
	<tiles:put name="data" type="string">
        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="depositsList"/>
            <tiles:put name="text" value="Вклады банка"/>
            <html:hidden property="field(selectedDeps)" />
            <tiles:put name="buttons">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.edit"/>
                    <tiles:put name="commandHelpKey" value="button.edit.help"/>
                    <tiles:put name="bundle"         value="depositsBundle"/>
                    <tiles:put name="onclick"        value="editDP()"/>
                </tiles:insert>

                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"     value="button.remove"/>
                    <tiles:put name="commandHelpKey" value="button.remove.help"/>
                    <tiles:put name="bundle"         value="depositsBundle"/>
                    <tiles:put name="validationFunction">
                        checkSelection('selectedIds', 'Выберите депозитные продукты')
                    </tiles:put>
                    <tiles:put name="confirmText"    value="Удалить выбранные депозитные продукты?"/>
                </tiles:insert>
              </tiles:put>

              <tiles:put name="data">
                  <tr>
                      <td>
                        ${form.listHtml}
                      </td>
                  </tr>
                  <tiles:put name="emptyMessage" value="Не найдено ни одного депозитного продукта,<br>соответствующего заданному фильтру!"/>
            </tiles:put>

        </tiles:insert>
	</tiles:put>

</tiles:insert>

</html:form>
