<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/departments/replication" enctype="multipart/form-data">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="departmentsMain">

        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function changefileValue()
                {
                    var filePath = document.getElementById('file');
                    var fileName = document.getElementById('file-file-name');
                    fileName.value = filePath.value.split('/').pop().split('\\').pop();
                }

                function setChoiceDepartmentMode(mode)
                {
                    if(mode == "all")
                    {
                        updateShowButtons(false);
                        updateShowTable(false);
                    }
                    else if(mode == "selected")
                    {
                        updateShowButtons(true);
                        updateShowTable(true);
                    }
                }

                function deleteSelectedDepartment()
                {
                    var checkeds = $("#selectedDepartmentsTable").find("input:checked").not("input#selectAll");
                    var unCheckeds = $("#selectedDepartmentsTable").find("input").filter(":not(input:checked)").filter(":not(input#selectAll)").filter(":not(input:hidden)");

                    if(checkeds.length > 0)
                        delElements(checkeds);
                    else if(unCheckeds.length == 1)
                        delElements(unCheckeds);
                    else
                        alert("Выберите подразделение!");

                    updateShowTable(true);
                    updateShowButtons(true);
                }

                function delElements(elements)
                {
                    elements.each(function(){
                        $(this).closest("tr").remove();
                    });
                }

                function appendSelectedDepartment(depInfo)
                {
                    <%-- Проверяем что не было выбрано более высого уровня --%>
                    if(depInfo.parent != null && $("#selectedDepartmentsTable table").find("tr#department" + depInfo.parent.id).length > 0)
                    {
                        return;
                    }
                    <%-- удаляем уже добавленных детей если добавляем родителя целиком --%>
                    $("#selectedDepartmentsTable table").find("tr").has("span.parent-department-" + depInfo.id).remove();
                    $("#selectedDepartmentsTable table>tbody").append(createCommonRow(depInfo));

                    updateShowTable(true);
                    updateShowButtons(true);
                }

                function getLevel(depInfo, n)
                {
                    var count = n == null ? 0 : n;
                    if(depInfo.parent == null)
                        return count;

                    return getLevel(depInfo.parent, count) + 1;
                }

                function checkAppendedDepartment(depInfo)
                {
                    <%-- проверяем на уже добавленное --%>
                    if(isExistDep(depInfo))
                    {
                        return "Данное подразделение уже добавлено\n" + depInfo.name;
                    }

                    <%-- только первые два уровня --%>
                    if(getLevel(depInfo) > 1)
                    {
                        return "Выберите подразделения только уровня ТБ или ОСБ";
                    }

                    return null;
                }

                function isExistDep(depInfo)
                {
                    return $("#selectedDepartmentsTable table.tblInf")
                            .find("input[name='selectedIds']")
                            .filter("input[value='" + depInfo.id +"']")
                            .length > 0;
                }

                function updateShowButtons(flag)
                {
                    if(flag)
                    {
                        $("#addButton").show();
                        if($("#selectedDepartmentsTable table.tblInf").find("tr").filter(":not(.tblInfHeader)").length > 0)
                            $("#removeButton").show();
                        else
                            $("#removeButton").hide();
                    }
                    else
                    {
                        $("#addButton, #removeButton").hide();
                    }
                }

                function updateShowTable(flag)
                {
                    if(flag)
                    {
                        if($("#selectedDepartmentsTable table.tblInf").find("tr").filter(":not(.tblInfHeader)").length > 0)
                            $("#selectedDepartmentsTable").show();
                        else
                            $("#selectedDepartmentsTable").hide();    
                    }
                    else
                    {
                        $("#selectedDepartmentsTable").hide();
                    }

                    $("input", ensureElement("selectedDepartmentsTable")).each(function(){
                        this.disabled = !flag;
                    });
                }

                function createCommonRow(depInfo)
                {
                    var tr = document.createElement("tr");
                    tr.id = "department" + depInfo.id;

                    var checkboxCell = document.createElement("td");
                    checkboxCell.vAlign = "top";
                    checkboxCell.width = "20px";

                    var valueCell = document.createElement("td");
                    valueCell.vAlign = "top";
                    valueCell.align = "left";

                    $(valueCell).addClass("name-department");

                    tr.appendChild(checkboxCell);
                    tr.appendChild(valueCell);

                    var checkbox = document.createElement("input");
                    checkbox.setAttribute("type","checkbox");
                    checkbox.id = "dep" + depInfo.id;
                    checkboxCell.appendChild(checkbox);

                    if(depInfo.parent == null)
                    {
                        var wrapMain = document.createElement("div");
                        wrapMain.className = "bold";
                        wrapMain.appendChild(document.createTextNode(depInfo.name));
                        valueCell.appendChild(wrapMain);
                    }
                    else
                    {
                        var wrapSub = document.createElement("div");
                        wrapSub.appendChild(document.createTextNode(depInfo.name));

                        var parentWrap = document.createElement("span");
                        parentWrap.className = " parent-department-" + depInfo.parent.id;
                        parentWrap.appendChild(document.createTextNode("(" + depInfo.parent.name + ")"));

                        wrapSub.appendChild(parentWrap);
                        valueCell.appendChild(wrapSub);
                    }

                    var hidden = document.createElement("input");
                    hidden.setAttribute("type","hidden");
                    hidden.setAttribute("name","selectedIds");
                    hidden.setAttribute("value", depInfo.id);
                    valueCell.appendChild(hidden);

                    return tr;
                }

                function switchSelectedAll()
                {
                    var elements = document.getElementsByName("selectedIds");
                    for(var i = 0; i < elements.length; i++)
                    {
                        var element = document.getElementById('dep'+elements.item(i).value);
                        if($("#selectedDepartmentsTable").find("input#selectAll").filter("input:checked").length > 0)
                            element.checked = true;
                        else
                            element.checked = false;
                    }
                }

                <c:if test="${fn:length(form.selectedDepartments) > 0}">
                    $(document).ready(function(){
                        $("#selected").click();

                        var depInfo;
                        <c:forEach var="dep" items="${form.selectedDepartments}">
                            <c:if test="${!phiz:isVSPOffice(dep)}">
                                depInfo =   <tiles:insert page="depInfoJS.jsp" flush="false">
                                                <tiles:put name="currentDep" beanName="dep"/>
                                            </tiles:insert>
                                if(checkAppendedDepartment(depInfo) == null)
                                    appendSelectedDepartment(depInfo);
                            </c:if>
                        </c:forEach>
                    });
                </c:if>
            </script>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message bundle="departmentsBundle" key="background.replication.name"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="departmentsBundle" key="background.replication.description"/>
                </tiles:put>

                <tiles:put name="data" type="string">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="background.replication.label.file.name" bundle="departmentsBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <input type="hidden" id="file-file-name" name="field(fileName)">
                            <input type="file" id="file" name="file" size="70" class="contactInput" onchange="changefileValue();"/>
                        </tiles:put>
                    </tiles:insert>

                    <fieldset width="100%">
                        <legend>
                            <bean:message key="background.replication.label.departments" bundle="departmentsBundle"/>
                        </legend>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <div style="float:left">
                                    <input type="radio" name="type" value="all" id="all" checked="true" onclick="setChoiceDepartmentMode('all');">
                                    <bean:message key="background.replication.label.departments.all" bundle="departmentsBundle"/>
                                </div>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <div style="float:left">
                                    <input type="radio" name="type" value="selected" id="selected" onclick="setChoiceDepartmentMode('selected');">
                                    <bean:message key="background.replication.label.departments.selected" bundle="departmentsBundle"/>
                                </div>
                            </tiles:put>
                            <tiles:put name="data">
                                <div>
                                    <span id="addButton" style="display: none">
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.replic.add"/>
                                            <tiles:put name="commandHelpKey" value="button.replic.add.help"/>
                                            <tiles:put name="bundle"         value="departmentsBundle"/>
                                            <tiles:put name="onclick"        value="openDepartmentsDictionary(appendSelectedDepartment, checkAppendedDepartment, 2, true)"/>
                                            <tiles:put name="typeBtn"        value="buttonGrayNew"/>
                                        </tiles:insert>
                                    </span>
                                    <span id="removeButton" style="display: none">
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.replic.remove"/>
                                            <tiles:put name="commandHelpKey" value="button.replic.remove.help"/>
                                            <tiles:put name="bundle"         value="departmentsBundle"/>
                                            <tiles:put name="onclick"        value="deleteSelectedDepartment()"/>
                                            <tiles:put name="typeBtn"        value="buttonGrayNew"/>
                                        </tiles:insert>
                                    </span>
                                </div>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <table width="100%" id="selectedDepartmentsTable" class="depositProductInfo" style="display:none">
                                    <tr>
                                        <td style="border:none;">
                                            <table width="100%" cellspacing="0" cellpadding="0" align="CENTER" class="tblInf">
                                                <tr class="tblInfHeader" style="padding: 0;">
                                                    <th width="20px" class="titleTable" style="padding: 0; text-align:center !important;">
                                                        <input id="selectAll" type="checkbox" onclick="switchSelectedAll()"/>
                                                    </th>
                                                    <th class="titleTable" style="padding: 0;">
                                                        &nbsp;<bean:message bundle="providerBundle" key="label.field.autopay.department.values"/>&nbsp;
                                                    </th>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </tiles:put>
                        </tiles:insert>

                    </fieldset>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="data">
                        <html:radio property="replicationMode" name="form" value="report"/>
                        <bean:message key="background.replication.edit.label.mode.report" bundle="departmentsBundle"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="data">
                        <html:radio property="replicationMode" name="form" value="replica"/>
                        <bean:message key="background.replication.edit.label.mode.replica" bundle="departmentsBundle"/>
                    </tiles:put>
                </tiles:insert>

                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.replic.load"/>
                        <tiles:put name="commandHelpKey" value="button.replic.load.help"/>
                        <tiles:put name="bundle"         value="departmentsBundle"/>
                    </tiles:insert>

                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.replic.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.replic.cancel.help"/>
                        <tiles:put name="bundle"         value="departmentsBundle"/>
                        <tiles:put name="action"         value="/departments/list.do"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>