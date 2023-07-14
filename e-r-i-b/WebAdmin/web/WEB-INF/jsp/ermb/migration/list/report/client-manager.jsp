<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<c:set var="form" value="${ClientManagerForm}"/>
<html:form action="/ermb/migration/clientManager">
    <tiles:insert definition="migrationMain">
        <tiles:put name="submenu" value="ManagerReport"/>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                doOnLoad(function(){
                    if (${form.fields.relocateToDownload != null && form.fields.relocateToDownload == true})
                    {
                        <c:set var="downloadFileURL" value="${phiz:calculateActionURL(pageContext,'/ermb/migration/clientManager/downloading')}"/>
                        clientBeforeUnload.showTrigger=false;
                        goTo('${downloadFileURL}');
                        clientBeforeUnload.showTrigger=false;
                    }
                    turnSelection(true, 'selectedSegments');
                });

                function setDepartmentInfo(result)
                {
                    var departmentTable = $('#departmentTable');
                    var departmentList = departmentTable.find('table.standartTable tbody');
                    if (departmentList.find('input[value=' + result["id"] + ']').length == 0)
                    {
                        departmentTable.find('div.tblNeedTableStyle').show();
                        departmentTable.find('div.workspace-box').remove();

                        var inputList = departmentTable.find('tr[class^="ListLine"] input');
                        departmentList.append(
                                '<tr class="ListLine' + inputList.length % 2 + '"><td class="listItem">' +
                                        '<input type="checkbox" name="selectedDepartments" value=' + result["id"] + '>' +
                                        '</td><td class="listItem">' + result["name"] + '</td></tr>'
                        );
                    }
                }

                function removeDepartment()
                {
                    if (!checkSelection("selectedDepartments", "Не выбран департамент."))
                        return;
                    var departmentTable = $('#departmentTable');

                    //удаляем строки с выбранными ТБ
                    departmentTable.find('tr[class^="ListLine"] input:checked').parent().parent().remove();

                    var inputList = departmentTable.find('tr[class^="ListLine"] input');
                    //если не осталось ни одного ТБ, прячем таблицу и добавляем сообщение
                    if (inputList.length == 0)
                    {
                        departmentTable.find('tr.tblInfHeader input')[0].checked = false;
                        departmentTable.find('div.tblNeedTableStyle').hide();
                        var emptyMsg = '<bean:message key="empty.departments.message" bundle="migrationBundle"/>';
                        departmentTable.append(
                                '<div class="workspace-box roundBorder redBlock"><div class="r-content">' + emptyMsg +
                                        '</div></div>'
                        );
                    }
                }

                function beforeSave()
                {
                    var departments = $('#departmentTable').find('tr[class^="ListLine"] input');
                    if (departments.length == 0)
                    {
                        alert('<bean:message bundle="migrationBundle" key="empty.departments.message"/>');
                        return false;
                    }

                    departments.each(function () {this.checked = true;});
                    return true;
                }
            </script>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="clientManagerReport"/>
                <tiles:put name="name">
                    <bean:message bundle="migrationBundle" key="unloading.client.manager.name"/>
                </tiles:put>
                <tiles:put name="description" value=""/>
                <tiles:put name="data">
                    <h3><bean:message key="unloading.client.manager.segment" bundle="migrationBundle"/></h3>
                    <tiles:insert definition="simpleTableTemplate" flush="false">
                        <tiles:put name="id" value="segmentList"/>
                        <tiles:put name="grid">
                            <sl:collection id="segment" model="list" property="segments" bundle="migrationBundle">
                                <sl:collectionParam id="selectType" value="checkbox"/>
                                <sl:collectionParam id="selectName" value="selectedSegments"/>
                                <sl:collectionParam id="selectProperty" value="value"/>

                                <sl:collectionItem title="label.segment" property="value"/>
                            </sl:collection>
                        </tiles:put>
                    </tiles:insert>

                    <fieldset>
                        <legend>
                            <bean:message key="unloading.client.manager.department" bundle="migrationBundle"/>
                        </legend>

                        <table cellpadding="0" cellspacing="0" width="100%">
                            <tr>
                                <td>
                                    <div style="float:right;">
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.choose.departments"/>
                                            <tiles:put name="commandHelpKey" value="button.choose.departments"/>
                                            <tiles:put name="bundle" value="migrationBundle"/>
                                            <tiles:put name="onclick" value="openDepartmentsDictionary(setDepartmentInfo, 'null', null, true);"/>
                                        </tiles:insert>
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.remove.departments"/>
                                            <tiles:put name="commandHelpKey" value="button.remove.departments"/>
                                            <tiles:put name="bundle" value="migrationBundle"/>
                                            <tiles:put name="onclick" value="removeDepartment();"/>
                                        </tiles:insert>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td id="departmentTable">
                                    <tiles:insert definition="tableTemplate" flush="false">
                                        <tiles:put name="head">
                                            <th width="20px" class="titleTable">
                                                <input name="isSelectedAllItems" type="checkbox" style="border:none"
                                                       onclick="switchSelection('isSelectedAllItems','selectedDepartments')"/>
                                            </th>
                                            <th class="titleTable bold">
                                                <bean:message bundle="migrationBundle" key="label.department"/>
                                            </th>
                                        </tiles:put>

                                        <tiles:put name="data">
                                            <c:forEach var="department" items="${form.departments}" varStatus="lineInfo">
                                                <tr class="ListLine${lineInfo.count%2}">
                                                    <td class="listItem" align="center">
                                                        <input type="checkbox" name="selectedDepartments" value="${department.id}">
                                                    </td>
                                                    <td class="listItem" nowrap="true">
                                                        ${department.name}
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tiles:put>
                                        <tiles:put name="createEmpty" value="true"/>
                                        <tiles:put name="emptyMessage">
                                            <bean:message key="empty.departments.message" bundle="migrationBundle"/>
                                        </tiles:put>
                                    </tiles:insert>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </tiles:put>

                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.unload"/>
                        <tiles:put name="commandHelpKey" value="button.unload.help"/>
                        <tiles:put name="bundle" value="migrationBundle"/>
                        <tiles:put name="validationFunction" value="beforeSave"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>