<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/employees/allowedDepartments" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="employeesEdit">
        <tiles:put name="pageTitle"><bean:message key="form.allowedDepartments.title" bundle="employeesBundle"/></tiles:put>
        <tiles:put name="submenu" type="string" value="ListDepartments"/>
        <tiles:put name="menu" type="string">
            <c:if test="${form.adminHasAllTBAccess}">
                <tiles:insert definition="clientButton" flush="false" operation="EditAllowedDepartmentsOperation">
                    <tiles:put name="commandTextKey" value="button.setAllTBAccess"/>
                    <tiles:put name="commandHelpKey" value="button.setAllTBAccess.help"/>
                    <tiles:put name="bundle" value="employeesBundle"/>
                    <tiles:put name="onclick" value="setAllDepartmentInfo();"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </c:if>
            <tiles:insert definition="clientButton" flush="false" operation="EditAllowedDepartmentsOperation">
                <tiles:put name="commandTextKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add"/>
                <tiles:put name="bundle" value="employeesBundle"/>
                <tiles:put name="onclick" value="openDepartmentList();"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
            <tiles:insert definition="commandButton" flush="false" operation="EditAllowedDepartmentsOperation">
                <tiles:put name="commandKey"         value="button.save"/>
                <tiles:put name="commandHelpKey"     value="button.save.help"/>
                <tiles:put name="bundle"             value="commonBundle"/>
                <tiles:put name="isDefault"          value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.close"/>
                <tiles:put name="commandHelpKey" value="button.close.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="action" value="/employees/list"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <script type="text/javascript">
                var ALL_TB_ACCESS_KEY = "*|*|*";

                function openDepartmentList()
                {
                    openDepartmentsDictionary(setDepartmentInfo, checkDepartmentInfo, null, true);
                }

                function setDepartmentsInfo(departmentId, departmentName, departmentKey)
                {
                    $('.tblNeedTableStyle').show();
                    $('.messageTab').parent().parent().parent().hide();

                    var hiddenInputName = 'addedDepartment';
                    if ($('[name=removedDepartment][value=' + departmentKey + ']').remove().length > 0)
                        hiddenInputName = 'key';

                    var line = '<tr class="ListLine0">' +
                                    '<td class="listItem">' +
                                        '<input type="checkbox" value="' + departmentId + '" name="selectedIds">' +
                                        '<input type="hidden" value="' + departmentKey + '" name="' + hiddenInputName + '">' +
                                    '</td>' +
                                    '<td class="listItem">' + departmentName + '</td>' +
                                '</tr>';
                    $('.standartTable').append(line);
                }

                function maskKey(key)
                {
                    if (isEmpty(key))
                        return '*';
                    return key;
                }

                function getKey(department)
                {
                    return maskKey(department['tb']) + '|' + maskKey(department['osb']) + '|' + maskKey(department['vsp']);
                }

                function setDepartmentInfo(department)
                {
                    setDepartmentsInfo(department['id'], department['name'], getKey(department));
                }

                function setAllDepartmentInfo()
                {
                    var msg = checkDepartmentInfoByKey(ALL_TB_ACCESS_KEY);
                    if (msg == null)
                        setDepartmentsInfo(null, "<bean:message key="form.allowedDepartments.table.row.allTB" bundle="employeesBundle"/>", ALL_TB_ACCESS_KEY)
                    else
                        alert(msg);
                }

                function getKeyLevel(key)
                {
                    var code = key.split('|');
                    if (code[0] == '*')
                        return 0;
                    if (code[1] == '*')
                        return 1;
                    if (code[2] == '*')
                        return 2;

                    return 3;
                }

                function checkDepartmentInfo(department)
                {
                    return checkDepartmentInfoByKey(getKey(department));
                }

                function checkDepartmentInfoByKey(key)
                {
                    var checkboxList = $('[name=selectedIds]');
                    if (checkboxList.length == 0)
                        return null;

                    if ($('[value=' + key + ']').length > 0)
                        return '<bean:message key="form.allowedDepartments.table.message.addError.exist" bundle="employeesBundle"/>';

                    var departmentLevel = getKeyLevel(key);
                    for(var k=0;k<checkboxList.size();k++)
                    {
                        var currentKey = $(checkboxList[k]).parent().find('[type=hidden]').val();
                        var currentLevel = getKeyLevel(currentKey);
                        var keyTb = key.split('|');
                        var currentKeyTb = currentKey.split('|');
                        var min =  currentLevel>departmentLevel ? departmentLevel : currentLevel;
                        var counter = 0;

                        for(var i=0; i < min; i++)
                        {
                            if( currentKeyTb[i] == keyTb[i] )
                            {
                                counter++;
                            }
                            else
                                break;
                        }
                        if (counter == min)
                        {
                            var message = '<bean:message key="form.allowedDepartments.table.message.addError.prefix" bundle="employeesBundle"/> ';
                            if (departmentLevel < 2)
                                message += '<bean:message key="form.allowedDepartments.table.message.addError.tb" bundle="employeesBundle"/>';
                            else if (departmentLevel == 2)
                                message += '<bean:message key="form.allowedDepartments.table.message.addError.osb" bundle="employeesBundle"/>';
                            else
                                message += '<bean:message key="form.allowedDepartments.table.message.addError.vsp" bundle="employeesBundle"/>';
                            return message;
                        }
                    }
                    return null;
                }

                function removeDepartments()
                {
                    var form = $('form[name=EditAllowedDepartmentsForm]');
                    $('[name=selectedIds]:checked').each(function(index, element)
                    {
                        var td = $(element).parent();
                        if (td.find('[name=addedDepartment]').length == 0)
                        {
                            var key = td.find('[name=key]').val();
                            form.append('<input type="hidden" name="removedDepartment" value="' + key + '">');
                        }
                        td.parent().remove();
                    });
                    if ($('[name=selectedIds]').length == 0)
                    {
                        $('.tblNeedTableStyle').hide();
                        $('.messageTab').parent().parent().parent().show();
                    }
                }
            </script>

            <c:set var="allowedDepartments" value="${form.allowedDepartments}"/>

            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="DepartmentsList"/>
                <tiles:put name="createEmpty" value="true"/>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove.help"/>
                        <tiles:put name="bundle"         value="employeesBundle"/>
                        <tiles:put name="onclick"        value="removeDepartments();"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="head">
                    <td width="20px" height="20px" nowrap="true" align="left">
                        <input type="checkbox" style="border:none" onclick="switchSelection('isSelectAll', 'selectedIds');" name="isSelectAll"/>
                    </td>
                    <td style="width:100%"><bean:message key="form.allowedDepartments.table.column.name" bundle="employeesBundle"/></td>
                </tiles:put>
                <tiles:put name="data">
                    <c:forEach items="${allowedDepartments}" var="department">
                        <tr class="ListLine0">
                            <td class="listItem">
                                <input type="checkbox" value="${department.id}" name="selectedIds"<c:if test="${not department.allowed}"> disabled="disabled"</c:if>>
                                <input type="hidden" name="key" value="${department.tb}|${department.osb}|${department.vsp}"/>
                            </td>
                            <td class="listItem"><c:out value="${department.name}"/></td>
                        </tr>
                    </c:forEach>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty allowedDepartments}"/>
                <tiles:put name="emptyMessage"><bean:message key="form.allowedDepartments.table.message.emptyDepartments" bundle="employeesBundle"/></tiles:put>
            </tiles:insert>
            <c:if test="${not empty allowedDepartments}">
                <table width="100%" cellpadding="4" style="display:none">
                    <tr>
                        <td class="messageTab" align="center">
                            <bean:message key="form.allowedDepartments.table.message.emptyDepartments" bundle="employeesBundle"/>
                        </td>
                    </tr>
                </table>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
