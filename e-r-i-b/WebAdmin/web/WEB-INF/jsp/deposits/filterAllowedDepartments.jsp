<%--
  Created by IntelliJ IDEA.
  User: osminin
  Date: 13.06.2009
  Time: 14:19:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>

<html:form action="/deposits/filterDepartments/list" onsubmit="return setEmptyAction(event);">

<tiles:insert definition="dictionary">
    <tiles:put name="pageTitle" type="string">Список доступных подразделений</tiles:put>
    <tiles:put name="submenu" type="string" value="allowedDepartments"/>
    <tiles:put name="menu" type="string">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.filterCancel"/>
            <tiles:put name="commandHelpKey" value="button.filterCancel.help"/>
            <tiles:put name="bundle" value="depositsBundle"/>
            <tiles:put name="onclick">window.close();</tiles:put>
            <tiles:put name="viewType" value="blueBorder"/>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="data" type="string">

        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="AllowedDepartmentListTable"/>
            <tiles:put name="buttons">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.choose"/>
                    <tiles:put name="commandHelpKey" value="button.choose.help"/>
                    <tiles:put name="bundle" value="depositsBundle"/>
                    <tiles:put name="onclick" value="setDepartmentData(event)"/>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="data">
                <tr>
                    <td>
                        <html:radio property="selectedIds" value="all"/>
                    </td>
                    <td>
                        Все
                    </td>
                </tr>
                <logic:iterate id="department" name="GetAllowedDepartmentsListForm" property="departments">
                    <tr>
                        <td>
                            <html:radio property="selectedIds" value="${department.id}"/>
                        </td>
                        <td>
                            <bean:write name="department" property="name"/>
                        </td>
                    </tr>
                </logic:iterate>
            </tiles:put>
        </tiles:insert>

    </tiles:put>
    <script type="text/javascript">
        var addUrl = "${phiz:calculateActionURL(pageContext,'/deposits/edit')}";

        function setDepartmentData(event)
        {
            var ids = document.getElementsByName("selectedIds");
            preventDefault(event);
            for (var i=0; i<ids.length; i++)
            {
                if (ids.item(i).checked)
                {
                    var r = ids.item(i).parentNode.parentNode;
					var a = new Array(2);
					a['name'] = trim(r.cells[1].innerHTML);
					a['id'] = ids.item(i).value;
                    window.opener.setDepartmentInfo(a);
                    window.close();
                    return;
                }
            }
            alert("Выберите подразделения!");
        }
    </script>
</tiles:insert>

</html:form>